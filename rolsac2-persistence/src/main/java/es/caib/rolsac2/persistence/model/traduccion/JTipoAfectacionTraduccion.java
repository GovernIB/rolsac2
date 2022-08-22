package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-afectacion-trad-sequence", sequenceName = "RS2_TRATPAN_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPAN", indexes = {@Index(name = "RS2_TRATPAN_PK", columnList = "TRTA_CODIGO")})
public class JTipoAfectacionTraduccion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-afectacion-trad-sequence")
    @Column(name = "TRTA_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTA_CODTPAN", nullable = false)
    private JTipoAfectacion tipoAfectacion;

    @Column(name = "TRTA_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTA_DESCRI")
    private String descripcion;

    public static List<JTipoAfectacionTraduccion> createInstance() {
        List<JTipoAfectacionTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoAfectacionTraduccion trad = new JTipoAfectacionTraduccion();
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

    public JTipoAfectacion getTipoAfectacion() {
        return tipoAfectacion;
    }

    public void setTipoAfectacion(JTipoAfectacion tipoAfectacion) {
        this.tipoAfectacion = tipoAfectacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtfIdioma) {
        this.idioma = trtfIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trtfDescri) {
        this.descripcion = trtfDescri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JTipoAfectacionTraduccion that = (JTipoAfectacionTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoFormaInicioTraduccion{" + "id=" + codigo + "}";
    }
}
