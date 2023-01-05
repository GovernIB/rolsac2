package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * El tipo Tipo boletin grid dto.
 */
@Schema(name = "TipoBoletinGrid")
public class TipoBoletinGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */ 
    private String identificador;

    /**
     * Nombre
     */
    private String nombre;

    /**
     * Url
     */
    private String url;

    /**
     * Instancia un nuevo Tipo boletin grid dto.
     */
    public TipoBoletinGridDTO() {
    }

    /**
     * Instancia un nuevo Tipo boletin grid dto.
     *
     * @param id             id
     * @param identificador  identificador
     * @param nombre         nombre
     * @param url            url
     */
    public TipoBoletinGridDTO(Long id, String identificador, String nombre, String url) {
        this.codigo = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.url = url;
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene url.
     *
     * @return  url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url  url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TipoBoletinGridDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
