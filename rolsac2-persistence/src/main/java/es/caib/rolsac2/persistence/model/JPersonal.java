package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "personal-sequence", sequenceName = "RS2_PERSON_SEQ", allocationSize = 1)
@Table(name = "RS2_PERSON", indexes = {@Index(name = "RS2_PERSON_PK_I", columnList = "PERS_CODIGO")})
@NamedQueries({@NamedQuery(name = JPersonal.FIND_BY_ID, query = "select p from JPersonal p where p.codigo = :id")})
public class JPersonal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Personal.FIND_BY_ID";

    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal-sequence")
    @Column(name = "PERS_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Identificacion del personal.
     */
    @Column(name = "PERS_IDENTI", length = 50)
    private String identificador;

    /**
     * Nombre
     **/
    @Column(name = "PERS_NOMBRE", length = 50)
    private String nombre;

    /**
     * Cargo de la persona.
     */
    @Column(name = "PERS_CARGO", /* nullable = false, */ length = 500)
    @Size(max = 500)
    private String cargo;

    /**
     * Funciones
     **/
    @Column(name = "PERS_FUNC", /* nullable = false, */ length = 4000)
    @Size(max = 4000)
    private String funciones;

    /**
     * La unidad administrativa (UA). De momento, es un Long pero deberia ser un JUnidadAdministrativa!!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERS_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Cargo de la persona.
     */
    @Column(name = "PERS_EMAIL", /* nullable = false, */ length = 100)
    @Size(max = 100)
    private String email;

    /**
     * Telefono fijo
     **/
    @Column(name = "PERS_TFNOFIJ", /* nullable = false, */ length = 9)
    @Size(max = 9)
    private String telefonoFijo;

    /**
     * Telefono movil
     **/
    @Column(name = "PERS_TFNOMOV", /* nullable = false, */ length = 9)
    @Size(max = 9)
    private String telefonoMovil;

    /**
     * Telefono Exterior fijo
     **/
    @Column(name = "PERS_EXTFIJ", /* nullable = false, */ length = 9)
    @Size(max = 9)
    private String telefonoExteriorFijo;

    /**
     * Telefono Exterior movil
     **/
    @Column(name = "PERS_EXTMOV", /* nullable = false, */ length = 9)
    @Size(max = 9)
    private String telefonoExteriorMovil;

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
     * @param identificacion identificacion
     */
    public void setIdentificador(String identificacion) {
        this.identificador = identificacion;
    }

    /**
     * Obtiene cargo.
     *
     * @return cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece cargo.
     *
     * @param cargo cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return unidad administrativa
     */
    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param unidadAdministrativa unidad administrativa
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
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
     * Obtiene telefono fijo.
     *
     * @return telefono fijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * Establece telefono fijo.
     *
     * @param telefonoFijo telefono fijo
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * Obtiene telefono movil.
     *
     * @return telefono movil
     */
    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    /**
     * Establece telefono movil.
     *
     * @param telefonoMovil telefono movil
     */
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    /**
     * Obtiene telefono exterior fijo.
     *
     * @return telefono exterior fijo
     */
    public String getTelefonoExteriorFijo() {
        return telefonoExteriorFijo;
    }

    /**
     * Establece telefono exterior fijo.
     *
     * @param telefonoExteriorFijo telefono exterior fijo
     */
    public void setTelefonoExteriorFijo(String telefonoExteriorFijo) {
        this.telefonoExteriorFijo = telefonoExteriorFijo;
    }

    /**
     * Obtiene telefono exterior movil.
     *
     * @return telefono exterior movil
     */
    public String getTelefonoExteriorMovil() {
        return telefonoExteriorMovil;
    }

    /**
     * Obtiene funciones.
     *
     * @return funciones
     */
    public String getFunciones() {
        return funciones;
    }

    /**
     * Establece funciones.
     *
     * @param funciones funciones
     */
    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    /**
     * Establece telefono exterior movil.
     *
     * @param telefonoExteriorMovil telefono exterior movil
     */
    public void setTelefonoExteriorMovil(String telefonoExteriorMovil) {
        this.telefonoExteriorMovil = telefonoExteriorMovil;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JPersonal)) return false;
        JPersonal that = (JPersonal) o;
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
