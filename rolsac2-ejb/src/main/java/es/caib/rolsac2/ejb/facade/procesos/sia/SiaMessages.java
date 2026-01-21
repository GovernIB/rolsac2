package es.caib.rolsac2.ejb.facade.procesos.sia;

import java.util.Locale;
import java.util.ResourceBundle;

public class SiaMessages {

    private static final String BUNDLE_NAME = "i18n.messages";

    public static String getMessage(String key, String idioma) {
        Locale locale = idioma != null ? new Locale(idioma) : new Locale("ca");
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        return bundle.getString(key);
    }

    public static String getMessage(String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        return bundle.getString(key);
    }
}
