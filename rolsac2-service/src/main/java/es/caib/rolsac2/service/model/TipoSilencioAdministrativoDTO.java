package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

/**
 * Dades d'un TipoSilencioAdministrativo.
 *
 * @author jsegovia
 */
@Schema(name = "TipoSilencioAdministrativo")
public class TipoSilencioAdministrativoDTO {

    private Long id;
    ;
    private String identificador;
    private Literal descripcion;

    private Object descripcion2;

    private Date fechaBorrar;

    public TipoSilencioAdministrativoDTO() {
    }


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (id == null) {
            return null;
        } else {
            return String.valueOf(id);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.id = null;
        } else {
            this.id = Long.valueOf(idString);
        }
    }

    public TipoSilencioAdministrativoDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoSilencioAdministrativo{" +
                "id=" + id +
                ", identificador=" + identificador +
                ", descripcion=" + descripcion.toString() +
                '}';
    }

    public Object getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(Object descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public Date getFechaBorrar() {
        return fechaBorrar;
    }

    public void setFechaBorrar(Date fechaBorrar) {
        this.fechaBorrar = fechaBorrar;
    }
}
