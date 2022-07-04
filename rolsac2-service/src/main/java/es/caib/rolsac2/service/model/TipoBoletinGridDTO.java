package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "TipoBoletinGrid")
public class TipoBoletinGridDTO {

  private Long id;
  private String identificador;
  private String nombre;
  private String url;

  public TipoBoletinGridDTO() {
  }

  public TipoBoletinGridDTO(Long id, String identificador, String nombre, String url) {
    this.id = id;
    this.identificador = identificador;
    this.nombre = nombre;
    this.url = url;
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

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "TipoBoletinGridDTO{" +
      "id=" + id +
      ", identificador='" + identificador + '\'' +
      ", nombre='" + nombre + '\'' +
      ", url='" + url + '\'' +
      '}';
  }
}