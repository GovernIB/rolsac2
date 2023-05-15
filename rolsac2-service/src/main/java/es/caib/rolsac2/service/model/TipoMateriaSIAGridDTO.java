package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * El tipo Tipo materia sia grid dto.
 *
 * @author Indra
 */
@Schema(name = "TipoMateriaSIAGrid")
public class TipoMateriaSIAGridDTO extends ModelApi implements Cloneable {

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
     * Descripción
     */
    private Literal descripcion;

    /**
     * Codigo SIA
     */
    private Long codigoSIA;

    /**
     * Instancia un nuevo Tipo materia sia grid dto.
     */
    public TipoMateriaSIAGridDTO() {
    }

    /**
     * Instacia un nuevo Tipo materia sia grid dto.
     *
     * @param id            id
     * @param identificador identificador
     */
    public TipoMateriaSIAGridDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
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

    public Long getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Long codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    @Override
    public String toString() {
        return "TipoMateriaSIAGridDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + '}';
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        TipoMateriaSIAGridDTO tipo = new TipoMateriaSIAGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        tipo.setCodigoSIA(this.getCodigoSIA());

        if (this.getDescripcion() != null) {
            tipo.setDescripcion((Literal) this.getDescripcion().clone());
        }
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoMateriaSIAGridDTO that = (TipoMateriaSIAGridDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }


    public int compareTo(TipoMateriaSIAGridDTO data2) {
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

        return 0;
    }

    public static int compareTo(List<TipoMateriaSIAGridDTO> dato, List<TipoMateriaSIAGridDTO> dato2) {
        if ((dato == null || dato.size() == 0) && (dato2 == null || dato2.size() == 0)) {
            return 0;
        }
        if ((dato == null || dato.size() == 0) && (dato2 != null && dato2.size() > 0)) {
            return -1;
        }
        if ((dato != null && dato.size() > 0) && (dato2 == null || dato2.size() == 0)) {
            return 1;
        }

        if (dato.size() > dato2.size()) {
            return 1;
        } else if (dato2.size() > dato.size()) {
            return -1;
        } else {
            for (TipoMateriaSIAGridDTO tipo : dato) {
                boolean existe = false;
                for (TipoMateriaSIAGridDTO tipo2 : dato2) {
                    if (tipo.compareTo(tipo2) == 0) {
                        existe = true;
                    }
                }
                if (!existe) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
