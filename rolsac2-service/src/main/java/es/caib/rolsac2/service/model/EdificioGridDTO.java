package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Size;

/**
 * El tipo Edificio grid dto.
 */
@Schema(name = "EdificioGrid")
public class EdificioGridDTO extends ModelApi {

    private Long codigo;

    /**
     * Direccion
     **/
    private String direccion;

    /**
     * Poblacion
     **/
    private String poblacion;

    /**
     * Codigo postal
     */
    @Size(max = 5)
    private String cp;

    /**
     * Tfno
     **/
    @Size(max = 9)
    private String telefono;

    /**
     * Fax
     **/
    @Size(max = 9)
    private String fax;

    /**
     * Email
     **/
    @Size(max = 100)
    private String email;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Obtiene codigo.
     *
     * @return el codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo el codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene direccion.
     *
     * @return la direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece direccion.
     *
     * @param direccion la direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene poblacion.
     *
     * @return la poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * Establece poblacion.
     *
     * @param poblacion la poblacion
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Obtiene cp.
     *
     * @return el cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * Establece cp.
     *
     * @param cp el cp
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * Obtiene telefono.
     *
     * @return el telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece telefono.
     *
     * @param telefono el telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene fax.
     *
     * @return el fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Establece fax.
     *
     * @param fax el fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Obtiene email.
     *
     * @return el email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece email.
     *
     * @param email el email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene descripcion.
     *
     * @return la descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion la descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }
}

