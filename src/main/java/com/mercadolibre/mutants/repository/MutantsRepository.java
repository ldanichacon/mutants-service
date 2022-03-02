package com.mercadolibre.mutants.repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.mercadolibre.mutants.model.MutantsRequest;
import com.mercadolibre.mutants.model.StatsResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Slf4j
public class MutantsRepository {
    @ConfigProperty(name = "datastore.project_id")
    String projectId;
    @ConfigProperty(name = "datastore.collection")
    String collection;
    private Firestore firestore;

    @PostConstruct
    public void initDatastore() throws IOException {
        this.firestore = FirestoreOptions
                .getDefaultInstance()
                .toBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
                .getService();
    }

    public void saveDna(MutantsRequest request, boolean isMutant) {
        DocumentReference docRef = firestore.collection(collection).document();
        Map<String, Object> data = new HashMap<>();
        data.put("dna", request.getDna());
        data.put("isMutant", isMutant);
        ApiFuture<WriteResult> result = docRef.set(data);
        try {
            log.info("Update time : " + result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalServerErrorException();
        }
    }

    public StatsResponse getStats() {
        CollectionReference mutants = firestore.collection(collection);
        Query queryMutant = mutants.whereEqualTo("isMutant", true);
        ApiFuture<QuerySnapshot> querySnapshotMutant = queryMutant.get();
        ApiFuture<QuerySnapshot> querySnapshotHuman = mutants.get();
        try {
            Double mutantDna = (double) querySnapshotMutant.get().getDocuments().size();
            Double humanDna = (double) querySnapshotHuman.get().getDocuments().size();
            return StatsResponse
                    .builder()
                    .mutantDna(mutantDna)
                    .humanDna(humanDna)
                    .ratio(mutantDna / humanDna)
                    .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new InternalServerErrorException();
        }
    }
}