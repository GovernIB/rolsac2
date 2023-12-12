package es.caib.rolsac2.service.model.filtro;

import java.util.List;

/**
 * Filtro de ua.
 */
public class UnidadAdministrativaFiltro extends AbstractFiltro {

    /**
     * El filtro que hay en UnidadAdministrativaView
     **/
    private String texto;
    private String nombre;
    private String identificador;
    private Long codEnti;
    private Long codigoNormativa;
    private Long codigo;
    private List<Long> codigos;
    private String codigoDIR3;
    private String estado;

    /**
     * Constructor
     **/
    public UnidadAdministrativaFiltro() {
        //Vacio
    }

    /**
     * Constructor para clonar
     *
     * @param otro
     */
    public UnidadAdministrativaFiltro(UnidadAdministrativaFiltro otro) {
        this.texto = otro.texto;
        this.nombre = otro.nombre;
        this.identificador = otro.identificador;
        this.codEnti = otro.codEnti;
        this.codigoNormativa = otro.codigoNormativa;
        this.codigo = otro.codigo;
        this.codigos = otro.codigos;
        this.codigoDIR3 = otro.codigoDIR3;
        this.estado = otro.estado;
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
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public List<Long> getCodigos() {
        return codigos;
    }

    public void setCodigos(List<Long> codigos) {
        this.codigos = codigos;
    }

    public Long getCodEnti() {
        return codEnti;
    }

    public void setCodEnti(Long codEnti) {
        this.codEnti = codEnti;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }


    public Long getCodigoNormativa() {
        return codigoNormativa;
    }

    public void setCodigoNormativa(Long codigoNormativa) {
        this.codigoNormativa = codigoNormativa;
    }

    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    public void setCodigoDIR3(String codigoDIR3) {
        this.codigoDIR3 = codigoDIR3;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
     * Esta relleno el nombre
     **/
    public boolean isRellenoNombre() {
        return nombre != null && !nombre.isEmpty();
    }

    /**
     * Esta relleno el identificador
     **/
    public boolean isRellenoIdentificador() {
        return identificador != null && !identificador.isEmpty();
    }

    /**
     * Esta relleno el codEnti
     **/
    public boolean isRellenoCodEnti() {
        return codEnti != null;
    }

    /**
     * Esta relleno el codigoNormativa
     **/
    public boolean isRellenoCodigoNormativa() {
        return codigoNormativa != null;
    }

    /**
     * Esta relleno el codigoNormativa
     **/
    public boolean isRellenoEstado() {
        return estado != null && !estado.isEmpty();
    }

    public boolean isRellenoCodigoDIR3() {
        return codigoDIR3 != null && !codigoDIR3.isEmpty();
    }

    public boolean isRellenoCodigo() {
        return codigo != null;
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public UnidadAdministrativaFiltro clone() {
        return new UnidadAdministrativaFiltro(this);
    }
}
