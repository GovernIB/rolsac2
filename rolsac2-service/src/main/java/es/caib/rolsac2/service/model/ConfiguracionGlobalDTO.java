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

  private Long id;

  private String propiedad;

  private String valor;

  private String descripcion;

  private Boolean noModificable = false;

  public ConfiguracionGlobalDTO() {}

  /**
   * Estos dos metodos se necesitan para el datatable y el rowKey
   *
   * @return the codigo
   */
  public String getIdString() {
    if (id == null) {
      return null;
    } else {
      return String.valueOf(id);
    }
  }

  /**
   * @param idString the codigo to set
   */
  public void setIdString(final String idString) {
    if (idString == null) {
      this.id = null;
    } else {
      this.id = Long.valueOf(idString);
    }
  }

  public ConfiguracionGlobalDTO(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    ConfiguracionGlobalDTO that = (ConfiguracionGlobalDTO) o;
    return id.equals(that.id) && propiedad.equals(that.propiedad) && valor.equals(that.valor)
        && descripcion.equals(that.descripcion) && noModificable.equals(that.noModificable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, propiedad, valor, descripcion, noModificable);
  }
}
