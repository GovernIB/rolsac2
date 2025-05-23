package es.caib.rolsac2.ejb.facade.procesos.pdu;

import es.caib.rolsac2.commons.plugins.pdu.api.IPluginPdu;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RRespuestaImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.ResultadoPdu;
import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.service.facade.PduServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.utils.UtilPDU;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
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

    /**
     * log.
     */
    private static final Logger log = LoggerFactory.getLogger(ProcesoProgramadoBasePduComponentBean.class);

    /**
     * Ejecuta el proceso programado.
     *
     * @param instanciaProceso Instancia proceso
     * @param params           Parámetros
     * @param idEntidad        Entidad
     * @return Resultado del proceso
     */
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


    private ResultadoProcesoProgramado indexarDatos(Pagina<IndexacionPDUDto> datos, IPluginPdu plugin, final ListaPropiedades detalles) {

        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            inicializarTotalesACero();
            StringBuilder mensajeTraza = new StringBuilder();

            if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {

                for (IndexacionPDUDto dato : datos.getItems()) {
                    ResultadoPdu resultado = null;
                    totalProcedimientos++;
                    if (dato.getAccion() == null) {
                        ProcedimientoDTO proc = procedimientoService.findProcedimientoByCodigo(dato.getCodElemento());
                        Integer accion = UtilPDU.getAccion(proc, null, null);
                        dato.setAccion(accion);
                    }
                    if (dato.getAccion() == null) {
                        log.error("El procediment " + dato.getCodElemento() + " no té definit un tipus d'indexació");
                        mensajeTraza.append("El procediment ").append(dato.getCodElemento()).append(" no té definit un tipus d'indexació");
                        continue;
                    }

                    if (dato.getAccion().compareTo(UtilPDU.ACCION_PDU_ALTA) == 0 || dato.getAccion().compareTo(UtilPDU.ACCION_PDU_BAJA) == 0) {
                        resultado = indexarPdu(dato, plugin, mensajeTraza);
                    } else {
                        log.error("El procediment " + dato.getCodElemento() + " no té acció definida");
                        mensajeTraza.append("El procediment ").append(dato.getCodElemento()).append(" no té acció definida");
                        continue;
                    }

                    if (resultado.isCorrecto()) {
                        totalProcedimientosOK++;
                    } else {
                        totalProcedimientosERROR++;
                    }

                    if (dato.getCodigo() != null) {
                        pduServiceFacade.actualizarPDU(dato, resultado);
                    }
                }

                String fechaFin = "La dada de fi es " + sdf.format(new Date());
                res.setFinalizadoOk(true);
                if (totalProcedimientos > 0) {
                    detalles.addPropiedad("Procediments", "S'ha indexat " + totalProcedimientos + " (correctes:" + totalProcedimientosOK + " , error:" + totalProcedimientosERROR + ")");
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


    private ResultadoPdu indexarPdu(IndexacionPDUDto indexacionDTO, IPluginPdu plugin, StringBuilder mensajeTraza) {

        indexacionDTO.setFechaIntentoIndexacion(new Date());

        Pair<RPeticionImportarEnlace, String> peticion = pduServiceFacade.crearPeticionPdu(indexacionDTO);

        if (peticion.getLeft() == null) {
            mensajeTraza.append("El procediment ").append(indexacionDTO.getCodElemento()).append(" no s'ha indexat, error:").append(peticion.getRight()).append(" \n");
            return new ResultadoPdu(false, peticion.getRight());
        }

        try {

            RRespuestaImportarEnlace resultadoPDU = plugin.importarEnlace(peticion.getLeft());

            if (resultadoPDU != null && HttpStatus.SC_OK == resultadoPDU.getCodigoEstado()) {

                ResultadoPdu resultado = new ResultadoPdu(true, "El procediment s'ha indexat correctament",
                        resultadoPDU);

                pduServiceFacade.actualizarPDU(indexacionDTO, resultado);

                mensajeTraza.append("El procediment ").append(indexacionDTO.getCodElemento()).append(" s'ha indexat correctament. \n");
                return resultado;
            } else {
                String mensajePdu = resultadoPDU != null ? resultadoPDU.getMesaje() : "Error desconocido";
                mensajeTraza.append("El procediment ").append(indexacionDTO.getCodElemento()).append(" NO s'ha indexat correctament, error:").append(mensajePdu).append(". \n");
                return new ResultadoPdu(false, "El procediment " + indexacionDTO.getCodElemento() + " NO s'ha indexat correctament, error:" + mensajePdu);
            }
        } catch (Exception e) {
            mensajeTraza.append("El procediment ").append(indexacionDTO.getCodElemento()).append(" no s'ha indexat, error:").append(e.getLocalizedMessage()).append(" \n");
            log.error("Error en la indexacion PDU de procediments / serveis", e);
            return new ResultadoPdu(false, e.getLocalizedMessage());
        }

    }


    private void inicializarTotalesACero() {
        totalProcedimientos = 0;
        totalProcedimientosOK = 0;
        totalProcedimientosERROR = 0;
    }


}
