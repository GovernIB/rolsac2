/**
 *
 */
package es.caib.rolsac2.service.model.auditoria;

import java.util.List;

/**
 * Clase que tiene la información que se va a mostrar en la ventana de auditoría
 *
 * @author Indra
 */
public class AuditoriaGridDTO {


    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Codigo
     **/
    private Integer codigo;

    /**
     * fecha en que se realizó el registro en auditoria
     **/
    private String fecha;

    /**
     * usuario que realiza el movimiento
     **/
    private String usuario;

    /**
     * Usuario perfil
     */
    private String usuarioPerfil;


    private String modificaciones;

    private List<AuditoriaCambio> cambios;

    private String literalFlujo;

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }


    /**
     * @param fecha the fecha to set
     */
    public void setFecha(final String fecha) {
        this.fecha = fecha;
    }


    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }


    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }


    @Override
    public boolean equals(final Object objeto) {
        return super.equals(objeto);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getModificaciones() {
        return modificaciones;
    }

    public void setModificaciones(String modificaciones) {
        this.modificaciones = modificaciones;
    }

    public List<AuditoriaCambio> getCambios() {
        return cambios;
    }

    public void setCambios(List<AuditoriaCambio> cambios) {
        this.cambios = cambios;
    }

    public String getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(String usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    public String getLiteralFlujo() {
        return literalFlujo;
    }

    public void setLiteralFlujo(String literalFlujo) {
        this.literalFlujo = literalFlujo;
    }
}
