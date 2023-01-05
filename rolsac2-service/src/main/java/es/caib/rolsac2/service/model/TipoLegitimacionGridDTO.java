package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * El tipo Tipo legitimacion grid dto.
 */
@Schema(name = "TipoLegitimacionGrid")
public class TipoLegitimacionGridDTO extends ModelApi {

    private Long codigo;
    private String identificador;
    private Literal descripcion;

    /**
     * Instancia un nuevo Tipo legitimacion grid dto.
     */
    public TipoLegitimacionGridDTO() {
    }

    /**
     * Instancia un nuevo Tipo legitimacion grid dto.
     *
     * @param id             id
     * @param identificador  identificador
     */
    public TipoLegitimacionGridDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
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

    @Override
    public String toString() {
        return "TipoLegitimacionGridDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
