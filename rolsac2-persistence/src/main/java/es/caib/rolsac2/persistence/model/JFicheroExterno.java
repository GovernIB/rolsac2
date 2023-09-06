package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Date;

/**
 * La clase J fichero externo.
 */
@Entity
@SequenceGenerator(name = "fichero-ext-sequence", sequenceName = "RS2_FICEXT_SEQ", allocationSize = 1)
@Table(name = "RS2_FICEXT", indexes = {@Index(name = "RS2_FICEXT_PK_I", columnList = "FIE_REFDOC")})
public class JFicheroExterno {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-ext-sequence")
    @Column(name = "FIE_CODIGO", unique = true, nullable = false, precision = 10, scale = 0)
    private Long codigo;

    /**
     * Referencia
     **/
    @Column(name = "FIE_REFDOC", unique = true, nullable = false, length = 1000)
    private String referencia;

    /**
     * Fecha
     **/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FIE_FECHA", nullable = false)
    private Date fecha;

    /**
     * Borrar
     **/
    @Column(name = "FIE_BORRAR", nullable = false, precision = 1, scale = 0)
    private boolean borrar;

    /**
     * Temporal
     **/
    @Column(name = "FIE_TEMP", nullable = false, precision = 1, scale = 0)
    private boolean temporal;

    /**
     * Nombre del fichero
     **/
    @Column(name = "FIE_FILENAME", unique = true, nullable = false, length = 1000)
    private String filename;

    /**
     * Tipo
     **/
    @Column(name = "FIE_FICTIP", unique = true, nullable = false, length = 50)
    private String tipo;

    /**
     * Id del elemento
     **/
    @Column(name = "FIE_FICELE", unique = true, nullable = false, precision = 18, scale = 0)
    private Long idElemento;

    /**
     * Obtiene referencia.
     *
     * @return referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Establece referencia.
     *
     * @param id id
     */
    public void setReferencia(String id) {
        this.referencia = id;
    }

    /**
     * Obtiene fecha.
     *
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece fecha.
     *
     * @param fieReffec fie reffec
     */
    public void setFecha(Date fieReffec) {
        this.fecha = fieReffec;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Is borrar boolean.
     *
     * @return boolean
     */
    public boolean isBorrar() {
        return borrar;
    }

    /**
     * Establece borrar.
     *
     * @param borrar borrar
     */
    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    /**
     * Is temporal boolean.
     *
     * @return boolean
     */
    public boolean isTemporal() {
        return temporal;
    }

    /**
     * Establece temporal.
     *
     * @param temporal temporal
     */
    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    /**
     * Obtiene filename.
     *
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Establece filename.
     *
     * @param filename filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene id elemento.
     *
     * @return id elemento
     */
    public Long getIdElemento() {
        return idElemento;
    }

    /**
     * Establece id elemento.
     *
     * @param idElemento id elemento
     */
    public void setIdElemento(Long idElemento) {
        this.idElemento = idElemento;
    }
 
}