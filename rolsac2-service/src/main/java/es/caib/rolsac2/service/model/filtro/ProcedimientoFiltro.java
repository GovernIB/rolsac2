package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtro de procedimientos.
 */
public class ProcedimientoFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en el viewProcedimientos
     **/
    private String texto;
    private String tipo;
    private Integer codigoSIA;
    private Long codigoProc;
    private List<Long> codigosProc;

    private Long codigoWF;
    private Long codigoTram;
    private String estadoSIA;
    private String siaFecha;
    private String fechaPublicacionDesde;
    private String fechaPublicacionHasta;
    private String codigoDir3SIA;
    private String codigoUaDir3;

    private String volcadoSIA;

    private TipoSilencioAdministrativoDTO silencioAdministrativo;

    private TipoProcedimientoDTO tipoProcedimiento;

    private TipoFormaInicioDTO formaInicio;

    private TipoPublicoObjetivoDTO publicoObjetivo;

    private List<TipoPublicoObjetivoEntidadGridDTO> publicoObjetivos;
    private List<TipoMateriaSIAGridDTO> materias;
    private List<NormativaGridDTO> normativas;
    private String estado;
    private List<String> estados;
    private boolean hijasActivas = false;
    private List<Long> idUAsHijas;

    /**
     * El UAs Responsable es para procedimientos
     **/
    private Long idUAResponsable;
    private List<Long> idUAsResponsable;
    /**
     * El UAs Instructor es para servicios
     **/
    private Long idUAInstructor;

    private List<Long> idUAInstructorOComun;
    private List<Long> idUAsInstructor;

    private List<Long> idsUAsHijasAux;
    private boolean todasUnidadesOrganicas = false;

    private TipoViaDTO finVia;
    private String tramiteVigente;
    private String tramiteTelematico;
    private Boolean telematico;
    private TipoTramitacionDTO plantilla;
    private PlatTramitElectronicaDTO plataforma;
    private String comun;
    private String estadoWF;

    private String mensajesPendiente;

    private List<TemaGridDTO> temas;

    private Boolean esProcedimiento;

    private List<String> canales;

    private String idTramite;

    private String identificadorPlataforma;

    private Integer version;

    private String visibleSEDE;

    private Boolean integrarPdu;

    private Boolean integradoPdu;

    private String tramitacionPersonaApoderada;

    private String disponibleFuncionarioHabilitado;


    //PROCEDIMIENTOS RESTAPI
    private Long codigo;
    private String nombre;
    private String inicioFechaActualizacion;
    private String finFechaActualizacion;
    private String inicioFechaCaducidad;
    private String finFechaCaducidad;
    private String observaciones;
    private String requisitos;
    private String inicioFechaSIA;
    private String finFechaSIA;
    private Long codigoUAResponsable;
    private String nombreUAResponsable;
    private Long codigoUACompetente;
    private String nombreUACompetente;
    private Long codigoUAInstructora;
    private String nombreUAInstructora;
    private Long codigoLOPDResponsable;
    private String nombreLOPDResponsable;
    private Long codigoLOPDLegitimacion;
    private String nombreLOPDLegitimacion;
    private String finalidadLOPD;
    private Long codigoLOPDDestinatario;
    private String nombreLOPDDestinatario;
    private String derechosLOPD;
    private String objeto;
    private Long codigoTipoProcedimiento;
    private String nombreTipoProcedimiento;
    private Long codigoIniciacion;
    private String nombreIniciacion;
    private Long codigoSilencioAdministrativo;
    private String nombreSilencioAdministrativo;
    private Long codigoTipoTramite;
    private String nombreTipoTramite;
    private Long codigoTipoVia;
    private String nombreTipoVia;
    private Boolean habilitadoApoderado;
    private Boolean habilitadoFuncionario;
    private Boolean tieneTasa;
    private String terminoResolucion;
    private Long codigoPublicoObjetivo;
    private String nombrePublicoObjetivo;
    private Long codigoNormativa;
    private String nombreNormativa;
    private Long codigoTema;
    private String nombreTema;
    private Long codigoDocumento;
    private String tituloDocumento;
    private Long codigoTramite;
    private String nombreTramite;
    private Integer faseTramite;
    private String inicioFechaInicioTramite;
    private String finFechaInicioTramite;
    private String inicioFechaCierreTramite;
    private String finFechaCierreTramite;
    private Boolean tramitePresencial;
    private Boolean tramiteTelefonico;
    private Boolean tramiteElectronico;

    //Servicios RESTAPI
    private String inicioFechaActualitzacion;
    private String finFechaActualitzacion;
    private Long uaResponsableCodigo;
    private String uaResponsableNombre;
    private Long uaInstructorCodigo;
    private String uaInstructorNombre;

    private Long lopdResponsableCodigo;
    private String lopdResponsableNombre;
    private String lopdFinalidad;
    private Long lopdDestinatarioCodigo;
    private String lopdDestinatarioNombre;
    private String lopdCabecera;
    private String lopdDerechos;
    private String responsableEmail;
    private String responsableTelefono;
    private Boolean activoLopd;
    private String incidenciasEmail;
    private Long tipoTramitacionCodigo;
    private String tipoTramitacionNombre;
    private Boolean tramitacionPresencial;
    private Boolean tramitacionTelefonica;
    private Boolean tramitacionElectronica;
    private Long plataformaTramitacionCodigo;
    private String plataformaTramitacionNombre;
    private Long plantillaTramitacionCodigo;
    private String plantillaTramitacionNombre;
    private Long publicoObjetivoCodigo;
    private String publicoObjetivoNombre;
    private Long normativaCodigo;
    private String normativaNombre;
    private Long temaCodigo;
    private String temaNombre;
    private Long documentoCodigo;
    private String documentoTitulo;
    /**
     * Este parametro es solo del REST
     **/
    private Boolean buscarEnDescendientesUA;

    /**
     * Constructor vacio
     */
    public ProcedimientoFiltro() {
        //Vacio
    }

    /**
     * Constructor
     *
     * @param otro
     */
    public ProcedimientoFiltro(ProcedimientoFiltro otro) {
        this.texto = otro.texto;
        this.tipo = otro.tipo;
        this.codigoSIA = otro.codigoSIA;
        this.codigoProc = otro.codigoProc;
        this.codigosProc = otro.codigosProc;
        this.codigoWF = otro.codigoWF;
        this.codigoTram = otro.codigoTram;
        this.estadoSIA = otro.estadoSIA;
        this.siaFecha = otro.siaFecha;
        this.fechaPublicacionDesde = otro.fechaPublicacionDesde;
        this.fechaPublicacionHasta = otro.fechaPublicacionHasta;
        this.codigoDir3SIA = otro.codigoDir3SIA;
        this.codigoUaDir3 = otro.codigoUaDir3;
        this.volcadoSIA = otro.volcadoSIA;
        this.silencioAdministrativo = otro.silencioAdministrativo;
        this.tipoProcedimiento = otro.tipoProcedimiento;
        this.formaInicio = otro.formaInicio;
        this.publicoObjetivo = otro.publicoObjetivo;
        this.publicoObjetivos = otro.publicoObjetivos;
        this.materias = otro.materias;
        this.normativas = otro.normativas;
        this.estado = otro.estado;
        this.estados = otro.estados;
        this.hijasActivas = otro.hijasActivas;
        this.idUAsHijas = otro.idUAsHijas;
        this.idUAResponsable = otro.idUAResponsable;
        this.idUAsResponsable = otro.idUAsResponsable;
        this.idUAInstructor = otro.idUAInstructor;
        this.idUAsInstructor = otro.idUAsInstructor;
        this.idsUAsHijasAux = otro.idsUAsHijasAux;
        this.todasUnidadesOrganicas = otro.todasUnidadesOrganicas;
        this.finVia = otro.finVia;
        this.tramiteVigente = otro.tramiteVigente;
        this.tramiteTelematico = otro.tramiteTelematico;
        this.telematico = otro.getTelematico();
        this.plantilla = otro.plantilla;
        this.plataforma = otro.plataforma;
        this.comun = otro.comun;
        this.estadoWF = otro.estadoWF;
        this.mensajesPendiente = otro.mensajesPendiente;
        this.temas = otro.temas;
        this.esProcedimiento = otro.esProcedimiento;
        this.canales = otro.canales;
        this.idTramite = otro.idTramite;
        this.identificadorPlataforma = otro.identificadorPlataforma;
        this.version = otro.version;
        this.setIdioma(otro.getIdioma());
        this.setIdUA(otro.getIdUA());
        this.setIdEntidad(otro.getIdEntidad());
        this.setPaginaTamanyo(otro.getPaginaTamanyo());
        this.setPaginaFirst(otro.getPaginaFirst());
        this.setOrderBy(otro.getOrderBy());
        this.setOrder(otro.getOrder());
        this.setAscendente(otro.isAscendente());
        this.setOperadoresString(otro.isOperadoresString());
        this.setPaginacionActiva(otro.isPaginacionActiva());
        this.setTotal(otro.getTotal());
        this.visibleSEDE = otro.getVisibleSEDE();
        this.codigo = otro.codigo;
        this.nombre = otro.nombre;

        this.inicioFechaActualizacion = otro.inicioFechaActualizacion;
        this.finFechaActualizacion = otro.finFechaActualizacion;
        this.inicioFechaCaducidad = otro.inicioFechaCaducidad;
        this.finFechaCaducidad = otro.finFechaCaducidad;
        this.observaciones = otro.observaciones;
        this.requisitos = otro.requisitos;
        this.inicioFechaSIA = otro.inicioFechaSIA;
        this.finFechaSIA = otro.finFechaSIA;
        this.codigoUAResponsable = otro.codigoUAResponsable;
        this.nombreUAResponsable = otro.nombreUAResponsable;
        this.codigoUACompetente = otro.codigoUACompetente;
        this.nombreUACompetente = otro.nombreUACompetente;
        this.codigoUAInstructora = otro.codigoUAInstructora;
        this.nombreUAInstructora = otro.nombreUAInstructora;
        this.codigoLOPDResponsable = otro.codigoLOPDResponsable;
        this.nombreLOPDResponsable = otro.nombreLOPDResponsable;
        this.codigoLOPDLegitimacion = otro.codigoLOPDLegitimacion;
        this.nombreLOPDLegitimacion = otro.nombreLOPDLegitimacion;
        this.finalidadLOPD = otro.finalidadLOPD;
        this.codigoLOPDDestinatario = otro.codigoLOPDDestinatario;
        this.nombreLOPDDestinatario = otro.nombreLOPDDestinatario;
        this.derechosLOPD = otro.derechosLOPD;
        this.objeto = otro.objeto;
        this.codigoTipoProcedimiento = otro.codigoTipoProcedimiento;
        this.nombreTipoProcedimiento = otro.nombreTipoProcedimiento;
        this.codigoIniciacion = otro.codigoIniciacion;
        this.nombreIniciacion = otro.nombreIniciacion;
        this.codigoSilencioAdministrativo = otro.codigoSilencioAdministrativo;
        this.nombreSilencioAdministrativo = otro.nombreSilencioAdministrativo;
        this.codigoTipoTramite = otro.codigoTipoTramite;
        this.nombreTipoTramite = otro.nombreTipoTramite;
        this.codigoTipoVia = otro.codigoTipoVia;
        this.nombreTipoVia = otro.nombreTipoVia;
        this.habilitadoApoderado = otro.habilitadoApoderado;
        this.habilitadoFuncionario = otro.habilitadoFuncionario;
        this.tieneTasa = otro.tieneTasa;
        this.terminoResolucion = otro.terminoResolucion;
        this.codigoPublicoObjetivo = otro.codigoPublicoObjetivo;
        this.nombrePublicoObjetivo = otro.nombrePublicoObjetivo;
        this.codigoNormativa = otro.codigoNormativa;
        this.nombreNormativa = otro.nombreNormativa;
        this.codigoTema = otro.codigoTema;
        this.nombreTema = otro.nombreTema;
        this.codigoDocumento = otro.codigoDocumento;
        this.tituloDocumento = otro.tituloDocumento;
        this.codigoTramite = otro.codigoTramite;
        this.nombreTramite = otro.nombreTramite;
        this.faseTramite = otro.faseTramite;
        this.inicioFechaInicioTramite = otro.inicioFechaInicioTramite;
        this.finFechaInicioTramite = otro.finFechaInicioTramite;
        this.inicioFechaCierreTramite = otro.inicioFechaCierreTramite;
        this.finFechaCierreTramite = otro.finFechaCierreTramite;
        this.tramitePresencial = otro.tramitePresencial;
        this.tramiteTelefonico = otro.tramiteTelefonico;
        this.tramiteElectronico = otro.tramiteElectronico;

        this.inicioFechaActualitzacion = otro.inicioFechaActualitzacion;
        this.finFechaActualitzacion = otro.finFechaActualitzacion;
        this.uaResponsableCodigo = otro.uaResponsableCodigo;
        this.uaResponsableNombre = otro.uaResponsableNombre;
        this.uaInstructorCodigo = otro.uaInstructorCodigo;
        this.uaInstructorNombre = otro.uaInstructorNombre;
        this.lopdResponsableCodigo = otro.lopdResponsableCodigo;
        this.lopdResponsableNombre = otro.lopdResponsableNombre;
        this.lopdFinalidad = otro.lopdFinalidad;
        this.lopdDestinatarioCodigo = otro.lopdDestinatarioCodigo;
        this.lopdDestinatarioNombre = otro.lopdDestinatarioNombre;
        this.lopdCabecera = otro.lopdCabecera;
        this.lopdDerechos = otro.lopdDerechos;
        this.responsableEmail = otro.responsableEmail;
        this.responsableTelefono = otro.responsableTelefono;
        this.activoLopd = otro.activoLopd;
        this.incidenciasEmail = otro.incidenciasEmail;
        this.tipoTramitacionCodigo = otro.tipoTramitacionCodigo;
        this.tipoTramitacionNombre = otro.tipoTramitacionNombre;
        this.tramitacionPresencial = otro.tramitacionPresencial;
        this.tramitacionTelefonica = otro.tramitacionTelefonica;
        this.tramitacionElectronica = otro.tramitacionElectronica;
        this.plataformaTramitacionCodigo = otro.plataformaTramitacionCodigo;
        this.plataformaTramitacionNombre = otro.plataformaTramitacionNombre;
        this.plantillaTramitacionCodigo = otro.plantillaTramitacionCodigo;
        this.plantillaTramitacionNombre = otro.plantillaTramitacionNombre;
        this.publicoObjetivoCodigo = otro.publicoObjetivoCodigo;
        this.publicoObjetivoNombre = otro.publicoObjetivoNombre;
        this.normativaCodigo = otro.normativaCodigo;
        this.normativaNombre = otro.normativaNombre;
        this.temaCodigo = otro.temaCodigo;
        this.temaNombre = otro.temaNombre;
        this.documentoCodigo = otro.documentoCodigo;
        this.documentoTitulo = otro.documentoTitulo;

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public Long getCodigoProc() {
        return codigoProc;
    }

    public void setCodigoProc(Long codigoProc) {
        this.codigoProc = codigoProc;
    }

    public List<Long> getCodigosProc() {
        return codigosProc;
    }

    public void setCodigosProc(List<Long> codigosProc) {
        this.codigosProc = codigosProc;
    }

    public Long getCodigoWF() {
        return codigoWF;
    }

    public void setCodigoWF(Long codigoWF) {
        this.codigoWF = codigoWF;
    }

    public Long getCodigoTram() {
        return codigoTram;
    }

    public void setCodigoTram(Long codigoTram) {
        this.codigoTram = codigoTram;
    }

    public String getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public String getSiaFecha() {
        return siaFecha;
    }

    public void setSiaFecha(String siaFecha) {
        this.siaFecha = siaFecha;
    }

    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    public void setCodigoDir3SIA(String codigoDir3SIA) {
        this.codigoDir3SIA = codigoDir3SIA;
    }

    public String getVolcadoSIA() {
        return volcadoSIA;
    }

    public void setVolcadoSIA(String volcadoSIA) {
        this.volcadoSIA = volcadoSIA;
    }

    public TipoSilencioAdministrativoDTO getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    public void setSilencioAdministrativo(TipoSilencioAdministrativoDTO silencioAdministrativo) {
        this.silencioAdministrativo = silencioAdministrativo;
    }

    public TipoProcedimientoDTO getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimientoDTO tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public TipoFormaInicioDTO getFormaInicio() {
        return formaInicio;
    }

    public void setFormaInicio(TipoFormaInicioDTO formaInicio) {
        this.formaInicio = formaInicio;
    }

    public TipoPublicoObjetivoDTO getPublicoObjetivo() {
        return publicoObjetivo;
    }

    public void setPublicoObjetivo(TipoPublicoObjetivoDTO publicoObjetivo) {
        this.publicoObjetivo = publicoObjetivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public String getEstadoWF() {
        return estadoWF;
    }

    public void setEstadoWF(String estadoWF) {
        this.estadoWF = estadoWF;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicioFechaActualizacion() {
        return inicioFechaActualizacion;
    }

    public void setInicioFechaActualizacion(String inicioFechaActualizacion) {
        this.inicioFechaActualizacion = inicioFechaActualizacion;
    }

    public String getFinFechaActualizacion() {
        return finFechaActualizacion;
    }

    public void setFinFechaActualizacion(String finFechaActualizacion) {
        this.finFechaActualizacion = finFechaActualizacion;
    }

    public String getInicioFechaCaducidad() {
        return inicioFechaCaducidad;
    }

    public void setInicioFechaCaducidad(String inicioFechaCaducidad) {
        this.inicioFechaCaducidad = inicioFechaCaducidad;
    }

    public String getFinFechaCaducidad() {
        return finFechaCaducidad;
    }

    public void setFinFechaCaducidad(String finFechaCaducidad) {
        this.finFechaCaducidad = finFechaCaducidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getInicioFechaSIA() {
        return inicioFechaSIA;
    }

    public void setInicioFechaSIA(String inicioFechaSIA) {
        this.inicioFechaSIA = inicioFechaSIA;
    }

    public String getFinFechaSIA() {
        return finFechaSIA;
    }

    public void setFinFechaSIA(String finFechaSIA) {
        this.finFechaSIA = finFechaSIA;
    }

    public Long getCodigoUAResponsable() {
        return codigoUAResponsable;
    }

    public void setCodigoUAResponsable(Long codigoUAResponsable) {
        this.codigoUAResponsable = codigoUAResponsable;
    }

    public String getNombreUAResponsable() {
        return nombreUAResponsable;
    }

    public void setNombreUAResponsable(String nombreUAResponsable) {
        this.nombreUAResponsable = nombreUAResponsable;
    }

    public Long getCodigoUACompetente() {
        return codigoUACompetente;
    }

    public void setCodigoUACompetente(Long codigoUACompetente) {
        this.codigoUACompetente = codigoUACompetente;
    }

    public String getNombreUACompetente() {
        return nombreUACompetente;
    }

    public void setNombreUACompetente(String nombreUACompetente) {
        this.nombreUACompetente = nombreUACompetente;
    }

    public Long getCodigoUAInstructora() {
        return codigoUAInstructora;
    }

    public void setCodigoUAInstructora(Long codigoUAInstructora) {
        this.codigoUAInstructora = codigoUAInstructora;
    }

    public String getNombreUAInstructora() {
        return nombreUAInstructora;
    }

    public void setNombreUAInstructora(String nombreUAInstructora) {
        this.nombreUAInstructora = nombreUAInstructora;
    }

    public Long getCodigoLOPDResponsable() {
        return codigoLOPDResponsable;
    }

    public void setCodigoLOPDResponsable(Long codigoLOPDResponsable) {
        this.codigoLOPDResponsable = codigoLOPDResponsable;
    }

    public String getNombreLOPDResponsable() {
        return nombreLOPDResponsable;
    }

    public void setNombreLOPDResponsable(String nombreLOPDResponsable) {
        this.nombreLOPDResponsable = nombreLOPDResponsable;
    }

    public Long getCodigoLOPDLegitimacion() {
        return codigoLOPDLegitimacion;
    }

    public void setCodigoLOPDLegitimacion(Long codigoLOPDLegitimacion) {
        this.codigoLOPDLegitimacion = codigoLOPDLegitimacion;
    }

    public String getNombreLOPDLegitimacion() {
        return nombreLOPDLegitimacion;
    }

    public void setNombreLOPDLegitimacion(String nombreLOPDLegitimacion) {
        this.nombreLOPDLegitimacion = nombreLOPDLegitimacion;
    }

    public String getFinalidadLOPD() {
        return finalidadLOPD;
    }

    public void setFinalidadLOPD(String finalidadLOPD) {
        this.finalidadLOPD = finalidadLOPD;
    }

    public Long getCodigoLOPDDestinatario() {
        return codigoLOPDDestinatario;
    }

    public void setCodigoLOPDDestinatario(Long codigoLOPDDestinatario) {
        this.codigoLOPDDestinatario = codigoLOPDDestinatario;
    }

    public String getNombreLOPDDestinatario() {
        return nombreLOPDDestinatario;
    }

    public void setNombreLOPDDestinatario(String nombreLOPDDestinatario) {
        this.nombreLOPDDestinatario = nombreLOPDDestinatario;
    }

    public String getDerechosLOPD() {
        return derechosLOPD;
    }

    public void setDerechosLOPD(String derechosLOPD) {
        this.derechosLOPD = derechosLOPD;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Long getCodigoTipoProcedimiento() {
        return codigoTipoProcedimiento;
    }

    public void setCodigoTipoProcedimiento(Long codigoTipoProcedimiento) {
        this.codigoTipoProcedimiento = codigoTipoProcedimiento;
    }

    public String getNombreTipoProcedimiento() {
        return nombreTipoProcedimiento;
    }

    public void setNombreTipoProcedimiento(String nombreTipoProcedimiento) {
        this.nombreTipoProcedimiento = nombreTipoProcedimiento;
    }

    public Long getCodigoIniciacion() {
        return codigoIniciacion;
    }

    public void setCodigoIniciacion(Long codigoIniciacion) {
        this.codigoIniciacion = codigoIniciacion;
    }

    public String getNombreIniciacion() {
        return nombreIniciacion;
    }

    public void setNombreIniciacion(String nombreIniciacion) {
        this.nombreIniciacion = nombreIniciacion;
    }

    public Long getCodigoSilencioAdministrativo() {
        return codigoSilencioAdministrativo;
    }

    public void setCodigoSilencioAdministrativo(Long codigoSilencioAdministrativo) {
        this.codigoSilencioAdministrativo = codigoSilencioAdministrativo;
    }

    public String getNombreSilencioAdministrativo() {
        return nombreSilencioAdministrativo;
    }

    public void setNombreSilencioAdministrativo(String nombreSilencioAdministrativo) {
        this.nombreSilencioAdministrativo = nombreSilencioAdministrativo;
    }

    public Long getCodigoTipoTramite() {
        return codigoTipoTramite;
    }

    public void setCodigoTipoTramite(Long codigoTipoTramite) {
        this.codigoTipoTramite = codigoTipoTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public Long getCodigoTipoVia() {
        return codigoTipoVia;
    }

    public void setCodigoTipoVia(Long codigoTipoVia) {
        this.codigoTipoVia = codigoTipoVia;
    }

    public String getNombreTipoVia() {
        return nombreTipoVia;
    }

    public void setNombreTipoVia(String nombreTipoVia) {
        this.nombreTipoVia = nombreTipoVia;
    }

    public Boolean getHabilitadoApoderado() {
        return habilitadoApoderado;
    }

    public void setHabilitadoApoderado(Boolean habilitadoApoderado) {
        this.habilitadoApoderado = habilitadoApoderado;
    }

    public Boolean getHabilitadoFuncionario() {
        return habilitadoFuncionario;
    }

    public void setHabilitadoFuncionario(Boolean habilitadoFuncionario) {
        this.habilitadoFuncionario = habilitadoFuncionario;
    }

    public Boolean getTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(Boolean tieneTasa) {
        this.tieneTasa = tieneTasa;
    }

    public String getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(String terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    public Long getCodigoPublicoObjetivo() {
        return codigoPublicoObjetivo;
    }

    public void setCodigoPublicoObjetivo(Long codigoPublicoObjetivo) {
        this.codigoPublicoObjetivo = codigoPublicoObjetivo;
    }

    public String getNombrePublicoObjetivo() {
        return nombrePublicoObjetivo;
    }

    public void setNombrePublicoObjetivo(String nombrePublicoObjetivo) {
        this.nombrePublicoObjetivo = nombrePublicoObjetivo;
    }

    public Long getCodigoNormativa() {
        return codigoNormativa;
    }

    public void setCodigoNormativa(Long codigoNormativa) {
        this.codigoNormativa = codigoNormativa;
    }

    public String getNombreNormativa() {
        return nombreNormativa;
    }

    public void setNombreNormativa(String nombreNormativa) {
        this.nombreNormativa = nombreNormativa;
    }

    public Long getCodigoTema() {
        return codigoTema;
    }

    public void setCodigoTema(Long codigoTema) {
        this.codigoTema = codigoTema;
    }

    public String getNombreTema() {
        return nombreTema;
    }

    public void setNombreTema(String nombreTema) {
        this.nombreTema = nombreTema;
    }

    public Long getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Long codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getTituloDocumento() {
        return tituloDocumento;
    }

    public void setTituloDocumento(String tituloDocumento) {
        this.tituloDocumento = tituloDocumento;
    }

    public Long getCodigoTramite() {
        return codigoTramite;
    }

    public void setCodigoTramite(Long codigoTramite) {
        this.codigoTramite = codigoTramite;
    }

    public String getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public Integer getFaseTramite() {
        return faseTramite;
    }

    public void setFaseTramite(Integer faseTramite) {
        this.faseTramite = faseTramite;
    }

    public String getInicioFechaInicioTramite() {
        return inicioFechaInicioTramite;
    }

    public void setInicioFechaInicioTramite(String inicioFechaInicioTramite) {
        this.inicioFechaInicioTramite = inicioFechaInicioTramite;
    }

    public String getFinFechaInicioTramite() {
        return finFechaInicioTramite;
    }

    public void setFinFechaInicioTramite(String finFechaInicioTramite) {
        this.finFechaInicioTramite = finFechaInicioTramite;
    }

    public String getInicioFechaCierreTramite() {
        return inicioFechaCierreTramite;
    }

    public void setInicioFechaCierreTramite(String inicioFechaCierreTramite) {
        this.inicioFechaCierreTramite = inicioFechaCierreTramite;
    }

    public String getFinFechaCierreTramite() {
        return finFechaCierreTramite;
    }

    public void setFinFechaCierreTramite(String finFechaCierreTramite) {
        this.finFechaCierreTramite = finFechaCierreTramite;
    }

    public Boolean getTramitePresencial() {
        return tramitePresencial;
    }

    public void setTramitePresencial(Boolean tramitePresencial) {
        this.tramitePresencial = tramitePresencial;
    }

    public Boolean getTramiteTelefonico() {
        return tramiteTelefonico;
    }

    public void setTramiteTelefonico(Boolean tramiteTelefonico) {
        this.tramiteTelefonico = tramiteTelefonico;
    }

    public Boolean getTramiteElectronico() {
        return tramiteElectronico;
    }

    public void setTramiteElectronico(Boolean tramiteElectronico) {
        this.tramiteElectronico = tramiteElectronico;
    }

    public List<TipoPublicoObjetivoEntidadGridDTO> getPublicoObjetivos() {
        return publicoObjetivos;
    }

    public List<Long> getPublicoObjetivosId() {
        List<Long> idPublicos = new ArrayList<>();
        for (TipoPublicoObjetivoEntidadGridDTO pub : publicoObjetivos) {
            idPublicos.add(pub.getCodigo());
        }
        return idPublicos;
    }

    public String getPublicoObjetivos(String idioma) {
        if (publicoObjetivos == null || publicoObjetivos.isEmpty()) {
            return "";
        } else {
            StringBuilder texto = new StringBuilder();
            for (TipoPublicoObjetivoEntidadGridDTO tipo : publicoObjetivos) {
                texto.append(tipo.getDescripcion().getTraduccion(idioma) + ",");
            }
            return texto.toString().substring(0, texto.toString().length() - 1);
        }
    }

    public void setPublicoObjetivos(List<TipoPublicoObjetivoEntidadGridDTO> publicoObjetivos) {
        this.publicoObjetivos = publicoObjetivos;
    }

    public List<TipoMateriaSIAGridDTO> getMaterias() {
        return materias;
    }

    public List<Long> getMateriasId() {
        List<Long> idMaterias = new ArrayList<>();
        for (TipoMateriaSIAGridDTO mat : materias) {
            idMaterias.add(mat.getCodigo());
        }
        return idMaterias;
    }


    public String getMaterias(String idioma) {
        if (materias == null || materias.isEmpty()) {
            return "";
        } else {
            StringBuilder texto = new StringBuilder();
            for (TipoMateriaSIAGridDTO materia : materias) {
                texto.append(materia.getDescripcion().getTraduccion(idioma) + ",");
            }
            return texto.toString().substring(0, texto.toString().length() - 1);
        }
    }

    public List<TemaGridDTO> getTemas() {
        return temas;
    }

    public String getTemas(String idioma) {
        if (temas == null || temas.isEmpty()) {
            return "";
        } else {
            StringBuilder texto = new StringBuilder();
            for (TemaGridDTO tema : temas) {
                texto.append(tema.getDescripcion().getTraduccion(idioma) + ",");
            }
            return texto.toString().substring(0, texto.toString().length() - 1);
        }
    }

    public List<Long> getTemasId() {
        List<Long> idTemas = new ArrayList<>();
        for (TemaGridDTO tema : temas) {
            idTemas.add(tema.getCodigo());
        }
        return idTemas;
    }

    public void setTemas(List<TemaGridDTO> temas) {
        this.temas = temas;
    }

    public void setMaterias(List<TipoMateriaSIAGridDTO> materias) {
        this.materias = materias;
    }

    public List<NormativaGridDTO> getNormativas() {
        return normativas;
    }

    public List<Long> getNormativasId() {
        List<Long> idNormativas = new ArrayList<>();
        for (NormativaGridDTO norm : normativas) {
            idNormativas.add(norm.getCodigo());
        }
        return idNormativas;
    }

    public void setNormativas(List<NormativaGridDTO> normativas) {
        this.normativas = normativas;
    }

    public String getNormativas(String idioma) {
        if (normativas == null || normativas.isEmpty()) {
            return "";
        } else {
            StringBuilder texto = new StringBuilder();
            for (NormativaGridDTO normativa : normativas) {
                texto.append(normativa.getTitulo().getTraduccion(idioma) + ",");
            }
            return texto.toString().substring(0, texto.toString().length() - 1);
        }
    }

    public TipoViaDTO getFinVia() {
        return finVia;
    }

    public void setFinVia(TipoViaDTO finVia) {
        this.finVia = finVia;
    }

    public String getTramiteVigente() {
        return tramiteVigente;
    }

    public void setTramiteVigente(String tramiteVigente) {
        this.tramiteVigente = tramiteVigente;
    }

    public String getTramiteTelematico() {
        return tramiteTelematico;
    }

    public void setTramiteTelematico(String tramiteTelematico) {
        this.tramiteTelematico = tramiteTelematico;
    }

    public void setTelematico(Boolean telematico) {
        this.telematico = telematico;
    }

    public Boolean getTelematico() {
        return telematico;
    }

    public TipoTramitacionDTO getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(TipoTramitacionDTO plantilla) {
        this.plantilla = plantilla;
    }


    public String getVisibleSEDE() {
        return visibleSEDE;
    }

    public void setVisibleSEDE(String visibleSEDE) {
        this.visibleSEDE = visibleSEDE;
    }

    public PlatTramitElectronicaDTO getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlatTramitElectronicaDTO plataforma) {
        this.plataforma = plataforma;
    }

    public String getComun() {
        return comun;
    }

    public void setComun(String comun) {
        this.comun = comun;
    }

    public List<Long> getIdsUAsHijasAux() {
        return idsUAsHijasAux;
    }

    public void setIdsUAsHijasAux(List<Long> idsUAsHijasAux) {
        this.idsUAsHijasAux = idsUAsHijasAux;
    }

    public boolean isHijasActivas() {
        return hijasActivas;
    }

    public void setHijasActivas(boolean hijasActivas) {
        this.hijasActivas = hijasActivas;
    }

    public boolean isTodasUnidadesOrganicas() {
        return todasUnidadesOrganicas;
    }

    public void setTodasUnidadesOrganicas(boolean todasUnidadesOrganicas) {
        this.todasUnidadesOrganicas = todasUnidadesOrganicas;
    }

    public List<Long> getIdUAsHijas() {
        return idUAsHijas;
    }

    public void setIdUAsHijas(List<Long> idUAsHijas) {
        this.idUAsHijas = idUAsHijas;
    }

    public String getMensajesPendiente() {
        return mensajesPendiente;
    }

    public void setMensajesPendiente(String mensajesPendiente) {
        this.mensajesPendiente = mensajesPendiente;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public String getIdentificadorPlataforma() {
        return identificadorPlataforma;
    }

    public void setIdentificadorPlataforma(String identificadorPlataforma) {
        this.identificadorPlataforma = identificadorPlataforma;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getIdUAResponsable() {
        return idUAResponsable;
    }

    public void setIdUAResponsable(Long idUAResponsable) {
        this.idUAResponsable = idUAResponsable;
    }

    public List<Long> getIdUAsResponsable() {
        return idUAsResponsable;
    }

    public void setIdUAsResponsable(List<Long> idUAsResponsable) {
        this.idUAsResponsable = idUAsResponsable;
    }

    public Long getIdUAInstructor() {
        return idUAInstructor;
    }

    public void setIdUAInstructor(Long idUAInstructor) {
        this.idUAInstructor = idUAInstructor;
    }

    public List<Long> getIdUAsInstructor() {
        return idUAsInstructor;
    }

    public void setIdUAsInstructor(List<Long> idUAsInstructor) {
        this.idUAsInstructor = idUAsInstructor;
    }

    public List<Long> getIdUAInstructorOComun() {
        return idUAInstructorOComun;
    }

    public void setIdUAInstructorOComun(List<Long> idUAInstructorOComun) {
        this.idUAInstructorOComun = idUAInstructorOComun;
    }

    public String getInicioFechaActualitzacion() {
        return inicioFechaActualitzacion;
    }

    public void setInicioFechaActualitzacion(String inicioFechaActualitzacion) {
        this.inicioFechaActualitzacion = inicioFechaActualitzacion;
    }

    public String getFinFechaActualitzacion() {
        return finFechaActualitzacion;
    }

    public void setFinFechaActualitzacion(String finFechaActualitzacion) {
        this.finFechaActualitzacion = finFechaActualitzacion;
    }

    public Long getUaResponsableCodigo() {
        return uaResponsableCodigo;
    }

    public void setUaResponsableCodigo(Long uaResponsableCodigo) {
        this.uaResponsableCodigo = uaResponsableCodigo;
    }

    public String getUaResponsableNombre() {
        return uaResponsableNombre;
    }

    public void setUaResponsableNombre(String uaResponsableNombre) {
        this.uaResponsableNombre = uaResponsableNombre;
    }

    public Long getUaInstructorCodigo() {
        return uaInstructorCodigo;
    }

    public void setUaInstructorCodigo(Long uaInstructorCodigo) {
        this.uaInstructorCodigo = uaInstructorCodigo;
    }

    public String getUaInstructorNombre() {
        return uaInstructorNombre;
    }

    public void setUaInstructorNombre(String uaInstructorNombre) {
        this.uaInstructorNombre = uaInstructorNombre;
    }

    public Long getLopdResponsableCodigo() {
        return lopdResponsableCodigo;
    }

    public void setLopdResponsableCodigo(Long lopdResponsableCodigo) {
        this.lopdResponsableCodigo = lopdResponsableCodigo;
    }

    public String getLopdResponsableNombre() {
        return lopdResponsableNombre;
    }

    public void setLopdResponsableNombre(String lopdResponsableNombre) {
        this.lopdResponsableNombre = lopdResponsableNombre;
    }

    public String getLopdFinalidad() {
        return lopdFinalidad;
    }

    public void setLopdFinalidad(String lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    public Long getLopdDestinatarioCodigo() {
        return lopdDestinatarioCodigo;
    }

    public void setLopdDestinatarioCodigo(Long lopdDestinatarioCodigo) {
        this.lopdDestinatarioCodigo = lopdDestinatarioCodigo;
    }

    public String getLopdDestinatarioNombre() {
        return lopdDestinatarioNombre;
    }

    public void setLopdDestinatarioNombre(String lopdDestinatarioNombre) {
        this.lopdDestinatarioNombre = lopdDestinatarioNombre;
    }

    public String getLopdCabecera() {
        return lopdCabecera;
    }

    public void setLopdCabecera(String lopdCabecera) {
        this.lopdCabecera = lopdCabecera;
    }

    public String getLopdDerechos() {
        return lopdDerechos;
    }

    public void setLopdDerechos(String lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    public void setResponsableTelefono(String responsableTelefono) {
        this.responsableTelefono = responsableTelefono;
    }

    public Boolean getActivoLopd() {
        return activoLopd;
    }

    public void setActivoLopd(Boolean activoLopd) {
        this.activoLopd = activoLopd;
    }

    public String getIncidenciasEmail() {
        return incidenciasEmail;
    }

    public void setIncidenciasEmail(String incidenciasEmail) {
        this.incidenciasEmail = incidenciasEmail;
    }

    public Long getTipoTramitacionCodigo() {
        return tipoTramitacionCodigo;
    }

    public void setTipoTramitacionCodigo(Long tipoTramitacionCodigo) {
        this.tipoTramitacionCodigo = tipoTramitacionCodigo;
    }

    public String getTipoTramitacionNombre() {
        return tipoTramitacionNombre;
    }

    public void setTipoTramitacionNombre(String tipoTramitacionNombre) {
        this.tipoTramitacionNombre = tipoTramitacionNombre;
    }

    public Boolean getTramitacionPresencial() {
        return tramitacionPresencial;
    }

    public void setTramitacionPresencial(Boolean tramitacionPresencial) {
        this.tramitacionPresencial = tramitacionPresencial;
    }

    public Boolean getTramitacionTelefonica() {
        return tramitacionTelefonica;
    }

    public void setTramitacionTelefonica(Boolean tramitacionTelefonica) {
        this.tramitacionTelefonica = tramitacionTelefonica;
    }

    public Boolean getTramitacionElectronica() {
        return tramitacionElectronica;
    }

    public void setTramitacionElectronica(Boolean tramitacionElectronica) {
        this.tramitacionElectronica = tramitacionElectronica;
    }

    public Long getPlataformaTramitacionCodigo() {
        return plataformaTramitacionCodigo;
    }

    public void setPlataformaTramitacionCodigo(Long plataformaTramitacionCodigo) {
        this.plataformaTramitacionCodigo = plataformaTramitacionCodigo;
    }

    public String getPlataformaTramitacionNombre() {
        return plataformaTramitacionNombre;
    }

    public void setPlataformaTramitacionNombre(String plataformaTramitacionNombre) {
        this.plataformaTramitacionNombre = plataformaTramitacionNombre;
    }

    public Long getPlantillaTramitacionCodigo() {
        return plantillaTramitacionCodigo;
    }

    public void setPlantillaTramitacionCodigo(Long plantillaTramitacionCodigo) {
        this.plantillaTramitacionCodigo = plantillaTramitacionCodigo;
    }

    public String getPlantillaTramitacionNombre() {
        return plantillaTramitacionNombre;
    }

    public void setPlantillaTramitacionNombre(String plantillaTramitacionNombre) {
        this.plantillaTramitacionNombre = plantillaTramitacionNombre;
    }

    public Long getPublicoObjetivoCodigo() {
        return publicoObjetivoCodigo;
    }

    public void setPublicoObjetivoCodigo(Long publicoObjetivoCodigo) {
        this.publicoObjetivoCodigo = publicoObjetivoCodigo;
    }

    public String getPublicoObjetivoNombre() {
        return publicoObjetivoNombre;
    }

    public void setPublicoObjetivoNombre(String publicoObjetivoNombre) {
        this.publicoObjetivoNombre = publicoObjetivoNombre;
    }

    public Long getNormativaCodigo() {
        return normativaCodigo;
    }

    public void setNormativaCodigo(Long normativaCodigo) {
        this.normativaCodigo = normativaCodigo;
    }

    public String getNormativaNombre() {
        return normativaNombre;
    }

    public void setNormativaNombre(String normativaNombre) {
        this.normativaNombre = normativaNombre;
    }

    public Long getTemaCodigo() {
        return temaCodigo;
    }

    public void setTemaCodigo(Long temaCodigo) {
        this.temaCodigo = temaCodigo;
    }

    public String getTemaNombre() {
        return temaNombre;
    }

    public void setTemaNombre(String temaNombre) {
        this.temaNombre = temaNombre;
    }

    public Long getDocumentoCodigo() {
        return documentoCodigo;
    }

    public void setDocumentoCodigo(Long documentoCodigo) {
        this.documentoCodigo = documentoCodigo;
    }

    public String getDocumentoTitulo() {
        return documentoTitulo;
    }

    public void setDocumentoTitulo(String documentoTitulo) {
        this.documentoTitulo = documentoTitulo;
    }

    /**
     * Esta relleno el codigo WF
     *
     * @return
     */
    public boolean isRellenoCodigoWF() {
        return this.codigoWF != null;
    }

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    /**
     * Esta relleno el codigoUaDir3
     *
     * @return
     */
    public boolean isRellenoCodigoUaDir3() {
        return codigoUaDir3 != null && !codigoUaDir3.isEmpty();
    }

    public boolean isRellenoTipo() {
        return tipo != null && !tipo.isEmpty();
    }

    public boolean isRellenoCodigoProc() {
        return codigoProc != null;
    }

    public boolean isRellenoCodigosProc() {
        return codigosProc != null && !codigosProc.isEmpty();
    }


    public boolean isRellenoCodigoTram() {
        return codigoTram != null;
    }

    public boolean isRellenoCodigoSIA() {
        return codigoSIA != null;
    }

    public boolean isRellenoEstadoSIA() {
        return estadoSIA != null && !estadoSIA.isEmpty();
    }

    public boolean isRellenoSiaFecha() {
        return siaFecha != null && !siaFecha.isEmpty();
    }

    public boolean isRellenoFechaPublicacionDesde() {
        return fechaPublicacionDesde != null && !fechaPublicacionDesde.isEmpty();
    }

    public boolean isRellenoFechaPublicacionHasta() {
        return fechaPublicacionHasta != null && !fechaPublicacionHasta.isEmpty();
    }

    public boolean isRellenoEstadoWF() {
        return estadoWF != null && !estadoWF.isEmpty();
    }

    public boolean isRellenoCodigoDir3SIA() {
        return codigoDir3SIA != null && !codigoDir3SIA.isEmpty();
    }

    public boolean isRellenoSilencioAdministrativo() {
        return silencioAdministrativo != null && silencioAdministrativo.getCodigo() != null;
    }

    public boolean isRellenoTipoProcedimiento() {
        return tipoProcedimiento != null && tipoProcedimiento.getCodigo() != null;
    }

    public boolean isRellenoFormaInicio() {
        return formaInicio != null && formaInicio.getCodigo() != null;
    }

    public boolean isRellenoPublicoObjetivo() {
        return publicoObjetivo != null && publicoObjetivo.getCodigo() != null;
    }

    public boolean isRellenoVolcadoSIA() {
        return volcadoSIA != null && !volcadoSIA.isEmpty();
    }

    public boolean isRellenoEstado() {
        return estado != null && !estado.isEmpty();
    }

    public boolean isRellenoEstados() {
        return estados != null && !estados.isEmpty();
    }

    public boolean isRellenoNormativas() {
        return normativas != null && !normativas.isEmpty();
    }

    public boolean isRellenoPublicoObjetivos() {
        return publicoObjetivos != null && !publicoObjetivos.isEmpty();
    }

    public boolean isRellenoMaterias() {
        return materias != null && !materias.isEmpty();
    }

    public boolean isRellenoTemas() {
        return temas != null && !temas.isEmpty();
    }

    public boolean isRellenoHijasActivas() {
        return hijasActivas;
    }

    public boolean isRellenoUasAux() {
        return idsUAsHijasAux != null;
    }

    public boolean isRellenoUasResponsable() {
        return idUAsResponsable != null && !idUAsResponsable.isEmpty();
    }

    public boolean isRellenoUaResponsable() {
        return idUAResponsable != null;
    }

    public boolean isRellenoUasInstructor() {
        return idUAsInstructor != null && !idUAsInstructor.isEmpty();
    }

    public boolean isRellenoUaInstructor() {
        return idUAInstructor != null;
    }

    public boolean isRellenoUaInstructorOComun() {
        return idUAInstructorOComun != null;
    }

    public boolean isRellenoTodasUnidadesOrganicas() {
        return todasUnidadesOrganicas;
    }

    public boolean isRellenoFinVia() {
        return finVia != null && finVia.getCodigo() != null;
    }

    public boolean isRellenoTramiteVigente() {
        return tramiteVigente != null && !tramiteVigente.isEmpty();
    }

    public boolean isRellenoTramiteTelematico() {
        return tramiteTelematico != null && !tramiteTelematico.isEmpty();
    }

    public boolean isRellenoTelematico() {
        return telematico != null;
    }

    public boolean isRellenoPlantilla() {
        return plantilla != null && plantilla.getCodigo() != null;
    }

    public boolean isRellenoPlataforma() {
        return plataforma != null && plataforma.getCodigo() != null;
    }

    public boolean isRellenoComun() {
        return comun != null && !comun.isEmpty();
    }

    public boolean isRellenoMensajesPendientes() {
        return mensajesPendiente != null && !mensajesPendiente.isEmpty();
    }

    public String getFechaPublicacionDesde() {
        return fechaPublicacionDesde;
    }

    public void setFechaPublicacionDesde(String fechaPublicacionDesde) {
        this.fechaPublicacionDesde = fechaPublicacionDesde;
    }

    public String getFechaPublicacionHasta() {
        return fechaPublicacionHasta;
    }

    public void setFechaPublicacionHasta(String fechaPublicacionHasta) {
        this.fechaPublicacionHasta = fechaPublicacionHasta;
    }

    public String getCodigoUaDir3() {
        return codigoUaDir3;
    }

    public void setCodigoUaDir3(String codigoUaDir3) {
        this.codigoUaDir3 = codigoUaDir3;
    }

    @Override
    protected String getDefaultOrder() {
        return "codigo";
    }

    public Boolean getEsProcedimiento() {
        return esProcedimiento;
    }

    public void setEsProcedimiento(Boolean esProcedimiento) {
        this.esProcedimiento = esProcedimiento;
    }

    public List<String> getCanales() {
        return canales;
    }

    public void setCanales(List<String> canales) {
        this.canales = canales;
    }

    public Boolean getIntegrarPdu() {
        return integrarPdu;
    }

    public void setIntegrarPdu(Boolean integrarPdu) {
        this.integrarPdu = integrarPdu;
    }

    public Boolean getIntegradoPdu() {
        return integradoPdu;
    }

    public void setIntegradoPdu(Boolean integradoPdu) {
        this.integradoPdu = integradoPdu;
    }

    public void setTramitacionPersonaApoderada(String tramitacionPersonaApoderada) {
        this.tramitacionPersonaApoderada = tramitacionPersonaApoderada;
    }

    public String getTramitacionPersonaApoderada() {
        return tramitacionPersonaApoderada;
    }

    public void setDisponibleFuncionarioHabilitado(String disponibleFuncionarioHabilitado) {
        this.disponibleFuncionarioHabilitado = disponibleFuncionarioHabilitado;
    }

    public String getDisponibleFuncionarioHabilitado() {
        return disponibleFuncionarioHabilitado;
    }



    public boolean isRellenoCanales() {
        return canales != null && !canales.isEmpty();
    }

    public boolean isRellenoIdTramite() {
        return idTramite != null;
    }

    public boolean isRellenoIdPlataforma() {
        return identificadorPlataforma != null;
    }

    public boolean isRellenoVersion() {
        return version != null;
    }

    public boolean isRellenoVisibleSEDE() {
        return visibleSEDE != null;
    }


    public boolean isRellenoIntegrarPdu() {
        return integrarPdu != null;
    }

    public boolean isRellenoIntegradoPdu() {
        return integradoPdu != null;
    }

    public boolean isRellenoTramitacionPersonaApoderada() {
        return tramitacionPersonaApoderada != null;
    }

    public boolean isRellenoDisponibleFuncionarioHabilitado() {
        return disponibleFuncionarioHabilitado != null;
    }

    public boolean isRellenoCodigo() {
        return codigo != null;
    }

    public boolean isRellenoNombre() {
        return nombre != null && !nombre.isEmpty();
    }

    public Boolean getBuscarEnDescendientesUA() {
        return buscarEnDescendientesUA;
    }

    public void setBuscarEnDescendientesUA(Boolean buscarEnDescendientesUA) {
        this.buscarEnDescendientesUA = buscarEnDescendientesUA;
    }

    public boolean isRellenoInicioFechaActualizacion() {
        return inicioFechaActualizacion != null && !inicioFechaActualizacion.isEmpty();
    }

    public boolean isRellenoFinFechaActualizacion() {
        return finFechaActualizacion != null && !finFechaActualizacion.isEmpty();
    }

    public boolean isRellenoInicioFechaCaducidad() {
        return inicioFechaCaducidad != null && !inicioFechaCaducidad.isEmpty();
    }

    public boolean isRellenoFinFechaCaducidad() {
        return finFechaCaducidad != null && !finFechaCaducidad.isEmpty();
    }

    public boolean isRellenoObservaciones() {
        return observaciones != null && !observaciones.isEmpty();
    }

    public boolean isRellenoRequisitos() {
        return requisitos != null && !requisitos.isEmpty();
    }

    public boolean isRellenoInicioFechaSIA() {
        return inicioFechaSIA != null && !inicioFechaSIA.isEmpty();
    }

    public boolean isRellenoFinFechaSIA() {
        return finFechaSIA != null && !finFechaSIA.isEmpty();
    }

    public boolean isRellenoCodigoUAResponsable() {
        return codigoUAResponsable != null;
    }

    public boolean isRellenoNombreUAResponsable() {
        return nombreUAResponsable != null && !nombreUAResponsable.isEmpty();
    }

    public boolean isRellenoCodigoUACompetente() {
        return codigoUACompetente != null;
    }

    public boolean isRellenoNombreUACompetente() {
        return nombreUACompetente != null && !nombreUACompetente.isEmpty();
    }

    public boolean isRellenoCodigoUAInstructora() {
        return codigoUAInstructora != null;
    }

    public boolean isRellenoNombreUAInstructora() {
        return nombreUAInstructora != null && !nombreUAInstructora.isEmpty();
    }

    public boolean isRellenoCodigoLOPDResponsable() {
        return codigoLOPDResponsable != null;
    }

    public boolean isRellenoNombreLOPDResponsable() {
        return nombreLOPDResponsable != null && !nombreLOPDResponsable.isEmpty();
    }

    public boolean isRellenoCodigoLOPDLegitimacion() {
        return codigoLOPDLegitimacion != null;
    }

    public boolean isRellenoNombreLOPDLegitimacion() {
        return nombreLOPDLegitimacion != null && !nombreLOPDLegitimacion.isEmpty();
    }

    public boolean isRellenoFinalidadLOPD() {
        return finalidadLOPD != null && !finalidadLOPD.isEmpty();
    }

    public boolean isRellenoCodigoLOPDDestinatario() {
        return codigoLOPDDestinatario != null;
    }

    public boolean isRellenoNombreLOPDDestinatario() {
        return nombreLOPDDestinatario != null && !nombreLOPDDestinatario.isEmpty();
    }

    public boolean isRellenoDerechosLOPD() {
        return derechosLOPD != null && !derechosLOPD.isEmpty();
    }

    public boolean isRellenoObjeto() {
        return objeto != null && !objeto.isEmpty();
    }

    public boolean isRellenoCodigoTipoProcedimiento() {
        return codigoTipoProcedimiento != null;
    }

    public boolean isRellenoNombreTipoProcedimiento() {
        return nombreTipoProcedimiento != null && !nombreTipoProcedimiento.isEmpty();
    }

    public boolean isRellenoCodigoIniciacion() {
        return codigoIniciacion != null;
    }

    public boolean isRellenoNombreIniciacion() {
        return nombreIniciacion != null && !nombreIniciacion.isEmpty();
    }

    public boolean isRellenoCodigoSilencioAdministrativo() {
        return codigoSilencioAdministrativo != null;
    }

    public boolean isRellenoNombreSilencioAdministrativo() {
        return nombreSilencioAdministrativo != null && !nombreSilencioAdministrativo.isEmpty();
    }

    public boolean isRellenoCodigoTipoTramite() {
        return codigoTipoTramite != null;
    }

    public boolean isRellenoNombreTipoTramite() {
        return nombreTipoTramite != null && !nombreTipoTramite.isEmpty();
    }

    public boolean isRellenoCodigoTipoVia() {
        return codigoTipoVia != null;
    }

    public boolean isRellenoNombreTipoVia() {
        return nombreTipoVia != null && !nombreTipoVia.isEmpty();
    }

    public boolean isRellenoHabilitadoApoderado() {
        return habilitadoApoderado != null;
    }

    public boolean isRellenoHabilitadoFuncionario() {
        return habilitadoFuncionario != null;
    }

    public boolean isRellenoTieneTasa() {
        return tieneTasa != null;
    }

    public boolean isRellenoTerminoResolucion() {
        return terminoResolucion != null && !terminoResolucion.isEmpty();
    }

    public boolean isRellenoCodigoPublicoObjetivo() {
        return codigoPublicoObjetivo != null;
    }

    public boolean isRellenoNombrePublicoObjetivo() {
        return nombrePublicoObjetivo != null && !nombrePublicoObjetivo.isEmpty();
    }

    public boolean isRellenoCodigoNormativa() {
        return codigoNormativa != null;
    }

    public boolean isRellenoNombreNormativa() {
        return nombreNormativa != null && !nombreNormativa.isEmpty();
    }

    public boolean isRellenoCodigoTema() {
        return codigoTema != null;
    }

    public boolean isRellenoNombreTema() {
        return nombreTema != null && !nombreTema.isEmpty();
    }

    public boolean isRellenoCodigoDocumento() {
        return codigoDocumento != null;
    }

    public boolean isRellenoTituloDocumento() {
        return tituloDocumento != null && !tituloDocumento.isEmpty();
    }

    public boolean isRellenoCodigoTramite() {
        return codigoTramite != null;
    }

    public boolean isRellenoNombreTramite() {
        return nombreTramite != null && !nombreTramite.isEmpty();
    }

    public boolean isRellenoFaseTramite() {
        return faseTramite != null;
    }

    public boolean isRellenoInicioFechaInicioTramite() {
        return inicioFechaInicioTramite != null && !inicioFechaInicioTramite.isEmpty();
    }

    public boolean isRellenoFinFechaInicioTramite() {
        return finFechaInicioTramite != null && !finFechaInicioTramite.isEmpty();
    }

    public boolean isRellenoInicioFechaCierreTramite() {
        return inicioFechaCierreTramite != null && !inicioFechaCierreTramite.isEmpty();
    }

    public boolean isRellenoFinFechaCierreTramite() {
        return finFechaCierreTramite != null && !finFechaCierreTramite.isEmpty();
    }

    public boolean isRellenoTramitePresencial() {
        return tramitePresencial != null;
    }

    public boolean isRellenoTramiteTelefonico() {
        return tramiteTelefonico != null;
    }

    public boolean isRellenoTramiteElectronico() {
        return tramiteElectronico != null;
    }

    public boolean isRellenoEsProcedimiento() {
        return esProcedimiento != null;
    }

    public boolean isRellenoResponsableEmail() {
        return responsableEmail != null && !responsableEmail.isEmpty();
    }

    public boolean isRellenoResponsableTelefono() {
        return responsableTelefono != null && !responsableTelefono.isEmpty();
    }

    public boolean isRellenoActivoLopd() {
        return activoLopd != null;
    }

    public boolean isRellenoIncidenciasEmail() {
        return incidenciasEmail != null && !incidenciasEmail.isEmpty();
    }

    public boolean isRellenoTipoTramitacionCodigo() {
        return tipoTramitacionCodigo != null;
    }

    public boolean isRellenoTipoTramitacionNombre() {
        return tipoTramitacionNombre != null && !tipoTramitacionNombre.isEmpty();
    }

    public boolean isRellenoTramitacionPresencial() {
        return tramitacionPresencial != null;
    }

    public boolean isRellenoTramitacionTelefonica() {
        return tramitacionTelefonica != null;
    }

    public boolean isRellenoTramitacionElectronica() {
        return tramitacionElectronica != null;
    }

    public boolean isRellenoPlataformaTramitacionCodigo() {
        return plataformaTramitacionCodigo != null;
    }

    public boolean isRellenoPlataformaTramitacionNombre() {
        return plataformaTramitacionNombre != null && !plataformaTramitacionNombre.isEmpty();
    }

    public boolean isRellenoPlantillaTramitacionCodigo() {
        return plantillaTramitacionCodigo != null;
    }

    public boolean isRellenoPlantillaTramitacionNombre() {
        return plantillaTramitacionNombre != null && !plantillaTramitacionNombre.isEmpty();
    }

    public boolean isRellenoPublicoObjetivoCodigo() {
        return publicoObjetivoCodigo != null;
    }

    public boolean isRellenoPublicoObjetivoNombre() {
        return publicoObjetivoNombre != null && !publicoObjetivoNombre.isEmpty();
    }

    public boolean isRellenoNormativaCodigo() {
        return normativaCodigo != null;
    }

    public boolean isRellenoNormativaNombre() {
        return normativaNombre != null && !normativaNombre.isEmpty();
    }

    public boolean isRellenoTemaCodigo() {
        return temaCodigo != null;
    }

    public boolean isRellenoTemaNombre() {
        return temaNombre != null && !temaNombre.isEmpty();
    }

    public boolean isRellenoDocumentoCodigo() {
        return documentoCodigo != null;
    }

    public boolean isRellenoDocumentoTitulo() {
        return documentoTitulo != null && !documentoTitulo.isEmpty();
    }

    public boolean isRellenoBuscarEnDescendientesUA() {
        return buscarEnDescendientesUA != null && buscarEnDescendientesUA;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public ProcedimientoFiltro clone() {
        return new ProcedimientoFiltro(this);
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("ProcedimientoFiltro{");

        texto.append("texto='").append(texto).append('\'');
        texto.append(", tipo='").append(tipo).append('\'');
        texto.append(", codigoSIA=").append(codigoSIA);
        texto.append(", codigoProc=").append(codigoProc);
        texto.append(", codigosProc=").append(codigosProc);
        texto.append(", codigoWF=").append(codigoWF);
        texto.append(", codigoTram=").append(codigoTram);
        texto.append(", estadoSIA='").append(estadoSIA).append('\'');
        texto.append(", siaFecha='").append(siaFecha).append('\'');
        texto.append(", fechaPublicacionDesde='").append(fechaPublicacionDesde).append('\'');
        texto.append(", fechaPublicacionHasta='").append(fechaPublicacionHasta).append('\'');
        texto.append(", codigoDir3SIA='").append(codigoDir3SIA).append('\'');
        texto.append(", codigoUaDir3='").append(codigoUaDir3).append('\'');
        texto.append(", volcadoSIA='").append(volcadoSIA).append('\'');
        texto.append(", silencioAdministrativo=").append(silencioAdministrativo);
        texto.append(", tipoProcedimiento=").append(tipoProcedimiento);
        texto.append(", formaInicio=").append(formaInicio);
        texto.append(", publicoObjetivo=").append(publicoObjetivo);
        texto.append(", publicoObjetivos=").append(publicoObjetivos);
        texto.append(", materias=").append(materias);
        texto.append(", normativas=").append(normativas);
        texto.append(", estado='").append(estado).append('\'');
        texto.append(", estados=").append(estados);
        texto.append(", hijasActivas=").append(hijasActivas);
        texto.append(", idUAsHijas=").append(idUAsHijas);
        texto.append(", idUAResponsable=").append(idUAResponsable);
        texto.append(", idUAsResponsable=").append(idUAsResponsable);
        texto.append(", idUAInstructor=").append(idUAInstructor);
        texto.append(", idUAsInstructor=").append(idUAsInstructor);
        texto.append(", idsUAsHijasAux=").append(idsUAsHijasAux);
        texto.append(", todasUnidadesOrganicas=").append(todasUnidadesOrganicas);
        texto.append(", finVia=").append(finVia);
        texto.append(", tramiteVigente='").append(tramiteVigente).append('\'');
        texto.append(", tramiteTelematico='").append(tramiteTelematico).append('\'');
        texto.append(", telematico=").append(telematico);
        texto.append(", plantilla=").append(plantilla);
        texto.append(", plataforma=").append(plataforma);
        texto.append(", comun='").append(comun).append('\'');
        texto.append(", estadoWF='").append(estadoWF).append('\'');
        texto.append(", mensajesPendiente='").append(mensajesPendiente).append('\'');
        texto.append(", temas=").append(temas);
        texto.append(", esProcedimiento=").append(esProcedimiento);
        texto.append(", canales=").append(canales);
        texto.append(", idTramite='").append(idTramite).append('\'');
        texto.append(", identificadorPlataforma='").append(identificadorPlataforma).append('\'');
        texto.append(", version=").append(version);
        texto.append(", visibleSEDE='").append(visibleSEDE).append('\'');
        texto.append(", integrarPdu=").append(integrarPdu);
        texto.append(", esPdu=").append(integradoPdu);
        texto.append(", tramitacionPersonaApoderada='").append(tramitacionPersonaApoderada).append('\'');
        texto.append(", disponibleFuncionarioHabilitado='").append(disponibleFuncionarioHabilitado).append('\'');
        texto.append(", idUA=").append(getIdUA());
        texto.append(", idEntidad=").append(getIdEntidad());
        texto.append(", idioma=").append(getIdioma());
        texto.append(", paginaTamanyo=").append(getPaginaTamanyo());
        texto.append(", paginaFirst=").append(getPaginaFirst());
        texto.append(", orderBy='").append(getOrderBy()).append('\'');
        texto.append(", order='").append(getOrder()).append('\'');
        texto.append(", ascendente=").append(isAscendente());
        texto.append(", operadoresString=").append(isOperadoresString());
        texto.append(", paginacionActiva=").append(isPaginacionActiva());
        texto.append(", total=").append(getTotal());
        texto.append(", visibleSEDE='").append(visibleSEDE).append('\'');
        texto.append('}');
        return texto.toString();
    }

    private List<Long> idUAs;

    public List<Long> getIdUAs() {
        return idUAs;
    }

    public void setIdUAs(List<Long> idUAs) {
        this.idUAs = idUAs;
    }

    public boolean isRellenoIdUAs() {
        return idUAs != null && !idUAs.isEmpty();
    }
}
