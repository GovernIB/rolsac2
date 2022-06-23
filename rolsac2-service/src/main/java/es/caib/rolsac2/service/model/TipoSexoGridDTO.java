package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "TipoSexoGrid")
public class TipoSexoGridDTO {

  private Long id;
  private String identificador;
  private Literal descripcion;

  public TipoSexoGridDTO() {
  }

  public TipoSexoGridDTO(Long id, String identificador) {
    this.id = id;
    this.identificador = identificador;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return "TipoSexoGridDTO{" +
      "id=" + id +
      ", identificador='" + identificador + '\'' +
      ", descripcion=" + descripcion +
      '}';
  }
}
