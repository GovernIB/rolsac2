package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'una Entidad.
 *
 * @author jsegovia
 */
@Schema(name = "EntidadGrid")
public class EntidadGridDTO extends ModelApi {

    private Long codigo;

    private String identificador;
    private String descripcion;
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

    public EntidadGridDTO(Long id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public String toString() {
        return "EntidadGridDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                '}';
    }
}
