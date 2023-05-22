package es.caib.rolsac2.commons.plugins.email.api;

/**
 * Excepción en plugin email.
 *
 * @author Indra
 *
 */
public class EmailPluginException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmailPluginException() {
        super();
    }

    public EmailPluginException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public EmailPluginException(String arg0) {
        super(arg0);
    }

    public EmailPluginException(Throwable arg0) {
        super(arg0);
    }

}
