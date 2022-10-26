package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSilencioAdministrativoFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Tipo Silencio Administrativo
 *
 * @author jsegovia
 */
public interface TipoSilencioAdministrativoRepository extends CrudRepository<JTipoSilencioAdministrativo, Long> {

    Optional<JTipoSilencioAdministrativo> findById(String id);

    List<TipoSilencioAdministrativoGridDTO> findPagedByFiltro(TipoSilencioAdministrativoFiltro filtro);

    long countByFiltro(TipoSilencioAdministrativoFiltro filtro);

    Boolean checkIdentificador(String identificador);

    List<TipoSilencioAdministrativoDTO> findAllTipoSilencio();
}
