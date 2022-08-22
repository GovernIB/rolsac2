package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.TipoSexoRepository;
import es.caib.rolsac2.persistence.repository.TipoUnidadAdministrativaRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de los casos de uso de mantenimiento de personal. Es responsabilidad de esta caap definir el limite de
 * las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(UnidadAdministrativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UnidadAdministrativaServiceFacadeBean implements UnidadAdministrativaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(UnidadAdministrativaServiceFacadeBean.class);

    @Resource
    private SessionContext context;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    private UnidadAdministrativaConverter converter;

    @Inject
    private TipoSexoRepository tipoSexoRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaDTO getRoot(String idioma, Long entidadId) {
        return converter.createDTO(unidadAdministrativaRepository.getRoot(idioma, entidadId));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getHijos(Long idUnitat, String idioma) {
        return converter.createTreeDTOs(unidadAdministrativaRepository.getHijos(idUnitat, idioma));

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(UnidadAdministrativaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JUnidadAdministrativa jUnidadAdministrativa = converter.createEntity(dto);
        unidadAdministrativaRepository.create(jUnidadAdministrativa);
        return jUnidadAdministrativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(UnidadAdministrativaDTO dto) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JTipoUnidadAdministrativa jTipoUnidadAdministrativa = null;
        if (dto.getTipo() != null) {
            jTipoUnidadAdministrativa = tipoUnidadAdministrativaRepository.getReference(dto.getTipo().getCodigo());
        }
        JTipoSexo jTipoSexo = null;
        if (dto.getResponsableSexo() != null) {
            jTipoSexo = tipoSexoRepository.getReference(dto.getResponsableSexo().getCodigo());
        }
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.getReference(dto.getCodigo());
        jUnidadAdministrativa.setEntidad(jEntidad);
        jUnidadAdministrativa.setTipo(jTipoUnidadAdministrativa);
        jUnidadAdministrativa.setResponsableSexo(jTipoSexo);

        JUnidadAdministrativa jUnidadAdministrativaPadre =
                dto.getPadre() != null ? unidadAdministrativaRepository.getReference(dto.getPadre().getCodigo())
                        : null;

        jUnidadAdministrativa.setPadre(jUnidadAdministrativaPadre);

        converter.mergeEntity(jUnidadAdministrativa, dto);
        unidadAdministrativaRepository.update(jUnidadAdministrativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.getReference(id);
        unidadAdministrativaRepository.delete(jUnidadAdministrativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaDTO findById(Long id) {
        return converter.createDTO(unidadAdministrativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<UnidadAdministrativaGridDTO> findByFiltro(UnidadAdministrativaFiltro filtro) {
        try {
            List<UnidadAdministrativaGridDTO> items = unidadAdministrativaRepository.findPagedByFiltro(filtro);
            long total = unidadAdministrativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<UnidadAdministrativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(UnidadAdministrativaFiltro filtro) {
        return (int) unidadAdministrativaRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCountHijos(Long parentId) {
        return unidadAdministrativaRepository.getCountHijos(parentId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoSexoDTO> findTipoSexo() {
        try {
            List<TipoSexoDTO> items = tipoSexoRepository.findAll();
            return items;
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        return unidadAdministrativaRepository.checkIdentificador(identificador);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId) {
        return unidadAdministrativaRepository.getUnidadesAdministrativaByEntidadId(entidadId);
    }
}
