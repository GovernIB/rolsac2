package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.persistence.model.JUsuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "user-trad-sequence", sequenceName = "RS2_TRAUSER_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAUSER",
        indexes = {
                @Index(name = "RS2_TRAUSER_PK_I", columnList = "TRUS_CODIGO")
        }
)
public class JUsuarioTraduccion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-trad-sequence")
    @Column(name = "TRUS_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRUS_CODUS", nullable = false)
    private JUsuario usuario;

    @Column(name = "TRUS_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRUS_OBSER", nullable = false)
    private String observaciones;


    public static List<JUsuarioTraduccion> createInstance(List<String> idiomas) {
        List<JUsuarioTraduccion> traducciones = new ArrayList<>();

        for (String idioma : idiomas) {
            JUsuarioTraduccion trad = new JUsuarioTraduccion();
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

    public JUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(JUsuario usuario) {
        this.usuario = usuario;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUsuarioTraduccion that = (JUsuarioTraduccion) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(usuario, that.usuario) && Objects.equals(idioma, that.idioma) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, usuario, idioma, observaciones);
    }

    @Override
    public String toString() {
        return "JUsuarioTraduccion{" +
                "codigo=" + codigo +
                ", usuario=" + usuario +
                ", idioma='" + idioma + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}

