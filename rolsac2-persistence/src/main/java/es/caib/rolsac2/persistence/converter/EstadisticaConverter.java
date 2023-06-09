package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEstadistica;
import es.caib.rolsac2.persistence.model.JEstadisticaAcceso;
import es.caib.rolsac2.service.model.auditoria.EstadisticaAccesoDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface EstadisticaConverter extends Converter<JEstadistica, EstadisticaDTO> {

    @Override
    @Mapping(target = "accesos", expression = "java(convertAccesosToDTO(entity))")
    @Mapping(source = "procedimiento.codigo", target = "codProcedimiento")
    @Mapping(source = "unidadAdministrativa.codigo", target = "codUa")
    EstadisticaDTO createDTO(JEstadistica entity);

    @Override
    @Mapping(target = "listadoAccesos", expression = "java(convertAccesos(jEstadistica, dto))")
    @Mapping(target = "procedimiento", ignore = true)
    @Mapping(target = "unidadAdministrativa", ignore = true)
    JEstadistica createEntity(EstadisticaDTO dto);

    @Override
    @Mapping(target = "listadoAccesos", expression = "java(convertAccesos(jEstadistica, dto))")
    @Mapping(target = "procedimiento", ignore = true)
    @Mapping(target = "unidadAdministrativa", ignore = true)
    void mergeEntity(@MappingTarget JEstadistica jEstadistica, EstadisticaDTO dto);

    default List<JEstadisticaAcceso> convertAccesos(JEstadistica jEstadistica, EstadisticaDTO dto) {
        List<JEstadisticaAcceso> jAccesos;
        if(jEstadistica.getListadoAccesos() == null) {
            jAccesos = new ArrayList<>();
        } else {
            jAccesos = jEstadistica.getListadoAccesos();
        }
        for(EstadisticaAccesoDTO acceso : dto.getAccesos()) {
            if(acceso.getCodigo() != null) {
                jAccesos.stream().filter(a -> a.getCodigo().compareTo(acceso.getCodigo()) == 0).findFirst()
                        .ifPresent(a -> a.setContador(a.getContador() + 1l));
            } else {
                JEstadisticaAcceso jEstadisticaAcceso = new JEstadisticaAcceso();
                jEstadisticaAcceso.setFechaAcceso(acceso.getFechaCreacion());
                jEstadisticaAcceso.setContador(acceso.getContador());
                jEstadisticaAcceso.setCodEstadistica(jEstadistica);
                jAccesos.add(jEstadisticaAcceso);
            }
        }
        return jAccesos;

    }

    default List<EstadisticaAccesoDTO> convertAccesosToDTO(JEstadistica jEstadistica) {
        List<EstadisticaAccesoDTO> accesos = new ArrayList<>();
        for(JEstadisticaAcceso jEstadisticaAcceso : jEstadistica.getListadoAccesos()) {
            EstadisticaAccesoDTO acceso = new EstadisticaAccesoDTO();
            acceso.setCodigo(jEstadisticaAcceso.getCodigo());
            acceso.setFechaCreacion(jEstadisticaAcceso.getFechaAcceso());
            acceso.setContador(jEstadisticaAcceso.getContador());
            accesos.add(acceso);
        }
        return accesos;
    }
}
