package es.caib.rolsac2.back.controller.component;


import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

import javax.ejb.EJB;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@FacesComponent(createTag = true, tagName = "unidadAdministrativaComponent", namespace = "http://back.rolsac2.caib.es/tags")
public class UnidadAdministrativaComponent extends UIInput implements NamingContainer {

    // Fields -------------------------------------------------------------------------------------
    @Inject
    protected SessionBean sessionBean;

    private UIInput texto;
    private UIInput textoES;
    private UIInput textoCA;
    private UIInput textoID;
    private UIInput textoIdioma;
    private UIInput boton;

    private TypeModoAcceso modoAcceso;

    private String textoValor;

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the component family of {@link UINamingContainer}.
     * (that's just required by composite component)
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /**
     * Para ver si es modo consulta.
     **/
    public boolean isModoConsulta() {
        return modoAcceso != null && modoAcceso == TypeModoAcceso.CONSULTA;
    }

    private UnidadAdministrativaDTO ua;

    @EJB
    UnidadAdministrativaServiceFacade uaService;


    /**
     * Set the selected and available values of the day, month and year fields based on the model.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        ua = (UnidadAdministrativaDTO) getValue();
        String idioma = (String) getAttributes().get("idioma");
        String iModoAcceso = (String) getAttributes().get("soloLecture");
        String ocultarTexto = (String) getAttributes().get("ocultarTexto");
        if (ocultarTexto != null && "true".equalsIgnoreCase(ocultarTexto)) {
            ((InputText) texto).setStyle("display:none;");
        }
        modoAcceso = (iModoAcceso != null && "true".equals(iModoAcceso.toLowerCase())) ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;

        if (ua == null) {
            ua = UnidadAdministrativaDTO.createInstance();
        }
        if (idioma == null) {
            idioma = "es";
        }

        textoIdioma.setValue(idioma);
        setearTextos(ua);
        super.encodeBegin(context);
    }

    private void setearTextos(UnidadAdministrativaDTO ua) {
        String idioma = (String) textoIdioma.getValue();
        textoValor = ua.getNombre().getTraduccion(idioma);
        textoES.setValue(ua.getNombre().getTraduccion("es"));
        textoCA.setValue(ua.getNombre().getTraduccion("ca"));
        textoID.setValue(ua.getId());
    }

    /**
     * Returns the submitted value in dd-MM-yyyy format.
     */
    @Override
    public Object getSubmittedValue() {
        return getCalcularUA();
    }

    private UnidadAdministrativaDTO getCalcularUA() {
        UnidadAdministrativaDTO ua = null;
        Long codigo = (Long) textoID.getValue();
        if (codigo != null) {
            ua = uaService.findById(codigo);
        }
        return ua;
    }

    /**
     * Converts the submitted value to concrete {@link Date} instance.
     */
    @Override
    protected Object getConvertedValue(FacesContext context, Object submittedValue) {
        try {
            return submittedValue;
        } catch (Exception e) {
            throw new ConverterException(e); // This is not to be expected in normal circumstances.
        }
    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
            UnidadAdministrativaDTO ua = (UnidadAdministrativaDTO) respuesta.getResult();
            if (ua != null) {
                setearTextos(ua);
            }
        }
    }

    public void abrirVentana() {
        final Map<String, String> params = new HashMap<>();
        /*
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getId().toString());
        }*/
        this.modoAcceso = TypeModoAcceso.ALTA;
        params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.modoAcceso.toString());
        String direccion = "/comun/dialogSeleccionarUA";
        UtilJSF.anyadirMochila("ua", getCalcularUA());
        UtilJSF.openDialog(direccion, modoAcceso, params, true, 1050, 350);
    }


    // Getters/setters ----------------------------------------------------------------------------


    public UIInput getTexto() {
        return texto;
    }

    public void setTexto(UIInput texto) {
        this.texto = texto;
    }

    public UIInput getBoton() {
        return boton;
    }

    public void setBoton(UIInput boton) {
        this.boton = boton;
    }


    public TypeModoAcceso getModoAcceso() {
        return modoAcceso;
    }

    public void setModoAcceso(TypeModoAcceso modoAcceso) {
        this.modoAcceso = modoAcceso;
    }


    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public String getTextoValor() {
        return textoValor;
    }

    public void setTextoValor(String textoValor) {
        this.textoValor = textoValor;
    }


    public UIInput getTextoES() {
        return textoES;
    }

    public void setTextoES(UIInput textoES) {
        this.textoES = textoES;
    }

    public UIInput getTextoCA() {
        return textoCA;
    }

    public void setTextoCA(UIInput textoCA) {
        this.textoCA = textoCA;
    }

    public UIInput getTextoID() {
        return textoID;
    }

    public void setTextoID(UIInput textoID) {
        this.textoID = textoID;
    }

    public UIInput getTextoIdioma() {
        return textoIdioma;
    }

    public void setTextoIdioma(UIInput textoIdioma) {
        this.textoIdioma = textoIdioma;
    }
}