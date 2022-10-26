package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.EntidadDTO;

public class PlatTramitElectronicaFiltro extends AbstractFiltro{

    private String texto;
    private Long codigo;
    private String identificador;
    private EntidadDTO entidad;

    @Override
    protected String getDefaultOrder() {
        return "id";
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

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public EntidadDTO getIdentidad() {
        return entidad;
    }

    public void setIdentidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    public boolean isRellenoCodigo() {
        return codigo != null;
    }

    public boolean isRellenoEntidad() {
        return entidad.getCodigo() != null;
    }

}
