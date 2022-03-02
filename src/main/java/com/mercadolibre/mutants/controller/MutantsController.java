package com.mercadolibre.mutants.controller;

import com.mercadolibre.mutants.model.MutantsRequest;
import com.mercadolibre.mutants.model.StatsResponse;
import com.mercadolibre.mutants.service.MutantsService;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class MutantsController {
    @Inject
    MutantsService service;

    @POST
    @Path("/mutant")
    public Response isMutant(MutantsRequest request) {
        if (service.isMutant(request)) {
            return Response.ok().build();
        }
        throw new ForbiddenException();
    }

    @GET
    @Path("/stats")
    public StatsResponse getStats() {
        return service.getStats();
    }
}