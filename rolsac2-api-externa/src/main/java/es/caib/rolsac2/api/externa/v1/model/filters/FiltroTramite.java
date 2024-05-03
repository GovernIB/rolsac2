package es.caib.rolsac2.api.externa.v1.model.filters;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * FiltroProcedimientoTramite.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "FiltroTramite", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTramite extends EntidadJson<FiltroTramite> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroTramite.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"orden\":0," + Constantes.SALTO_LINEA + "\"fase\":0," + Constantes.SALTO_LINEA + "\"codigoUnidadAdministrativa\":0," + Constantes.SALTO_LINEA + "\"codigoProcedimientoWF\":0," + Constantes.SALTO_LINEA + "\"codigoTipoTramitacion\":0," + Constantes.SALTO_LINEA + "\"idTramite\":0," + Constantes.SALTO_LINEA + "\"idPlataforma\":0," + Constantes.SALTO_LINEA + "\"version\":0," + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA + "\"texto\":\"string\"," + Constantes.SALTO_LINEA + "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "}";

	public static final String SAMPLE_JSON = "{" + "\n	\"orden\":null," + "\n	\"fase\":null," + "\n	\"codigoUnidadAdministrativa\":null," + "\n	\"codigoProcedimientoWF\":null," + "\n	\"codigoTipoTramitacion\":null," + "\n	\"idEntidad\":null," + "\n	\"idTramite\":null," + "\n	\"idPlataforma\":null," + "\n	\"version\":null," + "\n	\"texto\":null," + "\n	\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "\n}";

	@Schema(name = "orden", description = "orden", type = SchemaType.INTEGER, required = false)
	private Integer orden;

	/**
	 * Entidad
	 */
	@Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
	private Long idEntidad;

	@Schema(name = "fase", description = "fase", type = SchemaType.INTEGER, required = false)
	private Integer fase;

	@Schema(name = "codigoUnidadAdministrativa", description = "codigoUnidadAdministrativa", type = SchemaType.INTEGER, required = false)
	private Long codigoUnidadAdministrativa;

	@Schema(name = "codigoProcedimiento", description = "codigoProcedimiento", type = SchemaType.INTEGER, required = false)
	private Long codigoProcedimiento;

	@Schema(name = "codigoProcedimientoWF", description = "codigoProcedimientoWF", type = SchemaType.INTEGER, required = false)
	private Long codigoProcedimientoWF;

	@Schema(name = "codigoTipoTramitacion", description = "codigoTipoTramitacion", type = SchemaType.INTEGER, required = false)
	private Long codigoTipoTramitacion;

	/**
	 * FiltroPaginacion.
	 **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

	/**
	 * texto.
	 **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

	/**
	 * estadoWF.
	 **/
	@Schema(name = "estadoWF", description = "estadoWF", type = SchemaType.STRING, required = false)
	private String estadoWF;

	@Schema(description = "idTramite", type = SchemaType.STRING, required = false)
	private String idTramite;

	@Schema(description = "idPlataforma", type = SchemaType.STRING, required = false)
	private String idPlataforma;

	@Schema(description = "version", type = SchemaType.STRING, required = false)
	private Integer version;

	public String getEstadoWF() {
		return estadoWF;
	}

	public void setEstadoWF(String estadoWF) {
		this.estadoWF = estadoWF;
	}


	public Long getCodigoProcedimientoWF() {
		return codigoProcedimientoWF;
	}

	public void setCodigoProcedimientoWF(Long codigoProcedimientoWF) {
		this.codigoProcedimientoWF = codigoProcedimientoWF;
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public String getIdPlataforma() {
		return idPlataforma;
	}

	public void setIdPlataforma(String idPlataforma) {
		this.idPlataforma = idPlataforma;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ProcedimientoTramiteFiltro toProcedimientoTramiteFiltro() {
		ProcedimientoTramiteFiltro resultado = new ProcedimientoTramiteFiltro();

		if (this.texto != null && !this.texto.isEmpty()) {
			resultado.setTexto(texto);
		}

		if (this.idEntidad != null) {
			resultado.setIdEntidad(idEntidad);
		}

		if (this.orden != null) {
			resultado.setOrden(orden);
		}

		if (this.fase != null) {
			resultado.setFase(fase);
		}

		if (this.codigoUnidadAdministrativa != null) {
			UnidadAdministrativaDTO unidadAdministrativa = new UnidadAdministrativaDTO();
			unidadAdministrativa.setCodigo(codigoUnidadAdministrativa);
			resultado.setUnidadAdministrativa(unidadAdministrativa);
		}

		if (this.codigoProcedimientoWF != null) {
			ProcedimientoDTO procedimiento = new ProcedimientoDTO();
			procedimiento.setCodigoWF(codigoProcedimientoWF);
			resultado.setProcedimiento(procedimiento);
		}

		if (this.codigoTipoTramitacion != null) {
			TipoTramitacionDTO tipoTramitacion = new TipoTramitacionDTO();
			tipoTramitacion.setCodigo(codigoTipoTramitacion);
			resultado.setTipoTramitacion(tipoTramitacion);
		}

		if (this.idPlataforma != null) {
			resultado.setIdentificadorPlataforma(this.idPlataforma);
		}

		if (this.idTramite != null) {
			resultado.setIdTramite(idTramite);
		}

		if (this.version != null) {
			resultado.setVersion(version);
		}

		return resultado;

	}

	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getFase() {
		return fase;
	}

	public void setFase(Integer fase) {
		this.fase = fase;
	}

	public Long getCodigoUnidadAdministrativa() {
		return codigoUnidadAdministrativa;
	}

	public void setCodigoUnidadAdministrativa(Long codigoUnidadAdministrativa) {
		this.codigoUnidadAdministrativa = codigoUnidadAdministrativa;
	}

	public Long getCodigoProcedimiento() {
		return codigoProcedimiento;
	}

	public void setCodigoProcedimiento(Long codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;
	}

	public Long getCodigoTipoTramitacion() {
		return codigoTipoTramitacion;
	}

	public void setCodigoTipoTramitacion(Long codigoTipoTramitacion) {
		this.codigoTipoTramitacion = codigoTipoTramitacion;
	}

}
