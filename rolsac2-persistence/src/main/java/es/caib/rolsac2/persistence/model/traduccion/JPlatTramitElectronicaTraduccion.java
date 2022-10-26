package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.service.model.Constantes;
import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;

import javax.persistence.*;
import java.util.*;

@Entity
@SequenceGenerator(name = "plat-tramit-electronica-trad-sequence", sequenceName = "RS2_TRAPTTR_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAPLATRE",
        indexes = {
                @Index(name = "RS2_TRAPLATRE_PK_I", columnList = "TRPT_CODIGO")
        }
)
public class JPlatTramitElectronicaTraduccion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plat-tramit-electronica-trad-sequence")
    @Column(name = "TRPT_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRPT_CODPTTR", nullable = false)
    private JPlatTramitElectronica platTramitElectronica;

    @Column(name = "TRPT_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRPT_URL", nullable = false, length=50)
    private String urlAcceso;

    @Column(name = "TRPT_DESCR", nullable = false)
    private String descripcion;


    public static List<JPlatTramitElectronicaTraduccion> createInstance(List<String> idiomas) {
        List<JPlatTramitElectronicaTraduccion> traducciones = new ArrayList<>();

        for (String idioma : idiomas) {
            JPlatTramitElectronicaTraduccion trad = new JPlatTramitElectronicaTraduccion();
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

    public JPlatTramitElectronica getPlatTramitElectronica() {
        return platTramitElectronica;
    }

    public void setPlatTramitElectronica(JPlatTramitElectronica platTramitElectronica) {
        this.platTramitElectronica = platTramitElectronica;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String platIdioma) {
        this.idioma = platIdioma;
    }

    public String getUrlAcceso() { return this.urlAcceso; }

    public void setUrlAcceso(String urlAcceso)  {this.urlAcceso = urlAcceso;}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JPlatTramitElectronicaTraduccion that = (JPlatTramitElectronicaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JPlatTramitElectronicaTraduccion{" +
                "codigo=" + codigo +
                ", platTramitElectronica=" + platTramitElectronica +
                ", idioma='" + idioma + '\'' +
                ", urlAcceso='" + urlAcceso + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}