package es.caib.rolsac2.persistence.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "documento-normativa-traduccion-sequence", sequenceName = "RS2_TRADONO_SEQ", allocationSize = 1)
@Table(name = "RS2_TRADONO", indexes = {
        @Index(name = "RS2_TRADONO_UK", columnList = "TRDN_CODDONO, TRDN_IDIOMA")
})
@NamedQueries({
        @NamedQuery(name = JDocumentoNormativaTraduccion.FIND_BY_ID,
                query = "select p from JDocumentoNormativaTraduccion p where p.codigo = :codigo")
})
public class JDocumentoNormativaTraduccion extends BaseEntity {

    public static final String FIND_BY_ID = "documentoNormativaTraduccion.FIND_BY_ID";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento-normativa-traduccion-sequence")
    @Column(name = "TRDN_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRDN_CODDONO", nullable = false)
    private JDocumentoNormativa documentoNormativa;

    @Column(name = "TRDN_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name ="TRDN_TITULO", length = 512)
    private String titulo;

    @Column(name="TRDN_URL", length = 512)
    private String url;

    @Column(name="TRDN_DESCR", length = 4000)
    private String descripcion;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "TRDN_FICHER", nullable = false)
    private JFicheroExterno documento;



    public static List<JDocumentoNormativaTraduccion> createInstance(List<String> idiomas) {
        List<JDocumentoNormativaTraduccion> traducciones = new ArrayList<>();

        for (String idioma : idiomas) {
            JDocumentoNormativaTraduccion trad = new JDocumentoNormativaTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JDocumentoNormativa getDocumentoNormativa() {
        return documentoNormativa;
    }

    public void setDocumentoNormativa(JDocumentoNormativa documentoNormativa) {
        this.documentoNormativa = documentoNormativa;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public JFicheroExterno getDocumento() {
        return documento;
    }

    public void setDocumento(JFicheroExterno documento) {
        this.documento = documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDocumentoNormativaTraduccion that = (JDocumentoNormativaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JDocumentoNormativaTraduccion{" +
                "codigo=" + codigo +
                ", documentoNormativa=" + documentoNormativa +
                ", idioma='" + idioma + '\'' +
                ", titulo='" + titulo + '\'' +
                ", url='" + url + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", documento=" + documento +
                '}';
    }
}