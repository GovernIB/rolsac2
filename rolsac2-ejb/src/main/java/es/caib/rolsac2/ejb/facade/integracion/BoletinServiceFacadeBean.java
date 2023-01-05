package es.caib.rolsac2.ejb.facade.integracion;

import es.caib.rolsac2.commons.plugins.boletin.api.BoletinErrorException;
import es.caib.rolsac2.commons.plugins.boletin.api.IPluginBoletin;
import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;
import es.caib.rolsac2.persistence.converter.EdictoConverter;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.integracion.BoletinServiceFacade;
import es.caib.rolsac2.service.model.EdictoGridDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(BoletinServiceFacade.class)
public class BoletinServiceFacadeBean implements BoletinServiceFacade {

    @Inject
    SystemServiceFacade systemServiceFacade;

    @Inject
    EdictoConverter edictoConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<EdictoGridDTO> listar(final String numeroBoletin, final String fechaBoletin, final String numeroEdicto, final Long idEntidad) {
        final IPluginBoletin pluginBoletin = (IPluginBoletin) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.BOLETIN, idEntidad);
        List<Edicto> edictos;
        List<EdictoGridDTO> edictosGrid = new ArrayList<>();
        try{
            edictos = pluginBoletin.listar(numeroBoletin, fechaBoletin, numeroEdicto);
            for(Edicto edicto : edictos) {
                EdictoGridDTO edictoGridDTO = new EdictoGridDTO();
                edictoGridDTO = edictoConverter.edictoToEdictoGridDTO(edicto);
                edictosGrid.add(edictoGridDTO);
            }

        } catch (BoletinErrorException e) {
            throw new RuntimeException(e);
        }
        return edictosGrid;
    }
}
