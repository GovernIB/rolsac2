package es.caib.rolsac2.ejb.facade;


import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;
import es.caib.rolsac2.service.facade.ProcesosExecServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * Proceso Timer service.
 *
 * @author Indra
 */

@Stateless
@Local(ProcesoTimerServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@RunAs("RS2_SUP")
public class ProcesoTimerServiceFacadeBean implements ProcesoTimerServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProcesoTimerServiceFacadeBean.class);

    private static String idInstancia = "";

    @Inject
    private ProcesosExecServiceFacade procesosExecServiceFacade;

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Inject
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @Resource
    TimerService timerService;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void initTimer(String idInstancia) {
        this.setIdInstancia(idInstancia);
        String cron = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PROCESOS_AUTOMATICOS_CRON);
        timerService.createCalendarTimer(parseCron(cron), new TimerConfig("ProcesosTimer", false));
    }

    /**
     * Proceso que se lanza en el intérvalo indicado
     */
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    @Timeout
    public void procesar() {
        LOG.info("Procesos ROLSAC2 desde EJB");
        if (procesosExecServiceFacade.verificarMaestro(this.idInstancia)) {
            LOG.debug("Es maestro. Recupera procesos a lanzar..,");
            final List<EntidadDTO> entidades = administracionSupServiceFacade.findEntidadActivas();
            for (EntidadDTO entidad : entidades) {
                final List<String> procesos = procesosExecServiceFacade.calcularProcesosEjecucion(entidad.getCodigo());
                for (final String p : procesos) {
                    try {
                        LOG.debug("Lanza proceso " + p);
                        procesosExecServiceFacade.ejecutarProceso(p, entidad.getCodigo());
                    } catch (final Exception ex) {
                        LOG.error("Error al lanzar proceso " + p + ": " + ex.getMessage());
                    }
                }
            }
        } else {
            LOG.debug("No es maestro. No lanza procesos");
        }
    }

    /**
     * Método utilizado para el procesado manual de los procesos
     */
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void procesadoManual(String proceso, Long idEntidad) {
        try {
            LOG.debug("Lanza proceso " + proceso);
            procesosExecServiceFacade.ejecutarProceso(proceso, idEntidad);
        } catch (final Exception ex) {
            LOG.error("Error al lanzar proceso " + proceso + ": " + ex.getMessage());
        }
    }

    /**
     * Método utilizado para el procesado manual de los procesos
     */
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void procesadoManual(String proceso, ListaPropiedades listaPropiedades, Long idEntidad) {
        try {
            LOG.debug("Lanza proceso " + proceso);
            procesosExecServiceFacade.ejecutarProceso(proceso, listaPropiedades, idEntidad);
        } catch (final Exception ex) {
            LOG.error("Error al lanzar proceso " + proceso + ": " + ex.getMessage());
        }
    }

    /**
     * Se utiliza para parsear la expresión cron definida por properties y convertirla en
     *
     * @param cron
     * @return ScheduleExpression intérvalo de tiempo en el que se lanzan los procesos automáticos
     */
    private ScheduleExpression parseCron(String cron) {
        String[] crons = cron.split(" ");
        ScheduleExpression sc = new ScheduleExpression();
        if (crons.length == 5) {
            sc.minute(crons[0]);
            sc.hour(crons[1]);
            sc.dayOfMonth(crons[2]);
            sc.month(crons[3]);
            sc.dayOfWeek(crons[4]);
            return sc;
        } else if (crons.length == 6) {
            sc.second(crons[0]);
            sc.minute(crons[1]);
            sc.hour(crons[2]);
            sc.dayOfMonth(crons[3]);
            sc.month(crons[4]);
            sc.dayOfWeek(crons[5]);
            return sc;
        } else {
            //Debería de lanzarse una excepción
            return sc;
        }
    }

    public String getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(final String idInstancia) {
        this.idInstancia = idInstancia;
    }
}
