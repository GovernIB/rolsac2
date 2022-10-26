package es.caib.rolsac2.back.controller.component;


import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import org.keycloak.common.util.MimeTypeUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Date;

@FacesComponent(createTag = true, tagName = "ficheroComponent", namespace = "http://back.rolsac2.caib.es/tags")
public class FicheroComponent extends UIInput implements NamingContainer {

    // Fields -------------------------------------------------------------------------------------
    @Inject
    protected SessionBean sessionBean;

    @Inject
    protected FicheroServiceFacade ficheroServiceFacade;

    private UIInput textoInicializado;
    private UIInput textoID;
    private UIInput textoTemp;
    private UIInput textoFilename;

    private String idioma;
    private String required;
    private TypeModoAcceso modoAcceso;


    private String textoValor;

    private es.caib.rolsac2.service.model.types.TypeFicheroExterno tipoFichero;
    private Long idElemento;
    private String contentType;
    private String contentAccept;

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

    /**
     * Para ver si es modo creación.
     **/

    public boolean isModoCrear() {
        return modoAcceso != null && (modoAcceso == TypeModoAcceso.CONSULTA);
    }

    private FicheroDTO fichero;

    private boolean obligatorio;

    /**
     * Set the selected and available values of the day, month and year fields based on the model.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        final String inicializado = (String) textoInicializado.getValue();
        if (inicializado == null || inicializado.isEmpty()) {
            tipoFichero = (TypeFicheroExterno) getAttributes().get("tipoFichero");
            idElemento = (Long) getAttributes().get("idElemento");
            contentType = (String) getAttributes().get("contentType");
            contentAccept = (String) getAttributes().get("contentAccept");
            if (contentType == null) {
                contentType = "/(\\.|\\/)(gif|jpe?g|png)$/";
            }
            if (contentAccept == null) {
                contentAccept = ".gif,.jpg,.jpeg,.png";
            }
            textoInicializado.setValue("true"); //Lo marcamos como ya inicializado
            fichero = (FicheroDTO) getAttributes().get("fichero");
            if (fichero != null) {
                textoID.setValue(fichero.getCodigo());
                textoFilename.setValue(fichero.getFilename().toString());
            }
            idioma = (String) getAttributes().get("idioma");
            Boolean iModoAcceso = (Boolean) getAttributes().get("disabled");
            if (modoAcceso == null) {
                modoAcceso = (iModoAcceso != null && iModoAcceso) ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
            }
            String ocultarTexto = (String) getAttributes().get("ocultarTexto");
            if (ocultarTexto != null && "true".equalsIgnoreCase(ocultarTexto)) {
                //  ((InputText) texto).setStyle("display:none;");
            }
            if (fichero == null) {
                fichero = new FicheroDTO(); //FicheroDTO.createInstance();
                getAttributes().put("fichero", fichero);
            }
            if (idioma == null) {
                idioma = "es";
            }
        } else {
            fichero = (FicheroDTO) getAttributes().get("fichero");
            idioma = (String) getAttributes().get("idioma");
            // textoIdioma.setValue(idioma);
            getAttributes().put("fichero", fichero);
        }
/*
        textoValor = literal.getTraduccion(idioma);
        if (tipo == null || !tipo.equals("html")) {
            textoID.setValue(literal.getCodigo());
            textoIdioma.setValue(idioma);
            texto.setValue(literal.getTraduccion(idioma));
        } else {
            textoID.setValue(literal.getCodigo());
            textoIdioma.setValue(idioma);

        }*/

        super.encodeBegin(context);/*
        if (isModoConsulta()) {
            if (tipo == null || !tipo.equals("html")) {
                ((InputText) texto).setStyle("opacity: .35; pointer-events:none;");
            }
        }*/
    }

    /**
     * Returns the submitted value in dd-MM-yyyy format.
     */
    @Override
    public Object getSubmittedValue() {
        if (textoID.getValue() == null) {
            this.setValue(null);
            return null;
        } else {
            FicheroDTO fic = new FicheroDTO();
            fic.setCodigo((Long) textoID.getValue());
            fic.setFilename((String) textoFilename.getValue());
            fic.setTipo(tipoFichero);
            getAttributes().put("fichero", fic);
            this.setValue(fic);
            return fic;
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

    public void handleLogoUpload(FileUploadEvent event) {
        try {
            InputStream is = event.getFile().getInputStream();
            Long idFichero = ((FicheroServiceFacade) CDI.current().select(FicheroServiceFacade.class).get()).createFicheroExterno(is.readAllBytes(), event.getFile().getFileName(), (TypeFicheroExterno) getAttributes().get("tipoFichero"), (Long) getAttributes().get("idElemento"));
            FicheroDTO logo = new FicheroDTO();
            logo.setFilename(event.getFile().getFileName());
            //logo.setContenido(is.readAllBytes());
            logo.setTipo(tipoFichero);
            logo.setCodigo(idFichero);
            getAttributes().put("fichero", logo);
            textoID.setValue(idFichero);
            //textoTemp;
            textoFilename.setValue(event.getFile().getFileName());
            fichero = logo;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para intentar descargar un fichero.
     *
     * @throws IOException
     */
    public void download() throws IOException {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType(MimeTypeUtil.getContentType(fichero.getFilename())); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        //ec.setResponseContentLength(getContentLength(fichero)); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fichero.getFilename() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream output = ec.getResponseOutputStream();
        output.write(fichero.getContenido());

        fc.responseComplete();

    }

    public StreamedContent obtenerFichero() {
        if (fichero == null) {
            //No debería entrar por aqui.
            return null;
        }
        if (this.fichero.getContenido() == null) {
            //Nos bajamos el fichero si está vacío
            fichero = ficheroServiceFacade.getContentById(this.fichero.getCodigo());
        }
        String mimeType = URLConnection.guessContentTypeFromName(fichero.getFilename());
        InputStream fis = new ByteArrayInputStream(fichero.getContenido());
        StreamedContent file = DefaultStreamedContent.builder()
                .name(fichero.getFilename())
                .contentType(mimeType)
                .stream(() -> fis)
                .build();
        return file;
    }

    public void eliminarFichero() {
        this.fichero = null;
    }


    /*
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
    }*/

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
/*
    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal = literal;
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
    }*/

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

/*
    public InputTextarea getTextoA() {
        return textoA;
    }

    public void setTextoA(InputTextarea textoA) {
        this.textoA = textoA;
    }

    public InputTextarea getTextoESA() {
        return textoESA;
    }

    public void setTextoESA(InputTextarea textoESA) {
        this.textoESA = textoESA;
    }

    public InputTextarea getTextoCAA() {
        return textoCAA;
    }

    public void setTextoCAA(InputTextarea textoCAA) {
        this.textoCAA = textoCAA;
    }

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
    } */


    public FicheroDTO getFichero() {
        return fichero;
    }

    public void setFichero(FicheroDTO fichero) {
        this.fichero = fichero;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public TypeFicheroExterno getTipoFichero() {
        return tipoFichero;
    }

    public void setTipoFichero(TypeFicheroExterno tipoFichero) {
        this.tipoFichero = tipoFichero;
    }

    public Long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Long idElemento) {
        this.idElemento = idElemento;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentAccept() {
        return contentAccept;
    }

    public void setContentAccept(String contentAccept) {
        this.contentAccept = contentAccept;
    }

    public UIInput getTextoID() {
        return textoID;
    }

    public void setTextoID(UIInput textoID) {
        this.textoID = textoID;
    }

    public UIInput getTextoTemp() {
        return textoTemp;
    }

    public void setTextoTemp(UIInput textoTemp) {
        this.textoTemp = textoTemp;
    }

    public UIInput getTextoFilename() {
        return textoFilename;
    }

    public void setTextoFilename(UIInput textoFilename) {
        this.textoFilename = textoFilename;
    }
}