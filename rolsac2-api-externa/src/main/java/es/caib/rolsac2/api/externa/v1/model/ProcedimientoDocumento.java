package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;

/**
 * ProcedimientoDocumento.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "ProcedimientoDocumento", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO)
public class ProcedimientoDocumento extends EntidadBase<ProcedimientoDocumento> {

	private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoDocumento.class);

    /**
     * Orden
     */
    private Integer orden;

//    /**
//     * Código temporal para poder tratar con el dato
//     **/
//    @Schema(description = "codigoString", name = "codigoString", type = SchemaType.STRING, required = false)
//    private String codigoString;

    /*Título del documento*/
    @Schema(description = "titulo", name = "titulo", type = SchemaType.STRING, required = false)
    private String titulo;

//    /*URL del documento*/
//	@Schema(description = "url", name = "url", type = SchemaType.STRING, required = false)
//    private String url;

    /*Descripción del documento*/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/** Fichero asociado al documento **/
	@Schema(description = "link_fichero", required = false)
	private Link link_fichero;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long fichero;

//	@Schema(description = "link_procedimientoDTO", required = false)
//	private Link link_procedimientoDTO;
//	@JsonIgnore
//	@Schema(hidden = true)
//	@XmlTransient
//	private Long procedimientoDTO;

	public ProcedimientoDocumento(ProcedimientoDocumentoDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
		super( elem, urlBase, idioma, hateoasEnabled);

		if(elem.getDocumentos() != null) {
			Long fichero = elem.getDocumentos().getDocumentoTraduccion(idioma) == null ? null : elem.getDocumentos().getDocumentoTraduccion(idioma).getFichero();

			if(fichero == null) {
				FicheroDTO ficheroDTO = elem.getDocumentos().getDocumentoTraduccion(idioma) == null ? null : elem.getDocumentos().getDocumentoTraduccion(idioma).getFicheroDTO();

				if(ficheroDTO != null) {
					fichero = ficheroDTO.getCodigo();
				}
			}

			this.fichero = fichero;
		}

		generaLinks(urlBase);
	}

	public ProcedimientoDocumento() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
//		link_procedimientoDTO = this.generaLink(this.procedimientoDTO, Constantes.ENTIDAD_PROCEDIMIENTO, Constantes.URL_PROCEDIMIENTO,
//				urlBase, null);
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

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

//	public String getCodigoString() {
//		return codigoString;
//	}
//
//	public void setCodigoString(String codigoString) {
//		this.codigoString = codigoString;
//	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

//	public String getUrl() {
//		return url;
//	}

//	public void setUrl(String url) {
//		this.url = url;
//	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

//	public Link getLink_procedimientoDTO() {
//		return link_procedimientoDTO;
//	}
//
//	public void setLink_procedimientoDTO(Link link_procedimientoDTO) {
//		this.link_procedimientoDTO = link_procedimientoDTO;
//	}
//
//	public Long getProcedimientoDTO() {
//		return procedimientoDTO;
//	}
//
//	public void setProcedimientoDTO(Long procedimientoDTO) {
//		this.procedimientoDTO = procedimientoDTO;
//	}

	public Long getCodigo() {
		return codigo;
	}

}
