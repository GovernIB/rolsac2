package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JNormativa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "normativa-trad-sequence", sequenceName = "RS2_TRANORM_SEQ", allocationSize = 1)
@Table(name = "RS2_TRANORM", indexes = {@Index(name = "RS2_TRANORM_PK_I", columnList = "TRNO_CODIGO")})
public class JNormativaTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "normativa-trad-sequence")
    @Column(name = "TRNO_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRNO_CODTPNO", nullable = false)
    private JNormativa normativa;

    @Column(name = "TRNO_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRNO_TITUL", length = 2000)
    private String titulo;

    @Column(name = "TRNO_URL", length = 500)
    private String urlBoletin;

    @Column(name = "TRNO_RESPNOM")
    private String nombreResponsable;

    /**
     * Crear una instancia de traducción de normativa
     *
     * @param idiomas
     * @return
     */
    public static List<JNormativaTraduccion> createInstance(List<String> idiomas) {
        List<JNormativaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JNormativaTraduccion trad = new JNormativaTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    /**
     * Obtener el código
     *
     * @return
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Setear el código
     *
     * @param id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtener la normativa
     *
     * @return
     */
    public JNormativa getNormativa() {
        return normativa;
    }

    /**
     * Setear la normativa
     *
     * @param trnoCodtpno
     */
    public void setNormativa(JNormativa trnoCodtpno) {
        this.normativa = trnoCodtpno;
    }

    /**
     * Obtener el idioma de la normativa
     *
     * @return
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Setear el idioma de la normativa
     *
     * @param trnoIdioma
     */
    public void setIdioma(String trnoIdioma) {
        this.idioma = trnoIdioma;
    }

    /**
     * Obtener el título de la normativa
     *
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Setear el título de la normativa
     *
     * @param trnoTitul
     */
    public void setTitulo(String trnoTitul) {
        this.titulo = trnoTitul;
    }

    /**
     * Obtener la url del boletín
     *
     * @return
     */
    public String getUrlBoletin() {
        return urlBoletin;
    }

    /**
     * Setear la url del boletín
     *
     * @param urlBoletin
     */
    public void setUrlBoletin(String urlBoletin) {
        this.urlBoletin = urlBoletin;
    }

    /**
     * Obtener el nombre del responsable de la normativa
     *
     * @return nombre del responsable de la normativa
     */
    public String getNombreResponsable() {
        return nombreResponsable;
    }

    /**
     * Setear el nombre del responsable de la normativa
     *
     * @param nombreResponsable
     */
    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JNormativaTraduccion that = (JNormativaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JNormativaTraduccion{" + "codigo=" + codigo + ", idioma='" + idioma + '\'' + ", titulo='" + titulo + '\'' + ", urlBoletin='" + urlBoletin + '\'' + '}';
    }
}