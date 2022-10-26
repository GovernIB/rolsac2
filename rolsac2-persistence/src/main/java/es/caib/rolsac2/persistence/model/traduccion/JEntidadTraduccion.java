package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RS2_TRAENT",
        indexes = {
                @Index(name = "RS2_TRAENT_PK_I", columnList = "TREN_CODIGO")
        })

@SequenceGenerator(name = "entidad-traduccion-sequence", sequenceName = "RS2_TRAENT_SEQ", allocationSize = 1)
public class JEntidadTraduccion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entidad-traduccion-sequence")
    @Column(name = "TREN_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TREN_CODENT", nullable = false)
    private JEntidad entidad;

    @Column(name = "TREN_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TREN_DESCRI")
    private String descripcion;

    public static List<JEntidadTraduccion> createInstance(List<String> idiomas) {
        List<JEntidadTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JEntidadTraduccion trad = new JEntidadTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad trenCodent) {
        this.entidad = trenCodent;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trenIdioma) {
        this.idioma = trenIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trenDescri) {
        this.descripcion = trenDescri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JEntidadTraduccion that = (JEntidadTraduccion) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JEntidadTraduccion{" +
                "id=" + codigo +
                ", entidad=" + entidad +
                ", idioma='" + idioma + '\'' +
                '}';
    }
}