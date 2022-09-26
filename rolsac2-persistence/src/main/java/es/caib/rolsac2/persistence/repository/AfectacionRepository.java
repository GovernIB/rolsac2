package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JAfectacion;
import es.caib.rolsac2.service.model.AfectacionDTO;

import java.util.List;

public interface AfectacionRepository extends CrudRepository<JAfectacion, Long>{
    List<AfectacionDTO> findAfectacion();
}
