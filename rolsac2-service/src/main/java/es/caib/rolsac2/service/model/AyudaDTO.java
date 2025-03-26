package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * El tipo Alerta dto.
 */
@Schema(name = "Ayuda")
public class AyudaDTO extends ModelApi {


    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Perfil
     */
    private String perfil;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Fecha creacion
     */
    private Date fechaCreacion;

    /**
     * Fecha modificacion
     */
    private Date fechaModificacion;


    /**
     * Instancia un nuevo Alerta dto.
     */
    public AyudaDTO() {
        //Vacio
    }

    /**
     * Instancia un nuevo Alerta dto.
     *
     * @param codigo codigo
     */
    public AyudaDTO(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Constructor para crear a partir de otra alerta
     *
     * @param otro
     */
    public AyudaDTO(AyudaDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.identificador = otro.identificador;
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
            this.perfil = otro.perfil;
            this.fechaCreacion = otro.fechaCreacion;
            this.fechaModificacion = otro.fechaModificacion;
        }
    }

    /**
     * Create instance Alerta dto.
     *
     * @return Alerta dto
     */
    public static AyudaDTO createInstance(List<String> idiomas) {
        AyudaDTO alerta = new AyudaDTO();
        alerta.setDescripcion(Literal.createInstance(idiomas));
        return alerta;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AyudaDTO alertaDTO = (AyudaDTO) o;
        return codigo.equals(alertaDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "AlertaDTO{" + "codigo=" + codigo + ", identificador=" + identificador + ", perfil='" + perfil + '\'' + '}';
    }

    /**
     * Compare to int.
     *
     * @param tema tema
     * @return int
     */
    public int compareTo(final AyudaDTO tema) {
        if (tema == null) throw new NullPointerException("tema");

        return Long.compare(this.getCodigo(), tema.getCodigo());
    }

    public AyudaGridDTO toGridDTO() {
        AyudaGridDTO ayudaGridDTO = new AyudaGridDTO();
        ayudaGridDTO.setCodigo(this.codigo);
        ayudaGridDTO.setDescripcion(this.descripcion);
        ayudaGridDTO.setPerfil(this.perfil);
        ayudaGridDTO.setFechaCreacion(this.fechaCreacion);
        ayudaGridDTO.setFechaModificacion(this.fechaModificacion);
        return ayudaGridDTO;
    }

    @Override
    public AyudaDTO clone() {
        return new AyudaDTO(this);
    }
}
