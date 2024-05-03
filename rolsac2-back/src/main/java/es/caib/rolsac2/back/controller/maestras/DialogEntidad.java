package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.*;

/**
 * Controlador para editar un DialogEntidad.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class DialogEntidad extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEntidad.class);

    private String id;

    private EntidadDTO data;

    private EntidadDTO dataOriginal;

    private String identificadorAntiguo;
    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private AdministracionEntServiceFacade administracionEntServiceFacade;

    @EJB
    private FicheroServiceFacade ficheroServiceFacade;

    private List<String> idiomasPermitidos = new ArrayList<>();

    private List<String> idiomasObligatorios = new ArrayList<>();

    private StreamedContent file;

    @EJB
    private SystemServiceFacade systemServiceFacade;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();

        data = new EntidadDTO();
        if (this.isModoAlta()) {
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            final Literal lopdDerechos = new Literal();
            final Literal lopdDestinatario = new Literal();
            final Literal lopdFinalidad = new Literal();
            final Literal uaComun = new Literal();
            for (String idioma : sessionBean.getIdiomasPermitidosList()) {
                lopdDerechos.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_DERECHOS, idioma)));
                lopdDestinatario.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_DESTINATARIO, idioma)));
                lopdFinalidad.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_FINALIDAD, idioma)));
                uaComun.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.UA_COMUN, idioma)));
            }
            data.setLopdDerechos(lopdDerechos);
            data.setLopdDestinatario(lopdDestinatario);
            data.setLopdFinalidad(lopdFinalidad);
            data.setUaComun(uaComun);
            data.setActiva(false);
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionSupServiceFacade.findEntidadById(Long.valueOf(id));
            dataOriginal = data.clone();
            identificadorAntiguo = data.getIdentificador();
            /*if(data.getLogo() != null) {
                file = obtenerFicheroDescarga(data.getLogo());
            }*/
        }

        setIdiomas();
    }

    public void guardar() {

        if (!checkObligatorio()) {
            return;
        }
        adaptIdiomas();

        if (this.data.getCodigo() == null) {
            Long nuevaEntidad = administracionSupServiceFacade.createEntidad(this.data, sessionBean.obtenerUsuarioAutenticado());
            //administracionEntServiceFacade.createUsuarioEntidad(sessionBean.obtenerUsuarioAutenticado(), nuevaEntidad);
            sessionBean.actualizarEntidades();
        } else {
            administracionSupServiceFacade.updateEntidad(this.data);
            sessionBean.actualizarEntidades();
        }

        // Cerramos y retornamos resultado
        cerrarDefinitivo();
    }

    private boolean checkObligatorio() {

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }

        if (Objects.isNull(this.data.getCodigo()) && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.campoVacio"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equalsIgnoreCase(this.data.getIdentificador()) && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        return true;
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        EntidadDTO datoDTO = (EntidadDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDescripcion(datoDTO.getDescripcion());
        }
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getActiva(), dataOriginal.getActiva()) != 0 || UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0 || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0 || UtilComparador.compareTo(data.getRolAdmin(), dataOriginal.getRolAdmin()) != 0 || UtilComparador.compareTo(data.getRolAdminContenido(), dataOriginal.getRolAdminContenido()) != 0 || UtilComparador.compareTo(data.getRolGestor(), dataOriginal.getRolGestor()) != 0 || UtilComparador.compareTo(data.getRolInformador(), dataOriginal.getRolInformador()) != 0;
    }

    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    /**
     * Gestión de retorno Descripcion.
     *
     * @param event
     */
    public void returnDialogoDescripcion(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
            final Literal literales = (Literal) respuesta.getResult();
            data.setDescripcion(literales);
        }
    }

    public void adaptIdiomas() {
        StringBuffer sb = new StringBuffer();
        for (String s : idiomasPermitidos) {
            sb.append(s);
            sb.append(";");
        }
        this.data.setIdiomasPermitidos(sb.toString());
        sb.delete(0, sb.length());

        for (String s : idiomasObligatorios) {
            sb.append(s);
            sb.append(";");
        }
        this.data.setIdiomasObligatorios(sb.toString());
    }

    public void setIdiomas() {

        if (this.isModoAlta()) {
            idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();

            idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
        } else if (this.isModoEdicion()) {
            if (this.data.getIdiomasPermitidos() == null) {
                idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();
            } else {
                String[] idiomasPermitidosArr = this.data.getIdiomasPermitidos().split(";");
                for (int i = 0; i < idiomasPermitidosArr.length; i++) {
                    idiomasPermitidos.add(idiomasPermitidosArr[i]);
                }
            }

            if (this.data.getIdiomasObligatorios() == null) {
                idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
            } else {
                String[] idiomasObligatoriosArr = this.data.getIdiomasObligatorios().split(";");
                for (int i = 0; i < idiomasObligatoriosArr.length; i++) {
                    idiomasObligatorios.add(idiomasObligatoriosArr[i]);
                }
            }


        }

    }

    public void revisarIdiomas() {
        //Revisar si está en el listado de idiomas permitidos
        List<String> idiomasAux = new ArrayList<>(idiomasObligatorios);
        for (String idioma : idiomasAux) {
            if (!idiomasPermitidos.contains(idioma)) {
                idiomasObligatorios.remove(idioma);
            }
        }
    }

    public void handleLogoUpload(FileUploadEvent event) {
        try {
            InputStream is = event.getFile().getInputStream();
            String path = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            Long idFichero = ficheroServiceFacade.createFicheroExterno(is.readAllBytes(), event.getFile().getFileName(), TypeFicheroExterno.ENTIDAD, this.data.getCodigo(), path);

            FicheroDTO logo = new FicheroDTO();
            logo.setFilename(event.getFile().getFileName());
            //Quitamos el contenido para que ocupe menos
            //
            //logo.setContenido(is.readAllBytes());
            logo.setTipo(TypeFicheroExterno.ENTIDAD);
            logo.setCodigo(this.data.getCodigo());
            logo.setCodigo(idFichero);
            this.data.setLogo(logo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent obtenerLogo() {

        if (this.data.getLogo() == null || (this.data.getLogo().getCodigo() == null && this.data.getLogo().getContenido() == null)) {
            //No debería entrar por aqui.
            return null;
        }
        if (this.data.getLogo().getContenido() == null) {
            //Nos bajamos el fichero si está vacío
            String path = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            FicheroDTO fichero = ficheroServiceFacade.getContentById(this.data.getLogo().getCodigo(), path);
            this.data.setLogo(fichero);
        }
        //FicheroDTO logo = administracionSupServiceFacade.getLogoEntidad(this.data.getLogo().getCodigo());
        String mimeType = URLConnection.guessContentTypeFromName(this.data.getLogo().getFilename());
        InputStream fis = new ByteArrayInputStream(this.data.getLogo().getContenido());
        StreamedContent file = DefaultStreamedContent.builder().name(this.data.getLogo().getFilename()).contentType(mimeType).stream(() -> fis).build();
        return file;
    }

    public void eliminarLogo() {
        this.file = null;
        this.data.setLogo(null);
    }

    public boolean isIdiomaDefecto(String idioma) {
        return idioma.equals("es") || idioma.equals("ca");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntidadDTO getData() {
        return data;
    }

    public void setData(EntidadDTO data) {
        this.data = data;
    }

    public List<String> getIdiomasPermitidos() {
        return idiomasPermitidos;
    }

    public void setIdiomasPermitidos(List<String> idiomasPermitidos) {
        this.idiomasPermitidos = idiomasPermitidos;
    }

    public List<String> getIdiomasObligatorios() {
        return idiomasObligatorios;
    }

    public void setIdiomasObligatorios(List<String> idiomasObligatorios) {
        this.idiomasObligatorios = idiomasObligatorios;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public EntidadDTO getDataOriginal() {
        return dataOriginal;
    }

    public void setDataOriginal(EntidadDTO dataOriginal) {
        this.dataOriginal = dataOriginal;
    }
}
