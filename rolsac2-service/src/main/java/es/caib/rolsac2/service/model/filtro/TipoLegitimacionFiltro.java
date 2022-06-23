package es.caib.rolsac2.service.model.filtro;

public class TipoLegitimacionFiltro extends AbstractFiltro {
  /**
   * El filtro que hay en el tipoLegit
   **/
  private String texto;

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  /**
   * Esta relleno el texto
   *
   * @return
   */
  public boolean isRellenoTexto() {
    return texto != null && !texto.isEmpty();
  }

  @Override
  protected String getDefaultOrder() {
    return "id";
  }
}
