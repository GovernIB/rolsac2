package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JAyuda;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase J fichero auditoria.
 */
@Entity
@SequenceGenerator(name = "ayuda-traduccion-sequence", sequenceName = "RS2_TRAAYU_SEQ", allocationSize = 1)
@Table(name = "RS2_TRAAYU",
        indexes = {
                @Index(name = "RS2_TRAAYU_PK_I", columnList = "TAY_CODIGO")
        })
public class JAyudaTraduccion {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ayuda-traduccion-sequence")
    @Column(name = "TAY_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Ficha
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TAY_CODAYU", nullable = false)
    private JAyuda ayuda;

    /**
     * Usuario modificado
     **/
    @Column(name = "TAY_IDIOMA", nullable = false, length = 2)
    private String idioma;

    /**
     * Lista de modificaciones
     **/
    @Lob
    @Column(name = "TAY_HTML", nullable = false)
    private String html;

    public static List<JAyudaTraduccion> createInstance(List<String> idiomas) {
        List<JAyudaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JAyudaTraduccion trad = new JAyudaTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JAyuda getAyuda() {
        return ayuda;
    }

    public void setAyuda(JAyuda ayuda) {
        this.ayuda = ayuda;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}