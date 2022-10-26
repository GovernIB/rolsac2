package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.service.model.SeccionDTO;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.List;

public class LazyLoadingTreeNode extends DefaultTreeNode {


    public LazyLoadingTreeNode(UnidadAdministrativaDTO data, TreeNode parent) {
        super(UnidadAdministrativaDTO.class.getSimpleName(), data, parent);
    }

    public LazyLoadingTreeNode(TemaDTO data, TreeNode parent) {
        super(TemaDTO.class.getSimpleName(), data, parent);
    }

    public LazyLoadingTreeNode() {}

    public LazyLoadingTreeNode(SeccionDTO data, TreeNode parent) {
        super(SeccionDTO.class.getSimpleName(), data, parent);
    }

    @Override
    public List<TreeNode> getChildren() {
        return super.getChildren();
    }

    @Override
    public int getChildCount() {
        return super.getChildCount();
    }

    @Override
    public boolean isLeaf() {
        return super.isLeaf();
    }
}
