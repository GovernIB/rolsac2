package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-silencionadmvotra-sequence", sequenceName = "RS2_TRATPSA_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPSA",
        indexes = {
                @Index(name = "RS2_TRATPSA_PK", columnList = "TRTS_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoSilencioAdministrativoTraduccion.FIND_BY_ID,
                query = "select p from JTipoSilencioAdministrativoTraduccion p where p.id = :id")
})
public class JTipoSilencioAdministrativoTraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoSilencioAdministrativoTraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-silencionadmvotra-sequence")
    @Column(name = "TRTS_CODIGO", nullable = false, length = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRTS_CODTPSA")
    private JTipoSilencioAdministrativo tipoSilencioAdministrativo;

    @Column(name = "TRTS_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTS_DESCRI", length = 4000)
    private String descripcion;

    public static List<JTipoSilencioAdministrativoTraduccion> createInstance() {
        List<JTipoSilencioAdministrativoTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoSilencioAdministrativoTraduccion trad = new JTipoSilencioAdministrativoTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public JTipoSilencioAdministrativo getTipoSilencioAdministrativo() {
        return tipoSilencioAdministrativo;
    }

    public void setTipoSilencioAdministrativo(JTipoSilencioAdministrativo tipoSilencioAdministrativo) {
        this.tipoSilencioAdministrativo = tipoSilencioAdministrativo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "JTipoSilencioAdministrativoTraduccion{" +
                "id=" + id +
                "idioma=" + idioma +
                "descripcion=" + descripcion +
                '}';
    }

}