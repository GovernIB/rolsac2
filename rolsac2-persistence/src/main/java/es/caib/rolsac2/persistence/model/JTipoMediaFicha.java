package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaFichaTraduccion;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "fichero-tipo-ficha-sequence", sequenceName = "RS2_TIPOMEDFCH_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMEDFCH",
        indexes = {
                @Index(name = "RS2_TIPOMEDFCH_PK", columnList = "TPMF_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTipoMediaFicha.FIND_BY_ID,
                query = "select p from JTipoMediaFicha p where p.codigo = :id"),
        @NamedQuery(name = JTipoMediaFicha.COUNT_BY_IDENTIFICADOR,
                query = "select COUNT(p) from JTipoMediaFicha p where p.identificador = :identificador")
})
public class JTipoMediaFicha {

    public static final String FIND_BY_ID = "TipoMediaFicha.FIND_BY_ID";
    public static final String COUNT_BY_IDENTIFICADOR = "TipoMediaFicha.COUNT_BY_IDENTIFICADOR";
    private static final long serialVersionUID = 1L;
    /**
     * Descripción
     */
    @OneToMany(mappedBy = "tipoMediaFicha", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JTipoMediaFichaTraduccion> descripcion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-ficha-sequence")
    @Column(name = "TPMF_CODIGO", nullable = false)
    private Long codigo;

    @Column(name = "TPMF_IDENTI", nullable = false, length = 50)
    private String identificador;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String tpmfIdenti) {
        this.identificador = tpmfIdenti;
    }

    public List<JTipoMediaFichaTraduccion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            List<JTipoMediaFichaTraduccion> descripcion
    ) {
        this.descripcion = descripcion;
    }
}