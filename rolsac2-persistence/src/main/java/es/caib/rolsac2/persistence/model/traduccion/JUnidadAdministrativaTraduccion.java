package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ua-trad-sequence", sequenceName = "RS2_TRAUNAD_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAUNAD",
        indexes = {
                @Index(name = "RS2_TRAUNAD_PK_I", columnList = "TRUA_CODIGO")
        }
)
public class JUnidadAdministrativaTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-trad-sequence")
    @Column(name = "TRUA_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRUA_CODUNAD", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @Column(name = "TRUA_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRUA_NOMBRE")
    private String nombre;

    @Column(name = "TRUA_PRESEN", length = 4000)
    private String presentacion;

    @Column(name = "TRUA_URLWEB")
    private String url;

    @Lob
    @Column(name = "TRUA_RSPCV")
    private String responsableCV;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa truaCodunad) {
        this.unidadAdministrativa = truaCodunad;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String truaIdioma) {
        this.idioma = truaIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String truaNombre) {
        this.nombre = truaNombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String truaPresen) {
        this.presentacion = truaPresen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String truaUrlweb) {
        this.url = truaUrlweb;
    }

    public String getResponsableCV() {
        return responsableCV;
    }

    public void setResponsableCV(String truaRspcv) {
        this.responsableCV = truaRspcv;
    }

}