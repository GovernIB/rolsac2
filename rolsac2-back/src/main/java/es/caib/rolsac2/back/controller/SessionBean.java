package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.back.security.Security;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.*;

/**
 * Bean per mantener todos los datos importantes de session. En resumen:
 * <ul>
 * <li>Usuario</li>
 * <li>Permisos</li>
 * <li>UA del usuario</li>
 * <li>Idioma</li>
 * </ul>
 *
 * @author Indra
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {

    private static final long serialVersionUID = -3709390221710580769L;

    private static final Logger LOG = LoggerFactory.getLogger(SessionBean.class);

    @Inject
    SystemServiceFacade systemServiceBean;

    @Inject
    private Security seguridad;

    @Inject
    private FacesContext context;

    @Inject
    private AdministracionEntServiceFacade administracionEntServiceFacade;

    @Inject
    private AdministracionSupServiceFacade entidadservice;
    @Inject
    private UnidadAdministrativaServiceFacade uaservice;

    private UnidadAdministrativaDTO unidad;


    @Inject
    private AdministracionSupServiceFacade administracionSupServiceFacade;
    private UnidadAdministrativaDTO unidadActiva;
    private List<UnidadAdministrativaDTO> unidades;


    /**
     * Entidades
     **/
    private EntidadDTO entidad;
    private List<EntidadDTO> entidades;

    private Map<String, Object> mochilaDatos;

    /**
     * Locale actual de l'usuari
     */
    private Locale current;

    /**
     * Idioma.
     **/
    private String lang;

    /*Idiomas permitidos en entidad*/
    private List<String> idiomasPermitidos;

    /*Idiomas obligatorios en entidad*/;
    private List<String> idiomasObligatorios;


    /**
     * Perfil activo del usuario
     **/
    private TypePerfiles perfil;
    /**
     * Perfiles del usuario
     **/
    private List<TypePerfiles> perfiles;

    /**
     * Opcion seleccionada
     **/
    private String opcion = "dict.opcion";

    private String opcionActiva = "";

    private String style = "font-weight-bold";

    /*Atributos para el ancho y largo de los dialog*/
    private String screenWidth;

    private String screenHeight;

    /**
     * Inicializacion de los datos de usuario
     */
    @PostConstruct
    private void init() {
        LOG.info("Inicialitzant locale de l'usuari");
        Application app = context.getApplication();
        current = app.getViewHandler().calculateLocale(context);
        lang = current.getDisplayLanguage().contains("ca") ? "ca" : "es";
        // inicializamos mochila
        mochilaDatos = new HashMap<>();

        opcion = "dict.opcion";
        // cargarDatosMockup();
        // cargarDatos2();
        cargarDatos();
    }

    public boolean isPerfil(TypePerfiles typePerfil) {
        return perfil != null && perfil == typePerfil;
    }

    private String getUsuarioMockup() {
        String ruta = System.getProperty("es.caib.rolsac2.properties", null);
        if (ruta == null || ruta.isEmpty()) {
            return null;
        }

        try (InputStream input = new FileInputStream(ruta)) {

            Properties prop = new Properties();
            prop.load(input);
            String usuario = prop.getProperty("mockup.sesion.usuario");
            return usuario;
        } catch (Exception e) {
            return null;
        }
    }

    private void cargarDatos() {
        String idUsuario = getUsuarioMockup();
        Long lUsuario = 1l;
        if (idUsuario != null && !idUsuario.isEmpty()) {
            lUsuario = Long.valueOf(idUsuario);
        }
        UsuarioDTO usuario = administracionEntServiceFacade.findUsuarioById(lUsuario);

        if (usuario.getEntidad() != null) {
            entidades = new ArrayList<>();
            entidad = usuario.getEntidad();
            if (Boolean.TRUE.equals(entidad.getActiva())) {
                entidades.add(entidad);
            }
        }

        /*if (usuario.getUsuarioUnidadAdministrativa() != null) {
            unidades = new ArrayList<>();

            for (UsuarioUnidadAdministrativaDTO usuarioUnidadAdministrativa : usuario
                    .getUsuarioUnidadAdministrativa()) {
                UnidadAdministrativaDTO ua = usuarioUnidadAdministrativa.getUnidadAdministrativa();
                if (Boolean.TRUE.equals(entidad.getActiva()) && ua.getEntidad().getCodigo().equals(entidad.getCodigo())) {
                    unidades.add(usuarioUnidadAdministrativa.getUnidadAdministrativa());
                }
            }
        }*/

        if (usuario.getUnidadesAdministrativas() != null) {
            unidades = uaservice.getUnidadesAdministrativasByUsuario(usuario.getCodigo());
        }


        if (unidad == null || !unidades.isEmpty()) {
            unidad = unidades.get(0);
            unidadActiva = unidades.get(0);
        }

        perfiles = seguridad.getPerfiles();
        if (!perfiles.isEmpty()) {
            perfil = perfiles.get(0);
        }
    }

    /**
     * Redirige a la URL por defecto para el rol activo.
     */
    public void redirectDefaultUrl() {
        UtilJSF.redirectJsfDefaultPagePerfil(perfil);
        switch (this.perfil) {
            case ADMINISTRADOR_CONTENIDOS:
                opcion = "viewUnidadAdministrativa.titulo";
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewConfiguracionEntidad.titulo";
                break;
            case GESTOR:
                opcion = "viewUnidadAdministrativa.titulo";
                break;
            case INFORMADOR:
                break;
            case SUPER_ADMINISTRADOR:
                opcion = "viewTipoEntidades.titulo";
                break;
        }


    }


    private void cargarDatos2() {
        try {
            cargarDatosMockupPerfiles();
            UnidadAdministrativaDTO nieto = uaservice.findById(33l);
            unidadActiva = nieto;
            if (nieto == null) {
                cargarDatosMockup();
                cargarDatosMuckupEntidad();
            } else {
                cargarDatosMuckupEntidad();
                EntidadDTO entidad1 = administracionSupServiceFacade.findEntidadById(61l);
                EntidadDTO entidad2 = administracionSupServiceFacade.findEntidadById(5l);

                entidad = entidad1;
                entidades = new ArrayList<>();
                entidades.add(entidad1);
                entidades.add(entidad2);
            }
        } catch (Exception e) {
            cargarDatosMockup();
            cargarDatosMuckupEntidad();
        }
    }

    private void cargarDatosMockupPerfiles() {
        perfil = TypePerfiles.ADMINISTRADOR_CONTENIDOS;
        perfiles = new ArrayList<TypePerfiles>();
        perfiles.add(TypePerfiles.SUPER_ADMINISTRADOR);
        perfiles.add(TypePerfiles.ADMINISTRADOR_ENTIDAD);
        perfiles.add(TypePerfiles.ADMINISTRADOR_CONTENIDOS);
        perfiles.add(TypePerfiles.GESTOR);
        perfiles.add(TypePerfiles.INFORMADOR);
    }

    private void cargarDatosMockup() {
        // Obtenemos la UA del usuario (mockup)

        UnidadAdministrativaDTO hijo = new UnidadAdministrativaDTO();
        hijo.setCodigo(1l);
        Literal literalHijo = new Literal();
        List<Traduccion> traduccionHijo = new ArrayList<>();
        traduccionHijo.add(new Traduccion("es", "GOIB"));
        traduccionHijo.add(new Traduccion("ca", "GOIB"));
        literalHijo.setTraducciones(traduccionHijo);
        hijo.setNombre(literalHijo);
        hijo.setPadre(null);

        UnidadAdministrativaDTO padre = new UnidadAdministrativaDTO();
        padre.setCodigo(2l);
        Literal literalPadre = new Literal();
        List<Traduccion> traduccionPadre = new ArrayList<>();
        traduccionPadre.add(new Traduccion("es", "Conselleria d'educación"));
        traduccionPadre.add(new Traduccion("ca", "Conselleria d'educación"));
        literalPadre.setTraducciones(traduccionPadre);
        padre.setNombre(literalPadre);
        padre.setPadre(hijo);

        UnidadAdministrativaDTO nieto = new UnidadAdministrativaDTO();
        nieto.setCodigo(3l);
        Literal literalNieto = new Literal();
        List<Traduccion> traduccionesNieto = new ArrayList<>();
        traduccionesNieto.add(new Traduccion("es", "Secretaría de educación"));
        traduccionesNieto.add(new Traduccion("ca", "Secretaria d'educació"));
        literalNieto.setTraducciones(traduccionesNieto);
        nieto.setNombre(literalNieto);
        nieto.setPadre(padre);

        // unidad = padre;
        unidadActiva = nieto;
        // Ayto Baleares
        /*
         * UnidadAdministrativaDTO uaAyto = new UnidadAdministrativaDTO(); uaAyto.setId(4l); Literal literaluaAyto = new
         * Literal(); List<Traduccion> traduccionesuaAyto = new ArrayList<>(); traduccionesuaAyto.add(new
         * Traduccion("es", "Ayto. Mallorca")); traduccionesuaAyto.add(new Traduccion("ca", "Ajunt. Mallorca"));
         * literaluaAyto.setTraducciones(traduccionesuaAyto); uaAyto.setNombre(literaluaAyto); uaAyto.setPadre(null);
         * unidades.add(uaAyto);
         */


    }

    public void cargarDatosMuckupEntidad() {
        // ID 33
        EntidadDTO entidad1 = new EntidadDTO();
        Literal literalEnt1 = new Literal();
        List<Traduccion> traduccionesEnt1 = new ArrayList<>();
        traduccionesEnt1.add(new Traduccion("es", "GOIB"));
        traduccionesEnt1.add(new Traduccion("ca", "GOIB"));
        literalEnt1.setTraducciones(traduccionesEnt1);
        entidad1.setDescripcion(literalEnt1);

        EntidadDTO entidad2 = new EntidadDTO();
        Literal literalEnt2 = new Literal();
        List<Traduccion> traduccionesEnt2 = new ArrayList<>();
        traduccionesEnt2.add(new Traduccion("es", "Ayto. Mallorca"));
        traduccionesEnt2.add(new Traduccion("ca", "Ajunt. Mallorca"));
        literalEnt2.setTraducciones(traduccionesEnt2);
        entidad2.setDescripcion(literalEnt2);

        entidad = entidad1;
        entidades = new ArrayList<>();
        entidades.add(entidad1);
        entidades.add(entidad2);
    }

    public String getUrl() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url;
    }

    public String getUri() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURI().toString();
        return url;
    }

    public void cambiarUnidadAdministrativa(UnidadAdministrativaDTO ua) {
        if (unidadActiva != null && ua != null) {
            cambiarUnidadAdministrativaRecursivo(unidadActiva, ua);
        }
    }

    /*
     * @Deprecated public void actualizarUnidadAdministrativa(UnidadAdministrativaDTO ua) { if (ua != null &&
     * unidades.contains(ua)) { unidadActiva = ua; unidad = ua; } }
     */

    public void actualizarEntidad(EntidadDTO ent) {
        if (ent != null && entidades.contains(ent)) {
            // TODO Faltaría calcular la unidad activa conectandose a bbdd
            // unidadActiva = ua;
            entidad = ent;
        }
    }


    private void cambiarUnidadAdministrativaRecursivo(UnidadAdministrativaDTO unidadAdministrativa,
                                                      UnidadAdministrativaDTO ua) {
        if (unidadAdministrativa == null || ua == null || unidadAdministrativa.getCodigo().compareTo(ua.getCodigo()) == 0) {
            this.unidadActiva = ua;
            return;
        } else {
            cambiarUnidadAdministrativaRecursivo(unidadAdministrativa.getPadre(), ua);
        }
    }

    public List<UnidadAdministrativaDTO> getUnidadesAdministrativasActivas() {
        List<UnidadAdministrativaDTO> unidades = new ArrayList<>();

        if (unidadActiva != null) {
            unidades = getUnidadesRecursivo(unidadActiva);
        }
        return unidades;
    }

    /**
     * Método que de manera recursiva devuelve las UAs en modo lista poniendo a la raiz la primera.
     *
     * @param ua
     * @return
     */
    private List<UnidadAdministrativaDTO> getUnidadesRecursivo(UnidadAdministrativaDTO ua) {
        if (ua == null) {
            return new ArrayList<>();
        } else if (ua.getPadre() == null) {
            List<UnidadAdministrativaDTO> uas = new ArrayList<>();
            uas.add(ua);
            return uas;
        } else {
            List<UnidadAdministrativaDTO> uas = getUnidadesRecursivo(ua.getPadre());
            uas.add(ua);
            return uas;
        }
    }

    // Mètodes
    public void reload() {
        context.getPartialViewContext().getEvalScripts().add("location.replace(location)");
    }

    public void reloadEntidad() {
        String idUsuario = getUsuarioMockup();
        Long lUsuario = 1l;
        if (idUsuario != null && !idUsuario.isEmpty()) {
            lUsuario = Long.valueOf(idUsuario);
        }
        UsuarioDTO usuario = administracionEntServiceFacade.findUsuarioById(lUsuario);

        if (usuario.getEntidad() != null) {
            entidades = new ArrayList<>();
            entidad = usuario.getEntidad();
            if (Boolean.TRUE.equals(entidad.getActiva())) {
                entidades.add(entidad);
            }
        }
    }

    // Mètodes
    public void reloadPerfil() {
        String rolsac2back = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        switch (this.perfil) {
            case ADMINISTRADOR_CONTENIDOS:
                opcion = "viewUnidadAdministrativa.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/superadministrador/viewUnidadAdministrativa.xhtml')");
                reloadEntidad();
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewConfiguracionEntidad.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/entidades/viewConfiguracionEntidad.xhtml')");
                reloadEntidad();
                break;
            case GESTOR:
                opcion = "viewUnidadAdministrativa.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/superadministrador/viewUnidadAdministrativa.xhtml')");
                reloadEntidad();
                break;
            case INFORMADOR:
                context.getPartialViewContext().getEvalScripts().add("location.replace(location)");
                reloadEntidad();
                break;
            case SUPER_ADMINISTRADOR:
                opcion = "viewTipoEntidades.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/superadministrador/viewEntidades.xhtml')");
                break;
        }

    }

    public void updateLogo() {
        UIComponent imgLogo = UtilJSF.findComponent("imgLogo");
        UIComponent imgDefecto = UtilJSF.findComponent("imgDefecto");
        if (checkLogo()) {
            imgDefecto.getAttributes().put("style", "display: none");
            imgLogo.getAttributes().put("style", "");
        } else {
            imgLogo.getAttributes().put("style", "display: none");
            imgDefecto.getAttributes().put("style", "");
        }

    }

    public String getPathAbsoluto() {
        return systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
    }

    /**
     * Función que verifica que opción de las posibles del menú es la que está seleccionada
     */

    public boolean isActive(String opcionActiva) {
        return this.opcion.equals(opcionActiva);
    }

    /**
     * Función para establecer si la opción activa seleccionada es de un desplegable de Tipos
     */

    public boolean isTipoActivo() {
        List<String> tiposViewIds = new ArrayList<>();
        tiposViewIds.add("/maestras/tipo/viewTipoAfectacion.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoMateriaSIA.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoFormaInicio.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoNormativa.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoSexo.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoBoletin.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoVia.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoPublicoObjetivo.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoSilencioAdministrativo.xhtml");
        tiposViewIds.add("/maestras/tipo/viewTipoLegitimacion.xhtml");

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return tiposViewIds.contains(viewId);
    }

    public boolean isAdminUsuariosActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            List<String> tiposViewIds = new ArrayList<>();
            tiposViewIds.add("/entidades/viewUsuario.xhtml");
            tiposViewIds.add("/entidades/viewRoles.xhtml");
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return tiposViewIds.contains(viewId);
        } else {
            return false;
        }
    }

    public boolean isPersonalizacionActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            List<String> tiposViewIds = new ArrayList<>();
            tiposViewIds.add("/maestras/tipo/viewTipoUnidadAdministrativa.xhtml");
            tiposViewIds.add("/entidades/viewTipoMediaUA.xhtml");
            tiposViewIds.add("/entidades/viewPlatTramitElectronica.xhtml");
            tiposViewIds.add("/maestras/tipo/viewTipoTramitacion.xhtml");
            tiposViewIds.add("/maestras/tipo/viewTipoProcedimiento.xhtml");
            tiposViewIds.add("/superadministrador/viewTema.xhtml");
            tiposViewIds.add("/maestras/viewPublicoObjetivoEntidad.xhtml");
            tiposViewIds.add("/entidades/viewTipoMediaFicha.xhtml");
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return tiposViewIds.contains(viewId);
        } else {
            return false;
        }
    }

    public boolean isMonitorizacionActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            List<String> tiposViewIds = new ArrayList<>();
            tiposViewIds.add("/monitorizacion/viewAlertas.xhtml");
            tiposViewIds.add("/monitorizacion/viewCuadroControl.xhtml");
            tiposViewIds.add("/monitorizacion/viewEventosPlat.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesos.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesosLog.xhtml");
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return tiposViewIds.contains(viewId);
        } else {
            return false;
        }
    }

    public boolean isGestionOrganigramaActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            List<String> tiposViewIds = new ArrayList<>();
            tiposViewIds.add("/entidades/viewEvolucion.xhtml");
            tiposViewIds.add("/entidades/viewContenidos.xhtml");
            tiposViewIds.add("/superadministrador/viewUnidadAdministrativa.xhtml");
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return tiposViewIds.contains(viewId);
        } else {
            return false;
        }
    }

    public boolean isSuperAdministrador() {
        return this.getPerfil() == TypePerfiles.SUPER_ADMINISTRADOR;
    }

    public boolean checkLogo() {
        if (this.perfil == TypePerfiles.SUPER_ADMINISTRADOR) {
            return false;
        } else if (this.entidad.getLogo() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void updateAspect() {
        String width = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("formAspect:windowWidth");
        String height = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("formAspect:windowHeight");
        if (width != null) {
            this.screenWidth = width;
        }
        if (height != null) {
            this.screenHeight = height;
        }
    }

    public String getIdiomasPermitidos() {
        String idiomas = "";
        if (perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            for (TypeIdiomaFijo idioma : TypeIdiomaFijo.values()) {
                idiomas += idioma.toString() + ';';
            }
            for (TypeIdiomaOpcional idioma : TypeIdiomaOpcional.values()) {
                idiomas += idioma.toString() + ';';
            }
            return idiomas;
        } else {
            return this.entidad.getIdiomasPermitidos();
        }
    }

    public List<String> getIdiomasPermitidosList() {
        List<String> idiomas = new ArrayList<>();
        if (perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            for (TypeIdiomaFijo idioma : TypeIdiomaFijo.values()) {
                idiomas.add(idioma.toString());
            }
            for (TypeIdiomaOpcional idioma : TypeIdiomaOpcional.values()) {
                idiomas.add(idioma.toString());
            }
            return idiomas;
        } else {
            return List.of(this.entidad.getIdiomasPermitidos().split(";"));
        }
    }

    public String getIdiomasObligatorios() {
        String idiomas = "";
        if (perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            for (TypeIdiomaFijo idioma : TypeIdiomaFijo.values()) {
                idiomas += idioma.toString() + ';';
            }
            return idiomas;
        } else {
            return this.entidad.getIdiomasObligatorios();
        }
    }

    public List<String> getIdiomasObligatoriosList() {
        List<String> idiomas = new ArrayList<>();
        if (perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            for (TypeIdiomaFijo idioma : TypeIdiomaFijo.values()) {
                idiomas.add(idioma.toString());
            }
            return idiomas;
        } else {
            return List.of(this.entidad.getIdiomasObligatorios().split(";"));
        }
    }

    public StreamedContent getLogoEntidad() {
        try {
            FicheroDTO logo = entidadservice.getLogoEntidad(this.entidad.getLogo().getCodigo());
            String mimeType = URLConnection.guessContentTypeFromName(logo.getFilename());
            InputStream fis = new ByteArrayInputStream(logo.getContenido());
            StreamedContent file = DefaultStreamedContent.builder()
                    .name(logo.getFilename())
                    .contentType(mimeType)
                    .stream(() -> fis)
                    .build();
            return file;
        } catch (Exception e) {
            LOG.error("Error obtiendo el logo ", e);
            return null;
        }
    }


    public Locale getCurrent() {
        return current;
    }

    public void setCurrent(Locale current) {
        this.current = current;
        this.lang = current.getDisplayLanguage().contains("ca") ? "ca" : "es";
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public TypePerfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(TypePerfiles perfil) {
        this.perfil = perfil;
    }

    public List<TypePerfiles> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<TypePerfiles> perfiles) {
        this.perfiles = perfiles;
    }


    public UnidadAdministrativaDTO getUnidadActiva() {
        return unidadActiva;
    }

    public void setUnidadActiva(UnidadAdministrativaDTO unidadActiva) {
        this.unidadActiva = unidadActiva;
    }

    public Map<String, Object> getMochilaDatos() {
        return mochilaDatos;
    }

    public void setMochilaDatos(Map<String, Object> mochilaDatos) {
        this.mochilaDatos = mochilaDatos;
    }

    public void anyadirMochila(String key, Object value) {
        if (this.mochilaDatos == null) {
            this.mochilaDatos = new HashMap<>();
        }
        this.mochilaDatos.put(key, value);
    }

    public Object getValorMochilaByKey(String key) {
        return this.mochilaDatos == null ? null : this.mochilaDatos.get(key);
    }

    public void vaciarMochila() {
        this.mochilaDatos = new HashMap<>();
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public void setearOpcion(String opcion) {
        UIComponent opcionAnterior = UtilJSF.findComponent(this.opcion);
        if (opcionAnterior != null && opcionAnterior.getAttributes().get("style") != null) {
            opcionAnterior.getAttributes().put("style", "");
        }
        this.opcion = opcion;
        UIComponent opcionActualizada = UtilJSF.findComponent(this.opcion);
        if (opcionActualizada != null && opcionActualizada.getAttributes().get("style") != null) {
            opcionActualizada.getAttributes().put("style", "font-weight: bold");
        }
    }

    public void setearMigaPan() {
        cambiarUnidadAdministrativa(unidadActiva);
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public List<EntidadDTO> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<EntidadDTO> entidades) {
        this.entidades = entidades;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public Integer getScreenWidthInt() {
        return screenWidth == null ? null : Integer.parseInt(screenWidth);
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }



}
