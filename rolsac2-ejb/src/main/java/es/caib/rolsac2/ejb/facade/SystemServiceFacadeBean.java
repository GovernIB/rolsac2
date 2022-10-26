package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.service.facade.SystemServiceBean;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * EJB únic que s'executa a la inicialització.
 *
 * @author areus
 */
@Singleton
@Startup
public class SystemServiceFacadeBean implements SystemServiceBean {

    private static final Logger LOG = LoggerFactory.getLogger(SystemServiceFacadeBean.class);

    private Properties propertiesLocales;

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
}
