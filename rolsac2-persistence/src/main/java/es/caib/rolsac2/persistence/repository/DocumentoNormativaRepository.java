package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JDocumentoNormativa;

import java.util.List;


/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface DocumentoNormativaRepository extends CrudRepository<JDocumentoNormativa, Long> {

    List<JDocumentoNormativa> findDocumentosRelacionados(Long idNormativa);

}
