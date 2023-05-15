package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.DocumentoNormativaDTO;
import es.caib.rolsac2.service.model.FicheroDTO;

/**
 * DocumentoNormativa.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "DocumentoNormativa", description = Constantes.TXT_DEFINICION_CLASE
		+ Constantes.ENTIDAD_DOCUMENTO_NORMATIVA)
public class DocumentoNormativa extends EntidadBase<DocumentoNormativa> {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentoNormativa.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

//	/** Código que se utiliza para seleccionar los docs en la tabla de normativa **/
//	@Schema(description = "codigoTabla", name = "codigoTabla", type = SchemaType.STRING, required = false)
//	private String codigoTabla;

	/** Título del documento **/
	@Schema(description = "titulo", name = "titulo", type = SchemaType.STRING, required = false)
	private String titulo;

	/** URL del documento **/
	@Schema(description = "url", name = "url", type = SchemaType.STRING, required = false)
	private String url;//

	/** Descripción del documento **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/** Normativa asociada al documento **/
	@Schema(description = "link_normativa", required = false)
	private Link link_normativa;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long normativa;

	@Schema(description = "link_fichero", required = false)
	private Link link_fichero;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long fichero;

	public DocumentoNormativa(DocumentoNormativaDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
		super(elem, urlBase, idioma, hateoasEnabled);

		FicheroDTO fichero = null;
		if (elem.getDocumentos() != null) {
			fichero = elem.getDocumentos().getDocumentoTraduccion(idioma) == null ? null
					: elem.getDocumentos().getDocumentoTraduccion(idioma).getFicheroDTO();
			this.fichero = fichero == null ? null : fichero.getCodigo();
		}

		generaLinks(urlBase);
	}

	public DocumentoNormativa() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		link_normativa = this.generaLink(this.normativa, Constantes.ENTIDAD_NORMATIVAS, Constantes.URL_NORMATIVAS,
					urlBase, null);
		link_fichero = this.generaLink(this.fichero, Constantes.ENTIDAD_FICHERO, Constantes.URL_FICHERO, urlBase,
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

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

//	public String getCodigoTabla() {
//		return codigoTabla;
//	}
//
//	public void setCodigoTabla(String codigoTabla) {
//		this.codigoTabla = codigoTabla;
//	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Link getLink_normativa() {
		return link_normativa;
	}

	public void setLink_normativa(Link link_normativa) {
		this.link_normativa = link_normativa;
	}

	public Long getNormativa() {
		return normativa;
	}

	public void setNormativa(Long normativa) {
		this.normativa = normativa;
	}

	public Link getLink_fichero() {
		return link_fichero;
	}

	public void setLink_fichero(Link link_fichero) {
		this.link_fichero = link_fichero;
	}

	public Long getFichero() {
		return fichero;
	}

	public void setFichero(Long fichero) {
		this.fichero = fichero;
	}

}
