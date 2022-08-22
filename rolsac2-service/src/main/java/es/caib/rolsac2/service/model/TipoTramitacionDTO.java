package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Indra
 */
@Schema(name = "TipoTramitacion")
public class TipoTramitacionDTO extends ModelApi {

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
    @NotNull
    private PlatTramitElectronicaDTO codPlatTramitacion;

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
     * Indica si es una plantilla
     **/
    private boolean plantilla;

    /**
     * Indica la entidad que pertenece si es una entidad.
     **/
    private EntidadDTO entidad;

    /**
     * Constructor
     **/
    public TipoTramitacionDTO() {
        //Vacio
    }

    public TipoTramitacionDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public String getUrlTramitacion() {
        return urlTramitacion;
    }

    public void setUrlTramitacion(String urlTramitacion) {
        this.urlTramitacion = urlTramitacion;
    }

    public PlatTramitElectronicaDTO getCodPlatTramitacion() {
        return codPlatTramitacion;
    }

    public void setCodPlatTramitacion(PlatTramitElectronicaDTO codPlatTramitacion) {
        this.codPlatTramitacion = codPlatTramitacion;
    }

    public String getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(String tramiteId) {
        this.tramiteId = tramiteId;
    }

    public Integer getTramiteVersion() {
        return tramiteVersion;
    }

    public void setTramiteVersion(Integer tramiteVersion) {
        this.tramiteVersion = tramiteVersion;
    }

    public String getTramiteParametros() {
        return tramiteParametros;
    }

    public void setTramiteParametros(String tramiteParametros) {
        this.tramiteParametros = tramiteParametros;
    }

    public boolean isPlantilla() {
        return plantilla;
    }

    public void setPlantilla(boolean plantilla) {
        this.plantilla = plantilla;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "TipoTramitacionDTO{" + "id=" + codigo + ", tramitPresencial=" + tramitPresencial + ", plantilla=" + plantilla
                + ", tramitElectronica=" + tramitElectronica + ", urlTramitacion='" + urlTramitacion + '\''
                + ", codPlatTramitacion=" + codPlatTramitacion + ", tramiteId='" + tramiteId + '\''
                + ", tramiteVersion=" + tramiteVersion + ", tramiteParametros='" + tramiteParametros + '\'' + '}';
    }
}
