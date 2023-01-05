package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeEstadoProceso;
import es.caib.rolsac2.service.utils.UtilJSON;

import java.util.Date;

/**
 * The type Proceso log grid dto.
 */
public class ProcesoLogGridDTO extends ProcesoLogBaseDTO{
    /** Serial version UID. **/
    private static final long serialVersionUID = 1L;

    /**
     * Ruta Icons.
     */
    public static final String RUTA_ICON_VERDE = "/images/icons/verde.png";
    /**
     * The constant RUTA_ICON_AMARILLO.
     */
    public static final String RUTA_ICON_AMARILLO = "/images/icons/amarillo.png";
    /**
     * The constant RUTA_ICON_ROJO.
     */
    public static final String RUTA_ICON_ROJO = "/images/icons/rojo.png";

    /**
     * Proceso
     */
    private ProcesoDTO proceso;

    /**
     * Estado del proceso
     */
    private TypeEstadoProceso estadoProceso; // ESTADO PROCESO: INICIADO (I) / FINALIZADO OK (S) / FINALIZADO ERROR (N)

    /**
     *Fecha de inicio
     */
    private Date fechaInicio;

    /**
     * Fecha de finalizacion
     */
    private Date fechaFin;

    /**
     * Icono del proceso
     */
    private String iconProceso;

    /**
     * Texto estado
     */
    private String estadoTexto;

    /**
     * Mensaje de error
     */
    private String mensajeError;

    /**
     * Lista de propiedades
     */
    private ListaPropiedades listaPropiedades;


    /**
     * Obtiene lista propiedades.
     *
     * @return  lista propiedades
     */
    public ListaPropiedades getListaPropiedades() {
        return listaPropiedades;
    }

    /**
     * Establece lista propiedades.
     *
     * @param listaPropiedades  lista propiedades
     */
    public void setListaPropiedades(final ListaPropiedades listaPropiedades) {
        this.listaPropiedades = listaPropiedades;
    }

    /**
     * Obtiene icon proceso.
     *
     * @return  icon proceso
     */
    public String getIconProceso() {
        return iconProceso;
    }

    /**
     * Establece icon proceso.
     *
     * @param iconProceso  icon proceso
     */
    public void setIconProceso(final String iconProceso) {
        this.iconProceso = iconProceso;
    }

    /**
     * Obtiene estado proceso.
     *
     * @return  estado proceso
     */
    public TypeEstadoProceso getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * Establece estado proceso.
     *
     * @param estadoProceso  estado proceso
     */
    public void setEstadoProceso(final TypeEstadoProceso estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * Obtiene proceso.
     *
     * @return  proceso
     */
    public ProcesoDTO getProceso() {
        return proceso;
    }

    /**
     * Establece proceso.
     *
     * @param proceso  proceso
     */
    public void setProceso(final ProcesoDTO proceso) {
        this.proceso = proceso;
    }


    /**
     * Obtiene fecha inicio.
     *
     * @return  fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece fecha inicio.
     *
     * @param fechaInicio  fecha inicio
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene fecha fin.
     *
     * @return  fecha fin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece fecha fin.
     *
     * @param fechaFin  fecha fin
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene mensaje error.
     *
     * @return  mensaje error
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Establece mensaje error.
     *
     * @param mensajeError  mensaje error
     */
    public void setMensajeError(final String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * Obtiene estado texto.
     *
     * @return  estado texto
     */
    public String getEstadoTexto() {
        return estadoTexto;
    }

    /**
     * Establece estado texto.
     *
     * @param estadoTexto  estado texto
     */
    public void setEstadoTexto(String estadoTexto) {
        this.estadoTexto = estadoTexto;
    }

    /**
     * Castea un object[] en ProcesoGrid
     *
     * @param resultado  resultado
     * @return proceso log grid dto
     */
    public static ProcesoLogGridDTO cast(final Object[] resultado) {


        final ProcesoLogGridDTO proceso = new ProcesoLogGridDTO();

        proceso.setCodigo(Long.valueOf(resultado[0].toString()));

        Date fecha;
        fecha = (Date) resultado[2];
        proceso.setFechaInicio(fecha);

        if (resultado[3] != null) {
            fecha = (Date) resultado[3];
            proceso.setFechaFin(fecha);
        }

        final TypeEstadoProceso typoestado = TypeEstadoProceso.fromString(resultado[4].toString());

        proceso.setEstadoProceso(typoestado);
        switch (typoestado) {
            case CORRECTO:
                proceso.setIconProceso(RUTA_ICON_VERDE);
                proceso.setEstadoTexto("CORRECTO");
                break; // verde
            case ALERTA:
                proceso.setIconProceso(RUTA_ICON_AMARILLO);
                proceso.setEstadoTexto("ALERTA");
                break; // amarillo
            case ERROR:
                proceso.setIconProceso(RUTA_ICON_ROJO);
                proceso.setEstadoTexto("ERROR");
                break; // rojo
        }

        final ProcesoDTO p = new ProcesoDTO();
        p.setCodigo(Long.valueOf(resultado[1].toString()));
        p.setIdentificadorProceso(resultado[5].toString());
        p.setDescripcion(resultado[6].toString());
        proceso.setListaPropiedades(resultado[7] == null ? null : (ListaPropiedades) UtilJSON.fromJSON(resultado[7].toString(), ListaPropiedades.class));
        proceso.setProceso(p);

        return proceso;
    }
}
