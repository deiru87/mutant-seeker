package com.mercadolibre.shared.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dna",
        "isMutant"
})
public class Dna {

    @JsonProperty("dna")
    private List<String> dna = null;
    @JsonProperty("isMutant")
    private Boolean isMutant;
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

    @JsonProperty("isMutant")
    public Boolean getIsMutant() {
        return isMutant;
    }

    @JsonProperty("isMutant")
    public void setIsMutant(Boolean isMutant) {
        this.isMutant = isMutant;
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
        return "Dna{" +
                "dna=" + dna +
                ", isMutant=" + isMutant +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
