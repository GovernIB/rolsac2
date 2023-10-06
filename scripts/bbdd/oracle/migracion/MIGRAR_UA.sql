create or replace PROCEDURE "MIGRAR_UA" (codigoUA NUMBER, codigoEntidad NUMBER, codigoUARaiz NUMBER, resultado OUT VARCHAR2)
AS 
    /* SET SERVEROUTPUT ON; */
    /** GRANT: GRANT SELECT ON R1_UNIADM TO ROLSAC2;
               GRANT SELECT ON R1_UNIADM_TRAD TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_USUARI TO ROLSAC2; 
               GRANT SELECT ON R1_UNIADM_USU TO ROLSAC2;
    **/
    /** RSC_UNIAD (* INDICA QUE EL CAMPO NO SE MIGRA):
            UNA_CODI    ---> CODIGO UA [UNAD_CODIGO]
           *UNA_BUSKEY  ---> BUSINESS KEY
           *UNA_CLVHIT  ---> CLAVE HITA
            UNA_DOMINI  ---> DOMINIO [UNAD_DOMINI]
            UNA_ORDEN   ---> ORDEN [UNAD_ORDEN]
           *UNA_VALIDA  ---> VALIDA (1 PUBLICA, 2INTERNA, 3RESERVA, 4BAJA)
            UNA_RESPON  ---> RESPONSABLE NOMBRE [UNAD_RSPNOM]
            UNA_TELEFO  ---> TELEFONO [UNAD_TFNO]
            UNA_FAX     ---> FAX [UNAD_FAX]
            UNA_EMAIL   ---> EMAIL [UNAD_EMAIL]
            UNA_SEXRES  ---> SEXO RESPONSABLE [UNAD_RSPSEX]
           *UNA_FOTOP   ---> FOTO PEQUEÃ`A
           *UNA_FOTOG   ---> FOTO GRANDE
            UNA_CODUNA  ---> CODIGO UA PADRE [UNAD_UNADPADRE]
           *UNA_CODTRT  ---> TRATAMIENTO
           *UNA_LOGOH   ---> LOGO HORIZONTAL
           *UNA_LOGOV   ---> LOGO VERTICAL
           *UNA_LOGOS   ---> LOGO S
           *UNA_LOGOT   ---> LOGO T
           *UNR_CODADM  ---> ADMINISTRACIONES REMOTAS
           *UNR_URLREM  ---> EN ADMINISTRACIONES REMOTAS, URL REMOTA
           *UNR_IDEXTE  ---> ID EXTERNO
           *UNA_TYPE    ---> TIPO UA (esto no es Tipo Unidad Administrativa sino se utiliza para una historia rara de rolsac1, unidadAdministrativa o unidadAdministrativaRemota)
           *UNA_CODESP  ---> ESPACIO TERRITORIAL
           *UNA_FECUAC  ---> NO ESTA MAPEADO EN EL HBM
            UNA_CODEST  ---> CODIGO ESTANDAR [UNAD_IDENTI]
           *UNA_NFOTO1  ---> FOTO1
           *UNA_NFOTO2  ---> FOTO2
           *UNA_NFOTO3  ---> FOTO3
           *UNA_NFOTO4  ---> FOTO4
            UNA_EMAILR  ---> EMAIL RESPONSABLE [UNAD_RSPEMA]
            UNA_CODDR3  ---> CODIGO DIR3 [UNAD_DIR3]
        (TRADUCCIONES) RSC_TRAUNA:
            TUN_CODUNA  ---> CODIGO UNA [TRUA_CODUNAD]
            TUN_CODIDI  ---> IDIOMA     [TRUA_IDIOMA]
            TUN_NOMBRE  ---> NOMBRE     [TRUA_NOMBRE]
            TUN_PRESEN  ---> PRESENTACION [TRUA_PRESEN]
            TUN_ABREVI  ---> ABREVIACION [TRUA_ABREVI]
            TUN_URL     ---> URL        [TRUA_URLWEB]
            TUN_CVRESP  ---> CV RESPONSABLE [TRUA_RSPCV]

        RS2_UNIADM
            UNAD_CODIGO   --> CODIGO UA
            UNAD_UNADPADRE--> CODIGO UA PADRE
            UNAD_DIR3     --> CODIGO DIR3
            UNAD_ORDEN    --> ORDEN
            UNAD_RSPNOM   --> RESPONSABLE NOMBRE
            UNAD_RSPEMA   --> RESPONSABLE EMAIL
            UNAD_TFNO     --> TELEFONO
            UNAD_FAX      --> FAX
            UNAD_EMAIL    --> EMAIL
            UNAD_IDENTI   --> IDENTIFICADOR
            UNAD_RSPSEX   --> RESPONSABLE SEXO
            UNAD_RSPEMA   --> RESPONSABLE EMAIL
            UNAD_DOMINI   --> DOMINIO

        (CAMPOS SIN RELACION)
            UNAD_CODENTI  --> ENTIDAD
            UNAD_TIPOUA   --> TIPO UA
            UNAD_VERSION  --> VERSION

        RELACIONES:
            RS2_USERUA    --> USUARIOS X UAS
            RS2_UADNOR    --> NORMATIVAS
            RS2_UATEMA    --> TEMAS

        TRADUCCIONES (RS2_TRAUNAD), ESTAN TODAS:
            TRUA_CODIGO   --> CODIGO TRADUCCION UA
            TRUA_CODUNAD  --> CODIGO UA
            TRUA_IDIOMA   --> IDIOMA
            TRUA_NOMBRE   --> NOMBRE
            TRUA_PRESEN   --> PRESENTACION
            TRUA_URLWEB   --> URL
            TRUA_RSPCV    --> RESPONSABLE CV
            TRUA_ABREVI   --> ABREVIACION
    ***/
    
     

    CURSOR cursorTradUAsROLSAC1 (codUA NUMBER) IS
        SELECT * 
          FROM R1_UNIADM_TRAD
         WHERE TUN_CODUNA = codUA;
    CURSOR cursorUserUAsROLSAC1 (codUA NUMBER) IS
        SELECT *
          FROM  R1_UNIADM_USU
         WHERE UNU_CODUNA = codUA;
    CURSOR cursorMateriasRolsac1 (codUA NUMBER) IS 
        SELECT *  
          FROM  R1_UNIADM_MATE
         WHERE UNM_CODUNA = codUA
           AND UNM_CODMAT IN (SELECT TEMA_CODIGO FROM RS2_TEMA);
    maximoIdUAS NUMBER;
    VALOR       NUMBER;
    INDEXAR     BOOLEAN;
    EXISTE      NUMBER;
    CUANTOS     NUMBER := 0;
    NOMBRE      VARCHAR2(5000);
    NICK        VARCHAR2(5000);
    EXISTE_USER NUMBER(2,0);
    EXISTE_UA_USER NUMBER(2,0);
    codigoUser  NUMBER(10,0);
    EXISTE_TRAD_ES NUMBER(2,0);
    EXISTE_TRAD_CA NUMBER(2,0);
    TUN_CVRESP     CLOB := EMPTY_CLOB;
    CODIGOSIAEXIST NUMBER(2,0);
    CODIGOSIAR2 NUMBER(10,0);
    EXISTE_MAT_PRC number(2,0);
BEGIN

        /** RS2_UNIADM **/
        INDEXAR := FALSE;

        SELECT COUNT(*)
        INTO EXISTE
        FROM RS2_UNIADM
        WHERE UNAD_CODIGO = codigoUA;

        SELECT TUN_NOMBRE
        INTO NOMBRE
        FROM  R1_UNIADM_TRAD
        WHERE TUN_CODUNA = codigoUA AND ROWNUM = 1 ;
        /*
        IF EXISTE = 0
        THEN
            INDEXAR := CHECK_CUELGA_UA(codigoUA,codigoUARaiz);
        END IF;*/

        IF EXISTE = 0 /*AND INDEXAR*/
            THEN
            /** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
        BEGIN
                         /** MIGRAMOS LOS DATOS BASE EN UN NUEVO CAMPO EN ROLSAC2  */
        INSERT INTO RS2_UNIADM
        ( UNAD_CODIGO,
          UNAD_UNADPADRE,
          UNAD_DIR3,
          UNAD_IDENTI,
          UNAD_ORDEN,
          UNAD_TFNO,
          UNAD_FAX,
          UNAD_EMAIL,
          UNAD_RSPNOM,
          UNAD_RSPSEX,
          UNAD_RSPEMA,
          UNAD_DOMINI,
          UNAD_CODENTI,
          UNAD_VERSION,
          UNAD_TIPOUA)
        SELECT
            codigoUA,
            UNA_CODUNA,
            UNA_CODDR3,
            UNA_CODDR3,
            UNA_ORDEN,
            replace(UNA_TELEFO,' ',''),
            replace(UNA_FAX,' ',''),
            UNA_EMAIL,
            UNA_RESPON,
            UNA_SEXRES,
            UNA_EMAILR,
            UNA_DOMINI,
            codigoEntidad,
            1,
            UNA_CODTRT
        FROM R1_UNIADM
        WHERE UNA_CODI = codigoUA;

        /** INTRODUCIMOS LAS TRADUCCIONES **/ 
        FOR ROLSAC1_TRADUA IN cursorTradUAsROLSAC1(codigoUA)
        LOOP
            INSERT INTO RS2_TRAUNAD(
                TRUA_CODIGO, 
                TRUA_CODUNAD,
                TRUA_IDIOMA,
                TRUA_NOMBRE,
                TRUA_PRESEN,
                TRUA_URLWEB,
                TRUA_RSPCV,
                TRUA_ABREVI )
               VALUES (
                RS2_TRAUNAD_SEQ.NEXTVAL,
                codigoUA,
                ROLSAC1_TRADUA.TUN_CODIDI,
                ROLSAC1_TRADUA.TUN_NOMBRE,
                ROLSAC1_TRADUA.TUN_PRESEN,
                ROLSAC1_TRADUA.TUN_URL,
                ROLSAC1_TRADUA.TUN_CVRESP,
                ROLSAC1_TRADUA.TUN_ABREVI
               );

         END LOOP; 

        SELECT COUNT(*)
        INTO EXISTE_TRAD_ES
        FROM RS2_TRAUNAD
        WHERE TRUA_CODUNAD = codigoUA
          AND TRUA_IDIOMA = 'es'
        ;

        SELECT COUNT(*)
        INTO EXISTE_TRAD_CA
        FROM RS2_TRAUNAD
        WHERE TRUA_CODUNAD = codigoUA
          AND TRUA_IDIOMA =  'ca'
        ;

        /** INTRODUCIMOS LAS MATERIAS. **/
        FOR ROLSAC1_MATERIAS IN cursorMateriasRolsac1(codigoUA) 
        LOOP  
            /**  dbms_output.put_line('materias: ' || ROLSAC1_MATERIAS.PRM_CODMAT); **/
            INSERT INTO  RS2_UATEMA (UATE_CODUNA, UATE_CODTEMA)
              VALUES (codigoUA,ROLSAC1_MATERIAS.UNM_CODMAT );
                   
        END LOOP;
            
        /** SI NO EXISTE LA TRADUCCION EN ESPANYOL, DUPLICAMOS LA CATALAN **/
        IF EXISTE_TRAD_ES = 0 AND EXISTE_TRAD_CA = 1
                            THEN
                                 INSERT INTO RS2_TRAUNAD(
                                            TRUA_CODIGO,
                                            TRUA_CODUNAD,
                                            TRUA_IDIOMA,
                                            TRUA_NOMBRE,
                                            TRUA_PRESEN,
                                            TRUA_URLWEB,
                                            TRUA_RSPCV,
                                            TRUA_ABREVI )
        SELECT  TRUA_CODIGO,
                TRUA_CODUNAD,
                'es',
                TRUA_NOMBRE,
                TRUA_PRESEN,
                TRUA_URLWEB,
                TRUA_RSPCV,
                TRUA_ABREVI
        FROM RS2_TRAUNAD
        WHERE TRUA_CODUNAD = codigoUA
          AND TRUA_IDIOMA =  'ca';
        END IF;

                             /** Introducimos los usuarios **/
        FOR ROLSAC1_USERUA IN cursorUserUAsROLSAC1(codigoUA)
        LOOP
                /** SI EXISTE EL USUARIO EN ROLSAC2, VEMOS DE AÃ`ADIR LA RELACION **/
                SELECT USU_USERNA
                INTO NICK
                FROM R1_USUARIO
                WHERE USU_CODI = ROLSAC1_USERUA.UNU_CODUSU;

                SELECT COUNT(*)
                INTO EXISTE_USER
                FROM RS2_USER
                WHERE USER_USER = NICK;

                IF EXISTE_USER > 0
                THEN

                    SELECT USER_CODIGO
                    INTO codigoUser
                    FROM RS2_USER
                    WHERE USER_USER = NICK
                      AND ROWNUM = 1;

                    SELECT COUNT(*)
                    INTO EXISTE_UA_USER
                    FROM RS2_USERUA
                    WHERE UAUS_CODUA = codigoUA
                      AND UAUS_CODUSER = codigoUser;

                    IF EXISTE_UA_USER = 0
                    THEN
                                                INSERT INTO RS2_USERUA (UAUS_CODUA,UAUS_CODUSER )
                                                  VALUES (codigoUA,codigoUser );
                    END IF;
                END IF;
        END LOOP;
        COMMIT;
        resultado := 'La UA ' || codigoUA || ' "' || NOMBRE || '" se ha migrado.';
        EXCEPTION
                    WHEN OTHERS THEN
                       ROLLBACK;
                       resultado := 'La UA ' || codigoUA || ' "' || NOMBRE || '" ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '.';
        END;
     ELSE

                IF EXISTE > 0
                THEN
                   resultado := 'La UA ' || codigoUA || ' "' || NOMBRE || '" ya existe.';
                ELSE
                   resultado := 'La UA ' || codigoUA || ' "' || NOMBRE || '" no cualga de la raiz ' || codigoUA || ' .';
                END IF;
     END IF;

EXCEPTION 
    WHEN OTHERS THEN
            ROLLBACK;
            dbms_output.put_line('SQLCODE:' || SQLCODE);
            dbms_output.put_line('SQLERRM:' || SQLERRM);
            resultado := 'SE HA PRODUCIDO UN ERROR\n' || 'El error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n';
END;