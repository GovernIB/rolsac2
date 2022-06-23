package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Bean per mantener todos los datos importantes de session. En resumen:
 * <ul>
 *     <li>Usuario</li>
 *     <li>Permisos</li>
 *     <li>UA del usuario</li>
 *     <li>Idioma</li>
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
    private FacesContext context;

    private UnidadAdministrativaDTO unidad;

    private UnidadAdministrativaDTO unidadActiva;

    private List<UnidadAdministrativaDTO> unidades;

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
        cargarDatosMockup();
    }

    public boolean isPerfil(TypePerfiles typePerfil) {
        return perfil != null && perfil == typePerfil;
    }

    private void cargarDatosMockup() {
        //Obtenemos la UA del usuario (mockup)
        UnidadAdministrativaDTO padre = new UnidadAdministrativaDTO();
        padre.setId(1l);
        Literal literalPadre = new Literal();
        List<Traduccion> traduccionesPadre = new ArrayList<>();
        traduccionesPadre.add(new Traduccion("es", "GOIB"));
        traduccionesPadre.add(new Traduccion("ca", "GOIB"));
        literalPadre.setTraducciones(traduccionesPadre);
        padre.setNombre(literalPadre);

        UnidadAdministrativaDTO hijo = new UnidadAdministrativaDTO();
        hijo.setId(2l);
        Literal literalHijo = new Literal();
        List<Traduccion> traduccionesHijo = new ArrayList<>();
        traduccionesHijo.add(new Traduccion("es", "Conselleria de educación"));
        traduccionesHijo.add(new Traduccion("ca", "Conselleria d'educació"));
        literalHijo.setTraducciones(traduccionesHijo);
        hijo.setNombre(literalHijo);
        hijo.setPadre(padre);

        UnidadAdministrativaDTO nieto = new UnidadAdministrativaDTO();
        nieto.setId(3l);
        Literal literalNieto = new Literal();
        List<Traduccion> traduccionesNieto = new ArrayList<>();
        traduccionesNieto.add(new Traduccion("es", "Secretaría de educación"));
        traduccionesNieto.add(new Traduccion("ca", "Secretaria d'educació"));
        literalNieto.setTraducciones(traduccionesNieto);
        nieto.setNombre(literalNieto);
        nieto.setPadre(hijo);

        unidad = padre;
        unidadActiva = nieto;
        unidades = new ArrayList<>();
        unidades.add(unidad);
        //Ayto Baleares
        UnidadAdministrativaDTO uaAyto = new UnidadAdministrativaDTO();
        uaAyto.setId(4l);
        Literal literaluaAyto = new Literal();
        List<Traduccion> traduccionesuaAyto = new ArrayList<>();
        traduccionesuaAyto.add(new Traduccion("es", "Ayto. Mallorca"));
        traduccionesuaAyto.add(new Traduccion("ca", "Ajunt. Mallorca"));
        literaluaAyto.setTraducciones(traduccionesuaAyto);
        uaAyto.setNombre(literaluaAyto);
        uaAyto.setPadre(null);
        unidades.add(uaAyto);

        perfil = TypePerfiles.ADMINISTRADOR_CONTENIDOS;
        perfiles = new ArrayList<TypePerfiles>();
        perfiles.add(TypePerfiles.SUPER_ADMINISTRADOR);
        perfiles.add(TypePerfiles.ADMINISTRADOR_ENTIDAD);
        perfiles.add(TypePerfiles.ADMINISTRADOR_CONTENIDOS);
        perfiles.add(TypePerfiles.GESTOR);
        perfiles.add(TypePerfiles.INFORMADOR);
    }

    public void cambiarUnidadAdministrativa(UnidadAdministrativaDTO ua) {
        if (unidad != null && ua != null) {
            cambiarUnidadAdministrativaRecursivo(unidadActiva, ua);
        }
    }

    public void actualizarUnidadAdministrativa(UnidadAdministrativaDTO ua) {
        if (ua != null && unidades.contains(ua)) {
            unidadActiva = ua;
            unidad = ua;
        }
    }

    private void cambiarUnidadAdministrativaRecursivo(UnidadAdministrativaDTO unidadAdministrativa, UnidadAdministrativaDTO ua) {
        if (unidadAdministrativa == null || ua == null || unidadAdministrativa.getId().compareTo(ua.getId()) == 0) {
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
        context.getPartialViewContext().getEvalScripts()
                .add("location.replace(location)");
    }

    // Mètodes
    public void reloadPerfil() {
        String rolsac2back = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        switch (this.perfil) {
            case ADMINISTRADOR_CONTENIDOS:
                opcion = "viewFichas.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/maestras/viewFichas.xhtml')");
                break;
            case ADMINISTRADOR_ENTIDAD:
                opcion = "viewConfiguracionEntidad.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/entidades/viewConfiguracionEntidad.xhtml')");
                break;
            case GESTOR:
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace(location)");
                break;
            case INFORMADOR:
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace(location)");
                break;
            case SUPER_ADMINISTRADOR:
                opcion = "viewTipoEntidades.titulo";
                context.getPartialViewContext().getEvalScripts()
                        .add("location.replace('" + rolsac2back + "/superadministrador/viewEntidades.xhtml')");
                break;
        }

    }

    public Locale getCurrent() {
        return current;
    }

    public void setCurrent(Locale current) {
        this.current = current;
        this.lang = current.getDisplayLanguage().contains("ca") ? "ca" : "es";
    }

    public UnidadAdministrativaDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadAdministrativaDTO unidad) {
        this.unidad = unidad;
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

    public List<UnidadAdministrativaDTO> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadAdministrativaDTO> unidades) {
        this.unidades = unidades;
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
}
