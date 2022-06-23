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
import es.caib.rolsac2.persistence.converter.TipoAfectacionConverter;
import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoAfectacionTraduccion;
import es.caib.rolsac2.persistence.repository.TipoAfectacionRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoAfectacionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.TipoAfectacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

/**
 * Implementación de los casos de uso de mantenimiento de tipo de afectación. Es responsabilidad de esta capa definir el
 * limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(TipoAfectacionServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoAfectacionServiceFacadeBean implements TipoAfectacionServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoAfectacionServiceFacadeBean.class);

    @Inject
    private TipoAfectacionRepository tipoAfectacionRepository;

    @Inject
    private TipoAfectacionConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoAfectacionDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoAfectacion jTipoAfectacion = converter.createEntity(dto);
        tipoAfectacionRepository.create(jTipoAfectacion);
        return jTipoAfectacion.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoAfectacionDTO dto) throws RecursoNoEncontradoException {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.findById(dto.getId());
        converter.mergeEntity(jTipoAfectacion, dto);
        tipoAfectacionRepository.update(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.getReference(id);
        tipoAfectacionRepository.delete(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoAfectacionDTO findById(Long id) {
        JTipoAfectacion jTipoAfectacion = tipoAfectacionRepository.getReference(id);
        return converter.createDTO(jTipoAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoAfectacionGridDTO> findByFiltro(TipoAfectacionFiltro filtro) {
        try {
            List<TipoAfectacionGridDTO> items = tipoAfectacionRepository.findPagedByFiltro(filtro);
            long total = tipoAfectacionRepository.countByFiltro(filtro);
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
        return tipoAfectacionRepository.existeIdentificador(identificador);
    }
}
