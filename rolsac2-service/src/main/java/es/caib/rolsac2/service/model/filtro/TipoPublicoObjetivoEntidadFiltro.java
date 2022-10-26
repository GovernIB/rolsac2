package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Literal;

public class TipoPublicoObjetivoEntidadFiltro extends AbstractFiltro {

    private String texto;
    private Long codigo;
    private Long codigoEntidad;
    private Long codigoTipo;
    private String identificador;

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


    public Long getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(Long codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Long getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipoo(Long cod) { this.codigoTipo = cod; }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "codigo";
    }
}
