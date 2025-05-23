package es.caib.rolsac2.service.model;


import es.caib.rolsac2.service.utils.UtilComparador;

import java.util.Objects;

public class CategoriaPDUGridDTO extends ModelApi implements Cloneable, Comparable<CategoriaPDUGridDTO> {


    private Long codigo;
    private Integer orden;
    private String identificador;
    private Literal descripcion;
    private String entidad;

    // Default constructor
    public CategoriaPDUGridDTO() {
    }

    // Parameterized constructor
    public CategoriaPDUGridDTO(Long codigo, Integer orden, String identificador, Literal descripcion) {
        this.codigo = codigo;
        this.orden = orden;
        this.identificador = identificador;
        this.descripcion = descripcion;
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

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
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
        CategoriaPDUGridDTO that = (CategoriaPDUGridDTO) o;
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
    public int compareTo(CategoriaPDUGridDTO data2) {
        if (data2 == null) {
            return 1;
        }

        if (UtilComparador.compareTo(this.getOrden(), data2.getOrden()) != 0) {
            return UtilComparador.compareTo(this.getOrden(), data2.getOrden());
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion()) != 0) {
            return UtilComparador.compareTo(this.getDescripcion(), data2.getDescripcion());
        }

        if (UtilComparador.compareTo(this.getEntidad(), data2.getEntidad()) != 0) {
            return UtilComparador.compareTo(this.getEntidad(), data2.getEntidad());
        }

        return 0;
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        CategoriaPDUGridDTO tipo = new CategoriaPDUGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        tipo.setEntidad(this.getEntidad());
        this.setOrden(this.getOrden());
        if (this.getDescripcion() != null) {
            tipo.setDescripcion((Literal) this.getDescripcion().clone());
        }
        return tipo;
    }
}
