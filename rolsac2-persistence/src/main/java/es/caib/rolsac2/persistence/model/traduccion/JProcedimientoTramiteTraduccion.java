package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JProcedimientoTramite;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-tram-trad-sequence", sequenceName = "RS2_TRAPRTA_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAPRTA",
        indexes = {
                @Index(name = "RS2_TRAPRTA_PK_I", columnList = "TRTA_CODIGO")
        }
)
public class JProcedimientoTramiteTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tram-trad-sequence")
    @Column(name = "TRTA_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTA_CODPRTA", nullable = false)
    private JProcedimientoTramite procedimientoTramite;

    @Column(name = "TRTA_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Lob
    @Column(name = "TRTA_REQUISITOS")
    private String requisitos;

    @Column(name = "TRTA_NOMBRE")
    private String nombre;

    @Column(name = "TRTA_DOCUM", length = 4000)
    private String documentacion;

    @Column(name = "TRTA_OBSERV", length = 4000)
    private String observacion;

    @Column(name = "TRTA_TERMIN", length = 512)
    private String terminoMaximo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    public JProcedimientoTramite getProcedimientoTramite() {
        return procedimientoTramite;
    }

    public void setProcedimientoTramite(JProcedimientoTramite trtaCodprta) {
        this.procedimientoTramite = trtaCodprta;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtaIdioma) {
        this.idioma = trtaIdioma;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String trtaRequisitos) {
        this.requisitos = trtaRequisitos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String trtaNombre) {
        this.nombre = trtaNombre;
    }

    public String getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(String trtaDocum) {
        this.documentacion = trtaDocum;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String trtaObserv) {
        this.observacion = trtaObserv;
    }

    public String getTerminoMaximo() {
        return terminoMaximo;
    }

    public void setTerminoMaximo(String trtaTermin) {
        this.terminoMaximo = trtaTermin;
    }

}