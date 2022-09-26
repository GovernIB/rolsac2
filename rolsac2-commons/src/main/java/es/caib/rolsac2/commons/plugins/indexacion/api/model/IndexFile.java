package es.caib.rolsac2.commons.plugins.indexacion.api.model;


import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;

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

}
