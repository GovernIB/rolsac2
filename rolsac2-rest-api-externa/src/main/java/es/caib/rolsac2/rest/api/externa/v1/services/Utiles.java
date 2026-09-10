package es.caib.rolsac2.rest.api.externa.v1.services;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utiles {

    private static final DateTimeFormatter REPOSITORY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /*
     * Un + literal en un query param puede llegar decodificado por JAX-RS como
     * un espacio (semántica application/x-www-form-urlencoded). Sólo se corrige
     * ese caso concreto en la posición del offset positivo.
     */
    private static final Pattern ISO8601_OFFSET_WITH_DECODED_PLUS = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?) (\\d{2}:\\d{2})$"
    );

    /**
     * Convierte una cadena a booleano
     */
    public static boolean stringToBoolean(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false;
        }
        return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("si") || valor.equalsIgnoreCase("yes") || valor.equals("1");
    }

    /**
     * Convierte String a Integer y controla el error devolviendo nulo
     **/
    public static Integer stringToInteger(String valor) {
        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convierte String a Long y controla el error devolviendo nulo
     **/
    public static Long stringToLong(String valor) {
        try {
            return Long.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convierte String a Double y controla el error devolviendo nulo
     **/
    public static Double stringToDouble(String valor) {
        try {
            return Double.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convierte String a Float y controla el error devolviendo nulo
     **/
    public static Float stringToFloat(String valor) {
        try {
            return Float.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Comprueba si un texto está en formato ISO8601
     **/
    public static boolean isISO8601(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }
        try {
            OffsetDateTime.parse(normalizeISO8601QueryParam(fecha), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Convierte una fecha ISO8601 con offset al formato dd/MM/yyyy que espera
     * ProcedimientoRepositoryBean para los filtros de fecha.
     **/
    public static String iso8601ToRepositoryDate(String fecha) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(
                normalizeISO8601QueryParam(fecha),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
        );
        return offsetDateTime.toLocalDate().format(REPOSITORY_DATE_FORMATTER);
    }

    /**
     * Normaliza exclusivamente el caso en que un offset positivo (+02:00)
     * llega desde un query param como espacio ( 02:00). No modifica ningún
     * otro formato inválido.
     */
    private static String normalizeISO8601QueryParam(String fecha) {
        String value = fecha.trim();
        Matcher matcher = ISO8601_OFFSET_WITH_DECODED_PLUS.matcher(value);
        if (matcher.matches()) {
            return matcher.group(1) + "+" + matcher.group(2);
        }
        return value;
    }

    public static String getBaseUrl(final URI requestUri) {
        if (requestUri == null || requestUri.getScheme() == null || requestUri.getHost() == null) {
            return null;
        }

        StringBuilder urlBase = new StringBuilder();
        urlBase.append(requestUri.getScheme()).append("://").append(requestUri.getHost());

        int port = requestUri.getPort();
        if (port != -1
                && !(("http".equalsIgnoreCase(requestUri.getScheme()) && port == 80)
                || ("https".equalsIgnoreCase(requestUri.getScheme()) && port == 443))) {
            urlBase.append(':').append(port);
        }

        return urlBase.toString();
    }

    /**
     * Convierte una fecha a String en formato ISO8601: 2022-07-26T12:58:55+02:00
     * En formato obligatorio +02:00 de madrid.
     **/
    public static String getFechaISO8601(java.util.Date fecha) {

        if (fecha == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        java.time.ZoneId zoneId = java.time.ZoneId.of("Europe/Madrid");
        java.time.ZonedDateTime zonedDateTime = fecha.toInstant().atZone(zoneId);
        return zonedDateTime.format(formatter);
    }


}
