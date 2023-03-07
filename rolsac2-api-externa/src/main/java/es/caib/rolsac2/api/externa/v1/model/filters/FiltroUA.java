package es.caib.rolsac2.api.externa.v1.model.filters;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

/**
 * FiltroUA.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroUA", description = "Filtro propio de la entidad Unidad Administrativa")
public class FiltroUA extends FiltroPaginacion {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroUA.class);

	public static final String CAMPO_ORD_UA_ORDEN = "orden";

	public static final String SAMPLE =    Constantes.SALTO_LINEA +
			"{\"codigoUAPadre\":\"0\","	 + Constantes.SALTO_LINEA +
			"\"validacion\":\"0\","		 + Constantes.SALTO_LINEA +
			"\"codigoSeccion\":\"0\","	 + Constantes.SALTO_LINEA +
			"\"codigoNormativa\":\"0\"," + Constantes.SALTO_LINEA +
			"\"nombre\":\"0\"," + Constantes.SALTO_LINEA +
			"\"identificador\":\"0\"," + Constantes.SALTO_LINEA +
			"\"codEnti\":\"0\"," + Constantes.SALTO_LINEA +
			"\"texto\":\"0\"," + Constantes.SALTO_LINEA +
			"\"page\":\"1\",\"size\":\"30\","		+ Constantes.SALTO_LINEA +
			"\"listaOrden\":[{\"campo\":\"" + CAMPO_ORD_UA_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}]" +
			"}" + Constantes.SALTO_LINEA +
			"Usar codigoUAPadre = -1 para recuperar las UA que no tienen padre";

	public static final String SAMPLE_JSON =
			"{\"codigoUAPadre\":\"0\","	 +
			"\"validacion\":\"0\","		 +
			"\"codigoSeccion\":\"0\","	 +
			"\"codigoNormativa\":\"0\"," +
			"\"nombre\":\"0\"," +
			"\"identificador\":\"0\"," +
			"\"codEnti\":\"0\"," +
			"\"texto\":\"0\","	+
			"\"page\":\"1\",\"size\":\"30\","		+
			"\"listaOrden\":[{\"campo\":\"" + CAMPO_ORD_UA_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}]" +
			"}";

	/** CodigoUAPadre. **/
	@Schema(description = "Código Unidad Administrativa padre ", type = SchemaType.INTEGER, required = false)
	private Integer codigoUAPadre;

	/** Validacion. **/
	@Schema(description = "Validacion", type = SchemaType.INTEGER, required = false)
	private Integer validacion;

	/** CodigoSeccion. **/
	@Schema(description = "Código de sección", type = SchemaType.INTEGER, required = false)
	private Integer codigoSeccion;

	/** CodigoNormativa. **/
	@Schema(description = "Código de Normativa", type = SchemaType.INTEGER, required = false)
	private Integer codigoNormativa;

	/** texto. **/
	@Schema(description = "Texto", type = SchemaType.STRING, required = false)
    private String texto;

	/** nombre. **/
	@Schema(description = "nombre", type = SchemaType.STRING, required = false)
    private String nombre;

	/** identificador. **/
	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
    private String identificador;

	/** codEnti. **/
	@Schema(description = "codEnti", type = SchemaType.INTEGER, required = false)
    private Long codEnti;

	public static FiltroUA valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<FiltroUA> typeRef = new TypeReference<FiltroUA>() {
		};
		FiltroUA obj;
		try {
			obj = (FiltroUA) objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return obj;
	}


	public FiltroGenerico toFiltroGenerico() {
		FiltroGenerico fg = new FiltroGenerico();
		if(this.codigoUAPadre!= null) {
			fg.addFiltro(FiltroGenerico.FILTRO_UA_CODIGO_UA_PADRE, this.codigoUAPadre+"");
		}

		if(this.codigoNormativa!= null) {
			fg.addFiltro(FiltroGenerico.FILTRO_UA_CODIGO_NORMATIVA, this.codigoNormativa+"");
		}

		if(this.codigoSeccion!= null) {
			fg.addFiltro(FiltroGenerico.FILTRO_UA_CODIGO_SECCION, this.codigoSeccion+"");
		}

		if(this.validacion!=null) {
			fg.addFiltro(FiltroGenerico.FILTRO_UA_VALIDACION, this.validacion+"");
		}

		return fg;
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


	/**
	 * @return the codigoUAPadre
	 */
	public Integer getCodigoUAPadre() {
		return codigoUAPadre;
	}


	/**
	 * @param codigoUAPadre the codigoUAPadre to set
	 */
	public void setCodigoUAPadre(Integer codigoUAPadre) {
		this.codigoUAPadre = codigoUAPadre;
	}


	/**
	 * @return the validacion
	 */
	public Integer getValidacion() {
		return validacion;
	}


	/**
	 * @param validacion the validacion to set
	 */
	public void setValidacion(Integer validacion) {
		this.validacion = validacion;
	}


	/**
	 * @return the codigoSeccion
	 */
	public Integer getCodigoSeccion() {
		return codigoSeccion;
	}


	/**
	 * @param codigoSeccion the codigoSeccion to set
	 */
	public void setCodigoSeccion(Integer codigoSeccion) {
		this.codigoSeccion = codigoSeccion;
	}


	/**
	 * @return the codigoNormativa
	 */
	public Integer getCodigoNormativa() {
		return codigoNormativa;
	}


	/**
	 * @param codigoNormativa the codigoNormativa to set
	 */
	public void setCodigoNormativa(Integer codigoNormativa) {
		this.codigoNormativa = codigoNormativa;
	}


	public UnidadAdministrativaFiltro toUnidadAdministrativaFiltro() {
		UnidadAdministrativaFiltro resultado = new UnidadAdministrativaFiltro();

		if(this.codigoUAPadre!= null) {
			resultado.setIdUA(codigoUAPadre.longValue());
		}

		if(this.codigoNormativa!= null) {
			resultado.setCodigoNormativa(codigoNormativa);
		}

		if(this.codigoSeccion!= null) {

		}

		if(this.validacion!=null) {

		}

		resultado.setCodEnti(codEnti);
		resultado.setIdentificador(identificador);
		resultado.setNombre(nombre);
		resultado.setTexto(texto);


		return resultado;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public Long getCodEnti() {
		return codEnti;
	}


	public void setCodEnti(Long codEnti) {
		this.codEnti = codEnti;
	}

}
