-- De momento, no hay datos iniciales
/* Entidad por defecto */
Insert into ROLSAC2.RS2_ENTIDA (ENTI_CODIGO,ENTI_IDENTI,ENTI_ACTIVA,ROLE_ROLADE,ENTI_ROLADC,ENTI_ROLGES,ENTI_ROLINF,ENTI_LOGO) values (RS2_ENTIDA_SEQ.NEXTVAL ,'GOIB','1','ROLADE','ROLADC','ROLGES','ROLINF',null);

Insert into ROLSAC2.RS2_TRAENT (TREN_CODIGO,TREN_CODENT,TREN_IDIOMA,TREN_DESCRI) values (RS2_TRAENT_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,'es','GOIB descenti es');
Insert into ROLSAC2.RS2_TRAENT (TREN_CODIGO,TREN_CODENT,TREN_IDIOMA,TREN_DESCRI) values (RS2_TRAENT_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,'ca','GOIB descenti ca');


/*Usuario por defecto*/
Insert into ROLSAC2.RS2_USER (USER_CODIGO,USER_CODENTI,USER_USER) values (RS2_USER_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,'rolsac2');

/*UA por defecto*/
Insert into ROLSAC2.RS2_UNIADM (UNAD_CODIGO,UNAD_CODENTI,UNAD_TIPOUA,UNAD_UNADPADRE,UNAD_DIR3,UNAD_IDENTI,UNAD_ABREVI,UNAD_TFNO,UNAD_FAX,UNAD_EMAIL,UNAD_DOMINI,UNAD_RSPNOM,UNAD_RSPSEX,UNAD_RSPEMA,UNAD_ORDEN) 
    values (RS2_UNIADM_SEQ.NEXTVAL,RS2_ENTIDA_SEQ.CURRVAL,null,null,'dir3',null,null,null,null,null,null,null,null,null,'1');
Insert into ROLSAC2.RS2_TRAUNAD (TRUA_CODIGO,TRUA_CODUNAD,TRUA_IDIOMA,TRUA_NOMBRE,TRUA_PRESEN,TRUA_URLWEB,TRUA_RSPCV) values (RS2_TRAUNAD_SEQ.NEXTVAL,RS2_UNIADM_SEQ.CURRVAL,'es','nombreua es',null,null, EMPTY_CLOB());
Insert into ROLSAC2.RS2_TRAUNAD (TRUA_CODIGO,TRUA_CODUNAD,TRUA_IDIOMA,TRUA_NOMBRE,TRUA_PRESEN,TRUA_URLWEB,TRUA_RSPCV) values (RS2_TRAUNAD_SEQ.NEXTVAL,RS2_UNIADM_SEQ.CURRVAL,'ca','nombreua ca',null,null, EMPTY_CLOB());

Insert into ROLSAC2.RS2_USERUA (UAUS_CODUSER,UAUS_CODUA) values (RS2_USER_SEQ.CURRVAL,RS2_UNIADM_SEQ.CURRVAL);

Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO, PLUG_CODENTI, PLUG_DESC, PLUG_CLASSNAME, PLUG_PROPS, PLUG_PREPRO, PLUG_TIPO)
VALUES (RS2_PLUGIN_SEQ.NEXTVAL, RS2_ENTIDA_SEQ.CURRVAL, 'Plugin de boletín', 'es.caib.rolsac2.commons.plugins.boletin.eboib.EboibPlugin',
        '[{"codigo":"eboibUrlHack","valor":"false","orden":null},{"codigo":"eboibUrl","valor":"https://www.caib.es/eboibfront/","orden":null}]',
        'pluginsib.boletin.eboib.', 'BOL');

Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO, PLUG_CODENTI, PLUG_DESC, PLUG_CLASSNAME, PLUG_PROPS, PLUG_PREPRO, PLUG_TIPO)
VALUES (RS2_PLUGIN_SEQ.NEXTVAL, RS2_ENTIDA_SEQ.CURRVAL, 'Plugin de traducción', 'es.caib.rolsac2.commons.plugins.traduccion.translatorib.TranslatorIBPlugin',
        '[{"codigo":"url","valor":"https://dev.caib.es/translatorib/api/services/traduccion/v1","orden":null},{"codigo":"usr","valor":"api-tib","orden":null},{"codigo":"pwd","valor":"XXXXXX","orden":null}]',
        'pluginsib.traduccion.translatorib.', 'TRA');