package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
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
@SequenceGenerator(name = "tipo-publicoobjenttra-sequence", sequenceName = "RS2_TRATPSP_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPSP",
        indexes = {
                @Index(name = "RS2_TRATPPO_PK", columnList = "TRSP_CODIGO" +
                        "")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoPublicoObjetivoEntidadTraduccion.FIND_BY_ID,
                query = "select p from JTipoPublicoObjetivoEntidadTraduccion p where p.codigo = :id")
})
public class JTipoPublicoObjetivoEntidadTraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoPublicoObjetivoEntidadTraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-publicoobjenttra-sequence")
    @Column(name = "TRSP_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRSP_CODTPSP")
    private JTipoPublicoObjetivoEntidad tipoPublicoObjetivoEntidad;

    @Column(name = "TRSP_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRSP_DESCR", length = 255)
    private String descripcion;

    public static List<JTipoPublicoObjetivoEntidadTraduccion> createInstance(List<String> idiomas) {
        List<JTipoPublicoObjetivoEntidadTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JTipoPublicoObjetivoEntidadTraduccion trad = new JTipoPublicoObjetivoEntidadTraduccion();
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

    public JTipoPublicoObjetivoEntidad getTipoPublicoObjetivoEntidad() {
        return tipoPublicoObjetivoEntidad;
    }

    public void setTipoPublicoObjetivoEntidad(JTipoPublicoObjetivoEntidad tipoPublicoObjetivoEntidad) {
        this.tipoPublicoObjetivoEntidad = tipoPublicoObjetivoEntidad;
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
                "id=" + codigo +
                "tipoPublicoObjetivoEntidad=" + tipoPublicoObjetivoEntidad +
                "idioma=" + idioma +
                "descripcion=" + descripcion +
                '}';
    }

}