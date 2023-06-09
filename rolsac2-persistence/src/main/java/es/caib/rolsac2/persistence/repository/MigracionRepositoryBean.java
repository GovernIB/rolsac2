package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProceso;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Stateless
@Local(ProcesoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class MigracionRepositoryBean extends AbstractCrudRepository<JProceso, Long> implements MigracionRepository {


    protected MigracionRepositoryBean() {
        super(JProceso.class);
    }

    @Override
    public String ejecutarMetodo(String metodo, Long param1, Long param2) {

        String resultado = "";
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(metodo);
        query.registerStoredProcedureParameter("codigoUA", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("codigoEntidad", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("resultado", String.class, ParameterMode.INOUT);

        // set input parameter
        query.setParameter("codigoUA", param1);
        query.setParameter("codigoEntidad", param2);
        query.setParameter("resultado", resultado);

        // call the stored procedure and get the result
        query.execute();
        return (String) query.getOutputParameterValue("resultado");
    }


}


