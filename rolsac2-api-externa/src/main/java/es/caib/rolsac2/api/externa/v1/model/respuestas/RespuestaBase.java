package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Procediment;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a todas las respuestas.
 *
 * @author indra
 */
@XmlRootElement
@Schema(
        name = "RespuestaBase",
        description = "Estructura general de resposta de l'API REST externa."
)
public class RespuestaBase {


    private static final long serialVersionUID = 1L;

    @Schema(
            description = "Nom del conjunt de dades.",
            example = "Procediments"
    )
    private String title;

    @Schema(
            description = "Descripció clara i concisa de l'acció realitzada pel servei.",
            example = "Consulta de procediments disponibles."
    )
    private String description;

    @Schema(
            description = "Cobertura geogràfica. En l'aplicació correspon a l'entitat.",
            example = "1"
    )
    private String spatial;

    @Schema(
            description = "Codi DIR3 del creador. En l'aplicació correspon a l'arrel.",
            example = "A04003003"
    )
    private String creator;

    @Schema(
            description = "Data de descàrrega en format ISO8601.",
            example = "2022-07-26T12:58:55+02:00"
    )
    private String dateDownload;

    @Schema(
            description = "Nombre total d'elements disponibles.",
            example = "125"
    )
    private Long totalCount;

    @Schema(
            description = "Nombre total d'elements retornats en la pàgina actual.",
            example = "20"
    )
    private Integer itemsReturned;

    @Schema(
            description = "Mida de la pàgina.",
            example = "20"
    )
    private Integer pageSize;

    @Schema(
            description = "Nombre total de pàgines.",
            example = "7"
    )
    private Integer totalPages;

    @Schema(
            description = "Número de la pàgina actual.",
            example = "1"
    )
    private Integer page;

    @Schema(
            description = "URL completa per accedir a la pàgina següent. "
                    + "Serà nul si és la darrera pàgina.",
            example = "https://servidor/api/procediments?page=2&page-size=20",
            nullable = true
    )
    private String nextUrl;

    @Schema(
            description = "URL completa per accedir a la pàgina anterior. "
                    + "Serà nul si és la primera pàgina.",
            example = "https://servidor/api/procediments?page=1&page-size=20",
            nullable = true
    )
    private String previousUrl;

    @Schema(
            description = "Llista d'elements retornats."
    )
    private List<?> items;

    @Schema(
            description = "Temps d'execució del servei en mil·lisegons.",
            example = "125"
    )
    private Long tiempo;

    public RespuestaBase() {
        // Constructor por defecto
    }

    public RespuestaBase(int total, int size, Integer paginaTamanyo, Integer paginaFirst, String url, List<Procediment> lista, long tiempoMiliSegundos) {
        this.totalCount = (long) total;
        this.itemsReturned = size;
        this.pageSize = paginaTamanyo;
        this.page = paginaFirst;
        this.nextUrl = url;
        this.items = lista;
        this.tiempo = tiempoMiliSegundos;
    }

    public RespuestaBase(String status, String msg, long tiempo) {
        this.title = status;
        this.description = msg;
        this.tiempo = tiempo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getSpatial() {
        return spatial;
    }

    public void setSpatial(final String spatial) {
        this.spatial = spatial;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public String getDateDownload() {
        return dateDownload;
    }

    public void setDateDownload(final String dateDownload) {
        this.dateDownload = dateDownload;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getItemsReturned() {
        return itemsReturned;
    }

    public void setItemsReturned(final Integer itemsReturned) {
        this.itemsReturned = itemsReturned;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(final String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(final String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(final List<?> items) {
        this.items = items;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(final Long tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "RespuestaBase{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", spatial='" + spatial + '\'' +
                ", creator='" + creator + '\'' +
                ", dateDownload='" + dateDownload + '\'' +
                ", totalCount=" + totalCount +
                ", itemsReturned=" + itemsReturned +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", page=" + page +
                ", nextUrl='" + nextUrl + '\'' +
                ", previousUrl='" + previousUrl + '\'' +
                ", items=" + items +
                ", tiempo=" + tiempo +
                '}';
    }
}
