package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Long codigo;

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

    public static List<JUnidadAdministrativaTraduccion> createInstance() {
        List<JUnidadAdministrativaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JUnidadAdministrativaTraduccion trad = new JUnidadAdministrativaTraduccion();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUnidadAdministrativaTraduccion that = (JUnidadAdministrativaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JUnidadAdministrativaTraduccion{" +
                "id=" + codigo +
                ", unidadAdministrativa=" + unidadAdministrativa +
                ", idioma='" + idioma + '\'' +
                ", nombre='" + nombre + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", url='" + url + '\'' +
                ", responsableCV='" + responsableCV + '\'' +
                '}';
    }
}