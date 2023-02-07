package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Tipo via dto.
 *
 * @author Indra
 */
@Schema(name = "TipoVia")
public class TipoViaDTO extends ModelApi implements Cloneable {

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
     * @param id            id
     * @param identificador identificador
     * @param descripcion   descripcion
     */
    public TipoViaDTO(Long id, String identificador, Literal descripcion) {
        this.codigo = id;
        this.identificador = identificador;
        this.descripcion = descripcion;
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
        TipoViaDTO tipo = new TipoViaDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        if (this.getDescripcion() != null) {
            tipo.setDescripcion((Literal) this.getDescripcion().clone());
        }
        return tipo;
    }

    public int compareTo(TipoViaDTO data2) {

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
