package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.Objects;

public class ListaDocumentosDto implements Serializable {
    private final Integer id;

    public ListaDocumentosDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaDocumentosDto entity = (ListaDocumentosDto) o;
        return Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }
}
