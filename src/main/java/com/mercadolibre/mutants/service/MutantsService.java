package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.model.MutantsRequest;
import com.mercadolibre.mutants.model.StatsResponse;
import com.mercadolibre.mutants.repository.MutantsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MutantsService {
    private static final String MUTANT_REGEX = "^.*(A{4}|C{4}|T{4}|G{4}).*$";
    @Inject
    MutantsRepository repository;

    public boolean isMutant(MutantsRequest request) {
        boolean isMutant = true;
        if (validateRequest(request.getDna())) {
            if (!validateMatrix(request.getDna())) {
                if (!validateMatrix(transposeDna(request.getDna()))) {
                    isMutant = validateMatrix(getDiagonal(request.getDna()));
                }
            }
        } else {
            isMutant = false;
        }
        repository.saveDna(request, isMutant);
        return isMutant;
    }

    public StatsResponse getStats() {
        return repository.getStats();
    }

    private Boolean validateSequence(String sequence, String regex) {
        return sequence.matches(regex);
    }

    private boolean validateRequest(List<String> dna) {
        if (dna.size() < 4) {
            return false;
        }
        String regex = "^[ATCG]{" + dna.size() + "}$";
        dna.forEach(dnaString -> {
            if (!validateSequence(dnaString, regex)) {
                throw new BadRequestException();
            }
        });
        return true;
    }

    private Boolean validateMatrix(List<String> dna) {
        for (String dnaString : dna) {
            if (validateSequence(dnaString, MUTANT_REGEX)) {
                return true;
            }
        }
        return false;
    }

    private List<String> transposeDna(List<String> dna) {
        List<String> transposed = new ArrayList<>();
        dna.forEach(dnaString -> {
            for (int i = 0; i < dnaString.length(); i++) {
                if (transposed.size() <= i) {
                    transposed.add(String.valueOf(dnaString.charAt(i)));
                } else {
                    transposed.set(i, transposed.get(i).concat(String.valueOf(dnaString.charAt(i))));
                }
            }
        });
        return transposed;
    }

    private List<String> getDiagonal(List<String> dna) {
        List<String> diagonals = new ArrayList<>();
        for (int i = 0; i < (dna.size() * 2) - 1; i++) {
            int initialRow = i < dna.size() ? 0 : i + 1 - dna.size();
            int initialInverseRow = dna.size() - 1 - initialRow;
            int count = i < dna.size() ? i + 1 : dna.size() - initialRow;
            diagonals.add("");
            diagonals.add("");
            for (int j = 0; j < count; j++) {
                int row = initialRow + j;
                int inverseRow = initialInverseRow - j;
                int column = i < dna.size() ? dna.size() - 1 - i + j : j;
                diagonals.set(i * 2, diagonals.get(i * 2).concat(String.valueOf(dna.get(row).charAt(column))));
                diagonals.set((i * 2) + 1, diagonals.get((i * 2) + 1).concat(String.valueOf(dna.get(inverseRow).charAt(column))));
            }
        }
        return diagonals.stream().filter(sequence -> sequence.length() >= 4).collect(Collectors.toList());
    }
}