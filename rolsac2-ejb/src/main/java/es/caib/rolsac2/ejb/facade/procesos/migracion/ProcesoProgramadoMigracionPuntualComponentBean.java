package es.caib.rolsac2.ejb.facade.procesos.migracion;

import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.service.facade.MigracionServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Proceso migracion.
 *
 * @author Indra
 */
@Stateless(name = "procesoProgramadoMigracionComponent")
@Local(ProcesoProgramadoFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoProgramadoMigracionPuntualComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "MIGRA_PUNT";

    @Inject
    private ProcesoServiceFacade procesoService;


    @Inject
    private MigracionServiceFacade migracionService;

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoProgramadoMigracionPuntualComponentBean.class);

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final ListaPropiedades params, Long idEntidad) {
        log.info("Ejecución proceso migracion");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final StringBuilder mensajeTraza = new StringBuilder();
        String fechaInicio = "La dada de inici es " + sdf.format(new Date());
        detalles.addPropiedad("Informació del procés", fechaInicio);

        try {
            String borrarDatos = params.getPropiedad("borrarDatos");
            String cargarDatosTipos = params.getPropiedad("cargarDatosTipos");
            String cargarDatosUA = params.getPropiedad("cargarUas");
            String cargarDatosNormativas = params.getPropiedad("cargarNormativas");
            String cargarDatosProcedimientos = params.getPropiedad("cargarProcedimientos");
            detalles.addPropiedades(params);

            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            res.setFinalizadoOk(true);

            if (borrarDatos != null && "true".equals(borrarDatos)) {
                detalles.addPropiedad("Borrar datos", "Ejecutado correctamente");
            }

            if (cargarDatosTipos != null && "true".equals(cargarDatosTipos)) {
                detalles.addPropiedad("Cargar datos tipo", "Ejecutado correctamente");
            }

            if (cargarDatosUA != null && "true".equals(cargarDatosUA)) {
                String result = migracionService.ejecutarMetodo("MIGRAR_UAS", 1l, 1l);
                mensajeTraza.append(result);
                detalles.addPropiedad("Cargar datos UA", "Ejecutado correctamente");
            }

            if (cargarDatosNormativas != null && "true".equals(cargarDatosNormativas)) {
                detalles.addPropiedad("Cargar datos Normativas", "Ejecutado correctamente");
            }

            if (cargarDatosProcedimientos != null && "true".equals(cargarDatosProcedimientos)) {
                detalles.addPropiedad("Cargar datos Procedimientos", "Ejecutado correctamente");
            }
            detalles.addPropiedad("Fin del procés", fechaFin);


            res.setDetalles(detalles);

            res.setDetalles(detalles);
            res.setMensajeErrorTraza(mensajeTraza.toString());
        } catch (Exception e) {
            log.error("Error en el proceso programado", e);
            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza("Se ha producido un error no controlado en el proceso migracion. " + e.getLocalizedMessage());
            res.setFinalizadoOk(false);
        }
        return res;
    }



}
