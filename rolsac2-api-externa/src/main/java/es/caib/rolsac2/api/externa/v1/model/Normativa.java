package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.NormativaGridDTO;

/**
 * Normatives.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "Normativa", description = Constantes.TXT_DEFINICION_CLASE +  Constantes.ENTIDAD_NORMATIVAS)
public class Normativa extends EntidadBase {

	private static final Logger LOG = LoggerFactory.getLogger(Normativa.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private long codigo;

	/** fecha **/
	@Schema(description = "fechaAprobacion", required = false)
    private Calendar fechaAprobacion;//

	/** numero **/
	@Schema(description = "numero", type = SchemaType.INTEGER, required = false)
    private Long numero;

	/** fechaBoletin **/
	@Schema(description = "fechaBoletin", required = false)
    private Calendar fechaBoletin;//

	/** numeroBoletin **/
	@Schema(description = "numeroBoletin", type = SchemaType.STRING, required = false)
    private String numeroBoletin;

	/** urlBoletin **/
	@Schema(description = "urlBoletin", type = SchemaType.STRING, required = false)
    private String urlBoletin;//

	/** nombre **/
	@Schema(description = "nombre", type = SchemaType.STRING, required = false)
    private String nombre;//

	/** responsable **/
	@Schema(description = "nombreResponsable", type = SchemaType.STRING, required = false)
    private String nombreResponsable;//

	/** descripcion **/
	@Schema(description = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;//

	@Schema(description = "tipoNormativa", type = SchemaType.INTEGER, required = false)
	private Long tipoNormativa;

	//-- LINKS--//
	//-- se duplican las entidades para poder generar la clase link en funcion de la propiedad principal (sin "link_")
	/** boletin **/
	@Schema(description = "linkTipoBoletin", required = false)
	private Link linkTipoBoletin;
	@Schema(hidden = true)
	@XmlTransient
	private Long boletinOficial;

	@Schema(description = "linkEntidad", required = false)
	private Link linkEntidad;
	@Schema(hidden = true)
	@XmlTransient
	private Long entidad;

	public Normativa (NormativaGridDTO elem, String urlBase,String idioma,boolean hateoasEnabled ) {
		super( elem, urlBase, idioma, hateoasEnabled);
	}

	public Normativa (NormativaDTO elem, String urlBase,String idioma,boolean hateoasEnabled ) {
		super( elem, urlBase, idioma, hateoasEnabled);
	}

	public Normativa() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		linkTipoBoletin = this.generaLink(this.boletinOficial,Constantes.ENTIDAD_BOLETINES,Constantes.URL_BOLETINES, urlBase , null );
		linkEntidad = this.generaLink(this.entidad,Constantes.ENTIDAD_ENTIDADES,Constantes.URL_ENTIDADES, urlBase , null );
	}


	public String getUrlBoletin() {
		return urlBoletin;
	}

	public void setUrlBoletin(String urlBoletin) {
		this.urlBoletin = urlBoletin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static Logger getLog() {
		return LOG;
	}

	public static Normativa valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<Normativa> typeRef = new TypeReference<Normativa>() {
		};
		Normativa obj;
		try {
			obj = (Normativa) objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return obj;
	}


	public String toJson() {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			return objectMapper.writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}


	@Override
	protected void addSetersInvalidos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(Long codigo) {
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
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the fechaBoletin
	 */
	public java.util.Calendar getFechaBoletin() {
		return fechaBoletin;
	}

	/**
	 * @param fechaBoletin the fechaBoletin to set
	 */
	public void setFechaBoletin(java.util.Calendar fechaBoletin) {
		this.fechaBoletin = fechaBoletin;
	}

	/**
	 * @return the numero
	 */
	public java.lang.Long getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(java.lang.Long numero) {
		this.numero = numero;
	}

	public Calendar getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Calendar fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public String getNumeroBoletin() {
		return numeroBoletin;
	}

	public void setNumeroBoletin(String numeroBoletin) {
		this.numeroBoletin = numeroBoletin;
	}

	public String getNombreResponsable() {
		return nombreResponsable;
	}

	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getTipoNormativa() {
		return tipoNormativa;
	}

	public void setTipoNormativa(Long tipoNormativa) {
		this.tipoNormativa = tipoNormativa;
	}

	public Link getLinkTipoBoletin() {
		return linkTipoBoletin;
	}

	public void setLinkTipoBoletin(Link linkTipoBoletin) {
		this.linkTipoBoletin = linkTipoBoletin;
	}

	@XmlTransient
	public Long getBoletinOficial() {
		return boletinOficial;
	}

	public void setBoletinOficial(Long boletinOficial) {
		this.boletinOficial = boletinOficial;
	}

	public Link getLinkEntidad() {
		return linkEntidad;
	}

	public void setLinkEntidad(Link linkEntidad) {
		this.linkEntidad = linkEntidad;
	}

	@XmlTransient
	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}


}
