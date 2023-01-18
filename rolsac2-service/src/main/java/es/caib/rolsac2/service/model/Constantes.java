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

    // Campos auditoria procedimiento
    public static final String PROCEDIMIENTO_IDENTIFICADOR = "auditoria.procedimiento.identificador";
    public static final String PROCEDIMIENTO_INTERNO = "auditoria.procedimiento.interno";
    public static final String PROCEDIMIENTO_FECHASIA = "auditoria.procedimiento.fechaSIA";
    public static final String PROCEDIMIENTO_CODIGOSIA = "auditoria.procedimiento.codigoSIA";
    public static final String PROCEDIMIENTO_ESTADOSIA = "auditoria.procedimiento.estadoSIA";
    public static final String PROCEDIMIENTO_FECHACADUCIDAD = "auditoria.procedimiento.fechaCaducidad";
    public static final String PROCEDIMIENTO_FECHAPUBLICACION = "auditoria.procedimiento.fechaPublicacion";
    public static final String PROCEDIMIENTO_FECHAACTUALIZACION = "auditoria.procedimiento.fechaActualizacion";
    public static final String PROCEDIMIENTO_TIPOLEGITIMACION = "auditoria.procedimiento.tipoLegitimacion";
    public static final String PROCEDIMIENTO_UARESPONSABLE = "auditoria.procedimiento.uaResponsable";
    public static final String PROCEDIMIENTO_UARINSTRUCTOR = "auditoria.procedimiento.uaInstructor";
    public static final String PROCEDIMIENTO_FORMAINICIO = "auditoria.procedimiento.formaInicio";
    public static final String PROCEDIMIENTO_RESPONSABLE = "auditoria.procedimiento.responsable";
    public static final String PROCEDIMIENTO_TAXA = "auditoria.procedimiento.taxa";
    public static final String PROCEDIMIENTO_TIPOSILENCIO = "auditoria.procedimiento.tipoSilencio";
    public static final String PROCEDIMIENTO_TIPOPROCEDIMIENTO = "auditoria.procedimiento.tipoProcedimiento";
    public static final String PROCEDIMIENTO_COMUN = "auditoria.procedimiento.comun";
    public static final String PROCEDIMIENTO_TIPOVIA = "auditoria.procedimiento.tipoVIA";
    public static final String PROCEDIMIENTO_NOMBRE = "auditoria.procedimiento.nombre";
    public static final String PROCEDIMIENTO_LOPDRESPONABLE = "auditoria.procedimiento.lopdResponsable";
    public static final String PROCEDIMIENTO_LOPDFINALIDAD = "auditoria.procedimiento.lopdfinalidad";
    public static final String PROCEDIMIENTO_LOPDDESTINATARIO = "auditoria.procedimiento.lopddestinatario";
    public static final String PROCEDIMIENTO_LOPDDERECHOS = "auditoria.procedimiento.lopdderechos";
    public static final String PROCEDIMIENTO_REQUISITOS = "auditoria.procedimiento.requisitos";
    public static final String PROCEDIMIENTO_OBJETO = "auditoria.procedimiento.objeto";
    public static final String PROCEDIMIENTO_DESTINATARIOS = "auditoria.procedimiento.destinatarios";
    public static final String PROCEDIMIENTO_TERMINORESOLUCION = "auditoria.procedimiento.terminoresolucion";
    public static final String PROCEDIMIENTO_OBSERVACIONES = "auditoria.procedimiento.observaciones";
    public static final String PROCEDIMIENTO_INFOADICIONAL = "auditoria.procedimiento.infoAdicional";
    public static final String PROCEDIMIENTO_HABILITADOAPODERADO = "auditoria.procedimiento.habilitadoApoderado";
    public static final String PROCEDIMIENTO_HABILITADOFUNCIONARIO = "auditoria.procedimiento.habilitadoFuncionario";
    public static final String PROCEDIMIENTO_PUBLICOOBJETIVO = "auditoria.procedimiento.publicoObjetivo";
    public static final String PROCEDIMIENTO_MATERIASIA = "auditoria.procedimiento.materiaSIA";
    public static final String PROCEDIMIENTO_DOCUMENTOS = "auditoria.procedimiento.documentos";
    public static final String PROCEDIMIENTO_DOCUMENTOSLOPD = "auditoria.procedimiento.documentosLOPD";
    public static final String PROCEDIMIENTO_NORMATIVAS = "auditoria.procedimiento.normativas";
    public static final String PROCEDIMIENTO_TRAMITES = "auditoria.procedimiento.tramites";

    public static final String PROCEDIMIENTO = "P";
    public static final boolean PROCEDIMIENTO_PUBLICADO = false;
    public static final boolean PROCEDIMIENTO_ENMODIFICACION = true;
    public static final String SERVICIO = "S";

}
