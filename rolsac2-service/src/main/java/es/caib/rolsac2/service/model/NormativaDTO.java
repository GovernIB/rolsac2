package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Dades de una normativa.
 *
 * @author Indra
 */
@Schema(name = "Normativa")
public class NormativaDTO extends ModelApi {
    private Long codigo;

    private BoletinOficialDTO boletinOficial;

    //private AfectacionDTO afectacion;
    private TipoNormativaDTO tipoNormativa;
    private String numero;
    private LocalDate fechaAprobacion;
    //private TipoBoletinDTO tipoBoletin;
    private LocalDate fechaBoletin;
    private String numeroBoletin;
    private String urlBoletin;
    private String nombreResponsable;
    private Literal nombre;
    private EntidadDTO entidad;

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

    public BoletinOficialDTO getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(BoletinOficialDTO boletin) {
        this.boletinOficial = boletin;
    }

    public Literal getNombre() {
        return nombre;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormativaDTO that = (NormativaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "NormativaDTO{" +
                "id=" + codigo +
                ", numero='" + numero + '\'' +
                '}';
    }
}
