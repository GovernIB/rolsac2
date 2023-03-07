package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * La clase J plugin.
 */
@Entity
@Table(name = "RS2_SESION",
        indexes = {
                @Index(name = "RS2_SESION_PK_I", columnList = "SESI_USU")
        }
)
@NamedQueries({
        @NamedQuery(name = JSesion.FIND_BY_ID,
                query = "select j from JSesion j where j.idUsuario = :idUsu"),
        @NamedQuery(name = JSesion.COUNT_BY_ID, query = "select count(j) from JSesion j where j.idUsuario=: idUsu")
})
public class JSesion extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Sesion.FIND_BY_ID";

    /**
     * La consulta COUNT_BY_ID.
     */
    public static final String COUNT_BY_ID = "Sesion.COUNT_BY_ID";

    /**
     * Codigo
     **/
    @Id
    @Column(name = "SESI_USU", nullable = false)
    private Long idUsuario;

    /**
     * Fecha última sesión
     */
    @Column(name="SESI_FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaSesion;

    /**
     * Perfil del usuario
     */
    @Column(name="SESI_PERFIL", nullable = false, length = 50)
    private String perfil;

    /**
     * Idioma utilizado
     */
    @Column(name="SESI_IDIOMA", nullable = false, length = 2)
    private String idioma;

    /**
     * Entidad Activa
     **/
    @Column(name="SESI_ENTIDA")
    private Long idEntidad;

    /**
     * UA Activa
     **/
    @Column(name="SESI_UA")
    private Long idUa;

    /**
     * Tipo
     **/

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
        JSesion jSesion = (JSesion) o;
        return Objects.equals(idUsuario, jSesion.idUsuario) && Objects.equals(fechaUltimaSesion, jSesion.fechaUltimaSesion) && Objects.equals(perfil, jSesion.perfil) && Objects.equals(idioma, jSesion.idioma) && Objects.equals(idEntidad, jSesion.idEntidad) && Objects.equals(idUa, jSesion.idUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, fechaUltimaSesion, perfil, idioma, idEntidad, idUa);
    }

    @Override
    public String toString() {
        return "JSesion{" +
                "idUsuario=" + idUsuario +
                ", fechaUltimaSesion=" + fechaUltimaSesion +
                ", perfil='" + perfil + '\'' +
                ", idioma='" + idioma + '\'' +
                ", idEntidad=" + idEntidad +
                ", idUa=" + idUa +
                '}';
    }
}
