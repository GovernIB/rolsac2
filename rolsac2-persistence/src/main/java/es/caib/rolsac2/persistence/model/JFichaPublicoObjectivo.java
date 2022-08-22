package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JFichaPublicoObjectivoPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_FCHPOB")
public class JFichaPublicoObjectivo {
    @EmbeddedId
    private JFichaPublicoObjectivoPK codigo;

    @MapsId("tipoPublicoObjetivo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FCPO_TIPOPO", nullable = false)
    private JTipoPublicoObjetivo tipoPublicoObjectivo;

    public JFichaPublicoObjectivoPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JFichaPublicoObjectivoPK id) {
        this.codigo = id;
    }

    public JTipoPublicoObjetivo getTipoPublicoObjectivo() {
        return tipoPublicoObjectivo;
    }

    public void setTipoPublicoObjectivo(JTipoPublicoObjetivo fcpoTipopo) {
        this.tipoPublicoObjectivo = fcpoTipopo;
    }

}