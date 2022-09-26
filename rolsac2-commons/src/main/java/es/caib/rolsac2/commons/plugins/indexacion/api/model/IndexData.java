package es.caib.rolsac2.commons.plugins.indexacion.api.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;

import java.util.List;

/**
 * Informacion a indexar.
 */
public class IndexData extends StoredData {

    /**
     * Idiomas del contenido a indexar. Se verificará que existen los campos
     * obligatorios para los idiomas especificados.
     */
    private List<EnumIdiomas> idiomas;

    /**
     * Texto que será comparado en las búsquedas.
     */
    private MultilangLiteral searchText;

    /**
     * Texto que será comparado en las búsquedas pero con un peso menor.
     */
    private MultilangLiteral searchTextOptional;

    /**
     * Obtiene texto que será comparado en las búsquedas.
     *
     * @return the texto que será comparado en las búsquedas
     */
    public MultilangLiteral getSearchText() {
        return searchText;
    }

    /**
     * Establece texto que será comparado en las búsquedas.
     *
     * @param searchText the new texto que será comparado en las búsquedas
     */
    public void setSearchText(MultilangLiteral searchText) {
        this.searchText = searchText;
    }

    /**
     * Obtiene texto que será comparado en las búsquedas pero con un peso menor.
     *
     * @return the texto que será comparado en las búsquedas pero con un peso
     * menor
     */
    public MultilangLiteral getSearchTextOptional() {
        return searchTextOptional;
    }

    /**
     * Establece texto que será comparado en las búsquedas pero con un peso
     * menor.
     *
     * @param searchTextOptional the new texto que será comparado en las búsquedas pero con un
     *                           peso menor
     */
    public void setSearchTextOptional(MultilangLiteral searchTextOptional) {
        this.searchTextOptional = searchTextOptional;
    }

    /**
     * Obtiene idiomas del contenido a indexar.
     *
     * @return the idiomas del contenido a indexar
     */
    public List<EnumIdiomas> getIdiomas() {
        return idiomas;
    }

    /**
     * Establece idiomas del contenido a indexar.
     *
     * @param idiomas the new idiomas del contenido a indexar
     */
    public void setIdiomas(List<EnumIdiomas> idiomas) {
        this.idiomas = idiomas;
    }
}
