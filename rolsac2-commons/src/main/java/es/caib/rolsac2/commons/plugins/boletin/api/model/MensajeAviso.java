package es.caib.rolsac2.commons.plugins.boletin.api.model;

public class MensajeAviso {

    private String cabecera="";
    private String subcabecera="";
    private String descripcion="";

    public MensajeAviso()
    {
    }


    public void setCabecera(String cabecera)
    {
        this.cabecera = cabecera;
    }


    public String getCabecera()
    {
        return cabecera;
    }


    public void setSubcabecera(String subcabecera)
    {
        this.subcabecera = subcabecera;
    }


    public String getSubcabecera()
    {
        return subcabecera;
    }


    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }


    public String getDescripcion()
    {
        return descripcion;
    }
}
