package es.caib.rolsac2.ejb.facade.procesos.solr;

import es.caib.rolsac2.commons.plugins.indexacion.api.IPluginIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.solr.ProcedimientoBaseSolr;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Proceso solr.
 *
 * @author Indra
 */
@Stateless(name = "procesoProgramadoSolrComponent")
@Local(ProcesoProgramadoSolrComponentBean.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoProgramadoSolrComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "SOLR";

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private NormativaServiceFacade normativaService;


    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoSolrComponentBean.class);

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final ListaPropiedades params, Long idEntidad) {
        log.info("Ejecución proceso solr");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();


        /*if (params == null || params.getPropiedad("accion") == null || params.getPropiedad("entidad") == null) {
            res.setFinalizadoOk(false);
            detalles.addPropiedad("Informació del procés", "No están bien especificados los parámetros para la indexación");
            res.setDetalles(detalles);
            return res;
        } else {*/

        String accion = params.getPropiedad("accion");

        detalles.addPropiedades(params);
        List<ProcedimientoBaseSolr> procedimientos = procedimientoService.obtenerPendientesIndexar(true, Constantes.PROCEDIMIENTO);
        List<Long> normativas = null; //normativaService.obtenerPendientesIndexar();

        IPluginIndexacion plugin = (IPluginIndexacion) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.INDEXACION, idEntidad);

        if (plugin == null) {
            res.setFinalizadoOk(false);
            detalles.addPropiedad("Informació del procés", "No está especificado el plugin de indexación");
            res.setDetalles(detalles);
            return res;
        }

        if (procedimientos != null) {
            for (ProcedimientoBaseSolr proc : procedimientos) {
                Long codigoWF = procedimientoService.getCodigoPublicado(proc.getCodigo());
                boolean publicado = codigoWF != null;
                proc.setFechaIndexacion(new Date());
                if (publicado) {
                    try {
                        boolean todoCorrecto = true;
                        DataIndexacion dato;
                        //Mandar a indexar
                        proc.setMantenerIndexado(false);
                        if (proc.esTipoProcedimiento()) {
                            ProcedimientoSolrDTO procedimiento = procedimientoService.findDataIndexacionProcById(codigoWF);
                            ResultadoAccion resultado = plugin.indexarContenido(procedimiento.getDataIndexacion());
                            if (resultado != null && resultado.isCorrecto()) {
                                if (procedimiento.getProcedimientoDTO().getTramites() != null) {
                                    for (ProcedimientoTramiteDTO tramite : procedimiento.getProcedimientoDTO().getTramites()) {
                                        dato = procedimientoService.findDataIndexacionTram(tramite, procedimiento.getProcedimientoDTO(), procedimiento.getPathUA());
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
                            ProcedimientoSolrDTO servicio = procedimientoService.findDataIndexacionServById(codigoWF);
                            ResultadoAccion resultado = plugin.indexarContenido(servicio.getDataIndexacion());
                            if (resultado != null && resultado.isCorrecto()) {
                                proc.setMensajeError(resultado.getMensaje());
                                proc.setMantenerIndexado(false);
                                todoCorrecto = false;
                            }
                        }

                        if (todoCorrecto) {
                            proc.setMensajeError("El procediment s'ha indexat correctament");
                            proc.setMantenerIndexado(false);
                        } else {
                            proc.setMantenerIndexado(true);
                        }
                        procedimientoService.actualizarSolr(proc);
                    } catch (Exception e) {
                        proc.setMensajeError(e.getMessage());
                        proc.setMantenerIndexado(true);
                    }
                    procedimientoService.actualizarSolr(proc);
                } else {
                    proc.setMensajeError("El procediment no està publicat");
                    proc.setMantenerIndexado(false);
                    procedimientoService.actualizarSolr(proc);
                }
            }

           /* if (servicios != null) {
                for (Long serv : servicios) {

                }
            }*/

            if (normativas != null) {
                for (Long norm : normativas) {
                    //Intentar indexar
                }
            }

            //Mandarlo a indexar


            res.setFinalizadoOk(true);
            if (params.getPropiedad("valida") != null) {
                Boolean valida = params.getPropiedad("valida").equals("true");
                if (!valida) {
                    res.setFinalizadoOk(false);
                    res.setMensajeError("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ullamcorper semper laoreet. Nulla ut ex felis. " +
                            "Proin metus urna, venenatis blandit commodo sit amet, dictum eget diam. Nullam tempor tempus nunc, commodo ornare elit dictum id. " +
                            "Suspendisse semper blandit felis et facilisis. Donec sodales quam vitae lorem iaculis aliquam. Quisque faucibus, lorem in rutrum egestas, " +
                            "turpis orci lobortis elit, eget sagittis libero ligula at nulla. Praesent consectetur nisl ac orci faucibus fringilla ac a libero. " +
                            "Etiam tristique mauris massa, ac laoreet dolor tincidunt ac. Fusce finibus eget ex a mollis. Aliquam vel aliquam velit. " +
                            "Curabitur placerat mi non eros commodo molestie. Proin venenatis turpis tincidunt felis dignissim pretium id nec lectus.");
                } else {
                    res.setFinalizadoOk(true);
                }
            }
        }
        detalles.addPropiedad("Informació del procés", "Indexació de servicios correcte");
        detalles.addPropiedad("Informació del procés", "Indexació de procedimientos correcte");
        res.setDetalles(detalles);
        return res;
    }


}
