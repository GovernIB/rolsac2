package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@SequenceGenerator(name = "fichero-ext-sequence", sequenceName = "RS2_FICEXT_SEQ", allocationSize = 1)
@Table(name = "RS2_FICEXT",
        indexes = {
                @Index(name = "RS2_FICEXT_PK_I", columnList = "FIE_REFDOC")
        })
public class JFicheroExterno {

    /**
     * Referencia externa (path fichero)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-ext-sequence")
    @Column(name = "FIE_REFDOC", nullable = false, length = 1000)
    private String codigo;

    /**
     * Fecha referencia (sólo será válida la última, el resto se borrarán)
     **/
    @Column(name = "FIE_REFFEC", nullable = false)
    private Instant fechaReferencia;

    @Column(name = "FIE_CODFIC", nullable = false)
    private Integer fichero;

    /**
     * Indica si esta marcado para borrar (proceso de purgado)
     **/
    @Column(name = "FIE_BORRAR", nullable = false)
    private Boolean marcadoParaBorrar = false;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String id) {
        this.codigo = id;
    }

    public Instant getFechaReferencia() {
        return fechaReferencia;
    }

    public void setFechaReferencia(Instant fieReffec) {
        this.fechaReferencia = fieReffec;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer fieCodfic) {
        this.fichero = fieCodfic;
    }

    public Boolean getMarcadoParaBorrar() {
        return marcadoParaBorrar;
    }

    public void setMarcadoParaBorrar(Boolean fieBorrar) {
        this.marcadoParaBorrar = fieBorrar;
    }

}