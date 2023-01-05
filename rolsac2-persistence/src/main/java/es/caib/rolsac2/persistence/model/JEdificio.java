package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JEdificioTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un edificio. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "edificio-sequence", sequenceName = "RS2_EDIFIC_SEQ", allocationSize = 1)
@Table(name = "RS2_EDIFIC", indexes = {@Index(name = "RS2_EDIFIC_PK_I", columnList = "EDIF_CODIGO")})
@NamedQueries(@NamedQuery(name = JEdificio.FIND_BY_ID, query = "select p from JEdificio p where p.codigo = :id"))
public class JEdificio extends BaseEntity {

    /**
     * Consulta FIND_BY_ID
     */
    public static final String FIND_BY_ID = "Edificio.FIND_BY_ID";
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edificio-sequence")
    @Column(name = "EDIF_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Direccion
     **/
    @Column(name = "EDIF_DIREC")
    private String direccion;

    /**
     * Poblacion
     **/
    @Column(name = "EDIF_POBLAC")
    private String poblacion;

    /**
     * Codigo postal
     */
    @Column(name = "EDIF_CP", length = 5)
    private String cp;

    /**
     * Latitud
     **/
    @Column(name = "EDIF_LATI", length = 50)
    private String latitud;

    /**
     * Longitud
     **/
    @Column(name = "EDIF_LONG", length = 50)
    private String longitud;

    /**
     * Tfno
     **/
    @Column(name = "EDIF_TFNO", length = 9)
    private String telefono;

    /**
     * Fax
     **/
    @Column(name = "EDIF_FAX", length = 9)
    private String fax;

    /**
     * Email
     **/
    @Column(name = "EDIF_EMAIL", length = 100)
    private String email;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "edificio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JEdificioTraduccion> descripcion;

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
     * Obtiene direccion.
     *
     * @return  direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece direccion.
     *
     * @param edifDirec  edif direc
     */
    public void setDireccion(String edifDirec) {
        this.direccion = edifDirec;
    }

    /**
     * Obtiene poblacion.
     *
     * @return  poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Establece poblacion.
     *
     * @param edifPoblac  edif poblac
     */
    public void setPoblacion(String edifPoblac) {
        this.poblacion = edifPoblac;
    }

    /**
     * Obtiene cp.
     *
     * @return  cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * Establece cp.
     *
     * @param edifCp  edif cp
     */
    public void setCp(String edifCp) {
        this.cp = edifCp;
    }

    /**
     * Obtiene latitud.
     *
     * @return  latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * Establece latitud.
     *
     * @param edifLati  edif lati
     */
    public void setLatitud(String edifLati) {
        this.latitud = edifLati;
    }

    /**
     * Obtiene longitud.
     *
     * @return  longitud
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * Establece longitud.
     *
     * @param edifLong  edif long
     */
    public void setLongitud(String edifLong) {
        this.longitud = edifLong;
    }

    /**
     * Obtiene telefono.
     *
     * @return  telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece telefono.
     *
     * @param edifTfno  edif tfno
     */
    public void setTelefono(String edifTfno) {
        this.telefono = edifTfno;
    }

    /**
     * Obtiene fax.
     *
     * @return  fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Establece fax.
     *
     * @param edifFax  edif fax
     */
    public void setFax(String edifFax) {
        this.fax = edifFax;
    }

    /**
     * Obtiene email.
     *
     * @return  email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece email.
     *
     * @param edifEmail  edif email
     */
    public void setEmail(String edifEmail) {
        this.email = edifEmail;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public List<JEdificioTraduccion> getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(List<JEdificioTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JEdificio jEdificio = (JEdificio) o;
        return codigo.equals(jEdificio.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JEdificio{" + "id=" + codigo + '}';
    }
}
