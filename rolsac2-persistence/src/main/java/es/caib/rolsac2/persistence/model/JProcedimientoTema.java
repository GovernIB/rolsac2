package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoTemaPK;

import javax.persistence.*;

/**
 * La clase J procedimiento tema.
 */
@Entity
@Table(name = "RS2_PRCTEM")
public class JProcedimientoTema {
    /**
     * Codigo
     */
    @EmbeddedId
    private JProcedimientoTemaPK codigo;

    /**
     * Tema
     */
    @MapsId("tema")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTM_CODTEMA", nullable = false)
    private JTema tema;

    /**
     * Procedimiento
     */
    @MapsId("procedimiento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTM_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;


    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public JProcedimientoTemaPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JProcedimientoTemaPK id) {
        this.codigo = id;
    }

    /**
     * Obtiene tema.
     *
     * @return  tema
     */
    public JTema getTema() {
        return tema;
    }

    /**
     * Establece tema.
     *
     * @param prtmCodtema  prtm codtema
     */
    public void setTema(JTema prtmCodtema) {
        this.tema = prtmCodtema;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return  procedimiento
     */
    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param prtmCodprwf  prtm codprwf
     */
    public void setProcedimiento(JProcedimientoWorkflow prtmCodprwf) {
        this.procedimiento = prtmCodprwf;
    }

}