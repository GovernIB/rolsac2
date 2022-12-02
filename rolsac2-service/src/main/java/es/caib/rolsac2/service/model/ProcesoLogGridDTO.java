package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeEstadoProceso;
import es.caib.rolsac2.service.utils.UtilJSON;

import java.util.Date;

public class ProcesoLogGridDTO extends ProcesoLogBaseDTO{
    /** Serial version UID. **/
    private static final long serialVersionUID = 1L;

    /** Ruta Icons. **/
    public static final String RUTA_ICON_VERDE = "/images/icons/verde.png";
    public static final String RUTA_ICON_AMARILLO = "/images/icons/amarillo.png";
    public static final String RUTA_ICON_ROJO = "/images/icons/rojo.png";

    private ProcesoDTO proceso;

    private TypeEstadoProceso estadoProceso; // ESTADO PROCESO: INICIADO (I) / FINALIZADO OK (S) / FINALIZADO ERROR (N)

    private Date fechaInicio;
    private Date fechaFin;
    private String iconProceso;

    private String estadoTexto;
    private String mensajeError;
    private ListaPropiedades listaPropiedades;


    public ListaPropiedades getListaPropiedades() {
        return listaPropiedades;
    }

    public void setListaPropiedades(final ListaPropiedades listaPropiedades) {
        this.listaPropiedades = listaPropiedades;
    }

    public String getIconProceso() {
        return iconProceso;
    }

    public void setIconProceso(final String iconProceso) {
        this.iconProceso = iconProceso;
    }

    public TypeEstadoProceso getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(final TypeEstadoProceso estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public ProcesoDTO getProceso() {
        return proceso;
    }

    public void setProceso(final ProcesoDTO proceso) {
        this.proceso = proceso;
    }


    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(final String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getEstadoTexto() {
        return estadoTexto;
    }

    public void setEstadoTexto(String estadoTexto) {
        this.estadoTexto = estadoTexto;
    }

    /**
     * Castea un object[] en ProcesoGrid
     *
     * @param resultado
     * @return
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
