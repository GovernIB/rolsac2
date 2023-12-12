package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoPublicoObjetivo y  TipoPublicoObjetivoDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoPublicoObjetivoConverter extends Converter<JTipoPublicoObjetivo, TipoPublicoObjetivoDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoPublicoObjetivoDTO createDTO(JTipoPublicoObjetivo entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoPublicoObjetivo,dto.getDescripcion()))")
    JTipoPublicoObjetivo createEntity(TipoPublicoObjetivoDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JTipoPublicoObjetivo entity, TipoPublicoObjetivoDTO dto);

    default List<JTipoPublicoObjetivoTraduccion> convierteLiteralToTraduccion(JTipoPublicoObjetivo jTipoPublicoObjetivo, Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for (String idioma : descripcion.getIdiomas()) {
            if (descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }
        //Comprobamos si aún no se ha creado la entidad
        if (jTipoPublicoObjetivo.getDescripcion() == null || jTipoPublicoObjetivo.getDescripcion().isEmpty()) {
            jTipoPublicoObjetivo.setDescripcion(JTipoPublicoObjetivoTraduccion.createInstance(idiomasRellenos));
            for (JTipoPublicoObjetivoTraduccion jtrad : jTipoPublicoObjetivo.getDescripcion()) {
                jtrad.setTipoPublicoObjetivo(jTipoPublicoObjetivo);
            }
        } else if (idiomasRellenos.size() > jTipoPublicoObjetivo.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoPublicoObjetivoTraduccion> tradsAux = jTipoPublicoObjetivo.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoPublicoObjetivoTraduccion traduccion : jTipoPublicoObjetivo.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoPublicoObjetivoTraduccion trad = new JTipoPublicoObjetivoTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoPublicoObjetivo(jTipoPublicoObjetivo);
                tradsAux.add(trad);
            }
            jTipoPublicoObjetivo.setDescripcion(tradsAux);
        }
        for (JTipoPublicoObjetivoTraduccion traduccion : jTipoPublicoObjetivo.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoPublicoObjetivo.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoPublicoObjetivoTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoPublicoObjetivo().getCodigo()).findFirst().orElse(null));
            for (JTipoPublicoObjetivoTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }

        return resultado;
    }

    @Named("createTipPubDTOs")
    default List<TipoPublicoObjetivoDTO> createTipPubDTOs(List<JTipoPublicoObjetivo> entities) {
        List<TipoPublicoObjetivoDTO> dtos = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> dtos.add(createDTO(e)));
        }
        return dtos;
    }
}
