package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.ejb.util.JSONUtil;
import es.caib.rolsac2.ejb.util.JSONUtilException;
import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.AuditoriaException;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.ProcedimientoAuditoria;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
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


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getHijos(Long idUnitat, String idioma) {
        return converter.createTreeDTOs(unidadAdministrativaRepository.getHijos(idUnitat, idioma));

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaGridDTO> getHijosGrid(Long idUnitat, String idioma) {
        List<JUnidadAdministrativa> jUaHijas = unidadAdministrativaRepository.getHijos(idUnitat, idioma);
        List<UnidadAdministrativaGridDTO> uaHijas = new ArrayList<>();
        for(JUnidadAdministrativa ua : jUaHijas) {
            uaHijas.add(unidadAdministrativaRepository.modelToGridDTO(ua));
        }
        return uaHijas;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getHijosSimple(Long idUnitat, String idioma, UnidadAdministrativaDTO padre) {
        return unidadAdministrativaRepository.getHijosSimple(idUnitat, idioma, padre);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String obtenerPadreDir3(Long codigoUA, String idioma) {
        return unidadAdministrativaRepository.obtenerPadreDir3(codigoUA, idioma);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeTipoSexo(Long codigoSex) {
        return unidadAdministrativaRepository.existeTipoSexo(codigoSex);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<Long> getListaHijosRecursivo(Long codigoUA) {
        return unidadAdministrativaRepository.getListaHijosRecursivo(codigoUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(UnidadAdministrativaDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JUnidadAdministrativa jUnidadAdministrativa = converter.createEntity(dto);

        JUnidadAdministrativa jUnidadAdministrativaPadre =
                (dto.getPadre() != null && dto.getPadre().getCodigo() != null) ? unidadAdministrativaRepository.getReference(dto.getPadre().getCodigo())
                        : null;

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
        return jUnidadAdministrativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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

        JUnidadAdministrativa jUnidadAdministrativaPadre =
                (dto.getPadre() != null && dto.getPadre().getCodigo() != null) ? unidadAdministrativaRepository.getReference(dto.getPadre().getCodigo())
                        : null;

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
        this.crearAuditoria(dtoAntiguo,dto, perfil);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.getReference(id);
        unidadAdministrativaRepository.delete(jUnidadAdministrativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public UnidadAdministrativaDTO findById(Long id) {
        return converter.createDTO(unidadAdministrativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaGridDTO findGridById(Long id) {
        return unidadAdministrativaRepository.modelToGridDTO(unidadAdministrativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UnidadAdministrativaDTO findUASimpleByID(Long id, String idioma, Long idEntidadRoot) {
        return unidadAdministrativaRepository.findUASimpleByID(id, idioma, idEntidadRoot);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Pagina<UnidadAdministrativaGridDTO> findByFiltro(UnidadAdministrativaFiltro filtro, boolean isApiRest) {
        try {
            List<UnidadAdministrativaGridDTO> items = unidadAdministrativaRepository.findPagedByFiltro(filtro, isApiRest);
            long total = unidadAdministrativaRepository.countByFiltro(filtro, isApiRest);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<UnidadAdministrativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(UnidadAdministrativaFiltro filtro) {
        return (int) unidadAdministrativaRepository.countByFiltro(filtro, false);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCountHijos(Long parentId) {
        return unidadAdministrativaRepository.getCountHijos(parentId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AuditoriaGridDTO> findUaAuditoriasById(Long id) {
        return auditoriaRepository.findUaAuditoriasById(id);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificador(String identificador) {
        return unidadAdministrativaRepository.checkIdentificador(identificador);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId, String idioma) {
        List<JUnidadAdministrativa> jUnidades = unidadAdministrativaRepository.getUnidadesAdministrativaByEntidadId(entidadId, idioma);
        List<UnidadAdministrativaDTO> unidades = new ArrayList<>();
        for(JUnidadAdministrativa unidad : jUnidades) {
            unidades.add(converter.createDTO(unidad));
        }
        return unidades;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativasByUsuario(Long usuarioId) {
        List<JUnidadAdministrativa> jUnidadesAdministrativas = unidadAdministrativaRepository.getUnidadesAdministrativaByUsuario(usuarioId);
        List<UnidadAdministrativaDTO> unidadesAdministrativas = new ArrayList<>();
        for (JUnidadAdministrativa ua : jUnidadesAdministrativas) {
            UnidadAdministrativaDTO unidadAdministrativaDTO = converter.createDTO(ua);
            unidadesAdministrativas.add(unidadAdministrativaDTO);
        }
        return unidadesAdministrativas;
    }

    /**
     * Crear auditoria
     *
     * @param uaAntigua
     * @param uaNueva
     *
     */
    private void crearAuditoria(final UnidadAdministrativaDTO uaAntigua, final UnidadAdministrativaDTO uaNueva, TypePerfiles perfil) {

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
                this.auditoriaRepository.create(jUniAdminAuditoria);

            } catch (final JSONUtilException e) {
                throw new AuditoriaException(e);
            }
        }
    }
}
