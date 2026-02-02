package es.caib.rolsac2.api.interna.v1.model.respuestas;


import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a todas las respuestas.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "RespuestaBase", description = "Respuesta Base")
public class RespuestaBase {

    /**
     * fecha de descarga (en formato ISO8601, por ejemplo: 2022-07-26T12:58:55+02:00).
     **/
    @Schema(description = "fecha de descarga (en formato ISO8601, por ejemplo: 2022-07-26T12:58:55+02:00).")
    private String dateDownload;

    /**
     * Numero total de elementos.
     **/
    @Schema(description = "Numero total de elementos")
    private Integer totalCount;
    /**
     * Numero total de elementos retornados
     */
    @Schema(description = "Numero total de elementos retornados")
    private Integer itemsReturned;
    /**
     * tamanyo de la pagina
     **/
    @Schema(description = "tamanyo de la pagina")
    private String pageSize;
    /**
     * Numero total de paginas.
     **/
    @Schema(description = "Numero total de paginas")
    private Integer totalPages;
    /**
     * Numero de la pagina actual.
     **/
    @Schema(description = "Numero de la pagina actual")
    private Integer page;

    /**
     * listas de elementos de retorn
     **/
    @Schema(description = "listas de elementos de retorn")
    private List<?> items;
    /**
     * Tiempo en milisegundos de realizacion de la consulta
     **/
    @Schema(description = "Temps en mil-lisegons de realitzacio de la consulta")
    private Long tiempo;

    /**
     * Status a retornar.
     **/
    @Schema(required = true, description = "Status")
    private String status;

    /**
     * Mensaje de  error.
     **/
    @Schema(description = "Mensaje")
    private String mensaje;

    /**
     * Resultado.
     **/
    @Schema(description = "Texto resultado", required = false)
    private String resultadoURL;

    /**
     * Resultado.
     **/
    @Schema(description = "Long resultado", required = false)
    private Long resultadoLong;


    public RespuestaBase(int total, int size, Integer paginaTamanyo, Integer paginaFirst, String url, List<?> lista, long tiempoMiliSegundos) {
        super();
        Instant finish = Instant.now();
        ZoneId ZONA = ZoneId.of("Europe/Madrid");
        this.dateDownload = finish.atZone(ZONA).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.totalCount = total;
        this.itemsReturned = size;
        this.pageSize = String.valueOf(paginaTamanyo);
        this.totalPages = (paginaTamanyo != null && paginaTamanyo > 0)
                ? (int) Math.ceil((double) total / paginaTamanyo)
                : 0;
        this.page = paginaFirst;
        this.items = lista;
        this.tiempo = tiempoMiliSegundos;

    }


    public RespuestaBase(
            Integer totalCount, Integer itemsReturned, String pageSize, Integer totalPages,
            Integer page, String url, List<?> items, long tiempoEjecucion) {
        super();
        Instant finish = Instant.now();
        ZoneId ZONA = ZoneId.of("Europe/Madrid");
        this.dateDownload = finish.atZone(ZONA).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.totalCount = totalCount;
        this.itemsReturned = itemsReturned;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.page = page;
        this.items = items;
        this.tiempo = tiempoEjecucion;
    }


    public RespuestaBase(List<?> items, long tiempoEjecucion) {
        super();
        Instant finish = Instant.now();
        ZoneId ZONA = ZoneId.of("Europe/Madrid");
        this.dateDownload = finish.atZone(ZONA).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.totalCount = items == null ? 0 : items.size();
        this.itemsReturned = items == null ? 0 : items.size();
        this.pageSize = "1";
        this.totalPages = 0;
        this.page = 0;
        this.items = items;
        this.tiempo = tiempoEjecucion;
    }

    public RespuestaBase() {
        this.dateDownload = "";
        this.totalCount = 0;
        this.itemsReturned = 0;
        this.pageSize = "0";
        this.totalPages = 0;
        this.page = 0;
        this.items = null;
        this.tiempo = 0l;
    }


    public RespuestaBase(String status, String mensaje, long tiempo) {
        this.status = status;
        this.mensaje = mensaje;
        this.tiempo = tiempo;
    }


    public String getDateDownload() {
        return dateDownload;
    }

    public void setDateDownload(String dateDownload) {
        this.dateDownload = dateDownload;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getItemsReturned() {
        return itemsReturned;
    }

    public void setItemsReturned(Integer itemsReturned) {
        this.itemsReturned = itemsReturned;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getResultadoURL() {
        return resultadoURL;
    }

    public void setResultadoURL(String resultadoURL) {
        this.resultadoURL = resultadoURL;
    }

    public Long getResultadoLong() {
        return resultadoLong;
    }

    public void setResultadoLong(Long resultadoLong) {
        this.resultadoLong = resultadoLong;
    }


}
