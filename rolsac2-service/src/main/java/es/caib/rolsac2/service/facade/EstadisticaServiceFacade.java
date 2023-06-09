package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.EstadisticaException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.auditoria.EstadisticaDTO;
import es.caib.rolsac2.service.model.EstadisticaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.auditoria.Periodo;
import es.caib.rolsac2.service.model.filtro.EstadisticaFiltro;

public interface EstadisticaServiceFacade {

    Long create(EstadisticaFiltro filtro) throws RecursoNoEncontradoException;

    void delete(Long id) throws RecursoNoEncontradoException;

    void grabarAcceso(EstadisticaFiltro filtro) throws EstadisticaException;

}
