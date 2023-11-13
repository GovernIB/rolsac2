create or replace PROCEDURE "MIGRAR_SERV" (codigo NUMBER, codigoEntidad NUMBER, resultado OUT CLOB) AS 
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

    
    CURSOR cursorTradSERVICsROLSAC1 (codSER NUMBER) IS
        SELECT * 
        FROM  R1_SERVICIOS_TRAD
        WHERE TSR_CODSER = codSER;
    CURSOR cursorNormativasRolsac1 (codSER NUMBER) IS
        SELECT * 
        FROM R1_SERVICIOS_NORM
        WHERE SRN_CODSER = codSER;
   CURSOR cursorPublicoRolsac1 ( codSER NUMBER) IS 
        SELECT *  
          FROM R1_SERVICIOS_POBJ
         WHERE PSR_CODSER = codSER;
    CURSOR cursorMateriasRolsac1 (codSER NUMBER) IS 
        SELECT *  
          FROM R1_SERVICIOS_MATE
         WHERE SRM_CODSER = codSER 
           AND SRM_CODMAT IN (SELECT CODIGO_TEMA FROM RS2_TEMA);
    CURSOR cursorDocumentos(codSer NUMBER) IS
      SELECT *
        FROM R1_SERVICIOS_DOC
       WHERE DSR_CODSER = codSer
       ORDER BY DSR_ORDEN;
    maximoId    NUMBER;
    VALOR       NUMBER;
    EXISTE      NUMBER(2,0);
    CODIGO_BOLETIN NUMBER;
    l_clob      CLOB := EMPTY_CLOB;
    WF	        NUMBER(1,0);
    WFESTADO	VARCHAR2(1 CHAR);
    INTERNO     NUMBER(1,0);
    SER_VALIDA  NUMBER(3,0);
    NOMBRE      VARCHAR2(4000);
    EXISTE_TRAD NUMBER(2,0);
    CODIGOSIA   NUMBER(10,0);
    CODIGOSIAR2 NUMBER(10,0);
    CODIGOSIAEXIST NUMBER(2,0);
    CODIGO_PROCWF NUMBER(10,0);
    EXISTE_MAT_PRC NUMBER(2,0);
    TIPOTRAM_PLANTILLA  NUMBER(10,0);
    TIPOTRAM NUMBER(10,0);
    CODPLN NUMBER(10,0);
    TRAID  VARCHAR2(50);
    TRAVER NUMBER(3,0);
    CTELEM NUMBER(1,0);
    CPRESE NUMBER(1,0);
    CTELEF NUMBER(1,0);
    PARAMS VARCHAR2(300); 
    LSTDOC NUMBER(10,0);
    LSLOPD NUMBER(10,0);
    EXISTE_ARCHIVOS_LOPD NUMBER(2,0);
    EXISTE_ARCHIVOS      NUMBER(2,0);
    orden  NUMBER(2,0);
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
         FROM R1_SERVICIOS_TRAD
        WHERE TSR_CODSER = codigo ;

        NOMBRE := 'SIN TRAD';
         
        IF EXISTE_TRAD > 0
        THEN 
            SELECT TSR_NOMBRE
              INTO NOMBRE
             FROM R1_SERVICIOS_TRAD
             WHERE TSR_CODSER = codigo 
               AND ROWNUM = 1;
        END IF; 
         
        SELECT SER_VALIDA
          INTO SER_VALIDA
          FROM R1_SERVICIOS
          WHERE SER_CODI = codigo;

         IF EXISTE = 0 
         THEN   
                 /** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
                BEGIN 
                
                 /** EL TIPOTRAM_PLANTILLA EN SERVICIOS SIEMPRE ES NULL AL MIGRARLO, NO HAY PLANTILLAS EN ROLSAC1 SERVICIOS **/
                 TIPOTRAM_PLANTILLA := NULL;
                 TIPOTRAM := NULL;
                 SELECT  SER_TRAID,
                         SER_TRAVER,
                         SER_CTELEM,
                         SER_CPRESE,
                         SER_CTELEF,
                         SER_PARAMS,
                         SER_CODPLT
                   INTO TRAID,
                        TRAVER,
                        CTELEM,
                        CPRESE,
                        CTELEF,
                        PARAMS,
                        CODPLN
                     FROM R1_SERVICIOS
                     WHERE SER_CODI = codigo;   
                
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
                                             coalesce(CPRESE,0),
                                             coalesce(CTELEM,0),
                                             CTELEF,
                                             CODPLN,
                                             TRAID,
                                             NULL,
                                             TRAVER,
                                             PARAMS,
                                             0,
                                             codigoEntidad);
                                             
                                             
                   INSERT INTO RS2_TRATPTRA(TRTT_CODIGO,
                                            TRTT_CODTPTRA,
                                            TRTT_IDIOMA,
                                            TRTT_DESCRI,
                                            TRTT_URL)
                   SELECT RS2_TRATPTRA_SEQ.NEXTVAL,
                          TIPOTRAM,
                          TSR_CODIDI,
                          NULL, /*TTR_DESCRI,*/
                          TSR_ULRSER
                    FROM R1_SERVICIOS_TRAD
                    WHERE TSR_CODSER = codigo;
                    
                  /** LA INFORMACI�N DE LOS DOCUMENTOS. **/  
                SELECT COUNT(*)
                  INTO EXISTE_ARCHIVOS_LOPD
                  FROM R1_SERVICIOS_TRAD
                 WHERE TSR_CODSER = codigo
                   AND TSR_LOPDIA IS NOT NULL;   
                   
                IF EXISTE_ARCHIVOS_LOPD > 0
                THEN
                   SELECT RS2_LSTDOC_SEQ.NEXTVAL
                     INTO LSLOPD
                     FROM DUAL;
                     
                  INSERT INTO RS2_LSTDOC (LSDO_CODIGO) VALUES (LSLOPD);
                  
                  dbms_output.put_line('LSLOPD: ' || LSLOPD);
                END IF;
                
                SELECT COUNT(*) 
                  INTO EXISTE_ARCHIVOS
                  FROM R1_SERVICIOS_DOC 
                 WHERE DSR_CODSER = codigo ;
                
                IF EXISTE_ARCHIVOS > 0
                THEN
                   SELECT RS2_LSTDOC_SEQ.NEXTVAL
                     INTO LSTDOC
                     FROM DUAL;
                     
                     INSERT INTO RS2_LSTDOC (LSDO_CODIGO) VALUES (LSTDOC);
                END IF; 

                 /** MIGRAMOS LOS DATOS BASE EN UN NUEVO CAMPO EN ROLSAC2  */
                 INSERT INTO RS2_PROC
                      ( PROC_CODIGO, 
                        PROC_TIPO, 
                        PROC_SIACOD, 
                        PROC_SIAEST, 
                        PROC_SIAFC, 
                        PROC_FECACT)
                     SELECT 
                        SER_CODI,
                        'S',
                        SER_CODSIA,
                        SER_ESTSIA,
                        SER_FECSIA,
                        SER_FECACT
                     FROM R1_SERVICIOS
                     WHERE SER_CODI = codigo;

                     /** SEGUN ROLSAC1, VALIDACION ES:
                     PUBLICA = 1, INTERNA = 2, RESERVA = 3 BAJA = 4;
                     PUBLICA SERA DEFINITIVO Y PUBLICADO
                     INTERNA SERA ENMODIFICACION Y MODIFICACION
                     RESERVA SERA DEFINITIVO Y RESERVA
                     BAJA SERA DEFINITIVO Y BORRADO
                     */

                     IF SER_VALIDA = 1
                     THEN
                         WF := 0;
                         WFESTADO := 'P';
                         INTERNO := 0;
                     ELSIF SER_VALIDA = 2
                     THEN
                         WF := 1;
                         WFESTADO := 'M';
                         INTERNO := 1;
                     ELSIF SER_VALIDA = 3
                     THEN
                         WF := 0;
                         WFESTADO := 'R';
                         INTERNO := 1;
                     ELSE /** SER_VALIDA = 4 **/ 
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
                        PRWF_CODUAR,
                        PRWF_CODUAI,
                        PRWF_INTERNO,
                        PRWF_RSNOM,
                        PRWF_RSEMA,
                        PRWF_RSTFNO,
                        PRWF_DPTIPLEGI,
                        PRWF_SVTPRE,
                        PRWF_FECPUB,
                        PRWF_FECCAD,
                        PRWF_COMUN, 
                        PRWF_SVTREL,
                        PROC_LOPDACT,
                        PRWF_SVPRES,
                        PRWF_SVELEC,
                        PRWF_SVTEL,
                        PRWF_LSTDOC,
                        PRWF_LSLOPD)
                    SELECT
                        CODIGO_PROCWF,
                        codigo,
                        WF,
                        WFESTADO,
                        SER_SERRSP,
                        SER_SERRSP,
                        INTERNO,
                        coalesce (SER_NOMRSP, 'Desconegut'),
                        SER_CORREO,
                        SER_TELEFO,
                        SER_CODLEG,
                        TIPOTRAM_PLANTILLA,
                        SER_FECPUB,
                        SER_FECDES,
                        SER_COMUN,
                        TIPOTRAM,
                        SER_CLOPD,
                        SER_CPRESE,
                        SER_CTELEM,
                        SER_CTELEF,
                        LSTDOC,
                        LSLOPD
                     FROM R1_SERVICIOS
                     WHERE SER_CODI = codigo;

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
                    FOR ROLSAC1_TRADSERV IN cursorTradSERVICsROLSAC1(codigo)
                    LOOP
                        INSERT INTO RS2_TRAPRWF(
                            TRPW_CODIGO,
                            TRPW_CODPRWF,
                            TRPW_IDIOMA,
                            TRPW_NOMBRE,
                            TRPW_OBJETO,
                            TRPW_DESTIN,
                            TRPW_OBSER,
                            TRPW_SVREQ,
                            TRPW_PRRESO)
                           VALUES (
                            RS2_TRAPRWF_SEQ.NEXTVAL,
                            CODIGO_PROCWF,
                            ROLSAC1_TRADSERV.TSR_CODIDI,
                            ROLSAC1_TRADSERV.TSR_NOMBRE, 
                            ROLSAC1_TRADSERV.TSR_OBJETO, 
                            ROLSAC1_TRADSERV.TSR_DESTIN,
                            ROLSAC1_TRADSERV.TSR_OBSERV,
                            ROLSAC1_TRADSERV.TSR_ULRSER,
                            ROLSAC1_TRADSERV.TSR_REQUIS
                           );
                           
                           
                            /** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
                            IF LSLOPD IS NOT NULL AND ROLSAC1_TRADSERV.TSR_LOPDIA IS NOT NULL
                            THEN
                               
                                INSERT INTO RS2_TRADOPR
                                        (TRDP_CODIGO,TRDP_CODDOPR, TRDP_IDIOMA, TRDP_TITULO, TRDP_DESCRI,TRDP_FICROL1)
                                        VALUES (RS2_TRADOPR_SEQ.NEXTVAL,RS2_CODPR_LOPD, ROLSAC1_TRADSERV.TSR_CODIDI, '','', ROLSAC1_TRADSERV.TSR_LOPDIA);
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
                                SELECT RS2_TRADOPR_SEQ.NEXTVAL,RS2_DOCPR_SEQ.CURRVAL, TDS_CODIDI, TDS_TITULO,TDS_DESCRI, TDS_CODARC
                                  FROM R1_SERVICIOS_DOC_TRAD
                                 WHERE TDS_CODDSR = documento.DSR_CODI
                                ;
                              orden := orden + 1;
                        END LOOP;
                    END IF;
                    /** INTRODUCIMOS LAS MATERIAS. **/
                FOR ROLSAC1_MATERIAS IN cursorMateriasRolsac1(codigo) 
                LOOP  
                   dbms_output.put_line('materias: ' || ROLSAC1_MATERIAS.SRM_CODMAT);
                    
                    /** METEMOS EL TEMA **/
                    INSERT INTO  RS2_PRCTEM (PRTM_CODPRWF, PRTM_CODTEMA)
                      VALUES (CODIGO_PROCWF,ROLSAC1_MATERIAS.SRM_CODMAT );

                    SELECT COUNT(TPMS_CODIGO)
                      INTO CODIGOSIAEXIST
                      FROM RS2_TIPOMSI
                     WHERE TPMS_CODSIA IN (
                                SELECT MAT_CODSIA
                                 FROM R1_MATERIAS
                                WHERE MAT_CODI = ROLSAC1_MATERIAS.SRM_CODMAT
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
                                    WHERE MAT_CODI = ROLSAC1_MATERIAS.SRM_CODMAT
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
                     /** INTRODUCIMOS LAS NORMATIVAS. **/
                FOR ROLSAC1_NORMATIVA IN cursorNormativasRolsac1(codigo) 
                LOOP
                    INSERT INTO RS2_PRCNOR(PRWF_CODIGO, NORM_CODIGO)
                    VALUES (CODIGO_PROCWF, ROLSAC1_NORMATIVA.SRN_CODNOR);
                END LOOP;

                /** INTRODUCIMOS LOS PUBLICO OBJETIVOS. **/
                FOR ROLSAC1_PUBOBJ IN cursorPublicoRolsac1(codigo) 
                LOOP
                    INSERT INTO RS2_PRCPUB(PRPO_CODPRWF, PRPO_TIPPOBJ)
                    VALUES (CODIGO_PROCWF, ROLSAC1_PUBOBJ.PSR_CODPOB);
                END LOOP;
                        
                    commit;
                    dbms_lob.writeappend(l_clob, length('El serv ' || codigo || ' "' || NOMBRE || '" se ha migrado.'), 'El serv ' || codigo || ' "' || NOMBRE || '" se ha migrado.');

                    EXCEPTION 
                    WHEN OTHERS THEN
                       dbms_lob.writeappend(l_clob, length('El serv ' || codigo || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n'), 'El serv ' || codigo || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n');
                       rollback;
                  END;
            ELSE 
                dbms_lob.writeappend(l_clob, length('El serv ' || codigo || ' "' || NOMBRE || '" ya existe. \n'), 'El serv ' || codigo || ' "' || NOMBRE || '" ya existe. \n');
            END IF; 


    dbms_output.put_line(l_clob);
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
END MIGRAR_SERV;