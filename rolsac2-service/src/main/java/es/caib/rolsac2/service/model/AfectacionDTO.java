package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Clase afectacion.
 *
 * @author Indra
 */
@Schema(name = "Afectacion")
public class AfectacionDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Tipo afectación
     */
    private TipoAfectacionDTO tipo;

    /**
     * Normativa origen
     */
    private Long normativaOrigen;

    /**
     * Normativa afectada
     */
    private Long normativaAfectada;

    /**
     * Obtiene el codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo
     *
     * @param codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el tipo.
     *
     * @return tipo
     */
    public TipoAfectacionDTO getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo
     */
    public void setTipo(TipoAfectacionDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la normativa origen.
     *
     * @return normativaOrigen
     */
    public Long getNormativaOrigen() {
        return normativaOrigen;
    }

    /**
     * Establece la normativa origen
     *
     * @param normativaOrigen
     */
    public void setNormativaOrigen(Long normativaOrigen) {
        this.normativaOrigen = normativaOrigen;
    }

    /**
     * Obtiene la normativa afectada.
     *
     * @return normativaAfectada
     */
    public Long getNormativaAfectada() {
        return normativaAfectada;
    }

    /**
     * Establece la normativa afectada
     *
     * @param normativaAfectada
     */
    public void setNormativaAfectada(Long normativaAfectada) {
        this.normativaAfectada = normativaAfectada;
    }

    @Override
    public String toString() {
        return "AfectacionDTO{" +
                "id=" + codigo +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
