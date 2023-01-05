package es.caib.rolsac2.service.model;

import java.util.Objects;

/**
 * Tiene la información básica, el código (normalmente Long) y puede que algo de información de descripción.
 *
 * @author Indra
 */
public class ProcesoBaseDTO extends ModelApi implements Comparable<ProcesoBaseDTO> {


  /** Codigo. ***/
  private Long codigo;

  /**
   * Entidad
   */
  private EntidadDTO entidad;

  /**
   * Descripcion
   */
  private String descripcion;

  /**
   * Constructor.
   */
  public ProcesoBaseDTO() {
    super();
  }

  /**
   * Obtiene codigo.
   *
   * @return  codigo
   */
  public Long getCodigo() {
    return codigo;
  }

  /**
   * Establece codigo.
   *
   * @param codigo  codigo to set
   */
  public void setCodigo(final Long codigo) {
    this.codigo = codigo;
  }

  /**
   * Obtiene entidad.
   *
   * @return  entidad
   */
  public EntidadDTO getEntidad() { return entidad; }

  /**
   * Establece entidad.
   *
   * @param entidad  entidad
   */
  public void setEntidad(EntidadDTO entidad) { this.entidad = entidad; }

  /**
   * Obtiene descripcion.
   *
   * @return  descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * Establece descripcion.
   *
   * @param descripcion  descripcion
   */
  public void setDescripcion(final String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public int compareTo(final ProcesoBaseDTO o) {
    return this.getCodigo().compareTo(o.getCodigo());
  }

  /**
   * Equals.
   */
  @Override
  public boolean equals(final Object objeto) {
    boolean retorno;
    if (objeto == null) {
      retorno = false;
    } else if (!(objeto instanceof ProcesoBaseDTO)) {
      retorno = false;
    } else {
      final ProcesoBaseDTO apunte = (ProcesoBaseDTO) objeto;
      if (apunte.getCodigo() == null || this.getCodigo() == null) {
        retorno = false;
      } else {
        retorno = apunte.getCodigo().compareTo(this.getCodigo()) == 0;
      }
    }
    return retorno;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCodigo());
  }
}
