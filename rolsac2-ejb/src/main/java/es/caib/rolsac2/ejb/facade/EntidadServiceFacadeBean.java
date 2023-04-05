package es.caib.rolsac2.ejb.facade;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

/**
 * Implementación de los casos de uso de mantenimiento de una entidad. Es
 * responsabilidad de esta capa definir el limite de las transacciones y la
 * seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant
 * l'{@link ExceptionTranslate} que transforma els errors JPA amb les excepcions
 * de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(EntidadServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EntidadServiceFacadeBean implements EntidadServiceFacade {

	private static final Logger LOG = LoggerFactory.getLogger(EntidadServiceFacadeBean.class);
	private static final String ERROR_LITERAL = "Error";

	@Inject
	private EntidadRepository entidadRepository;

	@Inject
	private EntidadConverter converter;

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR })
	public Long create(EntidadDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

		if (dto.getCodigo() != null) {
			throw new DatoDuplicadoException(dto.getCodigo());
		}

		JEntidad jEntidad = converter.createEntity(dto);
		entidadRepository.create(jEntidad);
		return jEntidad.getCodigo();
	}

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR })
	public void update(EntidadDTO dto) throws RecursoNoEncontradoException {
		JEntidad jEntidad = entidadRepository.getReference(dto.getCodigo());
		converter.mergeEntity(jEntidad, dto);
		entidadRepository.update(jEntidad);
	}

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR })
	public void delete(Long id) throws RecursoNoEncontradoException {
		JEntidad jEntidad = entidadRepository.getReference(id);
		entidadRepository.delete(jEntidad);
	}

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR })
	public EntidadDTO findById(Long id) {
		JEntidad jEntidad = entidadRepository.getReference(id);
		EntidadDTO entidadDTO = converter.createDTO(jEntidad);
		return entidadDTO;
	}

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR })
	public List<EntidadDTO> findAll() {
		List<JEntidad> listaEntidades = entidadRepository.findAll();
		List<EntidadDTO> listaDTOs = converter.toDTOs(listaEntidades);
		return listaDTOs;
	}

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR })
	public Pagina<EntidadGridDTO> findByFiltro(EntidadFiltro filtro) {
		try {
			List<EntidadGridDTO> items = entidadRepository.findPagedByFiltro(filtro);
			long total = entidadRepository.countByFiltro(filtro);
			return new Pagina<>(items, total);
		} catch (Exception e) {
			LOG.error("Error", e);
			List<EntidadGridDTO> items = new ArrayList<>();
			long total = items.size();
			return new Pagina<>(items, total);
		}
	}

//    @Override
//    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
//    public Boolean checkIdentificador(String identificador) {
//        return entidadRepository.checkIdentificador(identificador);
//    }

	@Override
	@RolesAllowed({ TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
			TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR,
			TypePerfiles.RESTAPI_VALOR })
	public Pagina<EntidadDTO> findByFiltroRest(EntidadFiltro filtro) {
		try {
			List<EntidadDTO> items = entidadRepository.findPagedByFiltroRest(filtro);
			long total = entidadRepository.countByFiltro(filtro);
			return new Pagina<>(items, total);
		} catch (Exception e) {
			LOG.error(ERROR_LITERAL, e);
			List<EntidadDTO> items = new ArrayList<>();
			long total = items.size();
			return new Pagina<>(items, total);
		}
	}
}
