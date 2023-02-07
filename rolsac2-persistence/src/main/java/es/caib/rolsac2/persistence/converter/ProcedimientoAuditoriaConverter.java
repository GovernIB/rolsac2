package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JProcedimientoAuditoria;
import es.caib.rolsac2.service.model.auditoria.ProcedimientoAuditoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JTipoFormaInicio y TipoFormaInicioDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface ProcedimientoAuditoriaConverter extends Converter<JProcedimientoAuditoria, ProcedimientoAuditoria> {

    @Override
        //@Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    ProcedimientoAuditoria createDTO(JProcedimientoAuditoria entity);

    @Override
        //@Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoFormaInicio,dto.getDescripcion()))")
    JProcedimientoAuditoria createEntity(ProcedimientoAuditoria dto);

    @Override
        //@Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
        /// , ignore = true)
    void mergeEntity(@MappingTarget JProcedimientoAuditoria entity, ProcedimientoAuditoria dto);

    /*
    default List<JTipoFormaInicioTraduccion> convierteLiteralToTraduccion(JTipoFormaInicio jTipoFormaInicio,
                                                                          Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jTipoFormaInicio.getDescripcion() == null || jTipoFormaInicio.getDescripcion().isEmpty()) {
            jTipoFormaInicio.setDescripcion(JTipoFormaInicioTraduccion.createInstance(idiomasRellenos));
            for (JTipoFormaInicioTraduccion jtrad : jTipoFormaInicio.getDescripcion()) {
                jtrad.setTipoFormaInicio(jTipoFormaInicio);
            }
        } else if(idiomasRellenos.size() >  jTipoFormaInicio.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoFormaInicioTraduccion> tradsAux = jTipoFormaInicio.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoFormaInicioTraduccion traduccion : jTipoFormaInicio.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoFormaInicioTraduccion trad = new JTipoFormaInicioTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoFormaInicio(jTipoFormaInicio);
                tradsAux.add(trad);
            }
            jTipoFormaInicio.setDescripcion(tradsAux);
        }
        for (JTipoFormaInicioTraduccion traduccion : jTipoFormaInicio.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoFormaInicio.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoFormaInicioTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(
                    traducciones.stream().map(t -> t.getTipoFormaInicio().getCodigo()).findFirst().orElse(null));
            for (JTipoFormaInicioTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }

        return resultado;
    }
    */
}
