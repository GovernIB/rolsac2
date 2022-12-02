package es.caib.rolsac2.service.facade.integracion;


import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;

import java.util.List;

/**
 * Acceso al plugin de Boletín
 */
public interface BoletinServiceFacade {

    /**
     * Búsqueda de los edictos en el boletín
     * @param numeroBoletin
     * @param fechaBoletin
     * @param numeroEdicto
     * @return
     */
    List<Edicto> listar(final String numeroBoletin, final String fechaBoletin, final String numeroEdicto, final Long idEntidad);


}
