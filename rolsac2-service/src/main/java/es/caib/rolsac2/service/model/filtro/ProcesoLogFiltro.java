package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.orden.ProcesoLogOrden;
import es.caib.rolsac2.service.model.types.TypeEstadoProceso;

import java.util.Date;

/**
 * ProcesoLog Filtro.
 *
 * @author Indra
 *
 */
public class ProcesoLogFiltro extends AbstractFiltro {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** Filtro codigo. **/
  private Long codigo;

  /** Idioma. **/
  private String idioma;

  /** Proceso. **/
  private ProcesoGridDTO proceso;

  private Date fechaInicio;
  private Date fechaFin;

  /** Filtro estado. **/
  private TypeEstadoProceso estadoProceso;

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

  public ProcesoGridDTO getProceso() {
    return proceso;
  }

  public void setProceso(final ProcesoGridDTO proceso) {
    this.proceso = proceso;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(final Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }


  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(final Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public TypeEstadoProceso getEstadoProceso() {
    return estadoProceso;
  }

  public void setEstadoProceso(final TypeEstadoProceso estadoProceso) {
    this.estadoProceso = estadoProceso;
  }

  public String getTexto() { return texto; }

  public void setTexto(String texto) { this.texto = texto; }

  /** Está relleno el código. */
  public boolean isRellenoCodigo() {
    return this.getCodigo() != null;
  }

  /** Está relleno los Fecha Fin. */
  public boolean isRellenoFechaFin() {
    return this.getFechaFin() != null;
  }

  /** Está relleno los Fecha Fin. */
  public boolean isRellenoFechaInicio() {
    return this.getFechaInicio() != null;
  }

  /** Está relleno proceso. */
  public boolean isRellenoProceso() {
    return this.getProceso() != null && this.getProceso().getCodigo() != null;
  }

  /** Está relleno el Estado Proceso. */
  public boolean isRellenoEstadoProceso() {
    return this.getEstadoProceso() != null && this.getEstadoProceso() != TypeEstadoProceso.VACIO;
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
    return ProcesoLogOrden.FECHA_INICIO.toString();
  }
}
