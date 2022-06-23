package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para seleccionar una UA/entidad.
 *
 * @author areus
 */
@Named
@ViewScoped
public class DialogSeleccionarUA extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionarUA.class);

    private String id;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

    @Inject
    private SessionBean sessionBean;

    private TreeNode root;

    private TreeNode selectedNode;

    private UnidadAdministrativaDTO ua;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.

        // root = initMockup();
        LOG.error("Modo aceeso " + this.getModoAcceso());
        String valor = this.getModoAcceso();
        ua = (UnidadAdministrativaDTO) UtilJSF.getValorMochilaByKey("ua"); //(Literal) sessionBean.getValorMochilaByKey("literal");

        UnidadAdministrativaDTO uaRoot = new UnidadAdministrativaDTO(1l, "GOIB", "GOIB");
        UnidadAdministrativaDTO hijo1 = new UnidadAdministrativaDTO(1l, "Hijo1", "Hijo1");
        UnidadAdministrativaDTO hijo2 = new UnidadAdministrativaDTO(1l, "Hijo2", "Hijo2");

        root = new LazyLoadingTreeNode(uaRoot, uaService);

    }

    private List<UnidadAdministrativaDTO> listFiles(String folder) {
        UnidadAdministrativaDTO[] files = new UnidadAdministrativaDTO[2];
        UnidadAdministrativaDTO hijo1 = new UnidadAdministrativaDTO(1l, "Hijo1", "Hijo1");
        UnidadAdministrativaDTO hijo2 = new UnidadAdministrativaDTO(1l, "Hijo2", "Hijo2");

        /*if (files == null) {
            return new ArrayList<>();
        }*/

        List<UnidadAdministrativaDTO> result = new ArrayList<>();
        result.add(hijo1);
        result.add(hijo2);
        return result;
    }

    public TreeNode initMockup() {
        TreeNode root = new DefaultTreeNode(new UnidadAdministrativaDTO(1l, "GOIB", "GOIB"), null);

        TreeNode applications = new DefaultTreeNode(new UnidadAdministrativaDTO(2l, "Ministeri de Educació", "Ministerio de educación"), root);
        TreeNode cloud = new DefaultTreeNode(new UnidadAdministrativaDTO(3l, "Ministeri de Sanitat", "Ministerio de sanidad"), root);
        TreeNode desktop = new DefaultTreeNode(new UnidadAdministrativaDTO(4l, "Ministeri de Defensa", "Ministerio de defensa"), root);
        TreeNode documents = new DefaultTreeNode(new UnidadAdministrativaDTO(5l, "Ministeri d'Interior'", "Ministerio de interior"), root);
        TreeNode downloads = new DefaultTreeNode(new UnidadAdministrativaDTO(6l, "Ministeri de Cultura", "Ministerio de cultura"), root);
        TreeNode main = new DefaultTreeNode(new UnidadAdministrativaDTO(7l, "Ministeri de la dona'", "Ministerio de la mujer"), root);
        TreeNode other = new DefaultTreeNode(new UnidadAdministrativaDTO(8l, "Ministeri d'e 'Ordre", "Ministerio de orden"), root);
        TreeNode pictures = new DefaultTreeNode(new UnidadAdministrativaDTO(9l, "Ministeri d'Adicio", "Ministerio de adición"), root);
        TreeNode videos = new DefaultTreeNode(new UnidadAdministrativaDTO(10l, "Ministeri de Jubiliació", "Ministerio de jubilicación"), root);

        //Applications
        TreeNode primeface = new DefaultTreeNode(new UnidadAdministrativaDTO(11l, "Viceministre1", "Viceministro1"), applications);
        TreeNode primefacesapp = new DefaultTreeNode("app", new UnidadAdministrativaDTO(12l, "Viceministre2", "Viceministro2"), primeface);
        TreeNode nativeapp = new DefaultTreeNode("app", new UnidadAdministrativaDTO(13l, "Viceministre3", "Viceministro3"), primeface);
        TreeNode mobileapp = new DefaultTreeNode("app", new UnidadAdministrativaDTO(14l, "Viceministre4", "Viceministro4"), primeface);
        TreeNode editorapp = new DefaultTreeNode("app", new UnidadAdministrativaDTO(15l, "Viceministre5", "Viceministro5"), applications);
        TreeNode settingsapp = new DefaultTreeNode("app", new UnidadAdministrativaDTO(16l, "Viceministre6", "Viceministro6"), applications);
/*
        //Cloud
        TreeNode backup1 = new DefaultTreeNode("document", new Document("backup-1.zip", "10kb", "Zip"), cloud);
        TreeNode backup2 = new DefaultTreeNode("document", new Document("backup-2.zip", "10kb", "Zip"), cloud);

        //Desktop
        TreeNode note1 = new DefaultTreeNode("document", new Document("note-meeting.txt", "50kb", "Text"), desktop);
        TreeNode note2 = new DefaultTreeNode("document", new Document("note-todo.txt", "100kb", "Text"), desktop);

        //Documents
        TreeNode work = new DefaultTreeNode(new Document("Work", "55kb", "Folder"), documents);
        TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30kb", "Document"), work);
        TreeNode resume = new DefaultTreeNode("document", new Document("Resume.doc", "25kb", "Resume"), work);
        TreeNode home = new DefaultTreeNode(new Document("Home", "20kb", "Folder"), documents);
        TreeNode invoices = new DefaultTreeNode("excel", new Document("Invoice.xsl", "20kb", "Excel"), home);

        //Downloads
        TreeNode spanish = new DefaultTreeNode(new Document("Spanish", "10kb", "Folder"), downloads);
        TreeNode tutorial1 = new DefaultTreeNode("document", new Document("tutorial-a1.txt", "5kb", "Text"), spanish);
        TreeNode tutorial2 = new DefaultTreeNode("document", new Document("tutorial-a2.txt", "5kb", "Text"), spanish);
        TreeNode travel = new DefaultTreeNode(new Document("Travel", "15kb", "Folder"), downloads);
        TreeNode hotelpdf = new DefaultTreeNode("travel", new Document("Hotel.pdf", "10kb", "PDF"), travel);
        TreeNode flightpdf = new DefaultTreeNode("travel", new Document("Flight.pdf", "5kb", "PDF"), travel);

        //Main
        TreeNode bin = new DefaultTreeNode("document", new Document("bin", "50kb", "Link"), main);
        TreeNode etc = new DefaultTreeNode("document", new Document("etc", "100kb", "Link"), main);
        TreeNode var = new DefaultTreeNode("document", new Document("var", "100kb", "Link"), main);

        //Other
        TreeNode todotxt = new DefaultTreeNode("document", new Document("todo.txt", "3kb", "Text"), other);
        TreeNode logopng = new DefaultTreeNode("picture", new Document("logo.png", "2kb", "Picture"), other);

        //Pictures
        TreeNode barcelona = new DefaultTreeNode("picture", new Document("barcelona.jpg", "90kb", "Picture"), pictures);
        TreeNode primeng = new DefaultTreeNode("picture", new Document("primefaces.png", "30kb", "Picture"), pictures);
        TreeNode prime = new DefaultTreeNode("picture", new Document("prime.jpg", "30kb", "Picture"), pictures);

        //Videos
        TreeNode primefacesmkv = new DefaultTreeNode("video", new Document("primefaces.mkv", "1000kb", "Video"), videos);
        TreeNode introavi = new DefaultTreeNode("video", new Document("intro.avi", "500kb", "Video"), videos);
*/
        return root;
    }


    public void guardar() {

        /*
        Faltaría ver si la UA seleccionada
        if (this.data.getId() == null) {
            personalService.create(this.data, sessionBean.getUnidadActiva().getId());
        } */

        if (selectedNode == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
            return;
        }

        // Retornamos resultado
        LOG.error("Acceso:" + this.getModoAcceso());
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        result.setResult(selectedNode.getData());
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
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
