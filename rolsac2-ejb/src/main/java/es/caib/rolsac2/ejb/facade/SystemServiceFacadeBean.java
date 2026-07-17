package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.util.PropiedadUtil;
import es.caib.rolsac2.persistence.converter.SesionConverter;
import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.persistence.repository.ConfiguracionGlobalRepository;
import es.caib.rolsac2.persistence.repository.SesionRepository;
import es.caib.rolsac2.service.exception.PluginErrorException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.integracion.TraduccionServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.SesionFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.eclipse.microprofile.config.Config;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * EJB Ãºnic que s'executa a la inicialitzaciÃ³.
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

    @Inject
    private Config config;

    /**
     * Executat a l'inici de l'aplicaciÃ³.
     */
    @PostConstruct
    private void init() {
        // AquÃ­ es podrien llegir les opcions de configuraciÃ³, i comprovar que tots els parÃ metres necessaris hi sÃ³n,
        // o fixar els valors per defecte pels que no hi siguin, programar timers no persistents, ...
        LOG.info("Inici del mÃ²dul EJB");
        propertiesLocales = recuperarConfiguracionProperties();
    }

    /**
     * Executat quan s'atura l'aplicaciÃ³.
     */
    @PreDestroy
    private void destroy() {
        LOG.info("Aturada del mÃ²dul EJB");
    }

    @Override
    /*@RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})*/
    @PermitAll
    public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad) {
        ConfiguracionGlobalGridDTO conf = configGlobal.findByPropiedad(propiedad.toString());
        if (conf != null && conf.getValor() != null && !conf.getValor().isEmpty()) {
            return conf.getValor();
        } else {
            return this.propertiesLocales.getProperty(propiedad.toString());
        }
    }

    @Override
    /*@RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})*/
    @PermitAll
    public String obtenerPropiedadConfiguracion(String propiedad) {
        ConfiguracionGlobalGridDTO conf = configGlobal.findByPropiedad(propiedad);
        if (conf != null && conf.getValor() != null && !conf.getValor().isEmpty()) {
            return conf.getValor();
        } else {
            return this.propertiesLocales.getProperty(propiedad);
        }
    }

    @Override
    /*@RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})*/
    @PermitAll
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
        if (sesionRepository.comprobarDatos(sesionDTO)) {
            sesionRepository.borrarSessionByusuario(sesionDTO.getIdUsuario());
            sesionRepository.create(sesion);
        }
    }

    @Override
    @PermitAll
    public boolean comprobarDatos(Long idUsuario) {
        SesionDTO sesionDTO = sesionRepository.findByIdUsuario(idUsuario);
        if (sesionDTO == null) {
            return false;
        }
        return sesionRepository.comprobarDatos(sesionDTO);
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

    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    @Override
    public Pagina<SesionDTO> findAllSesiones(String usuario) {

        try {
            List<SesionDTO> items = sesionRepository.findAllSesiones(usuario);
            long total = sesionRepository.countAllSesiones();
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<SesionDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }


    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    @Override
    public Pagina<SesionDTO> findByFiltro(SesionFiltro filtro) {
        try {
            List<SesionDTO> items = sesionRepository.findPageByFiltro(filtro);
            long total = sesionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<SesionDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkSesion(Long idUsuario) {
        return sesionRepository.checkSesion(idUsuario);
    }

    @Override
    public void deleteAllSesion() {
        sesionRepository.deleteAllSesiones();
    }

    //********************************************************************************************************************************************
    //      MÃ©todos privados
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
     * FunciÃ³n encargada de instanciar un plugin
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

                    prop.put(prefijoGlobal + rplg.getPrefijoPropiedades() + propiedad.getCodigo(), PropiedadUtil.replacePlaceholders(config, valorProp));
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
