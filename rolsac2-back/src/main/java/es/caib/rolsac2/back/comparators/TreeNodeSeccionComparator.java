package es.caib.rolsac2.back.comparators;

import es.caib.rolsac2.service.model.SeccionDTO;
import org.primefaces.model.TreeNode;

import java.util.Comparator;

public class TreeNodeSeccionComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode n1, TreeNode n2) {
        return ((SeccionDTO) n1.getData()).compareTo((SeccionDTO) n2.getData()) * -1;
    }
}
