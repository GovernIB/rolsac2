package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * La clase J Proceso Control
 */

@Entity
@Table(name = "RS2_PROCEX",
        indexes = {
            @Index(name="RS2_PROCEX_PK_I", columnList = "PROCEX_CODIGO")
        }
)
public class JProcesoControl implements Serializable {

  /** Serial version UID. **/
  private static final long serialVersionUID = 1L;

  /**
   * Identificador
   */
  @Id
  @Column(name = "PROCEX_CODIGO", unique = true, nullable = false)
  private String identificador;

  /**
   * Instancia
   */
  @Column(name = "PROCEX_INSTAN", length = 50)
  private String instancia;

  /**
   * Fecha
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "PROCEX_FECHA")
  private Date fecha;

  /**
   * Obtiene identificador.
   *
   * @return identificador
   */
  public String getIdentificador() {
    return identificador;
  }

  /**
   * Establece identificador.
   *
   * @param identificador identificador a establecer
   */
  public void setIdentificador(final String identificador) {
    this.identificador = identificador;
  }

  /**
   * Obtiene instancia.
   *
   * @return instancia
   */
  public String getInstancia() {
    return instancia;
  }

  /**
   * Establece instancia.
   *
   * @param instancia instancia a establecer
   */
  public void setInstancia(final String instancia) {
    this.instancia = instancia;
  }

  /**
   * Obtiene fecha.
   *
   * @return fecha
   */
  public Date getFecha() {
    return fecha;
  }

  /**
   * Establece fecha.
   *
   * @param fecha fecha a establecer
   */
  public void setFecha(final Date fecha) {
    this.fecha = fecha;
  }


}
