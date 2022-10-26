package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.traduccion.JPlatTramitElectronicaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaEdificioTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;

/**
 * Conversor entre JPlatTramitElectronica y PlatTramitElectronicaDTO. La implementacion se generará automaticamente por
 * MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EntidadConverter.class})
public interface PlatTramitElectronicaConverter extends Converter<JPlatTramitElectronica, PlatTramitElectronicaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"descripcion\"))")
    @Mapping(target = "urlAcceso", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"urlAcceso\"))")
    PlatTramitElectronicaDTO createDTO(JPlatTramitElectronica entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jPlatTramitElectronica, dto))")
    JPlatTramitElectronica createEntity(PlatTramitElectronicaDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity, dto))")
    void mergeEntity(@MappingTarget JPlatTramitElectronica entity, PlatTramitElectronicaDTO dto);

    default List<PlatTramitElectronicaDTO> toDTOs(List<JPlatTramitElectronica> entities) {
        return entities.stream().map(e -> createDTO(e)).collect(Collectors.toList());
    }

    default List<JPlatTramitElectronicaTraduccion> convierteLiteralToTraduccion(JPlatTramitElectronica jPlatTramitElectronica, PlatTramitElectronicaDTO platTramitElectronicaDTO) {
        List<String> idiomasPermitidos = List.of(platTramitElectronicaDTO.getCodEntidad().getIdiomasPermitidos().split(";"));

        //Comprobamos si aún no se ha creado la entidad
        if (jPlatTramitElectronica.getTraducciones() == null || jPlatTramitElectronica.getTraducciones().isEmpty()) {
            jPlatTramitElectronica.setTraducciones(JPlatTramitElectronicaTraduccion.createInstance(idiomasPermitidos));
            for (JPlatTramitElectronicaTraduccion jTrad : jPlatTramitElectronica.getTraducciones()) {
                jTrad.setPlatTramitElectronica(jPlatTramitElectronica);
            }
        } else if (jPlatTramitElectronica.getTraducciones().size() < idiomasPermitidos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JPlatTramitElectronicaTraduccion> tradsAux = jPlatTramitElectronica.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JPlatTramitElectronicaTraduccion traduccion : jPlatTramitElectronica.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JPlatTramitElectronicaTraduccion trad = new JPlatTramitElectronicaTraduccion();
                trad.setIdioma(idioma);
                trad.setPlatTramitElectronica(jPlatTramitElectronica);
                tradsAux.add(trad);
            }
            jPlatTramitElectronica.setTraducciones(tradsAux);
        }

        for (JPlatTramitElectronicaTraduccion traduccion : jPlatTramitElectronica.getTraducciones()) {
            if (platTramitElectronicaDTO.getDescripcion() != null) {

                traduccion.setDescripcion(platTramitElectronicaDTO.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
            if (platTramitElectronicaDTO.getUrlAcceso() != null) {

                traduccion.setUrlAcceso(platTramitElectronicaDTO.getUrlAcceso().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jPlatTramitElectronica.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JPlatTramitElectronicaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getPlatTramitElectronica().getCodigo()).findFirst().orElse(null));

            for (JPlatTramitElectronicaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = "";
                if (nombreLiteral.equals("descripcion")) {
                    literal = traduccion.getDescripcion();
                } else if (nombreLiteral.equals("urlAcceso")) {
                    literal = traduccion.getUrlAcceso();
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }
}
