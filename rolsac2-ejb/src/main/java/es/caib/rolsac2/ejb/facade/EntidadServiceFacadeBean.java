package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.*;
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
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @Inject
    private NormativaServiceFacade normativaServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @Inject
    private TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    private MaestrasEntServiceFacade maestrasEntServiceFacade;

    @Inject
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @Inject
    private ProcesoServiceFacade procesoServiceFacade;

    @Inject
    private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;

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
    public Long create(EntidadDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JEntidad jEntidad = converter.createEntity(dto);
        entidadRepository.create(jEntidad);
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
        List<UnidadAdministrativaDTO> listaUas = unidadAdministrativaServiceFacade.getUnidadesAdministrativaByEntidadId(id, "es");
        ProcedimientoFiltro procedimientoFiltro = new ProcedimientoFiltro();

        List<ProcedimientoGridDTO> listaProc;
        List<ServicioGridDTO> listaServ;
        List<NormativaDTO> listaNormativa;

        List<TipoMediaUADTO> listaTMediaUa;
        List<TipoMediaEdificioDTO> listaTMediaEdificio;
        List<TipoTramitacionDTO> listaTTramitacion;
        List<PlatTramitElectronicaDTO> listaPlatTramitElec;
        List<TipoProcedimientoDTO> listaTProcedimiento;
        List<TipoPublicoObjetivoEntidadDTO> listaTObjetivo;
        List<ProcesoDTO> listaProceso;

        if (!listaUas.isEmpty()) {
            for (UnidadAdministrativaDTO ua : listaUas) {

                procedimientoFiltro.setIdUA(ua.getCodigo());
                procedimientoFiltro.setIdEntidad(id);
                procedimientoFiltro.setTipo("P");
                listaProc = procedimientoServiceFacade.findProcedimientosByFiltro(procedimientoFiltro).getItems();
                if (!listaProc.isEmpty()) {
                    listaProc.forEach(p -> procedimientoServiceFacade.deleteProcedimientoCompleto(p.getCodigo()));
                }

                procedimientoFiltro.setIdUA(ua.getCodigo());
                procedimientoFiltro.setTipo("S");
                procedimientoFiltro.setIdEntidad(id);

                listaServ = procedimientoServiceFacade.findServiciosByFiltro(procedimientoFiltro).getItems();
                if (!listaServ.isEmpty()) {
                    if (!listaServ.isEmpty()) {
                        listaServ.forEach(p -> procedimientoServiceFacade.deleteProcedimientoCompleto(p.getCodigo()));
                    }
                }
            }

        }

        //Borramos las indexaciones segun entidad
        indexacionRepository.deleteByEntidad(id);
        indexacionSIARepository.deleteByEntidad(id);

        //Borramos las normativas
        listaNormativa = normativaServiceFacade.findByEntidad(id);
        if (!listaNormativa.isEmpty()) {
            listaNormativa.forEach(nor -> normativaServiceFacade.delete(nor.getCodigo()));
        }

        //Borramos los tipo media UA
        listaTMediaUa = maestrasEntServiceFacade.findTipoMediaUAByEntidad(id);
        if (!listaTMediaUa.isEmpty()) {
            listaTMediaUa.forEach(tm -> maestrasEntServiceFacade.deleteTipoMediaUA(tm.getCodigo()));
        }

/*            listaTMediaFicha = maestrasEntServiceFacade.findTipoMediaFichaByEntidad(id);
            if (!listaTMediaFicha.isEmpty()) {
                listaTMediaFicha.forEach(tm -> maestrasEntServiceFacade.deleteTipoMediaFicha(tm.getCodigo()));
            }*/

        //Borramos los tipo media edificio
        listaTMediaEdificio = maestrasEntServiceFacade.findTipoMediaEdificioByEntidad(id);
        if (!listaTMediaEdificio.isEmpty()) {
            listaTMediaEdificio.forEach(tm -> maestrasEntServiceFacade.deleteTipoMediaEdificio(tm.getCodigo()));
        }

        //Borramos los tipo tramitación
        listaTTramitacion = maestrasSupServiceFacade.findTipoTramitacionByEntidad(id);
        if (!listaTTramitacion.isEmpty()) {
            listaTTramitacion.forEach(tm -> maestrasSupServiceFacade.deleteTipoTramitacion(tm.getCodigo()));
        }

        //Borramos las plantillas de tramitación
        listaPlatTramitElec = platTramitElectronicaServiceFacade.findAll(id);
        if (!listaPlatTramitElec.isEmpty()) {
            listaPlatTramitElec.forEach(tm -> platTramitElectronicaServiceFacade.delete(tm.getCodigo()));
        }

        //Borramos los tipos de público objetivo entidad
        listaTObjetivo = maestrasSupServiceFacade.findTipoPublicoObjetivoEntidadByEntidadId(id);
        if (!listaTObjetivo.isEmpty()) {
            listaTObjetivo.forEach(tm -> maestrasSupServiceFacade.deleteTipoPublicoObjetivoEntidad(tm.getCodigo()));
        }

        //Borramos los tipos de procedimiento
        listaTProcedimiento = maestrasSupServiceFacade.findAllTipoProcedimiento(id);
        if (!listaTProcedimiento.isEmpty()) {
            listaTProcedimiento.forEach(tm -> maestrasSupServiceFacade.deleteTipoProcedimiento(tm.getCodigo()));
        }

        //Borramos los usuarios segun entidad
        usuarioRepository.deleteByEntidad(id);

        //Borramos los temas segun entidad
        temaRepository.deleteByEntidad(id);

        //Eliminación del organigrama DIR3
        unidadAdministrativaServiceFacade.eliminarOrganigrama(id);

        //Eliminación de plugins
        pluginRepository.deleteByEntidad(id);

        listaProceso = procesoServiceFacade.findProcesoByEntidad(id);
        if(!listaProceso.isEmpty()) {
            listaProceso.forEach(p -> procesoServiceFacade.borrar(p.getCodigo()));
        }

        if (!listaUas.isEmpty()) {
            listaUas.forEach(p -> unidadAdministrativaServiceFacade.delete(p.getCodigo()));
        }

        tipoUnidadAdministrativaRepository.deleteByEntidad(id);

        JEntidad jEntidad = entidadRepository.getReference(id);
        entidadRepository.delete(jEntidad);
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
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
}
