package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;

import java.time.LocalDate;
import java.util.List;

public class NormativaFiltro extends AbstractFiltro {

    private String texto;

    private String nombre;

    private TipoNormativaDTO tipoNormativa;

    private TipoBoletinDTO tipoBoletin;

    private LocalDate fechaAprobacion;

    private LocalDate fechaBoletin;

    private String numero;

    private boolean hijasActivas = false;

    private List<Long> idUAsHijas;

    private List<Long> idsUAsHijasAux;

    private boolean todasUnidadesOrganicas = false;

    private Integer codigoProcedimiento;

    private Integer codigoServicio;

    private Long codigo;

    private Boolean vigente;

    private Boolean soloValidas;

    /**
     * Constructor
     */
    public NormativaFiltro() {
        //Vacio
    }

    /**
     * Constructor para clonar
     *
     * @param normativaFiltro
     */
    public NormativaFiltro(NormativaFiltro normativaFiltro) {
        this.texto = normativaFiltro.texto;
        this.nombre = normativaFiltro.nombre;
        this.tipoNormativa = normativaFiltro.tipoNormativa;
        this.tipoBoletin = normativaFiltro.tipoBoletin;
        this.fechaAprobacion = normativaFiltro.fechaAprobacion;
        this.fechaBoletin = normativaFiltro.fechaBoletin;
        this.numero = normativaFiltro.numero;
        this.hijasActivas = normativaFiltro.hijasActivas;
        this.idUAsHijas = normativaFiltro.idUAsHijas;
        this.idsUAsHijasAux = normativaFiltro.idsUAsHijasAux;
        this.todasUnidadesOrganicas = normativaFiltro.todasUnidadesOrganicas;
        this.codigoProcedimiento = normativaFiltro.codigoProcedimiento;
        this.codigoServicio = normativaFiltro.codigoServicio;
        this.codigo = normativaFiltro.codigo;
        this.vigente = normativaFiltro.vigente;
        this.soloValidas = normativaFiltro.soloValidas;
        this.setIdioma(normativaFiltro.getIdioma());
        this.setIdUA(normativaFiltro.getIdUA());
        this.setIdEntidad(normativaFiltro.getIdEntidad());
        this.setPaginaTamanyo(normativaFiltro.getPaginaTamanyo());
        this.setPaginaFirst(normativaFiltro.getPaginaFirst());
        this.setOrderBy(normativaFiltro.getOrderBy());
        this.setOrder(normativaFiltro.getOrder());
        this.setAscendente(normativaFiltro.isAscendente());
        this.setOperadoresString(normativaFiltro.isOperadoresString());
        this.setPaginacionActiva(normativaFiltro.isPaginacionActiva());
        this.setTotal(normativaFiltro.getTotal());
    }


    @Override
    protected String getDefaultOrder() {
        return "id";
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public TipoNormativaDTO getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(TipoNormativaDTO tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public TipoBoletinDTO getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(TipoBoletinDTO tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    public boolean isHijasActivas() {
        return hijasActivas;
    }

    public void setHijasActivas(boolean hijasActivas) {
        this.hijasActivas = hijasActivas;
    }

    public List<Long> getIdUAsHijas() {
        return idUAsHijas;
    }

    public void setIdUAsHijas(List<Long> idUAsHijas) {
        this.idUAsHijas = idUAsHijas;
    }

    public List<Long> getIdsUAsHijasAux() {
        return idsUAsHijasAux;
    }

    public void setIdsUAsHijasAux(List<Long> idsUAsHijasAux) {
        this.idsUAsHijasAux = idsUAsHijasAux;
    }

    public boolean isTodasUnidadesOrganicas() {
        return todasUnidadesOrganicas;
    }

    public void setTodasUnidadesOrganicas(boolean todasUnidadesOrganicas) {
        this.todasUnidadesOrganicas = todasUnidadesOrganicas;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

    public Boolean isVigente() {
        return vigente != null;
    }

    public Boolean getSoloValidas() {
        return soloValidas;
    }

    public void setSoloValidas(Boolean soloValidas) {
        this.soloValidas = soloValidas;
    }


    /**
     * Esta relleno el texto
     *
     * @return
     */
    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoNombre() {
        return nombre != null && !nombre.isEmpty();
    }

    public boolean isRellenoFechaAprobacion() {
        return fechaAprobacion != null;
    }

    public boolean isRellenoCodigo() {
        return codigo != null;
    }

    public boolean isRellenoFechaBoletin() {
        return fechaBoletin != null;
    }

    public boolean isRellenoTipoNormativa() {
        return tipoNormativa != null && tipoNormativa.getCodigo() != null;
    }

    public boolean isRellenoTipoBoletin() {
        return tipoBoletin != null && tipoBoletin.getCodigo() != null;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public boolean isRellenoHijasActivas() {
        return hijasActivas;
    }

    public boolean isRellenoUasAux() {
        return idsUAsHijasAux != null;
    }

    public boolean isRellenoTodasUnidadesOrganicas() {
        return this.todasUnidadesOrganicas;
    }

    public boolean isRellenoNumero() {
        return numero != null && !numero.isEmpty();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCodigoProcedimiento() {
        return codigoProcedimiento;
    }

    public void setCodigoProcedimiento(Integer codigoProcedimiento) {
        this.codigoProcedimiento = codigoProcedimiento;
    }

    public Integer getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Integer codigoServicio) {
        this.codigoServicio = codigoServicio;
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

    public boolean isRellenoVigente() {
        return vigente != null;
    }

    public boolean isRellenoSoloValidas() {
        return soloValidas != null;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public NormativaFiltro clone() {
        return new NormativaFiltro(this);
    }
}
