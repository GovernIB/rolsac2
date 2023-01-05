package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;


/**
 * The type Plat tramit electronica grid dto.
 */
@Schema(name = "PlatTramitElectronicaGrid")
public class PlatTramitElectronicaGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Codigo de la entidad
     */
    private String codEntidad;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Url de acceso
     */
    private Literal urlAcceso;

    /**
     * Instantiates a new Plat tramit electronica grid dto.
     */
    public PlatTramitElectronicaGridDTO(){}

    /**
     * Instancia una nueva Plat tramit electronica grid dto.
     *
     * @param codigo         codigo
     * @param identificador  identificador
     */
    public PlatTramitElectronicaGridDTO(Long codigo, String identificador) {
        this.codigo = codigo;
        this.identificador = identificador;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
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
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene cod entidad.
     *
     * @return  cod entidad
     */
    public String getCodEntidad() {
        return codEntidad;
    }

    /**
     * Establece cod entidad.
     *
     * @param codEntidad  cod entidad
     */
    public void setCodEntidad(String codEntidad) {
        this.codEntidad = codEntidad;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene url acceso.
     *
     * @return  url acceso
     */
    public Literal getUrlAcceso() {
        return urlAcceso;
    }

    /**
     * Establece url acceso.
     *
     * @param urlAcceso  url acceso
     */
    public void setUrlAcceso(Literal urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatTramitElectronicaGridDTO that = (PlatTramitElectronicaGridDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, codEntidad, descripcion, urlAcceso);
    }
}
