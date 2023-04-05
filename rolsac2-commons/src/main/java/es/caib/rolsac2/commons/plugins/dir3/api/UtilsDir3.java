package es.caib.rolsac2.commons.plugins.dir3.api;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class UtilsDir3 {


    public static LocalDateTime parsearFecha(BigDecimal timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp.longValue());
        LocalDateTime fecha = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return fecha;
    }

    public static String formatearFecha(Date fecha) throws Dir3ErrorException {
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(fecha);
        } catch (Exception e) {
            throw new Dir3ErrorException("Se ha producido un error al formatear la fecha en los parámetros. ");
        }

    }
}
