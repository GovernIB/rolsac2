package es.caib.rolsac2.service.model;

import java.util.Objects;

/**
 * Tiene la información básica, el código (normalmente Long) y puede que algo de información de descripción.
 *
 * @author Indra
 *
 */
public class ProcesoBaseDTO extends ModelApi implements Comparable<ProcesoBaseDTO> {


  /** Codigo. ***/
  private Long codigo;

  private EntidadDTO entidad;

  private String descripcion;

  /**
   * Constructor.
   */
  public ProcesoBaseDTO() {
    super();
  }

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

  public EntidadDTO getEntidad() { return entidad; }

  public void setEntidad(EntidadDTO entidad) { this.entidad = entidad; }

  public String getDescripcion() {
    return descripcion;
  }

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
