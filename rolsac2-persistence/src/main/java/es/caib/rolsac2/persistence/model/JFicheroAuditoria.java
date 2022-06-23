package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "fichero-audit-sequence", sequenceName = "RS2_FCHAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHAUDIT",
        indexes = {
                @Index(name = "RS2_FCHAUDIT_PK_I", columnList = "FCAU_CODIGO")
        })
public class JFicheroAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-audit-sequence")
    @Column(name = "FCAU_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RS2_CODFCH", nullable = false)
    private JFicha ficha;

    /**
     * Fecha modificacion
     **/
    @Column(name = "FCAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    /**
     * Lista de modificaciones
     **/
    @Lob
    @Column(name = "FCAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    @Column(name = "FCAU_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fcauFecmod) {
        this.fechaModificacion = fcauFecmod;
    }

    public String getListaModificaciones() {
        return listaModificaciones;
    }

    public void setListaModificaciones(String fcauLstmod) {
        this.listaModificaciones = fcauLstmod;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String fcauUsumod) {
        this.usuarioModificacion = fcauUsumod;
    }

    public JFicha getFicha() {
        return ficha;
    }

    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}