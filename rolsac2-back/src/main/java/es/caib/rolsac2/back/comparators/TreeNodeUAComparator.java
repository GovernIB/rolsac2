package es.caib.rolsac2.back.comparators;

import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

import org.primefaces.model.TreeNode;

import java.util.Comparator;

public class TreeNodeUAComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode n1, TreeNode n2) {
        return ((UnidadAdministrativaDTO) n1.getData()).compareTo((UnidadAdministrativaDTO) n2.getData()) * -1;
    }
}
