package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Datos del Grid de una Configuración Global
 *
 * @author jrodrigof
 */
@Schema(name = "ConfiguracionGlobalGrid")
public class ConfiguracionGlobalGridDTO extends ModelApi {

    private Long codigo;

    private String propiedad;

    private String valor;

    private String descripcion;

    private Boolean noModificable = false;

    public ConfiguracionGlobalGridDTO() {
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

    public ConfiguracionGlobalGridDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getNoModificable() {
        return noModificable;
    }

    public void setNoModificable(Boolean noModificable) {
        this.noModificable = noModificable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConfiguracionGlobalGridDTO that = (ConfiguracionGlobalGridDTO) o;
        return codigo.equals(that.codigo) && propiedad.equals(that.propiedad) && valor.equals(that.valor)
                && descripcion.equals(that.descripcion) && noModificable.equals(that.noModificable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, propiedad, valor, descripcion, noModificable);
    }

    @Override
    public String toString() {
        return "ConfiguracionGlobalGridDTO{" + "id=" + codigo + ", propiedad='" + propiedad + '\'' + ", valor='" + valor + '\''
                + ", descripcion='" + descripcion + '\'' + ", noModificable=" + noModificable + '}';
    }
}
