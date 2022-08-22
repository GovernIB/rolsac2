package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Dades de una Unidad Administrativa.
 *
 * @author Indra
 */
@Schema(name = "UnidadAdministrativa")
public class UnidadAdministrativaDTO extends ModelApi {

    /**
     * ID
     */
    private Long codigo;

    /**
     * Entidad
     **/
    private EntidadDTO entidad;
    /**
     * Padre
     **/
    private UnidadAdministrativaDTO padre;
    /**
     * Código DIR3.
     **/
    private String codigoDIR3;
    /**
     * Tipo de UA
     **/
    private TipoUnidadAdministrativaDTO tipo;
    /**
     * Hijos.
     **/
    private List<UnidadAdministrativaDTO> hijos;
    /* Identificador funcional **/
    private String identificador;
    /**
     * Abreviatura entidad
     **/
    private String abreviatura;
    /**
     * Telefono
     **/
    private String telefono;
    /**
     * Fax
     **/
    private String fax;
    /**
     * Email
     **/
    private String email;
    /**
     * Dominio
     **/
    private String dominio;
    /**
     * Responsable INFO
     **/
    private String responsableNombre;
    private TipoSexoDTO responsableSexo;
    private String responsableEmail;
    /**
     * Orden
     **/
    private Integer orden;

    private Literal nombre;

    private Literal presentacion;

    private Literal url;

    private Literal responsable;

    public UnidadAdministrativaDTO() {
    }

    public UnidadAdministrativaDTO(Long iId, String nombreCa, String nombreEs) {
        // TODO Borrar
        this.codigo = iId;
        this.nombre = new Literal();
        List<Traduccion> traducciones = new ArrayList<>();
        traducciones.add(new Traduccion("es", nombreEs));
        traducciones.add(new Traduccion("ca", nombreCa));
        this.nombre.setTraducciones(traducciones);
    }

    public UnidadAdministrativaDTO(Long id) {
        this.codigo = id;
    }

    public static UnidadAdministrativaDTO createInstance() {
        UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        ua.setNombre(Literal.createInstance());
        return ua;
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Literal getNombre() {
        return nombre;
    }

    public UnidadAdministrativaDTO getPadre() {
        return padre;
    }

    public void setPadre(UnidadAdministrativaDTO padre) {
        this.padre = padre;
    }

    public List<UnidadAdministrativaDTO> getHijos() {
        return hijos;
    }

    public void setHijos(List<UnidadAdministrativaDTO> hijos) {
        this.hijos = hijos;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public TipoUnidadAdministrativaDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoUnidadAdministrativaDTO tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    public TipoSexoDTO getResponsableSexo() {
        return responsableSexo;
    }

    public void setResponsableSexo(TipoSexoDTO responsableSexo) {
        this.responsableSexo = responsableSexo;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    public Literal getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Literal presentacion) {
        this.presentacion = presentacion;
    }

    public Literal getUrl() {
        return url;
    }

    public void setUrl(Literal url) {
        this.url = url;
    }

    public Literal getResponsable() {
        return responsable;
    }

    public void setResponsable(Literal responsable) {
        this.responsable = responsable;
    }

    public String getCodigoDIR3() {
        return codigoDIR3;
    }

    public void setCodigoDIR3(String codigoDIR3) {
        this.codigoDIR3 = codigoDIR3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadAdministrativaDTO that = (UnidadAdministrativaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "UnidadAdministrativaDTO{" +
                "id=" + codigo +
                '}';
    }

    public int compareTo(final UnidadAdministrativaDTO ua) {
        if (ua == null)
            throw new NullPointerException("ua");

        return Long.compare(this.getOrden(), ua.getOrden());
    }
}
