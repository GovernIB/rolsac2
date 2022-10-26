package es.caib.rolsac2.service.model.filtro;


public class SeccionFiltro extends AbstractFiltro {

    private String texto;
    private String identificador;
    private Long entidad;
    private String nombre;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    /**
     * Esta relleno el nombre
     **/
    public boolean isRellenoNombre() {
        return nombre != null && !nombre.isEmpty();
    }

    /**
     * Esta relleno el identificador
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    /**
     * Esta relleno el codEnti
     **/
    public boolean isRellenoEntidad() {
        return entidad != null;
    }

    @Override
    protected String getDefaultOrder() {
        return "codigo";
    }
}
