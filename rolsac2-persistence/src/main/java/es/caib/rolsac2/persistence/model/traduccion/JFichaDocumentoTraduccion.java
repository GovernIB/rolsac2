package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JFichaDocumento;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-doc-traduccion-sequence", sequenceName = "RS2_TRAFCDO_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAFCDO",
        indexes = {
                @Index(name = "RS2_TRAFCDO_PK_I", columnList = "TRDF_CODIGO")
        })
public class JFichaDocumentoTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-doc-traduccion-sequence")
    @Column(name = "TRDF_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRDF_CODFCDO", nullable = false)
    private JFichaDocumento documento;

    @Column(name = "TRDF_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRDF_TITULO")
    private String titulo;

    @Column(name = "TRDF_DESCRI", length = 4000)
    private String descripcion;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JFichaDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(JFichaDocumento trdfCodfcdo) {
        this.documento = trdfCodfcdo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trdfIdioma) {
        this.idioma = trdfIdioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String trdfTitulo) {
        this.titulo = trdfTitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trdfDescri) {
        this.descripcion = trdfDescri;
    }

}