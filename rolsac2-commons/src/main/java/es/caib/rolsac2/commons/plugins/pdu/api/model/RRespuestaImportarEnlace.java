package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RRespuestaImportarEnlace {


    /**
     * Código de estado
     */
    @JsonProperty("status")
    private Integer codigoEstado;

    /**
     * Lista de Enlaces
     */
    @JsonProperty("data")
    private List<String> enlaces;

    /**
     * Mensaje de estado
     */
    @JsonProperty("message")
    private String mesaje;

    /**
     * @return the codigoEstado
     */
    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    /**
     * @param codigoEstado the codigoEstado to set
     */
    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    /**
     * @return the enlaces
     */
    public List<String> getEnlaces() {
        return enlaces;
    }

    /**
     * @param enlaces the enlaces to set
     */
    public void setEnlaces(List<String> enlaces) {
        this.enlaces = enlaces;
    }

    /**
     * @return the mesaje
     */
    public String getMesaje() {
        return mesaje;
    }

    @Override
    public String toString() {
        return "RRespuestaImportarEnlace [codigoEstado=" + codigoEstado + ", enlaces=" + enlaces + ", mesaje=" + mesaje + "]";
    }

}