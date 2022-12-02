package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controlador para editar un DialogEntidad.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class ViewConfiguracionEntidad extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ViewConfiguracionEntidad.class);

    private String id;

    private EntidadDTO data;

    private String identificadorAntiguo;
    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private FicheroServiceFacade ficheroServiceFacade;

    private List<String> idiomasPermitidos = new ArrayList<>();

    private List<String> idiomasObligatorios = new ArrayList<>();

    private StreamedContent file;

    public void load() {
        LOG.debug("init view configuracion entidad");
        this.setearIdioma();

        data = UtilJSF.getSessionBean().getEntidad();

        setIdiomas();
    }

    public void guardar() {

        if (!checkObligatorio()) {
            return;
        }
        adaptIdiomas();

        administracionSupServiceFacade.updateEntidad(this.data);

        UtilJSF.getSessionBean().setEntidad(this.data);

        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("viewConfiguracionEntidad.actualizado"));

    }

    private boolean checkObligatorio() {
        if (this.data.getDescripcion() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogEntidad.obligatorio.descripcion"));
            return false;
        }

        if (!this.data.getDescripcion().checkObligatorio()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogEntidad.obligatorio.descripcion"));
            return false;
        }

        if (Objects.isNull(this.data.getCodigo())
                && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        /*
        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equalsIgnoreCase(this.data.getIdentificador())
                && administracionSupServiceFacade.existeIdentificadorEntidad(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }*/

        return true;
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void cerrar() {

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

        /*if (this.isModoAlta()) {
            idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();

            idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
        } else if (this.isModoEdicion()) {*/
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


        // }

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
            Long idFichero = ficheroServiceFacade.createFicheroExterno(is.readAllBytes(), event.getFile().getFileName(), TypeFicheroExterno.ENTIDAD, this.data.getCodigo());

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
            FicheroDTO fichero = ficheroServiceFacade.getContentById(this.data.getLogo().getCodigo());
            this.data.setLogo(fichero);
        }
        //FicheroDTO logo = administracionSupServiceFacade.getLogoEntidad(this.data.getLogo().getCodigo());
        String mimeType = URLConnection.guessContentTypeFromName(this.data.getLogo().getFilename());
        InputStream fis = new ByteArrayInputStream(this.data.getLogo().getContenido());
        StreamedContent file = DefaultStreamedContent.builder()
                .name(this.data.getLogo().getFilename())
                .contentType(mimeType)
                .stream(() -> fis)
                .build();
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
}