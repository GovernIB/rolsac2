package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "boletin-sequence", sequenceName = "RS2_BOLETI_SEQ", allocationSize = 1)
@Table(name = "RS2_BOLETI",
        indexes = {
                @Index(name = "RS2_BOLETI_PK_I", columnList = "BOLE_CODIGO")
        })
public class JBoletinOficial {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boletin-sequence")
    @Column(name = "BOLE_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Identificador
     **/
    @Column(name = "BOLE_IDENTI", nullable = false, length = 50)
    private String identificador;

    /**
     * Nombre
     **/
    @Column(name = "BOLE_NOMBRE", nullable = false)
    private String nombre;

    /**
     * URL
     **/
    @Column(name = "BOLE_URL", length = 500)
    private String url;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String boleIdenti) {
        this.identificador = boleIdenti;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String boleNombre) {
        this.nombre = boleNombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String boleUrl) {
        this.url = boleUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JBoletinOficial that = (JBoletinOficial) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JBoletinOficial{" +
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}