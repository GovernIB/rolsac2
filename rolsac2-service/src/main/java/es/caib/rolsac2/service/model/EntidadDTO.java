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

    private Long codigo;

    private String identificador;

    private Boolean activa;

    private String rolAdmin;

    private String rolAdminContenido;

    private String rolGestor;

    private String rolInformador;

    private Long logo;

    private Literal descripcion;

    public EntidadDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public EntidadDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadDTO that = (EntidadDTO) o;
        return codigo.equals(that.codigo) && activa.equals(that.activa) &&
                rolAdmin.equals(that.rolAdmin) && rolAdminContenido.equals(that.rolAdminContenido) &&
                rolGestor.equals(that.rolGestor) && rolInformador.equals(that.rolInformador);
    }


    @Override
    public int hashCode() {
        return Objects.hash(codigo, activa, rolAdmin, rolAdminContenido, rolGestor, rolInformador, logo, descripcion);
    }

    @Override
    public String toString() {
        return "EntidadDTO{" +
                "id=" + codigo +
                ", identificador=" + identificador +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                ", logo=" + logo +
                ", descripcion=" + descripcion +
                '}';
    }
}
