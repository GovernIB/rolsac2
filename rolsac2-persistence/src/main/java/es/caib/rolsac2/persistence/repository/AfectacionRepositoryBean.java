package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JAfectacion;
import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.service.model.AfectacionDTO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(AfectacionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AfectacionRepositoryBean extends AbstractCrudRepository<JAfectacion, Long> implements AfectacionRepository {

    protected AfectacionRepositoryBean() {
        super(JAfectacion.class);
    }

    @Override
    public List<JAfectacion> findAfectacionesRelacionadas(Long idNormativa) {
        TypedQuery<JAfectacion> query = entityManager.createNamedQuery(JAfectacion.FIND_BY_NORMATIVA_AFECTADA, JAfectacion.class);
        query.setParameter("codigo", idNormativa);
        List<JAfectacion> result = query.getResultList();
        return result;
    }

    @Override
    public List<JAfectacion> findAfectacionesOrigen(Long idNormativaOrigen) {
        TypedQuery<JAfectacion> query = entityManager.createNamedQuery(JAfectacion.FIND_BY_NORMATIVA_ORIGEN, JAfectacion.class);
        query.setParameter("codigo", idNormativaOrigen);
        List<JAfectacion> result = query.getResultList();
        return result;
    }

    @Override
    public List<JAfectacion> findAfectacionesAfectadas(Long idNormativaOrigen) {
        TypedQuery<JAfectacion> query = entityManager.createNamedQuery(JAfectacion.FIND_BY_NORMATIVA_AFECTADA, JAfectacion.class);
        query.setParameter("codigo", idNormativaOrigen);
        List<JAfectacion> result = query.getResultList();
        return result;
    }

    @Override
    public void actualizarAfectaciones(Long codigo, List<AfectacionDTO> afectaciones) {

        List<JAfectacion> afectacionesActuales;
        if (codigo == null) {
            afectacionesActuales = new ArrayList<>();
        } else {
            afectacionesActuales = findAfectacionesAfectadas(codigo);
        }

        //Borramos las afectaciones que se han quitado
        List<JAfectacion> afectacionesABorrar = new ArrayList<>();
        for (JAfectacion afectacion : afectacionesActuales) {
            boolean encontrado = false;
            for (AfectacionDTO afectacionDTO : afectaciones) {
                if (afectacionDTO.getCodigo() != null && afectacion.getCodigo().equals(afectacionDTO.getCodigo())) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                afectacionesABorrar.add(afectacion);
            }
        }
        for (JAfectacion afectacion : afectacionesABorrar) {
            entityManager.remove(afectacion);
        }

        JNormativa jNormativaAfectada = getNormativa(codigo);
        //Por cada afectación del dto, o bien se actualiza o bien se crea
        for (AfectacionDTO afectacionDTO : afectaciones) {
            if (afectacionDTO.getCodigo() == null) {
                JAfectacion jafectacion = new JAfectacion();
                jafectacion.setTipoAfectacion(getTipoAfectacion(afectacionDTO.getTipo().getCodigo()));
                jafectacion.setNormativaAfectada(jNormativaAfectada);//getNormativa(afectacionDTO.getNormativaAfectada().getCodigo()));
                jafectacion.setNormativaOrigen(getNormativa(afectacionDTO.getNormativaOrigen().getCodigo()));
                entityManager.persist(jafectacion);
            } else {
                for (JAfectacion afectacion : afectacionesActuales) {
                    if (afectacion.getCodigo().equals(afectacionDTO.getCodigo())) {
                        afectacion.setTipoAfectacion(getTipoAfectacion(afectacionDTO.getTipo().getCodigo()));
                        afectacion.setNormativaAfectada(getNormativa(afectacionDTO.getNormativaAfectada().getCodigo()));
                        afectacion.setNormativaOrigen(getNormativa(afectacionDTO.getNormativaOrigen().getCodigo()));
                        entityManager.merge(afectacion);
                        break;
                    }
                }
            }

        }
    }

    private JNormativa getNormativa(Long codigo) {
        return entityManager.find(JNormativa.class, codigo);
    }

    private JTipoAfectacion getTipoAfectacion(Long codigo) {
        return entityManager.find(JTipoAfectacion.class, codigo);
    }

}
