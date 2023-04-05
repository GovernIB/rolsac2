package es.caib.rolsac2.ejb.facade.integracion;

import es.caib.rolsac2.commons.plugins.boletin.api.BoletinErrorException;
import es.caib.rolsac2.commons.plugins.boletin.api.IPluginBoletin;
import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;
import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.dir3.api.IPluginDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.UnidadOrganica;
import es.caib.rolsac2.persistence.converter.EdictoConverter;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.integracion.BoletinServiceFacade;
import es.caib.rolsac2.service.facade.integracion.Dir3ServiceFacade;
import es.caib.rolsac2.service.model.EdictoGridDTO;
import es.caib.rolsac2.service.model.UnidadOrganicaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(Dir3ServiceFacade.class)
public class Dir3ServiceFacadeBean implements Dir3ServiceFacade {

    @Inject
    SystemServiceFacade systemServiceFacade;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadOrganicaDTO> obtenerArbolUnidades(final Long idEntidad, final ParametrosDir3 parametrosDir3, String dir3Raiz) throws Dir3ErrorException {
        final IPluginDir3 pluginDir3 = (IPluginDir3) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.DIR3, idEntidad);
        List<UnidadOrganica> unidades = pluginDir3.obtenerArbolUnidades(parametrosDir3);
        return convertirUnidades(unidades, dir3Raiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadOrganicaDTO> obtenerHistoricosFinales(final Long idEntidad, final ParametrosDir3 parametrosDir3) throws Dir3ErrorException {
        final IPluginDir3 pluginDir3 = (IPluginDir3) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.DIR3, idEntidad);
        List<UnidadOrganica> unidades = pluginDir3.obtenerHistoricosFinales(parametrosDir3);
        return convertirUnidades(unidades);
    }

    /**
     * Función utilizada para convertir las unidades al modelo utilizado por la vista. Se pasa
     * el código DIR3 de la raíz porque este no viene a nulo en la respuesta del API
     * @param unidadesOrganicas
     * @param dir3Raiz
     * @return
     */
    private List<UnidadOrganicaDTO> convertirUnidades(List<UnidadOrganica> unidadesOrganicas, String dir3Raiz) {
        List<UnidadOrganicaDTO> unidades = new ArrayList<>();
        for(UnidadOrganica unidadOrganica : unidadesOrganicas) {
            UnidadOrganicaDTO unidad = new UnidadOrganicaDTO();
            unidad.setDenominacion(unidadOrganica.getDenominacion());
            unidad.setCodigoDir3(unidadOrganica.getCodigo());
            unidad.setVersion(unidadOrganica.getVersion());
            if(dir3Raiz.equals(unidadOrganica.getCodigo())) {
                unidad.setCodigoDir3Padre(null);
            } else {
                unidad.setCodigoDir3Padre(unidadOrganica.getCodUnidadSuperior());
            }
            unidades.add(unidad);
        }
        return unidades;
    }

    /**
     * Función que se utiliza para convertir la respuesta del API al modelo utilizado en la vista.
     * @param unidadesOrganicas
     * @return
     */
    private List<UnidadOrganicaDTO> convertirUnidades(List<UnidadOrganica> unidadesOrganicas) {
        List<UnidadOrganicaDTO> unidades = new ArrayList<>();
        for(UnidadOrganica unidadOrganica : unidadesOrganicas) {
            UnidadOrganicaDTO unidad = new UnidadOrganicaDTO();
            unidad.setDenominacion(unidadOrganica.getDenominacion());
            unidad.setCodigoDir3(unidadOrganica.getCodigo());
            unidad.setCodigoDir3Padre(unidadOrganica.getCodUnidadSuperior());
            unidad.setVersion(unidadOrganica.getVersion());
            unidades.add(unidad);
        }
        return unidades;
    }

}
