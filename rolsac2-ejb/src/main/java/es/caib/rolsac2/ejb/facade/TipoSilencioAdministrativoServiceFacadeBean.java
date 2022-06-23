package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoSilencioAdministrativoConverter;
import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.persistence.repository.TipoSilencioAdministrativoRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoSilencioAdministrativoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSilencioAdministrativoFiltro;
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
@Local(TipoSilencioAdministrativoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoSilencioAdministrativoServiceFacadeBean implements TipoSilencioAdministrativoServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoSilencioAdministrativoServiceFacadeBean.class);

    @Inject
    private TipoSilencioAdministrativoRepository tipoSilencioAdministrativoRepository;

    @Inject
    private TipoSilencioAdministrativoConverter converter;

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoSilencioAdministrativoDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getId() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = converter.createEntity(dto);
        tipoSilencioAdministrativoRepository.create(jTipoSilencioAdministrativo);
        return jTipoSilencioAdministrativo.getId();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoSilencioAdministrativoDTO dto) throws RecursoNoEncontradoException {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(dto.getId());
        converter.mergeEntity(jTipoSilencioAdministrativo, dto);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(id);
        tipoSilencioAdministrativoRepository.delete(jTipoSilencioAdministrativo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoSilencioAdministrativoDTO findById(Long id) {
        JTipoSilencioAdministrativo jTipoSilencioAdministrativo = tipoSilencioAdministrativoRepository.getReference(id);
        TipoSilencioAdministrativoDTO tipoSilencioAdministrativoDTO = converter.createDTO(jTipoSilencioAdministrativo);
        return tipoSilencioAdministrativoDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoSilencioAdministrativoGridDTO> findByFiltro(TipoSilencioAdministrativoFiltro filtro) {
        try {
            List<TipoSilencioAdministrativoGridDTO> items = tipoSilencioAdministrativoRepository.findPagedByFiltro(filtro);
            long total = tipoSilencioAdministrativoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<TipoSilencioAdministrativoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        try {
            return tipoSilencioAdministrativoRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error("Error", e);
            return false;
        }
    }
}
