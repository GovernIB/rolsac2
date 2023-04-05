package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.persistence.model.JIndexacion;
import es.caib.rolsac2.service.model.IndexacionDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;

import java.util.List;
import java.util.Optional;

public interface IndexacionRepository extends CrudRepository<JIndexacion, Long> {

    Optional<JIndexacion> findById(String id);

    List<IndexacionDTO> findPagedByFiltro(ProcesoSolrFiltro filtro);

    long countByFiltro(ProcesoSolrFiltro filtro);

    public boolean existeIndexacion(Long idElemento, String tipo, Long idEntidad);


    void guardarIndexar(Long codigo, TypeIndexacion procedimiento, Long idEntidad, int accion);

    void actualizarDato(IndexacionDTO proc, ResultadoAccion resultadoAccion);
}
