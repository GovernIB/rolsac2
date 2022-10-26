package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "Seccion")
public class SeccionDTO extends ModelApi {

    private Long codigo;

    private EntidadDTO entidad;

    private String identificador;

    private SeccionDTO padre;

    private Literal nombre;

    public SeccionDTO(){}

    public SeccionDTO(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public static SeccionDTO createInstance() {
        SeccionDTO seccion = new SeccionDTO();
        seccion.setNombre(Literal.createInstance());
        return seccion;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public SeccionDTO getPadre() {
        return padre;
    }

    public void setPadre(SeccionDTO padre) {
        this.padre = padre;
    }

    public Literal getNombre() {
        return nombre;
    }

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
