package es.caib.rolsac2.service.model;



/**
 * Clase de negocio equivalente a la entidad Instancia.
 *
 * @author INDRA
 */
public class ProcesoDTO extends ProcesoGridDTO {


  /** Serial version UID. **/
  private static final long serialVersionUID = 1L;


  /** Constructor. **/
  public ProcesoDTO() {
    super();
  }


  /**
   * Devuelve una instancia de instancia.
   *
   * @return
   */
  public static ProcesoDTO createInstance() {
    return new ProcesoDTO();
  }


}
