package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un ProcedimientoNormativa.
 */
@Schema(name = "ProcedimientoNormativa")
public class ProcedimientoNormativaDTO {

    /**
     * Id del procedimiento
     */
    private Long codigo;

    /**
     * Nombre del procedimiento
     */
    private Literal nombre;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Literal getNombre() {
        return nombre;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoNormativaDTO that = (ProcedimientoNormativaDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre);
    }

    @Override
    public String toString() {
        return "ProcedimientoNormativaDTO{" +
                "codigo=" + codigo +
                ", nombre=" + nombre +
                '}';
    }
}
