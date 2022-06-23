package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "ua-hist-sequence", sequenceName = "RS2_UNAHIS_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAHIS",
        indexes = {
                @Index(name = "RS2_UNAHIS_PK_I", columnList = "UAHI_CODIGO")
        }
)
public class JUnidadAdministrativaHistorico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-hist-sequence")
    @Column(name = "UAHI_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAHI_CODUAOLD", nullable = false)
    private JUnidadAdministrativa unidadAdministrativaAntigua;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAHI_CODUANEW", nullable = false)
    private JUnidadAdministrativa unidadAdministrativaNueva;

    @Column(name = "UAHI_EXISTNEW", nullable = false)
    private Boolean codigoYaExistia = false;

    @Column(name = "UAHI_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    @Column(name = "UAHI_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativaAntigua() {
        return unidadAdministrativaAntigua;
    }

    public void setUnidadAdministrativaAntigua(JUnidadAdministrativa uahiCoduaold) {
        this.unidadAdministrativaAntigua = uahiCoduaold;
    }

    public JUnidadAdministrativa getUnidadAdministrativaNueva() {
        return unidadAdministrativaNueva;
    }

    public void setUnidadAdministrativaNueva(JUnidadAdministrativa uahiCoduanew) {
        this.unidadAdministrativaNueva = uahiCoduanew;
    }

    public Boolean getCodigoYaExistia() {
        return codigoYaExistia;
    }

    public void setCodigoYaExistia(Boolean uahiExistnew) {
        this.codigoYaExistia = uahiExistnew;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate uahiFecmod) {
        this.fechaModificacion = uahiFecmod;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String uahiUsumod) {
        this.usuarioModificacion = uahiUsumod;
    }

}