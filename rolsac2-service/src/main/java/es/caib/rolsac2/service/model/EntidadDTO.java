package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un Entidad.
 *
 * @author jsegovia
 */
@Schema(name = "Entidad")
public class EntidadDTO extends ModelApi {

    private Long id;

    private String dir3;

    private Boolean activa;

    private String rolAdmin;

    private String rolAdminContenido;

    private String rolGestor;

    private String rolInformador;

    private Long logo;

    public EntidadDTO() {
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

    public EntidadDTO(Long id) {
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

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadDTO that = (EntidadDTO) o;
        return id.equals(that.id) && dir3.equals(that.dir3) && activa.equals(that.activa) &&
                rolAdmin.equals(that.rolAdmin) && rolAdminContenido.equals(that.rolAdminContenido) &&
                rolGestor.equals(that.rolGestor) && rolInformador.equals(that.rolInformador) &&
                logo.equals(that.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dir3, activa, rolAdmin, rolAdminContenido, rolGestor, rolInformador, logo);
    }
}
