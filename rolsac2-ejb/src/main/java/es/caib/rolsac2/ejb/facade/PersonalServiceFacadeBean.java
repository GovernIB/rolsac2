package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.PersonalConverter;
import es.caib.rolsac2.persistence.model.JPersonal;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.PersonalRepository;
import es.caib.rolsac2.persistence.repository.PersonalRepositoryBean;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * Implementación de los casos de uso de mantenimiento de personal. Es responsabilidad de esta caap definir el limite de
 * las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(PersonalServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonalServiceFacadeBean implements PersonalServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalRepositoryBean.class);

    @Resource
    private SessionContext context;
    @Inject
    private PersonalRepository personalRepository;
    // @Inject
    // private ProcedimientoAuditoriaOldRepository auditoriaRepository;
    @Inject
    private PersonalConverter converter;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(PersonalDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        // Principal p = context.getCallerPrincipal();
        // context.isCallerInRole()

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JPersonal jPersonal = converter.createEntity(dto);

        if (dto.getUnidadAdministrativa() != null) {
            JUnidadAdministrativa jUnidadAdministrativa =
                    unidadAdministrativaRepository.findById(dto.getUnidadAdministrativa().getCodigo());
            jPersonal.setUnidadAdministrativa(jUnidadAdministrativa);
        }

        personalRepository.create(jPersonal);
        return jPersonal.getCodigo();
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(PersonalDTO dto) throws RecursoNoEncontradoException {
        JPersonal jPersonal = personalRepository.findById(dto.getCodigo());
        converter.mergeEntity(jPersonal, dto);

        if (dto.getUnidadAdministrativa() != null) {
            JUnidadAdministrativa jUnidadAdministrativa =
                    unidadAdministrativaRepository.findById(dto.getUnidadAdministrativa().getCodigo());
            jPersonal.setUnidadAdministrativa(jUnidadAdministrativa);
        }

        personalRepository.update(jPersonal);
    }

    /*
     * private void crearAuditoria(final PersonalDTO personaAntigua, final PersonalDTO personalNuevo) {
     *
     * final PersonalAuditoria personaAuditoria = new PersonalAuditoria(); List<AuditoriaCambio> cambios = new
     * ArrayList<>(); AuditoriaCambio cambio = null; final AuditoriaDTO valores = new AuditoriaDTO();
     *
     * // PERWF_TIPPER cambio = AuditoriaUtil.auditarCampoCadena(personaAntigua == null ? null :
     * personaAntigua.getIdentificador(), personalNuevo.getIdentificador(), Constantes.PERSONA_IDENTIFICADOR,
     * AuditoriaIdioma.NO_IDIOMA); if (cambio != null) { cambios.add(cambio); }
     *
     * valores.setCambios(cambios); // Crea el objeto de auditoria personaAuditoria.setFechaAuditoria(new Date());
     * //personaAuditoria.setUsuarioAuditoria(personalNuevo.getUsuarioAuditoria());
     * personaAuditoria.setPersonalDTO(personalNuevo);
     *
     * String auditoriaJson; try { auditoriaJson = JSONUtil.toJSON(valores);
     * personaAuditoria.setValoresAnteriores(auditoriaJson); //auditoriaRepository.guardarAuditoria(personaAuditoria,
     * JPersonaAuditoria.class); } catch (final JSONUtilException e) { throw new AuditoriaException(e); } }
     */

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JPersonal personal = personalRepository.getReference(id);
        personalRepository.delete(personal);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public PersonalDTO findById(Long id) {
        JPersonal jpersonal = personalRepository.getReference(id);
        return converter.createDTO(jpersonal);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
//    @PermitAll
    public Pagina<PersonalGridDTO> findByFiltro(PersonalFiltro filtro) {
        List<PersonalGridDTO> items = personalRepository.findPagedByFiltro(filtro);
        long total = personalRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    public int countByFiltro(PersonalFiltro filtro) {
        return (int) personalRepository.countByFiltro(filtro);
    }
}
