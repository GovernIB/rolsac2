package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.persistence.model.traduccion.JTipoNormativaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoSexoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoSexo y TipoSexoDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoSexoConverter extends Converter<JTipoSexo, TipoSexoDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoSexoDTO createDTO(JTipoSexo entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoSexo,dto.getDescripcion()))")
    JTipoSexo createEntity(TipoSexoDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
///, ignore = true)
    void mergeEntity(@MappingTarget JTipoSexo entity, TipoSexoDTO dto);

    default List<JTipoSexoTraduccion> convierteLiteralToTraduccion(JTipoSexo jtipoSexo, Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jtipoSexo.getDescripcion() == null || jtipoSexo.getDescripcion().isEmpty()) {
            jtipoSexo.setDescripcion(JTipoSexoTraduccion.createInstance(idiomasRellenos));
            for (JTipoSexoTraduccion jtrad : jtipoSexo.getDescripcion()) {
                jtrad.setTipoSexo(jtipoSexo);
            }
        } else if(idiomasRellenos.size() >  jtipoSexo.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoSexoTraduccion> tradsAux = jtipoSexo.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoSexoTraduccion traduccion : jtipoSexo.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoSexoTraduccion trad = new JTipoSexoTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoSexo(jtipoSexo);
                tradsAux.add(trad);
            }
            jtipoSexo.setDescripcion(tradsAux);
        }
        for (JTipoSexoTraduccion traduccion : jtipoSexo.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoSexo.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoSexoTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoSexo().getCodigo()).findFirst().orElse(null));
            for (JTipoSexoTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }

        return resultado;
    }
}
