package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoTramiteTraduccion;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La clase J procedimiento tramite.
 */
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
    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "ProcedimientoTramite.FIND_BY_ID";
    /**
     * La consulta FIND_BY_PROC_ID.
     */
    public static final String FIND_BY_PROC_ID = "ProcedimientoTramite.FIND_BY_PROC_ID";

    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tram-sequence")
    @Column(name = "PRTA_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Fase
     */
    @Column(name = "PRTA_FASE")
    private Integer fase;

    /**
     * Fase
     */
    @Column(name = "PRTA_ORDEN")
    private Integer orden;

    /**
     * Unidad administrativa competente
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_CODUAC", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Procedimiento
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    /**
     * Tipo tramitacion
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_TRMPRE")
    private JTipoTramitacion tipoTramitacionPlantilla;

    /**
     * Tipo tramitacion
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_TRMTRM")
    private JTipoTramitacion tipoTramitacion;

    /**
     * Lista de documentos
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRTA_LSTDOC")
    private JListaDocumentos listaDocumentos;

    /**
     * Lista de modelos
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSDO_CODIGO")
    private JListaDocumentos listaModelos;

    /**
     * Tasa asociada
     */
    @Column(name = "PRTA_TASA", nullable = false)
    private Boolean tasaAsociada = false;

    /**
     * Fecha de publicacion
     */
    @Column(name = "PRTA_FECPUB")
    private Date fechaPublicacion;

    /**
     * Fecha de inicio
     */
    @Column(name = "PRTA_FECINI")
    private Date fechaInicio;

    /**
     * Fecha de cierre
     */
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return unidad administrativa
     */
    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param prtaCoduac prta coduac
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa prtaCoduac) {
        this.unidadAdministrativa = prtaCoduac;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return procedimiento
     */
    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param prtaCodprwf prta codprwf
     */
    public void setProcedimiento(JProcedimientoWorkflow prtaCodprwf) {
        this.procedimiento = prtaCodprwf;
    }

    /**
     * Obtiene tipo tramitacion.
     *
     * @return tipo tramitacion
     */
    public JTipoTramitacion getTipoTramitacionPlantilla() {
        return tipoTramitacionPlantilla;
    }

    /**
     * Establece tipo tramitacion.
     *
     * @param prtaTrmpre prta trmpre
     */
    public void setTipoTramitacionPlantilla(JTipoTramitacion prtaTrmpre) {
        this.tipoTramitacionPlantilla = prtaTrmpre;
    }

    public JTipoTramitacion getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(JTipoTramitacion tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    /**
     * Obtiene lista documentos.
     *
     * @return lista documentos
     */
    public JListaDocumentos getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * Establece lista documentos.
     *
     * @param prtaLstdoc prta lstdoc
     */
    public void setListaDocumentos(JListaDocumentos prtaLstdoc) {
        this.listaDocumentos = prtaLstdoc;
    }

    /**
     * Obtiene lista modelos.
     *
     * @return lista modelos
     */
    public JListaDocumentos getListaModelos() {
        return listaModelos;
    }

    /**
     * Establece lista modelos.
     *
     * @param lsdoCodigo lsdo codigo
     */
    public void setListaModelos(JListaDocumentos lsdoCodigo) {
        this.listaModelos = lsdoCodigo;
    }

    /**
     * Obtiene tasa asociada.
     *
     * @return tasa asociada
     */
    public Boolean getTasaAsociada() {
        return tasaAsociada;
    }

    /**
     * Establece tasa asociada.
     *
     * @param prtaTasa prta tasa
     */
    public void setTasaAsociada(Boolean prtaTasa) {
        this.tasaAsociada = prtaTasa;
    }

    /**
     * Obtiene fecha publicacion.
     *
     * @return fecha publicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece fecha publicacion.
     *
     * @param fechaPublicacion fecha publicacion
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene fecha inicio.
     *
     * @return fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece fecha inicio.
     *
     * @param fechaInicio fecha inicio
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene fecha cierre.
     *
     * @return fecha cierre
     */
    public Date getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Establece fecha cierre.
     *
     * @param fechaCierre fecha cierre
     */
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * Obtiene traducciones.
     *
     * @return traducciones
     */
    public List<JProcedimientoTramiteTraduccion> getTraducciones() {
        return traducciones;
    }

    /**
     * Obtiene fase.
     *
     * @return fase
     */
    public Integer getFase() {
        return fase;
    }

    /**
     * Establece fase.
     *
     * @param fase fase
     */
    public void setFase(Integer fase) {
        this.fase = fase;
    }

    /**
     * Is tramit presencial boolean.
     *
     * @return boolean
     */
    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    /**
     * Establece tramit presencial.
     *
     * @param tramitPresencial tramit presencial
     */
    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    /**
     * Is tramit electronica boolean.
     *
     * @return boolean
     */
    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    /**
     * Establece tramit electronica.
     *
     * @param tramitElectronica tramit electronica
     */
    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    /**
     * Is tramit telefonica boolean.
     *
     * @return boolean
     */
    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    /**
     * Establece tramit telefonica.
     *
     * @param tramitTelefonica tramit telefonica
     */
    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    /**
     * Establece traducciones.
     *
     * @param traducciones traducciones
     */
    public void setTraducciones(List<JProcedimientoTramiteTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    /**
     * Merge.
     *
     * @param elemento         elemento
     * @param jTipoTramitacion j tipo tramitacion
     */
    public void merge(ProcedimientoTramiteDTO elemento, JTipoTramitacion jTipoTramitacionPlantilla, JTipoTramitacion jTipoTramitacion) {
        this.setOrden(elemento.getOrden());
        this.setTramitPresencial(elemento.isTramitPresencial());
        this.setTramitElectronica(elemento.isTramitElectronica());
        this.setTramitTelefonica(elemento.isTramitTelefonica());
        this.setFechaCierre(elemento.getFechaCierre());
        this.setFechaInicio(elemento.getFechaInicio());
        this.setFechaPublicacion(elemento.getFechaPublicacion());
        this.setTasaAsociada(elemento.getTasaAsociada());
        this.setTipoTramitacion(jTipoTramitacion);
        this.setTipoTramitacionPlantilla(jTipoTramitacionPlantilla);
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
