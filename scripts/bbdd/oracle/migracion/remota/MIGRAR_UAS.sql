create or replace PROCEDURE MIGRAR_UAS (codigoUA NUMBER, codigoEntidad NUMBER, resultado OUT CLOB)
AS
    /* SET SERVEROUTPUT ON; */
    /** GRANT: GRANT SELECT ON ROLSAC.RSC_UNIADM TO ROLSAC2;
               GRANT SELECT ON ROLSAC.RSC_TRAUNA TO ROLSAC2;
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
           *UNA_FOTOP   ---> FOTO PEQUEÑA
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
    CURSOR cursorUAsROLSAC1 IS
SELECT *
FROM RSC_UNIADM@ROLSAC;
CURSOR cursorTradUAsROLSAC1 (codUA NUMBER) IS
SELECT *
FROM  RSC_TRAUNA@ROLSAC
WHERE TUN_CODUNA = codUA;
maximoIdUAS NUMBER;
    VALOR       NUMBER;
    INDEXAR     BOOLEAN;
    EXISTE      NUMBER;
    CUANTOS     NUMBER := 0;
    l_clob      CLOB := EMPTY_CLOB;
BEGIN
     dbms_lob.createtemporary(l_clob, TRUE);
    dbms_lob.open(l_clob, dbms_lob.lob_readwrite);
    dbms_lob.writeappend(l_clob, length('INICIO MIGRACION UAS\n'), 'INICIO MIGRACION UAS\n');
    /*DBMS_OUTPUT.PUT_LINE('INICIO MIGRACION UAS');*/

    /** DESACTIVAMOS LA FK DE UA PADRE, SINO HABRÍA QUE REALIZAR UN ORDENADO COMPLEJO**/
EXECUTE IMMEDIATE 'ALTER TABLE RS2_UNIADM DISABLE CONSTRAINT RS2_UNIADM_UNIADM_FK';

/** RS2_UNIADM **/
FOR ROLSAC1_UA IN cursorUAsROLSAC1
    LOOP
         INDEXAR := FALSE;
SELECT COUNT(*)
INTO EXISTE
FROM RS2_UNIADM
WHERE UNAD_CODIGO = ROLSAC1_UA.UNA_CODI;

IF EXISTE = 0
         THEN
            INDEXAR := CHECK_CUELGA_UA(ROLSAC1_UA.UNA_CODI,codigoUA);
END IF;

         IF EXISTE = 0 AND INDEXAR
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
  UNAD_VERSION)
VALUES (
           ROLSAC1_UA.UNA_CODI,
           ROLSAC1_UA.UNA_CODUNA,
           ROLSAC1_UA.UNA_CODDR3,
           ROLSAC1_UA.UNA_CODDR3,
           ROLSAC1_UA.UNA_ORDEN,
           replace(ROLSAC1_UA.UNA_TELEFO,' ',''),
           replace(ROLSAC1_UA.UNA_FAX,' ',''),
           ROLSAC1_UA.UNA_EMAIL,
           ROLSAC1_UA.UNA_RESPON,
           ROLSAC1_UA.UNA_SEXRES,
           ROLSAC1_UA.UNA_EMAILR,
           ROLSAC1_UA.UNA_DOMINI,
           codigoEntidad,
           1
       );

/** INTRODUCIMOS LAS TRADUCCIONES **/
FOR ROLSAC1_TRADUA IN cursorTradUAsROLSAC1(ROLSAC1_UA.UNA_CODI)
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
                                    ROLSAC1_UA.UNA_CODI,
                                    ROLSAC1_TRADUA.TUN_CODIDI,
                                    ROLSAC1_TRADUA.TUN_NOMBRE,
                                    ROLSAC1_TRADUA.TUN_PRESEN,
                                    ROLSAC1_TRADUA.TUN_URL,
                                    ROLSAC1_TRADUA.TUN_CVRESP,
                                    ROLSAC1_TRADUA.TUN_ABREVI
                                   );

END LOOP;
EXCEPTION
                    WHEN OTHERS THEN
                       dbms_lob.writeappend(l_clob, length('La UA ' || ROLSAC1_UA.UNA_CODI || ' ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n'), 'La UA ' || ROLSAC1_UA.UNA_CODI || ' ha dado el error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n');
END;
ELSE

                IF EXISTE > 0
                THEN
                    dbms_lob.writeappend(l_clob, length('La UA ' || ROLSAC1_UA.UNA_CODI || ' ya existe. \n'), 'La UA ' || ROLSAC1_UA.UNA_CODI || ' ya existe. \n');
ELSE
                    dbms_lob.writeappend(l_clob, length('La UA ' || ROLSAC1_UA.UNA_CODI || ' no cualga de la raiz ' || codigoUA || ' . \n'), 'La UA ' || ROLSAC1_UA.UNA_CODI || ' no cualga de la raiz ' || codigoUA || ' . \n');
END IF;
            /* ELSE
                DBMS_OUTPUT.PUT_LINE('NO SE INDEXA UA : ' || ROLSAC1_UA.UNA_CODI || ' EXISTE:' || EXISTE );*/
END IF;

            CUANTOS := CUANTOS +1;
            IF MOD(CUANTOS,10) = 0
            THEN
                /* CADA 10, QUE HAGA UN COMMIT, **/
                COMMIT;
END IF;
END LOOP;

    /** SETEAMOS LA SECUENCIA AL MÁXIMO CÓDIGO. **/
SELECT MAX(UNAD_CODIGO) INTO maximoIdUAS FROM RS2_UNIADM;

EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_UNIADM_SEQ INCREMENT BY ' || maximoIdUAS;
SELECT RS2_UNIADM_SEQ.NEXTVAL INTO VALOR FROM DUAL;
EXECUTE IMMEDIATE 'ALTER SEQUENCE RS2_UNIADM_SEQ INCREMENT BY 1';

COMMIT;

/** ACTIVAMOS LA FK **/
EXECUTE IMMEDIATE 'ALTER TABLE RS2_UNIADM ENABLE CONSTRAINT RS2_UNIADM_UNIADM_FK';



dbms_lob.writeappend(l_clob, length('FIN MIGRACION UAS\n'), 'FIN MIGRACION UAS\n');
    /*DBMS_OUTPUT.PUT_LINE('FIN MIGRACION UAS *');*/
    dbms_lob.close(l_clob);
    resultado := l_clob;


END;