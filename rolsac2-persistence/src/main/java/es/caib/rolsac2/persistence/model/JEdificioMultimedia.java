package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J edificio multimedia.
 */
@Entity
@SequenceGenerator(name = "edificio-multimedia-sequence", sequenceName = "RS2_EDIMED_SEQ", allocationSize = 1)
@Table(name = "RS2_EDIMED",
        indexes = {
                @Index(name = "RS2_EDIMED_PK_I", columnList = "EDME_CODIGO")
        })
public class JEdificioMultimedia {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edificio-sequence")
    @Column(name = "EDME_CODIGO", nullable = false)
    private Integer codigo;

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
    private JTipoMediaEdificio tipoMedia;

    /**
     * Fichero *
     */
    @Column(name = "EDME_CODFIC")
    private Integer fichero;

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
     * Obtiene edificio.
     *
     * @return  edificio
     */
    public JEdificio getEdificio() {
        return edificio;
    }

    /**
     * Establece edificio.
     *
     * @param edmeCodedi  edme codedi
     */
    public void setEdificio(JEdificio edmeCodedi) {
        this.edificio = edmeCodedi;
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
     * @param edmeCodfic  edme codfic
     */
    public void setFichero(Integer edmeCodfic) {
        this.fichero = edmeCodfic;
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