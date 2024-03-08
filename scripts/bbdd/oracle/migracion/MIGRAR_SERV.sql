CREATE OR replace PROCEDURE "MIGRAR_SERV" (codigo        NUMBER,
                                           codigoentidad NUMBER,
                                           resultado     OUT CLOB)
AS
/* SET SERVEROUTPUT ON; */
/** GRANT: GRANT SELECT ON ROLSAC.RSC_PROCED TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_TRAPRO TO ROLSAC2; 
           GRANT SELECT ON R1_SERVICIOS TO ROLSAC2;
           GRANT SELECT ON R1_SERVICIOS_TRAD TO ROLSAC2; 

           GRANT SELECT ON ROLSAC.RSC_PRONOR TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_SERNOR TO ROLSAC2;
**/
  /** RSC_PROCED (* INDICA QUE EL CAMPO NO SE MIGRA):
          PRO_CODI    ---> CODIGO PROCEDIMIENTO [PRO_CODIGO]
         *PRO_TYPE    ---> EL TIPO (SI ES REMOTO) [PROC_TIPO] 
         *PRO_SIGNAT  ---> SIGNATURE 
          PRO_FECCAD  ---> FECHA CADUCIDAD [PRWF_FECCAD]
          PRO_FECPUB  ---> FECHA PUBLICICACION [PRWF_FECPUB]
          PRO_FECACT  ---> FECHA ACTUALIZACION [PROC_FECACT]
          PRO_VALIDA  ---> VALIDA [PRWF_WF - PRWF_WFESTADO - PRWF_INTERNO]
          PRO_CODUNA  ---> CODIGO UNIDAD ADMINISTRATIVA [PRWF_CODUAR]
          PRO_CODFAM  ---> FAMILIA
          PRO_TRAMIT  ---> TRAMITE
          PRO_VERSIO  ---> VERSION
         *PRR_CODADM  ---> CODIGO ADMINISTRACION REMOTA
         *PRR_IDEXTE  ---> CODIGO ID PARA ADMINISTRACION REMOTA
         *PRR_URLREM  ---> URL REMOTA EN ADMINISTRACION REMOTA
          PRO_URLEXT  ---> URL
          PRO_ORDCON  ---> ORDEN 
         *PRO_ORDDIR  ---> ORDEN2 
         *PRO_ORDSER  ---> ORDEN3
          PRO_CODINI  ---> FORMA DE INICIO [PRWF_PRTIPINIC]
          PRO_INDICA  ---> TIPO VIA ADMINISTRATIVA [PRWF_TIPVIA]
          PRO_VENTANA ---> VENTANILLA UNICA
          PRO_INFO    ---> DIRECCION ELECTRONICA [PRWF_RSEMA]
          PRO_TAXA    ---> TAXA 
          PRO_CODUNA_RESOL --> ORGANO RESOLUTORIO [PRWF_CODUAI]
          PRO_RESPON  ---> RESPONSABLE [PRWF_RSNOM]
          PRO_CODUNA_SERV --> ORGANO SERVICIO  
          PRO_CODSIA  ---> CODIGO SIA [PROC_SIACOD]
          PRO_CODSIL  ---> SILENCIO ADMINISTRATIVO [PRWF_PRTIPSIAD]
          PRO_ESTSIA  ---> ESTADO SIA  [PROC_SIAEST]
          PRO_FECSIA  ---> FECHA SIA  [PROC_SIAFC]
          PRO_COMUN   ---> COMUN    [PRWF_COMUN]
          PRO_CODLEG  ---> TIPO LEGITIMACION   [PRWF_DPTIPLEGI]
         *PRO_PDTVAL  ---> PENDIENTE VALIDACION   
          PRO_FUNHAB  ---> HABILITADO PARA FUNCIONARIO [PRWF_HABFUN]
          PRO_APOHAB  ---> HABILITADO PARA APODERADO [PRWF_HABAPO]
      (TRADUCCIONES) RSC_TRAPRO:
          TPR_CODPRO  ---> CODIGO PROCEDIMIENTO [TRPW_CODIGO]
          TPR_CODIDI  ---> IDIOMA     [TRPW_IDIOMA]
          TPR_NOMBRE  ---> NOMBRE [TRPW_NOMBRE]    
          TPR_RESUME  ---> RESUMEN [TRPW_OBJETO]
          TPR_DESTIN  ---> DESTINATARIO  [TRPW_DESTIN] 
          TPR_REQUIS  ---> REQUISITOS [TRPW_SVREQ]
          TPR_PLAZOS  ---> PLAZOS 
         *TPR_SILEN   ---> SILENCIO 
          TPR_RECURS  ---> RECURSO  
          TPR_OBSERV  ---> OBSERVACION [TRPW_OBSER]
          TPR_LUGAR   ---> LUGAR 
          TPR_RESOLUCION  ---> RESOLUCION [TRPW_PRRESO]
          TPR_NOTIFICACION  ---> NOTIFICACION  
          TPR_RESULT  ---> RESULTAT 
         *TPR_LOPDFI  ---> LOPD FINALIDAD   [FINALIDAD]
         *TPR_LOPDDS  ---> LOPD DESTINATARIO [DESTINATARIO]
         *TPR_LOPDDR  ---> LOPD DERECHO 
         *TPR_LOPDIA  ---> LOPD INFO ADICIONAL
      RSC_SERVIC (* INDICA QUE EL CAMPO NO SE MIGRA):
          SER_CODI   ---> CODIGO SERVICIO [PRO_CODIGO]
          SER_VALIDA ---> VALIDA [PRWF_WF - PRWF_WFESTADO - PRWF_INTERNO]
          SER_CODIGO
          SER_CODSIA ---> CODIGO SIA [PROC_SIACOD]
          SER_ESTSIA ---> ESTADO SIA  [PROC_SIAEST]
          SER_FECSIA ---> FECHA SIA  [PROC_SIAFC]
          SER_TASURL ---> TASA [PRWF_SVTASA]
          SER_NOMRSP ---> RESPONSABLE [PRWF_RSNOM]
          SER_CORREO ---> RESPONSABLE CORREO [PRWF_RSEMA]
          SER_TELEFO ---> RESPONSABLE TFNO [PRWF_RSTFNO]
          SER_INSTRU ---> SERVICIO INSTRUCTOR  
          SER_SERRSP ---> SERVICIO RESPONSABLE 
          SER_FECPUB ---> FECHA PUBLICACION [PRWF_FECPUB]
          SER_FECDES ---> FECHA DESPUBLICACION [PRWF_FECCAD]
          SER_FECACT ---> FECHA ACTUALIZACION [PROC_FECACT]
          SER_TRAULR ---> TRAMITE URL [PRWF_SVTREL.URL]
          SER_TRAID  ---> TRAMITE ID [PRWF_SVTREL.ID]
          SER_TRAVER ---> TRAMITE VERSION  [PRWF_SVTREL.VERS]
          SER_CTELEM ---> TRAMITE ES TELEMATICO [PRWF_SVTREL.TELEMATICO]
          SER_CPRESE ---> TRAMITE ES PRESENCIAL [PRWF_SVTREL.PRESENCIAL]
          SER_CTELEF ---> TRAMITE ES TELEFONICO [PRWF_SVTREL.TELEFONICO]
          SER_COMUN  ---> COMUN  [PRWF_COMUN]
          SER_CODPLT ---> TRAMITE PLANTILLA [PRWF_SVTREL.PRWF_SVTPRE]
          SER_PARAMS ---> TRAMITE PARAMS [PRWF_SVTREL.TELEFONICO]
          SER_CODLEG ---> LEGITIMACION [PRWF_DPTIPLEGI]
          SER_CLOPD  ---> LOPD ACTIVO  [PROC_LOPDACT]
         *SER_PDTVAL ---> PENDIENTE VALIDACION 
      (TRADUCCIONES) RSC_TRASER:
          TSR_CODSER ---> CODIGO SERVICIO [TRPW_CODIGO]
          TSR_CODIDI ---> IDIOMA [TRPW_IDIOMA]
          TSR_NOMBRE ---> NOMBRE [TRPW_NOMBRE]    
          TSR_OBJETO ---> OBJETO [TRPW_OBJETO]
          TSR_DESTIN ---> DESTINO  [TRPW_DESTIN] 
          TSR_REQUIS ---> REQUISITOS [TRPW_SVREQ]
          TSR_OBSERV ---> OBSERVACIONES  [TRPW_OBSER]
          TSR_ULRSER ---> URL SERVICIO  
         *TSR_LOPDFI ---> LOPD FINALIDAD
         *TSR_LOPDDS ---> LOPD DESTINO 
         *TSR_LOPDDR ---> LOPD DERECHOS 
         *TSR_LOPDIA ---> LOPD FINALIDAD
  
  
      RS2_PROC 
          PROC_CODIGO  ---> CODIGO PROCEDIMIENTO
          PROC_TIPO    ---> TIPO (PROC / SERV)  
          PROC_SIACOD  ---> CODIGO SIA 
          PROC_SIAEST  ---> ESTADO SIA   
          PROC_SIAFC   ---> FECHA SIA  
          PROC_SIADIR3 ---> DIR3 
          PROC_MENSA   ---> MENSAJE  
          PROC_PDTIDX  ---> PENDIENTE IDX 
          PROC_DATIDX  ---> FECHA INDEXACION 
          PROC_DATINX  --->  
          PROC_ERRIDX  ---> ERROR INDEXACION SOLR 
          PROC_ERRSIA  ---> ERROR SIA 
          PROC_PDTGST  ---> MENSAJE GESTOR     
          PROC_PDTSUP  ---> MENSAJE SUPERVISOR 
          PROC_FECACT  ---> FECHA ACTUALIZACION 
      RS2_PRCWF
          PRWF_CODIGO  ---> CODIGO PROCEDIMIENTO WF 
          PRWF_CODPROC ---> CODIGO PROCEDIMIENTO 
          PRWF_WF      ---> WF : DEFINITIVO O EN MODIFICACION  
          PRWF_WFESTADO --> ESTADO : PUB/ MOD/RES/BOR 
          PRWF_WFUSUA  ---> USUARIO 
          PRWF_CODUAR  ---> UA RESPONSABLE  
          PRWF_CODUAI  ---> UA INSTRUCTOR 
          PRWF_INTERNO ---> INTERNO  
          PRWF_RSNOM   ---> RESPONSABLE NOMBRE 
          PRWF_RSEMA   ---> RESPONSABLE EMAIL   
          PRWF_RSTFNO  ---> RESPONSABLE TELFNO 
          PRWF_DPTIPLEGI -> DATOS PERSONALES LEGITIMACION   
          PRWF_LSTDOC  ---> LISTA DOCUMENTOS  
          PRWF_LSLOPD  ---> LISTA DOCUMENTOS LOPD 
          PRWF_PRCODUAC --> UA COMPETENTE 
          PRWF_PRTIPINIC --> FORMA DE INICIO
          PRWF_PRTIPSIAD --> SILENCIO ADMINISTRATIVO 
          PRWF_SVTASA  ----> PARA SERVICIOS: TIENE TASA 
          PRWF_SVTPRE  ----> PARA SERVICIOS: TIPO TRAMITACION PLANTILLA
          PRWF_FECPUB  ----> FECHA PUBLICACION  
          PRWF_FECCAD  ----> FECHA CADUCIDAD 
          PRWF_TIPPRO  ----> TIPO PROCEDIMIENTO  
          PRWF_TIPVIA  ----> TIPO VIA 
          PROC_LOPDRESP ---> LOPD RESPONSABLE   
          PRWF_SVPRES   ---> PARA SERVICIOS: ES PRESENCIAL 
          PRWF_SVELEC   ---> PARA SERVICIOS: ES TELEMATICO 
          PRWF_SVTEL    ---> PARA SERVICIOS: ES TELEFONICO   
          PRWF_COMUN    ---> COMUN  
          PRWF_HABAPO   ---> HABILITADO PARA APODERADOS
          PRWF_HABFUN   ---> HABILITADO PARA FUNCIONARIO
          PRWF_SVTREL   ---> PARA SERVICIOS: TIPO TRAMITACION 
          PROC_LOPDACT  ---> PARA SERVICIOS: SI ESTA ACTIVO LOPD
      (CAMPOS SIN RELACION)             
  
      RELACIONES:
          ***** TODO ****
          RS2_DOCNORM   --> DOCUMENTOS NORM
          RS2_AFECTA    --> AFECTACIONES Y AFECTACIONES ORIGEN
  
      TRADUCCIONES (RS2_TRAPRWF) ESTAN TODAS:
          TRPW_CODIGO   --> CODIGO TRADUCCION PROC  WF
          TRPW_CODPRWF  --> CODIGO PROC WF 
          TRPW_IDIOMA   --> IDIOMA
          TRPW_NOMBRE   --> NOMBRE 
          TRPW_OBJETO   --> OBJETO 
          TRPW_DESTIN   --> DESTINATARIO 
          TRPW_OBSER    --> OBSERVACION 
          TRPW_DPFINA   --> DATOS PERSONALES: FINALIDAD 
          TRPW_DPDEST   --> DATOS PERSONALES: DESTINATARIO    
         *TRPW_DPDOC    --> DOCUMENTO LOPD 
         ?TRPW_SVREQ    --> PARA SERVICIOS: REQUISITOS  
         ?TRPW_PRRESO   --> TERMINO RESOLUCION 
  
  
  
  ***/
  CURSOR cursortradservicsrolsac1 (
    codser NUMBER) IS
SELECT *
FROM   r1_servicios_trad
WHERE  tsr_codser = codser
  AND tsr_nombre IS NOT NULL;
CURSOR cursornormativasrolsac1 (
    codser NUMBER) IS
SELECT *
FROM   r1_servicios_norm
WHERE  srn_codser = codser;
CURSOR cursorpublicorolsac1 (
    codser NUMBER) IS
SELECT *
FROM   r1_servicios_pobj
WHERE  psr_codser = codser
  AND psr_codpob IN (SELECT tpsp_codigo
                     FROM   rs2_tipospu);
CURSOR cursormateriasrolsac1 (
    codser NUMBER) IS
SELECT *
FROM   r1_servicios_mate
WHERE  srm_codser = codser
  AND srm_codmat IN (SELECT tema_codigo
                     FROM   rs2_tema);
CURSOR cursordocumentos(
    codser NUMBER) IS
SELECT *
FROM   r1_servicios_doc
WHERE  dsr_codser = codser
ORDER  BY dsr_orden;
maximoid             NUMBER;
  valor                NUMBER;
  existe               NUMBER(2, 0);
  codigo_boletin       NUMBER;
  l_clob               CLOB := empty_clob;
  wf                   NUMBER(1, 0);
  wfestado             VARCHAR2(1 CHAR);
  interno              NUMBER(1, 0);
  ser_valida           NUMBER(3, 0);
  nombre               VARCHAR2(4000);
  existe_trad          NUMBER(2, 0);
  codigosia            NUMBER(10, 0);
  codigosiar2          NUMBER(10, 0);
  codigosiaexist       NUMBER(2, 0);
  codigo_procwf        NUMBER(10, 0);
  existe_mat_prc       NUMBER(2, 0);
  tipotram_plantilla   NUMBER(10, 0);
  tipotram             NUMBER(10, 0);
  codpln               NUMBER(10, 0);
  traid                VARCHAR2(50);
  traver               NUMBER(3, 0);
  ctelem               NUMBER(1, 0);
  cprese               NUMBER(1, 0);
  ctelef               NUMBER(1, 0);
  params               VARCHAR2(300);
  lstdoc               NUMBER(10, 0);
  lslopd               NUMBER(10, 0);
  existe_archivos_lopd NUMBER(2, 0);
  existe_archivos      NUMBER(2, 0);
  orden                NUMBER(2, 0);
  rs2_codpr_lopd       NUMBER(10, 0);
  existe_trad_es       NUMBER(1, 0);
  existe_trad_ca       NUMBER(1, 0);
BEGIN
    dbms_lob.Createtemporary(l_clob, TRUE);

    dbms_lob.OPEN(l_clob, dbms_lob.lob_readwrite);

    /*DBMS_OUTPUT.PUT_LINE('INICIO MIGRACION NORMATIVAS');*/
SELECT Count(*)
INTO   existe
FROM   rs2_proc
WHERE  proc_codigo = codigo;

SELECT Count(*)
INTO   existe_trad
FROM   r1_servicios_trad
WHERE  tsr_codser = codigo
  AND tsr_nombre IS NOT NULL;

nombre := 'SIN TRAD';

SELECT Count(*)
INTO   existe_trad_es
FROM   r1_servicios_trad
WHERE  tsr_codser = codigo
  AND tsr_nombre IS NOT NULL
  AND tsr_codidi = 'es';

SELECT Count(*)
INTO   existe_trad_ca
FROM   r1_servicios_trad
WHERE  tsr_codser = codigo
  AND tsr_nombre IS NOT NULL
  AND tsr_codidi = 'ca';

IF existe_trad > 0 THEN
SELECT tsr_nombre
INTO   nombre
FROM   r1_servicios_trad
WHERE  tsr_codser = codigo
  AND tsr_nombre IS NOT NULL
  AND ROWNUM = 1;
END IF;

SELECT ser_valida
INTO   ser_valida
FROM   r1_servicios
WHERE  ser_codi = codigo;

IF existe = 0 THEN
      /** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
BEGIN
          /** EL TIPOTRAM_PLANTILLA EN SERVICIOS SIEMPRE ES NULL AL MIGRARLO, NO HAY PLANTILLAS EN ROLSAC1 SERVICIOS **/
          tipotram_plantilla := NULL;

          tipotram := NULL;

SELECT ser_traid,
       ser_traver,
       ser_ctelem,
       ser_cprese,
       ser_ctelef,
       ser_params,
       ser_codplt
INTO   traid, traver, ctelem, cprese,
    ctelef, params, codpln
FROM   r1_servicios
WHERE  ser_codi = codigo;

/** CREAMOS LA INFORMACI�N DE TIPO TRAMITACION **/
SELECT rs2_trmpre_seq.NEXTVAL
INTO   tipotram
FROM   dual;

INSERT INTO rs2_trmpre
(pres_codigo,
 pres_trpres,
 pres_trelec,
 pres_trtel,
 pres_intptr,
 pres_inttid,
 pres_faseproc,
 pres_inttve,
 pres_inttpa,
 pres_planti,
 pres_codenti)
VALUES      (tipotram,
             Coalesce(cprese, 0),
             Coalesce(ctelem, 0),
             ctelef,
             codpln,
             traid,
             NULL,
             traver,
             params,
             0,
             codigoentidad);

INSERT INTO rs2_tratptra
(trtt_codigo,
 trtt_codtptra,
 trtt_idioma,
 trtt_descri,
 trtt_url)
SELECT rs2_tratptra_seq.NEXTVAL,
       tipotram,
       tsr_codidi,
       NULL,/*TTR_DESCRI,*/
       tsr_ulrser
FROM   r1_servicios_trad
WHERE  tsr_codser = codigo;

/** LA INFORMACI�N DE LOS DOCUMENTOS. **/
SELECT Count(*)
INTO   existe_archivos_lopd
FROM   r1_servicios_trad
WHERE  tsr_codser = codigo
  AND tsr_lopdia IS NOT NULL;

IF existe_archivos_lopd > 0 THEN
SELECT rs2_lstdoc_seq.NEXTVAL
INTO   lslopd
FROM   dual;

INSERT INTO rs2_lstdoc
(lsdo_codigo)
VALUES      (lslopd);

dbms_output.Put_line('LSLOPD: '
                                 || lslopd);
END IF;

SELECT Count(*)
INTO   existe_archivos
FROM   r1_servicios_doc
WHERE  dsr_codser = codigo;

IF existe_archivos > 0 THEN
SELECT rs2_lstdoc_seq.NEXTVAL
INTO   lstdoc
FROM   dual;

INSERT INTO rs2_lstdoc
(lsdo_codigo)
VALUES      (lstdoc);
END IF;

          /** MIGRAMOS LOS DATOS BASE EN UN NUEVO CAMPO EN ROLSAC2  */
INSERT INTO rs2_proc
(proc_codigo,
 proc_tipo,
 proc_siacod,
 proc_siaest,
 proc_siafc,
 proc_fecact)
SELECT ser_codi,
       'S',
       ser_codsia,
       ser_estsia,
       ser_fecsia,
       ser_fecact
FROM   r1_servicios
WHERE  ser_codi = codigo;

/** SEGUN ROLSAC1, VALIDACION ES:
PUBLICA = 1, INTERNA = 2, RESERVA = 3 BAJA = 4;
PUBLICA SERA DEFINITIVO Y PUBLICADO
INTERNA SERA ENMODIFICACION Y MODIFICACION
RESERVA SERA DEFINITIVO Y RESERVA
BAJA SERA DEFINITIVO Y BORRADO
*/
IF ser_valida = 1 THEN
            wf := 0;

            wfestado := 'P';

            interno := 0;
          ELSIF ser_valida = 2 THEN
            wf := 1;

            wfestado := 'M';

            interno := 1;
          ELSIF ser_valida = 3 THEN
            wf := 0;

            wfestado := 'R';

            interno := 1;
ELSE /** SER_VALIDA = 4 **/
            wf := 0;

            wfestado := 'B';

            interno := 0;
END IF;

SELECT rs2_prcwf_seq.NEXTVAL
INTO   codigo_procwf
FROM   dual;

INSERT INTO rs2_prcwf
(prwf_codigo,
 prwf_codproc,
 prwf_wf,
 prwf_wfestado,
 prwf_coduar,
 prwf_coduai,
 prwf_interno,
 prwf_rsnom,
 prwf_rsema,
 prwf_rstfno,
 prwf_dptiplegi,
 prwf_svtpre,
 prwf_fecpub,
 prwf_feccad,
 prwf_comun,
 prwf_svtrel,
 proc_lopdact,
 prwf_svpres,
 prwf_svelec,
 prwf_svtel,
 prwf_lstdoc,
 prwf_lslopd)
SELECT codigo_procwf,
       codigo,
       wf,
       wfestado,
       ser_serrsp,
       ser_serrsp,
       interno,
       Coalesce (ser_nomrsp, 'Desconegut'),
       ser_correo,
       ser_telefo,
       ser_codleg,
       tipotram_plantilla,
       ser_fecpub,
       ser_fecdes,
       ser_comun,
       tipotram,
       ser_clopd,
       ser_cprese,
       ser_ctelem,
       ser_ctelef,
       lstdoc,
       lslopd
FROM   r1_servicios
WHERE  ser_codi = codigo;

/** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
IF lslopd IS NOT NULL THEN
SELECT rs2_docpr_seq.NEXTVAL
INTO   rs2_codpr_lopd
FROM   dual;

INSERT INTO rs2_docpr
(dopr_codigo,
 dopr_orden,
 docpr_codlsd)
VALUES      (rs2_codpr_lopd,
             0,
             lslopd);
END IF;

          /** INTRODUCIMOS LAS TRADUCCIONES **/
FOR rolsac1_tradserv IN cursortradservicsrolsac1(codigo) LOOP
              INSERT INTO rs2_traprwf
                          (trpw_codigo,
                           trpw_codprwf,
                           trpw_idioma,
                           trpw_nombre,
                           trpw_objeto,
                           trpw_destin,
                           trpw_obser,
                           trpw_svreq,
                           trpw_prreso,
                           trpw_dpfina,
                           trpw_dpdest)
              VALUES      ( rs2_traprwf_seq.NEXTVAL,
                           codigo_procwf,
                           rolsac1_tradserv.tsr_codidi,
                           rolsac1_tradserv.tsr_nombre,
                           rolsac1_tradserv.tsr_objeto,
                           rolsac1_tradserv.tsr_destin,
                           rolsac1_tradserv.tsr_observ,
                           rolsac1_tradserv.tsr_ulrser,
                           rolsac1_tradserv.tsr_requis,
                           rolsac1_tradserv.tsr_lopdfi,
                           rolsac1_tradserv.tsr_lopdds );

              /** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
              IF lslopd IS NOT NULL
                 AND rolsac1_tradserv.tsr_lopdia IS NOT NULL THEN
                INSERT INTO rs2_tradopr
                            (trdp_codigo,
                             trdp_coddopr,
                             trdp_idioma,
                             trdp_titulo,
                             trdp_descri,
                             trdp_ficrol1)
                VALUES      (rs2_tradopr_seq.NEXTVAL,
                             rs2_codpr_lopd,
                             rolsac1_tradserv.tsr_codidi,
                             '',
                             '',
                             rolsac1_tradserv.tsr_lopdia);
END IF;
END LOOP;

          /** SI NO EXISTE LA TRADUCCION EN ESPANYOL, DUPLICAMOS LA CATALAN **/
          IF existe_trad_es = 0 AND existe_trad_ca = 1 THEN
            INSERT INTO rs2_traprwf
                        (trpw_codigo,
                         trpw_codprwf,
                         trpw_idioma,
                         trpw_nombre,
                         trpw_objeto,
                         trpw_destin,
                         trpw_obser,
                         trpw_prreso,
                         trpw_dpfina,
                         trpw_dpdest)
SELECT rs2_traprwf_seq.NEXTVAL,
       trpw_codprwf,
       'es',
       trpw_nombre,
       trpw_objeto,
       trpw_destin,
       trpw_obser,
       trpw_prreso,
       trpw_dpfina,
       trpw_dpdest
FROM   rs2_traprwf
WHERE  trpw_idioma = 'ca'
  AND trpw_codprwf IN (SELECT prwf_codigo
                       FROM   rs2_prcwf
                       WHERE  prwf_codproc = codigo);
END IF;

          /** SI HAY DOCS, CREAMOS LOS FICHEROS. **/
          IF lstdoc IS NOT NULL THEN
            orden := 0;

FOR documento IN cursordocumentos(codigo) LOOP
                INSERT INTO rs2_docpr
                            (dopr_codigo,
                             dopr_orden,
                             docpr_codlsd)
                VALUES      (rs2_docpr_seq.NEXTVAL,
                             orden,
                             lstdoc);

INSERT INTO rs2_tradopr
(trdp_codigo,
 trdp_coddopr,
 trdp_idioma,
 trdp_titulo,
 trdp_descri,
 trdp_ficrol1)
SELECT rs2_tradopr_seq.NEXTVAL,
       rs2_docpr_seq.CURRVAL,
       tds_codidi,
       tds_titulo,
       tds_descri,
       tds_codarc
FROM   r1_servicios_doc_trad
WHERE  tds_coddsr = documento.dsr_codi;

orden := orden + 1;
END LOOP;
END IF;

          /** INTRODUCIMOS LAS MATERIAS. **/
FOR rolsac1_materias IN cursormateriasrolsac1(codigo) LOOP
              dbms_output.Put_line('materias: '
                                   || rolsac1_materias.srm_codmat);

              /** METEMOS EL TEMA **/
INSERT INTO rs2_prctem
(prtm_codprwf,
 prtm_codtema)
VALUES      (codigo_procwf,
             rolsac1_materias.srm_codmat );
END LOOP;

          /** INTRODUCIMOS LAS NORMATIVAS. **/
FOR rolsac1_normativa IN cursornormativasrolsac1(codigo) LOOP
              INSERT INTO rs2_prcnor
                          (prwf_codigo,
                           norm_codigo)
              VALUES      (codigo_procwf,
                           rolsac1_normativa.srn_codnor);
END LOOP;

          /** INTRODUCIMOS LOS PUBLICO OBJETIVOS. **/
FOR rolsac1_pubobj IN cursorpublicorolsac1(codigo) LOOP
              INSERT INTO rs2_prcpub
                          (prpo_codprwf,
                           prpo_tippobj)
              VALUES      (codigo_procwf,
                           rolsac1_pubobj.psr_codpob);
END LOOP;

COMMIT;

dbms_lob.Writeappend(l_clob, Length('El serv '
                                              || codigo
                                              || ' "'
                                              || nombre
                                              || '" se ha migrado.'), 'El serv '
                                                                      || codigo
                                                                      || ' "'
                                                                      || nombre
                                                                      ||
          '" se ha migrado.');
EXCEPTION
          WHEN OTHERS THEN
            dbms_lob.Writeappend(l_clob, Length('El serv '
                                                || codigo
                                                || ' "'
                                                || nombre
                                                || '" ha dado el error. CODE:'
                                                || SQLCODE
                                                || '  MSG:'
                                                || SQLERRM
                                                || '. \n'), 'El serv '
                                                            || codigo
                                                            || ' "'
                                                            || nombre
                                                            ||
            '" ha dado el error. CODE:'
                                                            || SQLCODE
                                                            || '  MSG:'
                                                            || SQLERRM
                                                            || '. \n');

ROLLBACK;
END;
ELSE
      dbms_lob.Writeappend(l_clob, Length('El serv '
                                          || codigo
                                          || ' "'
                                          || nombre
                                          || '" ya existe. \n'), 'El serv '
                                                                 || codigo
                                                                 || ' "'
                                                                 || nombre
                                                                 ||
      '" ya existe. \n');
END IF;

    dbms_output.Put_line(l_clob);

    dbms_lob.CLOSE(l_clob);

    resultado := l_clob;
EXCEPTION
  WHEN OTHERS THEN
             ROLLBACK;

             dbms_output.Put_line('SQLCODE:'
                                  || SQLCODE);

             dbms_output.Put_line('SQLERRM:'
                                  || SQLERRM);

             dbms_lob.Writeappend(l_clob, Length('SE HA PRODUCIDO UN ERROR\n'),
             'SE HA PRODUCIDO UN ERROR\n');

             dbms_lob.Writeappend(l_clob, Length('El error. CODE:'
                                                 || SQLCODE
                                                 || '  MSG:'
                                                 || SQLERRM
                                                 || '. \n'), 'El error. CODE:'
                                                             || SQLCODE
                                                             || '  MSG:'
                                                             || SQLERRM
                                                             || '. \n');

             dbms_lob.CLOSE(l_clob);

             resultado := l_clob;
END MIGRAR_SERV; 