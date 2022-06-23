package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Dades de una Unidad Administrativa.
 *
 * @author Indra
 */
@Schema(name = "UnidadAdministrativa")
public class UnidadAdministrativaDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * Entidad
     **/
    private EntidadDTO entidad;
    /**
     * Padre
     **/
    private UnidadAdministrativaDTO padre;
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
    private String responsableSexo;
    private String responsableEmail;
    /**
     * Orden
     **/
    private long orden;

    @NotEmpty
    @Size(max = 255)
    private Literal nombre;

    @Size(max = 4000)
    private Literal presentacion;

    @Size(max = 255)
    private Literal url;

    private Literal responsable;

    public UnidadAdministrativaDTO() {
    }

    public UnidadAdministrativaDTO(Long iId, String nombreCa, String nombreEs) {
        //TODO Borrar
        this.id = iId;
        this.nombre = new Literal();
        List<Traduccion> traducciones = new ArrayList<>();
        traducciones.add(new Traduccion("es", nombreEs));
        traducciones.add(new Traduccion("ca", nombreCa));
        this.nombre.setTraducciones(traducciones);
    }

    public UnidadAdministrativaDTO(Long id) {
        this.id = id;
    }

    public static UnidadAdministrativaDTO createInstance() {
        UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        ua.setNombre(Literal.createInstance());
        return ua;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getOrden() {
        return orden;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "UnidadAdministrativaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
