package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

/**
 * ProcesoSolr Filtro.
 *
 * @author Indra
 */
public class ProcesoPduFiltro extends AbstractFiltro {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Filtro codigo.
     **/
    private Long codigo;

    /**
     * Idioma.
     **/
    private String idioma;

    /**
     * Identificador
     */
    private String texto;


    /**
     * Tipo
     */
    private String tipo;


    /**
     * Cod elemento
     */
    private Long codElemento;

    private Boolean integrarPdu;

    private TypeProcedimientoEstado estadoProcedimiento;


    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }


    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Long codElemento) {
        this.codElemento = codElemento;
    }

    /**
     * @return the idioma
     */
    @Override
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    @Override
    public void setIdioma(final String idioma) {
        this.idioma = idioma;
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

    public Boolean getIntegrarPdu() {
        return integrarPdu;
    }

    public void setIntegrarPdu(Boolean integrarPdu) {
        this.integrarPdu = integrarPdu;
    }

    public TypeProcedimientoEstado getEstadoProcedimiento() {
        return estadoProcedimiento;
    }

    public void setEstadoProcedimiento(TypeProcedimientoEstado estadoProcedimiento) {
        this.estadoProcedimiento = estadoProcedimiento;
    }

    /**
     * Está relleno el código.
     */
    public boolean isRellenoCodigo() {
        return this.getCodigo() != null;
    }

    /**
     * Está relleno el tipo.
     */
    public boolean isRellenoTipo() {
        return this.getTipo() != null && !this.getTipo().isEmpty();
    }

    /**
     * Está relleno el tipo.
     */
    public boolean isRellenoCodElemento() {
        return this.getCodElemento() != null;
    }

    public boolean isRellenoEstadoProcedimiento() {
        return this.getEstadoProcedimiento() != null;
    }

    public boolean isRellenoIntegrarPdu(){
        return this.estadoProcedimiento != null;
    }

    @Override
    public String getDefaultOrder() {
        return "codigo";
    }

}
