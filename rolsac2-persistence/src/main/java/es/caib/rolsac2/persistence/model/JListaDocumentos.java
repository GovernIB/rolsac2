package es.caib.rolsac2.persistence.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

/**
 * La clase J lista documentos.
 */
@Entity
@BatchSize(size = 50)
@SequenceGenerator(name = "lista-doc-sequence", sequenceName = "RS2_LSTDOC_SEQ", allocationSize = 1)
@Table(name = "RS2_LSTDOC", indexes = {@Index(name = "RS2_LSTDOC_PK_I", columnList = "LSDO_CODIGO")})
public class JListaDocumentos {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lista-doc-sequence")
    @Column(name = "LSDO_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Documentos asociados a esta lista
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCPR_CODLSD", referencedColumnName = "LSDO_CODIGO", insertable = false, updatable = false)
    @BatchSize(size = 50)
    private List<JProcedimientoDocumento> documentos;

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene documentos.
     *
     * @return documentos
     */
    public List<JProcedimientoDocumento> getDocumentos() {
        return documentos;
    }

    /**
     * Establece documentos.
     *
     * @param documentos documentos
     */
    public void setDocumentos(List<JProcedimientoDocumento> documentos) {
        this.documentos = documentos;
    }

}