package es.caib.rolsac2.api.interna.v1.model.respuesta;

import es.caib.rolsac2.api.interna.v1.model.Procedimientos;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a todas las respuestas.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "RespuestaBase", description = "Respuesta Base")
public class RespuestaBase <T>{

    @Schema(description = "Listado con los objetos de resultado")
    private List<T> data;

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
     * Numero de Elementos.
     **/
    @Schema(required = true, description = "Numero de Elementos")
    private Long numeroElementos;

    @Schema(description = "Tamaño de la página")
    private int pageSize;

    @Schema(description = "Total de elementos")
    private long totalCount;

    @Schema(description = "Número de elementos devueltos")
    private String itemsReturned;

    @Schema(description = "Número de la página actual")
    private int page;

    @Schema(description = "Fecha actual en formato ISO8601")
    private String dateDownload;

    @Schema(description = "Nombre del método")
    private String name;

    @Schema(description = "Owner dir3")
    private String ownerDir3;

    /**
     * codigo
     **/
    @Schema(description = "Tiempo en milisegundos de realizacion de la consulta")
    private Long tiempo;

    public RespuestaBase(String status, String mensaje, Long numeroElementos, Long tiempo) {
        super();
        this.status = status;
        this.mensaje = mensaje;
        this.numeroElementos = numeroElementos;
        this.tiempo = tiempo;
    }

    public RespuestaBase() {
        this.status = null;
        this.mensaje = null;
        this.numeroElementos = null;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * @return the mensajeError
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensajeError to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the numeroElementos
     */
    public Long getNumeroElementos() {
        return numeroElementos;
    }

    /**
     * @param numeroElementos the numeroElementos to set
     */
    public void setNumeroElementos(Long numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

    /**
     * Método que devuelve el tiempo de la consulta.
     *
     * @return tiempo
     */
    public Long getTiempo() {
        return tiempo;
    }

    /**
     * Método que establece el tiempo de la consulta.
     */
    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public String getItemsReturned() {
        return itemsReturned;
    }

    public void setItemsReturned(String itemsReturned) {
        this.itemsReturned = itemsReturned;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getDateDownload() {
        return dateDownload;
    }

    public void setDateDownload(String dateDownload) {
        this.dateDownload = dateDownload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerDir3(String ownerDir3) {
        this.ownerDir3 = ownerDir3;
    }

    public String getOwnerDir3() {
        return ownerDir3;
    }





    public static class Builder<T> {
        private List<T> data;
        private String url;
        private int pageSize;
        private long totalCount;
        private String itemsReturned;
        private int page;
        private String dateDownload;
        private String name;
        private String ownerDir3;
        private String status;
        private String mensaje;
        private Long numeroElementos;
        private Long tiempo;

        public Builder data(List<T> data) {
            this.data = data;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder totalCount(long totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder itemsReturned(String itemsReturned) {
            this.itemsReturned = itemsReturned;
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder dateDownload(String dateDownload) {
            this.dateDownload = dateDownload;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ownerDir3(String ownerDir3) {
            this.ownerDir3 = ownerDir3;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder numeroElementos(Long numeroElementos) {
            this.numeroElementos = numeroElementos;
            return this;
        }

        public Builder tiempo(Long tiempo) {
            this.tiempo = tiempo;
            return this;
        }

        public RespuestaBase<T> build() {
            RespuestaBase respuesta = new RespuestaBase<T>();
            respuesta.setData(this.data);
//            respuesta.setUrl(this.url);
            respuesta.setPageSize(this.pageSize);
            respuesta.setTotalCount(this.totalCount);
            respuesta.setItemsReturned(this.itemsReturned);
            respuesta.setPage(this.page);
            respuesta.setDateDownload(this.dateDownload);
            respuesta.setName(this.name);
            respuesta.setOwnerDir3(this.ownerDir3);
            respuesta.setStatus(this.status);
            respuesta.setMensaje(this.mensaje);
            respuesta.setNumeroElementos(this.numeroElementos);
            respuesta.setTiempo(this.tiempo);
            return respuesta;
        }
    }
}
