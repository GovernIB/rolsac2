package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RS2_PRCNOR")
public class JProcedimientoNormativa {
    @EmbeddedId
    private JProcedimientoNormativaPK codigo;

    public JProcedimientoNormativaPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JProcedimientoNormativaPK codigo) {
        this.codigo = codigo;
    }
}