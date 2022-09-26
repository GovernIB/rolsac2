package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.BoletinOficialMapper;
import es.caib.rolsac2.persistence.model.JBoletinOficial;
import es.caib.rolsac2.persistence.repository.BoletinOficialRepository;
import es.caib.rolsac2.service.facade.BoletinOficialServiceFacade;
import es.caib.rolsac2.service.model.BoletinOficialDTO;
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
@Local(BoletinOficialServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BoletinOficialServiceFacadeBean implements BoletinOficialServiceFacade{

    private static final String ERROR_LITERAL = "Error";

    private static final Logger LOG = LoggerFactory.getLogger(BoletinOficialServiceFacadeBean.class);

    @Inject
    private BoletinOficialRepository boletinOficialRepository;

    @Inject
    private BoletinOficialMapper boletinOficialMapper;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public BoletinOficialDTO findBoletinOficialByCodigo(Long codigo) {
        JBoletinOficial jBoletinOficial = boletinOficialRepository.getReference(codigo);
        BoletinOficialDTO boletinOficialDTO = boletinOficialMapper.createDTO(jBoletinOficial);
        return boletinOficialDTO;
    }
}
