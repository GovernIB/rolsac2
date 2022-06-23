package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoBoletinConverter;
import es.caib.rolsac2.persistence.model.JTipoBoletin;
import es.caib.rolsac2.persistence.repository.TipoBoletinRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoBoletinServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;
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
@Local(TipoBoletinServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoBoletinServiceFacadeBean implements TipoBoletinServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoBoletinServiceFacadeBean.class);

    //@Resource
    //private SessionContext context;

    @Inject
    private TipoBoletinRepository tipoBoletinRepository;

    @Inject
    private TipoBoletinConverter converter;

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoBoletinDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoBoletin jTipoBoletin = converter.createEntity(dto);
        tipoBoletinRepository.create(jTipoBoletin);
        return jTipoBoletin.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoBoletinDTO dto) throws RecursoNoEncontradoException {
        JTipoBoletin jTipoBoletin = tipoBoletinRepository.getReference(dto.getId());
        converter.mergeEntity(jTipoBoletin, dto);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoBoletin TipoBoletin = tipoBoletinRepository.getReference(id);
        tipoBoletinRepository.delete(TipoBoletin);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoBoletinDTO findById(Long id) {

        JTipoBoletin TipoBoletin = tipoBoletinRepository.findById(id);
        TipoBoletinDTO TipoBoletinDTO = converter.createDTO(TipoBoletin);
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
            LOG.error("Error", e);
            List<TipoBoletinGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }
}
