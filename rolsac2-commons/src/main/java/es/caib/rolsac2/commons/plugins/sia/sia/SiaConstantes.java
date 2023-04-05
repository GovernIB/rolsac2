package es.caib.rolsac2.commons.plugins.sia.sia;

public class SiaConstantes {

    public static final Integer SIAJOB_ESTADO_CREADO = 0;
    public static final Integer SIAJOB_ESTADO_EN_EJECUCION = 1;
    public static final Integer SIAJOB_ESTADO_ENVIADO = 2;
    public static final Integer SIAJOB_ESTADO_ENVIADO_CON_ERRORES = 3;
    public static final Integer SIAJOB_ESTADO_ERROR_GRAVE = -1;

    public static final Integer SIAPENDIENTE_ESTADO_CREADO = 0;
    public static final Integer SIAPENDIENTE_ESTADO_CORRECTO = 1;
    public static final Integer SIAPENDIENTE_ESTADO_INCORRECTO = -1;
    public static final Integer SIAPENDIENTE_ESTADO_NO_CUMPLE_DATOS = -2;

    public static final String SIAPENDIENTE_TIPO_PROCEDIMIENTO = "PROC";
    public static final String SIAPENDIENTE_TIPO_SERVICIO = "SERV";
    // Se quitan por los nuevos cambios: public static final String
    // SIAPENDIENTE_TIPO_UNIDAD_ADMINISTRATIVA = "UA";
    // Se quitan por los nuevos cambios: public static final String
    // SIAPENDIENTE_TIPO_NORMATIVA = "NORM";

    public static final String ESTADO_BAJA = "B";
    public static final String ESTADO_ALTA = "A";
    public static final String ESTADO_MODIFICACION = "M";
    public static final String ESTADO_REACTIVACION = "AC";

    public static final Integer TIPOLOGIA_INTERNO_COMUN = 1;
    public static final Integer TIPOLOGIA_INTERNO_ESPECIFICO = 2;
    public static final Integer TIPOLOGIA_EXTERNO_COMUN = 3;
    public static final Integer TIPOLOGIA_EXTERNO_ESPECIFICO = 4;

    public static final Integer TIPO_TRAMITE_PROC = 1;

    public static final String TRAMITE_PROC = "P";
    public static final String TRAMITE_SERV = "S";

    public static final String SI = "S";
    public static final String NO = "N";

    /*
     * public static final String SIAPDT_ESTADO_INDEXAR = 0; public static final
     * String SIAPDT_ESTADO_DESINDEXAR = 1;
     */
    private static final String ERROR_MESSAGE = "Error obteniendo la propiedad ";

    public static final Integer SIAPENDIENTE_PROCEDIMIENTO_EXISTE = 1;
    public static final Integer SIAPENDIENTE_PROCEDIMIENTO_BORRADO = 0;
    public static final Integer SIAPENDIENTE_SERVICIO_EXISTE = 1;
    public static final Integer SIAPENDIENTE_SERVICIO_BORRADO = 0;

    /** El nombre cuando se crea el job de quartz. **/
    public static final String SIA_JOB_QUARTZ_NAME = "siaJob";
}
