package es.caib.rolsac2.service.model.filtro;

public class CuadroMandoFiltro {

    private Long idUa;

    private String accion;

    private Long idEntidad;

    private String idioma;

    private String tipo;

    private String idApp;

    public Long getIdUa() {
        return idUa;
    }

    public void setIdUa(Long idUa) {
        this.idUa = idUa;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public Boolean isRellenoEntidad() {
        return idEntidad != null;
    }

    public Boolean isRellenoUa() {
        return idUa != null;
    }

    public Boolean isRellenoIdApp() {
        return idApp != null;
    }
}
