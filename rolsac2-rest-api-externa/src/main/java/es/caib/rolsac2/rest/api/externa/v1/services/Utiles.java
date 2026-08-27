package es.caib.rolsac2.rest.api.externa.v1.services;

public class Utiles {

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
        String regex = "^\\d{4}-[01]\\d-[0-3]\\d[Tt][0-2]\\d:[0-5]\\d:[0-5]\\d(\\.\\d+)?([Zz]|([+-][0-2]\\d:[0-5]\\d))$";
        return fecha != null && fecha.matches(regex);
    }

    /**
     * Convierte una fecha a String en formato ISO8601: 2022-07-26T12:58:55+02:00
     * En formato obligatorio +02:00 de madrid.
     **/
    public static String getFechaISO8601(java.util.Date fecha) {

        if (fecha == null) {
            return null;
        }
        
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        java.time.ZoneId zoneId = java.time.ZoneId.of("Europe/Madrid");
        java.time.ZonedDateTime zonedDateTime = fecha.toInstant().atZone(zoneId);
        return zonedDateTime.format(formatter);
    }


}
