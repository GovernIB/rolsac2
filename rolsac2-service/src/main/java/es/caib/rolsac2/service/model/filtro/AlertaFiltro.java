package es.caib.rolsac2.service.model.filtro;

public class AlertaFiltro extends AbstractFiltro {

    private String texto;
    private String identificador;
    private Long codigo;
    private String usuario;

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

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public boolean isRellenoCodigo() {
        return codigo != null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Esta relleno el identificador
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    /**
     * Esta relleno el usuario
     **/
    public boolean isRellenoUsuario() {
        return usuario != null && !usuario.isEmpty();
    }


    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
