package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * La clase J procedimiento auditoria.
 */
@Entity
@SequenceGenerator(name = "procedimiento-audit-sequence", sequenceName = "RS2_PRAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_PRAUDIT",
        indexes = {
                @Index(name = "RS2_PRAUDIT_PK_I", columnList = "PRAU_CODIGO")
        }
)
public class JProcedimientoAuditoria {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-audit-sequence")
    @Column(name = "PRAU_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Procedimiento
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRAU_CODPROC", nullable = false)
    private JProcedimiento procedimiento;

    /**
     * Fecha de modificacion
     */
    @Column(name = "PRAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    /**
     * Lista de modificaciones
     */
    @Lob
    @Column(name = "PRAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    /**
     * Usuario modificado
     */
    @Column(name = "PRAU_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

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
     * Obtiene fecha modificacion.
     *
     * @return  fecha modificacion
     */
    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Establece fecha modificacion.
     *
     * @param prauFecmod  prau fecmod
     */
    public void setFechaModificacion(LocalDate prauFecmod) {
        this.fechaModificacion = prauFecmod;
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
     * @param prauLstmod  prau lstmod
     */
    public void setListaModificaciones(String prauLstmod) {
        this.listaModificaciones = prauLstmod;
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
     * @param prauUsumod  prau usumod
     */
    public void setUsuarioModificacion(String prauUsumod) {
        this.usuarioModificacion = prauUsumod;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return  procedimiento
     */
    public JProcedimiento getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param procedimiento  procedimiento
     */
    public void setProcedimiento(JProcedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }
}