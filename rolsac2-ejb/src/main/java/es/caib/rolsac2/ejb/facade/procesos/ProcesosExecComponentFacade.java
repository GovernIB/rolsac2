package es.caib.rolsac2.ejb.facade.procesos;

import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;

import java.util.List;

/**
 * Component auxiliar procesos (auditoría procesos, etc.).
 *
 * @author Indra
 */
public interface ProcesosExecComponentFacade {

    /**
     * Calcula procesos que deben ejecutarse.
     *
     * @return procesos que deben ejecutarse.
     */
    List<String> calcularProcesosEjecucion(Long idEntidad);

    /**
     * Obtiene parámetros ejecución proceso.
     *
     * @param idProceso Id proceso
     * @return parámetros ejecución proceso.
     */
    List<Propiedad> obtenerParametrosProceso(final String idProceso, final Long idEntidad);

    /**
     * Audita inicio proceso.
     *
     * @param idProceso id proceso
     * @return instancia proceso
     */
    Long auditarInicioProceso(String idProceso, Long idEntidad);

    /**
     * Audita fin proceso.
     *
     * @param idProceso        id proceso
     * @param instanciaProceso instancia proceso
     * @param resultadoProceso resultado
     */
    void auditarFinProceso(String idProceso, Long instanciaProceso, ResultadoProcesoProgramado resultadoProceso);

    /**
     * Verifica si la instancia está configurada como maestro para ejecutar los procesos.
     *
     * @param instanciaId Id instancia
     * @return true si es maestro
     */
    boolean verificarMaestro(String instanciaId);

}
