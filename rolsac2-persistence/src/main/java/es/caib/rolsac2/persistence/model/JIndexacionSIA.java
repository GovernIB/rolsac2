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
@SequenceGenerator(name = "indexacion-sia-sequence", sequenceName = "RS2_IDXSIA_SEQ", allocationSize = 1)
@Table(name = "RS2_IDSIA", indexes = {@Index(name = "RS2_IDXSIA_PK_I", columnList = "ISIA_CODIGO")})
@NamedQueries({@NamedQuery(name = JIndexacionSIA.FIND_BY_ID, query = "select p from JIndexacionSIA p where p.codigo = :id")})
public class JIndexacionSIA extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "IndexacionSia.FIND_BY_ID";

    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indexacion-sequence")
    @Column(name = "ISIA_CODIGO", nullable = false, length = 10)
    private Long codigo;


    /**
     * Tipo
     */
    @Column(name = "ISIA_TIPO", nullable = false, length = 10)
    private String tipo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISIA_CODENT", nullable = false)
    private JEntidad entidad;

    /**
     * Codigo elemento
     **/
    @Column(name = "ISIA_CODELE")
    private Long codElemento;

    /**
     * Fecha creacion.
     */
    @Column(name = "ISIA_FECCRE")
    private Date fechaCreacion;

    /**
     * Fecha intento indexacion.
     */
    @Column(name = "ISIA_FECINT")
    private Date fechaIntentoIndexacion;

    /**
     * Accion.
     */
    @Column(name = "ISIA_ACCION")
    private Integer estado;

    /**
     * Accion.
     */
    @Column(name = "ISIA_EXISTE")
    private Integer existe;

    /**
     * Mensaje error
     */
    @Column(name = "ISIA_MSGERR")
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

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getExiste() {
        return existe;
    }

    public void setExiste(Integer existe) {
        this.existe = existe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JIndexacionSIA)) return false;
        JIndexacionSIA that = (JIndexacionSIA) o;
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
