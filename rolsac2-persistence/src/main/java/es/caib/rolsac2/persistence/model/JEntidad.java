package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JEntidadTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
                query = "select p from JEntidad p where p.codigo = :id"),
        @NamedQuery(name = JEntidad.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JEntidad p where lower(p.identificador) like :identificador")
})
public class JEntidad extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "JEntidad.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "JEntidad.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-entidad-sequence")
    @Column(name = "ENTI_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @Column(name = "ENTI_IDENTI", length = 50, nullable = false)
    private String identificador;

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

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "entidad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JEntidadTraduccion> descripcion;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
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

    public List<JEntidadTraduccion> getDescripcion() {
        return descripcion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion(String idioma) {
        if (descripcion == null || descripcion.isEmpty()) {
            return "";
        }
        for (JEntidadTraduccion trad : this.descripcion) {
            if (trad.getIdioma() != null && idioma.equalsIgnoreCase(idioma)) {
                return trad.getDescripcion();
            }
        }
        return "";
    }

    public void setDescripcion(List<JEntidadTraduccion> descripcion) {
        if (this.descripcion == null || this.descripcion.isEmpty()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion.addAll(descripcion);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JEntidad))
            return false;
        JEntidad jEntidad = (JEntidad) o;
        return Objects.equals(codigo, jEntidad.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JEntidad{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                ", logo=" + logo +
                '}';
    }
}