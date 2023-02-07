package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * La clase J unidad administrativa auditoria.
 */
@Entity
@SequenceGenerator(name = "ua-audit-sequence", sequenceName = "RS2_UNAAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAAUDIT",
        indexes = {
                @Index(name = "RS2_UNAAUDIT_PK_I", columnList = "UAAU_CODIGO")
        }
)
public class JUnidadAdministrativaAuditoria {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-audit-sequence")
    @Column(name = "UAAU_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Unidad administrativa
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAAU_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Fecha de modificacion
     */
    @Column(name = "UAAU_FECMOD", nullable = false)
    private Date fechaModificacion;

    /**
     * Lista de modificaciones
     */
    @Lob
    @Column(name = "UAAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    /**
     * Usuario modificado
     */
    @Column(name = "UAAU_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    /**
     * perfil del usuario
     */
    @Column(name = "UAAU_USUPRF", nullable = false)
    private String usuarioPerfil;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return  unidad administrativa
     */
    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param uaauCodua  uaau codua
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa uaauCodua) {
        this.unidadAdministrativa = uaauCodua;
    }

    /**
     * Obtiene fecha modificacion.
     *
     * @return  fecha modificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Establece fecha modificacion.
     *
     * @param uaauFecmod  uaau fecmod
     */
    public void setFechaModificacion(Date uaauFecmod) {
        this.fechaModificacion = uaauFecmod;
    }

    /**
     * Obtiene lista modificaciones.
     *
     * @return  lista modificaciones
     */
    public String getListaModificaciones() {
        return listaModificaciones;
    }

    /**
     * Establece lista modificaciones.
     *
     * @param uaauLstmod  uaau lstmod
     */
    public void setListaModificaciones(String uaauLstmod) {
        this.listaModificaciones = uaauLstmod;
    }

    /**
     * Obtiene usuario modificacion.
     *
     * @return  usuario modificacion
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * Establece usuario modificacion.
     *
     * @param uaauUsumod  uaau usumod
     */
    public void setUsuarioModificacion(String uaauUsumod) {
        this.usuarioModificacion = uaauUsumod;
    }

    public String getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(String usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }
}