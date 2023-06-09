package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "normativa-trad-sequence", sequenceName = "RS2_TRANORM_SEQ", allocationSize = 1)
@Table(name = "RS2_TRANORM",
        indexes = {
                @Index(name = "RS2_TRANORM_PK_I", columnList = "TRNO_CODIGO")
        })
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

    public static List<JNormativaTraduccion> createInstance(List<String> idiomas) {
        List<JNormativaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JNormativaTraduccion trad = new JNormativaTraduccion();
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

    public JNormativa getNormativa() {
        return normativa;
    }

    public void setNormativa(JNormativa trnoCodtpno) {
        this.normativa = trnoCodtpno;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trnoIdioma) {
        this.idioma = trnoIdioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String trnoTitul) {
        this.titulo = trnoTitul;
    }

    public String getUrlBoletin() { return urlBoletin; }

    public void setUrlBoletin(String urlBoletin) { this.urlBoletin = urlBoletin; }

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
        return "JNormativaTraduccion{" +
                "codigo=" + codigo +
                ", normativa=" + normativa +
                ", idioma='" + idioma + '\'' +
                ", titulo='" + titulo + '\'' +
                ", urlBoletin='" + urlBoletin + '\'' +
                '}';
    }
}