package es.caib.rolsac2.commons.plugins.indexacion.api.model;

import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * Resultado busqueda.
 *
 * @author Indra
 */
public class ResultData {

    /**
     * Numero elementos encontrados.
     */
    private long numResultados;

    /**
     * Elementos encontrados.
     */
    private List<StoredData> resultados;


    /**
     * Obtiene numero elementos encontrados.
     *
     * @return numero elementos encontrados
     */
    public long getNumResultados() {
        return numResultados;
    }


    /**
     * Establece numero elementos encontrados.
     *
     * @param numResultados numero elementos encontrados
     */
    public void setNumResultados(long numResultados) {
        this.numResultados = numResultados;
    }


    /**
     * Obtiene elementos encontrados.
     *
     * @return elementos encontrados
     */
    public List<StoredData> getResultados() {
        return resultados;
    }


    /**
     * Establece elementos encontrados.
     *
     * @param resultados elementos encontrados
     */
    public void setResultados(List<StoredData> resultados) {
        this.resultados = resultados;
    }


}
