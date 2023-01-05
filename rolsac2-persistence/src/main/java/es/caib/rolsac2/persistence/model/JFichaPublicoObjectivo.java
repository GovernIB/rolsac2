package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JFichaPublicoObjectivoPK;

import javax.persistence.*;

/**
 * La clase J ficha publico objectivo.
 */
@Entity
@Table(name = "RS2_FCHPOB")
public class JFichaPublicoObjectivo {
    /**
     * Codigo
     **/
    @EmbeddedId
    private JFichaPublicoObjectivoPK codigo;

    /**
     * Tipo publico objetivo
     **/
    @MapsId("tipoPublicoObjetivo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCPO_TIPOPO", nullable = false)
    private JTipoPublicoObjetivo tipoPublicoObjectivo;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public JFichaPublicoObjectivoPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JFichaPublicoObjectivoPK id) {
        this.codigo = id;
    }

    /**
     * Obtiene tipo publico objectivo.
     *
     * @return  tipo publico objectivo
     */
    public JTipoPublicoObjetivo getTipoPublicoObjectivo() {
        return tipoPublicoObjectivo;
    }

    /**
     * Establece tipo publico objectivo.
     *
     * @param fcpoTipopo  fcpo tipopo
     */
    public void setTipoPublicoObjectivo(JTipoPublicoObjetivo fcpoTipopo) {
        this.tipoPublicoObjectivo = fcpoTipopo;
    }

}