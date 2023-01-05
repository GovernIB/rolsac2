package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The type Plat tramit electronica dto.
 *
 * @author Indra
 */
@Schema(name = "PlatTramitElectronica")
public class PlatTramitElectronicaDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Código entidad
     */

    private EntidadDTO codEntidad;

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

    /**
     * URL acceso
     */
    private Literal urlAcceso;

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
     * Obtiene cod entidad.
     *
     * @return  cod entidad
     */
    public EntidadDTO getCodEntidad() {
        return codEntidad;
    }

    /**
     * Establece cod entidad.
     *
     * @param codEntidad  cod entidad
     */
    public void setCodEntidad(EntidadDTO codEntidad) {
        this.codEntidad = codEntidad;
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

    /**
     * Obtiene url acceso.
     *
     * @return  url acceso
     */
    public Literal getUrlAcceso() {
        return urlAcceso;
    }

    /**
     * Establece url acceso.
     *
     * @param urlAcceso  url acceso
     */
    public void setUrlAcceso(Literal urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlatTramitElectronicaDTO that = (PlatTramitElectronicaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "PlatTramitElectronicaDTO{" + "id=" + codigo + ", codEntidad=" + codEntidad + ", identificador='"
                + identificador + '\'' + ", descripcion='" + descripcion + '\'' + ", urlAcceso='" + urlAcceso
                + '\'' + '}';
    }
}
