package es.caib.rolsac2.api.externa.v1.model.filters;

import java.io.IOException;

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
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

/**
 * FiltroServicios.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroServicios", description = "Filtro que permite buscar por diferentes campos")
public class FiltroServicios extends FiltroPaginacion {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroServicios.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA
			+ "{\"codigoUA\":\"0\"," + Constantes.SALTO_LINEA
			+ "\"codigoUADir3\":\"codigo\"," + Constantes.SALTO_LINEA
			+ "\"buscarEnDescendientesUA\":\"0/1\","			+ Constantes.SALTO_LINEA
			+ "\"activo\":\"0/1\",  (1=procedimientos publicos y no caducados, 0=procedimientos no publicos o caducados)"
			+ Constantes.SALTO_LINEA
			+ "\"estadoUA\":\"1/2/3\",  (1=Pública,2=Interna,3=Reserva)"
			+ Constantes.SALTO_LINEA
			+ "\"codigoAgrupacionHechoVital\":\"0\","
			+ Constantes.SALTO_LINEA
			+ "\"codigoPublicoObjetivo\":\"0\","
			+ Constantes.SALTO_LINEA
			+ "\"codigoAgrupacionMateria\":\"0\","
			+ Constantes.SALTO_LINEA
			+ "\"codigoMateria\":\"0\","
			+ Constantes.SALTO_LINEA
			+ "\"titulo\":\"texto\""
			+ Constantes.SALTO_LINEA
			+ "\"textos\":\"texto\""
			+ Constantes.SALTO_LINEA
			+ "\"fechaPublicacionDesde\":\"DD/MM/YYYY\","
			+ Constantes.SALTO_LINEA
			+ "\"fechaPublicacionHasta\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA
			+ "\"codigoTramiteTelematico\":\"codigo\"," + Constantes.SALTO_LINEA
			+ "\"codigoPlataforma\":\"codigo\","
			+ Constantes.SALTO_LINEA + "\"plataforma\":\"texto\"," + Constantes.SALTO_LINEA
			+ "\"parametros\":\"texto\"," + Constantes.SALTO_LINEA
			+ "\"versionTramiteTelematico\":\"version\","
			+ Constantes.SALTO_LINEA
			+ "\"comun\":\"0/1\", (1= servicios comunes)" + Constantes.SALTO_LINEA
			+ "\"codigoSia\":\"codigo\"," + Constantes.SALTO_LINEA
			+ "\"estadoSia\":\"A/B\", (A=Alta, B=Baja)"
			+ Constantes.SALTO_LINEA
			+ "\"fechaActualizacionSia\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA
			+ "\"page\":\"1\",\"size\":\"30\""
			+ "}";

	public static final String SAMPLE_JSON = "{\"codigoUA\":\"0\","
			+ "\"codigoUADir3\":\"codigo\","
			+ "\"buscarEnDescendientesUA\":\"0/1\","
			+ "\"activo\":\"0/1\",  (1=procedimientos publicos y no caducados, 0=procedimientos no publicos o caducados)"
			+ "\"estadoUA\":\"1/2/3\",  (1=Pública,2=Interna,3=Reserva)"
			+ "\"codigoAgrupacionHechoVital\":\"0\","
			+ "\"codigoPublicoObjetivo\":\"0\","
			+ "\"codigoAgrupacionMateria\":\"0\","
			+ "\"codigoMateria\":\"0\","
			+ "\"titulo\":\"texto\""
			+ "\"textos\":\"texto\""
			+ "\"fechaPublicacionDesde\":\"DD/MM/YYYY\","
			+ "\"fechaPublicacionHasta\":\"DD/MM/YYYY\","
			+ "\"codigoTramiteTelematico\":\"codigo\","
			+ "\"codigoPlataforma\":\"codigo\","
			+ "\"plataforma\":\"texto\","
			+ "\"parametros\":\"texto\","
			+ "\"versionTramiteTelematico\":\"version\","
			+ "\"comun\":\"0/1\", (1= servicios comunes)"
			+ "\"codigoSia\":\"codigo\","
			+ "\"estadoSia\":\"A/B\", (A=Alta, B=Baja)"
			+ "\"fechaActualizacionSia\":\"DD/MM/YYYY\","
			+ "\"page\":\"1\",\"size\":\"30\""
			+ "}";

	/** codigoUA. **/
	@Schema(description = "codigoUA", type = SchemaType.INTEGER, required = false)
	private Integer codigoUA;

	/** codigoUADir3. **/
	@Schema(description = "codigoUADir3", type = SchemaType.STRING, required = false)
	private String codigoUADir3;

	/** buscarEnDescendientesUA. **/
	@Schema(description = "buscarEnDescendientesUA", type = SchemaType.INTEGER, required = false)
	private Integer buscarEnDescendientesUA;

	/** activo. **/
	@Schema(description = "activo", type = SchemaType.INTEGER, required = false)
	private Integer activo;

	/** estadoUA. **/
	@Schema(description = "estadoUA- validacion ua", type = SchemaType.INTEGER, required = false)
	private Integer estadoUA;

	/** codigoAgrupacionHechoVital. **/
	@Schema(description = "codigoAgrupacionHechoVital", type = SchemaType.INTEGER, required = false)
	private Integer codigoAgrupacionHechoVital;

	/** codigoPublicoObjetivo. **/
	@Schema(description = "codigoPublicoObjetivo", type = SchemaType.INTEGER, required = false)
	private Integer codigoPublicoObjetivo;

	/** codigoFamilia. **/
	@Schema(description = "codigoFamilia", type = SchemaType.INTEGER, required = false)
	private Integer codigoFamilia;

	/** codigoAgrupacionMateria. **/
	@Schema(description = "codigoAgrupacionMateria", type = SchemaType.INTEGER, required = false)
	private Integer codigoAgrupacionMateria;

	/** codigoMateria. **/
	@Schema(description = "codigoMateria", type = SchemaType.INTEGER, required = false)
	private Integer codigoMateria;

	/** textos. **/
	@Schema(description = "textos", type = SchemaType.STRING, required = false)
	private String textos;

	/** textos. **/
	@Schema(description = "titulo", type = SchemaType.STRING, required = false)
	private String titulo;

	/** fechaPublicacionDesde. **/
	@Schema(description = "fechaPublicacionDesde", type = SchemaType.STRING, required = false)
	private String fechaPublicacionDesde;

	/** fechaPublicacionHasta. **/
	@Schema(description = "fechaPublicacionHasta", type = SchemaType.STRING, required = false)
	private String fechaPublicacionHasta;

	/** vigente. **/
	@Schema(description = "vigente", type = SchemaType.INTEGER, required = false)
	private Integer vigente;

	/** telematico. **/
	@Schema(description = "telematico", type = SchemaType.INTEGER, required = false)
	private Integer telematico;

	/** telematico. **/
	@Schema(description = "comun", type = SchemaType.INTEGER, required = false)
	private Integer comun;

	/** codigoSia. **/
	@Schema(description = "codigoSia", type = SchemaType.STRING, required = false)
	private String codigoSia;

	/** estadoSia. **/
	@Schema(description = "estadoSia", type = SchemaType.STRING, required = false)
	private String estadoSia;

	/** fechaActualizacionSia. **/
	@Schema(description = "fechaActualizacionSia", type = SchemaType.STRING, required = false)
	private String fechaActualizacionSia;

	/** codigoTramiteTelematico. **/
	@Schema(description = "codigoTramiteTelematico", type = SchemaType.STRING, required = false)
	private String codigoTramiteTelematico;

	/** versionTramiteTelematico. **/
	@Schema(description = "versionTramiteTelematico", type = SchemaType.STRING, required = false)
	private String versionTramiteTelematico;

	/** codigo plataforma. **/
	@Schema(description = "codigoPlataforma", type = SchemaType.INTEGER, required = false)
	private Integer codigoPlataforma;

	/** codigo plataforma. **/
	@Schema(description = "plataforma", type = SchemaType.STRING, required = false)
	private String plataforma;

	/** codigoAgrupacionMateria. **/
	@Schema(description = "parametros", type = SchemaType.STRING, required = false)
	private String parametros;

	public FiltroGenerico toFiltroGenerico() {
		final FiltroGenerico fg = new FiltroGenerico();

		if (this.codigoUA != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_UA, this.codigoUA + "");
		}

		if (this.codigoUADir3 != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_UA_DIR3, this.codigoUADir3 + "");
		}

		if (this.buscarEnDescendientesUA != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_UA_DESCENDIENTES, this.buscarEnDescendientesUA + "");
		}

		if (this.activo != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_ACTIVO, this.activo + "");
		}

		if (this.estadoUA != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_ESTADO_UA, this.estadoUA + "");
		}

		if (this.codigoAgrupacionHechoVital != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_AGRUPACION_HECHO_VITAL, this.codigoAgrupacionHechoVital + "");
		}

		if (this.codigoPublicoObjetivo != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_PUBLICO, this.codigoPublicoObjetivo + "");
		}

		if (this.codigoFamilia != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_FAMILIA, this.codigoFamilia + "");
		}

		if (this.codigoAgrupacionMateria != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_AGRUPACION_MATERIA, this.codigoAgrupacionMateria + "");
		}

		if (this.codigoMateria != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_MATERIA, this.codigoMateria + "");
		}

		if (this.comun != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_COMUN, this.comun + "");
		}

		if (this.textos != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_TEXTOS, this.textos + "");
		}

		if (this.titulo != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_TITULO, this.titulo + "");
		}

		if (this.fechaPublicacionDesde != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_FECHA_PUBLICACION_DESDE, this.fechaPublicacionDesde + "");
		}

		if (this.fechaPublicacionHasta != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_FECHA_PUBLICACION_HASTA, this.fechaPublicacionHasta + "");
		}

		if (this.vigente != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_VIGENTE, this.vigente + "");
		}

		if (this.telematico != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_TELEMATICO, this.telematico + "");
		}

		if (this.codigoSia != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_CODIGO_SIA, this.codigoSia + "");
		}

		if (this.estadoSia != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_ESTADO_SIA, this.estadoSia + "");
		}

		if (this.fechaActualizacionSia != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_FECHA_ACTUALIZACION_SIA, this.fechaActualizacionSia + "");
		}

		if (this.codigoTramiteTelematico != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_PROCEDIMIENTO_TRAMITE_TELEMATICO, this.codigoTramiteTelematico + "");
		}

		if (this.versionTramiteTelematico != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_PROCEDIMIENTO_VERSION_TRAMITE_TELEMATICO,
					this.versionTramiteTelematico + "");
		}

		if (this.plataforma != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_PLATAFORMA, this.plataforma + "");
		}

		if (this.codigoPlataforma != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_PLATAFORMA_CODIGO, this.codigoPlataforma + "");
		}

		if (this.parametros != null) {
			fg.addFiltro(FiltroGenerico.FILTRO_SERVICIOS_PARAMETROS, this.parametros + "");
		}

		return fg;
	}

	public static FiltroServicios valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<FiltroServicios> typeRef = new TypeReference<FiltroServicios>() {
		};
		FiltroServicios obj;
		try {
			obj = (FiltroServicios) objectMapper.readValue(json, typeRef);
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

	/**
	 * @return the codigoUA
	 */
	public Integer getCodigoUA() {
		return codigoUA;
	}

	/**
	 * @param codigoUA
	 *            the codigoUA to set
	 */
	public void setCodigoUA(final Integer codigoUA) {
		this.codigoUA = codigoUA;
	}

	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(final Integer activo) {
		this.activo = activo;
	}

	/**
	 * @return the estadoUA
	 */
	public Integer getEstadoUA() {
		return estadoUA;
	}

	/**
	 * @param estadoUA
	 *            the estadoUA to set
	 */
	public void setEstadoUA(final Integer estadoUA) {
		this.estadoUA = estadoUA;
	}

	/**
	 * @return the codigoAgrupacionHechoVital
	 */
	public Integer getCodigoAgrupacionHechoVital() {
		return codigoAgrupacionHechoVital;
	}

	/**
	 * @param codigoAgrupacionHechoVital
	 *            the codigoAgrupacionHechoVital to set
	 */
	public void setCodigoAgrupacionHechoVital(final Integer codigoAgrupacionHechoVital) {
		this.codigoAgrupacionHechoVital = codigoAgrupacionHechoVital;
	}

	/**
	 * @return the codigoPublicoObjetivo
	 */
	public Integer getCodigoPublicoObjetivo() {
		return codigoPublicoObjetivo;
	}

	/**
	 * @param codigoPublicoObjetivo
	 *            the codigoPublicoObjetivo to set
	 */
	public void setCodigoPublicoObjetivo(final Integer codigoPublicoObjetivo) {
		this.codigoPublicoObjetivo = codigoPublicoObjetivo;
	}

	/**
	 * @return the codigoFamilia
	 */
	public Integer getCodigoFamilia() {
		return codigoFamilia;
	}

	/**
	 * @param codigoFamilia
	 *            the codigoFamilia to set
	 */
	public void setCodigoFamilia(final Integer codigoFamilia) {
		this.codigoFamilia = codigoFamilia;
	}

	/**
	 * @return the codigoAgrupacionMateria
	 */
	public Integer getCodigoAgrupacionMateria() {
		return codigoAgrupacionMateria;
	}

	/**
	 * @param codigoAgrupacionMateria
	 *            the codigoAgrupacionMateria to set
	 */
	public void setCodigoAgrupacionMateria(final Integer codigoAgrupacionMateria) {
		this.codigoAgrupacionMateria = codigoAgrupacionMateria;
	}

	/**
	 * @return the codigoMateria
	 */
	public Integer getCodigoMateria() {
		return codigoMateria;
	}

	/**
	 * @param codigoMateria
	 *            the codigoMateria to set
	 */
	public void setCodigoMateria(final Integer codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	/**
	 * @return the textos
	 */
	public String getTextos() {
		return textos;
	}

	/**
	 * @param textos
	 *            the textos to set
	 */
	public void setTextos(final String textos) {
		this.textos = textos;
	}

	/**
	 * @return the fechaPublicacionDesde
	 */
	public String getFechaPublicacionDesde() {
		return fechaPublicacionDesde;
	}

	/**
	 * @param fechaPublicacionDesde
	 *            the fechaPublicacionDesde to set
	 */
	public void setFechaPublicacionDesde(final String fechaPublicacionDesde) {
		this.fechaPublicacionDesde = fechaPublicacionDesde;
	}

	/**
	 * @return the fechaPublicacionHasta
	 */
	public String getFechaPublicacionHasta() {
		return fechaPublicacionHasta;
	}

	/**
	 * @param fechaPublicacionHasta
	 *            the fechaPublicacionHasta to set
	 */
	public void setFechaPublicacionHasta(final String fechaPublicacionHasta) {
		this.fechaPublicacionHasta = fechaPublicacionHasta;
	}

	/**
	 * @return the vigente
	 */
	public Integer getVigente() {
		return vigente;
	}

	/**
	 * @param vigente
	 *            the vigente to set
	 */
	public void setVigente(final Integer vigente) {
		this.vigente = vigente;
	}

	/**
	 * @return the telematico
	 */
	public Integer getTelematico() {
		return telematico;
	}

	/**
	 * @param telematico
	 *            the telematico to set
	 */
	public void setTelematico(final Integer telematico) {
		this.telematico = telematico;
	}

	/**
	 * @return the fechaActualizacionSia
	 */
	public String getFechaActualizacionSia() {
		return fechaActualizacionSia;
	}

	/**
	 * @param fechaActualizacionSia
	 *            the fechaActualizacionSia to set
	 */
	public void setFechaActualizacionSia(final String fechaActualizacionSia) {
		this.fechaActualizacionSia = fechaActualizacionSia;
	}

	/**
	 * @return the estadoSia
	 */
	public String getEstadoSia() {
		return estadoSia;
	}

	/**
	 * @param estadoSia
	 *            the estadoSia to set
	 */
	public void setEstadoSia(final String estadoSia) {
		this.estadoSia = estadoSia;
	}

	/**
	 * @return the codigoSia
	 */
	public String getCodigoSia() {
		return codigoSia;
	}

	/**
	 * @param codigoSia
	 *            the codigoSia to set
	 */
	public void setCodigoSia(final String codigoSia) {
		this.codigoSia = codigoSia;
	}

	/**
	 * @return the buscarEnDescendientesUA
	 */
	public Integer getBuscarEnDescendientesUA() {
		return buscarEnDescendientesUA;
	}

	/**
	 * @param buscarEnDescendientesUA
	 *            the buscarEnDescendientesUA to set
	 */
	public void setBuscarEnDescendientesUA(final Integer buscarEnDescendientesUA) {
		this.buscarEnDescendientesUA = buscarEnDescendientesUA;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the codigoTramiteTelematico
	 */
	public String getCodigoTramiteTelematico() {
		return codigoTramiteTelematico;
	}

	/**
	 * @param codigoTramiteTelematico
	 *            the codigoTramiteTelematico to set
	 */
	public void setCodigoTramiteTelematico(final String codigoTramiteTelematico) {
		this.codigoTramiteTelematico = codigoTramiteTelematico;
	}

	/**
	 * @return the versionTramiteTelematico
	 */
	public String getVersionTramiteTelematico() {
		return versionTramiteTelematico;
	}

	/**
	 * @param versionTramiteTelematico
	 *            the versionTramiteTelematico to set
	 */
	public void setVersionTramiteTelematico(final String versionTramiteTelematico) {
		this.versionTramiteTelematico = versionTramiteTelematico;
	}

	/**
	 * @return the codigoUADir3
	 */
	public String getCodigoUADir3() {
		return codigoUADir3;
	}

	/**
	 * @param codigoUADir3
	 *            the codigoUADir3 to set
	 */
	public void setCodigoUADir3(final String codigoUADir3) {
		this.codigoUADir3 = codigoUADir3;
	}

	/**
	 * @return the comun
	 */
	public Integer getComun() {
		return comun;
	}

	/**
	 * @param comun
	 *            the comun to set
	 */
	public void setComun(final Integer comun) {
		this.comun = comun;
	}

	/**
	 * @return the codigoPlataforma
	 */
	public Integer getCodigoPlataforma() {
		return codigoPlataforma;
	}

	/**
	 * @param codigoPlataforma
	 *            the codigoPlataforma to set
	 */
	public void setCodigoPlataforma(final Integer codigoPlataforma) {
		this.codigoPlataforma = codigoPlataforma;
	}

	/**
	 * @return the parametros
	 */
	public String getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            the parametros to set
	 */
	public void setParametros(final String parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return the plataforma
	 */
	public String getPlataforma() {
		return plataforma;
	}

	/**
	 * @param plataforma
	 *            the plataforma to set
	 */
	public void setPlataforma(final String plataforma) {
		this.plataforma = plataforma;
	}

	public ProcedimientoFiltro toServicioFiltro() {
		final ProcedimientoFiltro fg = new ProcedimientoFiltro();

		if (this.codigoUA != null) {
//			fg.setCodigo
		}

		if (this.codigoUADir3 != null) {
			fg.setCodigoDir3SIA(codigoUADir3);
		}

		if (this.buscarEnDescendientesUA != null) {

		}

		if (this.activo != null) {

		}

		if (this.estadoUA != null) {

		}

		if (this.codigoAgrupacionHechoVital != null) {

		}

		if (this.codigoPublicoObjetivo != null) {

		}

		if (this.codigoFamilia != null) {

		}

		if (this.codigoAgrupacionMateria != null) {

		}

		if (this.codigoMateria != null) {

		}

		if (this.comun != null) {
			fg.setComun(comun.toString());
		}

		if (this.textos != null) {
			fg.setTexto(textos);
		}

		if (this.titulo != null) {
			fg.setTexto(titulo);
		}

		if (this.fechaPublicacionDesde != null) {

		}

		if (this.fechaPublicacionHasta != null) {

		}

		if (this.vigente != null) {

		}

		if (this.telematico != null) {

		}

		if (this.codigoSia != null) {
			fg.setCodigoSIA(Integer.valueOf(codigoSia));
		}

		if (this.estadoSia != null) {
			fg.setEstadoSIA(estadoSia);
		}

		if (this.fechaActualizacionSia != null) {

		}

		if (this.codigoTramiteTelematico != null) {

		}

		if (this.versionTramiteTelematico != null) {

		}

		if (this.plataforma != null) {

		}

		if (this.codigoPlataforma != null) {

		}

		if (this.parametros != null) {

		}

		return fg;
	}

}
