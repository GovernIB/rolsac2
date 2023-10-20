package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.comun.UtilsArbolTemas;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class DialogUnidadAdministrativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogUnidadAdministrativa.class);

    private String id = "";

    private boolean activoModoEvolucion = false;
    private String modoEvolucion;

    private String modoEvolucionUAs;

    private UnidadAdministrativaDTO data;

    private UnidadAdministrativaDTO dataOriginal;

    private UnidadAdministrativaDTO dataAntigua;

    private List<EntidadDTO> entidadesActivas;

    private List<UnidadAdministrativaDTO> padreSeleccionado;

    private List<TipoUnidadAdministrativaDTO> tipos;

    private List<TipoSexoDTO> tiposSexo;

    private String identificadorOld;

    private List<UsuarioDTO> usuarios;

    private UsuarioGridDTO usuarioSeleccionado;

    private List<TemaGridDTO> temasPadre;

    private TreeNode temaSeleccionado;

    private List<TreeNode> roots;

    private List<TreeNode> temasTabla;

    private List<TemaGridDTO> temasPadreAnyadidos;

    private Map<String, List<TemaGridDTO>> temasHijosRelacionados;

    private NormativaDTO normativaBaja;
    private boolean mostrarProcsNormativas = false;

    /**
     * Solo consulta, listado de normativas y procedimientos
     **/
    List<NormativaDTO> normativas;
    NormativaDTO normativaSeleccionado;
    List<ProcedimientoBaseDTO> procedimientos;
    ProcedimientoBaseDTO procedimientoSeleccionado;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private TipoUnidadAdministrativaServiceFacade tipoUnidadAdministrativaServiceFacade;

    @EJB
    private TemaServiceFacade temaServiceFacade;

    @EJB
    private SystemServiceFacade systemServiceFacade;


    public void load() {
        LOG.debug("init1");

        this.setearIdioma();
        data = new UnidadAdministrativaDTO();

        tiposSexo = unidadAdministrativaServiceFacade.findTipoSexo();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        tipos = tipoUnidadAdministrativaServiceFacade.findTipo();
        temasPadre = temaServiceFacade.getGridRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
        temasHijosRelacionados = new HashMap<>();
        temasPadreAnyadidos = new ArrayList<>();


        String valorMostrarProcsNormativas = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.MOSTRAR_EN_UA_PROCS_NORMATIVAS);
        if (valorMostrarProcsNormativas != null && "S".equals(valorMostrarProcsNormativas)) {
            mostrarProcsNormativas = true;
        }

        if (this.isModoAlta()) {
            data = new UnidadAdministrativaDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setPadre(sessionBean.getUnidadActiva());
            data.setNombre(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setUrl(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setPresentacion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setResponsable(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setUsuariosUnidadAdministrativa(new ArrayList<>());
            data.setTemas(new ArrayList<>());
            data.setEstado("V"); //Vigente
            dataOriginal = (UnidadAdministrativaDTO) data.clone();

            if (this.modoEvolucion != null && "S".equals(this.modoEvolucion) && this.modoEvolucionUAs != null && !this.modoEvolucionUAs.isEmpty()) {
                activoModoEvolucion = true;
                if (UtilJSF.getValorMochilaByKey("ua") == null) {
                    List<Long> uas = getUasEvolucion(this.modoEvolucionUAs);
                    List<UsuarioGridDTO> usuariosUAs = unidadAdministrativaServiceFacade.getUsuariosByUas(uas);
                    List<TemaGridDTO> temas = unidadAdministrativaServiceFacade.getTemasByUas(uas);
                    if (mostrarProcsNormativas) {
                        procedimientos = unidadAdministrativaServiceFacade.getProcedimientosByUas(uas, UtilJSF.getSessionBean().getLang());
                        normativas = unidadAdministrativaServiceFacade.getNormativaByUas(uas, UtilJSF.getSessionBean().getLang());
                    }
                    data.setUsuariosUnidadAdministrativa(usuariosUAs);
                    data.setTemas(temas);
                } else {
                    data = (UnidadAdministrativaDTO) UtilJSF.getValorMochilaByKey("ua");
                    if (mostrarProcsNormativas) {
                        procedimientos = unidadAdministrativaServiceFacade.getProcedimientosByUa(data.getCodigo(), UtilJSF.getSessionBean().getLang());
                        normativas = unidadAdministrativaServiceFacade.getNormativaByUa(data.getCodigo(), UtilJSF.getSessionBean().getLang());
                    }
                    UtilJSF.vaciarMochila();
                }
            }
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
            if (mostrarProcsNormativas) {
                normativas = unidadAdministrativaServiceFacade.getNormativaByUa(Long.valueOf(id), UtilJSF.getSessionBean().getLang());
                procedimientos = unidadAdministrativaServiceFacade.getProcedimientosByUa(Long.valueOf(id), UtilJSF.getSessionBean().getLang());
            }
            dataAntigua = (UnidadAdministrativaDTO) data.clone();
            dataOriginal = (UnidadAdministrativaDTO) data.clone();
            this.identificadorOld = data.getIdentificador();
            /*dataOriginal.setHijos(new ArrayList<>(data.getHijos()));*/
            dataOriginal.setTemas(new ArrayList<>(data.getTemas()));
            dataOriginal.setUsuariosUnidadAdministrativa(new ArrayList<>(data.getUsuariosUnidadAdministrativa()));

        }

        temasTabla = new ArrayList<>();
        for (TemaGridDTO tema : temasPadre) {
            temasTabla.add(new DefaultTreeNode(new TemaGridDTO(), null));
        }
        this.construirArbol();

        if (this.data.isBorrado() && data.getNormativas() != null && !data.getNormativas().isEmpty()) {
            normativaBaja = data.getNormativas().get(0);// unidadAdministrativaServiceFacade.getNormativaBaja(data.getCodigo());
        }

        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        data.setUsuarioAuditoria(usuario);
        dataOriginal = (UnidadAdministrativaDTO) data.clone();
    }

    private List<Long> getUasEvolucion(String modoEvolucionUAs) {
        List<Long> idUas = new ArrayList<>();
        if (modoEvolucionUAs != null) {
            for (String ua : modoEvolucionUAs.split(",")) {
                if (ua != null && !ua.trim().isEmpty()) {
                    idUas.add(Long.valueOf(ua.trim()));
                }
            }
        }
        return idUas;
    }

    /**
     * Consultar normativa
     */
    public void consultarNormativa(Integer index) {
        if (data.getNormativas() == null || data.getNormativas().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", normativaSeleccionado.getCodigo().toString());
            String ruta = "/maestras/dialogNormativa";
            UtilJSF.openDialog(ruta, TypeModoAcceso.CONSULTA, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 200), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
        }
    }

    /**
     * Consultar normativa
     */
    public void consultarProcedimiento(Integer index) {
        if (data.getNormativas() == null || data.getNormativas().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", procedimientoSeleccionado.getCodigoWF().toString());
            String ruta = "/maestras/dialogProcedimiento";
            UtilJSF.openDialog(ruta, TypeModoAcceso.CONSULTA, params, true, 1010, 733);
        }
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        UnidadAdministrativaDTO datoDTO = (UnidadAdministrativaDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setNombre(datoDTO.getNombre());
            data.setPresentacion(datoDTO.getPresentacion());
            data.setUrl(datoDTO.getUrl());
            data.setResponsable(datoDTO.getResponsable());
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        //Si no es modo evolución, se guarda (En modo evolución, sólo se guarda al final de realizar todos los pasos)
        if (!activoModoEvolucion) {
            if (this.data.getCodigo() == null) {
                unidadAdministrativaServiceFacade.create(this.data, sessionBean.getPerfil());
            } else {
                unidadAdministrativaServiceFacade.update(this.data, this.dataAntigua, sessionBean.getPerfil());
            }
        }

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getCodigoDIR3(), dataOriginal.getCodigoDIR3()) != 0 || !dataOriginal.getTemas().equals(data.getTemas()) || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0 || UtilComparador.compareTo(data.getAbreviatura(), dataOriginal.getAbreviatura()) != 0 || UtilComparador.compareTo(data.getTelefono(), dataOriginal.getTelefono()) != 0 || UtilComparador.compareTo(data.getFax(), dataOriginal.getFax()) != 0 || UtilComparador.compareTo(data.getEmail(), dataOriginal.getEmail()) != 0 || UtilComparador.compareTo(data.getDominio(), dataOriginal.getDominio()) != 0 || UtilComparador.compareTo(data.getResponsableEmail(), dataOriginal.getResponsableEmail()) != 0 || UtilComparador.compareTo(data.getResponsableNombre(), dataOriginal.getResponsableNombre()) != 0 || UtilComparador.compareTo(data.getPresentacion(), dataOriginal.getPresentacion()) != 0 || UtilComparador.compareTo(data.getUrl(), dataOriginal.getUrl()) != 0 || UtilComparador.compareTo(data.getResponsable(), dataOriginal.getResponsable()) != 0 || UtilComparador.compareTo(data.getNombre(), dataOriginal.getNombre()) != 0 || (data.getTipo() != null && dataOriginal.getTipo() != null && UtilComparador.compareTo(data.getTipo().getCodigo(), dataOriginal.getTipo().getCodigo()) != 0) || ((data.getTipo() == null || dataOriginal.getTipo() == null) && (data.getTipo() != null || dataOriginal.getTipo() != null)) || (data.getResponsableSexo() != null && dataOriginal.getResponsableSexo() != null && UtilComparador.compareTo(data.getResponsableSexo().getCodigo(), dataOriginal.getResponsableSexo().getCodigo()) != 0) || ((data.getResponsableSexo() == null || dataOriginal.getResponsableSexo() == null) && (data.getResponsableSexo() != null || dataOriginal.getResponsableSexo() != null)) || UtilComparador.compareTo(data.getUsuarioAuditoria(), dataOriginal.getUsuarioAuditoria()) != 0 || !data.getUsuariosUnidadAdministrativa().equals(dataOriginal.getUsuariosUnidadAdministrativa()) || UtilComparador.compareTo(data.getOrden(), dataOriginal.getOrden()) != 0;
    }

    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo()) && unidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        /*if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && unidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }*/
        if (Objects.nonNull(this.data.getEmail()) && !ValidacionTipoUtils.esEmailValido(this.data.getEmail())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.email.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getTelefono()) && !ValidacionTipoUtils.esTelefonoValido(this.data.getTelefono())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.telefono.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getFax()) && !ValidacionTipoUtils.esNumerico(this.data.getFax())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.fax.novalido"), true);
            return false;
        }
        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getNombre(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dict.nombre", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }


        return true;
    }

    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de Usuarios
     *********************************************************************************************************************************/

    /**
     * Abrir dialogo de Selección de Usuarios
     */
    public void abrirDialogUsuarios(TypeModoAcceso modoAcceso) {

        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", usuarioSeleccionado.getCodigo().toString());
            UtilJSF.openDialog("/entidades/dialogUsuario", modoAcceso, params, true, 700, 365);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("usuariosUnidadAdministrativa", data.getUsuariosUnidadAdministrativa());
            UtilJSF.anyadirMochila("unidadAdministrativa", data);
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("/entidades/dialogSeleccionUsuariosUnidadAdministrativa", modoAcceso, params, true, 1040, 460);
        }
    }

    /**
     * Método para dar de alta usuarios en una UA
     */
    public void anyadirUsuarios() {
        abrirDialogUsuarios(TypeModoAcceso.ALTA);
    }

    /**
     * Método para consultar el detalle de un usuario en una UA
     */
    public void consultarUsuario() {
        if (usuarioSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogUsuarios(TypeModoAcceso.CONSULTA);
        }
    }

    /**
     * Método para borrar un usuario en una UA
     */
    public void borrarUsuario() {
        if (usuarioSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getUsuariosUnidadAdministrativa().remove(usuarioSeleccionado);
            usuarioSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    /********************************************************************************************************************************
     * Funciones relativas a las asignaciones temáticas
     *********************************************************************************************************************************/


    public void abrirSeleccionTematica(TemaGridDTO temaPadre, TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("temaPadre", temaPadre);
        UtilJSF.anyadirMochila("temasRelacionados", new ArrayList<>(data.getTemas()));
        UtilJSF.openDialog("/comun/dialogSeleccionarTemaMultiple", modoAcceso, params, true, 590, 460);

    }

    public void altaTematicas(TemaGridDTO temaPadre) {
        this.abrirSeleccionTematica(temaPadre, TypeModoAcceso.ALTA);
    }

    /**
     * Método para consultar el detalle de un tema en una UA
     */
    public void consultarTema(Integer index) {
        if (temasTabla == null || temasTabla.get(index) == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            TemaGridDTO tema = (TemaGridDTO) temasTabla.get(index).getData();
            params.put("ID", tema.getCodigo().toString());
            UtilJSF.openDialog("dialogTema", TypeModoAcceso.CONSULTA, params, true, 700, 330);
        }
    }

    /**
     * Método para borrar un tema en una UA
     */
    public void borrarTema() {
        if (temaSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getTemas().remove(temaSeleccionado);
            temaSeleccionado = null;
            construirArbol();
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void returnDialogTemas(final SelectEvent event) {
        DialogResult resultado = (DialogResult) event.getObject();
        if (!resultado.isCanceled()) {
            List<TemaGridDTO> temasSeleccionados = (List<TemaGridDTO>) resultado.getResult();
            TemaGridDTO temaPadre = (TemaGridDTO) UtilJSF.getValorMochilaByKey("temaPadre");
            UtilJSF.vaciarMochila();

            for (TemaGridDTO tema : temasSeleccionados) {
                if (!data.getTemas().contains(tema)) {
                    data.getTemas().add(tema);
                }
            }
            List<TemaGridDTO> temasBorrado = new ArrayList<>();
            for (TemaGridDTO tema : data.getTemas()) {
                if (Arrays.asList(tema.getMathPath().split(";")).contains(temaPadre.getCodigo().toString()) && !temasSeleccionados.contains(tema)) {
                    temasBorrado.add(tema);
                }
            }
            data.getTemas().removeAll(temasBorrado);
            temasPadreAnyadidos.clear();
            construirArbol();
        }

    }

    private void construirArbol() {
        roots = new ArrayList<>();
        UtilsArbolTemas.construirArbol(roots, temasPadre, temasPadreAnyadidos, data.getTemas(), temaServiceFacade);
    }

    /*private void construirArbol() {
        roots = new ArrayList<>();
        for (TemaGridDTO temaPadre : temasPadre) {
            TreeNode root = new DefaultTreeNode(temaPadre, null);
            this.construirArbolDesdeHoja(temaPadre, root);
            roots.add(root);
        }
    }*/

    /**
     * Cosntruye los diferentes árboles que se mostrarán a partir de los temas seleccionados de una UA.
     *
     * @param hoja
     * @param arbol
     */
    private void construirArbolDesdeHoja(TemaGridDTO hoja, TreeNode arbol) {
        DefaultTreeNode nodo = null;
        if (hoja.getMathPath() == null) {
            for (TemaGridDTO tema : data.getTemas()) {
                if (tema.getMathPath().equals(hoja.getCodigo().toString())) {
                    if (temasPadreAnyadidos.isEmpty()) {
                        nodo = new DefaultTreeNode(tema, arbol);
                        temasPadreAnyadidos.add(tema);
                        arbol.setExpanded(true);
                        this.construirArbolDesdeHoja(tema, nodo);
                    } else if (!temasPadreAnyadidos.contains(tema)) {
                        nodo = new DefaultTreeNode(tema, arbol);
                        temasPadreAnyadidos.add(tema);
                        arbol.setExpanded(true);
                        this.construirArbolDesdeHoja(tema, nodo);
                    }

                } else if (tema.getMathPath().contains(hoja.getCodigo().toString())) {
                    String[] niveles = tema.getMathPath().split(";");
                    String idPadre = niveles[1];
                    TemaGridDTO temaPadre = temaServiceFacade.findGridById(Long.valueOf(idPadre));
                    if (!temasPadreAnyadidos.contains(temaPadre) && !data.getTemas().contains(temaPadre)) {
                        temasPadreAnyadidos.add(temaPadre);
                        temaPadre.setRelacionado(true);
                        nodo = new DefaultTreeNode(temaPadre, arbol);
                        arbol.setExpanded(true);
                        this.construirArbolDesdeHoja(temaPadre, nodo);
                    }
                }
            }
        } else {
            Integer nivel = hoja.getMathPath().split(";").length + 1;
            for (TemaGridDTO tema : data.getTemas()) {
                Integer nivelHijo = tema.getMathPath().split(";").length;

                if (tema.getMathPath().contains(hoja.getCodigo().toString()) && nivelHijo == nivel) {
                    if (temasPadreAnyadidos.isEmpty()) {
                        nodo = new DefaultTreeNode(tema, arbol);
                        arbol.setExpanded(true);
                        this.construirArbolDesdeHoja(tema, nodo);
                    } else if (!temasPadreAnyadidos.contains(tema)) {
                        nodo = new DefaultTreeNode(tema, arbol);
                        arbol.setExpanded(true);
                        this.construirArbolDesdeHoja(tema, nodo);
                    }
                } else if (tema.getMathPath().contains(hoja.getCodigo().toString())) {
                    String[] niveles = tema.getMathPath().split(";");
                    String idPadre = niveles[nivel];
                    TemaGridDTO temaPadre = temaServiceFacade.findGridById(Long.valueOf(idPadre));
                    if (!temasPadreAnyadidos.contains(temaPadre) && !data.getTemas().contains(temaPadre)) {
                        temasPadreAnyadidos.add(temaPadre);
                        temaPadre.setRelacionado(true);
                        nodo = new DefaultTreeNode(temaPadre, arbol);
                        arbol.setExpanded(true);
                        this.construirArbolDesdeHoja(temaPadre, nodo);
                    }
                }

            }
        }
    }

    /********************************************************************************************************************************
     * Funciones relativas a las auditorias
     *********************************************************************************************************************************/
    public void verAuditorias() {

        final Map<String, String> params = new HashMap<>();
        params.put("ID", data.getCodigo().toString());
        params.put("TIPO", "UA");
        UtilJSF.openDialog("/comun/dialogAuditoria", TypeModoAcceso.CONSULTA, params, true, 800, 500);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UnidadAdministrativaDTO getData() {
        return data;
    }

    public void setData(UnidadAdministrativaDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }

    public List<UnidadAdministrativaDTO> getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(List<UnidadAdministrativaDTO> padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }

    public List<TipoUnidadAdministrativaDTO> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoUnidadAdministrativaDTO> tipoSeleccionado) {
        this.tipos = tipoSeleccionado;
    }

    public List<TipoSexoDTO> getTiposSexo() {
        return tiposSexo;
    }

    public void setTiposSexo(List<TipoSexoDTO> tiposSexo) {
        this.tiposSexo = tiposSexo;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioGridDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioGridDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public TreeNode getTemaSeleccionado() {
        return temaSeleccionado;
    }

    public void setTemaSeleccionado(TreeNode temaSeleccionado) {
        this.temaSeleccionado = temaSeleccionado;
    }

    public List<TemaGridDTO> getTemasPadre() {
        return temasPadre;
    }

    public void setTemasPadre(List<TemaGridDTO> temasPadre) {
        this.temasPadre = temasPadre;
    }

    public List<TreeNode> getRoots() {
        return roots;
    }

    public void setRoots(List<TreeNode> roots) {
        this.roots = roots;
    }

    public List<TreeNode> getTemasTabla() {
        return temasTabla;
    }

    public void setTemasTabla(List<TreeNode> temasTabla) {
        this.temasTabla = temasTabla;
    }

    public Map<String, List<TemaGridDTO>> getTemasHijosRelacionados() {
        return temasHijosRelacionados;
    }

    public void setTemasHijosRelacionados(Map<String, List<TemaGridDTO>> temasHijosRelacionados) {
        this.temasHijosRelacionados = temasHijosRelacionados;
    }

    public NormativaDTO getNormativaBaja() {
        return normativaBaja;
    }

    public void setNormativaBaja(NormativaDTO normativaBaja) {
        this.normativaBaja = normativaBaja;
    }

    public String getModoEvolucion() {
        return modoEvolucion;
    }

    public void setModoEvolucion(String modoEvolucion) {
        this.modoEvolucion = modoEvolucion;
    }

    public String getModoEvolucionUAs() {
        return modoEvolucionUAs;
    }

    public void setModoEvolucionUAs(String modoEvolucionUAs) {
        this.modoEvolucionUAs = modoEvolucionUAs;
    }

    public List<NormativaDTO> getNormativas() {
        return normativas;
    }

    public void setNormativas(List<NormativaDTO> normativas) {
        this.normativas = normativas;
    }

    public List<ProcedimientoBaseDTO> getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(List<ProcedimientoBaseDTO> procedimientos) {
        this.procedimientos = procedimientos;
    }

    public NormativaDTO getNormativaSeleccionado() {
        return normativaSeleccionado;
    }

    public void setNormativaSeleccionado(NormativaDTO normativaSeleccionado) {
        this.normativaSeleccionado = normativaSeleccionado;
    }

    public ProcedimientoBaseDTO getProcedimientoSeleccionado() {
        return procedimientoSeleccionado;
    }

    public void setProcedimientoSeleccionado(ProcedimientoBaseDTO procedimientoSeleccionado) {
        this.procedimientoSeleccionado = procedimientoSeleccionado;
    }

    public boolean isMostrarProcsNormativas() {
        return mostrarProcsNormativas;
    }

    public void setMostrarProcsNormativas(boolean mostrarProcsNormativas) {
        this.mostrarProcsNormativas = mostrarProcsNormativas;
    }
}
