package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "tema-sequence", sequenceName = "RS2_TEMA_SEQ", allocationSize = 1)
@Table(name = "RS2_TEMA",
        indexes = {
                @Index(name = "RS2_TEMA_PK_I", columnList = "TEMA_CODIGO")
        }
)
public class JTema {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tema-sequence")
    @Column(name = "TEMA_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TEMA_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TEMA_IDENTI", nullable = false, length = 50)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMA_PADRE")
    private JTema temaPadre;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad temaCodenti) {
        this.entidad = temaCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String temaIdenti) {
        this.identificador = temaIdenti;
    }

    public JTema getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(JTema temaPadre) {
        this.temaPadre = temaPadre;
    }

}