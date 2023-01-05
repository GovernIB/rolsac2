package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "Procedimiento")
public class ProcedimientoGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Codigo del workflow modificado
     */
    private Long codigoWFMod;

    /**
     * Codigo del workflow publicado
     */
    private Long codigoWFPub;

    /**
     *  Tipo
     */
    private String tipo;

    /**
     * Estado
     */
    private String estado;

    /**
     * Estado SIA
     */
    private Boolean estadoSIA;

    /**
     * Fecha SIA
     */
    public LocalDate siaFecha;

    /**
     * Codigo DIR3 SIA
     */
    private String codigoDir3SIA;

    /**
     * Codigo SIA
     */
    private Integer codigoSIA;

    /**
     * Nombre
     */
    private String nombre;

    /**
     * Fecha
     */
    private LocalDate fecha;

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
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene estado sia.
     *
     * @return  estado sia
     */
    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * Establece estado sia.
     *
     * @param estadoSIA  estado sia
     */
    public void setEstadoSIA(Boolean estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    /**
     * Obtiene sia fecha.
     *
     * @return  sia fecha
     */
    public LocalDate getSiaFecha() {
        return siaFecha;
    }

    /**
     * Establece sia fecha.
     *
     * @param fechaSIA  fecha sia
     */
    public void setSiaFecha(LocalDate fechaSIA) {
        this.siaFecha = fechaSIA;
    }

    /**
     * Obtiene codigo sia.
     *
     * @return  codigo sia
     */
    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * Establece codigo sia.
     *
     * @param codigoSIA  codigo sia
     */
    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    /**
     * Obtiene codigo dir 3 sia.
     *
     * @return  codigo dir 3 sia
     */
    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    /**
     * Establece codigo dir 3 sia.
     *
     * @param codigoDir3SIA  codigo dir 3 sia
     */
    public void setCodigoDir3SIA(String codigoDir3SIA) {
        this.codigoDir3SIA = codigoDir3SIA;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene estado.
     *
     * @return  estado
     */
    public String getEstado() {
        if (estado == null) {
            return "";
        }
        return estado;
    }

    /**
     * Establece estado.
     *
     * @param estado  estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene codigo wf mod.
     *
     * @return  codigo wf mod
     */
    public Long getCodigoWFMod() {
        return codigoWFMod;
    }

    /**
     * Establece codigo wf mod.
     *
     * @param codigoWFMod  codigo wf mod
     */
    public void setCodigoWFMod(Long codigoWFMod) {
        this.codigoWFMod = codigoWFMod;
    }

    /**
     * Obtiene codigo wf pub.
     *
     * @return  codigo wf pub
     */
    public Long getCodigoWFPub() {
        return codigoWFPub;
    }

    /**
     * Establece codigo wf pub.
     *
     * @param codigoWFPub  codigo wf pub
     */
    public void setCodigoWFPub(Long codigoWFPub) {
        this.codigoWFPub = codigoWFPub;
    }

    /**
     * Tiene datos publicados boolean.
     *
     * @return  boolean
     */
    public boolean tieneDatosPublicados() {
        return this.codigoWFPub != null;
    }

    /**
     * Tiene datos en modificacion boolean.
     *
     * @return  boolean
     */
    public boolean tieneDatosEnModificacion() {
        return this.codigoWFMod != null;
    }

    /**
     * Obtiene fecha.
     *
     * @return  fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece fecha.
     *
     * @param fecha  fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", codigoSIA='" + codigoSIA + '\'' +
                '}';
    }
}
