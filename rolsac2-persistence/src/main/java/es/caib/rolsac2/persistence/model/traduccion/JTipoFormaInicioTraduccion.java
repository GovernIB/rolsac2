package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-forma-inicio-trad-sequence", sequenceName = "RS2_TRATPFI_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPFI", indexes = {@Index(name = "RS2_TRATPFI_PK_I", columnList = "TRTF_CODIGO")})
public class JTipoFormaInicioTraduccion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-forma-inicio-trad-sequence")
    @Column(name = "TRTF_CODIGO", nullable = false)
    private Long codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRTF_CODTPFI", nullable = false)
    private JTipoFormaInicio tipoFormaInicio;

    @Column(name = "TRTF_IDIOMA", nullable = false, length = 2)
    private String idioma;

    @Column(name = "TRTF_DESCRI")
    private String descripcion;

    public static List<JTipoFormaInicioTraduccion> createInstance() {
        List<JTipoFormaInicioTraduccion> traducciones = new ArrayList<>();
        for (String idioma : Constantes.IDIOMAS) {
            JTipoFormaInicioTraduccion trad = new JTipoFormaInicioTraduccion();
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

    public JTipoFormaInicio getTipoFormaInicio() {
        return tipoFormaInicio;
    }

    public void setTipoFormaInicio(JTipoFormaInicio trtfCodtpfi) {
        this.tipoFormaInicio = trtfCodtpfi;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String trtfIdioma) {
        this.idioma = trtfIdioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String trtfDescri) {
        this.descripcion = trtfDescri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JTipoFormaInicioTraduccion that = (JTipoFormaInicioTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoFormaInicioTraduccion{" + "id=" + codigo + "}";
    }
}
