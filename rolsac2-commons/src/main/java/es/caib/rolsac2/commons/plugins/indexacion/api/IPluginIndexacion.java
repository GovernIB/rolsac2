package es.caib.rolsac2.commons.plugins.indexacion.api;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.*;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;
import org.fundaciobit.pluginsib.core.IPlugin;

// TODO INTERFACE PLUGIN BASADA EN ACTUAL IMPLEMENTACION SOLR, HAY QUE EVALUAR CAMBIO A ELASTICSEARCH

/**
 * Plugin indexación.
 */
public interface IPluginIndexacion extends IPlugin {

    /**
     * Prefijo.
     */
    public static final String TRADUCCION_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "index.";

    /**
     * Indexa contenido.
     *
     * @param dataIndexacion contenido a indexar.El contenido es multiidioma por lo que se
     *                       rellenaran los idiomas necesarios.
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion indexarContenido(DataIndexacion dataIndexacion) throws IPluginIndexacionExcepcion;

    /**
     * Indexa fichero.
     *
     * @param ficheroIndexacion fichero a indexar. Cuando se indexa un fichero sólo se puede
     *                          indexar en un idioma. Se deberá rellenar los datos en el
     *                          idioma establecido.
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion indexarFichero(FicheroIndexacion ficheroIndexacion) throws IPluginIndexacionExcepcion;

    /**
     * Desindexa elementos caducados.
     * <p>
     * Elimina del índice los elementos caducados de la aplicación para la que
     * se ha creado el indexer.
     *
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion desindexarCaducados() throws IPluginIndexacionExcepcion;

    /**
     * Desindexa elemento y sus hijos.
     * <p>
     * Elimina del índice el elemento.
     *
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion desindexar(String id, EnumCategoria categoria) throws IPluginIndexacionExcepcion;

    /**
     * Desindexa aplicación.
     * <p>
     * Elimina del índice todos los elementos de la aplicación. Se usará cuando
     * se quiere regenerar de nuevo los contenidos indexados.
     *
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion desindexarAplicacion() throws IPluginIndexacionExcepcion;

    /**
     * Desindexa categoria de una aplicación.
     * <p>
     * Elimina del índice todos los elementos de la categoria. Se usará cuando
     * se quiere regenerar de nuevo los contenidos indexados.
     *
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion desindexarCategoria(EnumCategoria categoria) throws IPluginIndexacionExcepcion;

    /**
     * Desindexa todos los datos que tengan el elemento raiz.
     *
     * @param categoriaRaiz Categoria elemento raiz.
     * @param idRaiz        Identificador elemento raiz.
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion desindexarRaiz(final String idRaiz, EnumCategoria categoriaRaiz) throws IPluginIndexacionExcepcion;

    /**
     * Realiza commit.
     * <p>
     * Al realizar un commit se hacen visibles en las búsquedas los cambios
     * pendientes. Un commit afecta a todos los cambios pendientes sobre el
     * índice realizados, independientemente de quién los haya realizado. El
     * índice esta configurado para hacer autommit para cierto tiempo, por lo
     * que esta opción sólo debe usarse cuando se requiere que se hagan
     * efectivos los cambios de forma inmediata.
     *
     * @throws IPluginIndexacionExcepcion
     */
    ResultadoAccion commit() throws IPluginIndexacionExcepcion;

    /**
     * Convierte HTML a texto.
     * <p>
     * Información que este en formato HTML debe convertirse a texto para poder añadirla como texto de búsqueda en DataIndexacion.
     *
     * @param html HTML
     * @return texto
     */
    String htmlToText(String html);

    /**
     * Realiza búsqueda.
     *
     * @param sesionId           Id de la sesion
     * @param textoBusqueda      Texto de búsqueda
     * @param idiomaBusqueda     Idioma de búsqueda
     * @param filtroBusqueda     Filtro de búsqueda
     * @param paginacionBusqueda Paginación
     * @return Resultado búsqueda
     * @throws IPluginIndexacionExcepcion Excepción
     */
    ResultadoBusqueda buscar(String sesionId, String textoBusqueda, EnumIdiomas idiomaBusqueda, FiltroBusqueda filtroBusqueda, BusquedaPaginacion paginacionBusqueda) throws IPluginIndexacionExcepcion;

}
