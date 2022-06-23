package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JEdificio;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "edificio-traduccion-sequence", sequenceName = "RS2_TRAEDIF_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAEDIF",
        indexes = {
                @Index(name = "RS2_TRAEDIF_PK_I", columnList = "TRED_CODIGO")
        })
public class JEdificioTraduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edificio-traduccion-sequence")
    @Column(name = "TRED_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRED_CODEDIF", nullable = false)
    private JEdificio edificio;

    @Column(name = "TRED_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRED_NOMBRE", length = 4000)
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JEdificio getEdificio() {
        return edificio;
    }

    public void setEdificio(JEdificio tredCodedif) {
        this.edificio = tredCodedif;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String tredIdioma) {
        this.idioma = tredIdioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String tredNombre) {
        this.nombre = tredNombre;
    }

}