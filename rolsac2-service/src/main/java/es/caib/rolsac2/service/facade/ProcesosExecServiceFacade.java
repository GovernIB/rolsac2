package es.caib.rolsac2.service.facade;

import java.io.Serializable;
import java.util.List;

/**
 * Service para ejecución de procesos.
 *
 * @author Indra
 *
 */

public interface  ProcesosExecServiceFacade extends Serializable {

    /**
     * Ejecutar proceso.
     *
     * @param idProceso identificador proceso
     */
    void ejecutarProceso(String idProceso);

    /**
     * Calcula procesos que deben ejecutarse.
     *
     * @return procesos que deben ejecutarse.
     */
    List<String> calcularProcesosEjecucion();

    /**
     * Verifica si la instancia está configurada como maestro para ejecutar los procesos.
     *
     * @param instanciaId Id instancia
     * @return true si es maestro
     */
    boolean verificarMaestro(String instanciaId);

}
