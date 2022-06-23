package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JUsuarioUnidadAdministrativaPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RS2_USERUA")
public class JUsuarioUnidadAdministrativa {
    @EmbeddedId
    private JUsuarioUnidadAdministrativaPK id;

    public JUsuarioUnidadAdministrativaPK getId() {
        return id;
    }

    public void setId(JUsuarioUnidadAdministrativaPK id) {
        this.id = id;
    }

}