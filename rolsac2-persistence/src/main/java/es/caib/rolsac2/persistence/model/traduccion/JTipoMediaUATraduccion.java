package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.JTipoMediaUA;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "fichero-tipo-ua-trad-sequence", sequenceName = "RS2_TRATPMU_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPMU",
        indexes = {
                @Index(name = "RS2_TRATPMU_PK", columnList = "TRTM_CODIGO")
        })
@NamedQueries({
        @NamedQuery(name = JTipoMediaUATraduccion.FIND_BY_ID,
                query = "select p from JTipoMediaUATraduccion p where p.codigo = :id")
})
public class JTipoMediaUATraduccion {

    public static final String FIND_BY_ID = "JTipoMediaUATraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-tipo-ua-trad-sequence")
    @Column(name = "TRTM_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTM_CODTPMU", nullable = false)
    private JTipoMediaUA tipoMediaUA;

    @Column(name = "TRTM_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTM_DESCRI")
    private String descripcion;

    public static List<JTipoMediaUATraduccion> createInstance() {
        List<JTipoMediaUATraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoMediaUATraduccion trad = new JTipoMediaUATraduccion();
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

    public JTipoMediaUA getTipoMediaUA() {
        return tipoMediaUA;
    }

    public void setTipoMediaUA(JTipoMediaUA tipoMediaUA) {
        this.tipoMediaUA = tipoMediaUA;
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

}