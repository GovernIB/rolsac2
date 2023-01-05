package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Seccion dto.
 */
@Schema(name = "Seccion")
public class SeccionDTO extends ModelApi {

    private Long codigo;

    private EntidadDTO entidad;

    private String identificador;

    private SeccionDTO padre;

    private Literal nombre;

    /**
     * Instancia una nueva Seccion dto.
     */
    public SeccionDTO(){}

    /**
     * Instancia una nueva Seccion dto.
     *
     * @param codigo  codigo
     */
    public SeccionDTO(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
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
     * Create instance seccion dto.
     *
     * @return  seccion dto
     */
    public static SeccionDTO createInstance() {
        SeccionDTO seccion = new SeccionDTO();
        seccion.setNombre(Literal.createInstance());
        return seccion;
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
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(EntidadDTO entidad) {
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
    public SeccionDTO getPadre() {
        return padre;
    }

    /**
     * Establece padre.
     *
     * @param padre  padre
     */
    public void setPadre(SeccionDTO padre) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeccionDTO that = (SeccionDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "SeccionDTO{" +
                "id=" + codigo +
                '}';
    }

    public int compareTo(final SeccionDTO seccion) {
        if (seccion == null)
            throw new NullPointerException("seccion");

        return Long.compare(this.getCodigo(), seccion.getCodigo());
    }
}
