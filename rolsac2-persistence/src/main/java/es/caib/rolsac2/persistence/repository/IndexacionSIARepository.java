package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.persistence.model.JIndexacionSIA;
import es.caib.rolsac2.service.model.IndexacionSIADTO;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;

import java.util.List;
import java.util.Optional;

public interface IndexacionSIARepository extends CrudRepository<JIndexacionSIA, Long> {

    Optional<JIndexacionSIA> findById(String id);

    List<IndexacionSIADTO> findPagedByFiltro(ProcesoSIAFiltro filtro);

    long countByFiltro(ProcesoSIAFiltro filtro);

    public boolean existeIndexacion(Long idElemento, String tipo, Long idEntidad);


    void guardarIndexar(Long codigo, String tipo, Long idEntidad, int estado, int existe);


    void actualizarDato(IndexacionSIADTO dato, ResultadoSIA resultadoAccion);

    void deleteByEntidad(Long id);
}
