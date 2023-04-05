package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUnidadOrganica;

import java.util.List;

/**
 * Interface de las operaciones básicas sobre tipo de afectación
 *
 * @author Indra
 */
public interface UnidadOrganicaRepository extends CrudRepository<JUnidadOrganica, Long> {

  List<JUnidadOrganica> listarUnidadesHijas(String codigoDir3Padre, Long idEntidad);

  JUnidadOrganica obtenerUnidadRaiz(Long idEntidad);

  void eliminarRegistros(Long idEntidad);
}
