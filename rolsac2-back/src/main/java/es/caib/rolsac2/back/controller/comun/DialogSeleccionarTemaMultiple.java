package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.comparators.TreeNodeTemaComparator;
import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.util.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class DialogSeleccionarTemaMultiple extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionarTemaMultiple.class);

    private String id;

    @EJB
    private TemaServiceFacade temaServiceFacade;

    private TreeNode root;

    private TreeNode[] temasSeleccionados;

    private List<TemaGridDTO> temasRelacionados;

    private TemaGridDTO temaPadre;

    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        temaPadre = (TemaGridDTO) UtilJSF.getValorMochilaByKey("temaPadre");
        temasRelacionados = (List<TemaGridDTO>) UtilJSF.getValorMochilaByKey("temasRelacionados");

        root = new CheckboxTreeNode(temaPadre, null);
        root.setSelectable(false);
        root.setExpanded(true);

        for (TemaGridDTO tema : temasRelacionados) {
            if (tema.getMathPath() != null && tema.getMathPath().contains(temaPadre.getCodigo().toString())) {
                root.setExpanded(true);
            }
            if (tema.getCodigo().compareTo(temaPadre.getCodigo()) == 0) {
                root.setExpanded(true);
                root.setSelected(true);
            }
        }
        construirArbolDesdeHoja(temaPadre, root);

        UtilJSF.vaciarMochila();
    }

    private void construirArbolDesdeHoja(TemaGridDTO hoja, TreeNode arbol) {
        CheckboxTreeNode nodo = null;
        List<TemaGridDTO> hijos = temaServiceFacade.getGridHijos(hoja.getCodigo(), sessionBean.getLang());
        for (TemaGridDTO hijo : hijos) {
            nodo = new CheckboxTreeNode(hijo, arbol);
            for (TemaGridDTO tema : temasRelacionados) {
                if (tema.getMathPath() != null && tema.getMathPath().contains(hijo.getCodigo().toString())) {
                    nodo.setExpanded(true);
                }
                if (tema.getCodigo().compareTo(hijo.getCodigo()) == 0) {
                    nodo.setExpanded(true);
                    nodo.setSelected(true);
                }
            }
            this.construirArbolDesdeHoja(hijo, nodo);
        }
    }

    public void guardar() {
        if (temasSeleccionados == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"),
                    getLiteral("msg.seleccioneElemento"));
            return;
        } else {
            temasRelacionados.clear();
            for (TreeNode tree : Arrays.asList(temasSeleccionados)) {
                TemaGridDTO tema = (TemaGridDTO) tree.getData();
                temasRelacionados.add(tema);
            }
        }

        // Retornamos resultado
        //LOG.error("Acceso:" + this.getModoAcceso());
        UtilJSF.anyadirMochila("temaPadre", temaPadre);

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        result.setResult(temasRelacionados);
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

    public TreeNode[] getTemasSeleccionados() {
        return temasSeleccionados;
    }

    public void setTemasSeleccionados(TreeNode[] temasSeleccionados) {
        this.temasSeleccionados = temasSeleccionados;
    }

    public TemaGridDTO getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(TemaGridDTO temaPadre) {
        this.temaPadre = temaPadre;
    }
}
