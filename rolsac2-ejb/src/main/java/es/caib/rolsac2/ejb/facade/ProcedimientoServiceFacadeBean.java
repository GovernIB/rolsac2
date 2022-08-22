package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.repository.PersonalRepositoryBean;
import es.caib.rolsac2.persistence.repository.ProcedimientoRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
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
@Local(ProcedimientoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcedimientoServiceFacadeBean implements ProcedimientoServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalRepositoryBean.class);

    @Resource
    private SessionContext context;
    @Inject
    private ProcedimientoRepository procedimientoRepository;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(ProcedimientoDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {

        //Principal p = context.getCallerPrincipal();
        //context.isCallerInRole()

        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JProcedimiento jProcedimiento = createEntity(dto);
        //jficha.setUnidadAdministrativa(dto.getIdUnidadAdministrativa());
        procedimientoRepository.create(jProcedimiento);
        return jProcedimiento.getCodigo();
    }

    private JProcedimiento createEntity(ProcedimientoDTO dto) {
        //La conversion será muy compleja, hacerla a mano
        JProcedimiento jproc = null;
        return jproc;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(ProcedimientoDTO dto) throws RecursoNoEncontradoException {
        JProcedimiento jficha = procedimientoRepository.getReference(dto.getCodigo());
        ProcedimientoDTO personalDTOAntiguo = createDTO(jficha);
        mergeEntity(jficha, dto);

    }

    private ProcedimientoDTO createDTO(JProcedimiento jficha) {
        //Habrá que hacerlo a mano
        ProcedimientoDTO proc = null;
        return null;
    }

    private void mergeEntity(JProcedimiento jficha, ProcedimientoDTO dto) {
        //Merge manual
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JProcedimiento ficha = procedimientoRepository.getReference(id);
        procedimientoRepository.delete(ficha);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcedimientoDTO findById(Long id) {

        JProcedimiento jProcedimiento = procedimientoRepository.getReference(id);
        ProcedimientoDTO procedimientoDTO = createDTO(jProcedimiento);
        return procedimientoDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<ProcedimientoGridDTO> findByFiltro(ProcedimientoFiltro filtro) {
        List<ProcedimientoGridDTO> items = procedimientoRepository.findPagedByFiltro(filtro);
        long total = procedimientoRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(ProcedimientoFiltro filtro) {
        return (int) procedimientoRepository.countByFiltro(filtro);
    }
}
