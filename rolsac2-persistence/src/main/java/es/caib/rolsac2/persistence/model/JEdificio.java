package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "edificio-sequence", sequenceName = "RS2_EDIFIC_SEQ", allocationSize = 1)
@Table(name = "RS2_EDIFIC",
        indexes = {
                @Index(name = "RS2_EDIFIC_PK_I", columnList = "EDIF_CODIGO")
        })
public class JEdificio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edificio-sequence")
    @Column(name = "EDIF_CODIGO", nullable = false)
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String edifDirec) {
        this.direccion = edifDirec;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String edifPoblac) {
        this.poblacion = edifPoblac;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String edifCp) {
        this.cp = edifCp;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String edifLati) {
        this.latitud = edifLati;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String edifLong) {
        this.longitud = edifLong;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String edifTfno) {
        this.telefono = edifTfno;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String edifFax) {
        this.fax = edifFax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String edifEmail) {
        this.email = edifEmail;
    }

}