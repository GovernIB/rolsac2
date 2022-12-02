package es.caib.rolsac2.commons.plugins.boletin.api.model;

import com.hp.hpl.jena.rdf.model.Model;

import java.util.HashMap;
import java.util.Map;

public class ParametrosEBoib {
    private Integer numeroRegistros;

    private Map<String, Model> secciones;

    private Map<String, Model> tipoPublicaciones;

    public ParametrosEBoib() {
        this.numeroRegistros = 0;
        this.secciones = new HashMap<>();
        this.tipoPublicaciones = new HashMap<>();

    }

    public Integer getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(Integer numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public Map<String, Model> getSecciones() {
        return secciones;
    }

    public void setSecciones(Map<String, Model> secciones) {
        this.secciones = secciones;
    }

    public Map<String, Model> getTipoPublicaciones() {
        return tipoPublicaciones;
    }

    public void setTipoPublicaciones(Map<String, Model> tipoPublicaciones) {
        this.tipoPublicaciones = tipoPublicaciones;
    }
}
