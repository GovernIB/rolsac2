package es.caib.rolsac2.service.model.types;

import java.util.HashMap;
import java.util.Map;

public enum TypePropiedadConfiguracion {
    /**
     * Propiedad para obtener el path de los ficheros externos
     */
    PATH_FICHEROS_EXTERNOS("ficherosExternos.path"),
    /**
     * Propiedad para indicar si mostrar los procedimientos y normativas
     */
    MOSTRAR_EN_UA_PROCS_NORMATIVAS("ua.mostrar.procsNormativas"),
    /**
     * Propiedad para obtener el path de los ficheros externos
     */
    PATH_FICHEROS_ROLSAC1("ficherosRolsac1.path"),
    /**
     * Propiedad para indicar el intérvalo de tiempo de los procesos
     */
    PROCESOS_AUTOMATICOS_CRON("procesos.automaticos.cron"),
    /**
     * Propiedad para indicar el tiempo máximo que el maestro puede estar inactivo
     */
    PROCESOS_MIN_MAX_MAESTRO_ACTIVO("procesos.minMaxMaestroInactivo"),
    /**
     * /**
     * UA COMUN
     **/
    UA_COMUN("es.caib.rolsac2.comun.ua"),
    /**
     * Propiedad para obtener el idioma de la aplicacion por defecto
     */
    IDIOMA_DEFECTO("idiomaDefecto"),
    /**
     * Url base
     */
    URL_BASE("url.base"),
    /**
     * Deshabilitar certificado
     */
    DESAHABILITAR_CERTIFICADO("deshabilitar.certificado"),

    PDU_PARENT_URL("pdu.parentUrl"),

    PDU_PROCEDIMIENTOS_URL("pdu.urlProc"),
    PDU_SERVICIOS_URL("pdu.urlServ"),
    BACKEND_IDIOMAS("back.idiomas"),
    DEBUG_ACTIVO("debug.activar"),

    /**
     * Limite numero de elementos respuestas API Interna
     */
    API_MAX_LIMIT("api.max.limit")
    ;

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
