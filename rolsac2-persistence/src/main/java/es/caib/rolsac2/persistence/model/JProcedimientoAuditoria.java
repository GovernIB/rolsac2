package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "procedimiento-audit-sequence", sequenceName = "RS2_PRAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_PRAUDIT",
        indexes = {
                @Index(name = "RS2_PRAUDIT_PK_I", columnList = "PRAU_CODIGO")
        }
)
public class JProcedimientoAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-audit-sequence")
    @Column(name = "PRAU_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRAU_CODPROC", nullable = false)
    private JProcedimiento procedimiento;

    @Column(name = "PRAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    @Lob
    @Column(name = "PRAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    @Column(name = "PRAU_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate prauFecmod) {
        this.fechaModificacion = prauFecmod;
    }

    public String getListaModificaciones() {
        return listaModificaciones;
    }

    public void setListaModificaciones(String prauLstmod) {
        this.listaModificaciones = prauLstmod;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String prauUsumod) {
        this.usuarioModificacion = prauUsumod;
    }

    public JProcedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }
}