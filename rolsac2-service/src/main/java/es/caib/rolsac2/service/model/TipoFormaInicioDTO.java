package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * El tipo Tipo forma inicio dto.
 *
 * @author Indra
 */
@Schema(name = "TipoFormaInicio")
public class TipoFormaInicioDTO extends ModelApi implements Cloneable {

    private Long codigo;

    /**
     * Identificador
     */
    @NotEmpty
    @Size(max = 50)
    private String identificador;

    /**
     * Descripción
     */
    private Literal descripcion;
    // private Long descripcion;

    /**
     * Instancia un nuevo Tipo forma inicio dto.
     */
    public TipoFormaInicioDTO() {
    }

    /**
     * Instancia un nuevo Tipo forma inicio dto.
     *
     * @param id            id
     * @param identificador identificador
     */
    public TipoFormaInicioDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        TipoFormaInicioDTO tipo = new TipoFormaInicioDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        if (this.getDescripcion() != null) {
            tipo.setDescripcion((Literal) this.getDescripcion().clone());
        }
        return tipo;
    }

    public int compareTo(TipoFormaInicioDTO data2) {

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

    @Override
    public String toString() {
        return "TipoFormaInicioDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoFormaInicioDTO that = (TipoFormaInicioDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
