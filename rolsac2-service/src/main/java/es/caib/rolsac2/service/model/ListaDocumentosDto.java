package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * El tipo Lista documentos dto.
 */
public class ListaDocumentosDto implements Serializable {
    /**
     * Codigo
     */
    private final Integer codigo;

    /**
     * Instancia una nueva Lista documentos dto.
     *
     * @param id the id
     */
    public ListaDocumentosDto(Integer id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaDocumentosDto entity = (ListaDocumentosDto) o;
        return Objects.equals(this.codigo, entity.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + codigo + ")";
    }
}
