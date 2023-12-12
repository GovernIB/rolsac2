package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'una Entidad.
 *
 * @author Indra
 */
@Schema(name = "EntidadGrid")
public class EntidadGridDTO extends ModelApi {
    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;


    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Activa
     */
    private Boolean activa;

    /**
     * Rol Administrador
     */
    private String rolAdmin;

    /**
     * Rol administrador de contenido
     */
    private String rolAdminContenido;

    /**
     * Rol de gestor
     */
    private String rolGestor;

    /**
     * Rol de informador
     */
    private String rolInformador;

    /**
     * Indica si el administrador de contenido tiene un idioma por defecto
     **/
    private String admContenidoIdiomaPorDefecto;

    /**
     * Indica si el administrador de contenido tiene opción de seleccionar cualquier idioma o uno forzado
     */
    private String admContenidoSeleccionIdioma;

    /**
     * Instantiates a new Entidad grid dto.
     */
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
     * Instantiates a new Entidad grid dto.
     *
     * @param id el id
     */
    public EntidadGridDTO(Long id) {
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
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }


    /**
     * Obtiene activa.
     *
     * @return activa
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * Establece activa.
     *
     * @param activa activa
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
     * Obtiene adm contenido idioma por defecto.
     *
     * @return
     */
    public String getAdmContenidoIdiomaPorDefecto() {
        return admContenidoIdiomaPorDefecto;
    }

    /**
     * Establece adm contenido idioma por defecto.
     *
     * @param admContenidoIdiomaPorDefecto
     */
    public void setAdmContenidoIdiomaPorDefecto(String admContenidoIdiomaPorDefecto) {
        this.admContenidoIdiomaPorDefecto = admContenidoIdiomaPorDefecto;
    }

    /**
     * Obtiene adm contenido seleccion idioma.
     *
     * @return
     */
    public String getAdmContenidoSeleccionIdioma() {
        return admContenidoSeleccionIdioma;
    }

    /**
     * Establece adm contenido seleccion idioma.
     *
     * @param admContenidoSeleccionIdioma
     */
    public void setAdmContenidoSeleccionIdioma(String admContenidoSeleccionIdioma) {
        this.admContenidoSeleccionIdioma = admContenidoSeleccionIdioma;
    }

    @Override
    public String toString() {
        return "EntidadGridDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + ", activa=" + activa + ", rolAdmin='" + rolAdmin + '\'' + ", rolAdminContenido='" + rolAdminContenido + '\'' + ", rolGestor='" + rolGestor + '\'' + ", rolInformador='" + rolInformador + '\'' + '}';
    }
}
