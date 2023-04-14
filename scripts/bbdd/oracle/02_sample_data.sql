-- De momento, no hay datos iniciales
/* Entidad por defecto */
Insert into ROLSAC2.RS2_ENTIDA (ENTI_CODIGO,ENTI_IDENTI,ENTI_ACTIVA,ENTI_ROLADE,ENTI_ROLADC,ENTI_ROLGES,ENTI_ROLINF,ENTI_LOGO, ENTI_IDIPER, ENTI_IDIDEF, ENTI_IDIOBL) values (RS2_ENTIDA_SEQ.NEXTVAL ,'GOIB','1','ROLADE','ROLADC','ROLGES','ROLINF',null, 'es;ca;', 'ca', 'es;ca;');

Insert into ROLSAC2.RS2_TRAENT (TREN_CODIGO,TREN_CODENT,TREN_IDIOMA,TREN_DESCRI, TREN_LOPDFI, TREN_LOPDDS, TREN_LOPDDR, TREN_UACOMU) values (RS2_TRAENT_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,'es','GOIB descenti es','Tramitació del procediment','El destinatari de la lopd','El dret de la lopd','Responsable lopd común');
Insert into ROLSAC2.RS2_TRAENT (TREN_CODIGO,TREN_CODENT,TREN_IDIOMA,TREN_DESCRI, TREN_LOPDFI, TREN_LOPDDS, TREN_LOPDDR, TREN_UACOMU) values (RS2_TRAENT_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,'ca','GOIB descenti ca','Tramitación del procedimiento','El destinatario de la lopd','El derecho de la lopd','Responsable lopd comú');


/*Usuario por defecto*/
Insert into ROLSAC2.RS2_USER (USER_CODIGO,USER_USER, USER_NOMBRE, USER_EMAIL) values (RS2_USER_SEQ.NEXTVAL,'rolsac2', 'nom usuari', 'rolsac2@info.com');
Insert into ROLSAC2.RS2_TRAUSER (TRUS_CODIGO, TRUS_CODUS, TRUS_IDIOMA, TRUS_OBSER) values (RS2_TRAUSER_SEQ.NEXTVAL, RS2_USER_SEQ.CURRVAL, 'es', 'Observaciones ES');
Insert into ROLSAC2.RS2_TRAUSER (TRUS_CODIGO, TRUS_CODUS, TRUS_IDIOMA, TRUS_OBSER) values (RS2_TRAUSER_SEQ.NEXTVAL, RS2_USER_SEQ.CURRVAL, 'ca', 'Observaciones CA');

/*UA por defecto*/
Insert into ROLSAC2.RS2_UNIADM (UNAD_CODIGO,UNAD_CODENTI,UNAD_TIPOUA,UNAD_UNADPADRE,UNAD_DIR3,UNAD_IDENTI,UNAD_ABREVI,UNAD_TFNO,UNAD_FAX,UNAD_EMAIL,UNAD_DOMINI,UNAD_RSPNOM,UNAD_RSPSEX,UNAD_RSPEMA,UNAD_ORDEN,UNAD_VERSION)
values (RS2_UNIADM_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,null,null,'A04003003',null,null,null,null,null,null,null,null,null,'1','1');
Insert into ROLSAC2.RS2_TRAUNAD (TRUA_CODIGO,TRUA_CODUNAD,TRUA_IDIOMA,TRUA_NOMBRE,TRUA_PRESEN,TRUA_URLWEB,TRUA_RSPCV) values (RS2_TRAUNAD_SEQ.NEXTVAL,RS2_UNIADM_SEQ.CURRVAL,'es','Gobierno de las Islas Baleares',null,null, EMPTY_CLOB());
Insert into ROLSAC2.RS2_TRAUNAD (TRUA_CODIGO,TRUA_CODUNAD,TRUA_IDIOMA,TRUA_NOMBRE,TRUA_PRESEN,TRUA_URLWEB,TRUA_RSPCV) values (RS2_TRAUNAD_SEQ.NEXTVAL,RS2_UNIADM_SEQ.CURRVAL,'ca','Govern de les Illes Balears',null,null, EMPTY_CLOB());

Insert into ROLSAC2.RS2_USERUA (UAUS_CODUSER,UAUS_CODUA) values (RS2_USER_SEQ.CURRVAL,RS2_UNIADM_SEQ.CURRVAL);
Insert into ROLSAC2.RS2_USENTI (USEN_CODUSER,USEN_CODENTI) values (RS2_USER_SEQ.CURRVAL,RS2_ENTIDA_SEQ.CURRVAL);

/* Datos para inicio de procesos automáticos*/
insert into ROLSAC2.RS2_PROCES (PROCES_CODIGO, PROCES_CODENTI, PROCES_IDENTI, PROCES_DESCRI, proces_cron, proces_activo, proces_params) VALUES (RS2_PROCES_SEQ.NEXTVAL, RS2_ENTIDA_SEQ.CURRVAL, 'TEST', 'Proceso de prueba', null, 1, '[{"codigo":"valida","valor":"true"}]');
insert into ROLSAC2.RS2_PROCEX (procex_codigo, procex_instan, procex_fecha) VALUES ('MAESTRO', 'XXXX', to_date('1998/05/31:12:00:00AM', 'yyyy/mm/dd:hh:mi:ssam'));

Insert into ROLSAC2.RS2_BOLETI (BOLE_CODIGO,BOLE_IDENTI,BOLE_NOMBRE,BOLE_URL) values (1,'BOIB','Boletín Oficial de las Islas Baleares','https://www.caib.es/eboibfront/es');

Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO, PLUG_CODENTI, PLUG_DESC, PLUG_CLASSNAME, PLUG_PROPS, PLUG_PREPRO, PLUG_TIPO)
VALUES (RS2_PLUGIN_SEQ.NEXTVAL, RS2_ENTIDA_SEQ.CURRVAL, 'Plugin de boletín', 'es.caib.rolsac2.commons.plugins.boletin.eboib.EboibPlugin',
        '[{"codigo":"eboibUrl","valor":"https://www.caib.es/eboibfront/","orden":null}, {"codigo":"tipoBoletin","valor":"1","orden":null}]',
        'pluginsib.boletin.eboib.', 'BOL');

Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO, PLUG_CODENTI, PLUG_DESC, PLUG_CLASSNAME, PLUG_PROPS, PLUG_PREPRO, PLUG_TIPO)
VALUES (RS2_PLUGIN_SEQ.NEXTVAL, RS2_ENTIDA_SEQ.CURRVAL, 'Plugin de traducción', 'es.caib.rolsac2.commons.plugins.traduccion.translatorib.TranslatorIBPlugin',
        '[{"codigo":"url","valor":"https://dev.caib.es/translatorib/api/services/traduccion/v1","orden":null},{"codigo":"usr","valor":"api-tib","orden":null},{"codigo":"pwd","valor":"XXXXXX","orden":null}]',
        'pluginsib.traduccion.translatorib.', 'TRA');

Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO, PLUG_CODENTI, PLUG_DESC, PLUG_CLASSNAME, PLUG_PROPS, PLUG_PREPRO, PLUG_TIPO)
VALUES (RS2_PLUGIN_SEQ.NEXTVAL, RS2_ENTIDA_SEQ.CURRVAL, 'Plugin de consulta al API REST DIR3CAIB', 'es.caib.rolsac2.commons.plugins.dir3.caib.Dir3CaibRestPlugin',
        '[{"codigo":"url","valor":"https://dev.caib.es/dir3caib/rest","orden":null},{"codigo":"usr","valor":"$sistra_dir3caib","orden":null},{"codigo":"pwd","valor":"sistra_dir3caib","orden":null}]',
        'es.caib.rolsac2.pluginsib.dir3.caib.', 'DIR3');