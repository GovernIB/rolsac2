package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.back.security.Security;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
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

        if (usuario.getUsuarioUnidadAdministrativa() != null) {
            unidades = new ArrayList<>();

            for (UsuarioUnidadAdministrativaDTO usuarioUnidadAdministrativa : usuario
                    .getUsuarioUnidadAdministrativa()) {
                UnidadAdministrativaDTO ua = usuarioUnidadAdministrativa.getUnidadAdministrativa();
                if (Boolean.TRUE.equals(entidad.getActiva()) && ua.getEntidad().getCodigo().equals(entidad.getCodigo())) {
                    unidades.add(usuarioUnidadAdministrativa.getUnidadAdministrativa());
                }
            }
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
                opcion = "viewPersonal.titulo";
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewConfiguracionEntidad.titulo";
                break;
            case GESTOR:
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

    // Mètodes
    public void reloadPerfil() {
        String rolsac2back = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        switch (this.perfil) {
            case ADMINISTRADOR_CONTENIDOS:
                opcion = "viewPersonal.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/maestras/viewPersonal.xhtml')");
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewConfiguracionEntidad.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/entidades/viewConfiguracionEntidad.xhtml')");
                break;
            case GESTOR:
                context.getPartialViewContext().getEvalScripts().add("location.replace(location)");
                break;
            case INFORMADOR:
                context.getPartialViewContext().getEvalScripts().add("location.replace(location)");
                break;
            case SUPER_ADMINISTRADOR:
                opcion = "viewTipoEntidades.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/superadministrador/viewEntidades.xhtml')");
                break;
        }

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
        if (this.getPerfil() == TypePerfiles.SUPER_ADMINISTRADOR) {
            boolean resultado = this.opcion.equals("viewTipoAfectacion.titulo") || this.opcion.equals("viewTipoMateriaSIA.titulo") || this.opcion.equals("viewTipoFormaInicio.titulo") ||
                    this.opcion.equals("viewTipoNormativa.titulo") || this.opcion.equals("viewTipoSexo.titulo") || this.opcion.equals("viewTipoBoletin.titulo") ||
                    this.opcion.equals("viewTipoVia.titulo") || this.opcion.equals("viewTipoPublicoObjetivo.titulo") || this.opcion.equals("viewTipoSilencioAdministrativo.titulo") ||
                    this.opcion.equals("viewTipoLegitimacion.titulo");
            return resultado;
        } else {
            return false;
        }
    }

    public boolean isAdminUsuariosActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            return this.opcion.equals("viewUsuario.titulo") || this.opcion.equals("viewRoles.titulo");
        } else {
            return false;
        }
    }

    public boolean isPersonalizacionActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            return this.opcion.equals("viewTipoUnidadAdministrativa.titulo") || this.opcion.equals("viewTipoMediaUA.titulo")
                    || this.opcion.equals("viewEdificios.titulo") || this.opcion.equals("viewTipoMediaEdificio.titulo")
                    || this.opcion.equals("viewTipoMediaEdificio.titulo") || this.opcion.equals("viewPlatTramitElectronica.titulo")
                    || this.opcion.equals("viewTipoTramitacion.titulo") || this.opcion.equals("viewTipoMediaFicha.titulo")
                    || this.opcion.equals("viewTema.titulo");
        } else {
            return false;
        }
    }

    public boolean isMonitorizacionActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            return this.opcion.equals("viewAlertas.titulo") || this.opcion.equals("viewGestionProcesosIndex.titulo")
                    || this.opcion.equals("viewGestionProcesosSIA.titulo") || this.opcion.equals("viewEventosPlat.titulo")
                    || this.opcion.equals("viewCuadroControl.titulo");
        } else {
            return false;
        }
    }

    public boolean isGestionOrganigramaActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            return this.opcion.equals("viewEvolucion.titulo") || this.opcion.equals("viewContenidos.titulo");
        } else {
            return false;
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

    public void setearOpcion() {
        this.opcion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("opcion");
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
