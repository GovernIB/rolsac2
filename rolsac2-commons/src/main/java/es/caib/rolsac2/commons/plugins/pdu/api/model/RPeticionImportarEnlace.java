package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Esta es una clase modelo para manejar solicitudes de importación de enlaces.
 * Contiene una única propiedad, linkData, que es una instancia de RLinkData.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RPeticionImportarEnlace {

  // Los datos del enlace para la solicitud de importación
  @JsonProperty("linkData")
  private List<RLinkData> linkData;

  /**
   * Devuelve los datos del enlace para la solicitud de importación.
   * @return los datos del enlace
   */
  public List<RLinkData> getLinkData() {
    return linkData;
  }

  /**
   * Establece los datos del enlace para la solicitud de importación.
   * @param linkData los datos del enlace a establecer
   */
  public void setLinkData(List<RLinkData> linkData) {
    this.linkData = linkData;
  }

}
