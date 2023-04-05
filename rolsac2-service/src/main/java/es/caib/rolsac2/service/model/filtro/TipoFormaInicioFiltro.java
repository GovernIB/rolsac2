package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de tipo de forma de inicio
 */
public class TipoFormaInicioFiltro extends AbstractFiltro {

  private static final long serialVersionUID = 4427400478456345693L;
  /**
   * El filtro que hay en el viewTipoFormaInicio
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

  private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public boolean isRellenoCodigo() {
		 return codigo != null ;
	}
}
