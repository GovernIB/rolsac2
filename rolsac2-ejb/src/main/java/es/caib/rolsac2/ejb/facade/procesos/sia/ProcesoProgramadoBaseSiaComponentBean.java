package es.caib.rolsac2.ejb.facade.procesos.sia;

import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public ResultadoProcesoProgramado ejecutarPadre(final Long instanciaProceso, final ListaPropiedades params, boolean pendiente, Long idEntidad) {
        log.info("Ejecución proceso SIA");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaInicio = "La dada de inici es " + sdf.format(new Date());
        detalles.addPropiedad("Informació del procés", fechaInicio);

        try {
            String accion;
            if (pendiente) {
                accion = "pendientes";
            } else {
                accion = params.getPropiedad("accion");
            }

            String id = null;
            String tipo = null;
            if (accion.equals(Constantes.INDEXAR_SIA_PROCEDIMIENTO_PUNTUAL)) {
                id = params.getPropiedad("id");
                tipo = params.getPropiedad("tipo");
            }
            detalles.addPropiedades(params);

            Pagina<IndexacionSIADTO> datos = null;

            IPluginSIA plugin = null;

            try {
                plugin = (IPluginSIA) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.SIA, idEntidad);
            } catch (Exception e) {
                res.setFinalizadoOk(false);
                detalles.addPropiedad("Informació del procés", "Error obteniendo plugin de indexacion.");
                detalles.addPropiedad("Error", e.getLocalizedMessage());
                res.setDetalles(detalles);
                return res;
            }

            if (plugin == null) {
                res.setFinalizadoOk(false);
                detalles.addPropiedad("Informació del procés", "No está especificado el plugin de indexación");
                res.setDetalles(detalles);
                return res;
            }


            boolean puntual = false;
            ProcesoSIAFiltro filtro = new ProcesoSIAFiltro();
            filtro.setIdEntidad(idEntidad);
            filtro.setPaginaTamanyo(10000);
            filtro.setPaginaFirst(0);
            if (accion.equals(Constantes.INDEXAR_SIA_PENDIENTES)) {
                datos = procesoServiceFacade.findSIAByFiltro(filtro);
            } else if (accion.equals(Constantes.INDEXAR_SIA_COMPLETO)) {
                datos = procedimientoService.getProcedimientosParaIndexacionSIA(idEntidad);
            } else if (accion.equals(Constantes.INDEXAR_SIA_PROCEDIMIENTO_PUNTUAL)) {
                if (id == null || tipo == null) {
                    res.setFinalizadoOk(false);
                    detalles.addPropiedad("Informació del procés", "El procediment no té codi per a indexar");
                    res.setDetalles(detalles);
                    return res;
                }
                ProcedimientoBaseDTO procedimiento;
                Long codigoWF = procedimientoService.getCodigoByWF(Long.valueOf(id), Constantes.PROCEDIMIENTO_DEFINITIVO);
                if (codigoWF == null) {
                    codigoWF = procedimientoService.getCodigoByWF(Long.valueOf(id), Constantes.PROCEDIMIENTO_ENMODIFICACION);
                }
                if (tipo.equals("P")) {
                    procedimiento = procedimientoService.findProcedimientoById(codigoWF);
                } else {
                    procedimiento = procedimientoService.findServicioById(codigoWF);
                }
                if (procedimiento.getCodigoSIA() != null) {
                    res.setFinalizadoOk(false);
                    detalles.addPropiedad("Informació del procés", "El procediment ja té codi SIA");
                    res.setDetalles(detalles);
                    return res;
                }


                IndexacionSIADTO indexacionSIADTO = new IndexacionSIADTO();
                indexacionSIADTO.setCodElemento(Long.valueOf(id));
                indexacionSIADTO.setExiste(SiaUtils.SIAPENDIENTE_PROCEDIMIENTO_EXISTE);
                if (procedimiento instanceof ProcedimientoDTO) {
                    indexacionSIADTO.setTipo(TypeIndexacion.PROCEDIMIENTO.toString());
                } else {
                    indexacionSIADTO.setTipo(TypeIndexacion.SERVICIO.toString());
                }
                List<IndexacionSIADTO> lista = new ArrayList<>();
                lista.add(indexacionSIADTO);
                datos = new Pagina(lista, 1);
                puntual = true;

            }


            inicializarTotalesACero();
            StringBuilder mensajeTraza = new StringBuilder();

            if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {


                for (IndexacionSIADTO dato : datos.getItems()) {
                    switch (TypeIndexacion.fromString(dato.getTipo())) {
                        case PROCEDIMIENTO:
                            ResultadoSIA resultadoPro = indexarProcedimiento(dato, plugin, mensajeTraza, puntual);

                            //Si es distinto null, significa que es un dato pendiente
                            procedimientoService.actualizarSIA(dato, resultadoPro);

                            break;
                        case SERVICIO:
                            totalServicios++;
                            ResultadoSIA resultadoSrv = indexarServicio(dato, plugin, mensajeTraza, puntual);

                            //Si es distinto null, significa que es un dato pendiente
                            procedimientoService.actualizarSIA(dato, resultadoSrv);
                            break;
                    }

                }


                String fechaFin = "La dada de fi es " + sdf.format(new Date());
                res.setFinalizadoOk(true);
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
            res.setMensajeErrorTraza(mensajeTraza.toString());

        } catch (Exception e) {
            log.error("Error en el proceso programado", e);
            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza("Se ha producido un error no controlado en el proceso SIA. " + e.getLocalizedMessage());
            res.setFinalizadoOk(false);
        }
        return res;
    }


    private ResultadoSIA indexarServicio(IndexacionSIADTO indexacionDTO, IPluginSIA plugin, StringBuilder mensajeTraza, boolean indexacionForzada) {
        Long codigoWF = null;
        boolean publicado;
        if (indexacionForzada) {
            codigoWF = procedimientoService.getCodigoByWF(indexacionDTO.getCodElemento(), Constantes.PROCEDIMIENTO_ENMODIFICACION);
            publicado = false;
        } else {
            codigoWF = procedimientoService.getCodigoByWF(indexacionDTO.getCodElemento(), Constantes.PROCEDIMIENTO_DEFINITIVO);
            publicado = true;

            if (codigoWF == null) {
                mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " no está publicat. \n");
                return new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El servei " + indexacionDTO.getCodElemento() + " NO esta publicat.");
            }
        }
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        totalServicios++;

        EntidadRaizDTO entidadRaiz = uaService.getUaRaizByProcedimiento(indexacionDTO.getCodElemento());

        ServicioDTO servicioDTO = procedimientoService.findServicioById(codigoWF);
        if (indexacionDTO.getExiste().compareTo(SiaUtils.SIAPENDIENTE_PROCEDIMIENTO_BORRADO) == 0) {
            ResultadoSIA resultadoSIA = borradoSIA(indexacionDTO, plugin, entidadRaiz, servicioDTO);
            if (resultadoSIA != null && resultadoSIA.isCorrecto()) {
                mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " s'ha desindexat correctament. \n");
            } else {
                mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " NO s'ha desindexat correctament, error:" + resultadoSIA.getMensaje() + " \n");
            }
            return resultadoSIA;
        } else {
            if (publicado || indexacionForzada) {
                try {
                    SiaEnviableResultado esEnviable = SiaUtils.isEnviable(uaService, servicioDTO, indexacionForzada);
                    if (esEnviable.isNotificiarSIA()) {
                        final SiaCumpleDatos siaCumpleDatos = SiaUtils.cumpleDatos(uaService, servicioDTO, esEnviable, true, entidadRaiz);
                        //Si es común, no se indexa
                        if (siaCumpleDatos.isCumpleDatos()) {

                            EnvioSIA envioSIA = SiaUtils.cast(uaService, servicioDTO, esEnviable, siaCumpleDatos);
                            boolean borrado = estaBorrado(servicioDTO);
                            ResultadoSIA resultadoSIA = plugin.enviarSIA(envioSIA, borrado, indexacionForzada);
                            if (resultadoSIA != null && resultadoSIA.isCorrecto()) {
                                totalServiciosOK++;
                                mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " s'ha indexat correctament. \n");
                                resultadoSIA.setMensaje("El servei s'ha indexat correctament");
                                return resultadoSIA;
                            } else {
                                totalServiciosERROR++;
                                mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " NO s'ha indexat correctament, error:" + resultadoSIA.getMensaje() + ". \n");
                                new ResultadoSIA(0, "El servei s'ha indexat incorrectamente " + resultadoSIA.getMensaje().toString());
                            }
                        } else {
                            totalServiciosOK++;
                            // Guardamos un SIA Pendiente como que no cumple datos (ultima pestaña)
                            final ResultadoSIA siaPendiente = new ResultadoSIA();
                            siaPendiente.setCorrectos(1);
                            siaPendiente.setMensaje("No compleix les dades, " + siaCumpleDatos.getRespuesta());
                            siaPendiente.setResultado(ResultadoSIA.RESULTADO_NO_CUMPLE_DATOS);
                            mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " no cumpleix les dades, " + siaCumpleDatos.getRespuesta() + " \n");
                            return siaPendiente;
                        }
                    } else {
                        totalServiciosOK++;
                        mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " es no enviable \n");
                        return new ResultadoSIA(ResultadoSIA.RESULTADO_NO_ENVIABLE, esEnviable.getRespuesta());
                    }
                } catch (Exception e) {
                    totalServiciosERROR++;
                    mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " no s'ha indexat, error:" + e.getMessage() + " \n");
                    log.error("Error en la indexacion SIA de serveis ", e);
                    return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, e.getMessage());
                }
            } else {
                totalServiciosOK++;
                mensajeTraza.append("El servei " + indexacionDTO.getCodElemento() + " no està publicat. \n");
                return new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El servei no està publicat");
            }
        }

        return null;
    }

    private ResultadoSIA indexarProcedimiento(IndexacionSIADTO indexacionDTO, IPluginSIA plugin, StringBuilder mensajeTraza, boolean indexacionForzada) {
        Long codigoWF = null;

        boolean publicado;
        if (indexacionForzada) {
            codigoWF = procedimientoService.getCodigoByWF(indexacionDTO.getCodElemento(), Constantes.PROCEDIMIENTO_ENMODIFICACION);
            publicado = false;
        } else {
            codigoWF = procedimientoService.getCodigoByWF(indexacionDTO.getCodElemento(), Constantes.PROCEDIMIENTO_DEFINITIVO);
            publicado = true;

            if (codigoWF == null) {
                mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " no está publicat. \n");
                return new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El procediment " + indexacionDTO.getCodElemento() + " NO esta publicat.");
            }
        }

        indexacionDTO.setFechaIntentoIndexacion(new Date());


        totalProcedimientos++;

        EntidadRaizDTO entidadRaiz = uaService.getUaRaizByProcedimiento(indexacionDTO.getCodElemento());


        ProcedimientoDTO procedimientoDTO = procedimientoService.findProcedimientoById(codigoWF);
        if (indexacionDTO.getExiste().compareTo(SiaUtils.SIAPENDIENTE_PROCEDIMIENTO_BORRADO) == 0) {
            indexacionDTO.setCodigoSIA(procedimientoDTO.getCodigoSIA());
            ResultadoSIA resultadoSIA = borradoSIA(indexacionDTO, plugin, entidadRaiz, procedimientoDTO);
            if (resultadoSIA != null && resultadoSIA.isCorrecto()) {
                mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " s'ha desindexat correctament. \n");
            } else {
                mensajeTraza.append("El procedimient " + indexacionDTO.getCodElemento() + " NO s'ha desindexat correctament, error:" + resultadoSIA.getMensaje() + " \n");
            }
            return resultadoSIA;
        } else {
            if (publicado || indexacionForzada) {
                try {
                    SiaEnviableResultado esEnviable = SiaUtils.isEnviable(uaService, procedimientoDTO, indexacionForzada);
                    if (esEnviable.isNotificiarSIA()) {
                        final SiaCumpleDatos siaCumpleDatos = SiaUtils.cumpleDatos(uaService, procedimientoDTO, esEnviable, true, entidadRaiz);
                        //Si es común, no se indexa
                        if (siaCumpleDatos.isCumpleDatos()) {

                            EnvioSIA envioSIA = SiaUtils.cast(uaService, procedimientoDTO, esEnviable, siaCumpleDatos);
                            boolean borrado = estaBorrado(procedimientoDTO);
                            ResultadoSIA resultadoSIA = plugin.enviarSIA(envioSIA, borrado, indexacionForzada);
                            if (resultadoSIA != null && resultadoSIA.isCorrecto()) {
                                totalProcedimientosOK++;
                                mensajeTraza.append("El procediment " + indexacionDTO.getCodElemento() + " s'ha indexat correctament. \n");
                                ResultadoSIA resultado = new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El procediment s'ha indexat correctament");
                                resultado.setCodSIA(resultadoSIA.getCodSIA());
                                resultado.setEstadoSIA(resultadoSIA.getEstadoSIA());
                                resultado.setOperacion(resultadoSIA.getOperacion());
                                return resultado;
                            } else {
                                totalProcedimientosERROR++;
                                mensajeTraza.append("El procediment " + indexacionDTO.getCodElemento() + " NO s'ha indexat correctament, error:" + resultadoSIA.getMensaje() + ". \n");
                                return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, "El procediment " + indexacionDTO.getCodElemento() + " NO s'ha indexat correctament, error:" + resultadoSIA.getMensaje());
                            }
                        } else {
                            totalProcedimientosOK++;
                            // Guardamos un SIA Pendiente como que no cumple datos (ultima pestaña)
                            final ResultadoSIA siaPendiente = new ResultadoSIA();
                            siaPendiente.setCorrectos(1);
                            siaPendiente.setMensaje("No cumpleix les dades");
                            siaPendiente.setResultado(ResultadoSIA.RESULTADO_NO_CUMPLE_DATOS);
                            mensajeTraza.append("El procediment " + indexacionDTO.getCodElemento() + " no cumpleix les dades, " + siaCumpleDatos.getRespuesta() + " \n");
                            return siaPendiente;
                        }
                    } else {
                        mensajeTraza.append("El procediment " + indexacionDTO.getCodElemento() + " es no enviable \n");
                        return new ResultadoSIA(ResultadoSIA.RESULTADO_NO_ENVIABLE, esEnviable.getRespuesta());
                    }
                } catch (Exception e) {
                    totalProcedimientosERROR++;
                    mensajeTraza.append("El procediment " + indexacionDTO.getCodElemento() + " no s'ha indexat, error:" + e.getLocalizedMessage() + " \n");
                    log.error("Error en la indexacion SIA de procediments", e);
                    return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, e.getLocalizedMessage());
                }
            } else {
                totalProcedimientosOK++;
                mensajeTraza.append("El procediment " + indexacionDTO.getCodElemento() + " no està publicat. \n");
                return new ResultadoSIA(ResultadoSIA.RESULTADO_OK, "El procediment no està publicat");
            }
        }

    }

    private boolean estaBorrado(ProcedimientoBaseDTO proc) {
        if (!proc.getWorkflow().isPublicado()) {
            return false;
        }
        return proc.getEstado() != TypeProcedimientoEstado.PUBLICADO;
    }


    private void inicializarTotalesACero() {
        totalProcedimientos = 0;
        totalProcedimientosOK = 0;
        totalProcedimientosERROR = 0;
        totalServicios = 0;
        totalServiciosOK = 0;
        totalServiciosERROR = 0;
    }


    private ResultadoSIA borradoSIA(IndexacionSIADTO indexacionDTO, IPluginSIA plugin, EntidadRaizDTO entidadRaiz, ProcedimientoBaseDTO procedimiento) {
        EnvioSIA sia = new EnvioSIA();
        if (procedimiento.getCodigoSIA() != null) {
            sia.setIdSia(procedimiento.getCodigoSIA().toString());
        } else if (indexacionDTO.getCodigoSIA() != null) {
            sia.setIdSia(indexacionDTO.getCodigoSIA().toString());
        }
        sia.setOperacion(SiaUtils.ESTADO_BAJA);
        sia.setCdExpediente(procedimiento.getCodigo().toString());

        if (entidadRaiz != null) {
            sia.setUsuario(entidadRaiz.getUser());
            sia.setPassword(entidadRaiz.getPwd());
        }

        ResultadoSIA resultado = null;
        try {
            resultado = plugin.enviarSIA(sia, true, false);
        } catch (final Exception exception) {
            log.error("Se ha producido un error enviando el dato a SIA de un borrado de proc/serv " + indexacionDTO.getCodElemento(), exception);
            return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, exception.getLocalizedMessage());
        }
        return resultado;
    }
}
