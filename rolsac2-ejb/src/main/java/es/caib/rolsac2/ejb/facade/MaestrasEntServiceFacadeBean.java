package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.CategoriaPduConverter;
import es.caib.rolsac2.persistence.converter.TipoMediaEdificioConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.MaestrasEntServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.CategoriaPDUFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;
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
    TipoMediaEdificioRepository tipoMediaEdificioRepository;

    @Inject
    CategoriaPDURepository categoriaPDURepository;

    @Inject
    TipoMediaEdificioConverter tipoMediaEdificioConverter;

    @Inject
    CategoriaPduConverter categoriaPDUConverter;

    @Inject
    EntidadRepository entidadRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMediaEdificioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioConverter.createEntity(dto);
        tipoMediaEdificioRepository.create(jTipoMediaEdificio);
        return jTipoMediaEdificio.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMediaEdificioDTO dto) throws RecursoNoEncontradoException {
        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioRepository.findById(dto.getCodigo());
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        jTipoMediaEdificio.setEntidad(jEntidad);
        jTipoMediaEdificio.setIdentificador(dto.getIdentificador());
        tipoMediaEdificioConverter.convierteLiteralToTraduccion(jTipoMediaEdificio, dto.getDescripcion());
        tipoMediaEdificioRepository.update(jTipoMediaEdificio);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteTipoMediaEdificio(Long id) throws RecursoNoEncontradoException {
        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioRepository.getReference(id);
        tipoMediaEdificioRepository.delete(jTipoMediaEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMediaEdificioDTO findTipoMediaEdificioById(Long id) {
        JTipoMediaEdificio jTipoMediaEdificio = tipoMediaEdificioRepository.getReference(id);
        return tipoMediaEdificioConverter.createDTO(jTipoMediaEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoMediaEdificioDTO> findTipoMediaEdificioByEntidad(Long idEntidad) {
        List<JTipoMediaEdificio> jTipoMediaEdificios = tipoMediaEdificioRepository.findByEntidad(idEntidad);
        List<TipoMediaEdificioDTO> edificios = new ArrayList<>();
        jTipoMediaEdificios.forEach(te -> edificios.add(tipoMediaEdificioConverter.createDTO(te)));
        return edificios;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoMediaEdificioGridDTO> findByFiltro(TipoMediaEdificioFiltro filtro) {
        try {
            List<TipoMediaEdificioGridDTO> items = tipoMediaEdificioRepository.findPagedByFiltro(filtro);
            long total = tipoMediaEdificioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<TipoMediaEdificioGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorTipoMediaEdificio(String identificador, Long idEntidad) {
        return tipoMediaEdificioRepository.existeIdentificador(identificador, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(CategoriaPDUDTO dto) throws RecursoNoEncontradoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JCategoriaPDU jcategoriaPDU = categoriaPDUConverter.createEntity(dto);
        categoriaPDURepository.create(jcategoriaPDU);
        return jcategoriaPDU.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(CategoriaPDUDTO dto) throws RecursoNoEncontradoException {
        JCategoriaPDU jCategoriaPDU = categoriaPDURepository.findById(dto.getCodigo());
        categoriaPDUConverter.mergeEntity(jCategoriaPDU, dto);
        categoriaPDURepository.update(jCategoriaPDU);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteCategoriaPduDTO(Long id) throws RecursoNoEncontradoException {
        JCategoriaPDU jCategoriaPDU = categoriaPDURepository.getReference(id);
        categoriaPDURepository.delete(jCategoriaPDU);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public CategoriaPDUDTO findCategoriaPduDTOById(Long id) {
        JCategoriaPDU jcategoria = categoriaPDURepository.getReference(id);
        return categoriaPDUConverter.createDTO(jcategoria);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<CategoriaPDUDTO> findCategoriaPduDTOByEntidad(Long idEntidad) {
        List<JCategoriaPDU> jcategorias = categoriaPDURepository.findByEntidad(idEntidad);
        List<CategoriaPDUDTO> fichas = new ArrayList<>();
        jcategorias.forEach(te -> fichas.add(categoriaPDUConverter.createDTO(te)));
        return fichas;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<CategoriaPDUGridDTO> findByFiltro(CategoriaPDUFiltro filtro) {
        try {
            List<CategoriaPDUGridDTO> items = categoriaPDURepository.findPagedByFiltro(filtro);
            long total = categoriaPDURepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<CategoriaPDUGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorCategoriaPdu(String identificador, Long idEntidad) {
        return categoriaPDURepository.existeIdentificador(identificador, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR})
    public boolean estaAsociadoCategoriaPDU(Long codigoPDU) {
        return categoriaPDURepository.estaAsociadoCategoriaPDU(codigoPDU);
    }
}