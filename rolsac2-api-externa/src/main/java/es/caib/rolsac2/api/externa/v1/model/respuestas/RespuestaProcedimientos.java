package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Procediment;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.List;

/**
 * Resposta tipada del servei de consulta de procediments.
 *
 * <p>No afegeix cap camp al JSON de RespuestaBase. La seva funció és
 * concretar que {@code items} conté objectes {@link Procediment}, perquè
 * Swagger/OpenAPI mostri correctament l'esquema de retorn.</p>
 */
@XmlRootElement
@Schema(
        name = "RespostaProcediments",
        description = "Resposta paginada de la consulta de procediments."
)
public class RespuestaProcedimientos extends RespuestaBase<Procediment> {

    private static final long serialVersionUID = 1L;

    public RespuestaProcedimientos() {
        super();
    }

    public RespuestaProcedimientos(final long total,
                                   final Integer paginaTamanyo,
                                   final Integer pagina,
                                   final URI requestUri,
                                   final List<Procediment> lista,
                                   final long tiempoMiliSegundos) {
        super(total, paginaTamanyo, pagina, requestUri, lista, tiempoMiliSegundos);
    }

    public RespuestaProcedimientos(final int total,
                                   final int size,
                                   final Integer paginaTamanyo,
                                   final Integer pagina,
                                   final URI requestUri,
                                   final List<Procediment> lista,
                                   final long tiempoMiliSegundos) {
        super(total, size, paginaTamanyo, pagina, requestUri, lista, tiempoMiliSegundos);
    }

    @Override
    @Schema(description = "Llista de procediments retornats.")

    public List<Procediment> getItems() {
        return super.getItems();
    }

    @Override
    public void setItems(final List<Procediment> items) {
        super.setItems(items);
    }
}
