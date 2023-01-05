package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades de un ficha enlace.
 *
 * @author Indra
 */
@Schema(name = "FichaEnlace")
public class FichaEnlaceDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Ficha.
     */
    public Long ficha;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene ficha.
     *
     * @return  ficha
     */
    public Long getFicha() {
        return ficha;
    }

    /**
     * Establece ficha.
     *
     * @param ficha  ficha
     */
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
