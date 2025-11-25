package es.caib.rolsac2.back.comparators;

import es.caib.rolsac2.service.model.TemaDTO;
import org.primefaces.model.TreeNode;

import java.util.Comparator;

public class TreeNodeTemaComparator implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode n1, TreeNode n2) {
        TemaDTO tema1 = (TemaDTO) n1.getData();
        TemaDTO tema2 = (TemaDTO) n2.getData();
        if (tema1 == null && tema2 == null) {
            return 0;
        } else if (tema1 == null) {
            return 1;
        } else if (tema2 == null) {
            return -1;
        } else {
            String desc1 = tema1.getDescripcion() != null ? tema1.getDescripcion().getTraduccionConValor("ca") : null;
            ;
            String desc2 = tema2.getDescripcion() != null ? tema2.getDescripcion().getTraduccionConValor("ca") : null;
            if (desc1 == null && desc2 == null) {
                return 0;
            } else if (desc1 == null) {
                return 1;
            } else if (desc2 == null) {
                return -1;
            } else {
                return desc1.compareToIgnoreCase(desc2);
            }
        }
    }
}
