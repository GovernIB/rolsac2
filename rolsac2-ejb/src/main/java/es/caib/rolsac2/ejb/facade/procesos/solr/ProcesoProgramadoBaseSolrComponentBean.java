package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacionExcepcion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.ejb.facade.procesos.ProcesosExecComponentFacade;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
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
public abstract class ProcesoProgramadoBaseSolrComponentBean {

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private ProcesoServiceFacade procesoServiceFacade;

    @Inject
    private SystemServiceFacade systemService;

    @Inject
    private NormativaServiceFacade normativaService;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

    /**
     * Totales
     **/
    private int totalProcedimientos = 0;
    private int totalProcedimientosOK = 0;
    private int totalProcedimientosERROR = 0;
    private int totalProcedimientosDOC = 0;
    private int totalProcedimientosDOCOK = 0;
    private int totalProcedimientosDOCERROR = 0;
    private int totalTramites = 0;
    private int totalTramitesOK = 0;
    private int totalTramitesERROR = 0;
    private int totalTramitesDOC = 0;
    private int totalTramitesDOCOK = 0;
    private int totalTramitesDOCERROR = 0;
    private int totalServicios = 0;
    private int totalServiciosOK = 0;
    private int totalServiciosERROR = 0;
    private int totalNormativas = 0;
    private int totalNormativasOK = 0;
    private int totalNormativasERROR = 0;
    private int totalUas = 0;
    private int totalUasOK = 0;
    private int totalUasERROR = 0;
    private int totalIndexadosSolr = 0;
    private int totalIndexadosElastic = 0;
    private String ruta;

    /**
     * LOG
     **/
    private final static Logger log = LoggerFactory.getLogger(ProcesoProgramadoBaseSolrComponentBean.class);

    @Inject
    ProcesosExecComponentFacade procesosExecComponent;

    /**
     * Ejecuta el proceso padre
     *
     * @param instanciaProceso instancia del proceso
     * @param params           parametros
     * @param pendiente        si es pendiente
     * @param idEntidad        id de la entidad
     * @return resultado del proceso
     */
    public ResultadoProcesoProgramado ejecutarPadre(final Long instanciaProceso, final ListaPropiedades params, boolean pendiente, Long idEntidad) {
        log.info("Ejecución proceso solr");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaInicio = "La dada de inici es " + sdf.format(new Date());
        detalles.addPropiedad("Informació del procés", fechaInicio);

        boolean conDocs = true;
        final StringBuilder mensajeTraza = new StringBuilder();
        procesosExecComponent.auditarMitadProceso(instanciaProceso, mensajeTraza.toString() + "\n Estado actual: Inicio de la ejecución.");

        try {
            String accion;
            if (pendiente) {
                accion = "pendientes";
            } else {
                accion = params.getPropiedad("accion");
            }
            ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            detalles.addPropiedades(params);

            Pagina<IndexacionDTO> datos = null;

            IPluginIndexacion plugin = null;

            try {
                plugin = (IPluginIndexacion) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.INDEXACION, idEntidad);
                detalles.addPropiedad("SOLR Activo", plugin.isSolrActivo());
                detalles.addPropiedad("Elastic Activo", plugin.isElasticActivo());

            } catch (Exception e) {
                res.setFinalizadoOk(false);
                detalles.addPropiedad("Informació del procés", "Error obteniendo plugin de indexacion.");
                detalles.addPropiedad("Error", e.getMessage());
                res.setDetalles(detalles);
                return res;
            }

            if (plugin == null) {
                res.setFinalizadoOk(false);
                detalles.addPropiedad("Informació del procés", "No está especificado el plugin de indexación");
                res.setDetalles(detalles);
                return res;
            }


            ProcesoSolrFiltro filtro = new ProcesoSolrFiltro();
            filtro.setIdEntidad(idEntidad);
            filtro.setPaginaTamanyo(10000);
            filtro.setPaginaFirst(0);
            switch (accion) {
                case Constantes.INDEXAR_SOLR_NORMATIVAS:
                    datos = normativaService.getNormativasParaIndexacion(idEntidad);
                    break;
                case Constantes.INDEXAR_SOLR_PROCEDIMIENTOS:
                    datos = procedimientoService.getProcedimientosParaIndexacion(true, idEntidad);
                    break;
                case Constantes.INDEXAR_SOLR_PROCEDIMIENTOS_SIN_DOCS:
                    datos = procedimientoService.getProcedimientosParaIndexacion(true, idEntidad);
                    conDocs = false;
                    break;
                case Constantes.INDEXAR_SOLR_SERVICIOS:
                    datos = procedimientoService.getProcedimientosParaIndexacion(false, idEntidad);
                    break;
                case Constantes.INDEXAR_SOLR_UAS:
                    datos = uaService.getUAsParaIndexacion(idEntidad);
                    break;
                case Constantes.INDEXAR_SOLR_PENDIENTES:
                    filtro.setTipo(null);
                    datos = procesoServiceFacade.findSolrByFiltro(filtro);
                    break;
                case Constantes.INDEXAR_SOLR_BORRAR_CADUCADAS:
                case Constantes.INDEXAR_SOLR_BORRAR_TODO:
                    break;
            }

            inicializarTotalesACero();

            //Variable que se utiliza para hacer un commit cada 5
            int cuantos = 0;
            String ruta = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);

            if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {


                for (IndexacionDTO dato : datos.getItems()) {
                    cuantos++;
                    if (cuantos % 5 == 0) {
                        procesosExecComponent.auditarMitadProceso(instanciaProceso, "Ejecutándose todavia. Estado: " + (cuantos * 100 / datos.getItems().size()) + "%\n\n" + mensajeTraza.toString());
                    }

                    // Si la acción es 1, es indexar
                    switch (TypeIndexacion.fromString(dato.getTipo())) {
                        case PROCEDIMIENTO:
                            ResultadoAccion resultadoPro;
                            if (dato.getAccion() == 1) {
                                if (conDocs) {
                                    resultadoPro = indexarProcedimiento(dato, plugin, mensajeTraza);
                                } else {
                                    resultadoPro = indexarProcedimientoSinDoc(dato, plugin, mensajeTraza);
                                }
                            } else {
                                resultadoPro = desindexarProcedimiento(dato, plugin, mensajeTraza);
                            }

                            if (dato.getCodigo() != null) {
                                //Si es distinto null, significa que es un dato pendiente
                                procedimientoService.actualizarSolr(dato, resultadoPro);
                            }
                            tratarTotales(resultadoPro);
                            break;
                        case SERVICIO:
                            totalServicios++;
                            ResultadoAccion resultadoSrv;
                            if (dato.getAccion() == 1) {
                                resultadoSrv = indexarServicio(dato, plugin, mensajeTraza);
                            } else {
                                resultadoSrv = desindexarServicio(dato, plugin, mensajeTraza);
                            }

                            if (resultadoSrv.isCorrecto()) {
                                totalServiciosOK++;
                            } else {
                                totalServiciosERROR++;
                            }
                            tratarTotales(resultadoSrv);
                            if (dato.getCodigo() != null) {
                                //Si es distinto null, significa que es un dato pendiente
                                procedimientoService.actualizarSolr(dato, resultadoSrv);
                            }
                            break;
                        case UNIDAD_ADMINISTRATIVA:
                            totalUas++;
                            ResultadoAccion resultadoUA;
                            if (dato.getAccion() == 1) {
                                resultadoUA = indexarUA(dato, plugin, mensajeTraza);
                            } else {
                                resultadoUA = desindexarUA(dato, plugin, mensajeTraza);
                            }
                            if (resultadoUA.isCorrecto()) {
                                totalUasOK++;
                            } else {
                                totalUasERROR++;
                            }
                            if (dato.getCodigo() != null) {
                                //Si es distinto null, significa que es un dato pendiente
                                uaService.actualizarSolr(dato, resultadoUA);
                            }
                            tratarTotales(resultadoUA);
                            break;
                        case NORMATIVA:
                            totalNormativas++;
                            ResultadoAccion resultadoNormativa;
                            if (dato.getAccion() == 1) {
                                resultadoNormativa = indexarNormativa(dato, plugin, mensajeTraza, ruta);
                            } else {
                                resultadoNormativa = desindexarNormativa(dato, plugin, mensajeTraza);
                            }
                            if (resultadoNormativa.isCorrecto()) {
                                totalNormativasOK++;
                            } else {
                                totalNormativasERROR++;
                            }
                            tratarTotales(resultadoNormativa);
                            if (dato.getCodigo() != null) {
                                //Si es distinto null, significa que es un dato pendiente
                                normativaService.actualizarSolr(dato, resultadoNormativa);
                            }
                            break;
                    }
                }

                if (cuantos % 5 == 0) {
                    comitearIndexacion(plugin);
                }

                String fechaFin = "La dada de fi es " + sdf.format(new Date());
                res.setFinalizadoOk(true);
                if (totalProcedimientos > 0) {
                    detalles.addPropiedad("Procediments", "S'ha indexat " + totalProcedimientos + " (correctes:" + totalProcedimientosOK + " , error:" + totalProcedimientosERROR + ")");
                    detalles.addPropiedad(" - Procediments DOC", "S'ha indexat " + totalProcedimientosDOC + " (correctes:" + totalProcedimientosDOCOK + " , error:" + totalProcedimientosDOCERROR + ")");
                    detalles.addPropiedad(" - Tramites", "S'ha indexat " + totalTramites + " (correctes:" + totalTramitesOK + " , error:" + totalTramitesERROR + ")");
                    detalles.addPropiedad(" - Tramites DOC", "S'ha indexat " + totalTramitesDOC + " (correctes:" + totalTramitesDOCOK + " , error:" + totalTramitesDOCERROR + ")");
                }
                if (totalServicios > 0) {
                    detalles.addPropiedad("Serveis", "S'ha indexat " + totalServicios + " (correctes:" + totalServiciosOK + " , error:" + totalServiciosERROR + ")");
                }
                if (totalNormativas > 0) {
                    detalles.addPropiedad("Normatives", "S'ha indexat " + totalNormativas + " (correctes:" + totalNormativasOK + " , error:" + totalNormativasERROR + ")");
                }
                if (totalUas > 0) {
                    detalles.addPropiedad("UnitatsAdmin.", "S'ha indexat " + totalUas + " (correctes:" + totalUasOK + " , error:" + totalUasERROR + ")");
                }
                detalles.addPropiedad("Fin del procés", fechaFin);

                detalles.addPropiedad("totalSolr", "Dades correctament indexades solr:" + totalIndexadosSolr);
                detalles.addPropiedad("totalElastic", "Dades correctament indexades elastic:" + totalIndexadosElastic);
                res.setDetalles(detalles);
            } else if (accion.equals(Constantes.INDEXAR_SOLR_BORRAR_TODO)) {
                try {
                    plugin.desindexarAplicacion();
                    res.setFinalizadoOk(true);
                    detalles.addPropiedad("Informació del procés", "Desindexado toda la aplicacion");
                    res.setDetalles(detalles);
                    res.setFinalizadoOk(true);
                } catch (IPluginIndexacionExcepcion e) {
                    res.setFinalizadoOk(false);
                    detalles.addPropiedad("Informació del procés", "Error desindexando toda la aplicación");
                    res.setDetalles(detalles);
                }
            } else if (accion.equals(Constantes.INDEXAR_SOLR_BORRAR_CADUCADAS)) {
                try {
                    plugin.desindexarCaducados();
                    res.setFinalizadoOk(true);
                    detalles.addPropiedad("Informació del procés", "Desindexado los caducados");
                    res.setDetalles(detalles);
                    res.setFinalizadoOk(true);
                } catch (IPluginIndexacionExcepcion e) {
                    res.setFinalizadoOk(false);
                    detalles.addPropiedad("Informació del procés", "Error desindexando los caducados");
                    res.setDetalles(detalles);
                }
            } else {

                res.setFinalizadoOk(true);
                detalles.addPropiedad("Informació del procés", "Sense dades per a indexar");
                res.setDetalles(detalles);


            }

            //Se realiza un commit final porque pueden quedar de 1 a 4 datos sin comitear
            comitearIndexacion(plugin);

            res.setDetalles(detalles);
            res.setMensajeErrorTraza(mensajeTraza.toString());
        } catch (Exception e) {
            log.error("Error en el proceso programado", e);
            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza("Se ha producido un error no controlado en el proceso Solr. " + e.getMessage());
            res.setFinalizadoOk(false);
        }
        return res;
    }

    /**
     * Trata los totales de indexacion
     *
     * @param resultado resultado de la accion
     */
    private void tratarTotales(ResultadoAccion resultado) {
        if (resultado.isElasticActivo() && resultado.isResultadoElastic()) {
            totalIndexadosElastic++;
        }
        if (resultado.isSolrActivo() && resultado.isResultadoSolr()) {
            totalIndexadosSolr++;
        }
    }

    /**
     * Comitea una indexación
     *
     * @param plugin plugin de indexacion
     */
    private void comitearIndexacion(IPluginIndexacion plugin) {
        try {
            plugin.commit();
        } catch (Exception e) {
            log.error("Error comiteando la info ", e);
        }

    }

    /**
     * Desindexa una UA
     *
     * @param dato    dato a desindexar
     * @param plugin  plugin de indexacion
     * @param mensaje mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion desindexarUA(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalUas++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA);
            if (resultado != null && resultado.isCorrecto()) {
                totalUasOK++;
                mensaje.append("La UA ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" s'ha desindexat correctament. \n");
                return new ResultadoAccion(true, "UA desindexat correctament", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
            } else {
                totalUasERROR++;
                mensaje.append("La UA ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" NO s'ha desindexat correctament, error:");
                if (resultado != null && resultado.getMensaje() != null) {
                    mensaje.append(resultado.getMensaje());
                }
                mensaje.append(" \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            log.error("Error en desindexarUA", e);
            totalUasERROR++;
            mensaje.append("La UA ");
            mensaje.append(dato.getCodElemento());
            mensaje.append(" NO s'ha desindexat correctament, error:");
            mensaje.append(e.getMessage());
            mensaje.append(" \n");
            return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }

    /**
     * Indexa una UA.
     *
     * @param dato    dato a indexar
     * @param plugin  plugin de indexacion
     * @param mensaje mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion indexarUA(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        try {
            ProcedimientoSolrDTO procedimientoSolrDTO = uaService.findDataIndexacionUAById(dato.getCodElemento());

            if (procedimientoSolrDTO.getUnidadAdministrativaDTO() == null) {
                return new ResultadoAccion(false, "No existeix ua " + dato.getCodElemento(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
            }

            //Primero desindexamos por raiz
            plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA);

            ResultadoAccion resultado = plugin.indexarContenido(procedimientoSolrDTO.getDataIndexacion());
            if (resultado != null && resultado.isCorrecto()) {
                mensaje.append("UA ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" OK\n");
                return new ResultadoAccion(true, "UA OK", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
            } else {
                mensaje.append("UA ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" ERROR: ");
                if (resultado != null) {
                    mensaje.append(resultado.getMensaje(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return resultado;
            }

        } catch (Exception e) {
            log.error("Error en indexarUA", e);
            mensaje.append("UA ");
            mensaje.append(dato.getCodElemento());
            mensaje.append(" NO indexat ERROR:");
            if (e.getMessage() != null) {
                mensaje.append(e.getMessage(), 0, 100);
            } else {
                mensaje.append("DESC");
            }
            mensaje.append(" \n");
            return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }

    /**
     * Desindexa una normativa
     *
     * @param dato    dato a desindexar
     * @param plugin  plugin de indexacion
     * @param mensaje mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion desindexarNormativa(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalNormativas++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_NORMATIVA);
            if (resultado != null && resultado.isCorrecto()) {
                totalNormativasOK++;
                mensaje.append("NORM ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" OK\n");
                return new ResultadoAccion(true, "NORM  desindexat correctament", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
            } else {
                totalNormativasERROR++;
                mensaje.append("NORM ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" NO desindexat ERROR:");
                if (resultado != null && resultado.getMensaje() != null) {
                    mensaje.append(resultado.getMensaje(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            log.error("Error en desindexarNormativa", e);
            totalNormativasERROR++;
            mensaje.append("NORM ");
            mensaje.append(dato.getCodElemento());
            mensaje.append(" NO desindexat , ERROR:");
            if (e.getMessage() != null) {
                mensaje.append(e.getMessage(), 0, 100);
            } else {
                mensaje.append("DESC");
            }
            mensaje.append(" \n");
            return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }

    /**
     * Indexa un procedimiento.
     *
     * @param dato    dato a indexar
     * @param plugin  plugin de indexacion
     * @param mensaje mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion indexarNormativa(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje, String path) {

        try {
            ProcedimientoSolrDTO procedimientoSolrDTO = normativaService.findDataIndexacionNormById(dato.getCodElemento());

            if (procedimientoSolrDTO.getNormativaDTO() == null) {
                return new ResultadoAccion(false, "No existeix la normativa " + dato.getCodElemento(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
            }

            //Primero desindexamos por raiz
            plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_NORMATIVA);

            ResultadoAccion resultado = plugin.indexarContenido(procedimientoSolrDTO.getDataIndexacion());
            if (resultado != null && resultado.isCorrecto()) {

                if (procedimientoSolrDTO.getNormativaDTO().getDocumentosNormativa() != null) {
                    for (DocumentoNormativaDTO doc : procedimientoSolrDTO.getNormativaDTO().getDocumentosNormativa()) {
                        if (doc.getDocumentos() != null) {
                            for (DocumentoTraduccion docTraduccion : doc.getDocumentos().getTraducciones()) {
                                if (docTraduccion.getFicheroDTO() != null) {
                                    IndexFile indexFile = normativaService.findDataIndexacionDocNormById(procedimientoSolrDTO.getNormativaDTO(), doc, docTraduccion, procedimientoSolrDTO.getPathUAs(), path);
                                    ResultadoAccion resultadoDoc = plugin.indexarFichero(indexFile);
                                    if (resultadoDoc != null && !resultadoDoc.isCorrecto()) {
                                        mensaje.append("NORM ");
                                        mensaje.append(dato.getCodElemento());
                                        mensaje.append(" OK pero fitxer no.");
                                        mensaje.append("El fitxer  ");
                                        mensaje.append(docTraduccion.getCodigo());
                                        mensaje.append(" NO indexat, error:");
                                        if (resultadoDoc.getMensaje() != null) {
                                            mensaje.append(resultadoDoc.getMensaje(), 0, 100);
                                        } else {
                                            mensaje.append("DESC");
                                        }
                                        mensaje.append(" \n");
                                        return new ResultadoAccion(false, resultadoDoc.getMensaje(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
                                    }
                                }
                            }
                        }
                    }
                }
                mensaje.append("NORM ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" OK. \n");
                return new ResultadoAccion(true, "NORM indexat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
            } else {
                mensaje.append("NORM ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" NO indexat , ERROR:");
                if (resultado != null) {
                    mensaje.append(resultado.getMensaje(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return resultado;
            }

        } catch (Exception e) {
            log.error("Error en indexarNormativa", e);
            mensaje.append("NORM ");
            mensaje.append(dato.getCodElemento());
            mensaje.append(" NO indexat, ERROR:");
            if (e.getMessage() != null) {
                mensaje.append(e.getMessage(), 0, 100);
            } else {
                mensaje.append("DESC");
            }
            mensaje.append(" \n");
            return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }

    /**
     * Desindexa un procedimiento
     *
     * @param dato    procedimiento a desindexar
     * @param plugin  plugin de indexacion
     * @param mensaje mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion desindexarProcedimiento(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalProcedimientos++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_PROCEDIMIENTO);
            if (resultado != null && resultado.isCorrecto()) {
                totalProcedimientosOK++;
                mensaje.append("PROC ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" desindexat OK\n");
                return new ResultadoAccion(true, "PROC desindexat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
            } else {
                totalProcedimientosERROR++;
                mensaje.append("PROC ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" NO desindexat ERROR:");
                if (resultado != null && resultado.getMensaje() != null) {
                    mensaje.append(resultado.getMensaje(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            log.error("Error en desindexarProcedimiento", e);
            totalProcedimientosERROR++;
            mensaje.append("PROC ");
            mensaje.append(dato.getCodElemento());
            mensaje.append(" NO desindexat, ERROR:");
            if (e.getMessage() != null) {
                mensaje.append(e.getMessage(), 0, 100);
            } else {
                mensaje.append("DESC");
            }
            mensaje.append(" \n");
            return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }

    }

    /**
     * Indexa un procedimiento sin documentos.
     *
     * @param indexacionDTO dato a indexar
     * @param plugin        plugin de indexacion
     * @param mensaje       mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion indexarProcedimientoSinDoc(IndexacionDTO indexacionDTO, IPluginIndexacion plugin, StringBuilder mensaje) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        totalProcedimientos++;

        if (publicado) {
            try {

                //Primero desindexamos por raiz
                plugin.desindexar(indexacionDTO.getCodElemento().toString(), EnumCategoria.ROLSAC_PROCEDIMIENTO);

                ProcedimientoSolrDTO procedimiento = procedimientoService.findDataIndexacionProcById(codigoWF, plugin);

                ResultadoAccion resultado = plugin.indexarContenido(procedimiento.getDataIndexacion());
                if (resultado != null && resultado.isCorrecto()) {

                    boolean todoCorrecto = true;
                    StringBuilder mensajesIncorrectos = new StringBuilder();

                    if (procedimiento.getProcedimientoDTO().getTramites() != null) {
                        for (ProcedimientoTramiteDTO tramite : procedimiento.getProcedimientoDTO().getTramites()) {
                            totalTramites++;
                            DataIndexacion datoIndexadoTram = procedimientoService.findDataIndexacionTram(tramite, procedimiento.getProcedimientoDTO(), procedimiento.getPathUA(), plugin);
                            ResultadoAccion resultadoTramite = plugin.indexarContenido(datoIndexadoTram);
                            if (resultadoTramite != null && resultadoTramite.isCorrecto()) {
                                totalTramitesOK++;
                            } else {
                                totalTramitesERROR++;
                                todoCorrecto = false;
                                mensajesIncorrectos.append(" Tramite: ");
                                mensajesIncorrectos.append(tramite.getCodigo());
                                mensajesIncorrectos.append(" . ERROR:");
                                if (resultadoTramite != null) {
                                    mensajesIncorrectos.append(resultadoTramite.getMensaje());
                                } else {
                                    mensajesIncorrectos.append("null");
                                }
                            }
                        }
                    }

                    if (todoCorrecto) {
                        totalProcedimientosOK++;
                        mensaje.append("PROC ");
                        mensaje.append(indexacionDTO.getCodElemento());
                        mensaje.append(" OK\n");
                        return new ResultadoAccion(true, "PROC indexat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
                    } else {
                        totalProcedimientosERROR++;
                        mensaje.append("PROC ");
                        mensaje.append(indexacionDTO.getCodElemento());
                        mensaje.append(" OK ( ");
                        mensaje.append(mensajesIncorrectos.toString());
                        mensaje.append(" )\n");
                        return new ResultadoAccion(false, "Un tràmit o document de doc/tram s'ha indexat incorrectament" + mensajesIncorrectos.toString(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
                    }
                } else {
                    totalProcedimientosERROR++;
                    mensaje.append("El procediment ");
                    mensaje.append(indexacionDTO.getCodElemento());
                    mensaje.append(" no s'ha indexat. Error:");
                    if (resultado != null) {
                        mensaje.append(resultado.getMensaje());
                    } else {
                        mensaje.append("null");
                    }
                    mensaje.append(" \n");
                    return resultado;
                }

            } catch (Exception e) {
                log.error("Error en indexarProcedimiento", e);
                totalProcedimientosERROR++;
                mensaje.append("PROC ");
                mensaje.append(indexacionDTO.getCodElemento());
                mensaje.append(" NO indexat. ERROR:");
                if (e.getMessage() != null) {
                    mensaje.append(e.getMessage(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
            } finally {
                // Pausa de 1 segundo para evitar saturar el servidor de documentos
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.warn("Interrupción durante pausa de indexación", ie);
                }
            }
        } else {
            totalProcedimientosOK++;
            mensaje.append("PROC ");
            mensaje.append(indexacionDTO.getCodElemento());
            mensaje.append(" NO  indexat, NO PUBLICADO.\n");

            // Pausa de 1 segundo para evitar saturar el servidor de documentos
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.warn("Interrupción durante pausa de indexación", ie);
            }

            return new ResultadoAccion(true, "El procediment no està publicat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }


    /**
     * Indexa un procedimiento.
     *
     * @param indexacionDTO dato a indexar
     * @param plugin        plugin de indexacion
     * @param mensaje       mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion indexarProcedimiento(IndexacionDTO indexacionDTO, IPluginIndexacion plugin, StringBuilder mensaje) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        totalProcedimientos++;

        if (publicado) {
            try {

                //Primero desindexamos por raiz
                plugin.desindexar(indexacionDTO.getCodElemento().toString(), EnumCategoria.ROLSAC_PROCEDIMIENTO);

                ProcedimientoSolrDTO procedimiento = procedimientoService.findDataIndexacionProcById(codigoWF, plugin);

                ResultadoAccion resultado = plugin.indexarContenido(procedimiento.getDataIndexacion());
                if (resultado != null && resultado.isCorrecto()) {

                    boolean todoCorrecto = true;
                    StringBuilder mensajesIncorrectos = new StringBuilder();

                    if (procedimiento.getProcedimientoDTO().getDocumentos() != null) {
                        for (ProcedimientoDocumentoDTO doc : procedimiento.getProcedimientoDTO().getDocumentos()) {
                            if (doc.getDocumentos() != null) {
                                for (DocumentoTraduccion fichero : doc.getDocumentos().getTraducciones()) {
                                    // FicheroDTO ficheroDTO = procedimientoService.getFicheroDTOByDocumentoTraduccion(fichero.getCodigo());
                                    if (fichero.getFicheroDTO() != null && fichero.getFicheroDTO().getCodigo() != null) {
                                        totalProcedimientosDOC++;
                                        IndexFile datoIndexadoDoc = procedimientoService.findDataIndexacionProcDoc(procedimiento.getProcedimientoDTO(), doc, fichero, procedimiento.getPathUA(), ruta, plugin);
                                        ResultadoAccion resultadoDoc = plugin.indexarFichero(datoIndexadoDoc);
                                        if (resultadoDoc != null && resultadoDoc.isCorrecto()) {
                                            totalProcedimientosDOCOK++;
                                        } else {
                                            totalProcedimientosDOCERROR++;
                                            todoCorrecto = false;
                                            mensajesIncorrectos.append(" ProcedimientoDoc: ");
                                            mensajesIncorrectos.append(doc.getCodigo());
                                            mensajesIncorrectos.append(" . ERROR:");
                                            if (resultadoDoc != null) {
                                                mensajesIncorrectos.append(resultadoDoc.getMensaje());
                                            } else {
                                                mensajesIncorrectos.append("null");
                                            }
                                            mensajesIncorrectos.append(" \n");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (procedimiento.getProcedimientoDTO().getTramites() != null) {
                        for (ProcedimientoTramiteDTO tramite : procedimiento.getProcedimientoDTO().getTramites()) {
                            totalTramites++;
                            DataIndexacion datoIndexadoTram = procedimientoService.findDataIndexacionTram(tramite, procedimiento.getProcedimientoDTO(), procedimiento.getPathUA(), plugin);
                            ResultadoAccion resultadoTramite = plugin.indexarContenido(datoIndexadoTram);
                            if (resultadoTramite != null && resultadoTramite.isCorrecto()) {
                                totalTramitesOK++;

                                if (tramite.getListaDocumentos() != null) {

                                    for (ProcedimientoDocumentoDTO doc : tramite.getListaDocumentos()) {
                                        if (doc.getDocumentos() != null) {
                                            for (DocumentoTraduccion fichero : doc.getDocumentos().getTraducciones()) {
                                                if (fichero.getFicheroDTO() != null && fichero.getFicheroDTO().getCodigo() != null) {
                                                    totalTramitesDOC++;
                                                    IndexFile datoIndexadoDoc = procedimientoService.findDataIndexacionTramDoc(tramite, procedimiento.getProcedimientoDTO(), doc, fichero, procedimiento.getPathUA(), ruta, plugin);
                                                    ResultadoAccion resultadoDoc = plugin.indexarFichero(datoIndexadoDoc);
                                                    if (resultadoDoc != null && resultadoDoc.isCorrecto()) {
                                                        totalTramitesDOCOK++;
                                                    } else {
                                                        totalTramitesDOCERROR++;
                                                        todoCorrecto = false;
                                                        mensajesIncorrectos.append(" TramiteDoc: ");
                                                        mensajesIncorrectos.append(doc.getCodigo());
                                                        mensajesIncorrectos.append(" . ERROR:");
                                                        if (resultadoDoc != null) {
                                                            mensajesIncorrectos.append(resultadoDoc.getMensaje());
                                                        } else {
                                                            mensajesIncorrectos.append("null");
                                                        }
                                                        mensajesIncorrectos.append("\n");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if (tramite.getListaModelos() != null) {

                                    for (ProcedimientoDocumentoDTO doc : tramite.getListaModelos()) {
                                        if (doc.getDocumentos() != null) {
                                            for (DocumentoTraduccion fichero : doc.getDocumentos().getTraducciones()) {
                                                if (fichero.getFicheroDTO() != null && fichero.getFicheroDTO().getCodigo() != null) {

                                                    totalTramitesDOC++;
                                                    IndexFile datoIndexadoDoc = procedimientoService.findDataIndexacionTramDoc(tramite, procedimiento.getProcedimientoDTO(), doc, fichero, procedimiento.getPathUA(), ruta, plugin);
                                                    ResultadoAccion resultadoDoc = plugin.indexarFichero(datoIndexadoDoc);
                                                    if (resultadoDoc != null && resultadoDoc.isCorrecto()) {
                                                        totalTramitesDOCOK++;
                                                    } else {
                                                        totalTramitesDOCERROR++;
                                                        todoCorrecto = false;
                                                        mensajesIncorrectos.append(" TramiteModelo: ");
                                                        mensajesIncorrectos.append(doc.getCodigo());
                                                        mensajesIncorrectos.append(" . ERROR:");
                                                        if (resultadoDoc != null) {
                                                            mensajesIncorrectos.append(resultadoDoc.getMensaje());
                                                        } else {
                                                            mensajesIncorrectos.append("null");
                                                        }
                                                        mensajesIncorrectos.append("\n");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                            } else {
                                totalTramitesERROR++;
                                todoCorrecto = false;
                                mensajesIncorrectos.append(" Tramite: ");
                                mensajesIncorrectos.append(tramite.getCodigo());
                                mensajesIncorrectos.append(" . ERROR:");
                                if (resultadoTramite != null) {
                                    mensajesIncorrectos.append(resultadoTramite.getMensaje());
                                } else {
                                    mensajesIncorrectos.append("null");
                                }
                            }
                        }
                    }

                    if (todoCorrecto) {
                        totalProcedimientosOK++;
                        mensaje.append("PROC ");
                        mensaje.append(indexacionDTO.getCodElemento());
                        mensaje.append(" OK\n");
                        return new ResultadoAccion(true, "PROC indexat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
                    } else {
                        totalProcedimientosERROR++;
                        mensaje.append("PROC ");
                        mensaje.append(indexacionDTO.getCodElemento());
                        mensaje.append(" OK ( ");
                        mensaje.append(mensajesIncorrectos.toString());
                        mensaje.append(" )\n");
                        return new ResultadoAccion(false, "Un tràmit o document de doc/tram s'ha indexat incorrectament" + mensajesIncorrectos.toString(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
                    }
                } else {
                    totalProcedimientosERROR++;
                    mensaje.append("El procediment ");
                    mensaje.append(indexacionDTO.getCodElemento());
                    mensaje.append(" no s'ha indexat. Error:");
                    if (resultado != null) {
                        mensaje.append(resultado.getMensaje());
                    } else {
                        mensaje.append("null");
                    }
                    mensaje.append(" \n");
                    return resultado;
                }

            } catch (Exception e) {
                log.error("Error en indexarProcedimiento", e);
                totalProcedimientosERROR++;
                mensaje.append("PROC ");
                mensaje.append(indexacionDTO.getCodElemento());
                mensaje.append(" NO indexat. ERROR:");
                if (e.getMessage() != null) {
                    mensaje.append(e.getMessage(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
            } finally {
                // Pausa de 1 segundo para evitar saturar el servidor de documentos
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.warn("Interrupción durante pausa de indexación", ie);
                }
            }
        } else {
            totalProcedimientosOK++;
            mensaje.append("PROC ");
            mensaje.append(indexacionDTO.getCodElemento());
            mensaje.append(" NO  indexat, NO PUBLICADO.\n");

            // Pausa de 1 segundo para evitar saturar el servidor de documentos
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.warn("Interrupción durante pausa de indexación", ie);
            }

            return new ResultadoAccion(true, "El procediment no està publicat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }

    /**
     * Desindexa un servicio.
     *
     * @param dato    dato a desindexar
     * @param plugin  plugin de indexacion
     * @param mensaje mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion desindexarServicio(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalServicios++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_SERVICIO);
            if (resultado != null && resultado.isCorrecto()) {
                totalServiciosOK++;
                mensaje.append("SERV ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" desindexat . \n");
                return new ResultadoAccion(true, "El servei s'ha desindexat correctament", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
            } else {
                totalServiciosERROR++;
                mensaje.append("SERV ");
                mensaje.append(dato.getCodElemento());
                mensaje.append(" NO  desindexat, ERROR:");
                if (resultado != null && resultado.getMensaje() != null) {
                    mensaje.append(resultado.getMensaje(), 0, 100);
                } else {
                    mensaje.append("DESC");
                }
                mensaje.append(" \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            log.error("Error en desindexarServicio", e);
            totalServiciosERROR++;
            mensaje.append("El servei ");
            mensaje.append(dato.getCodElemento());
            mensaje.append(" no s'ha desindexat. Error: ");
            mensaje.append(e.getMessage());
            mensaje.append(" \n");
            return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
        }
    }

    /**
     * Indexa un servicio.
     *
     * @param indexacionDTO dato a indexar
     * @param plugin        plugin de indexacion
     * @param mensaje       mensaje de traza
     * @return resultado de la accion
     */
    private ResultadoAccion indexarServicio(IndexacionDTO indexacionDTO, IPluginIndexacion plugin, StringBuilder mensaje) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        if (publicado) {
            try {
                //Primero desindexamos por raiz
                plugin.desindexar(indexacionDTO.getCodElemento().toString(), EnumCategoria.ROLSAC_SERVICIO);

                ProcedimientoSolrDTO servicio = procedimientoService.findDataIndexacionServById(codigoWF, plugin);
                ResultadoAccion resultado = plugin.indexarContenido(servicio.getDataIndexacion());
                if (resultado.isCorrecto()) {
                    mensaje.append("SERV ");
                    mensaje.append(indexacionDTO.getCodElemento());
                    mensaje.append(" OK\n");

                } else {
                    mensaje.append("SERV ");
                    mensaje.append(indexacionDTO.getCodElemento());
                    mensaje.append(" NO indexat. ERROR:");
                    mensaje.append(resultado.getMensaje());
                    mensaje.append(" \n");
                }
                return resultado;
            } catch (Exception e) {
                log.error("Error en indexarServicio", e);
                mensaje.append("SERV ");
                mensaje.append(indexacionDTO.getCodElemento());
                mensaje.append(" NO indexat. ERROR:");
                mensaje.append(e.getMessage());
                mensaje.append(" \n");
                return new ResultadoAccion(false, e.getMessage(), Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), false, false);
            }
        } else {
            mensaje.append("SERV ");
            mensaje.append(indexacionDTO.getCodElemento());
            mensaje.append(" NO indexat perque no publicat.\n");
            return new ResultadoAccion(true, "El servei no està publicat", Boolean.parseBoolean(plugin.isSolrActivo()), Boolean.parseBoolean(plugin.isElasticActivo()), true, true);
        }
    }

    /**
     * Inicializa los totales a cero.
     */
    private void inicializarTotalesACero() {
        totalProcedimientos = 0;
        totalProcedimientosOK = 0;
        totalProcedimientosERROR = 0;
        totalProcedimientosDOC = 0;
        totalProcedimientosDOCOK = 0;
        totalProcedimientosDOCERROR = 0;
        totalTramites = 0;
        totalTramitesOK = 0;
        totalTramitesERROR = 0;
        totalTramitesDOC = 0;
        totalTramitesDOCOK = 0;
        totalTramitesDOCERROR = 0;
        totalServicios = 0;
        totalServiciosOK = 0;
        totalServiciosERROR = 0;
        totalNormativas = 0;
        totalNormativasOK = 0;
        totalNormativasERROR = 0;
        totalUas = 0;
        totalUasOK = 0;
        totalUasERROR = 0;
        totalIndexadosElastic = 0;
        totalIndexadosSolr = 0;
    }
}
