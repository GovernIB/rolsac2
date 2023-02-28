package es.caib.rolsac2.commons.plugins.dir3.api.model;

import es.caib.rolsac2.commons.plugins.dir3.api.UtilsDir3;

import java.math.BigDecimal;
import java.text.ParseException;

public class UnidadOrganicaResponse {
    private String codigo;

    private Integer version;

    private String denominacion;

    private String codigoEstadoEntidad;

    private String codUnidadSuperior;

    private String codUnidadRaiz;

    private String competencias;

    private BigDecimal fechaAltaOficial;

    private BigDecimal fechaBajaOficial;

    private Long nivelJerarquico;
    private Long nivelAdministracion;

    private String descripcionLocalidad;

    private String codPostal;


    private String nifCif;

    public UnidadOrganica createUnidadOrganica() {
        UnidadOrganica unidad = new UnidadOrganica();
        unidad.setCodUnidadRaiz(this.codUnidadRaiz);
        unidad.setCodigo(this.codigo);
        unidad.setCodUnidadSuperior(this.codUnidadSuperior);
        unidad.setCompetencias(this.competencias);
        unidad.setDenominacion(this.denominacion);
        unidad.setCodPostal(this.codPostal);
        unidad.setDescripcionLocalidad(this.descripcionLocalidad);
        unidad.setNifCif(this.nifCif);
        unidad.setCodigoEstadoEntidad(this.codigoEstadoEntidad);
        unidad.setNivelJerarquico(this.nivelJerarquico);
        unidad.setVersion(this.version);
        unidad.setNivelAdministracion(this.nivelAdministracion);
        if(this.fechaAltaOficial != null) {
            UtilsDir3.parsearFecha(this.fechaAltaOficial);
        }
        if(this.fechaBajaOficial != null) {
            UtilsDir3.parsearFecha(this.fechaBajaOficial);
        }


        return unidad;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigoEstadoEntidad() {
        return codigoEstadoEntidad;
    }

    public void setCodigoEstadoEntidad(String codigoEstadoEntidad) {
        this.codigoEstadoEntidad = codigoEstadoEntidad;
    }

    public String getCodUnidadSuperior() {
        return codUnidadSuperior;
    }

    public void setCodUnidadSuperior(String codUnidadSuperior) {
        this.codUnidadSuperior = codUnidadSuperior;
    }

    public String getCodUnidadRaiz() {
        return codUnidadRaiz;
    }

    public void setCodUnidadRaiz(String codUnidadRaiz) {
        this.codUnidadRaiz = codUnidadRaiz;
    }

    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public BigDecimal getFechaAltaOficial() {
        return fechaAltaOficial;
    }

    public void setFechaAltaOficial(BigDecimal fechaAltaOficial) {
        this.fechaAltaOficial = fechaAltaOficial;
    }

    public BigDecimal getFechaBajaOficial() {
        return fechaBajaOficial;
    }

    public void setFechaBajaOficial(BigDecimal fechaBajaOficial) {
        this.fechaBajaOficial = fechaBajaOficial;
    }

    public Long getNivelJerarquico() {
        return nivelJerarquico;
    }

    public void setNivelJerarquico(Long nivelJerarquico) {
        this.nivelJerarquico = nivelJerarquico;
    }

    public Long getNivelAdministracion() {
        return nivelAdministracion;
    }

    public void setNivelAdministracion(Long nivelAdministracion) {
        this.nivelAdministracion = nivelAdministracion;
    }

    public String getDescripcionLocalidad() {
        return descripcionLocalidad;
    }

    public void setDescripcionLocalidad(String descripcionLocalidad) {
        this.descripcionLocalidad = descripcionLocalidad;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getNifCif() {
        return nifCif;
    }

    public void setNifCif(String nifCif) {
        this.nifCif = nifCif;
    }


}
