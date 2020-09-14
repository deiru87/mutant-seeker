package com.mercadolibre.shared.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dna"
})
public class SeekerServiceRequest {

    @JsonProperty("dna")
    private List<String> dna = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("dna")
    public List<String> getDna() {
        return dna;
    }

    @JsonProperty("dna")
    public void setDna(List<String> dna) {
        this.dna = dna;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "SeekerServiceRequest{" +
                "dna=" + dna +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}