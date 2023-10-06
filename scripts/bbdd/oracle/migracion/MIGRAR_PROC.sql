create or replace PROCEDURE "MIGRAR_PROC" (codigo NUMBER, codigoEntidad NUMBER, resultado OUT CLOB) AS 
    /* SET SERVEROUTPUT ON; */
    /** GRANT: GRANT SELECT ON R1_PROCEDIMIENTOS TO ROLSAC2;
               GRANT SELECT ON R1_PROCEDIMIENTOS_TRAD TO ROLSAC2; 
               GRANT SELECT ON ROLSAC.RSC_SERVIC TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_TRASER TO ROLSAC2;  
               GRANT SELECT ON ROLSAC.RSC_TRATRA TO ROLSAC2;

               GRANT SELECT ON R1_PROCEDIMIENTOS_NORM TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_SERNOR TO ROLSAC2;
               GRANT SELECT ON R1_PROCEDIMIENTOS_POBJ TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_POBSER TO ROLSAC2;
               GRANT SELECT ON R1_PROCEDIMIENTOS_MATE TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_SERMAT TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_MATERI TO ROLSAC2;
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

    CURSOR cursorTradPROCEDsROLSAC1 (codPRO NUMBER) IS
        SELECT * 
        FROM  R1_PROCEDIMIENTOS_TRAD
        WHERE TPR_CODPRO = codPRO;
    CURSOR cursorNormativasRolsac1 (codPRO NUMBER) IS
        SELECT * 
        FROM R1_PROCEDIMIENTOS_NORM
        WHERE PRN_CODPRO = codPRO;
    CURSOR cursorPublicoRolsac1 ( codPRO NUMBER) IS 
        SELECT *  
          FROM R1_PROCEDIMIENTOS_POBJ
         WHERE PPR_CODPRO = codPRO;
    CURSOR cursorMateriasRolsac1 (codPRO NUMBER) IS 
        SELECT *  
          FROM R1_PROCEDIMIENTOS_MATE
         WHERE PRM_CODPRO = codPRO;
    CURSOR cursorTramitesRolsac1 ( codPRO NUMBER) IS 
        SELECT *  
          FROM R1_TRAMITES
         WHERE TRA_CODPRO = codPRO;        
    CURSOR cursorDocumentos(codPRO NUMBER) IS
      SELECT *
        FROM R1_PROCEDIMIENTOS_DOC
       WHERE DOC_CODPRO = codPRO
       ORDER BY DOC_ORDEN;
   CURSOR cursorDocumentosTram(codTRA NUMBER,tipo NUMBER) IS
      SELECT *
        FROM R1_TRAMITES_DOC
       WHERE DTR_CODTRA = codTRA
         AND DTR_TIPUS = tipo
       ORDER BY DTR_ORDEN;
    maximoId    NUMBER;
    VALOR       NUMBER;
    EXISTE      NUMBER;
    CODIGO_BOLETIN NUMBER;
    l_clob      CLOB := EMPTY_CLOB;
    WF	        NUMBER(1,0);
    WFESTADO	VARCHAR2(1 CHAR);
    INTERNO     NUMBER(1,0);
    PRO_VALIDA  NUMBER(3,0);
    NOMBRE      VARCHAR2(4000);
    EXISTE_TRAD NUMBER(2,0);
    CODIGOSIA   NUMBER(10,0);
    CODIGOSIAR2 NUMBER(10,0);
    CODIGOSIAEXIST NUMBER(2,0);
    CODIGO_UA   NUMBER(10,0);
    CODIGO_PROCWF NUMBER(10,0);
    EXISTE_MAT_PRC NUMBER(2,0);
    TIPOTRAM_PLANTILLA  NUMBER(10,0);
    TIPOTRAM NUMBER(10,0);
    LSTDOC NUMBER(10,0);
    LSLOPD NUMBER(10,0);
    EXISTE_ARCHIVOS_LOPD NUMBER(2,0);
    EXISTE_ARCHIVOS      NUMBER(2,0);
    orden  NUMBER(2,0);
    TOTAL_DOC_TRAM_RELACIONADO NUMBER(2,0);
    TOTAL_DOC_TRAM_MODELOS NUMBER(2,0);
    LSTDOCTRAM NUMBER(10,0);
    LDSOCODIGO NUMBER(10,0);
    RS2_CODPR_LOPD NUMBER(10,0);
BEGIN

    dbms_lob.createtemporary(l_clob, TRUE);
    dbms_lob.open(l_clob, dbms_lob.lob_readwrite);
     /*DBMS_OUTPUT.PUT_LINE('INICIO MIGRACION NORMATIVAS');*/

        SELECT COUNT(*) 
        INTO EXISTE 
        FROM RS2_PROC
        WHERE PROC_CODIGO = codigo;

        SELECT COUNT(*)
        INTO EXISTE_TRAD
        FROM R1_PROCEDIMIENTOS_TRAD
        WHERE TPR_CODPRO = codigo;



        NOMBRE := 'SIN TRAD';

        IF EXISTE_TRAD > 0
        THEN 
            SELECT TPR_NOMBRE
              INTO NOMBRE
             FROM R1_PROCEDIMIENTOS_TRAD
             WHERE TPR_CODPRO = codigo 
               AND ROWNUM = 1;
        END IF; 

        SELECT PRO_VALIDA
          INTO PRO_VALIDA
          FROM R1_PROCEDIMIENTOS
          WHERE PRO_CODI = codigo;

        IF EXISTE = 0 
        THEN   
             /** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
            BEGIN 

             /** MIGRAMOS LOS DATOS BASE EN UN NUEVO CAMPO EN ROLSAC2  */
             INSERT INTO RS2_PROC
                  ( PROC_CODIGO, 
                    PROC_TIPO, 
                    PROC_SIACOD, 
                    PROC_SIAEST, 
                    PROC_SIAFC, /*
                    PROC_MENSA, 
                    PROC_PDTIDX, 
                    PROC_DATIDX, 
                    PROC_DATINX, 
                    PROC_ERRIDX, 
                    PROC_ERRSIA, 
                    PROC_PDTGST, 
                    PROC_PDTSUP, */
                    PROC_FECACT)
                SELECT 
                    PRO_CODI,
                    'P',
                    PRO_CODSIA,
                    PRO_ESTSIA,
                    PRO_FECSIA,
                    PRO_FECACT
            FROM R1_PROCEDIMIENTOS
        WHERE PRO_CODI = codigo; 

                /** LA INFORMACI�N DE LOS DOCUMENTOS. **/  
                SELECT COUNT(*)
                  INTO EXISTE_ARCHIVOS_LOPD
                  FROM R1_PROCEDIMIENTOS_TRAD
                 WHERE TPR_CODPRO = codigo
                   AND TPR_LOPDIA IS NOT NULL;   
                   
                IF EXISTE_ARCHIVOS_LOPD > 0
                THEN
                   SELECT RS2_LSTDOC_SEQ.NEXTVAL
                     INTO LSLOPD
                     FROM DUAL;
                     
                  INSERT INTO RS2_LSTDOC (LSDO_CODIGO) VALUES (LSLOPD);
                END IF;
                
                SELECT COUNT(*) 
                  INTO EXISTE_ARCHIVOS
                  FROM R1_PROCEDIMIENTOS_DOC 
                 WHERE DOC_CODPRO = codigo ;
                
                IF EXISTE_ARCHIVOS > 0
                THEN
                   SELECT RS2_LSTDOC_SEQ.NEXTVAL
                     INTO LSTDOC
                     FROM DUAL;
                     
                     INSERT INTO RS2_LSTDOC (LSDO_CODIGO) VALUES (LSTDOC);
                END IF; 
                
                 /** SEGUN ROLSAC1, VALIDACION ES:
                 PUBLICA = 1, INTERNA = 2, RESERVA = 3 BAJA = 4;
                 PUBLICA SERA DEFINITIVO Y PUBLICADO
                 INTERNA SERA ENMODIFICACION Y MODIFICACION
                 RESERVA SERA DEFINITIVO Y RESERVA
                 BAJA SERA DEFINITIVO Y BORRADO
                 */

                 IF PRO_VALIDA = 1
                 THEN
                     WF := 0;
                     WFESTADO := 'P';
                     INTERNO := 0;
                 ELSIF PRO_VALIDA = 2
                 THEN
                     WF := 1;
                     WFESTADO := 'M';
                     INTERNO := 1;
                 ELSIF PRO_VALIDA = 3
                 THEN
                     WF := 0;
                     WFESTADO := 'R';
                     INTERNO := 1;
                 ELSE /** PRO_VALIDA = 4 **/ 
                     WF := 0;
                     WFESTADO := 'B';
                     INTERNO := 0;
                 END IF;

                SELECT RS2_PRCWF_SEQ.NEXTVAL
                  INTO CODIGO_PROCWF
                  FROM DUAL;

                 INSERT INTO RS2_PRCWF(
                    PRWF_CODIGO,
                    PRWF_CODPROC,
                    PRWF_WF,
                    PRWF_WFESTADO,
                    /*PRWF_WFUSUA,*/
                    PRWF_CODUAR,
                    PRWF_CODUAI,
                    PRWF_INTERNO,
                    PRWF_RSNOM,
                    PRWF_RSEMA,
                    /*PRWF_RSTFNO,*/
                    PRWF_DPTIPLEGI,
                    /*PRWF_LSTDOC,
                    PRWF_LSLOPD,*/
                    /*PRWF_PRCODUAC,*/
                    PRWF_PRTIPINIC,
                    PRWF_PRTIPSIAD,
                    /*PRWF_PRTIPFVIA, */
                    PRWF_SVTASA,
                    /*PRWF_SVTPRE,*/
                    PRWF_FECPUB,
                    PRWF_FECCAD,
                    PRWF_TIPPRO,
                    PRWF_TIPVIA,
                    /*PROC_LOPDRESP,
                    PRWF_SVPRES,
                    PRWF_SVELEC,
                    PRWF_SVTEL,*/
                    PRWF_COMUN,
                    PRWF_HABAPO,
                    PRWF_HABFUN,
                    PRWF_LSTDOC,
                    PRWF_LSLOPD)
                SELECT
                    CODIGO_PROCWF,
                    codigo,
                    WF,
                    WFESTADO,
                    /*PRWF_WFUSUA,*/
                    PRO_CODUNA,
                    coalesce (PRO_CODUNA_RESOL,PRO_CODUNA),
                    INTERNO,
                    coalesce (PRO_RESPON, 'Desconegut'),
                    PRO_INFO,
                    /*PRWF_RSTFNO,*/
                    PRO_CODLEG,
                    /*PRWF_LSTDOC,
                    PRWF_LSLOPD,*/
                    /*PRWF_PRCODUAC,*/
                    PRO_CODINI,
                    PRO_CODSIL,
                    /*PRWF_PRTIPFVIA,*/
                    PRO_TAXA,
                    /*PRWF_SVTPRE,*/
                    PRO_FECPUB,
                    PRO_FECCAD,
                    PRO_CODFAM,
                    PRO_INDICA,
                    /*PROC_LOPDRESP,
                    PRWF_SVPRES,
                    PRWF_SVELEC,
                    PRWF_SVTEL,*/
                    PRO_COMUN,
                    coalesce(PRO_APOHAB,0),
                    case when PRO_FUNHAB = 1 THEN 'S' else 'N' end,
                    LSTDOC,
                    LSLOPD
               FROM R1_PROCEDIMIENTOS
              WHERE PRO_CODI = codigo;

                 /** INTRODUCIMOS LOS PUBLICO OBJETIVOS. **/
                FOR ROLSAC1_TRAMITES IN cursorTramitesRolsac1(codigo) 
                LOOP
                    SELECT PRO_CODUNA
                      INTO CODIGO_UA
                      FROM R1_PROCEDIMIENTOS
                    WHERE PRO_CODI = codigo;
                    
                    TIPOTRAM_PLANTILLA := NULL;
                    TIPOTRAM := NULL;
                    
                    
                    IF ROLSAC1_TRAMITES.TRA_CODPLN IS NOT NULL
                    THEN 
                        TIPOTRAM_PLANTILLA := ROLSAC1_TRAMITES.TRA_CODPLN;
                    ELSE 
                        /** CREAMOS LA INFORMACI�N DE TIPO TRAMITACION **/
                        SELECT RS2_TRMPRE_SEQ.NEXTVAL 
                          INTO TIPOTRAM
                          FROM DUAL;
                          
                          INSERT INTO RS2_TRMPRE(PRES_CODIGO,   
                                                 PRES_TRPRES,
                                                 PRES_TRELEC,
                                                 PRES_TRTEL,
                                                 PRES_INTPTR,
                                                 PRES_INTTID,
                                                 PRES_FASEPROC,
                                                 PRES_INTTVE,
                                                 PRES_INTTPA,
                                                 PRES_PLANTI,
                                                 PRES_CODENTI
                                                 )
                                        VALUES (TIPOTRAM,
                                                 coalesce(ROLSAC1_TRAMITES.TRA_CPRESE,0),
                                                 coalesce(ROLSAC1_TRAMITES.TRA_CTELEM,0),
                                                 0, /** EL TELEFONICO SOLO EN SERVICIOS **/
                                                 ROLSAC1_TRAMITES.TRA_CODPLT,
                                                 ROLSAC1_TRAMITES.TRA_IDTRAMTEL,
                                                 ROLSAC1_TRAMITES.TRA_FASE,
                                                 ROLSAC1_TRAMITES.TRA_VERSIO,
                                                 ROLSAC1_TRAMITES.TRA_PARAMS,
                                                 0,
                                                 codigoEntidad);
                                                 
                                                 
                       INSERT INTO RS2_TRATPTRA(TRTT_CODIGO,
                                                TRTT_CODTPTRA,
                                                TRTT_IDIOMA,
                                                TRTT_DESCRI,
                                                TRTT_URL)
                       SELECT RS2_TRATPTRA_SEQ.NEXTVAL,
                              TIPOTRAM,
                              TTR_CODIDI,
                              NULL,/*TTR_DESCRI,*/
                              TTR_ULRTRA
                        FROM R1_TRAMITES_TRAD
                        WHERE TTR_CODTTR = ROLSAC1_TRAMITES.TRA_CODI;
                    END IF;
                    
                     SELECT COUNT(*)
                          INTO TOTAL_DOC_TRAM_MODELOS
                          FROM R1_TRAMITES_DOC
                         WHERE DTR_CODTRA = ROLSAC1_TRAMITES.TRA_CODI
                           AND DTR_TIPUS = 0 ;  
                         
                         SELECT COUNT(*)
                          INTO TOTAL_DOC_TRAM_RELACIONADO
                          FROM R1_TRAMITES_DOC
                         WHERE DTR_CODTRA = ROLSAC1_TRAMITES.TRA_CODI
                           AND DTR_TIPUS = 1 ;  
                           
                    LSTDOCTRAM := NULL;
                    LDSOCODIGO := NULL;
                    IF TOTAL_DOC_TRAM_MODELOS > 0 
                    THEN
                        SELECT RS2_LSTDOC_SEQ.NEXTVAL
                         INTO LDSOCODIGO
                         FROM DUAL;
                         
                        INSERT INTO RS2_LSTDOC (LSDO_CODIGO) VALUES (LDSOCODIGO);
                    END IF;
                    
                    IF TOTAL_DOC_TRAM_RELACIONADO > 0 
                    THEN
                         SELECT RS2_LSTDOC_SEQ.NEXTVAL
                         INTO LSTDOCTRAM
                         FROM DUAL;
                         
                        INSERT INTO RS2_LSTDOC (LSDO_CODIGO) VALUES (LSTDOCTRAM);
                    END IF;
                    
                    
                    INSERT INTO RS2_PRCTRM (PRTA_CODIGO,
                                            PRTA_CODUAC,
                                            PRTA_CODPRWF,
                                            PRTA_TRMPRE,
                                            PRTA_LSTDOC,
                                            LSDO_CODIGO,
                                            PRTA_TASA,
                                            PRTA_FECPUB,
                                            PRTA_FECINI,
                                            PRTA_FECCIE,
                                            PRTA_FASE,
                                            PRTA_TRPRES,
                                            PRTA_TRELEC,
                                            PRTA_TRTEL,
                                            PRTA_ORDEN,
                                            PRTA_TRMTRM)
                     VALUES (
                                ROLSAC1_TRAMITES.TRA_CODI,
                                CODIGO_UA,
                                CODIGO_PROCWF,
                                TIPOTRAM_PLANTILLA,
                                LSTDOCTRAM,
                                LDSOCODIGO,
                                0,
                                ROLSAC1_TRAMITES.TRA_DATPUBL,
                                ROLSAC1_TRAMITES.TRA_DATINICI,
                                ROLSAC1_TRAMITES.TRA_DATTANCAMENT,                               
                                ROLSAC1_TRAMITES.TRA_FASE,
                                coalesce(ROLSAC1_TRAMITES.TRA_CPRESE,0),
                                coalesce(ROLSAC1_TRAMITES.TRA_CTELEM,0),
                                0,
                                ROLSAC1_TRAMITES.TRA_ORDEN,
                                TIPOTRAM);


                        /** INSERTAMOS LAS TRADUCCIONES **/
                        INSERT INTO RS2_TRAPRTA
                        (TRTA_CODIGO,TRTA_CODPRTA,TRTA_IDIOMA,TRTA_REQUISITOS,TRTA_NOMBRE,TRTA_DOCUM,TRTA_OBSERV,TRTA_TERMIN)
                        SELECT RS2_TRAPRTA_SEQ.NEXTVAL, ROLSAC1_TRAMITES.TRA_CODI,TTR_CODIDI,TTR_REQUI,TTR_NOMBRE,TTR_DOCUME,TTR_DESCRI,TTR_PLAZOS
                        FROM R1_TRAMITES_TRAD
                        WHERE TTR_CODTTR = ROLSAC1_TRAMITES.TRA_CODI;
                      
                      IF LSTDOCTRAM IS NOT  NULL
                      THEN
                            orden := 0;
                            FOR documento IN cursorDocumentosTram(ROLSAC1_TRAMITES.TRA_CODI, 0)
                            LOOP
                                INSERT INTO RS2_DOCPR
                                            (DOPR_CODIGO, DOPR_ORDEN, DOCPR_CODLSD)
                                  VALUES (RS2_DOCPR_SEQ.NEXTVAL,ORDEN,LSTDOCTRAM);
                                
                                 INSERT INTO RS2_TRADOPR
                                            (TRDP_CODIGO,TRDP_CODDOPR, TRDP_IDIOMA, TRDP_TITULO, TRDP_DESCRI,TRDP_FICROL1)
                                    SELECT RS2_TRADOPR_SEQ.NEXTVAL,RS2_DOCPR_SEQ.CURRVAL, TDO_CODIDI, TDO_TITULO,TDO_DESCRI, TDO_CODARC
                                      FROM R1_TRAMITES_DOC_TRAD
                                     WHERE TDO_CODTRA = documento.DTR_CODI
                                    ;
                                  orden := orden + 1;
                            END LOOP;
                      END IF;
                      
                      IF LDSOCODIGO IS NOT  NULL
                      THEN
                            orden := 0;
                            FOR documento IN cursorDocumentosTram(ROLSAC1_TRAMITES.TRA_CODI, 1)
                            LOOP
                                INSERT INTO RS2_DOCPR
                                            (DOPR_CODIGO, DOPR_ORDEN, DOCPR_CODLSD)
                                  VALUES (RS2_DOCPR_SEQ.NEXTVAL,ORDEN,LDSOCODIGO);
                                
                                 INSERT INTO RS2_TRADOPR
                                            (TRDP_CODIGO,TRDP_CODDOPR, TRDP_IDIOMA, TRDP_TITULO, TRDP_DESCRI,TRDP_FICROL1)
                                    SELECT RS2_TRADOPR_SEQ.NEXTVAL,RS2_DOCPR_SEQ.CURRVAL, TDO_CODIDI, TDO_TITULO,TDO_DESCRI, TDO_CODARC
                                      FROM R1_TRAMITES_DOC_TRAD
                                     WHERE TDO_CODTRA = documento.DTR_CODI
                                    ;
                                  orden := orden + 1;
                            END LOOP;
                      END IF; 
                        
                END LOOP;

                /** INTRODUCIMOS LAS NORMATIVAS. **/
                FOR ROLSAC1_NORMATIVA IN cursorNormativasRolsac1(codigo) 
                LOOP
                    INSERT INTO RS2_PRCNOR(PRWF_CODIGO, NORM_CODIGO)
                    VALUES (CODIGO_PROCWF, ROLSAC1_NORMATIVA.PRN_CODNOR);
                END LOOP;

                /** INTRODUCIMOS LOS PUBLICO OBJETIVOS. **/
                FOR ROLSAC1_PUBOBJ IN cursorPublicoRolsac1(codigo) 
                LOOP
                    INSERT INTO RS2_PRCPUB(PRPO_CODPRWF, PRPO_TIPPOBJ)
                    VALUES (CODIGO_PROCWF, ROLSAC1_PUBOBJ.PPR_CODPOB);
                END LOOP;

                /** INTRODUCIMOS LAS MATERIAS. **/
                FOR ROLSAC1_MATERIAS IN cursorMateriasRolsac1(codigo) 
                LOOP  
                   dbms_output.put_line('materias: ' || ROLSAC1_MATERIAS.PRM_CODMAT);
                    
                    /** METEMOS EL TEMA **/
                    INSERT INTO  RS2_PRCTEM (PRTM_CODPRWF, PRTM_CODTEMA)
                      VALUES (CODIGO_PROCWF,ROLSAC1_MATERIAS.PRM_CODMAT );

                    SELECT COUNT(TPMS_CODIGO)
                      INTO CODIGOSIAEXIST
                      FROM RS2_TIPOMSI
                     WHERE TPMS_CODSIA IN (
                                SELECT MAT_CODSIA
                                 FROM R1_MATERIAS
                                WHERE MAT_CODI = ROLSAC1_MATERIAS.PRM_CODMAT
                            );
                            
                   
                    /** METEMOS EL TIPO SIA **/  
                    IF CODIGOSIAEXIST > 0 
                    THEN
                         SELECT TPMS_CODIGO
                          INTO CODIGOSIAR2
                          FROM RS2_TIPOMSI
                         WHERE TPMS_CODSIA IN (
                                    SELECT MAT_CODSIA
                                     FROM R1_MATERIAS
                                    WHERE MAT_CODI = ROLSAC1_MATERIAS.PRM_CODMAT
                                )
                           AND ROWNUM = 1;
                           
                        SELECT COUNT(*)
                          INTO EXISTE_MAT_PRC
                          FROM RS2_PRCMAS
                          WHERE PRMS_CODPRWF = CODIGO_PROCWF
                           AND  PRMS_TIPMSIA = CODIGOSIAR2;

                        /** SI EXSITE TIPO SIA, NO VOLVERLO A METER **/
                        IF EXISTE_MAT_PRC = 0 
                        THEN 
                            INSERT INTO RS2_PRCMAS(PRMS_CODPRWF, PRMS_TIPMSIA)
                            VALUES (CODIGO_PROCWF, CODIGOSIAR2);
                        END IF;
                    END IF;
                END LOOP;

                /** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
                IF LSLOPD IS NOT NULL
                THEN
                       SELECT RS2_DOCPR_SEQ.NEXTVAL
                           INTO RS2_CODPR_LOPD
                           FROM DUAL;
                           
                         INSERT INTO RS2_DOCPR
                                    (DOPR_CODIGO, DOPR_ORDEN, DOCPR_CODLSD)
                         VALUES (RS2_CODPR_LOPD,0,LSLOPD);
                         
                END IF;
                
                /** INTRODUCIMOS LAS TRADUCCIONES **/ 
                FOR ROLSAC1_TRADPROC IN cursorTradPROCEDsROLSAC1(codigo)
                LOOP
                    INSERT INTO RS2_TRAPRWF(
                        TRPW_CODIGO,
                        TRPW_CODPRWF,
                        TRPW_IDIOMA,
                        TRPW_NOMBRE,
                        TRPW_OBJETO,
                        TRPW_DESTIN,
                        TRPW_OBSER,
                        TRPW_PRRESO
                        /*TRPW_DPFINA,
                        TRPW_DPDEST,
                        TRPW_DPDOC,
                        TRPW_SVREQ,*/)
                       VALUES (
                        RS2_TRAPRWF_SEQ.NEXTVAL,
                        CODIGO_PROCWF,
                        ROLSAC1_TRADPROC.TPR_CODIDI,
                        ROLSAC1_TRADPROC.TPR_NOMBRE, 
                        ROLSAC1_TRADPROC.TPR_RESUME, 
                        ROLSAC1_TRADPROC.TPR_DESTIN,
                        ROLSAC1_TRADPROC.TPR_OBSERV,
                        ROLSAC1_TRADPROC.TPR_RESOLUCION
                       );


                        /** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
                        IF LSLOPD IS NOT NULL AND ROLSAC1_TRADPROC.TPR_LOPDIA IS NOT NULL
                        THEN
                            
                            INSERT INTO RS2_TRADOPR
                                    (TRDP_CODIGO,TRDP_CODDOPR, TRDP_IDIOMA, TRDP_TITULO, TRDP_DESCRI,TRDP_FICROL1)
                                    VALUES (RS2_TRADOPR_SEQ.NEXTVAL,RS2_CODPR_LOPD, ROLSAC1_TRADPROC.TPR_CODIDI, '','', ROLSAC1_TRADPROC.TPR_LOPDIA);
                        END IF;
                END LOOP;
                
                /** SI HAY DOCS, CREAMOS LOS FICHEROS. **/                     
                IF LSTDOC IS NOT NULL
                THEN
                    orden := 0;
                    FOR documento IN cursorDocumentos(codigo)
                    LOOP
                        INSERT INTO RS2_DOCPR
                                    (DOPR_CODIGO, DOPR_ORDEN, DOCPR_CODLSD)
                          VALUES (RS2_DOCPR_SEQ.NEXTVAL,ORDEN,LSTDOC);
                        
                         INSERT INTO RS2_TRADOPR
                                    (TRDP_CODIGO,TRDP_CODDOPR, TRDP_IDIOMA, TRDP_TITULO, TRDP_DESCRI,TRDP_FICROL1)
                            SELECT RS2_TRADOPR_SEQ.NEXTVAL,RS2_DOCPR_SEQ.CURRVAL, TDO_CODIDI, TDO_TITULO,TDO_DESCRI, TDO_CODARC
                              FROM R1_PROCEDIMIENTOS_DOC_TRAD
                             WHERE TDO_CODDOC = documento.DOC_CODI
                               /*AND TDO_CODARC IN (SELECT ARC_CODI FROM R1_ARCHIV WHERE ARC_DATOS IS NOT NULL)*/
                            ;
                          orden := orden + 1;
                    END LOOP;
                END IF;
                
                commit;
                dbms_lob.writeappend(l_clob, length('El proc ' || codigo || ' 1"' || NOMBRE || '" se ha migrado.'), 'El proc ' || codigo || ' 1"' || NOMBRE || '" se ha migrado.');
                EXCEPTION 
                WHEN OTHERS THEN
                    rollback;
                    dbms_output.put_line('SQLCODE:' || SQLCODE);
                     dbms_output.put_line('SQLERRM:' || SQLERRM);
                    dbms_lob.writeappend(l_clob, length('El proc ' || codigo || ' 2"' ||NOMBRE ||'" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n'), 'El proc ' || codigo || ' 2"' ||NOMBRE ||'" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n');
                 
              END;
        ELSE 
            dbms_lob.writeappend(l_clob, length('El proc ' || codigo || ' "' ||NOMBRE ||'" ya existe. \n'), 'El proc ' || codigo || ' "' ||NOMBRE ||'" ya existe. \n');
        END IF;


    dbms_lob.close(l_clob);
    resultado := l_clob;

EXCEPTION 
    WHEN OTHERS THEN
            ROLLBACK;
            dbms_output.put_line('SQLCODE:' || SQLCODE);
            dbms_output.put_line('SQLERRM:' || SQLERRM);
            dbms_lob.writeappend(l_clob, length('SE HA PRODUCIDO UN ERROR\n'), 'SE HA PRODUCIDO UN ERROR\n');
            dbms_lob.writeappend(l_clob, length('El error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n'), 'El error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n');
            dbms_lob.close(l_clob);
            resultado := l_clob;
END MIGRAR_PROC;