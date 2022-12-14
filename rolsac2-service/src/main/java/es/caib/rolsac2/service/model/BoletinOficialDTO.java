package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Boletin oficial dto.
 */
@Schema(name = "BoletinOficial")
public class BoletinOficialDTO extends ModelApi {

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
     * Normativa
     */
    private Long normativa;

    /**
     * Instancia un nuevo Boletin oficial dto.
     */
    public BoletinOficialDTO(){}


    /**
     * Obtiene codigo.
     *
     * @return el codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo the codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return el identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador el identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene nombre.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre el nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene url.
     *
     * @return la url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url la url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene normativa.
     *
     * @return la normativa
     */
    public Long getNormativa() {
        return normativa;
    }

    /**
     * Establece normativa.
     *
     * @param normativa la normativa
     */
    public void setNormativa(Long normativa) {
        this.normativa = normativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoletinOficialDTO that = (BoletinOficialDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "BoletinOficialDTO{" +
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                ", normativa=" + normativa +
                '}';
    }
}
