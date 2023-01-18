/**
 *
 */
package es.caib.rolsac2.service.model.auditoria;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Long codigo;

    /**
     * fecha en que se realizó el registro en auditoria
     **/
    private String fecha;

    /**
     * usuario que realiza el movimiento
     **/
    private String usuario;

    /**
     * indica si una persona es responsable en el órgano al cuál se va a modificar. Esto es para el caso de Personas
     **/
    private boolean responsable;

    /**
     * indica si una persona tiene cambio de puesto
     **/
    private boolean cambioPuesto;

    /**
     * indica si hay etapas (Caso de empleo público)
     **/
    private String tipoEtapa;


    /**
     * Convierte un objeto de Base de datos a un objeto AuditoriaGridDTO
     *
     * @param resultado
     */
    public void toModel(final Object[] resultado) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        this.setCodigo(Long.parseLong(resultado[0].toString()));
        this.setFecha(sdf.format((Date) resultado[1]));
        this.setUsuario(resultado[2].toString());
        if (resultado.length == 4 || resultado.length == 5) {
            this.setResponsable((boolean) resultado[3]);
        }
        if (resultado.length == 5) {
            this.setCambioPuesto((boolean) resultado[4]);
        }
        if (resultado.length == 6) {
            this.setTipoEtapa(resultado[5] != null ? resultado[5].toString() : "");
        }
    }


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


    /**
     * @return the responsable
     */
    public boolean isResponsable() {
        return responsable;
    }


    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(final boolean responsable) {
        this.responsable = responsable;
    }


    /**
     * @return the cambioPuesto
     */
    public boolean isCambioPuesto() {
        return cambioPuesto;
    }


    /**
     * @param cambioPuesto the cambioPuesto to set
     */
    public void setCambioPuesto(final boolean cambioPuesto) {
        this.cambioPuesto = cambioPuesto;
    }


    @Override
    public boolean equals(final Object objeto) {
        return super.equals(objeto);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    /**
     * @return the tipoEtapa
     */
    public String getTipoEtapa() {
        return tipoEtapa;
    }


    /**
     * @param tipoEtapa the tipoEtapa to set
     */
    public void setTipoEtapa(final String tipoEtapa) {
        this.tipoEtapa = tipoEtapa;
    }


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

}
