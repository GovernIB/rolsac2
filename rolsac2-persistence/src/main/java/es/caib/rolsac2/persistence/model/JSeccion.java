package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "seccion-sequence", sequenceName = "RS2_SECCION_SEQ", allocationSize = 1)
@Table(name = "RS2_SECCION",
        indexes = {
                @Index(name = "RS2_SECCION_PK_I", columnList = "SECC_CODIGO")
        }
)
public class JSeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seccion-sequence")
    @Column(name = "SECC_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SECC_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "SECC_IDENTI", nullable = false, length = 50)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECC_SECPADRE")
    private JSeccion padre;

    /**
     * SECCIÓN MANTENIBLE SOLO POR ADMIN ENTIDAD
     **/
    @Column(name = "SECC_ADME", nullable = false)
    private boolean manteniblePorAdmEntidad = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad seccCodenti) {
        this.entidad = seccCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String seccIdenti) {
        this.identificador = seccIdenti;
    }

    public JSeccion getPadre() {
        return padre;
    }

    public void setPadre(JSeccion seccSecpadre) {
        this.padre = seccSecpadre;
    }

    public boolean getManteniblePorAdmEntidad() {
        return manteniblePorAdmEntidad;
    }

    public void setManteniblePorAdmEntidad(boolean seccAdme) {
        this.manteniblePorAdmEntidad = seccAdme;
    }

}