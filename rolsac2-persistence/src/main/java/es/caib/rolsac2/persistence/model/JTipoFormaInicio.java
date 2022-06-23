package es.caib.rolsac2.persistence.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import es.caib.rolsac2.persistence.model.traduccion.JTipoFormaInicioTraduccion;

/**
 * Representacion de un tipo materia SIA. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-forma-inicio-sequence", sequenceName = "RS2_TIPOFOI_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOFOI", indexes = {@Index(name = "RS2_TIPOFOI_PK", columnList = "TPFI_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoFormaInicio.FIND_BY_ID,
                query = "select p from JTipoFormaInicio p where p.id = :id"),
                @NamedQuery(name = JTipoFormaInicio.COUNT_BY_IDENTIFICADOR,
                                query = "select COUNT(p) from JTipoFormaInicio p where p.identificador = :identificador")})
public class JTipoFormaInicio extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoFormaInicio.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoFormaInicio.COUNT_BY_IDENTIFICADOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-forma-inicio-sequence")
    @Column(name = "TPFI_CODIGO", nullable = false, length = 10)
    private Long id;

    /**
     * Identificacion del tipo de forma de inicio
     */
    @Column(name = "TPFI_IDENTI", length = 50)
    private String identificador;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoFormaInicio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoFormaInicioTraduccion> descripcion;

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

    public List<JTipoFormaInicioTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JTipoFormaInicioTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JTipoFormaInicio))
            return false;
        JTipoFormaInicio that = (JTipoFormaInicio) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JTipoFormaInicio{" + "id=" + id + "identificador=" + identificador + '}';
    }

}
