package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JNormativa;

import javax.persistence.*;

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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRNO_CODTPNO", nullable = false)
    private JNormativa normativa;

    @Column(name = "TRNO_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRNO_TITUL", length = 2000)
    private String titulo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}