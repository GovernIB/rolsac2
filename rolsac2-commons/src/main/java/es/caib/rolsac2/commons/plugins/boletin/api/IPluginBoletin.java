package es.caib.rolsac2.commons.plugins.boletin.api;

import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;
import org.fundaciobit.pluginsib.core.IPlugin;

import java.util.List;

// TODO REVISAR CAMPOS Y NORMALIZARLOS (P.E. NORMATIVA...).
// TODO VER COMO GENERALIZAR X ENTIDAD (CADA BOLETIN TIENE SU ENTIDAD)

/**
 * Plugin para integración con Boletín entidad.
 */
public interface IPluginBoletin extends IPlugin {

    /**
     * Prefijo.
     */
    public static final String TRADUCCION_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "boletin.";

    /**
     * Realiza busqueda en boletin.
     *
     * @param numeroBoletin       numero boletin
     * @param fechaBoletin        fecha boletin
     * @param numeroEdicto        numero edicto
     * @param saltarseCertificado saltarse certificado
     * @return lista de edictos
     * @throws BoletinErrorException excepcion
     */
    List<Edicto> listar(final String numeroBoletin, final String fechaBoletin, final String numeroEdicto, final Boolean saltarseCertificado) throws BoletinErrorException;

    /**
     * Obtiene boletin plugin
     *
     * @return boletin plugin
     */
    Long obtenerBoletinPlugin();

}
