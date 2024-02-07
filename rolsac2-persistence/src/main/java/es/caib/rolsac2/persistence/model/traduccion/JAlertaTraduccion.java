package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JAlerta;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "alerta-trad-sequence", sequenceName = "RS2_TRAALE_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAALE", indexes = {@Index(name = "RS2_TRAALE_PK_I", columnList = "TRAL_CODIGO")})
public class JAlertaTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta-trad-sequence")
    @Column(name = "TRAL_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRAL_CODALE", nullable = false)
    private JAlerta alerta;

    @Column(name = "TRAL_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRAL_DESCRI")
    private String descripcion;

    public static List<JAlertaTraduccion> createInstance(List<String> idiomas) {
        List<JAlertaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JAlertaTraduccion trad = new JAlertaTraduccion();
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

    public JAlerta getAlerta() {
        return alerta;
    }

    public void setAlerta(JAlerta alerta) {
        this.alerta = alerta;
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
        JAlertaTraduccion that = (JAlertaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JAlertaTraduccion{" + "codigo=" + codigo + ", idioma='" + idioma + '\'' + ", descripcion=" + descripcion + '}';
    }
}