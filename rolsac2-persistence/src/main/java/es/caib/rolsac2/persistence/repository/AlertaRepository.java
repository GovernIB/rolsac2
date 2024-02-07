package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JAlerta;
import es.caib.rolsac2.service.model.AlertaDTO;
import es.caib.rolsac2.service.model.AlertaGridDTO;
import es.caib.rolsac2.service.model.filtro.AlertaFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Alerta repository
 */
public interface AlertaRepository extends CrudRepository<JAlerta, Long> {

    /**
     * Obtener una alerta por su id
     *
     * @param id
     * @return
     */
    Optional<JAlerta> findById(String id);

    /**
     * Obtener alertas segun filtro
     *
     * @param filtro
     * @return
     */
    List<AlertaGridDTO> findPageByFiltro(AlertaFiltro filtro);

    void borrarAlertasUsuariosByIdAlerta(Long idAlerta);

    /**
     * Obtiene las alertas de un usuario pendientes por leer.
     *
     * @param usuario
     * @param perfiles
     * @param uas
     * @return
     */
    List<AlertaDTO> getAlertas(String usuario, List<String> perfiles, List<Long> uas);

    /**
     * Obtener total alerta segun filtro
     *
     * @param filtro
     * @return
     */
    long countByFiltro(AlertaFiltro filtro);

    /**
     * Obtener alertas segun filtro para restapi
     *
     * @param filtro
     * @return
     */
    List<AlertaDTO> findPagedByFiltroRest(AlertaFiltro filtro);

    /**
     * Las alertas usuario.
     *
     * @param filtro
     * @return
     */
    List<AlertaGridDTO> findAlertaUsuarioPageByFiltro(AlertaFiltro filtro);

    /**
     * El total de alertas usuario
     *
     * @param filtro
     * @return
     */
    long countAlertaUsuarioByFiltro(AlertaFiltro filtro);

    /**
     * Crea el alertaUsuarioDTO
     *
     * @param codigo
     * @param identificadorUsuario
     */
    void marcarAlertaLeida(Long codigo, String identificadorUsuario);
}
