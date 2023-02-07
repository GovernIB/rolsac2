package es.caib.rolsac2.commons.plugins.autenticacion.api;

import es.caib.rolsac2.commons.plugins.autenticacion.api.model.UsuarioAutenticado;
import org.fundaciobit.pluginsib.core.IPlugin;

public interface IPluginAutenticacion extends IPlugin {

    UsuarioAutenticado login(String usuario, String password) throws AutenticacionErrorException;
}
