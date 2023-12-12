package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.ejb.facade.procesos.solr.CastUtil;
import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.DocumentoNormativaConverter;
import es.caib.rolsac2.persistence.converter.NormativaConverter;
import es.caib.rolsac2.persistence.converter.TipoAfectacionConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.DocumentoNormativaFiltro;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Logged
@ExceptionTranslate
@Stateless
@Local(NormativaServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class NormativaServiceFacadeBean implements NormativaServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(NormativaServiceFacadeBean.class);

    @Inject
    private NormativaRepository normativaRepository;

    @Inject
    private EntidadRepository entidadRepository;

    @Inject
    private TipoNormativaRepository tipoNormativaRepository;

    @Inject
    private NormativaConverter converter;

    @Inject
    private AfectacionRepository afectacionRepository;

    @Inject
    private DocumentoNormativaConverter documentoNormativaConverter;

    @Inject
    private DocumentoNormativaRepository documentoNormativaRepository;

    @Inject
    private SystemServiceFacade systemService;

    @Inject
    private FicheroExternoRepository ficheroExternoRepository;

    @Inject
    private SystemServiceFacade systemServiceBean;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Inject
    private IndexacionRepository indexacionRepository;

    @Inject
    private UnidadAdministrativaRepository unidadAdministrativaRepository;

    @Inject
    private TipoBoletinRepository tipoBoletinRepository;

    @Inject
    private TipoAfectacionConverter tipoAfectacionConverter;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(NormativaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JNormativa jNormativa = converter.createEntity(dto);

        /**
         * Asociación de afectaciones a la normativa
         */
        List<JAfectacion> afectaciones = new ArrayList<>();
        if (dto.getAfectaciones() != null) {
            for (AfectacionDTO afectacionDTO : dto.getAfectaciones()) {
                JAfectacion jAfectacion = new JAfectacion();
                jAfectacion.setTipoAfectacion(tipoAfectacionConverter.createEntity(afectacionDTO.getTipo()));
                jAfectacion.setNormativaAfectada(jNormativa);
                JNormativa jNormativaOrigen = normativaRepository.findById(afectacionDTO.getNormativaOrigen().getCodigo());
                jAfectacion.setNormativaOrigen(jNormativaOrigen);
                jNormativaOrigen.getAfectacionesOrigen().add(jAfectacion);
                afectaciones.add(jAfectacion);
            }
        }
        jNormativa.setAfectaciones(afectaciones);


        /**
         * Asociación para UAs. En caso de que se hayan asignado UAs a la normativa,
         * se recuperan las UAs añadidas y se añaden al modelo de Normativa.
         */
        Set<JUnidadAdministrativa> unidadesAdministrativas = new HashSet<>();
        if (dto.getUnidadesAdministrativas() != null) {
            JUnidadAdministrativa jUnidadAdministrativa;
            for (UnidadAdministrativaGridDTO ua : dto.getUnidadesAdministrativas()) {
                jUnidadAdministrativa = unidadAdministrativaRepository.getReference(ua.getCodigo());
                unidadesAdministrativas.add(jUnidadAdministrativa);
            }
        }
        jNormativa.setUnidadesAdministrativas(unidadesAdministrativas);

        normativaRepository.create(jNormativa);
        dto.setCodigo(jNormativa.getCodigo());

        /**
         * Como se pueden dar de alta documentos al crearse la normativa,
         * revisamos si se han dado de alta algunos y, en caso afirmativo,
         * persistimos los documentos creados y actualizamos la entidad Normativa.
         */
        if (dto.getDocumentosNormativa() != null) {
            List<JDocumentoNormativa> documentosNormativa = new ArrayList<>();
            for (DocumentoNormativaDTO documentoNormativaDTO : dto.getDocumentosNormativa()) {
                documentoNormativaDTO.setNormativa(dto);
                Long id = createDocumentoNormativa(documentoNormativaDTO);
                documentosNormativa.add(documentoNormativaRepository.findById(id));
            }
            jNormativa.setDocumentosNormativa(documentosNormativa);
            normativaRepository.update(jNormativa);
        }

        indexacionRepository.guardarIndexar(jNormativa.getCodigo(), TypeIndexacion.NORMATIVA, jNormativa.getEntidad().getCodigo(), 1);

        return jNormativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(NormativaDTO dto) throws RecursoNoEncontradoException {
        JTipoBoletin jBoletinOficial = dto.getBoletinOficial() != null ? tipoBoletinRepository.findById(dto.getBoletinOficial().getCodigo()) : null;
        JTipoNormativa jTipoNormativa = dto.getTipoNormativa() != null ? tipoNormativaRepository.getReference(dto.getTipoNormativa().getCodigo()) : null;
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JNormativa jNormativa = normativaRepository.getReference(dto.getCodigo());
        jNormativa.setBoletinOficial(jBoletinOficial);
        jNormativa.setTipoNormativa(jTipoNormativa);
        jNormativa.setEntidad(jEntidad);

        /**
         * Actualizamos la asociación de las afectaciones
         */
        List<JAfectacion> jAfectaciones = afectacionRepository.findAfectacionesRelacionadas(jNormativa.getCodigo());
        List<JAfectacion> jAfectacionesOrigen = afectacionRepository.findAfectacionesOrigen(jNormativa.getCodigo());
        jNormativa.getAfectaciones().clear();
        jNormativa.getAfectaciones().addAll(jAfectaciones);
        jNormativa.getAfectacionesOrigen().clear();
        jNormativa.getAfectacionesOrigen().addAll(jAfectacionesOrigen);

        /**
         * Asociación para UAs. En caso de que se hayan asignado UAs a la normativa,
         * se recuperan las UAs añadidas y se añaden al modelo de Normativa.
         */
        Set<JUnidadAdministrativa> unidadesAdministrativas = new HashSet<>();
        if (dto.getUnidadesAdministrativas() != null) {
            JUnidadAdministrativa jUnidadAdministrativa;
            for (UnidadAdministrativaGridDTO ua : dto.getUnidadesAdministrativas()) {
                jUnidadAdministrativa = unidadAdministrativaRepository.getReference(ua.getCodigo());
                unidadesAdministrativas.add(jUnidadAdministrativa);
            }
        }

        jNormativa.getUnidadesAdministrativas().clear();
        jNormativa.getUnidadesAdministrativas().addAll(unidadesAdministrativas);


        converter.mergeEntity(jNormativa, dto);

        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(dto.getCodigo());
        jNormativa.getDocumentosNormativa().clear();
        jNormativa.getDocumentosNormativa().addAll(jDocumentosNormativas);
        normativaRepository.update(jNormativa);

        indexacionRepository.guardarIndexar(jNormativa.getCodigo(), TypeIndexacion.NORMATIVA, jNormativa.getEntidad().getCodigo(), 1);

    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JNormativa jNormativa = normativaRepository.getReference(id);
        List<JUnidadAdministrativa> jUnidadesAdministrativas = unidadAdministrativaRepository.getUnidadesAdministrativaByNormativa(id);
        for (JUnidadAdministrativa jUnidadAdministrativa : jUnidadesAdministrativas) {
            jUnidadAdministrativa.getUsuarios().remove(jNormativa);
            unidadAdministrativaRepository.update(jUnidadAdministrativa);
        }

        //La accion 2 es para borrar
        indexacionRepository.guardarIndexar(jNormativa.getCodigo(), TypeIndexacion.NORMATIVA, jNormativa.getEntidad().getCodigo(), 2);

        normativaRepository.delete(jNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public NormativaDTO findById(Long id) {
        JNormativa jNormativa = normativaRepository.findById(id);
        NormativaDTO normativaDTO = converter.createDTO(jNormativa);
        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(id);
        List<DocumentoNormativaDTO> documentosRelacionados = new ArrayList<>();
        if (jDocumentosNormativas != null && normativaDTO != null) {
            for (JDocumentoNormativa doc : jDocumentosNormativas) {
                documentosRelacionados.add(documentoNormativaConverter.createDTO(doc));
            }
            normativaDTO.setDocumentosNormativa(documentosRelacionados);
        }

        List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
        if (jNormativa != null && jNormativa.getUnidadesAdministrativas() != null && normativaDTO != null) {
            for (JUnidadAdministrativa jUnidadAdministrativa : jNormativa.getUnidadesAdministrativas()) {
                UnidadAdministrativaGridDTO unidadAdministrativa = unidadAdministrativaRepository.modelToGridDTO(jUnidadAdministrativa);
                unidadesAdministrativas.add(unidadAdministrativa);
            }
            normativaDTO.setUnidadesAdministrativas(unidadesAdministrativas);
        }

        return normativaDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<NormativaDTO> findByEntidad(Long idEntidad) {
        List<JNormativa> jNormativas = normativaRepository.findByEntidad(idEntidad);
        List<NormativaDTO> normativas = new ArrayList<>();
        jNormativas.forEach(nor -> normativas.add(converter.createDTO(nor)));
        return normativas;
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<NormativaGridDTO> findByFiltro(NormativaFiltro filtro) {
        try {
            List<NormativaGridDTO> items = normativaRepository.findPagedByFiltro(filtro);
            long total = normativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<NormativaGridDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<NormativaDTO> findExportByFiltro(NormativaFiltro filtro, ExportarDatos exportarDatos) {
        try {
            NormativaFiltro filtroClonado = filtro.clone();
            if (exportarDatos.getTodosLosDatos()) {
                filtroClonado.setPaginaFirst(0);
                filtroClonado.setPaginaTamanyo(10000);
            }
            return normativaRepository.findByFiltro(filtroClonado);
        } catch (Exception e) {
            LOG.error("Error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(NormativaFiltro filtro) {
        return (int) normativaRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countByEntidad(Long entidadId) {
        return normativaRepository.countByEntidad(entidadId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countAll() {
        return normativaRepository.countAll();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long countByUa(Long uaId) {
        return normativaRepository.countByUa(uaId);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AfectacionDTO> findAfectacionesByNormativa(Long idNormativa) {
        List<JAfectacion> afectaciones = afectacionRepository.findAfectacionesRelacionadas(idNormativa);
        List<AfectacionDTO> afectacionesDTO = new ArrayList<>();
        for (JAfectacion afectacion : afectaciones) {
            AfectacionDTO afectacionDTO = new AfectacionDTO();
            afectacionDTO.setCodigo(afectacion.getCodigo());
            afectacionDTO.setNormativaOrigen(converter.createDTO(afectacion.getNormativaOrigen()).convertDTOtoGridDTO());
            afectacionDTO.setNormativaAfectada(converter.createDTO(afectacion.getNormativaAfectada()).convertDTOtoGridDTO());
            afectacionDTO.setTipo(tipoAfectacionConverter.createDTO(afectacion.getTipoAfectacion()));
            afectacionesDTO.add(afectacionDTO);
        }
        afectaciones = afectacionRepository.findAfectacionesOrigen(idNormativa);
        for (JAfectacion afectacion : afectaciones) {
            AfectacionDTO afectacionDTO = new AfectacionDTO();
            afectacionDTO.setCodigo(afectacion.getCodigo());
            afectacionDTO.setNormativaOrigen(converter.createDTO(afectacion.getNormativaOrigen()).convertDTOtoGridDTO());
            afectacionDTO.setNormativaAfectada(converter.createDTO(afectacion.getNormativaAfectada()).convertDTOtoGridDTO());
            afectacionDTO.setTipo(tipoAfectacionConverter.createDTO(afectacion.getTipoAfectacion()));
            afectacionesDTO.add(afectacionDTO);
        }
        return afectacionesDTO;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createDocumentoNormativa(DocumentoNormativaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JDocumentoNormativa jDocumentoNormativa = documentoNormativaConverter.createEntity(dto);

        documentoNormativaRepository.create(jDocumentoNormativa);

        if (dto.getDocumentos() != null) {
            for (String idioma : dto.getDocumentos().getIdiomas()) {
                if (dto.getDocumentos().getTraduccion(idioma) != null) {
                    ficheroExternoRepository.persistFicheroExterno(jDocumentoNormativa.getCodigo(), dto.getNormativa().getCodigo(), systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
                }
            }
        }

        return jDocumentoNormativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateDocumentoNormativa(DocumentoNormativaDTO dto) {
        JDocumentoNormativa jDocumentoNormativa = documentoNormativaRepository.getReference(dto.getCodigo());
        if (dto.getDocumentos() != null) {
            for (String idioma : dto.getDocumentos().getIdiomas()) {
                if (dto.getDocumentos().getTraduccion(idioma) != null) {
                    ficheroExternoRepository.persistFicheroExterno(jDocumentoNormativa.getCodigo(), dto.getNormativa().getCodigo(), systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
                }
            }
        }
        documentoNormativaConverter.mergeEntity(jDocumentoNormativa, dto);
        documentoNormativaRepository.update(jDocumentoNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteDocumentoNormativa(Long id) throws RecursoNoEncontradoException {
        JDocumentoNormativa jDocumentoNormativa = documentoNormativaRepository.getReference(id);
        documentoNormativaRepository.delete(jDocumentoNormativa);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public DocumentoNormativaDTO findDocumentoNormativa(Long id) {
        return documentoNormativaConverter.createDTO(documentoNormativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<DocumentoNormativaDTO> findDocumentosNormativa(Long idNormativa) {
        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(idNormativa);
        List<DocumentoNormativaDTO> documentosRelacionados = new ArrayList<>();
        for (JDocumentoNormativa doc : jDocumentosNormativas) {
            documentosRelacionados.add(documentoNormativaConverter.createDTO(doc));
        }
        return documentosRelacionados;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConNormativa(Long codigo) {
        return procedimientoRepository.existeProcedimientosConNormativas(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeTipoNormativa(Long codigoTipoNor) {
        return normativaRepository.existeTipoNormativa(codigoTipoNor);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeBoletin(Long codigoBol) {
        return normativaRepository.existeBoletin(codigoBol);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcedimientoNormativaDTO> listarProcedimientosByNormativa(Long idNormativa) {
        return procedimientoRepository.getProcedimientosByNormativa(idNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<ProcedimientoNormativaDTO> listarServiciosByNormativa(Long idNormativa) {
        return procedimientoRepository.getServiciosByNormativa(idNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createAfectacion(AfectacionDTO afectacionDTO) {
        if (afectacionDTO != null) {
            JAfectacion jAfectacion = new JAfectacion();
            jAfectacion.setTipoAfectacion(tipoAfectacionConverter.createEntity(afectacionDTO.getTipo()));
            jAfectacion.setNormativaOrigen(normativaRepository.findById(afectacionDTO.getNormativaOrigen().getCodigo()));
            jAfectacion.setNormativaAfectada(normativaRepository.findById(afectacionDTO.getNormativaAfectada().getCodigo()));
            afectacionRepository.create(jAfectacion);
            return jAfectacion.getCodigo();
        }
        return null;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateAfectacion(AfectacionDTO afectacionDTO) {
        JAfectacion jAfectacion = afectacionRepository.findById(afectacionDTO.getCodigo());
        if (afectacionDTO.getTipo().getCodigo() != jAfectacion.getTipoAfectacion().getCodigo()) {
            jAfectacion.setTipoAfectacion(tipoAfectacionConverter.createEntity(afectacionDTO.getTipo()));
        }
        if (afectacionDTO.getNormativaOrigen().getCodigo() != afectacionDTO.getNormativaAfectada().getCodigo()) {
            jAfectacion.setNormativaOrigen(normativaRepository.findById(afectacionDTO.getNormativaOrigen().getCodigo()));
        }
        if (afectacionDTO.getNormativaAfectada().getCodigo() != jAfectacion.getNormativaAfectada().getCodigo()) {
            jAfectacion.setNormativaAfectada(normativaRepository.findById(afectacionDTO.getNormativaAfectada().getCodigo()));
        }
        afectacionRepository.update(jAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteAfectacion(Long idAfectacion) {
        JAfectacion jAfectacion = afectacionRepository.findById(idAfectacion);
        afectacionRepository.delete(jAfectacion);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public ProcedimientoSolrDTO findDataIndexacionNormById(Long idNormativa) {
        NormativaDTO normativaDTO = (NormativaDTO) this.findById(idNormativa);
        List<PathUA> pathUAs = new ArrayList<>();
        if (normativaDTO.getUnidadesAdministrativas() != null) {
            for (UnidadAdministrativaGridDTO ua : normativaDTO.getUnidadesAdministrativas()) {
                PathUA pathUA = unidadAdministrativaRepository.getPath(ua);
                pathUAs.add(pathUA);
            }
        }
        DataIndexacion dataIndexacion = CastUtil.getDataIndexacion(normativaDTO, pathUAs);
        ProcedimientoSolrDTO data = new ProcedimientoSolrDTO();
        data.setDataIndexacion(dataIndexacion);
        data.setNormativaDTO(normativaDTO);
        data.setPathUAs(pathUAs);
        return data;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public IndexFile findDataIndexacionDocNormById(NormativaDTO normativaDTO, DocumentoNormativaDTO doc, DocumentoTraduccion docTraduccion, List<PathUA> pathUAs) {
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        FicheroDTO ficheroDTO = ficheroExternoRepository.getContentById(docTraduccion.getFicheroDTO().getCodigo(), ruta);
        return CastUtil.getDataIndexacion(normativaDTO, doc, docTraduccion, ficheroDTO, docTraduccion.getIdioma(), pathUAs);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<IndexacionDTO> getNormativasParaIndexacion(Long idEntidad) {
        return normativaRepository.getNormativasParaIndexacion(idEntidad);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void actualizarSolr(IndexacionDTO indexacionDTO, ResultadoAccion resultadoAccion) {
        indexacionRepository.actualizarDato(indexacionDTO, resultadoAccion);
        //procedimientoRepository.actualizarSolr(indexacionDTO, resultadoAccion);
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR, TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR, TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Pagina<NormativaDTO> findByFiltroRest(NormativaFiltro filtro) {
        try {
            List<NormativaDTO> items = normativaRepository.findPagedByFiltroRest(filtro);
            long total = normativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<NormativaDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public Pagina<DocumentoNormativaDTO> findDocumentoNormativaByFiltroRest(DocumentoNormativaFiltro filtro) {
        try {
            List<DocumentoNormativaDTO> items = documentoNormativaRepository.findPagedByFiltroRest(filtro);
            long total = documentoNormativaRepository.countByFiltro(filtro);
            return new Pagina<>(items, total);
        } catch (Exception e) {
            LOG.error("Error", e);
            List<DocumentoNormativaDTO> items = new ArrayList<>();
            long total = items.size();
            return new Pagina<>(items, total);
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.RESTAPI_VALOR})
    public String obtenerIdiomaEntidad(Long codigo) {
        return normativaRepository.obtenerIdiomaEntidad(codigo);
    }


    /*******************************************************************************************************************
     * Funciones privadas del servicio
     *******************************************************************************************************************/

}
