package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.EntidadDTO;

public class UsuarioFiltro extends AbstractFiltro {
    private String texto;
    private Long id;
    private String identificador;
    private EntidadDTO entidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    public boolean isRellenoId() {
        return id != null;
    }

    public boolean isRellenoEntidad() {
        return entidad.getCodigo() != null;
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
