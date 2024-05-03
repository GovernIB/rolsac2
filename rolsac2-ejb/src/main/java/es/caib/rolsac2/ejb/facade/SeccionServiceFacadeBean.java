package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.SeccionConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JSeccion;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.SeccionRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.SeccionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.SeccionDTO;
import es.caib.rolsac2.service.model.SeccionGridDTO;
import es.caib.rolsac2.service.model.filtro.SeccionFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Logged
@ExceptionTranslate
@Stateless
@Local(SeccionServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SeccionServiceFacadeBean implements SeccionServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(SeccionServiceFacadeBean.class);

    @Resource
    SessionContext context;

    @Inject
    EntidadRepository entidadRepository;

    @Inject
    SeccionConverter converter;

    @Inject
    SeccionRepository seccionRepository;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<SeccionDTO> getHijos(Long idSeccion, String idioma) {
        return converter.createTreeDTOs(seccionRepository.getHijos(idSeccion, idioma));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<SeccionDTO> getRoot(String idioma, Long entidadId) {
        return converter.createDTOs(seccionRepository.getRoot(idioma, entidadId));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(SeccionDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JSeccion jSeccion = converter.createEntity(dto);
        seccionRepository.create(jSeccion);
        return jSeccion.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(SeccionDTO dto) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JSeccion jSeccion = seccionRepository.getReference(dto.getCodigo());

        if (dto.getPadre() != null) {
            JSeccion seccionPadre = seccionRepository.getReference(dto.getPadre().getCodigo());
            jSeccion.setPadre(seccionPadre);
        }
        jSeccion.setEntidad(jEntidad);
        converter.mergeEntity(jSeccion, dto);
        seccionRepository.update(jSeccion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JSeccion jSeccion = seccionRepository.getReference(id);
        seccionRepository.delete(jSeccion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public SeccionDTO findById(Long id) {
        return converter.createDTO(seccionRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<SeccionGridDTO> findByFiltro(SeccionFiltro filtro) {
        try {
            List<SeccionGridDTO> items = seccionRepository.findPageByFiltro(filtro);
            long total = seccionRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<SeccionGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(SeccionFiltro filtro) {
        return (int) seccionRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCountHijos(Long parentId) {
        return seccionRepository.getCountHijos(parentId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        return seccionRepository.checkIdentificador(identificador);
    }
}
