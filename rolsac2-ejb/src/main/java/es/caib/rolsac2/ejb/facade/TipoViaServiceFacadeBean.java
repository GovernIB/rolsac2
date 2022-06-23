package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoViaConverter;
import es.caib.rolsac2.persistence.model.JTipoVia;
import es.caib.rolsac2.persistence.repository.TipoViaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoViaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoViaDTO;
import es.caib.rolsac2.service.model.TipoViaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoViaFiltro;
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
@Local(TipoViaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoViaServiceFacadeBean implements TipoViaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoViaServiceFacadeBean.class);

    @Inject
    private TipoViaRepository tipoViaRepository;

    @Inject
    private TipoViaConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoViaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoVia jTipoVia = converter.createEntity(dto);
        tipoViaRepository.create(jTipoVia);
        return jTipoVia.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoViaDTO dto) throws RecursoNoEncontradoException {
        JTipoVia jTipoVia = tipoViaRepository.findById(dto.getId());
        converter.mergeEntity(jTipoVia, dto);
        tipoViaRepository.update(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoVia jTipoVia = tipoViaRepository.getReference(id);
        tipoViaRepository.delete(jTipoVia);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoViaDTO findById(Long id) {
        JTipoVia jTipoVia = tipoViaRepository.getReference(id);
        TipoViaDTO tipoViaDTO = converter.createDTO(jTipoVia);
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
            LOG.error("Error", e);
            List<TipoViaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
      TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificador(String identificador) {
        return tipoViaRepository.existeIdentificador(identificador);
    }
}
