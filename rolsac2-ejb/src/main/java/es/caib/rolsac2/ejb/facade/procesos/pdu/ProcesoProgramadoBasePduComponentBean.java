package es.caib.rolsac2.ejb.facade.procesos.pdu;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.pdu.api.IPluginPdu;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RRespuestaImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.ResultadoPdu;
import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.ejb.facade.procesos.sia.SiaUtils;
import es.caib.rolsac2.service.facade.PduServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoPduFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
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
public abstract class ProcesoProgramadoBasePduComponentBean implements ProcesoProgramadoFacade {

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    @Inject
    private ProcesoServiceFacade procesoServiceFacade;

    @Inject
    private PduServiceFacade pduServiceFacade;

    private int totalProcedimientos = 0;
    private int totalProcedimientosOK = 0;
    private int totalProcedimientosERROR = 0;
    private int totalServicios = 0;
    private int totalServiciosOK = 0;
    private int totalServiciosERROR = 0;

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoBasePduComponentBean.class);

    public ResultadoProcesoProgramado ejecutarPadre(final Long instanciaProceso, final ListaPropiedades params, Long idEntidad) {

        final ListaPropiedades detalles = new ListaPropiedades();
        ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaInicio = "La dada de inici es " + sdf.format(new Date());
        detalles.addPropiedad("Informació del procés", fechaInicio);

        try {

            String accion = params.getPropiedad("accion") != null ? params.getPropiedad("accion") : Constantes.INDEXAR_SIA_PENDIENTES;

            detalles.addPropiedades(params);

            Pagina<IndexacionPDUDto> datos = null;

            IPluginPdu plugin = null;

            // PLUGIN
            try {
                plugin = (IPluginPdu) systemServiceFacade.obtenerPluginEntidad(TypePluginEntidad.PDU, idEntidad);
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

            // DATOS
            if (accion.equals(Constantes.INDEXAR_SIA_PENDIENTES)) {
                datos = pduServiceFacade.getPendientesIntegrar(idEntidad);

            } else if (accion.equals(Constantes.INDEXAR_SIA_COMPLETO)) {

                datos = pduServiceFacade.getProcedimientosIntegrado(idEntidad);
            }


            // ACCIÓN
           res = indexarDatos(datos, plugin, detalles);


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


    private ResultadoProcesoProgramado indexarDatos(Pagina<IndexacionPDUDto> datos, IPluginPdu plugin, final ListaPropiedades detalles){

        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try{
            inicializarTotalesACero();
            StringBuilder mensajeTraza = new StringBuilder();

            if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {

                for (IndexacionPDUDto dato : datos.getItems()) {
                    ResultadoAccion resultado = null;
                    switch (TypeIndexacion.fromString(dato.getTipo())) {
                        case PROCEDIMIENTO:
                            totalProcedimientos++;
                            resultado = indexarPdu(dato, plugin, mensajeTraza);

                            if(resultado.isCorrecto()){
                                totalProcedimientosOK++;
                            }else{
                                totalProcedimientosERROR++;
                            }
                            break;
                        case SERVICIO:
                            totalServicios++;
                            resultado = indexarPdu(dato, plugin, mensajeTraza);

                            if(resultado.isCorrecto()){
                                totalServiciosOK++;
                            }else{
                                totalServiciosERROR++;
                            }
                    }

//                    procedimientoService.actualizarPDU(dato, resultado);
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

    private ResultadoAccion indexarPdu(IndexacionPDUDto indexacionDTO, IPluginPdu plugin, StringBuilder mensajeTraza) {

        indexacionDTO.setFechaIntentoIndexacion(new Date());

        Pair<RPeticionImportarEnlace, String> peticion = pduServiceFacade.crearPeticionPdu(indexacionDTO);

        if(peticion.getLeft() == null){
            mensajeTraza.append("El procediment/servei " + indexacionDTO.getCodElemento() + " no s'ha indexat, error:" + peticion.getRight() + " \n");
            return new ResultadoAccion(false, peticion.getRight());
        }

        try {

            RRespuestaImportarEnlace resultadoPDU = plugin.importarEnlace(peticion.getLeft());

            if(resultadoPDU != null && HttpStatus.SC_OK == resultadoPDU.getCodigoEstado() ){

                ResultadoPdu resultado = new ResultadoPdu(true, "El procediment s'ha indexat correctament",
                        resultadoPDU);

                pduServiceFacade.actualizarPDU(indexacionDTO, resultado);

                mensajeTraza.append("El procediment/servei " + indexacionDTO.getCodElemento() + " s'ha indexat correctament. \n");
                return resultado;
            } else {
                String mensajePdu = resultadoPDU != null ? resultadoPDU.getMesaje() : "Error desconocido";
                mensajeTraza.append("El procediment/servei " + indexacionDTO.getCodElemento() + " NO s'ha indexat correctament, error:" + mensajePdu + ". \n");
                return new ResultadoAccion(false, "El procediment " + indexacionDTO.getCodElemento() + " NO s'ha indexat correctament, error:" + mensajePdu);
            }
        } catch (Exception e) {
            mensajeTraza.append("El procediment/servei " + indexacionDTO.getCodElemento() + " no s'ha indexat, error:" + e.getLocalizedMessage() + " \n");
            log.error("Error en la indexacion PDU de procediments / serveis", e);
            return new ResultadoAccion(false, e.getLocalizedMessage());
        }

    }



    private void inicializarTotalesACero() {
        totalProcedimientos = 0;
        totalProcedimientosOK = 0;
        totalProcedimientosERROR = 0;
        totalServicios = 0;
        totalServiciosOK = 0;
        totalServiciosERROR = 0;
    }


//    private RRespuestaImportarEnlace borradoPdu(IndexacionPDUDto indexacionDTO, IPluginPdu plugin, EntidadRaizDTO entidadRaiz, ProcedimientoBaseDTO procedimiento) {
//        EnvioSIA sia = new EnvioSIA();
//        if (procedimiento.getCodigoSIA() != null) {
//            sia.setIdSia(procedimiento.getCodigoSIA().toString());
//        } else if (indexacionDTO.getCodigoSIA() != null) {
//            sia.setIdSia(indexacionDTO.getCodigoSIA().toString());
//        }
//        sia.setOperacion(SiaUtils.ESTADO_BAJA);
//        sia.setCdExpediente(procedimiento.getCodigo().toString());
//
//        if (entidadRaiz != null) {
//            sia.setUsuario(entidadRaiz.getUser());
//            sia.setPassword(entidadRaiz.getPwd());
//        }
//
//        RRespuestaImportarEnlace resultado = null;
//        try {
//            resultado = plugin.importarEnlace(null);// TODO
//        } catch (final Exception exception) {
//            log.error("Se ha producido un error enviando el dato a SIA de un borrado de proc/serv " + indexacionDTO.getCodElemento(), exception);
////            return new ResultadoSIA(ResultadoSIA.RESULTADO_ERROR, exception.getLocalizedMessage());
//        }
//        return resultado;
//    }

}
