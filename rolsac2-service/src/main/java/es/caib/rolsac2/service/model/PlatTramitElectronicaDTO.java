package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 *
 * @author Indra
 */
@Schema(name = "PlatTramitElectronica")
public class PlatTramitElectronicaDTO {

    private Long id;

    /**
     * Código entidad
     */

    @NotEmpty
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

    @NotEmpty
    @Size(max = 255)
    private String descripcion;

    /**
     * URL acceso
     */

    @NotEmpty
    @Size(max = 500)
    private String urlAcceso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntidadDTO getCodEntidad() {
        return codEntidad;
    }

    public void setCodEntidad(EntidadDTO codEntidad) {
        this.codEntidad = codEntidad;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlAcceso() {
        return urlAcceso;
    }

    public void setUrlAcceso(String urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlatTramitElectronicaDTO that = (PlatTramitElectronicaDTO) o;
        return id.equals(that.id) && codEntidad.equals(that.codEntidad) && identificador.equals(that.identificador)
                        && descripcion.equals(that.descripcion) && urlAcceso.equals(that.urlAcceso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codEntidad, identificador, descripcion, urlAcceso);
    }

    @Override
    public String toString() {
        return "PlatTramitElectronicaDTO{" + "id=" + id + ", codEntidad=" + codEntidad + ", identificador='"
                        + identificador + '\'' + ", descripcion='" + descripcion + '\'' + ", urlAcceso='" + urlAcceso
                        + '\'' + '}';
    }
}
