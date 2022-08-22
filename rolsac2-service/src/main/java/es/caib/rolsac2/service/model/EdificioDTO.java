package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Size;

@Schema(name = "Edificio")
public class EdificioDTO extends ModelApi {

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
     * Latitud
     **/
    @Size(max = 50)
    private String latitud;

    /**
     * Longitud
     **/
    @Size(max = 50)
    private String longitud;

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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }
}
