package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.*;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.*;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementación de los casos de uso de mantenimiento de tipo de afectación. Es
 * responsabilidad de esta capa definir el limite de las transacciones y la
 * seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant
 * l'{@link ExceptionTranslate} que transforma els errors JPA amb les excepcions
 * de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(MaestrasSupServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MaestrasSupServiceFacadeBean implements MaestrasSupServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(MaestrasSupServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";

    @Inject
    EntidadRepository entidadRepository;

    @Inject
    TipoAfectacionRepository tipoAfectacionRepository;

    @Inject
    TipoAfectacionConverter tipoAfectacionConverter;

    @Inject
    TipoBoletinRepository tipoBoletinRepository;

    @Inject
    TipoBoletinConverter tipoBoletinConverter;

    @Inject
    TipoFormaInicioRepository tipoFormaInicioRepository;

    @Inject
    TipoFormaInicioConverter tipoFormaInicioConverter;

    @Inject
    TipoLegitimacionRepository tipoLegitimacionRepository;

    @Inject
    TipoLegitimacionConverter tipoLegitimacionConverter;

    @Inject
    TipoMateriaSIARepository tipoMateriaSIARepository;

    @Inject
    TipoMateriaSIAConverter tipoMateriaSIAConverter;

    @Inject
    TipoNormativaRepository tipoNormativaRepository;

    @Inject
    TipoNormativaConverter tipoNormativaConverter;

    @Inject
    TipoMediaFichaRepository tipoMediaFichaRepository;

    @Inject
    TipoMediaUARepository tipoMediaUARepository;

    @Inject
    TipoMediaEdificioRepository tipoMediaEdificioRepository;

    @Inject
    TipoPublicoObjetivoRepository tipoPublicoObjetivoRepository;

    @Inject
    TipoPublicoObjetivoConverter tipoPublicoObjetivoConverter;

    @Inject
    TipoSilencioAdministrativoRepository tipoSilencioAdministrativoRepository;

    @Inject
    TipoSilencioAdministrativoConverter tipoSilencioAdministrativoConverter;

    @Inject
    TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    TipoTramitacionConverter tipoTramitacionConverter;

    @Inject
    TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    PlatTramitElectronicaRepository platTramitElectronicaRepository;

    @Inject
    TipoViaRepository tipoViaRepository;

    @Inject
    TipoViaConverter tipoViaConverter;

    @Inject
    TipoPublicoObjetivoEntidadConverter tipoPublicoObjetivoEntidadConverter;

    @Inject
    TipoPublicoObjetivoEntidadRepository tipoPublicoObjetivoEntidadRepository;

    @Inject
    ProcedimientoRepository procedimientoRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoAfectacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoAfectacion jTipoAfectacion = tipoAfectacionConverter.createEntity(dto);
        tipoAfectacionRepository.create(jTipoAfectacion);
        return jTipoAfectacion.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoAfectacionDTO dto) throws RecursoNoEncontradoException {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.findById(dto.getCodigo());
        tipoAfectacionConverter.mergeEntity(jTipoAfectacion, dto);
        tipoAfectacionRepository.update(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoAfectacion(Long id) throws RecursoNoEncontradoException {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.getReference(id);
        tipoAfectacionRepository.delete(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoAfectacionDTO findTipoAfectacionById(Long id) {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.getReference(id);
        return tipoAfectacionConverter.createDTO(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoAfectacionGridDTO> findByFiltro(TipoAfectacionFiltro filtro) {
        try {
            List<TipoAfectacionGridDTO> items = tipoAfectacionRepository.findPagedByFiltro(filtro);
            long total = tipoAfectacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoAfectacionDTO> findTipoAfectaciones() {
        List<JTipoAfectacion> jAfectaciones = tipoAfectacionRepository.listTipoAfectaciones();
        List<TipoAfectacionDTO> afectaciones = new ArrayList<>();
        for (JTipoAfectacion jTipoAfectacion : jAfectaciones) {
            TipoAfectacionDTO afectacion = tipoAfectacionConverter.createDTO(jTipoAfectacion);
            afectaciones.add(afectacion);
        }
        return afectaciones;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoAfectacion(String identificador) {
        return tipoAfectacionRepository.existeIdentificador(identificador);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoBoletinDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoBoletin jTipoBoletin = tipoBoletinConverter.createEntity(dto);
        tipoBoletinRepository.create(jTipoBoletin);
        return jTipoBoletin.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoBoletinDTO dto) throws RecursoNoEncontradoException {
        JTipoBoletin jTipoBoletin = tipoBoletinRepository.getReference(dto.getCodigo());
        tipoBoletinConverter.mergeEntity(jTipoBoletin, dto);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoBoletin(Long id) throws RecursoNoEncontradoException {
        JTipoBoletin TipoBoletin = tipoBoletinRepository.getReference(id);
        tipoBoletinRepository.delete(TipoBoletin);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public TipoBoletinDTO findTipoBoletinById(Long id) {

        JTipoBoletin TipoBoletin = tipoBoletinRepository.findById(id);
        return tipoBoletinConverter.createDTO(TipoBoletin);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoBoletinDTO> findBoletines() {
        try {
            return tipoBoletinRepository.findAll();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoBoletinDTO> findByFiltroRest(TipoBoletinFiltro filtro) {
        try {
            List<TipoBoletinDTO> items = tipoBoletinRepository.findPagedByFiltroRest(filtro);
            long total = tipoBoletinRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoBoletinDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoAfectacionDTO> findByFiltroRest(TipoAfectacionFiltro filtro) {
        try {
            List<TipoAfectacionDTO> items = tipoAfectacionRepository.findPagedByFiltroRest(filtro);
            long total = tipoAfectacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoAfectacionDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoFormaInicioDTO> findByFiltroRest(TipoFormaInicioFiltro filtro) {
        try {
            List<TipoFormaInicioDTO> items = tipoFormaInicioRepository.findPagedByFiltroRest(filtro);
            long total = tipoFormaInicioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoFormaInicioDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoNormativaDTO> findByFiltroRest(TipoNormativaFiltro fg) {
        try {
            List<TipoNormativaDTO> items = tipoNormativaRepository.findPagedByFiltroRest(fg);
            long total = tipoNormativaRepository.countByFiltro(fg);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoNormativaDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoSilencioAdministrativoDTO> findByFiltroRest(TipoSilencioAdministrativoFiltro fg) {
        try {
            List<TipoSilencioAdministrativoDTO> items = tipoSilencioAdministrativoRepository.findPagedByFiltroRest(fg);
            long total = tipoSilencioAdministrativoRepository.countByFiltro(fg);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoSilencioAdministrativoDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoUnidadAdministrativaDTO> findByFiltroRest(TipoUnidadAdministrativaFiltro fg) {
        try {
            List<TipoUnidadAdministrativaDTO> items = tipoUnidadAdministrativaRepository.findPagedByFiltroRest(fg);
            long total = tipoUnidadAdministrativaRepository.countByFiltro(fg);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoUnidadAdministrativaDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoBoletinGridDTO> findByFiltro(TipoBoletinFiltro filtro) {
        try {
            List<TipoBoletinGridDTO> items = tipoBoletinRepository.findPagedByFiltro(filtro);
            long total = tipoBoletinRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoBoletinGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoBoletinDTO> findAll() {
        try {
            return tipoBoletinRepository.findAll();
        } catch (Exception e) {
            LOG.error("Error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioConverter.createEntity(dto);
        tipoFormaInicioRepository.create(jTipoFormaInicio);
        return jTipoFormaInicio.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.findById(dto.getCodigo());
        tipoFormaInicioConverter.mergeEntity(jTipoFormaInicio, dto);
        tipoFormaInicioRepository.update(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoFormaInicio(Long id) throws RecursoNoEncontradoException {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.getReference(id);
        tipoFormaInicioRepository.delete(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoFormaInicioDTO findTipoFormaInicioById(Long id) {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.getReference(id);
        return tipoFormaInicioConverter.createDTO(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoFormaInicioGridDTO> findByFiltro(TipoFormaInicioFiltro filtro) {
        try {
            List<TipoFormaInicioGridDTO> items = tipoFormaInicioRepository.findPagedByFiltro(filtro);
            long total = tipoFormaInicioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoFormaInicioDTO> findAllTipoFormaInicio() {
        try {
            return tipoFormaInicioRepository.findAllTipoFormaInicio();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoFormaInicio(String identificador) {
        return tipoFormaInicioRepository.existeIdentificador(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        if (dto.isPorDefecto()) {
            tipoLegitimacionRepository.quitarTodosPorDefecto();
        }

        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionConverter.createEntity(dto);
        tipoLegitimacionRepository.create(jTipoLegitimacion);
        return jTipoLegitimacion.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException {

        if (dto.isPorDefecto()) {
            tipoLegitimacionRepository.quitarTodosPorDefecto();
        }

        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.findById(dto.getCodigo());
        tipoLegitimacionConverter.mergeEntity(jTipoLegitimacion, dto);
        tipoLegitimacionRepository.update(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoLegitimacion(Long id) throws RecursoNoEncontradoException {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.getReference(id);
        tipoLegitimacionRepository.delete(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoLegitimacionDTO findTipoLegitimacionById(Long id) {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.getReference(id);
        return tipoLegitimacionConverter.createDTO(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoLegitimacionGridDTO> findByFiltro(TipoLegitimacionFiltro filtro) {
        try {
            List<TipoLegitimacionGridDTO> items = tipoLegitimacionRepository.findPagedByFiltro(filtro);
            long total = tipoLegitimacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoLegitimacionGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoLegitimacion(String identificador) {
        return tipoLegitimacionRepository.existeIdentificador(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoLegitimacionDTO> findAllTipoLegitimacion() {
        try {
            return tipoLegitimacionRepository.findAllTipoLegitimacion();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIAConverter.createEntity(dto);
        tipoMateriaSIARepository.create(jTipoMateriaSIA);
        return jTipoMateriaSIA.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.findById(dto.getCodigo());
        tipoMateriaSIAConverter.mergeEntity(jTipoMateriaSIA, dto);
        tipoMateriaSIARepository.update(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoMateriaSIA(Long id) throws RecursoNoEncontradoException {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.getReference(id);
        tipoMateriaSIARepository.delete(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMateriaSIADTO findTipoMateriaSIAById(Long id) {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.getReference(id);
        return tipoMateriaSIAConverter.createDTO(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoMateriaSIAGridDTO> findByFiltro(TipoMateriaSIAFiltro filtro) {
        try {
            List<TipoMateriaSIAGridDTO> items = tipoMateriaSIARepository.findPagedByFiltro(filtro);
            long total = tipoMateriaSIARepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoMateriaSIA(String identificador) {
        return tipoMateriaSIARepository.existeIdentificador(identificador);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoNormativaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { // .isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoNormativa jTipoNormativa = tipoNormativaConverter.createEntity(dto);
        tipoNormativaRepository.create(jTipoNormativa);
        return jTipoNormativa.getCodigo();
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoNormativaDTO dto) throws RecursoNoEncontradoException {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(dto.getCodigo());
        tipoNormativaConverter.mergeEntity(jTipoNormativa, dto);
        tipoNormativaRepository.update(jTipoNormativa);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoNormativa(Long id) throws RecursoNoEncontradoException {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(id);
        tipoNormativaRepository.delete(jTipoNormativa);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoNormativaDTO findTipoNormativaById(Long id) {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(id);
        return tipoNormativaConverter.createDTO(jTipoNormativa);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoNormativaGridDTO> findByFiltro(TipoNormativaFiltro filtro) {
        try {
            List<TipoNormativaGridDTO> items = tipoNormativaRepository.findPagedByFiltro(filtro);
            long total = tipoNormativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoNormativaGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificadorTipoNormativa(String identificador) {
        try {
            return tipoNormativaRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return false;
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoNormativaDTO> findTipoNormativa() {
        try {
            return tipoNormativaRepository.findAll();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoPublicoObjetivoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { // .isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoConverter.createEntity(dto);
        jTipoPublicoObjetivo.setEmpleadoPublico(dto.isEmpleadoPublico());
        tipoPublicoObjetivoRepository.create(jTipoPublicoObjetivo);
        return jTipoPublicoObjetivo.getCodigo();
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoPublicoObjetivoDTO dto) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(dto.getCodigo());
        tipoPublicoObjetivoConverter.mergeEntity(jTipoPublicoObjetivo, dto);
        jTipoPublicoObjetivo.setEmpleadoPublico(dto.isEmpleadoPublico());
        tipoPublicoObjetivoRepository.update(jTipoPublicoObjetivo);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoPublicoObjetivo(Long id) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(id);
        tipoPublicoObjetivoRepository.delete(jTipoPublicoObjetivo);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoPublicoObjetivoDTO findTipoPublicoObjetivoById(Long id) {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(id);
        TipoPublicoObjetivoDTO tipoPublicoObjetivoDTO = tipoPublicoObjetivoConverter.createDTO(jTipoPublicoObjetivo);
        tipoPublicoObjetivoDTO.setEmpleadoPublico(jTipoPublicoObjetivo.isEmpleadoPublico());
        return tipoPublicoObjetivoDTO;
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoPublicoObjetivoGridDTO> findByFiltro(TipoPublicoObjetivoFiltro filtro) {
        try {
            List<TipoPublicoObjetivoGridDTO> items = tipoPublicoObjetivoRepository.findPagedByFiltro(filtro);
            long total = tipoPublicoObjetivoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoPublicoObjetivoGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificadorTipoPublicoObjetivo(String identificador) {
        try {
            return tipoPublicoObjetivoRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return false;
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoPublicoObjetivoDTO> findAllTiposPublicoObjetivo() {
        try {
            return tipoPublicoObjetivoRepository.findAll();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Inject
    private TipoSexoRepository tipoSexoRepository;

    @Inject
    private TipoSexoConverter tipoSexoConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoSexoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoSexo jTipoSexo = tipoSexoConverter.createEntity(dto);
        tipoSexoRepository.create(jTipoSexo);
        return jTipoSexo.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoSexoDTO dto) throws RecursoNoEncontradoException {
        JTipoSexo jTipoSexo = tipoSexoRepository.findById(dto.getCodigo());
        tipoSexoConverter.mergeEntity(jTipoSexo, dto);
        tipoSexoRepository.update(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoSexo(Long id) throws RecursoNoEncontradoException {
        JTipoSexo jTipoSexo = tipoSexoRepository.getReference(id);
        tipoSexoRepository.delete(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoSexoDTO findTipoSexoById(Long id) {
        JTipoSexo jTipoSexo = tipoSexoRepository.getReference(id);
        return tipoSexoConverter.createDTO(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoSexoGridDTO> findByFiltro(TipoSexoFiltro filtro) {
        try {
            List<TipoSexoGridDTO> items = tipoSexoRepository.findPagedByFiltro(filtro);
            long total = tipoSexoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoSexoGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoSexo(String identificador) {
        return tipoSexoRepository.existeIdentificador(identificador);
    }

    @Inject
    private TipoProcedimientoRepository tipoProcedimientoRepository;

    @Inject
    private TipoProcedimientoConverter tipoProcedimientoConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoProcedimientoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoProcedimiento jTipoProcedimiento = tipoProcedimientoConverter.createEntity(dto);
        JEntidad jentidad = entidadRepository.findById(dto.getEntidad().getCodigo());
        jTipoProcedimiento.setEntidad(jentidad);
        tipoProcedimientoRepository.create(jTipoProcedimiento);
        return jTipoProcedimiento.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoProcedimientoDTO dto) throws RecursoNoEncontradoException {
        JTipoProcedimiento jTipoProcedimiento = tipoProcedimientoRepository.findById(dto.getCodigo());
        tipoProcedimientoConverter.mergeEntity(jTipoProcedimiento, dto);
        tipoProcedimientoRepository.update(jTipoProcedimiento);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoProcedimiento(Long id) throws RecursoNoEncontradoException {
        JTipoProcedimiento jTipoProcedimiento = tipoProcedimientoRepository.getReference(id);
        tipoProcedimientoRepository.delete(jTipoProcedimiento);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoProcedimientoDTO findTipoProcedimientoById(Long id) {
        JTipoProcedimiento jTipoProcedimiento = tipoProcedimientoRepository.getReference(id);
        return tipoProcedimientoConverter.createDTO(jTipoProcedimiento);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoProcedimientoGridDTO> findByFiltro(TipoProcedimientoFiltro filtro) {
        try {
            List<TipoProcedimientoGridDTO> items = tipoProcedimientoRepository.findPagedByFiltro(filtro);
            long total = tipoProcedimientoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoProcedimientoGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    /**
     * Devuelve todos los procedimientos de una entidad.
     *
     * @param codigoEntidad Código de la entidad
     * @return Lista de procedimientos
     */
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoProcedimientoDTO> findAllTipoProcedimiento(Long codigoEntidad) {
        return tipoProcedimientoRepository.findAll(codigoEntidad);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoProcedimiento(String identificador) {
        return tipoProcedimientoRepository.existeIdentificador(identificador);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoSilencioAdministrativoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { // .isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoConverter.createEntity(dto);
        tipoSilencioAdministrativoRepository.create(jTipoSilencioAdministrativo);
        return jTipoSilencioAdministrativo.getCodigo();
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoSilencioAdministrativoDTO dto) throws RecursoNoEncontradoException {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(dto.getCodigo());
        tipoSilencioAdministrativoConverter.mergeEntity(jTipoSilencioAdministrativo, dto);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoSilencioAdministrativo(Long id) throws RecursoNoEncontradoException {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(id);
        tipoSilencioAdministrativoRepository.delete(jTipoSilencioAdministrativo);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoSilencioAdministrativoDTO findTipoSilencioAdministrativoById(Long id) {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(id);
        return tipoSilencioAdministrativoConverter.createDTO(jTipoSilencioAdministrativo);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoSilencioAdministrativoGridDTO> findByFiltro(TipoSilencioAdministrativoFiltro filtro) {
        try {
            List<TipoSilencioAdministrativoGridDTO> items = tipoSilencioAdministrativoRepository.findPagedByFiltro(filtro);
            long total = tipoSilencioAdministrativoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoSilencioAdministrativoGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificadorTipoSilencioAdministrativo(String identificador) {
        try {
            return tipoSilencioAdministrativoRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return false;
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoSilencioAdministrativoDTO> findAllTipoSilencio() {
        try {
            return tipoSilencioAdministrativoRepository.findAllTipoSilencio();
        } catch (Exception e) {
            LOG.error("Error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoTramitacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoTramitacion jTipoTramitacion = tipoTramitacionConverter.createEntity(dto);
        if (dto.getEntidad() != null) {
            JEntidad jentidad = entidadRepository.findById(dto.getEntidad().getCodigo());
            jTipoTramitacion.setEntidad(jentidad);
        }
        tipoTramitacionRepository.create(jTipoTramitacion);
        return jTipoTramitacion.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoTramitacionDTO dto) throws RecursoNoEncontradoException {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.findById(dto.getCodigo());

        if (Objects.nonNull(dto.getCodPlatTramitacion()) && Objects.nonNull(dto.getCodPlatTramitacion().getCodigo())) {
            JPlatTramitElectronica jPlatTramitElectronica = platTramitElectronicaRepository.getReference(dto.getCodPlatTramitacion().getCodigo());
            jTipoTramitacion.setCodPlatTramitacion(jPlatTramitElectronica);
        }

        tipoTramitacionConverter.mergeEntity(jTipoTramitacion, dto);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoTramitacion(Long id) throws RecursoNoEncontradoException {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(id);
        tipoTramitacionRepository.delete(jTipoTramitacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoTramitacionDTO findTipoTramitacionById(Long id) {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.findById(id);
        return tipoTramitacionConverter.createDTO(jTipoTramitacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoTramitacionDTO> findTipoTramitacionByEntidad(Long idEntidad) {
        List<JTipoTramitacion> jTipoTramitacions = tipoTramitacionRepository.findByEntidad(idEntidad);
        List<TipoTramitacionDTO> tramitaciones = new ArrayList<>();
        jTipoTramitacions.forEach(te -> tramitaciones.add(tipoTramitacionConverter.createDTO(te)));
        return tramitaciones;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoTramitacionGridDTO> findByFiltro(TipoTramitacionFiltro filtro) {
        try {
            List<TipoTramitacionGridDTO> items = tipoTramitacionRepository.findPagedByFiltro(filtro);
            long total = tipoTramitacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoTramitacionGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoTramitacionDTO> findAllTiposTramitacion() {
        try {
            return tipoTramitacionRepository.findAll();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoTramitacionDTO> findPlantillasTiposTramitacion(Long idEntidad, Integer fase) {
        try {
            return tipoTramitacionRepository.findPlantillas(idEntidad, fase);
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoViaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoVia jTipoVia = tipoViaConverter.createEntity(dto);
        tipoViaRepository.create(jTipoVia);
        return jTipoVia.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoViaDTO dto) throws RecursoNoEncontradoException {
        JTipoVia jTipoVia = tipoViaRepository.findById(dto.getCodigo());
        tipoViaConverter.mergeEntity(jTipoVia, dto);
        tipoViaRepository.update(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoVia(Long id) throws RecursoNoEncontradoException {
        JTipoVia jTipoVia = tipoViaRepository.getReference(id);
        tipoViaRepository.delete(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoViaDTO findTipoViaById(Long id) {
        JTipoVia jTipoVia = tipoViaRepository.getReference(id);
        return tipoViaConverter.createDTO(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoViaGridDTO> findByFiltro(TipoViaFiltro filtro) {
        try {
            List<TipoViaGridDTO> items = tipoViaRepository.findPagedByFiltro(filtro);
            long total = tipoViaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoViaGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoVia(String identificador) {
        return tipoViaRepository.existeIdentificador(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean checkIdentificadorTipoBoletin(String identificador) {
        return tipoBoletinRepository.checkIdentificadorTipoBoletin(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoPublicoObjetivoEntidadDTO dta) throws RecursoNoEncontradoException, DatoDuplicadoException {
        if (dta.getCodigo() != null) {
            throw new DatoDuplicadoException(6L);
        }

        JTipoPublicoObjetivoEntidad jTipoPOE = tipoPublicoObjetivoEntidadConverter.createEntity(dta);
        tipoPublicoObjetivoEntidadRepository.create(jTipoPOE);
        return jTipoPOE.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoPublicoObjetivoEntidadDTO dto) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivoEntidad jTipoPublicoObjetivoEntidad = tipoPublicoObjetivoEntidadRepository.findById(dto.getCodigo());
        JTipoPublicoObjetivo jTipoPublicoObjetivo = dto.getTipo() != null ? tipoPublicoObjetivoRepository.getReference(dto.getTipo().getCodigo()) : null;
        tipoPublicoObjetivoEntidadConverter.mergeEntity(jTipoPublicoObjetivoEntidad, dto);
        jTipoPublicoObjetivoEntidad.setTipo(jTipoPublicoObjetivo);
        tipoPublicoObjetivoEntidadRepository.update(jTipoPublicoObjetivoEntidad);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoPublicoObjetivoEntidad(Long id) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivoEntidad jTipoPublicoObjetivoEntidad = tipoPublicoObjetivoEntidadRepository.getReference(id);
        tipoPublicoObjetivoEntidadRepository.delete(jTipoPublicoObjetivoEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoPublicoObjetivoEntidadDTO findTipoPublicoObjetivoEntidadById(Long id) {
        JTipoPublicoObjetivoEntidad jTipoPublicoObjetivoEntidad = tipoPublicoObjetivoEntidadRepository.getReference(id);
        return tipoPublicoObjetivoEntidadConverter.createDTO(jTipoPublicoObjetivoEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoPublicoObjetivoEntidadDTO> findTipoPublicoObjetivoEntidadByEntidadId(Long idEntidad) {
        List<JTipoPublicoObjetivoEntidad> jTipo = tipoPublicoObjetivoEntidadRepository.findPageByEntidad(idEntidad);
        List<TipoPublicoObjetivoEntidadDTO> tipos = new ArrayList<>();
        for (JTipoPublicoObjetivoEntidad jT : jTipo) {
            tipos.add(tipoPublicoObjetivoEntidadConverter.createDTO(jT));
        }
        return tipos;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existePublicoObjetivo(Long codigoPO) {
        return tipoPublicoObjetivoEntidadRepository.existePublicoObjetivo(codigoPO);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoPublicoObjetivoEntidadGridDTO> findByFiltro(TipoPublicoObjetivoEntidadFiltro filtro) {
        try {
            List<TipoPublicoObjetivoEntidadGridDTO> items = tipoPublicoObjetivoEntidadRepository.findPagedByFiltro(filtro);
            long total = tipoPublicoObjetivoEntidadRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoPublicoObjetivoEntidad(String identificador) {
        return tipoPublicoObjetivoEntidadRepository.existeIdentificador(identificador);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConPublicoObjetivo(Long codigo) {
        return procedimientoRepository.existeProcedimientoConPublicoObjetivo(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConTipoMateriaSIA(Long codigo) {
        return procedimientoRepository.existeProcedimientoConMateria(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConSilencio(Long codigoSilen) {
        return procedimientoRepository.existeProcedimientoConSilencio(codigoSilen);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConFormaInicio(Long codigoForIni) {
        return procedimientoRepository.existeProcedimientoConFormaInicio(codigoForIni);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConLegitimacion(Long codigoLegi) {
        return procedimientoRepository.existeProcedimientoConLegitimacion(codigoLegi);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoViaDTO> findAllTipoVia() {
        try {
            return tipoViaRepository.findAll();
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoViaDTO> findByFiltroRest(TipoViaFiltro filtro) {
        try {
            List<TipoViaDTO> items = tipoViaRepository.findPagedByFiltroRest(filtro);
            long total = tipoViaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoViaDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoSexoDTO> findByFiltroRest(TipoSexoFiltro filtro) {
        try {
            List<TipoSexoDTO> items = tipoSexoRepository.findPagedByFiltroRest(filtro);
            long total = tipoSexoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoSexoDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoPublicoObjetivoDTO> findByFiltroRest(TipoPublicoObjetivoFiltro filtro) {
        try {
            List<TipoPublicoObjetivoDTO> items = tipoPublicoObjetivoRepository.findPagedByFiltroRest(filtro);
            long total = tipoPublicoObjetivoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoPublicoObjetivoDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoMateriaSIADTO> findByFiltroRest(TipoMateriaSIAFiltro filtro) {
        try {
            List<TipoMateriaSIADTO> items = tipoMateriaSIARepository.findPagedByFiltroRest(filtro);
            long total = tipoMateriaSIARepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMateriaSIADTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoLegitimacionDTO> findByFiltroRest(TipoLegitimacionFiltro filtro) {
        try {
            List<TipoLegitimacionDTO> items = tipoLegitimacionRepository.findPagedByFiltroRest(filtro);
            long total = tipoLegitimacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoLegitimacionDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoMediaFichaDTO> findByFiltroRest(TipoMediaFichaFiltro filtro) {
        try {
            List<TipoMediaFichaDTO> items = tipoMediaFichaRepository.findPagedByFiltroRest(filtro);
            long total = tipoMediaFichaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaFichaDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoProcedimientoDTO> findByFiltroRest(TipoProcedimientoFiltro filtro) {
        try {
            List<TipoProcedimientoDTO> items = tipoProcedimientoRepository.findPagedByFiltroRest(filtro);
            long total = tipoProcedimientoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoProcedimientoDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoMediaUADTO> findByFiltroRest(TipoMediaUAFiltro filtro) {
        try {
            List<TipoMediaUADTO> items = tipoMediaUARepository.findPagedByFiltroRest(filtro);
            long total = tipoMediaUARepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaUADTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoMediaEdificioDTO> findByFiltroRest(TipoMediaEdificioFiltro filtro) {
        try {
            List<TipoMediaEdificioDTO> items = tipoMediaEdificioRepository.findPagedByFiltroRest(filtro);
            long total = tipoMediaEdificioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaEdificioDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoTramitacionDTO> findByFiltroRest(TipoTramitacionFiltro filtro) {
        try {
            List<TipoTramitacionDTO> items = tipoTramitacionRepository.findPagedByFiltroRest(filtro);
            long total = tipoTramitacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoTramitacionDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<TipoPublicoObjetivoEntidadDTO> findByFiltroRest(TipoPublicoObjetivoEntidadFiltro filtro) {
        try {
            List<TipoPublicoObjetivoEntidadDTO> items = tipoPublicoObjetivoEntidadRepository.findPagedByFiltroRest(filtro);
            long total = tipoPublicoObjetivoEntidadRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoPublicoObjetivoEntidadDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public String getEnlaceTelematico(TipoTramitacionFiltro fg) {
        return tipoTramitacionRepository.getEnlaceTelematico(fg);
    }


}
