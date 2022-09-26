package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.BoletinOficialDTO;

public interface BoletinOficialServiceFacade {
    BoletinOficialDTO findBoletinOficialByCodigo(Long codigo);
}
