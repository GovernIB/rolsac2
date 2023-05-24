package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;

import java.util.List;

public interface TemaServiceFacade {

    List<TemaDTO> getHijos(Long id, String idioma);

    List<TemaGridDTO> getGridHijos(Long id, String idioma);

    List<TemaGridDTO> getGridRoot(String idioma, Long entidadId);

    List<TemaDTO> getRoot(String idioma, Long entidadId);

    Long create(TemaDTO dto);

    void update(TemaDTO dto, String idioma) throws RecursoNoEncontradoException;

    void delete(Long id) throws RecursoNoEncontradoException;

    TemaDTO findById(Long id);

    TemaGridDTO findGridById(Long id);

    Pagina<TemaGridDTO> findByFiltro(TemaFiltro filtro);

    int countByFiltro(TemaFiltro filtro);

    Long getCountHijos(Long parentId);

    Boolean checkIdentificador(String identificador, Long idEntidad);

    Pagina<TemaDTO> findByFiltroRest(TemaFiltro filtro);


}
