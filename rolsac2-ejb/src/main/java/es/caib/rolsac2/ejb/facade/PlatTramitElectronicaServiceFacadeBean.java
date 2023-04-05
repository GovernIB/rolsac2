package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PlatTramitElectronicaConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.PlatTramitElectronicaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.PlatTramitElectronicaGridDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;
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
	private static final String ERROR_LITERAL = "Error";

    @Inject
    private PlatTramitElectronicaRepository platTramitElectronicaRepository;

    @Inject
    private PlatTramitElectronicaConverter converter;

    @Inject
    private EntidadRepository entidadRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(PlatTramitElectronicaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JPlatTramitElectronica jPlatTramitElectronica = converter.createEntity(dto);
        platTramitElectronicaRepository.create(jPlatTramitElectronica);
        return jPlatTramitElectronica.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(PlatTramitElectronicaDTO dto) throws RecursoNoEncontradoException {
        JPlatTramitElectronica jPlatTramitElectronica = platTramitElectronicaRepository.getReference(dto.getCodigo());
        JEntidad jEntidad = entidadRepository.getReference(dto.getCodEntidad().getCodigo());
        jPlatTramitElectronica.setCodEntidad(jEntidad);
        converter.mergeEntity(jPlatTramitElectronica, dto);
        platTramitElectronicaRepository.update(jPlatTramitElectronica);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JPlatTramitElectronica jPlatTramitElectronica = platTramitElectronicaRepository.getReference(id);
        platTramitElectronicaRepository.delete(jPlatTramitElectronica);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public PlatTramitElectronicaDTO findById(Long id) {
        JPlatTramitElectronica jPlatTramitElectronica = platTramitElectronicaRepository.getReference(id);
        PlatTramitElectronicaDTO tipoMateriaSIADTO = converter.createDTO(jPlatTramitElectronica);
        return tipoMateriaSIADTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<PlatTramitElectronicaDTO> findAll(Long idEntidad) {
        List<JPlatTramitElectronica> listaEntidades = platTramitElectronicaRepository.findAll(idEntidad);
        List<PlatTramitElectronicaDTO> listaDTOs = converter.toDTOs(listaEntidades);
        return listaDTOs;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<PlatTramitElectronicaGridDTO> findByFiltro(PlatTramitElectronicaFiltro filtro) {
        try {
            List<PlatTramitElectronicaGridDTO> items = platTramitElectronicaRepository.findPagedByFiltro(filtro);
            long total = platTramitElectronicaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<PlatTramitElectronicaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        return platTramitElectronicaRepository.checkIdentificador(identificador);
    }

    @Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR,
			TypePerfiles.RESTAPI_VALOR })
	public Pagina<PlatTramitElectronicaDTO> findByFiltroRest(PlatTramitElectronicaFiltro filtro) {
		try {
			List<PlatTramitElectronicaDTO> items = platTramitElectronicaRepository.findPagedByFiltroRest(filtro);
			long total = platTramitElectronicaRepository.countByFiltro(filtro);
			return new Pagina<>(items, total);
		} catch (Exception e) {
			LOG.error(ERROR_LITERAL, e);
			List<PlatTramitElectronicaDTO> items = new ArrayList<>();
			long total = items.size();
			return new Pagina<>(items, total);
		}
	}
}
