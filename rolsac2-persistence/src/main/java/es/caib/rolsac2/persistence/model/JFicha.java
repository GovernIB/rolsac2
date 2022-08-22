package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ficha-sequence", sequenceName = "RS2_FICHA_SEQ", allocationSize = 1)
@Table(name = "RS2_FICHA",
        indexes = {
                @Index(name = "RS2_FICHA_PK_I", columnList = "FCHA_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JFicha.FIND_BY_ID,
                query = "select p from JFicha p where p.codigo = :id")
})
public class JFicha {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ficha-sequence")
    @Column(name = "FCHA_CODIGO", nullable = false)
    private Long codigo;

    public static final String FIND_BY_ID = "Ficha.FIND_BY_ID";

    @Column(name = "FCHA_RESPNOM")
    private String responsable;

    @Column(name = "FCHA_NOTAS", length = 4000)
    private String notas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCME_TIPOMED", nullable = false)
    private JUnidadAdministrativaSeccion seccionUA;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
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