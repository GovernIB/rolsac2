package es.caib.rolsac2.ejb.facade.integracion;

import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccionException;
import es.caib.rolsac2.commons.plugins.traduccion.api.Idioma;
import es.caib.rolsac2.commons.plugins.traduccion.translatorib.TranslatorIBPlugin;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.integracion.TraduccionServiceFacade;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;

@Stateless
@Local(TraduccionServiceFacade.class)
public class TraduccionServiceFacadeBean implements TraduccionServiceFacade {
    @Inject
    SystemServiceFacade systemServiceFacade;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String traducir(String tipoEntrada, String textoEntrada, Idioma idiomaEntrada, Idioma idiomaSalida, Map<String, String> opciones, Long idEntidad) throws IPluginTraduccionException {
        final TranslatorIBPlugin translatorIBPlugin = (TranslatorIBPlugin) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.TRADUCCION, idEntidad);
        String resultadoTraduccion;
        resultadoTraduccion = translatorIBPlugin.traducir(tipoEntrada, textoEntrada, idiomaEntrada, idiomaSalida, opciones);
        return resultadoTraduccion;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean tradExiste(Long idEntidad) {
        return (TranslatorIBPlugin) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.TRADUCCION, idEntidad) != null;
    }
}
