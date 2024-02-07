package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Tipo materia siadto.
 *
 * @author Indra
 */
@Schema(name = "TipoMateriaSIA")
public class TipoMateriaSIADTO extends ModelApi implements Cloneable {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Codigo SIA
     */
    private Long codigoSIA;

    /**
     * Instancia un nuevo Tipo materia siadto.
     */
    public TipoMateriaSIADTO() {
        //Constructor vacio
    }

    /**
     * Instancia un nuevo Tipo materia siadto.
     *
     * @param id            id
     * @param identificador identificador
     */
    public TipoMateriaSIADTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
    }

    /**
     * Instancia una nueva Entidad dto.
     *
     * @param otro the otro
     */
    public TipoMateriaSIADTO(TipoMateriaSIADTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.identificador = otro.identificador;
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
            this.codigoSIA = otro.codigoSIA;
        }
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene codigo SIA.
     *
     * @return codigoSIA
     */
    public Long getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * Establece codig o SIA.
     *
     * @param codigoSIA codigoSIA
     */
    public void setCodigoSIA(Long codigoSIA) {
        this.codigoSIA = codigoSIA;
    }


    @Override
    public String toString() {
        return "TipoMateriaSIADTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + '}';
    }

    @Override
    public TipoMateriaSIADTO clone() {
        return new TipoMateriaSIADTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoMateriaSIADTO that = (TipoMateriaSIADTO) o;
        boolean igual = codigo.equals(that.codigo);
        return igual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public int compareTo(TipoMateriaSIADTO data2) {

        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        if (UtilComparador.compareTo(this.getIdentificador(), data2.getIdentificador()) != 0) {
            return UtilComparador.compareTo(this.getIdentificador(), data2.getIdentificador());
        }

        if (UtilComparador.compareTo(this.getCodigoSIA(), data2.getCodigoSIA()) != 0) {
            return UtilComparador.compareTo(this.getCodigoSIA(), data2.getCodigoSIA());
        }
        return 0;
    }
}
