package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JEntidad;

import javax.persistence.*;

@Entity
@Table(name = "RS2_TRAENT",
        indexes = {
                @Index(name = "RS2_TRAENT_PK_I", columnList = "TREN_CODIGO")
        })

@SequenceGenerator(name = "entidad-traduccion-sequence", sequenceName = "RS2_TRAENT_SEQ", allocationSize = 1)
public class JEntidadTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entidad-traduccion-sequence")
    @Column(name = "TREN_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TREN_CODENT", nullable = false)
    private JEntidad entidad;

    @Column(name = "TREN_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TREN_DESCRI")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad trenCodent) {
        this.entidad = trenCodent;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trenIdioma) {
        this.idioma = trenIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trenDescri) {
        this.descripcion = trenDescri;
    }

}