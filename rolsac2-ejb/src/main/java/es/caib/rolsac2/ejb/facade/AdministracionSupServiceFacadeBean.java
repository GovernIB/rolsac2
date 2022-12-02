package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.ConfiguracionGlobalConverter;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.model.JConfiguracionGlobal;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.repository.ConfiguracionGlobalRepository;
import es.caib.rolsac2.persistence.repository.EntidadRepository;
import es.caib.rolsac2.persistence.repository.FicheroExternoRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
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
    private EntidadRepository entidadRepository;

    @Inject
    private ConfiguracionGlobalRepository configuracionGlobalRepository;

    @Inject
    private EntidadConverter entidadConverter;

    @Inject
    private ConfiguracionGlobalConverter configuracionGlobalConverter;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    private UnidadAdministrativaConverter unidadAdministrativaConverter;

    @Inject
    private SystemServiceFacade systemServiceBean;

    @Inject
    private FicheroExternoRepository ficheroExternoRepository;

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createEntidad(EntidadDTO dto, Long idUnitat)
            throws RecursoNoEncontradoException, DatoDuplicadoException {
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


        UnidadAdministrativaDTO unidadAdministrativaDTO = new UnidadAdministrativaDTO();
        unidadAdministrativaDTO.setEntidad(dto);
        unidadAdministrativaDTO.setIdentificador(dto.getIdentificador());
        unidadAdministrativaDTO.setOrden(0);
        JUnidadAdministrativa jUnidadAdministrativa = unidadAdministrativaConverter.createEntity(unidadAdministrativaDTO);
        jUnidadAdministrativa.setEntidad(jEntidad);
        unidadAdministrativaRepository.create(jUnidadAdministrativa);

        return jEntidad.getCodigo();
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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

        entidadRepository.update(jEntidad);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteEntidad(Long id) throws RecursoNoEncontradoException {
        JEntidad jEntidad = entidadRepository.getReference(id);
        entidadRepository.delete(jEntidad);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public EntidadDTO findEntidadById(Long id) {
        JEntidad jEntidad = entidadRepository.getReference(id);
        EntidadDTO entidadDTO = entidadConverter.createDTO(jEntidad);


        return entidadDTO;
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateConfGlobal(ConfiguracionGlobalDTO dto) throws RecursoNoEncontradoException {
        JConfiguracionGlobal jConfiguracionGlobal = configuracionGlobalRepository.getReference(dto.getCodigo());
        configuracionGlobalConverter.mergeEntity(jConfiguracionGlobal, dto);
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ConfiguracionGlobalDTO findConfGlobalById(Long id) {
        JConfiguracionGlobal jConfiguracionGlobal = configuracionGlobalRepository.getReference(id);
        ConfiguracionGlobalDTO configuracionGlobalDTO = configuracionGlobalConverter.createDTO(jConfiguracionGlobal);
        return configuracionGlobalDTO;
    }

    @Override
    // @RolesAllowed({Constants.RSC_USER, Constants.RSC_ADMIN})
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<ConfiguracionGlobalGridDTO> findConfGlobalByFiltro(ConfiguracionGlobalFiltro filtro) {
        try {
            List<ConfiguracionGlobalGridDTO> items = configuracionGlobalRepository.findPagedByFiltro(filtro);
            long total = configuracionGlobalRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error(ERROR_LITERAL, e);
            return new Pagina<>(new ArrayList<>(), 0);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeIdentificadorEntidad(String identificador) {
        return entidadRepository.existeIdentificadorEntidad(identificador);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public FicheroDTO getLogoEntidad(Long codigo) {
        if (codigo == null) {
            return null;
        }

        return ficheroExternoRepository.getContentById(codigo,
                systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
    }

}
