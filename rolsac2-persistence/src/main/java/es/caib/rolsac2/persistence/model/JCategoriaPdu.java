package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RS2_CATPDU")
public class JCategoriaPdu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RS2_CATPDU_SEQ")
    @SequenceGenerator(name = "RS2_CATPDU_SEQ", sequenceName = "RS2_CATPDU_SEQ", allocationSize = 1)
    @Column(name = "CATPDU_CODIGO")
    private Long codigo;

    @Column(name = "CATPDU_ORDEN", precision = 3)
    private Integer orden;

    @Column(name = "CATPDU_IDENTIF", length = 100)
    private String identificador;

    @Column(name = "CATPDU_DESCRI", length = 250)
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JCategoriaPdu jCategoria = (JCategoriaPdu) o;
        return codigo.equals(jCategoria.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JCategoriaPdu{" +
                "codigo=" + codigo +
                ", orden=" + orden +
                ", identificador='" + identificador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
