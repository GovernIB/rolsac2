package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JCategoriaPDUTraduccion;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RS2_CATPDU")
@NamedQueries({
        @NamedQuery(name = JCategoriaPDU.FIND_BY_ID,
                query = "select p from JCategoriaPDU p where p.codigo = :id"),
        @NamedQuery(name = JCategoriaPDU.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JCategoriaPDU p where p.entidad.codigo = :entidad and lower(p.identificador) = :identificador")
})
public class JCategoriaPDU {

    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "JCategoriaPDU.FIND_BY_ID";
    /**
     * La consulta COUNT_BY_IDENTIFICADOR.
     */
    public static final String COUNT_BY_IDENTIFICADOR = "JCategoriaPDU.COUNT_BY_IDENTIFICADOR";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RS2_CATPDU_SEQ")
    @SequenceGenerator(name = "RS2_CATPDU_SEQ", sequenceName = "RS2_CATPDU_SEQ", allocationSize = 1)
    @Column(name = "CPDU_CODIGO")
    private Long codigo;

    @Column(name = "CPDU_IDENTIF", length = 100)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CPDU_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Descripción
     */
    @OneToMany(mappedBy = "categoriaPDU", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JCategoriaPDUTraduccion> descripcion;


    // Getters and Setters

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }


    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<JCategoriaPDUTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<JCategoriaPDUTraduccion> descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JCategoriaPDU jCategoria = (JCategoriaPDU) o;
        return codigo.equals(jCategoria.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JCategoriaPdu{" +
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
