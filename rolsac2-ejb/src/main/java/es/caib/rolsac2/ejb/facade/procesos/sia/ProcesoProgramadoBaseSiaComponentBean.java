package es.caib.rolsac2.ejb.facade.procesos.sia;

import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Proceso solr.
 *
 * @author Indra
 */

// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public abstract class ProcesoProgramadoBaseSiaComponentBean {

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;
    @Inject
    private ProcesoServiceFacade procesoServiceFacade;
    @Inject
    private NormativaServiceFacade normativaService;


    private int totalProcedimientos = 0;
    private int totalProcedimientosOK = 0;
    private int totalProcedimientosERROR = 0;
    private int totalServicios = 0;
    private int totalServiciosOK = 0;
    private int totalServiciosERROR = 0;

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoBaseSiaComponentBean.class);


    public ResultadoProcesoProgramado ejecutarPadre(final ListaPropiedades params, boolean pendiente, Long idEntidad) {
        log.info("Ejecución proceso SIA");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();


        /*if (params == null || params.getPropiedad("accion") == null || params.getPropiedad("entidad") == null) {
            res.setFinalizadoOk(false);
            detalles.addPropiedad("Informació del procés", "No están bien especificados los parámetros para la indexación");
            res.setDetalles(detalles);
            return res;
        } else {*/

        String accion;
        if (pendiente) {
            accion = "pendientes";
        } else {
            accion = params.getPropiedad("accion");
        }

        detalles.addPropiedades(params);

        Pagina<IndexacionSIADTO> datos = null;

        IPluginSIA plugin = (IPluginSIA) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.SIA, idEntidad);

        if (plugin == null) {
            res.setFinalizadoOk(false);
            detalles.addPropiedad("Informació del procés", "No está especificado el plugin de indexación");
            res.setDetalles(detalles);
            return res;
        }


        ProcesoSIAFiltro filtro = new ProcesoSIAFiltro();
        filtro.setIdEntidad(idEntidad);
        filtro.setPaginaTamanyo(10000);
        filtro.setPaginaFirst(0);
        if (accion.equals(Constantes.INDEXAR_SIA_PENDIENTES)) {
            datos = procesoServiceFacade.findSIAByFiltro(filtro);
        } else if (accion.equals(Constantes.INDEXAR_SIA_COMPLETO)) {
            datos = procedimientoService.getProcedimientosParaIndexacionSIA(idEntidad);
        }


        inicializarTotalesACero();

        if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {

            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaInicio = "La dada de inici es " + sdf.format(new Date());

            for (IndexacionSIADTO dato : datos.getItems()) {
                switch (TypeIndexacion.fromString(dato.getTipo())) {
                    case PROCEDIMIENTO:
                        ResultadoSIA resultadoPro = indexarProcedimiento(dato, plugin);
                        if (dato.getCodigo() != null) {
                            //Si es distinto null, significa que es un dato pendiente
                            procedimientoService.actualizarSIA(dato, resultadoPro);
                        }
                        break;
                    case SERVICIO:
                        totalServicios++;
                        ResultadoSIA resultadoSrv = indexarServicio(dato, plugin);
                        if (resultadoSrv.isCorrecto()) {
                            totalServiciosOK++;
                        } else {
                            totalServiciosERROR++;
                        }
                        if (dato.getCodigo() != null) {
                            //Si es distinto null, significa que es un dato pendiente
                            procedimientoService.actualizarSIA(dato, resultadoSrv);
                        }
                        break;
                }

            }


            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            res.setFinalizadoOk(true);
            detalles.addPropiedad("Informació del procés", fechaInicio);
            if (totalProcedimientos > 0) {
                detalles.addPropiedad("Procediments", "S'ha indexat " + totalProcedimientos + " (correctes:" + totalProcedimientosOK + " , error:" + totalProcedimientosERROR + ")");
            }
            if (totalServicios > 0) {
                detalles.addPropiedad("Serveis", "S'ha indexat " + totalServicios + " (correctes:" + totalServiciosOK + " , error:" + totalServiciosERROR + ")");
            }
            detalles.addPropiedad("Fin del procés", fechaFin);


            res.setDetalles(detalles);
        } else {

            res.setFinalizadoOk(true);
            detalles.addPropiedad("Informació del procés", "Sense dades per a indexar");
            res.setDetalles(detalles);

        }
        res.setDetalles(detalles);
        return res;
    }


    private ResultadoSIA indexarServicio(IndexacionSIADTO indexacionDTO, IPluginSIA plugin) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        totalServicios++;

        EntidadRaizDTO entidadRaiz = null;

        if (indexacionDTO.getExiste().compareTo(SiaUtils.SIAPENDIENTE_PROCEDIMIENTO_BORRADO) == 0) {
            return borradoSIA(indexacionDTO, plugin, entidadRaiz);
        } else {
            if (publicado) {
                try {
                    ServicioDTO servicioDTO = procedimientoService.findServicioById(indexacionDTO.getCodElemento());
                    SiaEnviableResultado esEnviable = SiaUtils.isEnviable(uaService, servicioDTO);
                    EntidadRaizDTO siaUA = null;
                    if (esEnviable.isNotificiarSIA()) {
                        final SiaCumpleDatos siaCumpleDatos = SiaUtils.cumpleDatos(uaService, servicioDTO, esEnviable, true, siaUA);
                        //Si es común, no se indexa
                        if (siaCumpleDatos.isCumpleDatos()) {

                            EnvioSIA envioSIA = SiaUtils.cast(uaService, servicioDTO, esEnviable, siaCumpleDatos);
                            ResultadoSIA resultadoSIA = plugin.enviarSIA(envioSIA, false, false);
                            if (resultadoSIA != null && resultadoSIA.isCorrecto()) {
                                totalServiciosOK++;
                                new ResultadoSIA(1, "El procediment s'ha indexat correctament");
                            } else {
                                totalServiciosERROR++;
                                new ResultadoSIA(0, "El procediment s'ha indexat incorrectamente " + resultadoSIA.getMensaje().toString());
                            }
                        } else {
                            totalServiciosOK++;
                            // Guardamos un SIA Pendiente como que no cumple datos (ultima pestaña)
                            final ResultadoSIA siaPendiente = new ResultadoSIA();
                            siaPendiente.setCorrectos(1);
                            siaPendiente.setMensaje("No cumple los datos");
                            siaPendiente.setResultado(ResultadoSIA.RESULTADO_NO_CUMPLE_DATOS);
                            return siaPendiente;
                        }
                    } else {
                        totalServiciosOK++;
                        return new ResultadoSIA(ResultadoSIA.RESULTADO_NO_ENVIABLE, esEnviable.getRespuesta());
                    }
                } catch (Exception e) {
                    totalServiciosERROR++;
                    return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, e.getMessage());
                }
            } else {
                totalServiciosOK++;
                return new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El procediment no està publicat");
            }
        }

        return null;
    }

    private ResultadoSIA indexarProcedimiento(IndexacionSIADTO indexacionDTO, IPluginSIA plugin) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        totalProcedimientos++;

        EntidadRaizDTO entidadRaiz = null;

        if (indexacionDTO.getExiste().compareTo(SiaUtils.SIAPENDIENTE_PROCEDIMIENTO_BORRADO) == 0) {
            return borradoSIA(indexacionDTO, plugin, entidadRaiz);
        } else {
            if (publicado) {
                try {
                    ProcedimientoDTO procedimientoDTO = procedimientoService.findProcedimientoById(codigoWF);
                    SiaEnviableResultado esEnviable = SiaUtils.isEnviable(uaService, procedimientoDTO);
                    EntidadRaizDTO siaUA = uaService.getUaRaiz(procedimientoDTO.getUaInstructor().getCodigo());
                    if (esEnviable.isNotificiarSIA()) {
                        final SiaCumpleDatos siaCumpleDatos = SiaUtils.cumpleDatos(uaService, procedimientoDTO, esEnviable, true, siaUA);
                        //Si es común, no se indexa
                        if (siaCumpleDatos.isCumpleDatos()) {

                            EnvioSIA envioSIA = SiaUtils.cast(uaService, procedimientoDTO, esEnviable, siaCumpleDatos);
                            ResultadoSIA resultadoSIA = plugin.enviarSIA(envioSIA, false, false);
                            if (resultadoSIA != null && resultadoSIA.isCorrecto()) {
                                totalProcedimientosOK++;
                                new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El procediment s'ha indexat correctament");
                            } else {
                                totalProcedimientosERROR++;
                                new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, "El procediment s'ha indexat incorrectamente " + resultadoSIA.toString());
                            }
                        } else {
                            totalProcedimientosOK++;
                            // Guardamos un SIA Pendiente como que no cumple datos (ultima pestaña)
                            final ResultadoSIA siaPendiente = new ResultadoSIA();
                            siaPendiente.setCorrectos(1);
                            siaPendiente.setMensaje("No cumple los datos");
                            siaPendiente.setResultado(ResultadoSIA.RESULTADO_NO_CUMPLE_DATOS);
                            return siaPendiente;
                        }
                    } else {
                        return new ResultadoSIA(ResultadoSIA.RESULTADO_NO_ENVIABLE, esEnviable.getRespuesta());
                    }
                } catch (Exception e) {
                    totalProcedimientosERROR++;
                    return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, e.getMessage());
                }
            } else {
                totalProcedimientosOK++;
                return new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El procediment no està publicat");
            }
        }

        return null;
    }


    private void inicializarTotalesACero() {
        totalProcedimientos = 0;
        totalProcedimientosOK = 0;
        totalProcedimientosERROR = 0;
        totalServicios = 0;
        totalServiciosOK = 0;
        totalServiciosERROR = 0;
    }


    private ResultadoSIA borradoSIA(IndexacionSIADTO indexacionDTO, IPluginSIA plugin, EntidadRaizDTO entidadRaiz) {
        EnvioSIA sia = new EnvioSIA();
        sia.setIdSia(indexacionDTO.getCodigoSIA().toString());
        sia.setOperacion(SiaUtils.ESTADO_BAJA);
        //sia.setIdElemento(String.valueOf(idProc));

        if (entidadRaiz != null) {
            sia.setUsuario(entidadRaiz.getUser());
            sia.setPassword(entidadRaiz.getPwd());
        }

        ResultadoSIA resultado = null;
        try {
            resultado = plugin.enviarSIA(sia, true, false);
        } catch (final Exception exception) {
            log.error("Se ha producido un error enviando el dato a SIA de un borrado de proc/serv " + indexacionDTO.getCodElemento(), exception);
           /* throw new Exception(
                    "Se ha producido un error enviando el dato a SIA de un borrado de procedimiento " + idProc,
                    exception);*/
        }
        return resultado;
    }
}
