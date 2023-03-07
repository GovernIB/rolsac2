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
     * Código utilizado para mostrar afectaciones en tabla de relaciones en Normativas
     */
    private String codigoTabla;

    /**
     * Tipo afectación
     */
    private TipoAfectacionDTO tipo;

    /**
     * Normativa origen
     */
    private NormativaGridDTO normativaOrigen;

    /**
     * Normativa afectada
     */
    private NormativaGridDTO normativaAfectada;

    public AfectacionDTO(){}

    /**
     * Obtiene el codigo.
     *
     * @return codigo codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo
     *
     * @param codigo the codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el tipo.
     *
     * @return tipo tipo
     */
    public TipoAfectacionDTO getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo the tipo
     */
    public void setTipo(TipoAfectacionDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la normativa origen.
     *
     * @return normativaOrigen normativa origen
     */
    public NormativaGridDTO getNormativaOrigen() {
        return normativaOrigen;
    }

    /**
     * Establece la normativa origen
     *
     * @param normativaOrigen the normativa origen
     */
    public void setNormativaOrigen(NormativaGridDTO normativaOrigen) {
        this.normativaOrigen = normativaOrigen;
    }

    /**
     * Obtiene la normativa afectada.
     *
     * @return normativaAfectada normativa afectada
     */
    public NormativaGridDTO getNormativaAfectada() {
        return normativaAfectada;
    }

    /**
     * Establece la normativa afectada
     *
     * @param normativaAfectada the normativa afectada
     */
    public void setNormativaAfectada(NormativaGridDTO normativaAfectada) {
        this.normativaAfectada = normativaAfectada;
    }


    public String getCodigoTabla() {
        return codigoTabla;
    }

    public void setCodigoTabla(String codigoTabla) {
        this.codigoTabla = codigoTabla;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AfectacionDTO that = (AfectacionDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(tipo.getCodigo(), that.tipo.getCodigo())
                && Objects.equals(normativaOrigen.getCodigo(), that.normativaOrigen.getCodigo()) && Objects.equals(normativaAfectada.getCodigo(), that.normativaAfectada.getCodigo());
    }
}
