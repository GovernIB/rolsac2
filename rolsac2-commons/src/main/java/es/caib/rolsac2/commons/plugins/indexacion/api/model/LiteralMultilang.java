package es.caib.rolsac2.commons.plugins.indexacion.api.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.types.EnumIdiomas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Literal multiidioma.
 *
 * @author Indra
 */
public class LiteralMultilang {

    /**
     * Literales.
     */
    Map<String, String> valores = new HashMap<String, String>();

    /**
     * Añadir literal para un idioma.
     *
     * @param idioma Idioma
     * @param valor  literal
     */
    public void addIdioma(final EnumIdiomas idioma, final String valor) {
        valores.put(idioma.toString(), valor);
    }

    /**
     * Obtener el valor según un idioma
     *
     * @param idioma idioma
     * @return valor
     */
    public String get(final EnumIdiomas idioma) {
        return valores.get(idioma.toString());
    }

    /**
     * Comprueba si todos los valores de una lista de idiomas está relleno.
     *
     * @param idiomas Idiomas.
     * @return true si correcto
     */
    public boolean isCorrecto(final List<EnumIdiomas> idiomas) {
        boolean resultado = true;
        for (EnumIdiomas idioma : idiomas) {
            if (valores.get(idioma.toString()) == null
                    || valores.get(idioma.toString()).isEmpty()) {
                resultado = false;
            }
        }
        return resultado;
    }

    public List<String> getIdiomas() {
        return new ArrayList<String>(valores.keySet());
    }
}
