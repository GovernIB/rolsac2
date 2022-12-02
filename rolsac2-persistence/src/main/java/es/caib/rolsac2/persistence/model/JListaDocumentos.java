package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "lista-doc-sequence", sequenceName = "RS2_LSTDOC_SEQ", allocationSize = 1)
@Table(name = "RS2_LSTDOC",
        indexes = {
                @Index(name = "RS2_LSTDOC_PK_I", columnList = "LSDO_CODIGO")
        })
public class JListaDocumentos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lista-doc-sequence")
    @Column(name = "LSDO_CODIGO", nullable = false)
    private Long codigo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

}