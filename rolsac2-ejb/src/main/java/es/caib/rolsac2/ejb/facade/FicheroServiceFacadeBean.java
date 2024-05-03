package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.repository.FicheroExternoRepository;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Implementación de los casos de uso de mantenimiento ficheros. <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(FicheroServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FicheroServiceFacadeBean implements FicheroServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(FicheroServiceFacadeBean.class);

    @Inject
    private FicheroExternoRepository ficheroExternoRepository;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public FicheroDTO getContentById(Long idFichero, String path) {
        return ficheroExternoRepository.getContentById(idFichero, path);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public FicheroDTO getContentMetadata(Long idFichero, String path) {
        LOG.debug("Metadata fichero: " + idFichero);
        return ficheroExternoRepository.getContentById(idFichero, path);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public FicheroDTO getFicheroDTOById(Long idFichero) {
        JFicheroExterno jfichero = ficheroExternoRepository.findById(idFichero);
        FicheroDTO ficheroDTO = null;
        if (jfichero != null) {
            ficheroDTO = new FicheroDTO();
            ficheroDTO.setFilename(jfichero.getFilename());
            ficheroDTO.setCodigo(jfichero.getCodigo());
            ficheroDTO.setTipo(TypeFicheroExterno.fromString(jfichero.getTipo()));
        }
        return ficheroDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createFicheroExterno(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String path) {
        return ficheroExternoRepository.createFicheroExterno(content, fileName, tipoFicheroExterno, elementoFicheroExterno, path);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void persistFicheroExterno(Long codigoFichero, Long id, String path) {
        ficheroExternoRepository.persistFicheroExterno(codigoFichero, id, path);
    }


    @Override
    @PermitAll
    public List<Long> getFicherosTemporales() {
        return ficheroExternoRepository.getFicherosTemporales();
    }


    @Override
    @PermitAll
    public void borrarFicheroTemporal(String path, Long idFichero) {
        JFicheroExterno jFicheroExterno = ficheroExternoRepository.findTemporalById(idFichero);
        if (jFicheroExterno != null) {
            ficheroExternoRepository.purgeFicheroExterno(path, jFicheroExterno);
        }
    }

    @Override
    @PermitAll
    public List<Long> getFicherosMarcadosParaBorrar() {
        return ficheroExternoRepository.getFicherosMarcadosParaBorrar();
    }


    @Override
    @PermitAll
    public void borrarFicheroDefinitivamente(String path, Long idFichero) {
        JFicheroExterno jFicheroExterno = ficheroExternoRepository.findBorradoById(idFichero);
        if (jFicheroExterno != null) {
            ficheroExternoRepository.purgeFicheroExterno(path, jFicheroExterno);
        }
    }

}
