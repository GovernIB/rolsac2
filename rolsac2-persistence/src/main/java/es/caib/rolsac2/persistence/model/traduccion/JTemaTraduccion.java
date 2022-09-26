package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTE_CODTEMA", nullable = false)
    private JTema tema;

    @Column(name = "TRTE_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TNTR_DESCR")
    private String descripcion;

    public static List<JTemaTraduccion> createInstance() {
        List<JTemaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTemaTraduccion trad = new JTemaTraduccion();
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTemaTraduccion that = (JTemaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTemaTraduccion{" +
                "codigo=" + codigo +
                ", idioma='" + idioma + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}