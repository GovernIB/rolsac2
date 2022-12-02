package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "procedimiento-sequence", sequenceName = "RS2_PROC_SEQ", allocationSize = 1)
@Table(name = "RS2_PROC",
        indexes = {
                @Index(name = "RS2_PROC_PK_I", columnList = "PROC_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JProcedimiento.FIND_BY_ID,
                query = "select p from JProcedimiento p where p.codigo = :id")
})
public class JProcedimiento extends BaseEntity {
    public static final String FIND_BY_ID = "Procedimiento.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-sequence")
    @Column(name = "PROC_CODIGO", nullable = false)
    private Long codigo;

    /**
     * PROCEDIMIENTO (P) / SERVICIO (S)
     **/
    @Column(name = "PROC_TIPO", nullable = false, length = 1)
    private String tipo;

    @Column(name = "PROC_SIACOD")
    private Integer codigoSIA;

    @Column(name = "PROC_SIAEST")
    private Boolean estadoSIA;

    @Column(name = "PROC_SIAFC")
    private LocalDate siaFecha;

    @Column(name = "PROC_SIADIR3", length = 20)
    private String codigoDir3SIA;

    @Column(name = "PROC_MENSA")
    private String mensajes;

    @OneToMany(mappedBy = "procedimiento", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<JProcedimientoWorkflow> procedimientoWF;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String procTipo) {
        this.tipo = procTipo;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer procSiacod) {
        this.codigoSIA = procSiacod;
    }

    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(Boolean procSiaest) {
        this.estadoSIA = procSiaest;
    }

    public LocalDate getSiaFecha() {
        return siaFecha;
    }

    public void setSiaFecha(LocalDate procSiafc) {
        this.siaFecha = procSiafc;
    }

    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    public void setCodigoDir3SIA(String procSiadir3) {
        this.codigoDir3SIA = procSiadir3;
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public List<JProcedimientoWorkflow> getProcedimientoWF() {
        return procedimientoWF;
    }

    public void setProcedimientoWF(List<JProcedimientoWorkflow> procedimientoWF) {
        this.procedimientoWF = procedimientoWF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JProcedimiento that = (JProcedimiento) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JProcedimiento{" +
                "codigo=" + codigo +
                ", tipo='" + tipo + '\'' +
                ", codigoSIA=" + codigoSIA +
                ", estadoSIA=" + estadoSIA +
                ", siaFecha=" + siaFecha +
                ", codigoDir3SIA='" + codigoDir3SIA + '\'' +
                '}';
    }
}