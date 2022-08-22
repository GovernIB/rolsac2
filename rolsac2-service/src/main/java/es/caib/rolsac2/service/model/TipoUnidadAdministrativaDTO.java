package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un TipoUnidadAdministrativa.
 *
 * @author jsegovia
 */
@Schema(name = "TipoUnidadAdministrativa")
public class TipoUnidadAdministrativaDTO extends ModelApi {

    private Long codigo;
    private EntidadDTO entidad;
    private String identificador;
    private Literal descripcion;
    private Literal cargoMasculino;
    private Literal cargoFemenino;
    private Literal tratamientoMasculino;
    private Literal tratamientoFemenino;

    public TipoUnidadAdministrativaDTO() {
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

    public TipoUnidadAdministrativaDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public Literal getCargoMasculino() {
        return cargoMasculino;
    }

    public void setCargoMasculino(Literal cargoMasculino) {
        this.cargoMasculino = cargoMasculino;
    }

    public Literal getCargoFemenino() {
        return cargoFemenino;
    }

    public void setCargoFemenino(Literal cargoFemenino) {
        this.cargoFemenino = cargoFemenino;
    }

    public Literal getTratamientoMasculino() {
        return tratamientoMasculino;
    }

    public void setTratamientoMasculino(Literal tratamientoMasculino) {
        this.tratamientoMasculino = tratamientoMasculino;
    }

    public Literal getTratamientoFemenino() {
        return tratamientoFemenino;
    }

    public void setTratamientoFemenino(Literal tratamientoFemenino) {
        this.tratamientoFemenino = tratamientoFemenino;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoUnidadAdministrativaDTO that = (TipoUnidadAdministrativaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, entidad, identificador, descripcion, cargoMasculino, cargoFemenino, tratamientoMasculino, tratamientoFemenino);
    }

    @Override
    public String toString() {
        return "TipoUnidadAdministrativaDTO{" +
                "id=" + codigo +
                ", entidad=" + entidad +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                ", cargoMasculino=" + cargoMasculino +
                ", cargoFemenino=" + cargoFemenino +
                ", tratamientoMasculino=" + tratamientoMasculino +
                ", tratamientoFemenino=" + tratamientoFemenino +
                '}';
    }
}
