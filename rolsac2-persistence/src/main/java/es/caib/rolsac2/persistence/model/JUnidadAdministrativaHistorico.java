package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * La clase J unidad administrativa historico.
 */
@Entity
@SequenceGenerator(name = "ua-hist-sequence", sequenceName = "RS2_UNAHIS_SEQ", allocationSize = 1)
@Table(name = "RS2_UNAHIS",
        indexes = {
                @Index(name = "RS2_UNAHIS_PK_I", columnList = "UAHI_CODIGO")
        }
)
public class JUnidadAdministrativaHistorico {
    /**
     * Codigo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ua-hist-sequence")
    @Column(name = "UAHI_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Unidad administrativa antigua
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAHI_CODUAOLD", nullable = false)
    private JUnidadAdministrativa unidadAdministrativaAntigua;

    /**
     * Unidad administrativa
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAHI_CODUANEW", nullable = false)
    private JUnidadAdministrativa unidadAdministrativaNueva;

    /**
     * Codigo ya existe
     */
    @Column(name = "UAHI_EXISTNEW", nullable = false)
    private Boolean codigoYaExistia = false;

    /**
     * Fecha de modificacion
     */
    @Column(name = "UAHI_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    /**
     * Usuario modificado
     */
    @Column(name = "UAHI_USUMOD", nullable = false, length = 100)
    private String usuarioModificacion;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(Integer id) {
        this.codigo = id;
    }

    /**
     * Obtiene unidad administrativa antigua.
     *
     * @return  unidad administrativa antigua
     */
    public JUnidadAdministrativa getUnidadAdministrativaAntigua() {
        return unidadAdministrativaAntigua;
    }

    /**
     * Establece unidad administrativa antigua.
     *
     * @param uahiCoduaold  uahi coduaold
     */
    public void setUnidadAdministrativaAntigua(JUnidadAdministrativa uahiCoduaold) {
        this.unidadAdministrativaAntigua = uahiCoduaold;
    }

    /**
     * Obtiene unidad administrativa nueva.
     *
     * @return  unidad administrativa nueva
     */
    public JUnidadAdministrativa getUnidadAdministrativaNueva() {
        return unidadAdministrativaNueva;
    }

    /**
     * Establece unidad administrativa nueva.
     *
     * @param uahiCoduanew  uahi coduanew
     */
    public void setUnidadAdministrativaNueva(JUnidadAdministrativa uahiCoduanew) {
        this.unidadAdministrativaNueva = uahiCoduanew;
    }

    /**
     * Obtiene codigo ya existia.
     *
     * @return  codigo ya existia
     */
    public Boolean getCodigoYaExistia() {
        return codigoYaExistia;
    }

    /**
     * Establece codigo ya existia.
     *
     * @param uahiExistnew  uahi existnew
     */
    public void setCodigoYaExistia(Boolean uahiExistnew) {
        this.codigoYaExistia = uahiExistnew;
    }

    /**
     * Obtiene fecha modificacion.
     *
     * @return  fecha modificacion
     */
    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Establece fecha modificacion.
     *
     * @param uahiFecmod  uahi fecmod
     */
    public void setFechaModificacion(LocalDate uahiFecmod) {
        this.fechaModificacion = uahiFecmod;
    }

    /**
     * Obtiene usuario modificacion.
     *
     * @return  usuario modificacion
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * Establece usuario modificacion.
     *
     * @param uahiUsumod  uahi usumod
     */
    public void setUsuarioModificacion(String uahiUsumod) {
        this.usuarioModificacion = uahiUsumod;
    }

}