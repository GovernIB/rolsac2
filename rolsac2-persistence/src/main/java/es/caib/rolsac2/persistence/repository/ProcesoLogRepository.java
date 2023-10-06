package es.caib.rolsac2.persistence.repository;


import es.caib.rolsac2.persistence.model.JProcesoLog;
import es.caib.rolsac2.service.model.ProcesoLogDTO;
import es.caib.rolsac2.service.model.ProcesoLogGridDTO;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;

import java.util.Date;
import java.util.List;

public interface ProcesoLogRepository extends CrudRepository<JProcesoLog, Long> {

    Long auditarInicioProceso(String idProceso, Long idEntidad);

    void auditarFinProceso(String idProceso, Long instanciaProceso, ResultadoProcesoProgramado resultadoProceso);

    void auditarFinErrorProceso(String idProceso, Long instanciaProceso, ResultadoProcesoProgramado resultadoProceso);

    Date obtenerUltimaEjecucion(Long idProceso);

    Integer listarTotal(final ProcesoLogFiltro filtro);

    List<ProcesoLogGridDTO> listar(final ProcesoLogFiltro filtro);

    ProcesoLogDTO obtenerProcesoLogPorCodigo(final Long codigo);

    Date obtenerUltimaEjecucionCorrecta(final Long idProceso);

    List<ProcesoLogGridDTO> listar(final String idioma, final String tipo);

    ProcesoLogDTO convertProcesoLog(JProcesoLog jProcesoLog);

    /**
     * Audita la mitad del proceso
     **/
    void auditarMitadProceso(Long instanciaProceso, String detalles);
}
