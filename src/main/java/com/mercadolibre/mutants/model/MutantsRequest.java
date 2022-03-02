package com.mercadolibre.mutants.model;

import lombok.Data;

import java.util.List;

@Data
public class MutantsRequest {
    private List<String> dna;
}