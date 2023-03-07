package es.caib.rolsac2.service.model.filtro;

public class TemaFiltro extends AbstractFiltro {

    private String texto;
    private String identificador;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
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
