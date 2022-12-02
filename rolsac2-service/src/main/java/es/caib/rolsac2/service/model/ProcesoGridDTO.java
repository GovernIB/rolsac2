package es.caib.rolsac2.service.model;
import es.caib.rolsac2.service.utils.UtilJSON;
import java.util.List;


public class ProcesoGridDTO extends ProcesoBaseDTO {

  private String identificadorProceso;

  private String cron;

  private Boolean activo;

  private List<Propiedad> parametrosInvocacion;

  public String getIdentificadorProceso() {
    return identificadorProceso;
  }

  public void setIdentificadorProceso(final String identificadorProceso) {
    this.identificadorProceso = identificadorProceso;
  }

  public String getCron() {
    return cron;
  }

  public void setCron(final String cron) {
    this.cron = cron;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(final Boolean activo) {
    this.activo = activo;
  }

  /**
   * Castea un object[] en ProcesoGrid
   *
   * @param resultado
   * @param
   * @return
   */
  public static ProcesoGridDTO cast(final Object[] resultado) {
    final ProcesoGridDTO proceso = new ProcesoGridDTO();
    proceso.setCodigo(Long.valueOf(resultado[0].toString()));
    proceso.setDescripcion(resultado[1].toString());
    proceso.setIdentificadorProceso(resultado[2].toString());
    proceso.setCron((resultado[3] == null) ? "" : resultado[3].toString());
    proceso.setActivo(Boolean.valueOf(resultado[4].toString()));
    proceso.setParametrosInvocacion((resultado[5] == null) ? null : (List<Propiedad>) UtilJSON.fromListJSON(resultado[5].toString(), Propiedad.class));
    return proceso;
  }

  /**
   * Obtiene parametrosInvocacion.
   *
   * @return parametrosInvocacion
   */
  public List<Propiedad> getParametrosInvocacion() {
    return parametrosInvocacion;
  }

  /**
   * Establece parametrosInvocacion.
   *
   * @param parametrosInvocacion parametrosInvocacion a establecer
   */
  public void setParametrosInvocacion(final List<Propiedad> parametrosInvocacion) {
    this.parametrosInvocacion = parametrosInvocacion;
  }


}
