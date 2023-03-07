package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * El tipo Tipo tramitacion dto.
 *
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
     * Url
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
     */
    public TipoTramitacionDTO() {
        // Vacio
    }

    /**
     * Instantiates a new Tipo tramitacion dto.
     *
     * @param id id
     */
    public TipoTramitacionDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Create instance tipo tramitacion dto.
     *
     * @param idiomasPermitidosList idiomas permitidos list
     * @return tipo tramitacion dto
     */
    public static TipoTramitacionDTO createInstance(List<String> idiomasPermitidosList) {
        TipoTramitacionDTO tipoTramitacion = new TipoTramitacionDTO();
        tipoTramitacion.setTramitPresencial(false);
        tipoTramitacion.setTramitElectronica(false);
        tipoTramitacion.setTramitTelefonica(false);
        tipoTramitacion.setDescripcion(Literal.createInstance(idiomasPermitidosList));
        tipoTramitacion.setUrl(Literal.createInstance(idiomasPermitidosList));
        return tipoTramitacion;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Is tramit presencial boolean.
     *
     * @return boolean
     */
    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    /**
     * Establece tramit presencial.
     *
     * @param tramitPresencial tramit presencial
     */
    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    /**
     * Is tramit electronica boolean.
     *
     * @return boolean
     */
    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    /**
     * Establece tramit electronica.
     *
     * @param tramitElectronica tramit electronica
     */
    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    /**
     * Obtiene url tramitacion.
     *
     * @return url tramitacion
     */
    public String getUrlTramitacion() {
        return urlTramitacion;
    }

    /**
     * Establece url tramitacion.
     *
     * @param urlTramitacion url tramitacion
     */
    public void setUrlTramitacion(String urlTramitacion) {
        this.urlTramitacion = urlTramitacion;
    }

    /**
     * Obtiene cod plat tramitacion.
     *
     * @return cod plat tramitacion
     */
    public PlatTramitElectronicaDTO getCodPlatTramitacion() {
        return codPlatTramitacion;
    }

    /**
     * Establece cod plat tramitacion.
     *
     * @param codPlatTramitacion cod plat tramitacion
     */
    public void setCodPlatTramitacion(PlatTramitElectronicaDTO codPlatTramitacion) {
        this.codPlatTramitacion = codPlatTramitacion;
    }

    /**
     * Obtiene tramite id.
     *
     * @return tramite id
     */
    public String getTramiteId() {
        return tramiteId;
    }

    /**
     * Establece tramite id.
     *
     * @param tramiteId tramite id
     */
    public void setTramiteId(String tramiteId) {
        this.tramiteId = tramiteId;
    }

    /**
     * Obtiene tramite version.
     *
     * @return tramite version
     */
    public Integer getTramiteVersion() {
        return tramiteVersion;
    }

    /**
     * Establece tramite version.
     *
     * @param tramiteVersion tramite version
     */
    public void setTramiteVersion(Integer tramiteVersion) {
        this.tramiteVersion = tramiteVersion;
    }

    /**
     * Obtiene tramite parametros.
     *
     * @return tramite parametros
     */
    public String getTramiteParametros() {
        return tramiteParametros;
    }

    /**
     * Establece tramite parametros.
     *
     * @param tramiteParametros tramite parametros
     */
    public void setTramiteParametros(String tramiteParametros) {
        this.tramiteParametros = tramiteParametros;
    }

    /**
     * Is plantilla boolean.
     *
     * @return boolean
     */
    public boolean isPlantilla() {
        return plantilla;
    }

    /**
     * Establece plantilla.
     *
     * @param plantilla plantilla
     */
    public void setPlantilla(boolean plantilla) {
        this.plantilla = plantilla;
    }

    /**
     * Obtiene entidad.
     *
     * @return entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad entidad
     */
    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene url.
     *
     * @return url
     */
    public Literal getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url url
     */
    public void setUrl(Literal url) {
        this.url = url;
    }

    /**
     * Obtiene fase proc.
     *
     * @return fase proc
     */
    public final Integer getFaseProc() {
        return faseProc;
    }

    /**
     * Establece fase proc.
     *
     * @param faseProc fase proc
     */
    public final void setFaseProc(Integer faseProc) {
        this.faseProc = faseProc;
    }

    /**
     * Is tramit telefonica boolean.
     *
     * @return boolean
     */
    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    /**
     * Establece tramit telefonica.
     *
     * @param tramitTelefonica tramit telefonica
     */
    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public TipoTramitacionDTO clone() {
        return new TipoTramitacionDTO(this);
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

    /**
     * Instantiates a new Tipo tramitacion dto.
     *
     * @param otro otro
     */
    public TipoTramitacionDTO(TipoTramitacionDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
            this.tramitPresencial = otro.tramitPresencial;
            this.tramitElectronica = otro.tramitElectronica;
            this.urlTramitacion = otro.urlTramitacion;
            this.codPlatTramitacion = otro.codPlatTramitacion == null ? null : (PlatTramitElectronicaDTO) otro.codPlatTramitacion.clone();
            this.tramiteId = otro.tramiteId;
            this.tramiteVersion = otro.tramiteVersion;
            this.tramiteParametros = otro.tramiteParametros;
            this.plantilla = otro.plantilla;
            this.entidad = otro.entidad == null ? null : (EntidadDTO) otro.entidad.clone();
            this.url = otro.url == null ? null : (Literal) otro.url.clone();
        }
    }

    public int compareTo(TipoTramitacionDTO data2) {

        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        if (UtilComparador.compareTo(this.getFaseProc(), data2.getFaseProc()) != 0) {
            return UtilComparador.compareTo(this.getFaseProc(), data2.getFaseProc());
        }
        if (UtilComparador.compareTo(this.getTramiteId(), data2.getTramiteId()) != 0) {
            return UtilComparador.compareTo(this.getTramiteId(), data2.getTramiteId());
        }
        if (UtilComparador.compareTo(this.getUrlTramitacion(), data2.getUrlTramitacion()) != 0) {
            return UtilComparador.compareTo(this.getUrlTramitacion(), data2.getUrlTramitacion());
        }
        if (UtilComparador.compareTo(this.getTramiteParametros(), data2.getTramiteParametros()) != 0) {
            return UtilComparador.compareTo(this.getTramiteParametros(), data2.getTramiteParametros());
        }
        if (UtilComparador.compareTo(this.getTramiteVersion(), data2.getTramiteVersion()) != 0) {
            return UtilComparador.compareTo(this.getTramiteVersion(), data2.getTramiteVersion());
        }
        if (UtilComparador.compareTo(this.getCodPlatTramitacion(), data2.getCodPlatTramitacion()) != 0) {
            return UtilComparador.compareTo(this.getCodPlatTramitacion(), data2.getCodPlatTramitacion());
        }
        return 0;

    }
}
