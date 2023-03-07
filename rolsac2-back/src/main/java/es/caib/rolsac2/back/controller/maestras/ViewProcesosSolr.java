package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoSolrDTO;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.solr.ProcedimientoBaseSolr;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class ViewProcesosSolr extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesosSolr.class);

    @EJB
    private ProcesoServiceFacade procesoServiceFacade;

    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private SystemServiceFacade systemServiceFacade;

    private LazyDataModel<ProcedimientoBaseSolr> lazyModel;

    private ProcedimientoBaseSolr datoSeleccionado;

    private ProcesoSolrFiltro filtro;

    public LazyDataModel<ProcedimientoBaseSolr> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesosSolr.class);
        LOG.debug("load");
        filtro = new ProcesoSolrFiltro();
        filtro.setIdioma(sessionBean.getLang());
        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }


    public void buscar() {
        lazyModel = new LazyDataModel<ProcedimientoBaseSolr>() {
            @Override
            public ProcedimientoBaseSolr getRowData(String rowKey) {
                for (ProcedimientoBaseSolr pers : (List<ProcedimientoBaseSolr>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(ProcedimientoBaseSolr pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<ProcedimientoBaseSolr> load(int first, int pageSize, String sortField,
                                                    SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
                    Pagina<ProcedimientoBaseSolr> pagina = procesoServiceFacade.findSolrByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<ProcedimientoBaseSolr> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }

        };
    }

    public void abrirMensaje() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogMensajeSolr", TypeModoAcceso.CONSULTA, params, true, 645, 520);
    }

    public void indexarProcedimientos() {
        final List<ProcedimientoBaseSolr> idProcedimientos = procedimientoServiceFacade.obtenerPendientesIndexar(false, null);
        indexarDatos(idProcedimientos);
    }

    private void indexarDatos(List<ProcedimientoBaseSolr> procedimientos) {

        IPluginIndexacion plugin = (IPluginIndexacion) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.INDEXACION, UtilJSF.getSessionBean().getEntidad().getCodigo());

        if (plugin == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No está especificado el plugin de indexación");
            return;
        }

        if (procedimientos != null) {
            for (ProcedimientoBaseSolr proc : procedimientos) {
                Long codigoWF = procedimientoServiceFacade.getCodigoPublicado(proc.getCodigo());
                boolean publicado = codigoWF != null;
                proc.setFechaIndexacion(new Date());
                if (publicado) {
                    try {
                        boolean todoCorrecto = true;
                        DataIndexacion dato;
                        //Mandar a indexar
                        proc.setMantenerIndexado(false);
                        if (proc.esTipoProcedimiento()) {
                            ProcedimientoSolrDTO procedimiento = procedimientoServiceFacade.findDataIndexacionProcById(codigoWF);
                            ResultadoAccion resultado = plugin.indexarContenido(procedimiento.getDataIndexacion());
                            if (resultado != null && resultado.isCorrecto()) {
                                if (procedimiento.getProcedimientoDTO().getTramites() != null) {
                                    for (ProcedimientoTramiteDTO tramite : procedimiento.getProcedimientoDTO().getTramites()) {
                                        dato = procedimientoServiceFacade.findDataIndexacionTram(tramite, procedimiento.getProcedimientoDTO(), procedimiento.getPathUA());
                                        ResultadoAccion resultadoTramite = plugin.indexarContenido(dato);
                                        if (resultadoTramite != null && !resultadoTramite.isCorrecto()) {
                                            proc.setMensajeError(resultadoTramite.getMensaje());
                                            proc.setMantenerIndexado(false);
                                            todoCorrecto = false;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                proc.setMensajeError(resultado.getMensaje());
                                proc.setMantenerIndexado(false);
                                todoCorrecto = false;
                            }
                        } else {
                            ProcedimientoSolrDTO servicio = procedimientoServiceFacade.findDataIndexacionServById(codigoWF);
                            ResultadoAccion resultado = plugin.indexarContenido(servicio.getDataIndexacion());
                            if (resultado != null && resultado.isCorrecto()) {
                                proc.setMensajeError(resultado.getMensaje());
                                proc.setMantenerIndexado(false);
                                todoCorrecto = true;
                            }
                        }
                        plugin.commit();

                        if (todoCorrecto) {
                            proc.setMensajeError("El procediment s'ha indexat correctament");
                            proc.setMantenerIndexado(false);
                        } else {
                            proc.setMantenerIndexado(true);
                        }
                        procedimientoServiceFacade.actualizarSolr(proc);
                    } catch (Exception e) {
                        proc.setMensajeError(e.getMessage());
                        proc.setMantenerIndexado(true);
                    }
                    procedimientoServiceFacade.actualizarSolr(proc);
                } else {
                    proc.setMensajeError("El procediment no està publicat");
                    proc.setMantenerIndexado(false);
                    procedimientoServiceFacade.actualizarSolr(proc);
                }
            }
        }
    }

    public void indexarPendientes() {
        final List<ProcedimientoBaseSolr> idProcedimientos = procedimientoServiceFacade.obtenerPendientesIndexar(true, null);
        indexarDatos(idProcedimientos);
    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    public void borrarProceso() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            procesoServiceFacade.borrar(datoSeleccionado.getCodigo());
        }
    }

    public ProcedimientoBaseSolr getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(ProcedimientoBaseSolr datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ProcesoSolrFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcesoSolrFiltro filtro) {
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

}
