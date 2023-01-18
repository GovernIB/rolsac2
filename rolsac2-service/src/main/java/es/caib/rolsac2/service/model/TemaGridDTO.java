package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * The type Tema grid dto.
 */
@Schema(name = "Tema")
public class TemaGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     */
    private Long entidad;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Tema padre
     */
    private String temaPadre;

    /**
     * MathPath
     */
    private String mathPath;

    /**
     * Instancia un nuevo Tema grid dto.
     */
    public TemaGridDTO(){}

    /**
     * Obtiene id string.
     *
     * @return  id string
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
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
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public Long getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(Long entidad) {
        this.entidad = entidad;
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

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene tema padre.
     *
     * @return  tema padre
     */
    public String getTemaPadre() {
        return temaPadre;
    }

    /**
     * Establece tema padre.
     *
     * @param temaPadre  tema padre
     */
    public void setTemaPadre(String temaPadre) {
        this.temaPadre = temaPadre;
    }

    public String getMathPath() {
        return mathPath;
    }

    public void setMathPath(String mathPath) {
        this.mathPath = mathPath;
    }

    @Override
    public String toString() {
        return "TemaGridDTO{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", identificador='" + identificador + '\'' +
                ", temaPadre='" + temaPadre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemaGridDTO that = (TemaGridDTO) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
