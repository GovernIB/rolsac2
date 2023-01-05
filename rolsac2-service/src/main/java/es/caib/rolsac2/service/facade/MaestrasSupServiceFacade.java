package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.*;

import java.util.List;

public interface MaestrasSupServiceFacade {

    /**************************************************
     **************** TIPO AFECTACION *****************
     **************************************************/

    /**
     * Crea un nuevo tipo de afectación a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de afectación
     * @return EL identificador del nuevo tipo de afectación
     */
    Long create(TipoAfectacionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de afectación a la base de datos.
     *
     * @param dto nuevos datos del tipo de afectación
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void update(TipoAfectacionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de afectación de la bbdd
     *
     * @param id identificador del tipo de afectación a borrar
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void deleteTipoAfectacion(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna el tipo de afectación indicado por el identificador
     *
     * @param id identificador del tipo de afectación a buscar
     * @return dato del tipo de afectación indicado o vacío si no existe
     */
    TipoAfectacionDTO findTipoAfectacionById(Long id);

    /**
     * Devuelve una página con el tipo de afectación relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de afectación y la lista tipo de afectación con el rango indicado
     */
    Pagina<TipoAfectacionGridDTO> findByFiltro(TipoAfectacionFiltro filtro);

    /**
     * Método para listar los diferentes tipos de afectación dados de alta.
     * @return
     */
    List<TipoAfectacionDTO> findTipoAfectaciones();

    /**
     * Devuelve si existe un tipo de afectación con el identificador indicado
     *
     * @param identificador identificador del tipo de afectación
     * @return true si existe un tipo de afectación con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoAfectacion(String identificador);


    /**************************************************
     **************** TIPO BOLETIN ********************
     **************************************************/

    /**
     * Crea un nuevo TipoBoletin a la base de datos.
     *
     * @param dto datos del TipoBoletin
     * @return identificador
     */
    Long create(TipoBoletinDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoBoletin a la base de datos.
     *
     * @param dto nuevos datos del TipoBoletin
     * @throws RecursoNoEncontradoException si el TipoBoletin con el id no existe.
     */
    void update(TipoBoletinDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoBoletin de la bbdd
     *
     * @param id identificador del TipoBoletin a borrar
     * @throws RecursoNoEncontradoException si el TipoBoletin con el id no existe.
     */
    void deleteTipoBoletin(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un TipoBoletin indicat per l'identificador.
     *
     * @param id identificador del TipoBoletin a cercar
     * @return dades del TipoBoletin indicat o buid si no existeix.
     */
    TipoBoletinDTO findTipoBoletinById(Long id);

    /**
     * Devuelve una lista de todos los tipoBoletin en la bbdd.
     *
     * @return lista de todos los tipoBoletin.
     */
    List<TipoBoletinDTO> findBoletines();

    /**
     * Devuelve una página con el TipoBoletin relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoBoletin i la llista de TipoBoletin pel rang indicat.
     */
    Pagina<TipoBoletinGridDTO> findByFiltro(TipoBoletinFiltro filtro);

    /**
     * Devuelve una lista de todos los tipoBoletin
     *
     * @return una lista con todos los tipo boletín.
     */
    List<TipoBoletinDTO> findAll();


    /**************************************************
     **************** TIPO FORMA INICIO ***************
     **************************************************/

    /**
     * Crea un nuevo tipo de forma de inicio a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de forma de inicio
     * @return EL identificador del nuevo tipo de forma de inicio
     */
    Long create(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de forma de inicio a la base de datos.
     *
     * @param dto nuevos datos del tipo de forma de inicio
     * @throws RecursoNoEncontradoException si el tipo de forma de inicio con el id no existe.
     */
    void update(TipoFormaInicioDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de forma de inicio de la bbdd
     *
     * @param id identificador del tipo de forma de inicio a borrar
     * @throws RecursoNoEncontradoException si el tipo de forma de inicio con el id no existe.
     */
    void deleteTipoFormaInicio(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna tipo de forma de inicio indicado por el identificador
     *
     * @param id identificador del tipo de forma de inicio a buscar
     * @return los datos del tipo de forma de inicio indicado o vacío si no existe
     */
    TipoFormaInicioDTO findTipoFormaInicioById(Long id);

    /**
     * Devuelve una página con el tipo de forma de inicio relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de forma de inicio y la lista tipo de forma de inicio con el rango indicado
     */
    Pagina<TipoFormaInicioGridDTO> findByFiltro(TipoFormaInicioFiltro filtro);

    /**
     * Devuelve una lista de todos los tipos
     *
     * @param
     * @return lista con todos los tipos de forma inicio
     */

    List<TipoFormaInicioDTO> findAllTipoFormaInicio();

    /**
     * Devuelve si existe un tipo de forma de inicio con el identificador indicado
     *
     * @param identificador identificador del tipo de forma de inicio
     * @return true si existe un tipo de forma de inicio con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoFormaInicio(String identificador);

    /**************************************************
     **************** TIPO LEGITIMACION ***************
     **************************************************/

    /**
     * Crea un nuevo tipoLegitimacion a la base de datos.
     *
     * @param dto datos del tipoLegitimacion
     * @return identificador
     */
    Long create(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipoLegitimacion a la base de datos.
     *
     * @param dto nuevos datos del tipoLegitimacion
     * @throws RecursoNoEncontradoException si el tipoLegitimacion con el id no existe.
     */
    void update(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipoLegitimacion de la bbdd
     *
     * @param id identificador del tipoLegitimacion a borrar
     * @throws RecursoNoEncontradoException si el tipoLegitimacion con el id no existe.
     */
    void deleteTipoLegitimacion(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el tipoLegitimacion indicat per l'identificador.
     *
     * @param id identificador del tipoLegitimacion a cercar
     * @return un opcional amb les dades del tipoLegitimacion indicat o buid si no existeix.
     */
    TipoLegitimacionDTO findTipoLegitimacionById(Long id);

    /**
     * Devuelve una página con el tipoLegitimacion relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de tipoLegitimacion i la llista de tipoLegitimacion pel rang indicat.
     */
    Pagina<TipoLegitimacionGridDTO> findByFiltro(TipoLegitimacionFiltro filtro);

    /**
     * Devuelve si existe un tipo sexo con el identificador indicado
     *
     * @param identificador identificador del tipo legit
     * @return true si existe un tipo legit con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoLegitimacion(String identificador);

    /**
     * Devuelve una lista de TipoLegitimacion
     *
     * @param
     * @return Lista de todos los objetos de TipoLegitimacion en la bbdd
     */
    List<TipoLegitimacionDTO> findAllTipoLegitimacion();

    /**************************************************
     **************** TIPO MATERIA SIA ****************
     **************************************************/

    /**
     * Crea un nuevo tipo de materia SIA a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de materia SIA
     * @return EL identificador del nuevo tipo de materia SIA
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de materia SIA a la base de datos.
     *
     * @param dto nuevos datos del tipo de materia SIA
     * @throws RecursoNoEncontradoException si el tipo de materia SIA con el id no existe.
     */
    void update(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de materia SIA de la bbdd
     *
     * @param id identificador del tipo de materia SIA a borrar
     * @throws RecursoNoEncontradoException si el tipo de materia SIA con el id no existe.
     */
    void deleteTipoMateriaSIA(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con el tipo de materia SIA indicado por el identificador
     *
     * @param id identificador del tipo de materia SIA a buscar
     * @return un opcional con los datos del tipo de materia SIA indicado o vacío si no existe
     */
    TipoMateriaSIADTO findTipoMateriaSIAById(Long id);

    /**
     * Devuelve una página con el tipo de materia SIA relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de materia SIA y la lista tipo de materia SIA con el rango indicado
     */
    Pagina<TipoMateriaSIAGridDTO> findByFiltro(TipoMateriaSIAFiltro filtro);

    /**
     * Devuelve si existe un tipo de materia SIA con el identificador indicado
     *
     * @param identificador identificador del tipo de materia SIA
     * @return true si existe un tipo de materia SIA con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoMateriaSIA(String identificador);

    /**************************************************
     **************** TIPO NORMATIVA ****************
     **************************************************/

    /**
     * Crea un nuevo TipoNormativa a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del TipoNormativa
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo TipoNormativa
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoNormativaDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoNormativa a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoNormativaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoNormativa de la bbdd
     *
     * @param id identificador del TipoNormativa a borrar
     * @throws RecursoNoEncontradoException si el TipoNormativa con el id no existe.
     */
    void deleteTipoNormativa(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoNormativa indicat per l'identificador.
     *
     * @param id identificador del TipoNormativa a cercar
     * @return un opcional amb les dades del TipoNormativa indicat o buid si no existeix.
     */
    TipoNormativaDTO findTipoNormativaById(Long id);

    /**
     * Devuelve una página con el TipoNormativa relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoNormativa i la llista de TipoNormativa pel rang indicat.
     */
    Pagina<TipoNormativaGridDTO> findByFiltro(TipoNormativaFiltro filtro);

    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificadorTipoNormativa(String identificador);

    /**
     * Devuelve todos los tipos de normativa en la bbdd
     *
     * @return lista de todos los tipoNormativa
     */
    List<TipoNormativaDTO> findTipoNormativa();

    /**************************************************
     **************** TIPO PUBLICO OBJ. ***************
     **************************************************/

    /**
     * Crea un nuevo TipoPublicoObjetivo a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del TipoPublicoObjetivo
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo TipoPublicoObjetivo
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoPublicoObjetivoDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoPublicoObjetivo a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoPublicoObjetivoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoPublicoObjetivo de la bbdd
     *
     * @param id identificador del TipoPublicoObjetivo a borrar
     * @throws RecursoNoEncontradoException si el TipoPublicoObjetivo con el id no existe.
     */
    void deleteTipoPublicoObjetivo(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoPublicoObjetivo indicat per l'identificador.
     *
     * @param id identificador del TipoPublicoObjetivo a cercar
     * @return un opcional amb les dades del TipoPublicoObjetivo indicat o buid si no existeix.
     */
    TipoPublicoObjetivoDTO findTipoPublicoObjetivoById(Long id);

    /**
     * Devuelve una página con el TipoPublicoObjetivo relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoPublicoObjetivo i la llista de TipoPublicoObjetivo pel rang indicat.
     */
    Pagina<TipoPublicoObjetivoGridDTO> findByFiltro(TipoPublicoObjetivoFiltro filtro);


    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificadorTipoPublicoObjetivo(String identificador);

    /**
     * Devuelve lista de tipo publico objetivo
     *
     * @param
     * @return lista de tipo publico objetivo
     */

    List<TipoPublicoObjetivoDTO> findAllTiposPublicoObjetivo();

    /**************************************************
     **************** TIPO PROCEDIMIENTO ***********************
     **************************************************/

    /**
     * Crea un nuevo tipoProcedimiento a la base de datos.
     *
     * @param dto datos del tipoProcedimiento
     * @return identificador
     */
    Long create(TipoProcedimientoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipoProcedimiento a la base de datos.
     *
     * @param dto nuevos datos del tipoProcedimiento
     * @throws RecursoNoEncontradoException si el tipoProcedimiento con el id no existe.
     */
    void update(TipoProcedimientoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipoProcedimiento de la bbdd
     *
     * @param id identificador del tipoProcedimiento a borrar
     * @throws RecursoNoEncontradoException si el tipoProcedimiento con el id no existe.
     */
    void deleteTipoProcedimiento(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el tipoProcedimiento indicat per l'identificador.
     *
     * @param id identificador del tipoProcedimiento a cercar
     * @return un opcional amb les dades del tipoProcedimiento indicat o buid si no existeix.
     */
    TipoProcedimientoDTO findTipoProcedimientoById(Long id);

    /**
     * Devuelve una página con el tipoProcedimiento relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de tipoProcedimiento i la llista de tipoProcedimiento pel rang indicat.
     */
    Pagina<TipoProcedimientoGridDTO> findByFiltro(TipoProcedimientoFiltro filtro);

    /**
     * Devuelve si existe un tipo Procedimiento con el identificador indicado
     *
     * @param identificador identificador del tipo Procedimiento
     * @return true si existe un tipo Procedimiento con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoProcedimiento(String identificador);

    /**
     * Devuelve todos los procedimientos de una entidad.
     *
     * @param codigo
     * @return
     */
    List<TipoProcedimientoDTO> findAllTipoProcedimiento(Long codigo);

    /**************************************************
     **************** TIPO SEXO ***********************
     **************************************************/

    /**
     * Crea un nuevo tipoSexo a la base de datos.
     *
     * @param dto datos del tipoSexo
     * @return identificador
     */
    Long create(TipoSexoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipoSexo a la base de datos.
     *
     * @param dto nuevos datos del tipoSexo
     * @throws RecursoNoEncontradoException si el tipoSexo con el id no existe.
     */
    void update(TipoSexoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipoSexo de la bbdd
     *
     * @param id identificador del tipoSexo a borrar
     * @throws RecursoNoEncontradoException si el tipoSexo con el id no existe.
     */
    void deleteTipoSexo(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el tipoSexo indicat per l'identificador.
     *
     * @param id identificador del tipoSexo a cercar
     * @return un opcional amb les dades del tipoSexo indicat o buid si no existeix.
     */
    TipoSexoDTO findTipoSexoById(Long id);

    /**
     * Devuelve una página con el tipoSexo relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de tipoSexo i la llista de tipoSexo pel rang indicat.
     */
    Pagina<TipoSexoGridDTO> findByFiltro(TipoSexoFiltro filtro);

    /**
     * Devuelve si existe un tipo sexo con el identificador indicado
     *
     * @param identificador identificador del tipo sexo
     * @return true si existe un tipo sexo con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoSexo(String identificador);


    /**************************************************
     **************** TIPO SILENCIO ADM. **************
     **************************************************/

    /**
     * Crea un nuevo  TipoSilencioAdministrativo a la base de datos relacionada con la unidad indicada.
     *
     * @param dto      datos del  TipoSilencioAdministrativo
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo  TipoSilencioAdministrativo
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoSilencioAdministrativoDTO dto, Long idUnitat) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un  TipoSilencioAdministrativo a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(TipoSilencioAdministrativoDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un  TipoSilencioAdministrativo de la bbdd
     *
     * @param id identificador del  TipoSilencioAdministrativo a borrar
     * @throws RecursoNoEncontradoException si el  TipoSilencioAdministrativo con el id no existe.
     */
    void deleteTipoSilencioAdministrativo(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el  TipoSilencioAdministrativo indicat per l'identificador.
     *
     * @param id identificador del  TipoSilencioAdministrativo a cercar
     * @return un opcional amb les dades del  TipoSilencioAdministrativo indicat o buid si no existeix.
     */
    TipoSilencioAdministrativoDTO findTipoSilencioAdministrativoById(Long id);

    /**
     * Devuelve una página con el  TipoSilencioAdministrativo relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de  TipoSilencioAdministrativo i la llista de  TipoSilencioAdministrativo pel rang indicat.
     */
    Pagina<TipoSilencioAdministrativoGridDTO> findByFiltro(TipoSilencioAdministrativoFiltro filtro);

    /**
     * Devuelve si existe el identificador en la entidad
     *
     * @param identificador identificador a comprobar
     * @return si existe o no
     */
    Boolean checkIdentificadorTipoSilencioAdministrativo(String identificador);

    /**
     * Devuelve lista de tipo silencio administrativo
     *
     * @param
     * @return lista de tipo silencio adminsitrativo
     */

    List<TipoSilencioAdministrativoDTO> findAllTipoSilencio();

    /**************************************************
     **************** TIPO TRAMITACION  ***************
     **************************************************/

    /**
     * Crea un nuevo tipo de tramitación a la base de datos relacionada con la unidad indicada.
     *
     * @param dto datos del tipo de tramitación
     * @return EL identificador del nuevo tipo de tramitación
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(TipoTramitacionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de tramitación a la base de datos.
     *
     * @param dto nuevos datos del tipo de tramitación
     * @throws RecursoNoEncontradoException si el tipo de tramitación con el id no existe.
     */
    void update(TipoTramitacionDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de tramitación de la bbdd
     *
     * @param id identificador del tipo de tramitación a borrar
     * @throws RecursoNoEncontradoException si el tipo de tramitación con el id no existe.
     */
    void deleteTipoTramitacion(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional con el tipo de tramitación indicado por el identificador
     *
     * @param id identificador del tipo de tramitación a buscar
     * @return un opcional con los datos del tipo de tramitación indicado o vacío si no existe
     */
    TipoTramitacionDTO findTipoTramitacionById(Long id);

    /**
     * Devuelve una página con el tipo de tramitación relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de tramitación y la lista tipo de tramitación con el rango indicado
     */
    Pagina<TipoTramitacionGridDTO> findByFiltro(TipoTramitacionFiltro filtro);

    /**
     * Devuelve lista de tipo tramitación
     *
     * @param
     * @return lista de tipo tramitación
     */

    List<TipoTramitacionDTO> findAllTiposTramitacion();

    /**
     * Devuelve lista de tipo tramitación que son plantillas
     *
     * @param
     * @return lista de tipo tramitación que son plantillas
     */

    List<TipoTramitacionDTO> findPlantillasTiposTramitacion(Long idEntidad);

    /**************************************************
     **************** TIPO VIA  ***********************
     **************************************************/

    /**
     * Crea un nuevo TipoVia a la base de datos.
     *
     * @param dto datos del TipoVia
     * @return identificador
     */
    Long create(TipoViaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un TipoVia a la base de datos.
     *
     * @param dto nuevos datos del TipoVia
     * @throws RecursoNoEncontradoException si el TipoVia con el id no existe.
     */
    void update(TipoViaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un TipoVia de la bbdd
     *
     * @param id identificador del TipoVia a borrar
     * @throws RecursoNoEncontradoException si el TipoVia con el id no existe.
     */
    void deleteTipoVia(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna un opcional amb el TipoVia indicat per l'identificador.
     *
     * @param id identificador del TipoVia a cercar
     * @return un opcional amb les dades del TipoVia indicat o buid si no existeix.
     */
    TipoViaDTO findTipoViaById(Long id);

    /**
     * Devuelve una página con el TipoVia relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de TipoVia i la llista de TipoVia pel rang indicat.
     */
    Pagina<TipoViaGridDTO> findByFiltro(TipoViaFiltro filtro);

    /**
     * Devuelve si existe un tipo via con el identificador indicado
     *
     * @param identificador identificador del tipo via
     * @return true si existe un tipo via con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoVia(String identificador);

    /**
     * Existe identificador de tipo boletin
     *
     * @param identificador
     * @return
     */
    boolean checkIdentificadorTipoBoletin(String identificador);

    Long create(TipoPublicoObjetivoEntidadDTO dto) throws RecursoNoEncontradoException;

    /**
     * Actualiza los datos de un tipo de afectación a la base de datos.
     *
     * @param dto nuevos datos del tipo de afectación
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void update(TipoPublicoObjetivoEntidadDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra un tipo de afectación de la bbdd
     *
     * @param id identificador del tipo de afectación a borrar
     * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
     */
    void deleteTipoPublicoObjetivoEntidad(Long id) throws RecursoNoEncontradoException;

    /**
     * Retorna el tipo de afectación indicado por el identificador
     *
     * @param id identificador del tipo de afectación a buscar
     * @return dato del tipo de afectación indicado o vacío si no existe
     */
    TipoPublicoObjetivoEntidadDTO findTipoPublicoObjetivoEntidadById(Long id);

    /**
     * Retorna si el publico objetivo SIA está relacionado a algún tipo público objetivo por entidad.
     */
    boolean existePublicoObjetivo(Long codigoPO);

    /**
     * Devuelve una página con el tipo de afectación relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una página con el número total de tipo de afectación y la lista tipo de afectación con el rango indicado
     */
    Pagina<TipoPublicoObjetivoEntidadGridDTO> findByFiltro(TipoPublicoObjetivoEntidadFiltro filtro);

    /**
     * Devuelve si existe un tipo de afectación con el identificador indicado
     *
     * @param identificador identificador del tipo de afectación
     * @return true si existe un tipo de afectación con el identificador indicado, false en caso contrario
     */
    boolean existeIdentificadorTipoPublicoObjetivoEntidad(String identificador);

    /**
     * Indica si ya existen procedimientos con el publico objetivo
     *
     * @param codigo
     * @return
     */
    boolean existeProcedimientoConPublicoObjetivo(Long codigo);

    /**
     * Indica si ya existen procedimiento con la materia SIA
     *
     * @param codigo
     * @return
     */
    boolean existeProcedimientoConTipoMateriaSIA(Long codigo);

    /**
     * Indica si ya existen procedimiento con el silencio administrativo
     *
     * @param codigoSilen
     * @return
     */
    boolean existeProcedimientoConSilencio(Long codigoSilen);

    /**
     * Indica si ya existen procedimiento con la forma de inicio
     *
     * @param codigoForIni
     * @return
     */
    boolean existeProcedimientoConFormaInicio(Long codigoForIni);

    /**
     * Indica si ya existen procedimiento con la legitimación
     *
     * @param codigoLegi
     * @return
     */
    boolean existeProcedimientoConLegitimacion(Long codigoLegi);

    /**
     * Devuelve una lista de tipos de via.
     *
     * @return
     */
    List<TipoViaDTO> findAllTipoVia();
}
