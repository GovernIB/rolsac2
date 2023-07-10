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
FROM  RSC_TRANOR@ROLSAC
WHERE TNO_CODNOR = codNOR;
maximoId    NUMBER;
    VALOR       NUMBER;
    EXISTE      NUMBER;
    CODIGO_BOLETIN NUMBER;
    NOMBRE VARCHAR2(5000);
    EXISTE_TRAD_ES NUMBER(2,0);
    EXISTE_TRAD_CA NUMBER(2,0);
    l_clob      CLOB := EMPTY_CLOB;
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
FROM  RSC_TRANOR@ROLSAC
WHERE TNO_CODNOR = codigoNormativa AND ROWNUM = 1 ;

IF LENGTH(NOMBRE) > 50
       THEN
            NOMBRE := SUBSTR(NOMBRE, 0 , 49) || '...';
END IF;

       IF EXISTE = 0
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
    NOR_NUMERO,
    coalesce (NOR_FECHA, TO_DATE('1960/01/01', 'yyyy/mm/dd')),
    NOR_CODBOL,
    NOR_FECBOL,
    NOR_NUMNOR,
    CASE NOR_VALIDA WHEN NULL THEN 'V' WHEN 1 THEN 'V' WHEN 0 THEN 'D' ELSE 'V' END,
    CASE NOR_VALIDA WHEN NULL THEN 1 WHEN 1 THEN 1 WHEN 0 THEN 0 ELSE 1 END
FROM RSC_NORMAT@ROLSAC
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
FROM RSC_TRANOR@ROLSAC
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
                dbms_lob.writeappend(l_clob, length('La NORM ' || codigoNormativa || ' "' || NOMBRE || '" se ha migrado.'), 'La NORM ' || codigoNormativa || ' "' || NOMBRE || '" se ha migrado.');
EXCEPTION
                WHEN OTHERS THEN
                   ROLLBACK;
                   dbms_lob.writeappend(l_clob, length('La NORM ' || codigoNormativa || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '.'), 'La NORM ' || codigoNormativa || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '.');
END;
COMMIT;
ELSE
            dbms_lob.writeappend(l_clob, length('La normativa ' || codigoNormativa || ' "' || NOMBRE || '" ya existe.'), 'La normativa ' || codigoNormativa || ' "' || NOMBRE || '" ya existe.');

END IF;


    /** SETEAMOS LA SECUENCIA AL MÁXIMO CÓDIGO.
    SELECT MAX(NORM_CODIGO) INTO maximoId FROM RS2_NORMA;

    EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_NORMA_SEQ INCREMENT BY ' || maximoId;
    SELECT RS2_NORMA_SEQ.NEXTVAL INTO VALOR FROM DUAL;
    EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_NORMA_SEQ INCREMENT BY 1';
    **/
COMMIT;

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
END MIGRAR_NORMATIVA;