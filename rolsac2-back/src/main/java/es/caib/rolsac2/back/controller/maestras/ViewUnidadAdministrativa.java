package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilExport;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Constantes;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.*;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.*;

@Named
@ViewScoped
public class ViewUnidadAdministrativa extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewUnidadAdministrativa.class);

    private LazyDataModel<UnidadAdministrativaGridDTO> lazyModel;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaService;

    private UnidadAdministrativaGridDTO datoSeleccionado;

    private UnidadAdministrativaFiltro filtro;

    private boolean mostrarOcultas;

    /**
     * Cuando se exporta los datos
     **/
    private ExportarDatos exportarDatos;

    public LazyDataModel<UnidadAdministrativaGridDTO> getLazyModel() {
        return lazyModel;
    }

    // ACCIONES

    public void load() {
        this.setearIdioma();
        LOG.debug("load");
        permisoAccesoVentana(ViewUnidadAdministrativa.class);
        filtro = new UnidadAdministrativaFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        filtro.setCodEnti(sessionBean.getEntidad().getCodigo());
        mostrarOcultas = false;
        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscarAvanzada() {
        System.out.println();
    }

    public void cambiarUAbuscarEvt(UnidadAdministrativaDTO ua) {
        sessionBean.cambiarUnidadAdministrativa(ua);
        buscarEvt();
    }

    /**
     * El buscar desde el evento de seleccionar una UA.
     */
    public void buscarEvt() {
        if (filtro.getIdUA() == null || filtro.getIdUA().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            buscar();
        }
    }

    public void buscar() {
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        if (mostrarOcultas) {
            filtro.setEstado(null);
        } else {
            filtro.setEstado("V");
        }
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public UnidadAdministrativaGridDTO getRowData(String rowKey) {
                for (UnidadAdministrativaGridDTO pers : (List<UnidadAdministrativaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(UnidadAdministrativaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<UnidadAdministrativaGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<UnidadAdministrativaGridDTO> pagina = unidadAdministrativaService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());

                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<UnidadAdministrativaGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };

    }

    public void nuevaUnidadAdministrativa() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarUnidadAdministrativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            if (datoSeleccionado.isVigente()) {
                abrirVentana(TypeModoAcceso.EDICION);
            } else {
                abrirVentana(TypeModoAcceso.CONSULTA);
            }
        }
    }

    public void evolucionarUnidadAdministrativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentanaEvolucion(TypeModoAcceso.EDICION);
        }
    }

    public void consultarUnidadAdministrativa() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void dobleClickUa() {
        if (isInformador()) {
            this.consultarUnidadAdministrativa();
        } else {
            this.editarUnidadAdministrativa();
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogUnidadAdministrativa", modoAcceso, params, true, 975, 733);
    }

    private void abrirVentanaEvolucion(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionUnidadAdministrativa", modoAcceso, params, true, 775, 440);
    }

    public void borrarUnidadAdministrativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            unidadAdministrativaService.delete(datoSeleccionado.getCodigo());
        }
    }

    /**
     * Imprime el listado de normativas.
     */
    public void exportar() {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.TIPO.toString(), "UA");
        UtilJSF.anyadirMochila("exportar", exportarDatos);
        UtilJSF.openDialog("/maestras/dialogExportar", TypeModoAcceso.ALTA, params, true, 800, 700);
    }


    /**
     * Devuelve el resultado del dialogo de traspaso.
     *
     * @param event
     */
    public void returnDialogoExportar(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            exportarDatos = (ExportarDatos) respuesta.getResult();
        }
    }


    /**
     * Devuelve el fichero
     */
    public StreamedContent getFile() {

        ExportarDatos exportarDatos = this.exportarDatos.clone();

        List<ExportarCampos> campos = new ArrayList<>();
        // Eliminamos los campos no seleccionados
        for (ExportarCampos campo : exportarDatos.getCampos()) {
            if (campo.isSeleccionado()) {
                campos.add(campo);
            }
        }
        exportarDatos.setCampos(campos);

        List<UnidadAdministrativaDTO> uas = unidadAdministrativaService.findExportByFiltro(filtro, exportarDatos);
        String[][] datos = UtilExport.getValoresUAs(uas, exportarDatos, this.getIdioma());
        String[] cabecera = UtilExport.getCabecera(exportarDatos);
        return UtilExport.generarStreamedContent("UNIDAD_ADMINISTRATIVA", cabecera, datos, exportarDatos);
    }

    public StreamedContent getFileOld() {
        List<UnidadAdministrativaDTO> uas = unidadAdministrativaService.findExportByFiltro(filtro, this.exportarDatos);

        StringBuilder sb = new StringBuilder();

        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
            //Si exportamos en formato CSV, añadimos la cabecera
            for (ExportarCampos exp : this.exportarDatos.getCampos()) {
                sb.append(exp.getNombreCampo() + ";");
            }

            //Salto de linea
            sb.append(System.lineSeparator());
        }

        String filename = "datos.txt";
        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
            filename = "uas.csv";
        } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
            filename = "uas.txt";
        }
        for (UnidadAdministrativaDTO ua : uas) {

            if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                sb.append("UNIDAD_ADMINISTRATIVA: " + ua.getCodigo() + System.lineSeparator());
            }

            for (ExportarCampos exp : this.exportarDatos.getCampos()) {
                if (!exp.isSeleccionado()) {
                    continue;
                }

                if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                    sb.append("\t" + getLiteral(exp.getLiteral()) + ": ");
                }

                switch (exp.getCampo()) {
                    case "codigo":
                        sb.append(UtilExport.getValor(ua.getCodigo(), this.getIdioma()));
                        break;
                    case "identificador":
                        sb.append(UtilExport.getValor(ua.getIdentificador(), this.getIdioma()));
                        break;
                    case "codigoDIR3":
                        sb.append(UtilExport.getValor(ua.getCodigoDIR3(), this.getIdioma()));
                        break;
                    case "nombreCat":
                        sb.append(UtilExport.getValor(ua.getNombre(), Constantes.IDIOMA_CATALAN));
                        break;
                    case "nombreEsp":
                        sb.append(UtilExport.getValor(ua.getNombre(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "tipo":
                        if (ua.getTipo() == null) {
                            sb.append("");
                        } else {
                            sb.append(UtilExport.getValor(ua.getTipo().getDescripcion(), Constantes.IDIOMA_ESPANYOL));
                        }
                        break;
                    case "nombrePadre":
                        if (ua.getPadre() == null) {
                            sb.append("");
                        } else {
                            sb.append(UtilExport.getValor(ua.getPadre().getNombre(), Constantes.IDIOMA_ESPANYOL));
                        }
                        break;
                    case "orden":
                        sb.append(UtilExport.getValor(ua.getOrden(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "version":
                        sb.append(UtilExport.getValor(ua.getVersion(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "abreviatura":
                        sb.append(UtilExport.getValor(ua.getAbreviatura(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "url":
                        sb.append(UtilExport.getValor(ua.getUrl(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "presentacion":
                        sb.append(UtilExport.getValor(ua.getPresentacion(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "responsableNombre":
                        sb.append(UtilExport.getValor(ua.getResponsableNombre(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "responsableEmail":
                        sb.append(UtilExport.getValor(ua.getResponsableEmail(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "responsableSexo":
                        if (ua.getResponsableSexo() == null) {
                            sb.append("");
                        } else {
                            sb.append(UtilExport.getValor(ua.getResponsableSexo().getDescripcion(), Constantes.IDIOMA_ESPANYOL));
                        }
                        break;
                    case "contactoTelf":
                        sb.append(UtilExport.getValor(ua.getTelefono(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "contactoFax":
                        sb.append(UtilExport.getValor(ua.getFax(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "contactoEmail":
                        sb.append(UtilExport.getValor(ua.getEmail(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "contactoDominio":
                        sb.append(UtilExport.getValor(ua.getDominio(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    default:
                        break;
                }

                if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
                    sb.append(";");
                }
                if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                    sb.append(System.lineSeparator());
                }
            }
            sb.append(System.lineSeparator());
        }


        String mimeType = URLConnection.guessContentTypeFromName(filename);
        InputStream fis = new ByteArrayInputStream(sb.toString().getBytes());
        StreamedContent file = DefaultStreamedContent.builder().name(filename).contentType(mimeType).stream(() -> fis).build();
        return file;
    }

    public UnidadAdministrativaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(UnidadAdministrativaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public UnidadAdministrativaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(UnidadAdministrativaFiltro filtro) {
        this.filtro = filtro;
    }

    public void setFiltroTexto(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setTexto(texto);
        }
    }

    public String getFiltroTexto() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getTexto();
        }
        return "";
    }

    public boolean isAdmContenidos() {
        return sessionBean.isPerfil(TypePerfiles.ADMINISTRADOR_CONTENIDOS);
    }

    public boolean isMostrarOcultas() {
        return mostrarOcultas;
    }

    public void setMostrarOcultas(boolean mostrarOcultas) {
        this.mostrarOcultas = mostrarOcultas;
    }
}
