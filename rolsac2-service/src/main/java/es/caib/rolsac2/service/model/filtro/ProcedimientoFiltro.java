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
    private Long codigoSIA;
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
    private boolean todasUbidadesOrganicas = false;

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

    public Long getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Long procSiacod) {
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

    public boolean isHijasActivas() {
        return hijasActivas;
    }

    public void setHijasActivas(boolean hijasActivas) {
        this.hijasActivas = hijasActivas;
    }

    public boolean isTodasUbidadesOrganicas() {
        return todasUbidadesOrganicas;
    }

    public void setTodasUbidadesOrganicas(boolean todasUbidadesOrganicas) {
        this.todasUbidadesOrganicas = todasUbidadesOrganicas;
    }

    public List<Long> getIdUAsHijas() {
        return idUAsHijas;
    }

    public void setIdUAsHijas(List<Long> idUAsHijas) {
        this.idUAsHijas = idUAsHijas;
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

    public boolean isRellenoHijasActivas() {
        return hijasActivas;
    }

    public boolean isRellenoTodasUbidadesOrganicas() {
        return todasUbidadesOrganicas;
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
