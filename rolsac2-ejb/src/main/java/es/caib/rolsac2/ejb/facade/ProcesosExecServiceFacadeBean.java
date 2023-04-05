package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.facade.procesos.ProcesosAsyncTaskFacade;
import es.caib.rolsac2.ejb.facade.procesos.ProcesosExecComponentFacade;
import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.service.facade.ProcesosExecServiceFacade;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Implementación ejecución procesos. No transaccional para no interferir en tx según duración procesos.
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(ProcesosExecServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProcesosExecServiceFacadeBean implements ProcesosExecServiceFacade {

    /**
     * SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Componente ejecucion procesos asincronos.
     */
    @Inject
    ProcesosAsyncTaskFacade procesosAsyncTaskFacade;

    /**
     * Componente utilidad ejecucion procesos.
     */
    @Inject
    ProcesosExecComponentFacade procesosExecComponentFacade;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void ejecutarProceso(final String idProceso, Long idEntidad) {
        procesosAsyncTaskFacade.ejecutarProceso(idProceso, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void ejecutarProceso(final String idProceso, final ListaPropiedades params, Long idEntidad) {
        procesosAsyncTaskFacade.ejecutarProceso(idProceso, params, idEntidad);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<String> calcularProcesosEjecucion(Long idEntidad) {
        return procesosExecComponentFacade.calcularProcesosEjecucion(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean verificarMaestro(final String instanciaId) {
        return procesosExecComponentFacade.verificarMaestro(instanciaId);
    }

}
