package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ua-seccion-sequence", sequenceName = "RS2_UNASEC_SEQ", allocationSize = 1)
@Table(name = "RS2_UNASEC",
        indexes = {
                @Index(name = "RS2_UNASEC_PK_I", columnList = "UASE_CODIGO")
        }
)
public class JUnidadAdministrativaSeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-seccion-sequence")
    @Column(name = "UASE_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UASE_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UASE_CODSEC", nullable = false)
    private JSeccion seccion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa uaseCodua) {
        this.unidadAdministrativa = uaseCodua;
    }

    public JSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(JSeccion seccion) {
        this.seccion = seccion;
    }
}