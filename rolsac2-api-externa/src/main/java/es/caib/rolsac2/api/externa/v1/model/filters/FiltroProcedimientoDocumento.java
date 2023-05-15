package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;

/**
 * FiltroNormatives.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroProcedimientoDocumentos", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroProcedimientoDocumento extends EntidadJson<FiltroProcedimientoDocumento> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroProcedimientoDocumento.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{" +
			"\"orden\":0," 					+ Constantes.SALTO_LINEA +
			"\"codigoDocumento\":0," + Constantes.SALTO_LINEA +
			"\"codigoProcedimiento\":0," + Constantes.SALTO_LINEA +
			"\"texto\":\"0\"," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	public static final String SAMPLE_JSON = "{" +
			"\"orden\":null," 					+
			"\"codigoDocumento\":null," +
			"\"codigoProcedimiento\":null,"+
			"\"texto\":null," +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

    /**
     * Orden
     */
	@Schema(name = "orden", description = "orden", type = SchemaType.INTEGER, required = false)
    private Integer orden;

    /*Fichero asociado al documento*/
	@Schema(name = "fichero", description = "fichero", type = SchemaType.INTEGER, required = false)
    private Long codigoDocumento;

//    /**
//     * Procedimiento
//     **/
//	@Schema(name = "procedimientoDTO", description = "procedimientoDTO", type = SchemaType.INTEGER, required = false)
//    private Long codigoProcedimiento;

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

	public ProcedimientoDocumentoFiltro toProcedimientoDocumentoFiltro() {
		ProcedimientoDocumentoFiltro resultado = new ProcedimientoDocumentoFiltro();

		if (this.texto != null && !this.texto.isEmpty()) {
			resultado.setTexto(texto);
		}

//		if (this.codigoProcedimiento != null) {
//			ProcedimientoDTO procedimientoDTO = new ProcedimientoDTO();
//			procedimientoDTO.setCodigo(codigoProcedimiento);
//			resultado.setProcedimientoDTO(procedimientoDTO);
//		}

		if (this.codigoDocumento != null) {
			FicheroDTO fichero = new FicheroDTO();
			fichero.setCodigo(codigoDocumento);
			resultado.setDocumento(fichero);
		}

		if (this.orden != null) {
			resultado.setOrden(orden);
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

	public Long getDocumento() {
		return codigoDocumento;
	}

	public void setCodigoDocumento(Long codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

//	public Long getCodigoProcedimiento() {
//		return codigoProcedimiento;
//	}

//	public void setCodigoProcedimiento(Long codigoProcedimiento) {
//		this.codigoProcedimiento = codigoProcedimiento;
//	}


}
