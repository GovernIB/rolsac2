package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Mensaje;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.utils.UtilJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@ViewScoped
public class DialogProcedimientoFlujo extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimientoFlujo.class);

    private String id;

    private String estadoActual;

    private String consultarSoloMensajes;


    private RespuestaFlujo data;

    /**
     * Propiedad seleccionada.
     */
    private Propiedad mensajeSeleccionado;

    /**
     * PROPIEDADES PLUGIN (JSON)
     */
    private List<Mensaje> mensajes;

    private String mensajeNuevo;

    @Inject
    private SessionBean sessionBean;

    @EJB
    AdministracionEntServiceFacade administracionEntService;


    @EJB
    private SystemServiceFacade systemServiceFacade;

    private boolean mostrarEstados;


    List<TypeProcedimientoEstado> estados;
    TypeProcedimientoEstado estadoSeleccionado;

    String mensaje;
    TypeProcedimientoEstado typeEstadoActual;

    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        data = new RespuestaFlujo();
        mostrarEstados = true;
        mensaje = (String) UtilJSF.getValorMochilaByKey("mensajes");
        if (mensaje != null && !mensaje.isEmpty()) {
            mensajes = (List<Mensaje>) UtilJSON.getMensaje(mensaje);
        }
        if (mensajes == null) {
            mensajes = new ArrayList<>();
        }
        if (consultarSoloMensajes != null && "S".equals(consultarSoloMensajes)) {
            mostrarEstados = false;
        } else {
            if (estadoActual != null) {
                typeEstadoActual = TypeProcedimientoEstado.fromString(estadoActual);
                if (sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS) {
                    if (typeEstadoActual != null) {
                        estados = new ArrayList<>();
                        switch (typeEstadoActual) {
                            case MODIFICACION:
                                estados.add(TypeProcedimientoEstado.PUBLICADO);
                                estados.add(TypeProcedimientoEstado.BORRADO);
                                estados.add(TypeProcedimientoEstado.RESERVA);
                                break;
                            case PUBLICADO:
                                estados.add(TypeProcedimientoEstado.MODIFICACION);
                                estados.add(TypeProcedimientoEstado.BORRADO);
                                estados.add(TypeProcedimientoEstado.RESERVA);
                                break;
                            case BORRADO:
                                estados.add(TypeProcedimientoEstado.PUBLICADO);
                                estados.add(TypeProcedimientoEstado.RESERVA);
                                break;
                            case RESERVA:
                                estados.add(TypeProcedimientoEstado.BORRADO);
                                estados.add(TypeProcedimientoEstado.PUBLICADO);
                                break;
                            case PENDIENTE_PUBLICAR:
                                estados.add(TypeProcedimientoEstado.MODIFICACION);
                                estados.add(TypeProcedimientoEstado.PUBLICADO);
                                break;
                            case PENDIENTE_RESERVAR:
                                estados.add(TypeProcedimientoEstado.MODIFICACION);
                                estados.add(TypeProcedimientoEstado.RESERVA);
                                break;
                            case PENDIENTE_BORRAR:
                                estados.add(TypeProcedimientoEstado.MODIFICACION);
                                estados.add(TypeProcedimientoEstado.BORRADO);
                                break;
                        }
                    }
                }
                if (sessionBean.getPerfil() == TypePerfiles.GESTOR) {
                    estados = new ArrayList<>();
                    if (typeEstadoActual != null && typeEstadoActual == TypeProcedimientoEstado.MODIFICACION) {
                        estados.add(TypeProcedimientoEstado.PENDIENTE_PUBLICAR);
                        estados.add(TypeProcedimientoEstado.PENDIENTE_RESERVAR);
                        estados.add(TypeProcedimientoEstado.PENDIENTE_BORRAR);
                    }
                }
            }
        }

        if (mensajes != null && !mensajes.isEmpty()) {
            Collections.sort(mensajes);
        }
    }

    public void guardar() {

        final DialogResult result = new DialogResult();
        if (mostrarEstados && estadoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.obligatorio.estadoDestino"), true);
            return;
        }

        if (!mostrarEstados && (mensajeNuevo == null || mensajeNuevo.isEmpty())) {
            //Si no es un flujo y no hay mensaje escrito, simplemente cerrar
            cerrar();
            return;
        }

        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }

        //Si el estado actual o seleccionado es pendiente, entonces es un cambio de un
        if (typeEstadoActual.isEstadoPendiente() || estadoSeleccionado.isEstadoPendiente()) {
            //if (sessionBean.getPerfil() == TypePerfiles.GESTOR && estadoSeleccionado.isEstadoPendiente()) {
            String literal = estadoSeleccionado.getLiteralMensajePendiente(sessionBean.getLang());
            String valorLiteral = null;
            if (literal != null) {
                valorLiteral = systemServiceFacade.obtenerPropiedadConfiguracion(literal);
            }
            if (literal != null && valorLiteral != null && !valorLiteral.isEmpty()) {
                Mensaje msg = new Mensaje();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String fecha = sdf.format((Date) Calendar.getInstance().getTime());
                msg.setFecha(fecha);
                msg.setFechaReal((Date) Calendar.getInstance().getTime());
                String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
                msg.setUsuario(usuario);
                msg.setMensaje(valorLiteral);
                msg.setAdmContenido(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS);
                mensajes.add(0, msg);
            }
        }

        if (mensajeNuevo != null && !mensajeNuevo.isEmpty()) {
            if (mensajes == null) {
                mensajes = new ArrayList<>();
            }
            Mensaje msg = new Mensaje();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM HH:mm");
            String fecha = sdf.format((Date) Calendar.getInstance().getTime());
            msg.setFecha(fecha);
            msg.setFechaReal((Date) Calendar.getInstance().getTime());
            String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            msg.setUsuario(usuario);
            msg.setMensaje(mensajeNuevo);
            msg.setAdmContenido(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS);
            mensajes.add(0, msg);
        }

        data.setMensajes(UtilJSON.toJSON(mensajes));
        data.setEstadoDestino(this.estadoSeleccionado);
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }


    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public boolean esAdministradorContenido(Mensaje mensaje) {
        return mensaje != null && mensaje.isAdmContenido() != null && mensaje.isAdmContenido();
    }


    public String getEstilo(Object mensaje) {
        return "backgroundGestor borderDerArribaAbajo";
    }

    public String getEstiloAdmContenido(Mensaje mensaje) {

        if (esAdministradorContenido(mensaje)) {
            return "backgroundAdmContenido borderIzqArribaAbajo";
        } else {
            return "";
        }
    }

    public String getEstiloMensaje(Mensaje mensaje) {
        if (esAdministradorContenido(mensaje)) {
            return "backgroundAdmContenido borderArribaAbajo";
        } else {
            return "backgroundGestor borderIzqArribaAbajo";
        }
    }


    public String getEstiloFecha(Mensaje mensaje) {
        if (esAdministradorContenido(mensaje)) {
            return "backgroundAdmContenido borderDerArribaAbajo";
        } else {
            return "backgroundGestor borderArribaAbajo";
        }
    }

    public String getEstiloGestor(Mensaje mensaje) {
        if (esAdministradorContenido(mensaje)) {
            return "";
        } else {
            return "backgroundGestor borderDerArribaAbajo";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Propiedad getMensajeSeleccionado() {
        return mensajeSeleccionado;
    }

    public void setData(RespuestaFlujo data) {
        this.data = data;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void setMensajeSeleccionado(Propiedad mensajeSeleccionado) {
        this.mensajeSeleccionado = mensajeSeleccionado;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getConsultarSoloMensajes() {
        return consultarSoloMensajes;
    }

    public void setConsultarSoloMensajes(String consultarSoloMensajes) {
        this.consultarSoloMensajes = consultarSoloMensajes;
    }

    public RespuestaFlujo getData() {
        return data;
    }

    public boolean isMostrarEstados() {
        return mostrarEstados;
    }

    public void setMostrarEstados(boolean mostrarEstados) {
        this.mostrarEstados = mostrarEstados;
    }

    public List<TypeProcedimientoEstado> getEstados() {
        return estados;
    }

    public void setEstados(List<TypeProcedimientoEstado> estados) {
        this.estados = estados;
    }

    public TypeProcedimientoEstado getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(TypeProcedimientoEstado estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeNuevo() {
        return mensajeNuevo;
    }

    public void setMensajeNuevo(String mensajeNuevo) {
        this.mensajeNuevo = mensajeNuevo;
    }


}
