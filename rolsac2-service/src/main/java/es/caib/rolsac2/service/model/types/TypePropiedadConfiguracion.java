package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypePropiedadConfiguracion {
    /**
     * Propiedad para obtener el path de los ficheros externos
     */
    PATH_FICHEROS_EXTERNOS("ficherosExternos.path"),

    /**
     * Propiedad para indicar el intérvalo de tiempo de los procesos
     */
    PROCESOS_AUTOMATICOS_CRON("procesos.automaticos.cron"),

    /**
     * Propiedad para indicar el tiempo máximo que el maestro puede estar inactivo
     */
    PROCESOS_MIN_MAX_MAESTRO_ACTIVO("procesos.minMaxMaestroInactivo"),
    /**
     * LOPD
     **/
    LOPD_FINALIDAD("es.caib.rolsac2.lopd.finalidad"),
    LOPD_DESTINATARIO("es.caib.rolsac2.lopd.destinatario"),
    LOPD_DERECHOS("es.caib.rolsac2.lopd.derechos"),
    LOPD_RESPONSABLE("es.caib.rolsac2.lopd.responsable"),
    LOPD_CABECERA("es.caib.rolsac2.lopd.cabecera"),
    LOPD_PLANTILLA("es.caib.rolsac2.lopd.plantilla"),
    /**
     * UA COMUN
     **/
    UA_COMUN("es.caib.rolsac2.comun.ua"),
    /**
     * Propiedad para obtener el idioma de la aplicacion por defecto
     */
    IDIOMA_DEFECTO("idiomaDefecto"),

    URL_BASE("url.base");


    /**
     * Valor
     */
    private static final Map<String, TypePropiedadConfiguracion> BY_VALOR = new HashMap<>();


    static {
        for (TypePropiedadConfiguracion propiedadConfiguracion : values()) {
            BY_VALOR.put(propiedadConfiguracion.valor, propiedadConfiguracion);
        }
    }

    public final String valor;


    TypePropiedadConfiguracion(String valor) {
        this.valor = valor;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param text
     * @return
     */
    public static TypePropiedadConfiguracion fromString(final String text) {
        return BY_VALOR.get(text);
    }

    @Override
    public String toString() {
        return this.valor;
    }
}
