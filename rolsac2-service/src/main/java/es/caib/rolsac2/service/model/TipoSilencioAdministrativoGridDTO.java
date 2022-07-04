package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un Tipo Silencio Administrativo.
 *
 * @author jsegovia
 */
@Schema(name = "TipoSilencioAdministrativoGrid")
public class TipoSilencioAdministrativoGridDTO {

    private Long id;;
    private String identificador;
    private Literal descripcion;

    public TipoSilencioAdministrativoGridDTO() {
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

    public TipoSilencioAdministrativoGridDTO(Long id) {
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
        return "TipoSilencioAdministrativoGrid{" +
                "id=" + id +
                ", identificador=" + identificador +
                ", descripcion=" + descripcion.toString() +
                '}';
    }
}