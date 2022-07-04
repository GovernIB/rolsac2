package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PlatTramitElectronicaConverter;
import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.persistence.repository.PlatTramitElectronicaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Implementación de los casos de uso de mantenimiento de una plataforma de tramitación electrónica. Es responsabilidad
 * de esta capa definir el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(PlatTramitElectronicaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PlatTramitElectronicaServiceFacadeBean implements PlatTramitElectronicaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PlatTramitElectronicaServiceFacadeBean.class);

    @Inject
    private PlatTramitElectronicaRepository tipoMateriaSIARepository;

    @Inject
    private PlatTramitElectronicaConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(PlatTramitElectronicaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JPlatTramitElectronica jPlatTramitElectronica = converter.createEntity(dto);
        tipoMateriaSIARepository.create(jPlatTramitElectronica);
        return jPlatTramitElectronica.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(PlatTramitElectronicaDTO dto) throws RecursoNoEncontradoException {
        JPlatTramitElectronica jPlatTramitElectronica = tipoMateriaSIARepository.getReference(dto.getId());
        converter.mergeEntity(jPlatTramitElectronica, dto);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JPlatTramitElectronica jPlatTramitElectronica = tipoMateriaSIARepository.getReference(id);
        tipoMateriaSIARepository.delete(jPlatTramitElectronica);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public PlatTramitElectronicaDTO findById(Long id) {
        JPlatTramitElectronica jPlatTramitElectronica = tipoMateriaSIARepository.getReference(id);
        PlatTramitElectronicaDTO tipoMateriaSIADTO = converter.createDTO(jPlatTramitElectronica);
        return tipoMateriaSIADTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<PlatTramitElectronicaDTO> findAll() {
        List<JPlatTramitElectronica> listaEntidades = tipoMateriaSIARepository.findAll();
        List<PlatTramitElectronicaDTO> listaDTOs = converter.toDTOs(listaEntidades);
        return listaDTOs;
    }

    // @Override
    // @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
    // TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    // public Pagina<PlatTramitElectronicaGridDTO> findByFiltro(PlatTramitElectronicaFiltro filtro) {
    // try {
    // List<PlatTramitElectronicaGridDTO> items = tipoMateriaSIARepository.findPagedByFiltro(filtro);
    // long total = tipoMateriaSIARepository.countByFiltro(filtro);
    // return new Pagina<>(items, total);
    // } catch (Exception e) {
    // LOG.error("Error", e);
    // List<PlatTramitElectronicaGridDTO> items = new ArrayList<>();
    // long total = items.size();
    // return new Pagina<>(items, total);
    // }
    // }
}