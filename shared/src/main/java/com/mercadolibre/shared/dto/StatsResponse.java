package com.mercadolibre.shared.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "count_mutant_dna",
        "count_human_dna",
        "ratio"
})
public class StatsResponse {

    @JsonProperty("count_mutant_dna")
    private Integer countMutantDna;
    @JsonProperty("count_human_dna")
    private Integer countHumanDna;
    @JsonProperty("ratio")
    private Double ratio;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("count_mutant_dna")
    public Integer getCountMutantDna() {
        return countMutantDna;
    }

    @JsonProperty("count_mutant_dna")
    public void setCountMutantDna(Integer countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    @JsonProperty("count_human_dna")
    public Integer getCountHumanDna() {
        return countHumanDna;
    }

    @JsonProperty("count_human_dna")
    public void setCountHumanDna(Integer countHumanDna) {
        this.countHumanDna = countHumanDna;
    }

    @JsonProperty("ratio")
    public Double getRatio() {
        return ratio;
    }

    @JsonProperty("ratio")
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
