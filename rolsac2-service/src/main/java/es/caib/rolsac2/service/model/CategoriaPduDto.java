package es.caib.rolsac2.service.model;


import java.util.Objects;

public class CategoriaPDUDTO extends ModelApi implements Cloneable {


    private Long codigo;
    private Integer orden;
    private String identificador;
    private Literal descripcion;
    private EntidadDTO entidad;

    // Default constructor
    public CategoriaPDUDTO() {
    }

    // Parameterized constructor
    public CategoriaPDUDTO(Long codigo, Integer orden, String identificador, Literal descripcion) {
        this.codigo = codigo;
        this.orden = orden;
        this.identificador = identificador;
        this.descripcion = descripcion;
    }

    public CategoriaPDUDTO(CategoriaPDUDTO categoriaPDUDTO) {
        super();
        if (categoriaPDUDTO != null) {
            this.codigo = categoriaPDUDTO.codigo;
            this.orden = categoriaPDUDTO.orden;
            this.identificador = categoriaPDUDTO.identificador;
            this.descripcion = categoriaPDUDTO.descripcion == null ? null : (Literal) categoriaPDUDTO.descripcion.clone();
        }
    }

    // Getters and Setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "JCategoriaPduDTO{" +
                "codigo=" + codigo +
                ", orden=" + orden +
                ", identif='" + identificador + '\'' +
                ", descri='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaPDUDTO that = (CategoriaPDUDTO) o;
        return Objects.equals(codigo, that.codigo) &&
                Objects.equals(orden, that.orden) &&
                Objects.equals(identificador, that.identificador) &&
                Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, orden, identificador, descripcion);
    }

    @Override
    public CategoriaPDUDTO clone() {
        return new CategoriaPDUDTO(this);
    }

    public int compareTo(CategoriaPDUDTO categoriaPDUDTO) {
        if (this.codigo == null && categoriaPDUDTO.codigo == null) {
            return 0;
        } else if (this.codigo == null) {
            return -1;
        } else if (categoriaPDUDTO.codigo == null) {
            return 1;
        } else {
            return this.codigo.compareTo(categoriaPDUDTO.codigo);
        }
    }
}
