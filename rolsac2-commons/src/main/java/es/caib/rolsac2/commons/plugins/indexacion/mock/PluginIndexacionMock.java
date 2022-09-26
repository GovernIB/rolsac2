package es.caib.rolsac2.commons.plugins.indexacion.mock;

import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacionExcepcion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.*;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PluginIndexacionMock extends AbstractPluginProperties implements IPluginIndexacion {

    /**
     * Prefijo de la implementacion.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "indexmock.";

    /**
     * Constructor.
     *
     * @param prefijoPropiedades Prefijo props.
     * @param properties         Propiedades plugins.
     */
    public PluginIndexacionMock(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    private ResultadoAccion getResultadoCorrecto() {
        ResultadoAccion resultado = new ResultadoAccion(true, null);
        return resultado;
    }

    @Override
    public ResultadoAccion indexarContenido(IndexData indexData) throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion indexarFichero(IndexFile indexFile) throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion desindexarCaducados() throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion desindexar(String id, EnumCategoria categoria) throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion desindexarAplicacion() throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion desindexarCategoria(EnumCategoria categoria) throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion desindexarRaiz(String idRaiz, EnumCategoria categoriaRaiz) throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion commit() throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public String htmlToText(String html) {
        return html;
    }

    @Override
    public ResultData buscar(String sesionId, String textoBusqueda, EnumIdiomas idiomaBusqueda, FilterSearch filtroBusqueda, PaginationSearch paginacionBusqueda) throws IPluginIndexacionExcepcion {
        ResultData resultData = new ResultData();
        List<StoredData> resultados = new ArrayList<>();
        StoredData resultado1 = new StoredData();
        resultado1.setAplicacionId(EnumAplicacionId.ROLSAC);
        resultado1.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        MultilangLiteral titulo1 = new MultilangLiteral();
        titulo1.addIdioma(EnumIdiomas.CASTELLA, "Titulo1");
        titulo1.addIdioma(EnumIdiomas.CATALA, "Titol1");
        resultado1.setTitulo(titulo1);
        resultado1.setCategoriaPadre(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        resultado1.setElementoId("1");
        MultilangLiteral descripcion1 = new MultilangLiteral();
        descripcion1.addIdioma(EnumIdiomas.CASTELLA, "Descripcion 1");
        descripcion1.addIdioma(EnumIdiomas.CATALA, "Descripció 1");
        resultado1.setDescripcion(descripcion1);
        resultado1.setFechaActualizacion(new Date());
        resultado1.setFechaPublicacion(new Date());
        resultado1.setInterno(false);
        MultilangLiteral url1 = new MultilangLiteral();
        url1.addIdioma(EnumIdiomas.CASTELLA, "http://www.url1.es");
        url1.addIdioma(EnumIdiomas.CATALA, "http://www.url1.cat");
        resultado1.setUrl(url1);

        StoredData resultado2 = new StoredData();
        resultado2.setAplicacionId(EnumAplicacionId.ROLSAC);
        resultado2.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        MultilangLiteral titulo2 = new MultilangLiteral();
        titulo2.addIdioma(EnumIdiomas.CASTELLA, "Titulo2");
        titulo2.addIdioma(EnumIdiomas.CATALA, "Titol2");
        resultado2.setTitulo(titulo2);
        resultado2.setCategoriaPadre(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        resultado2.setElementoId("2");
        MultilangLiteral descripcion2 = new MultilangLiteral();
        descripcion2.addIdioma(EnumIdiomas.CASTELLA, "Descripcion 2");
        descripcion2.addIdioma(EnumIdiomas.CATALA, "Descripció 2");
        resultado2.setDescripcion(descripcion2);
        resultado2.setFechaActualizacion(new Date());
        resultado2.setFechaPublicacion(new Date());
        resultado2.setInterno(false);
        MultilangLiteral url2 = new MultilangLiteral();
        url2.addIdioma(EnumIdiomas.CASTELLA, "http://www.url2.es");
        url2.addIdioma(EnumIdiomas.CATALA, "http://www.url2.cat");
        resultado2.setUrl(url2);


        resultados.add(resultado1);
        resultados.add(resultado2);
        resultData.setResultados(resultados);
        resultData.setNumResultados(2);
        return resultData;
    }
}
