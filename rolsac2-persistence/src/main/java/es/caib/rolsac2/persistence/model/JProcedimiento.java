package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * LA clase J procedimiento.
 */
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
    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Procedimiento.FIND_BY_ID";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-sequence")
    @Column(name = "PROC_CODIGO", nullable = false)
    private Long codigo;

    /**
     * PROCEDIMIENTO (P) / SERVICIO (S)
     **/
    @Column(name = "PROC_TIPO", nullable = false, length = 1)
    private String tipo;

    /**
     * Codigo SIA
     */
    @Column(name = "PROC_SIACOD")
    private Integer codigoSIA;

    /**
     * Estiado SIA
     */
    @Column(name = "PROC_SIAEST")
    private Boolean estadoSIA;

    /**
     * Fecha SIA
     */
    @Column(name = "PROC_SIAFC")
    private LocalDate siaFecha;

    /**
     * Codigo Dir3 SIA
     */
    @Column(name = "PROC_SIADIR3", length = 20)
    private String codigoDir3SIA;

    /**
     * Mensajes
     */
    @Column(name = "PROC_MENSA")
    private String mensajes;

    /**
     * Workflow del procedimiento 
     */
    @OneToMany(mappedBy = "procedimiento", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<JProcedimientoWorkflow> procedimientoWF;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param procTipo  proc tipo
     */
    public void setTipo(String procTipo) {
        this.tipo = procTipo;
    }

    /**
     * Obtiene codigo sia.
     *
     * @return  codigo sia
     */
    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * Establece codigo sia.
     *
     * @param procSiacod  proc siacod
     */
    public void setCodigoSIA(Integer procSiacod) {
        this.codigoSIA = procSiacod;
    }

    /**
     * Obtiene estado sia.
     *
     * @return  estado sia
     */
    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * Establece estado sia.
     *
     * @param procSiaest  proc siaest
     */
    public void setEstadoSIA(Boolean procSiaest) {
        this.estadoSIA = procSiaest;
    }

    /**
     * Obtiene sia fecha.
     *
     * @return  sia fecha
     */
    public LocalDate getSiaFecha() {
        return siaFecha;
    }

    /**
     * Establece sia fecha.
     *
     * @param procSiafc  proc siafc
     */
    public void setSiaFecha(LocalDate procSiafc) {
        this.siaFecha = procSiafc;
    }

    /**
     * Obtiene codigo dir 3 sia.
     *
     * @return  codigo dir 3 sia
     */
    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    /**
     * Establece codigo dir 3 sia.
     *
     * @param procSiadir3  proc siadir 3
     */
    public void setCodigoDir3SIA(String procSiadir3) {
        this.codigoDir3SIA = procSiadir3;
    }

    /**
     * Obtiene mensajes.
     *
     * @return  mensajes
     */
    public String getMensajes() {
        return mensajes;
    }

    /**
     * Establece mensajes.
     *
     * @param mensajes  mensajes
     */
    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * Obtiene procedimiento wf.
     *
     * @return  procedimiento wf
     */
    public List<JProcedimientoWorkflow> getProcedimientoWF() {
        return procedimientoWF;
    }

    /**
     * Establece procedimiento wf.
     *
     * @param procedimientoWF  procedimiento wf
     */
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