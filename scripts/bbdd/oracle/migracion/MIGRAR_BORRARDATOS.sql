create or replace PROCEDURE "MIGRAR_BORRARDATOS" 
(codigoUA NUMBER, codigoEntidad NUMBER, resultado OUT CLOB) AS 
    CURSOR cProcedimientos 
    IS
        /** CURSOR QUE OBTIENE EL CODIGO DE LOS JPROC QUE TIENEN UN WF QUE TIENE UNA UA DE LA ENTIDAD QUE VAMOS A BORRAR **/
        SELECT PROC_CODIGO
         FROM RS2_PROC
         WHERE PROC_CODIGO 
         IN ( SELECT PRWF_CODPROC 
                FROM RS2_PRCWF
               WHERE PRWF_CODUAR IN (SELECT UNAD_CODIGO FROM RS2_UNIADM WHERE UNAD_CODENTI = codigoEntidad)
            ) 
        ;

    CURSOR cProcedimientosWF(COD_PROC NUMBER) 
    IS
        /** OBTIENE INFO DEL WF: SU CODIGO, CODIGO DE LA LISTAS DE DOCUMENTOS, EL TIPO TRAMITACION  **/
        SELECT PRWF_CODIGO, PRWF_LSTDOC, PRWF_LSLOPD, PRWF_SVTREL
          FROM RS2_PRCWF
         WHERE PRWF_CODPROC = COD_PROC;

    CURSOR cTramitesWF(COD_WF NUMBER) 
    IS
        /** OBTIENE INFO DEL TRAMITE: SU CODIGO, CODIGO DE LA LISTAS DE DOCUMENTOS, EL TIPO TRAMITACION  **/
        SELECT PRTA_CODIGO, PRTA_LSTDOC, LSDO_CODIGO, PRTA_TRMTRM
          FROM RS2_PRCTRM
         WHERE PRTA_CODPRWF = COD_WF;
    CURSOR cNormativas 
    IS
        SELECT NORM_CODIGO 
          FROM RS2_NORMA
         WHERE NORM_CODENTI= codigoEntidad;
    CURSOR cUnidadAdministrativa 
    IS
        SELECT UNAD_CODIGO 
          FROM RS2_UNIADM
         WHERE UNAD_CODENTI= codigoEntidad;
    l_clob      CLOB := EMPTY_CLOB;
BEGIN
  dbms_lob.createtemporary(l_clob, TRUE);
  dbms_lob.open(l_clob, dbms_lob.lob_readwrite);
  dbms_lob.writeappend(l_clob, length('INICIO BORRAR DATOS\n'), 'INICIO BORRAR DATOS\n');

  /** PASO 1. BORRAR PROCEDIMIENTOS Y RELACIONES **/
  FOR PROCEDIMIENTO IN cProcedimientos
  LOOP

          /** Recorremos los wf asociados al procedimiento **/
          FOR PROCEDIMIENTO_WF IN cProcedimientosWF ( PROCEDIMIENTO.PROC_CODIGO) 
          LOOP
                /** Recorremos los tramites **/
                FOR TRAMITE IN cTramitesWF ( PROCEDIMIENTO_WF.PRWF_CODIGO) 
                LOOP
                    /** Borramos traducciones **/
                    DELETE FROM RS2_TRAPRTA WHERE TRTA_CODPRTA = TRAMITE.PRTA_CODIGO;

                    /** Borramos tipo tramitacion **/
                    IF TRAMITE.PRTA_TRMTRM IS NOT NULL 
                    THEN
                        DELETE FROM RS2_TRATPTRA WHERE TRTT_CODTPTRA = TRAMITE.PRTA_TRMTRM;
                        DELETE FROM RS2_TRMPRE WHERE PRES_CODIGO = TRAMITE.PRTA_TRMTRM;
                    END IF;

                    /** Borramos documentos de lista documentos **/
                    IF TRAMITE.PRTA_LSTDOC IS NOT NULL 
                    THEN
                        DELETE FROM RS2_TRADOPR WHERE TRDP_CODDOPR IN (SELECT DOCPR_CODLSD FROM RS2_DOCPR WHERE DOCPR_CODLSD = TRAMITE.PRTA_LSTDOC);
                        DELETE FROM RS2_DOCPR WHERE DOCPR_CODLSD = TRAMITE.PRTA_LSTDOC;
                    END IF;

                    /** Borramos documentos de lista documentos **/
                    IF TRAMITE.LSDO_CODIGO IS NOT NULL 
                    THEN
                        DELETE FROM RS2_TRADOPR WHERE TRDP_CODDOPR IN (SELECT DOCPR_CODLSD FROM RS2_DOCPR WHERE DOCPR_CODLSD = TRAMITE.LSDO_CODIGO);
                        DELETE FROM RS2_DOCPR WHERE DOCPR_CODLSD = TRAMITE.LSDO_CODIGO;
                    END IF;

                    /** Borramos el tramite **/
                    DELETE FROM RS2_PRCTRM WHERE PRTA_CODIGO = TRAMITE.PRTA_CODIGO;

                     /** Borramos la lista documentos **/
                    IF TRAMITE.PRTA_LSTDOC IS NOT NULL 
                    THEN
                        DELETE FROM RS2_LSTDOC WHERE LSDO_CODIGO = TRAMITE.PRTA_LSTDOC;
                    END IF;

                    /** Borramos la lista documentos **/
                    IF TRAMITE.LSDO_CODIGO IS NOT NULL 
                    THEN
                        DELETE FROM RS2_LSTDOC WHERE LSDO_CODIGO = TRAMITE.LSDO_CODIGO;
                    END IF;    
                END LOOP;

                 /** Borramos traducciones **/
                DELETE FROM RS2_TRAPRWF WHERE TRPW_CODPRWF = PROCEDIMIENTO_WF.PRWF_CODIGO;

                /** Borramos las relaciones con normativas **/
                DELETE FROM RS2_PRCNOR WHERE PRWF_CODIGO = PROCEDIMIENTO_WF.PRWF_CODIGO;

                /** Borramos tipo tramitacion **/
                IF PROCEDIMIENTO_WF.PRWF_SVTREL IS NOT NULL 
                THEN
                    DELETE FROM RS2_TRATPTRA WHERE TRTT_CODTPTRA = PROCEDIMIENTO_WF.PRWF_SVTREL;
                    DELETE FROM RS2_TRMPRE WHERE PRES_CODIGO = PROCEDIMIENTO_WF.PRWF_SVTREL;
                END IF;

                /** Borramos documentos de lista documentos **/
                IF PROCEDIMIENTO_WF.PRWF_LSTDOC IS NOT NULL 
                THEN
                    DELETE FROM RS2_TRADOPR WHERE TRDP_CODDOPR IN (SELECT DOCPR_CODLSD FROM RS2_DOCPR WHERE DOCPR_CODLSD = PROCEDIMIENTO_WF.PRWF_LSTDOC);
                    DELETE FROM RS2_DOCPR WHERE DOCPR_CODLSD = PROCEDIMIENTO_WF.PRWF_LSTDOC;
                END IF;

                /** Borramos documentos de lista documentos **/
                IF PROCEDIMIENTO_WF.PRWF_LSLOPD IS NOT NULL 
                THEN
                    DELETE FROM RS2_TRADOPR WHERE TRDP_CODDOPR IN (SELECT DOCPR_CODLSD FROM RS2_DOCPR WHERE DOCPR_CODLSD = PROCEDIMIENTO_WF.PRWF_LSLOPD);
                    DELETE FROM RS2_DOCPR WHERE DOCPR_CODLSD = PROCEDIMIENTO_WF.PRWF_LSLOPD;
                END IF;

                 /** Borramos las relaciones (materias, pubobj y normativas) **/
                 DELETE FROM RS2_PRCMAS WHERE PRMS_CODPRWF = PROCEDIMIENTO_WF.PRWF_CODIGO;
                 DELETE FROM RS2_PRCPUB WHERE PRPO_CODPRWF = PROCEDIMIENTO_WF.PRWF_CODIGO;
                 DELETE FROM RS2_PRCNOR WHERE PRWF_CODIGO = PROCEDIMIENTO_WF.PRWF_CODIGO;
                 DELETE FROM RS2_PRCTEM WHERE PRTM_CODPRWF = PROCEDIMIENTO_WF.PRWF_CODIGO;

                 /** Borramos el procedimiento wf **/
                 DELETE FROM RS2_PRCWF WHERE PRWF_CODIGO = PROCEDIMIENTO_WF.PRWF_CODIGO;

                 /** Borramos la lista documentos **/
                IF PROCEDIMIENTO_WF.PRWF_LSTDOC IS NOT NULL 
                THEN
                    DELETE FROM RS2_LSTDOC WHERE LSDO_CODIGO = PROCEDIMIENTO_WF.PRWF_LSTDOC;
                END IF;

                /** Borramos la lista documentos **/
                IF PROCEDIMIENTO_WF.PRWF_LSLOPD IS NOT NULL 
                THEN
                    DELETE FROM RS2_LSTDOC WHERE LSDO_CODIGO = PROCEDIMIENTO_WF.PRWF_LSLOPD;
                END IF;  
          END LOOP;

          /** Borramos auditorias **/
          DELETE FROM RS2_PRAUDIT WHERE PRAU_CODPROC = PROCEDIMIENTO.PROC_CODIGO;

          /** Borramos el procedimiento **/
          DELETE FROM RS2_PROC WHERE PROC_CODIGO = PROCEDIMIENTO.PROC_CODIGO;
  END LOOP;

  /** PASO 2. BORRAR NORMATIVAS. **/
  FOR NORMATIVA IN cNormativas
  LOOP
        /** Borramos traducciones, afectaciones, rel con ua y normativas. **/
        DELETE FROM RS2_TRANORM WHERE TRNO_CODTPNO = NORMATIVA.NORM_CODIGO;
        DELETE FROM RS2_UADNOR WHERE UANO_CODNORM = NORMATIVA.NORM_CODIGO;
        DELETE FROM RS2_AFECTA WHERE AFNO_NORORG = NORMATIVA.NORM_CODIGO OR AFNO_NORAFE = NORMATIVA.NORM_CODIGO;
        DELETE FROM RS2_NORMA WHERE NORM_CODIGO = NORMATIVA.NORM_CODIGO;
        
  END LOOP;

  /** PASO 3. BORRAR UAS. **/

  /** DESACTIVAMOS LA FK DE UA PADRE, SINO HABRÍA QUE REALIZAR UN ORDENADO COMPLEJO**/
  EXECUTE IMMEDIATE 'ALTER TABLE RS2_UNIADM DISABLE CONSTRAINT RS2_UNIADM_UNIADM_FK';

  FOR UA IN cUnidadAdministrativa
  LOOP
        /** Borramos la relacion de UA con Usuario **/
        DELETE FROM RS2_USERUA WHERE UAUS_CODUA =  UA.UNAD_CODIGO;

        /** Borramos las auditorias **/
        DELETE FROM RS2_UNAAUDIT WHERE UAAU_CODUA =  UA.UNAD_CODIGO;

        /** Borramos traducciones y ua **/
        DELETE FROM RS2_TRAUNAD WHERE TRUA_CODUNAD = UA.UNAD_CODIGO;
        DELETE FROM RS2_UNIADM WHERE UNAD_CODIGO = UA.UNAD_CODIGO;
  END LOOP;

  /** BORRAMOS LA DOCUMENTACION DE PROCEDIMIENTO, DOCUMENTACION DE NORMATIVAS Y FICHEROS PARA ASEGURARNOS**/
  DELETE FROM RS2_TRADOPR;
  DELETE FROM RS2_DOCPR;
  DELETE FROM RS2_TRADONO;
  DELETE FROM RS2_DOCNORM;
  DELETE FROM RS2_FICEXT;

  /** ACTIVAMOS LA FK **/
  EXECUTE IMMEDIATE 'ALTER TABLE RS2_UNIADM ENABLE CONSTRAINT RS2_UNIADM_UNIADM_FK';


  dbms_lob.writeappend(l_clob, length('FIN BORRAR DATOS\n'), 'FIN BORRAR DATOS\n');
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

END MIGRAR_BORRARDATOS;