package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "fichero-tipo-ficha-sequence", sequenceName = "RS2_TIPOMEDFCH_SEQ", allocationSize = 1)
@Table(name = "RS2_TIPOMEDFCH",
        indexes = {
                @Index(name = "RS2_TIPOMEDFCH_PK_I", columnList = "TPMF_CODIGO")
        })
public class JFicheroTipoMultimediaFicha {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-ficha-sequence")
    @Column(name = "TPMF_CODIGO", nullable = false)
    private Integer id;

    @Column(name = "TPMF_IDENTI", nullable = false, length = 50)
    private String identificador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String tpmfIdenti) {
        this.identificador = tpmfIdenti;
    }

}