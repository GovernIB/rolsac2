package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.pk.JUsuarioEntidadPK;
import es.caib.rolsac2.persistence.model.pk.JUsuarioUnidadAdministrativaPK;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * La clase J usuario unidad administrativa.
 */
@Entity
@Table(name = "RS2_USENTI")
public class JUsuarioEntidad {
    /**
     * Codigo
     */
    @EmbeddedId
    private JUsuarioEntidadPK codigo;

    /**
     * Usuario
     */
    @MapsId("usuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USEN_CODUSER", insertable = false, updatable = false)
    private JUsuario usuario;

    /**
     * Entidad
     */
    @MapsId("entidad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USEN_CODENTI", insertable = false, updatable = false)
    private JEntidad entidad;


    public JUsuarioEntidadPK getCodigo() {
        return codigo;
    }

    public void setCodigo(JUsuarioEntidadPK codigo) {
        this.codigo = codigo;
    }

    public JUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(JUsuario usuario) {
        this.usuario = usuario;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUsuarioEntidad that = (JUsuarioEntidad) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(usuario, that.usuario) && Objects.equals(entidad, that.entidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, usuario, entidad);
    }

    @Override
    public String toString() {
        return "JUsuarioEntidad{" +
                "codigo=" + codigo +
                ", usuario=" + usuario +
                ", entidad=" + entidad +
                '}';
    }
}