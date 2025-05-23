package es.caib.rolsac2.commons.plugins.indexacion.api.model;

public class ResultadoAccion {

    /**
     * Si ha ido correcto
     **/
    private boolean correcto;

    /**
     * En caso de correcto = false, el mensaje de error
     */
    private String mensaje;

    /**
     * Indica que indexador esta activo
     **/
    private boolean solrActivo;
    private boolean elasticActivo;

    /**
     * Indica el resultado de la indexacion solr y elastic
     **/
    private boolean resultadoSolr;
    private boolean resultadoElastic;

    public ResultadoAccion(boolean correcto, String mensaje, boolean solrActivo, boolean elasticActivo, boolean resultadoSolr, boolean resultadoElastic) {
        this.correcto = correcto;
        this.mensaje = mensaje;
        this.solrActivo = solrActivo;
        this.elasticActivo = elasticActivo;
        this.resultadoSolr = resultadoSolr;
        this.resultadoElastic = resultadoElastic;
    }

    public ResultadoAccion(boolean correcto, String mensaje) {
        this.correcto = correcto;
        this.mensaje = mensaje;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isSolrActivo() {
        return solrActivo;
    }

    public void setSolrActivo(boolean solrActivo) {
        this.solrActivo = solrActivo;
    }

    public boolean isElasticActivo() {
        return elasticActivo;
    }

    public void setElasticActivo(boolean elasticActivo) {
        this.elasticActivo = elasticActivo;
    }

    public boolean isResultadoSolr() {
        return resultadoSolr;
    }

    public void setResultadoSolr(boolean resultadoSolr) {
        this.resultadoSolr = resultadoSolr;
    }

    public boolean isResultadoElastic() {
        return resultadoElastic;
    }

    public void setResultadoElastic(boolean resultadoElastic) {
        this.resultadoElastic = resultadoElastic;
    }

    @Override
    public String toString() {
        return "ResultadoAccion [correcto=" + correcto + ", mensaje=" + mensaje + ", solrActivo=" + solrActivo + ", elasticActivo=" + elasticActivo + ", resultadoSolr=" + resultadoSolr + ", resultadoElastic=" + resultadoElastic + "]";
    }
}
