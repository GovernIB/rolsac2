package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilExport;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypeExportarFormato;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.List;
import java.util.*;

@Named
@ViewScoped
public class ViewNormativa extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewNormativa.class);

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    private LazyDataModel<NormativaGridDTO> lazyModel;

    private NormativaGridDTO datoSeleccionado;

    private NormativaFiltro filtro;

    private List<TipoNormativaDTO> listTipoNormativa;

    private List<TipoBoletinDTO> listTipoBoletin;

    private Boolean isTraspaso = false;
    private boolean mostrarOcultas = false;

    /**
     * Cuando se exporta los datos
     **/
    private ExportarDatos exportarDatos;

    public LazyDataModel<NormativaGridDTO> getLazyModel() {
        return lazyModel;
    }

    /**
     * Método al inicializar la ventana.
     */
    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        permisoAccesoVentana(ViewNormativa.class);
        limpiarFiltro();
        cargarFiltros();
        // Generamos una búsqueda
        buscar();
    }

    /**
     * Actualiza la lista de normativas.
     */
    public void update() {
        buscar();
    }

    /**
     * Cambia la unidad administrativa a buscar.
     *
     * @param ua
     */
    public void cambiarUAbuscarEvt(UnidadAdministrativaDTO ua) {
        sessionBean.cambiarUnidadAdministrativa(ua);
        buscarEvt();
    }

    /**
     * Cambia el filtro de unidades orgánicas hijas.
     */
    public void filtroHijasActivasChange() {
        if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            filtro.setIdUAsHijas(unidadAdministrativaServiceFacade.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        } else if (filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> ids = new ArrayList<>();

            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                List<Long> idsUa = unidadAdministrativaServiceFacade.getListaHijosRecursivo(ua.getCodigo());
                ids.addAll(idsUa);
            }
            filtro.setIdUAsHijas(ids);
        } else if (!filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> idsUa = new ArrayList<>();
            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                idsUa.add(ua.getCodigo());
            }
            idsUa.add(sessionBean.getUnidadActiva().getCodigo());
            filtro.setIdUAsHijas(idsUa);
        }
    }

    /**
     * Cambia el filtro de unidades orgánicas.
     */
    public void filtroUnidadOrganicasChange() {
        if (filtro.isTodasUnidadesOrganicas()) {
            if (filtro.isHijasActivas()) {
                List<Long> ids = new ArrayList<>();

                for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                    List<Long> idsUa = unidadAdministrativaServiceFacade.getListaHijosRecursivo(ua.getCodigo());
                    ids.addAll(idsUa);
                }
                filtro.setIdUAsHijas(ids);
            } else {
                List<Long> idsUa = new ArrayList<>();
                for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                    idsUa.add(ua.getCodigo());
                }
                idsUa.add(sessionBean.getUnidadActiva().getCodigo());
                filtro.setIdUAsHijas(idsUa);
            }
        } else if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            filtro.setIdUAsHijas(unidadAdministrativaServiceFacade.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        }
    }

    /**
     * Limpia el filtro.
     */
    public void limpiarFiltro() {
        filtro = new NormativaFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setOrder("DESCENDING");
        this.buscar();
    }

    /**
     * El buscar desde el evento de seleccionar una UA.
     */
    public void buscarEvt() {
        if (filtro.getIdUA() == null || filtro.getIdUA().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            buscar();
        }
    }

    /**
     * Busca las normativas.
     */
    public void buscar() {

        lazyModel = new LazyDataModel<NormativaGridDTO>() {
            @Override
            public NormativaGridDTO getRowData(String rowKey) {
                for (NormativaGridDTO pers : (List<NormativaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(NormativaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<NormativaGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    if (filtro.isHijasActivas() && (filtro.getIdUAsHijas().size() > 1000)) {
                        List<Long> unidadesHijasAux = new ArrayList<>(filtro.getIdUAsHijas());
                        filtro.setIdUAsHijas(unidadesHijasAux.subList(0, 999));
                        filtro.setIdsUAsHijasAux(unidadesHijasAux.subList(1000, unidadesHijasAux.size() - 1));
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<NormativaGridDTO> pagina = normativaServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<NormativaGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }

        };
    }

    /**
     * Abre la ventana de normativa en modo alta.
     */
    public void nuevaNormativa() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    /**
     * Abre la ventana de normativa en modo edición.
     */
    public void editarNormativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    /**
     * Abre la ventana de normativa en modo consulta.
     */
    public void consultarNormativa() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    /**
     * Abre la ventana de normativa a partir de un doble click y su perfil. Si es informador, se abre en modo consulta.
     */
    public void dobleClickNormativa() {
        if (isInformador()) {
            this.consultarNormativa();
        } else {
            this.editarNormativa();
        }
    }

    /**
     * Borra la normativa seleccionada.
     */
    public void borrarNormativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            boolean existen = normativaServiceFacade.existeProcedimientoConNormativa(datoSeleccionado.getCodigo());
            if (existen) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.error.relacionProcedimientos"));
            } else {
                normativaServiceFacade.delete(datoSeleccionado.getCodigo());
            }
        }
    }

    /**
     * Devuelve el resultado del dialogo.
     *
     * @param event
     */
    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        isTraspaso = false;
        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    /**
     * Abre la ventana de normativa.
     *
     * @param modoAcceso
     */
    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        if (modoAcceso == TypeModoAcceso.ALTA && isTraspaso == true) {
            params.put("isTraspaso", isTraspaso.toString());
        }
        UtilJSF.openDialog("dialogNormativa", modoAcceso, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 400), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
    }

    /**
     * Abre el dialogo de traspaso.
     */
    public void abrirTraspaso() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogTraspasoBOIB", TypeModoAcceso.ALTA, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 400), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
    }

    /**
     * Devuelve el resultado del dialogo de traspaso.
     *
     * @param event
     */
    public void returnDialogoTraspaso(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            NormativaDTO normativaDTO = (NormativaDTO) respuesta.getResult();
            UtilJSF.anyadirMochila("normativaBOIB", normativaDTO);
            isTraspaso = true;
            PrimeFaces.current().executeScript("triggerNuevaNormativa()");
        }
    }

    /**
     * Imprime el listado de normativas.
     */
    public void exportar() {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.TIPO.toString(), "NORMATIVA");
        UtilJSF.anyadirMochila("exportar", exportarDatos);
        UtilJSF.openDialog("dialogExportar", TypeModoAcceso.ALTA, params, true, 800, 700);
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

        List<NormativaDTO> normativas = normativaServiceFacade.findExportByFiltro(filtro, exportarDatos);
        String[][] datos = UtilExport.getValoresNormativas(normativas, exportarDatos, this.getIdioma());
        String[] cabecera = UtilExport.getCabecera(exportarDatos);
        return UtilExport.generarStreamedContent("NORMATIVA", cabecera, datos, exportarDatos);
        /*
        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV) || this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT) || this.exportarDatos.getFormato().equals(TypeExportarFormato.XLS)) {
            return getFileCSVTXT();
        } else {
            return getFilePDF();
        }*/
    }

    public StreamedContent getFilePDF() {
        try {

            List<NormativaDTO> normativas = normativaServiceFacade.findExportByFiltro(filtro, this.exportarDatos);


            // Create a document and add a page to it
            PDDocument document = new PDDocument();

            PDPage page = new PDPage(PDRectangle.A4);
            // PDRectangle.LETTER and others are also possible
            PDRectangle rect = page.getMediaBox();
            // rect can be used to get the page width and height
            document.addPage(page);

            int pageWidth = (int) page.getTrimBox().getWidth(); //get width of the page
            int pageHeight = (int) page.getTrimBox().getHeight(); //get height of the page


            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setStrokingColor(Color.DARK_GRAY);
            contentStream.setLineWidth(1);

            int initX = 50;
            int initY = pageHeight - 50;
            int cellHeight = 20;
            int cellWidth = 100;

            int colCount = 3;
            int rowCount = 3;

            int i = 1;
            int j = 1;
            for (ExportarCampos exp : this.exportarDatos.getCampos()) {
                contentStream.addRect(initX, initY, cellWidth + 30, -cellHeight);

                contentStream.beginText();
                contentStream.newLineAtOffset(initX + 30, initY - cellHeight + 10);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                contentStream.showText(exp.getNombreCampo());
                contentStream.endText();
                initX += cellWidth + 30;
                j++;
            }


            for (i = 1; i <= this.exportarDatos.getCampos().size(); i++) {
                for (j = 2; j < normativas.size(); j++) {

                    contentStream.addRect(initX, initY, cellWidth, -cellHeight);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(initX + 10, initY - cellHeight + 10);
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                    contentStream.showText("Dinukaxxxxxxx");
                    contentStream.endText();

                    initX += cellWidth;

                }
                initX = 50;
                initY -= cellHeight;
            }

            contentStream.stroke();
            contentStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);

            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            StreamedContent streamedContent = new DefaultStreamedContent(inputStream, "application/pdf", "normativas.pdf");
            return streamedContent;

        } catch (Exception e) {
            return null;
        }
    }

    public StreamedContent getFileCSVTXT() {

        StringBuilder sb = new StringBuilder();
        List<NormativaDTO> normativas = normativaServiceFacade.findExportByFiltro(filtro, this.exportarDatos);

        XSSFWorkbook workbook = null;
        XSSFSheet spreadsheet = null;


        String filename = "datos.txt";
        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
            filename = "normativas.csv";
        } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
            filename = "normativas.txt";
        } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.XLS)) {
            filename = "normativas.xlsx";
            workbook = new XSSFWorkbook();
            spreadsheet = workbook.createSheet("normativas");
        }

        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
            //Si exportamos en formato CSV, añadimos la cabecera
            for (ExportarCampos exp : this.exportarDatos.getCampos()) {
                sb.append(exp.getNombreCampo() + ";");
            }

            //Salto de fila
            sb.append(System.lineSeparator());
        }

        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.XLS)) {
            //Si exportamos en formato XLS, añadimos la cabecera
            XSSFRow row = spreadsheet.createRow((short) 1);
            int i = 1;
            for (ExportarCampos exp : this.exportarDatos.getCampos()) {
                row.createCell(i).setCellValue(exp.getNombreCampo());
                i++;
            }
        }


        int fila = 2;
        for (NormativaDTO normativa : normativas) {

            //Variable para XLS
            int columna = 1;
            XSSFRow row = spreadsheet.createRow((short) fila);

            if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                sb.append("NORMATIVA: " + normativa.getCodigo() + System.lineSeparator());
            }

            for (ExportarCampos exp : this.exportarDatos.getCampos()) {

                if (!exp.isSeleccionado()) {
                    continue;
                }

                StringBuilder texto = new StringBuilder();
                if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                    texto.append("\t" + getLiteral(exp.getLiteral()) + ": ");
                }

                switch (exp.getCampo()) {
                    case "codigo":
                        texto.append(UtilExport.getValor(normativa.getCodigo(), this.getIdioma()));
                        break;
                    case "normaCat":
                        texto.append(UtilExport.getValor(normativa.getTitulo(), Constantes.IDIOMA_CATALAN));
                        break;
                    case "normaEsp":
                        texto.append(UtilExport.getValor(normativa.getTitulo(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "enlaceCat":
                        texto.append(UtilExport.getValor(normativa.getUrlBoletin(), Constantes.IDIOMA_CATALAN));
                        break;
                    case "enlaceEsp":
                        texto.append(UtilExport.getValor(normativa.getUrlBoletin(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "responsableCat":
                        texto.append(UtilExport.getValor(normativa.getNombreResponsable(), Constantes.IDIOMA_CATALAN));
                        break;
                    case "responsableEsp":
                        texto.append(UtilExport.getValor(normativa.getNombreResponsable(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "estado":
                        texto.append(normativa.getVigente() ? "VIGENT" : "NO VIGENT");
                        break;
                    case "rangoLegal":
                        texto.append(""); //normativa.getRangoLegal());
                        break;
                    case "tipoBoletin":
                        if (normativa.getBoletinOficial() == null) {
                            texto.append("");
                        } else {
                            texto.append(UtilExport.getValor(normativa.getBoletinOficial().getNombre(), this.getIdioma()));
                        }
                        break;
                    case "numeroBoletin":
                        texto.append(UtilExport.getValor(normativa.getNumeroBoletin(), this.getIdioma()));
                        break;
                    case "numero":
                        texto.append(UtilExport.getValor(normativa.getNumero(), this.getIdioma()));
                        break;
                    case "enlace":
                        texto.append(UtilExport.getValor(normativa.getUrlBoletin(), this.getIdioma()));
                        break;
                    case "fechaAprobacion":
                        texto.append(UtilExport.getValor(normativa.getFechaAprobacion(), this.getIdioma()));
                        break;
                    case "fechaBoletin":
                        texto.append(UtilExport.getValor(normativa.getFechaBoletin(), this.getIdioma()));
                        break;
                    case "tipoNormativa":
                        if (normativa.getTipoNormativa() == null) {
                            texto.append("");
                        } else {
                            texto.append(UtilExport.getValor(normativa.getTipoNormativa().getDescripcion(), this.getIdioma()));
                        }
                        break;
                    default:
                        break;
                }

                if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV)) {
                    texto.append(";");
                    sb.append(texto.toString());
                } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                    texto.append(System.lineSeparator());
                    sb.append(texto.toString());
                } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.XLS)) {
                    if (texto == null || texto.toString() == null) {
                        texto = new StringBuilder(" ");
                    }
                    row.createCell(columna).setCellValue(texto.toString());
                    columna++;
                }
                columna++; //Para el XLS, aumentamos la columna de la columna
            }
            fila++; //Para el xls, aumentamos la columna de la fila
            sb.append(System.lineSeparator());
        }


        if (this.exportarDatos.getFormato().equals(TypeExportarFormato.CSV) || this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
            String mimeType = URLConnection.guessContentTypeFromName(filename);
            InputStream fis = new ByteArrayInputStream(sb.toString().getBytes());
            StreamedContent file = DefaultStreamedContent.builder().name(filename).contentType(mimeType).stream(() -> fis).build();
            return file;
        } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.XLS)) {

            try {

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);

                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                StreamedContent streamedContent = new DefaultStreamedContent(inputStream, "application/xls", filename);
                return streamedContent;


            } catch (Exception e) {
                return null;
            }
        }

        return null;

    }

    /**
     * Carga los filtros de la ventana.
     */
    private void cargarFiltros() {
        listTipoBoletin = maestrasSupServiceFacade.findBoletines();
        listTipoNormativa = maestrasSupServiceFacade.findTipoNormativa();
    }

    public NormativaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(NormativaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public NormativaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(NormativaFiltro filtro) {
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

    public Boolean isTraspaso() {
        return isTraspaso;
    }

    public void setTraspaso(Boolean traspaso) {
        isTraspaso = traspaso;
    }

    public List<TipoNormativaDTO> getListTipoNormativa() {
        return listTipoNormativa;
    }

    public void setListTipoNormativa(List<TipoNormativaDTO> listTipoNormativa) {
        this.listTipoNormativa = listTipoNormativa;
    }

    public List<TipoBoletinDTO> getListTipoBoletin() {
        return listTipoBoletin;
    }

    public void setListTipoBoletin(List<TipoBoletinDTO> listTipoBoletin) {
        this.listTipoBoletin = listTipoBoletin;
    }

    public boolean isMostrarOcultas() {
        return mostrarOcultas;
    }

    public void setMostrarOcultas(boolean mostrarOcultas) {
        this.mostrarOcultas = mostrarOcultas;
    }
}
