package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "Tema")
public class TemaDTO extends ModelApi {

    private Long codigo;

    private EntidadDTO entidad;

    private String identificador;

    private TemaDTO temaPadre;

    private Literal descripcion;

    public TemaDTO(){}

    public TemaDTO(Long codigo) {
        this.codigo = codigo;
    }

    public static TemaDTO createInstance() {
        TemaDTO tema = new TemaDTO();
        tema.setDescripcion(Literal.createInstance());
        return tema;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public TemaDTO getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(TemaDTO temaPadre) {
        this.temaPadre = temaPadre;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemaDTO temaDTO = (TemaDTO) o;
        return codigo.equals(temaDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "TemaDTO{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", identificador='" + identificador + '\'' +
                ", temaPadre=" + temaPadre +
                '}';
    }

    public int compareTo(final TemaDTO tema) {
        if (tema == null)
            throw new NullPointerException("tema");

        return Long.compare(this.getCodigo(), tema.getCodigo());
    }
}
