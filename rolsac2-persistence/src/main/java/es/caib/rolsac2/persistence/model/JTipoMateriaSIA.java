package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoMateriaSIATraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un tipo materia SIA. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-materia-sia-sequence", sequenceName = "RS2_TIPOMSI_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMSI", indexes = {@Index(name = "RS2_TIPOMSI_PK", columnList = "TPMS_CODIGO")})
@NamedQueries({
        @NamedQuery(name = JTipoMateriaSIA.FIND_BY_ID, query = "select p from JTipoMateriaSIA p where p.id = :id"),
        @NamedQuery(name = JTipoMateriaSIA.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoMateriaSIA p where p.identificador = :identificador")})
public class JTipoMateriaSIA extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoMateriaSIA.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoMateriaSIA.COUNT_BY_IDENTIFICADOR";

    public JTipoMateriaSIA() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-materia-sia-sequence")
    @Column(name = "TPMS_CODIGO", nullable = false, length = 10)
    private Long id;

    /**
     * Identificacion del tipo de materia SIA
     */
    @Column(name = "TPMS_IDENTI", length = 50)
    private String identificador;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoMateriaSIA", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoMateriaSIATraduccion> descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificacion) {
        this.identificador = identificacion;
    }

    public List<JTipoMateriaSIATraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoMateriaSIATraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JTipoMateriaSIA))
            return false;
        JTipoMateriaSIA that = (JTipoMateriaSIA) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JTipoMateriaSIA{" + "id=" + id + "identificador=" + identificador + '}';
    }

}
