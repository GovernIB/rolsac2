package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JFicha;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-trad-sequence", sequenceName = "RS2_TRAFIC_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAFIC",
        indexes = {
                @Index(name = "RS2_TRAFIC_PK_I", columnList = "TRFC_CODIGO")
        })
public class JFichaTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-trad-sequence")
    @Column(name = "TRFC_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRFC_CODFCHA", nullable = false)
    private JFicha ficha;

    @Column(name = "TRFC_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRFC_TITULO")
    private String titulo;

    @Lob
    @Column(name = "TRFC_DESABR")
    private String descripcionAbreviada;

    @Lob
    @Column(name = "TRFC_DESEXT")
    private String descripcionExtendida;

    @Column(name = "TRFC_URLEXT", length = 512)
    private String urlExterna;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JFicha getFicha() {
        return ficha;
    }

    public void setFicha(JFicha trfcCodfcha) {
        this.ficha = trfcCodfcha;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trfcIdioma) {
        this.idioma = trfcIdioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String trfcTitulo) {
        this.titulo = trfcTitulo;
    }

    public String getDescripcionAbreviada() {
        return descripcionAbreviada;
    }

    public void setDescripcionAbreviada(String trfcDesabr) {
        this.descripcionAbreviada = trfcDesabr;
    }

    public String getDescripcionExtendida() {
        return descripcionExtendida;
    }

    public void setDescripcionExtendida(String trfcDesext) {
        this.descripcionExtendida = trfcDesext;
    }

    public String getUrlExterna() {
        return urlExterna;
    }

    public void setUrlExterna(String trfcUrlext) {
        this.urlExterna = trfcUrlext;
    }

}