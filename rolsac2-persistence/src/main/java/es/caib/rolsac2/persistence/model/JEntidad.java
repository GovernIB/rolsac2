package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * Representacion de una Entidad. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author jsegovia
 */
@Entity
@SequenceGenerator(name = "tipo-entidad-sequence", sequenceName = "RS2_ENTIDA_SEQ", allocationSize = 1)
@Table(name = "RS2_ENTIDA",
        indexes = {
                @Index(name = "RS2_ENTIDA_PK", columnList = "ENTI_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JEntidad.FIND_BY_ID,
                query = "select p from JEntidad p where p.id = :id")
})
public class JEntidad extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "Entidad.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-entidad-sequence")
    @Column(name = "ENTI_CODIGO", nullable = false, length = 10)
    private Long id;

    @Column(name = "ENTI_DIR3", length = 20, nullable = false)
    private String dir3;

    @Column(name = "ENTI_ACTIVA", nullable = false)
    private Boolean activa;

    @Column(name = "ROLE_ROLADE", nullable = false, length = 100)
    private String rolAdmin;

    @Column(name = "ENTI_ROLADC", nullable = false, length = 100)
    private String rolAdminContenido;

    @Column(name = "ENTI_ROLGES", nullable = false, length = 100)
    private String rolGestor;

    @Column(name = "ENTI_ROLINF", nullable = false, length = 100)
    private String rolInformador;

    @Column(name = "ENTI_LOGO", nullable = false, length = 10)
    private Long logo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDir3() {
        return dir3;
    }

    public void setDir3(String dir3) {
        this.dir3 = dir3;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getRolAdmin() {
        return rolAdmin;
    }

    public void setRolAdmin(String rolAdmin) {
        this.rolAdmin = rolAdmin;
    }

    public String getRolAdminContenido() {
        return rolAdminContenido;
    }

    public void setRolAdminContenido(String rolAdminContenido) {
        this.rolAdminContenido = rolAdminContenido;
    }

    public String getRolGestor() {
        return rolGestor;
    }

    public void setRolGestor(String rolGestor) {
        this.rolGestor = rolGestor;
    }

    public String getRolInformador() {
        return rolInformador;
    }

    public void setRolInformador(String rolInformador) {
        this.rolInformador = rolInformador;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "JEntidad{" +
                "id=" + id +
                ", dir3='" + dir3 + '\'' +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                ", logo=" + logo +
                '}';
    }
}