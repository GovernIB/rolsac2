package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JTipoMediaEdificio;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "fichero-tipo-edif-trad-sequence", sequenceName = "RS2_TRATPME_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPME",
        indexes = {
                @Index(name = "RS2_TRATPME_PK", columnList = "TRTD_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTipoMediaEdificioTraduccion.FIND_BY_ID,
                query = "select p from JTipoMediaEdificioTraduccion p where p.codigo = :id")
})
public class JTipoMediaEdificioTraduccion {

    public static final String FIND_BY_ID = "JTipoMediaEdificioTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-edif-trad-sequence")
    @Column(name = "TRTD_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTD_CODTPME", nullable = false)
    private JTipoMediaEdificio tipoMediaEdificio;

    @Column(name = "TRTD_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTD_DESCRI")
    private String descripcion;

    public static List<JTipoMediaEdificioTraduccion> createInstance(List<String> idiomas) {
        List<JTipoMediaEdificioTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JTipoMediaEdificioTraduccion trad = new JTipoMediaEdificioTraduccion();
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

    public JTipoMediaEdificio getTipoMediaEdificio() {
        return tipoMediaEdificio;
    }

    public void setTipoMediaEdificio(JTipoMediaEdificio tipoMediaEdificio) {
        this.tipoMediaEdificio = tipoMediaEdificio;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtdIdioma) {
        this.idioma = trtdIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trtdDescri) {
        this.descripcion = trtdDescri;
    }

}