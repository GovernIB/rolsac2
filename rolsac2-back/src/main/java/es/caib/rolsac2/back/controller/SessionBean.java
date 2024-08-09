package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.back.exception.GestorSinUAExcepcion;
import es.caib.rolsac2.back.exception.NoAutorizadoException;
import es.caib.rolsac2.back.exception.PerfilException;
import es.caib.rolsac2.back.security.Security;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeAlertas;
import es.caib.rolsac2.service.model.types.TypeIdiomaFijo;
import es.caib.rolsac2.service.model.types.TypeIdiomaOpcional;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Bean per mantener todos los datos importantes de session. En resumen:
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

    @EJB
    private AdministracionEntServiceFacade administracionEntServiceFacade;

    @EJB
    private AdministracionSupServiceFacade entidadservice;

    @EJB
    private UnidadAdministrativaServiceFacade uaService;

    @EJB
    private AlertaServiceFacade alertaService;


    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    private UnidadAdministrativaDTO unidadActiva;

    private UnidadAdministrativaGridDTO unidadActivaAux;

    private UnidadAdministrativaGridDTO unidadActivaHijaAux;

    /**
     * Entidad activa
     **/
    private EntidadDTO entidad;

    /**
     * Listado de entidades asociadas al usuario
     */
    private List<EntidadGridDTO> entidades;

    /**
     * Mochila de datos que sirve para pasar info entre pantallas
     */
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
     * Perfiles del usuario
     **/
    private List<TypePerfiles> perfilesBack;

    /**
     * Roles del usuario
     */
    private List<String> roles;

    private List<AlertaDTO> alertasAvisos;

    /**
     * Opcion seleccionada
     **/
    private String opcion = "dict.opcion";

    private String opcionActiva = "";

    /*Atributos para el ancho y largo de los dialog*/
    private String screenWidth;

    private String screenHeight;

    private Boolean renderPanelHijas;

    /**
     * Usuario
     */
    private UsuarioDTO usuario;

    /**
     * Inicializacion de los datos de usuario
     */
    @PostConstruct
    private void init() {
        LOG.info("Inicialitzant locale de l'usuari");
        Application app = context.getApplication();
        current = app.getViewHandler().calculateLocale(context);
        lang = current.getDisplayLanguage().contains("ca") ? "ca" : "es";
        current = Locale.forLanguageTag(lang);
        // inicializamos mochila
        mochilaDatos = new HashMap<>();
        String idUsuario = seguridad.getIdentificadorUsuario();
        usuario = administracionEntServiceFacade.findUsuarioSimpleByIdentificador(seguridad.getIdentificadorUsuario(), lang);

        cargarDatos();
        cargarAlertas();

    }

    public void cargarAlertas() {
        alertasAvisos = alertaService.getAlertas(seguridad.getIdentificadorUsuario(), perfiles, lang);

    }

    /************************************************************************************************************************************************
     * CONTROL DE ACCESO A LA APLICACIÓN Y GESTIÓN DE SESIONES
     *************************************************************************************************************************************************/

    /**
     * Método utilizado para la carga inicial de datos en la aplicación (entidad, UA...)
     */
    private void cargarDatos() {
        perfiles = seguridad.getPerfiles();
        String idUsuario = seguridad.getIdentificadorUsuario();
        if (perfiles != null) {
            perfilesBack = new ArrayList<>();
            for (TypePerfiles typePerfil : perfiles) {
                if (!typePerfil.isRestApi()) {
                    perfilesBack.add(typePerfil);
                }
            }
        }
        if (administracionEntServiceFacade.checkIdentificadorUsuario(idUsuario)) {
            UsuarioDTO usuario = administracionEntServiceFacade.findUsuarioSimpleByIdentificador(idUsuario, lang);
            entidades = new ArrayList<>();
            List<Long> idEntidades = new ArrayList<>();
            for (EntidadGridDTO entidad : usuario.getEntidades()) {
                if (entidad.getActiva()) {
                    idEntidades.add(entidad.getCodigo());
                }
            }
            perfiles = seguridad.getPerfiles();
            roles = seguridad.getRoles(idEntidades);
            if (systemServiceBean.checkSesion(usuario.getCodigo())) {
                SesionDTO sesion = systemServiceBean.findSesionById(usuario.getCodigo());
                //if (!TypePerfiles.SUPER_ADMINISTRADOR.equals(TypePerfiles.fromString(sesion.getPerfil())) && !TypePerfiles.GESTOR.equals(TypePerfiles.fromString(sesion.getPerfil())) && !TypePerfiles.INFORMADOR.equals(TypePerfiles.fromString(sesion.getPerfil()))) {
                if (TypePerfiles.ADMINISTRADOR_ENTIDAD.equals(TypePerfiles.fromString(sesion.getPerfil())) || TypePerfiles.ADMINISTRADOR_CONTENIDOS.equals(TypePerfiles.fromString(sesion.getPerfil()))) {
                    entidad = administracionSupServiceFacade.findEntidadById(sesion.getIdEntidad());
                    unidadActiva = uaService.findUASimpleByID(sesion.getIdUa(), this.lang, null);
                    //unidadActivaAux = unidadActiva.convertDTOtoGridDTO();
                    unidadActivaAux = uaService.findUaRaizByEntidad(sesion.getIdEntidad()).convertDTOtoGridDTO();
                } else if (TypePerfiles.GESTOR.equals(TypePerfiles.fromString(sesion.getPerfil())) || TypePerfiles.INFORMADOR.equals(TypePerfiles.fromString(sesion.getPerfil()))) {
                    entidad = administracionSupServiceFacade.findEntidadById(sesion.getIdEntidad());
                    try {
                        UnidadAdministrativaDTO unidad = uaService.findUASimpleByID(sesion.getIdUa(), this.lang, null); //uaService.findById(sesion.getIdUa());
                        checkUaGestor(unidad);
                        unidadActiva = unidad;
                    } catch (GestorSinUAExcepcion e) {
                        //En este caso, la sesión es errónea, volvemos a generarla
                        systemServiceBean.deleteSesion(usuario.getCodigo());
                        cargarDatos();
                    }
                }
                perfil = TypePerfiles.fromString(sesion.getPerfil());
                actualizarPerfiles();
                actualizarEntidades();
                lang = sesion.getIdioma();
                sesion.setFechaUltimaSesion(new Date());
                systemServiceBean.updateSesion(sesion);
            } else {
                SesionDTO sesionDTO = new SesionDTO();
                sesionDTO.setIdioma(lang);
                sesionDTO.setFechaUltimaSesion(new Date());
                sesionDTO.setIdUsuario(usuario.getCodigo());
                if (perfiles.contains(TypePerfiles.SUPER_ADMINISTRADOR)) {
                    sesionDTO.setPerfil(TypePerfiles.SUPER_ADMINISTRADOR.toString());
                    this.perfil = TypePerfiles.SUPER_ADMINISTRADOR;
                    systemServiceBean.crearSesion(sesionDTO);
                } else {
                    this.perfil = checkPerfilPosible();
                    actualizarPerfiles();
                    actualizarUnidadAdministrativa(usuario, perfil, sesionDTO);
                    actualizarEntidades();
                    if (perfil.equals(TypePerfiles.GESTOR)) {
                        try {
                            checkUaGestor(unidadActiva);
                        } catch (GestorSinUAExcepcion e) {
                            String rolsac2back = context.getExternalContext().getRequestContextPath();
                            context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/error/uaRelacionadaException.xhtml')");
                        }
                    }
                    sesionDTO.setIdEntidad(this.entidad.getCodigo());
                    sesionDTO.setIdUa(this.unidadActiva.getCodigo());
                    sesionDTO.setPerfil(this.perfil.toString());
                    systemServiceBean.crearSesion(sesionDTO);
                }

            }
            reloadPerfil();

        } else {
            //Lanzar excepción y mostrar
            String rolsac2back = context.getExternalContext().getRequestContextPath();
            context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/error/usuarioAltaException.xhtml')");
        }

    }

    /**
     * Función que se utiliza para realizar el cambio de un perfil a otro
     *
     * @param perfil
     */
    public void cambioPerfil(TypePerfiles perfil) {
        String idUsuario = seguridad.getIdentificadorUsuario();
        UsuarioDTO usuario = administracionEntServiceFacade.findUsuarioSimpleByIdentificador(idUsuario, lang);
        SesionDTO sesionDTO = systemServiceBean.findSesionById(usuario.getCodigo());
        sesionDTO.setFechaUltimaSesion(new Date());
        TypePerfiles perfilAntiguo = this.perfil;

        //Actualizamos la seleccion de idioma está seteado a nivel de adm contenido seleccion idioma y es Adm. contenido
        if ((perfil == TypePerfiles.GESTOR || perfil == TypePerfiles.ADMINISTRADOR_CONTENIDOS || perfil == TypePerfiles.INFORMADOR) && entidad != null && entidad.getAdmContenidoSeleccionIdioma() != null) {
            lang = entidad.getAdmContenidoSeleccionIdioma();
            sesionDTO.setIdioma(lang); //Forzamos el idioma de sesion
            current = Locale.forLanguageTag(lang); //Forzamos el idioma/locale de la aplicación
        }

        if (!perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            Boolean permiso = checkPermisosPerfil(perfil);
            if (permiso) {
                actualizarUnidadAdministrativa(usuario, perfil, sesionDTO);
                if (perfil.equals(TypePerfiles.GESTOR) || perfil.equals(TypePerfiles.INFORMADOR)) {
                    checkUaGestor(unidadActiva);
                }
                this.perfil = perfil;
                if (perfilAntiguo.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
                    actualizarPerfiles();
                }
                actualizarEntidades();
                reload();
                reloadPerfil();
            } else {
                throw new PerfilException();
            }
        } else {
            entidad = null;
            unidadActiva = null;
            setPerfil(perfil);
            actualizarPerfiles();
        }
        sesionDTO.setPerfil(this.perfil.toString());
        sesionDTO.setIdEntidad(this.entidad == null ? null : this.entidad.getCodigo());
        sesionDTO.setIdUa(this.unidadActiva == null ? null : this.unidadActiva.getCodigo());
        sesionDTO.setIdioma(lang);

        systemServiceBean.updateSesion(sesionDTO);
    }

    /**
     * Comprueba si hay algún perfil del usuario que se pueda setear por defecto.
     */
    private TypePerfiles checkPerfilPosible() {
        Boolean perfilPosible = Boolean.FALSE;
        for (TypePerfiles perfilActivo : perfiles) {
            if (this.perfil == null || this.perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
                perfilPosible = checkPermisosPerfil(perfilActivo);
                if (perfilPosible) {
                    return perfilActivo;
                }
            }
        }
        if (!perfilPosible) {
            throw new NoAutorizadoException();
        }

        return null;
    }

    /**
     * Dado un perfil, comprueba si este es adecuado en alguna entidad de las que tiene el
     * y setea la variable entidad y el perfil del SessionBean en caso de que haya conincidencia.
     *
     * @param perfilActivo
     * @return
     */
    private Boolean checkPermisosPerfil(TypePerfiles perfilActivo) {
        Boolean perfilCorrecto = Boolean.FALSE;
        switch (perfilActivo) {
            case ADMINISTRADOR_ENTIDAD: {
                for (String rol : roles) {
                    if (!perfilCorrecto) {
                        if (entidad == null) {
                            for (EntidadGridDTO entidadPosible : usuario.getEntidades()) {
                                if (rol.equals(entidadPosible.getRolAdmin())) {
                                    this.entidad = administracionSupServiceFacade.findEntidadById(entidadPosible.getCodigo());
                                    perfilCorrecto = Boolean.TRUE;
                                    break;
                                }
                            }
                        } else {
                            if (this.entidad.getRolAdmin().equals(rol)) {
                                perfilCorrecto = Boolean.TRUE;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
            }
            case ADMINISTRADOR_CONTENIDOS: {
                for (String rol : roles) {
                    if (!perfilCorrecto) {
                        if (entidad == null) {
                            for (EntidadGridDTO entidadPosible : usuario.getEntidades()) {
                                if (rol.equals(entidadPosible.getRolAdminContenido())) {
                                    this.entidad = administracionSupServiceFacade.findEntidadById(entidadPosible.getCodigo());
                                    perfilCorrecto = Boolean.TRUE;
                                    break;
                                }
                            }
                        } else {
                            if (this.entidad.getRolAdminContenido().equals(rol)) {
                                perfilCorrecto = Boolean.TRUE;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
            }

            case GESTOR: {
                for (String rol : roles) {
                    if (!perfilCorrecto) {
                        if (entidad == null) {
                            for (EntidadGridDTO entidadPosible : usuario.getEntidades()) {
                                if (rol.equals(entidadPosible.getRolGestor())) {
                                    this.entidad = administracionSupServiceFacade.findEntidadById(entidadPosible.getCodigo());
                                    perfilCorrecto = Boolean.TRUE;
                                    break;
                                }
                            }
                        } else {
                            if (this.entidad.getRolGestor().equals(rol)) {
                                perfilCorrecto = Boolean.TRUE;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
            }

            case INFORMADOR: {
                for (String rol : roles) {
                    if (!perfilCorrecto) {
                        if (entidad == null) {
                            for (EntidadGridDTO entidadPosible : usuario.getEntidades()) {
                                if (rol.equals(entidadPosible.getRolInformador())) {
                                    this.entidad = administracionSupServiceFacade.findEntidadById(entidadPosible.getCodigo());
                                    perfilCorrecto = Boolean.TRUE;
                                    break;
                                }
                            }
                        } else {
                            if (this.entidad.getRolInformador().equals(rol)) {
                                perfilCorrecto = Boolean.TRUE;
                            }
                        }
                    } else {
                        break;
                    }
                }
                break;
            }
        }
        return perfilCorrecto;
    }

    /**
     * Método que comprueba si para una entidad determinada, el perfil que se pasa por parámetro
     * se puede utilizar en la entidad.
     *
     * @param entidad
     * @param perfil
     * @return
     */
    private Boolean checkPermisosPerfilEntidad(EntidadGridDTO entidad, TypePerfiles perfil) {
        Boolean permiso = Boolean.FALSE;
        switch (perfil) {
            case SUPER_ADMINISTRADOR: {
                break;
            }
            case ADMINISTRADOR_ENTIDAD: {
                if (roles.contains(entidad.getRolAdmin())) {
                    permiso = Boolean.TRUE;
                }
                break;
            }
            case ADMINISTRADOR_CONTENIDOS: {
                if (roles.contains(entidad.getRolAdminContenido())) {
                    permiso = Boolean.TRUE;
                }
                break;
            }
            case GESTOR: {
                if (roles.contains(entidad.getRolGestor())) {
                    permiso = Boolean.TRUE;
                }
                break;
            }
            case INFORMADOR: {
                if (roles.contains(entidad.getRolInformador())) {
                    permiso = Boolean.TRUE;
                }
                break;
            }
        }
        return permiso;
    }

    private Boolean tieneRolEntidadRelacionada(EntidadGridDTO entidad) {
        return roles.contains(entidad.getRolAdminContenido()) || roles.contains(entidad.getRolAdmin()) || roles.contains(entidad.getRolGestor()) || roles.contains(entidad.getRolInformador());
    }

    public void actualizarPerfiles() {
        List<TypePerfiles> perfilesOld = seguridad.getPerfiles();
        perfiles.clear();
        if (this.perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            perfiles = seguridad.getPerfiles();
        } else {
            if (perfilesOld.contains(TypePerfiles.SUPER_ADMINISTRADOR)) {
                perfiles.add(TypePerfiles.SUPER_ADMINISTRADOR);
            }
            for (TypePerfiles perfilNuevo : perfilesOld) {
                for (String rol : roles) {
                    switch (perfilNuevo) {
                        case ADMINISTRADOR_ENTIDAD: {
                            if (rol.equals(entidad.getRolAdmin())) {
                                perfiles.add(perfilNuevo);
                            }
                            break;
                        }
                        case ADMINISTRADOR_CONTENIDOS: {
                            if (rol.equals(entidad.getRolAdminContenido())) {
                                perfiles.add(perfilNuevo);
                            }
                            break;
                        }
                        case GESTOR: {
                            if (rol.equals(entidad.getRolGestor())) {
                                perfiles.add(perfilNuevo);
                            }
                            break;
                        }
                        case INFORMADOR: {
                            if (rol.equals(entidad.getRolInformador())) {
                                perfiles.add(perfilNuevo);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Método para actualizar la UA activa de la sesión
     *
     * @param usuario
     */
    public void actualizarUnidadAdministrativa(UsuarioDTO usuario, TypePerfiles perfil, SesionDTO sesionDTO) {
        if (perfil.equals(TypePerfiles.GESTOR) || perfil.equals(TypePerfiles.INFORMADOR)) {
            if (usuario.getUnidadesAdministrativas() != null && !usuario.getUnidadesAdministrativas().isEmpty()) {
                UnidadAdministrativaGridDTO unidadSesion = usuario.getUnidadesAdministrativas().stream().filter(ua -> ua.getCodigo().compareTo(sesionDTO.getIdUa()) == 0).findFirst().orElse(null);

                // this.unidadActiva = unidadSesion == null ? uaService.findById(usuario.getUnidadesAdministrativas().get(0).getCodigo()) : uaService.findById(unidadSesion.getCodigo());
                this.unidadActiva = unidadSesion == null ? uaService.findUASimpleByID(usuario.getUnidadesAdministrativas().get(0).getCodigo(), this.lang, null) : uaService.findUASimpleByID(unidadSesion.getCodigo(), this.lang, null);
            } else {
                unidadActiva = uaService.getUnidadesAdministrativaByEntidadId(this.entidad.getCodigo(), lang).get(0);
            }
        } else {
            if (sesionDTO.getIdUa() == null) {
                this.unidadActiva = uaService.findRootEntidad(entidad.getCodigo());
            } else {
                this.unidadActiva = uaService.findUASimpleByID(sesionDTO.getIdUa(), this.lang, null); // uaService.findById(sesionDTO.getIdUa());
            }
        }

    }

    public void actualizarUaSesion() {
        SesionDTO sesionDTO = systemServiceBean.findSesionById(usuario.getCodigo());
        sesionDTO.setIdUa(unidadActiva.getCodigo());
        systemServiceBean.updateSesion(sesionDTO);
    }

    public List<UnidadAdministrativaGridDTO> obtenerUasEntidad() {
        return usuario.getUnidadesAdministrativas().stream().filter(ua -> ua.getIdEntidad().compareTo(this.entidad.getCodigo()) == 0).collect(Collectors.toList());
    }

    /**
     * Método para actualizar la entidad activa en la sesión
     *
     * @param ent
     */
    public void actualizarEntidad(EntidadGridDTO ent) {
        SesionDTO sesionDTO = systemServiceBean.findSesionById(usuario.getCodigo());
        if (ent != null && entidades.contains(ent)) {
            entidad = administracionSupServiceFacade.findEntidadById(ent.getCodigo());
            actualizarUnidadAdministrativa(usuario, perfil, sesionDTO);
            sesionDTO.setIdEntidad(entidad.getCodigo());
            sesionDTO.setIdUa(unidadActiva.getCodigo());
            sesionDTO.setFechaUltimaSesion(new Date());
            actualizarPerfiles();
            if (!checkPermisosPerfilEntidad(ent, this.perfil)) {
                Boolean perfilAdecuado = Boolean.FALSE;
                for (TypePerfiles perfil : perfiles) {
                    if (checkPermisosPerfilEntidad(ent, perfil)) {
                        this.perfil = perfil;
                        sesionDTO.setPerfil(perfil.toString());
                        perfilAdecuado = Boolean.TRUE;
                        reload();
                        reloadPerfil();
                    }
                }
                if (!perfilAdecuado) {
                    //Redirigimos a página de error (está asociado a una entidad pero no tiene ningún perfil con los roles adecuados.
                }
            }

            systemServiceBean.updateSesion(sesionDTO);
        }
        reload();
        reloadPerfil();
    }

    /**
     * Actualiza el listado de entidades del usuario
     */
    public void actualizarEntidades() {
        entidades.clear();
        for (EntidadGridDTO entidad : usuario.getEntidades()) {
            if (entidad.getActiva() && tieneRolEntidadRelacionada(entidad)) {
                entidades.add(entidad);
            }
        }
    }

    /**
     * Método utilizado para comprobar si el perfil activo es del tipo que se pasa.
     *
     * @param typePerfil
     * @return
     */
    public boolean isPerfil(TypePerfiles typePerfil) {
        return perfil != null && perfil == typePerfil;
    }


    public boolean isSuperAdministrador() {
        return this.getPerfil() == TypePerfiles.SUPER_ADMINISTRADOR;
    }

    /**************************************************************************************************************************************************
     * FUNCIONES RELACIONADAS CON EL BREADCRUMB
     ************************************************************************************************************************************************/

    /**
     * Modifica la UA activa de un gestor
     */
    public void cambiarUaActiva(Long idUa) {
        if (idUa != null) {
            UnidadAdministrativaDTO ua = uaService.findUASimpleByID(idUa, this.lang, null);//findById(idUa);
            cambiarUnidadAdministrativa(ua);
            SesionDTO sesionDTO = systemServiceBean.findSesionById(usuario.getCodigo());
            sesionDTO.setFechaUltimaSesion(new Date());
            sesionDTO.setIdUa(ua.getCodigo());
            systemServiceBean.updateSesion(sesionDTO);
            unidadActivaHijaAux = null;
            setRenderPanelHijas(false);
        }
    }

    public boolean tieneHijosUaActiva() {
        return uaService.getCountHijos(unidadActiva.getCodigo()) >= 1;
    }

    private List<UnidadAdministrativaGridDTO> lasUnidadesHijasActivas = null;
    private Long codigoUnidadActiva = null;

    public List<UnidadAdministrativaGridDTO> getUnidadesHijasActiva() {
        if ((lasUnidadesHijasActivas == null || lasUnidadesHijasActivas.isEmpty()) || !codigoUnidadActiva.equals(unidadActiva.getCodigo())) {
            lasUnidadesHijasActivas = uaService.getHijosGrid(unidadActiva.getCodigo(), lang);
            codigoUnidadActiva = unidadActiva.getCodigo();
        }
        return lasUnidadesHijasActivas; //uaService.getHijosGrid(unidadActiva.getCodigo(), lang);
    }

    private List<UnidadAdministrativaGridDTO> lasUnidadesHijasUA = null;
    private Long codigoUA = null;

    public List<UnidadAdministrativaGridDTO> getUnidadesHijasUA(Long idUa) {
        if ((lasUnidadesHijasUA == null || lasUnidadesHijasUA.isEmpty()) || !codigoUA.equals(idUa)) {
            lasUnidadesHijasUA = uaService.getHijosGrid(idUa, lang);
            codigoUA = idUa;
        }
        return lasUnidadesHijasUA; //uaService.getHijosGrid(idUa, lang);
    }

    public void renderPanelHijas() {
        setRenderPanelHijas(true);
    }

    public void checkUaGestor(UnidadAdministrativaDTO ua) {
        UsuarioDTO usuario = administracionEntServiceFacade.findUsuarioSimpleByIdentificador(seguridad.getIdentificadorUsuario(), lang);

        List<UnidadAdministrativaGridDTO> unidadesUsuario = usuario.getUnidadesAdministrativas().stream().filter(u -> u.getIdEntidad().compareTo(this.entidad.getCodigo()) == 0).collect(Collectors.toList());
        if (!unidadesUsuario.isEmpty()) {
            for (UnidadAdministrativaGridDTO unidad : unidadesUsuario) {
                if (unidad.getCodigo().compareTo(ua.getCodigo()) == 0) {
                    unidadActivaAux = unidad;
                    break;
                } else {
                    checkUaRecursivo(ua, unidad);
                }
            }
        } else {
            throw new GestorSinUAExcepcion();
        }

        if (unidadActivaAux == null) {
            throw new GestorSinUAExcepcion();
        }

    }

    private void checkUaRecursivo(UnidadAdministrativaDTO ua, UnidadAdministrativaGridDTO unidadPadre) {
        if (ua.getPadre() != null) {
            if (ua.getPadre().getCodigo().compareTo(unidadPadre.getCodigo()) == 0) {
                unidadActivaAux = unidadPadre;
            } else {
                checkUaRecursivo(ua.getPadre(), unidadPadre);
            }
        }
    }

    /**
     * Función utilizada para el cambio de UA en el breadcrumb
     *
     * @param ua
     */
    public void cambiarUnidadAdministrativa(UnidadAdministrativaDTO ua) {
        if (unidadActiva != null && ua != null) {
            cambiarUnidadAdministrativaRecursivo(unidadActiva, ua);
        }
    }

    public void cambiarUaActivaAux(UnidadAdministrativaGridDTO ua) {
        setUnidadActivaAux(ua);
    }

    private void cambiarUnidadAdministrativaRecursivo(UnidadAdministrativaDTO unidadAdministrativa, UnidadAdministrativaDTO ua) {
        if (unidadAdministrativa == null || ua == null || unidadAdministrativa.getCodigo().compareTo(ua.getCodigo()) == 0) {
            this.unidadActiva = ua;
            return;
        } else {
            cambiarUnidadAdministrativaRecursivo(unidadAdministrativa.getPadre(), ua);
        }
    }

    /**
     * Obtiene las UAs activas hijas de la unidad activa
     *
     * @return
     */
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativasActivas() {
        List<UnidadAdministrativaDTO> unidades = new ArrayList<>();

        if (unidadActiva != null) {
            unidades = getUnidadesRecursivo(unidadActiva);
        }
        return unidades;
    }

    /**
     * Método utilizado para obtener las unidades administrativas asociadas al usuario
     *
     * @return
     */
    public List<UnidadAdministrativaDTO> obtenerUnidadesAdministrativasUsuario() {
        return uaService.getUnidadesAdministrativasByUsuario(usuario.getCodigo(), true);
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

    public boolean isUnidadesActivasGestor() {
        return unidadActiva != null && unidadActivaAux != null && unidadActiva.getCodigo().compareTo(unidadActivaAux.getCodigo()) != 0;
    }

    public List<UnidadAdministrativaDTO> getUnidadesActivasGestor() {
        List<UnidadAdministrativaDTO> unidades = new ArrayList<>();
        try {
            if (unidadActiva != null && unidadActivaAux.getCodigo().compareTo(unidadActiva.getCodigo()) != 0) {
                unidades = getUnidadesRecursivoGestor(unidadActiva);
            }

        } catch (NullPointerException e) {
            String rolsac2back = context.getExternalContext().getRequestContextPath();
            context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/error/uaRelacionadaException.xhtml')");
        }
        return unidades;

        /*List<UnidadAdministrativaDTO> unidades = new ArrayList<>();

        if (unidadActiva != null && unidadActivaAux.getCodigo().compareTo(unidadActiva.getCodigo()) != 0) {
            unidades = getUnidadesRecursivoGestor(unidadActiva);
        }
        return unidades;*/
    }

    private List<UnidadAdministrativaDTO> getUnidadesRecursivoGestor(UnidadAdministrativaDTO ua) {
        if (ua == null) {
            return new ArrayList<>();
        } else if (ua.getPadre() == null || (unidadActivaAux != null && unidadActivaAux.getCodigo().compareTo(ua.getPadre().getCodigo()) == 0)) {
            List<UnidadAdministrativaDTO> uas = new ArrayList<>();
            uas.add(ua);
            return uas;
        } else {
            List<UnidadAdministrativaDTO> uas = getUnidadesRecursivoGestor(ua.getPadre());
            uas.add(ua);
            return uas;
        }
    }

    /**************************************************************************************************************************************************
     * FUNCIONES RELACIONADAS CON LA VISUALIZACIÓN DE LA APLICACIÓN Y LA NAVEGACIÓN
     ************************************************************************************************************************************************/

    /**
     * Redirige a la URL por defecto para el rol activo.
     */
    public void redirectDefaultUrl() {
        UtilJSF.redirectJsfDefaultPagePerfil(perfil);
        switch (this.perfil) {
            case ADMINISTRADOR_CONTENIDOS:
                opcion = "viewProcedimientos.titulo";
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewConfiguracionEntidad.titulo";
                break;
            case GESTOR:
                opcion = "viewProcedimientos.titulo";
                break;
            case INFORMADOR:
                opcion = "viewProcedimientos.titulo";
                break;
            case SUPER_ADMINISTRADOR:
                opcion = "viewTipoEntidades.titulo";
                break;
        }
    }

    /**
     * Obtener la URL de la petición
     *
     * @return
     */
    public String getUrl() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url;
    }

    /**
     * Obtener la URI de la petición
     *
     * @return
     */
    public String getUri() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURI().toString();
        return url;
    }

    /**
     * Recargar la pantalla
     */
    public void reload() {
        context.getPartialViewContext().getEvalScripts().add("location.replace(location)");
    }

    /**
     * Recargar el perfil
     */
    public void reloadPerfil() {
        String rolsac2back = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        switch (this.perfil) {
            case ADMINISTRADOR_CONTENIDOS:
                opcion = "viewProcedimientos.titulo";
                context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/maestras/viewProcedimientos.xhtml')");
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewCuadroControl.titulo";
                context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/monitorizacion/viewCuadroControl.xhtml')");
                break;
            case GESTOR:
                opcion = "viewProcedimientos.titulo";
                context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/maestras/viewProcedimientos.xhtml')");
                break;
            case INFORMADOR:
                opcion = "viewProcedimientos.titulo";
                context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/maestras/viewProcedimientos.xhtml')");
                break;
            case SUPER_ADMINISTRADOR:
                opcion = "viewTipoEntidades.titulo";
                context.getPartialViewContext().getEvalScripts().add("location.replace('" + rolsac2back + "/superadministrador/viewEntidades.xhtml')");
                break;
        }

    }


    /**
     * Función utilizada para actualizar el logo de la entidad
     */
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
            tiposViewIds.add("/entidades/viewTema.xhtml");
            tiposViewIds.add("/maestras/viewPublicoObjetivoEntidad.xhtml");
            tiposViewIds.add("/entidades/viewTipoMediaFicha.xhtml");
            tiposViewIds.add("/entidades/viewLOPD.xhtml");
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return tiposViewIds.contains(viewId);
        } else {
            return false;
        }
    }

    public boolean isMonitorizacionActivo() {
        if (this.getPerfil() == TypePerfiles.ADMINISTRADOR_ENTIDAD) {
            List<String> tiposViewIds = new ArrayList<>();
            tiposViewIds.add("/monitorizacion/viewConfiguracionesAlertas.xhtml");
            tiposViewIds.add("/monitorizacion/viewCuadroControl.xhtml");
            tiposViewIds.add("/monitorizacion/viewEventosPlat.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesos.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesosLog.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesosSolr.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesosSIA.xhtml");
            tiposViewIds.add("/monitorizacion/viewProcesosMigracion.xhtml");
            tiposViewIds.add("/entidades/viewEntidadRaiz.xhtml");
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
            tiposViewIds.add("/entidades/viewUnidadAdministrativa.xhtml");
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return tiposViewIds.contains(viewId);
        } else {
            return false;
        }
    }

    public boolean checkLogo() {
        if (this.perfil == TypePerfiles.SUPER_ADMINISTRADOR) {
            return false;
        } else if (this.entidad == null || this.entidad.getLogo() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Función utilizada para conocer el ancho y alto de la pantalla del usuario
     */
    public void updateAspect() {
        String width = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAspect:windowWidth");
        String height = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAspect:windowHeight");
        if (width != null) {
            this.screenWidth = width;
        }
        if (height != null) {
            this.screenHeight = height;
        }
    }

    /**
     * Función utilizada para obtener los idiomas permitidos en una entidad (en formato concatenado mediante ;)
     *
     * @return
     */
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

    /**
     * Función utilizada para obtener los idiomas permitidos en una entidad (en listado)
     *
     * @return
     */
    public List<String> getIdiomasPermitidosList() {
        List<String> idiomas = new ArrayList<>();
        if (perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR)) {
            for (TypeIdiomaFijo idioma : TypeIdiomaFijo.values()) {
                idiomas.add(idioma.toString());
            }
            for (TypeIdiomaOpcional idioma : TypeIdiomaOpcional.values()) {
                idiomas.add(idioma.toString());
            }
            //return idiomas;
        } else {
            idiomas = new LinkedList<String>(Arrays.asList(this.entidad.getIdiomasPermitidos().split(";")));
        }

        if (entidad != null && entidad.getAdmContenidoIdiomaPrioritario() != null) {
            //Cambiar la posición del idioma prioritario
            String idiomaPrioritario = entidad.getAdmContenidoIdiomaPrioritario();
            idiomas.remove(idiomaPrioritario);
            idiomas.add(0, idiomaPrioritario);
        }

        return idiomas;
    }

    /**
     * Función utilizada para obtener los idiomas obligatorios en una entidad (en formato concatenado mediante ;)
     *
     * @return
     */
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

    /**
     * Función utilizada para obtener los idiomas obligatorios en una entidad (en listado)
     *
     * @return
     */
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

    /**
     * Función utilizada para obtener el logo de la entidad
     *
     * @return
     */
    public StreamedContent getLogoEntidad() {
        try {
            FicheroDTO logo = entidadservice.getLogoEntidad(this.entidad.getLogo().getCodigo());
            String mimeType = URLConnection.guessContentTypeFromName(logo.getFilename());
            InputStream fis = new ByteArrayInputStream(logo.getContenido());
            StreamedContent file = DefaultStreamedContent.builder().name(logo.getFilename()).contentType(mimeType).stream(() -> fis).build();
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

    public boolean esCatalan() {
        return lang != null && "ca".equals(lang);
    }

    public boolean esCastellano() {
        return lang != null && "es".equals(lang);
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

    public UnidadAdministrativaGridDTO getUnidadActivaAux() {
        return unidadActivaAux;
    }

    public void setUnidadActivaAux(UnidadAdministrativaGridDTO unidadActivaAux) {
        this.unidadActivaAux = unidadActivaAux;
    }

    public UnidadAdministrativaGridDTO getUnidadActivaHijaAux() {
        return unidadActivaHijaAux;
    }

    public void setUnidadActivaHijaAux(UnidadAdministrativaGridDTO unidadActivaHijaAux) {
        this.unidadActivaHijaAux = unidadActivaHijaAux;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public List<EntidadGridDTO> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<EntidadGridDTO> entidades) {
        this.entidades = entidades;
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

    public Boolean getRenderPanelHijas() {
        return renderPanelHijas;
    }

    public void setRenderPanelHijas(Boolean renderPanelHijas) {
        this.renderPanelHijas = renderPanelHijas;
    }

    public List<TypePerfiles> getPerfilesBack() {
        return perfilesBack;
    }

    public void setPerfilesBack(List<TypePerfiles> perfilesBack) {
        this.perfilesBack = perfilesBack;
    }

    public List<AlertaDTO> getAlertasAvisos() {
        return alertasAvisos;
    }

    public void setAlertasAvisos(List<AlertaDTO> alertasAvisos) {
        this.alertasAvisos = alertasAvisos;
    }

    public List<AlertaDTO> getAlertas() {
        if (this.alertasAvisos == null) {
            return new ArrayList<>();
        }
        return this.alertasAvisos.stream().filter(a -> a.getTipo().equals(TypeAlertas.ALERTAS.toString())).collect(Collectors.toList());
    }

    public List<AlertaDTO> getAvisos() {
        if (this.alertasAvisos == null) {
            return new ArrayList<>();
        }
        return this.alertasAvisos.stream().filter(a -> a.getTipo().equals(TypeAlertas.AVISOS.toString())).collect(Collectors.toList());
    }

    public String getNotificacionAvisos() {
        List<AlertaDTO> avisos = getAvisos();
        if (avisos == null || avisos.isEmpty()) {
            return "";
        }
        return " (" + avisos.size() + ") ";
    }

    public void marcarAlertaLeida(Long codigo) {
        alertaService.marcarAlertaLeida(codigo, seguridad.getIdentificadorUsuario());

        this.alertasAvisos = alertaService.getAlertas(seguridad.getIdentificadorUsuario(), perfiles, lang);
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public UsuarioDTO obtenerUsuarioAutenticado() {
        return usuario;
    }
}
