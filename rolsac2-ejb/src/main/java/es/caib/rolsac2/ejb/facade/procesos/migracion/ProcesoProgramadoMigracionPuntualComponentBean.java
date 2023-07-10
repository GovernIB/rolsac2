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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private final static Integer TAMANYO_BLOQUE = 10;

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
            String cargarUsuarios = params.getPropiedad("cargarUsuarios");
            String cargarDatosTipos = params.getPropiedad("cargarDatosTipos");
            String cargarDatosUA = params.getPropiedad("cargarUas");
            String cargarDatosNormativas = params.getPropiedad("cargarNormativas");
            String cargarDatosProcedimientos = params.getPropiedad("cargarProcedimientos");
            Long entidad = Long.valueOf(params.getPropiedad("entidad"));
            Long uaRaiz = Long.valueOf(params.getPropiedad("uaRaiz"));
            String usuarios = params.getPropiedad("usuarios");
            detalles.addPropiedades(params);

            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            res.setFinalizadoOk(true);

            //Primero desactivamos posibles restricciones (no hace ya falta)
            //migracionService.ejecutarDesactivarRestricciones();

            if (borrarDatos != null && "true".equals(borrarDatos)) {
                String result = migracionService.ejecutarMetodo("MIGRAR_BORRARDATOS", entidad.toString(), uaRaiz.toString()) + "\n";
                mensajeTraza.append(result);
                detalles.addPropiedad("Borrar datos", "Ejecutado correctamente");
            }

            if (cargarUsuarios != null && "true".equals(cargarUsuarios)) {
                String result = migracionService.migrarUsuarios();
                mensajeTraza.append(result);
                detalles.addPropiedad("Migrar usuarios", "Ejecutado correctamente");
            }


            if (cargarDatosTipos != null && "true".equals(cargarDatosTipos)) {
                detalles.addPropiedad("Cargar datos tipo", "Ejecutado correctamente");
            }

            if (cargarDatosUA != null && "true".equals(cargarDatosUA)) {

                mensajeTraza.append("INICI MIGRACIO UAs \n");
                List<BigDecimal> idUAs = migracionService.getUAs(idEntidad, uaRaiz);
                if (idUAs != null && !idUAs.isEmpty()) {
                    // Recorre el array en bloques de 10 elementos
                    for (int i = 0; i < idUAs.size(); i += TAMANYO_BLOQUE) {

                        // Obtiene el bloque actual de 10 elementos
                        List<BigDecimal> bloque = obtenerBloque(idUAs, i, TAMANYO_BLOQUE);
                        String result = migracionService.migrarUAs(bloque, entidad, uaRaiz, usuarios);
                        mensajeTraza.append(result);
                    }

                }
                mensajeTraza.append("FI MIGRACIO UAs \n");
                detalles.addPropiedad("Cargar datos UA", "Ejecutado correctamente");
            }

            if (cargarDatosNormativas != null && "true".equals(cargarDatosNormativas)) {
                String result = migracionService.migrarNormativas(entidad);
                mensajeTraza.append(result);
                detalles.addPropiedad("Cargar datos Normativas", "Ejecutado correctamente");
            }

            if (cargarDatosProcedimientos != null && "true".equals(cargarDatosProcedimientos)) {
                String result = migracionService.migrarProcedimientos(entidad, uaRaiz);
                mensajeTraza.append(result);
                detalles.addPropiedad("Cargar datos Procedimientos", "Ejecutado correctamente");

                String result2 = migracionService.migrarServicios(entidad, uaRaiz);
                mensajeTraza.append(result2);
                detalles.addPropiedad("Cargar datos Servicios", "Ejecutado correctamente");
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
            res.setMensajeErrorTraza(mensajeTraza.toString() + "Se ha producido un error no controlado en el proceso migracion. " + e.getLocalizedMessage());
            res.setFinalizadoOk(false);
        } finally {
            try {
                // migracionService.ejecutarActivarRestriccionesSecuencias();
                migracionService.ejecutarRevisarSecuencias();
            } catch (Exception e2) {
                log.error("Error seteando secuencias y restricciones", e2);
            }
        }
        return res;
    }

    //Método para obtener un bloque de elementos
    private List<BigDecimal> obtenerBloque(List<BigDecimal> idUAs, int inicio, Integer tamanoBloque) {
        int fin = Math.min(inicio + tamanoBloque, idUAs.size());  // Calcula el índice final del bloque
        int tamano = fin - inicio;  // Calcula el tamaño real del bloque

        List<BigDecimal> bloque = new ArrayList<>();  // Crea un nuevo array para el bloque

        // Copia los elementos del array original al bloque
        for (int i = inicio; i < fin; i++) {
            bloque.add(idUAs.get(i));
        }

        return bloque;  // Retorna el bloque de elementos
    }



}
