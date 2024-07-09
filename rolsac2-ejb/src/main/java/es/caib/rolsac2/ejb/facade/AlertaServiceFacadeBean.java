package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.AlertaConverter;
import es.caib.rolsac2.persistence.model.JAlerta;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.repository.AlertaRepository;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.persistence.repository.UsuarioRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.AlertaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.AlertaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Logged
@ExceptionTranslate
@Stateless
@Local(AlertaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AlertaServiceFacadeBean implements AlertaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(AlertaServiceFacadeBean.class);
    private static final String ERROR_LITERAL = "Error";

    @Resource
    private SessionContext context;

    @Inject
    private AlertaRepository alertaRepository;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;
    @Inject
    private AlertaConverter converter;

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(AlertaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JAlerta jAlerta = converter.createEntity(dto);
        if (dto.getUnidadAdministrativa() != null && dto.getUnidadAdministrativa().getCodigo() != null) {
            jAlerta.setUnidadAdministrativa(unidadAdministrativaRepository.getReference(dto.getUnidadAdministrativa().getCodigo()));
        }

        alertaRepository.create(jAlerta);
        return jAlerta.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(AlertaDTO dto, String idioma) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JAlerta jAlerta = alertaRepository.getReference(dto.getCodigo());
        jAlerta.setEntidad(jEntidad);
        converter.mergeEntity(jAlerta, dto);
        if (dto.getUnidadAdministrativa() != null && dto.getUnidadAdministrativa().getCodigo() != null) {
            jAlerta.setUnidadAdministrativa(unidadAdministrativaRepository.getReference(dto.getUnidadAdministrativa().getCodigo()));
        }
        alertaRepository.update(jAlerta);
        alertaRepository.borrarAlertasUsuariosByIdAlerta(dto.getCodigo());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JAlerta jAlerta = alertaRepository.getReference(id);
        alertaRepository.borrarAlertasUsuariosByIdAlerta(id);
        alertaRepository.delete(jAlerta);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AlertaDTO findById(Long id, String idioma) {
        JAlerta jalerta = alertaRepository.findById(id);
        AlertaDTO alertaDTO = converter.createDTO(jalerta);
        if (jalerta.getUnidadAdministrativa() != null) {
            UnidadAdministrativaDTO ua = unidadAdministrativaRepository.findUASimpleByID(jalerta.getUnidadAdministrativa().getCodigo(), idioma, null);
            alertaDTO.setUnidadAdministrativa(ua);
        }
        return alertaDTO;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public AlertaGridDTO findGridById(Long id) {
        return converter.createGridDTO(alertaRepository.findById(id));
    }

    @Override
    @PermitAll
    //@RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AlertaDTO> getAlertas(String usuario, List<TypePerfiles> iperfiles, String lang) {
        List<String> perfiles = new ArrayList<>();
        for (TypePerfiles perfil : iperfiles) {
            perfiles.add(perfil.toString());
        }

        List<Long> uas = new ArrayList<>();
        UsuarioDTO usuarioDTO = usuarioRepository.findSimpleByIdentificador(usuario, lang);
        if (usuarioDTO != null) {
            List<UnidadAdministrativaGridDTO> unidadesAdministrativas = unidadAdministrativaRepository.getUnidadesAdministrativaGridDTOByUsuario(usuarioDTO.getCodigo(), lang);
            for (UnidadAdministrativaGridDTO ua : unidadesAdministrativas) {
                List<Long> uasPadres = unidadAdministrativaRepository.listarPadres(ua.getCodigo());
                uas.addAll(uasPadres);
                uas.add(ua.getCodigo());
            }
        }

        return alertaRepository.getAlertas(usuario, perfiles, uas);
    }

    @Override
    @RolesAllowed({TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<AlertaGridDTO> findAlertaUsuarioDTOByFiltro(AlertaFiltro filtro) {
        try {
            List<AlertaGridDTO> items = alertaRepository.findAlertaUsuarioPageByFiltro(filtro);
            long total = alertaRepository.countAlertaUsuarioByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<AlertaGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void marcarAlertaLeida(Long codigo, String identificadorUsuario) {
        alertaRepository.marcarAlertaLeida(codigo, identificadorUsuario);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<AlertaGridDTO> findByFiltro(AlertaFiltro filtro) {
        try {
            List<AlertaGridDTO> items = alertaRepository.findPageByFiltro(filtro);
            long total = alertaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<AlertaGridDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(AlertaFiltro filtro) {
        return (int) alertaRepository.countByFiltro(filtro);
    }


    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<AlertaDTO> findByFiltroRest(AlertaFiltro filtro) {
        try {
            List<AlertaDTO> items = alertaRepository.findPagedByFiltroRest(filtro);
            long total = alertaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            List<AlertaDTO> items = new ArrayList<>();
            return new Pagina<>(items, 0L);
        }
    }

}
