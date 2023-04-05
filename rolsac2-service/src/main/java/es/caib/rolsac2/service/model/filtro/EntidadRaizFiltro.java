package es.caib.rolsac2.service.model.filtro;

public class EntidadRaizFiltro extends AbstractFiltro{

    private String texto;
    private String ua;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoUa() {
        return ua != null;
    }

    @Override
    protected String getDefaultOrder() {
        return "codigo";
    }
}
