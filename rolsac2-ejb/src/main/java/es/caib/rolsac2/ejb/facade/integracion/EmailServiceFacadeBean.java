package es.caib.rolsac2.ejb.facade.integracion;

import es.caib.rolsac2.commons.plugins.email.api.AnexoEmail;
import es.caib.rolsac2.commons.plugins.email.api.EmailPluginException;
import es.caib.rolsac2.commons.plugins.email.emailSmtp.EmailSmtpPlugin;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.integracion.EmailServiceFacade;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
@Local(EmailServiceFacade.class)
public class EmailServiceFacadeBean implements EmailServiceFacade {

    @Inject
    SystemServiceFacade systemServiceFacade;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean envioEmail(List<String> destinatarios, String asunto, String mensaje, List<AnexoEmail> anexos, Long idEntidad) throws EmailPluginException {
        final EmailSmtpPlugin emailSmtpPlugin = (EmailSmtpPlugin) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.EMAIL, idEntidad);
        boolean resultado = false;
        resultado = emailSmtpPlugin.envioEmail(destinatarios, asunto, mensaje, anexos);
        return resultado;
    }
}
