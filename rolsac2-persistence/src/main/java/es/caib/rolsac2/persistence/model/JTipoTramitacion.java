package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Representacion de un tipo de tramitación. A nivel de clase, definimos la secuencia que utilizaremos y sus claves
 * unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-tramitacion-sequence", sequenceName = "RS2_TRMPRE_SEQ", allocationSize = 1)
@Table(name = "RS2_TRMPRE", indexes = {@Index(name = "RS2_TRMPRE_PK", columnList = "PRES_CODIGO")})
@NamedQueries({@NamedQuery(name = JTipoTramitacion.FIND_BY_ID,
        query = "select p from JTipoTramitacion p where p.codigo = :id")})
public class JTipoTramitacion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "JTipoTramitacion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-tramitacion-sequence")
    @Column(name = "PRES_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Tramitación presencial
     */
    @Column(name = "PRES_TRPRES", nullable = false, precision = 1, scale = 0)
    private boolean tramitPresencial;

    /**
     * Tramitación electrónica
     */
    @Column(name = "PRES_TRELEC", nullable = false, precision = 1, scale = 0)
    private boolean tramitElectronica;

    /**
     * URL tramitación
     */
    @Column(name = "PRES_EXTURL", nullable = false, length = 500)
    private String urlTramitacion;

    /**
     * Código plataforma tramitación
     */
    @ManyToOne
    @JoinColumn(name = "PRES_INTPTR")
    private JPlatTramitElectronica codPlatTramitacion;

    /**
     * Trámite Id
     */
    @Column(name = "PRES_INTTID", nullable = false, length = 50)
    private String tramiteId;

    /**
     * Trámite Versión
     */
    @Column(name = "PRES_INTTVE", nullable = false, precision = 3, scale = 0)
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
    @Column(name = "PRES_INTTPA", nullable = false, length = 500)
    private String tramiteParametros;

    /**
     * Tramitación electrónica
     */
    @Column(name = "PRES_PLANTI", nullable = false, precision = 1, scale = 0)
    private boolean plantilla;

    @ManyToOne
    @JoinColumn(name = "PRES_CODENTI")
    private JEntidad entidad;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
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

    public JPlatTramitElectronica getCodPlatTramitacion() {
        return codPlatTramitacion;
    }

    public void setCodPlatTramitacion(JPlatTramitElectronica codPlatTramitacion) {
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

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JTipoTramitacion))
            return false;
        JTipoTramitacion that = (JTipoTramitacion) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoTramitacion{" + "id=" + codigo + ", tramitPresencial=" + tramitPresencial + ", plantilla=" + plantilla
                + ", tramitElectronica=" + tramitElectronica + ", urlTramitacion='" + urlTramitacion + '\'' + ", codPlatTramitacion="
                + codPlatTramitacion + ", tramiteId='" + tramiteId + '\'' + ", tramiteVersion=" + tramiteVersion
                + ", tramiteParametros='" + tramiteParametros + '\'' + '}';
    }
}
