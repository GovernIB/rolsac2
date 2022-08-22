package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "Procedimiento")
public class ProcedimientoDTO extends ModelApi {
    private Long codigo;
    private boolean publicado;

    private Integer codigoSIA;

    private Boolean estadoSIA;

    private Date fechaSIA;
    private String signatura;
    private List<TramiteDTO> tramites;
    private List<ProcedimientoDocumentoDTO> documentos;
    private Set<NormativaDTO> normativas;
    private Date fechaCaducidad;
    private Date fechaPublicacion;
    private Date fechaActualizacion;

    private String tramite;
    private Long version;
    private String url;
    private Long orden;
    private Long orden2;
    private Long orden3;
    private Integer validacion;
    private UnidadAdministrativaDTO unidadAdministrativa; // organisme responsable
    //private Familia familia;
    private TipoFormaInicioDTO iniciacion;
    private String indicador;
    private String ventanillaUnica;
    // #351 se cambia info por dirElectronica
    // private String info;
    private String responsable;
    //private Set<HechoVitalProcedimiento> hechosVitalesProcedimientos;
    private Set<TipoPublicoObjetivoDTO> publicosObjetivo;
    private String taxa;
    private UnidadAdministrativaDTO organResolutori;
    private UnidadAdministrativaDTO servicioResponsable;
    private String dirElectronica;
    private TipoSilencioAdministrativoDTO silencio;
    private boolean comun;
    private boolean pendienteValidar;

    // ---------------------------------------------
    // Campos especiales para optimizar la búsqueda
    private String nombreProcedimiento;
    private String nombreFamilia;
    private String idioma;

    // LOPD
    private String lopdResponsable;
    //private LopdLegitimacion lopdLegitimacion;
    /**
     * Lopd Transient
     **/
    private String lopdFinalidad;
    private String lopdDestinatario;
    private String lopdDerechos;
    private Long lopdInfoAdicional;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public void setEstadoSIA(Boolean estadoSIA) {
        this.estadoSIA = estadoSIA;
    }


    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public String getSignatura() {
        return signatura;
    }

    public void setSignatura(String signatura) {
        this.signatura = signatura;
    }

    public List<TramiteDTO> getTramites() {
        return tramites;
    }

    public void setTramites(List<TramiteDTO> tramites) {
        this.tramites = tramites;
    }

    public List<ProcedimientoDocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<ProcedimientoDocumentoDTO> documentos) {
        this.documentos = documentos;
    }

    public Set<NormativaDTO> getNormativas() {
        return normativas;
    }

    public void setNormativas(Set<NormativaDTO> normativas) {
        this.normativas = normativas;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Long getOrden2() {
        return orden2;
    }

    public void setOrden2(Long orden2) {
        this.orden2 = orden2;
    }

    public Long getOrden3() {
        return orden3;
    }

    public void setOrden3(Long orden3) {
        this.orden3 = orden3;
    }

    public Integer getValidacion() {
        return validacion;
    }

    public void setValidacion(Integer validacion) {
        this.validacion = validacion;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public TipoFormaInicioDTO getIniciacion() {
        return iniciacion;
    }

    public void setIniciacion(TipoFormaInicioDTO iniciacion) {
        this.iniciacion = iniciacion;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getVentanillaUnica() {
        return ventanillaUnica;
    }

    public void setVentanillaUnica(String ventanillaUnica) {
        this.ventanillaUnica = ventanillaUnica;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }


    public Set<TipoPublicoObjetivoDTO> getPublicosObjetivo() {
        return publicosObjetivo;
    }

    public void setPublicosObjetivo(Set<TipoPublicoObjetivoDTO> publicosObjetivo) {
        this.publicosObjetivo = publicosObjetivo;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public UnidadAdministrativaDTO getOrganResolutori() {
        return organResolutori;
    }

    public void setOrganResolutori(UnidadAdministrativaDTO organResolutori) {
        this.organResolutori = organResolutori;
    }

    public UnidadAdministrativaDTO getServicioResponsable() {
        return servicioResponsable;
    }

    public void setServicioResponsable(UnidadAdministrativaDTO servicioResponsable) {
        this.servicioResponsable = servicioResponsable;
    }

    public String getDirElectronica() {
        return dirElectronica;
    }

    public void setDirElectronica(String dirElectronica) {
        this.dirElectronica = dirElectronica;
    }


    public Date getFechaSIA() {
        return fechaSIA;
    }

    public void setFechaSIA(Date fechaSIA) {
        this.fechaSIA = fechaSIA;
    }


    public TipoSilencioAdministrativoDTO getSilencio() {
        return silencio;
    }

    public void setSilencio(TipoSilencioAdministrativoDTO silencio) {
        this.silencio = silencio;
    }

    public boolean isComun() {
        return comun;
    }

    public void setComun(boolean comun) {
        this.comun = comun;
    }

    public boolean isPendienteValidar() {
        return pendienteValidar;
    }

    public void setPendienteValidar(boolean pendienteValidar) {
        this.pendienteValidar = pendienteValidar;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getLopdResponsable() {
        return lopdResponsable;
    }

    public void setLopdResponsable(String lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    public String getLopdFinalidad() {
        return lopdFinalidad;
    }

    public void setLopdFinalidad(String lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    public String getLopdDestinatario() {
        return lopdDestinatario;
    }

    public void setLopdDestinatario(String lopdDestinatario) {
        this.lopdDestinatario = lopdDestinatario;
    }

    public String getLopdDerechos() {
        return lopdDerechos;
    }

    public void setLopdDerechos(String lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    public Long getLopdInfoAdicional() {
        return lopdInfoAdicional;
    }

    public void setLopdInfoAdicional(Long lopdInfoAdicional) {
        this.lopdInfoAdicional = lopdInfoAdicional;
    }


    @Override
    public String toString() {
        return "ProcedimientoDTO{" +
                "id=" + codigo +
                ", responsable='" + responsable + '\'' +
                '}';
    }
}
