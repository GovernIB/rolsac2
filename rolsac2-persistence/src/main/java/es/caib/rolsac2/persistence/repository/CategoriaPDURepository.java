package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JCategoriaPDU;
import es.caib.rolsac2.service.model.CategoriaPDUDTO;
import es.caib.rolsac2.service.model.CategoriaPDUGridDTO;
import es.caib.rolsac2.service.model.filtro.CategoriaPDUFiltro;

import java.util.List;
import java.util.Optional;

public interface CategoriaPDURepository extends CrudRepository<JCategoriaPDU, Long> {

    Optional<JCategoriaPDU> findById(String id);

    List<JCategoriaPDU> findByEntidad(Long idEntidad);

    List<CategoriaPDUGridDTO> findPagedByFiltro(CategoriaPDUFiltro filtro);

    long countByFiltro(CategoriaPDUFiltro filtro);

    boolean existeIdentificador(String identificador, Long idEntidad);

    List<CategoriaPDUDTO> findPagedByFiltroRest(CategoriaPDUFiltro filtro);

    void deleteByEntidad(Long idEntidad);

    boolean estaAsociadoCategoriaPDU(Long codigoPDU);
}
