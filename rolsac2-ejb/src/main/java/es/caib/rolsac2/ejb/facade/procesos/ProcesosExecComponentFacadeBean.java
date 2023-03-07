package es.caib.rolsac2.ejb.facade.procesos;

import es.caib.rolsac2.ejb.util.procesos.InterpreteQuartz;
import es.caib.rolsac2.persistence.repository.ProcesoLogRepository;
import es.caib.rolsac2.persistence.repository.ProcesoRepository;
import es.caib.rolsac2.service.exception.ProcesoException;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.ProcesoDTO;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Componente auxiliar procesos (auditoría procesos, etc.).
 *
 * @author Indra
 */
@Stateless
@Local(ProcesosExecComponentFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcesosExecComponentFacadeBean implements ProcesosExecComponentFacade {

    /**
     * Configuracion.
     */
    @Inject
    private SystemServiceFacade systemServiceBean;


    /**
     * Dao conf procesos.
     */
    @Inject
    private ProcesoRepository procesoRepository;

    /**
     * Dao log procesos.
     */
    @Inject
    ProcesoLogRepository procesoLogRepository;


    /**
     * Fecha iniciación componente procesos.
     */
    private final Date fechaIniciacionProcesos = new Date();


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean verificarMaestro(final String instanciaId) {
        final int minMaxMaestroActivo = Integer.parseInt(systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PROCESOS_MIN_MAX_MAESTRO_ACTIVO));
        return procesoRepository.verificarMaestro(instanciaId, minMaxMaestroActivo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<String> calcularProcesosEjecucion() {
        final ProcesoFiltro filtro = new ProcesoFiltro();
        final List<ProcesoGridDTO> procesos = procesoRepository.listar(filtro);


        final List<String> result = new ArrayList<String>();
        for (final ProcesoGridDTO p : procesos) {
            if (p.getActivo() && !p.getCron().isBlank()) {
                Date fechaUltimaEjecucion = procesoLogRepository.obtenerUltimaEjecucion(p.getIdentificadorProceso());
                if (fechaUltimaEjecucion == null) {
                    fechaUltimaEjecucion = fechaIniciacionProcesos;
                }
                final InterpreteQuartz iq = new InterpreteQuartz();
                iq.setExpresion(p.getCron());
                iq.setFechaUltimaEjecucion(fechaUltimaEjecucion);
                if (iq.isActivar()) {
                    result.add(p.getIdentificadorProceso());
                }
            }
        }
        return result;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<Propiedad> obtenerParametrosProceso(final String idProceso) {
        List<Propiedad> res = null;
        final ProcesoDTO proceso = procesoRepository.obtenerProcesoPorIdentificador(idProceso);
        if (proceso == null) {
            throw new ProcesoException("No existe proceso " + idProceso);
        }
        res = proceso.getParametrosInvocacion();
        return res;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long obtenerEntidad(String idProceso) {
        final ProcesoDTO proceso = procesoRepository.obtenerProcesoPorIdentificador(idProceso);
        return proceso.getEntidad().getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long auditarInicioProceso(final String idProceso) {
        return procesoLogRepository.auditarInicioProceso(idProceso);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void auditarFinProceso(final String idProceso, final Long instanciaProceso, final ResultadoProcesoProgramado resultadoProceso) {
        procesoLogRepository.auditarFinProceso(idProceso, instanciaProceso, resultadoProceso);
    }

}
