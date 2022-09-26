package es.caib.rolsac2.commons.plugins.boletin.api.model;

import java.util.Date;
import java.util.Map;

/**
 * Resultado búsqueda boletín.
 */
public class Edicto {

    /** Boletín: tipo.*/
    private String boletinTipo;
    /** Boletín: número.*/
    private String boletinNumero;
    /** Boletín: fecha.*/
    private Date boletinFecha;
    /** Edicto: número.*/
    private String edictoNumero;
    /** Edicto: texto (map <idioma, texto>).*/
    private Map<String, String> edictoTexto;
    /** Edicto: url (map <idioma, url>).*/
    private Map<String, String> edictoUrl;

    public String getBoletinTipo() {
        return boletinTipo;
    }

    public void setBoletinTipo(String boletinTipo) {
        this.boletinTipo = boletinTipo;
    }

    public String getBoletinNumero() {
        return boletinNumero;
    }

    public void setBoletinNumero(String boletinNumero) {
        this.boletinNumero = boletinNumero;
    }

    public Date getBoletinFecha() {
        return boletinFecha;
    }

    public void setBoletinFecha(Date boletinFecha) {
        this.boletinFecha = boletinFecha;
    }

    public String getEdictoNumero() {
        return edictoNumero;
    }

    public void setEdictoNumero(String edictoNumero) {
        this.edictoNumero = edictoNumero;
    }

    public Map<String, String> getEdictoTexto() {
        return edictoTexto;
    }

    public void setEdictoTexto(Map<String, String> edictoTexto) {
        this.edictoTexto = edictoTexto;
    }

    public Map<String, String> getEdictoUrl() {
        return edictoUrl;
    }

    public void setEdictoUrl(Map<String, String> edictoUrl) {
        this.edictoUrl = edictoUrl;
    }
}
