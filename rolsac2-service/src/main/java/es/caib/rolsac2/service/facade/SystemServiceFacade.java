package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.SesionDTO;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.fundaciobit.pluginsib.core.IPlugin;

/*Interface de SystemService*/
public interface SystemServiceFacade {

    /**
     * Método utilizado para obtener las propiedades de configuración
     */
    String obtenerPropiedadConfiguracion(String propiedad);

    /**
     * Método utilizado para obtener las propiedades de configuración
     */
    String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

    /**
     * Método utilizado para obtener las propiedades de configuración pasando el idioma como parámetro.
     */
    String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad, String idioma);


    /**
     * Obtiene tipo plugin entidad.
     *
     * @param tipoPlugin tipo plugin
     * @return Plugin
     */
    IPlugin obtenerPluginEntidad(TypePluginEntidad tipoPlugin, Long idEntidad);


    void crearSesion(SesionDTO sesionDTO);

    void updateSesion(SesionDTO sesionDTO);

    void deleteSesion(Long idUsuario);

    SesionDTO findSesionById(Long idUsuario);

    Boolean checkSesion(Long idUsuario);

}
