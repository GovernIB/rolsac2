package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * The type Tema grid dto.
 */
@Schema(name = "Tema")
public class TemaGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     */
    private String entidad;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Tema padre
     */
    private String temaPadre;

    /**
     * Instancia un nuevo Tema grid dto.
     */
    public TemaGridDTO(){}

    /**
     * Obtiene id string.
     *
     * @return  id string
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

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
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene tema padre.
     *
     * @return  tema padre
     */
    public String getTemaPadre() {
        return temaPadre;
    }

    /**
     * Establece tema padre.
     *
     * @param temaPadre  tema padre
     */
    public void setTemaPadre(String temaPadre) {
        this.temaPadre = temaPadre;
    }

}
