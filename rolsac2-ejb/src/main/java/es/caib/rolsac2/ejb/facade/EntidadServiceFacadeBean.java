package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.repository.*;
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
    private UnidadAdministrativaRepository unidadAdministrativaRepository;


    @Inject
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @Inject
    private NormativaServiceFacade normativaServiceFacade;

    @Inject
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    /*@Inject
    private AdministracionEntServiceFacadeBean administracionEntServiceFacadeBean;*/

    @Inject
    private TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    private MaestrasEntServiceFacade maestrasEntServiceFacade;

    @Inject
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @Inject
    private ProcesoServiceFacade procesoServiceFacade;

    @Inject
    private ProcedimientoRepositoryBean procedimientoRepositoryBean;

    @Inject
    private ProcedimientoAuditoriaRepository procedimientoAuditoriaRepository;

    @Inject
    private UnidadAdministrativaAuditoriaRepository uaAuditoriaRepository;

    @Inject
    private TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    private IndexacionRepository indexacionRepository;

    @Inject
    private IndexacionSIARepository indexacionSIARepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private PluginRepository pluginRepository;

    @Inject
    private TemaRepository temaRepository;

    @Inject
    private PlatTramitElectronicaRepository platTramitElectronicaRepository;

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
        NormativaFiltro normativaFiltro = new NormativaFiltro();
        TipoUnidadAdministrativaFiltro tUaFiltro = new TipoUnidadAdministrativaFiltro();
        TipoMediaUAFiltro tipoMediaUAFiltro = new TipoMediaUAFiltro();
        TipoMediaFichaFiltro tipoMediaFichaFiltro = new TipoMediaFichaFiltro();
        ProcesoFiltro procesoFiltro = new ProcesoFiltro();

        List<ProcedimientoGridDTO> listaProc = new ArrayList<>();
        List<ServicioGridDTO> listaServ = new ArrayList<>();
        List<NormativaGridDTO> listaNormativa = new ArrayList<>();
        List<PluginDTO> listaPlugin = new ArrayList<>();
        List<TipoUnidadAdministrativaGridDTO> listatUa = new ArrayList<>();
        List<TipoMediaUAGridDTO> listaTMediaUa = new ArrayList<>();
        List<TipoMediaFichaGridDTO> listaTMediaFicha = new ArrayList<>();
        List<TipoProcedimientoDTO> listaTProcedimiento = new ArrayList<>();
        List<ProcesoGridDTO> listaProceso = new ArrayList<>();
        List<TipoPublicoObjetivoEntidadDTO> listaTObjetivo = new ArrayList<>();

        if (!listaUas.isEmpty()) {
            for (UnidadAdministrativaDTO ua : listaUas) {

                procedimientoFiltro.setIdUA(ua.getCodigo());
                procedimientoFiltro.setIdEntidad(id);
                procedimientoFiltro.setTipo("P");
                listaProc = procedimientoServiceFacade.findProcedimientosByFiltro(procedimientoFiltro).getItems();
                if (!listaProc.isEmpty()) {
                    for (ProcedimientoGridDTO dto : listaProc) {
                        procedimientoAuditoriaRepository.borrarAuditoriasByIdProcedimiento(dto.getCodigo());
                        if (dto.getCodigoWFMod() != null) {
                            procedimientoServiceFacade.deleteWF(dto.getCodigoWFMod());
                        }
                        if (dto.getCodigoWFPub() != null) {
                            procedimientoServiceFacade.deleteWF(dto.getCodigoWFPub());
                        }
                    }

                }

                procedimientoFiltro.setIdUA(ua.getCodigo());
                procedimientoFiltro.setTipo("S");
                procedimientoFiltro.setIdEntidad(id);

                listaServ = procedimientoServiceFacade.findServiciosByFiltro(procedimientoFiltro).getItems();
                if (!listaServ.isEmpty()) {
                    for (ServicioGridDTO dto : listaServ) {
                        procedimientoAuditoriaRepository.borrarAuditoriasByIdProcedimiento(dto.getCodigo());
                        if (dto.getCodigoWFMod() != null) {
                            procedimientoServiceFacade.deleteWF(dto.getCodigoWFMod());
                        }
                        if (dto.getCodigoWFPub() != null) {
                            procedimientoServiceFacade.deleteWF(dto.getCodigoWFPub());
                        }
                    }
                }

                listaTObjetivo = maestrasSupServiceFacade.findTipoPublicoObjetivoEntidadByEntidadId(id);
                if (!listaTObjetivo.isEmpty()) {
                    for (TipoPublicoObjetivoEntidadDTO dto : listaTObjetivo) {
                        maestrasSupServiceFacade.deleteTipoPublicoObjetivoEntidad(dto.getCodigo());
                    }
                }

                listaTProcedimiento = maestrasSupServiceFacade.findAllTipoProcedimiento(id);
                if (!listaTProcedimiento.isEmpty()) {
                    for (TipoProcedimientoDTO dto : listaTProcedimiento) {
                        maestrasSupServiceFacade.deleteTipoProcedimiento(dto.getCodigo());
                    }
                }


                normativaFiltro.setIdUA(ua.getCodigo());
                normativaFiltro.setIdioma("es");
                listaNormativa = normativaServiceFacade.findByFiltro(normativaFiltro).getItems();
                if (!listaNormativa.isEmpty()) {
                    for (NormativaGridDTO dto : listaNormativa) {
                        normativaServiceFacade.delete(dto.getCodigo());
                    }
                }
            /*listaPlugin = administracionEntServiceFacadeBean.listPluginsByEntidad(id);
            if (!listaPlugin.isEmpty()) {
                for (PluginDTO dto : listaPlugin) {
                    administracionEntServiceFacadeBean.deletePlugin(dto.getCodigo());
                }
            }*/

                tipoMediaUAFiltro.setIdEntidad(id);
                tipoMediaUAFiltro.setIdioma("es");
                listaTMediaUa = maestrasEntServiceFacade.findByFiltro(tipoMediaUAFiltro).getItems();
                if (!listaTMediaUa.isEmpty()) {
                    for (TipoMediaUAGridDTO dto : listaTMediaUa) {
                        maestrasEntServiceFacade.deleteTipoMediaUA(dto.getCodigo());
                    }
                }
                tipoMediaFichaFiltro.setIdEntidad(id);
                tipoMediaFichaFiltro.setIdioma("es");
                listaTMediaFicha = maestrasEntServiceFacade.findByFiltro(tipoMediaFichaFiltro).getItems();
                if (!listaTMediaFicha.isEmpty()) {
                    for (TipoMediaFichaGridDTO dto : listaTMediaFicha) {
                        maestrasEntServiceFacade.deleteTipoMediaFicha(dto.getCodigo());
                    }
                }

                //Borramos los tipos de tramitacion segun entidad
                tipoTramitacionRepository.deleteByUA(id);

                //Borramos las indexaciones segun entidad
                indexacionRepository.deleteByUA(id);
                indexacionSIARepository.deleteByUA(id);

                //Borramos los usuarios segun entidad
                usuarioRepository.deleteByUA(id);

                //Borramos los plugins segun entidad
                pluginRepository.deleteByUA(id);

                //Borramos los temas segun entidad
                temaRepository.deleteByUA(id);

                //Borramos las plataformas de tramitacion segun entidad
                platTramitElectronicaRepository.deleteByUA(id);
            }


            if (!listaUas.isEmpty()) {
                for (UnidadAdministrativaDTO dto : listaUas) {
                    unidadAdministrativaRepository.deleteUA(dto.getCodigo());
                }
            }


            tipoUnidadAdministrativaRepository.deleteByEntidad(id);
        }

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
