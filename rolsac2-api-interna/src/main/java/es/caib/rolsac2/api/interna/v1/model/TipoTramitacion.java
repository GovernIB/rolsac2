package es.caib.rolsac2.api.interna.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * TipoTramitacion.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "TipoTramitacion", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_TRAMITACION)
public class TipoTramitacion extends EntidadBase<TipoTramitacion> {

    private static final Logger LOG = LoggerFactory.getLogger(TipoTramitacion.class);

    /**
     * Identificador
     */
    @Schema(description = "tramiteId", name = "tramiteId", type = SchemaType.STRING, required = true)
    private String tramiteId;

    /**
     * enlace.
     **/
    @Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;

    /**
     * codigo
     **/
    @Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    /**
     * Tramitación presencial
     */
    @Schema(description = "tramitPresencial", name = "tramitPresencial", type = SchemaType.INTEGER, required = false)
    private Integer tramitPresencial;

    /**
     * Tramitación electrónica
     */
    @Schema(description = "tramitElectronica", name = "tramitElectronica", type = SchemaType.INTEGER, required = false)
    private Integer tramitElectronica;

    /**
     * Tramitacion telefonica
     */
    @Schema(description = "tramitTelefonica", name = "tramitTelefonica", type = SchemaType.INTEGER, required = false)
    private Integer tramitTelefonica;

    /**
     * Url
     */
    @Schema(description = "url", name = "url", type = SchemaType.STRING, required = false)
    private String url;

    /**
     * Fase procedimiento
     */
    @Schema(description = "faseProc", name = "faseProc", type = SchemaType.INTEGER, required = false)
    private Integer faseProc;

    /**
     * URL tramitación
     */
    @Schema(description = "urlTramitacion", name = "urlTramitacion", type = SchemaType.STRING, required = false)
    private String urlTramitacion;

    /**
     * Código plataforma tramitación
     */
    @Schema(description = "link_codPlatTramitacion", required = false)
    private Link link_codPlatTramitacion;
    @Schema(hidden = true)
    @JsonIgnore
    @XmlTransient
    private Long codPlatTramitacion;

    /**
     * Trámite Versión
     */
    @Schema(description = "tramiteVersion", name = "tramiteVersion", type = SchemaType.INTEGER, required = false)
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
    @Schema(description = "tramiteParametros", name = "tramiteParametros", type = SchemaType.STRING, required = false)
    private String tramiteParametros;

    /**
     * Indica si es una plantilla
     **/
    @Schema(description = "plantilla", name = "plantilla", type = SchemaType.INTEGER, required = false)
    private Integer plantilla;

    @Schema(description = "link_entidad", required = false)
    private Link link_entidad;
    @Schema(hidden = true)
    @JsonIgnore
    @XmlTransient
    private Long entidad;


    public TipoTramitacion(TipoTramitacionDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
        super(nodo, urlBase, idioma, hateoasEnabled);
    }

    public TipoTramitacion() {
        super();
    }

    @Override
    public void generaLinks(String urlBase) {
        link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
                null);
        link_codPlatTramitacion = this.generaLink(this.codPlatTramitacion, Constantes.ENTIDAD_PLATAFORMA, Constantes.URL_PLATAFORMA, urlBase,
                null);
    }

    @Override
    protected void addSetersInvalidos() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setId(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param enlace the enlace to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(String tramiteId) {
        this.tramiteId = tramiteId;
    }

    public Boolean getTramitPresencial() {
        return tramitPresencial != null ? tramitPresencial == 1 : null;
    }

    public void setTramitPresencial(Boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial != null ? (tramitPresencial ? 1 : 0) : null;
    }

    public Boolean getTramitElectronica() {
        return tramitElectronica != null ? tramitElectronica == 1 : null;
    }

    public void setTramitElectronica(Boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica != null ? (tramitElectronica ? 1 : 0) : null;
    }

    public Boolean getTramitTelefonica() {
        return tramitTelefonica != null ? tramitTelefonica == 1 : null;
    }

    public void setTramitTelefonica(Boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica != null ? (tramitTelefonica ? 1 : 0) : null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFaseProc() {
        return faseProc;
    }

    public void setFaseProc(Integer faseProc) {
        this.faseProc = faseProc;
    }

    public String getUrlTramitacion() {
        return urlTramitacion;
    }

    public void setUrlTramitacion(String urlTramitacion) {
        this.urlTramitacion = urlTramitacion;
    }

    public Link getLink_codPlatTramitacion() {
        return link_codPlatTramitacion;
    }

    public void setLink_codPlatTramitacion(Link link_codPlatTramitacion) {
        this.link_codPlatTramitacion = link_codPlatTramitacion;
    }

    public Long getCodPlatTramitacion() {
        return codPlatTramitacion;
    }

    public void setCodPlatTramitacion(Long codPlatTramitacion) {
        this.codPlatTramitacion = codPlatTramitacion;
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

    public Boolean getPlantilla() {
        return plantilla != null ? plantilla == 1 : null;
    }

    public void setPlantilla(Boolean plantilla) {
        this.plantilla = plantilla != null ? (plantilla ? 1 : 0) : null;
    }

    public Link getLink_entidad() {
        return link_entidad;
    }

    public void setLink_entidad(Link link_entidad) {
        this.link_entidad = link_entidad;
    }

    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }
}