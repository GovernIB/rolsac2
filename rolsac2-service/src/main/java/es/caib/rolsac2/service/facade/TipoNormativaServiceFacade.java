package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.TipoNormativaDTO;

public interface TipoNormativaServiceFacade {

    TipoNormativaDTO findTipoNormativaByCodigo(Long codigo);
}
