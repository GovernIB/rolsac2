package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JProcedimientoTasa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA para la tabla RS2_TRAPRTX (Traducciones de tasas de procedimiento).
 */
@Entity
@SequenceGenerator(name = "procedimiento-tasa-trad-sequence", sequenceName = "RS2_TRAPRTX_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAPRTX", indexes = {@Index(name = "RS2_TRAPRTX_PK_I", columnList = "TRTX_CODIGO")})
public class JProcedimientoTasaTraduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tasa-trad-sequence")
    @Column(name = "TRTX_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTX_CODPRTX", nullable = false)
    private JProcedimientoTasa tasa;

    @Column(name = "TRTX_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTX_IDENTI", nullable = false, length = 256)
    private String identificador;

    @Column(name = "TRTX_DESCRI", length = 4000)
    private String descripcion;

    @Column(name = "TRTX_FORPAG", length = 4000)
    private String formaPago;

    @Column(name = "TRTX_URL", length = 1000)
    private String url;

    public static List<JProcedimientoTasaTraduccion> createInstance(List<String> idiomas, JProcedimientoTasa tasa) {
        List<JProcedimientoTasaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JProcedimientoTasaTraduccion trad = new JProcedimientoTasaTraduccion();
            trad.setIdioma(idioma);
            trad.setTasa(tasa);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public JProcedimientoTasa getTasa() {
        return tasa;
    }

    public void setTasa(JProcedimientoTasa tasa) {
        this.tasa = tasa;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
