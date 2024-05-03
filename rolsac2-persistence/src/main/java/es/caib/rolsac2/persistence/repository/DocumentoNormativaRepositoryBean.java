package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.DocumentoNormativaConverter;
import es.caib.rolsac2.persistence.model.JDocumentoNormativa;
import es.caib.rolsac2.persistence.model.JDocumentoNormativaTraduccion;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.service.model.DocumentoNormativaDTO;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.filtro.DocumentoNormativaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementación del repositorio de un documento de normativa
 *
 * @author Indra
 */
@Stateless
@Local(DocumentoNormativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DocumentoNormativaRepositoryBean extends AbstractCrudRepository<JDocumentoNormativa, Long> implements DocumentoNormativaRepository {

    @Inject
    private FicheroExternoRepository ficheroExternoRepository;

    @Inject
    private DocumentoNormativaConverter converter;

    protected DocumentoNormativaRepositoryBean() {
        super(JDocumentoNormativa.class);
    }

    @Override
    public long countByFiltro(DocumentoNormativaFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }


    @Override
    public List<JDocumentoNormativa> findDocumentosRelacionados(Long idNormativa) {
        TypedQuery<JDocumentoNormativa> query = entityManager.createNamedQuery(JDocumentoNormativa.FIND_BY_NORMATIVA, JDocumentoNormativa.class);
        query.setParameter("codigo", idNormativa);
        List<JDocumentoNormativa> result = query.getResultList();
        return result;
    }


    @Override
    public List<DocumentoNormativaDTO> findPagedByFiltroRest(DocumentoNormativaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JDocumentoNormativa> jentidades = query.getResultList();
        List<DocumentoNormativaDTO> entidades = new ArrayList<>();
        if (jentidades != null) {
            for (JDocumentoNormativa jentidad : jentidades) {
                DocumentoNormativaDTO entidad = converter.createDTO(jentidad);

                entidades.add(entidad);
            }
        }
        return entidades;
    }

    @Override
    public void actualizarDocumentacion(Long codigo, List<DocumentoNormativaDTO> documentosNormativa, String path) {

        List<JDocumentoNormativa> documentosActuales;
        if (codigo == null) {
            documentosActuales = new ArrayList<>();
        } else {
            documentosActuales = findDocumentosRelacionados(codigo);
        }

        //Borramos los documentos que se han quitado
        List<JDocumentoNormativa> documentosABorrar = new ArrayList<>();
        for (JDocumentoNormativa documento : documentosActuales) {
            boolean encontrado = false;
            for (DocumentoNormativaDTO documentoDTO : documentosNormativa) {
                if (documentoDTO.getCodigo() != null && documento.getCodigo().equals(documentoDTO.getCodigo())) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                documentosABorrar.add(documento);
            }
        }
        for (JDocumentoNormativa documento : documentosABorrar) {
            for (JDocumentoNormativaTraduccion trad : documento.getTraducciones()) {
                if (trad.getDocumento() != null && trad.getDocumento().getCodigo() != null) {
                    ficheroExternoRepository.deleteFicheroExterno(trad.getDocumento().getCodigo());
                }
            }
            entityManager.remove(documento);
        }

        //Por cada documento del dto, o bien se actualiza o bien se crea
        for (DocumentoNormativaDTO documentoDTO : documentosNormativa) {
            if (documentoDTO.getCodigo() == null) {
                JDocumentoNormativa jDocumentoNormativa = converter.createEntity(documentoDTO);
                jDocumentoNormativa.setNormativa(getNormativa(codigo));
                entityManager.persist(jDocumentoNormativa);
                if (jDocumentoNormativa.getTraducciones() != null) {
                    for (JDocumentoNormativaTraduccion trad : jDocumentoNormativa.getTraducciones()) {
                        if (trad.getDocumento() != null && trad.getDocumento().getCodigo() != null) {
                            ficheroExternoRepository.persistFicheroExterno(trad.getDocumento().getCodigo(), jDocumentoNormativa.getNormativa().getCodigo(), path);
                        }
                    }
                }
            } else {
                JDocumentoNormativa jdocumento = entityManager.find(JDocumentoNormativa.class, documentoDTO.getCodigo());
                if (jdocumento == null) {
                    throw new IllegalArgumentException("No se ha encontrado el documento con código " + documentoDTO.getCodigo());
                }
                List<String> idiomas = documentoDTO.getTitulo().getIdiomas();
                for (String idioma : idiomas) {
                    JDocumentoNormativaTraduccion jtraduccion = getTraduccion(jdocumento, idioma);
                    if (jtraduccion == null) {
                        jtraduccion = new JDocumentoNormativaTraduccion();
                        jtraduccion.setIdioma(idioma);
                        if (documentoDTO.getDocumentos() != null && documentoDTO.getDocumentos().getTraduccion(idioma) != null) {
                            Long idFicheroExterno = documentoDTO.getDocumentos().getTraduccion(idioma).getCodigo();
                            JFicheroExterno jFicheroExterno = entityManager.find(JFicheroExterno.class, idFicheroExterno);
                            jtraduccion.setDocumento(jFicheroExterno);
                            ficheroExternoRepository.persistFicheroExterno(idFicheroExterno, jdocumento.getNormativa().getCodigo(), path);
                        }
                        jtraduccion.setTitulo(documentoDTO.getTitulo().getTraduccion(idioma));
                        jtraduccion.setDescripcion(documentoDTO.getDescripcion().getTraduccion(idioma));
                        entityManager.persist(jtraduccion);

                    } else {
                        jtraduccion.setTitulo(documentoDTO.getTitulo().getTraduccion(idioma));
                        jtraduccion.setDescripcion(documentoDTO.getDescripcion().getTraduccion(idioma));
                        FicheroDTO doc = null;
                        if (documentoDTO.getDocumentos() != null) {
                            doc = documentoDTO.getDocumentos().getTraduccion(idioma);
                        }

                        //Casos en los que hay que persistir fichero
                        //Caso 1. La traducción no tiene documento y ahora si
                        if (jtraduccion.getDocumento() == null && doc != null) {
                            Long idFicheroExterno = doc.getCodigo();
                            JFicheroExterno jFicheroExterno = entityManager.find(JFicheroExterno.class, idFicheroExterno);
                            jtraduccion.setDocumento(jFicheroExterno);
                            ficheroExternoRepository.persistFicheroExterno(idFicheroExterno, jdocumento.getNormativa().getCodigo(), path);
                        }

                        //Caso 2. La traducción tiene documento y ahora no
                        if (jtraduccion.getDocumento() != null && doc == null) {
                            ficheroExternoRepository.deleteFicheroExterno(jtraduccion.getDocumento().getCodigo());
                            jtraduccion.setDocumento(null);
                        }

                        //Caso 3. La traducción tiene documento y ahora tiene otro
                        if (jtraduccion.getDocumento() != null && doc != null && !jtraduccion.getDocumento().getCodigo().equals(doc.getCodigo())) {
                            ficheroExternoRepository.deleteFicheroExterno(jtraduccion.getDocumento().getCodigo());
                            Long idFicheroExterno = doc.getCodigo();
                            JFicheroExterno jFicheroExterno = entityManager.find(JFicheroExterno.class, idFicheroExterno);
                            jtraduccion.setDocumento(jFicheroExterno);
                            ficheroExternoRepository.persistFicheroExterno(idFicheroExterno, jdocumento.getNormativa().getCodigo(), path);
                        }

                        entityManager.merge(jtraduccion);
                    }
                }
            }
        }
    }

    private JDocumentoNormativaTraduccion getTraduccion(JDocumentoNormativa jdocumento, String idioma) {
        for (JDocumentoNormativaTraduccion trad : jdocumento.getTraducciones()) {
            if (trad.getIdioma().equals(idioma)) {
                return trad;
            }
        }
        return null;
    }

    private JNormativa getNormativa(Long codigo) {
        return entityManager.find(JNormativa.class, codigo);
    }

    private Query getQuery(boolean isTotal, DocumentoNormativaFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("select count(j) from JDocumentoNormativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j from JDocumentoNormativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j from JDocumentoNormativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(t.titulo) LIKE :filtro OR LOWER(t.url) LIKE :filtro OR LOWER(t.descripcion) LIKE :filtro ");
        }

        if (filtro.isRellenoNormativa()) {
            sql.append(" and (j.normativa.codigo = :normativa) ");
        }

        if (filtro.isRellenoDocumento()) {
            sql.append(" and (t.documento.codigo = :documento)");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo = :codigo ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        if (filtro.isRellenoNormativa()) {
            query.setParameter("normativa", filtro.getNormativa().getCodigo());
        }

        if (filtro.isRellenoDocumento()) {
            query.setParameter("documento", filtro.getDocumento().getCodigo());
        }

        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        return query;
    }


}
