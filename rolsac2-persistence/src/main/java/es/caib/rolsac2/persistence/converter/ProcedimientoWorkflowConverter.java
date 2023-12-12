package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.ProcedimientoWorkflowDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JProcedimientoWorkflow y ProcedimientoWorkflowDTO. La implementacion se generará automaticamente por
 * MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ProcedimientoConverter.class, UnidadAdministrativaConverter.class, TipoLegitimacionConverter.class, TipoFormaInicioConverter.class, TipoSilencioAdministrativoConverter.class, TipoViaConverter.class, TipoTramitacionConverter.class})
public interface ProcedimientoWorkflowConverter extends Converter<JProcedimientoWorkflow, ProcedimientoWorkflowDTO> {

    @Override
    @Mapping(target = "nombre", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"nombre\"))")
    @Mapping(target = "objeto", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"objeto\"))")
    @Mapping(target = "destinatarios", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"destinatarios\"))")
    @Mapping(target = "observaciones", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"observaciones\"))")
    @Mapping(target = "datosPersonalesFinalidad", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"datosPersonalesFinalidad\"))")
    @Mapping(target = "datosPersonalesDestinatario", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"datosPersonalesDestinatario\"))")
    @Mapping(target = "terminoResolucion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"terminoResolucion\"))")
    // TODO Hay que devolver el documento según el idioma
    //@Mapping(target = "documentoLOPD",
    //                expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"documentoLOPD\"))")
    @Mapping(target = "requisitos", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"requisitos\"))")
    ProcedimientoWorkflowDTO createDTO(JProcedimientoWorkflow entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jProcedimientoWorkflow, dto))")
    JProcedimientoWorkflow createEntity(ProcedimientoWorkflowDTO dto);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JProcedimientoWorkflow entity, ProcedimientoWorkflowDTO dto);


    default List<JProcedimientoWorkflowTraduccion> convierteLiteralToTraduccion(JProcedimientoWorkflow jProcedimientoWorkflow, ProcedimientoWorkflowDTO dto) {

        List<String> idiomasPermitidos = List.of(dto.getProcedimiento().getUaInstructor().getEntidad().getIdiomasPermitidos().split(";"));

        if (jProcedimientoWorkflow.getTraducciones() == null || jProcedimientoWorkflow.getTraducciones().isEmpty()) {
            jProcedimientoWorkflow.setTraducciones(JProcedimientoWorkflowTraduccion.createInstance(idiomasPermitidos));
            for (JProcedimientoWorkflowTraduccion jtrad : jProcedimientoWorkflow.getTraducciones()) {
                jtrad.setProcedimientoWorkflow(jProcedimientoWorkflow);
            }
        } else if (jProcedimientoWorkflow.getTraducciones().size() < idiomasPermitidos.size()) {
            // En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta
            // idiomas nuevos en entidad)
            List<JProcedimientoWorkflowTraduccion> tradsAux = jProcedimientoWorkflow.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JProcedimientoWorkflowTraduccion traduccion : jProcedimientoWorkflow.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            // Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JProcedimientoWorkflowTraduccion trad = new JProcedimientoWorkflowTraduccion();
                trad.setIdioma(idioma);
                trad.setProcedimientoWorkflow(jProcedimientoWorkflow);
                tradsAux.add(trad);
            }
            jProcedimientoWorkflow.setTraducciones(tradsAux);
        }
        for (JProcedimientoWorkflowTraduccion traduccion : jProcedimientoWorkflow.getTraducciones()) {
            if (dto.getNombre() != null) {
                traduccion.setNombre(dto.getNombre().getTraduccion(traduccion.getIdioma()));
            }

            if (dto.getObjeto() != null) {
                traduccion.setObjeto(dto.getObjeto().getTraduccion(traduccion.getIdioma()));
            }

            if (dto.getDestinatarios() != null) {
                traduccion.setDestinatarios(dto.getDestinatarios().getTraduccion(traduccion.getIdioma()));
            }

            if (dto.getObservaciones() != null) {
                traduccion.setObservaciones(dto.getObservaciones().getTraduccion(traduccion.getIdioma()));
            }

            if (dto.getDatosPersonalesFinalidad() != null) {
                traduccion.setDatosPersonalesFinalidad(dto.getDatosPersonalesFinalidad().getTraduccion(traduccion.getIdioma()));
            }

            if (dto.getDatosPersonalesDestinatario() != null) {
                traduccion.setDatosPersonalesDestinatario(dto.getDatosPersonalesDestinatario().getTraduccion(traduccion.getIdioma()));
            }

            if (dto.getRequisitos() != null) {
                traduccion.setRequisitos(dto.getRequisitos().getTraduccion(traduccion.getIdioma()));
            }

            // TODO Hay que devolver el documento según el idioma
            if (dto.getDocumentoLOPD() != null) {
                traduccion.setDocumentoLOPD(dto.getDocumentoLOPD());
            }
        }
        return jProcedimientoWorkflow.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JProcedimientoWorkflowTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getProcedimientoWorkflow().getCodigo()).findFirst().orElse(null));
            for (JProcedimientoWorkflowTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                switch (nombreLiteral) {
                    case "nombre":
                        literal = traduccion.getNombre();
                        break;
                    case "objeto":
                        literal = traduccion.getObjeto();
                        break;
                    case "destinatarios":
                        literal = traduccion.getDestinatarios();
                        break;
                    case "observaciones":
                        literal = traduccion.getObservaciones();
                        break;
                    case "datosPersonalesFinalidad":
                        literal = traduccion.getDatosPersonalesFinalidad();
                        break;
                    case "datosPersonalesDestinatario":
                        literal = traduccion.getDatosPersonalesDestinatario();
                        break;
                    case "requisitos":
                        literal = traduccion.getRequisitos();
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
