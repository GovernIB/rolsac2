package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import java.io.Serializable;
import java.util.List;

/**
 * Service para ejecución de procesos.
 *
 * @author Indra
 */

public interface ProcesosExecServiceFacade extends Serializable {

    /**
     * Ejecutar proceso.
     *
     * @param idProceso identificador proceso
     */
    void ejecutarProceso(String idProceso, Long idEntidad);



    void ejecutarProceso(String idProceso, ListaPropiedades params, Long idEntidad);

    /**
     * Calcula procesos que deben ejecutarse.
     *
     * @return procesos que deben ejecutarse.
     */
    List<String> calcularProcesosEjecucion(Long idEntidad);

    /**
     * Verifica si la instancia está configurada como maestro para ejecutar los procesos.
     *
     * @param instanciaId Id instancia
     * @return true si es maestro
     */
    boolean verificarMaestro(String instanciaId);

}
