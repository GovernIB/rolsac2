package es.caib.rolsac2.persistence.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * La clase documento normativa.
 */
@Entity
@SequenceGenerator(name = "documento-normativa-sequence", sequenceName = "RS2_DOCNORM_SEQ", allocationSize = 1)
@Table(name = "RS2_DOCNORM")
@NamedQueries({@NamedQuery(name = JDocumentoNormativa.FIND_BY_ID, query = "select p from JDocumentoNormativa p where p.codigo = :codigo"), @NamedQuery(name = JDocumentoNormativa.FIND_BY_NORMATIVA, query = "select p from JDocumentoNormativa p left outer join p.normativa t where t.codigo=:codigo")

})
public class JDocumentoNormativa extends BaseEntity {

    /**
     * Consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "documentoNormativa.FIND_BY_ID";
    /**
     * Consulta FIND_BY_NORMATIVA.
     */
    public static final String FIND_BY_NORMATIVA = "documentoNormativa.FIND_BY_NORMATIVA";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento-normativa-sequence")
    @Column(name = "DONO_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DONO_CODNORM", nullable = false)
    private JNormativa normativa;

    @OneToMany(mappedBy = "documentoNormativa", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JDocumentoNormativaTraduccion> traducciones;


    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }


    /**
     * Obtiene normativa.
     *
     * @return normativa
     */
    public JNormativa getNormativa() {
        return normativa;
    }

    /**
     * Establece normativa.
     *
     * @param normativa normativa
     */
    public void setNormativa(JNormativa normativa) {
        this.normativa = normativa;
    }

    /**
     * Obtiene traducciones.
     *
     * @return traducciones
     */
    public List<JDocumentoNormativaTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JDocumentoNormativaTraduccion> traducciones) {
        this.traducciones = traducciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDocumentoNormativa that = (JDocumentoNormativa) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JDocumentoNormativa{" + "codigo=" + codigo + '}';
    }
}