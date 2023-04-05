package es.caib.rolsac2.service.facade.integracion;


import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.service.model.EdictoGridDTO;
import es.caib.rolsac2.service.model.UnidadOrganicaDTO;

import java.util.List;

/**
 * Acceso al plugin de Boletín
 */
public interface Dir3ServiceFacade {

    /**
     *  Obtiene el árbol de unidades
     * @param idEntidad
     * @param parametrosDir3
     * @param dir3Raiz
     * @return
     * @throws Dir3ErrorException
     */
    List<UnidadOrganicaDTO> obtenerArbolUnidades(final Long idEntidad, final ParametrosDir3 parametrosDir3, String dir3Raiz) throws Dir3ErrorException;

    /**
     * Obtiene las unidades finales a las cuales ha evolucionado la unidad que se consulta.
     * @param idEntidad
     * @param parametrosDir3
     * @return
     * @throws Dir3ErrorException
     */
    List<UnidadOrganicaDTO> obtenerHistoricosFinales(final Long idEntidad, final ParametrosDir3 parametrosDir3) throws Dir3ErrorException;

}
