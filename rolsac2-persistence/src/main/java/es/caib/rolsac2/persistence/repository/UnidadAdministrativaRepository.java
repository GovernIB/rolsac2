package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

import java.util.List;
import java.util.Optional;

public interface UnidadAdministrativaRepository extends CrudRepository<JUnidadAdministrativa, Long> {
    Optional<JUnidadAdministrativa> findById(String id);

    List<UnidadAdministrativaGridDTO> findPagedByFiltro(UnidadAdministrativaFiltro filtro);

    List<JUnidadAdministrativa> getHijos(Long idUnitat, String idioma);

    JUnidadAdministrativa getRoot(String idioma, Long entidadId);

    Long getCountHijos(Long parentId);

    long countByFiltro(UnidadAdministrativaFiltro filtro);

    List<JTipoUnidadAdministrativa> getTipo(Long idUnitat, String idioma);

    Boolean checkIdentificador(String identificador);

    List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId);

    JUnidadAdministrativa findJUAById(UnidadAdministrativaDTO uaResponsable);
}
