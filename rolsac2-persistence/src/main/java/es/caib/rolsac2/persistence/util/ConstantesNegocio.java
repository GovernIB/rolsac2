package es.caib.rolsac2.persistence.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConstantesNegocio {

    /**
     * Workflow con el valor de definitivo.
     **/
    public static final Integer WORKFLOW_DEFINITIVO = 0;

    /**
     * Workflow con el valor de workflow estado publicado.
     **/
    public static final String WORKFLOW_ESTADO_PUBLICADO = "PUB";

    /**
     * Workflow con el valor de workflow estado eliminado.
     **/
    public static final String WORKFLOW_ESTADO_ELIMINADO = "ELI";

    /**
     * Constante oficial de separacion.
     **/
    public static final String SEPARADOR = "###";

    /**
     * Parámetro con el tiempo de bloqueo
     */
    public static final String TIEMPO_BLOQUEO_ENTIDAD = "siac.bloqueo.entidad";

    /**
     * Parámetro con el endpoint del cliente del servicio SALT de traducción
     */
    public static final String PARAMETRO_SALT_CLIENTE_ENDPOINT = "salt.cliente.endpoint";

    /**
     * Parámetro con el usuario del cliente del servicio SALT de traducción
     */
    public static final String PARAMETRO_SALT_CLIENTE_USUARIO = "salt.cliente.usuario";

    /**
     * Parámetro con la contraseña del cliente del servicio SALT de traducción
     */
    public static final String PARAMETRO_SALT_CLIENTE_PASSWORD = "salt.cliente.password";

    /**
     * Parámetro con el timeout del cliente del servicio SALT de traducción
     */
    public static final String PARAMETRO_SALT_CLIENTE_TIMEOUT = "salt.cliente.timeout";


    /**
     * Tipos de valores en personas.
     **/
    public static final String PERSONA_TIPO_IDENTIFICACION = "PE_TIPO_IDENTIFICACION";
    public static final String PERSONA_TIPO_VISIBILIDAD = "PE_VISIBILIDAD";
    public static final String PERSONA_TIPO_PERSONAL = "PE_TIPO_PERSONAL";

    /**
     * Tipos de valores en entidades SuscripcionCiudadano.
     ***/
    public static final String SUSCRIPCION_TIPO_SUSCRIPCION = "SU_TIPO_SUSCRIPCION";

    /**
     * Tipos de actualizacion en entidades Instancia.
     ***/
    public static final String IN_TIPO_ACTUALIZACION = "IN_INSTWF_TIPO_ACTUALIZACION";


    public static final String PETICION_TIPO_PRIORIDAD = "IN_PETICI_PRIORIDAD";
    public static final String GRUPOTITULO_TIPO_TITULACION = "EP_TIPO_TITULACION_EMPLEO_PUBLICO";
    public static final String GRUPOTITULO_TIPO_GRUPO = "EP_TIPO_GRUPO_EMPLEO_PUBLICO";

    public static final String APUNTESCONSULTA_TIPO_ACTUALIZACION = "GE_APCOWF_ACTUALIZACION";

    /**
     * Tipos valores en chat.
     **/
    public static final String CHAT_HORARIO_INICIO = "chat.horario.inicio";
    public static final String CHAT_HORARIO_FIN = "chat.horario.fin";
    public static final String CHAT_FECHA_VERANO_INICIO = "chat.horario.verano.inicio";
    public static final String CHAT_FECHA_VERANO_FIN = "chat.horario.verano.fin";
    public static final String CHAT_ALIVE_CIUDADANO = "chat.alive.ciudadano";
    public static final String CHAT_ALIVE_OPERADOR = "chat.alive.operador";

    public static final String PERSONA_EXPORT = "PE_EXPORT";
    public static final String PERSONA_EXPORT_INFORMADOR = "PE_EXPORT_INFO";
    public static final String PERSONAS_DOMINIOS_CORREO = "GE_DOMINIO_EMAIL_PERMITIDO";
    public static final String PERSONAS_DOMINIOS_PUBLICOS_CORREO = "GE_DOMINIO_EMAIL_PUBLICO_PERMITIDO";


    public static final String EXPORT_UGEP = "UGEP_EXPORT";


    /**
     * Tipos valores en organos.
     **/
    public static final String ORGANO_TIPO_ORGANO_REGISTRO = "OR_REGISTRO";
    public static final String ORGANO_TIPO_COLEGIADO = "OR_COLEGIADO";
    public static final String ORGANO_TIPO_SERVICIO_TERRITORIAL = "OR_SERVICIO_TERRITORIAL";
    public static final String ORGANO_REGLAMENTO_TIPO = "OR_REGLAMENTO_TIPO";
    public static final String ORGANO_REGLAMENTO_TIPO_CREACION = "CREACION";
    public static final String ORGANO_REGLAMENTO_TIPO_ELIMINACION = "ELIMINACION";
    public static final String ORGANO_REGLAMENTO_TIPO_MODIFICACION = "MODIFICACION";
    public static final String ORGANO_EXPORT = "OR_EXPORT";
    public static final String ORGANO_EXPORT_INFORMADOR = "OR_EXPORT_INFO";
    public static final String ORGANO_DOMINIOS_PUBLICOS_CORREO = "OR_DOMINIO_EMAIL_PUBLICO_PERMITIDO";
    public static final String ORGANO_TIPO_TRATAMIENTO = "OR_TRATAMIENTO";

    /**
     * Tiops de valores en administraciones locales / entidades.
     **/
    public static final String ENTIDAD_CONTACTO_TIPO_TELEFONO = "T";
    public static final String ENTIDAD_CONTACTO_TIPO_TELEFONO2 = "C";
    public static final String ENTIDAD_CONTACTO_TIPO_TELEFONO3 = "O";
    public static final String ENTIDAD_CONTACTO_TIPO_URL = "U";
    public static final String ENTIDAD_CONTACTO_TIPO_EMAIL = "E";
    public static final String ENTIDAD_CONTACTO_TIPO_FAX = "F";

    /**
     * Tipos de valores en procedimientos.
     **/
    public static final String DOCUMENTOS_TIPO_AUTENTICIDAD = "GE_TIPO_DOCUMENTO_AUTENTICIDAD";

    /**
     * Tipos de valores en entidades locales.
     ***/
    public static final String ENTIDAD_LOCAL_TIPO_PRU = "OR_EELL_TIPO_PRU";
    public static final String ENTIDAD_LOCAL_TIPO_REGISTRO = "OR_TIPO_REGISTRO_EELL";

    /**
     * Tipos de valores en procedimientos.
     **/
    public static final String PROCEDIMIENTOS_TIPO_INTERNO = "PR_TIPO_INTERNO";
    public static final String PROCEDIMIENTOS_TIPO_MATERIA = "PR_MATERIATIPO";
    public static final String PROCEDIMIENTOS_TIPO_ENLACE_LISTA = "PR_ENLACELISTA";
    public static final String PROCEDIMIENTOS_TIPO_NORMATIVA = "PR_TIPO_NORMATIVA";
    public static final String PROCEDIMIENTOS_TIPO_RECURSOS = "PR_TIPO_RECURSOS";
    public static final String PROCEDIMIENTOS_TIPO_FASE = "PR_FASES_PROCEDIMIENTO";
    public static final String PROCEDIMIENTOS_TIPO_MEDIO = "PR_TIPO_MEDIO";
    public static final String PROCEDIMIENTOS_TIPO_ACTUALIZACION = "PR_TIPO_ACTUALIZACION";
    public static final String PROCEDIMIENTOS_TIPO_APLICACION_GESTORA = "PR_TIPO_APLICACION_GESTORA";
    public static final String PROCEDIMIENTOS_TIPO_SILENCIOS = "PR_TIPO_SILENCIOS";
    public static final String PROCEDIMIENTOS_SOLICITUD_HORA_INICIO = "procedimiento.solicitud.horario.inicio";
    public static final String PROCEDIMIENTOS_SOLICITUD_HORA_FIN = "procedimiento.solicitud.horario.fin";
    public static final String PROCEDIMIENTOS_EXPORT = "PR_EXPORT";

    /**
     * Tipos valores en empleo público.
     **/
    public static final String EMPLEO_PUBLICO_TIPO_CONVOCATORIA = "EP_TIPO_CONVOCATORIA_EMPLEO_PUBLICO";
    public static final String EMPLEO_PUBLICO_TIPO_TITULACION = "EP_TIPO_TITULACION_EMPLEO_PUBLICO";
    public static final String EMPLEO_PUBLICO_TIPO_GRUPO = "EP_TIPO_GRUPO_EMPLEO_PUBLICO";
    public static final String EMPLEO_PUBLICO_TIPO_PRUEBA = "EP_TIPO_PRUEBA_EMPLEO_PUBLICO";
    public static final String EMPLEO_PUBLICO_TIPO_MEDIO = "EP_TIPO_MEDIO_PUBLICACION_EMPLEO_PUBLICO";
    public static final String EMPLEO_PUBLICO_TIPO_MEDIO_GVA = "EP_TIPO_MEDIO_PUBLICACION_EMPLEO_PUBLICO_GVA";
    public static final String EMPLEO_PUBLICO_ETAPA_TIPO_ENLACE = "EP_ETAPA_TIPO_ENLACE";

    /**
     * Parámetro con el appid del cliente del servicio LDAP
     */
    public static final String PARAMETRO_LDAP_CLIENTE_APPID = "ldap.cliente.appid";

    /**
     * Parámetro con el endpoint del cliente del servicio LDAP
     */
    public static final String PARAMETRO_LDAP_CLIENTE_ENDPOINT = "ldap.cliente.endpoint";

    /**
     * Parámetro con el timeout del cliente del servicio LDAP
     */
    public static final String PARAMETRO_LDAP_CLIENTE_TIMEOUT = "ldap.cliente.timeout";

    /**
     * Parámetro con el logCalls del cliente del servicio LDAP
     */
    public static final String PARAMETRO_LDAP_CLIENTE_LOGCALLS = "ldap.cliente.logCalls";

    /**
     * Tipo Valor asociado a la Visibilidad Oculata de Personas
     */
    public static final long TIPO_VALOR_PE_VISIBILIDAD_OCULTA = 13;

    /**
     * Tipo Valor asociado al Tipo de Persona Interna de Personas
     */
    public static final long TIPO_VALOR_PE_TIPO_PERSONAL_INTERNO = 4;

    /**
     * Tipos de valores para incidencia de información y oficina
     **/
    public static final String INCIDENCIA_TIPO_CATEGORIA = "IC_INCIDENCIA_TIPO_CATEGORIA";
    public static final String INCIDENCIA_TIPO_PRIORIDAD = "IC_INCIDENCIA_TIPO_PRIORIDAD";

    public static final String INCIDENCIA_TIPO_CLASIFICACION_INCIDENCIA = "IC_INCIDENCIA_TIPO_CLASIFICACION_INCIDENCIA";
    public static final String INCIDENCIA_TIPO_CLASIFICACION_INCIDENCIA_INFORMACION = "IC_INCIDENCIA_TIPO_CLASIFICACION_INFORMACION";
    public static final String INCIDENCIA_TIPO_CLASIFICACION_INCIDENCIA_OFICINA = "IC_INCIDENCIA_TIPO_CLASIFICACION_OFICINA";

    // Tipos de temas e incidencias para Comunicados e Incidencias
    public static final String COMUNICADO_INCIDENCIA_TIPO_TEMA = "CO_IC_TIPO_TEMA";
    public static final String COMUNICADO_INCIDENCIA_TIPO_CONTENIDO = "CO_IC_TIPO_CONTENIDO";


    // Constantes nombres reports
    public static final String REPORT_LISTADO_PERSONAS = "listPersonas";
    public static final String REPORT_DETALLE_PERSONAS = "detallePersona";
    public static final String REPORT_DETALLE_PROCEDIMIENTOS = "detalleProcedimiento";
    public static final String REPORT_DETALLE_OPOSICION = "detalleOposicion";
    public static final String REPORT_LISTADO_REGLAMENTOS = "listReglamentos";
    public static final String REPORT_LISTADO_INMUEBLES = "listInmuebles";
    public static final String REPORT_LISTADO_PERSONASCOMETIDO = "listPersonasCometido";
    public static final String REPORT_LISTADO_COMETIDOSPERSONA = "listCometidosPersona";
    public static final String REPORT_DETALLE_DEPARTAMENTOS = "detalleOrgano";
    public static final String REPORT_LISTADO_DEPARTAMENTOS = "listDepartamentos";
    public static final String REPORT_LISTADO_ALFABETICOORGANOS = "listAlfabeticoOrganos";
    public static final String REPORT_LISTADO_ALFABETICOPERSONAS = "listAlfabeticoPersonas";
    public static final String REPORT_LISTADO_PERSONASRESPONSABLES = "listPersonasResponsables";
    public static final String REPORT_LISTADO_PERSONASCORREOS = "listPersonasCorreos";
    public static final String REPORT_LISTADO_REGISTROS = "listRegistros";
    public static final String REPORT_DETALLE_REGISTROS = "detalleRegistro";
    public static final String REPORT_LISTADO_PERSONASORGANO = "listPersonasOrgano";
    public static final String REPORT_LISTADO_PERSONASORGANOSCERRADOS = "listPersonasOrganosCerrados";
    public static final String REPORT_LISTADO_ORGANOS_RESUMIDO = "listOrganosResumido";
    public static final String REPORT_LISTADO_APUNTE_CONSULTA = "listApuntesConsulta";
    public static final String REPORT_LISTADO_PREGUNTAS_FRECUENTES = "listPreguntasFrecuentes";
    public static final String REPORT_LISTADO_GRUPO_PREGUNTAS = "listGrupoPreguntas";
    public static final String REPORT_LISTADO_FORMULARIOS = "listFormularios";
    public static final String REPORT_LISTADO_PETICIONES_FORMULARIOS = "listPeticionesFormularios";
    public static final String REPORT_DETALLE_ADMINLOCALES = "detalleAdminLocales";
    public static final String REPORT_LISTADO_ADMINISTRACIONES_LOCALES = "listadoAdministracionesLocales";
    public static final String REPORT_LISTADO_COMETIDOS = "listCometidos";
    public static final String REPORT_LISTADO_OPERADORES = "listOperadores";
    public static final String REPORT_LISTADO_ENCUESTAS = "listEncuestas";
    public static final String REPORT_LISTADO_INCIDENCIAS_INFORMACION = "listIncidenciasInformacion";
    public static final String REPORT_LISTADO_INCIDENCIAS_OFICINA = "listIncidenciasOficina";
    public static final String REPORT_LISTADO_COMUNICADOS = "listComunicados";
    public static final String REPORT_LISTADO_PROCEDIMIENTOS = "listProcedimientos";
    public static final String REPORT_LISTADO_TIPOSFASES = "listTiposFases";
    public static final String REPORT_LISTADO_ENVIOS = "listEnvios";
    public static final String REPORT_LISTADO_GRUPOS_TITULACION = "listGruposTitulacion";
    public static final String REPORT_LISTADO_CODIFICACION_FUNCIONAL = "listCodificacionFuncional";
    public static final String REPORT_LISTADO_PLANTILLAS_CHAT = "listPlantillasChat";
    public static final String REPORT_LISTADO_TIPOSDOCUMENTO = "listTiposDocumento";
    public static final String REPORT_LISTADO_TIPOS_INCIDENCIAS = "listTiposIncidencias";
    public static final String REPORT_LISTADO_TIPOS_ORGANOS = "listTiposOrganos";
    public static final String REPORT_LISTADO_GRUPOS_PERMISOS = "listGruposPermisos";
    public static final String REPORT_LISTADO_TAXONOMIAS_PORTAL = "listTaxonomiasPortal";
    public static final String REPORT_LISTADO_EMPLEO_PUBLICO = "listEmpleoPublico";
    public static final String REPORT_LISTADO_OFICINAINFORMACION = "listOficinaInformacion";
    public static final String REPORT_LISTADO_OTROS_ORGANISMOS = "listOtrosOrganismos";
    public static final String REPORT_LISTADO_UGEP = "listUgep";
    public static final String REPORT_LISTADO_UNIDADES_ADMINISTRATIVAS = "listUnidadesAdministrativas";
    public static final String REPORT_LISTADO_OFICINASDIR3 = "listOficinasDir3";
    public static final String REPORT_DETALLE_INCIDENCIA_INFORMACION = "detalleIncidenciaInformacion";
    public static final String REPORT_DETALLE_INCIDENCIA_OFICINA = "detalleIncidenciaOficina";
    public static final String REPORT_LISTADO_OTRAS_ADMIN_PROCEDIMIENTOS = "listProcedimientosOtrasAdmin";
    public static final String REPORT_LISTADO_NOVEDADES = "listNovedades";
    public static final String REPORT_LISTADO_ORGANOS_DEPARTAMENTO = "listOrganosDepartamento";
    public static final String REPORT_LISTADO_ESTADISTICAS = "listEstadisticas";

    // Constantes tipo contenido para exportaciones
    public static final String REPORT_TIPO_CSV = "application/csv; charset=ISO-8859-1";
    public static final String REPORT_TIPO_PDF = "application/pdf";
    public static final String REPORT_TIPO_TXT = "text/plain";

    public static final String REPORT_NOMBRE_LISTADO = "Listado";
    public static final String REPORT_NOMBRE_DETALLE = "Detalle";
    public static final String REPORT_NOMBRE_LISTADO_SEPARADOR = "_";

    public static final String ARBOL_TEMATICO_CODIFICACION_FUNCIONAL = "arboltematico_codificacion_funcional";

    // Contantes para parámetros de reporte
    public static final String REPORT_PARAM_IDIOMA = "idioma";
    public static final String REPORT_PARAM_TIPOS_ORGANOS = "listaTiposOrganos";
    public static final String REPORT_PARAM_TIPOS_INCIDENCIAS = "listaTiposIncidencias";
    public static final String REPORT_PARAM_FILTRO_DESCRIPCION = "filtroDescripcion";
    public static final String REPORT_PARAM_TIPOSDOCUMENTO = "listaTiposDocumentos";
    public static final String REPORT_PARAM_INCIDENCIAS_INFORMACION = "listaIncidenciasInformacion";
    public static final String REPORT_PARAM_EMPLEO_PUBLICO = "listaEmpleoPublico";
    public static final String REPORT_PARAM_UNIDADES_ADMINISTRATIVAS = "listaUnidadesAdministrativas";

    private static final String NOMBRE = "NOMBRE";
    // Constantes cabeceras csv
    public static final String[] CABECERA_ARBOL_ORGANOS_ORG_ES = {ConstantesNegocio.NOMBRE, "CODO", "CÓDIGO"};
    public static final String[] CABECERA_ARBOL_ORGANOS_ORG_VA = {"NOM", "CODO", "CODI"};
    public static final String[] CABECERA_ARBOL_ORGANOS_FUN_ES = {ConstantesNegocio.NOMBRE, "CODF", "CÓDIGO"};
    public static final String[] CABECERA_ARBOL_ORGANOS_FUN_VA = {"NOM", "CODF", "CODI"};

    public static final String[] CABECERA_PERSONAS_ES = {ConstantesNegocio.NOMBRE, "APELLIDOS", "TELÉFONO", "FAX", "PLANTA", "DESPACHO", "ÓRGANO", "CORREO"};
    public static final String[] CABECERA_PERSONAS_VA = {"NOM", "COGNOMS", "TELÈFON", "FAX", "PLANTA", "DESPATX", "ÓRGAN", "CORREU"};

    public static final DateFormat FECHA_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // constantes para consultas SQL
    public static final String LITERAL_AND = " AND ";
    public static final String LITERAL_OR = " OR ";
    public static final String LITERAL_ORDERBY = " ORDER BY ";
    public static final String LITERAL_ASC = " ASC ";
    public static final String LITERAL_DESC = " DESC ";

    // Constantes para parámetross de auditorias
    public static final String AUDITORIA_PARAMETRO_CODIGO = "codigo";
    public static final String AUDITORIA_SELECT_COUNT = " SELECT count(P) ";
    public static final String AUDITORIA_SELECT_BASE_GRID = " SELECT P.codigo, P.fechaAuditoria, P.usuarioAuditoria";
    public static final String AUDITORIA_SELECT_CAMPO_RESPONSABLE = ", P.usuarioResponsable";
    public static final String AUDITORIA_SELECT_CAMPO_CAMBIOPUESTO = ", P.cambioPuesto ";
    public static final Object AUDITORIA_SELECT_CAMPO_ETAPAS = ", 0, 0, TETRAD.descripcion";

    // Constantes para parametros estado sesion de chat
    public static final int CHAT_ACTIVO = 1;
    public static final int CHAT_FIN_OPERADOR = 2;
    public static final int CHAT_ESPERA = 3;
    public static final int CHAT_REENVIO_OPERADOR = 4;
    public static final int CHAT_FIN_CIUDADANO = 5;
    public static final int CHAT_NO_OPERADOR = 6;
    public static final int CHAT_COLA_ESPERA_LLENA = 7;
    public static final int CHAT_FIN_ALIVE = 8;
    // Caso especial necesario para mostrar distintos estados en la vista
    public static final int CHAT_ABIERTO = 9;

    // Constantes para parametros tipo mensaje de chat
    public static final int MENSAJE_SISTEMA = 0;
    public static final int MENSAJE_INFORMADOR = 1;
    public static final int MENSAJE_CIUDADANO = 2;


    // Constantes para parametros estado sesion de chat


    // constantes para consultas SQL
    public static final String CHAT_PT_BIENVENIDA = "CHAT_PT_BIENVENIDA";
    public static final String CHAT_PT_CIERRE_INF = "CHAT_PT_CIERRE_INF";
    public static final String CHAT_PT_EN_ESPERA = "CHAT_PT_EN_ESPERA";
    public static final String CHAT_PT_ERR_TECNICO = "CHAT_PT_ERR_TECNICO";
    public static final String CHAT_PT_FUERA_HORA = "CHAT_PT_FUERA_HORA";
    public static final String CHAT_PT_SIN_INFOR = "CHAT_PT_SIN_INFOR";
    public static final String CHAT_PT_SATURACION = "CHAT_PT_SATURACION";
    public static final String CHAT_PT_TRANSFERIDO = "CHAT_PT_TRANSFERIDO";
    public static final String CHAT_PT_ALIVE = "CHAT_PT_ALIVE";
    public static final String CHAT_PT_DESPEDIDA = "CHAT_PT_DESPEDIDA";

    // Tipos de Valor para Envíos
    public static final String ENVIOS_TIPO_ENVIOS = "EV_ENVIOS_TIPO_MEDIO_ENVIO";

    // Constantes para tipos de actualización Novedades
    public static final String TIPO_ACTUALIZACION_APUNTE_CONSULTA_IDETIP = "GE_APCOWF_ACTUALIZACION";
    public static final String TIPO_ACTUALIZACION_APUNTE_CONSULTA_NUEVO = "Nuevo";
    public static final String TIPO_ACTUALIZACION_APUNTE_CONSULTA_ABRE_PLAZO = "Abre Plazo";
    public static final String TIPO_ACTUALIZACION_APUNTE_CONSULTA_MODIFICACION_FORMAL = "Modificación formal";

    // Constantes para tipos de actualización EP
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_IDETIP = "EP_TIPO_ACTUALIZACION";
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_NUEVO = "NUEVO";
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_ABRE_PLAZO = "ABRE_PLAZO";
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_MODIFICACION_SUSTANCIAL = "MODIFICACION_SUSTANCIAL";
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_MODIFICACION_FORMAL = "MODIFICACION_FORMAL";
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_ALTA_EXIGENCIA = "ALTA_EXIGENCIA";
    public static final String TIPO_ACTUALIZACION_EP_CONSULTA_TRAMITABLE_TELEMATICAMENTE = "TRAMITABLE_TELEMATICAMENTE";

    // Constantes para tipos de actualización EP
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_IDETIP = "PR_TIPO_ACTUALIZACION";
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_NUEVO = "NUEVO";
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_ABRE_PLAZO = "ABRE_PLAZO";
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_INFORMACION_INTERES = "INFORMACION_INTERES";
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_MODIFICACION_FORMAL = "MODIFICACION_FORMAL";
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_SUSPENSION_PLAZO = "SUSPENSION_PLAZO";
    public static final String TIPO_ACTUALIZACION_PR_CONSULTA_REANUDACION_PLAZO = "REANUDACION_PLAZO";

    // Constante para construir la url portal para guc-listados
    public static final String URL_PORTAL_ES = "http://www.gva.es/es/inicio/procedimientos?id_proc=";
    public static final String URL_PORTAL_VA = "http://www.gva.es/va/inicio/procedimientos?id_proc=";
    public static final String URL_PORTAL_2 = "&version=amp";

    // Constante para el parámetro de dias para la fecha de borrado por defecto
    public static final String DIAS_COMUNICADO_FECHA_BORRADO = "siac.comunicados.diasBorrado";

    // Constantes para Boletín de Novedades (suscripciones)
    public static final String BOLETIN_TIPO_EMPLEO_PUBLICO = "SU_TIPO_EP";
    public static final String BOLETIN_TIPO_PROCEDIMIENTOS = "SU_TIPO_PR";

    // Constantes para los parametros en el envio de emails de alertas
    public static final String ALERTAS_COMETIDOS_EMAIL_CONTENIDO = "proceso.alertas.cometidos.email.contenido";
    public static final String ALERTAS_COMETIDOS_EMAIL_ASUNTO = "proceso.alertas.cometidos.email.asunto";
    public static final String ALERTAS_COMETIDOS_EMAIL_REMITENTE = "proceso.alertas.cometidos.email.remitente";


    //Constantes pra los parametros en el proceso de purga
    public static final String NUM_DIAS_PURGA_CHAT = "proceso.purga.chats.dias";
    public static final String NUM_DIAS_PURGA_LOGS = "proceso.purga.logs.dias";
    public static final String FICHEROS_MAX_PURGA_FICHEROS = "proceso.purga.ficheros.max";
    public static final String BLOQUE_PURGA_FICHEROS = "proceso.purga.ficheros.bloque";
    public static final String NUM_DIAS_PURGA_FICHEROS = "proceso.purga.ficheros.dias";

    // Constantes para los DAO
    public static final String PARAMETRO_CODIGO_NIVEL_ADM_AGE = "1";
    public static final String PARAMETRO_CODIGO_NIVEL_ADM_AUT = "2";
    public static final String PARAMETRO_CODIGO_NIVEL_ADM_LOC = "3";
    public static final String PARAMETRO_CODIGO_NIVEL_ADM_UNI = "4";
    public static final String PARAMETRO_CODIGO_CV = "10";

    //Constantes para los estados de una UA
    public static final String UNIDADADMINISTRATIVA_ESTADO_BORRADOR = "B";
    public static final String UNIDADADMINISTRATIVA_ESTADO_VIGENTE = "V";
    public static final String UNIDADADMINISTRATIVA_ESTADO_EVOLUCIONADA = "E";
    public static final String UNIDADADMINISTRATIVA_ESTADO_BORRADA = "X";

    /**
     * Constructor vacio.
     **/
    private ConstantesNegocio() {
        // Constructor vacio
    }
}
