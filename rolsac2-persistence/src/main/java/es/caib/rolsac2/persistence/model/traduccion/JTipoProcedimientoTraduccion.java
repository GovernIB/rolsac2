package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoProcedimiento;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "tipo-procedimiento-traduccion-sequence", sequenceName = "RS2_TRATPPR_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPPR",
        indexes = {
                @Index(name = "RS2_TRATPPR_SEQ", columnList = "TRTP_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoProcedimientoTraduccion.FIND_BY_ID,
                query = "select p from JTipoProcedimientoTraduccion p where p.codigo = :id")
})
public class JTipoProcedimientoTraduccion extends BaseEntity {

    public static final String FIND_BY_ID = "TipoProcedimientoTraduccion.FIND_BY_ID";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-procedimiento-traduccion-sequence")
    @Column(name = "TRTP_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRTP_CODTPPR")
    private JTipoProcedimiento tipoProcedimiento;

    @Column(name = "TRTP_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTP_DESCR", length = 255)
    private String descripcion;

    public static List<JTipoProcedimientoTraduccion> createInstance(List<String> idiomas) {
        List<JTipoProcedimientoTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JTipoProcedimientoTraduccion trad = new JTipoProcedimientoTraduccion();
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

    public JTipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(JTipoProcedimiento tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JTipoProcedimientoTraduccion that = (JTipoProcedimientoTraduccion) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(tipoProcedimiento,
                that.tipoProcedimiento) && Objects.equals(idioma, that.idioma) && Objects.equals(descripcion,
                that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipoProcedimiento, idioma, descripcion);
    }
}
