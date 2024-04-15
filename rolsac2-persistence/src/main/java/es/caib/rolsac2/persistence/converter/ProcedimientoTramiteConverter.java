package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoTramiteTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.*;
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
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {UnidadAdministrativaConverter.class, ProcedimientoWorkflowConverter.class, TipoTramitacionConverter.class})
public interface ProcedimientoTramiteConverter extends Converter<JProcedimientoTramite, ProcedimientoTramiteDTO> {

    @Override
    @Mapping(target = "requisitos", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"requisitos\"))")
    @Mapping(target = "nombre", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"nombre\"))")
    @Mapping(target = "documentacion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"documentacion\"))")
    @Mapping(target = "observacion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"observacion\"))")
    @Mapping(target = "terminoMaximo", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"terminoMaximo\"))")
    @Mapping(target = "listaDocumentos", ignore = true)
    @Mapping(target = "listaModelos", ignore = true)
    @Mapping(target = "unidadAdministrativa", expression = "java(convertSencillo(entity.getUnidadAdministrativa(), true))")
    @Mapping(target = "procedimiento", expression = "java(convierteProcedimientoSimple(entity.getProcedimiento()))")
    ProcedimientoTramiteDTO createDTO(JProcedimientoTramite entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jProcedimientoTramite, dto))")
    @Mapping(target = "listaDocumentos", ignore = true)
    @Mapping(target = "listaModelos", ignore = true)
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
    @Mapping(target = "listaDocumentos", ignore = true)
    @Mapping(target = "listaModelos", ignore = true)
    void mergeEntity(@MappingTarget JProcedimientoTramite entity, ProcedimientoTramiteDTO dto);

    /**
     * Convierte una junidad administrativa a un DTO sencillo
     *
     * @param junidad
     * @param incluirPadre
     * @return
     */
    default UnidadAdministrativaDTO convertSencillo(JUnidadAdministrativa junidad, boolean incluirPadre) {

        if (junidad == null) {
            return null;
        }
        UnidadAdministrativaDTO unidadAdministrativaDTO = new UnidadAdministrativaDTO();
        unidadAdministrativaDTO.setCodigo(junidad.getCodigo());
        unidadAdministrativaDTO.setCodigoDIR3(junidad.getCodigoDIR3());
        unidadAdministrativaDTO.setIdentificador(junidad.getIdentificador());
        Literal nombre = new Literal();
        for (JUnidadAdministrativaTraduccion trad : junidad.getTraducciones()) {
            nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
        }
        unidadAdministrativaDTO.setNombre(nombre);
        unidadAdministrativaDTO.setOrden(junidad.getOrden());
        if (incluirPadre && junidad.getPadre() != null) {
            unidadAdministrativaDTO.setPadre(convertSencillo(junidad.getPadre(), false));
        }
        return unidadAdministrativaDTO;
    }

    default List<JProcedimientoTramiteTraduccion> convierteLiteralToTraduccion(JProcedimientoTramite jProcedimientoTramite, ProcedimientoTramiteDTO dto) {

        List<String> idiomasPermitidos = List.of(dto.getUnidadAdministrativa().getEntidad().getIdiomasPermitidos().split(";"));

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

    default ProcedimientoWorkflowDTO convierteProcedimientoSimple(JProcedimientoWorkflow procedimiento) {
        ProcedimientoWorkflowDTO resultado = new ProcedimientoWorkflowDTO();
        resultado.setCodigo(procedimiento.getCodigo());
        ProcedimientoDTO proc = new ProcedimientoDTO();
        proc.setCodigo(procedimiento.getProcedimiento().getCodigo());
        resultado.setProcedimiento(proc);
        Literal nombre = new Literal();
        for (JProcedimientoWorkflowTraduccion trad : procedimiento.getTraducciones()) {
            nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
        }
        resultado.setNombre(nombre);
        resultado.setEstado(procedimiento.getEstado());
        resultado.setWorkflow(procedimiento.getWorkflow());
        return resultado;
    }

    default Literal convierteTraduccionToLiteral(List<JProcedimientoTramiteTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getProcedimientoTramite().getCodigo()).findFirst().orElse(null));
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
