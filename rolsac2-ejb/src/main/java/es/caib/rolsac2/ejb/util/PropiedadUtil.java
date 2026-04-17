package es.caib.rolsac2.ejb.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropiedadUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PropiedadUtil.class);


    public static String replacePlaceholders(final Config config, final String valor) {
        String res = replacePlaceHolders(config, valor, false);
        res = replacePlaceHolders(config, res, true);
        return res;
    }

    /**
     * Reemplaza placeholders: sistema (${system.propiedad}) o configuración
     * (${config.propiedad).
     *
     * @param valor  Valor
     * @param system Indica si es de sistema
     * @return Valor reemplazado.
     */
    private static String replacePlaceHolders(final Config config, final String valor, final boolean system) {
        String placeholder;

        if (system) {
            placeholder = "${system.";
        } else {
            placeholder = "${config.";
        }

        String res = valor;
        if (res != null) {
            int pos = valor.indexOf(placeholder);
            while (pos >= 0) {
                final int pos2 = res.indexOf("}", pos + 1);
                if (pos2 >= 0) {
                    final String propPlaceholder = res.substring(pos + placeholder.length(), pos2);
                    String valuePlaceholder = "";
                    if (system) {
                        valuePlaceholder = System.getProperty(propPlaceholder);
                    } else {
                        valuePlaceholder = readPropiedad(config, propPlaceholder);
                    }
                    valuePlaceholder = StringUtils.defaultString(valuePlaceholder);
                    if (valuePlaceholder.contains(placeholder)) {
                        throw new Error(
                                "Valor no válido para propiedad " + propPlaceholder + ": " + valuePlaceholder);
                    }
                    if (StringUtils.isBlank(valuePlaceholder)) {
                        LOG.warn("Placeholder {} tiene valor vacío", propPlaceholder);
                    }
                    res = StringUtils.replace(res, placeholder + propPlaceholder + "}", valuePlaceholder);
                }
                pos = res.indexOf(placeholder);
            }
        }
        return res;
    }

    public static String readPropiedad(final Config config, final String propiedad) {
        // Busca primero en propiedades locales
        return config.getOptionalValue(propiedad, String.class).orElse(null);
    }
}
