package es.caib.rolsac2.commons.plugins.indexacion.api;

/**
 * Excepción plugin indexación.
 *
 * @author Indra
 */
public class IPluginIndexacionExcepcion extends Exception {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public IPluginIndexacionExcepcion() {
        super();
    }

    /**
     * Constructor.
     */
    public IPluginIndexacionExcepcion(final String excepcion) {
        super(excepcion);
    }

    /**
     * Constructor.
     */
    public IPluginIndexacionExcepcion(final Exception excepcion) {
        super(excepcion);
    }
}