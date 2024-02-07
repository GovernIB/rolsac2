package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;

import java.util.List;
import java.util.Optional;

public interface TemaRepository extends CrudRepository<JTema, Long> {
    Optional<JTema> findById(String id);

    List<TemaGridDTO> findPageByFiltro(TemaFiltro filtro);

    List<JTema> getRoot(String idioma, Long entidadId);

    List<JTema> getHijos(Long idTema, String idioma);

    Long getCountHijos(Long parentId);

    long countByFiltro(TemaFiltro filtro);

    Boolean checkIdentificador(String identificador, Long idEntidad);

    List<JTema> getHijosTodosNiveles(String mathPath, String idioma);

    List<TemaDTO> findPagedByFiltroRest(TemaFiltro filtro);

    void deleteByEntidad(Long id);

    List<TemaGridDTO> getTemasByUas(List<Long> uas);

    /**
     * Actualizar el jtema
     *
     * @param jTema
     * @param jTipoMateriaSIA
     */
    void actualizar(JTema jTema, JTipoMateriaSIA jTipoMateriaSIA);
}
