package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JTipoMediaFicha;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "fichero-tipo-fic-trad-sequence", sequenceName = "RS2_TRATPMF_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPMF",
        indexes = {
                @Index(name = "RS2_TRATPMF_PK", columnList = "TRTH_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTipoMediaFichaTraduccion.FIND_BY_ID,
                query = "select p from JTipoMediaFichaTraduccion p where p.codigo = :id")
})
public class JTipoMediaFichaTraduccion {

    public static final String FIND_BY_ID = "JTipoMediaFichaTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-fic-trad-sequence")
    @Column(name = "TRTH_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTH_CODTPMF", nullable = false)
    private JTipoMediaFicha tipoMediaFicha;

    @Column(name = "TRTH_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTH_DESCRI")
    private String descripcion;

    public static List<JTipoMediaFichaTraduccion> createInstance(List<String> idiomas) {
        List<JTipoMediaFichaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JTipoMediaFichaTraduccion trad = new JTipoMediaFichaTraduccion();
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

    public JTipoMediaFicha getTipoMediaFicha() {
        return tipoMediaFicha;
    }

    public void setTipoMediaFicha(JTipoMediaFicha tipoMediaFicha) {
        this.tipoMediaFicha = tipoMediaFicha;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trthIdioma) {
        this.idioma = trthIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trthDescri) {
        this.descripcion = trthDescri;
    }

}