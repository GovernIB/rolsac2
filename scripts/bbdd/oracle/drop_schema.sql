/**
 ** sobre ROLSAC2
  select drops from (select 'drop table "'|| table_name || '" cascade constraints;' AS drops from user_tables order by table_name)
  UNION
  select drops from (select 'drop sequence "' || sequence_name || '" ;' as drops from user_sequences order by sequence_name)
 ** Sobre WWW_ROLSAC2
  select 'drop synonym' || synonym_name || ' ;' from user_synonyms order by synonym_name
 **/

drop sequence "RS2_AFECTA_SEQ" ;
drop sequence "RS2_BOLETI_SEQ" ;
drop sequence "RS2_CNFGLO_SEQ" ;
drop sequence "RS2_DOCNORM_SEQ" ;
drop sequence "RS2_DOCPR_SEQ" ;
drop sequence "RS2_EDIFIC_SEQ" ;
drop sequence "RS2_EDIMED_SEQ" ;
drop sequence "RS2_ENTIDA_SEQ" ;
drop sequence "RS2_FCHAUDIT_SEQ" ;
drop sequence "RS2_FCHDOC_SEQ" ;
drop sequence "RS2_FCHENLA_SEQ" ;
drop sequence "RS2_FCHMED_SEQ" ;
drop sequence "RS2_FICEXT_SEQ" ;
drop sequence "RS2_FICHA_SEQ" ;
drop sequence "RS2_LSTDOC_SEQ" ;
drop sequence "RS2_NORMA_SEQ" ;
drop sequence "RS2_PERAUDIT_SEQ" ;
drop sequence "RS2_PERSON_SEQ" ;
drop sequence "RS2_PLATRE_SEQ" ;
drop sequence "RS2_PLUGIN_SEQ" ;
drop sequence "RS2_PRAUDIT_SEQ" ;
drop sequence "RS2_PRCNOR_SEQ" ;
drop sequence "RS2_PRCTRM_SEQ" ;
drop sequence "RS2_PRCWF_SEQ" ;
drop sequence "RS2_PROCES_SEQ" ;
drop sequence "RS2_PROCLOG_SEQ" ;
drop sequence "RS2_PROC_SEQ" ;
drop sequence "RS2_SECCION_SEQ" ;
drop sequence "RS2_TEMA_SEQ" ;
drop sequence "RS2_TIPOAFE_SEQ" ;
drop sequence "RS2_TIPOFOI_SEQ" ;
drop sequence "RS2_TIPOLEG_SEQ" ;
drop sequence "RS2_TIPOMEDEDI_SEQ" ;
drop sequence "RS2_TIPOMEDFCH_SEQ" ;
drop sequence "RS2_TIPOMEDUA_SEQ" ;
drop sequence "RS2_TIPOMSI_SEQ" ;
drop sequence "RS2_TIPONOR_SEQ" ;
drop sequence "RS2_TIPOPRO_SEQ" ;
drop sequence "RS2_TIPOPUB_SEQ" ;
drop sequence "RS2_TIPOSAD_SEQ" ;
drop sequence "RS2_TIPOSEX_SEQ" ;
drop sequence "RS2_TIPOSPU_SEQ" ;
drop sequence "RS2_TIPOUNA_SEQ" ;
drop sequence "RS2_TIPOVIA_SEQ" ;
drop sequence "RS2_TRADONO_SEQ" ;
drop sequence "RS2_TRADOPR_SEQ" ;
drop sequence "RS2_TRAEDIF_SEQ" ;
drop sequence "RS2_TRAENT_SEQ" ;
drop sequence "RS2_TRAFCDO_SEQ" ;
drop sequence "RS2_TRAFCEN_SEQ" ;
drop sequence "RS2_TRAFIC_SEQ" ;
drop sequence "RS2_TRANORM_SEQ" ;
drop sequence "RS2_TRAPRTA_SEQ" ;
drop sequence "RS2_TRAPRWF_SEQ" ;
drop sequence "RS2_TRAPTTR_SEQ" ;
drop sequence "RS2_TRASECC_SEQ" ;
drop sequence "RS2_TRATEMA_SEQ" ;
drop sequence "RS2_TRATPAN_SEQ" ;
drop sequence "RS2_TRATPFI_SEQ" ;
drop sequence "RS2_TRATPLE_SEQ" ;
drop sequence "RS2_TRATPME_SEQ" ;
drop sequence "RS2_TRATPMF_SEQ" ;
drop sequence "RS2_TRATPMS_SEQ" ;
drop sequence "RS2_TRATPMU_SEQ" ;
drop sequence "RS2_TRATPNO_SEQ" ;
drop sequence "RS2_TRATPPO_SEQ" ;
drop sequence "RS2_TRATPPR_SEQ" ;
drop sequence "RS2_TRATPSA_SEQ" ;
drop sequence "RS2_TRATPSP_SEQ" ;
drop sequence "RS2_TRATPSX_SEQ" ;
drop sequence "RS2_TRATPTRA_SEQ" ;
drop sequence "RS2_TRATPUA_SEQ" ;
drop sequence "RS2_TRATPVI_SEQ" ;
drop sequence "RS2_TRAUNAD_SEQ" ;
drop sequence "RS2_TRAUSER_SEQ" ;
drop sequence "RS2_TRMPRE_SEQ" ;
drop sequence "RS2_TRMSOL_SEQ" ;
drop sequence "RS2_UNAAUDIT_SEQ" ;
drop sequence "RS2_UNAEVO_SEQ" ;
drop sequence "RS2_UNAHIS_SEQ" ;
drop sequence "RS2_UNAMED_SEQ" ;
drop sequence "RS2_UNASEC_SEQ" ;
drop sequence "RS2_UNIADM_SEQ" ;
drop sequence "RS2_USER_SEQ" ;
drop table "RS2_AFECTA" cascade constraints;
drop table "RS2_BOLETI" cascade constraints;
drop table "RS2_CNFGLO" cascade constraints;
drop table "RS2_DOCNORM" cascade constraints;
drop table "RS2_DOCPR" cascade constraints;
drop table "RS2_EDIFIC" cascade constraints;
drop table "RS2_EDIMED" cascade constraints;
drop table "RS2_ENTIDA" cascade constraints;
drop table "RS2_FCHAUDIT" cascade constraints;
drop table "RS2_FCHDOC" cascade constraints;
drop table "RS2_FCHENLA" cascade constraints;
drop table "RS2_FCHMED" cascade constraints;
drop table "RS2_FCHPOB" cascade constraints;
drop table "RS2_FCHTEM" cascade constraints;
drop table "RS2_FICEXT" cascade constraints;
drop table "RS2_FICHA" cascade constraints;
drop table "RS2_LSTDOC" cascade constraints;
drop table "RS2_NORMA" cascade constraints;
drop table "RS2_PERAUDIT" cascade constraints;
drop table "RS2_PERSON" cascade constraints;
drop table "RS2_PLATRE" cascade constraints;
drop table "RS2_PLUGIN" cascade constraints;
drop table "RS2_PRAUDIT" cascade constraints;
drop table "RS2_PRCMAS" cascade constraints;
drop table "RS2_PRCNOR" cascade constraints;
drop table "RS2_PRCPUB" cascade constraints;
drop table "RS2_PRCSPU" cascade constraints;
drop table "RS2_PRCTEM" cascade constraints;
drop table "RS2_PRCTRM" cascade constraints;
drop table "RS2_PRCWF" cascade constraints;
drop table "RS2_PROC" cascade constraints;
drop table "RS2_PROCES" cascade constraints;
drop table "RS2_PROCEX" cascade constraints;
drop table "RS2_PROCLOG" cascade constraints;
drop table "RS2_SECCION" cascade constraints;
drop table "RS2_SESION" cascade constraints;
drop table "RS2_TEMA" cascade constraints;
drop table "RS2_TIPOAFE" cascade constraints;
drop table "RS2_TIPOFOI" cascade constraints;
drop table "RS2_TIPOLEG" cascade constraints;
drop table "RS2_TIPOMEDEDI" cascade constraints;
drop table "RS2_TIPOMEDFCH" cascade constraints;
drop table "RS2_TIPOMEDUA" cascade constraints;
drop table "RS2_TIPOMSI" cascade constraints;
drop table "RS2_TIPONOR" cascade constraints;
drop table "RS2_TIPOPRO" cascade constraints;
drop table "RS2_TIPOPUB" cascade constraints;
drop table "RS2_TIPOSAD" cascade constraints;
drop table "RS2_TIPOSEX" cascade constraints;
drop table "RS2_TIPOSPU" cascade constraints;
drop table "RS2_TIPOUNA" cascade constraints;
drop table "RS2_TIPOVIA" cascade constraints;
drop table "RS2_TRADONO" cascade constraints;
drop table "RS2_TRADOPR" cascade constraints;
drop table "RS2_TRAEDIF" cascade constraints;
drop table "RS2_TRAENT" cascade constraints;
drop table "RS2_TRAFCDO" cascade constraints;
drop table "RS2_TRAFCEN" cascade constraints;
drop table "RS2_TRAFIC" cascade constraints;
drop table "RS2_TRANORM" cascade constraints;
drop table "RS2_TRAPLATRE" cascade constraints;
drop table "RS2_TRAPRTA" cascade constraints;
drop table "RS2_TRAPRWF" cascade constraints;
drop table "RS2_TRASECC" cascade constraints;
drop table "RS2_TRATEMA" cascade constraints;
drop table "RS2_TRATPAN" cascade constraints;
drop table "RS2_TRATPFI" cascade constraints;
drop table "RS2_TRATPLE" cascade constraints;
drop table "RS2_TRATPME" cascade constraints;
drop table "RS2_TRATPMF" cascade constraints;
drop table "RS2_TRATPMS" cascade constraints;
drop table "RS2_TRATPMU" cascade constraints;
drop table "RS2_TRATPNO" cascade constraints;
drop table "RS2_TRATPPO" cascade constraints;
drop table "RS2_TRATPPR" cascade constraints;
drop table "RS2_TRATPSA" cascade constraints;
drop table "RS2_TRATPSP" cascade constraints;
drop table "RS2_TRATPSX" cascade constraints;
drop table "RS2_TRATPTRA" cascade constraints;
drop table "RS2_TRATPUA" cascade constraints;
drop table "RS2_TRATPVI" cascade constraints;
drop table "RS2_TRAUNAD" cascade constraints;
drop table "RS2_TRAUSER" cascade constraints;
drop table "RS2_TRMPRE" cascade constraints;
drop table "RS2_TRMSOL" cascade constraints;
drop table "RS2_UADNOR" cascade constraints;
drop table "RS2_UATEMA" cascade constraints;
drop table "RS2_UNAAUDIT" cascade constraints;
drop table "RS2_UNAEDI" cascade constraints;
drop table "RS2_UNAEVO" cascade constraints;
drop table "RS2_UNAHIS" cascade constraints;
drop table "RS2_UNAMED" cascade constraints;
drop table "RS2_UNASEC" cascade constraints;
drop table "RS2_UNIADM" cascade constraints;
drop table "RS2_USENTI" cascade constraints;
drop table "RS2_USER" cascade constraints;
drop table "RS2_USERUA" cascade constraints;