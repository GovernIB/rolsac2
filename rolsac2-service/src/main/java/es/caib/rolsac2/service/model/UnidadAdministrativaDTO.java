package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Dades de una Unidad Administrativa.
 *
 * @author Indra
 */
@Schema(name = "UnidadAdministrativa")
public class UnidadAdministrativaDTO {

    private Long id;

    private UnidadAdministrativaDTO padre;

    private List<UnidadAdministrativaDTO> hijos;

    private long orden;

    @NotEmpty
    @Size(max = 50)
    private Literal nombre;


    public UnidadAdministrativaDTO() {
    }

    public UnidadAdministrativaDTO(Long id) {
        this.id = id;
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
