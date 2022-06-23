package es.caib.rolsac2.ejb.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoFormaInicioConverter;
import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.persistence.model.traduccion.JTipoFormaInicioTraduccion;
import es.caib.rolsac2.persistence.repository.TipoFormaInicioRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoFormaInicioServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

/**
 * Implementación de los casos de uso de mantenimiento de tipo de forma de inicio. Es responsabilidad de esta capa
 * definir el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(TipoFormaInicioServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoFormaInicioServiceFacadeBean implements TipoFormaInicioServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoFormaInicioServiceFacadeBean.class);

    @Inject
    private TipoFormaInicioRepository tipoFormaInicioRepository;

    @Inject
    private TipoFormaInicioConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoFormaInicio jTipoFormaInicio = converter.createEntity(dto);
        tipoFormaInicioRepository.create(jTipoFormaInicio);
        return jTipoFormaInicio.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.findById(dto.getId());
        converter.mergeEntity(jTipoFormaInicio, dto);
        tipoFormaInicioRepository.update(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.getReference(id);
        tipoFormaInicioRepository.delete(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoFormaInicioDTO findById(Long id) {
        JTipoFormaInicio jTipoFormaInicio = tipoFormaInicioRepository.getReference(id);
        return converter.createDTO(jTipoFormaInicio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoFormaInicioGridDTO> findByFiltro(TipoFormaInicioFiltro filtro) {
        try {
            List<TipoFormaInicioGridDTO> items = tipoFormaInicioRepository.findPagedByFiltro(filtro);
            long total = tipoFormaInicioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificador(String identificador) {
        return tipoFormaInicioRepository.existeIdentificador(identificador);
    }
}
