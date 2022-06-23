package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoAfectacionTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un tipo de afectación. A nivel de clase, definimos la secuencia que utilizaremos y sus claves
 * unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-afectacion-sequence", sequenceName = "RS2_TIPOAFE_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOAFE", indexes = {@Index(name = "RS2_TIPOAFE_PK", columnList = "TPAN_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoAfectacion.FIND_BY_ID,
        query = "select p from JTipoAfectacion p where p.id = :id"),
        @NamedQuery(name = JTipoAfectacion.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoAfectacion p where p.identificador = :identificador")})
public class JTipoAfectacion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoAfectacion.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoAfectacion.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-afectacion-sequence")
    @Column(name = "TPAN_CODIGO", nullable = false, length = 10)
    private Long id;

    /**
     * Identificacion del tipo de afectación
     */
    @Column(name = "TPAN_IDENTI", length = 50, unique = true)
    private String identificador;


    /**
     * Descripción
     */
    //@OneToMany(mappedBy = "tipoAfectacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "tipoAfectacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoAfectacionTraduccion> descripcion;

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

    public List<JTipoAfectacionTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoAfectacionTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JTipoAfectacion))
            return false;
        JTipoAfectacion that = (JTipoAfectacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JTipoAfectacion{" + "id=" + id + "identificador=" + identificador + '}';
    }

}
