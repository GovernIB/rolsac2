package es.caib.rolsac2.service.model.filtro;


public class SesionFiltro extends AbstractFiltro {

    /**
     * Filtro perfil usuario
     */
    private String perfil;
    /**
     * Filtro CÃƒÂ³digo de usuario
     */
    private Long idUsuario;

    /**
     * Filtro Identificador de usuario
     */
    private String identificador;
    /**
     * Filtro nombre de usuario
     */
    private String usuario;


    /**
     * Devuelve el idUsuario
     *
     * @return
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el idUsuario
     *
     * @param idUsuario
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el perfil
     *
     * @return
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * Establece el perfil
     *
     * @param perfil
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    /**
     * Obtiene el identificador del usuario
     *
     * @return
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador del usuario
     *
     * @param identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

        /**
     * Obtiene el nombre del usuario
     *
     * @return
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Establece el nombre del usuario
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Indica si el perfil estÃƒÂ¡ relleno
     *
     * @return true o false
     */
    public boolean isRellenoPerfil() {
        return perfil != null && !perfil.isEmpty();
    }

    /**
     * Indica si estÃƒÂ¡ relleno el identificador
     *
     * @return true o false
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    /**
     * Indica si estÃƒÂ¡ relleno el idUsuario
     *
     * @return true o false
     */
    public boolean isRellenoIdUsuario() {
        return idUsuario != null;
    }

    /**
     * Indica si está relleno el nombre de usuario
     *
     * @return true o false
     */
    public boolean isRellenoUsuario() {
        return usuario != null && !usuario.isEmpty();
    }
    @Override
    protected String getDefaultOrder() {
        return "perfil";
    }
}
