package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoPublicoObjetivoConverter;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.persistence.repository.TipoPublicoObjetivoRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoPublicoObjetivoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;
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
@Local(TipoPublicoObjetivoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoPublicoObjetivoServiceFacadeBean implements TipoPublicoObjetivoServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoPublicoObjetivoServiceFacadeBean.class);

    @Inject
    private TipoPublicoObjetivoRepository tipoPublicoObjetivoRepository;

    @Inject
    private TipoPublicoObjetivoConverter converter;

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoPublicoObjetivoDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getId() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoPublicoObjetivo jTipoPublicoObjetivo = converter.createEntity(dto);
        tipoPublicoObjetivoRepository.create(jTipoPublicoObjetivo);
        return jTipoPublicoObjetivo.getId();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoPublicoObjetivoDTO dto) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(dto.getId());
        converter.mergeEntity(jTipoPublicoObjetivo, dto);
        tipoPublicoObjetivoRepository.update(jTipoPublicoObjetivo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(id);
        tipoPublicoObjetivoRepository.delete(jTipoPublicoObjetivo);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoPublicoObjetivoDTO findById(Long id) {
        JTipoPublicoObjetivo jTipoPublicoObjetivo = tipoPublicoObjetivoRepository.getReference(id);
        TipoPublicoObjetivoDTO tipoNormativaDTO = converter.createDTO(jTipoPublicoObjetivo);
        return tipoNormativaDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoPublicoObjetivoGridDTO> findByFiltro(TipoPublicoObjetivoFiltro filtro) {
        try {
            List<TipoPublicoObjetivoGridDTO> items = tipoPublicoObjetivoRepository.findPagedByFiltro(filtro);
            long total = tipoPublicoObjetivoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<TipoPublicoObjetivoGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        try {
            return tipoPublicoObjetivoRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error("Error", e);
            return false;
        }
    }
}
