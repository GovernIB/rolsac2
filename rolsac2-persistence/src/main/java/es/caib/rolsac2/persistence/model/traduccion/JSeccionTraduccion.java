package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JSeccion;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "seccion-trad-sequence", sequenceName = "RS2_TRASECC_SEQ", allocationSize = 1)
@Table(name = "RS2_TRASECC",
        indexes = {
                @Index(name = "RS2_TRASECC_PK_I", columnList = "TRSE_CODIGO")
        }
)
public class JSeccionTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seccion-trad-sequence")
    @Column(name = "TRSE_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRSE_CODSECC", nullable = false)
    private JSeccion seccion;

    @Column(name = "TRSE_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRSE_NOMBRE")
    private String nombre;

    @Column(name = "TRSE_DESCRI", length = 4000)
    private String descripcion;

    public static List<JSeccionTraduccion> createInstance() {
        List<JSeccionTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JSeccionTraduccion trad = new JSeccionTraduccion();
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

    public JSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(JSeccion trseCodsecc) {
        this.seccion = trseCodsecc;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trseIdioma) {
        this.idioma = trseIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String trseNombre) {
        this.nombre = trseNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trseDescri) {
        this.descripcion = trseDescri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSeccionTraduccion that = (JSeccionTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JSeccionTraduccion{" +
                "codigo=" + codigo +
                ", idioma='" + idioma + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}