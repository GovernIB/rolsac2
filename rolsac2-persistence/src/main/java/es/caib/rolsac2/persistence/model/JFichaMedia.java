package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J ficha media.
 */
@Entity
@SequenceGenerator(name = "ficha-media-sequence", sequenceName = "RS2_FCHMED_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHMED",
        indexes = {
                @Index(name = "RS2_FCHMED_PK_I", columnList = "FCME_CODIGO")
        })
public class JFichaMedia {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-media-sequence")
    @Column(name = "FCME_CODIGO", nullable = false)
    private Integer codigo;

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
    private JTipoMediaEdificio tipoMedia;


    /**
     * Ficha
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCME_CODFCH", nullable = false)
    private JFicha ficha;

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
     * Obtiene fichero.
     *
     * @return  fichero
     */
    public Integer getFichero() {
        return fichero;
    }

    /**
     * Establece fichero.
     *
     * @param trtcFichero  trtc fichero
     */
    public void setFichero(Integer trtcFichero) {
        this.fichero = trtcFichero;
    }

    /**
     * Obtiene ficha.
     *
     * @return  ficha
     */
    public JFicha getFicha() {
        return ficha;
    }

    /**
     * Establece ficha.
     *
     * @param ficha  ficha
     */
    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }

    /**
     * Obtiene tipo media.
     *
     * @return  tipo media
     */
    public JTipoMediaEdificio getTipoMedia() {
        return tipoMedia;
    }

    /**
     * Establece tipo media.
     *
     * @param tipoMedia  tipo media
     */
    public void setTipoMedia(JTipoMediaEdificio tipoMedia) {
        this.tipoMedia = tipoMedia;
    }
}