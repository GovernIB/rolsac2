package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.service.model.IndexacionDTO;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

import java.util.List;
import java.util.Optional;

public interface NormativaRepository extends CrudRepository<JNormativa, Long> {
    Optional<JNormativa> findById(String id);

    List<NormativaGridDTO> findPagedByFiltro(NormativaFiltro filtro);

    List<NormativaDTO> findByFiltro(NormativaFiltro filtro);

    long countByFiltro(NormativaFiltro filtro);

    Long countByEntidad(Long entidadId);

    Long countByUa(Long uaId);

    Long countAll();

    boolean existeTipoNormativa(Long codigoTipoNor);

    boolean existeBoletin(Long codigoBol);

    Pagina<IndexacionDTO> getNormativasParaIndexacion(Long idEntidad);

    List<NormativaDTO> findPagedByFiltroRest(NormativaFiltro filtro);

    List<JNormativa> findByEntidad(Long idEntidad);

    /**
     * Obtiuene la normativa de baja
     *
     * @param codigoUA
     * @return
     */
    NormativaDTO getNormativaBaja(Long codigoUA);

    /**
     * Obtiene las normativas de las uas
     *
     * @param uas
     * @return
     */
    List<NormativaDTO> getNormativaByUas(List<Long> uas, String idioma);

    /**
     * Actualizamos todas las normativas a las nuevas UAs
     *
     * @param idUAs
     * @param codigoUAfusion
     */
    void actualizarUA(List<Long> idUAs, Long codigoUAfusion);

    void evolucionarNorm(Long codigoNormativa, Long codigoUAOrigen, Long codigoUADestino);

    /**
     * Obtiene el idioma de la entidad
     *
     * @param codigoNorm
     * @return
     */
    String obtenerIdiomaEntidad(Long codigoNorm);
}
