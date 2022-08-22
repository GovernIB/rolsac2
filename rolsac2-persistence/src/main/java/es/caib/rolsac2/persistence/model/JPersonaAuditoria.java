package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "persona-audit-sequence", sequenceName = "RS2_PERAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_PERAUDIT",
        indexes = {
                @Index(name = "RS2_PERAUDIT_PK_I", columnList = "PERAU_CODIGO")
        })
public class JPersonaAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona-audit-sequence")
    @Column(name = "PERAU_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PERAU_CODPER", nullable = false)
    private JPersonal persona;

    @Column(name = "PERAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    @Lob
    @Column(name = "PERAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    @Column(name = "PERAU_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JPersonal getPersona() {
        return persona;
    }

    public void setPersona(JPersonal perauCodper) {
        this.persona = perauCodper;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate perauFecmod) {
        this.fechaModificacion = perauFecmod;
    }

    public String getListaModificaciones() {
        return listaModificaciones;
    }

    public void setListaModificaciones(String perauLstmod) {
        this.listaModificaciones = perauLstmod;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String perauUsumod) {
        this.usuarioModificacion = perauUsumod;
    }

}