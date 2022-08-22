package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.model.JFicha;
import es.caib.rolsac2.persistence.repository.FichaRepository;
import es.caib.rolsac2.persistence.repository.PersonalRepositoryBean;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.FichaServiceFacade;
import es.caib.rolsac2.service.model.FichaDTO;
import es.caib.rolsac2.service.model.FichaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.FichaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * Implementación de los casos de uso de mantenimiento de personal.
 * Es responsabilidad de esta caap definir el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma
 * els errors JPA amb les excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(FichaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FichaServiceFacadeBean implements FichaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalRepositoryBean.class);

    @Resource
    private SessionContext context;
    @Inject
    private FichaRepository fichaRepository;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(FichaDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {

        //Principal p = context.getCallerPrincipal();
        //context.isCallerInRole()

        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JFicha jficha = createEntity(dto);
        //jficha.setUnidadAdministrativa(dto.getIdUnidadAdministrativa());
        fichaRepository.create(jficha);
        return jficha.getCodigo();
    }

    private JFicha createEntity(FichaDTO dto) {
        //TODO muy complejo, hacerlo a mano
        JFicha jficha = null;
        return jficha;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(FichaDTO dto) throws RecursoNoEncontradoException {
        JFicha jficha = fichaRepository.getReference(dto.getCodigo());
        FichaDTO personalDTOAntiguo = createDTO(jficha);
        mergeEntity(jficha, dto);

    }

    private void mergeEntity(JFicha jficha, FichaDTO dto) {
        //TODO muy complejo, hacerlo a mano

    }

    private FichaDTO createDTO(JFicha jficha) {
        //TODO muy complejo, hacerlo a mano
        FichaDTO ficha = null;
        return ficha;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JFicha ficha = fichaRepository.getReference(id);
        fichaRepository.delete(ficha);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public FichaDTO findById(Long id) {

        JFicha jficha = fichaRepository.getReference(id);
        FichaDTO fichaDTO = createDTO(jficha);
        return fichaDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<FichaGridDTO> findByFiltro(FichaFiltro filtro) {
        List<FichaGridDTO> items = fichaRepository.findPagedByFiltro(filtro);
        long total = fichaRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(FichaFiltro filtro) {
        return (int) fichaRepository.countByFiltro(filtro);
    }
}
