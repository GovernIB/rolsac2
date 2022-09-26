package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.TipoNormativaConverter;
import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.repository.TipoNormativaRepository;
import es.caib.rolsac2.service.facade.TipoNormativaServiceFacade;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;


@Logged
@ExceptionTranslate
@Stateless
@Local(TipoNormativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipoNormativaServiceFacadeBean implements TipoNormativaServiceFacade{

    private static final String ERROR_LITERAL = "Error";

    private static final Logger LOG = LoggerFactory.getLogger(TipoNormativaServiceFacadeBean.class);

    @Inject
    private TipoNormativaRepository tipoNormativaRepository;

    @Inject
    private TipoNormativaConverter tipoNormativaConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public TipoNormativaDTO findTipoNormativaByCodigo(Long codigo) {
        JTipoNormativa jTipoNormativa = tipoNormativaRepository.getReference(codigo);
        TipoNormativaDTO tipoNormativaDTO = tipoNormativaConverter.createDTO(jTipoNormativa);
        return tipoNormativaDTO;
    }
}
