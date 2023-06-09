package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un TipoNormativa.
 *
 * @author jsegovia
 */
@Schema(name = "TipoNormativa")
public class TipoNormativaDTO extends ModelApi {

    private Long codigo;
    private String identificador;
    private Literal descripcion;
    private Long codigoSIA;
    private Long codigoBOIB;

    public TipoNormativaDTO() {
    }

    public TipoNormativaDTO(TipoNormativaDTO otro) {
        if (otro != null) {
            this.codigo = otro.getCodigo();
            this.identificador = otro.getIdentificador();
            this.descripcion = otro.getDescripcion() == null ? null : (Literal) otro.descripcion.clone();
        }
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public TipoNormativaDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoNormativaDTO that = (TipoNormativaDTO) o;
        return codigo.equals(that.codigo);
    }

    public Long getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Long codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public Long getCodigoBOIB() {
        return codigoBOIB;
    }

    public void setCodigoBOIB(Long codigoBOIB) {
        this.codigoBOIB = codigoBOIB;
    }

    @Override
    public TipoNormativaDTO clone() {
        return new TipoNormativaDTO(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "TipoNormativaDTO{" + "id=" + codigo + ", identificador=" + identificador + ", descripcion=" + descripcion.toString() + '}';
    }
}
