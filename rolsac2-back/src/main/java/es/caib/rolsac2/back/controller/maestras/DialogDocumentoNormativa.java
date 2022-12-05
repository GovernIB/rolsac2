package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.List;


@Named
@ViewScoped
public class DialogDocumentoNormativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogDocumentoNormativa.class);

    private String id = "";
    private DocumentoNormativaDTO data;

    @Inject
    private NormativaServiceFacade normativaServiceFacade;

    @Inject
    private FicheroServiceFacade ficheroServiceFacade;

    @Inject
    private SystemServiceFacade systemServiceBean;

    private TypeFicheroExterno tipo = TypeFicheroExterno.NORMATIVA_DOCUMENTO;


    public void load() {
        this.setearIdioma();
        String idNormativa = (String) UtilJSF.getDialogParam("idNormativa");

        if (this.isModoAlta()) {
            data = new DocumentoNormativaDTO();
            data.setNormativa(normativaServiceFacade.findById(Long.valueOf(idNormativa)));
            data.setTitulo(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setUrl(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setDocumentos(DocumentoMultiIdioma.createInstance(sessionBean.getIdiomasPermitidosList()));
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = normativaServiceFacade.findDocumentoNormativa(Long.valueOf(id));
        }

        UtilJSF.vaciarMochila();

        /*else if(isModoEdicion() || isModoConsulta()) {
            data = normativaServiceFacade.findDocumentoNormativa(Long.valueOf(id));
        }*/
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }
        if (this.data.getCodigo() == null) {
            normativaServiceFacade.createDocumentoNormativa(data);
        } else {
            normativaServiceFacade.updateDocumentoNormativa(data);
        }

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public boolean verificarGuardar() {

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getTitulo(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dict.titulo", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }

        return true;
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }


    public boolean hasDocument(String idioma) {
        if (this.data.getDocumentos().getTraduccion(idioma) != null && this.data.getDocumentos().getTraduccion(idioma).getCodigo() != null) {
            return true;
        }
        return false;
    }

    public void handleDocUpload(FileUploadEvent event) {
        try {
            InputStream is = event.getFile().getInputStream();
            Long idFichero = ficheroServiceFacade.createFicheroExterno(is.readAllBytes(), event.getFile().getFileName(),
                    TypeFicheroExterno.NORMATIVA_DOCUMENTO, this.data.getNormativa().getCodigo());

            FicheroDTO ficheroDTO = new FicheroDTO();
            ficheroDTO.setFilename(event.getFile().getFileName());
            //Quitamos el contenido para que ocupe menos
            //
            //logo.setContenido(is.readAllBytes());
            ficheroDTO.setTipo(TypeFicheroExterno.NORMATIVA_DOCUMENTO);
            ficheroDTO.setCodigo(this.data.getCodigo());
            ficheroDTO.setCodigo(idFichero);
            DocumentoTraduccion doc = new DocumentoTraduccion();
            doc.setIdioma(this.getIdioma());
            doc.setFicheroDTO(ficheroDTO);
            this.data.getDocumentos().add(doc);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName(String idioma) {
        return this.data.getDocumentos().getTraduccion(idioma).getFilename();
    }

    public StreamedContent obtenerDocumento(String idioma) {

        if (this.data.getDocumentos() == null || (this.data.getDocumentos().getTraduccion(idioma) == null && this.data.getDocumentos().getTraduccion(idioma).getCodigo() == null)) {
            //No debería entrar por aqui.
            return null;
        }
        FicheroDTO documento = this.data.getDocumentos().getTraduccion(idioma);
        if (documento.getContenido() == null) {
            //Nos bajamos el fichero si está vacío
            documento = ficheroServiceFacade.getContentById(documento.getCodigo());
        }
        //FicheroDTO logo = administracionSupServiceFacade.getLogoEntidad(this.data.getLogo().getCodigo());
        String mimeType = URLConnection.guessContentTypeFromName(documento.getFilename());
        InputStream fis = new ByteArrayInputStream(documento.getContenido());
        StreamedContent file = DefaultStreamedContent.builder()
                .name(documento.getFilename())
                .contentType(mimeType)
                .stream(() -> fis)
                .build();
        return file;
    }

    public void eliminarDocumento(String idioma) {
        DocumentoTraduccion docTrad = new DocumentoTraduccion();
        docTrad.setFicheroDTO(null);
        docTrad.setIdioma(idioma);
        this.data.getDocumentos().add(docTrad);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentoNormativaDTO getData() {
        return data;
    }

    public void setData(DocumentoNormativaDTO data) {
        this.data = data;
    }

    public TypeFicheroExterno getTipo() {
        return tipo;
    }

    public void setTipo(TypeFicheroExterno tipo) {
        this.tipo = tipo;
    }
}
