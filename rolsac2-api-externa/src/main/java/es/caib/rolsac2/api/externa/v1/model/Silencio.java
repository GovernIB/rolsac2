package es.caib.rolsac2.api.externa.v1.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;

/**
 * Serveis.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "Silencio", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_SERVICIOS)
public class Silencio extends EntidadBase<Silencio> {

	private static final Logger LOG = LoggerFactory.getLogger(Silencio.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private long codigo;

	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
	private String identificador;

	@Schema(description = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	@Schema(description = "descripcion2", type = SchemaType.STRING, required = false)
	private String descripcion2;

	@Schema(description = "fechaBorrar", required = false)
	private Calendar fechaBorrar;


	public Silencio() {
		super();
	}

	public Silencio(TipoSilencioAdministrativoDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
		super(elem, urlBase, idioma, hateoasEnabled);
	}

	@Override
	public void generaLinks(final String urlBase) {
//		linkServicioResponsable = this.generaLink(this.servicioResponsable, Constantes.ENTIDAD_UA, Constantes.URL_UA,
//				urlBase, null);
//		linkOrganoInstructor = this.generaLink(this.organoInstructor, Constantes.ENTIDAD_UA, Constantes.URL_UA,
//				urlBase, null);
//		linkLopdInfoAdicional = this.generaLinkArchivo(this.lopdInfoAdicional, urlBase, null);
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion2() {
		return descripcion2;
	}

	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	public Calendar getFechaBorrar() {
		return fechaBorrar;
	}

	public void setFechaBorrar(Calendar fechaBorrar) {
		this.fechaBorrar = fechaBorrar;
	}

}
