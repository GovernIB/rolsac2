package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Afectacion")
public class AfectacionDTO extends ModelApi {

    private Long codigo;

    private TipoAfectacionDTO tipo;

    private Long normativaOrigen;

    private Long normativaAfectada;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public TipoAfectacionDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoAfectacionDTO tipo) {
        this.tipo = tipo;
    }

    public Long getNormativaOrigen() {
        return normativaOrigen;
    }

    public void setNormativaOrigen(Long normativaOrigen) {
        this.normativaOrigen = normativaOrigen;
    }

    public Long getNormativaAfectada() {
        return normativaAfectada;
    }

    public void setNormativaAfectada(Long normativaAfectada) {
        this.normativaAfectada = normativaAfectada;
    }

    @Override
    public String toString() {
        return "AfectacionDTO{" +
                "id=" + codigo +
                '}';
    }
}
