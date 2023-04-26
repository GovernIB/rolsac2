package es.caib.rolsac2.api.externa.v1.model;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.FicheroDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Base64;

/**
 * Fichero.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "Fichero", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_FICHERO)
public class Fichero extends EntidadBase<Fichero> {

	private static final Logger LOG = LoggerFactory.getLogger(Fichero.class);

	/**
	 * referencia
	 */
	@Schema(description = "referencia", name = "referencia", type = SchemaType.STRING, required = true)
	private String referencia;

	/** filename. **/
	@Schema(description = "filename", name = "filename", type = SchemaType.STRING, required = false)
    private String filename;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

    /**
     * tipo
     */
	@Schema(description = "tipo", name = "tipo", type = SchemaType.STRING, required = false)
    private String tipo;

    /**
     * Contenido
     */
	@Schema(description = "contenido", name = "contenido", type = SchemaType.STRING, required = false)
    private String contenido;

	public Fichero(FicheroDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);

		if(nodo.getTipo() != null) {
			tipo = nodo.getTipo().getTipo();
		}

		if(nodo.getContenido() != null) {
			contenido = Base64.getEncoder().encodeToString(nodo.getContenido());
		}

	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Fichero() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
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
	 * @return the descripcion
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param enlace the enlace to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
