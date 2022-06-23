package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "edificio-multimedia-sequence", sequenceName = "RS2_EDIMED_SEQ", allocationSize = 1)
@Table(name = "RS2_EDIMED",
        indexes = {
                @Index(name = "RS2_EDIMED_PK_I", columnList = "EDME_CODIGO")
        })
public class JEdificioMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edificio-sequence")
    @Column(name = "EDME_CODIGO", nullable = false)
    private Integer id;

    /**
     * Edificio
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EDME_CODEDI", nullable = false)
    private JEdificio edificio;

    /**
     * Tipo media
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EDME_TIPOMED", nullable = false)
    private JFicheroTipoMultimediaEdificio tipoMedia;

    /**
     * Fichero *
     */
    @Column(name = "EDME_CODFIC")
    private Integer fichero;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEdificio getEdificio() {
        return edificio;
    }

    public void setEdificio(JEdificio edmeCodedi) {
        this.edificio = edmeCodedi;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer edmeCodfic) {
        this.fichero = edmeCodfic;
    }

    public JFicheroTipoMultimediaEdificio getTipoMedia() {
        return tipoMedia;
    }

    public void setTipoMedia(JFicheroTipoMultimediaEdificio tipoMedia) {
        this.tipoMedia = tipoMedia;
    }
}