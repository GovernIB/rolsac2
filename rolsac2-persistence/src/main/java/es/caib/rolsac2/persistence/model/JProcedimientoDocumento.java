package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-doc-sequence", sequenceName = "RS2_DOCPR_SEQ", allocationSize = 1)
@Table(name = "RS2_DOCPR",
        indexes = {
                @Index(name = "RS2_DOCPR_PK_I", columnList = "DOPR_CODIGO")
        }
)
public class JProcedimientoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-doc-sequence")
    @Column(name = "DOPR_CODIGO", nullable = false)
    private Integer id;

    /**
     * Lista de documentos
     **/
    @ManyToOne
    @JoinColumn(name = "DOCPR_CODLSD")
    private JListaDocumentos listaDocumentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JListaDocumentos getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(JListaDocumentos listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }
}