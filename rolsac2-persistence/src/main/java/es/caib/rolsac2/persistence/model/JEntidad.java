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

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "JEntidad.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "JEntidad.COUNT_BY_IDENTIFICADOR";

    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-entidad-sequence")
    @Column(name = "ENTI_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificador
     **/
    @Column(name = "ENTI_IDENTI", length = 50, nullable = false)
    private String identificador;

    /**
     * Activa
     **/
    @Column(name = "ENTI_ACTIVA", nullable = false)
    private Boolean activa;

    /**
     * Rol del administrador
     **/
    @Column(name = "ENTI_ROLADE", nullable = false, length = 100)
    private String rolAdmin;

    /**
     * Rol del administrador de contenido
     **/
    @Column(name = "ENTI_ROLADC", nullable = false, length = 100)
    private String rolAdminContenido;

    /**
     * Rol de gestor
     **/
    @Column(name = "ENTI_ROLGES", nullable = false, length = 100)
    private String rolGestor;

    /**
     * Rol de informador
     **/
    @Column(name = "ENTI_ROLINF", nullable = false, length = 100)
    private String rolInformador;

    /**
     * Logo
     **/
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ENTI_LOGO", nullable = false)
    private JFicheroExterno logo;

    /**
     * Fichero externo para personalizar CSS en los views
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ENTI_CSS", nullable = false)
    private JFicheroExterno cssPersonalizado;

    /**
     * Idioma por defecto
     **/
    @Column(name = "ENTI_IDIDEF", nullable = false, length = 20)
    private String idiomaDefectoRest;

    /**
     * Idiomas permitidos
     **/
    @Column(name = "ENTI_IDIPER", nullable = false, length = 20)
    private String idiomasPermitidos;

    /**
     * Idiomas obligatorios
     **/
    @Column(name = "ENTI_IDIOBL", nullable = false, length = 20)
    private String idiomasObligatorios;


    /**
     * Descripción
     */
    @OneToMany(mappedBy = "entidad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JEntidadTraduccion> descripcion;


    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene activa.
     *
     * @return  activa
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * Establece activa.
     *
     * @param activa  activa
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    /**
     * Obtiene rol admin.
     *
     * @return  rol admin
     */
    public String getRolAdmin() {
        return rolAdmin;
    }

    /**
     * Establece rol admin.
     *
     * @param rolAdmin  rol admin
     */
    public void setRolAdmin(String rolAdmin) {
        this.rolAdmin = rolAdmin;
    }

    /**
     * Obtiene rol admin contenido.
     *
     * @return  rol admin contenido
     */
    public String getRolAdminContenido() {
        return rolAdminContenido;
    }

    /**
     * Establece rol admin contenido.
     *
     * @param rolAdminContenido  rol admin contenido
     */
    public void setRolAdminContenido(String rolAdminContenido) {
        this.rolAdminContenido = rolAdminContenido;
    }

    /**
     * Obtiene rol gestor.
     *
     * @return  rol gestor
     */
    public String getRolGestor() {
        return rolGestor;
    }

    /**
     * Establece rol gestor.
     *
     * @param rolGestor  rol gestor
     */
    public void setRolGestor(String rolGestor) {
        this.rolGestor = rolGestor;
    }

    /**
     * Obtiene rol informador.
     *
     * @return  rol informador
     */
    public String getRolInformador() {
        return rolInformador;
    }

    /**
     * Establece rol informador.
     *
     * @param rolInformador  rol informador
     */
    public void setRolInformador(String rolInformador) {
        this.rolInformador = rolInformador;
    }

    /**
     * Obtiene logo.
     *
     * @return  logo
     */
    public JFicheroExterno getLogo() {
        return logo;
    }

    /**
     * Establece logo.
     *
     * @param logo  logo
     */
    public void setLogo(JFicheroExterno logo) {
        this.logo = logo;
    }

    /**
     * Obtiene CSS.
     *
     * @return  cssPersonalizado
     */
    public JFicheroExterno getCssPersonalizado() {
        return cssPersonalizado;
    }

    /**
     * Establece CSS.
     *
     * @param cssPersonalizado  CSS
     */
    public void setCssPersonalizado(JFicheroExterno cssPersonalizado) {
        this.cssPersonalizado = cssPersonalizado;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public List<JEntidadTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene idioma defecto rest.
     *
     * @return  idioma defecto rest
     */
    public String getIdiomaDefectoRest() {
        return idiomaDefectoRest;
    }

    /**
     * Establece idioma defecto rest.
     *
     * @param idiomaDefectoRest  idioma defecto rest
     */
    public void setIdiomaDefectoRest(String idiomaDefectoRest) {
        this.idiomaDefectoRest = idiomaDefectoRest;
    }

    /**
     * Obtiene idiomas permitidos.
     *
     * @return  idiomas permitidos
     */
    public String getIdiomasPermitidos() {
        return idiomasPermitidos;
    }

    /**
     * Establece idiomas permitidos.
     *
     * @param idiomasPermitidos  idiomas permitidos
     */
    public void setIdiomasPermitidos(String idiomasPermitidos) {
        this.idiomasPermitidos = idiomasPermitidos;
    }

    /**
     * Obtiene idiomas obligatorios.
     *
     * @return  idiomas obligatorios
     */
    public String getIdiomasObligatorios() {
        return idiomasObligatorios;
    }

    /**
     * Establece idiomas obligatorios.
     *
     * @param idiomasObligatorios  idiomas obligatorios
     */
    public void setIdiomasObligatorios(String idiomasObligatorios) {
        this.idiomasObligatorios = idiomasObligatorios;
    }

    /**
     * Obtiene descripcion.
     *
     * @param idioma  idioma
     * @return  descripcion
     */
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

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
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
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                ", logo=" + logo +
                ", idiomaDefectoRest='" + idiomaDefectoRest + '\'' +
                ", idiomasPermitidos='" + idiomasPermitidos + '\'' +
                ", idiomasObligatorios='" + idiomasObligatorios + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}