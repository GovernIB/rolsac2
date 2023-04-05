package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.back.controller.maestras.*;
import es.caib.rolsac2.back.controller.maestras.tipo.*;
import es.caib.rolsac2.back.exception.NoAutorizadoException;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.utils.Constants;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractController {

    @Inject
    private FacesContext context;
    @Inject
    protected SessionBean sessionBean;
    @Inject
    private AdministracionEntServiceFacade administracionEntServiceFacade;

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

    /**
     * Metodo utilizado para verificar si se ha dado de alta el plugin de traducción para una entidad determinada
     * return boolean
     */
    public boolean tradExiste() {
        return administracionEntServiceFacade.existePluginTipoByEntidad(sessionBean.getEntidad().getCodigo()
                , TypePluginEntidad.TRADUCCION.toString());
    }

    public void permisoAccesoVentana(Class clase) {
        if (!(clase != null && (isSuperadministrador()
                && (clase.equals(ViewEntidades.class) || clase.equals(ViewConfiguracionesGlobales.class)
                || clase.equals(ViewTipoAfectacion.class) || clase.equals(ViewTipoMateriaSIA.class)
                || clase.equals(ViewTipoFormaInicio.class) || clase.equals(ViewTipoNormativa.class)
                || clase.equals(ViewTipoSexo.class) || clase.equals(ViewTipoBoletin.class)
                || clase.equals(ViewTipoVia.class) || clase.equals(ViewTipoPublicoObjetivo.class)
                || clase.equals(ViewTipoSilencioAdministrativo.class)
                || clase.equals(ViewTipoLegitimacion.class) || clase.equals(ViewUsuario.class))
                || isAdministradorEntidad() && (clase.equals(ViewConfiguracionEntidad.class)
                || clase.equals(ViewPlugins.class) || clase.equals(ViewUsuario.class) || clase.equals(ViewEntidadRaiz.class)
                || clase.equals(ViewRoles.class) || clase.equals(ViewTipoUnidadAdministrativa.class)
                || clase.equals(ViewTipoMediaUA.class) || clase.equals(ViewPlatTramitElectronica.class)
                || clase.equals(ViewTipoTramitacion.class) || clase.equals(ViewTipoProcedimiento.class)
                || clase.equals(ViewTema.class) || clase.equals(ViewPublicoObjetivoEntidad.class)
                || clase.equals(ViewTipoMediaFicha.class) || clase.equals(ViewAlertas.class)
                || clase.equals(ViewEventosPlat.class)
                || clase.equals(ViewProcesos.class) || clase.equals(ViewProcesosSolr.class) || clase.equals(ViewProcesosSIA.class) || clase.equals(ViewProcesosLog.class)
                || clase.equals(ViewEvolucion.class) || clase.equals(ViewContenidos.class)
                || clase.equals(ViewUnidadAdministrativa.class)) || clase.equals(ViewLOPD.class)
                || clase.equals(ViewOrganigramaDir3.class)
                || (isAdministradorContenidos() || isGestor() || isInformador())
                && (clase.equals(ViewUnidadAdministrativa.class) || clase.equals(ViewNormativa.class)
                || clase.equals(ViewProcedimientos.class) || clase.equals(ViewServicios.class)
        )))
        ) {
            throw new NoAutorizadoException();
        }
        if (isInformador()) {
            setModoAcceso(TypeModoAcceso.CONSULTA.toString());
        }
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
        if (modoAcceso != null) {
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

    public String getLiteralFaltanIdiomas(String campo, String msg, List<String> idiomasPendientes) {
        return getLiteral(campo) + ": " + getLiteral(msg) + idiomasPendientes;
    }

    /**
     * Obtiene el valor de literal.
     *
     * @param key        key
     * @param parametros parametros para sustituir en el literal
     * @return el valor de literal
     */
    public String getLiteral(final String key, final Object[] parametros) {
        ResourceBundle labelsBundle = getBundle("labels");
        return MessageFormat.format(labelsBundle.getString(key), parametros);
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
