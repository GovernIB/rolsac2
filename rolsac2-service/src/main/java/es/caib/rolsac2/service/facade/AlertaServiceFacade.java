package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.AlertaDTO;
import es.caib.rolsac2.service.model.AlertaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.AlertaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import java.util.List;

public interface AlertaServiceFacade {


    Long create(AlertaDTO dto);

    void update(AlertaDTO dto, String idioma) throws RecursoNoEncontradoException;

    void delete(Long id) throws RecursoNoEncontradoException;

    AlertaDTO findById(Long id, String idioma);

    AlertaGridDTO findGridById(Long id);

    Pagina<AlertaGridDTO> findByFiltro(AlertaFiltro filtro);

    int countByFiltro(AlertaFiltro filtro);


    Pagina<AlertaDTO> findByFiltroRest(AlertaFiltro filtro);

    List<AlertaDTO> getAlertas(String usuario, List<TypePerfiles> perfiles, String lang);

    /**
     * Obtiene las alertas usuario segun el filtro
     *
     * @param filtro
     * @return
     */
    Pagina<AlertaGridDTO> findAlertaUsuarioDTOByFiltro(AlertaFiltro filtro);

    /**
     * Crear la AlertaUsuarioDTO
     *
     * @param codigo
     * @param identificadorUsuario
     */
    void marcarAlertaLeida(Long codigo, String identificadorUsuario);
}
