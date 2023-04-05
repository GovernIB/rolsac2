package es.caib.rolsac2.persistence.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "entiRaiz-sequence", sequenceName = "RS2_ENTIRAIZ_SEQ", allocationSize = 1)
@Table(name = "RS2_ENTIRAIZ",
        indexes = {
            @Index(name = "RS2_ENTIRAIZ_PK_I", columnList = "ENTIRAIZ_COD")
        })
@NamedQueries({
        @NamedQuery(name = JEntidadRaiz.FIND_BY_ID,
        query = "select p from JEntidadRaiz p where p.codigo = :codigo")
})
public class JEntidadRaiz extends BaseEntity{

    public static final String FIND_BY_ID = "EntidadRaiz.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entiRaiz-sequence")
    @Column(name = "ENTIRAIZ_COD", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ENTIRAIZ_CODUA", nullable = false)
    private JUnidadAdministrativa ua;

    @Column(name = "ENTIRAIZ_USER", length = 100, nullable = false)
    private String user;

    @Column(name = "ENTIRAIZ_PWD", length = 100, nullable = false)
    private String pwd;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public JUnidadAdministrativa getUa() {
        return ua;
    }

    public void setUa(JUnidadAdministrativa ua) {
        this.ua = ua;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JEntidadRaiz that = (JEntidadRaiz) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JEntidadRaiz{" +
                "codigo=" + codigo +
                ", ua=" + ua +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
