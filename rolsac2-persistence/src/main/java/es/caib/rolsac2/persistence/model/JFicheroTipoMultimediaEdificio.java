package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "fichero-tipo-edif-sequence", sequenceName = "RS2_TIPOMEDEDI_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMEDEDI",
        indexes = {
                @Index(name = "RS2_TIPOMEDEDI_PK_I", columnList = "TPME_CODIGO")
        })
public class JFicheroTipoMultimediaEdificio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-edif-sequence")
    @Column(name = "TPME_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPME_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TPME_IDENTI", nullable = false, length = 50)
    private String identificador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad tpmeCodenti) {
        this.entidad = tpmeCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String tpmeIdenti) {
        this.identificador = tpmeIdenti;
    }

}