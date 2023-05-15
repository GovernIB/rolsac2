package es.caib.rolsac2.commons.plugins.sia.api.model;

/**
 * Clase que indica el resultado de un envío a SIA.
 *
 * @author slromero
 */
public class ResultadoSIA {
    /**
     * Resultado enviable pero no cumple datos.
     **/
    public static int RESULTADO_NO_CUMPLE_DATOS = -2;
    /**
     * Resultado No enviable.
     **/
    public static int RESULTADO_NO_ENVIABLE = -3;
    /**
     * Resultado ok.
     **/
    public static int RESULTADO_OK = 1;
    /**
     * Resultado error.
     **/
    public static int RESULTADO_ERROR = -1;
    /**
     * resultado.
     **/
    public int resultado = RESULTADO_ERROR;
    /**
     * Mensaje.
     **/
    public String mensaje;
    /**
     * Correctos.
     **/
    public int correctos = 0;
    /**
     * Incorrectos.
     **/
    public int incorrectos = 0;
    /**
     * Código SIA.
     **/
    public String codSIA;
    /**
     * Estado SIA.
     **/
    public String estadoSIA;
    /**
     * Operacion.
     **/
    public String operacion;

    public ResultadoSIA() {

    }

    public ResultadoSIA(int pResultado, String pMensaje) {
        resultado = pResultado;
        mensaje = pMensaje;
    }

    /**
     * @return the resultado
     */
    public int getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isCorrecto() {
        if (resultado == ResultadoSIA.RESULTADO_OK) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNoError() {
        if (resultado != ResultadoSIA.RESULTADO_ERROR) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isError() {
        if (resultado == ResultadoSIA.RESULTADO_ERROR) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Es enviable pero no cumple los datos.
     *
     * @return
     */
    public boolean isNoCumpleDatos() {
        if (resultado == ResultadoSIA.RESULTADO_NO_CUMPLE_DATOS) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Es no enviable.
     *
     * @return
     */
    public boolean isNoEnviable() {
        if (resultado == ResultadoSIA.RESULTADO_NO_ENVIABLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the correctos
     */
    public int getCorrectos() {
        return correctos;
    }

    /**
     * @param correctos the correctos to set
     */
    public void setCorrectos(int correctos) {
        this.correctos = correctos;
    }

    /**
     * @return the incorrectos
     */
    public int getIncorrectos() {
        return incorrectos;
    }

    /**
     * @param incorrectos the incorrectos to set
     */
    public void setIncorrectos(int incorrectos) {
        this.incorrectos = incorrectos;
    }

    /**
     * @return the codSIA
     */
    public String getCodSIA() {
        return codSIA;
    }

    /**
     * @param codSIA the codSIA to set
     */
    public void setCodSIA(String codSIA) {
        this.codSIA = codSIA;
    }

    /**
     * @return the estadoSIA
     */
    public String getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * @param estadoSIA the estadoSIA to set
     */
    public void setEstadoSIA(String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    /**
     * @return the operacion
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
}
