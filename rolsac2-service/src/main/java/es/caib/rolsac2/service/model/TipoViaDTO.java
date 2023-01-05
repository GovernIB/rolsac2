package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Tipo via dto.
 */
@Schema(name = "TipoVia")
public class TipoViaDTO extends ModelApi {
 
    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */

    private String identificador;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Instancia un nuevo Tipo via dto.
     */
    public TipoViaDTO() {
        //Constructor vacio
    }

    /**
     * Instancia un nuevo Tipo via dto.
     *
     * @param id             id
     * @param identificador  identificador
     * @param descripcion    descripcion
     */
    public TipoViaDTO(Long id, String identificador, Literal descripcion) {
        this.codigo = id;
        this.identificador = identificador;
        this.descripcion = descripcion;
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
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoViaDTO that = (TipoViaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion);
    }

    @Override
    public String toString() {
        return "TipoViaDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}
