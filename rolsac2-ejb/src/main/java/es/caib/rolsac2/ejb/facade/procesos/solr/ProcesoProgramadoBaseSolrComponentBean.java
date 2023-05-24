package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacionExcepcion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumCategoria;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
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
public abstract class ProcesoProgramadoBaseSolrComponentBean {

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private ProcesoServiceFacade procesoServiceFacade;
    @Inject
    private NormativaServiceFacade normativaService;

    @Inject
    private UnidadAdministrativaServiceFacade uaService;

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

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoBaseSolrComponentBean.class);


    public ResultadoProcesoProgramado ejecutarPadre(final ListaPropiedades params, boolean pendiente, Long idEntidad) {
        log.info("Ejecución proceso solr");
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

            detalles.addPropiedades(params);

            Pagina<IndexacionDTO> datos = null;

            IPluginIndexacion plugin = null;

            try {
                plugin = (IPluginIndexacion) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.INDEXACION, idEntidad);
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
            StringBuilder mensajeTraza = new StringBuilder();

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

            if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {


                for (IndexacionDTO dato : datos.getItems()) {
                    cuantos++;

                    // Si la acción es 1, es indexar
                    switch (TypeIndexacion.fromString(dato.getTipo())) {
                        case PROCEDIMIENTO:
                            ResultadoAccion resultadoPro;
                            if (dato.getAccion() == 1) {
                                resultadoPro = indexarProcedimiento(dato, plugin, mensajeTraza);
                            } else {
                                resultadoPro = desindexarProcedimiento(dato, plugin, mensajeTraza);
                            }
                            if (dato.getCodigo() != null) {
                                //Si es distinto null, significa que es un dato pendiente
                                procedimientoService.actualizarSolr(dato, resultadoPro);
                            }
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
                            break;
                        case NORMATIVA:
                            totalNormativas++;
                            ResultadoAccion resultadoNormativa;
                            if (dato.getAccion() == 1) {
                                resultadoNormativa = indexarNormativa(dato, plugin, mensajeTraza);
                            } else {
                                resultadoNormativa = desindexarNormativa(dato, plugin, mensajeTraza);
                            }
                            if (resultadoNormativa.isCorrecto()) {
                                totalNormativasOK++;
                            } else {
                                totalNormativasERROR++;
                            }
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
            res.setMensajeErrorTraza("Se ha producido un error no controlado en el proceso Solr. " + e.getLocalizedMessage());
            res.setFinalizadoOk(false);
        }
        return res;
    }

    private void comitearIndexacion(IPluginIndexacion plugin) {
        try {
            plugin.commit();
        } catch (Exception e) {
            log.error("Error comiteando la info ", e);
        }

    }


    private ResultadoAccion desindexarUA(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalUas++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA);
            if (resultado != null && resultado.isCorrecto()) {
                totalUasOK++;
                mensaje.append("La UA " + dato.getCodElemento() + " s'ha desindexat correctament. \n");
                return new ResultadoAccion(true, "La UA s'ha desindexat correctament");
            } else {
                totalUasERROR++;
                mensaje.append("La UA " + dato.getCodElemento() + " NO s'ha desindexat correctament, error:" + resultado.getMensaje() + " \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            totalUasERROR++;
            mensaje.append("La UA " + dato.getCodElemento() + " NO s'ha desindexat correctament, error:" + e.getLocalizedMessage() + " \n");
            return new ResultadoAccion(false, e.getMessage());
        }
    }

    private ResultadoAccion indexarUA(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        try {
            ProcedimientoSolrDTO procedimientoSolrDTO = uaService.findDataIndexacionUAById(dato.getCodElemento());

            if (procedimientoSolrDTO.getUnidadAdministrativaDTO() == null) {
                return new ResultadoAccion(false, "No existeix la ua " + dato.getCodElemento());
            }

            //Primero desindexamos por raiz
            plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_UNIDAD_ADMINISTRATIVA);

            ResultadoAccion resultado = plugin.indexarContenido(procedimientoSolrDTO.getDataIndexacion());
            if (resultado != null && resultado.isCorrecto()) {
                mensaje.append("La UA " + dato.getCodElemento() + " s'ha indexat correctament. \n");
                return new ResultadoAccion(true, "La UA s'ha indexat correctament");
            } else {
                mensaje.append("La UA " + dato.getCodElemento() + " NO s'ha indexat correctament, error:" + resultado.getMensaje() + " \n");
                return resultado;
            }

        } catch (Exception e) {
            mensaje.append("La UA " + dato.getCodElemento() + " NO s'ha indexat correctament, error:" + e.getLocalizedMessage() + " \n");
            return new ResultadoAccion(false, e.getMessage());
        }
    }

    private ResultadoAccion desindexarNormativa(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalNormativas++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_NORMATIVA);
            if (resultado != null && resultado.isCorrecto()) {
                totalNormativasOK++;
                mensaje.append("La normativa " + dato.getCodElemento() + " s'ha desindexat correctament. \n");
                return new ResultadoAccion(true, "La normativa s'ha desindexat correctament");
            } else {
                totalNormativasERROR++;
                mensaje.append("La normativa " + dato.getCodElemento() + " NO s'ha desindexat correctament, error:" + resultado.getMensaje() + " \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            totalNormativasERROR++;
            mensaje.append("La normativa " + dato.getCodElemento() + " NO s'ha desindexat correctament, error:" + e.getLocalizedMessage() + " \n");
            return new ResultadoAccion(false, e.getMessage());
        }
    }

    private ResultadoAccion indexarNormativa(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {

        try {
            ProcedimientoSolrDTO procedimientoSolrDTO = normativaService.findDataIndexacionNormById(dato.getCodElemento());

            if (procedimientoSolrDTO.getNormativaDTO() == null) {
                return new ResultadoAccion(false, "No existeix la normativa " + dato.getCodElemento());
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
                                    IndexFile indexFile = normativaService.findDataIndexacionDocNormById(procedimientoSolrDTO.getNormativaDTO(), doc, docTraduccion, procedimientoSolrDTO.getPathUAs());
                                    ResultadoAccion resultadoDoc = plugin.indexarFichero(indexFile);
                                    if (resultadoDoc != null && !resultadoDoc.isCorrecto()) {
                                        mensaje.append("La normativa " + dato.getCodElemento() + " s'ha indexat correctament però algún document ha dado problemas \n");
                                        mensaje.append("El fitxer document " + docTraduccion.getFicheroDTO().getCodigo() + " no s'ha indexat, error:" + resultadoDoc.getMensaje() + " \n");
                                        return new ResultadoAccion(false, resultadoDoc.getMensaje());
                                    }
                                }
                            }
                        }
                    }
                }
                mensaje.append("La normativa " + dato.getCodElemento() + " s'ha indexat correctament. \n");
                return new ResultadoAccion(true, "La normativa s'ha indexat correctament");
            } else {
                mensaje.append("La normativa " + dato.getCodElemento() + " NO s'ha indexat. Error:" + resultado.getMensaje() + " \n");
                return resultado;
            }

        } catch (Exception e) {
            mensaje.append("La normativa " + dato.getCodElemento() + " NO s'ha indexat. Error:" + e.getLocalizedMessage() + " \n");
            return new ResultadoAccion(false, e.getMessage());
        }
    }


    private ResultadoAccion desindexarProcedimiento(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalProcedimientos++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_PROCEDIMIENTO);
            if (resultado != null && resultado.isCorrecto()) {
                totalProcedimientosOK++;
                mensaje.append("El procedimiento " + dato.getCodElemento() + "  s'ha desindexat correctament. \n");
                return new ResultadoAccion(true, "El procediment s'ha desindexat correctament");
            } else {
                totalProcedimientosERROR++;
                mensaje.append("El procedimiento " + dato.getCodElemento() + " NO s'ha desindexat correctament. Error:" + resultado.getMensaje() + " \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            totalProcedimientosERROR++;
            mensaje.append("El procedimiento " + dato.getCodElemento() + " NO s'ha desindexat correctament. Error:" + e.getLocalizedMessage() + " \n");
            return new ResultadoAccion(false, e.getMessage());
        }

    }

    private ResultadoAccion indexarProcedimiento(IndexacionDTO indexacionDTO, IPluginIndexacion plugin, StringBuilder mensaje) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        totalProcedimientos++;

        if (publicado) {
            try {

                //Primero desindexamos por raiz
                plugin.desindexar(indexacionDTO.getCodElemento().toString(), EnumCategoria.ROLSAC_NORMATIVA);

                ProcedimientoSolrDTO procedimiento = procedimientoService.findDataIndexacionProcById(codigoWF);
                //Si es común, no se indexa
                if (procedimiento.getProcedimientoDTO().esComun()) {
                    totalProcedimientosOK++;
                    mensaje.append("El procediment " + indexacionDTO.getCodElemento() + " es comú i no s'indexa \n");
                    return new ResultadoAccion(true, "El procediment es comú i no s'indexa");
                } else {
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
                                            IndexFile datoIndexadoDoc = procedimientoService.findDataIndexacionProcDoc(procedimiento.getProcedimientoDTO(), doc, fichero, procedimiento.getPathUA());
                                            ResultadoAccion resultadoDoc = plugin.indexarFichero(datoIndexadoDoc);
                                            if (resultadoDoc != null && resultadoDoc.isCorrecto()) {
                                                totalProcedimientosDOCOK++;
                                            } else {
                                                totalProcedimientosDOCERROR++;
                                                todoCorrecto = false;
                                                mensajesIncorrectos.append(" ProcedimientoDoc: " + doc.getCodigo() + " . ERROR:" + resultadoDoc.getMensaje() + "\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (procedimiento.getProcedimientoDTO().getTramites() != null) {
                            for (ProcedimientoTramiteDTO tramite : procedimiento.getProcedimientoDTO().getTramites()) {
                                totalTramites++;
                                DataIndexacion datoIndexadoTram = procedimientoService.findDataIndexacionTram(tramite, procedimiento.getProcedimientoDTO(), procedimiento.getPathUA());
                                ResultadoAccion resultadoTramite = plugin.indexarContenido(datoIndexadoTram);
                                if (resultadoTramite != null && resultadoTramite.isCorrecto()) {
                                    totalTramitesOK++;

                                    if (tramite.getListaDocumentos() != null) {

                                        for (ProcedimientoDocumentoDTO doc : tramite.getListaDocumentos()) {
                                            if (doc.getDocumentos() != null) {
                                                for (DocumentoTraduccion fichero : doc.getDocumentos().getTraducciones()) {
                                                    if (fichero.getFicheroDTO() != null && fichero.getFicheroDTO().getCodigo() != null) {
                                                        totalTramitesDOC++;
                                                        IndexFile datoIndexadoDoc = procedimientoService.findDataIndexacionTramDoc(tramite, procedimiento.getProcedimientoDTO(), doc, fichero, procedimiento.getPathUA());
                                                        ResultadoAccion resultadoDoc = plugin.indexarFichero(datoIndexadoDoc);
                                                        if (resultadoDoc != null && resultadoDoc.isCorrecto()) {
                                                            totalTramitesDOCOK++;
                                                        } else {
                                                            totalTramitesDOCERROR++;
                                                            todoCorrecto = false;
                                                            mensajesIncorrectos.append(" TramiteDoc: " + doc.getCodigo() + " . ERROR:" + resultadoDoc.getMensaje() + "\n");
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
                                                        IndexFile datoIndexadoDoc = procedimientoService.findDataIndexacionTramDoc(tramite, procedimiento.getProcedimientoDTO(), doc, fichero, procedimiento.getPathUA());
                                                        ResultadoAccion resultadoDoc = plugin.indexarFichero(datoIndexadoDoc);
                                                        if (resultadoDoc != null && resultadoDoc.isCorrecto()) {
                                                            totalTramitesDOCOK++;
                                                        } else {
                                                            totalTramitesDOCERROR++;
                                                            todoCorrecto = false;
                                                            mensajesIncorrectos.append(" TramiteModelo: " + doc.getCodigo() + " . ERROR:" + resultadoDoc.getMensaje() + "\n");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }


                                } else {
                                    totalTramitesERROR++;
                                    todoCorrecto = false;
                                    mensajesIncorrectos.append(" Tramite: " + tramite.getCodigo() + " . ERROR:" + resultadoTramite.getMensaje());
                                }
                            }
                        }

                        if (todoCorrecto) {
                            totalProcedimientosOK++;
                            mensaje.append("El procediment " + indexacionDTO.getCodElemento() + " i relacionats s'ha indexat correctament \n");
                            return new ResultadoAccion(true, "El procediment s'ha indexat correctament");
                        } else {
                            totalProcedimientosERROR++;
                            mensaje.append("El procediment " + indexacionDTO.getCodElemento() + " s'ha indexat però \n");
                            mensaje.append(mensajesIncorrectos.toString() + "\n");
                            return new ResultadoAccion(false, "Un tràmit o document de doc/tram s'ha indexat incorrectament" + mensajesIncorrectos.toString());
                        }
                    } else {
                        totalProcedimientosERROR++;
                        mensaje.append("El procediment " + indexacionDTO.getCodElemento() + " no s'ha indexat. Error:" + resultado.getMensaje() + " \n");
                        return resultado;
                    }
                }
            } catch (Exception e) {
                totalProcedimientosERROR++;
                mensaje.append("El procediment " + indexacionDTO.getCodElemento() + " no s'ha indexat. Error:" + e.getLocalizedMessage() + " \n");
                return new ResultadoAccion(false, e.getMessage());
            }
        } else {
            totalProcedimientosOK++;
            mensaje.append("El procediment " + indexacionDTO.getCodElemento() + " no s'ha indexat perque no està publicat.\n");
            return new ResultadoAccion(true, "El procediment no està publicat");
        }
    }


    private ResultadoAccion desindexarServicio(IndexacionDTO dato, IPluginIndexacion plugin, StringBuilder mensaje) {
        // Si la accion es 2, es desindexar
        try {
            totalServicios++;
            ResultadoAccion resultado = plugin.desindexar(dato.getCodElemento().toString(), EnumCategoria.ROLSAC_SERVICIO);
            if (resultado != null && resultado.isCorrecto()) {
                totalServiciosOK++;
                mensaje.append("El servei " + dato.getCodElemento() + " s'ha desindexat correctament \n");
                return new ResultadoAccion(true, "El servei s'ha desindexat correctament");
            } else {
                totalServiciosERROR++;
                mensaje.append("El servei " + dato.getCodElemento() + " no s'ha desindexat. Error: " + resultado.getMensaje() + " \n");
                return resultado;
            }
        } catch (IPluginIndexacionExcepcion e) {
            totalServiciosERROR++;
            mensaje.append("El servei " + dato.getCodElemento() + " no s'ha desindexat. Error: " + e.getLocalizedMessage() + " \n");
            return new ResultadoAccion(false, e.getMessage());
        }
    }

    private ResultadoAccion indexarServicio(IndexacionDTO indexacionDTO, IPluginIndexacion plugin, StringBuilder mensaje) {
        Long codigoWF = procedimientoService.getCodigoPublicado(indexacionDTO.getCodElemento());
        boolean publicado = codigoWF != null;
        indexacionDTO.setFechaIntentoIndexacion(new Date());
        if (publicado) {
            try {
                //Primero desindexamos por raiz
                plugin.desindexar(indexacionDTO.getCodElemento().toString(), EnumCategoria.ROLSAC_NORMATIVA);

                ProcedimientoSolrDTO servicio = procedimientoService.findDataIndexacionServById(codigoWF);
                ResultadoAccion resultado = plugin.indexarContenido(servicio.getDataIndexacion());
                if (resultado.isCorrecto()) {
                    mensaje.append("El servei " + indexacionDTO.getCodElemento() + " s'ha desindexat correctament \n");
                } else {
                    mensaje.append("El servei " + indexacionDTO.getCodElemento() + " NO s'ha desindexat correctament. Error:" + resultado.getMensaje() + " \n");
                }
                return resultado;
            } catch (Exception e) {
                mensaje.append("El servei " + indexacionDTO.getCodElemento() + " NO s'ha desindexat correctament. Error:" + e.getLocalizedMessage() + " \n");
                return new ResultadoAccion(false, e.getMessage());
            }
        } else {
            mensaje.append("El servei " + indexacionDTO.getCodElemento() + " NO s'ha desindexat, no està publicat. \n");
            return new ResultadoAccion(true, "El servei no està publicat");
        }
    }

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
    }

}
