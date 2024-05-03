package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.ListaPropiedades;

/**
 * Instancia service.
 *
 * @author Indra
 */
public interface ProcesoTimerServiceFacade {


    /**
     * Iniciar el timer de los procesos
     */
    void initTimer(String idInstancia);

    /**
     * Lógica para el procesado
     */
    void procesar();

    /**
     * Metodo utilizado para el proceso manual de los procesos
     *
     * @param proceso
     * @param idEntidad
     */
    void procesadoManual(String proceso, Long idEntidad);

    /**
     * Metodo utilizado para el proceso manual de los procesos indicando los parámetros necesarios.
     *
     * @param proceso
     * @param listaPropiedades
     * @param idEntidad
     */
    void procesadoManual(String proceso, ListaPropiedades listaPropiedades, Long idEntidad);
}
