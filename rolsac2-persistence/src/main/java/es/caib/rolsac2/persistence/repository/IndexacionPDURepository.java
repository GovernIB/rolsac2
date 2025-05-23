package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.pdu.api.model.ResultadoPdu;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.persistence.model.JIndexacionPdu;
import es.caib.rolsac2.persistence.model.JIndexacionSIA;
import es.caib.rolsac2.service.model.IndexacionPDUDto;
import es.caib.rolsac2.service.model.IndexacionSIADTO;
import es.caib.rolsac2.service.model.filtro.ProcesoPduFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;

import java.util.List;
import java.util.Optional;

public interface IndexacionPDURepository extends CrudRepository<JIndexacionPdu, Long> {

    Optional<JIndexacionSIA> findById(String id);

    List<IndexacionPDUDto> findPagedByFiltro(ProcesoPduFiltro filtro);

    long countByFiltro(ProcesoPduFiltro filtro);

    public boolean existeIndexacion(Long idElemento, String tipo, Long idEntidad);


    void guardarIndexar(Long codigo, String tipo, Long idEntidad, int accion);


    void actualizarDato(IndexacionPDUDto dato, ResultadoPdu resultadoAccion);

    void deleteByEntidad(Long id);

    void deleteByCodElemento(Long codElemento);
}
