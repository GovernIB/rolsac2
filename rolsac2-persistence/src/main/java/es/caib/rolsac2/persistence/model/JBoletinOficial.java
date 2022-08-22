package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

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
    private Integer codigo;

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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer id) {
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

}