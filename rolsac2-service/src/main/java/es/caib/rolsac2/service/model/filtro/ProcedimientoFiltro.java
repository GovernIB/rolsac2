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
    private Long codigoTram;
    private String estadoSIA;
    private String siaFecha;
    private String codigoDir3SIA;

    private String volcadoSIA;

    private TipoSilencioAdministrativoDTO silencioAdministrativo;

    private TipoProcedimientoDTO tipoProcedimiento;

    private TipoFormaInicioDTO formaInicio;

    private TipoPublicoObjetivoDTO publicoObjetivo;

    private List<TipoPublicoObjetivoEntidadGridDTO> publicoObjetivos;
    private List<TipoMateriaSIAGridDTO> materias;
    private List<NormativaGridDTO> normativas;
    private String estado;
    private boolean hijasActivas = false;
    private List<Long> idUAsHijas;

    private List<Long> idsUAsHijasAux;
    private boolean todasUnidadesOrganicas = false;

    private TipoViaDTO finVia;
    private String tramiteVigente;
    private String tramiteTelematico;
    private TipoTramitacionDTO plantilla;
    private PlatTramitElectronicaDTO plataforma;
    private String comun;

    private String mensajesPendiente;

    private List<TemaGridDTO> temas;

    public Long getCodigoProc() {
        return codigoProc;
    }

    public void setCodigoProc(Long codigoProc) {
        this.codigoProc = codigoProc;
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

    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoTipo() {
        return tipo != null && !tipo.isEmpty();
    }

    public boolean isRellenoCodigoProc() {
        return codigoProc != null;
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

    public boolean isRellenoUasAux() {return idsUAsHijasAux != null; }

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

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
