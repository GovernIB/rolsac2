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

    private FicheroDTO logo;

    private Literal descripcion;

    private String idiomaDefectoRest;

    private String idiomasPermitidos;

    private String idiomasObligatorios;

    /**
     * Instancia una nueva Entidad dto.
     */
    public EntidadDTO() {
    }

    /**
     * Instancia una nueva Entidad dto.
     *
     * @param otro the otro
     */
    public EntidadDTO(EntidadDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.identificador = otro.identificador;
            this.activa = otro.activa;
            this.rolAdmin = otro.rolAdmin;
            this.rolAdminContenido = otro.rolAdminContenido;
            this.rolGestor = otro.rolGestor;
            this.rolInformador = otro.rolInformador;
            this.logo = otro.logo;
            this.descripcion = otro.descripcion;
            this.idiomaDefectoRest = otro.idiomaDefectoRest;
            this.idiomasPermitidos = otro.idiomasPermitidos;
            this.idiomasObligatorios = otro.idiomasObligatorios;
        }
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instancia una nueva Entidad dto.
     *
     * @param id the id
     */
    public EntidadDTO(Long id) {
        this.codigo = id;
    }

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
     * Obtiene activa.
     *
     * @return  activa
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * Establece activa.
     *
     * @param activa  activa
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    /**
     * Obtiene rol admin.
     *
     * @return el rol admin
     */
    public String getRolAdmin() {
        return rolAdmin;
    }

    /**
     * Establece rol admin.
     *
     * @param rolAdmin el rol admin
     */
    public void setRolAdmin(String rolAdmin) {
        this.rolAdmin = rolAdmin;
    }

    /**
     * Obtiene rol admin contenido.
     *
     * @return el rol admin contenido
     */
    public String getRolAdminContenido() {
        return rolAdminContenido;
    }

    /**
     * Establece rol admin contenido.
     *
     * @param rolAdminContenido el rol admin contenido
     */
    public void setRolAdminContenido(String rolAdminContenido) {
        this.rolAdminContenido = rolAdminContenido;
    }

    /**
     * Obtiene rol gestor.
     *
     * @return el rol gestor
     */
    public String getRolGestor() {
        return rolGestor;
    }

    /**
     * Establece rol gestor.
     *
     * @param rolGestor el rol gestor
     */
    public void setRolGestor(String rolGestor) {
        this.rolGestor = rolGestor;
    }

    /**
     * Obtiene rol informador.
     *
     * @return el rol informador
     */
    public String getRolInformador() {
        return rolInformador;
    }

    /**
     * Establece rol informador.
     *
     * @param rolInformador el rol informador
     */
    public void setRolInformador(String rolInformador) {
        this.rolInformador = rolInformador;
    }

    /**
     * Obtiene logo.
     *
     * @return el logo
     */
    public FicheroDTO getLogo() {
        return logo;
    }

    /**
     * Establece logo.
     *
     * @param logo el logo
     */
    public void setLogo(FicheroDTO logo) {
        this.logo = logo;
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

    /**
     * Obtiene identificador.
     *
     * @return el identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador el identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene idioma defecto rest.
     *
     * @return el idioma defecto rest
     */
    public String getIdiomaDefectoRest() { return idiomaDefectoRest; }

    /**
     * Establece idioma defecto rest.
     *
     * @param idiomaDefectoRest el idioma defecto rest
     */
    public void setIdiomaDefectoRest(String idiomaDefectoRest) { this.idiomaDefectoRest = idiomaDefectoRest; }

    /**
     * Obtiene idiomas permitidos.
     *
     * @return los idiomas permitidos
     */
    public String getIdiomasPermitidos() { return idiomasPermitidos; }

    /**
     * Establece idiomas permitidos.
     *
     * @param idiomasPermitidos los idiomas permitidos
     */
    public void setIdiomasPermitidos(String idiomasPermitidos) { this.idiomasPermitidos = idiomasPermitidos; }

    /**
     * Obtiene idiomas obligatorios.
     *
     * @return los idiomas obligatorios
     */
    public String getIdiomasObligatorios() { return idiomasObligatorios; }

    /**
     * Establece idiomas obligatorios.
     *
     * @param idiomasObligatorios los idiomas obligatorios
     */
    public void setIdiomasObligatorios(String idiomasObligatorios) { this.idiomasObligatorios = idiomasObligatorios; }

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
        return Objects.hash(codigo, activa, rolAdmin, rolAdminContenido, rolGestor, rolInformador, logo, descripcion, idiomaDefectoRest, idiomasPermitidos, idiomasObligatorios);
    }

    @Override
    public String toString() {
        return "EntidadDTO{" +
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", activa=" + activa +
                ", rolAdmin='" + rolAdmin + '\'' +
                ", rolAdminContenido='" + rolAdminContenido + '\'' +
                ", rolGestor='" + rolGestor + '\'' +
                ", rolInformador='" + rolInformador + '\'' +
                ", logo=" + logo +
                ", descripcion=" + descripcion +
                ", idiomaDefectoRest='" + idiomaDefectoRest + '\'' +
                ", idiomasPermitidos='" + idiomasPermitidos + '\'' +
                ", idiomasObligatorios='" + idiomasObligatorios + '\'' +
                '}';
    }
}
