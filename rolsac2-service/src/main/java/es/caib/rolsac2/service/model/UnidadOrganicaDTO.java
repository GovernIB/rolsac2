package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeEstadoDir3;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Column;
import java.util.Objects;

/**
 * El tipo Tipo afectacion dto.
 *
 * @author Indra
 */
@Schema(name = "UnidadOrganica")
public class UnidadOrganicaDTO extends ModelApi implements Comparable<UnidadOrganicaDTO>{

    /**
     * Codigo
     */
    private Long codigo;

    /**
     *  Código DIR3 de la unidad
     */
    private String codigoDir3;

    /**
     * Identificador de la entidad
     */
    private Long idEntidad;

    /**
     * Código DIR3 del padre de la unidad
     */
    private String codigoDir3Padre;

    /**
     * Denominación de la unidad administrativa
     */
    private String denominacion;

    /**
     * Versión de la unidad administrativa
     */
    private Integer version;

    /**
     * Concatenación de la denominación y código DIR3 en un solo String para facilitar la búsqueda
     */
    private String denominacionDir3;

    /**
     * Aplicación de la que proviene
     */
    private String aplicacion;
    private TypeEstadoDir3 estado;

    /**
     * Instancia un nuevo Tipo afectacion dto.
     */
    public UnidadOrganicaDTO() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCodigoDir3() {
        return codigoDir3;
    }

    public void setCodigoDir3(String codigoDir3) {
        this.codigoDir3 = codigoDir3;
    }


    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getCodigoDir3Padre() {
        return codigoDir3Padre;
    }

    public void setCodigoDir3Padre(String codigoDir3Padre) {
        this.codigoDir3Padre = codigoDir3Padre;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public TypeEstadoDir3 getEstado() {
        return estado;
    }

    public void setEstado(TypeEstadoDir3 estado) {
        this.estado = estado;
    }

    public String getDenominacionDir3() {
        return denominacionDir3;
    }

    public void setDenominacionDir3(String denominacionDir3) {
        this.denominacionDir3 = denominacionDir3;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadOrganicaDTO that = (UnidadOrganicaDTO) o;
        return Objects.equals(codigoDir3, that.codigoDir3) && Objects.equals(codigoDir3Padre, that.codigoDir3Padre) && Objects.equals(denominacion, that.denominacion) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codigoDir3, idEntidad, codigoDir3Padre, denominacion, estado);
    }

    @Override
    public String toString() {
        return "UnidadOrganicaDTO{" +
                "codigo=" + codigo +
                ", codigoDir3='" + codigoDir3 + '\'' +
                ", idEntidad=" + idEntidad +
                ", codigoDir3Padre='" + codigoDir3Padre + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", estado=" + estado +
                ", version=" + version +
                '}';
    }

    @Override
    public int compareTo(UnidadOrganicaDTO o) {
        return this.codigoDir3.compareTo(o.codigoDir3);
    }
}
