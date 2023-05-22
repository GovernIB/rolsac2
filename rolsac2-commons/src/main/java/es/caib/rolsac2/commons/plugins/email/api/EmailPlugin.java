package es.caib.rolsac2.commons.plugins.email.api;

import org.fundaciobit.pluginsib.core.IPlugin;

import java.util.List;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public interface EmailPlugin extends IPlugin {

    /** Prefix. */
    public static final String EMAIL_BASE_PROPERTY = IPLUGIN_BASE_PROPERTIES;

    /**
     * Realiza envio email.
     *
     * @param destinatarios
     *            Destinatarios
     * @param asunto
     *            Asunto
     * @param mensaje
     *            Mensaje
     * @param anexos
     *            anexos
     * @return boolean
     */
    boolean envioEmail(List<String> destinatarios, String asunto,
            String mensaje, List<AnexoEmail> anexos)
            throws EmailPluginException;

}
