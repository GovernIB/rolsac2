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

ALTER TABLE RS2_PROC
	ADD CONSTRAINT RS2_PROC_CHECK_SIA
		CHECK (
			("PROC_SIAEST" IS NULL AND "PROC_SIACOD"  IS NULL)
				OR
			("PROC_SIAEST" IS NOT NULL AND "PROC_SIACOD"  IS NOT NULL)
			);



/** TAXAS **/


/** Ejecutar desde el usuario de ROLSAC **/
GRANT SELECT ON RSC_TAXA TO ROLSAC2;
GRANT SELECT ON RSC_TRATAX TO ROLSAC2;
GRANT SELECT ON RSC_TAXA TO WWW_ROLSAC2;
GRANT SELECT ON RSC_TRATAX TO WWW_ROLSAC2;


/** ejecutar desde el usuario ROLSAC2 **/

/** drop dolumn **/
ALTER TABLE RS2_PRCWF
DROP COLUMN PRWF_SVTASA;

CREATE SEQUENCE "RS2_PRCTAX_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE;
CREATE TABLE "RS2_PRCTAX"
(
	"PRTX_CODIGO"  NUMBER(10) NOT NULL,          -- CODIGO
	"PRTX_CODPRWF" NUMBER(10) NOT NULL          -- CODIGO PROC WF
) TABLESPACE ROLSAC2_DADES;
GRANT SELECT, INSERT, UPDATE, DELETE ON "RS2_PRCTAX" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_PRCTAX_SEQ" TO WWW_ROLSAC2;


CREATE SEQUENCE "RS2_TRAPRTX_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE;
CREATE TABLE "RS2_TRAPRTX"
(
	"TRTX_CODIGO"        NUMBER(10) NOT NULL,       -- CODIGO
	"TRTX_CODPRTX"      NUMBER(10) NOT NULL,       -- CODIGO TAXA
	"TRTX_IDIOMA"         VARCHAR2(2 CHAR) NOT NULL, -- IDIOMA
	"TRTX_IDENTI"          VARCHAR2(256 CHAR) NOT NULL,                 -- IDENTIFICADOR (O CODIGO EN LA PARTE VISUAL)
	"TRTX_DESCRI"         VARCHAR2(4000 CHAR) NULL,   -- DESCRIPCION
	"TRTX_FORPAG"       VARCHAR2(4000 CHAR) NULL,  -- FORMA DE PAGAMENT
	"TRTX_URL"              VARCHAR2(1000 CHAR) NULL  -- URL
) TABLESPACE ROLSAC2_DADES;

GRANT SELECT, INSERT, UPDATE, DELETE ON "RS2_TRAPRTX" TO WWW_ROLSAC2;
GRANT SELECT ON "RS2_TRAPRTX_SEQ" TO WWW_ROLSAC2;

CREATE OR REPLACE SYNONYM R1_PROCEDIMIENTOS_TAXAS FOR ROLSAC.RSC_TAXA;
CREATE OR REPLACE SYNONYM R1_PROCEDIMIENTOS_TAXAS_TRAD FOR ROLSAC.RSC_TRATAX;

/*** Ejecutar el usuario de WWW_ROLSAC2 **/
CREATE OR REPLACE SYNONYM RS2_PRCTAX FOR "ROLSAC2"."RS2_PRCTAX";
CREATE OR REPLACE SYNONYM RS2_TRAPRTX FOR "ROLSAC2"."RS2_TRAPRTX";
CREATE OR REPLACE SYNONYM RS2_PRCTAX_SEQ    FOR ROLSAC2.RS2_PRCTAX_SEQ;
CREATE OR REPLACE SYNONYM RS2_TRAPRTX_SEQ    FOR ROLSAC2.RS2_TRAPRTX_SEQ;
CREATE OR REPLACE SYNONYM R1_PROCEDIMIENTOS_TAXAS FOR ROLSAC.RSC_TAXA;
CREATE OR REPLACE SYNONYM R1_PROCEDIMIENTOS_TAXAS_TRAD FOR ROLSAC.RSC_TRATAX;

