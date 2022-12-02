package es.caib.rolsac2.service.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Permite establecer una lista de propiedades mediante una lista de propiedad/valor.
 *
 * @author Indra
 *
 */
public final class ListaPropiedades extends ModelApi {

  /** Serial version UID. **/
  private static final long serialVersionUID = 1L;

  /**
   * Map con los detalles del error.
   */
  private final Map<String, String> propiedadesError = new LinkedHashMap<>();

  /**
   * Añade un detalle de error.
   *
   * @param propiedad Propiedad
   * @param valor Parámetro valor
   */
  public void addPropiedad(final String propiedad, final String valor) {
    propiedadesError.put(propiedad, valor);
  }

  /**
   * Obtiene detalles del error como un Map<String,String>.
   *
   * @return Detalles error como un Map<String,String>
   */
  public Map<String, String> getPropiedades() {
    return this.propiedadesError;
  }

  /**
   * Método para añadir nuevas propiedades a una ListaPropiedades pasándole otra ListaPropiedades.
   *
   * @param lp Parámetro lp. ListaPropiedades a añadir
   */
  public void addPropiedades(final Map<String, String> lp) {
    if (lp != null) {
      for (final Map.Entry<String, String> propiedad : lp.entrySet()) {
        this.addPropiedad(propiedad.getKey(), propiedad.getValue());
      }
    }
  }

  /**
   * Método para añadir nuevas propiedades a una ListaPropiedades pasándole otra ListaPropiedades.
   *
   * @param lp Parámetro lp. ListaPropiedades a añadir
   */
  public void addPropiedades(final ListaPropiedades lp) {
    final Map<String, String> nueva = lp.getPropiedades();
    this.propiedadesError.putAll(nueva);
  }

  /**
   * Obtiene valor propiedad.
   *
   * @param propiedad Nombre propiedad
   * @return valor propiedad
   */
  public String getPropiedad(final String propiedad) {
    return this.getPropiedades().get(propiedad);
  }
}
