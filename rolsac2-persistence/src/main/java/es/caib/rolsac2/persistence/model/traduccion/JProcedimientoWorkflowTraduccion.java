package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "procedimiento-wf-trad-sequence", sequenceName = "RS2_TRAPRWF_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAPRWF", indexes = {@Index(name = "RS2_TRAPRWF_PK_I", columnList = "TRPW_CODIGO")})
public class JProcedimientoWorkflowTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-wf-trad-sequence")
    @Column(name = "TRPW_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRPW_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimientoWorkflow;

    @Column(name = "TRPW_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRPW_NOMBRE", length = 256)
    private String nombre;

    @Column(name = "TRPW_OBJETO")
    private String objeto;

    @Column(name = "TRPW_DESTIN")
    private String destinatarios;

    @Column(name = "TRPW_OBSER")
    private String observaciones;

    @Column(name = "TRPW_DPFINA")
    private String lopdFinalidad;

    @Column(name = "TRPW_DPDEST")
    private String lopdDestinatario;

    @Column(name = "TRPW_DPDOC")
    private Long documentoLOPD;

    @Column(name = "TRPW_KEYWORD")
    private String keywords;

    /**
     * PARA PROC: REQUISITOS
     **/
    @Column(name = "TRPW_SVREQ")
    private String requisitos;

    /**
     * PARA PROC: TERMINO RESOLUCION
     **/
    @Column(name = "TRPW_PRRESO")
    private String terminoResolucion;

    public static List<JProcedimientoWorkflowTraduccion> createInstance(List<String> idiomas) {
        List<JProcedimientoWorkflowTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JProcedimientoWorkflowTraduccion trad = new JProcedimientoWorkflowTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public static List<JProcedimientoWorkflowTraduccion> clonar(List<JProcedimientoWorkflowTraduccion> traducciones, JProcedimientoWorkflow jProcedimientoWorkflow) {
        List<JProcedimientoWorkflowTraduccion> retorno = null;
        if (traducciones != null) {
            retorno = new ArrayList<>();
            for (JProcedimientoWorkflowTraduccion traduccion : traducciones) {
                JProcedimientoWorkflowTraduccion trad = new JProcedimientoWorkflowTraduccion();
                trad.setProcedimientoWorkflow(jProcedimientoWorkflow);
                trad.setIdioma(traduccion.getIdioma());
                trad.setNombre(traduccion.getNombre());
                trad.setObjeto(traduccion.getObjeto());
                trad.setDestinatarios(traduccion.getDestinatarios());
                trad.setObservaciones(traduccion.getObservaciones());
                trad.setLopdFinalidad(traduccion.getLopdFinalidad());
                trad.setLopdDestinatario(traduccion.getLopdDestinatario());
                trad.setKeywords(traduccion.getKeywords());
                trad.setRequisitos(traduccion.getRequisitos());
                trad.setTerminoResolucion(traduccion.getTerminoResolucion());
                retorno.add(trad);
            }
        }
        return retorno;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JProcedimientoWorkflow getProcedimientoWorkflow() {
        return procedimientoWorkflow;
    }

    public void setProcedimientoWorkflow(JProcedimientoWorkflow trpwCodprwf) {
        this.procedimientoWorkflow = trpwCodprwf;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trpwIdioma) {
        this.idioma = trpwIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String trpwNombre) {
        this.nombre = trpwNombre;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String trpwObjeto) {
        this.objeto = trpwObjeto;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String trpwDestin) {
        this.destinatarios = trpwDestin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String trpwObser) {
        this.observaciones = trpwObser;
    }

    public String getLopdFinalidad() {
        return lopdFinalidad;
    }

    public void setLopdFinalidad(String lopdFin) {
        this.lopdFinalidad = lopdFin;
    }

    public String getLopdDestinatario() {
        return lopdDestinatario;
    }

    public void setLopdDestinatario(String lopdDest) {
        this.lopdDestinatario = lopdDest;
    }

    public Long getDocumentoLOPD() {
        return documentoLOPD;
    }

    public void setDocumentoLOPD(Long trpwDpdoc) {
        this.documentoLOPD = trpwDpdoc;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String trpwPrreq) {
        this.requisitos = trpwPrreq;
    }

    public String getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(String trpwPrreso) {
        this.terminoResolucion = trpwPrreso;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JProcedimientoWorkflowTraduccion that = (JProcedimientoWorkflowTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JProcedimientoWorkflowTraduccion{" + "codigo=" + codigo + '}';
    }
}