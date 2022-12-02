package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.orden.ProcesoOrden;

/**
 * ProcesoLog Filtro.
 *
 * @author Indra
 *
 */
public class ProcesoFiltro extends AbstractFiltro {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** Filtro codigo. **/
  private Long codigo;

  /** Idioma. **/
  private String idioma;

  /** Identificador */
  private String texto;


  /**
   * @return the codigo
   */
  public Long getCodigo() {
    return codigo;
  }



  /**
   * @param codigo the codigo to set
   */
  public void setCodigo(final Long codigo) {
    this.codigo = codigo;
  }

  /** Está relleno el código. */
  public boolean isRellenoCodigo() {
    return this.getCodigo() != null;
  }


  /**
   * @return the idioma
   */
  @Override
  public String getIdioma() {
    return idioma;
  }

  /**
   * @param idioma the idioma to set
   */
  @Override
  public void setIdioma(final String idioma) {
    this.idioma = idioma;
  }

  @Override
  public String getDefaultOrder() {
    return ProcesoOrden.CODIGO.toString();
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }
}
