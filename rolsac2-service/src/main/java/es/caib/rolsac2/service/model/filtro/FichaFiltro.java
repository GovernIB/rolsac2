package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de personal.
 */
public class FichaFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewPersonal
     **/
    private String texto;
    private String nombre;
    private String identificador;
    private String funciones;
    private String cargo;
    private String email;
    private Long unidadAdministrativa;
    private String telefonoFijo;
    private String telefonoMovil;
    private String telefonoExteriorFijo;
    private String telefonoExteriorMovil;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Long unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoExteriorFijo() {
        return telefonoExteriorFijo;
    }

    public void setTelefonoExteriorFijo(String telefonoExteriorFijo) {
        this.telefonoExteriorFijo = telefonoExteriorFijo;
    }

    public String getTelefonoExteriorMovil() {
        return telefonoExteriorMovil;
    }

    public void setTelefonoExteriorMovil(String telefonoExteriorMovil) {
        this.telefonoExteriorMovil = telefonoExteriorMovil;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    /**
     * Esta relleno el nombre
     **/
    public boolean isRellenoNombre() {
        return nombre != null && !nombre.isEmpty();
    }

    /**
     * Esta relleno el identificador
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    /**
     * Esta relleno el funciones
     **/
    public boolean isRellenoFunciones() {
        return funciones != null && !funciones.isEmpty();
    }

    /**
     * Esta relleno el cargo
     **/
    public boolean isRellenoCargo() {
        return cargo != null && !cargo.isEmpty();
    }

    /**
     * Esta relleno el email
     **/
    public boolean isRellenoEmail() {
        return email != null && !email.isEmpty();
    }

    /**
     * Esta relleno la unidiad administrativa
     **/
    public boolean isRellenoUnidadAdministrativa() {
        return unidadAdministrativa != null;
    }

    /**
     * Esta relleno el telefono fijo
     **/
    public boolean isRellenoTelefonoFijo() {
        return telefonoFijo != null && !telefonoFijo.isEmpty();
    }

    /**
     * Esta relleno el telefono movil
     **/
    public boolean isRellenoTelefonoMovil() {
        return telefonoMovil != null && !telefonoMovil.isEmpty();
    }

    /**
     * Esta relleno el telefono fijo exterior
     **/
    public boolean isRellenoTelefonoExteriorFijo() {
        return telefonoExteriorFijo != null && !telefonoExteriorFijo.isEmpty();
    }

    /**
     * Esta relleno el telefono movil exterior
     **/
    public boolean isRellenoTelefonoExteriorMovil() {
        return telefonoExteriorMovil != null && !telefonoExteriorMovil.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
