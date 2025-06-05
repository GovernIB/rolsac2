package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.exception.FicheroExternoException;
import es.caib.rolsac2.service.facade.AyudaServiceFacade;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.AyudaImagenGridDTO;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static org.keycloak.common.util.MimeTypeUtil.getContentType;

@Named
@ViewScoped
public class DialogTinyMCEIMG extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTinyMCEIMG.class);

    private List<AyudaImagenGridDTO> data;
    private List<AyudaImagenGridDTO> dataPerdidos;

    private AyudaImagenGridDTO datoSeleccionado;
    private AyudaImagenGridDTO itemSeleccionado;
    @EJB
    AyudaServiceFacade ayudaServiceFacade;

    @EJB
    SystemServiceFacade systemServiceBean;

    @EJB
    FicheroServiceFacade ficheroServiceBean;

    // Imagen por defecto (Base64, puedes cambiarla por una URL o un archivo en resources)
    private static final String URL_IMAGEN_DEFECTO = "/rolsac2-back/javax.faces.resource/img/STT_logo2.png.xhtml";


    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        data = ayudaServiceFacade.listImagenes(path);
        dataPerdidos = ayudaServiceFacade.listImagenesPerdidas(data, path);

        //Cargamos una imagen por defecto
        FacesContext facesContext = FacesContext.getCurrentInstance();
        InputStream inputStream = facesContext.getExternalContext().getResourceAsStream("resources/img/STT_logo2.png");

        if (inputStream == null) {
            System.out.println("⚠️ No se pudo cargar la imagen por defecto desde: " + URL_IMAGEN_DEFECTO);
            return;
        }

        this.imagenSeleccionada = DefaultStreamedContent.builder()
                .stream(() -> inputStream)
                .contentType("image/png")
                .name("no-disponible.png")
                .build();
    }

    private StreamedContent imagenSeleccionada;

    public void borrar(AyudaImagenGridDTO item) {
        String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        ficheroServiceBean.borrarFicheroAyuda(path, TypeFicheroExterno.AYUDAS_IMAGEN, item.getCodigo());
        data.remove(item);
    }

    public void borrarPerdida(AyudaImagenGridDTO item) {
        String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        ficheroServiceBean.borrarFicheroPerdido(path + "/" + item.getRuta());
        dataPerdidos.remove(item);
    }

    public void cargarImagen(AyudaImagenGridDTO item) {
        if (item != null) {

            String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            if (item.getCodigo() == null) {
                String ruta = path + "/" + item.getRuta();
                byte[] contenido = ficheroServiceBean.getContentByRuta(ruta);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(contenido);
                this.imagenSeleccionada = DefaultStreamedContent.builder()
                        .stream(() -> inputStream)
                        .contentType(getContentType(item.getFilename())) // Cambiar según el tipo de imagen (jpeg, jpg, gif, etc.)
                        .name(item.getFilename())
                        .build();
            } else {
                try {
                    FicheroDTO fichero = ficheroServiceBean.getContentById(item.getCodigo(), path);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(fichero.getContenido());
                    this.imagenSeleccionada = DefaultStreamedContent.builder()
                            .stream(() -> inputStream)
                            .contentType(getContentType(fichero.getFilename())) // Cambiar según el tipo de imagen (jpeg, jpg, gif, etc.)
                            .name(fichero.getFilename())
                            .build();
                } catch (FicheroExternoException e) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTinyMCEIMG.errorSeleccioneUnValor"), true);
                }
            }
        } else {
            this.imagenSeleccionada = null;
        }
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.EDICION);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        result.setResult(request.getContextPath() + "/api/uploads/" + datoSeleccionado.getCodigo());
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.datoSeleccionado)
                || Objects.isNull(this.datoSeleccionado.getRuta())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogTinyMCEIMG.errorSeleccioneUnValor"), true);
            return false;
        }
        return true;
    }

    public void seleccionarUnico(AyudaImagenGridDTO itemSeleccionado) {
        // Deseleccionamos todos los elementos
        for (AyudaImagenGridDTO item : data) {
            item.setSeleccionado(false);
        }

        // Marcamos solo el seleccionado
        itemSeleccionado.setSeleccionado(true);
        this.datoSeleccionado = itemSeleccionado;
    }


    public List<AyudaImagenGridDTO> getData() {
        return data;
    }

    public void setData(List<AyudaImagenGridDTO> data) {
        this.data = data;
    }

    public AyudaImagenGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(AyudaImagenGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public StreamedContent getImagenSeleccionada() {
        return imagenSeleccionada;
    }

    public void setImagenSeleccionada(StreamedContent imagenSeleccionada) {
        this.imagenSeleccionada = imagenSeleccionada;
    }

    public List<AyudaImagenGridDTO> getDataPerdidos() {
        return dataPerdidos;
    }

    public void setDataPerdidos(List<AyudaImagenGridDTO> dataPerdidos) {
        this.dataPerdidos = dataPerdidos;
    }

    public AyudaImagenGridDTO getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(AyudaImagenGridDTO itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }
}
