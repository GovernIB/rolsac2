package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoTramiteTraduccion;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "procedimiento-tram-sequence", sequenceName = "RS2_PRCTRM_SEQ", allocationSize = 1)
@Table(name = "RS2_PRCTRM", indexes = {@Index(name = "RS2_PRCTRM_PK_I", columnList = "PRTA_CODIGO")})
@NamedQueries({
        @NamedQuery(name = JProcedimientoTramite.FIND_BY_ID,
                query = "select p from JProcedimientoTramite p where p.codigo = :id"),
        @NamedQuery(name = JProcedimientoTramite.FIND_BY_PROC_ID,
                query = "select p from JProcedimientoTramite p where p.procedimiento.procedimiento.codigo = :id")
})

public class JProcedimientoTramite {
    public static final String FIND_BY_ID = "ProcedimientoTramite.FIND_BY_ID";
    public static final String FIND_BY_PROC_ID = "ProcedimientoTramite.FIND_BY_PROC_ID";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tram-sequence")
    @Column(name = "PRTA_CODIGO", nullable = false)
    private Long codigo;

    @Column(name = "PRTA_FASE")
    private Integer fase;

    /**
     * Unidad administrativa competente
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_CODUAC", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_TRMPRE", nullable = false)
    private JTipoTramitacion tipoTramitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRTA_LSTDOC")
    private JListaDocumentos listaDocumentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSDO_CODIGO")
    private JListaDocumentos listaModelos;

    @Column(name = "PRTA_TASA", nullable = false)
    private Boolean tasaAsociada = false;

    @Column(name = "PRTA_FECPUB")
    private Date fechaPublicacion;

    @Column(name = "PRTA_FECINI")
    private Date fechaInicio;

    @Column(name = "PRTA_FECCIE")
    private Date fechaCierre;

    /**
     * Tramitación presencial
     */
    @Column(name = "PRTA_TRPRES", nullable = false, precision = 1, scale = 0)
    private boolean tramitPresencial;

    /**
     * Tramitación electrónica
     */
    @Column(name = "PRTA_TRELEC", nullable = false, precision = 1, scale = 0)
    private boolean tramitElectronica;

    /**
     * Tramitacion telefonica
     */
    @Column(name = "PRTA_TRTEL", nullable = false, precision = 1, scale = 0)
    private boolean tramitTelefonica;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "procedimientoTramite", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JProcedimientoTramiteTraduccion> traducciones;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa prtaCoduac) {
        this.unidadAdministrativa = prtaCoduac;
    }

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow prtaCodprwf) {
        this.procedimiento = prtaCodprwf;
    }

    public JTipoTramitacion getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(JTipoTramitacion prtaTrmpre) {
        this.tipoTramitacion = prtaTrmpre;
    }

    public JListaDocumentos getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(JListaDocumentos prtaLstdoc) {
        this.listaDocumentos = prtaLstdoc;
    }

    public JListaDocumentos getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(JListaDocumentos lsdoCodigo) {
        this.listaModelos = lsdoCodigo;
    }

    public Boolean getTasaAsociada() {
        return tasaAsociada;
    }

    public void setTasaAsociada(Boolean prtaTasa) {
        this.tasaAsociada = prtaTasa;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public List<JProcedimientoTramiteTraduccion> getTraducciones() {
        return traducciones;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }

    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public void setTraducciones(List<JProcedimientoTramiteTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    public void merge(ProcedimientoTramiteDTO elemento, JTipoTramitacion jTipoTramitacion) {
        this.setTramitPresencial(elemento.isTramitPresencial());
        this.setTramitElectronica(elemento.isTramitElectronica());
        this.setTramitTelefonica(elemento.isTramitTelefonica());
        this.setFechaCierre(elemento.getFechaCierre());
        this.setFechaInicio(elemento.getFechaInicio());
        this.setFechaPublicacion(elemento.getFechaPublicacion());
        this.setTasaAsociada(elemento.getTasaAsociada());
        this.setTipoTramitacion(jTipoTramitacion);
        this.setFase(elemento.getFase());
        if (this.getTraducciones() == null || this.getTraducciones().isEmpty()) {
            List<JProcedimientoTramiteTraduccion> traduccionesNew = new ArrayList<>();
            List<String> idiomasPermitidos = List.of(elemento.getUnidadAdministrativa().getEntidad().getIdiomasPermitidos().split(";"));
            for (String idioma : idiomasPermitidos) {
                JProcedimientoTramiteTraduccion trad = new JProcedimientoTramiteTraduccion();
                trad.setIdioma(idioma);
                trad.setProcedimientoTramite(this);
                traduccionesNew.add(trad);
            }
            this.setTraducciones(traduccionesNew);
        }

        for (JProcedimientoTramiteTraduccion traduccion : this.getTraducciones()) {
            if (elemento.getNombre() != null) {
                traduccion.setNombre(elemento.getNombre().getTraduccion(traduccion.getIdioma()));
            }
            if (elemento.getDocumentacion() != null) {
                traduccion.setDocumentacion(elemento.getDocumentacion().getTraduccion(traduccion.getIdioma()));
            }
            if (elemento.getObservacion() != null) {
                traduccion.setObservacion(elemento.getObservacion().getTraduccion(traduccion.getIdioma()));
            }
            if (elemento.getTerminoMaximo() != null) {
                traduccion.setTerminoMaximo(elemento.getTerminoMaximo().getTraduccion(traduccion.getIdioma()));
            }
            if (elemento.getRequisitos() != null) {
                traduccion.setRequisitos(elemento.getRequisitos().getTraduccion(traduccion.getIdioma()));
            }
        }
    }
}
