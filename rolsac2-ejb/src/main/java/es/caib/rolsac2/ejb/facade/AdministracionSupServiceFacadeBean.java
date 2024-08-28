package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.ConfiguracionGlobalConverter;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
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
 * Implementación de los casos de uso de mantenimiento de la entidad y la configuración global. Es responsabilidad de
 * esta caap definir el limite de las transacciones y la seguridad.
 * <p>
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma els errors JPA amb les
 * excepcions de servei com la {@link RecursoNoEncontradoException}
 *
 * @author Indra
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(AdministracionSupServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AdministracionSupServiceFacadeBean implements AdministracionSupServiceFacade {

    private static final String ERROR_LITERAL = "Error";

    private static final Logger LOG = LoggerFactory.getLogger(AdministracionSupServiceFacadeBean.class);

    @Inject
    EntidadRepository entidadRepository;

    @Inject
    ConfiguracionGlobalRepository configuracionGlobalRepository;

    @Inject
    EntidadConverter entidadConverter;

    @Inject
    ConfiguracionGlobalConverter configuracionGlobalConverter;

    @Inject
    UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UnidadAdministrativaConverter unidadAdministrativaConverter;

    @Inject
    SystemServiceFacade systemServiceBean;

    @Inject
    FicheroExternoRepository ficheroExternoRepository;

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createEntidad(EntidadDTO dto, UsuarioDTO usuarioDTO) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { // .isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JEntidad jEntidad = entidadConverter.createEntity(dto);


        entidadRepository.create(jEntidad);

        /*En caso de que la entidad tenga un logo asociado, se persiste el logo*/
        if (dto.getLogo() != null) {
            //Marcamos el icono como correcto (persistir a true)
            // Además, pasamos la ID del jEntidad para que haya referencia
            ficheroExternoRepository.persistFicheroExterno(dto.getLogo().getCodigo(), jEntidad.getCodigo(), systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
        }

        //Creamos la UA raiz
        UnidadAdministrativaDTO unidadAdministrativaDTO = new UnidadAdministrativaDTO();
        unidadAdministrativaDTO.setEntidad(dto);
        unidadAdministrativaDTO.setIdentificador(dto.getIdentificador());
        unidadAdministrativaDTO.setOrden(0);
        unidadAdministrativaDTO.setVersion(1);
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaConverter.createEntity(unidadAdministrativaDTO);
        jUnidadAdministrativa.setEntidad(jEntidad);
        if (jUnidadAdministrativa.getTraducciones() != null) {
            for (JUnidadAdministrativaTraduccion trad : jUnidadAdministrativa.getTraducciones()) {
                trad.setNombre(dto.getIdentificador());
            }
        }
        unidadAdministrativaRepository.create(jUnidadAdministrativa);

        //Anyadir la relacion de usuario con Entidad
        JUsuario jUsuario = usuarioRepository.findById(usuarioDTO.getCodigo());
        usuarioRepository.anyadirNuevoUsuarioEntidad(jUsuario, jEntidad.getCodigo());

        //Anyadir la relacion de usuario con UA
        usuarioRepository.anyadirNuevoUsuarioUA(jUsuario, jUnidadAdministrativa);

        return jEntidad.getCodigo();
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateEntidad(EntidadDTO dto) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(dto.getCodigo());
        if (dto.getLogo() != null) {
            ficheroExternoRepository.persistFicheroExterno(dto.getLogo().getCodigo(), dto.getCodigo(), systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
            entidadConverter.mergeEntity(jEntidad, dto);
            JFicheroExterno jlogo = ficheroExternoRepository.getReference(dto.getLogo().getCodigo());
            jEntidad.setLogo(jlogo);
            /*
            if (jEntidad.getLogo() != null && dto.getLogo().getCodigo() == jEntidad.getLogo().getCodigo()) {
                entidadConverter.mergeEntity(jEntidad, dto);
            } else {
                if (jEntidad.getLogo() != null) {
                    ficheroExternoRepository.deleteFicheroExterno(jEntidad.getLogo().getCodigo());
                }
                entidadConverter.mergeEntity(jEntidad, dto);
                Long codigo = ficheroExternoRepository.createFicheroExterno(dto.getLogo().getContenido(), dto.getLogo().getFilename(),
                        dto.getLogo().getTipo(), dto.getLogo().getCodigo(), systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));

                JFicheroExterno jFicheroExterno = ficheroExternoRepository.findById(codigo);
                jEntidad.setLogo(jFicheroExterno);
            }*/
        } else {
            entidadConverter.mergeEntity(jEntidad, dto);
            jEntidad.setLogo(null);
        }
        if (dto.getCssPersonalizado() != null) {
            ficheroExternoRepository.persistFicheroExterno(dto.getCssPersonalizado().getCodigo(), dto.getCodigo(), systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
            entidadConverter.mergeEntity(jEntidad, dto);
            JFicheroExterno jCss = ficheroExternoRepository.getReference(dto.getCssPersonalizado().getCodigo());
            jEntidad.setCssPersonalizado(jCss);
        } else {
            jEntidad.setCssPersonalizado(null);
        }
        jEntidad.setAdmContenidoIdiomaPrioritario(dto.getAdmContenidoIdiomaPrioritario());
        jEntidad.setAdmContenidoSeleccionIdioma(dto.getAdmContenidoSeleccionIdioma());
        entidadRepository.update(jEntidad);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteEntidad(Long id) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(id);
        entidadRepository.delete(jEntidad);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EntidadDTO findEntidadById(Long id) {
        JEntidad jEntidad = entidadRepository.getReference(id);
        return entidadConverter.createDTO(jEntidad);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<EntidadGridDTO> findEntidadByFiltro(EntidadFiltro filtro) {
        try {
            List<EntidadGridDTO> items = entidadRepository.findPagedByFiltro(filtro);
            long total = entidadRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<EntidadGridDTO> listEntidadByFiltro(EntidadFiltro filtro) {
        try {
            return entidadRepository.findPagedByFiltro(filtro);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public long countEntidadByFiltro(EntidadFiltro filtro) {
        try {
            return entidadRepository.countByFiltro(filtro);

        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return 0;
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<EntidadDTO> findEntidadActivas() {
        try {
            List<EntidadDTO> entidadesActivas = new ArrayList<>();
            List<JEntidad> items = entidadRepository.findActivas();
            for (JEntidad jEntidad : items) {
                entidadesActivas.add(entidadConverter.createDTO(jEntidad));
            }
            return entidadesActivas;
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<String> findRolesDefinidos(List<Long> idEntidades) {
        return entidadRepository.findRolesDefinidos(idEntidades);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateConfGlobal(ConfiguracionGlobalDTO dto) throws RecursoNoEncontradoException {
        JConfiguracionGlobal jConfiguracionGlobal = configuracionGlobalRepository.getReference(dto.getCodigo());
        configuracionGlobalConverter.mergeEntity(jConfiguracionGlobal, dto);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ConfiguracionGlobalDTO findConfGlobalById(Long id) {
        JConfiguracionGlobal jConfiguracionGlobal = configuracionGlobalRepository.getReference(id);
        return configuracionGlobalConverter.createDTO(jConfiguracionGlobal);
    }

    @Override
    @RolesAllowed({TypePerfiles.SUPER_ADMINISTRADOR_VALOR})
    public List<ConfiguracionGlobalGridDTO> listConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
        try {
            return configuracionGlobalRepository.findPagedByFiltro(filtro);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.SUPER_ADMINISTRADOR_VALOR})
    public int countConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
        try {
            return (int) configuracionGlobalRepository.countByFiltro(filtro);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return 0;
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorEntidad(String identificador) {
        return entidadRepository.existeIdentificadorEntidad(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public FicheroDTO getLogoEntidad(Long codigo) {
        if (codigo == null) {
            return null;
        }

        return ficheroExternoRepository.getContentById(codigo, systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
    }

}
