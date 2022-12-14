package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * @author Indra
 */
@Schema(name = "TipoTramitacion")
public class TipoTramitacionDTO extends ModelApi implements Cloneable {

    /**
     * LOGGER
     **/
    private static final Logger LOG = LoggerFactory.getLogger(TipoTramitacionDTO.class);

    /**
     * Codigo
     **/
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
     * Tramitacion telefonica
     */
    private boolean tramitTelefonica;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Descripcion
     */
    private Literal url;

    /**
     * Fase procedimiento
     */
    private Integer faseProc;

    /**
     * URL tramitación
     */
    private String urlTramitacion;

    /**
     * Código plataforma tramitación
     */
    private PlatTramitElectronicaDTO codPlatTramitacion;

    /**
     * Trámite Id
     */
    private String tramiteId;

    /**
     * Trámite Versión
     */
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
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
        // Vacio
    }

    public TipoTramitacionDTO(Long id) {
        this.codigo = id;
    }

    public static TipoTramitacionDTO createInstance(List<String> idiomasPermitidosList) {
        TipoTramitacionDTO tipoTramitacion = new TipoTramitacionDTO();
        tipoTramitacion.setTramitPresencial(false);
        tipoTramitacion.setTramitElectronica(false);
        tipoTramitacion.setTramitTelefonica(false);
        tipoTramitacion.setDescripcion(Literal.createInstance(idiomasPermitidosList));
        tipoTramitacion.setUrl(Literal.createInstance(idiomasPermitidosList));
        return tipoTramitacion;
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

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public Literal getUrl() {
        return url;
    }

    public void setUrl(Literal url) {
        this.url = url;
    }

    public final Integer getFaseProc() {
        return faseProc;
    }

    public final void setFaseProc(Integer faseProc) {
        this.faseProc = faseProc;
    }

    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public Object clone() {
        TipoTramitacionDTO obj = null;
        try {
            obj = (TipoTramitacionDTO) super.clone();

            if (this.url != null) {
                obj.url = (Literal) this.url.clone();
            }
            if (this.descripcion != null) {
                obj.descripcion = (Literal) this.descripcion.clone();
            }

        } catch (CloneNotSupportedException ex) {
            LOG.error(" no se puede duplicar", ex);
        }
        return obj;
    }

    @Override
    public String toString() {
        return "TipoTramitacionDTO{" + "id=" + codigo + ", tramitPresencial="
                + tramitPresencial + ", plantilla=" + plantilla + ", tramitElectronica=" + tramitElectronica
                + ", urlTramitacion='" + urlTramitacion + '\'' + ", codPlatTramitacion=" + codPlatTramitacion
                + ", tramiteId='" + tramiteId + '\'' + ", tramiteVersion=" + tramiteVersion + ", tramiteParametros='"
                + tramiteParametros + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TipoTramitacionDTO that = (TipoTramitacionDTO) o;
        if (codigo != null && that.codigo != null) {
            return codigo.equals(that.codigo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public TipoTramitacionDTO(TipoTramitacionDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.descripcion = otro.descripcion;
            this.tramitPresencial = otro.tramitPresencial;
            this.tramitElectronica = otro.tramitElectronica;
            this.urlTramitacion = otro.urlTramitacion;
            this.codPlatTramitacion = otro.codPlatTramitacion;
            this.tramiteId = otro.tramiteId;
            this.tramiteVersion = otro.tramiteVersion;
            this.tramiteParametros = otro.tramiteParametros;
            this.plantilla = otro.plantilla;
            this.entidad = new EntidadDTO(otro.entidad);
        }
    }
}
