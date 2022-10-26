package es.caib.rolsac2.service.model.filtro;

import java.time.LocalDate;

/**
 * Filtro de procedimientos.
 */
public class ProcedimientoFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewProcedimientos
     **/
    private String texto;
    private String tipo;
    private String codigoSIA;
    private String estadoSIA;
    private String siaFecha;
    private String codigoDir3SIA;

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String procTipo) {
        this.tipo = procTipo;
    }
    public String getCodigoSIA() {
        return codigoSIA;
    }
    public void setCodigoSIA(String procSiacod) {this.codigoSIA = procSiacod; }
    public String getEstadoSIA() {
        return estadoSIA;
    }
    public void setEstadoSIA(String procSiaest) {
        this.estadoSIA = procSiaest;
    }
    public String  getSiaFecha() {
        return siaFecha;
    }
    public void setSiaFecha(String procSiafc) {
        this.siaFecha = procSiafc;
    }
    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }
    public void setCodigoDir3SIA(String procSiadir3) {
        this.codigoDir3SIA = procSiadir3;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }
    public boolean isRellenoTipo() {
        return tipo != null && !tipo.isEmpty();
    }
    public boolean isRellenoCodigoSIA() {
        return codigoSIA != null && !codigoSIA.isEmpty();
    }
    public boolean isRellenoEstadoSIA() {
        return estadoSIA != null && !estadoSIA.isEmpty();
    }
    public boolean isRellenoSiaFecha() {
        return siaFecha != null && !siaFecha.isEmpty();
    }
    public boolean isRellenoCodigoDir3SIA() { return codigoDir3SIA != null && !codigoDir3SIA.isEmpty();}




    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
