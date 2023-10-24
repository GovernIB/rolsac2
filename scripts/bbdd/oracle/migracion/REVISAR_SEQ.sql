create or replace PROCEDURE "REVISAR_SEQ"
AS
    maximoId NUMBER;
    VALOR    NUMBER;
    VALOR_SEQ NUMBER;
BEGIN

    /** SETEAMOS EL MAXIMO VALOR DE NORMATIVAS SEQ. **/
    SELECT MAX(NORM_CODIGO) INTO maximoId FROM RS2_NORMA;
    SELECT RS2_NORMA_SEQ.NEXTVAL
      INTO VALOR_SEQ
      FROM DUAL;

    IF maximoId > VALOR_SEQ
    THEN
        EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_NORMA_SEQ INCREMENT BY ' || maximoId;
        SELECT RS2_NORMA_SEQ.NEXTVAL INTO VALOR FROM DUAL;
        EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_NORMA_SEQ INCREMENT BY 1';
        COMMIT;
    END IF;


    /** SETEAMOS EL MAXIMO VALOR DE UAS SEQ. **/
    SELECT MAX(UNAD_CODIGO) INTO maximoId FROM RS2_UNIADM;
    SELECT RS2_UNIADM_SEQ.NEXTVAL
      INTO VALOR_SEQ
      FROM DUAL;

    IF maximoId > VALOR_SEQ
    THEN
        EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_UNIADM_SEQ INCREMENT BY ' || maximoId;
        SELECT RS2_UNIADM_SEQ.NEXTVAL INTO VALOR FROM DUAL;
        EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_UNIADM_SEQ INCREMENT BY 1';
        COMMIT;
    END IF;


     /** SETEAMOS EL MAXIMO VALOR DE PROC SEQ. **/
    SELECT MAX(PROC_CODIGO) INTO maximoId FROM RS2_PROC;
    SELECT RS2_PROC_SEQ.NEXTVAL
      INTO VALOR_SEQ
      FROM DUAL;

    IF maximoId > VALOR_SEQ
    THEN
        EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_PROC_SEQ INCREMENT BY ' || maximoId;
        SELECT RS2_PROC_SEQ.NEXTVAL INTO VALOR FROM DUAL;
        EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_PROC_SEQ INCREMENT BY 1';
        COMMIT;
    END IF;
    
    
     /** SETEAMOS EL MAXIMO VALOR DE TRAMITES SEQ. **/
    SELECT MAX(PRTA_CODIGO) INTO maximoId FROM RS2_PRCTRM;
    SELECT RS2_PRCTRM_SEQ.NEXTVAL
    INTO VALOR_SEQ
    FROM DUAL;

    IF maximoId > VALOR_SEQ
        THEN
            EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_PRCTRM_SEQ INCREMENT BY ' || maximoId;
    SELECT RS2_PRCTRM_SEQ.NEXTVAL INTO VALOR FROM DUAL;
    EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_PRCTRM_SEQ INCREMENT BY 1';
    COMMIT;
    END IF;

    /** ACTIVAMOS LA FK DE UA **/
    /* EXECUTE IMMEDIATE 'ALTER TABLE RS2_UNIADM ENABLE CONSTRAINT RS2_UNIADM_UNIADM_FK'; */

END;