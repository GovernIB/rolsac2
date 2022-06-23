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
import es.caib.rolsac2.persistence.converter.TipoMateriaSIAConverter;
import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.persistence.repository.TipoMateriaSIARepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoMateriaSIAServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

/**
 * Implementación de los casos de uso de mantenimiento de tipo de materia SIA. Es responsabilidad de esta capa definir
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
@Local(TipoMateriaSIAServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoMateriaSIAServiceFacadeBean implements TipoMateriaSIAServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoMateriaSIAServiceFacadeBean.class);

    @Inject
    private TipoMateriaSIARepository tipoMateriaSIARepository;

    @Inject
    private TipoMateriaSIAConverter converter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getId() != null) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoMateriaSIA jTipoMateriaSIA = converter.createEntity(dto);
        //jTipoMateriaSIA.getDescripcion().get(0).setTipoMateriaSIA(jTipoMateriaSIA);
        //jTipoMateriaSIA.getDescripcion().get(1).setTipoMateriaSIA(jTipoMateriaSIA);
        tipoMateriaSIARepository.create(jTipoMateriaSIA);
        return jTipoMateriaSIA.getId();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.findById(dto.getId());
        converter.mergeEntity(jTipoMateriaSIA, dto);
        /*for (JTipoMateriaSIATraduccion trad : jTipoMateriaSIA.getDescripcion()) {
            trad.setDescripcion(dto.getDescripcion().getTraduccion(trad.getIdioma()));
        }*/
        tipoMateriaSIARepository.update(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.getReference(id);
        tipoMateriaSIARepository.delete(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoMateriaSIADTO findById(Long id) {
        JTipoMateriaSIA jTipoMateriaSIA = tipoMateriaSIARepository.getReference(id);
        return converter.createDTO(jTipoMateriaSIA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoMateriaSIAGridDTO> findByFiltro(TipoMateriaSIAFiltro filtro) {
        try {
            List<TipoMateriaSIAGridDTO> items = tipoMateriaSIARepository.findPagedByFiltro(filtro);
            long total = tipoMateriaSIARepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<TipoMateriaSIAGridDTO> items = new ArrayList<>();
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
                    TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificador(String identificador) {
        return tipoMateriaSIARepository.existeIdentificador(identificador);
    }
}
