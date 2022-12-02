package es.caib.rolsac2.back.utils;

import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.controller.maestras.ViewEntidades;
import es.caib.rolsac2.back.controller.maestras.ViewPersonal;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import org.primefaces.PrimeFaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilJSF {

    /**
     * Parametro de sesion para securizar apertura dialogs.
     */
    private static final String SEC_OPEN_DIALOG = "SEC_OPEN_DIALOG";

	/** Path views. */
	public static final String PATH_VIEWS_SUPER_ADMIN = "/superadministrador/";
	public static final String PATH_VIEWS_ENTIDAD = "/entidades/";
	public static final String PATH_VIEWS_MAESTRAS = "/maestras/";

	/** Extensión .html **/
	private static final String EXTENSION_XHTML = ".xhtml";

	/** Log. */
	final static Logger LOG = LoggerFactory.getLogger(UtilJSF.class);

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
        getSessionBean().getMochilaDatos().put(SEC_OPEN_DIALOG, secOpenDialog);

        // Abre dialogo
        PrimeFaces.current().dialog().openDynamic(dialog, options, paramsDialog);
    }

	/**
	 * Redirige pagina JSF por defecto para role.
	 *
	 * @param jsfPage path JSF page
	 */
	public static void redirectJsfDefaultPagePerfil(final TypePerfiles perfil) {
		redirectJsfPage(getDefaultUrlPerfil(perfil));
	}

	/**
	 * Redirige pagina JSF.
	 *
	 * @param jsfPage path JSF page
	 */
	public static void redirectJsfPage(final String jsfPage) {
		try {
			final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext();
			final String contextPath = servletContext.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + jsfPage);
		} catch (final IOException e) {
			UtilJSF.loggearErrorFront("Error redirigiendo", e);
		}
	}

	/**
	 * Devuelve url por defecto segun perfil.
	 *
	 * @param role role
	 * @return url
	 */
	public static String getDefaultUrlPerfil(final TypePerfiles perfil) {
		String url = null;
		if (perfil == null) {
			url = "/error/errorUsuarioSinRol.xhtml";
		} else {
			switch (perfil) {
			case SUPER_ADMINISTRADOR:
				url = PATH_VIEWS_SUPER_ADMIN + UtilJSF.getViewNameFromClass(ViewEntidades.class) + EXTENSION_XHTML;
				break;
			case ADMINISTRADOR_ENTIDAD:
				url = PATH_VIEWS_ENTIDAD + "viewConfiguracionEntidad" + EXTENSION_XHTML;
				break;
			case ADMINISTRADOR_CONTENIDOS:
				url = PATH_VIEWS_MAESTRAS + UtilJSF.getViewNameFromClass(ViewPersonal.class) + EXTENSION_XHTML;
				break;
				//TO DO SUSTITUIR AL CREAR SU RESPECTIVA PÁGINA
			case GESTOR:
				url = PATH_VIEWS_SUPER_ADMIN + UtilJSF.getViewNameFromClass(ViewEntidades.class) + EXTENSION_XHTML;
				break;
				//TO DO SUSTITUIR AL CREAR SU RESPECTIVA PÁGINA
			case INFORMADOR:
				url = PATH_VIEWS_SUPER_ADMIN + UtilJSF.getViewNameFromClass(ViewEntidades.class) + EXTENSION_XHTML;
				break;
			default:
				url = "/error/errorUsuarioSinRol.xhtml";
				break;
			}
		}
		return url;
	}

	/**
	 * Devuelve view name suponiendo que se llama igual que la clase.
	 *
	 * @param clase clase
	 * @return view name
	 */
	public static String getViewNameFromClass(final Class<?> clase) {
		final String className = clase.getSimpleName();
		return className.substring(0, 1).toLowerCase() + className.substring(1);
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
     * @param detail           Detalle del mensaje
     * @param validationFailed añade la marca de error de validacion
     */
    public static void addMessageContext(final TypeNivelGravedad nivel, final String message,
                                         final String detail, final boolean validationFailed) {
        addMessageContext(nivel, message, detail, validationFailed, null);
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

    public static SessionBean getSessionBean() {
        return (SessionBean) FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionBean}", SessionBean.class);

    }

    public static void anyadirMochila(String literal, Object value) {
        getSessionBean().anyadirMochila(literal, value);
    }

    public static void vaciarMochila() {
        getSessionBean().vaciarMochila();
    }

    public static Object getValorMochilaByKey(String literal) {
        return getSessionBean().getValorMochilaByKey(literal);
    }

    public static Object getDialogParam(String parametro) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
    }

	public static void loggearErrorFront(String mensaje, Exception e) {
		if (e == null) {
			LOG.error(mensaje);
		} else {
			LOG.error(mensaje, e);
		}
	}

    public static UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component != null
                        && id.equals(component.getId())) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }

}
