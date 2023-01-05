package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JUsuarioUnidadAdministrativaPK;

import javax.persistence.*;

/**
 * La clase J usuario unidad administrativa.
 */
@Entity
@Table(name = "RS2_USERUA")
public class JUsuarioUnidadAdministrativa {
    /**
     * Codigo
     */
    @EmbeddedId
    private JUsuarioUnidadAdministrativaPK codigo;

    /**
     * Usuario
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAUS_CODUSER", insertable = false, updatable = false)
    private JUsuario usuario;

    /**
     * Unidad administrativa
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UAUS_CODUA", insertable = false, updatable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public JUsuarioUnidadAdministrativaPK getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id  id
     */
    public void setCodigo(JUsuarioUnidadAdministrativaPK id) {
        this.codigo = id;
    }

    /**
     * Obtiene usuario.
     *
     * @return  usuario
     */
    public JUsuario getUsuario() {
        return usuario;
    }

    /**
     * Establece usuario.
     *
     * @param usuario  usuario
     */
    public void setUsuario(JUsuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return  unidad administrativa
     */
    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param unidadAdministrativa  unidad administrativa
     */
    public void setUnidadAdministrativa(JUnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }
}