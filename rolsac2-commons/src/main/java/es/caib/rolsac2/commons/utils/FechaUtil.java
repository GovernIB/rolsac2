package es.caib.rolsac2.commons.utils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FechaUtil {

    public static String formatISO8601(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedDate = localDate.atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter);
        return formattedDate;
    }
}
