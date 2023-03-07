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
    public ResultadoAccion indexarContenido(DataIndexacion dataIndexacion) throws IPluginIndexacionExcepcion {
        return getResultadoCorrecto();
    }

    @Override
    public ResultadoAccion indexarFichero(FicheroIndexacion ficheroIndexacion) throws IPluginIndexacionExcepcion {
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
    public ResultadoBusqueda buscar(String sesionId, String textoBusqueda, EnumIdiomas idiomaBusqueda, FiltroBusqueda filtroBusqueda, BusquedaPaginacion paginacionBusqueda) throws IPluginIndexacionExcepcion {
        ResultadoBusqueda resultData = new ResultadoBusqueda();
        List<DataIndexacion> resultados = new ArrayList<>();
        DataIndexacion resultado1 = new DataIndexacion();
        resultado1.setAplicacionId(EnumAplicacionId.ROLSAC);
        resultado1.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        LiteralMultilang titulo1 = new LiteralMultilang();
        titulo1.addIdioma(EnumIdiomas.CASTELLA, "Titulo1");
        titulo1.addIdioma(EnumIdiomas.CATALA, "Titol1");
        resultado1.setTitulo(titulo1);
        resultado1.setCategoriaPadre(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        resultado1.setElementoId("1");
        LiteralMultilang descripcion1 = new LiteralMultilang();
        descripcion1.addIdioma(EnumIdiomas.CASTELLA, "Descripcion 1");
        descripcion1.addIdioma(EnumIdiomas.CATALA, "Descripció 1");
        resultado1.setDescripcion(descripcion1);
        resultado1.setFechaActualizacion(new Date());
        resultado1.setFechaPublicacion(new Date());
        resultado1.setInterno(false);
        LiteralMultilang url1 = new LiteralMultilang();
        url1.addIdioma(EnumIdiomas.CASTELLA, "http://www.url1.es");
        url1.addIdioma(EnumIdiomas.CATALA, "http://www.url1.cat");
        resultado1.setUrl(url1);

        DataIndexacion resultado2 = new DataIndexacion();
        resultado2.setAplicacionId(EnumAplicacionId.ROLSAC);
        resultado2.setCategoria(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        LiteralMultilang titulo2 = new LiteralMultilang();
        titulo2.addIdioma(EnumIdiomas.CASTELLA, "Titulo2");
        titulo2.addIdioma(EnumIdiomas.CATALA, "Titol2");
        resultado2.setTitulo(titulo2);
        resultado2.setCategoriaPadre(EnumCategoria.ROLSAC_PROCEDIMIENTO);
        resultado2.setElementoId("2");
        LiteralMultilang descripcion2 = new LiteralMultilang();
        descripcion2.addIdioma(EnumIdiomas.CASTELLA, "Descripcion 2");
        descripcion2.addIdioma(EnumIdiomas.CATALA, "Descripció 2");
        resultado2.setDescripcion(descripcion2);
        resultado2.setFechaActualizacion(new Date());
        resultado2.setFechaPublicacion(new Date());
        resultado2.setInterno(false);
        LiteralMultilang url2 = new LiteralMultilang();
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
