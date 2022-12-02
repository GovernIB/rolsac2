package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para seleccionar un literal.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogLiteral extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogLiteral.class);

    private String id;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

    private List<String> idiomasAMostrar;

    private Map<String, String> textosEnIdioma;

    private Literal literal;

    private boolean required;

    private String nombreLiteral;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.

        textosEnIdioma = new HashMap<>();

        literal = (Literal) UtilJSF.getValorMochilaByKey("literal"); //(Literal) sessionBean.getValorMochilaByKey("literal");
        if (literal == null) {
            literal = Literal.createInstance();
        }
        //TODO: diferenciar si estamos en menú de admin de entidad o de superadministrador

        idiomasAMostrar = sessionBean.getIdiomasPermitidosList();
        for (String idioma : idiomasAMostrar) {
            textosEnIdioma.put(idioma, literal.getTraduccion(idioma));
        }

        required = (boolean) UtilJSF.getValorMochilaByKey("required");
        nombreLiteral = (String) UtilJSF.getValorMochilaByKey("nombreLiteral");

        UtilJSF.vaciarMochila();//sessionBean.vaciarMochila();
        LOG.debug("Modo acceso " + this.getModoAcceso());
    }


    public void guardar() {

        /*literal.add(new Traduccion("es", textoES));
        literal.add(new Traduccion("ca", textoCA));*/
        boolean errorValidacion = false;
        if (required) {
            errorValidacion = validar();
        }
        if (errorValidacion) {
            return;
        } else {
            for (Map.Entry<String, String> texto : textosEnIdioma.entrySet()) {
                literal.add(new Traduccion(texto.getKey(), texto.getValue()));
            }

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

    }

    public void cerrar() {

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public boolean validar() {
        boolean error = false;
        List<String> idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
        List<String> idiomasPendientes = new ArrayList<>();
        for (String idioma : idiomasObligatorios) {
            if (textosEnIdioma.get(idioma) == null) {
                error = true;
                idiomasPendientes.add(idioma);
            }
        }
        if (error) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogLiteral.validacion.idiomas") + idiomasPendientes.toString(),
                    true);
        }
        return error;
    }

    public boolean calcularRequired(String idioma) {
        return sessionBean.getIdiomasObligatoriosList().contains(idioma);
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

    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal = literal;
    }

    public List<String> getIdiomasAMostrar() {
        return idiomasAMostrar;
    }

    public void setIdiomasAMostrar(List<String> idiomasAMostrar) {
        this.idiomasAMostrar = idiomasAMostrar;
    }

    public Map<String, String> getTextosEnIdioma() {
        return textosEnIdioma;
    }

    public void setTextosEnIdioma(Map<String, String> textosEnIdioma) {
        this.textosEnIdioma = textosEnIdioma;
    }

    public String getNombreLiteral() {
        return nombreLiteral;
    }

    public void setNombreLiteral(String nombreLiteral) {
        this.nombreLiteral = nombreLiteral;
    }
}
