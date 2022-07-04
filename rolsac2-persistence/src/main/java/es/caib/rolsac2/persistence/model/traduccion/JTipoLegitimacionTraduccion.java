package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoLegitimacion;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-legitimacion-traduccion-sequence", sequenceName = "RS2_TRATPLE_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPLE",
  indexes = {
    @Index(name = "RS2_TRATPLE_PK", columnList = "TRTL_CODIGO")
  }
)
@NamedQueries({
  @NamedQuery(name = JTipoLegitimacionTraduccion.FIND_BY_ID,
    query = "select p from JTipoLegitimacionTraduccion p where p.id = :id")
})
public class JTipoLegitimacionTraduccion extends BaseEntity {

    public static final String FIND_BY_ID = "TipoLegitimacionTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-legitimacion-traduccion-sequence")
    @Column(name = "TRTL_CODIGO", nullable = false, length = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRTL_CODTPLE")
    private JTipoLegitimacion tipoLegitimacion;

    @Column(name = "TRTL_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTL_DESCRI", length = 255)
    private String descripcion;

    public static List<JTipoLegitimacionTraduccion> createInstance() {
        List<JTipoLegitimacionTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoLegitimacionTraduccion trad = new JTipoLegitimacionTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JTipoLegitimacion getTipoLegitimacion() {
        return tipoLegitimacion;
    }

    public void setTipoLegitimacion(JTipoLegitimacion tipoLegitimacion) {
        this.tipoLegitimacion = tipoLegitimacion;
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
        JTipoLegitimacionTraduccion that = (JTipoLegitimacionTraduccion) o;
        return Objects.equals(id, that.id) && Objects.equals(tipoLegitimacion,
          that.tipoLegitimacion) && Objects.equals(idioma, that.idioma) && Objects.equals(descripcion,
          that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoLegitimacion, idioma, descripcion);
    }
}