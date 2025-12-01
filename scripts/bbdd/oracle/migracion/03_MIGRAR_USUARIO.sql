create or replace PROCEDURE "MIGRAR_USUARIO" (usuario VARCHAR2, resultado OUT CLOB)
AS 
    /* SET SERVEROUTPUT ON; */
    /** GRANT: GRANT SELECT ON R1_USUARIO TO ROLSAC2;
    **/ 
    /** RSC_USUARI (* INDICA QUE EL CAMPO NO SE MIGRA):
            USU_CODI    ---> CODIGO USER [USER_CODIGO]
            USU_USERNA  ---> NICK [USER_USER]
           *USU_PASSWO  ---> CONTRA 
            USU_NOMBRE  ---> NOMBRE [USER_NOMBRE]
           *USU_OBSERV  ---> OBSERVACIONES 
           *USU_PERFIL  ---> PERFIL 
            USU_EMAIL   ---> EMAIL [USER_EMAIL]
           *USU_PERMISOS --> PERMISOS

        RS2_USER
            USER_CODIGO
            USER_USER
            USER_NOMBRE
            USER_EMAIL

    ***/

    maximoIdUAS NUMBER;
    VALOR       NUMBER; 
    INDEXAR     BOOLEAN;
    EXISTE      NUMBER;
    CUANTOS     NUMBER := 0;
    l_clob      CLOB := EMPTY_CLOB; 
BEGIN
    dbms_lob.createtemporary(l_clob, TRUE);
    dbms_lob.open(l_clob, dbms_lob.lob_readwrite);

    /** RS2_UNIADM **/
    INDEXAR := FALSE;
    SELECT COUNT(*) 
    INTO EXISTE 
    FROM RS2_USER
    WHERE USER_USER = usuario;

    IF EXISTE = 0 /*AND INDEXAR*/
    THEN 
    /** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
     BEGIN 


                 /** MIGRAMOS LOS DATOS BASE EN UN NUEVO CAMPO EN ROLSAC2  */
                 INSERT INTO RS2_USER
                      ( USER_CODIGO,
                        USER_USER,
                        USER_NOMBRE,
                        USER_EMAIL)
                     SELECT 
                        RS2_USER_SEQ.NEXTVAL,
                        USUARIO,
                        USU_NOMBRE,
                        USU_EMAIL
                     FROM R1_USUARIO
                     WHERE USU_USERNA = usuario; 

        COMMIT;
        dbms_lob.writeappend(l_clob, length('El user ' || usuario || ' se ha migrado.'), 'El user ' || usuario || ' se ha migrado.');
        EXCEPTION 
            WHEN OTHERS THEN
               ROLLBACK;
               dbms_lob.writeappend(l_clob, length('El user ' || usuario || ' ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n'), 'El user ' || usuario || ' ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n');
        END;
    ELSE 
        dbms_lob.writeappend(l_clob, length('El user ' || usuario || ' ya existe. \n'), 'El user ' || usuario || ' ya existe. \n');
    END IF;

    /*DBMS_OUTPUT.PUT_LINE('FIN MIGRACION UAS *');*/
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
END;