package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoVia;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-via-traduccion-sequence", sequenceName = "RS2_TRATPVI_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPVI",
        indexes = {
                @Index(name = "RS2_TRATPVI_PK", columnList = "TRTV_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoViaTraduccion.FIND_BY_ID,
                query = "select p from JTipoViaTraduccion p where p.codigo = :id")
})
public class JTipoViaTraduccion extends BaseEntity {

    public static final String FIND_BY_ID = "TipoViaTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-via-traduccion-sequence")
    @Column(name = "TRTV_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRTV_CODTPVI")
    private JTipoVia tipoVia;

    @Column(name = "TRTV_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTV_DESCRI", length = 255)
    private String descripcion;

    public static List<JTipoViaTraduccion> createInstance() {
        List<JTipoViaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoViaTraduccion trad = new JTipoViaTraduccion();
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

    public JTipoVia getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(JTipoVia tipoVia) {
        this.tipoVia = tipoVia;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JTipoViaTraduccion that = (JTipoViaTraduccion) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(tipoVia,
                that.tipoVia) && Objects.equals(idioma, that.idioma) && Objects.equals(descripcion,
                that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipoVia, idioma, descripcion);
    }
}
