package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.component.FicheroUpload;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Dialogo de documento de procedimiento (procedimiento, tramites y servicios)
 *
 * @Author Indra
 */
@Named
@ViewScoped
public class DialogDocumentoProcedimiento extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogDocumentoProcedimiento.class);

    private String id = "";
    private ProcedimientoDocumentoDTO data;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private FicheroServiceFacade ficheroServiceFacade;

    @Inject
    private SystemServiceFacade systemServiceBean;

    private TypeFicheroExterno tipoFichero = TypeFicheroExterno.PROCEDIMIENTO_DOCUMENTOS;

    private Long idProcedimento;

    private String tipo;

    private boolean documentoObligatorio = false;

    /**
     * Carga la ventana.
     */
    public void load() {
        this.setearIdioma();

        if (this.isModoAlta()) {
            data = new ProcedimientoDocumentoDTO();
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setTitulo(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setDocumentos(DocumentoMultiIdioma.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setCodigoString(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = (ProcedimientoDocumentoDTO) UtilJSF.getValorMochilaByKey("documento");
            //Si los ficheros no tienen el filename, hay que recuperarlo, igual que tipo
            if (data.getDocumentos() != null) {
                for (DocumentoTraduccion documento : data.getDocumentos().getTraducciones()) {
                    if (documento.getFicheroDTO() != null && documento.getFicheroDTO().getCodigo() != null && documento.getFicheroDTO().getFilename() == null) {
                        documento.setFicheroDTO(ficheroServiceFacade.getFicheroDTOById(documento.getFicheroDTO().getCodigo()));
                    }
                }
            }
            UtilJSF.vaciarMochila();
        }

        documentoObligatorio = true;

    }

    /**
     * Comprueba si el documento es obligatorio
     *
     * @return true si es obligatorio
     */
    public boolean isDocumentoObligatorio() {
        return documentoObligatorio;
    }

    /**
     * Comprueba si es de tipo procedimiento documento
     *
     * @return true si es de tipo procedimiento documento
     */
    public boolean isTipoProcedimientoDocumento() {
        return tipo != null && "PROC_DOC".equals(tipo);
    }

    /**
     * Comprueba si es de tipo servicio documento
     *
     * @return true si es de tipo servicio documento
     */
    public boolean isTipoServicioDocumento() {
        return tipo != null && "SERV_DOC".equals(tipo);
    }

    /**
     * Comprueba si es de tipo tramite documento
     *
     * @return true si es de tipo tramite documento
     */
    public boolean isTipoTramiteDocumento() {
        return tipo != null && "TRAM_DOC".equals(tipo);
    }

    /**
     * Comprueba si es de tipo tramite modelo
     *
     * @return true si es de tipo tramite modelo
     */
    public boolean isTipoTramiteModelo() {
        return tipo != null && "TRAM_MOD".equals(tipo);
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
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

    /**
     * Verifica si se puede guardar el documento
     *
     * @return true si se puede guardar
     */
    public boolean verificarGuardar() {

        if (documentoObligatorio) {

            if (tieneAlgunDocumentoRelleno()) {
                for (DocumentoTraduccion doc : data.getDocumentos().getTraducciones()) {
                    if (esIdiomaObligatorio(doc.getIdioma()) && (doc.getFicheroDTO() == null || doc.getFicheroDTO().getCodigo() == null)) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, this.getLiteral("dict.obligatorio.documentos"), true);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Comprueba si tiene algún documento relleno
     *
     * @return true si tiene algún documento relleno
     */
    private boolean tieneAlgunDocumentoRelleno() {
        boolean tieneAlgunDocumentoRelleno = false;
        for (DocumentoTraduccion doc : data.getDocumentos().getTraducciones()) {
            if (doc.getFicheroDTO() != null && doc.getFicheroDTO().getCodigo() != null) {
                tieneAlgunDocumentoRelleno = true;
                break;
            }
        }
        return tieneAlgunDocumentoRelleno;
    }

    /**
     * Comprueba si el idioma es obligatorio
     *
     * @param idioma idioma
     * @return true si es obligatorio
     */
    private boolean esIdiomaObligatorio(String idioma) {
        return TypeIdiomaFijo.CATALAN.toString().equals(idioma) || TypeIdiomaFijo.CASTELLANO.toString().equals(idioma);
    }

    /**
     * Cierra el dialogo
     */
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

    /**
     * Traduce el documento
     */
    public void traducir() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    /**
     * Retorna el dialogo de traducción
     *
     * @param event evento
     */
    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        ProcedimientoDocumentoDTO datoDTO = (ProcedimientoDocumentoDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
        }
    }

    /**
     * Comprueba si tiene documento
     *
     * @param idioma idioma
     * @return true si tiene documento
     */
    public boolean hasDocument(String idioma) {
        if (this.data.getDocumentos().getTraduccion(idioma) != null && this.data.getDocumentos().getTraduccion(idioma).getCodigo() != null) {
            return true;
        }
        return false;
    }

    /**
     * Maneja la subida de documentos
     *
     * @param event evento
     */
    public void handleDocUpload(FileUploadEvent event) {
        try {
            InputStream is = event.getFile().getInputStream();
            String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            Long idFichero = ficheroServiceFacade.createFicheroExterno(is.readAllBytes(), event.getFile().getFileName(), TypeFicheroExterno.PROCEDIMIENTO_DOCUMENTOS, idProcedimento, path);

            FicheroDTO ficheroDTO = new FicheroDTO();
            ficheroDTO.setFilename(event.getFile().getFileName());
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

    /**
     * Obtiene el fichero
     *
     * @param idioma idioma
     * @return fichero
     */
    public UploadedFile getFichero(String idioma) {
        FicheroDTO trad = this.data.getDocumentos().getTraduccion(idioma);
        FicheroUpload uploadedFile = null;
        if (trad == null) {
            return uploadedFile;
        }
        if (trad.getContenido() != null) {
            uploadedFile = new FicheroUpload(trad.getContenido(), trad.getFilename(), "");

        } else if (trad.getCodigo() != null) {
            String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            FicheroDTO fic = ficheroServiceFacade.getContentById(trad.getCodigo(), path);
            uploadedFile = new FicheroUpload(fic.getContenido(), fic.getFilename(), "");
        }
        return uploadedFile;
    }

    /**
     * Obtiene el nombre del fichero
     *
     * @param idioma idioma
     * @return nombre del fichero
     */
    public String getFileName(String idioma) {
        return this.data.getDocumentos().getTraduccion(idioma).getFilename();
    }

    /**
     * Obtiene el documento
     *
     * @param idioma idioma
     * @return documento
     */
    public StreamedContent obtenerDocumento(String idioma) {

        if (this.data.getDocumentos() == null || (this.data.getDocumentos().getTraduccion(idioma) == null && this.data.getDocumentos().getTraduccion(idioma).getCodigo() == null)) {
            //No debería entrar por aqui.
            return null;
        }
        FicheroDTO documento = this.data.getDocumentos().getTraduccion(idioma);
        if (documento.getContenido() == null) {
            //Nos bajamos el fichero si está vacío
            String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            documento = ficheroServiceFacade.getContentById(documento.getCodigo(), path);
        }
        //FicheroDTO logo = administracionSupServiceFacade.getLogoEntidad(this.data.getLogo().getCodigo());
        String mimeType = URLConnection.guessContentTypeFromName(documento.getFilename());
        InputStream fis = new ByteArrayInputStream(documento.getContenido());
        StreamedContent file = DefaultStreamedContent.builder().name(documento.getFilename()).contentType(mimeType).stream(() -> fis).build();
        return file;
    }

    /**
     * Elimina el documento
     *
     * @param idioma idioma
     */
    public void eliminarDocumento(String idioma) {
        DocumentoTraduccion docTrad = new DocumentoTraduccion();
        docTrad.setFicheroDTO(null);
        docTrad.setIdioma(idioma);
        this.data.getDocumentos().add(docTrad);
    }

    /**
     * Obtiene el id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene los datos
     *
     * @return datos
     */
    public ProcedimientoDocumentoDTO getData() {
        return data;
    }

    /**
     * Establece los datos
     *
     * @param data datos
     */
    public void setData(ProcedimientoDocumentoDTO data) {
        this.data = data;
    }

    /**
     * Obtiene el tipo de fichero
     *
     * @return tipo de fichero
     */
    public TypeFicheroExterno getTipoFichero() {
        return tipoFichero;
    }

    /**
     * Establece el tipo de fichero
     *
     * @param tipoFichero tipo de fichero
     */
    public void setTipoFichero(TypeFicheroExterno tipoFichero) {
        this.tipoFichero = tipoFichero;
    }

    /**
     * Obtiene el id del procedimiento
     *
     * @return id del procedimiento
     */
    public Long getIdProcedimento() {
        return idProcedimento;
    }

    /**
     * Establece el id del procedimiento
     *
     * @param idProcedimento id del procedimiento
     */
    public void setIdProcedimento(Long idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    /**
     * Obtiene el tipo
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
