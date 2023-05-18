package es.caib.rolsac2.api.externa.v1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;

/**
 * ProcedimientoTramite.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "ProcedimientoTramite", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_PROCEDIMIENTO_TRAMITE)
public class ProcedimientoTramite extends EntidadBase<ProcedimientoTramite> {

	private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoTramite.class);

    @Schema(description = "fase", name = "fase", type = SchemaType.INTEGER, required = false)
    private Integer fase;

    @Schema(description = "tasaAsociada", name = "tasaAsociada", type = SchemaType.BOOLEAN, required = false)
    private Boolean tasaAsociada;

    @Schema(description = "requisitos", name = "requisitos", type = SchemaType.STRING, required = false)
    private String requisitos;

    @Schema(description = "nombre", name = "nombre", type = SchemaType.STRING, required = false)
    private String nombre;

    @Schema(description = "documentacion", name = "documentacion", type = SchemaType.STRING, required = false)
    private String documentacion;

    @Schema(description = "observacion", name = "observacion", type = SchemaType.STRING, required = false)
    private String observacion;

    @Schema(description = "terminoMaximo", name = "terminoMaximo", type = SchemaType.STRING, required = false)
    private String terminoMaximo;

    @Schema(description = "fechaPublicacion", name = "fechaPublicacion", type = SchemaType.STRING, required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaPublicacion;

    @Schema(description = "fechaInicio", name = "fechaInicio", type = SchemaType.STRING, required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaInicio;

    @Schema(description = "fechaCierre", name = "fechaCierre", type = SchemaType.STRING, required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaCierre;

    /**
     * Tipos de presentacion: telematica, presencial o telefonica.
     **/
    @Schema(description = "tramitPresencial", name = "tramitPresencial", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitPresencial;

    @Schema(description = "tramitElectronica", name = "tramitElectronica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitElectronica;

    @Schema(description = "tramitTelefonica", name = "tramitTelefonica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitTelefonica;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	@Schema(description = "link_unidadAdministrativa", required = false)
	private Link link_unidadAdministrativa;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long unidadAdministrativa;

	@Schema(description = "link_procedimiento", required = false)
	private Link link_procedimiento;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long procedimiento;

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

	@Schema(description = "link_listaDocumentos", required = false)
	private List<Link> link_listaDocumentos;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
    private List<Long> listaDocumentos;

	@Schema(description = "link_listaModelos", required = false)
	private List<Link> link_listaModelos;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
    private List<Long> listaModelos;

	public ProcedimientoTramite(ProcedimientoTramiteDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
		super( elem, urlBase, idioma, hateoasEnabled);

		ProcedimientoDTO procedimientoDTO = null;
		if(elem.getProcedimiento() != null) {
			procedimientoDTO = elem.getProcedimiento().getProcedimiento();
			procedimiento = procedimientoDTO == null ? null : procedimientoDTO.getCodigo();
		}

		if(elem.getListaDocumentos() != null && !elem.getListaDocumentos().isEmpty()) {
			listaDocumentos = new ArrayList<Long>();

			for(ProcedimientoDocumentoDTO documento : elem.getListaDocumentos()) {
				listaDocumentos.add(documento.getCodigo());
			}
		}

		if(elem.getListaModelos() != null && !elem.getListaModelos().isEmpty()) {
			listaModelos = new ArrayList<Long>();

			for(ProcedimientoDocumentoDTO documento : elem.getListaModelos()) {
				listaModelos.add(documento.getCodigo());
			}
		}

		generaLinks(urlBase);
	}

	public ProcedimientoTramite() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		link_procedimiento = this.generaLink(this.procedimiento, Constantes.ENTIDAD_PROCEDIMIENTO, Constantes.URL_PROCEDIMIENTO,
				urlBase, null);
		link_unidadAdministrativa = this.generaLink(this.unidadAdministrativa, Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase,
				null);
		link_tipoTramitacion = this.generaLink(this.tipoTramitacion, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase,
				null);
		link_plantillaSel = this.generaLink(this.plantillaSel, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase,
				null);
		link_listaModelos = this.generaLink(this.listaModelos, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase,
				null);
		link_listaDocumentos = this.generaLink(this.listaDocumentos, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase,
				null);
	}

	@Override
	protected void addSetersInvalidos() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setId(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public Integer getFase() {
		return fase;
	}

	public void setFase(Integer fase) {
		this.fase = fase;
	}

	public Boolean getTasaAsociada() {
		return tasaAsociada;
	}

	public void setTasaAsociada(Boolean tasaAsociada) {
		this.tasaAsociada = tasaAsociada;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTerminoMaximo() {
		return terminoMaximo;
	}

	public void setTerminoMaximo(String terminoMaximo) {
		this.terminoMaximo = terminoMaximo;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public boolean isTramitPresencial() {
		return tramitPresencial;
	}

	public void setTramitPresencial(boolean tramitPresencial) {
		this.tramitPresencial = tramitPresencial;
	}

	public boolean isTramitElectronica() {
		return tramitElectronica;
	}

	public void setTramitElectronica(boolean tramitElectronica) {
		this.tramitElectronica = tramitElectronica;
	}

	public boolean isTramitTelefonica() {
		return tramitTelefonica;
	}

	public void setTramitTelefonica(boolean tramitTelefonica) {
		this.tramitTelefonica = tramitTelefonica;
	}

	public Link getLink_unidadAdministrativa() {
		return link_unidadAdministrativa;
	}

	public void setLink_unidadAdministrativa(Link link_unidadAdministrativa) {
		this.link_unidadAdministrativa = link_unidadAdministrativa;
	}

	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public Link getLink_procedimiento() {
		return link_procedimiento;
	}

	public void setLink_procedimiento(Link link_procedimiento) {
		this.link_procedimiento = link_procedimiento;
	}

	public Long getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(Long procedimiento) {
		this.procedimiento = procedimiento;
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

	public List<Link> getLink_listaDocumentos() {
		return link_listaDocumentos;
	}

	public void setLink_listaDocumentos(List<Link> link_listaDocumentos) {
		this.link_listaDocumentos = link_listaDocumentos;
	}

	public List<Long> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Long> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<Link> getLink_listaModelos() {
		return link_listaModelos;
	}

	public void setLink_listaModelos(List<Link> link_listaModelos) {
		this.link_listaModelos = link_listaModelos;
	}

	public List<Long> getListaModelos() {
		return listaModelos;
	}

	public void setListaModelos(List<Long> listaModelos) {
		this.listaModelos = listaModelos;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


}
