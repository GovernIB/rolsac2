package es.caib.rolsac2.ejb.facade.procesos.ficheros;

import es.caib.rolsac2.ejb.facade.procesos.ProcesoProgramadoFacade;
import es.caib.rolsac2.ejb.facade.procesos.ProcesosExecComponentFacade;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
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
import java.util.List;

/**
 * Proceso borrar ficheros.
 *
 * @author Indra
 */
@Stateless(name = "procesoBorrarFicherosComponent")
@Local(ProcesoProgramadoFacade.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
// En funcion del proceso, sera o no tx por si se tiene que dividir en transacciones
public class ProcesoBorrarFicherosComponentBean implements ProcesoProgramadoFacade {

    /**
     * Código interno del proceso
     */
    private static final String CODIGO_PROCESO = "BORRAR_FIC";

    @Inject
    private ProcesoServiceFacade procesoService;

    /**
     * Componente ejecucion procesos.
     */
    @Inject
    ProcesosExecComponentFacade procesosExecComponent;

    @Inject
    SystemServiceFacade systemServiceBean;

    @Inject
    FicheroServiceFacade ficheroExternoFacade;

    /**
     * log.
     */
    private static Logger log = LoggerFactory.getLogger(ProcesoBorrarFicherosComponentBean.class);

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getCodigoProceso() {
        return CODIGO_PROCESO;
    }

    private final static Integer TAMANYO_BLOQUE = 10;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ResultadoProcesoProgramado ejecutar(final Long instanciaProceso, final ListaPropiedades params, Long idEntidad) {
        log.info("Ejecución proceso borrar fic");
        final ListaPropiedades detalles = new ListaPropiedades();
        final ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final StringBuilder mensajeTraza = new StringBuilder();
        String fechaInicio = "La dada de inici es " + sdf.format(new Date());
        detalles.addPropiedad("Informació del procés", fechaInicio);


        procesosExecComponent.auditarMitadProceso(instanciaProceso, mensajeTraza.toString() + "\n Estado actual: Inicio de la ejecución.\n");
        String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        try {
            detalles.addPropiedades(params);

            List<Long> idFicheros = ficheroExternoFacade.getFicherosTemporales();
            if (idFicheros != null && idFicheros.isEmpty()) {
                mensajeTraza.append("No hay ficheros temporales a borrar.\n");
            } else {
                mensajeTraza.append("Se van a borrar " + idFicheros.size() + " ficheros temporales.\n");
                int i = 0;
                for (Long idFichero : idFicheros) {
                    try {
                        ficheroExternoFacade.borrarFicheroTemporal(path, idFichero);
                        i++;
                        mensajeTraza.append("Borrado fichero " + i + " de " + idFicheros.size() + " (" + getPorcentaje(i, idFicheros.size()) + ")\n");
                    } catch (Exception e) {
                        mensajeTraza.append("Error al borrar fichero " + i + " de " + idFicheros.size() + " (" + getPorcentaje(i, idFicheros.size()) + ")\n");
                        log.error("Error al borrar fichero " + idFichero, e);
                    }
                }
            }

            List<Long> idFicherosMarcadosParaBorrar = ficheroExternoFacade.getFicherosMarcadosParaBorrar();
            if (idFicherosMarcadosParaBorrar != null && idFicherosMarcadosParaBorrar.isEmpty()) {
                mensajeTraza.append("No hay ficheros marcados para borrar.\n");
            } else {
                mensajeTraza.append("Se van a borrar " + idFicherosMarcadosParaBorrar.size() + " ficheros marcados para borrar.\n");
                int i = 0;
                for (Long idFichero : idFicherosMarcadosParaBorrar) {
                    try {
                        ficheroExternoFacade.borrarFicheroDefinitivamente(path, idFichero);
                        i++;
                        mensajeTraza.append("Borrado fichero " + i + " de " + idFicherosMarcadosParaBorrar.size() + " (" + getPorcentaje(i, idFicherosMarcadosParaBorrar.size()) + ")\n");
                    } catch (Exception e) {
                        mensajeTraza.append("Error al borrar fichero " + i + " de " + idFicherosMarcadosParaBorrar.size() + " (" + getPorcentaje(i, idFicherosMarcadosParaBorrar.size()) + ")\n");
                        log.error("Error al borrar fichero " + idFichero, e);
                    }
                }
            }

            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            res.setFinalizadoOk(true);


            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza(mensajeTraza.toString());

        } catch (Exception e) {
            log.error("Error en el proceso programado", e);
            String fechaFin = "La dada de fi es " + sdf.format(new Date());
            detalles.addPropiedad("Fin del procés", fechaFin);
            res.setDetalles(detalles);
            res.setMensajeErrorTraza(mensajeTraza.toString() + "Se ha producido un error no controlado en el proceso de borrar ficheros. " + e.getLocalizedMessage());
            res.setFinalizadoOk(false);
        }
        return res;
    }

    private String getPorcentaje(int i, int size) {
        return ((i * 100) / size) + "%";
    }

}
