package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.AyudaDTO;
import es.caib.rolsac2.service.model.AyudaGridDTO;
import es.caib.rolsac2.service.model.AyudaImagenGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.AyudaFiltro;

import java.util.List;

public interface AyudaServiceFacade {

    /**
     * Obtener ayuda segun filtro
     *
     * @param filtro Filtro
     * @return Lista de ayudas
     */
    List<AyudaGridDTO> findPageByFiltro(AyudaFiltro filtro);

    /**
     * Borra la ayuda segun id
     *
     * @param idAyuda Id de la ayuda
     */
    void borrarAyudaById(Long idAyuda);

    /**
     * Obtiene las alertas de un usuario pendientes por leer.
     *
     * @param identificador El identificador de la página
     * @param perfil        El perfil del usuario
     * @return Ayuda
     */
    AyudaDTO getAyuda(String identificador, String perfil);

    /**
     * Obtiene las alertas de un usuario pendientes por leer.
     *
     * @param identificador El identificador de la página
     * @param perfil        El perfil del usuario
     * @return Ayuda
     */
    AyudaGridDTO getAyudaGrid(String identificador, String perfil);


    /**
     * Las ayudas usuario.
     *
     * @param filtro es el filtro
     * @return lista de ayudas
     */
    Pagina<AyudaGridDTO> findAyudaPageByFiltro(AyudaFiltro filtro);

    /**
     * El total de ayudas
     *
     * @param filtro es el filtro
     * @return el total de ayudas
     */
    long countAyudaByFiltro(AyudaFiltro filtro);

    Long create(AyudaDTO dto);

    void update(AyudaDTO dto) throws RecursoNoEncontradoException;

    AyudaGridDTO findGridById(Long id);

    AyudaGridDTO findGridByIdentificador(String identificador, String perfil);

    AyudaDTO findByIdentificador(String id, String todos);

    List<AyudaImagenGridDTO> listImagenes(String path);

    List<AyudaImagenGridDTO> listImagenesPerdidas(List<AyudaImagenGridDTO> data, String path);
}
