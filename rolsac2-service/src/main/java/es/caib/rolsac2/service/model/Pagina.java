package es.caib.rolsac2.service.model;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Resultat d'una consulta paginada que agrupa la llista de resultats amb el nombre total de resultats.
 * Immutable.
 *
 * @author areus
 */
@XmlRootElement
@Schema(name = "Pagina")
public class Pagina<T> {

    private final List<T> items;
    private final long total;

    @JsonbCreator
    public Pagina(@JsonbProperty("items") List<T> items, @JsonbProperty("total") long total) {
        Objects.requireNonNull(items, "items no pot ser null");
        this.items = Collections.unmodifiableList(items);
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Pagina{" +
                "items=" + items +
                ", total=" + total +
                '}';
    }
}