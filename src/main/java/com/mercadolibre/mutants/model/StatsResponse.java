package com.mercadolibre.mutants.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponse {
    @JsonProperty("count_mutant_dna")
    private Double mutantDna;
    @JsonProperty("count_human_dna")
    private Double humanDna;
    private Double ratio;
}