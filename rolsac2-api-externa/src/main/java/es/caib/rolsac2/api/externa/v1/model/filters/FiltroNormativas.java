package es.caib.rolsac2.api.externa.v1.model.filters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

/**
 * FiltroNormatives.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroNormativas", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroNormativas extends EntidadJson<FiltroNormativas> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroNormativas.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA +
			"{"	+
//			"\"codigoUA\":\"0\"," 					+ Constantes.SALTO_LINEA +
			"\"fechaBoletin\":\"DD/MM/YYYY\"," 		+ Constantes.SALTO_LINEA +
			"\"codigoProcedimiento\":\"0\"," 		+ Constantes.SALTO_LINEA +
			"\"codigoServicio\":\"0\"," 			+ Constantes.SALTO_LINEA +
			"\"numNorma\":\"0\"," 					+ Constantes.SALTO_LINEA +
			"\"fechaPublicacion\":\"DD/MM/YYYY\"," 	+ Constantes.SALTO_LINEA +
//			"\"tipoPublicacion\":\"0\"," 			+ Constantes.SALTO_LINEA +
			"\"texto\":\"0\"," 						+ Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	public static final String SAMPLE_JSON =
			"{" +
//			"\"codigoUA\":\"0\"," 					+
			"\"fechaBoletin\":null," 		+
			"\"codigoProcedimiento\":null," 		+
			"\"codigoServicio\":null," 			+
			"\"numNorma\":null," 					+
			"\"fechaPublicacion\":null," 	+
//			"\"tipoPublicacion\":\"0\"," 			+
			"\"texto\":null," 						+
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

//	/** codigoUA. **/
//	@Schema(name = "codigoUA", description = "codigoUA", type = SchemaType.INTEGER, required = false)
//	private Integer codigoUA;

	/** fechaBoletin. **/
	@Schema(name = "fechaBoletin", description = "fechaBoletin", type = SchemaType.STRING, required = false)
	private String fechaBoletin;

	/** codigoProcedimiento **/
	@Schema(name = "codigoProcedimiento", description = "codigoProcedimiento", type = SchemaType.INTEGER, required = false)
	private Integer codigoProcedimiento;

	/** codigoServicio. **/
	@Schema(name = "codigoServicio", description = "codigoServicio", type = SchemaType.INTEGER, required = false)
	private Integer codigoServicio;

	/** numNorma. **/
	@Schema(name = "numNorma", description = "numNorma", type = SchemaType.STRING, required = false)
	private String numNorma;

	/** fechaPublicacion. **/
	@Schema(name = "fechaPublicacion", description = "fechaPublicacion", type = SchemaType.STRING, required = false)
	private String fechaPublicacion;

//	/** tipoPublicacion. **/
//	@Schema(name = "tipoPublicacion", description = "tipoPublicacion", type = SchemaType.INTEGER, required = false)
//	private Integer tipoPublicacion;

	/** FiltroPaginacion. **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

	/** texto. **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

//	public static FiltroNormativas valueOf(final String json) {
//		final ObjectMapper objectMapper = new ObjectMapper();
//		final TypeReference<FiltroNormativas> typeRef = new TypeReference<FiltroNormativas>() {
//		};
//		FiltroNormativas obj;
//		try {
//			obj = (FiltroNormativas) objectMapper.readValue(json, typeRef);
//		} catch (final IOException e) {
//			LOG.error(e.getMessage(), e);
//			throw new RuntimeException(e);
//		}
//		return obj;
//	}
//
//	public String toJson() {
//		try {
//			final ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//			return objectMapper.writeValueAsString(this);
//		} catch (final JsonProcessingException e) {
//			LOG.error(e.getMessage(), e);
//			throw new RuntimeException(e);
//		}
//	}

//	/**
//	 * @return the codigoUA
//	 */
//	public Integer getCodigoUA() {
//		return codigoUA;
//	}

//	/**
//	 * @param codigoUA the codigoUA to set
//	 */
//	public void setCodigoUA(Integer codigoUA) {
//		this.codigoUA = codigoUA;
//	}

	/**
	 * @return the fechaBoletin
	 */
	public String getFechaBoletin() {
		return fechaBoletin;
	}

	/**
	 * @param fechaBoletin the fechaBoletin to set
	 */
	public void setFechaBoletin(String fechaBoletin) {
		this.fechaBoletin = fechaBoletin;
	}

	/**
	 * @return the codigoProcedimiento
	 */
	public Integer getCodigoProcedimiento() {
		return codigoProcedimiento;
	}

	/**
	 * @param codigoProcedimiento the codigoProcedimiento to set
	 */
	public void setCodigoProcedimiento(Integer codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;
	}

	/**
	 * @return the codigoServicio
	 */
	public Integer getCodigoServicio() {
		return codigoServicio;
	}

	/**
	 * @param codigoServicio the codigoServicio to set
	 */
	public void setCodigoServicio(Integer codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	/**
	 * @return the numNorma
	 */
	public String getNumNorma() {
		return numNorma;
	}

	/**
	 * @param numNorma the numNorma to set
	 */
	public void setNumNorma(String numNorma) {
		this.numNorma = numNorma;
	}

	/**
	 * @return the fechaPublicacion
	 */
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * @param fechaPublicacion the fechaPublicacion to set
	 */
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

//	/**
//	 * @return the tipoPublicacion
//	 */
//	public Integer getTipoPublicacion() {
//		return tipoPublicacion;
//	}

//	/**
//	 * @param tipoPublicacion the tipoPublicacion to set
//	 */
//	public void setTipoPublicacion(Integer tipoPublicacion) {
//		this.tipoPublicacion = tipoPublicacion;
//	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public NormativaFiltro toNormativaFiltro() {
		NormativaFiltro resultado = new NormativaFiltro();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

//		if (this.codigoUA != null) {
//
//		}

		if (this.fechaBoletin != null) {
			resultado.setFechaBoletin(LocalDate.parse(fechaBoletin, formatter));
		}

		if (this.codigoProcedimiento != null) {
			resultado.setCodigoProcedimiento(codigoProcedimiento);
		}

		if (this.codigoServicio != null) {
			resultado.setCodigoServicio(codigoServicio);
		}

		if (this.numNorma != null) {
			resultado.setNumero(numNorma);
		}

		if (this.fechaPublicacion != null) {
			resultado.setFechaAprobacion(LocalDate.parse(fechaPublicacion, formatter));
		}

		// ¿tipoPublicacion es tipoNormativa o tipoBoletin?
//		if (this.tipoPublicacion != null) {
//			resultado.setTipoNormativa(tipoNormativa);
//			resultado.setTipoBoletin(tipoBoletin);
//		}

		if (this.texto != null) {
			resultado.setTexto(texto);
		}

		return resultado;

	}

	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}

}
