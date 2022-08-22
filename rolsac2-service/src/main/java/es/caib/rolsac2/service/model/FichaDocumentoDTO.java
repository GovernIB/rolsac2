package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades de un documento ficha.
 *
 * @author Indra
 */
@Schema(name = "FichaDocumento")
public class FichaDocumentoDTO extends ModelApi {
    private Long codigo;

    private Integer fichero;

    private FichaDTO ficha;

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

    public FichaDTO getFicha() {
        return ficha;
    }

    public void setFicha(FichaDTO ficha) {
        this.ficha = ficha;
    }

    @Override
    public String toString() {
        return "FichaDocumentoDTO{" +
                "id=" + codigo +
                ", fichero='" + fichero + '\'' +
                '}';
    }
}
