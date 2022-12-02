package es.caib.rolsac2.commons.plugins.boletin.api.model;

import java.util.List;

public class ResultadoBoib {

        private String rdfUrl;
        private String numBoib;
        private String anyo;
        private String num;
        private String url;
        private List<String> enviaments;
        private boolean historic;

    public String getRdfUrl() {
        return rdfUrl;
    }

    public void setRdfUrl(String rdfUrl) {
        this.rdfUrl = rdfUrl;
    }

    public String getNumBoib() {
        return numBoib;
    }

    public void setNumBoib(String numBoib) {
        this.numBoib = numBoib;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getEnviaments() {
        return enviaments;
    }

    public void setEnviaments(List<String> enviaments) {
        this.enviaments = enviaments;
    }

    public boolean isHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }
}
