package es.caib.rolsac2.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constantes {
    public final static List<String> IDIOMAS = new ArrayList<>(Arrays.asList("es", "ca"));
    public final static String IDIOMA_ESPANYOL = "es";
    public final static String IDIOMA_CATALAN = "ca";

    // Campos auditoria persona
    public static final String PERSONA_IDENTIFICADOR = "auditoria.personas.identificador";

    public static final String PROCEDIMIENTO = "P";
    public static final boolean PROCEDIMIENTO_PUBLICADO = false;
    public static final boolean PROCEDIMIENTO_ENMODIFICACION = true;
    public static final String SERVICIO = "S";

}
