package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-wf-trad-sequence", sequenceName = "RS2_TRAPRWF_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAPRWF",
        indexes = {
                @Index(name = "RS2_TRAPRWF_PK_I", columnList = "TRPW_CODIGO")
        }
)
public class JProcedimientoWorkflowTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-wf-trad-sequence")
    @Column(name = "TRPW_CODIGO", nullable = false)
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRPW_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimientoWorkflow;

    @Column(name = "TRPW_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Lob
    @Column(name = "TRPW_NOMBRE")
    private String nombre;

    @Lob
    @Column(name = "TRPW_OBJETO")
    private String objeto;

    @Lob
    @Column(name = "TRPW_DESTIN")
    private String destinatarios;

    @Lob
    @Column(name = "TRPW_OBSER")
    private String observaciones;

    @Lob
    @Column(name = "TRPW_DPFINA")
    private String datosPersonalesFinalidad;

    @Lob
    @Column(name = "TRPW_DPDEST")
    private String datosPersonalesDestinatario;

    @Column(name = "TRPW_DPDOC")
    private Integer documentoLOPD;

    /**
     * PARA PROC: REQUISITOS
     **/
    @Lob
    @Column(name = "TRPW_PRREQ")
    private String requisitos;

    /**
     * PARA PROC: TERMINO RESOLUCION
     **/
    @Lob
    @Column(name = "TRPW_PRRESO")
    private String terminoResolucion;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
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

    public String getDatosPersonalesFinalidad() {
        return datosPersonalesFinalidad;
    }

    public void setDatosPersonalesFinalidad(String trpwDpfina) {
        this.datosPersonalesFinalidad = trpwDpfina;
    }

    public String getDatosPersonalesDestinatario() {
        return datosPersonalesDestinatario;
    }

    public void setDatosPersonalesDestinatario(String trpwDpdest) {
        this.datosPersonalesDestinatario = trpwDpdest;
    }

    public Integer getDocumentoLOPD() {
        return documentoLOPD;
    }

    public void setDocumentoLOPD(Integer trpwDpdoc) {
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

}