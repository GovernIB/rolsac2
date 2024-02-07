package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.EstadisticaConverter;
import es.caib.rolsac2.persistence.model.JEstadistica;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.EstadisticaRepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.EstadisticaException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.EstadisticaServiceFacade;
import es.caib.rolsac2.service.model.auditoria.EstadisticaAccesoDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaDTO;
import es.caib.rolsac2.service.model.auditoria.Periodo;
import es.caib.rolsac2.service.model.filtro.EstadisticaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.utils.PeriodoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Logged
@ExceptionTranslate
@Stateless
@Local(EstadisticaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EstadisticaServiceFacadeBean implements EstadisticaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(EstadisticaServiceFacade.class);
    private static final String ERROR_LITERAL = "Error";

    @Inject
    private EstadisticaRepository estadisticaRepository;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    private EstadisticaConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(EstadisticaFiltro filtro) throws RecursoNoEncontradoException, DatoDuplicadoException {

        EstadisticaDTO estadistica = new EstadisticaDTO();
        estadistica.setTipo(filtro.getTipo());
        estadistica.setIdentificadorApp(filtro.getIdApp());

        List<EstadisticaAccesoDTO> accesos = new ArrayList<>();
        EstadisticaAccesoDTO acceso = new EstadisticaAccesoDTO();
        acceso.setFechaCreacion(new Date());
        acceso.setContador(1l);
        accesos.add(acceso);
        estadistica.setAccesos(accesos);

        JEstadistica jEstadistica = converter.createEntity(estadistica);
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.findById(filtro.getCodigo());
        jEstadistica.setUnidadAdministrativa(jUnidadAdministrativa);

        if (filtro.getTipo().equals("P") || filtro.getTipo().equals("S")) {
            JProcedimiento jProcedimiento = procedimientoRepository.findById(filtro.getCodigo());
            jEstadistica.setProcedimiento(jProcedimiento);
        }

        estadisticaRepository.create(jEstadistica);
        return jEstadistica.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JEstadistica jEstadistica = estadisticaRepository.getReference(id);
        estadisticaRepository.delete(jEstadistica);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public void grabarAcceso(EstadisticaFiltro filtro) throws EstadisticaException {

        if (filtro.getTipo().equals("P") || filtro.getTipo().equals("S")) {
            if (!procedimientoRepository.checkExisteProcedimiento(filtro.getCodigo())) {
                throw new EstadisticaException("No existe el procedimiento o servicio al que se está grabando el acceso");
            }
        } else {
            if (!unidadAdministrativaRepository.checkExsiteUa(filtro.getCodigo())) {
                throw new EstadisticaException("No existe la unidad administrativa a la que se está grabando el acceso");
            }
        }
        try {
            Periodo periodo = PeriodoUtil.crearPeriodoMes();
            filtro.setPeriodo(periodo);
            if (!estadisticaRepository.checkExisteEstadistica(filtro)) {
                this.create(filtro);
            } else {
                JEstadistica jEstadistica = estadisticaRepository.findByUk(filtro);
                EstadisticaDTO estadistica = converter.createDTO(jEstadistica);
                this.actualizarEstadistica(estadistica, periodo);
                converter.mergeEntity(jEstadistica, estadistica);
                estadisticaRepository.update(jEstadistica);
            }
        } catch (Exception e) {
            throw new EstadisticaException("No se ha podido grabar el acceso correctamente.");
        }
    }

    private void actualizarEstadistica(EstadisticaDTO estadisticaDTO, Periodo periodo) {
        if (estadisticaDTO.getAccesos() == null) {
            List<EstadisticaAccesoDTO> accesos = new ArrayList<>();
            EstadisticaAccesoDTO acceso = new EstadisticaAccesoDTO();
            acceso.setFechaCreacion(periodo.getFechaInicio());
            acceso.setContador(1l);
            accesos.add(acceso);
            estadisticaDTO.setAccesos(accesos);
        } else {
            EstadisticaAccesoDTO accesoActual = estadisticaDTO.getAccesos().stream().filter(e -> e.getFechaCreacion().after(periodo.getFechaInicio()) && e.getFechaCreacion().before(periodo.getFechaFin())).findFirst().orElse(null);
            if (accesoActual == null) {
                EstadisticaAccesoDTO acceso = new EstadisticaAccesoDTO();
                acceso.setFechaCreacion(periodo.getFechaInicio());
                acceso.setContador(1l);
                estadisticaDTO.getAccesos().add(acceso);
            } else {
                accesoActual.setContador(accesoActual.getContador() + 1l);
            }
        }
    }

}
