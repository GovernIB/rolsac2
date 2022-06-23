package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JFicheroTipoMultimediaUA;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "fichero-tipo-ua-trad-sequence", sequenceName = "RS2_TRATPMU_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPMU",
        indexes = {
                @Index(name = "RS2_TRATPMU_PK_I", columnList = "TRTM_CODIGO")
        })
public class JFicheroTipoMultimediaUATraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-ua-trad-sequence")
    @Column(name = "TRTM_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTM_CODTPMU", nullable = false)
    private JFicheroTipoMultimediaUA ficheroTipoMultimediaUA;

    @Column(name = "TRTM_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTM_DESCRI")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JFicheroTipoMultimediaUA getFicheroTipoMultimediaUA() {
        return ficheroTipoMultimediaUA;
    }

    public void setFicheroTipoMultimediaUA(JFicheroTipoMultimediaUA trtmCodtpmu) {
        this.ficheroTipoMultimediaUA = trtmCodtpmu;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtmIdioma) {
        this.idioma = trtmIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trtmDescri) {
        this.descripcion = trtmDescri;
    }

}