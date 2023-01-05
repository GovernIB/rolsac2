package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Dades de un grid ficha.
 *
 * @author Indra
 */
@Schema(name = "FichaGrid")
public class FichaGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Validacion.
     */
    public Integer validacion;

    /**
     * Responsable
     */
    private String responsable;

    /**
     * Notas
     */
    private String notas;

    /**
     * Fecha de publicacion
     */
    private Date fechaPublicacion;

    /**
     * Fecha de caducidad
     */
    private Date fechaCaducidad;

    /**
     * Fecha de actualizacion
     */
    private Date fechaActualizacion;

    /**
     * Tema del foro
     */
    private String foro_tema;

    /**
     * Documentos
     */
    private List<FichaDocumentoDTO> documentos;

    /**
     * Enlaces
     */
    private List<FichaEnlaceDTO> enlaces;
    //private Set<FichaUA> fichasua;
    //private Set<HechoVital> hechosVitales;
    /**
     * Informacion
     */
    private String info;

    /**
     * Publico objetivo
     */
    private Set<TipoPublicoObjetivoDTO> publicosObjetivo;

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
     * Obtiene validacion.
     *
     * @return  validacion
     */
    public Integer getValidacion() {
        return validacion;
    }

    /**
     * Establece validacion.
     *
     * @param validacion  validacion
     */
    public void setValidacion(Integer validacion) {
        this.validacion = validacion;
    }

    /**
     * Obtiene documentos.
     *
     * @return  documentos
     */
    public List<FichaDocumentoDTO> getDocumentos() {
        return documentos;
    }

    /**
     * Establece documentos.
     *
     * @param documentos  documentos
     */
    public void setDocumentos(List<FichaDocumentoDTO> documentos) {
        this.documentos = documentos;
    }

    /**
     * Obtiene enlaces.
     *
     * @return  enlaces
     */
    public List<FichaEnlaceDTO> getEnlaces() {
        return enlaces;
    }

    /**
     * Establece enlaces.
     *
     * @param enlaces  enlaces
     */
    public void setEnlaces(List<FichaEnlaceDTO> enlaces) {
        this.enlaces = enlaces;
    }

    /**
     * Obtiene info.
     *
     * @return  info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Establece info.
     *
     * @param info  info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Obtiene publicos objetivo.
     *
     * @return  publicos objetivo
     */
    public Set<TipoPublicoObjetivoDTO> getPublicosObjetivo() {
        return publicosObjetivo;
    }

    /**
     * Establece publicos objetivo.
     *
     * @param publicosObjetivo  publicos objetivo
     */
    public void setPublicosObjetivo(Set<TipoPublicoObjetivoDTO> publicosObjetivo) {
        this.publicosObjetivo = publicosObjetivo;
    }

    /**
     * Obtiene responsable.
     *
     * @return  responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * Establece responsable.
     *
     * @param responsable  responsable
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    /**
     * Obtiene notas.
     *
     * @return  notas
     */
    public String getNotas() {
        return notas;
    }

    /**
     * Establece notas.
     *
     * @param notas  notas
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }

    /**
     * Obtiene fecha publicacion.
     *
     * @return  fecha publicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece fecha publicacion.
     *
     * @param fechaPublicacion  fecha publicacion
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene fecha caducidad.
     *
     * @return  fecha caducidad
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Establece fecha caducidad.
     *
     * @param fechaCaducidad  fecha caducidad
     */
    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * Obtiene fecha actualizacion.
     *
     * @return  fecha actualizacion
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Establece fecha actualizacion.
     *
     * @param fechaActualizacion  fecha actualizacion
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Obtiene foro tema.
     *
     * @return  foro tema
     */
    public String getForo_tema() {
        return foro_tema;
    }

    /**
     * Establece foro tema.
     *
     * @param foro_tema  foro tema
     */
    public void setForo_tema(String foro_tema) {
        this.foro_tema = foro_tema;
    }

    @Override
    public String toString() {
        return "FichaGridDTO{" +
                "id=" + codigo +
                ", responsable='" + responsable + '\'' +
                '}';
    }
}
