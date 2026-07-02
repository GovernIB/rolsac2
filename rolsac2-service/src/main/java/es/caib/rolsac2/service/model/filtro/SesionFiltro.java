package es.caib.rolsac2.service.model.filtro;


public class SesionFiltro extends AbstractFiltro {

    /**
     * Filtro perfil usuario
     */
    private String perfil;
    /**
     * Filtro Código de usuario
     */
    private Long idUsuario;

    /**
     * Filtro Identificador de usuario
     */
    private String identificador;


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
     * Indica si el perfil está relleno
     *
     * @return true o false
     */
    public boolean isRellenoPerfil() {
        return perfil != null && !perfil.isEmpty();
    }

    /**
     * Indica si está relleno el identificador
     *
     * @return true o false
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    /**
     * Indica si está relleno el idUsuario
     *
     * @return true o false
     */
    public boolean isRellenoIdUsuario() {
        return idUsuario != null;
    }

    @Override
    protected String getDefaultOrder() {
        return "perfil";
    }
}
