package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de ua.
 */
public class UnidadAdministrativaFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en UnidadAdministrativaView
     **/
    private String texto;
    private String nombre;
    private String identificador;

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

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
