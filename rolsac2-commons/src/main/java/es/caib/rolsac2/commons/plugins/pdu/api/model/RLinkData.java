package es.caib.rolsac2.commons.plugins.pdu.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RLinkData {


    /**
     * Título
     */
    @JsonProperty("title")
    private String title;

    /**
     * Descripción
     */
    @JsonProperty("description")
    private String description;

    /**
     * Url
     */
    @JsonProperty("url")
    private String url;

    /**
     * Tipo de enlace
     */
    @JsonProperty("type")
    private String type;


    /**
     * Idioma
     */
    @JsonProperty("language")
    private String language;

    /**
     * Tipo de url
     */
    @JsonProperty("urlType")
    private RTypeUrl urlType;

    /**
     * Código nacional
     */
    @JsonProperty("nationalCode")
    private String nationalCode;

    /**
     * Url padre (web folder)
     */
    @JsonProperty("parentUrl")
    private String parentUrl;

    /**
     * Borrado (sí se quiere eliminar o no, en este caso será alta o modificación, según exista ya o no)
     */
    @JsonProperty("delete")
    private RTypeDelete delete;

    /**
     * Información de búsqueda en el dashboard de los ODS
     */
    @JsonProperty("sdgDashboardInfoSearchResults")
    private String sdgDashboardInfoSearchResults;

    /**
     * Url de rastreo
     */
    @JsonProperty("crawlUrl")
    private String crawlUrl;

    /**
     * Lista de Sitemaps
     */
    @JsonProperty("sitemaps")
    private List<String> sitemaps;

    /**
     * Lista de rutas excluidas
     */
    @JsonProperty("excludedPaths")
    private List<String> excludedPaths;

    /**
     * Lista de parámetros ignorados
     */
    @JsonProperty("ignoreParams")
    private List<String> ignoreParams;

    /**
     * Categorías
     */
    @JsonProperty("categories")
    private List<RCategory> categories;

    //Cambios añadidos el 2 de julio de 2024 por cambios en el servicio PDU que requieren estos campos aunque sean vacíos
    //De momento sólo están en PRE pero se puede subir a PRO pues se ignoraran estos campos
    /**
     * Nombre procedimiento
     */
    @JsonProperty("procedureName")
    private String procedureName;

    /**
     * Tipo procedimiento
     */
    @JsonProperty("procedureType")
    private String procedureType;

    /**
     * Disponiblidad procedimiento
     */
    @JsonProperty("procedureAvailability")
    private String procedureAvailability;

    /**
     * Datos procedimiento
     */
    @JsonProperty("procedureData")
    private List<String> procedureData;


    /**
     * Procedimientos asociados
     */
    @JsonProperty("associatedProcedures")
    private List<String> associatedProcedures;


    /**
     * Obtiene la descripción del enlace.
     *
     * @return la descripción del enlace.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción del enlace.
     *
     * @param description la nueva descripción del enlace.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene el título del enlace.
     *
     * @return el título del enlace.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título del enlace.
     *
     * @param title el nuevo título del enlace.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene la URL del enlace.
     *
     * @return la URL del enlace.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del enlace.
     *
     * @param url la nueva URL del enlace.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el tipo de enlace.
     *
     * @return el tipo de enlace.
     */
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo de enlace.
     *
     * @param type el nuevo tipo de enlace.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtiene el idioma del enlace.
     *
     * @return el idioma del enlace.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Establece el idioma del enlace.
     *
     * @param language el nuevo idioma del enlace.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Obtiene el tipo de URL del enlace.
     *
     * @return el tipo de URL del enlace.
     */
    public RTypeUrl getUrlType() {
        return urlType;
    }

    /**
     * Establece el tipo de URL del enlace.
     *
     * @param urlType el nuevo tipo de URL del enlace.
     */
    public void setUrlType(RTypeUrl urlType) {
        this.urlType = urlType;
    }

    /**
     * Obtiene el código nacional del enlace.
     *
     * @return el código nacional del enlace.
     */
    public String getNationalCode() {
        return nationalCode;
    }

    /**
     * Establece el código nacional del enlace.
     *
     * @param nationalCode el nuevo código nacional del enlace.
     */
    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    /**
     * Obtiene la URL padre del enlace.
     *
     * @return la URL padre del enlace.
     */
    public String getParentUrl() {
        return parentUrl;
    }

    /**
     * Establece la URL padre del enlace.
     *
     * @param parentUrl la nueva URL padre del enlace.
     */
    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    /**
     * Obtiene el tipo de eliminación del enlace.
     *
     * @return el tipo de eliminación del enlace.
     */
    public RTypeDelete getDelete() {
        return delete;
    }

    /**
     * Establece el tipo de eliminación del enlace.
     *
     * @param delete el nuevo tipo de eliminación del enlace.
     */
    public void setDelete(RTypeDelete delete) {
        this.delete = delete;
    }

    /**
     * Obtiene la información de resultados de búsqueda del panel de control SDG del enlace.
     *
     * @return la información de resultados de búsqueda del panel de control SDG del enlace.
     */
    public String getSdgDashboardInfoSearchResults() {
        return sdgDashboardInfoSearchResults;
    }

    /**
     * Establece la información de resultados de búsqueda del panel de control SDG del enlace.
     *
     * @param sdgDashboardInfoSearchResults la nueva información de resultados de búsqueda del panel de control SDG del enlace.
     */
    public void setSdgDashboardInfoSearchResults(String sdgDashboardInfoSearchResults) {
        this.sdgDashboardInfoSearchResults = sdgDashboardInfoSearchResults;
    }

    /**
     * Obtiene la URL de rastreo del enlace.
     *
     * @return la URL de rastreo del enlace.
     */
    public String getCrawlUrl() {
        return crawlUrl;
    }

    /**
     * Establece la URL de rastreo del enlace.
     *
     * @param crawlUrl la nueva URL de rastreo del enlace.
     */
    public void setCrawlUrl(String crawlUrl) {
        this.crawlUrl = crawlUrl;
    }

    /**
     * Obtiene la lista de sitemaps del enlace.
     *
     * @return la lista de sitemaps del enlace.
     */
    public List<String> getSitemaps() {
        return sitemaps;
    }

    /**
     * Establece la lista de sitemaps del enlace.
     *
     * @param sitemaps la nueva lista de sitemaps del enlace.
     */
    public void setSitemaps(List<String> sitemaps) {
        this.sitemaps = sitemaps;
    }

    /**
     * Obtiene la lista de rutas excluidas del enlace.
     *
     * @return la lista de rutas excluidas del enlace.
     */
    public List<String> getExcludedPaths() {
        return excludedPaths;
    }

    /**
     * Establece la lista de rutas excluidas del enlace.
     *
     * @param excludedPaths la nueva lista de rutas excluidas del enlace.
     */
    public void setExcludedPaths(List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    /**
     * Obtiene la lista de parámetros ignorados del enlace.
     *
     * @return la lista de parámetros ignorados del enlace.
     */
    public List<String> getIgnoreParams() {
        return ignoreParams;
    }

    /**
     * Establece la lista de parámetros ignorados del enlace.
     *
     * @param ignoreParams la nueva lista de parámetros ignorados del enlace.
     */
    public void setIgnoreParams(List<String> ignoreParams) {
        this.ignoreParams = ignoreParams;
    }

    /**
     * Obtiene la lista de categorías del enlace.
     *
     * @return la lista de categorías del enlace.
     */
    public List<RCategory> getCategories() {
        return categories;
    }

    /**
     * Establece la lista de categorías del enlace.
     *
     * @param categories la nueva lista de categorías del enlace.
     */
    public void setCategories(List<RCategory> categories) {
        this.categories = categories;
    }

    /**
     * Obtiene el nombre del procedimiento.
     *
     * @return El nombre del procedimiento.
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * Establece el nombre del procedimiento.
     *
     * @param procedureName El nuevo nombre del procedimiento.
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * Obtiene el tipo del procedimiento.
     *
     * @return El tipo del procedimiento.
     */
    public String getProcedureType() {
        return procedureType;
    }

    /**
     * Establece el tipo del procedimiento.
     *
     * @param procedureType El nuevo tipo del procedimiento.
     */
    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    /**
     * Obtiene la disponibilidad del procedimiento.
     *
     * @return La disponibilidad del procedimiento.
     */
    public String getProcedureAvailability() {
        return procedureAvailability;
    }

    /**
     * Establece la disponibilidad del procedimiento.
     *
     * @param procedureAvailability La nueva disponibilidad del procedim
     *
     */
    public void setProcedureAvailability(String procedureAvailability) {
        this.procedureAvailability = procedureAvailability;
    }

    /**
     * Obtiene los datos del procedimiento.
     *
     * @return Los datos del procedimiento.
     */
    public List<String> getProcedureData() {
        return procedureData;
    }

    /**
     * Establece los datos del procedimiento.
     *
     * @param procedureData Los nuevos datos del procedimiento.
     */
    public void setProcedureData(List<String> procedureData) {
        this.procedureData = procedureData;
    }

    /**
     * Obtiene los procedimientos asociados.
     *
     * @return Los procedimientos asociados.
     */
    public List<String> getAssociatedProcedures() {
        return associatedProcedures;
    }

    /**
     * Establece los procedimientos asociados.
     *
     * @param associatedProcedures Los nuevos procedimientos asociados.
     */
    public void setAssociatedProcedures(List<String> associatedProcedures) {
        this.associatedProcedures = associatedProcedures;
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        texto.append("RLinkData{");
        texto.append("title='").append(title).append('\'');
        texto.append(", description='").append(description).append('\'');
        texto.append(", url='").append(url).append('\'');
        texto.append(", type='").append(type).append('\'');
        texto.append(", language='").append(language).append('\'');
        texto.append(", urlType=").append(urlType);
        texto.append(", nationalCode='").append(nationalCode).append('\'');
        texto.append(", parentUrl='").append(parentUrl).append('\'');
        texto.append(", delete=").append(delete);
        texto.append(", sdgDashboardInfoSearchResults='").append(sdgDashboardInfoSearchResults).append('\'');
        texto.append(", crawlUrl='").append(crawlUrl).append('\'');
        texto.append(", sitemaps=").append(sitemaps);
        texto.append(", excludedPaths=").append(excludedPaths);
        texto.append(", ignoreParams=").append(ignoreParams);
        texto.append(", categories=").append(categories);
        texto.append(", procedureName='").append(procedureName).append('\'');
        texto.append(", procedureType='").append(procedureType).append('\'');
        texto.append(", procedureAvailability='").append(procedureAvailability).append('\'');
        texto.append(", procedureData=").append(procedureData);
        texto.append(", associatedProcedures=").append(associatedProcedures);
        texto.append('}');
        return texto.toString();
    }
}
