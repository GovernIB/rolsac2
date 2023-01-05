package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

/**
 * El tipo Seccion grid dto.
 */
@Schema(name = "Seccion")
public class SeccionGridDTO extends ModelApi {

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
     * Padre
     */
    private Literal padre;

    /**
     * Nombre
     */
    private Literal nombre;

    /**
     * Instancia una nueva Seccion grid dto.
     */
    public SeccionGridDTO(){}

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
     * Obtiene padre.
     *
     * @return  padre
     */
    public Literal getPadre() {
        return padre;
    }

    /**
     * Establece padre.
     *
     * @param padre  padre
     */
    public void setPadre(Literal padre) {
        this.padre = padre;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }
}
