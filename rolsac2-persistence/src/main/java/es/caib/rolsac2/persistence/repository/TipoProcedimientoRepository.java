package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoProcedimiento;
import es.caib.rolsac2.service.model.TipoProcedimientoDTO;
import es.caib.rolsac2.service.model.TipoProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoProcedimientoFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoProcedimientoRepository extends CrudRepository<JTipoProcedimiento, Long> {

    List<TipoProcedimientoDTO> findAll();

    Optional<JTipoProcedimiento> findById(String id);

    List<TipoProcedimientoGridDTO> findPagedByFiltro(TipoProcedimientoFiltro filtro);

    long countByFiltro(TipoProcedimientoFiltro filtro);

    boolean existeIdentificador(String identificador);

    List<TipoProcedimientoDTO> findAll(Long codigoEntidad);

	List<TipoProcedimientoDTO> findPagedByFiltroRest(TipoProcedimientoFiltro filtro);

    void deleteByEntidad(Long idEntidad);

}
