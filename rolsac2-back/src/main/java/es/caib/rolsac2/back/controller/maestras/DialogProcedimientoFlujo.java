package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.commons.plugins.email.api.AnexoEmail;
import es.caib.rolsac2.commons.plugins.email.api.EmailPlugin;
import es.caib.rolsac2.commons.plugins.email.api.EmailPluginException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Mensaje;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.types.*;
import es.caib.rolsac2.service.utils.UtilJSON;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@ViewScoped
public class DialogProcedimientoFlujo extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimientoFlujo.class);

    private String id;
    private String idWF;
    private Long idWFL;
    private String estadoActual;
    private String literalEstadoActual;
    private String estadoProcedimiento;
    private String consultarSoloMensajes;


    private RespuestaFlujo data;

    private Boolean checkMail;

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

    private String tipo;

    private ProcedimientoDTO procedimientoDTO;

    private ServicioDTO servicioDTO;


    List<TypeProcedimientoEstado> estados;
    TypeProcedimientoEstado estadoSeleccionado;

    String mensaje;
    TypeProcedimientoEstado typeEstadoActual;
    private Long idProcedimiento;

    @EJB
    private ProcedimientoServiceFacade procedimientoService;

    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        data = new RespuestaFlujo();
        mostrarEstados = true;
        checkMail = false;
        mensaje = (String) UtilJSF.getValorMochilaByKey("mensajes");
        procedimientoDTO = (ProcedimientoDTO) UtilJSF.getValorMochilaByKey("procedimiento");
        if (idWF != null) {
            idWFL = Long.valueOf(idWF);
        }
        if (id != null) {
            idProcedimiento = Long.valueOf(id);
        }
        if (mensaje != null && !mensaje.isEmpty()) {
            mensajes = (List<Mensaje>) UtilJSON.getMensaje(mensaje);
        }
        if (mensajes == null) {
            mensajes = new ArrayList<>();
        }/*
        if (estadoActual != null && !estadoActual.isEmpty()) {
            literalEstadoActual = getLiteral("TypeProcedimientoEstado." + estadoActual);
        }*/
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
                                break;
                            case PENDIENTE_PUBLICAR:
                                estados.add(TypeProcedimientoEstado.MODIFICACION);
                                estados.add(TypeProcedimientoEstado.PUBLICADO);
                                break;
                            case PUBLICADO:
                                if (this.estadoProcedimiento != null && !this.estadoProcedimiento.contains("PV") && !this.estadoProcedimiento.contains("M")) {
                                    /* Si el procedimiento tiene un WF modificado que esta en pendiente publicacion o en modificacion, no se puede mover en el flujo segun grafica #226 */
                                    estados.add(TypeProcedimientoEstado.CERRADO);
                                }
                                break;
                            case PENDIENTE_CERRAR:
                                estados.add(TypeProcedimientoEstado.PUBLICADO);
                                estados.add(TypeProcedimientoEstado.CERRADO);
                                break;
                            case CERRADO:
                                if (this.estadoProcedimiento != null && !this.estadoProcedimiento.contains("PV") && !this.estadoProcedimiento.contains("M")) {
                                    /* Si el procedimiento tiene un WF modificado que esta en pendiente publicacion o en modificacion, no se puede mover en el flujo segun grafica #226 */
                                    estados.add(TypeProcedimientoEstado.PUBLICADO);
                                }
                                break;
                        }
                    }
                }
                if (sessionBean.getPerfil() == TypePerfiles.GESTOR) {
                    estados = new ArrayList<>();
                    if (typeEstadoActual != null && typeEstadoActual == TypeProcedimientoEstado.MODIFICACION) {
                        estados.add(TypeProcedimientoEstado.PENDIENTE_PUBLICAR);
                    }
                    if (typeEstadoActual != null && typeEstadoActual == TypeProcedimientoEstado.PUBLICADO) {
                        if (this.estadoProcedimiento != null && !this.estadoProcedimiento.contains("PV") && !this.estadoProcedimiento.contains("M")) {
                            /* Si el procedimiento tiene un WF modificado que esta en pendiente publicacion o en modificacion, no se puede mover en el flujo segun grafica #226 */
                            estados.add(TypeProcedimientoEstado.PENDIENTE_CERRAR);
                            this.estadoSeleccionado = estados.get(0);
                        }
                    }
                }
            }
        }

        if (mensajes != null && !mensajes.isEmpty()) {
            Collections.sort(mensajes);
            ValidacionTipoUtils.normalizarMensajes(mensajes);
        }

        tipo = (String) UtilJSF.getValorMochilaByKey("tipo");
    }

    public void guardarOEnviarMail() {
        if (checkMail) {
            /* Las validaciones ahora se hace en DialogProcedimiento
            boolean correctoPDU = checkPdu();
            if (!correctoPDU) {
                return;
            }*/
            enviarMail();
        } else {
            guardar(false);
        }
    }

    /**
     * Enviar email.
     */
    public void enviarMail() {
        Properties prop = new Properties();

        List<AnexoEmail> lista = new ArrayList<>();
        List<String> listaDestinatarios = new ArrayList<>();
        List<String> listaUsuariosDestinatarios = new ArrayList<>();
        boolean buscoAdministradorContenidos;
        if (this.isAdministradorContenidos()) {
            buscoAdministradorContenidos = false;
        } else {
            buscoAdministradorContenidos = true;
        }

        if (!mostrarEstados && mensajeNuevo == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimientoFlujo.errorTextomail"), true);
            return;
        }
        if (mensajes != null) {
            for (Mensaje mensaje : mensajes) {
                if (mensaje.isAdmContenido() == buscoAdministradorContenidos) {
                    if (!listaUsuariosDestinatarios.contains(mensaje.getUsuario()) && !mensaje.getUsuario().equals(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser())) {
                        listaUsuariosDestinatarios.add(mensaje.getUsuario());
                    }
                }
            }
        }

        if (listaUsuariosDestinatarios.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimientoFlujo.errormail"), true);
            return;
        }

        listaDestinatarios = administracionEntService.getEmailUsuarios(listaUsuariosDestinatarios);
        if (listaDestinatarios == null || listaDestinatarios.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimientoFlujo.errormail"), true);
            return;
        }

        // Quitar nulos (y, si quieres, vacíos/espacios)
        for (Iterator<String> it = listaDestinatarios.iterator(); it.hasNext(); ) {
            String s = it.next();
            if (s == null || s.trim().isEmpty()) {  // quita .trim/.isEmpty si solo quieres eliminar nulos
                it.remove();
            }
        }
        if (listaDestinatarios == null || listaDestinatarios.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimientoFlujo.errormail"), true);
            return;
        }

        String mensajeEnviar = "";
        if (mensajeNuevo != null && !mensajeNuevo.isEmpty()) {
            Object[] parametros = new Object[3];
            parametros[0] = UtilJSF.getSessionBean().getUsuario().getNombre();

            String enlaceProp;

            if ("P".equals(tipo)) {
                enlaceProp = systemServiceFacade.obtenerPropiedadConfiguracion("email.proc.enlace") + idProcedimiento;
            } else {
                enlaceProp = systemServiceFacade.obtenerPropiedadConfiguracion("email.serv.enlace") + idProcedimiento;
            }
            String mensajeNuevoSeguro = StringEscapeUtils.escapeHtml4(mensajeNuevo);
            mensajeNuevoSeguro = mensajeNuevoSeguro.replace("\r\n", "\n").replace("\r", "\n");
            mensajeNuevoSeguro = mensajeNuevoSeguro.replace("\n", "<br/>");
            parametros[1] = mensajeNuevoSeguro;
            parametros[2] = enlaceProp;

            String mensajeInicial = systemServiceFacade.obtenerPropiedadConfiguracion("email.email.inicio." + getIdioma());
            if (mensajeInicial == null || mensajeInicial.isEmpty()) {
                mensajeInicial = getLiteral("dialogProcedimientoFlujo.mensajeUsuario");
            }
            mensajeEnviar = MessageFormat.format(mensajeInicial, parametros);
        } else {
            cerrar();
            return;
        }

        String asunto = "";
        String nombre = procedimientoService.getNombreProcedimientoServicio(idProcedimiento);

        if (tipo.equals("S")) {
            asunto = getLiteral("dialogProcedimientoFlujo.actualizacions") + nombre;
        } else {
            asunto = getLiteral("dialogProcedimientoFlujo.actualizacionp") + nombre;
        }

        final EmailPlugin pluginEmail = (EmailPlugin) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.EMAIL, UtilJSF.getSessionBean().getEntidad().getCodigo());

        try {
            boolean respuesta = pluginEmail.envioEmail(listaDestinatarios, asunto, mensajeEnviar, null, sessionBean.getLang());
            LOG.debug("Resultado Email: ");
            LOG.debug(Boolean.toString(respuesta));

        } catch (EmailPluginException e) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcedimientoFlujo.errorenvio") + ". " + e.getMessage(), true);
            LOG.error("Error enviando el email", e);
            return;
        }

        guardar(true);
    }

    public String estructurarCopiaChat() {
        StringBuilder sb = new StringBuilder();
        for (Mensaje mensaje : mensajes) {
            sb.append(mensaje.getMensaje());
            sb.append("\n");
            sb.append("(");
            sb.append(mensaje.getUsuario());
            sb.append(" - " + (mensaje.isAdmContenido() ? getLiteral("TypePerfiles.RS2_ADC") : getLiteral("TypePerfiles.RS2_GES")));
            sb.append("):");
            sb.append(mensaje.getFecha());
            sb.append("\n\n");
        }
        return sb.toString();
    }

    public void guardar(boolean emailEnviado) {

        final DialogResult result = new DialogResult();
        if (mostrarEstados && estadoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.obligatorio.estadoDestino"), true);
            return;
        }

        if (!mostrarEstados && (mensajeNuevo == null || mensajeNuevo.isEmpty())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimientoFlujo.errorTextovacio"), true);
            return;
        }

        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }

        //Validaciones PDU se hacen ahora en DialogProcedimiento
        /** boolean correctoPDU = checkPdu();
         if (!correctoPDU) {
         return;
         } ***/

        //Si el estado actual o seleccionado es pendiente, entonces es un cambio de un
        if ((typeEstadoActual != null && typeEstadoActual.isEstadoPendiente()) || (estadoSeleccionado != null && estadoSeleccionado.isEstadoPendiente())) {
            //if (sessionBean.getPerfil() == TypePerfiles.GESTOR && estadoSeleccionado.isEstadoPendiente()) {
            String literal = estadoSeleccionado.getLiteralMensajePendiente(sessionBean.getLang());
            String valorLiteral = null;
            if (literal != null) {
                valorLiteral = systemServiceFacade.obtenerPropiedadConfiguracion(literal);
            }
            if (literal != null && valorLiteral != null && !valorLiteral.isEmpty()) {
                Mensaje msg = new Mensaje();
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String fecha = sdf.format((Date) Calendar.getInstance().getTime());
                msg.setFecha(fecha);
                msg.setFechaReal((Date) Calendar.getInstance().getTime());
                String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
                msg.setUsuario(usuario);
                msg.setMensaje(valorLiteral);
                msg.setPendienteMensajesGestor(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS);
                msg.setPendienteMensajesSupervisor(!(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS));
                msg.setAdmContenido(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS);
                if (emailEnviado) {
                    msg.setFechaEmail((Date) Calendar.getInstance().getTime());
                }
                mensajes.add(0, msg);
            }
        }

        if (mensajeNuevo != null && !mensajeNuevo.isEmpty()) {
            if (mensajes == null) {
                mensajes = new ArrayList<>();
            }
            Mensaje msg = new Mensaje();
            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = sdf.format((Date) Calendar.getInstance().getTime());
            msg.setFecha(fecha);
            msg.setFechaReal((Date) Calendar.getInstance().getTime());
            String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            msg.setUsuario(usuario);
            msg.setMensaje(mensajeNuevo);
            msg.setAdmContenido(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS);
            msg.setPendienteMensajesGestor(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS);
            msg.setPendienteMensajesSupervisor(!(sessionBean.getPerfil() == TypePerfiles.ADMINISTRADOR_CONTENIDOS));
            if (emailEnviado) {
                msg.setFechaEmail((Date) Calendar.getInstance().getTime());
            }
            mensajes.add(0, msg);
        }
        ValidacionTipoUtils.sanitizarMensajes(mensajes);
        data.setMensajes(UtilJSON.toJSON(mensajes));
        data.setEstadoDestino(this.estadoSeleccionado);
        data.setPendienteMensajesSupervisor(getLeidoSupervisor());
        data.setPendienteMensajesGestor(getLeidoGestor());
        data.setCodigoProcedimiento(this.idProcedimiento.toString());
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    /**
     * private boolean checkPdu() {
     * if (estadoSeleccionado != null && "P".equals(tipo) && estadoSeleccionado.isEstadoValidacionPDU() && procedimientoDTO != null && procedimientoDTO.isIntegrarPdu()) {
     * <p>
     * boolean rellenoCategoriaPDU = procedimientoDTO.getCategoriasPDU() != null && !procedimientoDTO.getCategoriasPDU().isEmpty();
     * boolean rellenoIdiomasIngles = procedimientoDTO.isRellenoIdiomasPDU();
     * <p>
     * String msgError = getLiteral("dialogProcedimientoFlujo.errorMoverPDU") + " " + getLiteral("TypeProcedimientoEstado." + estadoSeleccionado.toString());
     * if (!rellenoCategoriaPDU) {
     * if (!rellenoIdiomasIngles) {
     * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanCategoriasPDUeIngles"), true);
     * return false;
     * } else {
     * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanCategoriasPDU"), true);
     * return false;
     * }
     * } else if (!rellenoIdiomasIngles) {
     * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanIngles"), true);
     * return false;
     * }
     * <p>
     * if (procedimientoDTO.getTramites() != null && !procedimientoDTO.getTramites().isEmpty()) {
     * for (ProcedimientoTramiteDTO tram : procedimientoDTO.getTramites()) {
     * if (!tram.isRellenoIdiomasPDU()) {
     * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanInglesTramites"), true);
     * return false;
     * }
     * }
     * }
     * }
     * return true;
     * }
     **/

    public void marcarComoLeido(Integer posicion) {


        if (this.isGestor()) {
            this.mensajes.get(posicion).setPendienteMensajesGestor(false);
        } else {
            this.mensajes.get(posicion).setPendienteMensajesSupervisor(false);
        }

        //Sanitizamos mensajes por si hubiera el carÃ¡cter de apÃ³strofe (')
        ValidacionTipoUtils.sanitizarMensajes(mensajes);
        String mensajesJSON = UtilJSON.toJSON(mensajes);
        procedimientoService.actualizarMensajes(idProcedimiento, mensajesJSON, getLeidoSupervisor(), getLeidoGestor());
        //Normalizamos los mensajes para que se muestren correctamente.
        ValidacionTipoUtils.normalizarMensajes(mensajes);
    }

    private boolean getLeidoSupervisor() {
        boolean pendiente = false;
        for (Mensaje mensaje : mensajes) {
            if (mensaje.isPendienteMensajesSupervisor()) {
                pendiente = true;
                break;
            }
        }
        return pendiente;
    }

    private boolean getLeidoGestor() {
        boolean pendiente = false;
        for (Mensaje mensaje : mensajes) {
            if (mensaje.isPendienteMensajesGestor()) {
                pendiente = true;
                break;
            }
        }
        return pendiente;
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

    public Boolean getCheckMail() {
        return checkMail;
    }

    public void setCheckMail(Boolean checkMail) {
        this.checkMail = checkMail;
    }

    public void abrirAyuda() {
        if (mostrarEstados) {
            this.ayuda("dialogProcedimientoFlujo");
        } else {
            this.ayuda("dialogProcedimientoMsg");
        }
    }

    public String getIdWF() {
        return idWF;
    }

    public void setIdWF(String idWF) {
        this.idWF = idWF;
    }

    public String getLiteralEstadoActual() {
        return literalEstadoActual;
    }

    public void setLiteralEstadoActual(String literalEstadoActual) {
        this.literalEstadoActual = literalEstadoActual;
    }

    public String getEstadoProcedimiento() {
        return estadoProcedimiento;
    }

    public void setEstadoProcedimiento(String estadoProcedimiento) {
        this.estadoProcedimiento = estadoProcedimiento;
    }

    public String getValorEstado(String campo) {
        if (campo == null) {
            return "";
        }
        if (TypeProcedimientoEstado.MODIFICACION.toString().equals(campo) || TypeProcedimientoEstado.PENDIENTE_PUBLICAR.toString().equals(campo)) {
            return getEstadoPublicado(estadoProcedimiento) + campo;
        } else {
            return campo;
        }
    }

    private String getEstadoPublicado(String estadoProcedimiento) {
        if (estadoProcedimiento == null) {
            return "";
        }
        switch (estadoProcedimiento) {
            case "T":
                return "T";
            case "PT":
                return "PT";
            case "M":
                return "";
            case "PV":
                return "";
            case "P":
                return "P";
            case "PPV":
                return "P";
            case "PM":
                return "P";
            default:
                return "";


        }
    }
}

