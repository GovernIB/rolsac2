package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.comparators.TreeNodeSeccionComparator;
import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.SeccionServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.SeccionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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
import java.util.List;

@Named
@ViewScoped
public class DialogSeleccionarSeccion extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionarSeccion.class);

    private String id;

    @Inject
    private SeccionServiceFacade seccionServiceFacade;

    @Inject
    private SessionBean sessionBean;

    private TreeNode root;

    private TreeNode selectedNode;

    private SeccionDTO seccion;

    private Boolean esCabecera;

    public void load() {
        LOG.debug("init");

        seccion = (SeccionDTO) UtilJSF.getValorMochilaByKey("seccion");
        esCabecera = Boolean.parseBoolean((String) UtilJSF.getDialogParam("esCabecera"));
        root = new LazyLoadingTreeNode();

        if (seccion != null && seccion.getCodigo() != null){
            seccion = seccionServiceFacade.findById(seccion.getCodigo());
            if (seccion.getPadre() != null) {
                construirArbolDesdeHoja(seccion, (LazyLoadingTreeNode) root);
            } else {
                List<SeccionDTO> seccionesRoot =
                        seccionServiceFacade.getRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
                for(SeccionDTO seccionRoot : seccionesRoot) {
                    LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(seccionRoot, root);
                    rootChildNode.setSelected(true);
                    addTreeNodeCargando(rootChildNode);
                }

            }
        } else {
            List<SeccionDTO> seccionesRoot = seccionServiceFacade.getRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
            for(SeccionDTO seccionRoot : seccionesRoot) {
                LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(seccionRoot, root);
                addTreeNodeCargando(rootChildNode);
            }

        }
        ordenarArbol();
    }

    private void ordenarArbol() {
        if (root.getChildren().get(0) != null) {
            TreeUtils.sortNode(root.getChildren().get(0), new TreeNodeSeccionComparator());
        }
    }

    private LazyLoadingTreeNode construirArbolDesdeHoja(SeccionDTO hoja, LazyLoadingTreeNode arbol) {
        LazyLoadingTreeNode nodo = null;
        if (hoja.getPadre() != null) {
            nodo = new LazyLoadingTreeNode(hoja, construirArbolDesdeHoja(hoja.getPadre(), arbol));
        } else {
            nodo = new LazyLoadingTreeNode(hoja, arbol);
        }

        final LazyLoadingTreeNode resultNodo = nodo;
        resultNodo.setType("SeccionDTO");
        resultNodo.setExpanded(true);

        List<SeccionDTO> childs = seccionServiceFacade.getHijos(((SeccionDTO) resultNodo.getData()).getCodigo(), sessionBean.getLang());

        childs.removeIf(c -> seccion.getPadre() != null && c.getCodigo().equals(seccion.getPadre().getCodigo()));

        if (((SeccionDTO) resultNodo.getData()).getCodigo().equals(seccion.getCodigo())) {
            resultNodo.setSelected(true);
            resultNodo.setExpanded(false);

            if (!childs.isEmpty()) {
                resultNodo.getChildren().add(addTreeNodeCargando(resultNodo));
            }
            selectedNode = resultNodo;
        } else {
            if (!childs.isEmpty()) {
                nodo.getChildren().clear();
                childs.forEach(c -> {
                    if (!c.getCodigo().equals(seccion.getCodigo())) {
                        LazyLoadingTreeNode grandChild = new LazyLoadingTreeNode(c, resultNodo);
                        addTreeNodeCargando(grandChild);
                        resultNodo.getChildren().add(grandChild);
                    }
                });
            }
        }
        return resultNodo;
    }

    private LazyLoadingTreeNode addTreeNodeCargando(TreeNode parentTreeNode) {
        SeccionDTO cargando = new SeccionDTO();
        Literal descripcion = new Literal();
        Traduccion tradEs = new Traduccion();
        tradEs.setIdioma("es");
        tradEs.setLiteral("Cargando");
        Traduccion tradCa = new Traduccion();
        tradCa.setIdioma("ca");
        tradCa.setLiteral("Carregant");
        descripcion.setTraducciones(Arrays.asList(tradEs, tradCa));
        cargando.setNombre(descripcion);
        return new LazyLoadingTreeNode(cargando, parentTreeNode);
    }

    public void onNodeExpand(NodeExpandEvent event) {
        final TreeNode expandedTreeNode = event.getTreeNode();

        List<SeccionDTO> childs = seccionServiceFacade.getHijos(
                ((SeccionDTO) expandedTreeNode.getData()).getCodigo(), sessionBean.getLang());

        if (!childs.isEmpty()) {
            expandedTreeNode.getChildren().clear();
            childs.forEach(c -> {
                LazyLoadingTreeNode grandChild = new LazyLoadingTreeNode(c, expandedTreeNode);
                addTreeNodeCargando(grandChild);
                expandedTreeNode.getChildren().add(grandChild);
            });

            expandedTreeNode.setExpanded(true);
        } else {
            expandedTreeNode.getChildren().clear();
            expandedTreeNode.setExpanded(false);
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        String node = event.getTreeNode().getData().toString();
    }

    public void guardar() {

        if (selectedNode == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"),
                    getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
            return;
        }

        // Retornamos resultado
        LOG.error("Acceso:" + this.getModoAcceso());

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        result.setResult(selectedNode.getData());
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        result.setCanceled(true);
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
