package es.caib.rolsac2.commons.plugins.indexacion.solr;

import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacionExcepcion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.*;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;
import es.caib.solr.api.SolrFactory;
import es.caib.solr.api.SolrIndexer;
import es.caib.solr.api.SolrSearcher;
import es.caib.solr.api.exception.ExcepcionSolrApi;
import es.caib.solr.api.model.FilterSearch;
import es.caib.solr.api.model.MultilangLiteral;
import es.caib.solr.api.model.PaginationSearch;
import es.caib.solr.api.model.PathUO;
import es.caib.solr.api.model.ResultData;
import es.caib.solr.api.model.StoredData;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Plugin de indexacion SOLR / Elastic
 */
public class PluginIndexacionSolr extends AbstractPluginProperties implements IPluginIndexacion {

    private static final Logger LOG = LoggerFactory.getLogger(PluginIndexacionSolr.class);

    /**
     * Prefijo de la implementacion.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "solr.";

    /**
     * Constructor.
     *
     * @param prefijoPropiedades Prefijo props.
     * @param properties         Propiedades plugins.
     */
    public PluginIndexacionSolr(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }


    private List<PathUA> getUas(List<PathUO> uos) {
        List<PathUA> pathUOs = new ArrayList<>();
        for (PathUO ua : uos) {
            PathUA uo = new PathUA();
            uo.setPath(ua.getPath());
            pathUOs.add(uo);
        }

        return pathUOs;
    }

    @Override
    public ResultadoAccion indexarContenido(DataIndexacion dataIndexacion) throws IPluginIndexacionExcepcion {

        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);


        //Es el unico momento para convertir el html de la descripcion en texto plano
        if (dataIndexacion.getCategoria() == EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA && dataIndexacion.getDescripcionHTML() != null) {
            LiteralMultilang descripcion = new LiteralMultilang();
            for (String idioma : dataIndexacion.getDescripcionHTML().getIdiomas()) {
                if (dataIndexacion.getDescripcionHTML().get(EnumIdiomas.fromString(idioma)) != null) {
                    String textoHTML = dataIndexacion.getDescripcionHTML().get(EnumIdiomas.fromString(idioma));
                    String texto = solrIndexer.htmlToText(textoHTML);
                    descripcion.addIdioma(EnumIdiomas.fromString(idioma), texto);
                }
            }
            dataIndexacion.setDescripcion(descripcion);
        }

        try {
            solrIndexer.indexarContenido(dataIndexacion.cast());
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    private String getPropiedadSolrUrl() {
        return getPropiedad("urlSolr");
    }

    private String getPropiedadSolrUsr() {
        return getPropiedad("usrSolr");
    }

    private String getPropiedadSolrIndex() {
        return getPropiedad("indexSolr");
    }

    private String getPropiedadSolrPwd() {
        return getPropiedad("pwdSolr");
    }

    private boolean isPropiedadSolrActivo() {
        return getPropiedad("activoSolr") != null && "true".equalsIgnoreCase(getPropiedad("activoSolr"));
    }

    private String getPropiedadElasticUrl() {
        return getPropiedad("urlElastic");
    }

    private String getPropiedadElasticUsr() {
        return getPropiedad("usrElastic");
    }

    private String getPropiedadElasticPwd() {
        return getPropiedad("pwdElastic");
    }

    private boolean isPropiedadElasticActivo() {
        return getPropiedad("activoElastic") != null && "true".equalsIgnoreCase(getPropiedad("activoElastic"));
    }

    private String getPropiedad(String propiedad) {
        return getProperty(propiedad);
    }

    @Override
    public ResultadoAccion indexarFichero(IndexFile ficheroIndexacion) throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.indexarFichero(ficheroIndexacion.cast());
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }


    @Override
    public ResultadoAccion desindexarCaducados() throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.desindexarCaducados();
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    @Override
    public ResultadoAccion desindexar(String id, EnumCategoria categoria) throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.desindexar(id, es.caib.solr.api.model.types.EnumCategoria.fromString(categoria.toString()));
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    @Override
    public ResultadoAccion desindexarAplicacion() throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.desindexarAplicacion();
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    @Override
    public ResultadoAccion desindexarCategoria(EnumCategoria categoria) throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.desindexarCategoria(es.caib.solr.api.model.types.EnumCategoria.fromString(categoria.toString()));
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    @Override
    public ResultadoAccion desindexarRaiz(String idRaiz, EnumCategoria categoriaRaiz) throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.desindexarRaiz(idRaiz, es.caib.solr.api.model.types.EnumCategoria.fromString(categoriaRaiz.toString()));
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    @Override
    public ResultadoAccion commit() throws IPluginIndexacionExcepcion {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        try {
            solrIndexer.commit();
            resultadoAccion = new ResultadoAccion(true, "");
        } catch (Exception e) {
            LOG.error("Error indexando contenido", e);
            //throw new IPluginIndexacionExcepcion(e);
            if (e instanceof es.caib.solr.api.exception.ExcepcionSolrApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en solr." + e.getMessage());
            } else if (e instanceof es.caib.solr.api.exception.ExcepcionElasticApi) {
                resultadoAccion = new ResultadoAccion(false, "Error en elastic." + e.getMessage());
            } else {
                resultadoAccion = new ResultadoAccion(false, "Error en solr y elastic." + e.getMessage());
            }
        }

        return resultadoAccion;
    }

    @Override
    public String htmlToText(String html) {
        String urlSolr = getPropiedadSolrUrl();
        String index = getPropiedadSolrIndex();
        String userSolr = getPropiedadSolrUsr();
        String passSolr = getPropiedadSolrPwd();

        String urlElastic = getPropiedadElasticUrl();
        String userElastic = getPropiedadElasticUsr();
        String passElastic = getPropiedadElasticPwd();

        boolean solrActivo = isPropiedadSolrActivo();
        boolean elasticActivo = isPropiedadElasticActivo();

        ResultadoAccion resultadoAccion = null;
        final SolrIndexer solrIndexer = SolrFactory.getIndexer(urlSolr, index, es.caib.solr.api.model.types.EnumAplicacionId.ROLSAC, userSolr, passSolr, urlElastic, userElastic, passElastic, solrActivo, elasticActivo);
        return solrIndexer.htmlToText(html);
    }

    @Override
    public ResultadoBusqueda buscar(String sesionId, String textoBusqueda, EnumIdiomas idiomaBusqueda, FiltroBusqueda filtroBusqueda, BusquedaPaginacion paginacionBusqueda) throws IPluginIndexacionExcepcion {
        final String username = ""; //PropertiesUtil.getSolrUser();
        final String password = ""; //PropertiesUtil.getSolrPass();
        final String index = ""; //PropertiesUtil.getSolrIndex();
        final String urlSolr = ""; //PropertiesUtil.getSolrUrl();
        final SolrSearcher buscador = SolrFactory.getSearcher("ROLSAC2", urlSolr, index, username, password);

        final FilterSearch filterSearch = new FilterSearch();
        if (filtroBusqueda.getAplicaciones() != null && !filtroBusqueda.getAplicaciones().isEmpty()) {
            filterSearch.setAplicaciones(calcularAplicaciones(filtroBusqueda.getAplicaciones()));
        }
        if (filtroBusqueda.getCategorias() != null && !filtroBusqueda.getCategorias().isEmpty()) {
            filterSearch.setCategorias(calcularCategorias(filtroBusqueda.getCategorias()));
        }
        if (filtroBusqueda.getUos() != null && !filtroBusqueda.getUos().isEmpty()) {
            List<PathUO> uos = new ArrayList<>();
            for (PathUA uo : filtroBusqueda.getUos()) {
                PathUO pathUO = new PathUO();
                pathUO.setPath(uo.getPath());
                uos.add(pathUO);
            }
            filterSearch.setUos(uos);
        }
        if (filtroBusqueda.getUo() != null) {
            PathUO pathUO = new PathUO();
            pathUO.setPath(filtroBusqueda.getUo().getPath());
            filterSearch.setUo(pathUO);
        }

        final PaginationSearch paginationSearch = new PaginationSearch();
        paginationSearch.setDesde(paginacionBusqueda.getDesde());
        paginationSearch.setMaxElementos(paginacionBusqueda.getMaxElementos());
        final ResultData resultadoSolr;
        try {
            resultadoSolr = buscador.buscar("SESSION_ID", textoBusqueda, es.caib.solr.api.model.types.EnumIdiomas.fromString(idiomaBusqueda.toString()), filterSearch, paginationSearch);
        } catch (ExcepcionSolrApi e) {
            throw new IPluginIndexacionExcepcion(e);
        }

        ResultadoBusqueda resultado = new ResultadoBusqueda();
        resultado.setResultados(getResultado(resultadoSolr.getResultados()));
        resultado.setNumResultados((int) resultadoSolr.getNumResultados());
        return resultado;
    }

    @Override
    public String isSolrActivo() {
        return this.isPropiedadSolrActivo() ? "true" : "false";
    }

    @Override
    public String isElasticActivo() {
        return isPropiedadElasticActivo() ? "true" : "false";
    }

    private List<es.caib.solr.api.model.types.EnumCategoria> calcularCategorias(List<EnumCategoria> categorias) {
        List<es.caib.solr.api.model.types.EnumCategoria> enumCategorias = new ArrayList<>();
        if (categorias != null) {
            for (EnumCategoria categoria : categorias) {
                enumCategorias.add(es.caib.solr.api.model.types.EnumCategoria.fromString(categoria.toString()));
            }
        }

        return enumCategorias;
    }

    private List<es.caib.solr.api.model.types.EnumAplicacionId> calcularAplicaciones(List<EnumAplicacionId> aplicaciones) {
        List<es.caib.solr.api.model.types.EnumAplicacionId> enumAplicaciones = new ArrayList<>();
        if (aplicaciones != null) {
            for (EnumAplicacionId aplicacion : aplicaciones) {
                enumAplicaciones.add(es.caib.solr.api.model.types.EnumAplicacionId.fromString(aplicacion.toString()));
            }
        }
        return enumAplicaciones;
    }

    private List<DataIndexacion> getResultado(List<StoredData> resultados) {
        List<DataIndexacion> datos = new ArrayList<>();
        if (resultados != null) {
            for (StoredData resultado : resultados) {
                DataIndexacion id = new DataIndexacion();
                id.setElementoId(resultado.getElementoId());
                id.setAplicacionId(getAplicacionId(resultado.getAplicacionId()));
                id.setCategoria(getCategoria(resultado.getCategoria()));
                id.setTitulo(getLiteralMultiLang(resultado.getTitulo()));

                //id.setSearchText(getMultiLangLiteral(resultado.getSearchText()));
                //id.setSearchTextOptional(getMultiLangLiteral(resultado.getSearchTextOptional());
                //id.setIdiomas(getIdiomas(resultado.getidi()));
                id.setDescripcion(getLiteralMultiLang(resultado.getDescripcion()));
                id.setUrl(getLiteralMultiLang(resultado.getUrl()));
                id.setCategoriaPadre(getCategoria(resultado.getCategoriaPadre()));
                id.setDescripcionPadre(getLiteralMultiLang(resultado.getDescripcionPadre()));
                id.setUrlPadre(getLiteralMultiLang(resultado.getUrlPadre()));
                id.setFechaActualizacion(resultado.getFechaActualizacion());
                id.setFechaPublicacion(resultado.getFechaPublicacion());
                id.setFechaCaducidad(resultado.getFechaCaducidad());
                id.setFechaPlazoIni(resultado.getFechaPlazoIni());
                id.setFechaPlazoFin(resultado.getFechaPlazoFin());
                id.setUrlFoto(resultado.getUrlFoto());
                id.setExtension(getLiteralMultiLang(resultado.getExtension()));
                id.setUos(this.getUas(resultado.getUos()));
                id.setFamiliaId(resultado.getFamiliaId());
                id.setMateriaId(resultado.getMateriaId());
                id.setPublicoId(resultado.getPublicoId());
                id.setTelematico(resultado.getTelematico());
                id.setElementoIdRaiz(resultado.getElementoIdRaiz());
                id.setInterno(resultado.isInterno());
                id.setElementoIdPadre(resultado.getElementoIdPadre());
                id.setCategoriaRaiz(getCategoria(resultado.getCategoriaRaiz()));
                id.setScore(resultado.getScore());
            }
        }
        return datos;
    }


    private LiteralMultilang getLiteralMultiLang(MultilangLiteral valor) {
        if (valor == null) {
            return null;
        }
        LiteralMultilang literal = new LiteralMultilang();
        List<String> idiomas = valor.getIdiomas();
        for (String idioma : idiomas) {
            literal.addIdioma(EnumIdiomas.fromString(idioma), valor.get(es.caib.solr.api.model.types.EnumIdiomas.fromString(idioma)));
        }
        return literal;
    }

    private EnumCategoria getCategoria(es.caib.solr.api.model.types.EnumCategoria categoria) {
        return categoria == null ? null : EnumCategoria.fromString(categoria.toString());
    }

    private EnumAplicacionId getAplicacionId(es.caib.solr.api.model.types.EnumAplicacionId aplicacionId) {
        return aplicacionId == null ? null : EnumAplicacionId.fromString(aplicacionId.toString());
    }

}
