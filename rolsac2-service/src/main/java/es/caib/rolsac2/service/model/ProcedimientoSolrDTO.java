package es.caib.rolsac2.service.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un ProcedimientoSolrDTO.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoSolrDTO")
public class ProcedimientoSolrDTO {

    private ProcedimientoDTO procedimientoDTO;

    private ServicioDTO servicioDTO;
    private DataIndexacion dataIndexacion;

    private PathUA pathUA;

    public ProcedimientoDTO getProcedimientoDTO() {
        return procedimientoDTO;
    }

    public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
        this.procedimientoDTO = procedimientoDTO;
    }

    public DataIndexacion getDataIndexacion() {
        return dataIndexacion;
    }

    public void setDataIndexacion(DataIndexacion dataIndexacion) {
        this.dataIndexacion = dataIndexacion;
    }

    public ServicioDTO getServicioDTO() {
        return servicioDTO;
    }

    public void setServicioDTO(ServicioDTO servicioDTO) {
        this.servicioDTO = servicioDTO;
    }

    public PathUA getPathUA() {
        return pathUA;
    }

    public void setPathUA(PathUA pathUA) {
        this.pathUA = pathUA;
    }
}
