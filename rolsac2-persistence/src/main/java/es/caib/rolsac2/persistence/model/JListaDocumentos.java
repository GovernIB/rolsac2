package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La clase J lista documentos.
 */
@Entity
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

}