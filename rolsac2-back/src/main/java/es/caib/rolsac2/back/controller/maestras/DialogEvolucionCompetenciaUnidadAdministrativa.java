package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class DialogEvolucionCompetenciaUnidadAdministrativa extends EvolucionController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionCompetenciaUnidadAdministrativa.class);

    /**
     * Unidades destino
     **/
    private List<UnidadAdministrativaDTO> selectedUnidades;
    private UnidadAdministrativaDTO uaSeleccionada;
    private Long uaAsociar;

    /**
     * Datos de la UA para dividir
     **/
    private List<NormativaGridDTO> normativas = new ArrayList<>();
    private List<ProcedimientoCompletoDTO> procedimientos;
    private List<ProcedimientoCompletoDTO> servicios;

    private List<NormativaGridDTO> selectedNormativas = new ArrayList<>();
    private List<ProcedimientoCompletoDTO> selectedProcedimientos = new ArrayList<>();
    private List<ProcedimientoCompletoDTO> selectedServicios = new ArrayList<>();

    /**
     * Indica si NO se migran los procedimientos/servicios en reserva
     **/
    private boolean noMigrarReserva = true;
    /**
     * Indica si se migrar todas las UAs
     **/
    private boolean comportamientoTodos = false;
    @EJB
    ProcedimientoServiceFacade procedimientoServiceFacade;

    public void load() {
        LOG.debug("DialogEvolucionDivisionUnidadAdministrativa load");
        this.setearIdioma();
        if (id != null && id.split(",").length > 1) {
            ids = id.split(",");
        }

        if (ids != null && ids.length > 0) {
            data = unidadAdministrativaServiceFacade.findUASimpleByID(Long.valueOf(ids[0]), UtilJSF.getSessionBean().getLang(), null);
        } else if (id != null) {
            data = unidadAdministrativaServiceFacade.findUASimpleByID(Long.valueOf(id), UtilJSF.getSessionBean().getLang(), null);
        }

        uaDestino = new Literal();
        setSelectedUnidades(new ArrayList<UnidadAdministrativaDTO>());

    }

    /**
     * En el flujo se tiene que comprobar: <br />
     * - Que se han seleccionado al menos 2 UAs destino <br />
     * - Que se han asignado las opciones de UA destino a todos los procedimientos, servicios y normativas <br />
     *
     * @param event
     * @return
     */
    public String onFlowProcess(FlowEvent event) {

        if (event.getOldStep().contains("destino") && event.getNewStep().contains("reasignar")) {


            //Comprobamos que se han seleccionado al menos 2 UAs destino
            if (selectedUnidades == null || selectedUnidades.isEmpty() || selectedUnidades.size() == 0) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionCompetenciaUnidadAdministrativa.error.debeSeleccionarMinimo1"), true);
                PrimeFaces.current().executeScript("PF('statusDialog1').hide();");
                return event.getOldStep();
            }

            List<Long> idUA = new ArrayList<>();
            idUA.add(data.getCodigo());
            ProcedimientoFiltro filtroProcedimiento = new ProcedimientoFiltro();
            filtroProcedimiento.setIdUA(data.getCodigo());
            filtroProcedimiento.setIdioma(getIdioma());
            filtroProcedimiento.setTipo(Constantes.PROCEDIMIENTO);
            ExportarDatos exportarDatos = new ExportarDatos(new ArrayList<>());
            exportarDatos.setTodosLosDatos(true);
            procedimientos = procedimientoService.findExportByFiltro(filtroProcedimiento, exportarDatos);
            //procedimientos = unidadAdministrativaServiceFacade.getProcedimientosByUa(data.getCodigo(), Constantes.PROCEDIMIENTO, UtilJSF.getSessionBean().getLang(), null);
            filtroProcedimiento.setTipo(Constantes.SERVICIO);
            servicios = procedimientoService.findExportByFiltro(filtroProcedimiento, exportarDatos);
            //servicios = unidadAdministrativaServiceFacade.getProcedimientosByUa(data.getCodigo(), Constantes.SERVICIO, UtilJSF.getSessionBean().getLang(), null);
            NormativaFiltro filtroNormativas = new NormativaFiltro();
            filtroNormativas.setIdUA(data.getCodigo());
            filtroNormativas.setIdioma(getIdioma());
            filtroNormativas.setPaginaFirst(0);
            filtroNormativas.setPaginaTamanyo(100000);
            //normativas = normativaServiceFacade.findByFiltro(filtroNormativas).getItems();
        }

        if (event.getOldStep().contains("reasignar") && event.getNewStep().contains("finalizar")) {
            //Comprobamos que se han asignado las opciones de UA destino a todos los procedimientos, servicios y normativas
            if (procedimientos != null && !procedimientos.isEmpty()) {
                for (ProcedimientoCompletoDTO procedimiento : procedimientos) {
                    if (procedimiento.getOpcionUAdestino() == null) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionCompetenciaUnidadAdministrativa.error.debeAsignarOpcionProc"), true);
                        PrimeFaces.current().executeScript("PF('statusDialog1').hide();");
                        return event.getOldStep();
                    }
                }
            }

            if (servicios != null && !servicios.isEmpty()) {
                for (ProcedimientoCompletoDTO servicioGridDTO : servicios) {
                    if (servicioGridDTO.getOpcionUAdestino() == null) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionCompetenciaUnidadAdministrativa.error.debeAsignarOpcionServ"), true);
                        PrimeFaces.current().executeScript("PF('statusDialog1').hide();");
                        return event.getOldStep();
                    }
                }
            }

            /*if (normativas != null && !normativas.isEmpty()) {
                for (NormativaGridDTO normativa : normativas) {
                    if (normativa.getOpcionUAdestino() == null) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionCompetenciaUnidadAdministrativa.error.debeAsignarOpcionNorm"), true);
                        PrimeFaces.current().executeScript("PF('statusDialog1').hide();");
                        return event.getOldStep();
                    }
                }
            }*/
        }
        PrimeFaces.current().executeScript("PF('statusDialog1').hide();");
        return event.getNewStep();
    }

    /**
     * Método para reasignar los procedimientos, servicios y normativas a las UAs destino
     */
    public void evolucionar() {

        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        unidadAdministrativaServiceFacade.evolucionDivisionCompetencias(false, this.data.getCodigo(), this.selectedUnidades, this.fechaBaja, this.normativa, this.procedimientos, this.servicios, this.normativas, UtilJSF.getSessionBean().getEntidad(), UtilJSF.getSessionBean().getPerfil(), usuario, noMigrarReserva, comportamientoTodos);

        final DialogResult result = new DialogResult();
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    /**
     * Método para consultar el detalle de una UA
     */
    public void consultarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogUAs(TypeModoAcceso.CONSULTA);
        }
    }

    /**
     * Abrir dialogo de Selección de Unidades Administrativas
     */
    public void abrirDialogUAs(TypeModoAcceso modoAcceso) {
        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", uaSeleccionada.getCodigo().toString());
            UtilJSF.vaciarMochila();
            UtilJSF.openDialog("dialogUnidadAdministrativa", modoAcceso, params, true, 1530, 733);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("unidadesAdministrativas", selectedUnidades);
            final Map<String, String> params = new HashMap<>();
            UtilJSF.vaciarMochila();
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.ALTA.toString());
            // params.put("esCabecera", "true");
            String direccion = "/comun/dialogSeleccionarUA";
            UtilJSF.openDialog(direccion, modoAcceso, params, true, 850, 575);
        }
    }

    /**
     * Método para dar de alta UAs en un usuario
     */
    public void anyadirUAs() {
        abrirDialogUAs(TypeModoAcceso.ALTA);
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (selectedUnidades != null && !selectedUnidades.isEmpty()) {
                for (UnidadAdministrativaDTO ua : selectedUnidades) {
                    if (ua.getCodigo().compareTo(uaSeleccionada.getCodigo()) == 0) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.elementoRepetido"));
                        return;
                    }
                }
            }
            selectedUnidades.add(uaSeleccionada);
        }
    }

    /**
     * Método para borrar un usuario en una UA
     */
    public void borrarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            selectedUnidades.remove(uaSeleccionada);
        }
    }

    public void crearUA() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.MODO_EVOLUCION.toString(), "S");
        //params.put(TypeParametroVentana.MODO_EVOLUCION_UAS.toString(), getUas());
        //UtilJSF.anyadirMochila("ua", uaFusion);
        UtilJSF.openDialog("dialogUnidadAdministrativa", TypeModoAcceso.ALTA, params, true, 975, 733);
    }

    public void editarUA() {
        // Muestra dialogo
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        }
        final Map<String, String> params = new HashMap<>();
        UtilJSF.vaciarMochila();
        TypeModoAcceso modoUA;
        if (uaSeleccionada.getCodigo() < 0) {
            params.put(TypeParametroVentana.MODO_EVOLUCION.toString(), "S");
            UtilJSF.anyadirMochila("ua", uaSeleccionada);
            modoUA = TypeModoAcceso.EDICION;
        } else {
            params.put(TypeParametroVentana.ID.toString(), uaSeleccionada.getCodigo().toString());
            modoUA = TypeModoAcceso.CONSULTA;
        }
        UtilJSF.openDialog("dialogUnidadAdministrativa", modoUA, params, true, 975, 733);
    }

    /**
     * El return dialogo cuando se crea una UA.
     *
     * @param event
     */
    public void returnDialogoCrearUA(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha rellenado los datos
        if (!respuesta.isCanceled()) {
            UnidadAdministrativaDTO ua = ((UnidadAdministrativaDTO) respuesta.getResult());
            if (ua.getCodigo() == null) {
                //Se da por hecho que no tiene código, asi que se le pone uno en negativo para que sepamos que se tiene que crear.
                ua.setCodigo(Calendar.getInstance().getTimeInMillis() * -1);
                selectedUnidades.add(ua);
            }
        }
    }

    /**
     * Asocia las seleccionadas a la UA destino.
     */
    public void asociar() {

        if (selectedProcedimientos != null) {
            for (ProcedimientoCompletoDTO proc : selectedProcedimientos) {
                proc.setOpcionUAdestino(uaAsociar);
            }
        }

        if (selectedServicios != null) {
            for (ProcedimientoCompletoDTO serv : selectedServicios) {
                serv.setOpcionUAdestino(uaAsociar);
            }
        }

        if (selectedNormativas != null) {
            for (NormativaGridDTO norm : selectedNormativas) {
                norm.setOpcionUAdestino(uaAsociar);
            }
        }
        selectedProcedimientos = new ArrayList<>();
        selectedServicios = new ArrayList<>();
        selectedNormativas = new ArrayList<>();
    }

    /**
     * El return dialog cuando se edita una UA (que tiene que tener codigo negativo, es decir, se tiene que crear)
     *
     * @param event
     */
    public void returnDialogoEditarUA(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha rellenado los datos
        if (!respuesta.isCanceled()) {
            UnidadAdministrativaDTO ua = ((UnidadAdministrativaDTO) respuesta.getResult());
            if (ua.getCodigo() < 0) { //Solo con codigo negativo que es pendiente de crear
                for (UnidadAdministrativaDTO unidAdm : selectedUnidades) {
                    if (unidAdm.getCodigo().compareTo(ua.getCodigo()) == 0) {
                        unidAdm = ua;
                    }
                }
            }
        }
    }

    public UnidadAdministrativaDTO getUaSeleccionada() {
        return uaSeleccionada;
    }

    public void setUaSeleccionada(UnidadAdministrativaDTO uaSeleccionada) {
        this.uaSeleccionada = uaSeleccionada;
    }

    public List<UnidadAdministrativaDTO> getSelectedUnidades() {
        if (selectedUnidades != null) {
            int i = 1;
            for (UnidadAdministrativaDTO unidad : selectedUnidades) {
                unidad.setNumero(i++);
            }
        }
        return selectedUnidades;
    }

    public void setSelectedUnidades(List<UnidadAdministrativaDTO> selectedUnidades) {
        this.selectedUnidades = selectedUnidades;
    }

    public List<NormativaGridDTO> getNormativas() {
        return normativas;
    }

    public void setNormativas(List<NormativaGridDTO> normativas) {
        this.normativas = normativas;
    }


    public List<ProcedimientoCompletoDTO> getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(List<ProcedimientoCompletoDTO> procedimientos) {
        this.procedimientos = procedimientos;
    }

    public List<ProcedimientoCompletoDTO> getServicios() {
        return servicios;
    }

    public void setServicios(List<ProcedimientoCompletoDTO> servicios) {
        this.servicios = servicios;
    }

    public List<NormativaGridDTO> getSelectedNormativas() {
        return selectedNormativas;
    }

    public void setSelectedNormativas(List<NormativaGridDTO> selectedNormativas) {
        this.selectedNormativas = selectedNormativas;
    }

    public List<ProcedimientoCompletoDTO> getSelectedProcedimientos() {
        return selectedProcedimientos;
    }

    public void setSelectedProcedimientos(List<ProcedimientoCompletoDTO> selectedProcedimientos) {
        this.selectedProcedimientos = selectedProcedimientos;
    }

    public List<ProcedimientoCompletoDTO> getSelectedServicios() {
        return selectedServicios;
    }

    public void setSelectedServicios(List<ProcedimientoCompletoDTO> selectedServicios) {
        this.selectedServicios = selectedServicios;
    }

    public Long getUaAsociar() {
        return uaAsociar;
    }

    public void setUaAsociar(Long uaAsociar) {
        this.uaAsociar = uaAsociar;
    }

    public boolean isComportamientoTodos() {
        return comportamientoTodos;
    }

    public void setComportamientoTodos(boolean comportamientoTodos) {
        this.comportamientoTodos = comportamientoTodos;
    }
}
