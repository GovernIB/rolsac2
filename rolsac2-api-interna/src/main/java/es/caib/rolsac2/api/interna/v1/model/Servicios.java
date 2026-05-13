package es.caib.rolsac2.api.interna.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.api.interna.v1.utils.Utiles;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Calendar;

/**
 * Serveis.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "Servicios", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_SERVICIOS)
public class Servicios extends EntidadBase {

    private static final Logger LOG = LoggerFactory.getLogger(Servicios.class);

    /**
     * codigo
     **/
    @Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
    private long codigo;

    //	@Schema(description = "codigoServicio", type = SchemaType.STRING, required = false)
    //	private String codigoServicio; // en el modelo se llama codigo

    @Schema(description = "codigoSIA", type = SchemaType.INTEGER, required = false)
    private Integer codigoSIA;

    //	@Schema(description = "correo", type = SchemaType.STRING, required = false)
    //	private String correo;

    @Schema(description = "destinatarios", type = SchemaType.STRING, required = false)
    private String destinatarios;

    @Schema(description = "estadoSIA", type = SchemaType.STRING, required = false)
    private String estadoSIA;

    @Schema(description = "fechaActualizacion", required = false)
    private Calendar fechaActualizacion;

    //	@Schema(description = "fechaDespublicacion", required = false)
    //	private Calendar fechaDespublicacion;

    @Schema(description = "fechaPublicacion", required = false)
    private Calendar fechaPublicacion;

    @Schema(description = "fechaSIA", required = false)
    private Calendar fechaSIA;


    @Schema(description = "nombre", type = SchemaType.STRING, required = false)
    private String nombre;

    @Schema(description = "objeto", type = SchemaType.STRING, required = false)
    private String objeto;

    @Schema(description = "observaciones", type = SchemaType.STRING, required = false)
    private String observaciones;

    @Schema(description = "requisitos", type = SchemaType.STRING, required = false)
    private String requisitos;

    /**
     * es comun
     **/
    @Schema(description = "comun", type = SchemaType.INTEGER, required = false)
    private Integer comun;

    /**
     * LOPD
     **/
    @Schema(description = "linkLopdInfoAdicional", required = false)
    private Link linkLopdInfoAdicional;
    @Schema(description = "lopdResponsable", type = SchemaType.STRING, required = false)
    private String lopdResponsable;
    @Schema(description = "lopdFinalidad", type = SchemaType.STRING, required = false)
    private String lopdFinalidad;
    @Schema(description = "lopdDestinatario", type = SchemaType.STRING, required = false)
    private String lopdDestinatario;
    @Schema(description = "lopdDerechos", type = SchemaType.STRING, required = false)
    private String lopdDerechos;
    @Schema(description = "lopdCabecera", type = SchemaType.STRING, required = false)
    private String lopdCabecera;
    @Schema(description = "lopdLegitimacion", required = false)
    private Legitimacion lopdLegitimacion;
    @Schema(description = "lopdComun", type = SchemaType.STRING, required = false)
    private String lopdComun;

    @Schema(description = "codigoWF", type = SchemaType.STRING, required = false)
    private Long codigoWF;

    @Schema(description = "tipo", type = SchemaType.STRING, required = false)
    private String tipo;
    @Schema(description = "workflow", type = SchemaType.STRING, required = false)
    private TypeProcedimientoWorkflow workflow;
    @Schema(description = "estado", type = SchemaType.STRING, required = false)
    private TypeProcedimientoEstado estado;
    @Schema(description = "interno", type = SchemaType.INTEGER, required = false)
    private Integer interno;
    @Schema(description = "publicado", type = SchemaType.INTEGER, required = false)
    private Integer publicado;
    @Schema(description = "fechaCaducidad", required = false)
    private Calendar fechaCaducidad;

    @Schema(description = "uaResponsable", type = SchemaType.INTEGER, required = false)
    private Long uaResponsable;

    @Schema(description = "linkUnidadAdministrativaInstructora", required = false)
    private Link linkUnidadAdministrativaInstructora;
    @Schema(description = "uaInstructor", type = SchemaType.INTEGER, required = false)
    private Long uaInstructor;
    @Schema(description = "habilitadoApoderado", type = SchemaType.INTEGER, required = false)
    private Integer habilitadoApoderado;
    @Schema(description = "habilitadoFuncionario", type = SchemaType.STRING, required = false)
    private String habilitadoFuncionario;
    @Schema(description = "tieneTasa", type = SchemaType.INTEGER, required = false)
    private Integer tieneTasa = 0;
    @Schema(description = "responsableEmail", type = SchemaType.STRING, required = false)
    private String responsableEmail;

    @Schema(description = "responsableTelefono", type = SchemaType.STRING, required = false)
    private String responsableTelefono;
    @Schema(description = "nombreProcedimientoWorkFlow", type = SchemaType.STRING, required = false)
    private String nombreProcedimientoWorkFlow;

    @Schema(description = "datosContacto", required = false)
    private DatosContacto datosContacto;

    @Schema(description = "terminoResolucion", type = SchemaType.STRING, required = false)
    private String terminoResolucion;
    @Schema(description = "tramitPresencial", type = SchemaType.INTEGER, required = false)
    private Integer tramitPresencial;
    @Schema(description = "tramitElectronica", type = SchemaType.INTEGER, required = false)
    private Integer tramitElectronica;
    @Schema(description = "tramitTelefonica", type = SchemaType.INTEGER, required = false)
    private Integer tramitTelefonica;
    @Schema(description = "activoLOPD", type = SchemaType.INTEGER, required = false)
    private Integer activoLOPD;

    @Schema(description = "link_tipoTramitacion", required = false)
    private Link link_tipoTramitacion;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long tipoTramitacion;

    @Schema(description = "link_plantillaSel", required = false)
    private Link link_plantillaSel;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long plantillaSel;

    /**
     * Constructor
     *
     * @param elem
     * @param urlBase
     * @param idioma
     * @param hateoasEnabled
     */
    public Servicios(final ServicioDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled, String idiomaPorDefecto) {
        super(elem, urlBase, idioma, hateoasEnabled);
        if (elem != null) {
            this.codigo = elem.getCodigo();
            this.comun = elem.getComun();
            this.activoLOPD = elem.isActivoLOPD() ? 1 : 0;
            this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA();
            this.codigoWF = elem.getCodigoWF();
            this.nombre = elem.getNombreProcedimientoWorkFlow() == null ? null : elem.getNombreProcedimientoWorkFlow().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.destinatarios = elem.getDestinatarios() == null ? null : elem.getDestinatarios().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.estado = elem.getEstado() == null ? null : elem.getEstado();
            this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA().toString();
            this.fechaPublicacion = elem.getFechaPublicacion() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaPublicacion());
            this.fechaActualizacion = elem.getFechaActualizacion() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaActualizacion());
            this.fechaCaducidad = elem.getFechaCaducidad() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaCaducidad());
            this.fechaSIA = elem.getFechaSIA() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaSIA());
            this.habilitadoApoderado = elem.isHabilitadoApoderado() == null ? null : (elem.isHabilitadoApoderado() ? 1 : 0);
            this.habilitadoFuncionario = elem.getHabilitadoFuncionario();
            this.interno = elem.isInterno() ? 1 : 0;
            this.lopdResponsable = elem.getLopdResponsable();
            this.nombreProcedimientoWorkFlow = elem.getNombreProcedimientoWorkFlow() == null ? null : elem.getNombreProcedimientoWorkFlow().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.objeto = elem.getObjeto() == null ? null : elem.getObjeto().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.observaciones = elem.getObservaciones() == null ? null : elem.getObservaciones().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.publicado = elem.isPublicado() ? 1 : 0;
            this.requisitos = elem.getRequisitos() == null ? null : elem.getRequisitos().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.responsableEmail = elem.getResponsableEmail();

            this.responsableTelefono = elem.getResponsableTelefono();
            this.terminoResolucion = elem.getTerminoResolucion() == null ? null : elem.getTerminoResolucion().getTraduccionConValor(idioma, idiomaPorDefecto);
            this.tieneTasa = elem.isTieneTasa() ? 1 : 0;
            this.tipo = elem.getTipo();
            this.tramitElectronica = elem.isTramitElectronica() ? 1 : 0;
            this.tramitTelefonica = elem.isTramitTelefonica() ? 1 : 0;
            this.tramitPresencial = elem.isTramitPresencial() ? 1 : 0;
            this.uaInstructor = elem.getUaInstructor() == null ? null : elem.getUaInstructor().getCodigo();
            this.uaResponsable = elem.getUaResponsable() == null ? null : elem.getUaResponsable().getCodigo();
            this.workflow = elem.getWorkflow() == null ? null : elem.getWorkflow();
            if (this.tramitElectronica != null && this.tramitElectronica == 1) {
                this.tipoTramitacion = elem.getTipoTramitacion() == null ? null : elem.getTipoTramitacion().getCodigo();
                this.plantillaSel = elem.getPlantillaSel() == null ? null : elem.getPlantillaSel().getCodigo();
            }
            // copiamos los datos que no tienen la misma estructura:
            if (elem.getDatosPersonalesLegitimacion() != null) {
                this.lopdLegitimacion = new Legitimacion(elem.getDatosPersonalesLegitimacion(), urlBase, idioma, hateoasEnabled, idiomaPorDefecto);
            }
            if (elem.getLopdDerechos() != null) {
                this.lopdDerechos = elem.getLopdDerechos().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (elem.getLopdFinalidad() != null) {
                this.lopdFinalidad = elem.getLopdFinalidad().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (elem.getLopdDestinatario() != null) {
                this.lopdDestinatario = elem.getLopdDestinatario().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (elem.getLopdCabecera() != null) {
                this.lopdCabecera = elem.getLopdCabecera().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (elem.getLopdComun() != null) {
                this.lopdComun = elem.getLopdComun().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (elem.getDocumentosLOPD() != null && !elem.getDocumentosLOPD().isEmpty()) {
                String descripcion = getDescripcion(elem.getDocumentosLOPD().get(0), idioma, idiomaPorDefecto);
                Long codigoDoc = null;
                DocumentoMultiIdioma docsLOPD = elem.getDocumentosLOPD().get(0).getDocumentos();
                if (docsLOPD != null && docsLOPD.getTraducciones() != null && !docsLOPD.getTraducciones().isEmpty()) {
                    es.caib.rolsac2.service.model.DocumentoTraduccion tradLOPD = docsLOPD.getTraducciones().stream()
                            .filter(t -> idioma.equals(t.getIdioma()))
                            .findFirst()
                            .orElse(null);
                    if (tradLOPD == null || tradLOPD.getFicheroDTO() == null || tradLOPD.getFicheroDTO().getCodigo() == null) {
                        tradLOPD = docsLOPD.getTraducciones().stream()
                                .filter(t -> idiomaPorDefecto.equals(t.getIdioma()))
                                .findFirst()
                                .orElse(null);
                    }
                    if (tradLOPD == null) {
                        tradLOPD = docsLOPD.getTraducciones().get(0);
                    }
                    if (tradLOPD != null) {
                        codigoDoc = tradLOPD.getFicheroDTO().getCodigo();
                    }
                }
                if (codigoDoc != null) {
                    linkLopdInfoAdicional = this.generaLinkArchivo(codigoDoc, urlBase, descripcion);
                }
            }
            if (elem.getUaInstructor() != null) {
                linkUnidadAdministrativaInstructora = this.generaLink(elem.getUaInstructor().getCodigo(), Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase, getDescripcionUA(elem.getUaInstructor(), idioma, idiomaPorDefecto));
            }

            this.datosContacto = new DatosContacto();
            if (elem.getUaResponsableLiteral() != null) {
                String servicioResponsable = elem.getUaResponsableLiteral().getTraduccionConValor(idioma, idiomaPorDefecto);
                if (servicioResponsable == null) {
                    servicioResponsable = elem.getUaResponsableLiteral().getTraduccion();
                }
                this.datosContacto.setServicioResponsable(servicioResponsable);
            }
            this.datosContacto.setPersonaResponsable(elem.getResponsable());
            this.datosContacto.setEmailIncidencias(elem.getIncidenciasEmail());

            this.hateoasEnabled = hateoasEnabled;

            generaLinks(urlBase);
        }
    }

    /**
     * Obtiene el nombre de la UA, primero el idioma y luego el idiomapordefecto
     *
     * @param ua               unidad administrativa
     * @param idioma           idioma
     * @param idiomaPorDefecto idioma por defecto
     * @return nombre
     */
    private String getDescripcionUA(UnidadAdministrativaDTO ua, String idioma, String idiomaPorDefecto) {
        String descripcion = null;
        if (ua.getNombre() != null) {
            descripcion = ua.getNombre().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (ua.getNombre() != null && descripcion == null) {
            descripcion = ua.getNombre().getTraduccion();
        }
        return descripcion;
    }

    private String getDescripcion(ProcedimientoDocumentoDTO documentoLOPD, String idioma, String idiomaPorDefecto) {
        String descripcion = null;
        if (documentoLOPD.getDescripcion() != null) {
            descripcion = documentoLOPD.getDescripcion().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (documentoLOPD.getDescripcion() != null && descripcion == null) {
            descripcion = documentoLOPD.getDescripcion().getTraduccion();
        }
        return descripcion;
    }


    public Servicios() {
        super();
    }

    public Servicios(ServicioGridDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
        if (elem != null) {
            this.codigo = elem.getCodigo();
            this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA();
            this.estado = TypeProcedimientoEstado.valueOf(elem.getEstado());
            this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA().toString();
            this.tipo = elem.getTipo();
            this.nombre = elem.getNombre();
            this.codigoWF = elem.getCodigoWFPub();
        }
    }

    @Override
    public void generaLinks(String urlBase) {
        link_tipoTramitacion = this.generaLink(this.tipoTramitacion, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase, null);
        link_plantillaSel = this.generaLink(this.plantillaSel, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase, null);
    }

    @Override
    protected void addSetersInvalidos() {
        if (!SETTERS_INVALIDS.contains("setCodigo")) {
            SETTERS_INVALIDS.add("setCodigo");
        }

        if (!SETTERS_INVALIDS.contains("setPlataforma")) {
            SETTERS_INVALIDS.add("setPlataforma");
        }

    }

    @Override
    public void setId(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigoSIA
     */
    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * @param codigoSIA the codigoSIA to set
     */
    public void setCodigoSIA(final Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    /**
     * @return the destinatarios
     */
    public String getDestinatarios() {
        return destinatarios;
    }

    /**
     * @param destinatarios the destinatarios to set
     */
    public void setDestinatarios(final String destinatarios) {
        this.destinatarios = destinatarios;
    }

    /**
     * @return the estadoSIA
     */
    public String getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * @param estadoSIA the estadoSIA to set
     */
    public void setEstadoSIA(final String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    /**
     * @return the fechaActualizacion
     */
    public Calendar getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(final Calendar fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the fechaPublicacion
     */
    public Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion the fechaPublicacion to set
     */
    public void setFechaPublicacion(final Calendar fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the fechaSIA
     */
    public Calendar getFechaSIA() {
        return fechaSIA;
    }

    /**
     * @param fechaSIA the fechaSIA to set
     */
    public void setFechaSIA(final Calendar fechaSIA) {
        this.fechaSIA = fechaSIA;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    //	/**
    //	 * @return the nombreResponsable
    //	 */
    //	public java.lang.String getNombreResponsable() {
    //		return nombreResponsable;
    //	}
    //
    //	/**
    //	 * @param nombreResponsable the nombreResponsable to set
    //	 */
    //	public void setNombreResponsable(final java.lang.String nombreResponsable) {
    //		this.nombreResponsable = nombreResponsable;
    //	}

    /**
     * @return the objeto
     */
    public String getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(final String objeto) {
        this.objeto = objeto;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(final String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the requisitos
     */
    public String getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos the requisitos to set
     */
    public void setRequisitos(final String requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * @return the comun
     */
    public Integer getComun() {
        return comun;
    }

    /**
     * @param comun the comun to set
     */
    public void setComun(final Integer comun) {
        this.comun = comun;
    }

    /**
     * @return the lopdResponsable
     */
    public String getLopdResponsable() {
        return lopdResponsable;
    }

    /**
     * @param lopdResponsable the lopdResponsable to set
     */
    public void setLopdResponsable(final String lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    /**
     * @return the lopdFinalidad
     */
    public String getLopdFinalidad() {
        return lopdFinalidad;
    }

    /**
     * @param lopdFinalidad the lopdFinalidad to set
     */
    public void setLopdFinalidad(final String lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    /**
     * @return the lopdDestinatario
     */
    public String getLopdDestinatario() {
        return lopdDestinatario;
    }

    /**
     * @param lopdDestinatario the lopdDestinatario to set
     */
    public void setLopdDestinatario(final String lopdDestinatario) {
        this.lopdDestinatario = lopdDestinatario;
    }

    /**
     * @return the lopdDerechos
     */
    public String getLopdDerechos() {
        return lopdDerechos;
    }

    /**
     * @param lopdDerechos the lopdDerechos to set
     */
    public void setLopdDerechos(final String lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    /**
     * @return the lopdCabecera
     */
    public String getLopdCabecera() {
        return lopdCabecera;
    }

    /**
     * @param lopdCabecera the lopdCabecera to set
     */
    public void setLopdCabecera(String lopdCabecera) {
        this.lopdCabecera = lopdCabecera;
    }

    /**
     * @return the lopdComun
     */
    public String getLopdComun() {
        return lopdComun;
    }

    /**
     * @param lopdComun the lopdComun to set
     */
    public void setLopdComun(String lopdComun) {
        this.lopdComun = lopdComun;
    }

    /**
     * @return the codigoWF
     */
    public Long getCodigoWF() {
        return codigoWF;
    }

    /**
     * @param codigoWF the codigoWF to set
     */
    public void setCodigoWF(Long codigoWF) {
        this.codigoWF = codigoWF;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return get the workflow
     */
    public TypeProcedimientoWorkflow getWorkflow() {
        return workflow;
    }

    /**
     * @param workflow the workflow to set
     */
    public void setWorkflow(TypeProcedimientoWorkflow workflow) {
        this.workflow = workflow;
    }

    /**
     * @return get the estado
     */
    public TypeProcedimientoEstado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(TypeProcedimientoEstado estado) {
        this.estado = estado;
    }

    /**
     * @return get the interno
     */
    public Integer getInterno() {
        return interno;
    }

    /**
     * @param interno the interno to set
     */
    public void setInterno(Integer interno) {
        this.interno = interno;
    }

    /**
     * @return get the publicado
     */
    public Integer getPublicado() {
        return publicado;
    }

    /**
     * @param publicado the publicado to set
     */
    public void setPublicado(Integer publicado) {
        this.publicado = publicado;
    }

    /**
     * @return get the fechaCaducidad
     */
    public Calendar getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * @param fechaCaducidad the fechaCaducidad to set
     */
    public void setFechaCaducidad(Calendar fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * @return the lopdLegitimacion
     */
    public Legitimacion getLopdLegitimacion() {
        return lopdLegitimacion;
    }

    /**
     * @param lopdLegitimacion the lopdLegitimacion to set
     */
    public void setLopdLegitimacion(Legitimacion lopdLegitimacion) {
        this.lopdLegitimacion = lopdLegitimacion;
    }

    /**
     * @return the linkLopdInfoAdicional
     */
    public Link getLinkLopdInfoAdicional() {
        return linkLopdInfoAdicional;
    }

    public void setLinkLopdInfoAdicional(Link linkLopdInfoAdicional) {
        this.linkLopdInfoAdicional = linkLopdInfoAdicional;
    }

    public Long getUaResponsable() {
        return uaResponsable;
    }

    public void setUaResponsable(Long uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    public Long getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(Long uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public void setHabilitadoApoderado(Integer habilitadoApoderado) {
        this.habilitadoApoderado = habilitadoApoderado;
    }

    public String getHabilitadoFuncionario() {
        return habilitadoFuncionario;
    }

    public void setHabilitadoFuncionario(String habilitadoFuncionario) {
        this.habilitadoFuncionario = habilitadoFuncionario;
    }

    public Integer getTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(Integer tieneTasa) {
        this.tieneTasa = tieneTasa;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }


    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    public void setResponsableTelefono(String responsableTelefono) {
        this.responsableTelefono = responsableTelefono;
    }

    public String getNombreProcedimientoWorkFlow() {
        return nombreProcedimientoWorkFlow;
    }

    public void setNombreProcedimientoWorkFlow(String nombreProcedimientoWorkFlow) {
        this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public String getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(String terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    public Integer getTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(Integer tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public Integer getTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(Integer tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public Integer getTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(Integer tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public Integer getActivoLOPD() {
        return activoLOPD;
    }

    public void setActivoLOPD(Integer activoLOPD) {
        this.activoLOPD = activoLOPD;
    }

    public Link getLink_tipoTramitacion() {
        return link_tipoTramitacion;
    }

    public void setLink_tipoTramitacion(Link link_tipoTramitacion) {
        this.link_tipoTramitacion = link_tipoTramitacion;
    }

    public Long getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(Long tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    public Link getLink_plantillaSel() {
        return link_plantillaSel;
    }

    public void setLink_plantillaSel(Link link_plantillaSel) {
        this.link_plantillaSel = link_plantillaSel;
    }

    public Long getPlantillaSel() {
        return plantillaSel;
    }

    public void setPlantillaSel(Long plantillaSel) {
        this.plantillaSel = plantillaSel;
    }


    public Link getLinkUnidadAdministrativaInstructora() {
        return linkUnidadAdministrativaInstructora;
    }

    public void setLinkUnidadAdministrativaInstructora(Link linkUnidadAdministrativaInstructora) {
        this.linkUnidadAdministrativaInstructora = linkUnidadAdministrativaInstructora;
    }

    public Integer getHabilitadoApoderado() {
        return habilitadoApoderado;
    }
}