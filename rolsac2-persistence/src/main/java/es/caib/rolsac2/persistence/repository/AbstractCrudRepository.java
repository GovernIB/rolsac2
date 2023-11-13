package es.caib.rolsac2.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Implementació bàsica d'un {@link CrudRepository}.
 *
 * @param <E>  Tipus de l'entitat.
 * @param <PK> Tipus de la clau primària de l'entitat.
 * @author Indra
 */
public abstract class AbstractCrudRepository<E, PK> implements CrudRepository<E, PK> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Guarda la classe del tipus d'entitat.
     */
    private final Class<E> entityClass;

    /**
     * Emmagatzema el tipus d'entitat.
     */
    protected AbstractCrudRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Long create(E entity) {
        entityManager.persist(entity);
        entityManager.flush();
        try {
            return (Long) entity.getClass().getDeclaredMethod("getCodigo").invoke(entity);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
        entityManager.flush();
    }

    @Override
    public E findById(PK id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public E getReference(PK id) {
        return entityManager.getReference(entityClass, id);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
