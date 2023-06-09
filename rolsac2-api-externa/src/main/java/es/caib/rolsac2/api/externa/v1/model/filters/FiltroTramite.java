package es.caib.rolsac2.api.externa.v1.model.filters;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class FiltroTramite extends EntidadJson<FiltroTramite> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroTramite.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{" +
			"\"orden\":0," 					+ Constantes.SALTO_LINEA +
			"\"fase\":0," 					+ Constantes.SALTO_LINEA +
//			"\"fechaPublicacion\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
//			"\"fechaCierre\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
			"\"codigoUnidadAdministrativa\":0," + Constantes.SALTO_LINEA +
			"\"codigoProcedimiento\":0," + Constantes.SALTO_LINEA +
			"\"codigoTipoTramitacion\":0," + Constantes.SALTO_LINEA +
			"\"estadoWF\":\"D/M/T/A\", (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))" + Constantes.SALTO_LINEA +
//			"\"fechaInicio\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
			"\"idEntidad\":0," + Constantes.SALTO_LINEA +
			"\"texto\":\"string\"," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	public static final String SAMPLE_JSON = "{" +
			"\"orden\":null," +
			"\"fase\":null," +
//			"\"fechaPublicacion\":null," +
			"\"codigoUnidadAdministrativa\":null," +
			"\"codigoProcedimiento\":null,"+
			"\"codigoTipoTramitacion\":null,"+
//			"\"fechaInicio\":null," +
//			"\"fechaCierre\":null," +
 			"\"estadoWF\":null," +
 			"\"idEntidad\":null," +
			"\"texto\":null," +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

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

    @Schema(name = "codigoTipoTramitacion", description = "codigoTipoTramitacion", type = SchemaType.INTEGER, required = false)
    private Long codigoTipoTramitacion;

//    @Schema(description = "fechaPublicacion", name = "fechaPublicacion", type = SchemaType.STRING, required = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    private Date fechaPublicacion;
//
//    @Schema(description = "fechaInicio", name = "fechaInicio", type = SchemaType.STRING, required = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    private Date fechaInicio;
//
//    @Schema(description = "fechaCierre", name = "fechaCierre", type = SchemaType.STRING, required = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    private Date fechaCierre;

	/** FiltroPaginacion. **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

	/** texto. **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

	/** estadoWF. **/
	@Schema(name = "estadoWF", description = "estadoWF", type = SchemaType.STRING, required = false)
	private String estadoWF;

	public String getEstadoWF() {
		return estadoWF;
	}

	public void setEstadoWF(String estadoWF) {
		this.estadoWF = estadoWF;
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

		if (this.estadoWF != null) {
			resultado.setEstadoWF(estadoWF);
		} else {
			resultado.setEstadoWF("T");
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

//	public Date getFechaPublicacion() {
//		return fechaPublicacion;
//	}
//
//	public void setFechaPublicacion(Date fechaPublicacion) {
//		this.fechaPublicacion = fechaPublicacion;
//	}
//
//	public Date getFechaInicio() {
//		return fechaInicio;
//	}
//
//	public void setFechaInicio(Date fechaInicio) {
//		this.fechaInicio = fechaInicio;
//	}
//
//	public Date getFechaCierre() {
//		return fechaCierre;
//	}
//
//	public void setFechaCierre(Date fechaCierre) {
//		this.fechaCierre = fechaCierre;
//	}

}
