package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de los casos de uso de mantenimiento de una entidad. Es
 * responsabilidad de esta capa definir el limite de las transacciones y la
 * seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant
 * l'{@link ExceptionTranslate} que transforma els errors JPA amb les excepcions
 * de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(EntidadServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EntidadServiceFacadeBean implements EntidadServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(EntidadServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private EntidadConverter converter;

    @Inject
    private UnidadOrganicaRepository unidadOrganicaRepository;

    @Inject
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @Inject
    private NormativaRepository normativaRepository;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private ProcedimientoAuditoriaRepository auditoriaProcedimientoRepository;

    @Inject
    private TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    private TipoMediaUARepository tipoMediaUARepository;

    @Inject
    private TipoMediaEdificioRepository tipoMediaEdificioRepository;

    @Inject
    private TipoProcedimientoRepository tipoProcedimientoRepository;

    @Inject
    private TipoPublicoObjetivoEntidadRepository tipoPublicoObjetivoEntRepository;

    @Inject
    private PlatTramitElectronicaRepository platTramitElectronicaRepository;

    @Inject
    private TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    private ProcesoRepository procesoRepository;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private IndexacionRepository indexacionRepository;

    @Inject
    private IndexacionSIARepository indexacionSIARepository;

    @Inject
    private PluginRepository pluginRepository;

    @Inject
    private TemaRepository temaRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(EntidadDTO dto, UsuarioDTO usuarioDTO) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JEntidad jEntidad = converter.createEntity(dto);
        entidadRepository.create(jEntidad);

        JUsuario jUsuario = usuarioRepository.findById(usuarioDTO.getCodigo());
        usuarioRepository.anyadirNuevoUsuarioEntidad(jUsuario, jEntidad.getCodigo());

        return jEntidad.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(EntidadDTO dto) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getCodigo());
        converter.mergeEntity(jEntidad, dto);
        entidadRepository.update(jEntidad);
    }

    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        List<JUnidadAdministrativa> listaUas = unidadAdministrativaRepository.getUnidadesAdministrativaByEntidadId(id, "es");
        ProcedimientoFiltro procedimientoFiltro = new ProcedimientoFiltro();

        List<ProcedimientoGridDTO> listaProc;
        List<ServicioGridDTO> listaServ;
        List<JNormativa> listaNormativa;

        if (!listaUas.isEmpty()) {
            for (JUnidadAdministrativa ua : listaUas) {

                procedimientoFiltro.setIdUA(ua.getCodigo());
                procedimientoFiltro.setIdEntidad(id);
                procedimientoFiltro.setTipo("P");
                listaProc = procedimientoRepository.findProcedimientosPagedByFiltro(procedimientoFiltro);
                if (!listaProc.isEmpty()) {
                    listaProc.forEach(p -> borrarProcedimientoBase(p.getCodigo()));
                }

                procedimientoFiltro.setIdUA(ua.getCodigo());
                procedimientoFiltro.setTipo("S");
                procedimientoFiltro.setIdEntidad(id);

                listaServ = procedimientoRepository.findServiciosPagedByFiltro(procedimientoFiltro);
                if (!listaServ.isEmpty()) {
                    if (!listaServ.isEmpty()) {
                        listaServ.forEach(p -> borrarProcedimientoBase(p.getCodigo()));
                    }
                }
            }

        }

        //Borramos las indexaciones segun entidad
        indexacionRepository.deleteByEntidad(id);
        indexacionSIARepository.deleteByEntidad(id);

        //Borramos las normativas
        listaNormativa = normativaRepository.findByEntidad(id);
        if (!listaNormativa.isEmpty()) {
            listaNormativa.forEach(nor -> normativaRepository.delete(nor));
        }

        //Borramos los tipo media UA
        tipoMediaUARepository.deleteByEntidad(id);

        //Borramos los tipo media edificio
        tipoMediaEdificioRepository.deleteByEntidad(id);

        //Borramos los tipo tramitación
        tipoTramitacionRepository.deleteByEntidad(id);

        //Borramos las plantillas de tramitación
        platTramitElectronicaRepository.deleteByEntidad(id);

        //Borramos los tipos de público objetivo entidad
        tipoPublicoObjetivoEntRepository.deleteByEntidad(id);

        //Borramos los tipos de procedimiento
        tipoProcedimientoRepository.deleteByEntidad(id);

        //Borramos los usuarios segun entidad
        usuarioRepository.deleteByEntidad(id);

        //Borramos los temas segun entidad
        temaRepository.deleteByEntidad(id);

        //Eliminación del organigrama DIR3
        unidadOrganicaRepository.eliminarRegistros(id);

        //Eliminación de plugins
        pluginRepository.deleteByEntidad(id);

        //Eliminacion de procesos
        procesoRepository.deleteByEntidad(id);

        //UAs
        if (listaUas != null) {
            for (JUnidadAdministrativa ua : listaUas) {
                unidadAdministrativaRepository.deleteUA(ua.getCodigo());
            }
        }

        //Tipo de UAs
        tipoUnidadAdministrativaRepository.deleteByEntidad(id);

        //Borramos definitivamente la entidad
        JEntidad jEntidad = entidadRepository.getReference(id);
        entidadRepository.delete(jEntidad);
    }

    private void borrarProcedimientoBase(Long id) {
        auditoriaProcedimientoRepository.borrarAuditoriasByIdProcedimiento(id);

        JProcedimiento jproc = procedimientoRepository.getReference(id);
        JProcedimientoWorkflow jprocMod = procedimientoRepository.getWF(id, true);
        if (jprocMod != null) {
            procedimientoRepository.deleteWF(jprocMod.getCodigo());
        }
        JProcedimientoWorkflow jprocPub = procedimientoRepository.getWF(id, false);
        if (jprocPub != null) {
            procedimientoRepository.deleteWF(jprocPub.getCodigo());
        }
        procedimientoRepository.delete(jproc);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EntidadDTO findById(Long id) {
        JEntidad jEntidad = entidadRepository.getReference(id);
        EntidadDTO entidadDTO = converter.createDTO(jEntidad);
        return entidadDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<EntidadDTO> findAll() {
        List<JEntidad> listaEntidades = entidadRepository.findAll();
        List<EntidadDTO> listaDTOs = converter.toDTOs(listaEntidades);
        return listaDTOs;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<EntidadGridDTO> findByFiltro(EntidadFiltro filtro) {
        try {
            List<EntidadGridDTO> items = entidadRepository.findPagedByFiltro(filtro);
            long total = entidadRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<EntidadGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    //    @Override
    //    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    //    public Boolean checkIdentificador(String identificador) {
    //        return entidadRepository.checkIdentificador(identificador);
    //    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<EntidadDTO> findByFiltroRest(EntidadFiltro filtro) {
        try {
            List<EntidadDTO> items = entidadRepository.findPagedByFiltroRest(filtro);
            long total = entidadRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<EntidadDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    public String getIdiomaPorDefecto(Long idEntidad) {
        return entidadRepository.getIdiomaPorDefecto(idEntidad);
    }
}
