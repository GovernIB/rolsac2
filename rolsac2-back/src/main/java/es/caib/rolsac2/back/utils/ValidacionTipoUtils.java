package es.caib.rolsac2.back.utils;

import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Mensaje;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionTipoUtils {

    public static final String PATRON_EMAIL =
                    "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,4}$";
    public static final String PATRON_TELEFONO = "^\\d{9}$";
    public static final String PATRON_COD_POSTAL = "^\\d{5}$";
    public static final String PATRON_COORDENADA = "^[-]?\\d+[\\.]?\\d*$";
    public static final String PATRON_URL = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%{2}|[-()_.!~*';/?:@&=+$, A-Za-z0-9])+)" + "([).!';/?:, ][[:blank:]])?$";

    private ValidacionTipoUtils() {}

    public static boolean esTelefonoValido(String telefono) {
        boolean resultado = false;
        final Pattern patron = Pattern.compile(PATRON_TELEFONO);
        if (!esCadenaVacia(telefono)) {
            final Matcher m = patron.matcher(telefono);
            resultado = m.matches();
        }
        return resultado;
    }

    public static boolean esUrlValido(String url) {
        boolean resultado = false;
        final Pattern patron = Pattern.compile(PATRON_URL);
        if (!esCadenaVacia(url)) {
            final Matcher m = patron.matcher(url);
            resultado = m.matches();
        }
        return resultado;
    }

    public static boolean esCodPostalValido(String cosPostal) {
        boolean resultado = false;
        final Pattern patron = Pattern.compile(PATRON_COD_POSTAL);
        if (!esCadenaVacia(cosPostal)) {
            final Matcher m = patron.matcher(cosPostal);
            resultado = m.matches();
        }
        return resultado;
    }

    public static boolean esEmailValido(String email) {
        boolean resultado = false;
        final Pattern patron = Pattern.compile(PATRON_EMAIL);
        if (!esCadenaVacia(email)) {
            final Matcher m = patron.matcher(email);
            resultado = m.matches();
        }
        return resultado;
    }

    public static boolean esLatitudValido(String latitud) {
        boolean resultado = esCoordenadaValida(latitud);
        if (resultado) {
            try {
                float latitudNum = Float.parseFloat(latitud);
                resultado = latitudNum > -90 && latitudNum < 90;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return resultado;
    }

    public static boolean esLongitudValido(String longitud) {
        boolean resultado = esCoordenadaValida(longitud);
        if (resultado) {
            try {
                float longitudNum = Float.parseFloat(longitud);
                resultado = (longitudNum > -180 && longitudNum < 180);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return resultado;
    }

    private static boolean esCoordenadaValida(String coordenada) {
        boolean resultado = false;
        final Pattern patron = Pattern.compile(PATRON_COORDENADA);
        if (!esCadenaVacia(coordenada)) {
            final Matcher m = patron.matcher(coordenada);
            resultado = m.matches();
        }

        return resultado;
    }

    public static boolean esNumerico(final String numero) {
        return (!esCadenaVacia(numero) && numero.matches("[0-9]+"));
    }

    public static boolean esEntero(final String numero) {
        boolean res = false;
        try {
            res = (!esCadenaVacia(numero) && (esNumerico(numero) || (Integer.parseInt(numero) <= 0)));
        } catch (final NumberFormatException e) {
            res = false;
        }
        return res;
    }

    public static boolean esCadenaVacia(String cadena) {
        return cadena == null || cadena.isEmpty() || cadena.isBlank();
    }

    public static List<String> esLiteralCorrecto(Literal literal, List<String> idiomasObligatorios) {
        List<String> idiomasPendientes = new ArrayList<>();
        for(String idioma: idiomasObligatorios) {
            if(literal.getTraduccion(idioma) == null || literal.getTraduccion(idioma).isEmpty()) {
                idiomasPendientes.add(idioma);
            }
        }
        return idiomasPendientes;
    }

    public static void sanitizarMensajes(List<Mensaje> mensajes) {
        mensajes.forEach(m -> m.setMensaje(m.getMensaje().replace("'", "''")));
    }

    public static void normalizarMensajes(List<Mensaje> mensajes) {
        mensajes.forEach(m -> m.setMensaje(m.getMensaje().replace("''", "'")));
    }
}
