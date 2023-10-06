create or replace PROCEDURE "MIGRAR_NORMATIVA" (codigoNormativa NUMBER, codigoEntidad NUMBER, resultado OUT CLOB) AS 
    /* SET SERVEROUTPUT ON; */
    /** GRANT: GRANT SELECT ON ROLSAC.RSC_NORMAT TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_TRANOR TO ROLSAC2; 
    **/  
    /** RSC_NORMAT (* INDICA QUE EL CAMPO NO SE MIGRA):
            NOR_CODI    ---> CODIGO NORMATIVA [UNAD_CODIGO]
           *NOR_TYPE    ---> INDICA EL TIPO PERO DE UNA MANERA PECULIAR EN ROLSAC1 (normativa o normativaRemoto)
            NOR_NUMERO  ---> NUMERO [NORM_NUMERO]
           *NOR_REGIST  ---> REGISTRO
           *NOR_LEY     ---> LEY
            NOR_FECHA   ---> FECHA [NORM_FCAPRO]
            NOR_FECBOL  ---> FECHA BOLETIN [NORM_BOLEFEC]
           *NOR_VALIDA  ---> VALIDACION (1 PUBLICA, 2INTERNA, 3RESERVA, 4BAJA)
            NOR_CODBOL  ---> CODIGO BOLETIN [NORM_BOLECOD]
            NOR_CODTIP  ---> TIPO NORMATIVA [NORM_TIPNOR]
            NOR_CODUNA  ---> CODIGO UA [AHORA ES CODIGO ENTIDAD]
           *NOR_CODIVUDS --> NOSEUTILIZA
           *NOR_DESCCODIVUDS --> NOSEUTILIZA
           *NER_IDEXTE  ---> EN NORMATIVA REMOTA , EL ID EXTERNO
           *NER_URLREM  ---> EN NORMATIVA REMOTA , LA URL
           *NER_CODADM  ---> EN NORMATIVA REMOTA , LA UA REMOTA
            NOR_NUMNOR  ---> NUMERO NORMATIVA [NORM_BOLENUM]
           *NOR_VALIDAANT -> VALIDA ANTIGUO?
           *NOR_CODBOLANT -> COD. BOLETIN ANTIGUO?
           *NOR_TYPEN   ---> CODIGO VIEJOS 
           *NOR_VALIDN   --> CODIGO VIEJOS
           *NOR_CODBON   -->  CODIGO VIEJOS
           *NOR_CODBOL_ANT -> CODIGO BOLETIN ANTIGUO
           *NOR_DATVAL  ---> DATOS VALIDOS
        (TRADUCCIONES) RSC_TRANOR:
            TNO_CODNOR  ---> CODIGO NORMATIVA [TRNO_CODTPNO]
            TNO_CODIDI  ---> IDIOMA     [TRNO_IDIOMA]
           *TNO_SECCIO 
           *TNO_APARTA 
           *TNO_PAGINI 
           *TNO_PAGFIN 
            TNO_TITULO  ---> TITULO [TRNO_TITUL]
            TNO_ENLACE  ---> ENLACE/URL [TRNO_URL]
           *TNO_RESPON  ---> NOMBRE RESPONSABLE [TRNO_RESPNOM]
           *TNO_CODARC
           *TNO_OBSERV 

        RS2_NORMA 
            NORM_CODIGO   --> CODIGO NORMATIVA
            NORM_CODENTI  --> CODIGO DE LA ENTIDAD
            NORM_TIPNOR   --> TIPO NORMATIVA
            NORM_NUMERO   --> NUMERO
            NORM_FCAPRO   --> FECHA APROBACION
            NORM_BOLECOD  --> BOLETIN CODIGO
            NORM_BOLEFEC  --> BOLETIN FECHA
            NORM_BOLENUM  --> BOLETIN NUMERO 
            NORM_ESTADO   --> ESTADO

        (CAMPOS SIN RELACION)             

        RELACIONES:
            RS2_DOCNORM   --> DOCUMENTOS NORM
            RS2_AFECTA    --> AFECTACIONES Y AFECTACIONES ORIGEN

        TRADUCCIONES (RS2_TRANORM), ESTAN TODAS:
            TRNO_CODIGO   --> CODIGO TRADUCCION NORM
            TRNO_CODTPNO  --> CODIGO NORM
            TRNO_IDIOMA   --> IDIOMA
            TRNO_TITUL    --> TITULO
            TRNO_URL      --> URL
            TRNO_RESPNOM  --> RESPONSABLE
    ***/

    CURSOR cursorTradNORMATIVASsROLSAC1 (codNOR NUMBER) IS
        SELECT * 
        FROM  R1_NORMAT_TRAD
        WHERE TNO_CODNOR = codNOR;
    CURSOR cursorUAsNORMATIVASsROLSAC1 (codNOR NUMBER) IS
        SELECT * 
        FROM  R1_UNIADM_NORM
        WHERE UNN_CODNOR = codNOR;
    CURSOR cursorAfectaOrigenNormsROLSAC1 (codNOR NUMBER) IS
        SELECT * 
        FROM  R1_NORMAT_AFECTA
        WHERE AFE_CODNOR = codNOR;  
    CURSOR cursorAfectaDestiNormsROLSAC1 (codNOR NUMBER) IS
        SELECT * 
        FROM  R1_NORMAT_AFECTA
        WHERE AFE_CODNOA = codNOR;    
    CURSOR cursorDocsNORMATIVASsROLSAC1 (codNOR NUMBER) IS
        SELECT * 
        FROM  R1_NORMAT_DOC
        WHERE DNO_CODNOR = codNOR;
    maximoId    NUMBER;
    VALOR       NUMBER;
    EXISTE      NUMBER;
    CODIGO_BOLETIN NUMBER;
    NOMBRE VARCHAR2(5000);
    EXISTE_TRAD_ES NUMBER(2,0);
    EXISTE_TRAD_CA NUMBER(2,0);
    l_clob      CLOB := EMPTY_CLOB;
    TOTAL_TRADS    NUMBER(2,0);
    EXISTE_RELACION NUMBER(2,0);
    EXISTE_NORMATIVA NUMBER(2,0);
    EXISTE_UA        NUMBER(2,0);
BEGIN

    dbms_lob.createtemporary(l_clob, TRUE);
    dbms_lob.open(l_clob, dbms_lob.lob_readwrite);
    /*DBMS_OUTPUT.PUT_LINE('INICIO MIGRACION NORMATIVAS');*/


    /** RS2_NORMA **/
      SELECT COUNT(*) 
           INTO EXISTE 
           FROM RS2_NORMA
           WHERE NORM_CODIGO = codigoNormativa;

      SELECT TNO_TITULO
        INTO NOMBRE
        FROM  R1_NORMAT_TRAD
        WHERE TNO_CODNOR = codigoNormativa AND ROWNUM = 1 ;

       IF LENGTH(NOMBRE) > 50 
       THEN
            NOMBRE := SUBSTR(NOMBRE, 0 , 49) || '...';
       END IF;

      SELECT COUNT(*)
        INTO TOTAL_TRADS
        FROM R1_NORMAT_TRAD
       WHERE TNO_CODNOR = codigoNormativa;    
      
       IF EXISTE = 0 AND TOTAL_TRADS > 0
       THEN   
             /** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
            BEGIN 

             INSERT INTO RS2_NORMA
                  ( NORM_CODIGO,
                    NORM_CODENTI,
                    NORM_TIPNOR,
                    NORM_NUMERO,
                    NORM_FCAPRO,
                    NORM_BOLECOD,
                    NORM_BOLEFEC,
                    NORM_BOLENUM,
                    NORM_ESTADO,
                    NORM_VIGENT)
                 SELECT 
                    NOR_CODI,
                    codigoEntidad,
                    coalesce( NOR_CODTIP, 66),
                    NOR_NUMNOR,
                    coalesce (NOR_FECHA, TO_DATE('1960/01/01', 'yyyy/mm/dd')),
                    NOR_CODBOL, 
                    NOR_FECBOL,
                    NOR_NUMERO,
                    CASE NOR_VALIDA WHEN NULL THEN 'V' WHEN 1 THEN 'V' WHEN 0 THEN 'D' ELSE 'V' END,
                    CASE NOR_VALIDA WHEN NULL THEN 1 WHEN 1 THEN 1 WHEN 0 THEN 0 ELSE 1 END
             FROM R1_NORMAT
                 WHERE NOR_CODI = codigoNormativa
                 ;  

                 INSERT INTO RS2_TRANORM(
                        TRNO_CODIGO, 
                        TRNO_CODTPNO,
                        TRNO_IDIOMA,
                        TRNO_TITUL,
                        TRNO_URL, 
                        TRNO_RESPNOM)
                 SELECT RS2_TRAUNAD_SEQ.NEXTVAL,
                        codigoNormativa,
                        TNO_CODIDI,
                        TNO_TITULO,
                        TNO_ENLACE,
                        TNO_RESPON 
                   FROM R1_NORMAT_TRAD
                  WHERE TNO_CODNOR = codigoNormativa;    

                SELECT COUNT(*) 
                  INTO EXISTE_TRAD_ES
                  FROM RS2_TRANORM
                  WHERE TRNO_CODTPNO = codigoNormativa
                    AND TRNO_IDIOMA = 'es'
                  ;
                
                 SELECT COUNT(*) 
                  INTO EXISTE_TRAD_CA
                  FROM RS2_TRANORM
                  WHERE TRNO_CODTPNO = codigoNormativa
                    AND TRNO_IDIOMA = 'ca'
                  ;
                  
                /** SI NO EXISTE LA TRADUCCION EN ESPAÑOL, DUPLICAMOS LA CATALAN **/  
                IF EXISTE_TRAD_ES = 0 AND EXISTE_TRAD_CA = 1
                THEN
                        INSERT INTO RS2_TRANORM(
                                TRNO_CODIGO, 
                                TRNO_CODTPNO,
                                TRNO_IDIOMA,
                                TRNO_TITUL,
                                TRNO_URL, 
                                TRNO_RESPNOM)
                        SELECT  TRNO_CODIGO, 
                                TRNO_CODTPNO,
                                'es',
                                TRNO_TITUL,
                                TRNO_URL, 
                                TRNO_RESPNOM
                            FROM RS2_TRANORM
                          WHERE TRNO_CODTPNO = codigoNormativa
                            AND TRNO_IDIOMA = 'ca';
                END IF;
                
                /** La relacion de UAs con Normativas **/
                FOR normUA IN cursorUAsNORMATIVASsROLSAC1(codigoNormativa)
                LOOP
                    SELECT COUNT(*)
                      INTO EXISTE_RELACION
                      FROM RS2_UADNOR
                      WHERE UANO_CODNORM = codigoNormativa
                        AND UANO_CODUNA = normUA.UNN_CODUNA;
                        
                    SELECT COUNT(*)
                      INTO EXISTE_UA
                      FROM RS2_UNIADM
                      WHERE UNAD_CODIGO = normUA.UNN_CODUNA;
                    
                        
                    IF EXISTE_RELACION = 0 AND EXISTE_UA = 1
                    THEN 
                        INSERT INTO RS2_UADNOR (UANO_CODNORM, UANO_CODUNA)
                          VALUES (codigoNormativa,normUA.UNN_CODUNA); 
                    END IF;
                END LOOP;
                
                FOR documento IN cursorDocsNORMATIVASsROLSAC1(codigoNormativa)
                LOOP
                    INSERT INTO RS2_DOCNORM
                                (DONO_CODIGO, DONO_CODNORM)
                      VALUES (RS2_DOCNORM_SEQ.NEXTVAL,codigoNormativa);
                    
                     INSERT INTO RS2_TRADONO
                                (TRDN_CODIGO, TRDN_CODDONO , TRDN_IDIOMA, TRDN_TITULO, TRDN_URL, TRDN_DESCR, TRDN_FICROL1)
                        SELECT RS2_TRADONO_SEQ.NEXTVAL,RS2_DOCNORM_SEQ.CURRVAL, TDN_CODIDI, coalesce(TDN_TITULO,' '),TDN_ENLACE, TDN_DESCRI, TDN_CODARC
                          FROM R1_NORMAT_DOC_TRAD
                         WHERE TDN_CODDNR = documento.DNO_CODI
                        ;
                END LOOP;
                
                /** La afectacion con Normativas DE ORIGEN **/
                FOR normAFECTA IN cursorAfectaOrigenNormsROLSAC1(codigoNormativa)
                LOOP
                    SELECT COUNT(*)
                      INTO EXISTE_RELACION
                      FROM RS2_AFECTA
                      WHERE AFNO_NORORG = codigoNormativa
                        AND AFNO_NORAFE = normAFECTA.AFE_CODNOA;
                    
                      
                     SELECT COUNT(*)
                      INTO EXISTE_NORMATIVA
                      FROM RS2_NORMA
                      WHERE NORM_CODIGO = normAFECTA.AFE_CODNOA;
                    
                        
                    IF EXISTE_RELACION = 0 AND EXISTE_NORMATIVA = 1
                    THEN 
                        INSERT INTO RS2_AFECTA (AFNO_CODIGO, AFNO_TIPAFNO, AFNO_NORORG, AFNO_NORAFE)
                          VALUES (RS2_AFECTA_SEQ.nextval, normAFECTA.AFE_CODTIA, codigoNormativa, normAFECTA.AFE_CODNOA); 
                    END IF;
                END LOOP;
                
                /** La afectacion con Normativas DE DESTINO **/
                FOR normAFECTA IN cursorAfectaDestiNormsROLSAC1(codigoNormativa)
                LOOP
                    SELECT COUNT(*)
                      INTO EXISTE_RELACION
                      FROM RS2_AFECTA
                      WHERE AFNO_NORAFE = codigoNormativa
                        AND AFNO_NORORG = normAFECTA.AFE_CODNOR;
                    
                      
                     SELECT COUNT(*)
                      INTO EXISTE_NORMATIVA
                      FROM RS2_NORMA
                      WHERE NORM_CODIGO = normAFECTA.AFE_CODNOR;
                    
                        
                    IF EXISTE_RELACION = 0 AND EXISTE_NORMATIVA = 1
                    THEN 
                        INSERT INTO RS2_AFECTA (AFNO_CODIGO, AFNO_TIPAFNO, AFNO_NORORG, AFNO_NORAFE)
                          VALUES (RS2_AFECTA_SEQ.nextval, normAFECTA.AFE_CODTIA, normAFECTA.AFE_CODNOR, codigoNormativa); 
                    END IF;
                END LOOP;
                
                COMMIT;
                dbms_lob.writeappend(l_clob, length('La NORM ' || codigoNormativa || ' "' || NOMBRE || '" se ha migrado.'), 'La NORM ' || codigoNormativa || ' "' || NOMBRE || '" se ha migrado.');
                EXCEPTION 
                WHEN OTHERS THEN
                   ROLLBACK;
                   dbms_lob.writeappend(l_clob, length('La NORM ' || codigoNormativa || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '.'), 'La NORM ' || codigoNormativa || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '.');
              END;
       ELSE 
            IF TOTAL_TRADS = 0
            THEN
                dbms_lob.writeappend(l_clob, length('La normativa ' || codigoNormativa || ' "' || NOMBRE || '" no tiene traducciones.'), 'La normativa ' || codigoNormativa || ' "' || NOMBRE || '" no tiene traducciones.');
            ELSE 
                dbms_lob.writeappend(l_clob, length('La normativa ' || codigoNormativa || ' "' || NOMBRE || '" ya existe.'), 'La normativa ' || codigoNormativa || ' "' || NOMBRE || '" ya existe.');
            END IF;
       END IF;

 
    dbms_lob.close(l_clob);
    resultado := l_clob;
EXCEPTION 
    WHEN OTHERS THEN
            ROLLBACK;
            dbms_output.put_line('SQLCODE:' || SQLCODE);
            dbms_output.put_line('SQLERRM:' || SQLERRM);
            dbms_lob.writeappend(l_clob, length('SE HA PRODUCIDO UN ERROR CON ' || codigoNormativa || ' \n'), 'SE HA PRODUCIDO UN ERROR CON ' || codigoNormativa || ' \n');
            dbms_lob.writeappend(l_clob, length('El error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n'), 'El error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n');
            dbms_lob.close(l_clob);
            resultado := l_clob;
END MIGRAR_NORMATIVA;