package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JFicha;
import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface SesionRepository extends CrudRepository<JSesion, Long> {

    Boolean checkSesion(Long idUsuario);

}
