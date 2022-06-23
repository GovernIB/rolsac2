package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "ua-audit-sequence", sequenceName = "RS2_UNAAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAAUDIT",
        indexes = {
                @Index(name = "RS2_UNAAUDIT_PK_I", columnList = "UAAU_CODIGO")
        }
)
public class JUnidadAdministrativaAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-audit-sequence")
    @Column(name = "UAAU_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAAU_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @Column(name = "UAAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    @Lob
    @Column(name = "UAAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    @Column(name = "UAAU_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa uaauCodua) {
        this.unidadAdministrativa = uaauCodua;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate uaauFecmod) {
        this.fechaModificacion = uaauFecmod;
    }

    public String getListaModificaciones() {
        return listaModificaciones;
    }

    public void setListaModificaciones(String uaauLstmod) {
        this.listaModificaciones = uaauLstmod;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String uaauUsumod) {
        this.usuarioModificacion = uaauUsumod;
    }

}