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
@SequenceGenerator(name = "indexacion-sequence", sequenceName = "RS2_IDSOLR_SEQ", allocationSize = 1)
@Table(name = "RS2_IDSOLR", indexes = {@Index(name = "RS2_IDSOLR_PK_I", columnList = "ISOL_CODIGO")})
@NamedQueries({@NamedQuery(name = JIndexacion.FIND_BY_ID, query = "select p from JIndexacion p where p.codigo = :id")})
public class JIndexacion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Indexacion.FIND_BY_ID";

    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indexacion-sequence")
    @Column(name = "ISOL_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Tipo.
     */
    @Column(name = "ISOL_TIPO", length = 3)
    private String tipo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISOL_CODENT", nullable = false)
    private JEntidad entidad;

    /**
     * Codigo elemento
     **/
    @Column(name = "ISOL_CODELE")
    private Long codElemento;

    /**
     * Fecha creacion.
     */
    @Column(name = "ISOL_FECCRE")
    private Date fechaCreacion;

    /**
     * Fecha intento indexacion.
     */
    @Column(name = "ISOL_FECINT")
    private Date fechaIntentoIndexacion;

    /**
     * Accion.
     */
    @Column(name = "ISOL_ACCION")
    private Integer accion;

    /**
     * Mensaje error
     */
    @Column(name = "ISOL_MSGERR")
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    public Long getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Long codElemento) {
        this.codElemento = codElemento;
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
        if (this == o)
            return true;
        if (!(o instanceof JIndexacion))
            return false;
        JIndexacion that = (JIndexacion) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JPersonal{" + "id=" + codigo +
                // ", codiSia='" + codiSia + '\'' +
                '}';
    }

}
