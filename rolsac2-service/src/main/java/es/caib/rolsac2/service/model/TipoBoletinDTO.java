package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * El tipo Tipo boletin dto.
 */
@Schema(name = "TipoBoletin")
public class TipoBoletinDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    @NotEmpty
    @Size(max = 50)
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
     * Instancia un nuevo Tipo boletin dto.
     */
    public TipoBoletinDTO() {
    }

    /**
     * Instancia un nuevo Tipo boletin dto.
     *
     * @param id             id
     * @param identificador  identificador
     * @param nombre         nombre
     * @param url            url
     */
    public TipoBoletinDTO(Long id, String identificador, String nombre, String url) {
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
        return "TipoBoletinDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoBoletinDTO that = (TipoBoletinDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador, that.identificador) && Objects.equals(nombre, that.nombre) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, nombre, url);
    }
}
