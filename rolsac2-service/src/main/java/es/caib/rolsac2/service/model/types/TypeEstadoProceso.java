package es.caib.rolsac2.service.model.types;

/**
 * Enumerado de tipo estado de la tabla SIAC_PX_PROCES, cuyo valor es el campo
 * Estado (combo con  enum denominado TypeEstadoProceso para vacío, “Correcto”, “Alerta”, “Error”
 *
 * @author Indra
 *
 */
public enum TypeEstadoProceso {

  VACIO("V"), CORRECTO("C"), ALERTA("A"), ERROR("E");

  /** Label. **/
  private String valor;

  /** Constructor. **/
  private TypeEstadoProceso(final String label) {
    this.valor = label;
  }

  /**
   * Obtiene enumerado a partir de texto.
   *
   * @param
   * @return
   */
  public static TypeEstadoProceso fromString(final String label) {
    TypeEstadoProceso tipo = null;
    for (final TypeEstadoProceso type : TypeEstadoProceso.values()) {
      if (type.toString().equals(label)) {
        tipo = type;
        break;
      }
    }
    return tipo;
  }

  /**
   * Convierte enum a String
   */
  @Override
  public String toString() {
    return valor;
  }
}
