package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de Entidad.
 */
public class ConfiguracionGlobalFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el -
     **/
    private String texto;

    /**
     * El filtro de propiedad
     */
    private String propiedad;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
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
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoPropiedad() {
        return propiedad != null && !propiedad.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
