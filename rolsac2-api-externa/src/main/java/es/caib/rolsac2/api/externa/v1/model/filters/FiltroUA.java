package es.caib.rolsac2.api.externa.v1.model.filters;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
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
public class FiltroUA extends EntidadJson<FiltroUA> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroUA.class);

	public static final String CAMPO_ORD_UA_ORDEN = "orden";

	public static final String SAMPLE =    Constantes.SALTO_LINEA +
			"{" +
//			"\"codigoUAPadre\":\"0\","	 + Constantes.SALTO_LINEA +
//			"\"validacion\":\"0\","		 + Constantes.SALTO_LINEA +
//			"\"codigoSeccion\":\"0\","	 + Constantes.SALTO_LINEA +
			"\"codigoNormativa\":\"0\"," + Constantes.SALTO_LINEA +
			"\"nombre\":\"0\"," + Constantes.SALTO_LINEA +
			"\"identificador\":\"0\"," + Constantes.SALTO_LINEA +
			"\"codEnti\":\"0\"," + Constantes.SALTO_LINEA +
			"\"texto\":\"0\"," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," + Constantes.SALTO_LINEA +
			"\"listaOrden\":[{\"campo\":\"" + CAMPO_ORD_UA_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}]" +
			"}"
//			+ Constantes.SALTO_LINEA + "Usar codigoUAPadre = -1 para recuperar las UA que no tienen padre"
			;

	public static final String SAMPLE_JSON =
			"{" +
//			"\"codigoUAPadre\":\"0\","	 +
//			"\"validacion\":\"0\","		 +
//			"\"codigoSeccion\":\"0\","	 +
			"\"codigoNormativa\":null," +
			"\"nombre\":null," +
			"\"identificador\":null," +
			"\"codEnti\":null," +
			"\"texto\":null,"	+
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}," +
			"\"listaOrden\":null" +
			"}";

//	/** CodigoUAPadre. **/
//	@Schema(description = "Código Unidad Administrativa padre ", type = SchemaType.INTEGER, required = false)
//	private Integer codigoUAPadre;

//	/** Validacion. **/
//	@Schema(description = "Validacion", type = SchemaType.INTEGER, required = false)
//	private Integer validacion;

//	/** CodigoSeccion. **/
//	@Schema(description = "Código de sección", type = SchemaType.INTEGER, required = false)
//	private Integer codigoSeccion;

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

	/** FiltroPaginacion. **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

	/** Lista de campos a ordenar. **/
	@Schema(description = "Lista de campos por los que ordenar", required = false)
	private List<CampoOrden> listaOrden;

	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}

//	/**
//	 * @return the codigoUAPadre
//	 */
//	public Integer getCodigoUAPadre() {
//		return codigoUAPadre;
//	}


//	/**
//	 * @param codigoUAPadre the codigoUAPadre to set
//	 */
//	public void setCodigoUAPadre(Integer codigoUAPadre) {
//		this.codigoUAPadre = codigoUAPadre;
//	}


//	/**
//	 * @return the validacion
//	 */
//	public Integer getValidacion() {
//		return validacion;
//	}

//	/**
//	 * @param validacion the validacion to set
//	 */
//	public void setValidacion(Integer validacion) {
//		this.validacion = validacion;
//	}

//	/**
//	 * @return the codigoSeccion
//	 */
//	public Integer getCodigoSeccion() {
//		return codigoSeccion;
//	}


//	/**
//	 * @param codigoSeccion the codigoSeccion to set
//	 */
//	public void setCodigoSeccion(Integer codigoSeccion) {
//		this.codigoSeccion = codigoSeccion;
//	}

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

//		if(this.codigoUAPadre!= null) {
//
//		}

		if(this.codigoNormativa!= null) {
			resultado.setCodigoNormativa(codigoNormativa);
		}

//		if(this.codigoSeccion!= null) {
//
//		}

//		if(this.validacion!=null) {
//
//		}

		if(this.codEnti!= null) {
			resultado.setCodEnti(codEnti);
		}

		if(this.identificador!= null) {
			resultado.setIdentificador(identificador);
		}

		if(this.nombre!= null) {
			resultado.setNombre(nombre);
		}

		if(this.texto!= null) {
			resultado.setTexto(texto);
		}

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

	/**
	 * @return the listaOrden
	 */
	public List<CampoOrden> getListaOrden() {
		return listaOrden;
	}

	/**
	 * @param listaOrden the listaOrden to set
	 */
	public void setListaOrden(List<CampoOrden> listaOrden) {
		this.listaOrden = listaOrden;
	}

}
