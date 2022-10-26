package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JSeccion;
import es.caib.rolsac2.service.model.SeccionGridDTO;
import es.caib.rolsac2.service.model.filtro.SeccionFiltro;


import java.util.List;
import java.util.Optional;

public interface SeccionRepository extends CrudRepository<JSeccion, Long>{
    Optional<JSeccion> findById(String id);

    List<SeccionGridDTO> findPageByFiltro(SeccionFiltro filtro);

    List<JSeccion> getRoot(String idioma, Long entidadId);

    List<JSeccion> getHijos(Long idSeccion, String idioma);

    Long getCountHijos(Long parentId);

    long countByFiltro(SeccionFiltro filtro);

    Boolean checkIdentificador(String identificador);
}
