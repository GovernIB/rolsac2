package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de tipo de tramitación
 */
public class TipoTramitacionFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewTipoTramitacion
     **/
    private String texto;

    /**
     * Indica si es de tipo plantilla
     **/
    private Boolean tipoPlantilla;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(Boolean tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
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
     * Esta relleno el tipo plantilla
     *
     * @return
     */
    public boolean isRellenoTipoPlantilla() {
        return tipoPlantilla != null;
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
