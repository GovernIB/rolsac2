package es.caib.rolsac2.back.utils;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilJSF {

    /**
     * Parametro de sesion para securizar apertura dialogs.
     */
    private static final String SEC_OPEN_DIALOG = "SEC_OPEN_DIALOG";

    /**
     * Constructor privado para evitar problema.
     */
    private UtilJSF() {
        // not called
    }

    /**
     * Abre pantalla de dialogo
     *
     * @param dialog     Nombre pantalla dialogo (dialogo.xhtml o id navegacion)
     * @param modoAcceso Modo de acceso
     * @param params     parametros
     * @param modal      si se abre en forma modal
     * @param width      anchura
     * @param heigth     altura
     */
    public static void openDialog(final String dialog, final TypeModoAcceso modoAcceso,
                                  final Map<String, String> params, final boolean modal, final int width, final int heigth) {
        // Opciones dialogo
        final Map<String, Object> options = new HashMap<>();
        options.put("modal", modal);
        options.put("width", width);
        options.put("height", heigth);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("MODO_ACCESO", modoAcceso.toString());


        // Parametros
        String idParam = "";
        final Map<String, List<String>> paramsDialog = new HashMap<>();
        paramsDialog.put(TypeParametroVentana.MODO_ACCESO.toString(), Collections.singletonList(modoAcceso.toString()));
        if (params != null) {
            for (final String key : params.keySet()) {
                paramsDialog.put(key, Collections.singletonList(params.get(key)));
                if (TypeParametroVentana.ID.toString().equals(key)) {
                    idParam = params.get(key);
                }
            }
        }

        // Metemos en sessionbean un parámetro de seguridad para evitar que se
        // pueda cambiar el modo de acceso
        final String secOpenDialog = modoAcceso.toString() + "-" + idParam + "-" + System.currentTimeMillis();
        //getSessionBean().getMochilaDatos().put(SEC_OPEN_DIALOG, secOpenDialog);

        // Abre dialogo
        PrimeFaces.current().dialog().openDynamic(dialog, options, paramsDialog);
    }

    private static Severity getSeverity(final TypeNivelGravedad nivel) {
        Severity severity;
        switch (nivel) {
            case INFO:
                severity = FacesMessage.SEVERITY_INFO;
                break;
            case WARNING:
                severity = FacesMessage.SEVERITY_WARN;
                break;
            case ERROR:
                severity = FacesMessage.SEVERITY_ERROR;
                break;
            case FATAL:
                severity = FacesMessage.SEVERITY_FATAL;
                break;
            default:
                severity = FacesMessage.SEVERITY_INFO;
                break;
        }
        return severity;
    }

    /**
     * Añade mensaje a un componente para que lo trate la aplicación (growl,
     * messages,...).
     *
     * @param nivel        Nivel de gravedad.
     * @param message      Mensaje
     * @param detail       Detalle
     * @param idComponente Si el id componente es nulo, se enviará al growl.
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
                                         final String idComponente) {
        final Severity severity = getSeverity(nivel);
        final FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(idComponente, new FacesMessage(severity, message, detail));
    }


    /**
     * Añade mensaje al contexto para que lo trate la aplicación (growl,
     * messages,...).
     *
     * @param nivel   Nivel gravedad
     * @param message Mensaje
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message) {
        addMessageContext(nivel, message, message, null);
    }

    /**
     * Añade mensaje al contexto para que lo trate la aplicación (growl,
     * messages,...).
     *
     * @param nivel   Nivel gravedad
     * @param message Mensaje
     * @param detail  Detalle
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail) {
        addMessageContext(nivel, message, detail, null);
    }

    /**
     * Añade mensaje al contexto para que lo trate la aplicación (growl,
     * messages,...).
     *
     * @param nivel            Nivel gravedad
     * @param message          Mensaje
     * @param validationFailed añade la marca de error de validacion
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message,
                                         final boolean validationFailed) {
        addMessageContext(nivel, message, message, validationFailed, null);
    }

    /**
     * Añade mensaje al contexto para que lo trate la aplicación (growl,
     * messages,...).
     *
     * @param nivel            Nivel gravedad
     * @param message          Mensaje
     * @param validationFailed añade la marca de error de validacion
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message,
                                         final boolean validationFailed, final String idComponente) {
        addMessageContext(nivel, message, message, validationFailed, idComponente);
    }

    /**
     * Añade mensaje al contexto para que lo trate la aplicación (growl,
     * messages,...).
     *
     * @param nivel            Nivel gravedad
     * @param message          Mensaje
     * @param detail           Detalle
     * @param validationFailed añade la marca de error de validacion
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
                                         final boolean validationFailed, final String idComponente) {
        addMessageContext(nivel, message, detail, idComponente);

        if (validationFailed) {
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    public static void closeDialog(DialogResult result) {
        PrimeFaces.current().dialog().closeDynamic(result);
    }

}
