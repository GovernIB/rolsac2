package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoNormativaConverter;
import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.repository.TipoNormativaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.TipoNormativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.TipoNormativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoNormativaFiltro;
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
@Local(TipoNormativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoNormativaServiceFacadeBean implements TipoNormativaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TipoNormativaServiceFacadeBean.class);

    @Inject
    private TipoNormativaRepository tipoNormativaRepository;

    @Inject
    private TipoNormativaConverter converter;

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(TipoNormativaDTO dto, Long idUnitat) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getId() != null) { //.isPresent()) {
            throw new DatoDuplicadoException(dto.getId());
        }

        JTipoNormativa jTipoNormativa = converter.createEntity(dto);
        tipoNormativaRepository.create(jTipoNormativa);
        return jTipoNormativa.getId();
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(TipoNormativaDTO dto) throws RecursoNoEncontradoException {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(dto.getId());
        converter.mergeEntity(jTipoNormativa, dto);
        tipoNormativaRepository.update(jTipoNormativa);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(id);
        tipoNormativaRepository.delete(jTipoNormativa);
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoNormativaDTO findById(Long id) {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(id);
        TipoNormativaDTO tipoNormativaDTO = converter.createDTO(jTipoNormativa);
        return tipoNormativaDTO;
    }

    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<TipoNormativaGridDTO> findByFiltro(TipoNormativaFiltro filtro) {
        try {
            List<TipoNormativaGridDTO> items = tipoNormativaRepository.findPagedByFiltro(filtro);
            long total = tipoNormativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<TipoNormativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }


    @Override
    //@RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        try {
            return tipoNormativaRepository.checkIdentificador(identificador);
        } catch (Exception e) {
            LOG.error("Error", e);
            return false;
        }
    }
}
