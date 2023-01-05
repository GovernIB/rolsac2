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
    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Entidad
     */
    private EntidadDTO entidad;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Cargo Masculino
     */
    private Literal cargoMasculino;

    /**
     * Cargo Femenino
     */
    private Literal cargoFemenino;

    /**
     * Tratamiento masculino
     */
    private Literal tratamientoMasculino;

    /**
     * Tratamiento femenino
     */
    private Literal tratamientoFemenino;

    /**
     * Instancia un nevo Tipo unidad administrativa dto.
     */
    public TipoUnidadAdministrativaDTO() {
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
     * Instantiates a new Tipo unidad administrativa dto.
     *
     * @param id  id
     */
    public TipoUnidadAdministrativaDTO(Long id) {
        this.codigo = id;
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
     * Obtiene cargo masculino.
     *
     * @return  cargo masculino
     */
    public Literal getCargoMasculino() {
        return cargoMasculino;
    }

    /**
     * Establece cargo masculino.
     *
     * @param cargoMasculino  cargo masculino
     */
    public void setCargoMasculino(Literal cargoMasculino) {
        this.cargoMasculino = cargoMasculino;
    }

    /**
     * Obtiene cargo femenino.
     *
     * @return  cargo femenino
     */
    public Literal getCargoFemenino() {
        return cargoFemenino;
    }

    /**
     * Establece cargo femenino.
     *
     * @param cargoFemenino  cargo femenino
     */
    public void setCargoFemenino(Literal cargoFemenino) {
        this.cargoFemenino = cargoFemenino;
    }

    /**
     * Obtiene tratamiento masculino.
     *
     * @return  tratamiento masculino
     */
    public Literal getTratamientoMasculino() {
        return tratamientoMasculino;
    }

    /**
     * Establece tratamiento masculino.
     *
     * @param tratamientoMasculino  tratamiento masculino
     */
    public void setTratamientoMasculino(Literal tratamientoMasculino) {
        this.tratamientoMasculino = tratamientoMasculino;
    }

    /**
     * Obtiene tratamiento femenino.
     *
     * @return  tratamiento femenino
     */
    public Literal getTratamientoFemenino() {
        return tratamientoFemenino;
    }

    /**
     * Establece tratamiento femenino.
     *
     * @param tratamientoFemenino  tratamiento femenino
     */
    public void setTratamientoFemenino(Literal tratamientoFemenino) {
        this.tratamientoFemenino = tratamientoFemenino;
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
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
