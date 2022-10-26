package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.persistence.model.JSeccion;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoTramiteTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.SeccionDTO;
import es.caib.rolsac2.service.model.Traduccion;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JProcedimientoTramite y ProcedimientoTramiteDTO. La implementacion se generará automaticamente por
 * MapStruct
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {UnidadAdministrativaConverter.class, ProcedimientoWorkflowConverter.class,
                                TipoTramitacionConverter.class})
public interface ProcedimientoTramiteConverter extends Converter<JProcedimientoTramite, ProcedimientoTramiteDTO> {

    @Override
    @Mapping(target = "requisitos",
                    expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"requisitos\"))")
    @Mapping(target = "nombre", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"nombre\"))")
    @Mapping(target = "documentacion",
                    expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"documentacion\"))")
    @Mapping(target = "observacion",
                    expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"observacion\"))")
    @Mapping(target = "terminoMaximo",
                    expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"terminoMaximo\"))")
    ProcedimientoTramiteDTO createDTO(JProcedimientoTramite entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jProcedimientoTramite, dto))")
    JProcedimientoTramite createEntity(ProcedimientoTramiteDTO dto);

    default List<ProcedimientoTramiteDTO> createDTOs(List<JProcedimientoTramite> entities) {
        List<ProcedimientoTramiteDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createDTO(e)));
        }
        return resultado;
    }

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JProcedimientoTramite entity, ProcedimientoTramiteDTO dto);


    default List<JProcedimientoTramiteTraduccion> convierteLiteralToTraduccion(
                    JProcedimientoTramite jProcedimientoTramite, ProcedimientoTramiteDTO dto) {

        List<String> idiomasPermitidos =
                        List.of(dto.getUnidadAdministrativa().getEntidad().getIdiomasPermitidos().split(";"));

        if (jProcedimientoTramite.getTraducciones() == null || jProcedimientoTramite.getTraducciones().isEmpty()) {
            jProcedimientoTramite.setTraducciones(JProcedimientoTramiteTraduccion.createInstance(idiomasPermitidos));
            for (JProcedimientoTramiteTraduccion jtrad : jProcedimientoTramite.getTraducciones()) {
                jtrad.setProcedimientoTramite(jProcedimientoTramite);
            }
        } else if (jProcedimientoTramite.getTraducciones().size() < idiomasPermitidos.size()) {
            // En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta
            // idiomas nuevos en entidad)
            List<JProcedimientoTramiteTraduccion> tradsAux = jProcedimientoTramite.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JProcedimientoTramiteTraduccion traduccion : jProcedimientoTramite.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            // Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JProcedimientoTramiteTraduccion trad = new JProcedimientoTramiteTraduccion();
                trad.setIdioma(idioma);
                trad.setProcedimientoTramite(jProcedimientoTramite);
                tradsAux.add(trad);
            }
            jProcedimientoTramite.setTraducciones(tradsAux);
        }
        for (JProcedimientoTramiteTraduccion traduccion : jProcedimientoTramite.getTraducciones()) {
            if (dto.getRequisitos() != null) {
                traduccion.setRequisitos(dto.getRequisitos().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getNombre() != null) {
                traduccion.setNombre(dto.getNombre().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getDocumentacion() != null) {
                traduccion.setDocumentacion(dto.getDocumentacion().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getObservacion() != null) {
                traduccion.setObservacion(dto.getObservacion().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getTerminoMaximo() != null) {
                traduccion.setTerminoMaximo(dto.getTerminoMaximo().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jProcedimientoTramite.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JProcedimientoTramiteTraduccion> traducciones,
                    String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getProcedimientoTramite().getCodigo()).findFirst()
                            .orElse(null));
            for (JProcedimientoTramiteTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                switch (nombreLiteral) {
                    case "requisitos":
                        literal = traduccion.getRequisitos();
                        break;
                    case "nombre":
                        literal = traduccion.getNombre();
                        break;
                    case "documentacion":
                        literal = traduccion.getDocumentacion();
                        break;
                    case "observacion":
                        literal = traduccion.getObservacion();
                        break;
                    case "terminoMaximo":
                        literal = traduccion.getTerminoMaximo();
                        break;
                    default:
                        literal = null;
                        break;
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }

        return resultado;
    }
}
