package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;

/**
 * Bulletins.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "TipoUnidadAdministrativa", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_UNIDAD)
public class TipoUnidadAdministrativa extends EntidadBase<TipoUnidadAdministrativa> {

	private static final Logger LOG = LoggerFactory.getLogger(TipoUnidadAdministrativa.class);

	/**
	 * Identificador
	 */
	@Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = true)
	private String identificador;

	/** descripcion. **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	@Schema(description = "link_entidad", required = false)
	private Link link_entidad;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long entidad;

	/**
	 * Cargo Masculino
	 */
	@Schema(description = "cargoMasculino", name = "cargoMasculino", type = SchemaType.STRING, required = false)
	private String cargoMasculino;

	/**
	 * Cargo Femenino
	 */
	@Schema(description = "cargoFemenino", name = "cargoFemenino", type = SchemaType.STRING, required = false)
	private String cargoFemenino;

	/**
	 * Tratamiento masculino
	 */
	@Schema(description = "tratamientoMasculino", name = "tratamientoMasculino", type = SchemaType.STRING, required = false)
	private String tratamientoMasculino;

	/**
	 * Tratamiento femenino
	 */
	@Schema(description = "tratamientoFemenino", name = "tratamientoFemenino", type = SchemaType.STRING, required = false)
	private String tratamientoFemenino;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	public TipoUnidadAdministrativa(TipoUnidadAdministrativaDTO nodo, String urlBase, String idioma,
			boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);
	}

	public TipoUnidadAdministrativa() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
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

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Link getLink_entidad() {
		return link_entidad;
	}

	public void setLink_entidad(Link link_entidad) {
		this.link_entidad = link_entidad;
	}

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	public String getCargoMasculino() {
		return cargoMasculino;
	}

	public void setCargoMasculino(String cargoMasculino) {
		this.cargoMasculino = cargoMasculino;
	}

	public String getCargoFemenino() {
		return cargoFemenino;
	}

	public void setCargoFemenino(String cargoFemenino) {
		this.cargoFemenino = cargoFemenino;
	}

	public String getTratamientoMasculino() {
		return tratamientoMasculino;
	}

	public void setTratamientoMasculino(String tratamientoMasculino) {
		this.tratamientoMasculino = tratamientoMasculino;
	}

	public String getTratamientoFemenino() {
		return tratamientoFemenino;
	}

	public void setTratamientoFemenino(String tratamientoFemenino) {
		this.tratamientoFemenino = tratamientoFemenino;
	}
}
