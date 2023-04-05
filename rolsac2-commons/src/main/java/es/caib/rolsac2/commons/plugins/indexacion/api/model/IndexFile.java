package es.caib.rolsac2.commons.plugins.indexacion.api.model;


import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumAplicacionId;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;
import es.caib.solr.api.model.PathUO;

import java.util.ArrayList;
import java.util.List;

/**
 * Informacion a indexar para fichero.
 * <p>
 * Un fichero solo puede ser indexado para un idioma, por lo que los campos
 * multiidioma deberan ser establecidos en el idioma correspondiente.
 */
public class IndexFile extends StoredData {

    /**
     * Contenido fichero.
     */
    private byte[] fileContent;

    /**
     * Idioma fichero.
     */
    private EnumIdiomas idioma;

    /**
     * Texto que será comparado en las búsquedas pero con un peso menor.
     */
    private MultilangLiteral searchTextOptional;

    /**
     * Obtiene idioma fichero.
     *
     * @return the idioma fichero
     */
    public EnumIdiomas getIdioma() {
        return idioma;
    }

    /**
     * Establece idioma fichero.
     *
     * @param idioma the new idioma fichero
     */
    public void setIdioma(EnumIdiomas idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene contenido fichero.
     *
     * @return the contenido fichero
     */
    public byte[] getFileContent() {
        return fileContent;
    }

    /**
     * Establece contenido fichero.
     *
     * @param fileContent the new contenido fichero
     */
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * @return the searchTextOptional
     */
    public MultilangLiteral getSearchTextOptional() {
        return searchTextOptional;
    }

    /**
     * @param searchTextOptional the searchTextOptional to set
     */
    public void setSearchTextOptional(MultilangLiteral searchTextOptional) {
        this.searchTextOptional = searchTextOptional;
    }

    public es.caib.solr.api.model.IndexFile cast() {
        es.caib.solr.api.model.IndexFile fichero = new es.caib.solr.api.model.IndexFile();
        fichero.setFileContent(getFileContent());
        fichero.setIdioma(es.caib.solr.api.model.types.EnumIdiomas.fromString(this.getIdioma().toString()));
        fichero.setSearchTextOptional(getLiteral(getSearchTextOptional()));


        fichero.setElementoId(getElementoId());
        fichero.setElementoIdPadre(getElementoIdPadre());
        fichero.setCategoria(getCategoria(this.getCategoria()));
        fichero.setAplicacionId(getAplicacionId(getAplicacionId()));
        fichero.setTitulo(getLiteral(getTitulo()));
        fichero.setDescripcion(getLiteral(getDescripcion()));
        fichero.setUrl(getLiteral(getUrl()));
        fichero.setCategoriaPadre(getCategoria(getCategoriaPadre()));
        fichero.setDescripcionPadre(getLiteral(getDescripcionPadre()));
        fichero.setUrlPadre(getLiteral(getUrlPadre()));
        fichero.setFechaActualizacion(getFechaActualizacion());
        fichero.setFechaPublicacion(getFechaPublicacion());
        fichero.setFechaCaducidad(getFechaCaducidad());
        fichero.setFechaPlazoIni(getFechaPlazoIni());
        fichero.setFechaPlazoFin(getFechaPlazoFin());
        fichero.setUrlFoto(getUrlFoto());
        fichero.setExtension(getLiteral(getExtension()));
        fichero.setUos(getPathUos(getUos()));
        fichero.setFamiliaId(getFamiliaId());
        fichero.setMateriaId(getMateriaId());
        fichero.setPublicoId(getPublicoId());
        fichero.setTelematico(getTelematico());
        fichero.setCategoriaRaiz(getCategoria(getCategoriaRaiz()));
        fichero.setElementoIdRaiz(getElementoIdRaiz());
        fichero.setInterno(isInterno());
        fichero.setScore(getScore());

        return fichero;
    }

    private es.caib.solr.api.model.MultilangLiteral getLiteral(MultilangLiteral valor) {
        if (valor == null) {
            return null;
        }
        es.caib.solr.api.model.MultilangLiteral literal = new es.caib.solr.api.model.MultilangLiteral();
        List<String> idiomas = valor.getIdiomas();
        for (String idioma : idiomas) {
            literal.addIdioma(es.caib.solr.api.model.types.EnumIdiomas.fromString(idioma), valor.get(EnumIdiomas.fromString(idioma)));
        }
        return literal;
    }

    private List<PathUO> getPathUos(List<es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUO> uos) {
        List<PathUO> pathUOs = new ArrayList<>();
        for (es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUO ua : uos) {
            PathUO uo = new PathUO();
            uo.setPath(ua.getPath());
            pathUOs.add(uo);
        }

        return pathUOs;
    }


    private es.caib.solr.api.model.MultilangLiteral getMultiLangLiteral(LiteralMultilang valor) {
        if (valor == null) {
            return null;
        }
        es.caib.solr.api.model.MultilangLiteral literal = new es.caib.solr.api.model.MultilangLiteral();
        List<String> idiomas = valor.getIdiomas();
        for (String idioma : idiomas) {
            literal.addIdioma(es.caib.solr.api.model.types.EnumIdiomas.fromString(idioma), valor.get(EnumIdiomas.fromString(idioma)));
        }
        return literal;
    }

    private LiteralMultilang getLiteralMultiLang(es.caib.solr.api.model.MultilangLiteral valor) {
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

    private es.caib.solr.api.model.types.EnumCategoria getCategoria(EnumCategoria categoria) {
        return categoria == null ? null : es.caib.solr.api.model.types.EnumCategoria.fromString(categoria.toString());
    }

    private es.caib.solr.api.model.types.EnumAplicacionId getAplicacionId(EnumAplicacionId aplicacionId) {
        return aplicacionId == null ? null : es.caib.solr.api.model.types.EnumAplicacionId.fromString(aplicacionId.toString());
    }


}
