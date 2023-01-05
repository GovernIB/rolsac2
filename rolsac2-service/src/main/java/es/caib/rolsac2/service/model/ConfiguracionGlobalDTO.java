package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Datos de una Configuración Global
 *
 * @author jrodrigof
 */
@Schema(name = "ConfiguracionGlobal")
public class ConfiguracionGlobalDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Propiedad
     */
    private String propiedad;

    /**
     * Valor
     */
    private String valor;

    /**
     * Descripcion
     */
    private String descripcion;

    /**
     * noModificable
     */
    private Boolean noModificable = false;

    /**
     * Instancia una nueva Configuracion global dto.
     */
    public ConfiguracionGlobalDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }
    
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instancia una nueva Configuracion global dto.
     *
     * @param id el id
     */
    public ConfiguracionGlobalDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return el codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo el codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene propiedad.
     *
     * @return la propiedad
     */
    public String getPropiedad() {
        return propiedad;
    }

    /**
     * Establece propiedad.
     *
     * @param propiedad la propiedad
     */
    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    /**
     * Obtiene valor.
     *
     * @return el valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece valor.
     *
     * @param valor el valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiene descripcion.
     *
     * @return el descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion la descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene no modificable.
     *
     * @return  no modificable
     */
    public Boolean getNoModificable() {
        return noModificable;
    }

    /**
     * Establece no modificable.
     *
     * @param noModificable  no modificable
     */
    public void setNoModificable(Boolean noModificable) {
        this.noModificable = noModificable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConfiguracionGlobalDTO that = (ConfiguracionGlobalDTO) o;
        return codigo.equals(that.codigo) && propiedad.equals(that.propiedad) && valor.equals(that.valor)
                && descripcion.equals(that.descripcion) && noModificable.equals(that.noModificable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, propiedad, valor, descripcion, noModificable);
    }
}
