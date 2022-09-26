package es.caib.rolsac2.back.controller.component;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@FacesComponent(createTag = true, tagName = "temaComponent")
public class TemaComponent extends UIInput implements NamingContainer {

    private static final String ES_CABECERA = "esCabecera";

    // Fields -------------------------------------------------------------------------------------

    private UIInput texto;
    private UIInput textoES;
    private UIInput textoCA;
    private UIInput textoID;
    private UIInput textoIdioma;
    private UIInput boton;

    private TypeModoAcceso modoAcceso;

    private String textoValor;

    private String ocultarTexto;

    private String updateElementos;

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the component family of {@link UINamingContainer}. (that's just required by composite component)
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

    /**
     * Para ver si es modo creación.
     **/

    public boolean isModoCrear() {
        return modoAcceso != null && (modoAcceso == TypeModoAcceso.CONSULTA);
    }

    /**
     * Set the selected and available values of the day, month and year fields based on the model.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        String idioma = (String) getAttributes().get("idioma");
        String iModoAcceso = (String) getAttributes().get("disable");
        ocultarTexto = (String) getAttributes().get("ocultarTexto");

        if (ocultarTexto != null && ocultarTexto.equalsIgnoreCase("true")) {
            ((InputText) texto).setStyle("display:none;");
        }
        modoAcceso = ("true".equalsIgnoreCase(iModoAcceso)) ? TypeModoAcceso.CONSULTA
                : TypeModoAcceso.EDICION;

        if (this.getValue() == null) {
            this.setValue(TemaDTO.createInstance());
        }

        if (idioma == null) {
            idioma = "es";
        }

        textoIdioma.setValue(idioma);
        setearTextos((TemaDTO) this.getValue());
        super.encodeBegin(context);
    }

    /**
     * Returns the submitted value in dd-MM-yyyy format.
     */
    @Override
    public Object getSubmittedValue() {
        return this.getValue();
    }

    @Override
    protected Object getConvertedValue(FacesContext context, Object submittedValue) {
        try {
            return submittedValue;
        } catch (Exception e) {
            throw new ConverterException(e); // This is not to be expected in normal circumstances.
        }
    }

    private void setearTextos(TemaDTO tema) {
        if (tema != null) {
            String idioma = (String) textoIdioma.getValue();
            textoValor = tema.getIdentificador();
            /*
            textoES.setValue(ua.getNombre().getTraduccion("es"));
            textoCA.setValue(ua.getNombre().getTraduccion("ca"));
            textoID.setValue(ua.getCodigo());*/
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            TemaDTO temaSeleccionado = (TemaDTO) respuesta.getResult();
            if (temaSeleccionado != null) {
                setearTextos(temaSeleccionado);
                this.setValue(temaSeleccionado);
            }
        }
    }

    public void abrirVentana() {
        final Map<String, String> params = new HashMap<>();
        this.modoAcceso = TypeModoAcceso.ALTA;
        params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.modoAcceso.toString());
        String direccion = "/comun/dialogSeleccionarTema";

        UtilJSF.anyadirMochila("tema", this.getValue());
        UtilJSF.openDialog(direccion, modoAcceso, params, true, 750, 375);
    }

    public UIInput getTexto() {
        return texto;
    }

    public void setTexto(UIInput texto) {
        this.texto = texto;
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

    public TypeModoAcceso getModoAcceso() {
        return modoAcceso;
    }

    public void setModoAcceso(TypeModoAcceso modoAcceso) {
        this.modoAcceso = modoAcceso;
    }

    public String getTextoValor() {
        return textoValor;
    }

    public void setTextoValor(String textoValor) {
        this.textoValor = textoValor;
    }

    public String getUpdateElementos() {
        if (Boolean.TRUE.equals(getAttributes().get(ES_CABECERA))) {
            updateElementos = "texto @form";
        } else {
            updateElementos = "texto";
        }
        return updateElementos;
    }

    public void setUpdateElementos(String updateElementos) {
        this.updateElementos = updateElementos;
    }
}