package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * El tipo Tipo sexo dto.
 */
@Schema(name = "TipoSexo")
public class TipoSexoDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    @NotEmpty
    @Size(max = 50)
    private String identificador;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Instancia un nuevo Tipo sexo dto.
     */
    public TipoSexoDTO() {
    }

    /**
     * Instancia un nuevo Tipo sexo dto.
     *
     * @param id             id
     * @param identificador  identificador
     */
//todo descripcion
    public TipoSexoDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
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
        TipoSexoDTO that = (TipoSexoDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion);
    }

    @Override
    public String toString() {
        return "TipoSexoDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }

    public int compareTo(TipoSexoDTO data2) {

        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        if (UtilComparador.compareTo(this.getIdentificador(), data2.getIdentificador()) != 0) {
            return UtilComparador.compareTo(this.getIdentificador(), data2.getIdentificador());
        }

        return 0;

    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public Object clone() {
        TipoSexoDTO tipo = new TipoSexoDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        if (this.getDescripcion() != null) {
            tipo.setDescripcion((Literal) this.getDescripcion().clone());
        }
        return tipo;
    }
}
