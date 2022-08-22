package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades de un documento ficha.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoDocumento")
public class ProcedimientoDocumentoDTO extends ModelApi {
    private Long codigo;

    private Integer fichero;

    private ProcedimientoDTO procedimientoDTO;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer fichero) {
        this.fichero = fichero;
    }

    public ProcedimientoDTO getProcedimientoDTO() {
        return procedimientoDTO;
    }

    public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
        this.procedimientoDTO = procedimientoDTO;
    }

    @Override
    public String toString() {
        return "ProcedimientoDocumentoDTO{" +
                "id=" + codigo +
                ", fichero='" + fichero + '\'' +
                '}';
    }
}
