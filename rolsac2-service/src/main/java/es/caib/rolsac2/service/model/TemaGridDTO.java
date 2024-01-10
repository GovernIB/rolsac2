package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

/**
 * The type Tema grid dto.
 */
@Schema(name = "Tema")
public class TemaGridDTO extends ModelApi implements Comparable<TemaGridDTO> {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     */
    private Long entidad;

    /**
     * Numero
     */
    private Integer numero;

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

    private boolean relacionado;

    /**
     * Materia SIA
     */
    private TipoMateriaSIADTO codigoSIA;

    /**
     * Instancia un nuevo Tema grid dto.
     */
    public TemaGridDTO() {
    }

    @Override
    public int compareTo(TemaGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getTemaPadre(), data2.getTemaPadre()) != 0) {
            return UtilComparador.compareTo(this.getTemaPadre(), data2.getTemaPadre());
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

        if (UtilComparador.compareTo(this.getMathPath(), data2.getMathPath()) != 0) {
            return UtilComparador.compareTo(this.getMathPath(), data2.getMathPath());
        }
        return 0;
    }

    public static int compareTo(List<TemaGridDTO> dato, List<TemaGridDTO> dato2) {
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
            for (TemaGridDTO tipo : dato) {
                boolean existe = false;
                for (TemaGridDTO tipo2 : dato2) {
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

    /**
     * Obtiene id string.
     *
     * @return id string
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
     * @param idString codigo to set
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
     * Obtiene entidad.
     *
     * @return entidad
     */
    public Long getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad entidad
     */
    public void setEntidad(Long entidad) {
        this.entidad = entidad;
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

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene tema padre.
     *
     * @return tema padre
     */
    public String getTemaPadre() {
        return temaPadre;
    }

    /**
     * Establece tema padre.
     *
     * @param temaPadre tema padre
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


    public boolean isRelacionado() {
        return relacionado;
    }

    public void setRelacionado(boolean relacionado) {
        this.relacionado = relacionado;
    }

    public TipoMateriaSIADTO getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(TipoMateriaSIADTO codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    @Override
    public String toString() {
        return "TemaGridDTO{" + "codigo=" + codigo + ", entidad=" + entidad + ", identificador='" + identificador + '\'' + ", temaPadre='" + temaPadre + '\'' + '}';
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

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public Object clone() {
        TemaGridDTO tipo = new TemaGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        tipo.setMathPath(this.getMathPath());
        tipo.setDescripcion(this.getDescripcion());
        tipo.setTemaPadre(this.getTemaPadre());
        tipo.setEntidad(this.getEntidad());
        tipo.setIdString(this.getIdString());
        tipo.setRelacionado(this.isRelacionado());
        tipo.setCodigoSIA(this.codigoSIA);
        return tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
