/**
 *
 */
package es.caib.rolsac2.service.model.auditoria;

/**
 * @author Indra
 */
public final class ConstantesAuditoria {

    private ConstantesAuditoria() {
        super();
    }

    public static final String PETICION_FORMULARIO_PETICI_TIPPRI = "auditoria.peticiones.codigotipoprioridad";
    public static final String PETICION_FORMULARIO_PETICI_DESCRI = "auditoria.peticiones.descripcion";
    public static final String PETICION_FORMULARIO_PETICI_FCENT = "auditoria.peticiones.fechaentrada";
    public static final String PETICION_FORMULARIO_PETICI_FCSAL = "auditoria.peticiones.fechasalida";
    public static final String PETICION_FORMULARIO_PETICI_OBSER = "auditoria.peticiones.observaciones";
    public static final String PETICION_FORMULARIO_PETICI_FCALER = "auditoria.peticiones.fechaalerta";
    public static final String PETICION_FORMULARIO_PETICI_DSALER = "auditoria.peticiones.descalerta";
    public static final String PETICION_FORMULARIO_PETICI_CODPER = "auditoria.peticiones.codigopersona";
    public static final String PETICION_FORMULARIO_PETICI_NOMPER = "auditoria.peticiones.nombrecontacto";
    public static final String PETICION_FORMULARIO_PETICI_EMAPER = "auditoria.peticiones.emailcontacto";
    public static final String PETICION_FORMULARIO_PETICI_SRVPER = "auditoria.peticiones.servperscontacto";
    public static final String PETICION_FORMULARIO_PETICI_TELPER = "auditoria.peticiones.telperscontacto";
    public static final String PETICION_FORMULARIO_PETICI_FAXPER = "auditoria.peticiones.faxperscontacto";

    // Auditoría de personas
    public static final String PERSONA_ESTADOWF = "auditoria.personas.estadowf";

    // Auditoría de entidades locales
    public static final String ENTIDAD_LOCAL_ESTADOWF = "auditoria.entidadesLocales.estadowf";

    // Auditoría de órganos
    public static final String ORGANO_ORGWF_CODORG = "auditoria.organos.codigoorgano";
    public static final String ORGANO_ORGWF_WF = "auditoria.organos.workflow";
    public static final String ORGANO_ORGWF_WFEST = "auditoria.organos.estado";
    public static final String ORGANO_ORGWF_CODTORG = "auditoria.organos.tipoorgano";
    public static final String ORGANO_ORGWF_TIPREG = "auditoria.organos.tiporegistro";
    public static final String ORGANO_ORGWF_CODPADREO = "auditoria.organos.organopadre";
    public static final String ORGANO_ORGWF_CODRAIZO = "auditoria.organos.organoraiz";
    public static final String ORGANO_ORGWF_CODRAMAO = "auditoria.organos.ramaorganica";
    public static final String ORGANO_ORGWF_ORDENO = "auditoria.organos.ordenorganico";
    public static final String ORGANO_ORGWF_CODO = "auditoria.organos.codigoorganico";
    public static final String ORGANO_ORGWF_CODPADREF = "auditoria.organos.organopadrefuncional";
    public static final String ORGANO_ORGWF_CODRAMAF = "auditoria.organos.ramafuncional";
    public static final String ORGANO_ORGWF_ORDENF = "auditoria.organos.ordenfuncional";
    public static final String ORGANO_ORGWF_CODF = "auditoria.organos.codigofuncional";
    public static final String ORGANO_ORGWF_CODORAIZ = "auditoria.organos.codoraiz";
    public static final String ORGANO_ORGWF_CODIUBI = "auditoria.organos.inmueble";
    public static final String ORGANO_ORGWF_TLF = "auditoria.organos.telefonoexterno";
    public static final String ORGANO_ORGWF_FAX = "auditoria.organos.faxexterno";
    public static final String ORGANO_ORGWF_EMAIL = "auditoria.organos.email";
    public static final String ORGANO_ORGWF_CODDIR3 = "auditoria.organos.coddir3";
    public static final String ORGANO_ORGWF_FCMODI = "auditoria.organos.fechamodificacion";
    public static final String ORGANO_ORGWF_FCBAJA = "auditoria.organos.fechabaja";
    public static final String ORGANO_ORGWF_TLFINT = "auditoria.organos.telefonointerno";
    public static final String ORGANO_ORGWF_FAXINT = "auditoria.organos.faxinterno";
    public static final String ORGANO_ORGWF_SINDIR3 = "auditoria.organos.sindir3";
    public static final String ORGANO_ORGWF_TIPOCOL = "auditoria.organos.tipoorganocolegiado";
    public static final String ORGANO_ORGWF_TIPSTER = "auditoria.organos.tiposervicioterritorial";
    public static final String ORGANO_ORGWF_TIPGEN = "auditoria.organos.tipogenero";
    public static final String ORGANO_ORGWF_CODPER = "auditoria.organos.persona";
    public static final String ORGANO_ORGWF_MASTLF = "auditoria.organos.mastelefonos";
    public static final String ORGANO_ORGWF_ORDEN = "auditoria.organos.orden";
    public static final String ORGANO_ORGWF_VALIJA = "auditoria.organos.valija";
    public static final String ORGANO_ORGWF_PLANTA = "auditoria.organos.planta";
    public static final String ORGANO_ORGWF_REGORVE = "auditoria.organos.registroorve";
    public static final String ORGANO_ORGWF_REGFACE = "auditoria.organos.registroface";
    public static final String ORGANO_ORGWF_GUIACOM = "auditoria.organos.guiacom";
    public static final String ORGANO_ORGWF_CODEXT = "auditoria.organos.codigoexterno";
    public static final String ORGANO_ORGWF_WFOBS = "auditoria.organos.observaciones";
    public static final String ORGANO_ORGWF_NIF = "auditoria.organos.nif";
    public static final String ORGANO_ORGWF_OCULTO = "auditoria.organos.oculto";

    // Campos auditoria apunteconsulta
    public static final String APUNTECONSULTA_TIPOACTUALIZACION = "auditoria.apunteconsulta.tipoactualizacion";
    public static final String APUNTECONSULTA_DEPARTAMENTO = "auditoria.apunteconsulta.departamento";
    public static final String APUNTECONSULTA_CODIGOFUNCIONAL = "auditoria.apunteconsulta.codigofuncional";
    public static final String APUNTECONSULTA_LISTAENLACE = "auditoria.apunteconsulta.listaenlace";
    public static final String APUNTECONSULTA_FECHAINICIOVIGENCIA = "auditoria.apunteconsulta.fechainiciovigencia";
    public static final String APUNTECONSULTA_FECHAFINVIGENCIA = "auditoria.apunteconsulta.fechafinvigencia";
    public static final String APUNTECONSULTA_FECHAALERTA = "auditoria.apunteconsulta.fechaalerta";
    public static final String APUNTECONSULTA_DESCRIPCIONALERTA = "auditoria.apunteconsulta.descripcionalerta";
    public static final String APUNTECONSULTA_ESTADOWF = "auditoria.apunteconsulta.estadowf";

    // Campos auditoria apunteconsultatraduccion
    public static final String APUNTECONSULTATRADUCCION_DESCRIPCION = "auditoria.apunteconsultatraduccion.descripcion";
    public static final String APUNTECONSULTATRADUCCION_NOTAINTERNA = "auditoria.apunteconsultatraduccion.notainterna";

    // AuditoriaDTO de preguntas frecuentes
    public static final String PREGUNTA_PRGWF_CODORG = "auditoria.pregunta.codigoorgano";
    public static final String PREGUNTA_PRGWF_CODFUN = "auditoria.pregunta.codigofuncional";
    public static final String PREGUNTA_PRGWF_VISGUC = "auditoria.pregunta.visibleguc";
    public static final String PREGUNTA_PRGWF_VISPOR = "auditoria.pregunta.visibleportal";
    public static final String PREGUNTA_PRGWF_WFEST = "auditoria.pregunta.estadowrokflow";

    // AuditoriaDTO de preguntas frecuentes traducción
    public static final String PREGUNTA_PRGTRA_PREGUN = "auditoria.pregunta.pregunta";
    public static final String PREGUNTA_PRGTRA_RESPUE = "auditoria.pregunta.respuesta";

    // Auditoría de Instancias
    public static final String INSTANCIA_INSTWF_TIPACT = "auditoria.instancia.tipoactualizacion";
    public static final String INSTANCIA_INSTAN_INDVAL = "auditoria.instancia.validado";
    public static final String INSTANCIA_INSTWF_CODORG = "auditoria.instancia.codigoorgano";
    public static final String INSTANCIA_INSTWF_CODFUN = "auditoria.instancia.codigofuncional";
    public static final String INSTANCIA_INSTWF_EXTERN = "auditoria.instancia.instanciaexterna";
    public static final String INSTANCIA_INSTWF_GUARDA = "auditoria.instancia.instanciaguardable";
    public static final String INSTANCIA_INSTWF_PAGPIE = "auditoria.instancia.piepagina";
    public static final String INSTANCIA_INSTWF_PAGLAT = "auditoria.instancia.lateralpagina";
    public static final String INSTANCIA_INSTWF_PAGLOG = "auditoria.instancia.logospagina";
    public static final String INSTANCIA_INSTWF_OBSDIS = "auditoria.instancia.observaciondiseno";
    public static final String INSTANCIA_INSTWF_TRAMIT = "auditoria.instancia.tramite";
    public static final String INSTANCIA_INSTWF_EMPPUB = "auditoria.instancia.empleopublico";
    public static final String INSTANCIA_INSTWF_INTERN = "auditoria.instancia.interno";
    public static final String INSTANCIA_INSTWF_INISEG = "auditoria.instancia.inicioseguimiento";
    public static final String INSTANCIA_INSTWF_RELLEN = "auditoria.instancia.rellenable";
    public static final String INSTANCIA_INSTWF_VISIBL = "auditoria.instancia.visible";
    public static final String INSTANCIA_INSTWF_APAIS = "auditoria.instancia.apaisado";
    public static final String INSTANCIA_INSTWF_ENI = "auditoria.instancia.tipoENI";
    public static final String INSTANCIA_INSTWF_WFEST = "auditoria.instancia.estadoworkflow";
    public static final String INSTANCIA_INSTWF_INDBI = "auditoria.instancia.instanciabilingue";
    public static final String INSTANCIA_INSTWF_INDES = "auditoria.instancia.instanciaes";
    public static final String INSTANCIA_INSTWF_INDVA = "auditoria.instancia.instanciaval";
    public static final String INSTANCIA_INSTWF_INDEN = "auditoria.instancia.instanciaen";

    // Auditoría de Instancias traduccion
    public static final String INSTANCIA_INSTRA_DESCRI = "auditoria.instancia.descripcion";
    public static final String INSTANCIA_INSTRA_MODELO = "auditoria.instancia.modelo";
    public static final String INSTANCIA_INSTRA_NOTINT = "auditoria.instancia.notasinternas";
    public static final String INSTANCIA_INSTRA_ACLARA = "auditoria.instancia.aclaracion";

    // AuditoriaDTO de UGEP
    public static final String UGEP_CODIGOORGANORESPONSABLE = "auditoria.ugep.organoresponsable";
    public static final String UGEP_CODIGOINMUEBLE = "auditoria.ugep.inmueble";
    public static final String UGEP_FECHASUPRESION = "auditoria.ugep.fechasupresion";
    public static final String UGEP_TELEFONO = "auditoria.ugep.telefono";
    public static final String UGEP_FAX = "auditoria.ugep.fax";
    public static final String UGEP_EMAIL = "auditoria.ugep.email";
    public static final String UGEP_REGISTROENFASE = "auditoria.ugep.registroenface";

    // AuditoriaDTO de UGEP traduccion
    public static final String UGEP_TRADUCCION_DENOMINACION = "auditoria.ugeptraduccion.denominacion";
    public static final String UGEP_TRADUCCION_OBSERVACIONES = "auditoria.ugeptraduccion.observaciones";


    /*
     * ###################################### #### AuditoriaDTO de Procedimientos ##### ######################################
     */

    // AuditoriaDTO Fase Instruccion tramitacion
    public static final String PROCEDIMIENTO_FINSTRA_TRAMITEORGANO = "auditoria.procedimiento.finstra.tramiteorgano";
    public static final String PROCEDIMIENTO_FINSTRA_CRITERIOVALORACION = "auditoria.procedimiento.finstra.criteriovaloracion";
    public static final String PROCEDIMIENTO_FINSTRA_INFOTRAMITE = "auditoria.procedimiento.finstra.infotramite";
    public static final String PROCEDIMIENTO_FINSTRA_PRUEBAS = "auditoria.procedimiento.finstra.pruebas";
    public static final String PROCEDIMIENTO_FINSTRA_OBSERVACIONES = "auditoria.procedimiento.finstra.observaciones";

    // AuditoriaDTO Fase Instruccion alegacion
    public static final String PROCEDIMIENTO_FINSALE_PLAZO = "auditoria.procedimiento.finsale.plazo";
    public static final String PROCEDIMIENTO_FINSALE_OBJETO = "auditoria.procedimiento.finsale.objeto";
    public static final String PROCEDIMIENTO_FINSALE_PRESENTACION = "auditoria.procedimiento.finsale.presentacion";
    public static final String PROCEDIMIENTO_FINSALE_DOCUMENTACION = "auditoria.procedimiento.finsale.documentacion";

    // AuditoriaDTO Fase Iniciacion Solucion
    public static final String PROCEDIMIENTO_FINISOL_OBJETO = "auditoria.procedimiento.finisol.objeto";
    public static final String PROCEDIMIENTO_FINISOL_PLAZO = "auditoria.procedimiento.finisol.plazo";
    public static final String PROCEDIMIENTO_FINISOL_DOCUMENTACION = "auditoria.procedimiento.finisol.documentacion";
    public static final String PROCEDIMIENTO_FINISOL_SOLICITANTES = "auditoria.procedimiento.finisol.solicitantes";
    public static final String PROCEDIMIENTO_FINISOL_REQUISITOS = "auditoria.procedimiento.finisol.requisitos";
    public static final String PROCEDIMIENTO_FINISOL_LUGARPRESENTACION = "auditoria.procedimiento.finisol.lugarpresentacion";
    public static final String PROCEDIMIENTO_FINISOL_URLPRESENTACION = "auditoria.procedimiento.finisol.urlpresentacion";
    public static final String PROCEDIMIENTO_FINISOL_TASAS = "auditoria.procedimiento.finisol.tasas";
    public static final String PROCEDIMIENTO_FINISOL_TASASURL = "auditoria.procedimiento.finisol.tasasurl";
    public static final String PROCEDIMIENTO_FINISOL_OBSERVACIONES = "auditoria.procedimiento.finisol.observaciones";

    // AuditoriaDTO Procedimiento Traduccion
    public static final String PROCEDIMIENTO_DESCRIPCION = "auditoria.procedimiento.descripcion";
    public static final String PROCEDIMIENTO_NOTASINTERNAS = "auditoria.procedimiento.notasinternas";
    public static final String PROCEDIMIENTO_INSTANCIA = "auditoria.procedimiento.instancia";

    // AuditoriaDTO Fase Finalilzación Resolución
    public static final String PROCEDIMIENTO_FINRES_ORGANORESOLUCION = "auditoria.procedimiento.finres.organoresolucion";
    public static final String PROCEDIMIENTO_FINRES_OBLIGACIONES = "auditoria.procedimiento.finres.obligaciones";
    public static final String PROCEDIMIENTO_FINRES_RESOLUCIONCUANTIA = "auditoria.procedimiento.finres.resolucioncuantia";
    public static final String PROCEDIMIENTO_FINRES_OBSERVACIONES = "auditoria.procedimiento.finres.observaciones";
    public static final String PROCEDIMIENTO_FINRES_DOCUMENTACION = "auditoria.procedimiento.finres.documentacion";
    public static final String PROCEDIMIENTO_FINRES_PLAZO = "auditoria.procedimiento.finres.plazo";

    // AuditoriaDTO Fase Finalilzación Justificación
    public static final String PROCEDIMIENTO_FINJUS_PLAZO = "auditoria.procedimiento.finjus.plazo";
    public static final String PROCEDIMIENTO_FINJUS_OBJETO = "auditoria.procedimiento.finjus.objeto";
    public static final String PROCEDIMIENTO_FINJUS_PRESENTACION = "auditoria.procedimiento.finjus.presentacion";
    public static final String PROCEDIMIENTO_FINJUS_DOCUMENTACION = "auditoria.procedimiento.finjus.documentacion";

    // AuditoriaDTO Fase Comun Subsanacion
    public static final String PROCEDIMIENTO_FCOMSUB_PLAZO = "auditoria.procedimiento.fcomsub.plazo";
    public static final String PROCEDIMIENTO_FCOMSUB_DOCUMENTACION = "auditoria.procedimiento.fcomsub.documentacion";
    public static final String PROCEDIMIENTO_FCOMSUB_PRESENTACION = "auditoria.procedimiento.fcomsub.presentacion";
    public static final String PROCEDIMIENTO_FCOMSUB_OBJETO = "auditoria.procedimiento.fcomsub.objeto";

    // AuditoriaDTO Fase Comun Desistimiento
    public static final String PROCEDIMIENTO_FCOMDES_PLAZO = "auditoria.procedimiento.fcomdes.plazo";
    public static final String PROCEDIMIENTO_FCOMDES_DOCUMENTACION = "auditoria.procedimiento.fcomdes.documentacion";
    public static final String PROCEDIMIENTO_FCOMDES_PRESENTACION = "auditoria.procedimiento.fcomdes.presentacion";
    public static final String PROCEDIMIENTO_FCOMDES_OBJETO = "auditoria.procedimiento.fcomdes.objeto";

    // AuditoriaDTO Fase Comun Alzada
    public static final String PROCEDIMIENTO_FCOMALZ_PLAZO = "auditoria.procedimiento.fcomalz.plazo";
    public static final String PROCEDIMIENTO_FCOMALZ_DOCUMENTACION = "auditoria.procedimiento.fcomalz.documentacion";
    public static final String PROCEDIMIENTO_FCOMALZ_PRESENTACION = "auditoria.procedimiento.fcomalz.presentacion";
    public static final String PROCEDIMIENTO_FCOMALZ_OBJETO = "auditoria.procedimiento.fcomalz.objeto";
    public static final String PROCEDIMIENTO_FCOMALZ_OBSERVACIONES = "auditoria.procedimiento.fcomalz.observaciones";

    // AuditoriaDTO Fase Comun Reposición
    public static final String PROCEDIMIENTO_FCOMREPO_PLAZO = "auditoria.procedimiento.fcomrepo.plazo";
    public static final String PROCEDIMIENTO_FCOMREPO_DOCUMENTACION = "auditoria.procedimiento.fcomrepo.documentacion";
    public static final String PROCEDIMIENTO_FCOMREPO_PRESENTACION = "auditoria.procedimiento.fcomrepo.presentacion";
    public static final String PROCEDIMIENTO_FCOMREPO_OBJETO = "auditoria.procedimiento.fcomrepo.objeto";

    // AuditoriaDTO Enlace Informativo
    public static final String PROCEDIMIENTO_ENLINFO_OBJETO = "auditoria.procedimiento.enlinfo.objeto";
    public static final String PROCEDIMIENTO_ENLINFO_URL = "auditoria.procedimiento.enlinfo.url";

    // AuditoriaDTO Servicio Informativo
    public static final String PROCEDIMIENTO_SERVINFO_OBJETO = "auditoria.procedimiento.servinfo.objeto";
    public static final String PROCEDIMIENTO_SERVINFO_SOLICITANTES = "auditoria.procedimiento.servinfo.solicitantes";
    public static final String PROCEDIMIENTO_SERVINFO_REQUISITOS = "auditoria.procedimiento.servinfo.requisitos";
    public static final String PROCEDIMIENTO_SERVINFO_OBSERVACIONES = "auditoria.procedimiento.servinfo.observaciones";

    // AuditoriaDTO Procedimiento
    public static final String PROCEDIMIENTO_ESTADO = "auditoria.procedimiento.estado";
    public static final String PROCEDIMIENTO_ORGANO = "auditoria.procedimiento.organo";
    public static final String PROCEDIMIENTO_CODFUNCIONAL = "auditoria.procedimiento.codfuncional";
    public static final String PROCEDIMIENTO_FECHANOVEDADES = "auditoria.procedimiento.fechanovedades";
    public static final String PROCEDIMIENTO_FECHAALERTA = "auditoria.procedimiento.fechaalerta";
    public static final String PROCEDIMIENTO_REFCONSELLERIA = "auditoria.procedimiento.refconselleria";
    public static final String PROCEDIMIENTO_CONTACTOCONSELLERIA = "auditoria.procedimiento.contactoconselleria";
    public static final String PROCEDIMIENTO_DESCRIPCIONALERTA = "auditoria.procedimiento.descripcionalerta";
    public static final String PROCEDIMIENTO_NOVEDADBOLETIN = "auditoria.procedimiento.novedadboletin";
    public static final String PROCEDIMIENTO_ALTASIA = "auditoria.procedimiento.altasia";
    public static final String PROCEDIMIENTO_APLICACIONGESTORA = "auditoria.procedimiento.aplicaciongestora";
    public static final String PROCEDIMIENTO_PUBOBJCIUDADANOS = "auditoria.procedimiento.pubobjciudadanos";
    public static final String PROCEDIMIENTO_PUBOBJINTERNO = "auditoria.procedimiento.pubobjinterno";
    public static final String PROCEDIMIENTO_PUBOBJEMPRESAS = "auditoria.procedimiento.pubobjempresas";
    public static final String PROCEDIMIENTO_PUBOBJADMINISTRACION = "auditoria.procedimiento.pubobjadministracion";
    public static final String PROCEDIMIENTO_TIPOPROCEDIMIENTO = "auditoria.procedimiento.tipoprocedimiento";
    public static final String PROCEDIMIENTO_TIPOSERVICIO = "auditoria.procedimiento.tiposervicio";
    public static final String PROCEDIMIENTO_APUNTESCONSULTA = "auditoria.procedimiento.apuntesconsulta";


    // Auditoría de Empleo Público
    public static final String OPOSICION_ESTADOWF = "auditoria.oposcion.estadowf";
    public static final String OPOSICION_OPOSWF_WFEST = "auditoria.oposicion.estado";
    public static final String OPOSICION_OPOSWF_CODORG = "auditoria.oposicion.codorgano";
    public static final String OPOSICION_OPOSWF_FCNOVE = "auditoria.oposicion.fechanovedad";
    public static final String OPOSICION_OPOSWF_BOLETI = "auditoria.oposicion.boletin";
    public static final String OPOSICION_OPOSWF_TIPACT = "auditoria.oposicion.tipoactualizacion";
    public static final String OPOSICION_OPOSWF_TIPCNV = "auditoria.oposicion.tipoconvocatoria";
    public static final String OPOSICION_OPOSWF_TIPTIT = "auditoria.oposicion.tipotitulo";
    public static final String OPOSICION_OPOSWF_TIPPRU = "auditoria.oposicion.tipoprueba";
    public static final String OPOSICION_OPOSWF_SEGUIM = "auditoria.oposicion.seguimiento";
    public static final String OPOSICION_OPOSWF_NUMPLZ = "auditoria.oposicion.numeroplazas";
    public static final String OPOSICION_OPOSWF_WFOBS = "auditoria.oposicion.observacioneswf";
    public static final String OPOSICION_OPOSWF_FABRPLZ = "auditoria.oposicion.fechaabreplazo";
    public static final String OPOSICION_OPOSWF_FCIEPLZ = "auditoria.oposicion.fechacierreplazo";
    public static final String OPOSICION_OPOSWF_TURNO = "auditoria.oposicion.turno";

    public static final String OPOSICION_OPOTRA_DESCRI = "auditoria.oposiciontrad.descripcion";
    public static final String OPOSICION_OPOTRA_REQUIS = "auditoria.oposiciontrad.requisito";
    public static final String OPOSICION_OPOTRA_DESPLZ = "auditoria.oposiciontrad.descripcionplazas";
    public static final String OPOSICION_OPOTRA_TITESP = "auditoria.oposiciontrad.titulacionespecifica";
    public static final String OPOSICION_OPOTRA_URL = "auditoria.oposiciontrad.urlacceso";

    public static final String OPOSICION_ETAPAS_TIPMED = "auditoria.oposicionetapa.tipomediopublicacion";
    public static final String OPOSICION_ETAPAS_CODLSE = "auditoria.oposicionetapa.listaenlace";
    public static final String OPOSICION_ETAPAS_CODTRA = "auditoria.oposicionetapa.tramiteelectronico";
    public static final String OPOSICION_ETAPAS_PLAZO = "auditoria.oposicionetapa.plazo";
    public static final String OPOSICION_ETAPAS_FCINIP = "auditoria.oposicionetapa.fechainicioplazo";
    public static final String OPOSICION_ETAPAS_FCFINP = "auditoria.oposicionetapa.fechafinplazo";
    public static final String OPOSICION_ETAPAS_FCNOVE = "auditoria.oposicionetapa.fechanovedad";
    public static final String OPOSICION_ETAPAS_FCPUBL = "auditoria.oposicionetapa.fechapublicacion";
    public static final String OPOSICION_ETAPAS_IMTASA = "auditoria.oposicionetapa.importetasa";
    public static final String OPOSICION_ETAPAS_DSTASA = "auditoria.oposicionetapa.descripciontasa";
    public static final String OPOSICION_ETAPAS_NUMPUB = "auditoria.oposicionetapa.numeropublicacion";
    public static final String OPOSICION_ETAPAS_RECPOT = "auditoria.oposicionetapa.recursopotestativo";
    public static final String OPOSICION_ETAPAS_RECALZ = "auditoria.oposicionetapa.recursoalzada";
    public static final String OPOSICION_ETAPAS_RECCON = "auditoria.oposicionetapa.recursocontencioso";
    public static final String OPOSICION_ETAPAS_ALABOR = "auditoria.oposicionetapa.alarmaborrado";
    public static final String OPOSICION_ETAPAS_FCALER = "auditoria.oposicionetapa.fechaalerta";
    public static final String OPOSICION_ETAPAS_DSALER = "auditoria.oposicionetapa.descripcionalerta";
    public static final String OPOSICION_ETAPAS_BOLETIN = "auditoria.oposicionetapa.boletinnovedades";


    public static final String OPOSICION_ETATRA_DOCINFO = "auditoria.oposicionetapatrad.documentacion";
    public static final String OPOSICION_ETATRA_LUGARP = "auditoria.oposicionetapatrad.lugarpresentacion";
    public static final String OPOSICION_ETATRA_FORMAP = "auditoria.oposicionetapatrad.otrasformaspresentacion";
    public static final String OPOSICION_ETATRA_PLAZOP = "auditoria.oposicionetapatrad.plazopresentacion";
    public static final String OPOSICION_ETATRA_INFCOM = "auditoria.oposicionetapatrad.informacioncomplementaria";
    public static final String OPOSICION_ETATRA_DSTASA = "auditoria.oposicionetapatrad.descripciontasas";
    public static final String OPOSICION_ETATRA_EXTASA = "auditoria.oposicionetapatrad.exenciontasas";
    public static final String OPOSICION_ETATRA_NOTINT = "auditoria.oposicionetapatrad.notasinternas";

    // Auditoría de procedimientos externos
    public static final String PROCEDIMIENTOEXTERNO_ESTADOWF = "auditoria.procedimientoexterno.estadowf";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_CODOTR = "auditoria.procedimientoexterno.otroorgano";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_FECINI = "auditoria.procedimientoexterno.fecinicio";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_FECFIN = "auditoria.procedimientoexterno.fecfin";
    public static final String PROCEDIMIENTOEXTERNO_PUBOBJCIUDADANOS = "auditoria.procedimientoexterno.pubobjciudadanos";
    public static final String PROCEDIMIENTOEXTERNO_PUBOBJINTERNO = "auditoria.procedimientoexterno.pubobjinterno";
    public static final String PROCEDIMIENTOEXTERNO_PUBOBJEMPRESAS = "auditoria.procedimientoexterno.pubobjempresas";
    public static final String PROCEDIMIENTOEXTERNO_PUBOBJADMINISTRACION = "auditoria.procedimientoexterno.pubobjadministracion";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_INDPRES = "auditoria.procedimientoexterno.presencial";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_INDTELE = "auditoria.procedimientoexterno.telematica";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_BOLIND = "auditoria.procedimientoexterno.boletin";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_BOLFEC = "auditoria.procedimientoexterno.fecnovedades";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_BOLTIP = "auditoria.procedimientoexterno.tipoactualizacion";
    public static final String PROCEDIMIENTOEXTERNO_PREWF_TIPO = "auditoria.procedimientoexterno.tipo";

    public static final String PROCEDIMIENTOEXTERNO_DESCRIPCION = "auditoria.procedimientoexterno.descripcion";
    public static final String PROCEDIMIENTOEXTERNO_INFOINTERES = "auditoria.procedimientoexterno.infoInteres";
    public static final String PROCEDIMIENTOEXTERNO_REQUISITOS = "auditoria.procedimientoexterno.requisitos";
    public static final String PROCEDIMIENTOEXTERNO_PLAZO = "auditoria.procedimientoexterno.plazo";
    public static final String PROCEDIMIENTOEXTERNO_DOCUMENTACION = "auditoria.procedimientoexterno.documentacion";
    public static final String PROCEDIMIENTOEXTERNO_TASAS = "auditoria.procedimientoexterno.tasas";
    public static final String PROCEDIMIENTOEXTERNO_PRESPRE = "auditoria.procedimientoexterno.prespre";
    public static final String PROCEDIMIENTOEXTERNO_PRESURL = "auditoria.procedimientoexterno.presurl";
    public static final String PROCEDIMIENTOEXTERNO_TELEPRE = "auditoria.procedimientoexterno.telepre";
    public static final String PROCEDIMIENTOEXTERNO_TELEURL = "auditoria.procedimientoexterno.teleurl";
    public static final String PROCEDIMIENTOEXTERNO_MASINF = "auditoria.procedimientoexterno.masinf";
    public static final String PROCEDIMIENTOEXTERNO_INFINTER = "auditoria.procedimientoexterno.infointerna";
    public static final String PROCEDIMIENTOEXTERNO_SOLINT = "auditoria.procedimientoexterno.solicitantes";


    // PRETRA_INFINTER
    // PRETRA_SOLINT

    // Labels para conversiones de idioma
    public static final String AUDITORIA_BOOLEAN = "auditoria.boolean.";
    public static final String AUDITORIA_WFEST = "workflow.estado.";


}


