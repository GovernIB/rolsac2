package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;

/**
 * Filtro de procedimientos.
 */
public class ProcedimientoDocumentoFiltro extends AbstractFiltro {


    /**
     * Codigo
     **/
    private Long codigo;

    /**
     * Orden
     */
    private Integer orden;

    /*Filtro de descripcion y titulo*/
    private String texto;

    /*Fichero asociado al documento*/
    private FicheroDTO documento;

//    /**
//     * Procedimiento
//     **/
//    private ProcedimientoDTO procedimientoDTO;

    @Override
    protected String getDefaultOrder() {
        return "id";
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

//	public ProcedimientoDTO getProcedimientoDTO() {
//		return procedimientoDTO;
//	}
//
//	public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
//		this.procedimientoDTO = procedimientoDTO;
//	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public FicheroDTO getDocumento() {
		return documento;
	}

	public void setDocumento(FicheroDTO documento) {
		this.documento = documento;
	}

	public boolean isRellenoTexto() {
		return texto != null && !texto.isEmpty();
	}

	public boolean isRellenoDocumento() {
		return documento != null;
	}

	public boolean isRellenoCodigo() {
		return codigo != null;
	}
}
