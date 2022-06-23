package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoSexoConverter;
import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.persistence.repository.TipoSexoRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoSexoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.TipoSexoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSexoFiltro;
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
@Local(TipoSexoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoSexoServiceFacadeBean implements TipoSexoServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoSexoServiceFacadeBean.class);

    @Inject
    private TipoSexoRepository tipoSexoRepository;

    @Inject
    private TipoSexoConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoSexoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoSexo jTipoSexo = converter.createEntity(dto);
        tipoSexoRepository.create(jTipoSexo);
        return jTipoSexo.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoSexoDTO dto) throws RecursoNoEncontradoException {
        JTipoSexo jTipoSexo = tipoSexoRepository.findById(dto.getId());
        converter.mergeEntity(jTipoSexo, dto);
        tipoSexoRepository.update(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoSexo jTipoSexo = tipoSexoRepository.getReference(id);
        tipoSexoRepository.delete(jTipoSexo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoSexoDTO findById(Long id) {
        JTipoSexo jTipoSexo = tipoSexoRepository.getReference(id);
        TipoSexoDTO tipoSexoDTO = converter.createDTO(jTipoSexo);
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
            LOG.error("Error", e);
            List<TipoSexoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificador(String identificador) {
        return tipoSexoRepository.existeIdentificador(identificador);
    }
}
