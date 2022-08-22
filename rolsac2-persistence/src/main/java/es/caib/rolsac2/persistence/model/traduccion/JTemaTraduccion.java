package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JTema;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "tema-trad-sequence", sequenceName = "RS2_TRATEMA_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATEMA",
        indexes = {
                @Index(name = "RS2_TRATEMA_PK_I", columnList = "TRTE_CODIGO")
        }
)
public class JTemaTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tema-trad-sequence")
    @Column(name = "TRTE_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTE_CODTEMA", nullable = false)
    private JTema tema;

    @Column(name = "TRTE_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TNTR_DESCR")
    private String descripcion;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JTema getTema() {
        return tema;
    }

    public void setTema(JTema trteCodtema) {
        this.tema = trteCodtema;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trteIdioma) {
        this.idioma = trteIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String tntrDescr) {
        this.descripcion = tntrDescr;
    }

}