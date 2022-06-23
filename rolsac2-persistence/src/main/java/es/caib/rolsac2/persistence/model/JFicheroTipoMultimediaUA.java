package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "fichero-tipo-ua-sequence", sequenceName = "RS2_TIPOMEDUA_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMEDUA",
        indexes = {
                @Index(name = "RS2_TIPOMEDUA_PK_I", columnList = "TPMU_CODIGO")
        })
public class JFicheroTipoMultimediaUA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-ua-sequence")
    @Column(name = "TPMU_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPMU_CODENTI", nullable = false)
    private JEntidad entidad;

    @Column(name = "TPMU_IDENTI", nullable = false, length = 50)
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

    public void setEntidad(JEntidad tpmuCodenti) {
        this.entidad = tpmuCodenti;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String tpmuIdenti) {
        this.identificador = tpmuIdenti;
    }

}