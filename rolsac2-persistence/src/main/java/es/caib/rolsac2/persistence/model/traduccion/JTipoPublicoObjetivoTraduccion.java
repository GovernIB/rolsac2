package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author jsegovia
 */
@Entity
@SequenceGenerator(name = "tipo-publicoobjtra-sequence", sequenceName = "RS2_TRATPPO_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPPO",
        indexes = {
                @Index(name = "RS2_TRATPPO_PK", columnList = "TRTP_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoPublicoObjetivoTraduccion.FIND_BY_ID,
                query = "select p from JTipoPublicoObjetivoTraduccion p where p.id = :id")
})
public class JTipoPublicoObjetivoTraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoPublicoObjetivoTraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-publicoobjtra-sequence")
    @Column(name = "TRTP_CODIGO", nullable = false, length = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRTP_CODTPPO")
    private JTipoPublicoObjetivo tipoPublicoObjetivo;

    @Column(name = "TRTP_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTP_DESCRI", length = 255)
    private String descripcion;

    public static List<JTipoPublicoObjetivoTraduccion> createInstance() {
        List<JTipoPublicoObjetivoTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoPublicoObjetivoTraduccion trad = new JTipoPublicoObjetivoTraduccion();
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

    public JTipoPublicoObjetivo getTipoPublicoObjetivo() {
        return tipoPublicoObjetivo;
    }

    public void setTipoPublicoObjetivo(JTipoPublicoObjetivo tipoPublicoObjetivo) {
        this.tipoPublicoObjetivo = tipoPublicoObjetivo;
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
    public String toString() {
        return "JTipoPublicoObjetivo{" +
                "id=" + id +
                "tipoPublicoObjetivo=" + tipoPublicoObjetivo +
                "idioma=" + idioma +
                "descripcion=" + descripcion +
                '}';
    }

}