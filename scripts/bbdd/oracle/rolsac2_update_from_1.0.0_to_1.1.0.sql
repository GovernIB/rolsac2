/** ANYADIMOS LA OPCION DE LOGO EN LA ENTIDAD **/
ALTER TABLE RS2_ENTIDA ADD ENTI_LOGO2 NUMBER(10) NULL;
COMMENT ON COLUMN RS2_ENTIDA.ENTI_LOGO2 IS 'LOGO APLICACION';
ALTER TABLE "RS2_ENTIDA"
	ADD CONSTRAINT "RS2_ENTIDA_LOGO2_FK"
		FOREIGN KEY (ENTI_LOGO2) REFERENCES RS2_FICEXT(FIE_CODIGO);

/** ANYADIMOS A LA ENTIDAD EL CAMPO LOPD COMUN **/
ALTER TABLE RS2_TRAENT ADD TREN_LOPDCO VARCHAR2(4000 CHAR) NULL;
COMMENT ON COLUMN "RS2_TRAENT"."TREN_LOPDCO" IS 'LOPD COMUN';


DELETE FROM ROLSAC2.RS2_PLUGIN;
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de boletín','es.caib.rolsac2.commons.plugins.boletin.eboib.EboibPlugin','[{"codigo":"eboibUrl","valor":"https://www.caib.es/eboibfront/","orden":null}, {"codigo":"tipoBoletin","valor":"1","orden":null}]','BOL','pluginsib.boletin.eboib.');
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de traducción','es.caib.rolsac2.commons.plugins.traduccion.translatorib.TranslatorIBPlugin','[{"codigo":"url","valor":"${config.plg.traduccion.url}","orden":null},{"codigo":"usr","valor":"${config.plg.traduccion.usr}","orden":null},{"codigo":"pwd","valor":"${config.plg.traduccion.pwd}","orden":null}]','TRA','pluginsib.traduccion.translatorib.');
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de consulta al API REST DIR3CAIB','es.caib.rolsac2.commons.plugins.dir3.caib.Dir3CaibRestPlugin','[{"codigo":"url","valor":"${config.plg.dir3.url}","orden":null},{"codigo":"usr","valor":"${config.plg.dir3.usr}","orden":null},{"codigo":"pwd","valor":"${config.plg.dir3.pwd}","orden":null}]','DI3','es.caib.rolsac2.pluginsib.dir3.caib.');
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de indexacion sia','es.caib.rolsac2.commons.plugins.sia.sia.SiaWSPlugin','[{"codigo":"url","valor":"${config.plg.sia.url}","orden":null}]','SIA','pluginsib.indexacion.sia.');
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de envío de email','es.caib.rolsac2.commons.plugins.email.emailSmtp.EmailSmtpPlugin','[{"codigo":"jndi","valor":"${config.plg.email.jndi}","orden":null}]','EMA','pluginsib.email.smtp.');
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de indexacion','es.caib.rolsac2.commons.plugins.indexacion.solr.PluginIndexacionSolr',TO_CLOB(q'[[{"codigo":"urlSolr","valor":"${config.plg.indexacion.solrUrl}","orden":null},{"codigo":"usrSolr","valor":"${config.plg.indexacion.solrUsr}","orden":null},{"codigo":"indexSolr","valor":"${config.plg.indexacion.solrIdxl}","orden":null},{"codigo":"pwdSolr","valor":"${config.plg.indexacion.solrPwd}","orden":null},{"codigo":"activoSolr","valor":"true","orden":null},{"codigo":"urlElastic","valor":"${config.plg.indexacion.elasticUrl}","orden":null},{"codigo":"usrElastic","valor":"${config.plg.indexaci]')
	|| TO_CLOB(q'[on.elasticUsr}","orden":null},{"codigo":"pwdElastic","valor":"${config.plg.indexacion.elasticPwd}","orden":null},{"codigo":"activoElastic","valor":"false","orden":null}]]'),'IDX','pluginsib.index.solr.');
Insert into ROLSAC2.RS2_PLUGIN (PLUG_CODIGO,PLUG_CODENTI,PLUG_DESC,PLUG_CLASSNAME,PLUG_PROPS,PLUG_TIPO,PLUG_PREPRO) values (RS2_PLUGIN_SEQ.NEXTVAL,'1','Plugin de PDU','es.caib.rolsac2.commons.plugins.pdu.PDUPlugin','[{"codigo":"url","valor":"${config.plg.pdu.url}","orden":null},{"codigo":"usr","valor":"${config.plg.pdu.usr}","orden":null},{"codigo":"pwd","valor":"${config.plg.pdu.pwd}","orden":null}]','PDU','pluginsib.pdu.');
COMMIT;

