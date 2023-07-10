package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.ejb.facade.procesos.solr.CastUtil;
import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.ejb.util.JSONUtil;
import es.caib.rolsac2.ejb.util.JSONUtilException;
import es.caib.rolsac2.persistence.converter.*;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.AuditoriaException;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.auditoria.ProcedimientoAuditoria;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;
import es.caib.rolsac2.service.model.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;

/**
 * Implementación de los casos de uso de mantenimiento de personal. Es
 * responsabilidad de esta caap definir el limite de las transacciones y la
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
@Local(ProcedimientoServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcedimientoServiceFacadeBean implements ProcedimientoServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoServiceFacade.class);

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private UnidadAdministrativaRepository uaRepository;

    @Inject
    private TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    private IndexacionRepository indexacionRepository;

    @Inject
    private IndexacionSIARepository indexacionSIARepository;

    @Inject
    private IndexacionConverter indexacionConverter;

    @Inject
    private PlatTramitElectronicaConverter platTramitElectronicaConverter;

    @Inject
    private TipoFormaInicioConverter tipoFormaInicioConverter;

    @Inject
    private ProcedimientoAuditoriaConverter procedimientoAuditoriaConverter;

    @Inject
    private TipoSilencioAdministrativoConverter tipoSilencioAdministrativoConverter;

    @Inject
    private TipoProcedimientoConverter tipoProcedimientoConverter;

    @Inject
    private TipoViaConverter tipoViaConverter;

    @Inject
    private TemaConverter temaConverter;

    @Inject
    private TipoTramitacionConverter tipoTramitacionConverter;

    @Inject
    private TipoLegitimacionConverter tipoLegitimacionConverter;

    @Inject
    private ProcedimientoTramiteConverter procedimientoTramiteConverter;

    @Inject
    private ProcedimientoTramiteRepository procedimientoTramiteRepository;

    @Inject
    private ProcedimientoDocumentoRepository procedimientoDocumentoRepository;

    @Inject
    private SystemServiceFacade systemService;

    @Inject
    private FicheroExternoRepository ficheroExternoRepository;

    @Inject
    private TemaRepository temaRepository;

    @Inject
    private ProcedimientoAuditoriaRepository auditoriaRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(ProcedimientoBaseDTO dto, TypePerfiles perfil) throws RecursoNoEncontradoException, DatoDuplicadoException {
        return create(dto, perfil, true);
    }

    private Long create(ProcedimientoBaseDTO dto, TypePerfiles perfil, boolean conAuditoria) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codigo no existeix ja
        if (dto.getCodigoWF() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JProcedimientoWorkflow jProcWF = new JProcedimientoWorkflow();
        mergear(jProcWF, dto);
        List<JProcedimientoWorkflowTraduccion> traducciones = crearTraducciones(dto, jProcWF);

        JProcedimiento jProcedimiento;
        if (dto.getCodigo() == null) {
            jProcedimiento = new JProcedimiento();
            if (dto instanceof ProcedimientoDTO) {
                jProcedimiento.setTipo(Constantes.PROCEDIMIENTO);
            }
            if (dto instanceof ServicioDTO) {
                jProcedimiento.setTipo(Constantes.SERVICIO);
            }
        } else {
            jProcedimiento = procedimientoRepository.findById(dto.getCodigo());
        }
        jProcedimiento.setFechaActualizacion(new Date());

        procedimientoRepository.create(jProcedimiento);
        dto.setCodigo(jProcedimiento.getCodigo());
        jProcWF.setProcedimiento(jProcedimiento);
        jProcWF.setTraducciones(traducciones);

        // Añadimos los temas asociados al DTO
        Set<JTema> temas = new HashSet<>();
        if (dto.getTemas() != null) {
            for (TemaGridDTO tema : dto.getTemas()) {
                JTema jTema = temaRepository.getReference(tema.getCodigo());
                temas.add(jTema);
            }
        }
        jProcWF.setTemas(temas);

        procedimientoRepository.createWF(jProcWF);

        procedimientoRepository.mergeMateriaSIAProcWF(jProcWF.getCodigo(), dto.getMateriasSIA());
        procedimientoRepository.mergePublicoObjetivoProcWF(jProcWF.getCodigo(), dto.getPublicosObjetivo());
        procedimientoRepository.mergeNormativaProcWF(jProcWF.getCodigo(), dto.getNormativas());
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentos() == null ? null : jProcWF.getListaDocumentos().getCodigo(), false, dto.getDocumentos(), ruta);
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentosLOPD() == null ? null : jProcWF.getListaDocumentosLOPD().getCodigo(), true, dto.getDocumentosLOPD(), ruta);
        if (dto instanceof ProcedimientoDTO) {
            List<ProcedimientoTramiteDTO> tramites = ((ProcedimientoDTO) dto).getTramites();
            procedimientoRepository.mergeTramitesProcWF(jProcWF.getCodigo(), tramites, ruta);
        }
        dto.setCodigoWF(jProcWF.getCodigo());
        ProcedimientoBaseDTO dtoAntiguo = ProcedimientoBaseDTO.createInstance(dto.getNombreProcedimientoWorkFlow().getIdiomas());
        if (conAuditoria) {
            crearAuditoria(dtoAntiguo, dto, perfil, "auditoria.flujo.CREAR", TypeAccionAuditoria.ALTA.toString());
        }
        // return jProcedimiento.getCodigo();
        return jProcWF.getCodigo();
    }

    private List<JProcedimientoWorkflowTraduccion> crearTraducciones(ProcedimientoBaseDTO dto, JProcedimientoWorkflow jProcWF) {
        List<JProcedimientoWorkflowTraduccion> traducciones = new ArrayList<>();
        for (final String idioma : dto.getNombreProcedimientoWorkFlow().getIdiomas()) {
            JProcedimientoWorkflowTraduccion trad = new JProcedimientoWorkflowTraduccion();
            trad.setProcedimientoWorkflow(jProcWF);
            trad.setIdioma(idioma);
            mergeTraduccion(trad, dto);
            traducciones.add(trad);
        }
        return traducciones;
    }

    private void mergear(JProcedimientoWorkflow jProcWF, ProcedimientoBaseDTO dto) {
        jProcWF.setWorkflow(dto.getWorkflow().getValor());
        jProcWF.setFechaCaducidad(dto.getFechaCaducidad());
        jProcWF.setFechaPublicacion(dto.getFechaPublicacion());
        jProcWF.setEstado(dto.getEstado().toString());
        jProcWF.setInterno(dto.isInterno());
        jProcWF.setResponsableEmail(dto.getResponsableEmail());
        jProcWF.setResponsableNombre(dto.getResponsable());
        jProcWF.setResponsableTelefono(dto.getResponsableTelefono());
        jProcWF.setTieneTasa(dto.isTieneTasa());
        jProcWF.setUaResponsable(uaRepository.findJUAById(dto.getUaResponsable()));
        jProcWF.setUaInstructor(uaRepository.findJUAById(dto.getUaInstructor()));
        jProcWF.setFormaInicio(tipoFormaInicioConverter.createEntity(dto.getIniciacion()));
        jProcWF.setSilencioAdministrativo(tipoSilencioAdministrativoConverter.createEntity(dto.getSilencio()));
        jProcWF.setTipoProcedimiento(tipoProcedimientoConverter.createEntity(dto.getTipoProcedimiento()));
        jProcWF.setTipoVia(tipoViaConverter.createEntity(dto.getTipoVia()));
        jProcWF.setDatosPersonalesLegitimacion(tipoLegitimacionConverter.createEntity(dto.getDatosPersonalesLegitimacion()));
        jProcWF.setLopdResponsable(dto.getLopdResponsable());

        jProcWF.setComun(dto.getComun());
        // Actualizamos temas
        Set<JTema> temas = new HashSet<>();
        if (dto.getTemas() != null) {
            for (TemaGridDTO tema : dto.getTemas()) {
                JTema jTema = temaRepository.getReference(tema.getCodigo());
                temas.add(jTema);
            }
        }

        if (jProcWF.getTemas() == null) {
            jProcWF.setTemas(new HashSet<>());
        }
        jProcWF.getTemas().clear();
        jProcWF.getTemas().addAll(temas);

        if (dto instanceof ProcedimientoDTO) {
            jProcWF.setHabilitadoApoderado(((ProcedimientoDTO) dto).isHabilitadoApoderado());
            jProcWF.setHabilitadoFuncionario(((ProcedimientoDTO) dto).getHabilitadoFuncionario());
        }
        if (dto instanceof ServicioDTO) {
            jProcWF.setActivoLOPD(((ServicioDTO) dto).isActivoLOPD());
            jProcWF.setTramitElectronica(((ServicioDTO) dto).isTramitElectronica());
            jProcWF.setTramitPresencial(((ServicioDTO) dto).isTramitPresencial());
            jProcWF.setTramitTelefonica(((ServicioDTO) dto).isTramitTelefonica());
            if (((ServicioDTO) dto).getPlantillaSel() != null || ((ServicioDTO) dto).getTipoTramitacion() != null) {
                JTipoTramitacion jTipoTramitacion = null;
                if (((ServicioDTO) dto).getPlantillaSel() != null && ((ServicioDTO) dto).getPlantillaSel().getCodigo() != null) {
                    jTipoTramitacion = tipoTramitacionRepository.findById(((ServicioDTO) dto).getPlantillaSel().getCodigo());
                    jProcWF.setTramiteElectronicoPlantilla(jTipoTramitacion);
                    if (jProcWF.getTramiteElectronico() != null && jProcWF.getTramiteElectronico().getCodigo() != null) {
                        tipoTramitacionRepository.borrarByServicio(jProcWF.getCodigo(), jProcWF.getTramiteElectronico().getCodigo());
                    }
                    jProcWF.setTramiteElectronico(null);
                } else if (((ServicioDTO) dto).getTipoTramitacion() != null) { // && ((ServicioDTO)
                    // dto).getTipoTramitacion().getTramiteId()
                    // != null) {
                    if (((ServicioDTO) dto).getTipoTramitacion().getCodigo() == null) {
                        jTipoTramitacion = tipoTramitacionConverter.createEntity(((ServicioDTO) dto).getTipoTramitacion());
                        tipoTramitacionRepository.create(jTipoTramitacion);
                    } else {
                        jTipoTramitacion = tipoTramitacionRepository.findById(((ServicioDTO) dto).getTipoTramitacion().getCodigo());
                        jTipoTramitacion.merge(((ServicioDTO) dto).getTipoTramitacion());
                        if (((ServicioDTO) dto).getTipoTramitacion().getCodPlatTramitacion() == null) {
                            jTipoTramitacion.setCodPlatTramitacion(null);
                        } else {
                            JPlatTramitElectronica jplataforma = platTramitElectronicaConverter.createEntity(((ServicioDTO) dto).getTipoTramitacion().getCodPlatTramitacion());
                            // JPlatTramitElectronica jplataforma =
                            // entityManager.find(JPlatTramitElectronica.class,
                            // elemento.getTipoTramitacion().getCodPlatTramitacion().getCodigo());
                            jTipoTramitacion.setCodPlatTramitacion(jplataforma);
                        }
                        tipoTramitacionRepository.update(jTipoTramitacion);
                    }
                    jProcWF.setTramiteElectronicoPlantilla(null);
                    jProcWF.setTramiteElectronico(jTipoTramitacion);

                    if (((ServicioDTO) dto).getTipoTramitacion().getCodigo() == null) {
                        jTipoTramitacion = tipoTramitacionConverter.createEntity(((ServicioDTO) dto).getTipoTramitacion());
                        jTipoTramitacion = tipoTramitacionRepository.crearActualizar(jTipoTramitacion);
                        jProcWF.setTramiteElectronicoPlantilla(null);
                        jProcWF.setTramiteElectronico(jTipoTramitacion);
                    } else {
                        jTipoTramitacion = tipoTramitacionRepository.findById(((ServicioDTO) dto).getTipoTramitacion().getCodigo());
                        jTipoTramitacion.merge(((ServicioDTO) dto).getTipoTramitacion());
                        jTipoTramitacion.setPlantilla(false);
                        jTipoTramitacion = tipoTramitacionRepository.crearActualizar(jTipoTramitacion);
                    }

                }
            } else {
                jProcWF.setTramiteElectronicoPlantilla(null);
                if (jProcWF.getTramiteElectronico() != null) {
                    tipoTramitacionRepository.delete(jProcWF.getTramiteElectronico());
                }
                jProcWF.setTramiteElectronico(null);
            }
        }

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(ProcedimientoBaseDTO dto, ProcedimientoBaseDTO dtoAntiguo, TypePerfiles perfil, Long idEntidad) throws RecursoNoEncontradoException {
        JProcedimientoWorkflow jProcWF = procedimientoRepository.getWF(dto.getCodigo(), dto.getWorkflow().getValor());
        this.updateWF(dto, jProcWF);
        TypeIndexacion tipo = (dto instanceof ProcedimientoDTO) ? TypeIndexacion.PROCEDIMIENTO : TypeIndexacion.SERVICIO;
        indexacionRepository.guardarIndexar(dto.getCodigo(), tipo, idEntidad, 1);
        indexacionSIARepository.guardarIndexar(dto.getCodigo(), tipo.toString(), idEntidad, 1, 1);
        crearAuditoria(dtoAntiguo, dto, perfil, null, TypeAccionAuditoria.MODIFICACION.toString());
    }

    private void updateWF(ProcedimientoBaseDTO dto, JProcedimientoWorkflow jProcWF) throws RecursoNoEncontradoException {
        mergear(jProcWF, dto);
        mergearTraducciones(jProcWF, dto);
        jProcWF.getProcedimiento().setFechaActualizacion(new Date());
        procedimientoRepository.updateWF(jProcWF);
        procedimientoRepository.mergeMateriaSIAProcWF(jProcWF.getCodigo(), dto.getMateriasSIA());
        procedimientoRepository.mergePublicoObjetivoProcWF(jProcWF.getCodigo(), dto.getPublicosObjetivo());
        procedimientoRepository.mergeNormativaProcWF(jProcWF.getCodigo(), dto.getNormativas());
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        if (dto instanceof ProcedimientoDTO) {
            List<ProcedimientoTramiteDTO> tramites = ((ProcedimientoDTO) dto).getTramites();
            procedimientoRepository.mergeTramitesProcWF(jProcWF.getCodigo(), tramites, ruta);
        }
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentos() == null ? null : jProcWF.getListaDocumentos().getCodigo(), false, dto.getDocumentos(), ruta);
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentosLOPD() == null ? null : jProcWF.getListaDocumentosLOPD().getCodigo(), true, dto.getDocumentosLOPD(), ruta);
        procedimientoRepository.actualizarFechaActualizacion(dto.getCodigo());

    }

    private void mergearTraducciones(JProcedimientoWorkflow jProcWF, ProcedimientoBaseDTO dto) {
        if (jProcWF.getTraducciones() != null) {
            for (JProcedimientoWorkflowTraduccion traduccion : jProcWF.getTraducciones()) {
                mergeTraduccion(traduccion, dto);
            }
        }
    }

    private void mergeTraduccion(JProcedimientoWorkflowTraduccion traduccion, ProcedimientoBaseDTO dto) {
        if (dto.getNombreProcedimientoWorkFlow() != null) {
            traduccion.setNombre(dto.getNombreProcedimientoWorkFlow().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getDatosPersonalesDestinatario() != null) {
            traduccion.setDatosPersonalesDestinatario(dto.getDatosPersonalesDestinatario().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getDatosPersonalesFinalidad() != null) {
            traduccion.setDatosPersonalesFinalidad(dto.getDatosPersonalesFinalidad().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getRequisitos() != null) {
            traduccion.setRequisitos(dto.getRequisitos().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getObjeto() != null) {
            traduccion.setObjeto(dto.getObjeto().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getDestinatarios() != null) {
            traduccion.setDestinatarios(dto.getDestinatarios().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getTerminoResolucion() != null) {
            traduccion.setTerminoResolucion(dto.getTerminoResolucion().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getObservaciones() != null) {
            traduccion.setObservaciones(dto.getObservaciones().getTraduccion(traduccion.getIdioma()));
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JProcedimiento jproc = procedimientoRepository.getReference(id);
        JProcedimientoWorkflow jprocMod = procedimientoRepository.getWF(id, true);
        if (jprocMod != null) {
            procedimientoRepository.deleteWF(jprocMod.getCodigo());
        }
        JProcedimientoWorkflow jprocPub = procedimientoRepository.getWF(id, false);
        if (jprocPub != null) {
            procedimientoRepository.deleteWF(jprocPub.getCodigo());
        }
        procedimientoRepository.delete(jproc);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteWF(Long idWF) throws RecursoNoEncontradoException {
        procedimientoRepository.deleteWF(idWF);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteProcedimientoCompleto(Long id) {

        // Primero borramos las auditorias asociadas al procedimiento
        auditoriaRepository.borrarAuditoriasByIdProcedimiento(id);
        // Borramos los workflows asociados
        this.delete(id);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long generarModificacion(Long codigoWFPub, String usuario, TypePerfiles perfil) {
        ProcedimientoBaseDTO procPublicado = getProcedimientoDTOByCodigoWF(codigoWFPub);
        ProcedimientoBaseDTO procModificar = limpiar(procPublicado);
        procModificar.setEstado(TypeProcedimientoEstado.MODIFICACION);
        procModificar.setWorkflow(TypeProcedimientoWorkflow.MODIFICACION);
        procModificar.setUsuarioAuditoria(usuario);
        Long codigoNuevo = this.create(procModificar, perfil, false);
        procModificar.setCodigoWF(codigoNuevo);
        // Se crea la auditoria manualmente, sin los cambios comparados porque no hace
        // falta.
        generarAuditoria("auditoria.flujo.crearDesdePublicado", procModificar.getCodigo(), usuario, perfil, TypeAccionAuditoria.MODIFICACION.toString());
        return procModificar.getCodigoWF();
    }

    private void generarAuditoria(String literal, Long codigo, String usuario, TypePerfiles perfil, String accion) {
        final ProcedimientoAuditoria procedimientoAuditoria = new ProcedimientoAuditoria();
        List<AuditoriaCambio> cambios = new ArrayList<>();
        try {
            String auditoriaJson = JSONUtil.toJSON(cambios);
            JProcedimientoAuditoria jprocAudit = new JProcedimientoAuditoria();
            JProcedimiento jproc = this.procedimientoRepository.findById(codigo);
            jprocAudit.setProcedimiento(jproc);
            Calendar calendar = Calendar.getInstance();
            jprocAudit.setFechaModificacion(calendar.getTime());
            jprocAudit.setListaModificaciones(auditoriaJson);
            jprocAudit.setUsuarioModificacion(usuario);
            jprocAudit.setUsuarioPerfil(perfil.toString());
            jprocAudit.setLiteralFlujo(literal);
            jprocAudit.setAccion(accion);
            this.auditoriaRepository.guardar(jprocAudit);
        } catch (Exception e) {
            LOG.error("Error creando auditoria", e);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AuditoriaGridDTO> findProcedimientoAuditoriasById(Long id) {
        return auditoriaRepository.findProcedimientoAuditoriasById(id);

    }

    /**
     * Método para quitar los códigos de cualquier dato que tenga.
     */
    public ProcedimientoBaseDTO limpiar(ProcedimientoBaseDTO proc) {
        proc.setCodigoWF(null);
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        // this.setCodigo(); El código del procedimiento se queda, no se altera
        if (proc.getDocumentosLOPD() != null) {
            for (ProcedimientoDocumentoDTO documento : proc.getDocumentosLOPD()) {
                limpiarDocumento(documento, ruta);
            }
        }

        if (proc.getDocumentos() != null) {
            for (ProcedimientoDocumentoDTO documento : proc.getDocumentos()) {
                limpiarDocumento(documento, ruta);
            }
        }

        // Normativas y publico objetivo, no hace falta tocar nada
        if (proc instanceof ProcedimientoDTO) {
            if (((ProcedimientoDTO) proc).getTramites() != null) {
                for (ProcedimientoTramiteDTO tramite : ((ProcedimientoDTO) proc).getTramites()) {
                    tramite.setCodigo(null);
                    if (tramite.getTipoTramitacion() != null) {
                        tramite.getTipoTramitacion().setCodigo(null);
                    }
                    if (tramite.getListaDocumentos() != null) {
                        for (ProcedimientoDocumentoDTO documento : tramite.getListaDocumentos()) {
                            limpiarDocumento(documento, ruta);
                        }
                    }

                    if (tramite.getListaModelos() != null) {
                        for (ProcedimientoDocumentoDTO documento : tramite.getListaModelos()) {
                            limpiarDocumento(documento, ruta);
                        }
                    }
                }
            }
        }
        if (proc instanceof ServicioDTO) {
            if (((ServicioDTO) proc).getTipoTramitacion() != null) {
                ((ServicioDTO) proc).setTipoTramitacion(null);
            }
        }
        return proc;
    }

    private void limpiarDocumento(ProcedimientoDocumentoDTO documento, String ruta) {
        documento.setCodigo(null);
        if (documento.getDocumentos() != null && documento.getDocumentos().getTraducciones() != null) {
            for (DocumentoTraduccion trad : documento.getDocumentos().getTraducciones()) {
                if (trad.getFicheroDTO() != null && trad.getFicheroDTO().getCodigo() != null) {
                    FicheroDTO ficheroDTO = ficheroExternoRepository.getContentById(trad.getFicheroDTO().getCodigo(), ruta);
                    Long idFicheroNuevo = ficheroExternoRepository.createFicheroExterno(ficheroDTO.getContenido(), ficheroDTO.getFilename(), ficheroDTO.getTipo(), null, ruta);
                    trad.getFicheroDTO().setCodigo(idFicheroNuevo);
                }
            }
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public ProcedimientoDTO findProcedimientoById(Long codigoWF) {
        return (ProcedimientoDTO) getProcedimientoDTOByCodigoWF(codigoWF);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public ProcedimientoDTO findProcedimientoByCodigo(Long codigo) {
        return (ProcedimientoDTO) getProcedimientoDTOByCodigo(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<TipoPublicoObjetivoEntidadDTO> getTipoPubObjEntByProc(Long codigo, String enlaceWF) {
        List<JProcedimientoWorkflow> listJprocWF = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = obtenerProcedimientosWorkflow(codigo, enlaceWF, listJprocWF);

        if (jprocWF != null) {
            return procedimientoRepository.getTipoPubObjEntByWFRest(jprocWF.getCodigo());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 1) {
            return procedimientoRepository.getTipoPubObjEntByWFRest(listJprocWF.get(0).getCodigo());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 2) {
            return procedimientoRepository.getTipoPubObjEntByWFRest(listJprocWF.get(0).getCodigo(), listJprocWF.get(1).getCodigo(), enlaceWF);
        }

        return new ArrayList<>();
    }

    private JProcedimientoWorkflow obtenerProcedimientosWorkflow(Long codigo, String enlaceWF, List<JProcedimientoWorkflow> listJprocWF) {
        JProcedimientoWorkflow jprocWFAux = null;
        JProcedimientoWorkflow jprocWF = null;
        switch (enlaceWF) {
            case "D":
                jprocWF = procedimientoRepository.getWFByCodigoWF(getCodigoPublicado(codigo));
                break;
            case "M":
                jprocWF = procedimientoRepository.getWFByCodigoWF(getCodigoModificacion(codigo));
                break;
            case "A":
            case "T":
            default:
                jprocWFAux = procedimientoRepository.getWFByCodigoWF(getCodigoPublicado(codigo));

                if (jprocWFAux != null) {
                    listJprocWF.add(jprocWFAux);
                }

                jprocWFAux = procedimientoRepository.getWFByCodigoWF(getCodigoModificacion(codigo));

                if (jprocWFAux != null) {
                    listJprocWF.add(jprocWFAux);
                }
        }
        return jprocWF;
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<TipoMateriaSIADTO> getTipoMateriaByProc(Long codigo, String enlaceWF) {
        List<JProcedimientoWorkflow> listJprocWF = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = obtenerProcedimientosWorkflow(codigo, enlaceWF, listJprocWF);

        if (jprocWF != null) {
            return procedimientoRepository.getMateriaSIAByWFRest(jprocWF.getCodigo());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 1) {
            return procedimientoRepository.getMateriaSIAByWFRest(listJprocWF.get(0).getCodigo());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 2) {
            return procedimientoRepository.getMateriaSIAByWFRest(listJprocWF.get(0).getCodigo(), listJprocWF.get(1).getCodigo(), enlaceWF);
        }

        return new ArrayList<>();
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<ProcedimientoDocumentoDTO> getDocumentosByProc(Long codigo, String enlaceWF) {
        List<JProcedimientoWorkflow> listJprocWF = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = obtenerProcedimientosWorkflow(codigo, enlaceWF, listJprocWF);

        if (jprocWF != null && jprocWF.getListaDocumentos() != null) {
            return procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentos());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 1) {
            return procedimientoRepository.getDocumentosByListaDocumentos(listJprocWF.get(0).getListaDocumentos());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 2) {
            return procedimientoRepository.getDocumentosByListaDocumentos(listJprocWF.get(0).getListaDocumentos(), listJprocWF.get(1).getListaDocumentos(), enlaceWF);
        }

        return new ArrayList<>();
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<ProcedimientoDocumentoDTO> getDocumentosLOPDByProc(Long codigo, String enlaceWF) {
        List<JProcedimientoWorkflow> listJprocWF = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = obtenerProcedimientosWorkflow(codigo, enlaceWF, listJprocWF);

        if (jprocWF != null && jprocWF.getListaDocumentosLOPD() != null) {
            return procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentosLOPD());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 1) {
            return procedimientoRepository.getDocumentosByListaDocumentos(listJprocWF.get(0).getListaDocumentosLOPD());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 2) {
            return procedimientoRepository.getDocumentosByListaDocumentos(listJprocWF.get(0).getListaDocumentosLOPD(), listJprocWF.get(1).getListaDocumentosLOPD(), enlaceWF);
        }

        return new ArrayList<>();
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<NormativaDTO> getNormativasByProc(Long codigo, String enlaceWF) {
        List<JProcedimientoWorkflow> listJprocWF = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = obtenerProcedimientosWorkflow(codigo, enlaceWF, listJprocWF);

        if (jprocWF != null) {
            return procedimientoRepository.getNormativasByWFRest(jprocWF.getCodigo());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 1) {
            return procedimientoRepository.getNormativasByWFRest(listJprocWF.get(0).getCodigo());
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 2) {
            return procedimientoRepository.getNormativasByWFRest(listJprocWF.get(0).getCodigo(), listJprocWF.get(1).getCodigo(), enlaceWF);
        }

        return new ArrayList<>();

    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<TemaDTO> getTemasByProc(Long codigo, String enlaceWF) {
        List<JProcedimientoWorkflow> listJprocWF = new ArrayList<>();
        List<TemaDTO> temasDTO = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = obtenerProcedimientosWorkflow(codigo, enlaceWF, listJprocWF);

        if (jprocWF != null && jprocWF.getTemas() != null) {
            for (JTema tema : jprocWF.getTemas()) {
                temasDTO.add(temaConverter.createDTO(tema));
            }
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 1) {
            for (JTema tema : listJprocWF.get(0).getTemas()) {
                temasDTO.add(temaConverter.createDTO(tema));
            }
        } else if (!listJprocWF.isEmpty() && listJprocWF.size() == 2) {
            for (JTema tema : listJprocWF.get(0).getTemas()) {
                temasDTO.add(temaConverter.createDTO(tema));
            }

            for (JTema tema : listJprocWF.get(1).getTemas()) {
                if (!contiene(temasDTO, tema)) {
                    temasDTO.add(temaConverter.createDTO(tema));
                }
            }
        }

        return temasDTO;
    }

    private boolean contiene(List<TemaDTO> temasDTO, JTema tema) {
        for (TemaDTO temaDTO : temasDTO) {
            if (temaDTO.getCodigo().longValue() == tema.getCodigo().longValue()) {
                return true;
            }
        }
        return false;
    }

    private ProcedimientoBaseDTO getProcedimientoDTOByCodigo(Long codigo) {
        JProcedimiento jproc = procedimientoRepository.findById(codigo);

        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWFByCodigoWF(getCodigoPublicado(codigo));

        ProcedimientoBaseDTO proc = createDTO(jproc);

        // JProcedimientoWorkflow jprocWF = procedimientoRepository.getWF(id,
        // Constantes.PROCEDIMIENTO_ENMODIFICACION);
        proc.setCodigoWF(jprocWF.getCodigo());
        proc.setFechaPublicacion(jprocWF.getFechaPublicacion());
        proc.setFechaCaducidad(jprocWF.getFechaCaducidad());
        proc.setResponsableEmail(jprocWF.getResponsableEmail());
        proc.setResponsableTelefono(jprocWF.getResponsableTelefono());
        proc.setWorkflow(TypeProcedimientoWorkflow.fromBoolean(jprocWF.getWorkflow()));
        proc.setEstado(TypeProcedimientoEstado.fromString(jprocWF.getEstado()));
        proc.setMensajes(jproc.getMensajes());
        proc.setTieneTasa(jprocWF.getTieneTasa());
        proc.setResponsable(jprocWF.getResponsableNombre());
        proc.setLopdResponsable(jprocWF.getLopdResponsable());
        proc.setComun(jprocWF.getComun());
        // proc.setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
        // proc.setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
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
        if (jprocWF.getTipoVia() != null) {
            proc.setTipoVia(tipoViaConverter.createDTO(jprocWF.getTipoVia()));
        }
        if (jprocWF.getDatosPersonalesLegitimacion() != null) {
            proc.setDatosPersonalesLegitimacion(tipoLegitimacionConverter.createDTO(jprocWF.getDatosPersonalesLegitimacion()));
        }

        // proc.setComun(jprocWF.isComun());
        Literal nombreProcedimientoWorkFlow = new Literal();
        Literal requisitos = new Literal();
        Literal datosPersonalesDestinatario = new Literal();
        Literal detDatosPersonalesFinalidad = new Literal();
        Literal objeto = new Literal();
        Literal destinatarios = new Literal();
        Literal terminoResolucion = new Literal();
        Literal observaciones = new Literal();

        if (jprocWF.getTraducciones() != null) {
            for (JProcedimientoWorkflowTraduccion trad : jprocWF.getTraducciones()) {
                nombreProcedimientoWorkFlow.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
                requisitos.add(new Traduccion(trad.getIdioma(), trad.getRequisitos()));
                datosPersonalesDestinatario.add(new Traduccion(trad.getIdioma(), trad.getDatosPersonalesDestinatario()));
                detDatosPersonalesFinalidad.add(new Traduccion(trad.getIdioma(), trad.getDatosPersonalesFinalidad()));
                objeto.add(new Traduccion(trad.getIdioma(), trad.getObjeto()));
                destinatarios.add(new Traduccion(trad.getIdioma(), trad.getDestinatarios()));
                terminoResolucion.add(new Traduccion(trad.getIdioma(), trad.getTerminoResolucion()));
                observaciones.add(new Traduccion(trad.getIdioma(), trad.getObservaciones()));
            }
        }
        proc.setNombreProcedimientoWorkFlow(nombreProcedimientoWorkFlow);
        proc.setDatosPersonalesDestinatario(datosPersonalesDestinatario);
        proc.setDatosPersonalesFinalidad(detDatosPersonalesFinalidad);
        proc.setRequisitos(requisitos);
        proc.setObjeto(objeto);
        proc.setDestinatarios(destinatarios);
        proc.setTerminoResolucion(terminoResolucion);
        proc.setObservaciones(observaciones);
        // proc.setLopdInfoAdicional(lopdInfoAdicional);
        proc.setMateriasSIA(procedimientoRepository.getMateriaGridSIAByWF(proc.getCodigoWF()));
        proc.setPublicosObjetivo(procedimientoRepository.getTipoPubObjEntByWF(proc.getCodigoWF()));
        proc.setNormativas(procedimientoRepository.getNormativasByWF(proc.getCodigoWF()));
        proc.setDocumentos(procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentos()));
        proc.setDocumentosLOPD(procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentosLOPD()));

        // Reordenamos por posicion
        Collections.sort(proc.getNormativas());
        Collections.sort(proc.getDocumentos());
        // Collections.sort(proc.getDocumentosLOPD());

        if (jprocWF.getTemas() != null) {
            List<TemaGridDTO> temasDTO = new ArrayList<>();
            for (JTema tema : jprocWF.getTemas()) {
                TemaGridDTO temaGridDTO = new TemaGridDTO();
                temaGridDTO.setCodigo(tema.getCodigo());
                temaGridDTO.setIdentificador(tema.getIdentificador());
                temaGridDTO.setEntidad(tema.getEntidad().getCodigo());
                temaGridDTO.setMathPath(tema.getMathPath());
                if (tema.getTemaPadre() != null) {
                    temaGridDTO.setTemaPadre(tema.getTemaPadre().getIdentificador());
                }
                temasDTO.add(temaGridDTO);
            }
            proc.setTemas(temasDTO);
        }

        if (proc instanceof ProcedimientoDTO) {
            ((ProcedimientoDTO) proc).setTramites(procedimientoRepository.getTramitesByWF(proc.getCodigoWF()));
            Collections.sort(((ProcedimientoDTO) proc).getTramites());
            if (((ProcedimientoDTO) proc).getTramites() != null && !((ProcedimientoDTO) proc).getTramites().isEmpty()) {
                for (ProcedimientoTramiteDTO tram : ((ProcedimientoDTO) proc).getTramites()) {
                    if (tram.getListaModelos() != null && !tram.getListaModelos().isEmpty()) {
                        Collections.sort(tram.getListaModelos());
                    }
                    if (tram.getListaDocumentos() != null && !tram.getListaDocumentos().isEmpty()) {
                        Collections.sort(tram.getListaDocumentos());
                    }
                }

            }
            ((ProcedimientoDTO) proc).setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
            ((ProcedimientoDTO) proc).setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
        }
        if (proc instanceof ServicioDTO) {
            ((ServicioDTO) proc).setTramitElectronica(jprocWF.isTramitElectronica());
            ((ServicioDTO) proc).setTramitPresencial(jprocWF.isTramitPresencial());
            ((ServicioDTO) proc).setTramitTelefonica(jprocWF.isTramitTelefonica());
            ((ServicioDTO) proc).setActivoLOPD(jprocWF.getActivoLOPD());

            if (jprocWF.getTramiteElectronico() != null) {
                TipoTramitacionDTO tipo = tipoTramitacionConverter.createDTO(jprocWF.getTramiteElectronico());
                ((ServicioDTO) proc).setTipoTramitacion(tipo);

            } else if (jprocWF.getTramiteElectronicoPlantilla() != null) {
                TipoTramitacionDTO tipo = tipoTramitacionConverter.createDTO(jprocWF.getTramiteElectronicoPlantilla());
                ((ServicioDTO) proc).setPlantillaSel(tipo);
            }
        }
        return proc;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public ProcedimientoSolrDTO findDataIndexacionProcById(Long codigoWF) {
        ProcedimientoDTO procedimiento = (ProcedimientoDTO) getProcedimientoDTOByCodigoWF(codigoWF);
        PathUA pathUA = uaRepository.getPath(procedimiento.getUaInstructor().getUAGrid());
        DataIndexacion dataIndexacion = CastUtil.getDataIndexacion(procedimiento, pathUA);
        ProcedimientoSolrDTO proc = new ProcedimientoSolrDTO();
        proc.setDataIndexacion(dataIndexacion);
        proc.setProcedimientoDTO(procedimiento);
        proc.setPathUA(pathUA);
        return proc;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ServicioDTO findServicioById(Long codigoWF) {
        return (ServicioDTO) getProcedimientoDTOByCodigoWF(codigoWF);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcedimientoSolrDTO findDataIndexacionServById(Long codigoWF) {
        ServicioDTO serv = (ServicioDTO) getProcedimientoDTOByCodigoWF(codigoWF);
        PathUA pathUA = uaRepository.getPath(serv.getUaInstructor().getUAGrid());
        DataIndexacion dataIndexacion = CastUtil.getDataIndexacion(serv, pathUA);
        ProcedimientoSolrDTO proc = new ProcedimientoSolrDTO();
        proc.setDataIndexacion(dataIndexacion);
        proc.setServicioDTO(serv);
        proc.setPathUA(pathUA);
        return proc;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public ProcedimientoBaseDTO convertirDTO(Object obj) {
        JProcedimientoWorkflow jprocWF = (JProcedimientoWorkflow) obj;
        return convertDTO(jprocWF);
    }

    private ProcedimientoBaseDTO getProcedimientoDTOByCodigoWF(Long codigoWF) {
        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWFByCodigoWF(codigoWF);
        return convertDTO(jprocWF);

    }

    private ProcedimientoBaseDTO convertDTO(JProcedimientoWorkflow jprocWF) {
        JProcedimiento jproc = jprocWF.getProcedimiento();
        ProcedimientoBaseDTO proc = createDTO(jproc);

        // JProcedimientoWorkflow jprocWF = procedimientoRepository.getWF(id,
        // Constantes.PROCEDIMIENTO_ENMODIFICACION);
        proc.setCodigoWF(jprocWF.getCodigo());
        proc.setFechaPublicacion(jprocWF.getFechaPublicacion());
        proc.setFechaCaducidad(jprocWF.getFechaCaducidad());
        proc.setFechaActualizacion(jproc.getFechaActualizacion());
        proc.setResponsableEmail(jprocWF.getResponsableEmail());
        proc.setResponsableTelefono(jprocWF.getResponsableTelefono());
        proc.setWorkflow(TypeProcedimientoWorkflow.fromBoolean(jprocWF.getWorkflow()));
        proc.setEstado(TypeProcedimientoEstado.fromString(jprocWF.getEstado()));
        proc.setMensajes(jproc.getMensajes());
        proc.setTieneTasa(jprocWF.getTieneTasa());
        proc.setResponsable(jprocWF.getResponsableNombre());
        proc.setLopdResponsable(jprocWF.getLopdResponsable());
        proc.setComun(jprocWF.getComun());
        // proc.setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
        // proc.setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
        if (jprocWF.getUaResponsable() != null) {
            proc.setUaResponsable(jprocWF.getUaResponsable().toDTO());
        }
        if (jprocWF.getTramitElectronica() != null) {
            proc.setTramitElectronica(jprocWF.getTramitElectronica());
        }
        if (jprocWF.getTramitPresencial() != null) {
            proc.setTramitPresencial(jprocWF.getTramitPresencial());
        }
        if (jprocWF.getTramitTelefonica() != null) {
            proc.setTramitTelefonica(jprocWF.getTramitTelefonica());
        }
        if (jprocWF.getUaInstructor() != null) {
            proc.setUaInstructor(jprocWF.getUaInstructor().toDTO());
        }
        if (jprocWF.getUaCompetente() != null) {
            proc.setUaCompetente(jprocWF.getUaCompetente().toDTO());
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
        if (jprocWF.getTipoVia() != null) {
            proc.setTipoVia(tipoViaConverter.createDTO(jprocWF.getTipoVia()));
        }
        if (jprocWF.getDatosPersonalesLegitimacion() != null) {
            proc.setDatosPersonalesLegitimacion(tipoLegitimacionConverter.createDTO(jprocWF.getDatosPersonalesLegitimacion()));
        }

        // proc.setComun(jprocWF.isComun());
        Literal nombreProcedimientoWorkFlow = new Literal();
        Literal requisitos = new Literal();
        Literal datosPersonalesDestinatario = new Literal();
        Literal detDatosPersonalesFinalidad = new Literal();
        Literal objeto = new Literal();
        Literal destinatarios = new Literal();
        Literal terminoResolucion = new Literal();
        Literal observaciones = new Literal();

        if (jprocWF.getTraducciones() != null) {
            for (JProcedimientoWorkflowTraduccion trad : jprocWF.getTraducciones()) {
                nombreProcedimientoWorkFlow.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
                requisitos.add(new Traduccion(trad.getIdioma(), trad.getRequisitos()));
                datosPersonalesDestinatario.add(new Traduccion(trad.getIdioma(), trad.getDatosPersonalesDestinatario()));
                detDatosPersonalesFinalidad.add(new Traduccion(trad.getIdioma(), trad.getDatosPersonalesFinalidad()));
                objeto.add(new Traduccion(trad.getIdioma(), trad.getObjeto()));
                destinatarios.add(new Traduccion(trad.getIdioma(), trad.getDestinatarios()));
                terminoResolucion.add(new Traduccion(trad.getIdioma(), trad.getTerminoResolucion()));
                observaciones.add(new Traduccion(trad.getIdioma(), trad.getObservaciones()));
            }
        }
        proc.setNombreProcedimientoWorkFlow(nombreProcedimientoWorkFlow);
        proc.setDatosPersonalesDestinatario(datosPersonalesDestinatario);
        proc.setDatosPersonalesFinalidad(detDatosPersonalesFinalidad);
        proc.setRequisitos(requisitos);
        proc.setObjeto(objeto);
        proc.setDestinatarios(destinatarios);
        proc.setTerminoResolucion(terminoResolucion);
        proc.setObservaciones(observaciones);
        // proc.setLopdInfoAdicional(lopdInfoAdicional);
        proc.setMateriasSIA(procedimientoRepository.getMateriaGridSIAByWF(proc.getCodigoWF()));
        proc.setPublicosObjetivo(procedimientoRepository.getTipoPubObjEntByWF(proc.getCodigoWF()));
        proc.setNormativas(procedimientoRepository.getNormativasByWF(proc.getCodigoWF()));
        proc.setDocumentos(procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentos()));
        proc.setDocumentosLOPD(procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentosLOPD()));

        // Reordenamos por posicion
        Collections.sort(proc.getNormativas());
        Collections.sort(proc.getDocumentos());
        // Collections.sort(proc.getDocumentosLOPD());

        if (jprocWF.getTemas() != null) {
            List<TemaGridDTO> temasDTO = new ArrayList<>();
            for (JTema tema : jprocWF.getTemas()) {
                TemaGridDTO temaGridDTO = new TemaGridDTO();
                temaGridDTO.setCodigo(tema.getCodigo());
                temaGridDTO.setIdentificador(tema.getIdentificador());
                temaGridDTO.setEntidad(tema.getEntidad().getCodigo());
                temaGridDTO.setMathPath(tema.getMathPath());

                if (tema.getTemaPadre() != null) {
                    temaGridDTO.setTemaPadre(tema.getTemaPadre().getIdentificador());
                }
                List<Traduccion> traducciones = new ArrayList<>();
                for (JTemaTraduccion temaTraduccion : tema.getDescripcion()) {
                    traducciones.add(new Traduccion(temaTraduccion.getIdioma(), temaTraduccion.getDescripcion()));
                }
                Literal descripcion = new Literal();
                descripcion.setTraducciones(traducciones);
                temaGridDTO.setDescripcion(descripcion);
                temasDTO.add(temaGridDTO);
            }
            proc.setTemas(temasDTO);
        }

        if (proc instanceof ProcedimientoDTO) {
            ((ProcedimientoDTO) proc).setTramites(procedimientoRepository.getTramitesByWF(proc.getCodigoWF()));
            Collections.sort(((ProcedimientoDTO) proc).getTramites());
            if (((ProcedimientoDTO) proc).getTramites() != null && !((ProcedimientoDTO) proc).getTramites().isEmpty()) {
                for (ProcedimientoTramiteDTO tram : ((ProcedimientoDTO) proc).getTramites()) {
                    if (tram.getListaModelos() != null && !tram.getListaModelos().isEmpty()) {
                        Collections.sort(tram.getListaModelos());
                    }
                    if (tram.getListaDocumentos() != null && !tram.getListaDocumentos().isEmpty()) {
                        Collections.sort(tram.getListaDocumentos());
                    }
                }

            }
            ((ProcedimientoDTO) proc).setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
            ((ProcedimientoDTO) proc).setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
        }

        if (proc instanceof ProcedimientoBaseDTO) {

            ((ProcedimientoBaseDTO) proc).setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
            ((ProcedimientoBaseDTO) proc).setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
        }

        if (proc instanceof ServicioDTO) {
            ((ServicioDTO) proc).setTramitElectronica(jprocWF.isTramitElectronica());
            ((ServicioDTO) proc).setTramitPresencial(jprocWF.isTramitPresencial());
            ((ServicioDTO) proc).setTramitTelefonica(jprocWF.isTramitTelefonica());
            ((ServicioDTO) proc).setActivoLOPD(jprocWF.getActivoLOPD());

            if (jprocWF.getTramiteElectronico() != null) {
                TipoTramitacionDTO tipo = tipoTramitacionConverter.createDTO(jprocWF.getTramiteElectronico());
                ((ServicioDTO) proc).setTipoTramitacion(tipo);

            } else if (jprocWF.getTramiteElectronicoPlantilla() != null) {
                TipoTramitacionDTO tipo = tipoTramitacionConverter.createDTO(jprocWF.getTramiteElectronicoPlantilla());
                ((ServicioDTO) proc).setPlantillaSel(tipo);
            }
        }
        return proc;
    }

    /**
     * Crear auditoria
     *
     * @param procedimientoAntiguo
     * @param procedimientoNuevo
     */
    private void crearAuditoria(final ProcedimientoBaseDTO procedimientoAntiguo, final ProcedimientoBaseDTO procedimientoNuevo, TypePerfiles perfil, String literalFlujo, String accion) {

        final ProcedimientoAuditoria procedimientoAuditoria = new ProcedimientoAuditoria();
        List<AuditoriaCambio> cambios = new ArrayList<>();
        AuditoriaCambio cambio = null;

        cambios = ProcedimientoDTO.auditar(procedimientoAntiguo, procedimientoNuevo);

        if (!cambios.isEmpty()) {
            try {
                String auditoriaJson = JSONUtil.toJSON(cambios);
                JProcedimientoAuditoria jprocAudit = new JProcedimientoAuditoria();
                JProcedimiento jproc = this.procedimientoRepository.findById(procedimientoNuevo.getCodigo());
                jprocAudit.setProcedimiento(jproc);
                Calendar calendar = Calendar.getInstance();
                jprocAudit.setFechaModificacion(calendar.getTime());
                jprocAudit.setListaModificaciones(auditoriaJson);
                jprocAudit.setUsuarioModificacion(procedimientoNuevo.getUsuarioAuditoria());
                jprocAudit.setUsuarioPerfil(perfil.toString());
                jprocAudit.setLiteralFlujo(literalFlujo);
                jprocAudit.setAccion(accion);
                this.auditoriaRepository.guardar(jprocAudit);

            } catch (final JSONUtilException e) {
                throw new AuditoriaException(e);
            }
        }
    }

    private ProcedimientoBaseDTO createDTO(JProcedimiento jproc) {
        ProcedimientoBaseDTO dato = null;
        if (jproc.getTipo().equals(Constantes.PROCEDIMIENTO)) {
            dato = new ProcedimientoDTO();
        }
        if (jproc.getTipo().equals(Constantes.SERVICIO)) {
            dato = new ServicioDTO();
        }
        dato.setCodigo(jproc.getCodigo());
        // ;jproc.getCodigoDir3SIA();
        dato.setCodigoSIA(jproc.getCodigoSIA());
        dato.setEstadoSIA(jproc.getEstadoSIA());
        dato.setMensajes(jproc.getMensajes());
        if (jproc.getSiaFecha() != null) {
            dato.setFechaSIA(jproc.getSiaFecha());
        }
        dato.setErrorSIA(jproc.getMensajeIndexacionSIA());
        dato.setTipo(jproc.getTipo());
        return dato;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Pagina<ProcedimientoGridDTO> findProcedimientosByFiltro(ProcedimientoFiltro filtro) {
        List<ProcedimientoGridDTO> items = procedimientoRepository.findProcedimientosPagedByFiltro(filtro);
        long total = procedimientoRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public String getEnlaceTelematicoByServicio(ProcedimientoFiltro filtro) {
        return procedimientoRepository.getEnlaceTelematico(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public String getEnlaceTelematicoByTramite(ProcedimientoTramiteFiltro filtro) {
        return procedimientoTramiteRepository.getEnlaceTelematico(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public ProcedimientoBaseDTO findProcedimientoBaseById(Long codigo) {
        JProcedimiento jproc = procedimientoRepository.findById(codigo);
        ProcedimientoBaseDTO proc = new ProcedimientoBaseDTO();
        if (jproc != null) {
            proc.setCodigoSIA(jproc.getCodigoSIA());
            proc.setFechaSIA(jproc.getSiaFecha());
            proc.setErrorSIA(jproc.getMensajeIndexacionSIA());
            proc.setEstadoSIA(jproc.getEstadoSIA());
        }
        return proc;
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<ProcedimientoBaseDTO> findProcedimientosByFiltroRest(ProcedimientoFiltro filtro) {
        List<ProcedimientoBaseDTO> items = procedimientoRepository.findProcedimientosPagedByFiltroRest(filtro);
        long total = procedimientoRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Pagina<ServicioGridDTO> findServiciosByFiltro(ProcedimientoFiltro filtro) {
        List<ServicioGridDTO> items = procedimientoRepository.findServiciosPagedByFiltro(filtro);
        long total = procedimientoRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Long countByFiltro(ProcedimientoFiltro filtro) {
        return procedimientoRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countByEntidad(Long entidadId) {
        return procedimientoRepository.countByEntidad(entidadId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countServicioByEntidad(Long entidadId) {
        return procedimientoRepository.countServicioByEntidad(entidadId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Optional<ProcedimientoTramiteDTO> findProcedimientoTramiteById(Long id) {
        Optional<JProcedimientoTramite> opt = procedimientoTramiteRepository.findById(id.toString());
        return opt.map(jProcedimientoTramite -> procedimientoTramiteConverter.createDTO(jProcedimientoTramite));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcedimientoTramiteDTO> findProcTramitesByProcedimientoId(Long id) {
        return procedimientoTramiteConverter.createDTOs(procedimientoTramiteRepository.findByProcedimientoId(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
        // Comprovam que el codiSia no existeix ja (
        if (dto.getCodigo() != null) { // .isPresent()) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JProcedimientoTramite jProcedimientoTramite = procedimientoTramiteConverter.createEntity(dto);
        procedimientoTramiteRepository.create(jProcedimientoTramite);
        return jProcedimientoTramite.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException {
        JProcedimientoTramite entidad = procedimientoTramiteRepository.getReference(dto.getCodigo());
        procedimientoTramiteConverter.mergeEntity(entidad, dto);
        procedimientoTramiteRepository.update(entidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteProcedimientoTramite(Long id) throws RecursoNoEncontradoException {
        JProcedimientoTramite entidad = procedimientoTramiteRepository.getReference(id);
        procedimientoTramiteRepository.delete(entidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void guardarFlujo(ProcedimientoBaseDTO data, TypeProcedimientoEstado estadoDestino, String mensajes, TypePerfiles perfil, boolean pendienteMensajeSupervisor, boolean pendienteMensajesGestor, Long idEntidad) {

        // Paso 0. Marcamos para indexar en solr/elastic y SIA.
        TypeIndexacion tipo = (data instanceof ProcedimientoDTO) ? TypeIndexacion.PROCEDIMIENTO : TypeIndexacion.SERVICIO;

        /** 2 == borrado ; 1 == insertar **/
        int accionIdx = (estadoDestino == TypeProcedimientoEstado.BORRADO || estadoDestino == TypeProcedimientoEstado.RESERVA) ? 2 : 1;
        indexacionRepository.guardarIndexar(data.getCodigo(), tipo, idEntidad, accionIdx);
        int accion = (estadoDestino != null && estadoDestino == TypeProcedimientoEstado.BORRADO) ? 0 : 1;
        indexacionSIARepository.guardarIndexar(data.getCodigo(), tipo.toString(), idEntidad, 1, accion);

        // Primero borramos el wf destino (si es de destinto wf)
        if (TypeProcedimientoEstado.distintoWorkflow(data.getEstado(), estadoDestino)) {

            Long codigoWF = procedimientoRepository.getCodigoByWF(data.getCodigo(), true);
            ProcedimientoBaseDTO procDestino = null;
            if (data instanceof ProcedimientoDTO && codigoWF != null) {
                procDestino = (ProcedimientoDTO) getProcedimientoDTOByCodigoWF(codigoWF);
            }
            if (data instanceof ServicioDTO && codigoWF != null) {
                procDestino = (ServicioDTO) getProcedimientoDTOByCodigoWF(codigoWF);
            }

            if (procDestino == null && data.getCodigo() != null) {
                if (estadoDestino.equals(TypeProcedimientoEstado.BORRADO) || estadoDestino.equals(TypeProcedimientoEstado.RESERVA)) {
                    generarAuditoria("auditoria.flujo." + data.getEstado().toString() + estadoDestino.toString(), data.getCodigo(), data.getUsuarioAuditoria(), perfil, TypeAccionAuditoria.BAJA.toString());
                } else {
                    generarAuditoria("auditoria.flujo." + data.getEstado().toString() + estadoDestino.toString(), data.getCodigo(), data.getUsuarioAuditoria(), perfil, TypeAccionAuditoria.MODIFICACION.toString());
                }

            } else {
                if (estadoDestino.equals(TypeProcedimientoEstado.BORRADO) || estadoDestino.equals(TypeProcedimientoEstado.RESERVA)) {
                    crearAuditoria(procDestino, data, perfil, "auditoria.flujo." + data.getEstado().toString() + estadoDestino.toString(), TypeAccionAuditoria.BAJA.toString());
                } else {
                    crearAuditoria(procDestino, data, perfil, "auditoria.flujo." + data.getEstado().toString() + estadoDestino.toString(), TypeAccionAuditoria.MODIFICACION.toString());
                }
            }

            // Borramos el wf destino
            procedimientoRepository.deleteWF(data.getCodigo(), estadoDestino.getWorkflowSegunEstado().getValor());
        }

        // Segundo actualizamos el dato
        JProcedimientoWorkflow jProcWF = procedimientoRepository.getWFByCodigoWF(data.getCodigoWF());

        data.setEstado(estadoDestino);
        data.setWorkflow(estadoDestino.getWorkflowSegunEstado());
        this.updateWF(data, jProcWF);

        // Actualizamos los mensajes de procedimientos
        procedimientoRepository.actualizarMensajes(data.getCodigo(), mensajes, pendienteMensajeSupervisor, pendienteMensajesGestor);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarMensajes(Long idProc, String mensajes, boolean pendienteMensajeSupervisor, boolean pendienteMensajesGestor) {
        procedimientoRepository.actualizarMensajes(idProc, mensajes, pendienteMensajeSupervisor, pendienteMensajesGestor);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCodigoByWF(Long codigo, boolean valor) {
        return procedimientoRepository.getCodigoByWF(codigo, valor);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarSolr(IndexacionDTO proc, ResultadoAccion resultadoAccion) {
        indexacionRepository.actualizarDato(proc, resultadoAccion);
        procedimientoRepository.actualizarSolr(proc, resultadoAccion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarSIA(IndexacionSIADTO siadto, ResultadoSIA resultadoAccion) {
        if (siadto.getCodigo() != null) {
            indexacionSIARepository.actualizarDato(siadto, resultadoAccion);
        }
        procedimientoRepository.actualizarSIA(siadto, resultadoAccion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Long getCodigoPublicado(Long codProc) {
        return procedimientoRepository.getCodigoByWF(codProc, Constantes.PROCEDIMIENTO_DEFINITIVO);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Long getCodigoModificacion(Long codProc) {
        return procedimientoRepository.getCodigoByWF(codProc, Constantes.PROCEDIMIENTO_ENMODIFICACION);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public DataIndexacion findDataIndexacionTram(ProcedimientoTramiteDTO tramite, ProcedimientoDTO procedimientoDTO, PathUA pathUA) {
        return CastUtil.getDataIndexacion(tramite, procedimientoDTO, pathUA);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public String getMensajesByCodigo(Long codigo) {
        return procedimientoRepository.getMensajesByCodigo(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public ServicioDTO findServicioByCodigo(Long codigo) {
        return (ServicioDTO) getProcedimientoDTOByCodigo(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Pagina<IndexacionDTO> getProcedimientosParaIndexacion(boolean isTipoProcedimiento, Long idEntidad) {
        return procedimientoRepository.getProcedimientosParaIndexacion(isTipoProcedimiento, idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public Pagina<IndexacionSIADTO> getProcedimientosParaIndexacionSIA(Long idEntidad) {
        return procedimientoRepository.getProcedimientosParaIndexacionSIA(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR, TypePerfiles.RESTAPI_VALOR})
    public IndexFile findDataIndexacionProcDoc(ProcedimientoDTO procedimientoDTO, ProcedimientoDocumentoDTO doc, DocumentoTraduccion documentoTraduccion, PathUA pathUA) {
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        FicheroDTO ficheroDTO = ficheroExternoRepository.getContentById(documentoTraduccion.getFicheroDTO().getCodigo(), ruta);
        return CastUtil.getDataIndexacion(procedimientoDTO, doc, documentoTraduccion, ficheroDTO, documentoTraduccion.getIdioma(), pathUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<ProcedimientoDocumentoDTO> findProcedimientoDocumentoByFiltroRest(ProcedimientoDocumentoFiltro filtro) {
        try {
            List<ProcedimientoDocumentoDTO> items = procedimientoDocumentoRepository.findPagedByFiltroRest(filtro);
            long total = procedimientoDocumentoRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<ProcedimientoDocumentoDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    public IndexFile findDataIndexacionTramDoc(ProcedimientoTramiteDTO tramite, ProcedimientoDTO procedimientoDTO, ProcedimientoDocumentoDTO doc, DocumentoTraduccion fichero, PathUA pathUA) {
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        FicheroDTO ficheroDTO = ficheroExternoRepository.getContentById(fichero.getFicheroDTO().getCodigo(), ruta);
        return CastUtil.getDataIndexacion(procedimientoDTO, tramite, doc, fichero, ficheroDTO, fichero.getIdioma(), pathUA);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<ProcedimientoTramiteDTO> findProcedimientoTramiteByFiltroRest(ProcedimientoTramiteFiltro filtro) {
        try {
            List<ProcedimientoTramiteDTO> items = procedimientoTramiteRepository.findPagedByFiltroRest(filtro);
            long total = procedimientoTramiteRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<ProcedimientoTramiteDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    public ProcedimientoAuditoriaConverter getProcedimientoAuditoriaConverter() {
        return procedimientoAuditoriaConverter;
    }

    public void setProcedimientoAuditoriaConverter(ProcedimientoAuditoriaConverter procedimientoAuditoriaConverter) {
        this.procedimientoAuditoriaConverter = procedimientoAuditoriaConverter;
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<ProcedimientoDocumentoDTO> getDocumentosByTram(Long codigo) {
        JProcedimientoTramite jtramite = procedimientoTramiteRepository.findById(codigo);

        if (jtramite != null && jtramite.getListaDocumentos() != null) {
            return procedimientoRepository.getDocumentosByListaDocumentos(jtramite.getListaDocumentos());
        }

        return new ArrayList<>();
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<ProcedimientoDocumentoDTO> getModelosByTram(Long codigo) {
        JProcedimientoTramite jtramite = procedimientoTramiteRepository.findById(codigo);

        if (jtramite != null && jtramite.getListaDocumentos() != null) {
            return procedimientoRepository.getDocumentosByListaDocumentos(jtramite.getListaModelos());
        }

        return new ArrayList<>();
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<TipoPublicoObjetivoEntidadDTO> getTipoPubObjEntByCodProcWF(Long codigoWF) {
        return procedimientoRepository.getTipoPubObjEntByWFRest(codigoWF);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<ProcedimientoDocumentoDTO> getDocumentosLOPDByCodProcWF(Long codigoWF) {
        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWFByCodigoWF(codigoWF);

        if (jprocWF != null) {
            return procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentosLOPD());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<ProcedimientoDocumentoDTO> getDocumentosByCodProcWF(Long codigoWF) {
        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWFByCodigoWF(codigoWF);

        if (jprocWF != null) {
            return procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentos());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<TemaDTO> getTemasByCodProcWF(Long codigoWF) {
        List<TemaDTO> temasDTO = new ArrayList<>();

        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWFByCodigoWF(codigoWF);

        if (jprocWF != null && jprocWF.getTemas() != null) {
            for (JTema tema : jprocWF.getTemas()) {
                temasDTO.add(temaConverter.createDTO(tema));
            }
        }

        return temasDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<NormativaDTO> getNormativasByCodProcWF(Long codigoWF) {
        return procedimientoRepository.getNormativasByWFRest(codigoWF);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public List<TipoMateriaSIADTO> getTipoMateriaByCodProcWF(Long codigoWF) {
        return procedimientoRepository.getMateriaSIAByWFRest(codigoWF);
    }
}
