package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades de un ficha enlace.
 *
 * @author Indra
 */
@Schema(name = "FichaEnlace")
public class FichaEnlaceDTO extends ModelApi {
    private Long codigo;

    public Long ficha;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getFicha() {
        return ficha;
    }

    public void setFicha(Long ficha) {
        this.ficha = ficha;
    }

    @Override
    public String toString() {
        return "FichaEnlaceDTO{" +
                "id=" + codigo +
                ", ficha='" + ficha + '\'' +
                '}';
    }
}
