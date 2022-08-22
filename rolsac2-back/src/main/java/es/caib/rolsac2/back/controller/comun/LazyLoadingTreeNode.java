package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.List;

public class LazyLoadingTreeNode extends DefaultTreeNode {


    public LazyLoadingTreeNode(UnidadAdministrativaDTO data, TreeNode parent) {
        super(UnidadAdministrativaDTO.class.getSimpleName(), data, parent);
    }

    public LazyLoadingTreeNode() {}

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
