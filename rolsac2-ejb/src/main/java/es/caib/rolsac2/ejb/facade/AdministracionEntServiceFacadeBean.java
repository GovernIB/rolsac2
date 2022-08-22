package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.EdificioConverter;
import es.caib.rolsac2.persistence.converter.PluginConverter;
import es.caib.rolsac2.persistence.converter.UsuarioConverter;
import es.caib.rolsac2.persistence.model.JEdificio;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JPlugin;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.repository.EdificioRepository;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.PluginRepository;
import es.caib.rolsac2.persistence.repository.UsuarioRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;
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

@Logged
@ExceptionTranslate
@Stateless
@Local(AdministracionEntServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AdministracionEntServiceFacadeBean implements AdministracionEntServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(AdministracionEntServiceFacadeBean.class);
    public static final String ERROR_LITERAL = "Error";

    @Inject
    private EdificioRepository edificioRepository;

    @Inject
    private EdificioConverter edificioConverter;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private UsuarioConverter converter;

    @Inject
    private PluginRepository pluginRepository;

    @Inject
    private PluginConverter pluginConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(EdificioDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {

        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JEdificio jEdificio = edificioConverter.createEntity(dto);
        edificioRepository.create(jEdificio);
        return jEdificio.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(EdificioDTO dto) throws RecursoNoEncontradoException {
        JEdificio jEdificio = edificioRepository.findById(dto.getCodigo());
        edificioConverter.mergeEntity(jEdificio, dto);
        edificioRepository.update(jEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JEdificio jEdificio = edificioRepository.getReference(id);
        edificioRepository.delete(jEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EdificioDTO findById(Long id) {
        JEdificio jEdificio = edificioRepository.getReference(id);
        return edificioConverter.createDTO(jEdificio);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
        usuarioRepository.create(jUsuario);
        return jUsuario.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(UsuarioDTO dto) throws RecursoNoEncontradoException {
        JUsuario jUsuario = usuarioRepository.getReference(dto.getCodigo());
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        jUsuario.setEntidad(jEntidad);
        converter.mergeEntity(jUsuario, dto);
        usuarioRepository.update(jUsuario);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteUsuario(Long id) throws RecursoNoEncontradoException {
        JUsuario jUsuario = usuarioRepository.getReference(id);
        usuarioRepository.delete(jUsuario);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public UsuarioDTO findUsuarioById(Long id) {
        JUsuario jUsuario = usuarioRepository.getReference(id);
        UsuarioDTO usuarioDTO = converter.createDTO(jUsuario);
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
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Boolean checkIdentificadorUsuario(String identificador) {
        return usuarioRepository.checkIdentificador(identificador);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createPlugin(PluginDto dto) throws RecursoNoEncontradoException {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JPlugin jPlugin = pluginConverter.createEntity(dto);
        pluginRepository.create(jPlugin);
        return jPlugin.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updatePlugin(PluginDto dto) throws RecursoNoEncontradoException {
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
    public PluginDto findPluginById(Long id) {
        JPlugin jPlugin = pluginRepository.getReference(id);
        PluginDto pluginDto = pluginConverter.createDTO(jPlugin);
        return pluginDto;
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
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }
}
