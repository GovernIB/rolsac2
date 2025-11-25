package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.comparators.TreeNodeTemaComparator;
import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.*;
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
import java.util.Objects;

@Named
@ViewScoped
public class DialogSeleccionarTema extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionarTema.class);

    private String id;
    private String codigoTema;
    @Inject
    private TemaServiceFacade temaServiceFacade;

    @Inject
    private SessionBean sessionBean;

    private TreeNode root;

    private TreeNode selectedNode;

    private TemaDTO temaAux;

    private TemaDTO tema;

    private Boolean esCabecera;

    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        tema = (TemaDTO) UtilJSF.getValorMochilaByKey("tema");
        esCabecera = Boolean.parseBoolean((String) UtilJSF.getDialogParam("esCabecera"));
        root = new LazyLoadingTreeNode();

        List<TemaDTO> temasRoot = temaServiceFacade.getRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());

        if (tema != null && tema.getCodigo() != null) {
            tema = temaServiceFacade.findById(tema.getCodigo());
            temaAux = tema.clone();

            // Obtener la raíz del tema actual
            TemaDTO temaRaiz = tema;
            while (temaRaiz.getTemaPadre() != null) {
                temaRaiz = temaRaiz.getTemaPadre();
            }

            // Construir solo el árbol de la raíz del tema seleccionado
            for (TemaDTO temaRoot : temasRoot) {
                if (temaRoot.getCodigo().equals(temaRaiz.getCodigo())) {
                    LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(temaRoot, root);
                    rootChildNode.setExpanded(true);

                    List<TemaDTO> hijos = temaServiceFacade.getHijos(temaRoot.getCodigo(), sessionBean.getLang());
                    Long codTema = null;
                    if (codigoTema != null && !codigoTema.isEmpty()) {
                        codTema = Long.parseLong(codigoTema);
                    }
                    for (TemaDTO hijo : hijos) {
                        if (codTema != null && hijo.getCodigo().compareTo(codTema) == 0) {
                            continue;
                        }
                        LazyLoadingTreeNode hijoNode = new LazyLoadingTreeNode(hijo, rootChildNode);
                        hijoNode.setExpanded(false);
                        hijoNode.setSelectable(true);

                        if (hijo.getCodigo().equals(tema.getCodigo())) {
                            hijoNode.setSelected(true);
                            selectedNode = hijoNode;
                        }
                    }
                    break;
                }
            }
        } else {
            // Cargar todas las raíces con sus hijos de primer nivel
            for (TemaDTO temaRoot : temasRoot) {
                LazyLoadingTreeNode rootChildNode = new LazyLoadingTreeNode(temaRoot, root);
                rootChildNode.setExpanded(true);

                List<TemaDTO> hijos = temaServiceFacade.getHijos(temaRoot.getCodigo(), sessionBean.getLang());
                for (TemaDTO hijo : hijos) {
                    LazyLoadingTreeNode hijoNode = new LazyLoadingTreeNode(hijo, rootChildNode);
                    hijoNode.setExpanded(false);
                    hijoNode.setSelectable(true);
                }
            }
        }

        ordenarArbol();
    }

    private void ordenarArbol() {
        if (!root.getChildren().isEmpty()) {
            TreeUtils.sortNode(root.getChildren().get(0), new TreeNodeTemaComparator());
        }
    }

    private LazyLoadingTreeNode construirArbolDesdeHoja(TemaDTO hoja, LazyLoadingTreeNode arbol) {
        LazyLoadingTreeNode nodo = null;
        if (hoja.getTemaPadre() != null) {
            nodo = new LazyLoadingTreeNode(hoja, construirArbolDesdeHoja(hoja.getTemaPadre(), arbol));
        } else {
            nodo = new LazyLoadingTreeNode(hoja, arbol);
        }

        final LazyLoadingTreeNode resultNodo = nodo;
        resultNodo.setType("TemaDTO");
        resultNodo.setExpanded(true);

        List<TemaDTO> childs = temaServiceFacade.getHijos(((TemaDTO) resultNodo.getData()).getCodigo(), sessionBean.getLang());

        childs.removeIf(c -> tema.getTemaPadre() != null && c.getCodigo().equals(tema.getTemaPadre().getCodigo()));

        if (((TemaDTO) resultNodo.getData()).getCodigo().equals(tema.getCodigo())) {
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
                    if (!c.getCodigo().equals(tema.getCodigo())) {
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
        TemaDTO cargando = new TemaDTO();
        Literal descripcion = new Literal();
        Traduccion tradEs = new Traduccion();
        tradEs.setIdioma("es");
        tradEs.setLiteral("Cargando");
        Traduccion tradCa = new Traduccion();
        tradCa.setIdioma("ca");
        tradCa.setLiteral("Carregant");
        descripcion.setTraducciones(Arrays.asList(tradEs, tradCa));
        cargando.setDescripcion(descripcion);
        return new LazyLoadingTreeNode(cargando, parentTreeNode);
    }

    public void onNodeExpand(NodeExpandEvent event) {
        final TreeNode expandedTreeNode = event.getTreeNode();

        if (((TemaDTO) expandedTreeNode.getData()).getTemaPadre() != null) {
            //Sólo se puede expandir si no es el tema padre
            event.getTreeNode().setExpanded(true);
            return;
        }

        List<TemaDTO> childs = temaServiceFacade.getHijos(((TemaDTO) expandedTreeNode.getData()).getCodigo(), sessionBean.getLang());

        if (!childs.isEmpty()) {
            expandedTreeNode.getChildren().clear();
            childs.forEach(c -> {
                if (c != null && c.getCodigo() != null && tema != null && tema.getTemaPadre() != null &&
                        c.getCodigo().equals(tema.getTemaPadre().getCodigo())) {
                    return;
                }
                LazyLoadingTreeNode grandChild = new LazyLoadingTreeNode(c, expandedTreeNode);
                addTreeNodeCargando(grandChild);
                expandedTreeNode.getChildren().add(grandChild);
            });

            expandedTreeNode.setExpanded(true);
        } else {
            expandedTreeNode.getChildren().clear();
            expandedTreeNode.setExpanded(true);
        }
    }

    public String getIcono(TemaGridDTO valor) {
        if (valor.getTipoMateriaSIA() == null) {
            return "";
        } else {
            return Constantes.INDEXAR_SIA_ICONO;
        }
    }

    public String getIcono(TemaDTO valor) {
        if (valor.getTipoMateriaSIA() == null) {
            return "";
        } else {
            return Constantes.INDEXAR_SIA_ICONO;
        }
    }


    public String getIconoSIA() {
        return Constantes.INDEXAR_SIA_ICONO;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        String node = event.getTreeNode().getData().toString();
    }

    public void guardar() {

        if (selectedNode == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
            return;
        } /*else {
            sessionBean.cambiarTema((TemaDTO) selectedNode.getData());
        }*/

        // Retornamos resultado
        LOG.error("Acceso:" + this.getModoAcceso());

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        result.setResult(selectedNode.getData());
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        /*final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        result.setCanceled(true);
        UtilJSF.closeDialog(result);*/
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        if (selectedNode != null) {
            //            temaAux = (TemaDTO) selectedNode.getData();
            result.setModoAcceso(TypeModoAcceso.EDICION);
            result.setResult(temaAux);
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

    public TemaDTO getTemaAux() {
        return temaAux;
    }

    public void setTemaAux(TemaDTO temaAux) {
        this.temaAux = temaAux;
    }

    public String getCodigoTema() {
        return codigoTema;
    }

    public void setCodigoTema(String codigoTema) {
        this.codigoTema = codigoTema;
    }
}
