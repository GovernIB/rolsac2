package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ua-fic-sequence", sequenceName = "RS2_UNAMED_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAMED",
        indexes = {
                @Index(name = "RS2_UNAMED_PK_I", columnList = "UAME_CODIGO")
        }
)
public class JUnidadAdministrativaFichero {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-fic-sequence")
    @Column(name = "UAME_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAME_CODUA", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @Column(name = "UAME_FICHERO")
    private Integer fichero;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa uameCodua) {
        this.unidadAdministrativa = uameCodua;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer uameFichero) {
        this.fichero = uameFichero;
    }

}