package es.caib.rolsac2.persistence.model.traduccion;

import es.caib.rolsac2.persistence.model.BaseEntity;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.service.model.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representacion de un personal. A nivel de clase, definimos la secuencia que utilizaremos y sus claves unicas.
 *
 * @author Indra
 */
@Entity
@SequenceGenerator(name = "tipo-uatra-sequence", sequenceName = "RS2_TIPOUNA_SEQ", allocationSize = 1)
@Table(name = "RS2_TRATPUA",
        indexes = {
                @Index(name = "RS2_TRATPUA_PK", columnList = "TRTU_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JTipoUnidadAdministrativaTraduccion.FIND_BY_ID,
                query = "select p from JTipoUnidadAdministrativaTraduccion p where p.codigo = :id")
})
public class JTipoUnidadAdministrativaTraduccion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "TipoUnidadAdministrativaTraduccion.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo-uatra-sequence")
    @Column(name = "TRTU_CODIGO", nullable = false, length = 10)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "TRTU_CODTPUA")
    private JTipoUnidadAdministrativa tipoUnidadAdministrativa;


    @Column(name = "TRTU_IDIOMA", length = 2)
    private String idioma;

    @Column(name = "TRTU_DESCRI", length = 255)
    private String descripcion;


    @Column(name = "TRTU_CRGMAS", length = 255)
    private String cargoMasculino;


    @Column(name = "TRTU_CRGFEM", length = 255)
    private String cargoFemenino;


    @Column(name = "TRTU_TRCMAS", length = 255)
    private String tratamientoMasculino;


    @Column(name = "TRTU_TRCFEM", length = 255)
    private String tratamientoFemenino;


    public static List<JTipoUnidadAdministrativaTraduccion> createInstance(List<String> idiomas) {
        List<JTipoUnidadAdministrativaTraduccion> traducciones = new ArrayList<>();
        for (String idioma : idiomas) {
            JTipoUnidadAdministrativaTraduccion trad = new JTipoUnidadAdministrativaTraduccion();
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

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCargoMasculino() {
        return cargoMasculino;
    }

    public void setCargoMasculino(String cargoMasculino) {
        this.cargoMasculino = cargoMasculino;
    }

    public String getCargoFemenino() {
        return cargoFemenino;
    }

    public void setCargoFemenino(String cargoFemenino) {
        this.cargoFemenino = cargoFemenino;
    }

    public String getTratamientoMasculino() {
        return tratamientoMasculino;
    }

    public void setTratamientoMasculino(String tratamientoMasculino) {
        this.tratamientoMasculino = tratamientoMasculino;
    }

    public String getTratamientoFemenino() {
        return tratamientoFemenino;
    }

    public void setTratamientoFemenino(String tratamientoFemenino) {
        this.tratamientoFemenino = tratamientoFemenino;
    }

    public JTipoUnidadAdministrativa getTipoUnidadAdministrativa() {
        return tipoUnidadAdministrativa;
    }

    public void setTipoUnidadAdministrativa(JTipoUnidadAdministrativa tipoUnidadAdministrativa) {
        this.tipoUnidadAdministrativa = tipoUnidadAdministrativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JTipoUnidadAdministrativaTraduccion that = (JTipoUnidadAdministrativaTraduccion) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "JTipoUnidadAdministrativaTraduccion{" +
                "id=" + codigo +
                "idioma=" + idioma +
                "descripcion=" + descripcion +
                "cargoMasculino=" + cargoMasculino +
                "cargoFemenino=" + cargoFemenino +
                "tratamientoMasculino=" + tratamientoMasculino +
                "tratamientoMasculino=" + tratamientoMasculino +
                '}';
    }

}