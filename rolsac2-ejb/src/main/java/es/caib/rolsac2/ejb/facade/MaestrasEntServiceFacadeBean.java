package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoMediaEdificioConverter;
import es.caib.rolsac2.persistence.converter.TipoMediaFichaConverter;
import es.caib.rolsac2.persistence.converter.TipoMediaUAConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoMediaEdificio;
import es.caib.rolsac2.persistence.model.JTipoMediaFicha;
import es.caib.rolsac2.persistence.model.JTipoMediaUA;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.TipoMediaEdificioRepository;
import es.caib.rolsac2.persistence.repository.TipoMediaFichaRepository;
import es.caib.rolsac2.persistence.repository.TipoMediaUARepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.MaestrasEntServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaFichaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaUAFiltro;
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

@Logged
@ExceptionTranslate
@Stateless
@Local(MaestrasEntServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MaestrasEntServiceFacadeBean implements MaestrasEntServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(MaestrasEntServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";

    @Inject
    private TipoMediaEdificioRepository tipoMediaEdificioRepository;

    @Inject
    private TipoMediaFichaRepository tipoMediaFichaRepository;

    @Inject
    private TipoMediaUARepository tipoMediaUARepository;

    @Inject
    private TipoMediaEdificioConverter tipoMediaEdificioConverter;

    @Inject
    private TipoMediaFichaConverter tipoMediaFichaConverter;

    @Inject
    private TipoMediaUAConverter tipoMediaUAConverter;

    @Inject
    private EntidadRepository entidadRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMediaEdificioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioConverter.createEntity(dto);
        tipoMediaEdificioRepository.create(jTipoMediaEdificio);
        return jTipoMediaEdificio.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMediaEdificioDTO dto) throws RecursoNoEncontradoException {
        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioRepository.findById(dto.getCodigo());
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        jTipoMediaEdificio.setEntidad(jEntidad);
        jTipoMediaEdificio.setIdentificador(dto.getIdentificador());
        tipoMediaEdificioConverter.convierteLiteralToTraduccion(jTipoMediaEdificio, dto.getDescripcion());
        tipoMediaEdificioRepository.update(jTipoMediaEdificio);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoMediaEdificio(Long id) throws RecursoNoEncontradoException {
        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioRepository.getReference(id);
        tipoMediaEdificioRepository.delete(jTipoMediaEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMediaEdificioDTO findTipoMediaEdificioById(Long id) {
        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioRepository.getReference(id);
        TipoMediaEdificioDTO tipoMediaEdificioDTO = tipoMediaEdificioConverter.createDTO(jTipoMediaEdificio);
        return tipoMediaEdificioDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoMediaEdificioDTO> findTipoMediaEdificioByEntidad(Long idEntidad) {
        List<JTipoMediaEdificio> jTipoMediaEdificios = tipoMediaEdificioRepository.findByEntidad(idEntidad);
        List<TipoMediaEdificioDTO> edificios = new ArrayList<>();
        jTipoMediaEdificios.forEach(te -> edificios.add(tipoMediaEdificioConverter.createDTO(te)));
        return edificios;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoMediaEdificioGridDTO> findByFiltro(TipoMediaEdificioFiltro filtro) {
        try {
            List<TipoMediaEdificioGridDTO> items = tipoMediaEdificioRepository.findPagedByFiltro(filtro);
            long total = tipoMediaEdificioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaEdificioGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoMediaEdificio(String identificador, Long idEntidad) {
        return tipoMediaEdificioRepository.existeIdentificador(identificador, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMediaUADTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoMediaUA jTipoMediaUA = tipoMediaUAConverter.createEntity(dto);
        tipoMediaUARepository.create(jTipoMediaUA);
        return jTipoMediaUA.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMediaUADTO dto) throws RecursoNoEncontradoException {
        JTipoMediaUA jTipoMediaUA = tipoMediaUARepository.findById(dto.getCodigo());
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        jTipoMediaUA.setEntidad(jEntidad);
        jTipoMediaUA.setIdentificador(dto.getIdentificador());
        tipoMediaUAConverter.convierteLiteralToTraduccion(jTipoMediaUA, dto.getDescripcion());
        tipoMediaUARepository.update(jTipoMediaUA);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoMediaUA(Long id) throws RecursoNoEncontradoException {
        JTipoMediaUA jTipoMediaUA = tipoMediaUARepository.getReference(id);
        tipoMediaUARepository.delete(jTipoMediaUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMediaUADTO findTipoMediaUAById(Long id) {
        JTipoMediaUA jTipoMediaUA = tipoMediaUARepository.getReference(id);
        TipoMediaUADTO tipoMediaUADTO = tipoMediaUAConverter.createDTO(jTipoMediaUA);
        return tipoMediaUADTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoMediaUADTO> findTipoMediaUAByEntidad(Long idEntidad) {
        List<JTipoMediaUA> jTipoMediaUAS = tipoMediaUARepository.findByEntidad(idEntidad);
        List<TipoMediaUADTO> medias = new ArrayList<>();
        jTipoMediaUAS.forEach(te -> medias.add(tipoMediaUAConverter.createDTO(te)));
        return medias;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoMediaUAGridDTO> findByFiltro(TipoMediaUAFiltro filtro) {
        try {
            List<TipoMediaUAGridDTO> items = tipoMediaUARepository.findPagedByFiltro(filtro);
            long total = tipoMediaUARepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaUAGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoMediaUA(String identificador, Long idEntidad) {
        return tipoMediaUARepository.existeIdentificador(identificador, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMediaFichaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoMediaFicha jTipoMediaFicha = tipoMediaFichaConverter.createEntity(dto);
        tipoMediaFichaRepository.create(jTipoMediaFicha);
        return jTipoMediaFicha.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMediaFichaDTO dto) throws RecursoNoEncontradoException {
        JTipoMediaFicha jTipoMediaFicha = tipoMediaFichaRepository.findById(dto.getCodigo());
        tipoMediaFichaConverter.mergeEntity(jTipoMediaFicha, dto);
        tipoMediaFichaRepository.update(jTipoMediaFicha);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoMediaFicha(Long id) throws RecursoNoEncontradoException {
        JTipoMediaFicha jTipoMediaFicha = tipoMediaFichaRepository.getReference(id);
        tipoMediaFichaRepository.delete(jTipoMediaFicha);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMediaFichaDTO findTipoMediaFichaById(Long id) {
        JTipoMediaFicha jTipoMediaFicha = tipoMediaFichaRepository.getReference(id);
        TipoMediaFichaDTO tipoMediaFichaDTO = tipoMediaFichaConverter.createDTO(jTipoMediaFicha);
        return tipoMediaFichaDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoMediaFichaDTO> findTipoMediaFichaByEntidad(Long idEntidad) {
        List<JTipoMediaFicha> jTipoMediaFichas = tipoMediaFichaRepository.findByEntidad(idEntidad);
        List<TipoMediaFichaDTO> fichas = new ArrayList<>();
        jTipoMediaFichas.forEach(te -> fichas.add(tipoMediaFichaConverter.createDTO(te)));
        return fichas;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoMediaFichaGridDTO> findByFiltro(TipoMediaFichaFiltro filtro) {
        try {
            List<TipoMediaFichaGridDTO> items = tipoMediaFichaRepository.findPagedByFiltro(filtro);
            long total = tipoMediaFichaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaFichaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoMediaFicha(String identificador) {
        return tipoMediaFichaRepository.existeIdentificador(identificador);
    }
}
