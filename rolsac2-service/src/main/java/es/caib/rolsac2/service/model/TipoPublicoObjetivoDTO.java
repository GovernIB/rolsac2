package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un TipoPublicoObjetivo.
 *
 * @author jsegovia
 */
@Schema(name = "TipoPublicoObjetivo")
public class TipoPublicoObjetivoDTO extends ModelApi {

    private Long codigo;
    private String identificador;
    private Literal descripcion;

    public TipoPublicoObjetivoDTO() {
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

    public TipoPublicoObjetivoDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoNormativaDTO{" +
                "id=" + codigo +
                ", identificador=" + identificador +
                ", descripcion=" + descripcion.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoPublicoObjetivoDTO that = (TipoPublicoObjetivoDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador, that.identificador) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion);
    }
}
