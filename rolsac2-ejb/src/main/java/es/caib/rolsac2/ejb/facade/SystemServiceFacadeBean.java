package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.service.exception.PluginErrorException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.PluginDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * EJB únic que s'executa a la inicialització.
 */
@Singleton
@Startup
public class SystemServiceFacadeBean implements SystemServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(SystemServiceFacadeBean.class);

    private Properties propertiesLocales;

    @Inject
    AdministracionEntServiceFacade administracionEntServiceFacade;

    /**
     * Executat a l'inici de l'aplicació.
     */
    @PostConstruct
    private void init() {
        // Aquí es podrien llegir les opcions de configuració, i comprovar que tots els paràmetres necessaris hi són,
        // o fixar els valors per defecte pels que no hi siguin, programar timers no persistents, ...
        LOG.info("Inici del mòdul EJB");
        propertiesLocales = recuperarConfiguracionProperties();
    }

    /**
     * Executat quan s'atura l'aplicació.
     */
    @PreDestroy
    private void destroy() {
        LOG.info("Aturada del mòdul EJB");
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad) {
        return this.propertiesLocales.getProperty(propiedad.toString());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad, String idioma) {
        return this.propertiesLocales.getProperty(propiedad.toString() + "." + idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public IPlugin obtenerPluginEntidad(TypePluginEntidad tipoPlugin, Long idEntidad) {

        return createPlugin(administracionEntServiceFacade.listPluginsByEntidad(idEntidad), tipoPlugin.toString());
    }

    /*********************************************************************************************************************************************
     * Métodos privados
     **********************************************************************************************************************************************/

    /**
     * Carga de propiedades locales desde fichero de properties
     */

    /* Carga de propiedades locales de fichero de properties*/
    private Properties recuperarConfiguracionProperties() {
        final String pathProperties = System.getProperty("es.caib.rolsac2.properties");
        try (FileInputStream fis = new FileInputStream(pathProperties);) {
            final Properties props = new Properties();
            props.load(fis);
            return props;
        } catch (final IOException e) {
            return null;
        }
    }


    /**
     * Función encargada de instanciar un plugin
     * @param plugins
     * @param plgTipo
     * @return
     */
    private IPlugin createPlugin(final List<PluginDTO> plugins, final String plgTipo) {

        /**
         * Como aún no está implementada la parte de configuración, dejamos el prefijo global harcodeado
         * TODO: Añadir la lógica de leer el prefijo global de los plugins una vez implementada la config
         */
        /*String prefijoGlobal = this.getPropiedadGlobal(TypePropiedadConfiguracion.PLUGINS_PREFIJO);
        if (prefijoGlobal == null) {
            throw new PluginErrorException("No s'ha definit propietat global per prefix global per plugins: "
                    + TypePropiedadConfiguracion.PLUGINS_PREFIJO);
        }
        if (!prefijoGlobal.endsWith(".")) {
            prefijoGlobal = prefijoGlobal + ".";
        }*/

        String prefijoGlobal = "es.caib.rolsac2.";

        IPlugin plg = null;
        PluginDTO rplg = null;
        String classname = null;
        try {
            for (final PluginDTO p : plugins) {
                if (p.getTipo().equals(plgTipo)) {
                    rplg = p;
                    break;
                }
            }

            if (rplg == null) {
                throw new PluginErrorException("No existeix plugin de tipus " + plgTipo);
            }

            classname = rplg.getClassname();

            Properties prop = null;
            if (rplg.getPrefijoPropiedades() != null && rplg.getPropiedades() != null
                    && !rplg.getPropiedades().isEmpty()) {
                prop = new Properties();
                for (final Propiedad propiedad : rplg.getPropiedades()) {
                    //Se carga la propiedad en el sistema
                    final String valorProp = propiedad.getValor();

                    prop.put(prefijoGlobal + rplg.getPrefijoPropiedades() + propiedad.getCodigo(), valorProp);
                }
            }

            plg = (IPlugin) PluginsManager.instancePluginByClassName(classname, prefijoGlobal + rplg.getPrefijoPropiedades(), prop);

            if (plg == null) {
                throw new PluginErrorException(
                        "No s'ha pogut instanciar plugin de tipus " + plgTipo + " , PluginManager retorna nulo.");
            }

            return plg;

        } catch (final Exception e) {
            throw new PluginErrorException("Error al instanciar plugin " + plgTipo + " amb classname " + classname, e);
        }
    }


}
