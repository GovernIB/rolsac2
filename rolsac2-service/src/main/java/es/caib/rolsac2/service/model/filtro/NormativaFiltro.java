package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;

import java.time.LocalDate;
import java.util.List;

public class NormativaFiltro extends AbstractFiltro {

    private String texto;

    private TipoNormativaDTO tipoNormativa;

    private TipoBoletinDTO tipoBoletin;

    private LocalDate fechaAprobacion;

    private LocalDate fechaBoletin;

    private boolean hijasActivas = false;

    private List<Long> idUAsHijas;

    private boolean todasUnidadesOrganicas = false;

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

    public TipoNormativaDTO getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(TipoNormativaDTO tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public TipoBoletinDTO getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(TipoBoletinDTO tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public boolean isHijasActivas() {
        return hijasActivas;
    }

    public void setHijasActivas(boolean hijasActivas) {
        this.hijasActivas = hijasActivas;
    }

    public List<Long> getIdUAsHijas() {
        return idUAsHijas;
    }

    public void setIdUAsHijas(List<Long> idUAsHijas) {
        this.idUAsHijas = idUAsHijas;
    }

    public boolean isTodasUnidadesOrganicas() {
        return todasUnidadesOrganicas;
    }

    public void setTodasUnidadesOrganicas(boolean todasUnidadesOrganicas) {
        this.todasUnidadesOrganicas = todasUnidadesOrganicas;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoFechaAprobacion() { return fechaAprobacion != null; }

    public boolean isRellenoFechaBoletin() { return fechaBoletin != null; }

    public boolean isRellenoTipoNormativa() {
        return tipoNormativa != null && tipoNormativa.getCodigo() != null;
    }

    public boolean isRellenoTipoBoletin() {
        return tipoBoletin != null && tipoBoletin.getCodigo() != null;
    }

    public boolean isRellenoHijasActivas() {
        return hijasActivas;
    }

    public boolean isRellenoTodasUnidadesOrganicas() { return this.todasUnidadesOrganicas;
    }
}
