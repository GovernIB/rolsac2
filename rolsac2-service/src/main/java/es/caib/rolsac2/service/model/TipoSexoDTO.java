package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(name = "TipoSexo")
public class TipoSexoDTO {

  private Long id;
  @NotEmpty
  @Size(max = 50)
  private String identificador;
  private Literal descripcion;

  public TipoSexoDTO() {
  }

  //todo descripcion
  public TipoSexoDTO(Long id, String identificador) {
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
    return "TipoSexoDTO{" +
      "id=" + id +
      ", identificador='" + identificador + '\'' +
      ", descripcion=" + descripcion +
      '}';
  }
}
