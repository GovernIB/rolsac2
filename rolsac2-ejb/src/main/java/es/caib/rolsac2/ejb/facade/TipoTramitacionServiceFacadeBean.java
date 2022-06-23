package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PlatTramitElectronicaConverter;
import es.caib.rolsac2.persistence.converter.TipoTramitacionConverter;
import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.persistence.repository.PlatTramitElectronicaRepository;
import es.caib.rolsac2.persistence.repository.TipoTramitacionRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoTramitacionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.TipoTramitacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;
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
 * Implementación de los casos de uso de mantenimiento de tipo de tramitación. Es responsabilidad de esta capa definir
 * el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(TipoTramitacionServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoTramitacionServiceFacadeBean implements TipoTramitacionServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoTramitacionServiceFacadeBean.class);

    @Inject
    private TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    private TipoTramitacionConverter converter;

    @Inject
    private PlatTramitElectronicaRepository platTramitElectronicaRepository;

    @Inject
    private PlatTramitElectronicaConverter platTramitElectronicaConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoTramitacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoTramitacion jTipoTramitacion = converter.createEntity(dto);
        tipoTramitacionRepository.create(jTipoTramitacion);
        return jTipoTramitacion.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoTramitacionDTO dto) throws RecursoNoEncontradoException {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(dto.getId());

        if (Objects.nonNull(dto.getCodPlatTramitacion()) && Objects.nonNull(dto.getCodPlatTramitacion().getId())) {
            JPlatTramitElectronica jPlatTramitElectronica = platTramitElectronicaRepository.getReference(dto.getCodPlatTramitacion().getId());
            jTipoTramitacion.setCodPlatTramitacion(jPlatTramitElectronica);
        }

        converter.mergeEntity(jTipoTramitacion, dto);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(id);
        tipoTramitacionRepository.delete(jTipoTramitacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoTramitacionDTO findById(Long id) {
        JTipoTramitacion jTipoTramitacion = tipoTramitacionRepository.getReference(id);
        TipoTramitacionDTO tipoTramitacionDTO = converter.createDTO(jTipoTramitacion);
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
            LOG.error("Error", e);
            List<TipoTramitacionGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }
}
