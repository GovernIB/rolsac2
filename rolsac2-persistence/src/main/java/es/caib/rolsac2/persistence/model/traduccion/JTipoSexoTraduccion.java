package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-sexo-traduccion-sequence", sequenceName = "RS2_TRATPSX_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPSX",
        indexes = {
                @Index(name = "RS2_TRATPSX_PK", columnList = "TRTS_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoSexoTraduccion.FIND_BY_ID,
                query = "select p from JTipoSexoTraduccion p where p.codigo = :id")
})
public class JTipoSexoTraduccion extends BaseEntity {

    public static final String FIND_BY_ID = "TipoSexoTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-sexo-traduccion-sequence")
    @Column(name = "TRTS_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRTS_CODTPSX")
    private JTipoSexo tipoSexo;

    @Column(name = "TRTS_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTS_DESCRI", length = 255)
    private String descripcion;

    public static List<JTipoSexoTraduccion> createInstance() {
        List<JTipoSexoTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoSexoTraduccion trad = new JTipoSexoTraduccion();
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

    public JTipoSexo getTipoSexo() {
        return tipoSexo;
    }

    public void setTipoSexo(JTipoSexo tipoSexo) {
        this.tipoSexo = tipoSexo;
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
        JTipoSexoTraduccion that = (JTipoSexoTraduccion) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(tipoSexo,
                that.tipoSexo) && Objects.equals(idioma, that.idioma) && Objects.equals(descripcion,
                that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipoSexo, idioma, descripcion);
    }
}
