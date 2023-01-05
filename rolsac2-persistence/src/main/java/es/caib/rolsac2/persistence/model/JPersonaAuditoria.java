package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * La clase J persona auditoria.
 */
@Entity
@SequenceGenerator(name = "persona-audit-sequence", sequenceName = "RS2_PERAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_PERAUDIT",
        indexes = {
                @Index(name = "RS2_PERAUDIT_PK_I", columnList = "PERAU_CODIGO")
        })
public class JPersonaAuditoria {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona-audit-sequence")
    @Column(name = "PERAU_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Personal
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PERAU_CODPER", nullable = false)
    private JPersonal persona;

    /**
     * Fecha de modificacion
     **/
    @Column(name = "PERAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    /**
     * Lista de modificaciones
     **/
    @Lob
    @Column(name = "PERAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    /**
     * Usuario modificado
     **/
    @Column(name = "PERAU_USUMOD", nullable = false, length = 100)
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
     * Obtiene persona.
     *
     * @return  persona
     */
    public JPersonal getPersona() {
        return persona;
    }

    /**
     * Establece persona.
     *
     * @param perauCodper  perau codper
     */
    public void setPersona(JPersonal perauCodper) {
        this.persona = perauCodper;
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
     * @param perauFecmod  perau fecmod
     */
    public void setFechaModificacion(LocalDate perauFecmod) {
        this.fechaModificacion = perauFecmod;
    }

    /**
     * Obtiene lista modificaciones.
     *
     * @return  lista modificaciones
     */
    public String getListaModificaciones() {
        return listaModificaciones;
    }

    /**
     * Establece lista modificaciones.
     *
     * @param perauLstmod  perau lstmod
     */
    public void setListaModificaciones(String perauLstmod) {
        this.listaModificaciones = perauLstmod;
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
     * @param perauUsumod  perau usumod
     */
    public void setUsuarioModificacion(String perauUsumod) {
        this.usuarioModificacion = perauUsumod;
    }

}