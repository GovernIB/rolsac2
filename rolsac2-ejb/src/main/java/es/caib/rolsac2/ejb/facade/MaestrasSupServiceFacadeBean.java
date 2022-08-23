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
 * Implementación de los casos de uso de mantenimiento de tipo de afectación. Es responsabilidad de esta capa definir el
 * limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
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
    private EntidadRepository entidadRepository;
    @Inject
    private TipoAfectacionRepository tipoAfectacionRepository;

    @Inject
    private TipoAfectacionConverter tipoAfectacionConverter;

    @Inject
    private TipoBoletinRepository tipoBoletinRepository;

    @Inject
    private TipoBoletinConverter tipoBoletinConverter;

    @Inject
    private TipoFormaInicioRepository tipoFormaInicioRepository;

    @Inject
    private TipoFormaInicioConverter tipoFormaInicioConverter;


    @Inject
    private TipoLegitimacionRepository tipoLegitimacionRepository;

    @Inject
    private TipoLegitimacionConverter tipoLegitimacionConverter;

    @Inject
    private TipoMateriaSIARepository tipoMateriaSIARepository;

    @Inject
    private TipoMateriaSIAConverter tipoMateriaSIAConverter;

    @Inject
    private TipoNormativaRepository tipoNormativaRepository;

    @Inject
    private TipoNormativaConverter tipoNormativaConverter;

    @Inject
    private TipoPublicoObjetivoRepository tipoPublicoObjetivoRepository;

    @Inject
    private TipoPublicoObjetivoConverter tipoPublicoObjetivoConverter;


    @Inject
    private TipoSilencioAdministrativoRepository tipoSilencioAdministrativoRepository;

    @Inject
    private TipoSilencioAdministrativoConverter tipoSilencioAdministrativoConverter;


    @Inject
    private TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    private TipoTramitacionConverter tipoTramitacionConverter;

    @Inject
    private PlatTramitElectronicaRepository platTramitElectronicaRepository;

    @Inject
    private PlatTramitElectronicaConverter platTramitElectronicaConverter;

    @Inject
    private TipoViaRepository tipoViaRepository;

    @Inject
    private TipoViaConverter tipoViaConverter;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoAfectacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoAfectacion jTipoAfectacion = tipoAfectacionConverter.createEntity(dto);
        tipoAfectacionRepository.create(jTipoAfectacion);
        return jTipoAfectacion.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoAfectacionDTO dto) throws RecursoNoEncontradoException {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.findById(dto.getCodigo());
        tipoAfectacionConverter.mergeEntity(jTipoAfectacion, dto);
        tipoAfectacionRepository.update(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoAfectacion(Long id) throws RecursoNoEncontradoException {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.getReference(id);
        tipoAfectacionRepository.delete(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoAfectacionDTO findTipoAfectacionById(Long id) {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.getReference(id);
        return tipoAfectacionConverter.createDTO(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoAfectacionGridDTO> findByFiltro(TipoAfectacionFiltro filtro) {
        try {
            List<TipoAfectacionGridDTO> items = tipoAfectacionRepository.findPagedByFiltro(filtro);
            long total = tipoAfectacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoAfectacion(String identificador) {
        return tipoAfectacionRepository.existeIdentificador(identificador);
    }


    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoBoletinDTO findTipoBoletinById(Long id) {

        JTipoBoletin TipoBoletin = tipoBoletinRepository.findById(id);
        TipoBoletinDTO TipoBoletinDTO = tipoBoletinConverter.createDTO(TipoBoletin);
        return TipoBoletinDTO;
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
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioConverter.createEntity(dto);
        tipoFormaInicioRepository.create(jTipoFormaInicio);
        return jTipoFormaInicio.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.findById(dto.getCodigo());
        tipoFormaInicioConverter.mergeEntity(jTipoFormaInicio, dto);
        tipoFormaInicioRepository.update(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoFormaInicio(Long id) throws RecursoNoEncontradoException {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.getReference(id);
        tipoFormaInicioRepository.delete(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoFormaInicioDTO findTipoFormaInicioById(Long id) {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.getReference(id);
        return tipoFormaInicioConverter.createDTO(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoFormaInicio(String identificador) {
        return tipoFormaInicioRepository.existeIdentificador(identificador);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionConverter.createEntity(dto);
        tipoLegitimacionRepository.create(jTipoLegitimacion);
        return jTipoLegitimacion.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.findById(dto.getCodigo());
        tipoLegitimacionConverter.mergeEntity(jTipoLegitimacion, dto);
        tipoLegitimacionRepository.update(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoLegitimacion(Long id) throws RecursoNoEncontradoException {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.getReference(id);
        tipoLegitimacionRepository.delete(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoLegitimacionDTO findTipoLegitimacionById(Long id) {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.getReference(id);
        TipoLegitimacionDTO tipoLegitimacionDTO = tipoLegitimacionConverter.createDTO(jTipoLegitimacion);
        return tipoLegitimacionDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoLegitimacionGridDTO> findByFiltro(TipoLegitimacionFiltro filtro) {
        try {
            List<TipoLegitimacionGridDTO> items = tipoLegitimacionRepository.findPagedByFiltro(filtro);
            long total = tipoLegitimacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoLegitimacionGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoLegitimacion(String identificador) {
        return tipoLegitimacionRepository.existeIdentificador(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIAConverter.createEntity(dto);
        tipoMateriaSIARepository.create(jTipoMateriaSIA);
        return jTipoMateriaSIA.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.findById(dto.getCodigo());
        tipoMateriaSIAConverter.mergeEntity(jTipoMateriaSIA, dto);
        tipoMateriaSIARepository.update(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoMateriaSIA(Long id) throws RecursoNoEncontradoException {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.getReference(id);
        tipoMateriaSIARepository.delete(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMateriaSIADTO findTipoMateriaSIAById(Long id) {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.getReference(id);
        return tipoMateriaSIAConverter.createDTO(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoMateriaSIA(String identificador) {
        return tipoMateriaSIARepository.existeIdentificador(identificador);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoNormativaDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoNormativa jTipoNormativa = tipoNormativaConverter.createEntity(dto);
        tipoNormativaRepository.create(jTipoNormativa);
        return jTipoNormativa.getCodigo();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoNormativaDTO dto) throws RecursoNoEncontradoException {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(dto.getCodigo());
        tipoNormativaConverter.mergeEntity(jTipoNormativa, dto);
        tipoNormativaRepository.update(jTipoNormativa);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoNormativa(Long id) throws RecursoNoEncontradoException {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(id);
        tipoNormativaRepository.delete(jTipoNormativa);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoNormativaDTO findTipoNormativaById(Long id) {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(id);
        TipoNormativaDTO tipoNormativaDTO = tipoNormativaConverter.createDTO(jTipoNormativa);
        return tipoNormativaDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoNormativaGridDTO> findByFiltro(TipoNormativaFiltro filtro) {
        try {
            List<TipoNormativaGridDTO> items = tipoNormativaRepository.findPagedByFiltro(filtro);
            long total = tipoNormativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoNormativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }


    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
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
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoPublicoObjetivoDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoConverter.createEntity(dto);
        tipoPublicoObjetivoRepository.create(jTipoPublicoObjetivo);
        return jTipoPublicoObjetivo.getCodigo();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoPublicoObjetivoDTO dto) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(dto.getCodigo());
        tipoPublicoObjetivoConverter.mergeEntity(jTipoPublicoObjetivo, dto);
        tipoPublicoObjetivoRepository.update(jTipoPublicoObjetivo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoPublicoObjetivo(Long id) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(id);
        tipoPublicoObjetivoRepository.delete(jTipoPublicoObjetivo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoPublicoObjetivoDTO findTipoPublicoObjetivoById(Long id) {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(id);
        TipoPublicoObjetivoDTO tipoNormativaDTO = tipoPublicoObjetivoConverter.createDTO(jTipoPublicoObjetivo);
        return tipoNormativaDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoPublicoObjetivoGridDTO> findByFiltro(TipoPublicoObjetivoFiltro filtro) {
        try {
            List<TipoPublicoObjetivoGridDTO> items = tipoPublicoObjetivoRepository.findPagedByFiltro(filtro);
            long total = tipoPublicoObjetivoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoPublicoObjetivoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificadorTipoPublicoObjetivo(String identificador) {
        try {
            return tipoPublicoObjetivoRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return false;
        }
    }


    @Inject
    private TipoSexoRepository tipoSexoRepository;

    @Inject
    private TipoSexoConverter tipoSexoConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoSexoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoSexo jTipoSexo = tipoSexoConverter.createEntity(dto);
        tipoSexoRepository.create(jTipoSexo);
        return jTipoSexo.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoSexoDTO dto) throws RecursoNoEncontradoException {
        JTipoSexo jTipoSexo = tipoSexoRepository.findById(dto.getCodigo());
        tipoSexoConverter.mergeEntity(jTipoSexo, dto);
        tipoSexoRepository.update(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoSexo(Long id) throws RecursoNoEncontradoException {
        JTipoSexo jTipoSexo = tipoSexoRepository.getReference(id);
        tipoSexoRepository.delete(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoSexoDTO findTipoSexoById(Long id) {
        JTipoSexo jTipoSexo = tipoSexoRepository.getReference(id);
        TipoSexoDTO tipoSexoDTO = tipoSexoConverter.createDTO(jTipoSexo);
        return tipoSexoDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoSexoGridDTO> findByFiltro(TipoSexoFiltro filtro) {
        try {
            List<TipoSexoGridDTO> items = tipoSexoRepository.findPagedByFiltro(filtro);
            long total = tipoSexoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoSexoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoSexo(String identificador) {
        return tipoSexoRepository.existeIdentificador(identificador);
    }


    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoSilencioAdministrativoDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoConverter.createEntity(dto);
        tipoSilencioAdministrativoRepository.create(jTipoSilencioAdministrativo);
        return jTipoSilencioAdministrativo.getCodigo();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoSilencioAdministrativoDTO dto) throws RecursoNoEncontradoException {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(dto.getCodigo());
        tipoSilencioAdministrativoConverter.mergeEntity(jTipoSilencioAdministrativo, dto);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoSilencioAdministrativo(Long id) throws RecursoNoEncontradoException {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(id);
        tipoSilencioAdministrativoRepository.delete(jTipoSilencioAdministrativo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoSilencioAdministrativoDTO findTipoSilencioAdministrativoById(Long id) {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(id);
        TipoSilencioAdministrativoDTO tipoSilencioAdministrativoDTO = tipoSilencioAdministrativoConverter.createDTO(jTipoSilencioAdministrativo);
        return tipoSilencioAdministrativoDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoSilencioAdministrativoGridDTO> findByFiltro(TipoSilencioAdministrativoFiltro filtro) {
        try {
            List<TipoSilencioAdministrativoGridDTO> items = tipoSilencioAdministrativoRepository.findPagedByFiltro(filtro);
            long total = tipoSilencioAdministrativoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoSilencioAdministrativoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoTramitacionDTO dto) throws RecursoNoEncontradoException {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(dto.getCodigo());

        if (Objects.nonNull(dto.getCodPlatTramitacion()) && Objects.nonNull(dto.getCodPlatTramitacion().getCodigo())) {
            JPlatTramitElectronica jPlatTramitElectronica = platTramitElectronicaRepository.getReference(dto.getCodPlatTramitacion().getCodigo());
            jTipoTramitacion.setCodPlatTramitacion(jPlatTramitElectronica);
        }

        tipoTramitacionConverter.mergeEntity(jTipoTramitacion, dto);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoTramitacion(Long id) throws RecursoNoEncontradoException {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(id);
        tipoTramitacionRepository.delete(jTipoTramitacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoTramitacionDTO findTipoTramitacionById(Long id) {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(id);
        TipoTramitacionDTO tipoTramitacionDTO = tipoTramitacionConverter.createDTO(jTipoTramitacion);
        return tipoTramitacionDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoTramitacionGridDTO> findByFiltro(TipoTramitacionFiltro filtro) {
        try {
            List<TipoTramitacionGridDTO> items = tipoTramitacionRepository.findPagedByFiltro(filtro);
            long total = tipoTramitacionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoTramitacionGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoViaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoVia jTipoVia = tipoViaConverter.createEntity(dto);
        tipoViaRepository.create(jTipoVia);
        return jTipoVia.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoViaDTO dto) throws RecursoNoEncontradoException {
        JTipoVia jTipoVia = tipoViaRepository.findById(dto.getCodigo());
        tipoViaConverter.mergeEntity(jTipoVia, dto);
        tipoViaRepository.update(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoVia(Long id) throws RecursoNoEncontradoException {
        JTipoVia jTipoVia = tipoViaRepository.getReference(id);
        tipoViaRepository.delete(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoViaDTO findTipoViaById(Long id) {
        JTipoVia jTipoVia = tipoViaRepository.getReference(id);
        TipoViaDTO tipoViaDTO = tipoViaConverter.createDTO(jTipoVia);
        return tipoViaDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoViaGridDTO> findByFiltro(TipoViaFiltro filtro) {
        try {
            List<TipoViaGridDTO> items = tipoViaRepository.findPagedByFiltro(filtro);
            long total = tipoViaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoViaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoVia(String identificador) {
        return tipoViaRepository.existeIdentificador(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean checkIdentificadorTipoBoletin(String identificador) {
        return tipoBoletinRepository.checkIdentificadorTipoBoletin(identificador);
    }

}