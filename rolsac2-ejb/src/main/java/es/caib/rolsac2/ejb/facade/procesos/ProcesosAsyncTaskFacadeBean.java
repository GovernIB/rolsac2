package es.caib.rolsac2.ejb.facade.procesos;

import es.caib.rolsac2.ejb.facade.procesos.solr.ProcesoProgramadoSolrComponentBean;
import es.caib.rolsac2.service.exception.ProcesoNoExistenteException;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Implementación ejecución procesos. No transaccional para no interferir en tx según duración procesos.
 *
 * @author Indra
 */
@Stateless
@Local(ProcesosAsyncTaskFacade.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProcesosAsyncTaskFacadeBean implements ProcesosAsyncTaskFacade {

    /**
     * Componente ejecucion procesos.
     */
    @Inject
    ProcesosExecComponentFacade procesosExecComponent;

    // Lista de procesos
    @EJB(name = "procesoProgramadoTestComponent")
    private ProcesoProgramadoFacade procesoProgramadoTestComponent;

    @EJB(name = "procesoProgramadoSolrComponent")
    private ProcesoProgramadoSolrComponentBean procesoProgramadoSolrComponent;

    @Override
    @Asynchronous
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void ejecutarProceso(final String idProceso) {
        final ProcesoProgramadoFacade proceso = obtenerProceso(idProceso);
        final ListaPropiedades params = obtenerParamsProceso(idProceso);
        final Long idEntidad = obtenerEntidad(idProceso);
        final Long instanciaProceso = procesosExecComponent.auditarInicioProceso(idProceso);
        try {
            final ResultadoProcesoProgramado resultadoProceso = proceso.ejecutar(params, idEntidad);
            procesosExecComponent.auditarFinProceso(idProceso, instanciaProceso, resultadoProceso);
        } catch (final Exception ex) {
            final ResultadoProcesoProgramado resultadoProcesoProgramado = new ResultadoProcesoProgramado();
            resultadoProcesoProgramado.setFinalizadoOk(false);
            resultadoProcesoProgramado.setMensajeError("Error no controlado: " + ex.getMessage());
            procesosExecComponent.auditarFinProceso(idProceso, instanciaProceso, resultadoProcesoProgramado);
        }



    }

    /**
     * Obtiene parametros de la configuración de procesos.
     *
     * @param idProceso id proceso
     * @return parametros proceso.
     */
    private ListaPropiedades obtenerParamsProceso(final String idProceso) {
        final ListaPropiedades props = new ListaPropiedades();
        final List<Propiedad> params = procesosExecComponent.obtenerParametrosProceso(idProceso);
        if (params != null) {
            for (final Propiedad p : params) {
                props.addPropiedad(p.getCodigo(), p.getValor());
            }
        }
        return props;
    }

    /**
     * Obtiene parametros de la configuración de procesos.
     *
     * @param idProceso id proceso
     * @return parametros proceso.
     */
    private Long obtenerEntidad(final String idProceso) {
        Long entidad = procesosExecComponent.obtenerEntidad(idProceso);
        return entidad;
    }


    /**
     * Obtiene proceso a partir de codigo proceso.
     *
     * @param idProceso id proceso
     * @return proceso
     */
    private ProcesoProgramadoFacade obtenerProceso(final String idProceso) {

        ProcesoProgramadoFacade proceso = null;
        for (final Field field : this.getClass().getDeclaredFields()) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj instanceof ProcesoProgramadoFacade && ((ProcesoProgramadoFacade) obj).getCodigoProceso().equals(idProceso)) {
                    proceso = (ProcesoProgramadoFacade) obj;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // Pasamos a siguiente campo
            }
        }

        if (proceso == null) {
            throw new ProcesoNoExistenteException(idProceso);
        }

        return proceso;
    }


}
