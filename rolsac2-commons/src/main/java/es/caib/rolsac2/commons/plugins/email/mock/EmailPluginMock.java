package es.caib.rolsac2.commons.plugins.email.mock;

import es.caib.rolsac2.commons.plugins.email.api.AnexoEmail;
import es.caib.rolsac2.commons.plugins.email.api.EmailPlugin;
import es.caib.rolsac2.commons.plugins.email.api.EmailPluginException;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Plugin mock email.
 *
 * @author Indra
 *
 */
public class EmailPluginMock extends AbstractPluginProperties
        implements EmailPlugin {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public EmailPluginMock() {
    }

    public EmailPluginMock(final String prefijoPropiedades,
                           final Properties properties) {
    }

    @Override
    public boolean envioEmail(List<String> destinatarios, String asunto,
            String mensaje, List<AnexoEmail> anexos)
            throws EmailPluginException {
        String dest = "";
        for (final String d : destinatarios) {
            dest += d + " ";
        }
        log.info("Simular envio mail. \nDestinatarios:" + dest + "\nAsunto: "
                + asunto + "\nCuerpo:" + mensaje);

        return true;
    }

}
