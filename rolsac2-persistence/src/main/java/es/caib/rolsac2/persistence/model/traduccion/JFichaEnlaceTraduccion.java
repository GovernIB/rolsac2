package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JFichaEnlace;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-enlace-trad-sequence", sequenceName = "RS2_TRAFCEN_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAFCEN",
        indexes = {
                @Index(name = "RS2_TRAFCEN_PK_I", columnList = "TRFC_CODIGO")
        })
public class JFichaEnlaceTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-enlace-trad-sequence")
    @Column(name = "TRFC_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRFC_CODFCEN", nullable = false)
    private JFichaEnlace fichaEnlace;

    @Column(name = "TRFC_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRFC_TITULO")
    private String titulo;

    @Column(name = "TRFC_URL")
    private String url;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JFichaEnlace getFichaEnlace() {
        return fichaEnlace;
    }

    public void setFichaEnlace(JFichaEnlace trfcCodfcen) {
        this.fichaEnlace = trfcCodfcen;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String trfcUrl) {
        this.url = trfcUrl;
    }

}