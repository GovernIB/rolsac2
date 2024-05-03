package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JAfectacion;
import es.caib.rolsac2.service.model.AfectacionDTO;

import java.util.List;


public interface AfectacionRepository extends CrudRepository<JAfectacion, Long> {

    List<JAfectacion> findAfectacionesRelacionadas(Long idNormativa);

    List<JAfectacion> findAfectacionesOrigen(Long idNormativaOrigen);

    List<JAfectacion> findAfectacionesAfectadas(Long idNormativaOrigen);

    void actualizarAfectaciones(Long codigo, List<AfectacionDTO> afectaciones);
}
