package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un Tramite.
 *
 * @author Indra
 */
@Schema(name = "Tramite")
public class TramiteDTO extends ModelApi {
    private Long codigo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "TramiteDTO{" +
                "id=" + codigo +
                '}';
    }
}
