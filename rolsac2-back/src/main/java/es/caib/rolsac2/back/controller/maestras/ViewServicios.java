package es.caib.rolsac2.back.controller.maestras;

//import es.caib.rolsac2.api.externa.v1.model.Servicios;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilExport;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeExportarFormato;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.*;

@Named
@ViewScoped
public class ViewServicios extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ViewServicios.class);

    @EJB
    ProcedimientoServiceFacade procedimientoService;


    @EJB
    UnidadAdministrativaServiceFacade uaService;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    @EJB
    PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;
    private ServicioGridDTO datoSeleccionado;
    private ProcedimientoFiltro filtro;
    private LazyDataModel<ServicioGridDTO> lazyModel;

    private List<TipoProcedimientoDTO> listTipoProcedimiento;
    private List<TipoSilencioAdministrativoDTO> listTipoSilencio;
    private List<TipoPublicoObjetivoDTO> listTipoPublicoObjetivo;
    private List<TipoFormaInicioDTO> listTipoFormaInicio;
    private List<TipoLegitimacionDTO> listTipoLegitimacion;
    private List<TipoTramitacionDTO> listPlantillas;
    private List<PlatTramitElectronicaDTO> listPlataformas;

    private String[] canalesSeleccionados;

    public LazyDataModel<ServicioGridDTO> getLazyModel() {
        return lazyModel;
    }

    /**
     * Cuando se exporta los datos
     **/
    private ExportarDatos exportarDatos;

    public void load() {
        LOG.debug("load View Servicios");
        permisoAccesoVentana(ViewServicios.class);
        this.limpiarFiltro();
        cargarFiltros();
        buscar();
    }

    public void filtroHijasActivasChange() {
        if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            filtro.setIdUAsInstructor(uaService.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        } else if (filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> ids = new ArrayList<>();

            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                List<Long> idsUa = uaService.getListaHijosRecursivo(ua.getCodigo());
                ids.addAll(idsUa);
            }
            filtro.setIdUAsInstructor(ids);
        } else if (!filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> idsUa = new ArrayList<>();
            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                idsUa.add(ua.getCodigo());
            }
            idsUa.add(sessionBean.getUnidadActiva().getCodigo());
            filtro.setIdUAsInstructor(idsUa);
        }
    }

    public void filtroUnidadOrganicasChange() {
        if (filtro.isTodasUnidadesOrganicas()) {
            if (filtro.isHijasActivas()) {
                List<Long> ids = new ArrayList<>();

                for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                    List<Long> idsUa = uaService.getListaHijosRecursivo(ua.getCodigo());
                    ids.addAll(idsUa);
                }
                filtro.setIdUAsInstructor(ids);
            } else {
                List<Long> idsUa = new ArrayList<>();
                for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                    idsUa.add(ua.getCodigo());
                }
                idsUa.add(sessionBean.getUnidadActiva().getCodigo());
                filtro.setIdUAsInstructor(idsUa);
            }
        } else if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            filtro.setIdUAsInstructor(uaService.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        }
    }

    public void limpiarFiltro() {
        filtro = new ProcedimientoFiltro();
        filtro.setIdUAInstructor(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        //filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setTipo("S");
        filtro.setEsProcedimiento(Boolean.FALSE);
        filtro.setOrder("DESCENDING");
        canalesSeleccionados = new String[0];
    }

    private void cargarFiltros() {
        filtro.setEsProcedimiento(Boolean.FALSE);
        listTipoFormaInicio = maestrasSupService.findAllTipoFormaInicio();
        listTipoSilencio = maestrasSupService.findAllTipoSilencio();
        listTipoLegitimacion = maestrasSupService.findAllTipoLegitimacion();
        listTipoProcedimiento = maestrasSupService.findAllTipoProcedimiento(sessionBean.getEntidad().getCodigo());
        listTipoPublicoObjetivo = maestrasSupService.findAllTiposPublicoObjetivo();
        listPlantillas = new ArrayList<>();
        TipoTramitacionDTO plantillaFake = new TipoTramitacionDTO();
        Literal literal = Literal.createInstance();
        List<Traduccion> traduccions = new ArrayList<>();
        traduccions.add(new Traduccion("es", "Ninguna"));
        traduccions.add(new Traduccion("es", "Cap"));
        literal.setTraducciones(traduccions);
        plantillaFake.setCodigo(-1l);
        plantillaFake.setDescripcion(literal);
        listPlantillas.add(plantillaFake);
        listPlantillas.addAll(maestrasSupService.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo(), null));
        listPlataformas = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());
    }

    public void nuevoProcedimiento() {
        abrirVentana(TypeModoAcceso.ALTA, null);
    }

    public void dblClickProcedimiento() {
        if (datoSeleccionado != null) {
            if (datoSeleccionado.getCodigoWFMod() != null && !isInformador()) {
                editarProcedimiento();
            } else if (datoSeleccionado.getCodigoWFPub() != null || isInformador()) {
                consultarProcedimiento();
            }
        }
    }

    public void editarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));
        } else {
            Long idProcMod = this.datoSeleccionado.getCodigoWFMod();
            if (idProcMod == null) {
                PrimeFaces.current().executeScript("PF('cdDeseaCrearEditar').show();");
                return;
            }
            ServicioDTO serv = procedimientoService.findServicioById(idProcMod);
            abrirVentana(TypeModoAcceso.EDICION, serv);

        }
    }

    public void editarProcedimientoSinPreguntar() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));
        } else {
            Long idProcMod = this.datoSeleccionado.getCodigoWFMod();
            if (idProcMod == null) {
                String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
                idProcMod = procedimientoService.generarModificacion(datoSeleccionado.getCodigoWFPub(), usuario, sessionBean.getPerfil());
                this.datoSeleccionado.setCodigoWFMod(idProcMod);
            }
            ServicioDTO serv = procedimientoService.findServicioById(idProcMod);
            abrirVentana(TypeModoAcceso.EDICION, serv);

        }
    }

    public void abrirMensajes(Long codigo) {
        final Map<String, String> params = new HashMap<>();
        String mensajes = procedimientoService.getMensajesByCodigo(codigo);
        UtilJSF.anyadirMochila("mensajes", mensajes);
        params.put("SOLO_MENSAJES", "S");
        params.put("ID", codigo.toString());
        //params.put("ESTADO", data.getEstado().toString());
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void returnDialogMensajes(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();
            procedimientoService.actualizarMensajes(Long.valueOf(respuestaFlujo.getCodigoProcedimiento()), respuestaFlujo.getMensajes(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor());
            procedimientoService.actualizarMensajes(Long.valueOf(respuestaFlujo.getCodigoProcedimiento()), respuestaFlujo.getMensajes(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor());
        }
    }

    public void consultarProcedimiento() {
        if (datoSeleccionado != null) {
            Long idProcPub = datoSeleccionado.getCodigoWFPub();//procedimientoService.getCodigoByWF(datoSeleccionado.getCodigo(), TypeProcedimientoWorkflow.PUBLICADO.getValor());
            if (idProcPub == null) {
                // Mensaje --> No tiene publicado el dato
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewServicios.error.procNoPublicado"), getLiteral("msg.seleccioneElemento"));
            } else {
                ServicioDTO serv = procedimientoService.findServicioById(idProcPub);
                abrirVentana(TypeModoAcceso.CONSULTA, serv);
            }
        }
    }

    public void borrarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            Long idProcMod = datoSeleccionado.getCodigoWFMod();
            Long idProcPub = datoSeleccionado.getCodigoWFPub();
            if (idProcMod != null) {
                //PrimeFaces.current().executeScript("PF('confirmDlgBorrarModificado').show();");
                procedimientoService.deleteWF(idProcMod);
                this.datoSeleccionado.setCodigoWFMod(null);
                ServicioGridDTO proc = this.datoSeleccionado;
                this.buscar();
                this.seleccionarPorId(proc);
            } else if (idProcPub != null) {
                //PrimeFaces.current().executeScript("PF('confirmDlgBorrarPublicado').show();");
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewServicios.error.borrarPublicado"));
            } else {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento") + ".");// UtilJSF.getLiteral("info.borrado.ok"));
            }
        }
    }

    public void borrarProcedimentoMod() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            Long idProcMod = datoSeleccionado.getCodigoWFMod();
            if (idProcMod == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento") + " Modificacion");
            } else {
                procedimientoService.deleteWF(idProcMod);
                ServicioGridDTO proc = this.datoSeleccionado;
                this.buscar();
                this.seleccionarPorId(proc);
            }
        }
    }


    public void borrarProcedimentoPub() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            Long idProcPub = datoSeleccionado.getCodigoWFPub();
            if (idProcPub == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento") + " Modificacion");
            } else {
                procedimientoService.deleteWF(idProcPub);
                ServicioGridDTO proc = this.datoSeleccionado;
                this.buscar();
                this.seleccionarPorId(proc);
            }
        }
    }

    private void seleccionarPorId(ServicioGridDTO idProcSeleccionado) {
        if (idProcSeleccionado == null || this.lazyModel == null) {
            return;
        }

        this.datoSeleccionado = idProcSeleccionado;
    }

    private void abrirVentana(TypeModoAcceso modoAcceso, ServicioDTO serv) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (serv != null) {
            UtilJSF.anyadirMochila("SERV", serv);
        }
        //Integer ancho = sessionBean.getScreenWidthInt();
        //if (ancho == null) {
        //    ancho = 1433;
        //}
        Integer ancho = 1010;
        UtilJSF.openDialog("dialogServicio", modoAcceso, params, true, ancho, 743);
    }

    /**
     * Imprime el listado de normativas.
     */
    public void exportar() {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.TIPO.toString(), "SERV");
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

        List<ProcedimientoCompletoDTO> procedimientos = procedimientoService.findExportByFiltro(filtro, exportarDatos);

        Map<String, String> literalesWF = new HashMap<>();
        literalesWF.put("1", getLiteral("dict.wf.1"));
        literalesWF.put("0", getLiteral("dict.wf.0"));

        Map<String, String> literalesEstado = new HashMap<>();
        literalesEstado.put("M", getLiteral("TypeProcedimientoEstado.M"));
        literalesEstado.put("S", getLiteral("TypeProcedimientoEstado.S"));
        literalesEstado.put("T", getLiteral("TypeProcedimientoEstado.T"));
        literalesEstado.put("U", getLiteral("TypeProcedimientoEstado.U"));
        literalesEstado.put("B", getLiteral("TypeProcedimientoEstado.B"));
        literalesEstado.put("P", getLiteral("TypeProcedimientoEstado.P"));
        literalesEstado.put("R", getLiteral("TypeProcedimientoEstado.R"));

        Map<String, String> literalesEstadoSIA = new HashMap<>();
        literalesEstadoSIA.put("A", getLiteral("dialogProcedimiento.estadoSIA.A"));
        literalesEstadoSIA.put("B", getLiteral("dialogProcedimiento.estadoSIA.B"));
        String[][] datos = UtilExport.getValoresCompletos(procedimientos, exportarDatos, this.getIdioma(), literalesWF, literalesEstado, literalesEstadoSIA);
        String[] cabecera = UtilExport.getCabecera(exportarDatos);
        return UtilExport.generarStreamedContent("Servei", cabecera, datos, exportarDatos);
    }

    /**
     * Devuelve el fichero
     */
    public StreamedContent getFileOld() {

        List<ProcedimientoBaseDTO> servicios = new ArrayList<>(); // procedimientoService.findExportByFiltro(filtro, this.exportarDatos);

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
            filename = "servicios.csv";
        } else if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
            filename = "servicios.txt";
        }
        for (ProcedimientoBaseDTO dato : servicios) {
            ServicioDTO servicioDTO = (ServicioDTO) dato;
            if (this.exportarDatos.getFormato().equals(TypeExportarFormato.TXT)) {
                sb.append("SERVICIO: " + servicioDTO.getCodigo() + System.lineSeparator());
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
                        sb.append(UtilExport.getValor(servicioDTO.getCodigo(), this.getIdioma()));
                        break;
                    case "codigoSIA":
                        sb.append(UtilExport.getValor(servicioDTO.getCodigoSIA(), this.getIdioma()));
                        break;
                    case "estadoSIA":
                        sb.append(UtilExport.getValor(servicioDTO.getEstadoSIA(), this.getIdioma()));
                        break;
                    case "fechaSIA":
                        sb.append(UtilExport.getValor(servicioDTO.getFechaSIA(), this.getIdioma()));
                        break;
                    case "estado":
                        sb.append(UtilExport.getValor(servicioDTO.getEstado(), this.getIdioma()));
                        break;
                    case "visibilidad":
                        sb.append(UtilExport.getValor(servicioDTO.esVisible(), this.getIdioma()));
                        break;
                    /*case "pendienteValidar":
                        sb.append(UtilExport.getValor(servicioDTO.isPendienteIndexar(), this.getIdioma()));
                        break;*/
                    case "nombreCat":
                        sb.append(UtilExport.getValor(servicioDTO.getNombreProcedimientoWorkFlow(), Constantes.IDIOMA_CATALAN));
                        break;
                    case "nombreEsp":
                        sb.append(UtilExport.getValor(servicioDTO.getNombreProcedimientoWorkFlow(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "objetoCat":
                        sb.append(UtilExport.getValor(servicioDTO.getObjeto(), Constantes.IDIOMA_CATALAN));
                        break;
                    case "objetoEsp":
                        sb.append(UtilExport.getValor(servicioDTO.getObjeto(), Constantes.IDIOMA_ESPANYOL));
                        break;
                    case "publicoObjetivo":
                        if (servicioDTO.getPublicosObjetivo() == null || servicioDTO.getPublicosObjetivo().isEmpty()) {
                            sb.append("");
                        } else {
                            String publicoObjetivo = "";
                            for (TipoPublicoObjetivoEntidadGridDTO tipoPublicoObjetivoDTO : servicioDTO.getPublicosObjetivo()) {
                                if (tipoPublicoObjetivoDTO.getDescripcion().getTraduccion(this.getIdioma()) == null) {
                                    publicoObjetivo += tipoPublicoObjetivoDTO.getDescripcion().getTraduccion() + ", ";
                                } else {
                                    publicoObjetivo += tipoPublicoObjetivoDTO.getDescripcion().getTraduccion(this.getIdioma()) + ", ";
                                }
                            }
                            sb.append(UtilExport.getValor(publicoObjetivo, this.getIdioma()));
                        }
                        break;
                    case "unidadAdministrativaInstructora":
                        sb.append(UtilExport.getValor(servicioDTO.getUaInstructor(), this.getIdioma()));
                        break;
                    case "unidadAdministrativaResponsable":
                        sb.append(UtilExport.getValor(servicioDTO.getUaResponsable(), this.getIdioma()));
                        break;
                    case "unidadAdministrativaCompetente":
                        sb.append(UtilExport.getValor(servicioDTO.getUaCompetente(), this.getIdioma()));
                        break;
                    case "responsable":
                        sb.append(UtilExport.getValor(servicioDTO.getResponsable(), this.getIdioma()));
                        break;
                    case "unidadAdministrativaResolutoria":
                        sb.append(UtilExport.getValor(servicioDTO.getUaCompetente(), this.getIdioma()));
                        break;
                    case "numeroNormas":
                        sb.append(UtilExport.getValor(servicioDTO.getNormativas() == null ? 0 : servicioDTO.getNormativas().size(), this.getIdioma()));
                        break;
                    case "fechaActualizacion":
                        sb.append(UtilExport.getValor(servicioDTO.getFechaActualizacion(), this.getIdioma()));
                        break;
                    case "usuarioUltimaActualizacion":
                        sb.append(UtilExport.getValor(servicioDTO.getUsuarioAuditoria(), this.getIdioma()));
                        break;
                    case "comun":
                        sb.append(UtilExport.getValor(servicioDTO.esComun(), this.getIdioma()));
                        break;
                    case "presencial":
                        sb.append(UtilExport.getValor(servicioDTO.isTramitPresencial(), this.getIdioma()));
                        break;
                    case "telematico":
                        sb.append(UtilExport.getValor(servicioDTO.isTramitElectronica(), this.getIdioma()));
                        break;
                    case "telefonico":
                        sb.append(UtilExport.getValor(servicioDTO.isTramitTelefonica(), this.getIdioma()));
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


    public void cambiarUAbuscarEvt(UnidadAdministrativaDTO ua) {
        sessionBean.cambiarUnidadAdministrativa(ua);
        buscarEvt();
    }

    /**
     * El buscar desde el evento de seleccionar una UA.
     */
    public void buscarEvt() {
        if (filtro.isTodasUnidadesOrganicas()) {
            filtroUnidadOrganicasChange();
        } else if (filtro.isHijasActivas()) {
            filtroHijasActivasChange();
        }
        if (filtro.getIdUA() == null || filtro.getIdUA().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            buscar();
        }
    }

    public void buscar() {
        filtro.setIdUAInstructor(sessionBean.getUnidadActiva().getCodigo());
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ServicioGridDTO getRowData(String rowKey) {
                if (getWrappedData() != null) {
                    for (ServicioGridDTO proc : (List<ServicioGridDTO>) getWrappedData()) {
                        if (proc.getCodigo().toString().equals(rowKey)) return proc;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(ServicioGridDTO proc) {
                return proc.getCodigo().toString();
            }

            @Override
            public List<ServicioGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    if (filtro.isHijasActivas() && (filtro.getIdUAsHijas().size() > 1000)) {
                        List<Long> unidadesHijasAux = new ArrayList<>(filtro.getIdUAsHijas());
                        filtro.setIdUAsInstructor(unidadesHijasAux.subList(0, 999));
                        filtro.setIdsUAsHijasAux(unidadesHijasAux.subList(1000, unidadesHijasAux.size() - 1));
                    }
                    if (canalesSeleccionados != null && canalesSeleccionados.length > 0) {
                        filtro.setCanales(Arrays.asList(canalesSeleccionados));
                    }
                    Pagina<ServicioGridDTO> pagina = procedimientoService.findServiciosByFiltro(filtro);

                    //Pagina<ProcedimientoBaseDTO> paginax = procedimientoService.findProcedimientosByFiltroRest(filtro);
                    //new Servicios((ServicioDTO) paginax.getItems().get(0), null, filtro.getIdioma(), true);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<ServicioGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            ServicioGridDTO proc = this.datoSeleccionado;
            this.buscar();
            this.seleccionarPorId(proc);

        }
    }

    public void seleccionarMaterias() {
        UtilJSF.anyadirMochila("materiasSeleccionadas", filtro.getMaterias());
        UtilJSF.openDialog("tipo/dialogSeleccionMateriaSIA", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarNormativas() {
        UtilJSF.anyadirMochila("normativasSeleccionadas", filtro.getNormativas());
        UtilJSF.openDialog("tipo/dialogSeleccionNormativa", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarPubObjetivos() {
        UtilJSF.anyadirMochila("tipoPubObjEntSeleccionadas", filtro.getMaterias());
        UtilJSF.openDialog("dialogSeleccionTipoPublicoObjetivoEntidad", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarTemas() {
        final Map<String, String> params = new HashMap<>();
        params.put("filtrado", "true");
        UtilJSF.anyadirMochila("temas", filtro.getTemas());
        UtilJSF.openDialog("/comun/dialogSeleccionarTemaMultiple", TypeModoAcceso.EDICION, params, true, 1040, 460);
    }

    public void returnDialogMateria(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TipoMateriaSIAGridDTO> materiasSeleccionadas = (List<TipoMateriaSIAGridDTO>) respuesta.getResult();
            if (materiasSeleccionadas == null) {
                filtro.setMaterias(new ArrayList<>());
            } else {
                if (filtro.getMaterias() == null) {
                    filtro.setMaterias(new ArrayList<>());
                }
                filtro.setMaterias(new ArrayList<>());
                filtro.getMaterias().addAll(materiasSeleccionadas);
            }
        }
    }

    public void returnDialogNormativa(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<NormativaGridDTO> normativaG = (List<NormativaGridDTO>) respuesta.getResult();

            if (normativaG == null) {
                filtro.setNormativas(new ArrayList<>());
            } else {
                if (filtro.getNormativas() == null) {
                    filtro.setNormativas(new ArrayList<>());
                }
                filtro.setNormativas(new ArrayList<>());
                filtro.getNormativas().addAll(normativaG);
            }
        }
    }

    public void returnDialogPubObjEnt(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TipoPublicoObjetivoEntidadGridDTO> tipPubObjEntSeleccionadas = (List<TipoPublicoObjetivoEntidadGridDTO>) respuesta.getResult();
            if (tipPubObjEntSeleccionadas == null) {
                filtro.setPublicoObjetivos(new ArrayList<>());
            } else {
                if (filtro.getPublicoObjetivos() == null) {
                    filtro.setPublicoObjetivos(new ArrayList<>());
                }
                filtro.setPublicoObjetivos(new ArrayList<>());
                filtro.getPublicoObjetivos().addAll(tipPubObjEntSeleccionadas);
            }
        }
    }

    public void returnDialogTema(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TemaGridDTO> temas = (List<TemaGridDTO>) respuesta.getResult();

            if (temas == null) {
                filtro.setTemas(new ArrayList<>());
            } else {
                if (filtro.getTemas() == null) {
                    filtro.setTemas(new ArrayList<>());
                }
                filtro.setTemas(new ArrayList<>());
                filtro.getTemas().addAll(temas);
            }
        }
    }

    public void setFiltro(ProcedimientoFiltro filtro) {
        this.filtro = filtro;
    }

    public ProcedimientoFiltro getFiltro() {
        return filtro;
    }

    public void setDatoSeleccionado(ServicioGridDTO dato) {
        this.datoSeleccionado = dato;
    }

    public ServicioGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public List<TipoProcedimientoDTO> getListTipoProcedimiento() {
        return listTipoProcedimiento;
    }

    public void setListTipoProcedimiento(List<TipoProcedimientoDTO> listTipoProcedimiento) {
        this.listTipoProcedimiento = listTipoProcedimiento;
    }

    public List<TipoSilencioAdministrativoDTO> getListTipoSilencio() {
        return listTipoSilencio;
    }

    public void setListTipoSilencio(List<TipoSilencioAdministrativoDTO> listTipoSilencio) {
        this.listTipoSilencio = listTipoSilencio;
    }

    public List<TipoPublicoObjetivoDTO> getListTipoPublicoObjetivo() {
        return listTipoPublicoObjetivo;
    }

    public void setListTipoPublicoObjetivo(List<TipoPublicoObjetivoDTO> listTipoPublicoObjetivo) {
        this.listTipoPublicoObjetivo = listTipoPublicoObjetivo;
    }

    public List<TipoFormaInicioDTO> getListTipoFormaInicio() {
        return listTipoFormaInicio;
    }

    public void setListTipoFormaInicio(List<TipoFormaInicioDTO> listTipoFormaInicio) {
        this.listTipoFormaInicio = listTipoFormaInicio;
    }

    public List<TipoLegitimacionDTO> getListTipoLegitimacion() {
        return listTipoLegitimacion;
    }

    public void setListTipoLegitimacion(List<TipoLegitimacionDTO> listTipoLegitimacion) {
        this.listTipoLegitimacion = listTipoLegitimacion;
    }

    public List<TipoTramitacionDTO> getListPlantillas() {
        return listPlantillas;
    }

    public void setListPlantillas(List<TipoTramitacionDTO> listPlantillas) {
        this.listPlantillas = listPlantillas;
    }

    public List<PlatTramitElectronicaDTO> getListPlataformas() {
        return listPlataformas;
    }

    public void setListPlataformas(List<PlatTramitElectronicaDTO> listPlataformas) {
        this.listPlataformas = listPlataformas;
    }

    public String[] getCanalesSeleccionados() {
        return canalesSeleccionados;
    }

    public void setCanalesSeleccionados(String[] canalesSeleccionados) {
        this.canalesSeleccionados = canalesSeleccionados;
    }
}
