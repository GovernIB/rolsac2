package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JFicheroTipoMultimediaEdificio;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "fichero-tipo-edif-trad-sequence", sequenceName = "RS2_TRATPME_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPME",
        indexes = {
                @Index(name = "RS2_TRATPME_PK_I", columnList = "TRTD_CODIGO")
        })
public class JFicheroTipoMultimediaEdificioTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-edif-trad-sequence")
    @Column(name = "TRTD_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTD_CODTPME", nullable = false)
    private JFicheroTipoMultimediaEdificio ficheroTipoMultimediaEdificio;

    @Column(name = "TRTD_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTD_DESCRI")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JFicheroTipoMultimediaEdificio getFicheroTipoMultimediaEdificio() {
        return ficheroTipoMultimediaEdificio;
    }

    public void setFicheroTipoMultimediaEdificio(JFicheroTipoMultimediaEdificio trtdCodtpme) {
        this.ficheroTipoMultimediaEdificio = trtdCodtpme;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtdIdioma) {
        this.idioma = trtdIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trtdDescri) {
        this.descripcion = trtdDescri;
    }

}