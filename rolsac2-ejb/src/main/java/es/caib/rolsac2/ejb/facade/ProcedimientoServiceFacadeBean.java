package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.ejb.util.AuditoriaUtil;
import es.caib.rolsac2.ejb.util.JSONUtil;
import es.caib.rolsac2.ejb.util.JSONUtilException;
import es.caib.rolsac2.persistence.converter.*;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoTramitacionTraduccion;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.AuditoriaException;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import es.caib.rolsac2.service.model.auditoria.AuditoriaDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaIdioma;
import es.caib.rolsac2.service.model.auditoria.ProcedimientoAuditoria;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorfklow;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private TipoTramitacionRepository tipoTramitacionRepository;

    @Inject
    private PlatTramitElectronicaRepository platTramitElectronicaRepository;
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
    private TipoViaConverter tipoViaConverter;

    @Inject
    private TipoTramitacionConverter tipoTramitacionConverter;
    @Inject
    private TipoLegitimacionConverter tipoLegitimacionConverter;
    @Inject
    private ProcedimientoWorkflowRepository procedimientoWorkflowRepository;

    @Inject
    private ProcedimientoTramiteConverter procedimientoTramiteConverter;

    @Inject
    private ProcedimientoTramiteRepository procedimientoTramiteRepository;

    @Inject
    private SystemServiceFacade systemService;

    @Inject
    private FicheroExternoRepository ficheroExternoRepository;

    // @Inject
    // private AuditoriaRepository auditoriaRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(ProcedimientoBaseDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException {
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


        procedimientoRepository.create(jProcedimiento);

        jProcWF.setProcedimiento(jProcedimiento);
        jProcWF.setTraducciones(traducciones);
        procedimientoRepository.createWF(jProcWF);

        procedimientoRepository.mergeMateriaSIAProcWF(jProcWF.getCodigo(), dto.getMateriasGridSIA());
        procedimientoRepository.mergePublicoObjetivoProcWF(jProcWF.getCodigo(), dto.getTiposPubObjEntGrid());
        procedimientoRepository.mergeNormativaProcWF(jProcWF.getCodigo(), dto.getNormativas());
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentos() == null ? null : jProcWF.getListaDocumentos().getCodigo(), false, dto.getDocumentos(), ruta);
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentosLOPD() == null ? null : jProcWF.getListaDocumentosLOPD().getCodigo(), true, dto.getDocumentosLOPD(), ruta);
        if (dto instanceof ProcedimientoDTO) {
            List<ProcedimientoTramiteDTO> tramites = ((ProcedimientoDTO) dto).getTramites();
            procedimientoRepository.mergeTramitesProcWF(jProcWF.getCodigo(), tramites, ruta);
        }
        dto.setCodigoWF(jProcWF.getCodigo());
        return jProcedimiento.getCodigo();
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
        jProcWF.setDatosPersonalesActivo(dto.isDatosPersonalesActivo());
        jProcWF.setResponsableEmail(dto.getDirElectronica());
        jProcWF.setResponsableNombre(dto.getResponsable());
        jProcWF.setTieneTasa(dto.getTaxa());
        jProcWF.setUaResponsable(uaRepository.findJUAById(dto.getUaResponsable()));
        jProcWF.setUaInstructor(uaRepository.findJUAById(dto.getUaInstructor()));
        jProcWF.setFormaInicio(tipoFormaInicioConverter.createEntity(dto.getIniciacion()));
        jProcWF.setSilencioAdministrativo(tipoSilencioAdministrativoConverter.createEntity(dto.getSilencio()));
        jProcWF.setTipoProcedimiento(tipoProcedimientoConverter.createEntity(dto.getTipoProcedimiento()));
        jProcWF.setTipoVia(tipoViaConverter.createEntity(dto.getTipoVia()));
        jProcWF.setDatosPersonalesLegitimacion(tipoLegitimacionConverter.createEntity(dto.getDatosPersonalesLegitimacion()));
        jProcWF.setLopdResponsable(dto.getLopdResponsable());
        jProcWF.setComun(dto.isComun());
        if (dto instanceof ProcedimientoDTO) {
            jProcWF.setHabilitadoApoderado(((ProcedimientoDTO) dto).isHabilitadoApoderado());
            jProcWF.setHabilitadoFuncionario(((ProcedimientoDTO) dto).getHabilitadoFuncionario());
        }
        if (dto instanceof ServicioDTO) {
            jProcWF.setTramitElectronica(((ServicioDTO) dto).isTramitElectronica());
            jProcWF.setTramitPresencial(((ServicioDTO) dto).isTramitPresencial());
            jProcWF.setTramitTelefonica(((ServicioDTO) dto).isTramitTelefonica());
            if (((ServicioDTO) dto).getPlantillaSel() != null || ((ServicioDTO) dto).getTipoTramitacion() != null) {
                JTipoTramitacion jTipoTramitacion = null;
                if (((ServicioDTO) dto).getPlantillaSel() != null && ((ServicioDTO) dto).getPlantillaSel().getCodigo() != null) {
                    jTipoTramitacion = tipoTramitacionRepository.findById(((ServicioDTO) dto).getPlantillaSel().getCodigo());
                } else if (((ServicioDTO) dto).getTipoTramitacion() != null) {
                    if (((ServicioDTO) dto).getTipoTramitacion().getCodigo() == null) {
                        jTipoTramitacion = tipoTramitacionConverter.createEntity(((ServicioDTO) dto).getTipoTramitacion());
                        tipoTramitacionRepository.create(jTipoTramitacion);
                    } else {
                        jTipoTramitacion = tipoTramitacionRepository.findById(((ServicioDTO) dto).getTipoTramitacion().getCodigo());
                        tipoTramitacionConverter.mergeEntity(jTipoTramitacion, ((ServicioDTO) dto).getTipoTramitacion());
                        tipoTramitacionRepository.update(jTipoTramitacion);
                    }
                }
                jProcWF.setTramiteElectronico(jTipoTramitacion);
            } else {
                jProcWF.setTramiteElectronico(null);
            }
        }

    }

    private void mergeTraduccionTipoTramitacion(JTipoTramitacion jTipoTramitacion, TipoTramitacionDTO tipoTramitacion) {
        if (tipoTramitacion.getUrl() != null) {
            for (String idioma : tipoTramitacion.getUrl().getIdiomas()) {
                JTipoTramitacionTraduccion trad = jTipoTramitacion.getTraduccion(idioma);
                if (trad == null) {
                    trad = new JTipoTramitacionTraduccion();
                    trad.setUrl(tipoTramitacion.getUrl().getTraduccion(idioma));
                    trad.setIdioma(idioma);
                    trad.setTipoTramitacion(jTipoTramitacion);
                } else {
                    trad.setUrl(tipoTramitacion.getUrl().getTraduccion(idioma));
                }
            }
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(ProcedimientoBaseDTO dto) throws RecursoNoEncontradoException {
        JProcedimientoWorkflow jProcWF = procedimientoRepository.getWF(dto.getCodigo(), dto.getWorkflow().getValor());
        this.updateWF(dto, jProcWF);
    }

    private void updateWF(ProcedimientoBaseDTO dto, JProcedimientoWorkflow jProcWF) throws RecursoNoEncontradoException {
        mergear(jProcWF, dto);
        mergearTraducciones(jProcWF, dto);
        procedimientoRepository.updateWF(jProcWF);
        procedimientoRepository.mergeMateriaSIAProcWF(jProcWF.getCodigo(), dto.getMateriasGridSIA());
        procedimientoRepository.mergePublicoObjetivoProcWF(jProcWF.getCodigo(), dto.getTiposPubObjEntGrid());
        procedimientoRepository.mergeNormativaProcWF(jProcWF.getCodigo(), dto.getNormativas());
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        if (dto instanceof ProcedimientoDTO) {
            List<ProcedimientoTramiteDTO> tramites = ((ProcedimientoDTO) dto).getTramites();
            procedimientoRepository.mergeTramitesProcWF(jProcWF.getCodigo(), tramites, ruta);
        }
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentos() == null ? null : jProcWF.getListaDocumentos().getCodigo(), false, dto.getDocumentos(), ruta);
        procedimientoRepository.mergeDocumentos(jProcWF.getCodigo(), jProcWF.getListaDocumentosLOPD() == null ? null : jProcWF.getListaDocumentosLOPD().getCodigo(), true, dto.getDocumentosLOPD(), ruta);
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
        if (dto.getLopdDerechos() != null) {
            traduccion.setLopdDerechos(dto.getLopdDerechos().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getLopdDestinatario() != null) {
            traduccion.setLopdDestinatario(dto.getLopdDestinatario().getTraduccion(traduccion.getIdioma()));
        }
        if (dto.getLopdFinalidad() != null) {
            traduccion.setLopdFinalidad(dto.getLopdFinalidad().getTraduccion(traduccion.getIdioma()));
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteWF(Long idWF) throws RecursoNoEncontradoException {
        procedimientoRepository.deleteWF(idWF);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long generarModificacion(Long codigoWFPub) {
        ProcedimientoBaseDTO procPublicado = getProcedimientoDTOByCodigoWF(codigoWFPub);
        ProcedimientoBaseDTO procModificar = limpiar(procPublicado);
        procModificar.setEstado(TypeProcedimientoEstado.MODIFICACION);
        procModificar.setWorkflow(TypeProcedimientoWorfklow.MODIFICACION);
        this.create(procModificar);
        return procModificar.getCodigoWF();
    }

    /**
     * Método para quitar los códigos de cualquier dato que tenga.
     */
    public ProcedimientoBaseDTO limpiar(ProcedimientoBaseDTO proc) {
        proc.setCodigoWF(null);
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        //this.setCodigo(); El código del procedimiento se queda, no se altera
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

        //Normativas y publico objetivo, no hace falta tocar nada
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcedimientoDTO findProcedimientoById(Long codigoWF) {
        return (ProcedimientoDTO) getProcedimientoDTOByCodigoWF(codigoWF);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ServicioDTO findServicioById(Long codigoWF) {
        return (ServicioDTO) getProcedimientoDTOByCodigoWF(codigoWF);
    }

    private ProcedimientoBaseDTO getProcedimientoDTOByCodigoWF(Long codigoWF) {
        JProcedimientoWorkflow jprocWF = procedimientoRepository.getWFByCodigoWF(codigoWF);
        JProcedimiento jproc = jprocWF.getProcedimiento();

        ProcedimientoBaseDTO proc = createDTO(jproc);

        //JProcedimientoWorkflow jprocWF = procedimientoRepository.getWF(id, Constantes.PROCEDIMIENTO_ENMODIFICACION);
        proc.setCodigoWF(jprocWF.getCodigo());
        proc.setFechaPublicacion(jprocWF.getFechaPublicacion());
        proc.setFechaCaducidad(jprocWF.getFechaCaducidad());
        proc.setDirElectronica(jprocWF.getResponsableEmail());
        proc.setWorkflow(TypeProcedimientoWorfklow.fromBoolean(jprocWF.getWorkflow()));
        proc.setEstado(TypeProcedimientoEstado.fromString(jprocWF.getEstado()));
        proc.setMensajes(jproc.getMensajes());
        proc.setTaxa(jprocWF.getTieneTasa());
        proc.setResponsable(jprocWF.getResponsableNombre());
        proc.setLopdResponsable(jprocWF.getLopdResponsable());
        proc.setComun(jprocWF.isComun());
        //proc.setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
        //proc.setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
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

        //proc.setComun(jprocWF.isComun());
        Literal nombreProcedimientoWorkFlow = new Literal();
        Literal requisitos = new Literal();
        Literal datosPersonalesDestinatario = new Literal();
        Literal detDatosPersonalesFinalidad = new Literal();
        Literal lopdFinalidad = new Literal();
        Literal lopdDestinatario = new Literal();
        Literal lopdDerechos = new Literal();
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
                lopdFinalidad.add(new Traduccion(trad.getIdioma(), trad.getLopdFinalidad()));
                lopdDestinatario.add(new Traduccion(trad.getIdioma(), trad.getLopdDestinatario()));
                lopdDerechos.add(new Traduccion(trad.getIdioma(), trad.getLopdDerechos()));
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
        proc.setLopdFinalidad(lopdFinalidad);
        proc.setLopdDestinatario(lopdDestinatario);
        proc.setLopdDerechos(lopdDerechos);
        proc.setObjeto(objeto);
        proc.setDestinatarios(destinatarios);
        proc.setTerminoResolucion(terminoResolucion);
        proc.setObservaciones(observaciones);
        //proc.setLopdInfoAdicional(lopdInfoAdicional);
        proc.setMateriasGridSIA(procedimientoRepository.getMateriaGridSIAByWF(proc.getCodigoWF()));
        proc.setTiposPubObjEntGrid(procedimientoRepository.getTipoPubObjEntByWF(proc.getCodigoWF()));
        proc.setNormativas(procedimientoRepository.getNormativasByWF(proc.getCodigoWF()));
        proc.setDocumentos(procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentos()));
        proc.setDocumentosLOPD(procedimientoRepository.getDocumentosByListaDocumentos(jprocWF.getListaDocumentosLOPD()));
        if (proc instanceof ProcedimientoDTO) {
            ((ProcedimientoDTO) proc).setTramites(procedimientoRepository.getTramitesByWF(proc.getCodigoWF()));
            ((ProcedimientoDTO) proc).setHabilitadoApoderado(jprocWF.isHabilitadoApoderado());
            ((ProcedimientoDTO) proc).setHabilitadoFuncionario(jprocWF.getHabilitadoFuncionario());
        }
        if (proc instanceof ServicioDTO) {
            ((ServicioDTO) proc).setTramitElectronica(jprocWF.isTramitElectronica());
            ((ServicioDTO) proc).setTramitPresencial(jprocWF.isTramitPresencial());
            ((ServicioDTO) proc).setTramitTelefonica(jprocWF.isTramitTelefonica());
            if (jprocWF.getTramiteElectronico() != null) {
                TipoTramitacionDTO tipo = tipoTramitacionConverter.createDTO(jprocWF.getTramiteElectronico());
                if (jprocWF.getTramiteElectronico().isPlantilla()) {
                    ((ServicioDTO) proc).setPlantillaSel(tipo);
                    ((ServicioDTO) proc).setTipoTramitacion(new TipoTramitacionDTO());
                } else {
                    ((ServicioDTO) proc).setTipoTramitacion(tipo);
                }
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
    private void crearAuditoria(final ProcedimientoDTO procedimientoAntiguo, final ProcedimientoDTO procedimientoNuevo) {

        final ProcedimientoAuditoria procedimientoAuditoria = new ProcedimientoAuditoria();
        List<AuditoriaCambio> cambios = new ArrayList<>();
        AuditoriaCambio cambio = null;
        final AuditoriaDTO valores = new AuditoriaDTO();

        cambio = AuditoriaUtil.auditarLiteral(procedimientoAntiguo == null ? null :
                        procedimientoAntiguo.getNombreProcedimientoWorkFlow(), procedimientoNuevo.getNombreProcedimientoWorkFlow(), Constantes.PERSONA_IDENTIFICADOR,
                AuditoriaIdioma.NO_IDIOMA);
        if (cambio != null) {
            cambios.add(cambio);
        }

        valores.setCambios(cambios);
        // Crea el objeto de auditoria
        procedimientoAuditoria.setFechaAuditoria(new Date());
        procedimientoAuditoria.setUsuarioAuditoria(procedimientoNuevo.getUsuarioAuditoria());
        procedimientoAuditoria.setProcedimientoDTO(procedimientoNuevo);

        String auditoriaJson;
        try {
            auditoriaJson = JSONUtil.toJSON(valores);
            procedimientoAuditoria.setValoresAnteriores(auditoriaJson);
            //auditoriaRepository.guardarAuditoria(procedimientoAuditoria, JProcedimientoAuditoria.class);
        } catch (final JSONUtilException e) {
            throw new AuditoriaException(e);
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
        //;jproc.getCodigoDir3SIA();
        dato.setCodigoSIA(jproc.getCodigoSIA());
        dato.setEstadoSIA(jproc.getEstadoSIA());
        dato.setMensajes(jproc.getMensajes());
        if (jproc.getSiaFecha() != null) {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            dato.setFechaSIA(Date.from(jproc.getSiaFecha().atStartOfDay(defaultZoneId).toInstant()));
        }
        dato.setTipo(jproc.getTipo());
        return dato;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<ProcedimientoGridDTO> findProcedimientosByFiltro(ProcedimientoFiltro filtro) {
        List<ProcedimientoGridDTO> items = procedimientoRepository.findProcedimientosPagedByFiltro(filtro);
        long total = procedimientoRepository.countByFiltro(filtro);
        return new Pagina<>(items, total);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<ServicioGridDTO> findServiciosByFiltro(ProcedimientoFiltro filtro) {
        List<ServicioGridDTO> items = procedimientoRepository.findServiciosPagedByFiltro(filtro);
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

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void guardarFlujo(ProcedimientoBaseDTO data, TypeProcedimientoEstado estadoDestino, String mensajes) {

        //Primero borramos el wf destino (si es de destinto wf)
        if (TypeProcedimientoEstado.distintoWorkflow(data.getEstado(), estadoDestino)) {
            //Borramos el wf destino
            procedimientoRepository.deleteWF(data.getCodigo(), estadoDestino.getWorkflowSegunEstado().getValor());
        }

        //Segundo actualizamos el dato
        JProcedimientoWorkflow jProcWF = procedimientoRepository.getWFByCodigoWF(data.getCodigoWF());
        data.setEstado(estadoDestino);
        data.setWorkflow(estadoDestino.getWorkflowSegunEstado());
        this.updateWF(data, jProcWF);

        //Actualizamos los mensajes de procedimientos
        procedimientoRepository.actualizarMensajes(data.getCodigo(), mensajes);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarMensajes(Long idProc, String mensajes) {
        procedimientoRepository.actualizarMensajes(idProc, mensajes);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long getCodigoByWF(Long codigo, boolean valor) {
        return procedimientoRepository.getCodigoByWF(codigo, valor);
    }

}
