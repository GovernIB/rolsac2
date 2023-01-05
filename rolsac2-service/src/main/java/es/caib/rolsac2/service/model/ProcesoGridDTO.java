package es.caib.rolsac2.service.model;
import es.caib.rolsac2.service.utils.UtilJSON;
import java.util.List;


/**
 * The type Proceso grid dto.
 */
public class ProcesoGridDTO extends ProcesoBaseDTO {

  /**
   * Identificador del proceso
   */
  private String identificadorProceso;

  /**
   * Cron
   */
  private String cron;

  /**
   * Activo
   */
  private Boolean activo;

  /**
   * Parametros de invocacion
   */
  private List<Propiedad> parametrosInvocacion;

  /**
   * Obtiene identificador proceso.
   *
   * @return  identificador proceso
   */
  public String getIdentificadorProceso() {
    return identificadorProceso;
  }

  /**
   * Establece identificador proceso.
   *
   * @param identificadorProceso  identificador proceso
   */
  public void setIdentificadorProceso(final String identificadorProceso) {
    this.identificadorProceso = identificadorProceso;
  }

  /**
   * Obtiene cron.
   *
   * @return  cron
   */
  public String getCron() {
    return cron;
  }

  /**
   * Establece cron.
   *
   * @param cron  cron
   */
  public void setCron(final String cron) {
    this.cron = cron;
  }

  /**
   * Obtiene activo.
   *
   * @return  activo
   */
  public Boolean getActivo() {
    return activo;
  }

  /**
   * Establece activo.
   *
   * @param activo  activo
   */
  public void setActivo(final Boolean activo) {
    this.activo = activo;
  }

  /**
   * Castea un object[] en ProcesoGrid
   *
   * @param resultado  resultado
   * @return proceso grid dto
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
   * @return parametrosInvocacion parametros invocacion
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
