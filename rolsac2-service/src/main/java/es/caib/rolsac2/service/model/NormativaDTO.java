package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Dades de una normativa.
 *
 * @author Indra
 */
@Schema(name = "Normativa")
public class NormativaDTO extends ModelApi {
    private Long codigo;

    private BoletinOficialDTO boletin;

    private AfectacionDTO afectacion;
    private TipoNormativaDTO tipoNormativa;
    private String numero;
    private LocalDate fechaAprobacion;
    private TipoBoletinDTO tipoBoletin;
    private LocalDate fechaBoletin;
    private String numeroBoletin;
    private String urlBoletin;
    private String nombreResponsable;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public TipoNormativaDTO getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(TipoNormativaDTO tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getNumeroBoletin() {
        return numeroBoletin;
    }

    public void setNumeroBoletin(String numeroBoletin) {
        this.numeroBoletin = numeroBoletin;
    }

    public String getUrlBoletin() {
        return urlBoletin;
    }

    public void setUrlBoletin(String urlBoletin) {
        this.urlBoletin = urlBoletin;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public BoletinOficialDTO getBoletin() {
        return boletin;
    }

    public void setBoletin(BoletinOficialDTO boletin) {
        this.boletin = boletin;
    }

    public AfectacionDTO getAfectacion() {
        return afectacion;
    }

    public void setAfectacion(AfectacionDTO afectacion) {
        this.afectacion = afectacion;
    }

    public TipoBoletinDTO getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(TipoBoletinDTO tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    @Override
    public String toString() {
        return "NormativaDTO{" +
                "id=" + codigo +
                ", numero='" + numero + '\'' +
                '}';
    }
}
