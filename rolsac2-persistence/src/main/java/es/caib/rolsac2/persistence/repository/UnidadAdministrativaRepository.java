package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UnidadAdministrativaRepository extends CrudRepository<JUnidadAdministrativa, Long> {

    UnidadAdministrativaDTO findUASimpleByID(Long id, String idioma, Long idEntidadRoot);

    Boolean checkExsiteUa(Long idUa);

    Optional<JUnidadAdministrativa> findById(String id);

    List<UnidadAdministrativaGridDTO> findPagedByFiltro(UnidadAdministrativaFiltro filtro);

    List<JUnidadAdministrativa> getHijos(Long idUnitat, String idioma);

    JUnidadAdministrativa findRootEntidad(Long idEntidad);


    Long getCountHijos(Long parentId);

    long countByFiltro(UnidadAdministrativaFiltro filtro);

    long countByFiltroEntidad(Long entidadId);

    List<JTipoUnidadAdministrativa> getTipo(Long idUnitat, String idioma);

    Boolean checkIdentificador(String identificador);

    List<JUnidadAdministrativa> getUnidadesAdministrativaByEntidadId(Long entidadId, String idioma);

    JUnidadAdministrativa findJUAById(UnidadAdministrativaDTO uaResponsable);

    List<UnidadAdministrativaDTO> getHijosSimple(Long idUnitat, String idioma, UnidadAdministrativaDTO padre);

    List<JUnidadAdministrativa> getUnidadesAdministrativaByUsuario(Long usuarioId);

    List<JUnidadAdministrativa> getUnidadesAdministrativaByNormativa(Long normativaId);

    UnidadAdministrativaGridDTO modelToGridDTO(JUnidadAdministrativa jUnidadAdministrativa);

    String obtenerPadreDir3(Long codigoUA, String idioma);

    boolean existeTipoSexo(Long codigoSex);

    List<Long> getListaHijosRecursivo(Long codigoUA);

    PathUA getPath(UnidadAdministrativaGridDTO uaInstructor);

    UnidadOrganicaDTO obtenerUnidadRaiz(Long idEntidad);

    List<UnidadOrganicaDTO> obtenerUnidadesHijas(String codigoDir3, Long idEntidad);

    Pagina<IndexacionDTO> getUAsParaIndexacion(Long idEntidad);

    boolean isVisibleUA(UnidadAdministrativaDTO uaInstructor);

    List<String[]> getRutaArbol(Long codigo);

    Map<Long, String> obtenerCodigosDIR3(List<Long> codigos);

    String obtenerCodigoDIR3(Long codigoUA);

    List<UnidadAdministrativaDTO> findPagedByFiltroRest(UnidadAdministrativaFiltro fg);

    void deleteUA(Long id);

    void deleteByEntidad(Long id);

    void marcarBaja(Long codigo, Date fechaBaja, TypePerfiles perfil, String usuario, String literal, String param1, String param2);

    void marcarBajaConNormativas(Long codigo, Date fechaBaja, TypePerfiles perfil, String usuario, String literal, String param1, String param2, NormativaDTO normativaBaja, List<NormativaGridDTO> normativasBaja);

    List<JUnidadAdministrativa> getUnidadesAdministrativaByPadre(Long codigoUAOriginal);

    String getNombreUA(List<Long> uas);

    List<UnidadAdministrativaGridDTO> getUnidadesAdministrativaGridDTOByUsuario(Long codigo, String lang);

    UnidadAdministrativaGridDTO getUaRaizEntidad(Long codEntidad);
}
