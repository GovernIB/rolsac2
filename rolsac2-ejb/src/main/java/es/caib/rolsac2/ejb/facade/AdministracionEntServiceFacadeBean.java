package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.*;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;
import es.caib.rolsac2.service.model.filtro.EntidadRaizFiltro;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Logged
@ExceptionTranslate
@Stateless
@Local(AdministracionEntServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AdministracionEntServiceFacadeBean implements AdministracionEntServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(AdministracionEntServiceFacadeBean.class);
    public static final String ERROR_LITERAL = "Error";

    @Inject
    EdificioRepository edificioRepository;

    @Inject
    EdificioConverter edificioConverter;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EntidadRepository entidadRepository;

    @Inject
    EntidadConverter entidadConverter;

    @Inject
    UsuarioConverter converter;

    @Inject
    PluginRepository pluginRepository;

    @Inject
    PluginConverter pluginConverter;

    @Inject
    UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    EntidadRaizConverter entidadRaizConverter;

    @Inject
    EntidadRaizRepository entidadRaizRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(EdificioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JEdificio jEdificio = edificioConverter.createEntity(dto);
        edificioRepository.create(jEdificio);
        return jEdificio.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(EdificioDTO dto) throws RecursoNoEncontradoException {
        JEdificio jEdificio = edificioRepository.findById(dto.getCodigo());
        edificioConverter.mergeEntity(jEdificio, dto);
        edificioRepository.update(jEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JEdificio jEdificio = edificioRepository.getReference(id);
        edificioRepository.delete(jEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EdificioDTO findById(Long id) {
        JEdificio jEdificio = edificioRepository.getReference(id);
        return edificioConverter.createDTO(jEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<EdificioGridDTO> findByFiltro(EdificioFiltro filtro) {
        try {
            List<EdificioGridDTO> items = edificioRepository.findPagedByFiltro(filtro);
            long total = edificioRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    //USUARIO:

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(UsuarioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JUsuario jUsuario = converter.createEntity(dto);

        //Asociación para UAs. En caso de que se hayan asignado UAs al usuario,
        //se recuperan las UAs añadidas y se añaden al modelo de Usuario.
        Set<JUnidadAdministrativa> unidadesAdministrativas = new HashSet<>();
        if (dto.getUnidadesAdministrativas() != null) {
            JUnidadAdministrativa jUnidadAdministrativa;
            for (UnidadAdministrativaGridDTO ua : dto.getUnidadesAdministrativas()) {
                jUnidadAdministrativa = unidadAdministrativaRepository.getReference(ua.getCodigo());
                unidadesAdministrativas.add(jUnidadAdministrativa);
            }
        }
        jUsuario.setUnidadesAdministrativas(unidadesAdministrativas);

        Set<JEntidad> entidades = new HashSet<>();
        if (dto.getEntidades() != null) {
            JEntidad jEntidad;
            for (EntidadGridDTO ent : dto.getEntidades()) {
                jEntidad = entidadRepository.getReference(ent.getCodigo());
                entidades.add(jEntidad);
            }
        }
        jUsuario.setEntidades(entidades);
        usuarioRepository.create(jUsuario);

        return jUsuario.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void createUsuarioEntidad(UsuarioDTO usuarioDTO, Long idEntidad) {
        JUsuario jUsuario = usuarioRepository.findById(usuarioDTO.getCodigo());
        usuarioRepository.anyadirNuevoUsuarioEntidad(jUsuario, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(UsuarioDTO dto) throws RecursoNoEncontradoException {
        JUsuario jUsuario = usuarioRepository.getReference(dto.getCodigo());

        //Asociación para UAs. En caso de que se hayan asignado UAs al usuario,
        //se recuperan las UAs añadidas y se añaden al modelo de Usuario.
        Set<JUnidadAdministrativa> unidadesAdministrativas = new HashSet<>();
        if (dto.getUnidadesAdministrativas() != null) {
            JUnidadAdministrativa jUnidadAdministrativa;
            for (UnidadAdministrativaGridDTO ua : dto.getUnidadesAdministrativas()) {
                jUnidadAdministrativa = unidadAdministrativaRepository.getReference(ua.getCodigo());
                unidadesAdministrativas.add(jUnidadAdministrativa);
            }
        }

        jUsuario.getUnidadesAdministrativas().clear();
        jUsuario.getUnidadesAdministrativas().addAll(unidadesAdministrativas);

        Set<JEntidad> entidades = new HashSet<>();
        if (dto.getEntidades() != null) {
            JEntidad jEntidad;
            for (EntidadGridDTO ent : dto.getEntidades()) {
                jEntidad = entidadRepository.getReference(ent.getCodigo());
                entidades.add(jEntidad);
            }
        }

        jUsuario.getEntidades().clear();
        jUsuario.getEntidades().addAll(entidades);

        converter.mergeEntity(jUsuario, dto);
        usuarioRepository.update(jUsuario);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteUsuario(Long id) throws RecursoNoEncontradoException {
        JUsuario jUsuario = usuarioRepository.getReference(id);
        List<JUnidadAdministrativa> jUnidadesAdministrativas = unidadAdministrativaRepository.getUnidadesAdministrativaByUsuario(id);
        for (JUnidadAdministrativa jUnidadAdministrativa : jUnidadesAdministrativas) {
            jUnidadAdministrativa.getUsuarios().remove(jUsuario);
            unidadAdministrativaRepository.update(jUnidadAdministrativa);
        }

        List<Long> entidadesAsociadas = usuarioRepository.findEntidadesAsociadas(id);
        for (Long entidad : entidadesAsociadas) {
            usuarioRepository.eliminarUsuarioEntidad(jUsuario, entidad);
        }

        usuarioRepository.delete(jUsuario);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteUsuarioEntidad(Long idUsuario, Long idEntidad) {
        JUsuario usuario = usuarioRepository.findById(idUsuario);
        usuarioRepository.eliminarUsuarioEntidad(usuario, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UsuarioDTO findUsuarioById(Long id) {
        JUsuario jUsuario = usuarioRepository.getReference(id);
        UsuarioDTO usuarioDTO = converter.createDTO(jUsuario);
        List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
        if (jUsuario.getUnidadesAdministrativas() != null) {
            for (JUnidadAdministrativa jUnidadAdministrativa : jUsuario.getUnidadesAdministrativas()) {
                UnidadAdministrativaGridDTO unidadAdministrativa = unidadAdministrativaRepository.modelToGridDTO(jUnidadAdministrativa);
                unidadesAdministrativas.add(unidadAdministrativa);
            }
        }
        usuarioDTO.setUnidadesAdministrativas(unidadesAdministrativas);

        List<EntidadGridDTO> entidades = new ArrayList<>();
        List<Long> idsEntidades = usuarioRepository.findEntidadesAsociadas(jUsuario.getCodigo());
        for (Long idEntidad : idsEntidades) {
            entidades.add(entidadConverter.createGridDTO(entidadRepository.findById(idEntidad)));
        }
        usuarioDTO.setEntidades(entidades);
        return usuarioDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UsuarioDTO findUsuarioByIdentificador(String identificador) {
        JUsuario jUsuario = usuarioRepository.findByIdentificador(identificador);
        UsuarioDTO usuarioDTO = converter.createDTO(jUsuario);
        List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
        if (jUsuario.getUnidadesAdministrativas() != null) {
            for (JUnidadAdministrativa jUnidadAdministrativa : jUsuario.getUnidadesAdministrativas()) {
                UnidadAdministrativaGridDTO unidadAdministrativa = unidadAdministrativaRepository.modelToGridDTO(jUnidadAdministrativa);
                unidadesAdministrativas.add(unidadAdministrativa);
            }
        }
        usuarioDTO.setUnidadesAdministrativas(unidadesAdministrativas);

        List<EntidadGridDTO> entidades = new ArrayList<>();
        List<Long> idsEntidades = usuarioRepository.findEntidadesAsociadas(jUsuario.getCodigo());
        for (Long idEntidad : idsEntidades) {
            entidades.add(entidadConverter.createGridDTO(entidadRepository.findById(idEntidad)));
        }
        usuarioDTO.setEntidades(entidades);
        return usuarioDTO;
    }

    @Override
    @PermitAll
    public UsuarioDTO findUsuarioSimpleByIdentificador(String identificador, String lang) {
        UsuarioDTO usuarioDTO = usuarioRepository.findSimpleByIdentificador(identificador, lang);
        if (usuarioDTO == null) {
            return null;
        }

        List<UnidadAdministrativaGridDTO> unidadesAdministrativas = unidadAdministrativaRepository.getUnidadesAdministrativaGridDTOByUsuario(usuarioDTO.getCodigo(), lang);
        usuarioDTO.setUnidadesAdministrativas(unidadesAdministrativas);

        List<EntidadGridDTO> entidades = entidadRepository.getEntidadGridDTOByUsuario(usuarioDTO.getCodigo(), lang);
        usuarioDTO.setEntidades(entidades);

        return usuarioDTO;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<UsuarioGridDTO> findByFiltro(UsuarioFiltro filtro) {
        try {
            List<UsuarioGridDTO> items = usuarioRepository.findPagedByFiltro(filtro);
            long total = usuarioRepository.countByFiltro(filtro);

            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<UsuarioGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<UsuarioGridDTO> findAllUsuariosByFiltro(UsuarioFiltro filtro) {
        try {
            List<UsuarioGridDTO> items = usuarioRepository.findAllUsuariosByFiltro(filtro);
            long total = usuarioRepository.countAllUsuariosByFiltro(filtro);

            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<UsuarioGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @PermitAll
    public Boolean checkIdentificadorUsuario(String identificador) {
        return usuarioRepository.checkIdentificador(identificador);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createPlugin(PluginDTO dto) throws RecursoNoEncontradoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JPlugin jPlugin = pluginConverter.createEntity(dto);
        pluginRepository.create(jPlugin);
        return jPlugin.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updatePlugin(PluginDTO dto) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JPlugin jPlugin = pluginRepository.getReference(dto.getCodigo());
        jPlugin.setEntidad(jEntidad);
        pluginConverter.mergeEntity(jPlugin, dto);
        pluginRepository.update(jPlugin);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deletePlugin(Long id) throws RecursoNoEncontradoException {
        JPlugin jPlugin = pluginRepository.getReference(id);
        pluginRepository.delete(jPlugin);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public PluginDTO findPluginById(Long id) {
        JPlugin jPlugin = pluginRepository.getReference(id);
        return pluginConverter.createDTO(jPlugin);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<PluginGridDTO> findByFiltro(PluginFiltro filtro) {
        try {
            List<PluginGridDTO> items = pluginRepository.findPagedByFiltro(filtro);
            long total = pluginRepository.countByFiltro(filtro);

            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<PluginGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<PluginDTO> listPluginsByEntidad(Long idEntidad) {
        List<JPlugin> jPlugins = pluginRepository.listPluginsByEntidad(idEntidad);
        return pluginConverter.createDTOs(jPlugins);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existePluginTipo(Long codigoPlugin, String tipo) {
        return pluginRepository.existePluginTipo(codigoPlugin, tipo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existePluginTipoByEntidad(Long idEntidad, String tipo) {
        return pluginRepository.existePluginTipoByEntidad(idEntidad, tipo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createEntidadRaiz(EntidadRaizDTO dto) throws RecursoNoEncontradoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JEntidadRaiz jEntidadRaiz = entidadRaizConverter.createEntity(dto);
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.findById(dto.getUa().getCodigo());
        jEntidadRaiz.setUa(jUnidadAdministrativa);
        entidadRaizRepository.create(jEntidadRaiz);
        return jEntidadRaiz.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateEntidadRaiz(EntidadRaizDTO dto) throws RecursoNoEncontradoException {
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaRepository.getReference(dto.getUa().getCodigo());
        JEntidadRaiz jEntidadRaiz = entidadRaizRepository.getReference(dto.getCodigo());
        jEntidadRaiz.setUa(jUnidadAdministrativa);
        entidadRaizConverter.mergeEntity(jEntidadRaiz, dto);
        entidadRaizRepository.update(jEntidadRaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteEntidadRaiz(Long id) throws RecursoNoEncontradoException {
        JEntidadRaiz jEntidadRaiz = entidadRaizRepository.getReference(id);
        entidadRaizRepository.delete(jEntidadRaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EntidadRaizDTO findEntidadRaizById(Long id) {
        JEntidadRaiz jEntidadRaiz = entidadRaizRepository.getReference(id);
        return entidadRaizConverter.createDTO(jEntidadRaiz);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<EntidadRaizGridDTO> findByFiltro(EntidadRaizFiltro filtro) {
        try {
            List<EntidadRaizGridDTO> items = entidadRaizRepository.findPageByFiltro(filtro);
            long total = entidadRaizRepository.countByFiltro(filtro);

            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<EntidadRaizGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }


}
