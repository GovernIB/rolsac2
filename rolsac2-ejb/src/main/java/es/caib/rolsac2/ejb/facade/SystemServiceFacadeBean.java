package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.persistence.converter.SesionConverter;
import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.persistence.repository.ConfiguracionGlobalRepository;
import es.caib.rolsac2.persistence.repository.SesionRepository;
import es.caib.rolsac2.service.exception.PluginErrorException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.integracion.TraduccionServiceFacade;
import es.caib.rolsac2.service.model.ConfiguracionGlobalGridDTO;
import es.caib.rolsac2.service.model.PluginDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.SesionDTO;
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
import javax.ejb.Singleton;
import javax.ejb.Startup;
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

    @Inject
    TraduccionServiceFacade traduccionServiceFacade;

    @Inject
    SesionRepository sesionRepository;

    @Inject
    SesionConverter sesionConverter;

    @Inject
    ConfiguracionGlobalRepository configGlobal;

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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad) {
        ConfiguracionGlobalGridDTO conf = configGlobal.findByPropiedad(propiedad.toString());
        if (conf != null && conf.getValor() != null && !conf.getValor().isEmpty()) {
            return conf.getValor();
        } else {
            return this.propertiesLocales.getProperty(propiedad.toString());
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String obtenerPropiedadConfiguracion(String propiedad) {
        ConfiguracionGlobalGridDTO conf = configGlobal.findByPropiedad(propiedad);
        if (conf != null && conf.getValor() != null && !conf.getValor().isEmpty()) {
            return conf.getValor();
        } else {
            return this.propertiesLocales.getProperty(propiedad);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad, String idioma) {
        ConfiguracionGlobalGridDTO conf = configGlobal.findByPropiedad(propiedad.toString() + "." + idioma);
        if (conf != null && conf.getValor() != null && !conf.getValor().isEmpty()) {
            return conf.getValor();
        } else {
            return this.propertiesLocales.getProperty(propiedad.toString() + "." + idioma);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public IPlugin obtenerPluginEntidad(TypePluginEntidad tipoPlugin, Long idEntidad) {

        return createPlugin(administracionEntServiceFacade.listPluginsByEntidad(idEntidad), tipoPlugin.toString());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void crearSesion(SesionDTO sesionDTO) {
        JSesion sesion = sesionConverter.createEntity(sesionDTO);
        sesionRepository.create(sesion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateSesion(SesionDTO sesionDTO) {
        JSesion sesion = sesionRepository.findById(sesionDTO.getIdUsuario());
        sesionConverter.mergeEntity(sesion, sesionDTO);
        sesionRepository.update(sesion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteSesion(Long idUsuario) {
        JSesion sesion = sesionRepository.findById(idUsuario);
        sesionRepository.delete(sesion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public SesionDTO findSesionById(Long idUsuario) {
        return sesionConverter.createDTO(sesionRepository.findById(idUsuario));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkSesion(Long idUsuario) {
        return sesionRepository.checkSesion(idUsuario);
    }

    //********************************************************************************************************************************************
    //      Métodos privados
    //*********************************************************************************************************************************************/

    /**
     * Carga de propiedades locales desde fichero de properties
     *
     * @return Properties
     */
    private Properties recuperarConfiguracionProperties() {
        final String pathProperties = System.getProperty("es.caib.rolsac2.properties");
        try (FileInputStream fis = new FileInputStream(pathProperties)) {
            final Properties props = new Properties();
            props.load(fis);
            return props;
        } catch (final IOException e) {
            return null;
        }
    }


    /**
     * Función encargada de instanciar un plugin
     *
     * @param plugins Lista de plugins
     * @param plgTipo Tipo de plugin
     * @return IPlugin
     */
    private IPlugin createPlugin(final List<PluginDTO> plugins, final String plgTipo) {

        String prefijoGlobal = "es.caib.rolsac2.";

        IPlugin plg;
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
            if (rplg.getPrefijoPropiedades() != null && rplg.getPropiedades() != null && !rplg.getPropiedades().isEmpty()) {
                prop = new Properties();
                for (final Propiedad propiedad : rplg.getPropiedades()) {
                    //Se carga la propiedad en el sistema
                    final String valorProp = propiedad.getValor();

                    prop.put(prefijoGlobal + rplg.getPrefijoPropiedades() + propiedad.getCodigo(), valorProp);
                }
            }

            plg = (IPlugin) PluginsManager.instancePluginByClassName(classname, prefijoGlobal + rplg.getPrefijoPropiedades(), prop);

            if (plg == null) {
                throw new PluginErrorException("No s'ha pogut instanciar plugin de tipus " + plgTipo + " , PluginManager retorna nulo.");
            }

            return plg;

        } catch (final Exception e) {
            throw new PluginErrorException("Error al instanciar plugin " + plgTipo + " amb classname " + classname, e);
        }
    }


}
