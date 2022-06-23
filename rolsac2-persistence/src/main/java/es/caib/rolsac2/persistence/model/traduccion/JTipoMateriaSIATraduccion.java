package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un tipo materia SIA. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-materia-sia-traduccion-sequence", sequenceName = "RS2_TRATPMS_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPMS", indexes = {@Index(name = "RS2_TRATPMS_PK", columnList = "TRTI_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoMateriaSIATraduccion.FIND_BY_ID,
        query = "select p from JTipoMateriaSIATraduccion p where p.id = :id")})
public class JTipoMateriaSIATraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "JTipoMateriaSIATraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-materia-sia-traduccion-sequence")
    @Column(name = "TRTI_CODIGO", nullable = false, length = 10)
    private Long id;

    /**
     * Tipo materia SIA
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTI_CODTPMS", nullable = false)
    private JTipoMateriaSIA tipoMateriaSIA;

    /**
     * Idioma del literal
     */
    @Column(name = "TRTI_IDIOMA", length = 2)
    private String idioma;

    /**
     * Descripción del literal
     */
    @Column(name = "TRTI_DESCRI", length = 255)
    private String descripcion;

    public static List<JTipoMateriaSIATraduccion> createInstance() {
        List<JTipoMateriaSIATraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoMateriaSIATraduccion trad = new JTipoMateriaSIATraduccion();
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

    public JTipoMateriaSIA getTipoMateriaSIA() {
        return tipoMateriaSIA;
    }

    public void setTipoMateriaSIA(JTipoMateriaSIA tipoMateriaSIA) {
        this.tipoMateriaSIA = tipoMateriaSIA;
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
        if (this == o)
            return true;
        if (!(o instanceof JTipoMateriaSIATraduccion))
            return false;
        JTipoMateriaSIATraduccion that = (JTipoMateriaSIATraduccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JTipoMateriaSIATraduccion{" + "id=" + id + "}";
    }

}
