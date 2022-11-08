package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;

/**
 * Filtro de procedimientos.
 */
public class ProcedimientoFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewProcedimientos
     **/
    private String texto;
    private String tipo;
    private Long codigoSIA;
    private String estadoSIA;
    private String siaFecha;
    private String codigoDir3SIA;

    private String volcadoSIA;

    private TipoSilencioAdministrativoDTO silencioAdministrativo;

    private TipoProcedimientoDTO tipoProcedimiento;

    private TipoFormaInicioDTO formaInicio;

    private TipoPublicoObjetivoDTO publicoObjetivo;


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

    public Long getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Long procSiacod) {
        this.codigoSIA = procSiacod;
    }

    public String getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(String procSiaest) {
        this.estadoSIA = procSiaest;
    }

    public String getSiaFecha() {
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

    public TipoSilencioAdministrativoDTO getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    public void setSilencioAdministrativo(TipoSilencioAdministrativoDTO silencioAdministrativo) {
        this.silencioAdministrativo = silencioAdministrativo;
    }

    public TipoProcedimientoDTO getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimientoDTO tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public TipoFormaInicioDTO getFormaInicio() {
        return formaInicio;
    }

    public void setFormaInicio(TipoFormaInicioDTO formaInicio) {
        this.formaInicio = formaInicio;
    }

    public TipoPublicoObjetivoDTO getPublicoObjetivo() {
        return publicoObjetivo;
    }

    public void setPublicoObjetivo(TipoPublicoObjetivoDTO publicoObjetivo) {
        this.publicoObjetivo = publicoObjetivo;
    }

    public String getVolcadoSIA() {
        return volcadoSIA;
    }

    public void setVolcadoSIA(String volcadoSIA) {
        this.volcadoSIA = volcadoSIA;
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
        return codigoSIA != null;
    }

    public boolean isRellenoEstadoSIA() {
        return estadoSIA != null && !estadoSIA.isEmpty();
    }

    public boolean isRellenoSiaFecha() {
        return siaFecha != null && !siaFecha.isEmpty();
    }

    public boolean isRellenoCodigoDir3SIA() {
        return codigoDir3SIA != null && !codigoDir3SIA.isEmpty();
    }

    public boolean isRellenoSilencioAdministrativo() {
        return silencioAdministrativo != null && silencioAdministrativo.getCodigo() != null;
    }

    public boolean isRellenoTipoProcedimiento() {
        return tipoProcedimiento != null && tipoProcedimiento.getCodigo() != null;
    }

    public boolean isRellenoFormaInicio() {
        return formaInicio != null && formaInicio.getCodigo() != null;
    }

    public boolean isRellenoPublicoObjetivo() {
        return publicoObjetivo != null && publicoObjetivo.getCodigo() != null;
    }

    public boolean isRellenoVolcadoSIA() {
        return volcadoSIA != null && !volcadoSIA.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
