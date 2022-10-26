package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.*;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.persistence.repository.ProcedimientoRepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoTramiteRepository;
import es.caib.rolsac2.persistence.repository.ProcedimientoWorkflowRepository;
import es.caib.rolsac2.persistence.repository.UnidadAdministrativaRepository;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorfklow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
@Local(ProcedimientoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcedimientoServiceFacadeBean implements ProcedimientoServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoServiceFacade.class);

    @Resource
    private SessionContext context;
    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private UnidadAdministrativaRepository uaRepository;
    @Inject
    private ProcedimientoConverter converter;

    @Inject
    private UnidadAdministrativaConverter uaConverter;
    @Inject
    private ProcedimientoWorkflowConverter converterWorkflow;

    @Inject
    private TipoFormaInicioConverter tipoFormaInicioConverter;
    @Inject
    private TipoSilencioAdministrativoConverter tipoSilencioAdministrativoConverter;

    @Inject
    private TipoProcedimientoConverter tipoProcedimientoConverter;

    @Inject
    private TipoLegitimacionConverter tipoLegitimacionConverter;
    @Inject
    private ProcedimientoWorkflowRepository procedimientoWorkflowRepository;

    @Inject
    private ProcedimientoTramiteConverter procedimientoTramiteConverter;

    @Inject
    private ProcedimientoTramiteRepository procedimientoTramiteRepository;


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(ProcedimientoDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codigo no existeix ja
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JProcedimientoWorkflow jProcWF = new JProcedimientoWorkflow();
        mergear(jProcWF, dto);
        List<JProcedimientoWorkflowTraduccion> traducciones = crearTraducciones(dto, jProcWF);


        JProcedimiento jProcedimiento = converter.createEntity(dto);
        jProcedimiento.setTipo(Constantes.PROCEDIMIENTO);
        procedimientoRepository.create(jProcedimiento);

        jProcWF.setProcedimiento(jProcedimiento);
        jProcWF.setTraducciones(traducciones);
        procedimientoRepository.createWF(jProcWF);

        procedimientoRepository.mergeMateriaSIAProcWF(jProcWF.getCodigo(), dto.getMateriasGridSIA());
        procedimientoRepository.mergePublicoObjetivoProcWF(jProcWF.getCodigo(), dto.getTiposPubObjEntGrid());

        return jProcedimiento.getCodigo();
    }

    private List<JProcedimientoWorkflowTraduccion> crearTraducciones(ProcedimientoDTO dto, JProcedimientoWorkflow jProcWF) {
        List<JProcedimientoWorkflowTraduccion> traducciones = new ArrayList<>();
        for (final String idioma : dto.getNombreProcedimientoWorkFlow().getIdiomas()) {
            JProcedimientoWorkflowTraduccion trad = new JProcedimientoWorkflowTraduccion();
            trad.setProcedimientoWorkflow(jProcWF);
            trad.setIdioma(idioma);
            trad.setNombre(dto.getNombreProcedimientoWorkFlow().getTraduccion(idioma));
            trad.setDatosPersonalesDestinatario(dto.getDatosPersonalesDestinatario().getTraduccion(idioma));
            trad.setDatosPersonalesFinalidad(dto.getDatosPersonalesFinalidad().getTraduccion(idioma));
            trad.setRequisitos(dto.getRequisitos().getTraduccion(idioma));
            traducciones.add(trad);
        }
        return traducciones;
    }

    private void mergear(JProcedimientoWorkflow jProcWF, ProcedimientoDTO dto) {
        jProcWF.setWorkflow(TypeProcedimientoWorfklow.MODIFICACION.getValor());
        jProcWF.setFechaCaducidad(dto.getFechaCaducidad());
        jProcWF.setFechaPublicacion(dto.getFechaPublicacion());
        jProcWF.setEstado(TypeProcedimientoEstado.MODIFICACION.toString());
        jProcWF.setInterno(dto.isInterno());
        jProcWF.setDatosPersonalesActivo(dto.isDatosPersonalesActivo());
        jProcWF.setResponsableEmail(dto.getDirElectronica());
        jProcWF.setResponsableNombre(dto.getResponsable());
        jProcWF.setTieneTasa(dto.getTaxa());
        jProcWF.setUaResponsable(uaRepository.findJUAById(dto.getUaResponsable()));
        jProcWF.setUaInstructor(uaRepository.findJUAById(dto.getUaInstructor()));
        jProcWF.setFormaInicio(tipoFormaInicioConverter.createEntity(dto.getIniciacion()));
        jProcWF.setSilencioAdministrativo(tipoSilencioAdministrativoConverter.createEntity(dto.getSilencio()));
        jProcWF.setTipoProcedimiento(tipoProcedimientoConverter.createEntity(dto.getTipoProcedimiento()));
        jProcWF.setDatosPersonalesLegitimacion(tipoLegitimacionConverter.createEntity(dto.getDatosPersonalesLegitimacion()));
        jProcWF.setLopdResponsable(dto.getLopdResponsable());
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(ProcedimientoDTO dto) throws RecursoNoEncontradoException {
        JProcedimientoWorkflow jProcWF = procedimientoRepository.getWF(dto.getCodigo(), dto.getWorkflow().getValor());
        mergear(jProcWF, dto);
        mergearTraducciones(jProcWF, dto);
        procedimientoRepository.updateWF(jProcWF);
        procedimientoRepository.mergeMateriaSIAProcWF(jProcWF.getCodigo(), dto.getMateriasGridSIA());
        procedimientoRepository.mergePublicoObjetivoProcWF(jProcWF.getCodigo(), dto.getTiposPubObjEntGrid());

    }

    private void mergearTraducciones(JProcedimientoWorkflow jProcWF, ProcedimientoDTO dto) {
        if (jProcWF.getTraducciones() != null) {
            for (JProcedimientoWorkflowTraduccion traduccion : jProcWF.getTraducciones()) {
                traduccion.setNombre(dto.getNombreProcedimientoWorkFlow().getTraduccion(traduccion.getIdioma()));
                traduccion.setDatosPersonalesDestinatario(dto.getDatosPersonalesDestinatario().getTraduccion(traduccion.getIdioma()));
                traduccion.setDatosPersonalesFinalidad(dto.getDatosPersonalesFinalidad().getTraduccion(traduccion.getIdioma()));
                traduccion.setRequisitos(dto.getDatosPersonalesDestinatario().getTraduccion(traduccion.getIdioma()));
            }
        }
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JProcedimiento jproc = procedimientoRepository.getReference(id);
        JProcedimientoWorkflow jprocMod = procedimientoRepository.getWF(id, true);
        if (jprocMod != null) {
            procedimientoRepository.deleteWF(id, true);
        }
        JProcedimientoWorkflow jprocPub = procedimientoRepository.getWF(id, false);
        if (jprocMod != null) {
            procedimientoRepository.deleteWF(id, false);
        }
        procedimientoRepository.delete(jproc);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcedimientoDTO findById(Long id) {
        JProcedimiento jproc = procedimientoRepository.getReference(id);
        ProcedimientoDTO proc = converter.createDTO(jproc);

        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWF(id, Constantes.PROCEDIMIENTO_ENMODIFICACION);
        proc.setCodigoWF(jprocWF.getCodigo());
        proc.setFechaPublicacion(jprocWF.getFechaPublicacion());
        proc.setFechaCaducidad(jprocWF.getFechaCaducidad());
        proc.setDirElectronica(jprocWF.getResponsableEmail());
        proc.setWorkflow(TypeProcedimientoWorfklow.fromBoolean(jprocWF.getWorkflow()));
        proc.setEstado(TypeProcedimientoEstado.fromString(jprocWF.getEstado()));
        proc.setCodigoWF(jprocWF.getCodigo());
        proc.setTaxa(jprocWF.getTieneTasa());
        proc.setResponsable(jprocWF.getResponsableNombre());
        proc.setLopdResponsable(jprocWF.getLopdResponsable());
        if (jprocWF.getUaResponsable() != null) {
            proc.setUaResponsable(jprocWF.getUaResponsable().toDTO());
        }
        if (jprocWF.getUaResponsable() != null) {
            proc.setUaInstructor(jprocWF.getUaInstructor().toDTO());
        }
        if (jprocWF.getFormaInicio() != null) {
            proc.setIniciacion(tipoFormaInicioConverter.createDTO(jprocWF.getFormaInicio()));
        }
        if (jprocWF.getSilencioAdministrativo() != null) {
            proc.setSilencio(tipoSilencioAdministrativoConverter.createDTO(jprocWF.getSilencioAdministrativo()));
        }
        if (jprocWF.getTipoProcedimiento() != null) {
            proc.setTipoProcedimiento(tipoProcedimientoConverter.createDTO(jprocWF.getTipoProcedimiento()));
        }
        if (jprocWF.getDatosPersonalesLegitimacion() != null) {
            proc.setDatosPersonalesLegitimacion(tipoLegitimacionConverter.createDTO(jprocWF.getDatosPersonalesLegitimacion()));
        }

        //proc.setComun(jprocWF.isComun());
        Literal nombreProcedimientoWorkFlow = new Literal();
        Literal requisitos = new Literal();
        Literal datosPersonalesDestinatario = new Literal();
        Literal detDatosPersonalesFinalidad = new Literal();
        if (jprocWF.getTraducciones() != null) {
            for (JProcedimientoWorkflowTraduccion trad : jprocWF.getTraducciones()) {
                nombreProcedimientoWorkFlow.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
                requisitos.add(new Traduccion(trad.getIdioma(), trad.getRequisitos()));
                datosPersonalesDestinatario.add(new Traduccion(trad.getIdioma(), trad.getDatosPersonalesDestinatario()));
                detDatosPersonalesFinalidad.add(new Traduccion(trad.getIdioma(), trad.getDatosPersonalesFinalidad()));
            }
        }
        proc.setNombreProcedimientoWorkFlow(nombreProcedimientoWorkFlow);
        proc.setDatosPersonalesDestinatario(datosPersonalesDestinatario);
        proc.setDatosPersonalesFinalidad(detDatosPersonalesFinalidad);
        proc.setRequisitos(requisitos);
        proc.setMateriasGridSIA(procedimientoRepository.getMateriaGridSIAByWF(proc.getCodigoWF()));
        proc.setTiposPubObjEntGrid(procedimientoRepository.getTipoPubObjEntByWF(proc.getCodigoWF()));
        return proc;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<ProcedimientoGridDTO> findByFiltro(ProcedimientoFiltro filtro) {
        List<ProcedimientoGridDTO> items = procedimientoRepository.findPagedByFiltro(filtro);
        long total = procedimientoRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(ProcedimientoFiltro filtro) {
        return (int) procedimientoRepository.countByFiltro(filtro);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Optional<ProcedimientoTramiteDTO> findProcedimientoTramiteById(Long id) {
        Optional<JProcedimientoTramite> opt = procedimientoTramiteRepository.findById(id.toString());
        return opt.map(jProcedimientoTramite -> procedimientoTramiteConverter.createDTO(jProcedimientoTramite));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcedimientoTramiteDTO> findProcTramitesByProcedimientoId(Long id) {
        return procedimientoTramiteConverter.createDTOs(procedimientoTramiteRepository.findByProcedimientoId(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createProcedimientoTramite(ProcedimientoTramiteDTO dto)
            throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { // .isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JProcedimientoTramite jProcedimientoTramite = procedimientoTramiteConverter.createEntity(dto);
        procedimientoTramiteRepository.create(jProcedimientoTramite);
        return jProcedimientoTramite.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException {
        JProcedimientoTramite entidad = procedimientoTramiteRepository.getReference(dto.getCodigo());
        procedimientoTramiteConverter.mergeEntity(entidad, dto);
        procedimientoTramiteRepository.update(entidad);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteProcedimientoTramite(Long id) throws RecursoNoEncontradoException {
        JProcedimientoTramite entidad = procedimientoTramiteRepository.getReference(id);
        procedimientoTramiteRepository.delete(entidad);
    }


}
