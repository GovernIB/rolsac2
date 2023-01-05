package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JAfectacion;


import java.util.List;


public interface AfectacionRepository extends CrudRepository<JAfectacion, Long>{

    List<JAfectacion> findAfectacionesRelacionadas(Long idNormativa);

    List<JAfectacion> findAfectacionesOrigen(Long idNormativaOrigen);
}
