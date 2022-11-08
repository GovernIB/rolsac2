package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorfklow;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "Procedimiento")
public class ProcedimientoDTO extends ModelApi {
    private Long codigo;
    private Long codigoWF;
    private TypeProcedimientoWorfklow workflow;
    private TypeProcedimientoEstado estado;
    private boolean interno;
    private boolean publicado;
    private boolean datosPersonalesActivo;

    private Integer codigoSIA;

    private String tipo;

    private Boolean estadoSIA;
    private Literal nombreProcedimientoWorkFlow;
    private Literal datosPersonalesFinalidad;
    private Literal datosPersonalesDestinatario;
    private Literal requisitos;
    private Date fechaSIA;
    private String signatura;
    private List<ProcedimientoTramiteDTO> tramites;
    private List<ProcedimientoDocumentoDTO> documentos;
    private List<NormativaGridDTO> normativas;
    private Date fechaCaducidad;
    private Date fechaPublicacion;
    private Date fechaActualizacion;

    private TipoLegitimacionDTO datosPersonalesLegitimacion;
    private String tramite;
    private Long version;
    private String url;
    private Long orden;
    private Long orden2;
    private Long orden3;
    private Integer validacion;
    private UnidadAdministrativaDTO uaResponsable;
    private UnidadAdministrativaDTO uaInstructor;
    //private Familia familia;
    private TipoFormaInicioDTO iniciacion;
    private String indicador;
    private String ventanillaUnica;
    // #351 se cambia info por dirElectronica
    // private String info;
    private String responsable;
    //private Set<HechoVitalProcedimiento> hechosVitalesProcedimientos;
    private List<TipoPublicoObjetivoDTO> publicosObjetivo;
    private Boolean taxa;
    private UnidadAdministrativaDTO organResolutori;
    private UnidadAdministrativaDTO servicioResponsable;
    private String dirElectronica;
    private TipoSilencioAdministrativoDTO silencio;
    private TipoProcedimientoDTO tipoProcedimiento;
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

    private List<TipoPublicoObjetivoEntidadGridDTO> tiposPubObjEntGrid;
    private List<TipoMateriaSIAGridDTO> materiasGridSIA;
    private List<NormativaGridDTO> normativasGrid;


    public static ProcedimientoDTO createInstance(List<String> idiomas) {
        ProcedimientoDTO proc = new ProcedimientoDTO();
        proc.setWorkflow(TypeProcedimientoWorfklow.MODIFICACION);
        proc.setEstado(TypeProcedimientoEstado.MODIFICACION);
        proc.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        proc.setDatosPersonalesDestinatario(Literal.createInstance(idiomas));
        proc.setDatosPersonalesFinalidad(Literal.createInstance(idiomas));
        proc.setRequisitos(Literal.createInstance(idiomas));
        return proc;
    }

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

    public Literal getNombreProcedimientoWorkFlow() {
        return nombreProcedimientoWorkFlow;
    }

    public Literal getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombreProcedimientoWorkFlow(Literal nombreProcedimientoWorkFlow) {
        this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
    }

    public Literal getDatosPersonalesFinalidad() {
        return datosPersonalesFinalidad;
    }

    public void setDatosPersonalesFinalidad(Literal datosPersonalesFinalidad) {
        this.datosPersonalesFinalidad = datosPersonalesFinalidad;
    }

    public TipoLegitimacionDTO getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(TipoLegitimacionDTO datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public Literal getDatosPersonalesDestinatario() {
        return datosPersonalesDestinatario;
    }

    public void setDatosPersonalesDestinatario(Literal datosPersonalesDestinatario) {
        this.datosPersonalesDestinatario = datosPersonalesDestinatario;
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

    public List<ProcedimientoTramiteDTO> getTramites() {
        return tramites;
    }

    public void setTramites(List<ProcedimientoTramiteDTO> tramites) {
        this.tramites = tramites;
    }

    public List<ProcedimientoDocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<ProcedimientoDocumentoDTO> documentos) {
        this.documentos = documentos;
    }

    public List<NormativaGridDTO> getNormativas() {
        return normativas;
    }

    public void setNormativas(List<NormativaGridDTO> normativas) {
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


    public List<TipoPublicoObjetivoDTO> getPublicosObjetivo() {
        return publicosObjetivo;
    }

    public void setPublicosObjetivo(List<TipoPublicoObjetivoDTO> publicosObjetivo) {
        this.publicosObjetivo = publicosObjetivo;
    }

    public Boolean getTaxa() {
        return taxa;
    }

    public void setTaxa(Boolean taxa) {
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

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    /*
        public TypeProcedimientoEstado getEstado() {
            return estado;
        }

        public void setEstado(TypeProcedimientoEstado estado) {
            this.estado = estado;
        }
    */
    public boolean isInterno() {
        return interno;
    }

    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    public boolean isDatosPersonalesActivo() {
        return datosPersonalesActivo;
    }

    public void setDatosPersonalesActivo(boolean datosPersonalesActivo) {
        this.datosPersonalesActivo = datosPersonalesActivo;
    }

    public Long getCodigoWF() {
        return codigoWF;
    }

    public void setCodigoWF(Long codigoWF) {
        this.codigoWF = codigoWF;
    }

    /*
        public TypeProcedimientoWorfklow getWorkflow() {
            return workflow;
        }

        public void setWorkflow(TypeProcedimientoWorfklow workflow) {
            this.workflow = workflow;
        }
    */
    public UnidadAdministrativaDTO getUaResponsable() {
        return uaResponsable;
    }

    public void setUaResponsable(UnidadAdministrativaDTO uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    public UnidadAdministrativaDTO getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(UnidadAdministrativaDTO uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public List<TipoPublicoObjetivoEntidadGridDTO> getTiposPubObjEntGrid() {
        return tiposPubObjEntGrid;
    }

    public void setTiposPubObjEntGrid(List<TipoPublicoObjetivoEntidadGridDTO> tiposPubObjEntGrid) {
        this.tiposPubObjEntGrid = tiposPubObjEntGrid;
    }

    public List<TipoMateriaSIAGridDTO> getMateriasGridSIA() {
        return materiasGridSIA;
    }

    public void setMateriasGridSIA(List<TipoMateriaSIAGridDTO> materiasGridSIA) {
        this.materiasGridSIA = materiasGridSIA;
    }

    public TipoProcedimientoDTO getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimientoDTO tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public List<NormativaGridDTO> getNormativasGrid() {
        return normativasGrid;
    }

    public void setNormativasGrid(List<NormativaGridDTO> normativasGrid) {
        this.normativasGrid = normativasGrid;
    }

    public TypeProcedimientoWorfklow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(TypeProcedimientoWorfklow workflow) {
        this.workflow = workflow;
    }

    public TypeProcedimientoEstado getEstado() {
        return estado;
    }

    public void setEstado(TypeProcedimientoEstado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" +
                "id=" + codigo +
                ", responsable='" + responsable + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoDTO that = (ProcedimientoDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
