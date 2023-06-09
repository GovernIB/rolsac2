package es.caib.rolsac2.service.model.types;

/**
 * Enumerado de tipo estado de la tabla SIAC_PX_PROCES, cuyo valor es el campo
 * Estado (combo con  enum denominado TypeEstadoProceso para vacío, “Correcto”, “Alerta”, “Error”
 *
 * @author Indra
 *
 */
public enum TypeAccionAuditoria {

  ALTA("A"), BAJA("B"), MODIFICACION("M");

  /** Label. **/
  private String valor;

  /** Constructor. **/
  private TypeAccionAuditoria(final String label) {
    this.valor = label;
  }

  /**
   * Obtiene enumerado a partir de texto.
   *
   * @param
   * @return
   */
  public static TypeAccionAuditoria fromString(final String label) {
    TypeAccionAuditoria tipo = null;
    for (final TypeAccionAuditoria type : TypeAccionAuditoria.values()) {
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
