package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Dades de una sessió.
 *
 * @author Indra
 */
@Schema(name = "Sesion")
public class SesionDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long idUsuario;

    /**
     * Fecha última sesión.
     */
    private Date fechaUltimaSesion;

    /**
     * Perfil
     */
    private String perfil;

    /**
     * Idioma
     */
    private String idioma;

    /**
     * ID Entidad Activa
     */
    private Long idEntidad;

    /**
     * ID UA Activa
     */
    private Long idUa;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaUltimaSesion() {
        return fechaUltimaSesion;
    }

    public void setFechaUltimaSesion(Date fechaUltimaSesion) {
        this.fechaUltimaSesion = fechaUltimaSesion;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Long getIdUa() {
        return idUa;
    }

    public void setIdUa(Long idUa) {
        this.idUa = idUa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SesionDTO sesionDTO = (SesionDTO) o;
        return Objects.equals(idUsuario, sesionDTO.idUsuario) && Objects.equals(fechaUltimaSesion, sesionDTO.fechaUltimaSesion) && Objects.equals(perfil, sesionDTO.perfil) && Objects.equals(idioma, sesionDTO.idioma) && Objects.equals(idEntidad, sesionDTO.idEntidad) && Objects.equals(idUa, sesionDTO.idUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, fechaUltimaSesion, perfil, idioma, idEntidad, idUa);
    }

    @Override
    public String toString() {
        return "SesionDTO{" +
                "idUsuario=" + idUsuario +
                ", fechaUltimaSesion=" + fechaUltimaSesion +
                ", perfil='" + perfil + '\'' +
                ", idioma='" + idioma + '\'' +
                ", idEntidad=" + idEntidad +
                ", idUa=" + idUa +
                '}';
    }
}
