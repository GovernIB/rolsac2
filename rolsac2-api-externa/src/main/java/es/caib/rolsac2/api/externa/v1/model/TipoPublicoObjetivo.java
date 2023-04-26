package es.caib.rolsac2.api.externa.v1.model;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TipoPublicoObjetivo.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "TipoPublicoObjetivo", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_PUBLICO)
public class TipoPublicoObjetivo extends EntidadBase<TipoPublicoObjetivo> {

	private static final Logger LOG = LoggerFactory.getLogger(TipoPublicoObjetivo.class);

	/**
	 * Identificador
	 */
	@Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = true)
	private String identificador;

	/** descripcion. **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	/**
	 * empleadoPublico
	 */
	@Schema(description = "empleadoPublico", name = "empleadoPublico", type = SchemaType.BOOLEAN, required = false)
	private Boolean empleadoPublico;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	public TipoPublicoObjetivo(TipoPublicoObjetivoDTO nodo, String urlBase, String idioma,
			boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);
	}

	public TipoPublicoObjetivo() {
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
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param enlace the enlace to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	/**
	 * Obtiene identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Establece identificador.
	 *
	 * @param identificador identificador
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Boolean getEmpleadoPublico() {
		return empleadoPublico;
	}

	public void setEmpleadoPublico(Boolean empleadoPublico) {
		this.empleadoPublico = empleadoPublico;
	}
}
