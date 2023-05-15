package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.api.externa.v1.utils.Utiles;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.ServicioGridDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;

/**
 * Serveis.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "Inicio", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_SERVICIOS)
public class Inicio extends EntidadBase<Inicio> {

	private static final Logger LOG = LoggerFactory.getLogger(Inicio.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private long codigo;

	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
	private String identificador;

	@Schema(description = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	public Inicio() {
		super();
	}

	public Inicio(TipoFormaInicioDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
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

	public String getSilencio() {
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

}
