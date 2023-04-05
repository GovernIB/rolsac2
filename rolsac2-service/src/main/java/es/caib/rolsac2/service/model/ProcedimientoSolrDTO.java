package es.caib.rolsac2.service.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

/**
 * Dades d'un ProcedimientoSolrDTO.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoSolrDTO")
public class ProcedimientoSolrDTO {

    private ProcedimientoDTO procedimientoDTO;

    private NormativaDTO normativaDTO;

    private UnidadAdministrativaDTO unidadAdministrativaDTO;
    private ServicioDTO servicioDTO;
    private DataIndexacion dataIndexacion;

    private IndexFile indexFile;

    private PathUA pathUA;

    private List<PathUA> pathUAs;

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

    public NormativaDTO getNormativaDTO() {
        return normativaDTO;
    }

    public void setNormativaDTO(NormativaDTO normativaDTO) {
        this.normativaDTO = normativaDTO;
    }

    public PathUA getPathUA() {
        return pathUA;
    }

    public void setPathUA(PathUA pathUA) {
        this.pathUA = pathUA;
    }

    public List<PathUA> getPathUAs() {
        return pathUAs;
    }

    public void setPathUAs(List<PathUA> pathUAs) {
        this.pathUAs = pathUAs;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativaDTO() {
        return unidadAdministrativaDTO;
    }

    public void setUnidadAdministrativaDTO(UnidadAdministrativaDTO unidadAdministrativaDTO) {
        this.unidadAdministrativaDTO = unidadAdministrativaDTO;
    }

    public IndexFile getIndexFile() {
        return indexFile;
    }

    public void setIndexFile(IndexFile indexFile) {
        this.indexFile = indexFile;
    }
}
