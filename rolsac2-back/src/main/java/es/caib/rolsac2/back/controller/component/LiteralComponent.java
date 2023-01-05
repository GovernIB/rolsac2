package es.caib.rolsac2.back.controller.component;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Constantes;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.event.SelectEvent;

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
import java.util.List;
import java.util.Map;

@FacesComponent(createTag = true, tagName = "literalComponent", namespace = "http://back.rolsac2.caib.es/tags")
public class LiteralComponent extends UIInput implements NamingContainer {

    // Fields
    // -------------------------------------------------------------------------------------
    @Inject
    protected SessionBean sessionBean;

    private UIInput texto;
    private InputTextarea textoA;
    private UIInput textoID;
    private InputTextarea textoIDA;
    private UIInput textoIdioma;
    private InputTextarea textoIdiomaA;
    private UIInput boton;
    private CommandButton btnAbrir;

    private UIInput textoInicializado;
    private InputTextarea textoInicializadoA;
    private String idioma;
    private String required;
    private TypeModoAcceso modoAcceso;

    private String tipo;
    private String height;
    private String width;
    private String idCompuesto;
    private UIInput textoHTML;

    private String textoValor;
    private Boolean toolbar;

    public static String ICONO_ROJO = "iconoRojo";
    public static String ICONO_AMARILLO = "iconoAmarillo";
    public static String ICONO_VERDE = "iconoVerde";

    /**
     * Texto utilizado principalmente para cuando es required a true y no rellenas
     * los valores, el poner el borde rojo
     **/
    private String estiloInput;

    private String soloLecture;

    // Actions
    // ------------------------------------------------------------------------------------

    /**
     * Returns the component family of {@link UINamingContainer}. (that's just
     * required by composite component)
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

    private Literal literal;

    private boolean obligatorio;

    /**
     * Set the selected and available values of the day, month and year fields based
     * on the model.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        final String inicializado = (String) textoInicializado.getValue();
        if (inicializado == null || inicializado.isEmpty()) {
            tipo = (String) getAttributes().get("tipo");
            textoInicializado.setValue("true"); // Lo marcamos como ya inicializado
            literal = (Literal) getAttributes().get("literal");
            idioma = (String) getAttributes().get("idioma");
            String ocultarTexto = (String) getAttributes().get("ocultarTexto");
            if (ocultarTexto != null && "true".equalsIgnoreCase(ocultarTexto)) {
                ((InputText) texto).setStyle("display:none;");
            }
            if (literal == null) {
                literal = Literal.createInstance();
                getAttributes().put("literal", literal);
            }
            if (idioma == null) {
                idioma = "es";
            }
            comprobarLectura();
        } else {
            literal = (Literal) getAttributes().get("literal");
            if (literal == null) {
                literal = Literal.createInstance();
            }
            idioma = (String) getAttributes().get("idioma");
            textoIdioma.setValue(idioma);
            getAttributes().put("literal", literal);
            comprobarLectura();
        }

        textoValor = literal.getTraduccion(idioma);
        if (tipo == null || !tipo.equals("html")) {
            textoID.setValue(literal.getCodigo());
            textoIdioma.setValue(idioma);
            texto.setValue(literal.getTraduccion(idioma));
        } else {
            textoID.setValue(literal.getCodigo());
            textoIdioma.setValue(idioma);

        }

        super.encodeBegin(context);
        if (isModoConsulta()) {
            if (tipo == null || !tipo.equals("html")) {
                if (texto instanceof InputText) {
                    ((InputText) texto).setStyle("opacity: .35; pointer-events:none;");
                }
                if (texto instanceof InputTextarea) {
                    ((InputTextarea) texto).setStyle("opacity: .35; pointer-events:none;");
                }

            }
        }

        /*Boolean ocultar = (Boolean) getAttributes().get("ocultar");
        if (ocultar == null || !ocultarTexto) {
            //ocultar(false);
        } else {
            //ocultar(true);
        }*/

    }

    private void ocultar(boolean ocultar) {
        if (texto instanceof InputText) {
            ((InputText) texto).setRendered(!ocultar);
        }
        if (texto instanceof InputTextarea) {
            ((InputTextarea) texto).setRendered(!ocultar);
        }
        if (btnAbrir != null) {
            btnAbrir.setRendered(ocultar);
        }

    }

    private void comprobarLectura() {
        Boolean iModoAcceso = (Boolean) getAttributes().get("disabled");
        if (modoAcceso == null) {
            modoAcceso = (iModoAcceso != null && iModoAcceso) ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
        }
        if (getAttributes().get("soloLecture") != null && "true".equals(getAttributes().get("soloLecture"))) {
            modoAcceso = TypeModoAcceso.CONSULTA;
        }

        if (isModoConsulta()) {
            if (tipo == null || !tipo.equals("html")) {
                ((InputText) texto).setStyle("opacity: .35; pointer-events:none;");
            }
        } else {
            if (texto instanceof InputText) {
                ((InputText) texto).setStyle("");
            }
            if (texto instanceof InputTextarea) {
                ((InputTextarea) texto).setStyle("");
            }
        }
    }

    /**
     * Returns the submitted value in dd-MM-yyyy format.
     */
    @Override
    public Object getSubmittedValue() {
        // return texto == null ? Literal.createInstance() : texto.getValue();
        Literal literal = (Literal) getAttributes().get("literal");
        return literal;
    }

    /*
     * private Literal getCalcularLiteral() { Long codigoAux = null; Literal literal
     * = Literal.createInstance(); if (tipo == null || !tipo.equals("html")) { if
     * (textoES.getValue() != null && !((String) textoES.getValue()).isBlank()) {
     * literal.add(new Traduccion("es", textoES.getValue().toString())); } if
     * (textoCA.getValue() != null && !((String) textoCA.getValue()).isBlank()) {
     * literal.add(new Traduccion("ca", textoCA.getValue().toString())); }
     *
     * if (textoID.getValue() instanceof String) { codigoAux =
     * Long.parseLong((String) textoID.getValue()); } else if (textoID.getValue()
     * instanceof Long) { codigoAux = (Long) textoID.getValue(); } } else { if
     * (textoESA.getValue() != null && !((String) textoESA.getValue()).isBlank()) {
     * literal.add(new Traduccion("es", textoESA.getValue().toString())); } if
     * (textoCAA.getValue() != null && !((String) textoCAA.getValue()).isBlank()) {
     * literal.add(new Traduccion("ca", textoCAA.getValue().toString())); }
     *
     * if (textoID.getValue() instanceof String) { codigoAux =
     * Long.parseLong((String) textoID.getValue()); } else if (textoID.getValue()
     * instanceof Long) { codigoAux = (Long) textoID.getValue(); } }
     * literal.setCodigo(codigoAux); return literal; }
     */

    /*
     * public void actualizarLiteral(AjaxBehaviorEvent event) {
     *
     * String nuevoValor = (String) ((UIInput) event.getComponent()).getValue();
     * String elidioma = (String) textoIdioma.getValue(); if ("ca".equals(elidioma))
     * { if (tipo == null || !tipo.equals("html")) { textoCA.setValue(nuevoValor); }
     * else { textoCAA.setValue(nuevoValor); } } else { if (tipo == null ||
     * !tipo.equals("html")) { textoES.setValue(nuevoValor); } else {
     * textoESA.setValue(nuevoValor); } } marcarActualizadoComponente(); }
     */

    public void actualizarLiteral() {

        String idioma = (String) getAttributes().get("idioma");
        Literal literal;
        if (getAttributes().get("literal") != null) {
            literal = (Literal) getAttributes().get("literal");
        } else {
            literal = Literal.createInstance();
        }
        String prueba = textoValor;

        Traduccion trad = new Traduccion();
        if (tipo != null && tipo.equals("html")) {
            trad.setLiteral((String) textoHTML.getValue());
        } else {
            trad.setLiteral((String) texto.getValue());
        }
        trad.setIdioma(idioma);
        literal.add(trad);
        getAttributes().put("literal", literal);

        marcarActualizadoComponente();
    }

    /**
     * Cuando se produce un cambio, se avisa al abstractController que ha habido
     * cambios.
     */
    private void marcarActualizadoComponente() {
        if (getAttributes().get("vista") != null && getAttributes().get("vista") instanceof AbstractController) {
            ((AbstractController) getAttributes().get("vista")).actualizadoComponente();
        }
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
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            literal = (Literal) respuesta.getResult();
            getAttributes().put("literal", literal);
            this.setModoAcceso(respuesta.getModoAcceso());
            if (tipo == null || !tipo.equals("html")) {
                texto.setValue(literal.getTraduccion((String) textoIdioma.getValue()));
                marcarActualizadoComponente();
                PrimeFaces.current().ajax().update("formDialog:tabs:txtNombre:texto");
            } else {
                textoIdioma.getValue();
                marcarActualizadoComponente();
                // PrimeFaces.current().executeScript("recargarTiny(\"" +
                // this.getTextoESA().getValue() + "\",\"" + this.getTextoCAA().getValue() +
                // "\")");
            }

            PrimeFaces.current().ajax().update("formDialog:tabs:txtNombre:btnAbrir");
        }
    }

    public void abrirVentana() {
        Boolean iModoAcceso = (Boolean) getAttributes().get("disabled");
        modoAcceso = (iModoAcceso != null && iModoAcceso) ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
        if (tipo == null || tipo.equals("literal") || tipo.isBlank()) {
            final Map<String, String> params = new HashMap<>();
            /*
             * if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION ||
             * modoAcceso == TypeModoAcceso.CONSULTA)) {
             * params.put(TypeParametroVentana.ID.toString(),
             * this.datoSeleccionado.getId().toString()); }
             */
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.modoAcceso.toString());
            String direccion = "/comun/dialogLiteral";
            Literal literal = (Literal) getAttributes().get("literal");
            UtilJSF.anyadirMochila("literal", literal);
            UtilJSF.anyadirMochila("required", isObligatorio());
            String nombreLiteral = (String) getAttributes().get("nombreLiteral");
            UtilJSF.anyadirMochila("nombreLiteral", nombreLiteral);
            Integer valores = ((String) getAttributes().get("idiomasPermitidos")).split(";").length;
            Integer height = (valores >= 4) ? 750 : valores * 200;
            UtilJSF.openDialog(direccion, modoAcceso, params, true, 1050, height);
        } else if (tipo.equals("html")) {
            final Map<String, String> params = new HashMap<>();
            /*
             * if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION ||
             * modoAcceso == TypeModoAcceso.CONSULTA)) {
             * params.put(TypeParametroVentana.ID.toString(),
             * this.datoSeleccionado.getId().toString()); }
             */
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.modoAcceso.toString());
            params.put("idioma", (String) getAttributes().get("idioma"));
            String direccion = "/comun/dialogLiteralHTML";
            Literal literal = (Literal) getAttributes().get("literal");
            UtilJSF.anyadirMochila("literal", literal);
            UtilJSF.openDialog(direccion, modoAcceso, params, true, 1050, 750);
        }
    }

    // Getters/setters
    // ----------------------------------------------------------------------------
    public String getEstiloBoton() {
        // if (isObligatorio() && !isCompleto()) {
        return comprobarEstado();
    }

    /**
     * Metodo que comprueba si los literales están bien rellenos.
     *
     * @return
     */
    private boolean isCompleto() {
        return this.literal != null && this.literal.getTraduccion(Constantes.IDIOMA_CATALAN) != null
                && !this.literal.getTraduccion(Constantes.IDIOMA_CATALAN).trim().isEmpty()
                && this.literal.getTraduccion(Constantes.IDIOMA_ESPANYOL) != null
                && !this.literal.getTraduccion(Constantes.IDIOMA_ESPANYOL).trim().isEmpty();
    }

    public String comprobarEstado() {
        if (getAttributes().get("idiomasPermitidos") != null && getAttributes().get("idiomasObligatorios") != null) {
            List<String> idiomasPermitidos = List.of(((String) getAttributes().get("idiomasPermitidos")).split(";"));
            List<String> idiomasObligatorios = List
                    .of(((String) getAttributes().get("idiomasObligatorios")).split(";"));
            Literal literal = (Literal) getAttributes().get("literal");
            boolean faltaCampo = false;

            if (idiomasPermitidos.size() > literal.getIdiomas().size()) {

                if (idiomasObligatorios.size() > literal.getIdiomas().size()) {
                    return ICONO_ROJO;
                }
                // Verificamos si hay algún idioma obligatorio nuevo
                //                          no está en los literales
                for (String idioma : idiomasObligatorios) {
                    if (!literal.getIdiomas().contains(idioma)) {
                        return ICONO_ROJO;
                    }
                }

                // Verificamos que los campos obligatorios estén todos
                for (Traduccion traduccion : literal.getTraducciones()) {
                    if ((traduccion.getLiteral() == null || traduccion.getLiteral().trim().isEmpty())
                            && idiomasPermitidos.contains(traduccion.getIdioma())) {
                        if (idiomasObligatorios.contains(traduccion.getIdioma())) {
                            return ICONO_ROJO;
                        }
                    }
                }

                // Como no hay restricciones de rojo, lo que faltan son idiomasOpcionales nuevos
                return ICONO_AMARILLO;
            }
            for (Traduccion traduccion : literal.getTraducciones()) {
                if ((traduccion.getLiteral() == null || traduccion.getLiteral().trim().isEmpty())
                        && idiomasPermitidos.contains(traduccion.getIdioma())) {
                    if (idiomasObligatorios.contains(traduccion.getIdioma())) {
                        return ICONO_ROJO;
                    }
                    faltaCampo = true;
                }
            }

            if (faltaCampo) {
                return ICONO_AMARILLO;
            } else {
                return ICONO_VERDE;
            }
        } else {
            return "";
        }

    }

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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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

    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal = literal;
    }

    /*
     * public UIInput getTextoES() { return textoES; }
     *
     * public void setTextoES(UIInput textoES) { this.textoES = textoES; }
     *
     * public UIInput getTextoCA() { return textoCA; }
     *
     * public void setTextoCA(UIInput textoCA) { this.textoCA = textoCA; }
     */

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

    public UIInput getTextoInicializado() {
        return textoInicializado;
    }

    public void setTextoInicializado(UIInput textoInicializado) {
        this.textoInicializado = textoInicializado;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public boolean isObligatorio() {
        return getAttributes().get("required") != null && (boolean) getAttributes().get("required");
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public InputTextarea getTextoA() {
        return textoA;
    }

    public void setTextoA(InputTextarea textoA) {
        this.textoA = textoA;
    }

    /*
     * public InputTextarea getTextoESA() { return textoESA; }
     *
     * public void setTextoESA(InputTextarea textoESA) { this.textoESA = textoESA; }
     *
     * public InputTextarea getTextoCAA() { return textoCAA; }
     *
     * public void setTextoCAA(InputTextarea textoCAA) { this.textoCAA = textoCAA; }
     */

    public InputTextarea getTextoIDA() {
        return textoIDA;
    }

    public void setTextoIDA(InputTextarea textoIDA) {
        this.textoIDA = textoIDA;
    }

    public InputTextarea getTextoIdiomaA() {
        return textoIdiomaA;
    }

    public void setTextoIdiomaA(InputTextarea textoIdiomaA) {
        this.textoIdiomaA = textoIdiomaA;
    }

    public InputTextarea getTextoInicializadoA() {
        return textoInicializadoA;
    }

    public void setTextoInicializadoA(InputTextarea textoInicializadoA) {
        this.textoInicializadoA = textoInicializadoA;
    }

    public String getEstiloInput() {
        return estiloInput;
    }

    public void setEstiloInput(String estiloInput) {
        this.estiloInput = estiloInput;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public final String getHeight() {
        return height;
    }

    public final void setHeight(String height) {
        this.height = height;
    }

    public final String getWidth() {
        return width;
    }

    public final void setWidth(String width) {
        this.width = width;
    }

    public final String getIdCompuesto() {
        return idCompuesto;
    }

    public final void setIdCompuesto(String idCompuesto) {
        this.idCompuesto = idCompuesto;
    }

    public final UIInput getTextoHTML() {
        return textoHTML;
    }

    public final void setTextoHTML(UIInput textoHTML) {
        this.textoHTML = textoHTML;
    }

    /**
     * @return the toolbar
     */
    public final Boolean getToolbar() {
        if (toolbar != null) {
            return toolbar;
        } else {
            return true;
        }
    }

    /**
     * @param toolbar the toolbar to set
     */
    public final void setToolbar(Boolean toolbar) {
        this.toolbar = toolbar;
    }

    public String getSoloLecture() {
        return soloLecture;
    }

    public void setSoloLecture(String soloLecture) {
        this.soloLecture = soloLecture;
    }

    public CommandButton getBtnAbrir() {
        return btnAbrir;
    }

    public void setBtnAbrir(CommandButton btnAbrir) {
        this.btnAbrir = btnAbrir;
    }
}