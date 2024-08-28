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
    private List<Long> idUAsInstructor;

    private List<Long> idsUAsHijasAux;
    private boolean todasUnidadesOrganicas = false;

    private TipoViaDTO finVia;
    private String tramiteVigente;
    private String tramiteTelematico;
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
        this.setVisibleSEDE(otro.getVisibleSEDE());
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

    public void setCodigosProc(String cods) {
        String[] codigos = cods.split(",");
        this.codigosProc = new ArrayList<>();
        for (String codigo : codigos) {
            codigosProc.add(Long.parseLong(codigo));
        }
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String procTipo) {
        this.tipo = procTipo;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer procSiacod) {
        this.codigoSIA = procSiacod;
    }

    public String getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(String procSiaest) {
        this.estadoSIA = procSiaest;
    }

    public String getSiaFecha() {
        return siaFecha;
    }

    public void setSiaFecha(String procSiafc) {
        this.siaFecha = procSiafc;
    }

    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    public void setCodigoDir3SIA(String procSiadir3) {
        this.codigoDir3SIA = procSiadir3;
    }

    public String getEstadoWF() {
        return estadoWF;
    }

    public void setEstadoWF(String estadoWF) {
        this.estadoWF = estadoWF;
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

    public String getVolcadoSIA() {
        return volcadoSIA;
    }

    public void setVolcadoSIA(String volcadoSIA) {
        this.volcadoSIA = volcadoSIA;
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
        return "id";
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


    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public ProcedimientoFiltro clone() {
        return new ProcedimientoFiltro(this);
    }

}
