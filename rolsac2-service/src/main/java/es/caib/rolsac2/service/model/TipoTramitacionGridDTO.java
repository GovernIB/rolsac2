package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * El tipo Tipo tramitacion grid dto.
 *
 * @author Indra
 */
@Schema(name = "TipoTramitacionGrid")
public class TipoTramitacionGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Tramitación presencial
     */
    private boolean tramitPresencial;

    /**
     * Tramitación electrónica
     */
    private boolean tramitElectronica;

    /**
     * URL tramitación
     */
    @NotEmpty
    @Size(max = 500)
    private String urlTramitacion;

    /**
     * Código plataforma tramitación
     */

//  private Long codPlatTramitacion;
    private String codPlatTramitacion;

    /**
     * Trámite Id
     */
    @NotEmpty
    @Size(max = 50)
    private String tramiteId;

    /**
     * Trámite Versión
     */
    @NotNull
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
    @NotEmpty
    @Size(max = 500)
    private String tramiteParametros;

    /**
     * Instancia un nuevo Tipo tramitacion grid dto.
     */
    public TipoTramitacionGridDTO() {
    }

    /**
     * Instancia un nuevo tramitacion grid dto.
     *
     * @param id  id
     */
    public TipoTramitacionGridDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Is tramit presencial boolean.
     *
     * @return  boolean
     */
    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    /**
     * Establece tramit presencial.
     *
     * @param tramitPresencial  tramit presencial
     */
    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    /**
     * Is tramit electronica boolean.
     *
     * @return  boolean
     */
    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    /**
     * Establece tramit electronica.
     *
     * @param tramitElectronica  tramit electronica
     */
    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    /**
     * Obtiene url tramitacion.
     *
     * @return  url tramitacion
     */
    public String getUrlTramitacion() {
        return urlTramitacion;
    }

    /**
     * Establece url tramitacion.
     *
     * @param urlTramitacion  url tramitacion
     */
    public void setUrlTramitacion(String urlTramitacion) {
        this.urlTramitacion = urlTramitacion;
    }

    /**
     * Obtiene cod plat tramitacion.
     *
     * @return  cod plat tramitacion
     */
    public String getCodPlatTramitacion() {
        return codPlatTramitacion;
    }

    /**
     * Establece cod plat tramitacion.
     *
     * @param codPlatTramitacion  cod plat tramitacion
     */
    public void setCodPlatTramitacion(String codPlatTramitacion) {
        this.codPlatTramitacion = codPlatTramitacion;
    }

//  public Long getCodPlatTramitacion() {
//    return codPlatTramitacion;
//  }
//
//  public void setCodPlatTramitacion(Long codPlatTramitacion) {
//    this.codPlatTramitacion = codPlatTramitacion;
//  }

    /**
     * Obtiene tramite id.
     *
     * @return  tramite id
     */
    public String getTramiteId() {
        return tramiteId;
    }

    /**
     * Establece tramite id.
     *
     * @param tramiteId  tramite id
     */
    public void setTramiteId(String tramiteId) {
        this.tramiteId = tramiteId;
    }

    /**
     * Obtiene tramite version.
     *
     * @return  tramite version
     */
    public Integer getTramiteVersion() {
        return tramiteVersion;
    }

    /**
     * Establece tramite version.
     *
     * @param tramiteVersion  tramite version
     */
    public void setTramiteVersion(Integer tramiteVersion) {
        this.tramiteVersion = tramiteVersion;
    }

    /**
     * Obtiene tramite parametros.
     *
     * @return  tramite parametros
     */
    public String getTramiteParametros() {
        return tramiteParametros;
    }

    /**
     * Establece tramite parametros.
     *
     * @param tramiteParametros  tramite parametros
     */
    public void setTramiteParametros(String tramiteParametros) {
        this.tramiteParametros = tramiteParametros;
    }

    @Override
    public String toString() {
        return "TipoTramitacionDTO{" + "id=" + codigo + ", tramitPresencial=" + tramitPresencial + ", tramitElectronica="
                + tramitElectronica + ", urlTramitacion='" + urlTramitacion + '\'' + ", codPlatTramitacion="
                + codPlatTramitacion + ", tramiteId='" + tramiteId + '\'' + ", tramiteVersion=" + tramiteVersion
                + ", tramiteParametros='" + tramiteParametros + '\'' + '}';
    }
}
