package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-sequence", sequenceName = "RS2_FICHA_SEQ", allocationSize = 1)
@Table(name = "RS2_FICHA",
        indexes = {
                @Index(name = "RS2_FICHA_PK_I", columnList = "FCHA_CODIGO")
        })
public class JFicha {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-sequence")
    @Column(name = "FCHA_CODIGO", nullable = false)
    private Integer id;

    @Column(name = "FCHA_RESPNOM")
    private String responsable;

    @Column(name = "FCHA_NOTAS", length = 4000)
    private String notas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCME_TIPOMED", nullable = false)
    private JUnidadAdministrativaSeccion seccionUA;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String fchaRespnom) {
        this.responsable = fchaRespnom;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String fchaNotas) {
        this.notas = fchaNotas;
    }

    public JUnidadAdministrativaSeccion getSeccionUA() {
        return seccionUA;
    }

    public void setSeccionUA(JUnidadAdministrativaSeccion seccionUA) {
        this.seccionUA = seccionUA;
    }
}