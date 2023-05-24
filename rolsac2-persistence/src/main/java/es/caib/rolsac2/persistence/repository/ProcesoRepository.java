package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProceso;
import es.caib.rolsac2.service.model.ProcesoDTO;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;

import java.util.List;

public interface ProcesoRepository extends CrudRepository<JProceso, Long> {

    boolean verificarMaestro(final String instanciaId, final int minMaxMaestroInactivo);

    void borrar(final Long codigo);

    void guardar(final ProcesoDTO proceso);

    Integer listarTotal(final ProcesoFiltro filtro);

    List<ProcesoGridDTO> listar(final ProcesoFiltro filtro);

    ProcesoDTO obtenerProcesoPorCodigo(final Long codigo);

    List<ProcesoGridDTO> listar(final String idioma, final String tipo);

    ProcesoDTO obtenerProcesoPorIdentificador(final String identificador, final Long idEntidad);

    ProcesoDTO convertProceso(JProceso jProceso);

    List<ProcesoDTO> findProcesoByEntidad(Long idEntidad);

    void deleteByEntidad(Long id);
}
