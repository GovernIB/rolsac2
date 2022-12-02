package es.caib.rolsac2.commons.plugins.boletin.api.model;

import java.util.Date;
import java.util.HashMap;

public class Normativa {

    private String numeroBoib;
    private String validacion;
    private String idTipo;
    private String nombreTipo;
    private String idBoletin;
    private String nombreBoletin;
    private String idUA;
    private String nombreUA;
    private String valorRegistro;
    private String tipoNormativa;
    private String idTipoNormativa;

    private Date fechaBoletin;

    private HashMap<String, String> titulos;

    private HashMap<String,String> enlaces;

    private HashMap<String, String> apartados;

    private HashMap<String, String> paginaInicial;
    private HashMap<String, String> paginaFinal;
    private HashMap<String, String> observaciones;
    private HashMap<String, String> tipoPublicacion;


    public Normativa()
    {
        this.apartados = new HashMap<>();
        this.titulos = new HashMap<>();
        this.enlaces = new HashMap<>();
        this.paginaFinal = new HashMap<>();
        this.paginaInicial = new HashMap<>();
        this.observaciones = new HashMap<>();
        this.tipoPublicacion = new HashMap<>();
    }


    public void setNumeroBoib(String numeroBoib)
    {
        this.numeroBoib = numeroBoib;
    }

    public String getNumeroBoib()
    {
        return numeroBoib;
    }

    public void setValidacion(String validacion)
    {
        this.validacion = validacion;
    }

    public String getValidacion()
    {
        return validacion;
    }

    public void setIdTipo(String idTipo)
    {
        this.idTipo = idTipo;
    }

    public String getIdTipo()
    {
        return idTipo;
    }

    public void setNombreTipo(String nombreTipo)
    {
        this.nombreTipo = nombreTipo;
    }

    public String getNombreTipo()
    {
        return nombreTipo;
    }

    public void setIdBoletin(String idBoletin)
    {
        this.idBoletin = idBoletin;
    }

    public String getIdBoletin()
    {
        return idBoletin;
    }
    public void setNombreBoletin(String nombreBoletin)
    {
        this.nombreBoletin = nombreBoletin;
    }

    public String getNombreBoletin()
    {
        return nombreBoletin;
    }

    public void setIdUA(String idUA)
    {
        this.idUA = idUA;
    }

    public String getIdUA()
    {
        return idUA;
    }

    public void setNombreUA(String nombreUA)
    {
        this.nombreUA = nombreUA;
    }

    public String getNombreUA()
    {
        return nombreUA;
    }

    public void setValorRegistro(String valorRegistro)
    {
        this.valorRegistro = valorRegistro;
    }

    public String getValorRegistro()
    {
        return valorRegistro;
    }

    public String getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(String tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public String getIdTipoNormativa() {
        return idTipoNormativa;
    }

    public void setIdTipoNormativa(String idTipoNormativa) {
        this.idTipoNormativa = idTipoNormativa;
    }

    public Date getFechaBoletin() { return fechaBoletin; }

    public void setFechaBoletin(Date fechaBoletin) { this.fechaBoletin = fechaBoletin; }

    public HashMap<String, String> getTitulos() { return titulos; }

    public void setTitulos(HashMap<String, String> titulos) { this.titulos = titulos; }

    public HashMap<String, String> getEnlaces() {  return enlaces; }

    public void setEnlaces(HashMap<String, String> enlaces) { this.enlaces = enlaces; }

    public HashMap<String, String> getApartados() { return apartados; }

    public void setApartados(HashMap<String, String> apartados) { this.apartados = apartados; }

    public HashMap<String, String> getPaginaInicial() { return paginaInicial; }

    public void setPaginaInicial(HashMap<String, String> paginaInicial) { this.paginaInicial = paginaInicial; }

    public HashMap<String, String> getPaginaFinal() { return paginaFinal; }

    public void setPaginaFinal(HashMap<String, String> paginaFinal) { this.paginaFinal = paginaFinal; }

    public HashMap<String, String> getObservaciones() { return observaciones; }

    public void setObservaciones(HashMap<String, String> observaciones) { this.observaciones = observaciones; }

    public HashMap<String, String> getTipoPublicacion() { return tipoPublicacion; }

    public void setTipoPublicacion(HashMap<String, String> tipoPublicacion) { this.tipoPublicacion = tipoPublicacion; }
}
