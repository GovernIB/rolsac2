package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades de un documento ficha.
 *
 * @author Indra
 */
@Schema(name = "FichaDocumento")
public class FichaDocumentoDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Fichero
     */
    private Integer fichero;

    /**
     * Ficha
     */
    private FichaDTO ficha;

    /**
     * Obtiene codigo.
     *
     * @return el codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo el codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene fichero.
     *
     * @return el fichero
     */
    public Integer getFichero() {
        return fichero;
    }

    /**
     * Establece fichero.
     *
     * @param fichero el fichero
     */
    public void setFichero(Integer fichero) {
        this.fichero = fichero;
    }

    /**
     * Obtiene ficha.
     *
     * @return la ficha
     */
    public FichaDTO getFicha() {
        return ficha;
    }

    /**
     * Establece ficha.
     *
     * @param ficha la ficha
     */
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
