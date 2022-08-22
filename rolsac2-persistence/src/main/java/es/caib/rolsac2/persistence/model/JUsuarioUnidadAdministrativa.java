package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JUsuarioUnidadAdministrativaPK;

import javax.persistence.*;

@Entity
@Table(name = "RS2_USERUA")
public class JUsuarioUnidadAdministrativa {
    @EmbeddedId
    private JUsuarioUnidadAdministrativaPK codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAUS_CODUSER", insertable = false, updatable = false)
    private JUsuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAUS_CODUA", insertable = false, updatable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    public JUsuarioUnidadAdministrativaPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JUsuarioUnidadAdministrativaPK id) {
        this.codigo = id;
    }

    public JUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(JUsuario usuario) {
        this.usuario = usuario;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }
}