package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "indexacionPdu-sequence", sequenceName = "RS2_IDPDU_SEQ", allocationSize = 1)
@Table(name = "RS2_IDPDU", indexes = {@Index(name = "RS2_IDPDU_PK_I", columnList = "IPDU_CODIGO")})
@NamedQueries({@NamedQuery(name = JIndexacionPdu.FIND_BY_ID, query = "select i from JIndexacionPdu i where i.codigo = :id")})
public class JIndexacionPdu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "IndexacionPdu.FIND_BY_ID";

    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indexacionPdu-sequence")
    @Column(name = "IPDU_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Tipo.
     */
//    @Column(name = "IPDU_TIPO", length = 3)
//    private String tipo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IPDU_CODENT", nullable = false)
    private JEntidad entidad;

//    /**
//     * Codigo elemento
//     **/
//    @Column(name = "IPDU_CODELE")
//    private Long codElemento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IPDU_CODELE", nullable = false)
    private JProcedimiento procedimiento;

    /**
     * Fecha creacion.
     */
    @Column(name = "IPDU_FECCRE")
    private Date fechaCreacion;

    /**
     * Fecha intento indexacion.
     */
    @Column(name = "IPDU_FECINT")
    private Date fechaIntentoIndexacion;

    /**
     * Accion.
     */
    @Column(name = "IPDU_ACCION")
    private Integer accion;

    /**
     * Mensaje error
     */
    @Column(name = "IPDU_MSGERR")
    private String mensajeError;

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

//    public String getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(String tipo) {
//        this.tipo = tipo;
//    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    public JProcedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaIntentoIndexacion() {
        return fechaIntentoIndexacion;
    }

    public void setFechaIntentoIndexacion(Date fechaIntentoIndexacion) {
        this.fechaIntentoIndexacion = fechaIntentoIndexacion;
    }

    public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JIndexacionPdu)) return false;
        JIndexacionPdu that = (JIndexacionPdu) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JPersonal{" + "id=" + codigo + '}';
    }

}
