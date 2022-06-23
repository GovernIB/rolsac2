package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoUnidadAdministrativaObjetivoConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.TipoUnidadAdministrativaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoUnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;
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
 * Implementación de los casos de uso de mantenimiento de personal.
 * Es responsabilidad de esta caap definir el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma
 * els errors JPA amb les excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author jsegovia
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(TipoUnidadAdministrativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoUnidadAdministrativaServiceFacadeBean implements TipoUnidadAdministrativaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoUnidadAdministrativaServiceFacadeBean.class);

    @Inject
    private TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private TipoUnidadAdministrativaObjetivoConverter converter;

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoUnidadAdministrativaDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getId() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoUnidadAdministrativa jTipoPublicoObjetivo = converter.createEntity(dto);
        tipoUnidadAdministrativaRepository.create(jTipoPublicoObjetivo);
        return jTipoPublicoObjetivo.getId();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoUnidadAdministrativaDTO dto) throws RecursoNoEncontradoException {
        JTipoUnidadAdministrativa tipoUnidadAdministrativa = tipoUnidadAdministrativaRepository.getReference(dto.getId());
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getId());
        tipoUnidadAdministrativa.setEntidad(jEntidad);
        converter.mergeEntity(tipoUnidadAdministrativa, dto);
        tipoUnidadAdministrativaRepository.update(tipoUnidadAdministrativa);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoUnidadAdministrativa jTipoPublicoObjetivo = tipoUnidadAdministrativaRepository.getReference(id);
        tipoUnidadAdministrativaRepository.delete(jTipoPublicoObjetivo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoUnidadAdministrativaDTO findById(Long id) {
        JTipoUnidadAdministrativa jTipoPublicoObjetivo = tipoUnidadAdministrativaRepository.getReference(id);
        TipoUnidadAdministrativaDTO tipoNormativaDTO = converter.createDTO(jTipoPublicoObjetivo);
        return tipoNormativaDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoUnidadAdministrativaGridDTO> findByFiltro(TipoUnidadAdministrativaFiltro filtro) {
        try {
            List<TipoUnidadAdministrativaGridDTO> items = tipoUnidadAdministrativaRepository.findPagedByFiltro(filtro);
            long total = tipoUnidadAdministrativaRepository.countByFiltro(filtro);

            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<TipoUnidadAdministrativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        try {
            return tipoUnidadAdministrativaRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error("Error", e);
            return false;
        }
    }

}
