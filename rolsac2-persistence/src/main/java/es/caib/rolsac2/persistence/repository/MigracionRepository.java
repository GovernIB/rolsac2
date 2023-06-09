package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProceso;

public interface MigracionRepository extends CrudRepository<JProceso, Long> {

    public String ejecutarMetodo(String metodo, Long param1, Long param2);
}
