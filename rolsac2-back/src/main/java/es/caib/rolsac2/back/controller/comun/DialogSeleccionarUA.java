package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.comparators.TreeNodeUAComparator;
import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.util.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controlador para seleccionar una UA/entidad.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogSeleccionarUA extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionarUA.class);

    private String id;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

    @Inject
    private SessionBean sessionBean;

    private TreeNode root;

    private TreeNode selectedNode;

    private UnidadAdministrativaDTO ua;

    private UnidadAdministrativaDTO uaAux;

    private Boolean esCabecera = Boolean.FALSE;

    /**
     *  Indica la raiz del arbol que se debe mostrar. Si es nulo, se muestra la ua principal de la entidad del usuario.
     */
    private List<UnidadAdministrativaGridDTO> uAsRaiz;

    public void load() {
        LOG.debug("init");

        ua = (UnidadAdministrativaDTO) UtilJSF.getValorMochilaByKey("ua");
        uAsRaiz = (List< UnidadAdministrativaGridDTO>) UtilJSF.getValorMochilaByKey("uAsRaiz");
        esCabecera = Boolean.parseBoolean((String) UtilJSF.getDialogParam("esCabecera"));
        root = new LazyLoadingTreeNode();

        //Dependiendo de si se pasa la UA o no, se tiene que cargar el arbol de manera distinta.
        if (ua != null && ua.getCodigo() != null) {
            uaAux = (UnidadAdministrativaDTO) ua.clone();

            ua = uaService.findUASimpleByID(ua.getCodigo(), sessionBean.getLang(), null);
            if ( !esUARaizArbol(ua)) {
                LazyLoadingTreeNode selectedRaiz = construirArbolDesdeHoja(ua, (LazyLoadingTreeNode) root);
                UnidadAdministrativaDTO raiz = (UnidadAdministrativaDTO)selectedRaiz.getData();
                if( !CollectionUtils.isEmpty(uAsRaiz)) {
                    for (UnidadAdministrativaGridDTO uaRaiz : uAsRaiz) {
                        if (!Objects.equals(raiz.getCodigo(), uaRaiz.getCodigo())) {
                            UnidadAdministrativaDTO uaRoot = uaService.findUASimpleByID(uaRaiz.getCodigo(), sessionBean.getLang(), null);
                            LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(uaRoot, root);
                            if (!CollectionUtils.isEmpty(((UnidadAdministrativaDTO) rootChildNode.getData()).getHijos()))
                                addTreeNodeCargando(rootChildNode);
                        }
                    }
                }

            } else {

                if(CollectionUtils.isEmpty(uAsRaiz)) {
                    UnidadAdministrativaDTO uaRoot = uaService.findUASimpleByID(null, sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
                    LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(uaRoot, root);
                    rootChildNode.setSelected(true);
                    addTreeNodeCargando(rootChildNode);
                }else{
                    for( UnidadAdministrativaGridDTO uaRaiz : uAsRaiz) {
                        UnidadAdministrativaDTO uaRoot = uaService.findUASimpleByID(uaRaiz.getCodigo(), sessionBean.getLang(), null);
                        LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(uaRoot, root);
                        if( ! CollectionUtils.isEmpty( ( (UnidadAdministrativaDTO)rootChildNode.getData()).getHijos()))
                            addTreeNodeCargando(rootChildNode);

                        if ( ua != null &&
                                ((UnidadAdministrativaDTO) rootChildNode.getData()).getCodigo().equals(ua.getCodigo())) {
                            rootChildNode.setSelected(true);
                        }
                    }
                }
//                UnidadAdministrativaDTO uaRoot = uaService.findUASimpleByID(null, sessionBean.getLang(), sessionBean.getEntidad().getCodigo());

            }
        } else {

            if(CollectionUtils.isEmpty(uAsRaiz)) {
                UnidadAdministrativaDTO  uaRoot = uaService.findUASimpleByID(null, sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
                LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(uaRoot, root);
                addTreeNodeCargando(rootChildNode);
            }else{
                for( UnidadAdministrativaGridDTO uaRaiz : uAsRaiz){
                    UnidadAdministrativaDTO  uaRoot = uaService.findUASimpleByID(uaRaiz.getCodigo(), sessionBean.getLang(), null);
                    LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(uaRoot, root);
                    addTreeNodeCargando(rootChildNode);
                }

            }
        }
    }

    private void ordenarArbol() {
        if (root.getChildren().get(0) != null) {
            TreeUtils.sortNode(root.getChildren().get(0), new TreeNodeUAComparator());
        }
    }

    /**
     * Construye el árbol completo se le pase el raiz o una hoja. Si es una hoja va buscando padres hacia arriba
     * para llegar al raiz.
     *
     * El nodo ua pasado al dialog queda seleccionado.
     *
     * @param hoja
     * @param arbol
     * @return
     */
    private LazyLoadingTreeNode construirArbolDesdeHoja(UnidadAdministrativaDTO hoja, LazyLoadingTreeNode arbol) {
        LazyLoadingTreeNode nodo = null;
        if (!esUARaizArbol(hoja)) {
            nodo =  construirArbolDesdeHoja(hoja.getPadre(), arbol);
        } else {
            nodo = new LazyLoadingTreeNode(hoja, arbol);
        }

        final LazyLoadingTreeNode resultNodo = nodo;
        resultNodo.setType("UnidadAdministrativaDTO");
        resultNodo.setExpanded(true);

        List<UnidadAdministrativaDTO> childs = ((UnidadAdministrativaDTO) resultNodo.getData()).getHijos();
        if (childs == null) {
            if (((UnidadAdministrativaDTO) resultNodo.getData()).getCodigo().equals(ua.getCodigo())) {
                resultNodo.setSelected(true);
                if( !esUARaizArbol(ua)) {
                    resultNodo.setExpanded(false);
                }
                selectedNode = resultNodo;
            }
        } else {
            childs.removeIf(c -> ua.getPadre() != null && c.getCodigo().equals(ua.getPadre().getCodigo()));

            if (((UnidadAdministrativaDTO) resultNodo.getData()).getCodigo().equals(ua.getCodigo())) {
                resultNodo.setSelected(true);

                if (!childs.isEmpty()) {
                    resultNodo.getChildren().add(addTreeNodeCargando(resultNodo));
                }
                selectedNode = resultNodo;
            }

            if (!childs.isEmpty()) {
                nodo.getChildren().clear();
                childs.forEach(c -> {
                            LazyLoadingTreeNode grandChild = new LazyLoadingTreeNode(c, resultNodo);
                            if( c.getCodigo().equals(ua.getCodigo())){
                                grandChild.setSelected(true);
                                selectedNode = grandChild;
                            }
                            addTreeNodeCargando(grandChild);

                            resultNodo.getChildren().add(grandChild);
                });
            }
        }

        if (resultNodo.getChildren() != null) {
            for (TreeNode nodo2 : resultNodo.getChildren()) {
                if (!tieneHijos((UnidadAdministrativaDTO) nodo2.getData())) {
                    nodo2.getChildren().clear();
                }
            }
        }
        return resultNodo;
    }

    private boolean esUARaizArbol(UnidadAdministrativaDTO ua) {
       return ua.getPadre() == null || (uAsRaiz != null && uAsRaiz.stream().map(UnidadAdministrativaGridDTO::getCodigo).collect(Collectors.toList()).contains(ua.getCodigo()) );
    }

    private LazyLoadingTreeNode addTreeNodeCargando(TreeNode parentTreeNode) {
        UnidadAdministrativaDTO cargando = new UnidadAdministrativaDTO();
        Literal nombre = new Literal();
        Traduccion tradEs = new Traduccion();
        tradEs.setIdioma("es");
        tradEs.setLiteral("Cargando");
        Traduccion tradCa = new Traduccion();
        tradCa.setIdioma("ca");
        tradCa.setLiteral("Carregant");
        nombre.setTraducciones(Arrays.asList(tradEs, tradCa));
        cargando.setNombre(nombre);
        return new LazyLoadingTreeNode(cargando, parentTreeNode);
    }

    public void onNodeExpand(NodeExpandEvent event) {
        final TreeNode expandedTreeNode = event.getTreeNode();

        List<UnidadAdministrativaDTO> childs = uaService.getHijosSimple(((UnidadAdministrativaDTO) expandedTreeNode.getData()).getCodigo(), sessionBean.getLang(), ((UnidadAdministrativaDTO) expandedTreeNode.getData()));

        if (!childs.isEmpty()) {
            expandedTreeNode.getChildren().clear();
            childs.forEach(c -> {
                LazyLoadingTreeNode grandChild = new LazyLoadingTreeNode(c, expandedTreeNode);
                addTreeNodeCargando(grandChild);
                expandedTreeNode.getChildren().add(grandChild);
            });

            expandedTreeNode.setExpanded(true);
            for (Object o : expandedTreeNode.getChildren()) {
                TreeNode nodo = (TreeNode) o;
                if (!tieneHijos((UnidadAdministrativaDTO) nodo.getData())) {
                    nodo.getChildren().clear();
                }
            }
            PrimeFaces.current().executeScript("seleccion()");
        } else {
            expandedTreeNode.getChildren().clear();
            expandedTreeNode.setExpanded(false);
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        String node = event.getTreeNode().getData().toString();
    }

    public Boolean tieneHijos(UnidadAdministrativaDTO ua) {
        List<UnidadAdministrativaDTO> childs = uaService.getHijosSimple(ua.getCodigo(), sessionBean.getLang(), ua);
        return (childs.size() >= 1);
    }

    public void guardar() {

        /*
         * Faltaría ver si la UA seleccionada if (this.data.getId() == null) { personalService.create(this.data,
         * sessionBean.getUnidadActiva().getId()); }
         */

        if (selectedNode == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
            return;
        } else {
            if (Boolean.TRUE.equals(esCabecera) && !sessionBean.getUnidadActiva().getCodigo().equals(((UnidadAdministrativaDTO) selectedNode.getData()).getCodigo())) {
                sessionBean.cambiarUnidadAdministrativa((UnidadAdministrativaDTO) selectedNode.getData());
            }
        }

        // Retornamos resultado
        LOG.debug("Acceso:" + this.getModoAcceso());

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        result.setResult(selectedNode.getData());
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);

        if (selectedNode != null) {
            result.setResult(uaAux);
        } else {
            result.setCanceled(true);
        }
        UtilJSF.closeDialog(result);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

}

