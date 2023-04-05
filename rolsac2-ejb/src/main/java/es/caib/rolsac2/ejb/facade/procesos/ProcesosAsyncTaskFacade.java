package es.caib.rolsac2.ejb.facade.procesos;

import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Asynchronous;

/**
 * Component con funcionalidades para ejecución procesos (asincrono).
 *
 * @author Indra
 */
public interface ProcesosAsyncTaskFacade {

    /**
     * Ejecutar proceso.
     *
     * @param idProceso identificador proceso
     */
    void ejecutarProceso(String idProceso, Long idEntidad);

    @Asynchronous
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    void ejecutarProceso(String idProceso, ListaPropiedades params, Long idEntidad);
}
