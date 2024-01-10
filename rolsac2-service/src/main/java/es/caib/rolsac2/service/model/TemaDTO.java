package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Tema dto.
 */
@Schema(name = "Tema")
public class TemaDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     */
    private EntidadDTO entidad;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Tema padre
     */
    private TemaDTO temaPadre;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Path del objeto para organizarlo por niveles
     */
    private String mathPath;

    /**
     * Materia SIA
     */
    private TipoMateriaSIADTO tipoMateriaSIA;

    /**
     * Instancia un nuevo Tema dto.
     */
    public TemaDTO() {
    }

    /**
     * Instancia un nuevo Tema dto.
     *
     * @param codigo codigo
     */
    public TemaDTO(Long codigo) {
        this.codigo = codigo;
    }

    public TemaDTO(TemaDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.entidad = otro.entidad == null ? null : (EntidadDTO) otro.entidad.clone();
            this.identificador = otro.identificador;
            this.temaPadre = otro.temaPadre == null ? null : (TemaDTO) otro.temaPadre.clone();
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
            this.mathPath = otro.mathPath;
            this.tipoMateriaSIA = otro.tipoMateriaSIA;
        }
    }

    /**
     * Create instance tema dto.
     *
     * @return tema dto
     */
    public static TemaDTO createInstance() {
        TemaDTO tema = new TemaDTO();
        tema.setDescripcion(Literal.createInstance());
        return tema;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return codigo
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
     * Obtiene entidad.
     *
     * @return entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad entidad
     */
    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene tema padre.
     *
     * @return tema padre
     */
    public TemaDTO getTemaPadre() {
        return temaPadre;
    }

    /**
     * Establece tema padre.
     *
     * @param temaPadre tema padre
     */
    public void setTemaPadre(TemaDTO temaPadre) {
        this.temaPadre = temaPadre;
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

    public String getMathPath() {
        return mathPath;
    }

    public void setMathPath(String mathPath) {
        this.mathPath = mathPath;
    }

    public TipoMateriaSIADTO getTipoMateriaSIA() {
        return tipoMateriaSIA;
    }

    public void setTipoMateriaSIA(TipoMateriaSIADTO tipoMateriaSIA) {
        this.tipoMateriaSIA = tipoMateriaSIA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemaDTO temaDTO = (TemaDTO) o;
        return codigo.equals(temaDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "TemaDTO{" + "codigo=" + codigo + ", entidad=" + entidad + ", identificador='" + identificador + '\'' + ", temaPadre=" + temaPadre + '}';
    }

    /**
     * Compare to int.
     *
     * @param tema tema
     * @return int
     */
    public int compareTo(final TemaDTO tema) {
        if (tema == null) throw new NullPointerException("tema");

        return Long.compare(this.getCodigo(), tema.getCodigo());
    }

    public TemaGridDTO toGridDTO() {
        TemaGridDTO temaGridDTO = new TemaGridDTO();
        temaGridDTO.setTemaPadre(this.temaPadre.identificador);
        temaGridDTO.setEntidad(this.entidad.getCodigo());
        temaGridDTO.setCodigo(this.codigo);
        temaGridDTO.setIdentificador(this.identificador);
        temaGridDTO.setCodigoSIA(this.tipoMateriaSIA);

        return temaGridDTO;
    }

    @Override
    public TemaDTO clone() {
        return new TemaDTO(this);
    }
}
