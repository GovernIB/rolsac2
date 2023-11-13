package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.repository.EstadisticaRepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoAuditoriaRepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoRepository;
import es.caib.rolsac2.service.facade.CuadroMandoServiceFacade;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCMGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Logged
@ExceptionTranslate
@Stateless
@Local(CuadroMandoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuadroMandoServiceFacadeBean implements CuadroMandoServiceFacade {

    @Inject
    private ProcedimientoAuditoriaRepository auditoriaRepository;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    EstadisticaRepository estadisticaRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AuditoriaCMGridDTO> findAuditoriasUltimaSemana(CuadroMandoFiltro filtro) {
        return auditoriaRepository.findAuditoriasUltimaSemana(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EstadisticaCMDTO countByFiltro(CuadroMandoFiltro filtro) {
        return auditoriaRepository.countByFiltro(filtro);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countProcedimientosByUa(Long uaId) {
        return procedimientoRepository.countByUa(uaId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countAllProcedimientos() {
        return procedimientoRepository.countAll();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countServicioByUa(Long uaId) {
        return procedimientoRepository.countServicioByUa(uaId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countAllServicio() {
        return procedimientoRepository.countAllServicio();
    }

    //@Override
    //@RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    //public Long countProcEstadoByUa(Long uaId, String estado) {
    //    return procedimientoRepository.countProcEstadoByUa(uaId, estado);
    //}

    //@Override
    //@RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    //public Long countServEstadoByUa(Long uaId, String estado) {
    //    return procedimientoRepository.countServEstadoByUa(uaId, estado);
    //}

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long[] getProcedimientosByUa(Long codigo, String tipo, String lang) {
        List<Long> uas = new ArrayList<>();
        uas.add(codigo);
        Long[] total = new Long[3];
        total[0] = procedimientoRepository.getProcedimientosTotalByUas(uas, tipo, lang, true);
        total[1] = procedimientoRepository.getProcedimientosTotalByUas(uas, tipo, lang, false);
        total[2] = total[0] + total[1];
        return total;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EstadisticaCMDTO obtenerEstadisiticasMensuales(CuadroMandoFiltro filtro) {
        return estadisticaRepository.countEstadisticasMensuales(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<String> obtenerAplicacionesEstadistica() {
        return estadisticaRepository.obtenerAplicaciones();
    }
}