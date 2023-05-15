package es.caib.rolsac2.service.model.filtro;

public class TipoPublicoObjetivoEntidadFiltro extends AbstractFiltro {

    private String texto;
    private Long codigo;
    private Long codigoTipo;
    private String identificador;
    private String traducciones;

    public TipoPublicoObjetivoEntidadFiltro() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long cod) { this.codigo = cod; }

    public Long getCodigoTipo() {
        return codigoTipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setCodigoTipo(Long codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getTraducciones() {
        return traducciones;
    }

    public void setTraducciones(String traducciones) {
        this.traducciones = traducciones;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoTraducciones() {
        return traducciones != null && !traducciones.isEmpty();
    }

    /**
     * Esta relleno el identificador
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "codigo";
    }

	public boolean isRellenoCodigo() {
		return codigo != null;
	}

	public boolean isRellenoCodigoTipo() {
		return codigoTipo != null;
	}
}
