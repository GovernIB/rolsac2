package es.caib.rolsac2.api.externa.v1.model.filters;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.filtro.DocumentoNormativaFiltro;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

/**
 * FiltroNormatives.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroDocumentoNormativa", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroDocumentoNormativa extends EntidadJson<FiltroDocumentoNormativa> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroDocumentoNormativa.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{" +
			"\"codigoNormativa\":0," 					+ Constantes.SALTO_LINEA +
			"\"codigoDocumento\":0," + Constantes.SALTO_LINEA +
			"\"texto\":\"string\"," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	public static final String SAMPLE_JSON = "{" +
			"\"codigoNormativa\":null," 					+
			"\"codigoDocumento\":null," 					 +
			"\"texto\":null," +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

    /*Normativa asociada al documento*/
	@Schema(name = "codigoNormativa", description = "codigoNormativa", type = SchemaType.INTEGER, required = false)
    private Long codigoNormativa;

    /*Fichero asociado al documento*/
    @Schema(name = "codigoDocumento", description = "codigoDocumento", type = SchemaType.INTEGER, required = false)
    private Long codigoDocumento;

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

	public DocumentoNormativaFiltro toDocumentoNormativaFiltro() {
		DocumentoNormativaFiltro resultado = new DocumentoNormativaFiltro();

		if (this.texto != null && !this.texto.isEmpty()) {
			resultado.setTexto(texto);
		}
		if (this.codigoNormativa != null) {
			NormativaDTO procedimientoDTO = new NormativaDTO();
			procedimientoDTO.setCodigo(codigoNormativa);
			resultado.setNormativa(procedimientoDTO);
		}

		if (this.codigoDocumento != null) {
			FicheroDTO fichero = new FicheroDTO();
			fichero.setCodigo(codigoDocumento);
			resultado.setDocumento(fichero);
		}

		return resultado;

	}

	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}

	public Long getCodigoNormativa() {
		return codigoNormativa;
	}

	public void setCodigoNormativa(Long codigoNormativa) {
		this.codigoNormativa = codigoNormativa;
	}

	public Long getCodigoDocumento() {
		return codigoDocumento;
	}

	public void setCodigoDocumento(Long codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

}
