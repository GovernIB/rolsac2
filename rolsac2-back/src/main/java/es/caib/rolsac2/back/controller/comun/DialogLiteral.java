package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccionException;
import es.caib.rolsac2.commons.plugins.traduccion.api.Idioma;
import es.caib.rolsac2.commons.plugins.traduccion.api.TipoEntrada;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.integracion.TraduccionServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
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

    @EJB
    private TraduccionServiceFacade traduccionServiceFacade;


    private List<String> idiomasAMostrar;

    private Map<String, String> textosEnIdioma;

    private Literal literal;

    private boolean required;

    private String nombreLiteral;

    private Integer maxlength;

    //Para la traduccion
    private List<String> idiomas;
    private String idiomaDestino;
    private String idiomaOrigen;
    private String idiomaAuxOrigen;
    private String idiomaAuxDestino;
    private boolean sustitucion = true;

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

        Integer length = (Integer) UtilJSF.getValorMochilaByKey("maxlength");
        maxlength = length != null ? length : 9999;

        UtilJSF.vaciarMochila();//sessionBean.vaciarMochila();
        LOG.debug("Modo acceso " + this.getModoAcceso());


        idiomaOrigen = sessionBean.getLang();
        idiomaAuxOrigen = idiomaOrigen;
        if (idiomaOrigen.equals("es")) {
            idiomaDestino = "ca";
            idiomaAuxDestino = idiomaDestino;
        } else {
            idiomaDestino = "es";
            idiomaAuxDestino = idiomaDestino;
        }
    }

    public void traducirLiterales() {
        if (idiomaDestino.equals(idiomaOrigen)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraduccion.mismoIdioma"), true);
        } else {
            try {
                Map<String, String> opciones = new HashMap<>();
                String tradDestino = traduccionServiceFacade.traducir(TipoEntrada.TEXTO_PLANO.toString(), literal.getTraduccion(idiomaOrigen), comprobarIdioma(idiomaOrigen), comprobarIdioma(idiomaDestino), opciones, sessionBean.getEntidad().getCodigo());
                if (isSustitucion()) {
                    literal.add(new Traduccion(idiomaDestino, tradDestino));
                } else {
                    if (literal.getTraduccion(idiomaDestino) == null || literal.getTraduccion(idiomaDestino).isEmpty()) {
                        literal.add(new Traduccion(idiomaDestino, tradDestino));
                    }
                }
                UtilJSF.updateComponent(getIdTexto(idiomaDestino));
                textosEnIdioma.put(idiomaDestino, tradDestino);
            } catch (IPluginTraduccionException e) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraduccion.errorComunicacion"));
            } catch (Exception e) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTraduccion.error"));
            }

        }
    }

    private String getIdTexto(String idiomaDestino) {
        return "formDialog:txt" + idiomaDestino.substring(0, 1).toUpperCase() + idiomaDestino.substring(1);
    }

    /**
     * Comprueba el idioma y lo devuelve en formato Idioma.
     *
     * @param idioma
     * @return
     */
    public Idioma comprobarIdioma(String idioma) {
        switch (idioma) {
            case "ca":
                return Idioma.CATALAN;
            case "es":
                return Idioma.CASTELLANO;
            case "fr":
                return Idioma.FRANCES;
            case "en":
                return Idioma.INGLES;
            case "de":
                return Idioma.ALEMAN;
            case "it":
                return Idioma.ITALIANO;
            default:
                return null;
        }
    }

    /**
     * Guarda el idioma
     */
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

    /**
     * Cierra la pagina
     */
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
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogLiteral.validacion.idiomas") + idiomasPendientes.toString(), true);
        }
        return error;
    }

    public boolean calcularRequired(String idioma) {
        if (required) {
            return sessionBean.getIdiomasObligatoriosList().contains(idioma);
        } else {
            return false;
        }

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

    public Integer getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(Integer maxlength) {
        this.maxlength = maxlength;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getIdiomaDestino() {
        return idiomaDestino;
    }

    public void setIdiomaDestino(String idiomaDestino) {
        this.idiomaDestino = idiomaDestino;
    }

    public String getIdiomaOrigen() {
        return idiomaOrigen;
    }

    public void setIdiomaOrigen(String idiomaOrigen) {
        this.idiomaOrigen = idiomaOrigen;
    }

    public String getIdiomaAuxOrigen() {
        return idiomaAuxOrigen;
    }

    public void setIdiomaAuxOrigen(String idiomaAuxOrigen) {
        this.idiomaAuxOrigen = idiomaAuxOrigen;
    }

    public String getIdiomaAuxDestino() {
        return idiomaAuxDestino;
    }

    public void setIdiomaAuxDestino(String idiomaAuxDestino) {
        this.idiomaAuxDestino = idiomaAuxDestino;
    }

    public boolean isSustitucion() {
        return sustitucion;
    }

    public void setSustitucion(boolean sustitucion) {
        this.sustitucion = sustitucion;
    }
}
