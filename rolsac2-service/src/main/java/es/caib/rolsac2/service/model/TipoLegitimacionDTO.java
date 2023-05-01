package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * El tipo Tipo legitimacion dto.
 */
@Schema(name = "TipoLegitimacion")
public class TipoLegitimacionDTO extends ModelApi implements Cloneable {

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
     * Legitimacion
     **/
    private boolean porDefecto;

    /**
     * Instancia un nuevo Tipo legitimacion dto.
     */
    public TipoLegitimacionDTO() {
    }

    public TipoLegitimacionDTO(TipoLegitimacionDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.identificador = otro.identificador;
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
            this.porDefecto = otro.porDefecto;
        }
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

    public boolean isPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(boolean porDefecto) {
        this.porDefecto = porDefecto;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */

    @Override
    public TipoLegitimacionDTO clone() {
        return new TipoLegitimacionDTO(this);
    }

    @Override
    public String toString() {
        return "TipoLegitimacionDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + ", descripcion=" + descripcion + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoLegitimacionDTO that = (TipoLegitimacionDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public int compareTo(TipoLegitimacionDTO data2) {

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
}
