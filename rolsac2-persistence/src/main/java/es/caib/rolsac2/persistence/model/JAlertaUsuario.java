package es.caib.rolsac2.persistence.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * La clase JAlertaUsuario
 */
@Entity
@SequenceGenerator(name = "alerta-usuario-sequence", sequenceName = "RS2_ALERUSU_SEQ", allocationSize = 1)
@Table(name = "RS2_ALERUSU", indexes = {@Index(name = "RS2_ALERUSU_PK_I", columnList = "ALUS_CODIGO")})
@NamedQueries({@NamedQuery(name = JAlertaUsuario.FIND_BY_ID, query = "select p from JAlertaUsuario p where p.codigo = :id")})
public class JAlertaUsuario extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "AlertaUsuario.FIND_BY_ID";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta-usuario-sequence")
    @Column(name = "ALUS_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Entidad
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ALUS_CODALE", nullable = false)
    private JAlerta alerta;

    /**
     * Identificador
     */
    @Column(name = "ALUS_CODUSU", nullable = false)
    private String usuario;

    /**
     * Fecha
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ALUS_FECHA")
    private Date fecha;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public JAlerta getAlerta() {
        return alerta;
    }

    public void setAlerta(JAlerta alerta) {
        this.alerta = alerta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JAlertaUsuario JAlertaUsuario = (JAlertaUsuario) o;
        return codigo.equals(JAlertaUsuario.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JAlertaUsuario{" + "codigo=" + codigo + ", alerta=" + alerta + ", usuario='" + usuario + '\'' + '}';
    }
}