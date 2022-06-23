package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 *
 * @author Indra
 */
@Schema(name = "TipoTramitacionGrid")
public class TipoTramitacionGridDTO {

  private Long id;

  /**
   * Tramitación presencial
   */
  private boolean tramitPresencial;

  /**
   * Tramitación electrónica
   */
  private boolean tramitElectronica;

  /**
   * URL tramitación
   */
  @NotEmpty
  @Size(max = 500)
  private String urlTramitacion;

  /**
   * Código plataforma tramitación
   */
  @NotNull
//  private Long codPlatTramitacion;
  private String codPlatTramitacion;

  /**
   * Trámite Id
   */
  @NotEmpty
  @Size(max = 50)
  private String tramiteId;

  /**
   * Trámite Versión
   */
  @NotNull
  private Integer tramiteVersion;

  /**
   * Trámite parámetros
   */
  @NotEmpty
  @Size(max = 500)
  private String tramiteParametros;

  public TipoTramitacionGridDTO() {}

  public TipoTramitacionGridDTO(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isTramitPresencial() {
    return tramitPresencial;
  }

  public void setTramitPresencial(boolean tramitPresencial) {
    this.tramitPresencial = tramitPresencial;
  }

  public boolean isTramitElectronica() {
    return tramitElectronica;
  }

  public void setTramitElectronica(boolean tramitElectronica) {
    this.tramitElectronica = tramitElectronica;
  }

  public String getUrlTramitacion() {
    return urlTramitacion;
  }

  public void setUrlTramitacion(String urlTramitacion) {
    this.urlTramitacion = urlTramitacion;
  }

  public String getCodPlatTramitacion() {
    return codPlatTramitacion;
  }

  public void setCodPlatTramitacion(String codPlatTramitacion) {
    this.codPlatTramitacion = codPlatTramitacion;
  }

//  public Long getCodPlatTramitacion() {
//    return codPlatTramitacion;
//  }
//
//  public void setCodPlatTramitacion(Long codPlatTramitacion) {
//    this.codPlatTramitacion = codPlatTramitacion;
//  }

  public String getTramiteId() {
    return tramiteId;
  }

  public void setTramiteId(String tramiteId) {
    this.tramiteId = tramiteId;
  }

  public Integer getTramiteVersion() {
    return tramiteVersion;
  }

  public void setTramiteVersion(Integer tramiteVersion) {
    this.tramiteVersion = tramiteVersion;
  }

  public String getTramiteParametros() {
    return tramiteParametros;
  }

  public void setTramiteParametros(String tramiteParametros) {
    this.tramiteParametros = tramiteParametros;
  }

  @Override
  public String toString() {
    return "TipoTramitacionDTO{" + "id=" + id + ", tramitPresencial=" + tramitPresencial + ", tramitElectronica="
        + tramitElectronica + ", urlTramitacion='" + urlTramitacion + '\'' + ", codPlatTramitacion="
        + codPlatTramitacion + ", tramiteId='" + tramiteId + '\'' + ", tramiteVersion=" + tramiteVersion
        + ", tramiteParametros='" + tramiteParametros + '\'' + '}';
  }
}
