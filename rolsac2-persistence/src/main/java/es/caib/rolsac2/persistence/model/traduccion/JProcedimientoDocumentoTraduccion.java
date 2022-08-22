package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JProcedimientoDocumento;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-doc-trad-sequence", sequenceName = "RS2_TRADOPR_SEQ", allocationSize = 1)
@Table(name = "RS2_TRADOPR",
        indexes = {
                @Index(name = "RS2_TRADOPR_PK_I", columnList = "TRDP_CODIGO")
        }
)
public class JProcedimientoDocumentoTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-doc-trad-sequence")
    @Column(name = "TRDP_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRDP_CODDOPR", nullable = false)
    private JProcedimientoDocumento documento;

    @Column(name = "TRDP_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRDP_TITULO")
    private String titulo;

    @Column(name = "TRDP_DESCRI", length = 4000)
    private String descripcion;

    @Column(name = "TRDP_FICHER")
    private Integer fichero;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JProcedimientoDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(JProcedimientoDocumento trdpCoddopr) {
        this.documento = trdpCoddopr;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trdpIdioma) {
        this.idioma = trdpIdioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String trdpTitulo) {
        this.titulo = trdpTitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trdpDescri) {
        this.descripcion = trdpDescri;
    }

    public Integer getFichero() {
        return fichero;
    }

    public void setFichero(Integer trdpFicher) {
        this.fichero = trdpFicher;
    }

}