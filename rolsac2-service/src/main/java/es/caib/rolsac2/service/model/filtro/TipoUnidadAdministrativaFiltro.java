package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de TipoUnidadAdministrativa.
 */
public class TipoUnidadAdministrativaFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewTipoUnidadAdministrativa
     **/
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
