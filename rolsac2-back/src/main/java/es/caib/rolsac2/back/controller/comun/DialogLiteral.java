package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Controlador para seleccionar una UA/entidad.
 *
 * @author areus
 */
@Named
@ViewScoped
public class DialogLiteral extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogLiteral.class);

    private String id;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

    @Inject
    private SessionBean sessionBean;

    private String textoCA;
    private String textoES;
    private Literal literal;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.

        literal = (Literal) UtilJSF.getValorMochilaByKey("literal"); //(Literal) sessionBean.getValorMochilaByKey("literal");
        if (literal == null) {
            literal = Literal.createInstance();
        }
        textoES = literal.getTraduccion("es");
        textoCA = literal.getTraduccion("ca");

        UtilJSF.vaciarMochila();//sessionBean.vaciarMochila();
        LOG.debug("Modo acceso " + this.getModoAcceso());


    }


    public void guardar() {

        literal.add(new Traduccion("es", textoES));
        literal.add(new Traduccion("ca", textoCA));

        // Retornamos resultado
        LOG.debug("Acceso:" + this.getModoAcceso());
        if (this.getModoAcceso() == null) {
            //TODO Pendiente
            this.setModoAcceso(TypeModoAcceso.ALTA.toString());
        }
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        result.setResult(literal);
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

    public UnidadAdministrativaServiceFacade getUaService() {
        return uaService;
    }

    public void setUaService(UnidadAdministrativaServiceFacade uaService) {
        this.uaService = uaService;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public String getTextoCA() {
        return textoCA;
    }

    public void setTextoCA(String textoCA) {
        this.textoCA = textoCA;
    }

    public String getTextoES() {
        return textoES;
    }

    public void setTextoES(String textoES) {
        this.textoES = textoES;
    }

    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal = literal;
    }
}
