package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JProcedimientoTramite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "procedimiento-tram-trad-sequence", sequenceName = "RS2_TRAPRTA_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAPRTA", indexes = {@Index(name = "RS2_TRAPRTA_PK_I", columnList = "TRTA_CODIGO")})
public class JProcedimientoTramiteTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tram-trad-sequence")
    @Column(name = "TRTA_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTA_CODPRTA", nullable = false)
    private JProcedimientoTramite procedimientoTramite;

    @Column(name = "TRTA_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTA_REQUISITOS")
    private String requisitos;

    @Column(name = "TRTA_NOMBRE", length = 255)
    private String nombre;

    @Column(name = "TRTA_DOCUM")
    private String documentacion;

    @Column(name = "TRTA_OBSERV")
    private String observacion;

    @Column(name = "TRTA_TERMIN", length = 512)
    private String terminoMaximo;

    public static List<JProcedimientoTramiteTraduccion> createInstance(List<String> idiomas) {
        List<JProcedimientoTramiteTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JProcedimientoTramiteTraduccion trad = new JProcedimientoTramiteTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public static List<JProcedimientoTramiteTraduccion> clonar(List<JProcedimientoTramiteTraduccion> traducciones, JProcedimientoTramite clonado) {
        List<JProcedimientoTramiteTraduccion> retorno = new ArrayList<>();
        if (traducciones != null) {
            for (JProcedimientoTramiteTraduccion traduccion : traducciones) {
                JProcedimientoTramiteTraduccion traduccionClonada = JProcedimientoTramiteTraduccion.clonar(traduccion, clonado);
                retorno.add(traduccionClonada);
            }
        }
        return retorno;
    }

    public static JProcedimientoTramiteTraduccion clonar(JProcedimientoTramiteTraduccion traduccion, JProcedimientoTramite clonado) {
        JProcedimientoTramiteTraduccion retorno = null;
        if (traduccion != null) {
            retorno = new JProcedimientoTramiteTraduccion();
            retorno.setProcedimientoTramite(clonado);
            retorno.setIdioma(traduccion.getIdioma());
            retorno.setRequisitos(traduccion.getRequisitos());
            retorno.setNombre(traduccion.getNombre());
            retorno.setDocumentacion(traduccion.getDocumentacion());
            retorno.setObservacion(traduccion.getObservacion());
            retorno.setTerminoMaximo(traduccion.getTerminoMaximo());
        }
        return retorno;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
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