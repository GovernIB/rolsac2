package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-normativatra-sequence", sequenceName = "RS2_TRATPNO_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPNO",
        indexes = {
                @Index(name = "RS2_TRATPNO_PK", columnList = "TRTN_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoNormativaTraduccion.FIND_BY_ID,
                query = "select p from JTipoNormativaTraduccion p where p.codigo = :id")
})
public class JTipoNormativaTraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoNormativaTraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-normativatra-sequence")
    @Column(name = "TRTN_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRTN_CODTPNO")
    private JTipoNormativa tipoNormativa;

    @Column(name = "TRTN_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TNTR_DESCR", length = 255)
    private String descripcion;

    public static List<JTipoNormativaTraduccion> createInstance() {
        List<JTipoNormativaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoNormativaTraduccion trad = new JTipoNormativaTraduccion();
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

    public JTipoNormativa getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(JTipoNormativa tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTipoNormativaTraduccion that = (JTipoNormativaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoNormativaTraduccion{" +
                "id=" + codigo +
                "idioma=" + idioma +
                "descripcion=" + descripcion +
                '}';
    }

}