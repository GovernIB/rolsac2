package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La clase J documento normativa traduccion.
 */
@Entity
@SequenceGenerator(name = "documento-normativa-traduccion-sequence", sequenceName = "RS2_TRADONO_SEQ", allocationSize = 1)
@Table(name = "RS2_TRADONO", indexes = {@Index(name = "RS2_TRADONO_UK", columnList = "TRDN_CODDONO, TRDN_IDIOMA")})
@NamedQueries({@NamedQuery(name = JDocumentoNormativaTraduccion.FIND_BY_ID, query = "select p from JDocumentoNormativaTraduccion p where p.codigo = :codigo")})
public class JDocumentoNormativaTraduccion extends BaseEntity {

    /**
     * Cosulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "documentoNormativaTraduccion.FIND_BY_ID";


    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento-normativa-traduccion-sequence")
    @Column(name = "TRDN_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Documento de la normativa
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRDN_CODDONO", nullable = false)
    private JDocumentoNormativa documentoNormativa;

    /**
     * Idioma
     **/
    @Column(name = "TRDN_IDIOMA", nullable = false, length = 2)
    private String idioma;

    /**
     * Titulo
     **/
    @Column(name = "TRDN_TITULO", length = 512)
    private String titulo;

    /**
     * Url
     **/
    @Column(name = "TRDN_URL", length = 512)
    private String url;

    /**
     * Descripcion
     **/
    @Column(name = "TRDN_DESCR", length = 4000)
    private String descripcion;

    /**
     * Documento
     **/
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "TRDN_FICHER", nullable = false)
    private JFicheroExterno documento;

    /**
     * Documento
     **/
    @Column(name = "TRDN_FICROL1")
    private Long ficheroRolsac1;


    /**
     * Crea una instancia de list.
     *
     * @param idiomas idiomas
     * @return list
     */
    public static List<JDocumentoNormativaTraduccion> createInstance(List<String> idiomas) {
        List<JDocumentoNormativaTraduccion> traducciones = new ArrayList<>();

        for (String idioma : idiomas) {
            JDocumentoNormativaTraduccion trad = new JDocumentoNormativaTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene documento normativa.
     *
     * @return documento normativa
     */
    public JDocumentoNormativa getDocumentoNormativa() {
        return documentoNormativa;
    }

    /**
     * Establece documento normativa.
     *
     * @param documentoNormativa documento normativa
     */
    public void setDocumentoNormativa(JDocumentoNormativa documentoNormativa) {
        this.documentoNormativa = documentoNormativa;
    }

    /**
     * Obtiene idioma.
     *
     * @return idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece idioma.
     *
     * @param idioma idioma
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene titulo.
     *
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece titulo.
     *
     * @param titulo titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene url.
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene documento.
     *
     * @return documento
     */
    public JFicheroExterno getDocumento() {
        return documento;
    }

    /**
     * Establece documento.
     *
     * @param documento documento
     */
    public void setDocumento(JFicheroExterno documento) {
        this.documento = documento;
    }

    public Long getFicheroRolsac1() {
        return ficheroRolsac1;
    }

    public void setFicheroRolsac1(Long ficheroRolsac1) {
        this.ficheroRolsac1 = ficheroRolsac1;
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
        return "JDocumentoNormativaTraduccion{" + "codigo=" + codigo + ", documentoNormativa=" + documentoNormativa + ", idioma='" + idioma + '\'' + ", titulo='" + titulo + '\'' + ", url='" + url + '\'' + ", descripcion='" + descripcion + '\'' + ", documento=" + documento + '}';
    }
}