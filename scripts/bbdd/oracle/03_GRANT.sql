/*
 ** SOBRE ROLSAC2
  select grants from (select 'GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "' || table_name || '" TO www_rolsac2; ' as grants from user_tables order by table_name)
  union
  select grants from (select 'GRANT SELECT ON "' || sequence_name || '" TO WWW_ROLSAC2;' as grants from user_sequences order by sequence_name);
 */
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_AFECTA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_BOLETI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_CNFGLO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_DOCNORM" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_DOCPR" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_EDIFIC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_EDIMED" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_ENTIDA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FCHAUDIT" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FCHDOC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FCHENLA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FCHMED" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FCHPOB" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FCHTEM" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FICEXT" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_FICHA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_LSTDOC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_NORMA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PERAUDIT" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PERSON" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PLATRE" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PLUGIN" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRAUDIT" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCMAS" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCNOR" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCPUB" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCSPU" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCTEM" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCTRM" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PRCWF" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PROC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PROCES" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PROCEX" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_PROCLOG" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_SECCION" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_SESION" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TEMA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOAFE" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOFOI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOLEG" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOMEDEDI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOMEDFCH" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOMEDUA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOMSI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPONOR" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOPRO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOPUB" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOSAD" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOSEX" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOSPU" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOUNA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TIPOVIA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRADONO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRADOPR" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAEDIF" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAENT" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAFCDO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAFCEN" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAFIC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRANORM" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAPLATRE" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAPRTA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAPRWF" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRASECC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATEMA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPAN" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPFI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPLE" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPME" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPMF" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPMS" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPMU" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPNO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPPO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPPR" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPSA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPSP" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPSX" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPTRA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPUA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRATPVI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAUNAD" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRAUSER" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRMPRE" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_TRMSOL" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UADNOR" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UATEMA" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNAAUDIT" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNAEDI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNAEVO" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNAHIS" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNAMED" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNASEC" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_UNIADM" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_USENTI" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_USER" TO www_rolsac2;
GRANT ALTER, DELETE, INDEX, INSERT, SELECT, UPDATE, REFERENCES, ON COMMIT REFRESH, QUERY REWRITE, DEBUG, FLASHBACK ON "RS2_USERUA" TO www_rolsac2;
GRANT SELECT ON "RS2_AFECTA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_BOLETI_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_CNFGLO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_DOCNORM_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_DOCPR_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_EDIFIC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_EDIMED_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_ENTIDA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_FCHAUDIT_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_FCHDOC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_FCHENLA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_FCHMED_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_FICEXT_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_FICHA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_LSTDOC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_NORMA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PERAUDIT_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PERSON_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PLATRE_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PLUGIN_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PRAUDIT_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PRCNOR_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PRCTRM_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PRCWF_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PROCES_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PROCLOG_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PROC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_SECCION_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TEMA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOAFE_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOFOI_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOLEG_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOMEDEDI_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOMEDFCH_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOMEDUA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOMSI_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPONOR_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOPRO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOPUB_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOSAD_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOSEX_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOSPU_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOUNA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TIPOVIA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRADONO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRADOPR_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAEDIF_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAENT_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAFCDO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAFCEN_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAFIC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRANORM_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAPRTA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAPRWF_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAPTTR_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRASECC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATEMA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPAN_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPFI_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPLE_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPME_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPMF_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPMS_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPMU_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPNO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPPO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPPR_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPSA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPSP_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPSX_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPTRA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPUA_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRATPVI_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAUNAD_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAUSER_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRMPRE_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRMSOL_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_UNAAUDIT_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_UNAEVO_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_UNAHIS_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_UNAMED_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_UNASEC_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_UNIADM_SEQ" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_USER_SEQ" TO WWW_ROLSAC2;