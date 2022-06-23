package es.caib.rolsac2.persistence.converter;

/**
 * Interfície per representar un conversor entre un Entity i un DTO
 *
 * @param <E> classe del Entity
 * @param <D> classe del DTO
 * @author areus
 */
public interface Converter<E, D> {

    D createDTO(E entity);

    E createEntity(D dto);

    void mergeEntity(E entity, D dto);
}
