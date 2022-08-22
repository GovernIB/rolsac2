package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

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
public class JProcedimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-sequence")
    @Column(name = "PROC_CODIGO", nullable = false)
    private Long codigo;
    public static final String FIND_BY_ID = "Procedimiento.FIND_BY_ID";

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

}