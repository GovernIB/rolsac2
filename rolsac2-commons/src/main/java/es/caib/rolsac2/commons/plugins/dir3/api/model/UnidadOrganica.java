package es.caib.rolsac2.commons.plugins.dir3.api.model;

import java.util.Date;

public class UnidadOrganica {
    private String codigo;

    private Integer version;

    private String denominacion;

    private String codigoEstadoEntidad;

    private String codUnidadSuperior;

    private String codUnidadRaiz;

    private String competencias;

    private Date fechaAltaOficial;

    private Date fechaBajaOficial;

    private Long nivelJerarquico;
    private Long nivelAdministracion;

    private String descripcionLocalidad;

    private String codPostal;

    private String nifCif;

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

    public Date getFechaAltaOficial() {
        return fechaAltaOficial;
    }

    public void setFechaAltaOficial(Date fechaAltaOficial) {
        this.fechaAltaOficial = fechaAltaOficial;
    }

    public Date getFechaBajaOficial() {
        return fechaBajaOficial;
    }

    public void setFechaBajaOficial(Date fechaBajaOficial) {
        this.fechaBajaOficial = fechaBajaOficial;
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


    @Override
    public String toString() {
        return "UnidadOrganica{" +
                "codigo='" + codigo + '\'' +
                ", version=" + version +
                ", denominacion='" + denominacion + '\'' +
                ", codigoEstadoEntidad='" + codigoEstadoEntidad + '\'' +
                ", codUnidadSuperior='" + codUnidadSuperior + '\'' +
                ", codUnidadRaiz='" + codUnidadRaiz + '\'' +
                ", competencias='" + competencias + '\'' +
                ", fechaAltaOficial=" + fechaAltaOficial +
                ", fechaBajaOficial=" + fechaBajaOficial +
                ", nivelJerarquico=" + nivelJerarquico +
                ", nivelAdministracion=" + nivelAdministracion +
                ", descripcionLocalidad='" + descripcionLocalidad + '\'' +
                ", codPostal='" + codPostal + '\'' +
                ", nifCif='" + nifCif + '\'' +
                '}';
    }
}
