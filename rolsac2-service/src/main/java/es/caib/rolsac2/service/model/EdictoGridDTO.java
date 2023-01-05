package es.caib.rolsac2.service.model;

import java.time.LocalDate;
import java.util.Objects;

public class EdictoGridDTO {

    private String codigoTabla;


    /**
     * Tipo de boletín.
     */
    private String tipoBoletin;

    /**
     * Número del boletín. Tendrá formato NNN/YYYY
     */
    private String numeroBoletin;

    /**
     * Fecha del boletín
     */
    private LocalDate fechaBoletin;


    /**
     * Título del edicto
     */
    private Literal titulo;

    /**
     * URL del edicto
     */
    private Literal url;

    private String numeroRegistro;

    public String getCodigoTabla() {
        return codigoTabla;
    }

    public void setCodigoTabla(String codigoTabla) {
        this.codigoTabla = codigoTabla;
    }

    public String getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(String tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    public String getNumeroBoletin() {
        return numeroBoletin;
    }

    public void setNumeroBoletin(String numeroBoletin) {
        this.numeroBoletin = numeroBoletin;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public Literal getTitulo() {
        return titulo;
    }

    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    public Literal getUrl() {
        return url;
    }

    public void setUrl(Literal url) {
        this.url = url;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdictoGridDTO that = (EdictoGridDTO) o;
        return Objects.equals(tipoBoletin, that.tipoBoletin) && Objects.equals(numeroBoletin, that.numeroBoletin) && Objects.equals(fechaBoletin, that.fechaBoletin) && Objects.equals(titulo, that.titulo) && Objects.equals(url, that.url) && Objects.equals(numeroRegistro, that.numeroRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoBoletin, numeroBoletin, fechaBoletin, titulo, url, numeroRegistro);
    }

    @Override
    public String toString() {
        return "EdictoGridDTO{" +
                "tipoBoletin='" + tipoBoletin + '\'' +
                ", numeroBoletin='" + numeroBoletin + '\'' +
                ", fechaBoletin=" + fechaBoletin +
                ", titulo=" + titulo +
                ", url=" + url +
                ", numeroRegistro='" + numeroRegistro + '\'' +
                '}';
    }
}
