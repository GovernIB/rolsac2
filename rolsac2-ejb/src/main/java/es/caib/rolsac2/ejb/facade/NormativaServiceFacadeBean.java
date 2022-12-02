package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.ejb.interceptor.ExceptionTranslate;
import es.caib.rolsac2.ejb.interceptor.Logged;
import es.caib.rolsac2.persistence.converter.DocumentoNormativaConverter;
import es.caib.rolsac2.persistence.converter.NormativaConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.repository.*;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
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
    private TipoBoletinRepository tipoBoletinRepository;

    @Inject
    private NormativaConverter converter;

    @Inject
    private BoletinOficialRepository boletinOficialRepository;

    @Inject
    private AfectacionRepository afectacionRepository;

    @Inject
    private DocumentoNormativaConverter documentoNormativaConverter;

    @Inject
    private DocumentoNormativaRepository documentoNormativaRepository;

    @Inject
    FicheroExternoRepository ficheroExternoRepository;

    @Inject
    private SystemServiceFacade systemServiceBean;

    @Inject
    private ProcedimientoRepository procedimientoRepository;

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long create(NormativaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }
        JNormativa jNormativa = converter.createEntity(dto);
        /*Añadimos lógica para los documentos*/
        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(dto.getCodigo());
        jNormativa.setDocumentosNormativa(jDocumentosNormativas);

        normativaRepository.create(jNormativa);
        return jNormativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void update(NormativaDTO dto) throws RecursoNoEncontradoException {
        JBoletinOficial jBoletinOficial = dto.getBoletinOficial() != null ? boletinOficialRepository.getReference(dto.getBoletinOficial().getCodigo()) : null;
        JTipoNormativa jTipoNormativa = dto.getTipoNormativa() != null ? tipoNormativaRepository.getReference(dto.getTipoNormativa().getCodigo()) : null;
        JEntidad jEntidad = entidadRepository.getReference(dto.getEntidad().getCodigo());
        JNormativa jNormativa = normativaRepository.getReference(dto.getCodigo());
        jNormativa.setBoletinOficial(jBoletinOficial);
        jNormativa.setTipoNormativa(jTipoNormativa);
        jNormativa.setEntidad(jEntidad);


        converter.mergeEntity(jNormativa, dto);

        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(dto.getCodigo());
        jNormativa.getDocumentosNormativa().clear();
        jNormativa.getDocumentosNormativa().addAll(jDocumentosNormativas);
        normativaRepository.update(jNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void delete(Long id) throws RecursoNoEncontradoException {
        JNormativa jNormativa = normativaRepository.getReference(id);
        normativaRepository.delete(jNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public NormativaDTO findById(Long id) {
        NormativaDTO normativaDTO = converter.createDTO(normativaRepository.findById(id));
        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(id);
        List<DocumentoNormativaDTO> documentosRelacionados = new ArrayList<>();
        for (JDocumentoNormativa doc : jDocumentosNormativas) {
            documentosRelacionados.add(documentoNormativaConverter.createDTO(doc));
        }
        normativaDTO.setDocumentosNormativa(documentosRelacionados);
        return normativaDTO;
    }


    //TODO: Falta revisar este método para ver si se ha de adaptar.
    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
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
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public int countByFiltro(NormativaFiltro filtro) {
        return (int) normativaRepository.countByFiltro(filtro);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<BoletinOficialDTO> findBoletinOficial() {
        try {
            List<BoletinOficialDTO> items = boletinOficialRepository.findBoletinOficial();
            return items;
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<AfectacionDTO> findAfectacion() {
        try {
            List<AfectacionDTO> items = afectacionRepository.findAfectacion();
            return items;
        } catch (Exception e) {
            LOG.error("Error: ", e);
            return new ArrayList<>();
        }
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public Long createDocumentoNormativa(DocumentoNormativaDTO dto) {
        if (dto.getCodigo() != null) {
            throw new DatoDuplicadoException(dto.getCodigo());
        }

        JDocumentoNormativa jDocumentoNormativa = documentoNormativaConverter.createEntity(dto);

        documentoNormativaRepository.create(jDocumentoNormativa);

        if (dto.getDocumentos() != null) {
            for (String idioma : dto.getDocumentos().getIdiomas()) {
                if (dto.getDocumentos().getTraduccion(idioma) != null) {
                    ficheroExternoRepository.persistFicheroExterno(dto.getNormativa().getCodigo(), dto.getNormativa().getCodigo(),
                            systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
                }
            }
        }

        return jDocumentoNormativa.getCodigo();
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void updateDocumentoNormativa(DocumentoNormativaDTO dto) {
        JDocumentoNormativa jDocumentoNormativa = documentoNormativaRepository.getReference(dto.getCodigo());
        if (dto.getDocumentos() != null) {
            for (String idioma : dto.getDocumentos().getIdiomas()) {
                if (dto.getDocumentos().getTraduccion(idioma) != null) {
                    ficheroExternoRepository.persistFicheroExterno(dto.getNormativa().getCodigo(), dto.getNormativa().getCodigo(),
                            systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS));
                }
            }
        }
        documentoNormativaConverter.mergeEntity(jDocumentoNormativa, dto);
        documentoNormativaRepository.update(jDocumentoNormativa);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public void deleteDocumentoNormativa(Long id) throws RecursoNoEncontradoException {
        JDocumentoNormativa jDocumentoNormativa = documentoNormativaRepository.getReference(id);
        documentoNormativaRepository.delete(jDocumentoNormativa);
    }


    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public DocumentoNormativaDTO findDocumentoNormativa(Long id) {
        return documentoNormativaConverter.createDTO(documentoNormativaRepository.findById(id));
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public List<DocumentoNormativaDTO> findDocumentosNormativa(Long idNormativa) {
        List<JDocumentoNormativa> jDocumentosNormativas = documentoNormativaRepository.findDocumentosRelacionados(idNormativa);
        List<DocumentoNormativaDTO> documentosRelacionados = new ArrayList<>();
        for (JDocumentoNormativa doc : jDocumentosNormativas) {
            documentosRelacionados.add(documentoNormativaConverter.createDTO(doc));
        }
        return documentosRelacionados;
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeProcedimientoConNormativa(Long codigo) {
        return procedimientoRepository.existeProcedimientosConNormativas(codigo);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeTipoNormativa(Long codigoTipoNor) {
        return normativaRepository.existeTipoNormativa(codigoTipoNor);
    }

    @Override
    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    public boolean existeBoletin(Long codigoBol) {
        return normativaRepository.existeBoletin(codigoBol);
    }

}
