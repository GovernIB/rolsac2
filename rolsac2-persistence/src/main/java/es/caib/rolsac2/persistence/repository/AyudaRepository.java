package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JAyuda;
import es.caib.rolsac2.service.model.AyudaDTO;
import es.caib.rolsac2.service.model.AyudaGridDTO;
import es.caib.rolsac2.service.model.AyudaImagenGridDTO;
import es.caib.rolsac2.service.model.filtro.AyudaFiltro;

import java.util.List;

/**
 * Ayuda repository
 */
public interface AyudaRepository extends CrudRepository<JAyuda, Long> {


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
     * @return la ayuda
     */
    AyudaDTO getAyuda(String identificador, String perfil);

    /**
     * Obtiene las alertas de un usuario pendientes por leer.
     *
     * @param identificador El identificador de la página
     * @param perfil        El perfil del usuario
     * @return la ayuda
     */
    AyudaGridDTO getAyudaGrid(String identificador, String perfil);


    /**
     * Las ayudas usuario.
     *
     * @param filtro es el filtro
     * @return lista de ayudas
     */
    List<AyudaGridDTO> findAyudaPageByFiltro(AyudaFiltro filtro);

    /**
     * El total de ayudas
     *
     * @param filtro es el filtro
     * @return el total de ayudas
     */
    long countAyudaByFiltro(AyudaFiltro filtro);

    /**
     * Lista de ayudas
     *
     * @return Ayudas
     */
    List<AyudaImagenGridDTO> getImagenes();
}
