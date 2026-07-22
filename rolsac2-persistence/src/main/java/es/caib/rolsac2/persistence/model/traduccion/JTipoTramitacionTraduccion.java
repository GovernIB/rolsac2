package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoTramitacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que
 * utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@Table(name = "RS2_TRATPTRA", indexes = {@Index(name = "RS2_TRATPTRA_PK_I", columnList = "TRTT_CODIGO")})
@SequenceGenerator(name = "tipo-tramitaciontra-sequence", sequenceName = "RS2_TRATPTRA_SEQ", allocationSize = 1)
@NamedQueries({@NamedQuery(name = JTipoTramitacionTraduccion.FIND_BY_ID, query = "select p from JTipoTramitacionTraduccion p where p.codigo = :id")})
public class JTipoTramitacionTraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoTramitacionTraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-tramitaciontra-sequence")
    @Column(name = "TRTT_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRTT_CODTPTRA")
    private JTipoTramitacion tipoTramitacion;

    @Column(name = "TRTT_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTT_DESCRI", length = 255)
    private String descripcion;

    @Column(name = "TRTT_URL", length = 512)
    private String url;

    public static List<JTipoTramitacionTraduccion> createInstance(List<String> idiomas) {
        List<JTipoTramitacionTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JTipoTramitacionTraduccion trad = new JTipoTramitacionTraduccion();
            trad.setIdioma(idioma);
            traducciones.add(trad);
        }
        return traducciones;
    }

    public static List<JTipoTramitacionTraduccion> clonar(List<JTipoTramitacionTraduccion> traducciones, JTipoTramitacion tipoTramitacion) {
        List<JTipoTramitacionTraduccion> retorno = null;
        if (traducciones != null) {
            Map<String, JTipoTramitacionTraduccion> traduccionesPorIdioma = new LinkedHashMap<>();
            for (JTipoTramitacionTraduccion otroTrad : traducciones) {
                if (otroTrad == null || otroTrad.getIdioma() == null) {
                    continue;
                }
                String idioma = otroTrad.getIdioma().trim().toLowerCase();
                if (traduccionesPorIdioma.containsKey(idioma)) {
                    continue;
                }
                JTipoTramitacionTraduccion traduccion = new JTipoTramitacionTraduccion();
                traduccion.setDescripcion(otroTrad.getDescripcion());
                traduccion.setIdioma(idioma);
                traduccion.setTipoTramitacion(tipoTramitacion);
                traduccion.setUrl(otroTrad.getUrl());
                traduccionesPorIdioma.put(idioma, traduccion);
            }
            retorno = new ArrayList<>(traduccionesPorIdioma.values());
        }
        return retorno;
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

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public JTipoTramitacion getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(JTipoTramitacion tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTipoTramitacionTraduccion that = (JTipoTramitacionTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoTramitacionTraduccion{" + "id=" + codigo + "idioma=" + idioma + "descripcion=" + descripcion + '}';
    }

}