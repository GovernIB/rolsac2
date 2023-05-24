package es.caib.rolsac2.api.externa.v1.model.filters;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;

/**
 * FiltroProcedimientoTramite.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroTramite", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroProcedimientoTramite extends EntidadJson<FiltroProcedimientoTramite> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroProcedimientoTramite.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{" +
			"\"orden\":0," 					+ Constantes.SALTO_LINEA +
			"\"fase\":0," 					+ Constantes.SALTO_LINEA +
			"\"fechaPublicacion\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
			"\"fechaCierre\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
			"\"codigoUnidadAdministrativa\":0," + Constantes.SALTO_LINEA +
			"\"codigoProcedimiento\":0," + Constantes.SALTO_LINEA +
			"\"codigoTipoTramitacion\":0," + Constantes.SALTO_LINEA +
			"\"fechaInicio\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
			"\"texto\":\"string\"," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	public static final String SAMPLE_JSON = "{" +
			"\"orden\":null," +
			"\"fase\":null," +
			"\"fechaPublicacion\":null," +
			"\"codigoUnidadAdministrativa\":null," +
			"\"codigoProcedimiento\":null,"+
			"\"codigoTipoTramitacion\":null,"+
			"\"fechaInicio\":null," +
			"\"fechaCierre\":null," +
			"\"texto\":null," +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

    private Integer orden;
	private Integer fase;

    private Long codigoUnidadAdministrativa;
    private Long codigoProcedimiento;
    private Long codigoTipoTramitacion;

    private Date fechaPublicacion;
    private Date fechaInicio;
    private Date fechaCierre;

	/** FiltroPaginacion. **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

	/** texto. **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

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

	public ProcedimientoTramiteFiltro toProcedimientoTramiteFiltro() {
		ProcedimientoTramiteFiltro resultado = new ProcedimientoTramiteFiltro();

		if (this.texto != null && !this.texto.isEmpty()) {
			resultado.setTexto(texto);
		}

		if (this.fechaInicio != null) {
			resultado.setFechaInicio(fechaInicio);
		}

		if (this.fechaPublicacion != null) {
			resultado.setFechaPublicacion(fechaPublicacion);
		}

		if (this.fechaCierre != null) {
			resultado.setFechaCierre(fechaCierre);
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

		if (this.codigoProcedimiento != null) {
			ProcedimientoDTO procedimiento = new ProcedimientoDTO();
			procedimiento.setCodigo(codigoProcedimiento);
			resultado.setProcedimiento(procedimiento);
		}

		if (this.codigoTipoTramitacion != null) {
			TipoTramitacionDTO tipoTramitacion = new TipoTramitacionDTO();
			tipoTramitacion.setCodigo(codigoTipoTramitacion);
			resultado.setTipoTramitacion(tipoTramitacion);
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

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

}
