package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.List;
import java.util.stream.Collectors;

public class LazyLoadingTreeNode extends DefaultTreeNode {

    private UnidadAdministrativaServiceFacade service;
    private boolean childrenFetched;

    public LazyLoadingTreeNode(UnidadAdministrativaDTO data, UnidadAdministrativaServiceFacade service) {
        super(UnidadAdministrativaDTO.class.getSimpleName(), data, null);
        this.service = service;
    }

    @Override
    public List<TreeNode> getChildren() {
        ensureChildrenFetched();
        return super.getChildren();
    }

    @Override
    public int getChildCount() {
        ensureChildrenFetched();
        return super.getChildCount();
    }

    @Override
    public boolean isLeaf() {
        ensureChildrenFetched();
        return super.isLeaf();
    }

    private void ensureChildrenFetched() {
        if (!childrenFetched) {
            childrenFetched = true;
            Long parentId = getData() == null ? null : ((UnidadAdministrativaDTO) getData()).getId();
            List<LazyLoadingTreeNode> childNodes = service
                    .getHijos(parentId)
                    .stream()
                    .map(item -> new LazyLoadingTreeNode(item, service))
                    .collect(Collectors.toList());
            super.getChildren().addAll(childNodes);
        }
    }


    /*
    private Function<String, List<UnidadAdministrativaDTO>> loadFunction;
    private boolean lazyLoaded;

    public LazyLoadingTreeNode(UnidadAdministrativaDTO data, Function<String, List<UnidadAdministrativaDTO>> loadFunction) {
        super(data);
        this.loadFunction = loadFunction;
    }

    @Override
    public List<TreeNode> getChildren() {
        lazyLoad();

        return super.getChildren();
    }

    @Override
    public int getChildCount() {
        lazyLoad();

        return super.getChildCount();
    }

    @Override
    public boolean isLeaf() {
        lazyLoad();

        return super.isLeaf();
    }

    private void lazyLoad() {
        if (!lazyLoaded) {
            lazyLoaded = true;

            Long id = ((UnidadAdministrativaDTO) getData()).getId();

            List<LazyLoadingTreeNode> childNodes = getChildNodes(id);
            super.getChildren().addAll(childNodes);
        }
    }

    private List<LazyLoadingTreeNode> getChildNodes(Long id) {
        List<LazyLoadingTreeNode> kids = new ArrayList<>();
        UnidadAdministrativaDTO hijo1 = new UnidadAdministrativaDTO(1l, "Hijo1", "Hijo1");
        UnidadAdministrativaDTO hijo2 = new UnidadAdministrativaDTO(1l, "Hijo2", "Hijo2");
        kids.add(new LazyLoadingTreeNode(hijo1, loadFunction));
        kids.add(new LazyLoadingTreeNode(hijo2, loadFunction));
        return kids;
    }

     */
}
