package es.caib.rolsac2.back.comparators;

import es.caib.rolsac2.service.model.TemaDTO;
import org.primefaces.model.TreeNode;

import java.util.Comparator;

public class TreeNodeTemaComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode n1, TreeNode n2) {
        return ((TemaDTO) n1.getData()).compareTo((TemaDTO) n2.getData()) * -1;
    }
}
