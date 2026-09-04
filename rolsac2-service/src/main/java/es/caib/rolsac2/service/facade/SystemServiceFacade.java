package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.SesionDTO;
import es.caib.rolsac2.service.model.filtro.SesionFiltro;
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
     * Devuelve true si el dato de sesion existe y está correcto.
     *
     * @param idUsuario IdUsuario
     * @return True si ok , false si mal.
     */
    boolean comprobarDatos(Long idUsuario);

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

    Pagina<SesionDTO> findAllSesiones(String usuario);

    Pagina<SesionDTO> findByFiltro(SesionFiltro filtro);

    Boolean checkSesion(Long idUsuario);

    void deleteAllSesion();

    /**
     * Devuelve el idioma contenido de la entidad (para los perfiles adm. contenido, gestor e informador)
     *
     * @param codigo
     * @return
     */
    String getIdiomaContenidoByEntidad(Long codigo);
}
