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
@SequenceGenerator(name = "personal-sequence", sequenceName = "RS2_PERSONAL_SEQ", allocationSize = 1)
@Table(name = "RS2_PERSON",
        indexes = {
                @Index(name = "RS2_PERSON_PK_I", columnList = "PERS_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JPersonal.FIND_BY_ID,
                query = "select p from JPersonal p where p.id = :id")
})
public class JPersonal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "Personal.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal-sequence")
    @Column(name = "PERS_CODIGO", nullable = false, length = 10)
    private Long id;

    /**
     * Identificacion del personal.
     */
    @Column(name = "PERS_IDENTI", length = 50)
    //@NotNull
    //@Pattern(regexp = "[0-9]{6,8}", message = "{codiSia.Pattern.message}")
    private String identificador;


    @Column(name = "PERS_NOMBRE", length = 50)
    private String nombre;

    /**
     * Cargo de la persona.
     */
    @Column(name = "PERS_CARGO", /*nullable = false,*/ length = 500)
    //@NotEmpty
    @Size(max = 500)
    private String cargo;

    @Column(name = "PERS_FUNC", /*nullable = false,*/ length = 4000)
    //@NotEmpty
    @Size(max = 4000)
    private String funciones;

    /**
     * La unidad administrativa (UA).
     * De momento, es un Long pero deberia ser un JUnidadAdministrativa!!
     */
   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNITATORGANICAID", nullable = false,
            foreignKey = @ForeignKey(name = "RSC_PROCEDIMENT_UNITAT_FK"))*/
    @Column(name = "PERS_CODUA", /*nullable = false,*/ length = 10)
    private Long unidadAdministrativa;

    /**
     * Cargo de la persona.
     */
    @Column(name = "PERS_EMAIL", /*nullable = false,*/ length = 100)
    @Size(max = 100)
    private String email;

    /**
     * Telefono fijo
     **/
    @Column(name = "PERS_TFNOFIJ", /*nullable = false,*/ length = 9)
    @Size(max = 9)
    private String telefonoFijo;

    /**
     * Telefono movil
     **/
    @Column(name = "PERS_TFNOMOV", /*nullable = false,*/ length = 9)
    @Size(max = 9)
    private String telefonoMovil;

    /**
     * Telefono Exterior fijo
     **/
    @Column(name = "PERS_EXTFIJ", /*nullable = false,*/ length = 9)
    @Size(max = 9)
    private String telefonoExteriorFijo;

    /**
     * Telefono Exterior movil
     **/
    @Column(name = "PERS_EXTMOV", /*nullable = false,*/ length = 9)
    @Size(max = 9)
    private String telefonoExteriorMovil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificacion) {
        this.identificador = identificacion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Long unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoExteriorFijo() {
        return telefonoExteriorFijo;
    }

    public void setTelefonoExteriorFijo(String telefonoExteriorFijo) {
        this.telefonoExteriorFijo = telefonoExteriorFijo;
    }

    public String getTelefonoExteriorMovil() {
        return telefonoExteriorMovil;
    }

    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    public void setTelefonoExteriorMovil(String telefonoExteriorMovil) {
        this.telefonoExteriorMovil = telefonoExteriorMovil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JPersonal)) return false;
        JPersonal that = (JPersonal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JPersonal{" +
                "id=" + id +
                //  ", codiSia='" + codiSia + '\'' +
                '}';
    }

}