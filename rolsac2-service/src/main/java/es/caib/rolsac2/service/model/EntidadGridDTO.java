package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'una Entidad.
 *
 * @author jsegovia
 */
@Schema(name = "EntidadGrid")
public class EntidadGridDTO extends ModelApi {

    private Long id;

    private String dir3;

    private Boolean activa;

    private String rolAdmin;

    private String rolAdminContenido;

    private String rolGestor;

    private String rolInformador;


    public EntidadGridDTO() {
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

    public EntidadGridDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDir3() {
        return dir3;
    }

    public void setDir3(String dir3) {
        this.dir3 = dir3;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getRolAdmin() {
        return rolAdmin;
    }

    public void setRolAdmin(String rolAdmin) {
        this.rolAdmin = rolAdmin;
    }

    public String getRolAdminContenido() {
        return rolAdminContenido;
    }

    public void setRolAdminContenido(String rolAdminContenido) {
        this.rolAdminContenido = rolAdminContenido;
    }

    public String getRolGestor() {
        return rolGestor;
    }

    public void setRolGestor(String rolGestor) {
        this.rolGestor = rolGestor;
    }

    public String getRolInformador() {
        return rolInformador;
    }

    public void setRolInformador(String rolInformador) {
        this.rolInformador = rolInformador;
    }


    @Override
    public String toString() {
        return "EntidadGridDTO{" +
                "id=" + id +
                ", dir3='" + dir3 + '\'' +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                '}';
    }
}
