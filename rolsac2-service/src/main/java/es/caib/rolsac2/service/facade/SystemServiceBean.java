package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

/*Interface de SystemService*/
public interface SystemServiceBean {

    /* Método utilizado para obtener las propiedades de configuración*/
    String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);


}
