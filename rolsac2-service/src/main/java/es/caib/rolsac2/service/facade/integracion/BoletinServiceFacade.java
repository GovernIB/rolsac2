package es.caib.rolsac2.service.facade.integracion;


import es.caib.rolsac2.service.model.EdictoGridDTO;

import java.util.List;

/**
 * Acceso al plugin de Boletín
 */
public interface BoletinServiceFacade {

    /**
     * Búsqueda de los edictos en el boletín
     *
     * @param numeroBoletin
     * @param fechaBoletin
     * @param numeroEdicto
     * @return
     */
    List<EdictoGridDTO> listar(final String numeroBoletin, final String fechaBoletin, final String numeroEdicto, final Long idEntidad);

    Long obtenerBoletinPlugin(final Long idEntidad);


}
