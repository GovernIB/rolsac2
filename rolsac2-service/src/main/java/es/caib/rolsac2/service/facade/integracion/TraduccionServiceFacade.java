package es.caib.rolsac2.service.facade.integracion;


import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccionException;
import es.caib.rolsac2.commons.plugins.traduccion.api.Idioma;

import java.util.Map;

/**
 * Acceso al plugin de Traducción
 */
public interface TraduccionServiceFacade {

    /**
     * Traducción de texto
     * @param tipoEntrada
     * @param textoEntrada
     * @param idiomaEntrada
     * @param idiomaSalida
     * @param opciones
     * @return
     */
    String traducir(String tipoEntrada, String textoEntrada, Idioma idiomaEntrada, Idioma idiomaSalida, Map<String, String> opciones, Long idEntidad) throws IPluginTraduccionException;

    Boolean tradExiste(Long idEntidad);
}
