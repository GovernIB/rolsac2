package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Dades de una ficha.
 *
 * @author Indra
 */
@Schema(name = "Ficha")
public class FichaDTO extends ModelApi {
    private Long codigo;

    public Integer validacion;
    private String responsable;
    private String notas;

    private Date fechaPublicacion;
    private Date fechaCaducidad;
    private Date fechaActualizacion;
    private String foro_tema;
    private List<FichaDocumentoDTO> documentos;
    private List<FichaEnlaceDTO> enlaces;
    //private Set<FichaUA> fichasua;
    //private Set<HechoVital> hechosVitales;
    private String info;
    private Set<TipoPublicoObjetivoDTO> publicosObjetivo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getValidacion() {
        return validacion;
    }

    public void setValidacion(Integer validacion) {
        this.validacion = validacion;
    }

    public List<FichaDocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<FichaDocumentoDTO> documentos) {
        this.documentos = documentos;
    }

    public List<FichaEnlaceDTO> getEnlaces() {
        return enlaces;
    }

    public void setEnlaces(List<FichaEnlaceDTO> enlaces) {
        this.enlaces = enlaces;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<TipoPublicoObjetivoDTO> getPublicosObjetivo() {
        return publicosObjetivo;
    }

    public void setPublicosObjetivo(Set<TipoPublicoObjetivoDTO> publicosObjetivo) {
        this.publicosObjetivo = publicosObjetivo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getForo_tema() {
        return foro_tema;
    }

    public void setForo_tema(String foro_tema) {
        this.foro_tema = foro_tema;
    }

    @Override
    public String toString() {
        return "FichaDTO{" +
                "id=" + codigo +
                ", responsable='" + responsable + '\'' +
                '}';
    }
}
