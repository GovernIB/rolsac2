package es.caib.rolsac2.service.facade.integracion;

import es.caib.rolsac2.commons.plugins.email.api.AnexoEmail;
import es.caib.rolsac2.commons.plugins.email.api.EmailPluginException;

import java.util.List;

public interface EmailServiceFacade {

    /**
     * Envio de correo
     * @param destinatarios
     * @param asunto
     * @param mensaje
     * @param anexos
     * @return
     */

    boolean envioEmail(List<String> destinatarios, String asunto, String mensaje, List<AnexoEmail> anexos, Long idEntidad) throws EmailPluginException;
}
