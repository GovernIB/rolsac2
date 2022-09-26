package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "fichero-ext-sequence", sequenceName = "RS2_FICEXT_SEQ", allocationSize = 1)
@Table(name = "RS2_FICEXT",
        indexes = {
                @Index(name = "RS2_FICEXT_PK_I", columnList = "FIE_REFDOC")
        })
public class JFicheroExterno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FICEXT_SEQ")
    @SequenceGenerator(allocationSize = 1, name = "STG_FICEXT_SEQ", sequenceName = "STG_FICEXT_SEQ")
    @Column(name = "FIE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
    private Long codigo;
    @Column(name = "FIE_REFDOC", unique = true, nullable = false, length = 1000)
    private String referencia;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FIE_FECHA", nullable = false)
    private Date fecha;
    @Column(name = "FIE_BORRAR", nullable = false, precision = 1, scale = 0)
    private boolean borrar;
    @Column(name = "FIE_TEMP", nullable = false, precision = 1, scale = 0)
    private boolean temporal;
    @Column(name = "FIE_FILENAME", unique = true, nullable = false, length = 1000)
    private String filename;
    @Column(name = "FIE_FICTIP", unique = true, nullable = false, length = 50)
    private String tipo;
    @Column(name = "FIE_FICELE", unique = true, nullable = false, precision = 18, scale = 0)
    private Long idElemento;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String id) {
        this.referencia = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fieReffec) {
        this.fecha = fieReffec;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public boolean isBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    public boolean isTemporal() {
        return temporal;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Long idElemento) {
        this.idElemento = idElemento;
    }
}