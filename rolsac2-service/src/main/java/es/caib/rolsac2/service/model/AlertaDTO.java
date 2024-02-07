package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * El tipo Alerta dto.
 */
@Schema(name = "Alerta")
public class AlertaDTO extends ModelApi {


    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     */
    private EntidadDTO entidad;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Unidad administrativa cuando ambito es UA.
     */
    private UnidadAdministrativaDTO unidadAdministrativa;

    /**
     * Identificador
     */
    private String tipo;

    /**
     * Identificador
     */
    private String ambito;

    /**
     * Fecha ini
     */
    private Date fechaIni;

    /**
     * Fecha ini
     */
    private Date fechaFin;

    /**
     * Descendientes UA cuando ambito es UA.
     */
    private boolean descendientes;

    /**
     * Perfil cuando ambito es de tipo perfil
     */
    private String perfil;

    /**
     * Instancia un nuevo Alerta dto.
     */
    public AlertaDTO() {
        //Vacio
    }

    /**
     * Instancia un nuevo Alerta dto.
     *
     * @param codigo codigo
     */
    public AlertaDTO(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Constructor para crear a partir de otra alerta
     *
     * @param otro
     */
    public AlertaDTO(AlertaDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.entidad = otro.entidad;
            this.tipo = otro.tipo;
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
            this.unidadAdministrativa = otro.unidadAdministrativa == null ? null : (UnidadAdministrativaDTO) otro.unidadAdministrativa.clone();
            this.ambito = otro.ambito;
            this.fechaIni = otro.fechaIni;
            this.fechaFin = otro.fechaFin;
            this.descendientes = otro.descendientes;
            this.perfil = otro.perfil;
        }
    }

    /**
     * Create instance Alerta dto.
     *
     * @return Alerta dto
     */
    public static AlertaDTO createInstance(List<String> idiomas) {
        AlertaDTO alerta = new AlertaDTO();
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

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isDescendientes() {
        return descendientes;
    }

    public void setDescendientes(boolean descendientes) {
        this.descendientes = descendientes;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertaDTO alertaDTO = (AlertaDTO) o;
        return codigo.equals(alertaDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "AlertaDTO{" + "codigo=" + codigo + ", entidad=" + entidad + ", tipo='" + tipo + '\'' + '}';
    }

    /**
     * Compare to int.
     *
     * @param tema tema
     * @return int
     */
    public int compareTo(final AlertaDTO tema) {
        if (tema == null) throw new NullPointerException("tema");

        return Long.compare(this.getCodigo(), tema.getCodigo());
    }

    public AlertaGridDTO toGridDTO() {
        AlertaGridDTO alertaGridDTO = new AlertaGridDTO();
        alertaGridDTO.setEntidad(this.entidad);
        alertaGridDTO.setCodigo(this.codigo);
        alertaGridDTO.setTipo(this.tipo);
        alertaGridDTO.setDescripcion(this.descripcion);
        alertaGridDTO.setAmbito(this.ambito);
        alertaGridDTO.setFechaIni(this.fechaIni);
        alertaGridDTO.setFechaFin(this.fechaFin);
        return alertaGridDTO;
    }

    @Override
    public AlertaDTO clone() {
        return new AlertaDTO(this);
    }
}
