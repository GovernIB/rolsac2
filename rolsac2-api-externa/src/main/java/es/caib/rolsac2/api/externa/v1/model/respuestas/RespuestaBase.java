package es.caib.rolsac2.api.externa.v1.model.respuestas;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * Estructura comuna de resposta de l'API REST externa.
 *
 * <p>Conté exclusivament els camps comuns definits pel contracte:
 * metadades del conjunt de dades, informació de paginació, els elements
 * retornats i el temps d'execució.</p>
 *
 * @param <T> tipus dels elements continguts a {@code items}
 */
@XmlRootElement
@Schema(
        name = "RespuestaBase",
        description = "Estructura comuna de resposta de l'API REST externa."
)
public class RespuestaBase<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final ZoneId ZONA = ZoneId.of("Europe/Madrid");

    @Schema(description = "Nom del conjunt de dades.", example = "Procediments")
    private String title;

    @Schema(
            description = "Descripció clara i concisa de l'acció realitzada pel servei.",
            example = "Retorna els procediments disponibles en funció dels filtres indicats."
    )
    private String description;

    @Schema(
            description = "Cobertura geogràfica. A ROLSAC2 correspon a l'entitat consultada.",
            example = "1"
    )
    private String spatial;

    @Schema(
            description = "Codi DIR3 del creador. A ROLSAC2 correspon al codi DIR3 de l'arrel.",
            example = "A04003003"
    )
    private String creator;

    @Schema(
            description = "Data i hora de descàrrega en format ISO8601 amb offset.",
            example = "2026-09-10T17:30:00+02:00"
    )
    private String dateDownload;

    @Schema(description = "Nombre total d'elements que compleixen la consulta.", example = "125")
    private Long totalCount;

    @Schema(description = "Nombre d'elements retornats a la pàgina actual.", example = "20")
    private Integer itemsReturned;

    @Schema(description = "Mida de pàgina aplicada.", example = "20")
    private Integer pageSize;

    @Schema(description = "Nombre total de pàgines.", example = "7")
    private Integer totalPages;

    @Schema(description = "Número de la pàgina actual, començant per 0.", example = "0")
    private Integer page;

    @Schema(
            description = "URL completa de la pàgina següent. És nul·la si no hi ha pàgina següent.",
            example = "https://servidor/rolsac2api/externa/services/v1/procediments?page=1&page-size=20",
            nullable = true
    )
    private String nextUrl;

    @Schema(
            description = "URL completa de la pàgina anterior. És nul·la si no hi ha pàgina anterior.",
            example = "https://servidor/rolsac2api/externa/services/v1/procediments?page=0&page-size=20",
            nullable = true
    )
    private String previousUrl;

    /**
     * En la classe base el tipus és genèric. Els esquemes concrets
     * RespuestaProcedimientos i RespuestaServicios documenten explícitament
     * Procediment i Servei respectivament.
     */
    @Schema(description = "Llista d'elements retornats.")
    private List<T> items;

    @Schema(description = "Temps d'execució del servei en mil·lisegons.", example = "125")
    private Long tiempo;

    public RespuestaBase() {
        this.dateDownload = generarDateDownload();
        this.items = Collections.emptyList();
    }

    /**
     * Constructor principal per a respostes paginades.
     */
    public RespuestaBase(final long total,
                         final Integer paginaTamanyo,
                         final Integer pagina,
                         final URI requestUri,
                         final List<T> lista,
                         final long tiempoMiliSegundos) {

        this.dateDownload = generarDateDownload();
        this.totalCount = Math.max(0L, total);
        this.pageSize = normalitzarPageSize(paginaTamanyo);
        this.page = normalitzarPagina(pagina);
        this.items = lista != null ? lista : Collections.<T>emptyList();
        this.itemsReturned = this.items.size();
        this.totalPages = calcularTotalPaginas(this.totalCount, this.pageSize);
        this.tiempo = Math.max(0L, tiempoMiliSegundos);

        completarUrlsPaginacion(this.totalCount, this.pageSize, this.page, requestUri);
    }

    /**
     * Constructor compatible amb el codi existent.
     *
     * <p>El paràmetre {@code size} es conserva per compatibilitat, però
     * {@code itemsReturned} es calcula sempre a partir de la llista real.</p>
     */
    public RespuestaBase(final int total,
                         final int size,
                         final Integer paginaTamanyo,
                         final Integer pagina,
                         final URI requestUri,
                         final List<T> lista,
                         final long tiempoMiliSegundos) {
        this((long) total, paginaTamanyo, pagina, requestUri, lista, tiempoMiliSegundos);
    }

    /**
     * Constructor antic conservat únicament per compatibilitat binària/fonte.
     *
     * @deprecated utilitzar el constructor que rep {@link URI}, ja que és
     *             l'únic que pot calcular correctament nextUrl/previousUrl.
     */
    @Deprecated
    public RespuestaBase(final int total,
                         final int size,
                         final Integer paginaTamanyo,
                         final Integer pagina,
                         final String url,
                         final List<T> lista,
                         final long tiempoMiliSegundos) {

        this.dateDownload = generarDateDownload();
        this.totalCount = Math.max(0L, total);
        this.pageSize = normalitzarPageSize(paginaTamanyo);
        this.page = normalitzarPagina(pagina);
        this.items = lista != null ? lista : Collections.<T>emptyList();
        this.itemsReturned = this.items.size();
        this.totalPages = calcularTotalPaginas(this.totalCount, this.pageSize);
        this.nextUrl = url;
        this.tiempo = Math.max(0L, tiempoMiliSegundos);
    }

    /**
     * Constructor utilitzat per respostes simples d'estat.
     */
    public RespuestaBase(final String status, final String msg, final long tiempo) {
        this();
        this.title = status;
        this.description = msg;
        this.tiempo = Math.max(0L, tiempo);
    }

    private static String generarDateDownload() {
        return Instant.now()
                .atZone(ZONA)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private static int normalitzarPageSize(final Integer paginaTamanyo) {
        return paginaTamanyo != null && paginaTamanyo > 0
                ? paginaTamanyo
                : DEFAULT_PAGE_SIZE;
    }

    private static int normalitzarPagina(final Integer pagina) {
        return pagina != null && pagina >= 0
                ? pagina
                : DEFAULT_PAGE;
    }

    private static Integer calcularTotalPaginas(final long total, final int paginaTamanyo) {
        if (total <= 0L) {
            return 0;
        }
        return (int) Math.ceil((double) total / (double) paginaTamanyo);
    }

    private void completarUrlsPaginacion(final long total,
                                         final int paginaTamanyo,
                                         final int paginaActual,
                                         final URI requestUri) {

        if (requestUri == null) {
            return;
        }

        if (paginaActual > 0) {
            this.previousUrl = construirUrlPaginacion(
                    requestUri,
                    paginaActual - 1,
                    paginaTamanyo
            );
        }

        final long primerElementoPaginaSiguiente =
                ((long) paginaActual + 1L) * (long) paginaTamanyo;

        if (primerElementoPaginaSiguiente < total) {
            this.nextUrl = construirUrlPaginacion(
                    requestUri,
                    paginaActual + 1,
                    paginaTamanyo
            );
        }
    }

    private static String construirUrlPaginacion(final URI requestUri,
                                                 final int pagina,
                                                 final int paginaTamanyo) {
        return UriBuilder.fromUri(requestUri)
                .replaceQueryParam("page")
                .replaceQueryParam("page-size")
                .queryParam("page", pagina)
                .queryParam("page-size", paginaTamanyo)
                .build()
                .toString();
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

    public List<T> getItems() {
        return items;
    }

    public void setItems(final List<T> items) {
        this.items = items != null ? items : Collections.<T>emptyList();
        this.itemsReturned = this.items.size();
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
