package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.Objects;

public class ListaDocumentosDto implements Serializable {
    private final Integer codigo;

    public ListaDocumentosDto(Integer id) {
        this.codigo = id;
    }

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
