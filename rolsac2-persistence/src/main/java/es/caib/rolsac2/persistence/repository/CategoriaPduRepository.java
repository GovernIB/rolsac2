package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JCategoriaPdu;

import java.util.List;

public interface CategoriaPduRepository extends CrudRepository<JCategoriaPdu, Long> {

    List<JCategoriaPdu> findAll();

}
