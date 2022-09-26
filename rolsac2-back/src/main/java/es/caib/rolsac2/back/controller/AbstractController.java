package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.utils.Constants;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ResourceBundle;

public abstract class AbstractController {

    @Inject
    private FacesContext context;

    @Inject
    protected SessionBean sessionBean;

    private String idioma;

    //@Inject
    //protected SecurityBean securityBean;

    /**
     * Modo de acceso
     **/
    private String modoAcceso;

    private boolean actualizadoSinGuardar = false;

    public void setOpcion(String literalOpcion) {
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "labels");
        String texto = bundle.getString(literalOpcion);
        sessionBean.setOpcion(texto);
    }

    /**
     * Método para comprobar si tiene permisos para acceder a la ventana.
     **/
    public void checkPermisos(String ventana) {
        /*if (!securityBean.tieneAcessoVentana(ventana, sessionBean.getPerfil())) {
            throw new SinPermisoDeAcesoException("");
        }*/
    }

    public boolean isPerfil(TypePerfiles typePerfil) {
        return sessionBean.getPerfil() != null && sessionBean.getPerfil() == typePerfil;
    }

    public boolean isSuperadministrador() {
        return isPerfil(TypePerfiles.SUPER_ADMINISTRADOR);
    }

    public boolean isAdministradorEntidad() {
        return isPerfil(TypePerfiles.ADMINISTRADOR_ENTIDAD);
    }

    public boolean isAdministradorContenidos() {
        return isPerfil(TypePerfiles.ADMINISTRADOR_CONTENIDOS);
    }

    public boolean isGestor() {
        return isPerfil(TypePerfiles.GESTOR);
    }

    public boolean isInformador() {
        return isPerfil(TypePerfiles.INFORMADOR);
    }

    public String getModoAcceso() {
        return modoAcceso;
    }

    public boolean isModoAlta() {
        return modoAcceso != null && TypeModoAcceso.ALTA == TypeModoAcceso.valueOf(modoAcceso);
    }

    public boolean isModoEdicion() {
        return modoAcceso != null && TypeModoAcceso.EDICION == TypeModoAcceso.valueOf(modoAcceso);
    }

    public boolean isModoConsulta() {
        return modoAcceso != null && TypeModoAcceso.CONSULTA == TypeModoAcceso.valueOf(modoAcceso);
    }

    public void setModoAcceso(String modoAcceso) {
    	if(modoAcceso!=null) {
    		this.modoAcceso = modoAcceso;
    	}
    }

    protected FacesContext getContext() {
        return context;
    }

    protected boolean isUserRoleRSCAdmin() {
        return context.getExternalContext().isUserInRole(Constants.RSC_ADMIN);
    }

    protected boolean isUserRoleRSCUser() {
        return context.getExternalContext().isUserInRole(Constants.RSC_USER);
    }

    protected boolean isUserRoleRSCMentira() {
        return context.getExternalContext().isUserInRole("TROLA");
    }

    protected String getUserName() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    protected ResourceBundle getBundle(String bundleName) {
        return context.getApplication().getResourceBundle(context, bundleName);
    }

    public String getLiteral(String literal) {
        ResourceBundle labelsBundle = getBundle("labels");
        return labelsBundle.getString(literal);
    }

    protected void addGlobalMessage(String message) {
        context.addMessage(null, new FacesMessage(message));
    }

    protected void keepMessages() {
        context.getExternalContext().getFlash().setKeepMessages(true);
    }

    public void ayuda() {
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "La sección de ayuda no está implementada");// UtilJSF.getLiteral("info.borrado.ok"));
    }

    protected void setearIdioma() {
        idioma = sessionBean.getLang();
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public boolean isActualizadoSinGuardar() {
        return actualizadoSinGuardar;
    }

    public void setActualizadoSinGuardar(boolean actualizadoSinGuardar) {
        this.actualizadoSinGuardar = actualizadoSinGuardar;
    }

    public void actualizadoComponente() {
        this.actualizadoSinGuardar = true;
    }
}
