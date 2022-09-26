package es.caib.rolsac2.service.model.filtro;

public class NormativaFiltro extends AbstractFiltro {

    private String texto;

    @Override
    protected String getDefaultOrder() {
        return "id";
    }

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
}
