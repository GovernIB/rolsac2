package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Servei;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.List;

/**
 * Resposta tipada del servei de consulta de serveis.
 *
 * <p>No afegeix cap camp al JSON de RespuestaBase. La seva funció és
 * concretar que {@code items} conté objectes {@link Servei}, perquè
 * Swagger/OpenAPI mostri correctament l'esquema de retorn.</p>
 */
@XmlRootElement
@Schema(
        name = "RespostaServeis",
        description = "Resposta paginada de la consulta de serveis."
)
public class RespuestaServicios extends RespuestaBase<Servei> {

    private static final long serialVersionUID = 1L;

    public RespuestaServicios() {
        super();
    }

    public RespuestaServicios(final long total,
                              final Integer paginaTamanyo,
                              final Integer pagina,
                              final URI requestUri,
                              final List<Servei> lista,
                              final long tiempoMiliSegundos) {
        super(total, paginaTamanyo, pagina, requestUri, lista, tiempoMiliSegundos);
    }

    public RespuestaServicios(final int total,
                              final int size,
                              final Integer paginaTamanyo,
                              final Integer pagina,
                              final URI requestUri,
                              final List<Servei> lista,
                              final long tiempoMiliSegundos) {
        super(total, size, paginaTamanyo, pagina, requestUri, lista, tiempoMiliSegundos);
    }

    @Override
    @Schema(description = "Llista de serveis retornats.")
    public List<Servei> getItems() {
        return super.getItems();
    }

    @Override
    public void setItems(final List<Servei> items) {
        super.setItems(items);
    }
}
