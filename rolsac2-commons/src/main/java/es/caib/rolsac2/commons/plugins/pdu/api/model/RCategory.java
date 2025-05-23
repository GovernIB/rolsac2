package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RCategory {

    /** Categoria
     *
     */
    @JsonProperty("category")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
