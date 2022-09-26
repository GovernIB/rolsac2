package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JBoletinOficial;
import es.caib.rolsac2.service.model.BoletinOficialDTO;

import java.util.List;

public interface BoletinOficialRepository extends CrudRepository<JBoletinOficial, Long>{
    List<BoletinOficialDTO> findBoletinOficial();
}
