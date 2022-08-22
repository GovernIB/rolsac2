package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JEdificio;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "edificio-traduccion-sequence", sequenceName = "RS2_TRAEDIF_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAEDIF", indexes = {@Index(name = "RS2_TRAEDIF_PK_I", columnList = "TRED_CODIGO")})
public class JEdificioTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edificio-traduccion-sequence")
    @Column(name = "TRED_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRED_CODEDIF", nullable = false)
    private JEdificio edificio;

    @Column(name = "TRED_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRED_NOMBRE", length = 4000)
    private String nombre;

    public static List<JEdificioTraduccion> createInstance() {
        List<JEdificioTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JEdificioTraduccion trad = new JEdificioTraduccion();
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

    public JEdificio getEdificio() {
        return edificio;
    }

    public void setEdificio(JEdificio tredCodedif) {
        this.edificio = tredCodedif;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String tredIdioma) {
        this.idioma = tredIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String tredNombre) {
        this.nombre = tredNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JEdificioTraduccion that = (JEdificioTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JEdificioTraduccion{" + "id=" + codigo + '}';
    }
}
