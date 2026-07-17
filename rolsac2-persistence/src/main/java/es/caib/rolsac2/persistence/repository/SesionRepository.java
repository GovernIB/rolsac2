package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.service.model.SesionDTO;
import es.caib.rolsac2.service.model.filtro.SesionFiltro;

import java.util.List;

/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface SesionRepository extends CrudRepository<JSesion, Long> {

    Boolean checkSesion(Long idUsuario);

    List<JSesion> findAllSesiones();

    Long countAllSesiones();

    List<SesionDTO> findPageByFiltro(SesionFiltro filtro);

    long countByFiltro(SesionFiltro filtro);

    int deleteAllSesiones();

    SesionDTO findByIdUsuario(Long idUsuario);

    boolean comprobarDatos(SesionDTO sesion);

    void borrarSessionByusuario(Long idUsuario);
}
