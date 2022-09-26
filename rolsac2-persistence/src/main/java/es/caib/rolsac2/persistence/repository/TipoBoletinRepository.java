package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoBoletin;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoBoletinRepository extends CrudRepository<JTipoBoletin, Long> {

    Optional<JTipoBoletin> findById(String id);

    List<TipoBoletinGridDTO> findPagedByFiltro(TipoBoletinFiltro filtro);

    long countByFiltro(TipoBoletinFiltro filtro);

    boolean checkIdentificadorTipoBoletin(String identificador);

    List<TipoBoletinDTO> findAll();
}
