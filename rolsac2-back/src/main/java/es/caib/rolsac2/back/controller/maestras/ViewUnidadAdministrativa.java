package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilExport;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
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
            public String getRowKey(UnidadAdministrativaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            /*
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
            }*/

            public int count(Map<String, FilterMeta> filterBy) {
                return unidadAdministrativaService.countByFiltro(filtro);
            }

            @Override
            public List<UnidadAdministrativaGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    return unidadAdministrativaService.findPagedByFiltro(filtro);
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

    /**
     * El return dialog.
     *
     * @return
     */
    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    /**
     * Abre la ventana de unidad administrativa
     *
     * @param modoAcceso
     */
    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogUnidadAdministrativa", modoAcceso, params, true, 975, 733);
    }

    /**
     * Abre la ventana de evolución de la unidad administrativa
     *
     * @param modoAcceso
     */
    private void abrirVentanaEvolucion(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionUnidadAdministrativa", modoAcceso, params, true, 775, 440);
    }

    /**
     * Borra una unidad administrativa
     */
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
        return UtilExport.generarStreamedContent("UnitatAdministrativa", cabecera, datos, exportarDatos);
    }

    /**
     * Devuelve el dato seleccionado
     *
     * @return
     */
    public UnidadAdministrativaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    /**
     * Setea el dato seleccionado
     *
     * @return
     */
    public void setDatoSeleccionado(UnidadAdministrativaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    /**
     * Devuelve el filtro
     *
     * @return
     */
    public UnidadAdministrativaFiltro getFiltro() {
        return filtro;
    }

    /**
     * Setea el filtro
     *
     * @return
     */
    public void setFiltro(UnidadAdministrativaFiltro filtro) {
        this.filtro = filtro;
    }

    /**
     * Setea el filtro texto
     *
     * @return
     */
    public void setFiltroTexto(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setTexto(texto);
        }
    }

    /**
     * Devuelve el filtro texto
     *
     * @return
     */
    public String getFiltroTexto() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getTexto();
        }
        return "";
    }

    /**
     * Devuelve si es adm contenido
     *
     * @return
     */
    public boolean isAdmContenidos() {
        return sessionBean.isPerfil(TypePerfiles.ADMINISTRADOR_CONTENIDOS);
    }

    /**
     * Devuelve el valor de mostrarOcultas
     *
     * @return
     */
    public boolean isMostrarOcultas() {
        return mostrarOcultas;
    }

    /**
     * Setear el valor de mostrarOcultas
     *
     * @return
     */
    public void setMostrarOcultas(boolean mostrarOcultas) {
        this.mostrarOcultas = mostrarOcultas;
    }
}
