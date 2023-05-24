package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JUsuarioTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * La clase J usuario.
 */
@Entity
@SequenceGenerator(name = "usuario-sequence", sequenceName = "RS2_USER_SEQ", allocationSize = 1)
@Table(name = "RS2_USER", indexes = {@Index(name = "RS2_USER_PK_I", columnList = "USER_CODIGO")})
@NamedQueries({@NamedQuery(name = JUsuario.FIND_BY_ID, query = "select p from JUsuario p where p.codigo = :codigo"), @NamedQuery(name = JUsuario.COUNT_BY_IDENTIFICADOR, query = "select count(p) from JUsuario p where p.identificador = :identificador"), @NamedQuery(name = JUsuario.FIND_BY_IDENTIFICADOR, query = "select p from JUsuario p where p.identificador = :identificador")

})
public class JUsuario extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Usuario.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "Usuario.COUNT_BY_IDENTIFICADOR";

    /**
     * La consulta FIND_BY_IDENTIFICADOR.
     */
    public static final String FIND_BY_IDENTIFICADOR = "Usuario.FIND_BY_IDENTIFICADOR";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario-sequence")
    @Column(name = "USER_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Identificador
     */
    @Column(name = "USER_USER", nullable = false, length = 100)
    private String identificador;

    /**
     * Nombre
     */
    @Column(name = "USER_NOMBRE", nullable = false)
    private String nombre;

    /**
     * Email
     */
    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JUsuarioTraduccion> traducciones;


    /**
     * Unidades administrativas
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RS2_USERUA", joinColumns = {@JoinColumn(name = "UAUS_CODUSER")}, inverseJoinColumns = {@JoinColumn(name = "UAUS_CODUA")})
    private Set<JUnidadAdministrativa> unidadesAdministrativas;

    /**
     * Entidades
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RS2_USENTI", joinColumns = {@JoinColumn(name = "USEN_CODUSER")}, inverseJoinColumns = {@JoinColumn(name = "USEN_CODENTI")})
    private Set<JEntidad> entidades;

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
     * @param userUser user user
     */
    public void setIdentificador(String userUser) {
        this.identificador = userUser;
    }

    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene traducciones.
     *
     * @return traducciones
     */
    public List<JUsuarioTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JUsuarioTraduccion> traducciones) {
        this.traducciones = traducciones;
    }

    /**
     * Obtiene unidades administrativas.
     *
     * @return unidades administrativas
     */
    public Set<JUnidadAdministrativa> getUnidadesAdministrativas() {
        return unidadesAdministrativas;
    }

    /**
     * Establece unidades administrativas.
     *
     * @param unidadesAdministrativas unidades administrativas
     */
    public void setUnidadesAdministrativas(Set<JUnidadAdministrativa> unidadesAdministrativas) {
        this.unidadesAdministrativas = unidadesAdministrativas;
    }

    public Set<JEntidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(Set<JEntidad> entidades) {
        this.entidades = entidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUsuario jUsuario = (JUsuario) o;
        return Objects.equals(codigo, jUsuario.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JUsuario{" + "codigo=" + codigo + ", identificador='" + identificador + '\'' + ", nombre='" + nombre + '\'' + ", email='" + email + '\'' + ", traducciones=" + traducciones + '}';
    }
}
