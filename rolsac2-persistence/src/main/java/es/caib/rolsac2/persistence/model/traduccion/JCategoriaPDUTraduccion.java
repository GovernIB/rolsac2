package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JCategoriaPDU;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "categoriapdu-trad-sequence", sequenceName = "RS2_TRACPDU_SEQ", allocationSize = 1)
@Table(name = "RS2_TRACPDU",
        indexes = {
                @Index(name = "RS2_TRACPDU_PK", columnList = "TPDU_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JCategoriaPDUTraduccion.FIND_BY_ID,
                query = "select p from JCategoriaPDUTraduccion p where p.codigo = :id")
})
public class JCategoriaPDUTraduccion {

    public static final String FIND_BY_ID = "JCategoriaPDUTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoriapdu-trad-sequence")
    @Column(name = "TPDU_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TPDU_CATPDU", nullable = false)
    private JCategoriaPDU categoriaPDU;

    @Column(name = "TPDU_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TPDU_DESCRI", length = 255)
    private String descripcion;

    public static List<JCategoriaPDUTraduccion> createInstance(List<String> idiomas) {
        List<JCategoriaPDUTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JCategoriaPDUTraduccion trad = new JCategoriaPDUTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtmIdioma) {
        this.idioma = trtmIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trtmDescri) {
        this.descripcion = trtmDescri;
    }

    public JCategoriaPDU getCategoriaPDU() {
        return categoriaPDU;
    }

    public void setCategoriaPDU(JCategoriaPDU categoriaPDU) {
        this.categoriaPDU = categoriaPDU;
    }
}