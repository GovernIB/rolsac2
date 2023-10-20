package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.ejb.facade.procesos.solr.CastUtil;
import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.ejb.util.JSONUtil;
import es.caib.rolsac2.ejb.util.JSONUtilException;
import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.converter.UnidadOrganicaConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.persistence.util.ConstantesNegocio;
import es.caib.rolsac2.service.exception.AuditoriaException;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaValorCampo;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypeAccionAuditoria;
import es.caib.rolsac2.service.model.types.TypeEstadoDir3;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.utils.UtilJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.*;

/**
 * Implementación de los casos de uso de mantenimiento de personal. Es responsabilidad de esta caap definir el limite de
 * las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(UnidadAdministrativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UnidadAdministrativaServiceFacadeBean implements UnidadAdministrativaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(UnidadAdministrativaServiceFacadeBean.class);

    @Resource
    private SessionContext context;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    private EntidadRaizRepository entidadRaizRepository;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private NormativaRepository normativaRepository;

    @Inject
    private IndexacionRepository indexacionRepository;

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private TipoUnidadAdministrativaRepository tipoUnidadAdministrativaRepository;

    @Inject
    private UnidadAdministrativaConverter converter;

    @Inject
    private TipoSexoRepository tipoSexoRepository;


    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private TemaRepository temaRepository;

    @Inject
    private UnidadAdministrativaAuditoriaRepository auditoriaRepository;

    @Inject
    private UnidadOrganicaRepository unidadOrganicaRepository;

    @Inject
    private UnidadOrganicaConverter unidadOrganicaConverter;

    @Inject
    private UnidadAdministrativaConverter unidadAdministrativaConverter;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getHijos(Long idUnitat, String idioma) {
        return converter.createTreeDTOs(unidadAdministrativaRepository.getHijos(idUnitat, idioma));

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaGridDTO> getHijosGrid(Long idUnitat, String idioma) {
        List<JUnidadAdministrativa> jUaHijas = unidadAdministrativaRepository.getHijos(idUnitat, idioma);
        List<UnidadAdministrativaGridDTO> uaHijas = new ArrayList<>();
        for (JUnidadAdministrativa ua : jUaHijas) {
            uaHijas.add(unidadAdministrativaRepository.modelToGridDTO(ua));
        }
        return uaHijas;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getHijosSimple(Long idUnitat, String idioma, UnidadAdministrativaDTO padre) {
        return unidadAdministrativaRepository.getHijosSimple(idUnitat, idioma, padre);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String obtenerPadreDir3(Long codigoUA, String idioma) {
        return unidadAdministrativaRepository.obtenerPadreDir3(codigoUA, idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeTipoSexo(Long codigoSex) {
        return unidadAdministrativaRepository.existeTipoSexo(codigoSex);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<Long> getListaHijosRecursivo(Long codigoUA) {
        return unidadAdministrativaRepository.getListaHijosRecursivo(codigoUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(UnidadAdministrativaDTO dto, TypePerfiles perfil) throws RecursoNoEncontradoException, DatoDuplicadoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JUnidadAdministrativa jUnidadAdministrativa = converter.createEntity(dto);

        JUnidadAdministrativa jUnidadAdministrativaPadre = (dto.getPadre() != null && dto.getPadre().getCodigo() != null) ? unidadAdministrativaRepository.getReference(dto.getPadre().getCodigo()) : null;

        jUnidadAdministrativa.setPadre(jUnidadAdministrativaPadre);

        //Añadimos los usuarios
        Set<JUsuario> usuarios = new HashSet<>();
        if (dto.getUsuariosUnidadAdministrativa() != null) {
            JUsuario jUsuario;
            for (UsuarioGridDTO usuario : dto.getUsuariosUnidadAdministrativa()) {
                jUsuario = usuarioRepository.getReference(usuario.getCodigo());
                usuarios.add(jUsuario);
            }
        }
        jUnidadAdministrativa.setUsuarios(usuarios);

        Set<JTema> temas = new HashSet<>();
        if (dto.getTemas() != null) {
            for (TemaGridDTO tema : dto.getTemas()) {
                JTema jTema = temaRepository.getReference(tema.getCodigo());
                temas.add(jTema);
            }
        }
        jUnidadAdministrativa.setTemas(temas);

        unidadAdministrativaRepository.create(jUnidadAdministrativa);
        indexacionRepository.guardarIndexar(jUnidadAdministrativa.getCodigo(), TypeIndexacion.UNIDAD_ADMINISTRATIVA, jUnidadAdministrativa.getEntidad().getCodigo(), 1);

        dto.setCodigo(jUnidadAdministrativa.getCodigo());
        UnidadAdministrativaDTO dtoAntiguo = UnidadAdministrativaDTO.createInstance();
        crearAuditoria(dtoAntiguo, dto, perfil, "auditoria.flujo.CREAR", TypeAccionAuditoria.ALTA.toString());

        return jUnidadAdministrativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(UnidadAdministrativaDTO dto, UnidadAdministrativaDTO dtoAntiguo, TypePerfiles perfil) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JTipoUnidadAdministrativa jTipoUnidadAdministrativa = null;
        if (dto.getTipo() != null) {
            jTipoUnidadAdministrativa = tipoUnidadAdministrativaRepository.getReference(dto.getTipo().getCodigo());
        }
        JTipoSexo jTipoSexo = null;
        if (dto.getResponsableSexo() != null) {
            jTipoSexo = tipoSexoRepository.getReference(dto.getResponsableSexo().getCodigo());
        }
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.getReference(dto.getCodigo());
        jUnidadAdministrativa.setEntidad(jEntidad);
        jUnidadAdministrativa.setTipo(jTipoUnidadAdministrativa);
        jUnidadAdministrativa.setResponsableSexo(jTipoSexo);

        JUnidadAdministrativa jUnidadAdministrativaPadre = (dto.getPadre() != null && dto.getPadre().getCodigo() != null) ? unidadAdministrativaRepository.getReference(dto.getPadre().getCodigo()) : null;

        jUnidadAdministrativa.setPadre(jUnidadAdministrativaPadre);

        //Actualizamos usuarios
        Set<JUsuario> usuarios = new HashSet<>();
        if (dto.getUsuariosUnidadAdministrativa() != null) {
            JUsuario jUsuario;
            for (UsuarioGridDTO usuario : dto.getUsuariosUnidadAdministrativa()) {
                jUsuario = usuarioRepository.getReference(usuario.getCodigo());
                usuarios.add(jUsuario);
            }
        }

        jUnidadAdministrativa.getUsuarios().clear();
        jUnidadAdministrativa.getUsuarios().addAll(usuarios);

        //Actualizamos temas
        Set<JTema> temas = new HashSet<>();
        if (dto.getTemas() != null) {
            for (TemaGridDTO tema : dto.getTemas()) {
                JTema jTema = temaRepository.getReference(tema.getCodigo());
                temas.add(jTema);
            }
        }

        jUnidadAdministrativa.getTemas().clear();
        jUnidadAdministrativa.getTemas().addAll(temas);

        converter.mergeEntity(jUnidadAdministrativa, dto);
        unidadAdministrativaRepository.update(jUnidadAdministrativa);
        indexacionRepository.guardarIndexar(jUnidadAdministrativa.getCodigo(), TypeIndexacion.UNIDAD_ADMINISTRATIVA, jUnidadAdministrativa.getEntidad().getCodigo(), 1);
        crearAuditoria(dtoAntiguo, dto, perfil, null, TypeAccionAuditoria.MODIFICACION.toString());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.getReference(id);
        //Marcar para borrar
        indexacionRepository.guardarIndexar(jUnidadAdministrativa.getCodigo(), TypeIndexacion.UNIDAD_ADMINISTRATIVA, jUnidadAdministrativa.getEntidad().getCodigo(), 2);
        unidadAdministrativaRepository.deleteUA(id);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaDTO findById(Long id) {
        return converter.createDTO(unidadAdministrativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaGridDTO findGridById(Long id) {
        return unidadAdministrativaRepository.modelToGridDTO(unidadAdministrativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaDTO findUASimpleByID(Long id, String idioma, Long idEntidadRoot) {
        return unidadAdministrativaRepository.findUASimpleByID(id, idioma, idEntidadRoot);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaDTO findRootEntidad(Long idEntidad) {
        return converter.createDTO(unidadAdministrativaRepository.findRootEntidad(idEntidad));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<UnidadAdministrativaGridDTO> findByFiltro(UnidadAdministrativaFiltro filtro) {
        try {
            List<UnidadAdministrativaGridDTO> items = unidadAdministrativaRepository.findPagedByFiltro(filtro);
            long total = unidadAdministrativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<UnidadAdministrativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(UnidadAdministrativaFiltro filtro) {
        return (int) unidadAdministrativaRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countByEntidad(Long entidadId) {
        return unidadAdministrativaRepository.countByFiltroEntidad(entidadId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCountHijos(Long parentId) {
        return unidadAdministrativaRepository.getCountHijos(parentId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AuditoriaGridDTO> findUaAuditoriasById(Long id) {
        return auditoriaRepository.findUaAuditoriasById(id);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TipoSexoDTO> findTipoSexo() {
        try {
            List<TipoSexoDTO> items = tipoSexoRepository.findAll();
            return items;
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        return unidadAdministrativaRepository.checkIdentificador(identificador);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId, String idioma) {
        List<JUnidadAdministrativa> jUnidades = unidadAdministrativaRepository.getUnidadesAdministrativaByEntidadId(entidadId, idioma);
        List<UnidadAdministrativaDTO> unidades = new ArrayList<>();
        for (JUnidadAdministrativa unidad : jUnidades) {
            unidades.add(converter.createDTO(unidad));
        }
        return unidades;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativasByUsuario(Long usuarioId) {
        List<JUnidadAdministrativa> jUnidadesAdministrativas = unidadAdministrativaRepository.getUnidadesAdministrativaByUsuario(usuarioId);
        List<UnidadAdministrativaDTO> unidadesAdministrativas = new ArrayList<>();
        for (JUnidadAdministrativa ua : jUnidadesAdministrativas) {
            UnidadAdministrativaDTO unidadAdministrativaDTO = converter.createDTO(ua);
            unidadesAdministrativas.add(unidadAdministrativaDTO);
        }
        return unidadesAdministrativas;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadOrganicaDTO> obtenerUnidadesHijasDir3(String codigoDir3, Long idEntidad) {
        List<JUnidadOrganica> jUnidadesHijas = unidadOrganicaRepository.listarUnidadesHijas(codigoDir3, idEntidad);
        List<UnidadOrganicaDTO> unidadesHijasRolsac = obtenerUnidadesHijasRolsac(codigoDir3, idEntidad);
        List<UnidadOrganicaDTO> unidadesHijasDir3 = new ArrayList<>();
        for (JUnidadOrganica unidad : jUnidadesHijas) {
            unidadesHijasDir3.add(unidadOrganicaConverter.createDTO(unidad));
        }
        compararUnidadesOrganicasDir3(unidadesHijasDir3, unidadesHijasRolsac);
        return unidadesHijasDir3;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadOrganicaDTO> obtenerUnidadesHijasRolsac(String codigoDir3, Long idEntidad) {
        List<UnidadOrganicaDTO> unidadesHijasRolsac = unidadAdministrativaRepository.obtenerUnidadesHijas(codigoDir3, idEntidad);
        List<JUnidadOrganica> jUnidadesHijas = unidadOrganicaRepository.listarUnidadesHijas(codigoDir3, idEntidad);
        List<UnidadOrganicaDTO> unidadesHijasDir3 = new ArrayList<>();
        for (JUnidadOrganica unidad : jUnidadesHijas) {
            unidadesHijasDir3.add(unidadOrganicaConverter.createDTO(unidad));
        }
        compararUnidadesOrganicasRolsac(unidadesHijasDir3, unidadesHijasRolsac);
        return unidadesHijasRolsac;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadOrganicaDTO obtenerUnidadRaizDir3(Long idEntidad) {
        return unidadOrganicaConverter.createDTO(unidadOrganicaRepository.obtenerUnidadRaiz(idEntidad));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadOrganicaDTO obtenerUnidadRaizRolsac(Long idEntidad) {
        return unidadAdministrativaRepository.obtenerUnidadRaiz(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadOrganicaDTO> findUoByEntidad(Long idEntidad) {
        List<JUnidadOrganica> jUnidadOrganicas = unidadOrganicaRepository.findByEntidad(idEntidad);
        List<UnidadOrganicaDTO> unidadesOrganicas = new ArrayList<>();
        jUnidadOrganicas.forEach(uo -> unidadesOrganicas.add(unidadOrganicaConverter.createDTO(uo)));
        return unidadesOrganicas;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void eliminarOrganigrama(Long idEntidad) {
        unidadOrganicaRepository.eliminarRegistros(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void crearOrganigrama(List<UnidadOrganicaDTO> unidades, Long idEntidad) {
        JEntidad entidad = entidadRepository.getReference(idEntidad);
        for (UnidadOrganicaDTO unidad : unidades) {
            JUnidadOrganica jUnidadOrganica = unidadOrganicaConverter.createEntity(unidad);
            jUnidadOrganica.setEntidad(entidad);
            unidadOrganicaRepository.create(jUnidadOrganica);
        }
    }

    /*******************************************************************************************************************
     * Funciones privadas
     ******************************************************************************************************************/

    /**
     * Crear auditoria
     *
     * @param uaAntigua
     * @param uaNueva
     */
    private void crearAuditoria(final UnidadAdministrativaDTO uaAntigua, final UnidadAdministrativaDTO uaNueva, TypePerfiles perfil, String literalFlujo, String accion) {

        List<AuditoriaCambio> cambios = new ArrayList<>();
        AuditoriaCambio cambio = null;
        cambios = UnidadAdministrativaDTO.auditar(uaAntigua, uaNueva);

        if (!cambios.isEmpty()) {
            try {
                String auditoriaJson = JSONUtil.toJSON(cambios);
                JUnidadAdministrativaAuditoria jUniAdminAuditoria = new JUnidadAdministrativaAuditoria();
                JUnidadAdministrativa jUniAdm = this.unidadAdministrativaRepository.findById(uaNueva.getCodigo());
                jUniAdminAuditoria.setUnidadAdministrativa(jUniAdm);
                Calendar calendar = Calendar.getInstance();
                jUniAdminAuditoria.setFechaModificacion(calendar.getTime());
                jUniAdminAuditoria.setListaModificaciones(auditoriaJson);
                jUniAdminAuditoria.setUsuarioModificacion(uaNueva.getUsuarioAuditoria());
                jUniAdminAuditoria.setUsuarioPerfil(perfil.toString());
                jUniAdminAuditoria.setLiteralFlujo(literalFlujo);
                jUniAdminAuditoria.setAccion(accion);
                this.auditoriaRepository.guardar(jUniAdminAuditoria);

            } catch (final JSONUtilException e) {
                throw new AuditoriaException(e);
            }
        }
    }

    private void compararUnidadesOrganicasDir3(List<UnidadOrganicaDTO> unidadesDir3, List<UnidadOrganicaDTO> unidadesRolsac) {
        if (!unidadesRolsac.containsAll(unidadesDir3)) {
            for (UnidadOrganicaDTO unidadDir3 : unidadesDir3) {
                if (!unidadesRolsac.contains(unidadDir3)) {
                    //De las hijas de Rolsac, filtramos para ver si hay alguna que tenga el DIR3 igual
                    UnidadOrganicaDTO unidadFiltrada = unidadesRolsac.stream().filter(uo -> uo.getCodigoDir3() != null && uo.getCodigoDir3().equals(unidadDir3.getCodigoDir3())).findFirst().orElse(null);
                    //En caso de que no haya ninguna, significa que la unidad es completamente nueva (cod DIR3 nuevo)
                    if (unidadFiltrada == null) {
                        unidadDir3.setEstado(TypeEstadoDir3.NUEVO);
                    } else {
                        //Si accedemos aquí, es que tiene la denominación o la versión diferente
                        unidadDir3.setEstado(TypeEstadoDir3.EXISTE_MODIFICADA);
                    }
                }
            }
        }
    }

    private void compararUnidadesOrganicasRolsac(List<UnidadOrganicaDTO> unidadesDir3, List<UnidadOrganicaDTO> unidadesRolsac) {
        if (!unidadesDir3.containsAll(unidadesRolsac)) {
            for (UnidadOrganicaDTO unidadRolsac : unidadesRolsac) {
                if (!unidadesDir3.contains(unidadRolsac)) {
                    //De las hijas de Dir3, filtramos para ver si hay alguna que tenga el DIR3 igual
                    UnidadOrganicaDTO unidadFiltrada = unidadesDir3.stream().filter(uo -> uo.getCodigoDir3().equals(unidadRolsac.getCodigoDir3())).findFirst().orElse(null);
                    //En caso de que no haya ninguna, significa que la unidad ha evolucionado a otra
                    if (unidadFiltrada == null) {
                        unidadRolsac.setEstado(TypeEstadoDir3.ELIMINADO);
                    } else {
                        //Si accedemos aquí, es que tiene la denominación o la versión diferente
                        unidadRolsac.setEstado(TypeEstadoDir3.EXISTE_MODIFICADA);
                    }
                }
            }
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcedimientoSolrDTO findDataIndexacionUAById(Long codElemento) {
        UnidadAdministrativaDTO uaDTO = (UnidadAdministrativaDTO) this.findById(codElemento);
        PathUA pathUA = unidadAdministrativaRepository.getPath(uaDTO.getUAGrid());

        DataIndexacion dataIndexacion = CastUtil.getDataIndexacion(uaDTO, pathUA);
        ProcedimientoSolrDTO data = new ProcedimientoSolrDTO();
        data.setDataIndexacion(dataIndexacion);
        data.setUnidadAdministrativaDTO(uaDTO);
        data.setPathUA(pathUA);
        return data;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<IndexacionDTO> getUAsParaIndexacion(Long idEntidad) {
        return unidadAdministrativaRepository.getUAsParaIndexacion(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarSolr(IndexacionDTO indexacionDTO, ResultadoAccion resultadoAccion) {
        indexacionRepository.actualizarDato(indexacionDTO, resultadoAccion);
        //unidadAdministrativaRepository.actualizarSolr(indexacionDTO, resultadoAccion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean isVisibleUA(UnidadAdministrativaDTO uaInstructor) {
        return unidadAdministrativaRepository.isVisibleUA(uaInstructor);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public String obtenerCodigoDIR3(Long uaInstructor) {
        return unidadAdministrativaRepository.obtenerCodigoDIR3(uaInstructor);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EntidadRaizDTO getUaRaiz(Long codigoUA) {
        return entidadRaizRepository.getEntidadRaizByUA(codigoUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EntidadRaizDTO getUaRaizByProcedimiento(Long codProcedimiento) {
        Long codigoUA = procedimientoRepository.getUAbyCodProcedimiento(codProcedimiento);
        if (codigoUA == null) {
            return null;
        }
        return entidadRaizRepository.getEntidadRaizByUA(codigoUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void evolucionBasica(Long codigoUA, Date fechaBaja, Literal nombreNuevo, NormativaDTO normativa, EntidadDTO entidad, TypePerfiles perfil, String usuario) {

        JUnidadAdministrativa juaOriginal = unidadAdministrativaRepository.findById(codigoUA);
        String nombreAntiguo = getNombreUA(juaOriginal.getTraducciones());
        UnidadAdministrativaDTO uaOriginal = unidadAdministrativaConverter.createDTO(juaOriginal);

        unidadAdministrativaRepository.marcarBaja(codigoUA, fechaBaja, perfil, usuario, "auditoria.uas.evolucionBasicaBaja", nombreAntiguo, getNombreLiteral(nombreNuevo));
        UnidadAdministrativaDTO nueva = (UnidadAdministrativaDTO) uaOriginal;
        nueva.setEntidad(entidad);
        nueva.setCodigo(null);
        nueva.setEstado(ConstantesNegocio.UNIDADADMINISTRATIVA_ESTADO_VIGENTE);
        nueva.setFechaBaja(null);
        nueva.setNombre(nombreNuevo);
        nueva.setVersion(0);
        JUnidadAdministrativa jnueva = unidadAdministrativaConverter.createEntity(nueva);
        jnueva.setPadre(juaOriginal.getPadre());
        unidadAdministrativaRepository.create(jnueva);

        //Mover todos los datos de procedimientos/servicios a la nueva UA
        procedimientoRepository.actualizarUA(codigoUA, jnueva.getCodigo());

        //Mover todos los datos de normativas y ususarios a la nueva UA
        jnueva = unidadAdministrativaRepository.findById(jnueva.getCodigo());
        juaOriginal = unidadAdministrativaRepository.findById(codigoUA);
        if (juaOriginal.getNormativas() != null) {
            if (jnueva.getNormativas() == null) {
                jnueva.setNormativas(new HashSet<>());
            }
            for (JNormativa jnormativa : juaOriginal.getNormativas()) {
                jnueva.getNormativas().add(jnormativa);
            }

            if (normativa == null) {
                juaOriginal.setNormativas(null);
            } else {
                Set<JNormativa> normativaCierre = new HashSet<>();
                normativaCierre.add(normativaRepository.getReference(normativa.getCodigo()));
                juaOriginal.setNormativas(normativaCierre);
            }
        }

        if (juaOriginal.getUsuarios() != null) {
            if (jnueva.getUsuarios() == null) {
                jnueva.setUsuarios(new HashSet<>());
            }
            for (JUsuario jusuario : juaOriginal.getUsuarios()) {
                jnueva.getUsuarios().add(jusuario);
            }

            juaOriginal.setUsuarios(null);
        }

        unidadAdministrativaRepository.update(juaOriginal);
        unidadAdministrativaRepository.update(jnueva);

        //Cambiamos las uas que tengan de padre el original
        List<JUnidadAdministrativa> jhijos = unidadAdministrativaRepository.getUnidadesAdministrativaByPadre(codigoUA);
        if (jhijos != null && !jhijos.isEmpty()) {
            for (JUnidadAdministrativa jhijo : jhijos) {
                jhijo.setPadre(jnueva);
                unidadAdministrativaRepository.update(jhijo);
            }
        }

        //Generamos la auditoria
        JUnidadAdministrativaAuditoria jAuditoria = new JUnidadAdministrativaAuditoria();
        jAuditoria.setUnidadAdministrativa(juaOriginal);
        jAuditoria.setFechaModificacion(new Date());
        jAuditoria.setUsuarioModificacion(usuario);
        jAuditoria.setUsuarioPerfil(perfil.toString());
        jAuditoria.setLiteralFlujo("auditoria.uas.evolucionBasicaSin");
        jAuditoria.setAccion(TypeAccionAuditoria.MODIFICACION.toString());

        final AuditoriaCambio auditoria = new AuditoriaCambio();
        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setValorAnterior(nombreAntiguo);
        valorCampo.setValorNuevo(getNombreLiteral(nombreNuevo));
        auditoria.setIdCampo("auditoria.uas.evolucionBasica");
        auditoria.setValoresModificados(Arrays.asList(valorCampo));
        jAuditoria.setListaModificaciones(UtilJSON.toJSON(Arrays.asList(auditoria)));
        auditoriaRepository.guardar(jAuditoria);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public NormativaDTO getNormativaBaja(Long codigoUA) {
        return normativaRepository.getNormativaBaja(codigoUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void evolucionDependencia(Long codigoUA, Long codigoUAPadre, EntidadDTO entidad, TypePerfiles perfil, String usuario) {
        JUnidadAdministrativa juaOriginal = unidadAdministrativaRepository.findById(codigoUA);
        JUnidadAdministrativa juaPadre = unidadAdministrativaRepository.findById(codigoUAPadre);

        //Generamos la auditoria
        JUnidadAdministrativaAuditoria jAuditoria = new JUnidadAdministrativaAuditoria();
        jAuditoria.setUnidadAdministrativa(juaOriginal);
        jAuditoria.setFechaModificacion(new Date());
        jAuditoria.setUsuarioModificacion(usuario);
        jAuditoria.setUsuarioPerfil(perfil.toString());
        jAuditoria.setLiteralFlujo("auditoria.uas.evolucionDependenciaSin");
        jAuditoria.setAccion(TypeAccionAuditoria.MODIFICACION.toString());

        final AuditoriaCambio auditoria = new AuditoriaCambio();
        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        valorCampo.setValorAnterior(getNombreUA(juaOriginal.getPadre().getTraducciones()));
        valorCampo.setValorNuevo(getNombreUA(juaPadre.getTraducciones()));
        auditoria.setIdCampo("auditoria.uas.evolucionDependencia");
        auditoria.setValoresModificados(Arrays.asList(valorCampo));
        jAuditoria.setListaModificaciones(UtilJSON.toJSON(Arrays.asList(auditoria)));
        auditoriaRepository.guardar(jAuditoria);

        //Cambiamos el padre (evolucion dependencia)
        juaOriginal.setPadre(juaPadre);
        unidadAdministrativaRepository.update(juaOriginal);
    }

    /**
     * Devuelve el nombre de la unidad administrativa en catalán, si no existe en catalán, devuelve el nombre en el primer idioma que encuentre
     *
     * @param traducciones
     * @return
     */
    private String getNombreUA(List<JUnidadAdministrativaTraduccion> traducciones) {
        if (traducciones == null || traducciones.isEmpty()) {
            return "";
        }

        for (JUnidadAdministrativaTraduccion traduccion : traducciones) {
            if (traduccion.getIdioma().equals(Constantes.IDIOMA_CATALAN)) {
                return traduccion.getNombre();
            }
        }

        return traducciones.get(0).getNombre();
    }

    /**
     * Devuelve el literal en catalán, si no existe en catalán, devuelve el literal en el primer idioma que encuentre
     *
     * @param traducciones
     * @return
     */
    private String getNombreLiteral(Literal traducciones) {
        if (traducciones == null || traducciones.estaVacio()) {
            return "";
        }

        for (Traduccion traduccion : traducciones.getTraducciones()) {
            if (traduccion.getIdioma().equals(Constantes.IDIOMA_CATALAN)) {
                return traduccion.getLiteral();
            }
        }

        return traducciones.getTraducciones().get(0).getLiteral();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean checkCuelga(UnidadAdministrativaDTO padreAntiguo, UnidadAdministrativaDTO padreNuevo) {
        JUnidadAdministrativa jelemento = unidadAdministrativaRepository.findById(padreNuevo.getCodigo());
        Long codigoOriginal = padreAntiguo.getCodigo();
        int bucle = 0;
        while (bucle < 10 && jelemento.getPadre() != null) {
            Long codigoPadre = jelemento.getPadre().getCodigo();
            if (codigoPadre.compareTo(codigoOriginal) == 0) {
                return true;
            }
            jelemento = unidadAdministrativaRepository.findById(codigoPadre);
            bucle++;
        }

        return false;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UsuarioGridDTO> getUsuariosByUas(List<Long> uas) {
        return usuarioRepository.getUsuariosByUas(uas);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<NormativaDTO> getNormativaByUas(List<Long> uas, String idioma) {
        return normativaRepository.getNormativaByUas(uas, idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<NormativaDTO> getNormativaByUa(Long ua, String idioma) {
        List<Long> uas = new ArrayList<>();
        uas.add(ua);
        return normativaRepository.getNormativaByUas(uas, idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcedimientoBaseDTO> getProcedimientosByUas(List<Long> uas, String idioma) {
        return procedimientoRepository.getProcedimientosByUas(uas, idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcedimientoBaseDTO> getProcedimientosByUa(Long ua, String idioma) {
        List<Long> uas = new ArrayList<>();
        uas.add(ua);
        return procedimientoRepository.getProcedimientosByUas(uas, idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<TemaGridDTO> getTemasByUas(List<Long> uas) {
        return temaRepository.getTemasByUas(uas);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void evolucionFusion(List<UnidadAdministrativaGridDTO> selectedUnidades, NormativaDTO normativaBaja, Date fechaBaja, UnidadAdministrativaDTO uaFusion, TypePerfiles perfil, String usuario) {

        //Creamos la nueva ua
        Long codigoUAfusion = this.create(uaFusion, perfil);

        //Generamos la auditoria de la UA Fusionada
        final AuditoriaCambio auditoria = new AuditoriaCambio();
        final AuditoriaValorCampo valorCampo = new AuditoriaValorCampo();
        List<Long> uas = new ArrayList<>();
        for (UnidadAdministrativaGridDTO unidad : selectedUnidades) {
            uas.add(unidad.getCodigo());
        }

        JUnidadAdministrativa juaFusion = unidadAdministrativaRepository.getReference(codigoUAfusion);
        JUnidadAdministrativaAuditoria jAuditoria = new JUnidadAdministrativaAuditoria();
        jAuditoria.setUnidadAdministrativa(juaFusion);
        jAuditoria.setFechaModificacion(new Date());
        jAuditoria.setUsuarioModificacion(usuario);
        jAuditoria.setUsuarioPerfil(perfil.toString());
        jAuditoria.setLiteralFlujo("auditoria.uas.evolucionFusionAlta");
        jAuditoria.setAccion(TypeAccionAuditoria.ALTA.toString());
        valorCampo.setValorAnterior(unidadAdministrativaRepository.getNombreUA(uas));
        String nombreUAfusion = unidadAdministrativaRepository.getNombreUA(Arrays.asList(codigoUAfusion));
        valorCampo.setValorNuevo(nombreUAfusion);
        auditoria.setIdCampo("auditoria.uas.evolucionFusion");
        auditoria.setValoresModificados(Arrays.asList(valorCampo));
        jAuditoria.setListaModificaciones(UtilJSON.toJSON(Arrays.asList(auditoria)));
        auditoriaRepository.guardar(jAuditoria);

        //Mover todos los datos de procedimientos/servicios a la nueva UA
        List<Long> idUAs = new ArrayList<>();
        for (UnidadAdministrativaGridDTO ua : selectedUnidades) {
            idUAs.add(ua.getCodigo());
        }
        procedimientoRepository.actualizarUA(idUAs, codigoUAfusion);

        //Mover todos los datos de normativas a la nueva UA
        normativaRepository.actualizarUA(idUAs, codigoUAfusion);

        //Actualizamos todas las UAs
        for (Long ua : idUAs) {

            //Damos de baja la UA y, si se ha asociado a alguna UA, lo hacemos
            JUnidadAdministrativa jua = unidadAdministrativaRepository.findById(ua);
            unidadAdministrativaRepository.marcarBaja(ua, fechaBaja, perfil, usuario, "auditoria.uas.evolucionFusionBajaauditoria.uas.evolucionFusionBaja", getNombreUA(jua.getTraducciones()), nombreUAfusion);
            Set<JNormativa> normativaCierre = new HashSet<>();
            if (normativaBaja != null) {
                normativaCierre.add(normativaRepository.getReference(normativaBaja.getCodigo()));
            }
            jua.setNormativas(normativaCierre);
            unidadAdministrativaRepository.update(jua);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR, TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<UnidadAdministrativaDTO> findByFiltroRest(UnidadAdministrativaFiltro fg) {
        try {
            List<UnidadAdministrativaDTO> items = unidadAdministrativaRepository.findPagedByFiltroRest(fg);
            long total = unidadAdministrativaRepository.countByFiltro(fg);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<UnidadAdministrativaDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }
}
