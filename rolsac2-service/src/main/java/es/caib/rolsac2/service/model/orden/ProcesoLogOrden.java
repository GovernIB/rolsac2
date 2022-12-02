package es.caib.rolsac2.service.model.orden;

/** Campos orden. **/
public enum ProcesoLogOrden implements AbstractOrden {

  CODIGO("PL.codigo"), DESCRIPCION("TRAD.descripcion"), ESTADO("PL.estado"), FECHA_INICIO("PL.fechaInicio");

  /** Valor. **/
  private final String valor;

  /** Constructor. */
  private ProcesoLogOrden(final String iValor) {
    valor = iValor;
  }

  /**
   * Obtiene enumerado a partir de texto.
   *
   * @param valor
   * @return
   */
  public static ProcesoLogOrden fromString(final String valor) {
    ProcesoLogOrden tipo = null;
    if (valor != null) {
      for (final ProcesoLogOrden type : ProcesoLogOrden.values()) {
        if (type.toString().contentEquals(valor)) {
          tipo = type;
          break;
        }
      }
    }
    return tipo;
  }

  @Override
  public String toString() {
    return valor;
  }
}
