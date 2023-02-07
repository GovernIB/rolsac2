package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoDocumentoTraduccion;
import es.caib.rolsac2.service.model.*;

import javax.persistence.*;
import java.util.List;

/**
 * La claseJ procedimiento documento.
 */
@Entity
@SequenceGenerator(name = "procedimiento-doc-sequence", sequenceName = "RS2_DOCPR_SEQ", allocationSize = 1)
@Table(name = "RS2_DOCPR",
        indexes = {
                @Index(name = "RS2_DOCPR_PK_I", columnList = "DOPR_CODIGO")
        }
)
public class JProcedimientoDocumento {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-doc-sequence")
    @Column(name = "DOPR_CODIGO", nullable = false)
    private Long codigo;

    @Column(name = "DOPR_ORDEN")
    private Integer orden;

    /**
     * Lista de documentos
     **/
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "DOCPR_CODLSD")
    //private JListaDocumentos listaDocumentos;
    @Column(name = "DOCPR_CODLSD")
    private Long listaDocumentos;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "documento", fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<JProcedimientoDocumentoTraduccion> traducciones;

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
     * Obtiene lista documentos.
     *
     * @return lista documentos
     */
    public Long getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * Establece lista documentos.
     *
     * @param listaDocumentos lista documentos
     */
    public void setListaDocumentos(Long listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene traducciones.
     *
     * @return traducciones
     */
    public List<JProcedimientoDocumentoTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JProcedimientoDocumentoTraduccion> traducciones) {
        this.traducciones = traducciones;
    }

    /**
     * To model procedimiento documento dto.
     *
     * @return procedimiento documento dto
     */
    public ProcedimientoDocumentoDTO toModel() {
        ProcedimientoDocumentoDTO doc = new ProcedimientoDocumentoDTO();
        doc.setCodigo(this.getCodigo());
        doc.setOrden(this.getOrden());
        Literal titulo = new Literal();
        Literal descripcion = new Literal();
        Literal url = new Literal();
        DocumentoMultiIdioma documentos = new DocumentoMultiIdioma();
        if (this.traducciones != null) {
            for (JProcedimientoDocumentoTraduccion traduccion : traducciones) {
                titulo.add(new Traduccion(traduccion.getIdioma(), traduccion.getTitulo()));
                descripcion.add(new Traduccion(traduccion.getIdioma(), traduccion.getDescripcion()));
                //url.add(new Traduccion(traduccion.getIdioma(), traduccion.getUrl()));
                if (traduccion.getDocumento() != null && traduccion.getDocumento().getTraducciones() != null && traduccion.getDocumento().getTraduccion(traduccion.getIdioma()) != null) {
                    JProcedimientoDocumentoTraduccion jdocTrad = traduccion.getDocumento().getTraduccion(traduccion.getIdioma());

                    DocumentoTraduccion doctrad = new DocumentoTraduccion();
                    doctrad.setIdioma(traduccion.getIdioma());

                    FicheroDTO fichero = new FicheroDTO();
                    fichero.setCodigo(jdocTrad.getFichero());
                    doctrad.setFicheroDTO(fichero);

                    documentos.add(doctrad);
                }
            }
        }
        doc.setTitulo(titulo);
        doc.setDescripcion(descripcion);
        doc.setUrl(url);
        doc.setDocumentos(documentos);
        return doc;
    }

    private JProcedimientoDocumentoTraduccion getTraduccion(String idioma) {
        if (this.traducciones != null) {
            for (JProcedimientoDocumentoTraduccion trad : this.traducciones) {
                if (idioma.equals(trad.getIdioma())) {
                    return trad;
                }
            }
        }
        return null;
    }
}