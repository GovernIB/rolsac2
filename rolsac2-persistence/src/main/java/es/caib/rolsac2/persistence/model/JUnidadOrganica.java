package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Modelo para el guardado del organigrama DIR3.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "unidad-organica-sequence", sequenceName = "RS2_UNIORG_SEQ", allocationSize = 1)
@Table(name = "RS2_UNIORG", indexes = {@Index(name = "RS2_UNIORG_PK", columnList = "ORG_CODDIR3")})
public class JUnidadOrganica extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidad-organica-sequence")
    @Column(name = "ORG_CODIGO", nullable = false, length = 10)
    private Long codigo;

    /**
     * Código DIR3 de la unidad
     */
    @Id
    @Column(name = "ORG_CODDIR3", nullable = false, length = 50)
    private String codigoDir3;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="ORG_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Código DIR3 del padre de la unidad
     */
    @Column(name = "ORG_CODPADRE", length = 50)
    private String codigoDir3Padre;

    /**
     * Denominación de la unidad administrativa
     */
    @Column(name = "ORG_DENOM")
    private String denominacion;

    /**
     * Versión de la unidad administrativa
     */
    @Column(name = "ORG_VERSION")
    private Integer version;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCodigoDir3() {
        return codigoDir3;
    }

    public void setCodigoDir3(String codigoDir3) {
        this.codigoDir3 = codigoDir3;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    public String getCodigoDir3Padre() {
        return codigoDir3Padre;
    }

    public void setCodigoDir3Padre(String codigoDir3Padre) {
        this.codigoDir3Padre = codigoDir3Padre;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUnidadOrganica that = (JUnidadOrganica) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(codigoDir3, that.codigoDir3) && Objects.equals(entidad, that.entidad) && Objects.equals(codigoDir3Padre, that.codigoDir3Padre) && Objects.equals(denominacion, that.denominacion) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, codigoDir3, entidad, codigoDir3Padre, denominacion, version);
    }

    @Override
    public String toString() {
        return "JUnidadOrganica{" +
                "codigo=" + codigo +
                ", codigoDir3='" + codigoDir3 + '\'' +
                ", entidad=" + entidad +
                ", codigoDir3Padre='" + codigoDir3Padre + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", version=" + version +
                '}';
    }
}
