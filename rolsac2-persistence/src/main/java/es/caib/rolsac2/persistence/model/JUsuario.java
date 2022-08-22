package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "usuario-sequence", sequenceName = "RS2_USER_SEQ", allocationSize = 1)
@Table(name = "RS2_USER", indexes = {@Index(name = "RS2_USER_PK_I", columnList = "USER_CODIGO")})
@NamedQueries({@NamedQuery(name = JUsuario.FIND_BY_ID, query = "select p from JUsuario p where p.codigo = :codigo"),
        @NamedQuery(name = JUsuario.COUNT_BY_IDENTIFICADOR,
                query = "select count(p) from JUsuario p where p.identificador = :identificador")

})
public class JUsuario extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_ID = "Usuario.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "Usuario.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario-sequence")
    @Column(name = "USER_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "USER_USER", nullable = false, length = 100)
    private String identificador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<JUsuarioUnidadAdministrativa> usuarioUnidadAdministrativa;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad userCodenti) {
        this.entidad = userCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String userUser) {
        this.identificador = userUser;
    }

    public List<JUsuarioUnidadAdministrativa> getUsuarioUnidadAdministrativa() {
        return usuarioUnidadAdministrativa;
    }

    public void setUsuarioUnidadAdministrativa(List<JUsuarioUnidadAdministrativa> usuarioUnidadAdministrativa) {
        this.usuarioUnidadAdministrativa = usuarioUnidadAdministrativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JUsuario jUsuario = (JUsuario) o;
        return Objects.equals(codigo, jUsuario.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JUsuario{" + "id=" + codigo + ", entidad=" + entidad + ", identificador='" + identificador + '\'' + '}';
    }
}
