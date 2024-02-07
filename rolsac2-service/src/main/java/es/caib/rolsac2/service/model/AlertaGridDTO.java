package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The type Alerta grid dto.
 */
@Schema(name = "AlegraGrid")
public class AlertaGridDTO extends ModelApi implements Comparable<AlertaGridDTO> {

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
     * Entidad
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
     * Fecha  (Es de AlertaUsuarioDTO pero así se reaprovecha)
     */
    private Date fecha;


    /**
     * Instancia un nuevo Tema grid dto.
     */
    public AlertaGridDTO() {
    }

    @Override
    public int compareTo(AlertaGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }


        return 0;
    }

    public static int compareTo(List<AlertaGridDTO> dato, List<AlertaGridDTO> dato2) {
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
            for (AlertaGridDTO tipo : dato) {
                boolean existe = false;
                for (AlertaGridDTO tipo2 : dato2) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "AlertaGridDTO{" + "codigo=" + codigo + ", entidad=" + entidad + ", tipo='" + tipo + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertaGridDTO that = (AlertaGridDTO) o;
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
        AlertaGridDTO tipo = new AlertaGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setDescripcion(this.getDescripcion());
        tipo.setEntidad(this.getEntidad());
        tipo.setIdString(this.getIdString());
        tipo.setTipo(this.getTipo());
        tipo.setFechaIni(this.getFechaIni());
        tipo.setFechaFin(this.getFechaFin());
        tipo.setAmbito(this.getAmbito());
        tipo.setUnidadAdministrativa(this.getUnidadAdministrativa());
        tipo.setFecha(this.getFecha());
        return tipo;
    }

}
