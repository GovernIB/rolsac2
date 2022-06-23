package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JFicheroTipoMultimediaFicha;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "fichero-tipo-fic-trad-sequence", sequenceName = "RS2_TRATPMF_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPMF",
        indexes = {
                @Index(name = "RS2_TRATPMF_PK_I", columnList = "TRTH_CODIGO")
        })
public class JFicheroTipoMultimediaFichaTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-fic-trad-sequence")
    @Column(name = "TRTH_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTH_CODTPMF", nullable = false)
    private JFicheroTipoMultimediaFicha ficheroTipoMultimediaFicha;

    @Column(name = "TRTH_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTH_DESCRI")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JFicheroTipoMultimediaFicha getFicheroTipoMultimediaFicha() {
        return ficheroTipoMultimediaFicha;
    }

    public void setFicheroTipoMultimediaFicha(JFicheroTipoMultimediaFicha trthCodtpmf) {
        this.ficheroTipoMultimediaFicha = trthCodtpmf;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trthIdioma) {
        this.idioma = trthIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trthDescri) {
        this.descripcion = trthDescri;
    }

}