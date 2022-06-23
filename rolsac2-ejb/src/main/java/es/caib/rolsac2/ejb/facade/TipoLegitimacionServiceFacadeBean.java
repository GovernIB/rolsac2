package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoLegitimacionConverter;
import es.caib.rolsac2.persistence.model.JTipoLegitimacion;
import es.caib.rolsac2.persistence.repository.TipoLegitimacionRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoLegitimacionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.TipoLegitimacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoLegitimacionFiltro;
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
@Local(TipoLegitimacionServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoLegitimacionServiceFacadeBean implements TipoLegitimacionServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoLegitimacionServiceFacadeBean.class);

    @Inject
    private TipoLegitimacionRepository tipoLegitimacionRepository;

    @Inject
    private TipoLegitimacionConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoLegitimacion jTipoLegitimacion = converter.createEntity(dto);
        tipoLegitimacionRepository.create(jTipoLegitimacion);
        return jTipoLegitimacion.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.findById(dto.getId());
        converter.mergeEntity(jTipoLegitimacion, dto);
        tipoLegitimacionRepository.update(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.getReference(id);
        tipoLegitimacionRepository.delete(jTipoLegitimacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoLegitimacionDTO findById(Long id) {
        JTipoLegitimacion jTipoLegitimacion = tipoLegitimacionRepository.getReference(id);
        TipoLegitimacionDTO tipoLegitimacionDTO = converter.createDTO(jTipoLegitimacion);
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
            LOG.error("Error", e);
            List<TipoLegitimacionGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificador(String identificador) {
        return tipoLegitimacionRepository.existeIdentificador(identificador);
    }
}
