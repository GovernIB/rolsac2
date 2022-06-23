package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-media-sequence", sequenceName = "RS2_FCHMED_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHMED",
        indexes = {
                @Index(name = "RS2_FCHMED_PK_I", columnList = "FCME_CODIGO")
        })
public class JFichaMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-media-sequence")
    @Column(name = "FCME_CODIGO", nullable = false)
    private Integer id;

    /**
     * Fichero
     **/
    @Column(name = "TRTC_FICHERO")
    private Integer fichero;

    /**
     * Tipo Media
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCME_TIPOMED", nullable = false)
    private JFicheroTipoMultimediaEdificio tipoMedia;


    /**
     * Fichero
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCME_CODFCH", nullable = false)
    private JFicha ficha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer trtcFichero) {
        this.fichero = trtcFichero;
    }

    public JFicha getFicha() {
        return ficha;
    }

    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }

    public JFicheroTipoMultimediaEdificio getTipoMedia() {
        return tipoMedia;
    }

    public void setTipoMedia(JFicheroTipoMultimediaEdificio tipoMedia) {
        this.tipoMedia = tipoMedia;
    }
}