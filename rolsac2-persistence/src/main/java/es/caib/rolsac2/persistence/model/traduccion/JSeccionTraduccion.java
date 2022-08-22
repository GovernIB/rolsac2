package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JSeccion;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "seccion-trad-sequence", sequenceName = "RS2_TRASECC_SEQ", allocationSize = 1)
@Table(name = "RS2_TRASECC",
        indexes = {
                @Index(name = "RS2_TRASECC_PK_I", columnList = "TRSE_CODIGO")
        }
)
public class JSeccionTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seccion-trad-sequence")
    @Column(name = "TRSE_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRSE_CODSECC", nullable = false)
    private JSeccion seccion;

    @Column(name = "TRSE_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRSE_NOMBRE")
    private String nombre;

    @Column(name = "TRSE_DESCRI", length = 4000)
    private String descripcion;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(JSeccion trseCodsecc) {
        this.seccion = trseCodsecc;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trseIdioma) {
        this.idioma = trseIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String trseNombre) {
        this.nombre = trseNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trseDescri) {
        this.descripcion = trseDescri;
    }

}