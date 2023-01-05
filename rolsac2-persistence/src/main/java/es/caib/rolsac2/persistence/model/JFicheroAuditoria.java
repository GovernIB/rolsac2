package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * La clase J fichero auditoria.
 */
@Entity
@SequenceGenerator(name = "fichero-audit-sequence", sequenceName = "RS2_FCHAUDIT_SEQ", allocationSize = 1)
@Table(name = "RS2_FCHAUDIT",
        indexes = {
                @Index(name = "RS2_FCHAUDIT_PK_I", columnList = "FCAU_CODIGO")
        })
public class JFicheroAuditoria {
    /**
     * Codigo
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fichero-audit-sequence")
    @Column(name = "FCAU_CODIGO", nullable = false)
    private Integer codigo;

    /**
     * Ficha
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RS2_CODFCH", nullable = false)
    private JFicha ficha;

    /**
     * Fecha modificacion
     **/
    @Column(name = "FCAU_FECMOD", nullable = false)
    private LocalDate fechaModificacion;

    /**
     * Lista de modificaciones
     **/
    @Lob
    @Column(name = "FCAU_LSTMOD", nullable = false)
    private String listaModificaciones;

    /**
     * Usuario modificado
     **/
    @Column(name = "FCAU_USUMOD", nullable = false, length = 100)
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
     * @param fcauFecmod  fcau fecmod
     */
    public void setFechaModificacion(LocalDate fcauFecmod) {
        this.fechaModificacion = fcauFecmod;
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
     * @param fcauLstmod  fcau lstmod
     */
    public void setListaModificaciones(String fcauLstmod) {
        this.listaModificaciones = fcauLstmod;
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
     * @param fcauUsumod  fcau usumod
     */
    public void setUsuarioModificacion(String fcauUsumod) {
        this.usuarioModificacion = fcauUsumod;
    }

    /**
     * Obtiene ficha.
     *
     * @return  ficha
     */
    public JFicha getFicha() {
        return ficha;
    }

    /**
     * Establece ficha.
     *
     * @param ficha  ficha
     */
    public void setFicha(JFicha ficha) {
        this.ficha = ficha;
    }
}