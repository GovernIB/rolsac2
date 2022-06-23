package es.caib.rolsac2.service.model.filtro;

/**
 * Filtro de tipo de materia SIA
 */
public class TipoMateriaSIAFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewTipoMateriaSIA
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
