create or replace PROCEDURE "MIGRAR_NORMATIVAS_AFE" (resultado OUT CLOB) AS 
    /* SET SERVEROUTPUT ON; */
    /** GRANT: GRANT SELECT ON ROLSAC.RSC_AFECTA TO ROLSAC2;
    **/  
    l_clob      CLOB := EMPTY_CLOB;
BEGIN

    dbms_lob.createtemporary(l_clob, TRUE);
    dbms_lob.open(l_clob, dbms_lob.lob_readwrite);
    /*DBMS_OUTPUT.PUT_LINE('INICIO MIGRACION NORMATIVAS');*/
    
    
       INSERT INTO RS2_AFECTA
            (AFNO_CODIGO, AFNO_TIPAFNO,AFNO_NORORG,AFNO_NORAFE)
        SELECT RS2_AFECTA_SEQ.NEXTVAL, AFE_CODTIA, AFE_CODNOR, AFE_CODNOA
            FROM R1_NORMAT_AFECTA
           WHERE (AFE_CODTIA, AFE_CODNOR, AFE_CODNOA) NOT IN (
                    SELECT AFNO_TIPAFNO,AFNO_NORORG,AFNO_NORAFE
                     FROM RS2_AFECTA
                        ) 
             AND AFE_CODNOR IN (SELECT NORM_CODIGO FROM RS2_NORMA );
        
       dbms_lob.writeappend(l_clob, length('Las afectaciones se han migrado.'), 'Las afectaciones se han migrado se han migrado.');
 
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
END MIGRAR_NORMATIVAS_AFE;