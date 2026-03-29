create or replace PROCEDURE "MIGRAR_PROC" (codigo        NUMBER,
                                           codigoentidad NUMBER,
                                           resultado     OUT CLOB)
AS
	/* SET SERVEROUTPUT ON; */
/** GRANT: GRANT SELECT ON R1_PROCEDIMIENTOS TO ROLSAC2;
           GRANT SELECT ON R1_PROCEDIMIENTOS_TRAD TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_SERVIC TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_TRASER TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_TRATRA TO ROLSAC2;

           GRANT SELECT ON R1_PROCEDIMIENTOS_NORM TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_SERNOR TO ROLSAC2;
           GRANT SELECT ON R1_PROCEDIMIENTOS_POBJ TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_POBSER TO ROLSAC2;
           GRANT SELECT ON R1_PROCEDIMIENTOS_MATE TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_SERMAT TO ROLSAC2;
           GRANT SELECT ON ROLSAC.RSC_MATERI TO ROLSAC2;
**/
	/** RSC_PROCED (* INDICA QUE EL CAMPO NO SE MIGRA):
			PRO_CODI    ---> CODIGO PROCEDIMIENTO [PRO_CODIGO]
		   *PRO_TYPE    ---> EL TIPO (SI ES REMOTO) [PROC_TIPO]
		   *PRO_SIGNAT  ---> SIGNATURE
			PRO_FECCAD  ---> FECHA CADUCIDAD [PRWF_FECCAD]
			PRO_FECPUB  ---> FECHA PUBLICICACION [PRWF_FECPUB]
			PRO_FECACT  ---> FECHA ACTUALIZACION [PROC_FECACT]
			PRO_VALIDA  ---> VALIDA [PRWF_WF - PRWF_WFESTADO - PRWF_INTERNO]
			PRO_CODUNA  ---> CODIGO UNIDAD ADMINISTRATIVA [PRWF_CODUAR]
			PRO_CODFAM  ---> FAMILIA
			PRO_TRAMIT  ---> TRAMITE
			PRO_VERSIO  ---> VERSION
		   *PRR_CODADM  ---> CODIGO ADMINISTRACION REMOTA
		   *PRR_IDEXTE  ---> CODIGO ID PARA ADMINISTRACION REMOTA
		   *PRR_URLREM  ---> URL REMOTA EN ADMINISTRACION REMOTA
			PRO_URLEXT  ---> URL
			PRO_ORDCON  ---> ORDEN
		   *PRO_ORDDIR  ---> ORDEN2
		   *PRO_ORDSER  ---> ORDEN3
			PRO_CODINI  ---> FORMA DE INICIO [PRWF_PRTIPINIC]
			PRO_INDICA  ---> TIPO VIA ADMINISTRATIVA [PRWF_TIPVIA]
			PRO_VENTANA ---> VENTANILLA UNICA
			PRO_INFO    ---> DIRECCION ELECTRONICA [PRWF_RSEMA]
			PRO_TAXA    ---> TAXA
			PRO_CODUNA_RESOL --> ORGANO RESOLUTORIO [PRWF_CODUAI]
			PRO_RESPON  ---> RESPONSABLE [PRWF_RSNOM]
			PRO_CODUNA_SERV --> ORGANO SERVICIO [PRWF_PRCODUAC]
			PRO_CODSIA  ---> CODIGO SIA [PROC_SIACOD]
			PRO_CODSIL  ---> SILENCIO ADMINISTRATIVO [PRWF_PRTIPSIAD]
			PRO_ESTSIA  ---> ESTADO SIA  [PROC_SIAEST]
			PRO_FECSIA  ---> FECHA SIA  [PROC_SIAFC]
			PRO_COMUN   ---> COMUN    [PRWF_COMUN]
			PRO_CODLEG  ---> TIPO LEGITIMACION   [PRWF_DPTIPLEGI]
		   *PRO_PDTVAL  ---> PENDIENTE VALIDACION
			PRO_FUNHAB  ---> HABILITADO PARA FUNCIONARIO [PRWF_HABFUN]
			PRO_APOHAB  ---> HABILITADO PARA APODERADO [PRWF_HABAPO]
		(TRADUCCIONES) RSC_TRAPRO:
			TPR_CODPRO  ---> CODIGO PROCEDIMIENTO [TRPW_CODIGO]
			TPR_CODIDI  ---> IDIOMA     [TRPW_IDIOMA]
			TPR_NOMBRE  ---> NOMBRE [TRPW_NOMBRE]
			TPR_RESUME  ---> RESUMEN [TRPW_OBJETO]
			TPR_DESTIN  ---> DESTINATARIO  [TRPW_DESTIN]
			TPR_REQUIS  ---> REQUISITOS [TRPW_SVREQ]
			TPR_PLAZOS  ---> PLAZOS
		   *TPR_SILEN   ---> SILENCIO
			TPR_RECURS  ---> RECURSO
			TPR_OBSERV  ---> OBSERVACION [TRPW_OBSER]
			TPR_LUGAR   ---> LUGAR
			TPR_RESOLUCION  ---> RESOLUCION [TRPW_PRRESO]
			TPR_NOTIFICACION  ---> NOTIFICACION
			TPR_RESULT  ---> RESULTAT
		   *TPR_LOPDFI  ---> LOPD FINALIDAD   [FINALIDAD]
		   *TPR_LOPDDS  ---> LOPD DESTINATARIO [DESTINATARIO]
		   *TPR_LOPDDR  ---> LOPD DERECHO
		   *TPR_LOPDIA  ---> LOPD INFO ADICIONAL
		RSC_SERVIC (* INDICA QUE EL CAMPO NO SE MIGRA):
			SER_CODI   ---> CODIGO SERVICIO [PRO_CODIGO]
			SER_VALIDA ---> VALIDA [PRWF_WF - PRWF_WFESTADO - PRWF_INTERNO]
			SER_CODIGO
			SER_CODSIA ---> CODIGO SIA [PROC_SIACOD]
			SER_ESTSIA ---> ESTADO SIA  [PROC_SIAEST]
			SER_FECSIA ---> FECHA SIA  [PROC_SIAFC]
			SER_TASURL ---> TASA [PRWF_SVTASA]
			SER_NOMRSP ---> RESPONSABLE [PRWF_RSNOM]
			SER_CORREO ---> RESPONSABLE CORREO [PRWF_RSEMA]
			SER_TELEFO ---> RESPONSABLE TFNO [PRWF_RSTFNO]
			SER_INSTRU ---> SERVICIO INSTRUCTOR
			SER_SERRSP ---> SERVICIO RESPONSABLE
			SER_FECPUB ---> FECHA PUBLICACION [PRWF_FECPUB]
			SER_FECDES ---> FECHA DESPUBLICACION [PRWF_FECCAD]
			SER_FECACT ---> FECHA ACTUALIZACION [PROC_FECACT]
			SER_TRAULR ---> TRAMITE URL [PRWF_SVTREL.URL]
			SER_TRAID  ---> TRAMITE ID [PRWF_SVTREL.ID]
			SER_TRAVER ---> TRAMITE VERSION  [PRWF_SVTREL.VERS]
			SER_CTELEM ---> TRAMITE ES TELEMATICO [PRWF_SVTREL.TELEMATICO]
			SER_CPRESE ---> TRAMITE ES PRESENCIAL [PRWF_SVTREL.PRESENCIAL]
			SER_CTELEF ---> TRAMITE ES TELEFONICO [PRWF_SVTREL.TELEFONICO]
			SER_COMUN  ---> COMUN  [PRWF_COMUN]
			SER_CODPLT ---> TRAMITE PLANTILLA [PRWF_SVTREL.PRWF_SVTPRE]
			SER_PARAMS ---> TRAMITE PARAMS [PRWF_SVTREL.TELEFONICO]
			SER_CODLEG ---> LEGITIMACION [PRWF_DPTIPLEGI]
			SER_CLOPD  ---> LOPD ACTIVO  [PROC_LOPDACT]
		   *SER_PDTVAL ---> PENDIENTE VALIDACION
		(TRADUCCIONES) RSC_TRASER:
			TSR_CODSER ---> CODIGO SERVICIO [TRPW_CODIGO]
			TSR_CODIDI ---> IDIOMA [TRPW_IDIOMA]
			TSR_NOMBRE ---> NOMBRE [TRPW_NOMBRE]
			TSR_OBJETO ---> OBJETO [TRPW_OBJETO]
			TSR_DESTIN ---> DESTINO  [TRPW_DESTIN]
			TSR_REQUIS ---> REQUISITOS [TRPW_SVREQ]
			TSR_OBSERV ---> OBSERVACIONES  [TRPW_OBSER]
			TSR_ULRSER ---> URL SERVICIO
		   *TSR_LOPDFI ---> LOPD FINALIDAD
		   *TSR_LOPDDS ---> LOPD DESTINO
		   *TSR_LOPDDR ---> LOPD DERECHOS
		   *TSR_LOPDIA ---> LOPD FINALIDAD


		RS2_PROC
			PROC_CODIGO  ---> CODIGO PROCEDIMIENTO
			PROC_TIPO    ---> TIPO (PROC / SERV)
			PROC_SIACOD  ---> CODIGO SIA
			PROC_SIAEST  ---> ESTADO SIA
			PROC_SIAFC   ---> FECHA SIA
			PROC_SIADIR3 ---> DIR3
			PROC_MENSA   ---> MENSAJE
			PROC_PDTIDX  ---> PENDIENTE IDX
			PROC_DATIDX  ---> FECHA INDEXACION
			PROC_DATINX  --->
			PROC_ERRIDX  ---> ERROR INDEXACION SOLR
			PROC_ERRSIA  ---> ERROR SIA
			PROC_PDTGST  ---> MENSAJE GESTOR
			PROC_PDTSUP  ---> MENSAJE SUPERVISOR
			PROC_FECACT  ---> FECHA ACTUALIZACION
		RS2_PRCWF
			PRWF_CODIGO  ---> CODIGO PROCEDIMIENTO WF
			PRWF_CODPROC ---> CODIGO PROCEDIMIENTO
			PRWF_WF      ---> WF : DEFINITIVO O EN MODIFICACION
			PRWF_WFESTADO --> ESTADO : PUB/ MOD/RES/BOR
			PRWF_WFUSUA  ---> USUARIO
			PRWF_CODUAR  ---> UA RESPONSABLE
			PRWF_CODUAI  ---> UA INSTRUCTOR
			PRWF_INTERNO ---> INTERNO
			PRWF_RSNOM   ---> RESPONSABLE NOMBRE
			PRWF_RSEMA   ---> RESPONSABLE EMAIL
			PRWF_RSTFNO  ---> RESPONSABLE TELFNO
			PRWF_DPTIPLEGI -> DATOS PERSONALES LEGITIMACION
			PRWF_LSTDOC  ---> LISTA DOCUMENTOS
			PRWF_LSLOPD  ---> LISTA DOCUMENTOS LOPD
			PRWF_PRCODUAC --> UA COMPETENTE
			PRWF_PRTIPINIC --> FORMA DE INICIO
			PRWF_PRTIPSIAD --> SILENCIO ADMINISTRATIVO
			PRWF_SVTASA  ----> PARA SERVICIOS: TIENE TASA
			PRWF_SVTPRE  ----> PARA SERVICIOS: TIPO TRAMITACION PLANTILLA
			PRWF_FECPUB  ----> FECHA PUBLICACION
			PRWF_FECCAD  ----> FECHA CADUCIDAD
			PRWF_TIPPRO  ----> TIPO PROCEDIMIENTO
			PRWF_TIPVIA  ----> TIPO VIA
			PROC_LOPDRESP ---> LOPD RESPONSABLE
			PRWF_SVPRES   ---> PARA SERVICIOS: ES PRESENCIAL
			PRWF_SVELEC   ---> PARA SERVICIOS: ES TELEMATICO
			PRWF_SVTEL    ---> PARA SERVICIOS: ES TELEFONICO
			PRWF_COMUN    ---> COMUN
			PRWF_HABAPO   ---> HABILITADO PARA APODERADOS
			PRWF_HABFUN   ---> HABILITADO PARA FUNCIONARIO
			PRWF_SVTREL   ---> PARA SERVICIOS: TIPO TRAMITACION
			PROC_LOPDACT  ---> PARA SERVICIOS: SI ESTA ACTIVO LOPD
		(CAMPOS SIN RELACION)

		RELACIONES:
			***** TODO ****
			RS2_DOCNORM   --> DOCUMENTOS NORM
			RS2_AFECTA    --> AFECTACIONES Y AFECTACIONES ORIGEN

		TRADUCCIONES (RS2_TRAPRWF) ESTAN TODAS:
			TRPW_CODIGO   --> CODIGO TRADUCCION PROC  WF
			TRPW_CODPRWF  --> CODIGO PROC WF
			TRPW_IDIOMA   --> IDIOMA
			TRPW_NOMBRE   --> NOMBRE
			TRPW_OBJETO   --> OBJETO
			TRPW_DESTIN   --> DESTINATARIO
			TRPW_OBSER    --> OBSERVACION
			TRPW_DPFINA   --> DATOS PERSONALES: FINALIDAD
			TRPW_DPDEST   --> DATOS PERSONALES: DESTINATARIO
		   *TRPW_DPDOC    --> DOCUMENTO LOPD
		   ?TRPW_SVREQ    --> PARA SERVICIOS: REQUISITOS
		   ?TRPW_PRRESO   --> TERMINO RESOLUCION



	***/
	CURSOR cursortradprocedsrolsac1 (
		codpro NUMBER) IS
		SELECT *
		FROM   r1_procedimientos_trad
		WHERE  tpr_codpro = codpro;
	CURSOR cursornormativasrolsac1 (
		codpro NUMBER) IS
		SELECT *
		FROM   r1_procedimientos_norm
		WHERE  prn_codpro = codpro
		  AND prn_codnor IN (SELECT norm_codigo
		                     FROM   RS2_NORMA);
	CURSOR cursorpublicorolsac1 (
		codpro NUMBER) IS
		SELECT *
		FROM   r1_procedimientos_pobj
		WHERE  ppr_codpro = codpro
		  AND ppr_codpob IN (SELECT tpsp_codigo
		                     FROM   rs2_tipospu);
	CURSOR cursormateriasrolsac1 (
		codpro NUMBER) IS
		SELECT *
		FROM   r1_procedimientos_mate
		WHERE  prm_codpro = codpro
		  AND prm_codmat IN (SELECT tema_codigo
		                     FROM   rs2_tema);
	CURSOR cursortramitesrolsac1 (
		codpro NUMBER) IS
		SELECT *
		FROM   r1_tramites
		WHERE  tra_codpro = codpro;
	CURSOR cursordocumentos(
		codpro NUMBER) IS
		SELECT *
		FROM   r1_procedimientos_doc
		WHERE  doc_codpro = codpro
		ORDER  BY doc_orden;
	CURSOR cursordocumentostram(
		codtra NUMBER,
		tipo   NUMBER) IS
		SELECT *
		FROM   r1_tramites_doc
		WHERE  dtr_codtra = codtra
		  AND dtr_tipus = tipo
		ORDER  BY dtr_orden;

	CURSOR cursorAuditoria(
		codpro NUMBER) IS
		SELECT HIS_CODI, HIS_TYPE, HIS_NOMBRE, HIS_CODPRO, AUD_CODI, AUD_USUARI, AUD_FECHA, AUD_CODOPE
		FROM r1_procedimientos_audito,
		     r1_procedimientos_histor
		WHERE  his_codpro = codpro
		  AND  aud_codhis = his_codi
		ORDER  BY AUD_FECHA;
	maximoid                   NUMBER;
	valor                      NUMBER;
	existe                     NUMBER;
	codigo_boletin             NUMBER;
	l_clob                     CLOB := empty_clob;
	wf                         NUMBER;
	wfestado                   VARCHAR2(1 CHAR);
	interno                    NUMBER;
	pro_valida                 NUMBER;
	nombre                     VARCHAR2(4000);
	existe_trad                NUMBER;
	codigosia                  NUMBER(10, 0);
	codigosiar2                NUMBER(10, 0);
	codigosiaexist             NUMBER;
	codigo_procwf              NUMBER(10, 0);
	existe_mat_prc             NUMBER;
	tipotram_plantilla         NUMBER(10, 0);
	tipotram                   NUMBER(10, 0);
	lstdoc                     NUMBER(10, 0);
	lslopd                     NUMBER(10, 0);
	existe_archivos_lopd       NUMBER;
	existe_archivos            NUMBER;
	orden                      NUMBER;
	total_doc_tram_relacionado NUMBER;
	total_doc_tram_modelos     NUMBER;
	lstdoctram                 NUMBER;
	ldsocodigo                 NUMBER;
	rs2_codpr_lopd             NUMBER;
	existe_trad_es             NUMBER;
	existe_trad_ca             NUMBER;
	existe_trad_tram_es        NUMBER;
	existe_trad_tram_ca        NUMBER;
	vUsuario                   NUMBER(10,0);
	vExisteUsuario             NUMBER;
	mensajeAuditoria           VARCHAR2(255 CHAR);
	V_PRWF_CODUAI              NUMBER(10, 0);
	V_PRWF_PRCODUAC            NUMBER(10, 0);
	V_PRWF_CODUAR              NUMBER(10, 0);
	V_pro_coduna               NUMBER(10, 0);
	V_pro_coduna_resol         NUMBER(10, 0);
	V_PRO_CODUNA_SERV          NUMBER(10, 0);
	V_CODORG              NUMBER(10, 0);
	-- Excepción específica para el -20001
	ex_ua_no_migrada EXCEPTION;
	PRAGMA EXCEPTION_INIT(ex_ua_no_migrada, -20001);
	v_uaresp  VARCHAR2(1000 CHAR) ;
BEGIN
	dbms_lob.Createtemporary(l_clob, TRUE);

	dbms_lob.OPEN(l_clob, dbms_lob.lob_readwrite);

	SELECT Count(*)
	INTO   existe
	FROM   rs2_proc
	WHERE  proc_codigo = codigo;

	SELECT Count(*)
	INTO   existe_trad
	FROM   r1_procedimientos_trad
	WHERE  tpr_codpro = codigo;

	SELECT Count(*)
	INTO   existe_trad_es
	FROM   r1_procedimientos_trad
	WHERE  tpr_codpro = codigo
	  AND  TPR_NOMBRE IS NOT NULL
	  AND tpr_codidi = 'es';

	SELECT Count(*)
	INTO   existe_trad_ca
	FROM   r1_procedimientos_trad
	WHERE  tpr_codpro = codigo
	  AND  TPR_NOMBRE IS NOT NULL
	  AND tpr_codidi = 'ca';

	nombre := 'SIN TRAD';

	IF existe_trad > 0 THEN
		SELECT tpr_nombre
		INTO   nombre
		FROM   r1_procedimientos_trad
		WHERE  tpr_codpro = codigo
		  AND ROWNUM = 1;
	END IF;

	SELECT pro_valida
	INTO   pro_valida
	FROM   r1_procedimientos
	WHERE  pro_codi = codigo;


	IF existe = 0 THEN
		/** CAPTURAMOS POR SI SE PRODUCE UN ERROR NO PREVISTO */
		BEGIN
			/** MIGRAMOS LOS DATOS BASE EN UN NUEVO CAMPO EN ROLSAC2  */
			INSERT INTO rs2_proc
			(proc_codigo,
			 proc_tipo,
			 proc_siacod,
			 proc_siaest,
			 proc_siafc,/*
                                                     PROC_MENSA,
                                                     PROC_PDTIDX,
                                                     PROC_DATIDX,
                                                     PROC_DATINX,
                                                     PROC_ERRIDX,
                                                     PROC_ERRSIA,
                                                     PROC_PDTGST,
                                                     PROC_PDTSUP, */
			 proc_fecact)
			SELECT pro_codi,
			       'P',
			       pro_codsia,
			       pro_estsia,
			       pro_fecsia,
			       pro_fecact
			FROM   r1_procedimientos
			WHERE  pro_codi = codigo;

			/** INSERTAMOS LAS AUDITORIAS **/
			FOR rolsac1_auditoria IN cursorAuditoria(codigo)
				LOOP
					IF rolsac1_auditoria.AUD_CODOPE = 1
					THEN
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: INSERTAT';
					ELSIF rolsac1_auditoria.AUD_CODOPE = 2
					THEN
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: MODIFICAT';
					ELSIF rolsac1_auditoria.AUD_CODOPE = 3
					THEN
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: VALIDAT';
					ELSIF rolsac1_auditoria.AUD_CODOPE = 4
					THEN
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: RESERVA';
					ELSIF rolsac1_auditoria.AUD_CODOPE =5
					THEN
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: ESBORRAT';
					ELSIF rolsac1_auditoria.AUD_CODOPE =6
					THEN
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: INVALIDAT';
					ELSE
						mensajeAuditoria := 'MIGRACIÓ ROLSAC1: DESC';
					END IF;

					INSERT INTO
						RS2_PRAUDIT (
						PRAU_CODIGO,
						PRAU_CODPROC,
						PRAU_FECMOD,
						PRAU_LSTMOD,
						PRAU_USUMOD,
						PRAU_USUPRF,
						PRAU_LTLFLJ,
						PRAU_ACCION
					)
					VALUES (
						       RS2_PRAUDIT_SEQ.NEXTVAL,
						       codigo,
						       rolsac1_auditoria.AUD_FECHA,
						       '[]',
						       rolsac1_auditoria.AUD_USUARI,
						       '',
						       mensajeAuditoria,
						       rolsac1_auditoria.AUD_CODOPE
					       );
				END LOOP;

			/** LA INFORMACI¿N DE LOS DOCUMENTOS. **/
			SELECT Count(*)
			INTO   existe_archivos_lopd
			FROM   r1_procedimientos_trad
			WHERE  tpr_codpro = codigo
			  AND tpr_lopdia IS NOT NULL;

			IF existe_archivos_lopd > 0 THEN
				SELECT rs2_lstdoc_seq.NEXTVAL
				INTO   lslopd
				FROM   dual;

				INSERT INTO rs2_lstdoc
				(lsdo_codigo)
				VALUES      (lslopd);
			END IF;

			SELECT Count(*)
			INTO   existe_archivos
			FROM   r1_procedimientos_doc
			WHERE  doc_codpro = codigo;

			IF existe_archivos > 0 THEN
				SELECT rs2_lstdoc_seq.NEXTVAL
				INTO   lstdoc
				FROM   dual;

				INSERT INTO rs2_lstdoc
				(lsdo_codigo)
				VALUES      (lstdoc);
			END IF;

			/** SEGUN ROLSAC1, VALIDACION ES:
			PUBLICA = 1, INTERNA = 2, RESERVA = 3 BAJA = 4;
			PUBLICA SERA DEFINITIVO Y PUBLICADO
			INTERNA SERA ENMODIFICACION Y MODIFICACION
			RESERVA SERA DEFINITIVO Y RESERVA
			BAJA SERA DEFINITIVO Y BORRADO
			*/
			IF pro_valida = 1 THEN
				wf := 0;

				wfestado := 'P';

				interno := 0;
			ELSIF pro_valida = 2 THEN
				wf := 1;

				wfestado := 'M';

				interno := 1;
			ELSIF pro_valida = 3 THEN
				wf := 0;

				wfestado := 'T';

				interno := 1;
			ELSE /** PRO_VALIDA = 4 **/
				wf := 0;

				wfestado := 'B';

				interno := 0;
			END IF;

			SELECT rs2_prcwf_seq.NEXTVAL
			INTO   codigo_procwf
			FROM   dual;

			SELECT pro_coduna, pro_coduna_resol, PRO_CODUNA_SERV
			INTO   v_pro_coduna, v_pro_coduna_resol, v_PRO_CODUNA_SERV
			FROM   r1_procedimientos
			WHERE  pro_codi = codigo;

			V_PRWF_CODUAI := obtenerUAconDIR3( v_pro_coduna);
			V_PRWF_PRCODUAC := obtenerUAconDIR3(v_pro_coduna_resol);
			V_PRWF_CODUAR := obtenerUAconDIR3( NVL (v_PRO_CODUNA_SERV, v_pro_coduna) );

			IF V_PRWF_CODUAI IS NULL or
			   V_PRWF_CODUAR IS NULL
			THEN
				RAISE_APPLICATION_ERROR(
						-20010,
						'La Unitat Administrativa Responsable és nul, comprovi el procediment perquè té assignat una Unitat Administrativa que no penja de l''arrel GOIB.'
				);
			END IF;

			IF CHECK_CUELGA_UA_PROC(V_PRWF_CODUAR, 1) = 0
			THEN
				RAISE_APPLICATION_ERROR(
						-20010,
						'La Unitat Administrativa té assignat una Unitat Administrativa Responsable que no penja de l''arrel GOIB.'
				);
			END IF;

			IF CHECK_CUELGA_UA_PROC(V_PRWF_CODUAI, 1) = 0
			THEN
				RAISE_APPLICATION_ERROR(
						-20010,
						'La Unitat Administrativa té assignat una Unitat Administrativa Instructora que no penja de l''arrel GOIB.'
				);
			END IF;

			IF V_PRWF_PRCODUAC IS NOT NULL AND  CHECK_CUELGA_UA_PROC(V_PRWF_PRCODUAC, 1) = 0
			THEN
				RAISE_APPLICATION_ERROR(
						-20010,
						'La Unitat Administrativa té assignat una Unitat Administrativa Compentent que no penja de l''arrel GOIB.'
				);
			END IF;

			INSERT INTO rs2_prcwf
			(prwf_codigo,
			 prwf_codproc,
			 prwf_wf,
			 prwf_wfestado,
				/*PRWF_WFUSUA,*/
             PRWF_CODUAI,
             PRWF_PRCODUAC,
             PRWF_CODUAR,
             prwf_interno,
             prwf_rsnom,
             prwf_rsema,
				/*PRWF_RSTFNO,*/
             prwf_dptiplegi,
				/*PRWF_LSTDOC,
				PRWF_LSLOPD,*/
				/*PRWF_PRCODUAC,*/
             prwf_prtipinic,
             prwf_prtipsiad,
				/*PRWF_PRTIPFVIA, */
             prwf_svtasa,
				/*PRWF_SVTPRE,*/
             prwf_fecpub,
             prwf_feccad,
             prwf_tippro,
             prwf_tipvia,
				/*PROC_LOPDRESP,
				PRWF_SVPRES,
				PRWF_SVELEC,
				PRWF_SVTEL,*/
             prwf_comun,
             prwf_habapo,
             prwf_habfun,
             prwf_lstdoc,
             prwf_lslopd)
			SELECT codigo_procwf,
			       codigo,
			       wf,
			       wfestado,
				/*PRWF_WFUSUA,*/
				   V_PRWF_CODUAI,
				   V_PRWF_PRCODUAC ,
				   V_PRWF_CODUAR,
				   interno,
				   SUBSTR(NVL(pro_respon, 'Desconegut'), 1, 255) AS prwf_rsnom,
				   SUBSTR(NVL(pro_info, ''), 1, 100) AS prwf_rsema,
				/*PRWF_RSTFNO,*/
				   pro_codleg,
				/*PRWF_LSTDOC,
				PRWF_LSLOPD,*/
				/*PRWF_PRCODUAC,*/
				   pro_codini,
				   pro_codsil,
				/*PRWF_PRTIPFVIA,*/
				   CASE
					   WHEN pro_taxa IN ('0', '1') THEN pro_taxa
					   WHEN pro_taxa IS NULL THEN NULL
					   ELSE '0'
				       END AS pro_taxa,
				/*PRWF_SVTPRE,*/
				   pro_fecpub,
				   pro_feccad,
				   pro_codfam,
				   pro_indica,
				/*PROC_LOPDRESP,
				PRWF_SVPRES,
				PRWF_SVELEC,
				PRWF_SVTEL,*/
				   CASE
					   WHEN pro_comun IS NULL THEN 0   -- O NULL si quieres permitirlo
					   WHEN pro_comun BETWEEN 0 AND 9 THEN pro_comun
					   ELSE 0
				       END as pro_comun,
				   CASE
					   WHEN pro_apohab IS NULL THEN NULL
					   WHEN pro_apohab BETWEEN 0 AND 9 THEN pro_apohab
					   ELSE 0
				       END as pro_apohab,
				   CASE
					   WHEN pro_funhab = 1 THEN 'S'
					   ELSE 'N'
				       END,
				   lstdoc,
				   lslopd
			FROM   r1_procedimientos
			WHERE  pro_codi = codigo;

			/** INTRODUCIMOS LOS PUBLICO OBJETIVOS. **/
			FOR rolsac1_tramites IN cursortramitesrolsac1(codigo)
				LOOP


					tipotram_plantilla := NULL;

					tipotram := NULL;

					IF rolsac1_tramites.tra_codpln IS NOT NULL THEN
						tipotram_plantilla := rolsac1_tramites.tra_codpln;
					ELSE
						/** CREAMOS LA INFORMACI¿N DE TIPO TRAMITACION **/
						SELECT rs2_trmpre_seq.NEXTVAL
						INTO   tipotram
						FROM   dual;

						INSERT INTO rs2_trmpre
						(pres_codigo,
						 pres_trpres,
						 pres_trelec,
						 pres_trtel,
						 pres_intptr,
						 pres_inttid,
						 pres_faseproc,
						 pres_inttve,
						 pres_inttpa,
						 pres_planti,
						 pres_codenti)
						VALUES      (tipotram,
						             Coalesce(rolsac1_tramites.tra_cprese, 0),
						             Coalesce(rolsac1_tramites.tra_ctelem, 0),
						             0,/** EL TELEFONICO SOLO EN SERVICIOS **/
						             rolsac1_tramites.tra_codplt,
						             rolsac1_tramites.tra_idtramtel,
						             rolsac1_tramites.tra_fase,
						             rolsac1_tramites.tra_versio,
						             rolsac1_tramites.tra_params,
						             0,
						             codigoentidad);

						/** insertamos las traducciones de tramites **/
						INSERT INTO rs2_tratptra
						(trtt_codigo,
						 trtt_codtptra,
						 trtt_idioma,
						 trtt_descri,
						 trtt_url)
						SELECT rs2_tratptra_seq.NEXTVAL,
						       tipotram,
						       ttr_codidi,
						       NULL,/*TTR_DESCRI,*/
						       ttr_ulrtra
						FROM   r1_tramites_trad
						WHERE  ttr_codttr = rolsac1_tramites.tra_codi
						  AND TTR_NOMBRE IS NOT NULL;



					END IF;

					SELECT Count(*)
					INTO   total_doc_tram_modelos
					FROM   r1_tramites_doc
					WHERE  dtr_codtra = rolsac1_tramites.tra_codi
					  AND dtr_tipus = 1;

					SELECT Count(*)
					INTO   total_doc_tram_relacionado
					FROM   r1_tramites_doc
					WHERE  dtr_codtra = rolsac1_tramites.tra_codi
					  AND dtr_tipus = 0;

					lstdoctram := NULL;

					ldsocodigo := NULL;

					IF total_doc_tram_modelos > 0 THEN
						SELECT rs2_lstdoc_seq.NEXTVAL
						INTO   ldsocodigo
						FROM   dual;

						INSERT INTO rs2_lstdoc
						(lsdo_codigo)
						VALUES      (ldsocodigo);
					END IF;

					IF total_doc_tram_relacionado > 0 THEN
						SELECT rs2_lstdoc_seq.NEXTVAL
						INTO   lstdoctram
						FROM   dual;

						INSERT INTO rs2_lstdoc
						(lsdo_codigo)
						VALUES      (lstdoctram);
					END IF;

					IF rolsac1_tramites.TRA_ORGCOMP IS NULL
					THEN
						v_codorg := V_PRWF_CODUAI;
					ELSE
						V_CODORG := obtenerUAconDIR3(rolsac1_tramites.TRA_ORGCOMP);
					END IF;



					INSERT INTO rs2_prctrm
					(prta_codigo,
					 prta_codtram,
					 prta_coduac,
					 prta_codprwf,
					 prta_trmpre,
					 prta_lstdoc,
					 lsdo_codigo,
					 prta_tasa,
					 prta_fecpub,
					 prta_fecini,
					 prta_feccie,
					 prta_fase,
					 prta_trpres,
					 prta_trelec,
					 prta_trtel,
					 prta_orden,
					 prta_trmtrm)
					VALUES      ( rolsac1_tramites.tra_codi,
					              rolsac1_tramites.tra_codi,
					              V_CODORG,
					              codigo_procwf,
					              tipotram_plantilla,
					              lstdoctram,
					              ldsocodigo,
					              0,
					              rolsac1_tramites.tra_datpubl,
					              rolsac1_tramites.tra_datinici,
					              rolsac1_tramites.tra_dattancament,
					              rolsac1_tramites.tra_fase,
					              Coalesce(rolsac1_tramites.tra_cprese, 0),
					              Coalesce(rolsac1_tramites.tra_ctelem, 0),
					              0,
					              rolsac1_tramites.tra_orden,
					              tipotram);

					/** INSERTAMOS LAS TRADUCCIONES **/
					INSERT INTO rs2_traprta
					(trta_codigo,
					 trta_codprta,
					 trta_idioma,
					 trta_requisitos,
					 trta_nombre,
					 trta_docum,
					 trta_observ,
					 trta_termin)
					SELECT rs2_traprta_seq.NEXTVAL,
					       rolsac1_tramites.tra_codi,
					       ttr_codidi,
					       ttr_requi,
					       ttr_nombre,
					       ttr_docume,
					       ttr_descri,
					       ttr_plazos
					FROM   r1_tramites_trad
					WHERE  ttr_codttr = rolsac1_tramites.tra_codi
					  and ttr_nombre is not null;

					SELECT Count(*)
					INTO   existe_trad_tram_es
					FROM rs2_traprta
					WHERE  trta_codprta = rolsac1_tramites.tra_codi
					  AND trta_idioma = 'es';

					SELECT Count(*)
					INTO   existe_trad_tram_ca
					FROM rs2_traprta
					WHERE  trta_codprta = rolsac1_tramites.tra_codi
					  AND trta_idioma = 'ca';

					/** SI NO EXISTE LA TRADUCCION EN ESPANYOL, DUPLICAMOS LA CATALAN **/
					IF existe_trad_tram_es = 0
						AND existe_trad_tram_ca = 1 THEN
						INSERT INTO rs2_traprta
						(trta_codigo,
						 trta_codprta,
						 trta_idioma,
						 trta_requisitos,
						 trta_nombre,
						 trta_docum,
						 trta_observ,
						 trta_termin)
						SELECT rs2_traprta_seq.NEXTVAL,
						       trta_codprta,
						       'es',
						       trta_requisitos,
						       trta_nombre,
						       trta_docum,
						       trta_observ,
						       trta_termin
						FROM rs2_traprta
						WHERE trta_idioma = 'ca'
						  AND trta_codprta = rolsac1_tramites.tra_codi;
					END IF;


					IF lstdoctram IS NOT NULL THEN
						orden := 0;

						FOR documento IN cursordocumentostram(rolsac1_tramites.tra_codi,
						                 0)
							LOOP
								INSERT INTO rs2_docpr
								(dopr_codigo,
								 dopr_orden,
								 docpr_codlsd)
								VALUES      (rs2_docpr_seq.NEXTVAL,
								             CASE
									             WHEN orden > 99 THEN 99
									             ELSE orden
									             END,
								             lstdoctram);

								INSERT INTO rs2_tradopr
								(trdp_codigo,
								 trdp_coddopr,
								 trdp_idioma,
								 trdp_titulo,
								 trdp_descri,
								 trdp_ficrol1)
								SELECT rs2_tradopr_seq.NEXTVAL,
								       rs2_docpr_seq.CURRVAL,
								       tdo_codidi,
								       tdo_titulo,
								       tdo_descri,
								       tdo_codarc
								FROM   r1_tramites_doc_trad
								WHERE  tdo_codtra = documento.dtr_codi;

								orden := orden + 1;
							END LOOP;
					END IF;

					IF ldsocodigo IS NOT NULL THEN
						orden := 0;

						FOR documento IN cursordocumentostram(rolsac1_tramites.tra_codi,
						                 1)
							LOOP
								INSERT INTO rs2_docpr
								(dopr_codigo,
								 dopr_orden,
								 docpr_codlsd)
								VALUES      (rs2_docpr_seq.NEXTVAL,
								             orden,
								             ldsocodigo);

								INSERT INTO rs2_tradopr
								(trdp_codigo,
								 trdp_coddopr,
								 trdp_idioma,
								 trdp_titulo,
								 trdp_descri,
								 trdp_ficrol1)
								SELECT rs2_tradopr_seq.NEXTVAL,
								       rs2_docpr_seq.CURRVAL,
								       tdo_codidi,
								       tdo_titulo,
								       tdo_descri,
								       tdo_codarc
								FROM   r1_tramites_doc_trad
								WHERE  tdo_codtra = documento.dtr_codi;

								orden := orden + 1;
							END LOOP;
					END IF;
				END LOOP;

			/** INTRODUCIMOS LAS NORMATIVAS. **/
			FOR rolsac1_normativa IN cursornormativasrolsac1(codigo) LOOP
					INSERT INTO rs2_prcnor
					(prwf_codigo,
					 norm_codigo)
					VALUES      (codigo_procwf,
					             rolsac1_normativa.prn_codnor);
				END LOOP;

			/** INTRODUCIMOS LOS PUBLICO OBJETIVOS. **/
			FOR rolsac1_pubobj IN cursorpublicorolsac1(codigo) LOOP
					INSERT INTO rs2_prcpub
					(prpo_codprwf,
					 prpo_tippobj)
					VALUES      (codigo_procwf,
					             rolsac1_pubobj.ppr_codpob);
				END LOOP;

			/** INTRODUCIMOS LAS MATERIAS. **/
			FOR rolsac1_materias IN cursormateriasrolsac1(codigo) LOOP
					dbms_output.Put_line('materias: '
						|| rolsac1_materias.prm_codmat);

					/** METEMOS EL TEMA **/
					INSERT INTO rs2_prctem
					(prtm_codprwf,
					 prtm_codtema)
					VALUES      (codigo_procwf,
					             rolsac1_materias.prm_codmat );
				END LOOP;

			/** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
			IF lslopd IS NOT NULL THEN
				SELECT rs2_docpr_seq.NEXTVAL
				INTO   rs2_codpr_lopd
				FROM   dual;

				INSERT INTO rs2_docpr
				(dopr_codigo,
				 dopr_orden,
				 docpr_codlsd)
				VALUES      (rs2_codpr_lopd,
				             0,
				             lslopd);
			END IF;

			/** INTRODUCIMOS LAS TRADUCCIONES **/
			FOR rolsac1_tradproc IN cursortradprocedsrolsac1(codigo) LOOP

					IF rolsac1_tradproc.tpr_nombre IS NOT NULL
					THEN

						/* Primer intento: idioma de la traducción */
						SELECT SUBSTR(
								       COALESCE(MAX(NULLIF(TRIM(TUN_NOMBRE), '')), ''),
								       1,
								       1000
						       )
						INTO v_uaresp
						FROM R1_UNIADM_TRAD
						WHERE TUN_CODUNA = v_PRO_CODUNA_SERV
						  AND TUN_CODIDI = rolsac1_tradproc.tpr_codidi;

						/* Si no encuentra nada, segundo intento: catalán */
						IF v_uaresp IS NULL OR TRIM(v_uaresp) = '' THEN
							SELECT SUBSTR(
									       COALESCE(MAX(NULLIF(TRIM(TUN_NOMBRE), '')), ''),
									       1,
									       1000
							       )
							INTO v_uaresp
							FROM R1_UNIADM_TRAD
							WHERE TUN_CODUNA = v_PRO_CODUNA_SERV
							  AND TUN_CODIDI = 'ca';
						END IF;

						INSERT INTO rs2_traprwf
						(trpw_codigo,
						 trpw_codprwf,
						 trpw_idioma,
						 trpw_nombre,
						 trpw_objeto,
						 trpw_destin,
						 trpw_obser,
						 trpw_prreso,
						 trpw_dpfina,
						 trpw_dpdest,
						 trpw_uaresp)
						VALUES      ( rs2_traprwf_seq.NEXTVAL,
						              codigo_procwf,
						              rolsac1_tradproc.tpr_codidi,
						              SUBSTR(NVL(rolsac1_tradproc.tpr_nombre, ''), 1, 255) ,
						              rolsac1_tradproc.tpr_resume,
						              rolsac1_tradproc.tpr_destin,
						              rolsac1_tradproc.tpr_observ,
						              rolsac1_tradproc.tpr_resolucion,
						              rolsac1_tradproc.tpr_lopdfi,
						              rolsac1_tradproc.tpr_lopdds,
						              v_uaresp
						            );

						/** SI HAY LOPD, CREAMOS LOS FICHEROS. **/
						IF lslopd IS NOT NULL
							AND rolsac1_tradproc.tpr_lopdia IS NOT NULL THEN
							INSERT INTO rs2_tradopr
							(trdp_codigo,
							 trdp_coddopr,
							 trdp_idioma,
							 trdp_titulo,
							 trdp_descri,
							 trdp_ficrol1)
							VALUES      (rs2_tradopr_seq.NEXTVAL,
							             rs2_codpr_lopd,
							             rolsac1_tradproc.tpr_codidi,
							             '',
							             '',
							             rolsac1_tradproc.tpr_lopdia);
						END IF;

					END IF;
				END LOOP;

			/** SI NO EXISTE LA TRADUCCION EN ESPANYOL, DUPLICAMOS LA CATALAN **/
			IF existe_trad_es = 0
				AND existe_trad_ca = 1 THEN

				IF v_uaresp IS NULL
				THEN
					SELECT SUBSTR(
							       COALESCE(MAX(NULLIF(TRIM(TUN_NOMBRE), '')), ''),
							       1,
							       1000
					       )
					INTO v_uaresp
					FROM R1_UNIADM_TRAD
					WHERE TUN_CODUNA = v_PRO_CODUNA_SERV
					  AND TUN_CODIDI = 'ca';
				END IF;

				INSERT INTO rs2_traprwf
				(trpw_codigo,
				 trpw_codprwf,
				 trpw_idioma,
				 trpw_nombre,
				 trpw_objeto,
				 trpw_destin,
				 trpw_obser,
				 trpw_prreso,
				 trpw_dpfina,
				 trpw_dpdest,
				 trpw_uaresp)
				SELECT rs2_traprwf_seq.NEXTVAL,
				       trpw_codprwf,
				       'es',
				       trpw_nombre,
				       trpw_objeto,
				       trpw_destin,
				       trpw_obser,
				       trpw_prreso,
				       trpw_dpfina,
				       trpw_dpdest,
				       v_uaresp
				FROM   rs2_traprwf
				WHERE  trpw_idioma = 'ca'
				  AND trpw_codprwf IN (SELECT prwf_codigo
				                       FROM   rs2_prcwf
				                       WHERE  prwf_codproc = codigo);
			END IF;

			/** SI HAY DOCS, CREAMOS LOS FICHEROS. **/
			IF lstdoc IS NOT NULL THEN
				orden := 0;

				FOR documento IN cursordocumentos(codigo) LOOP
						INSERT INTO rs2_docpr
						(dopr_codigo,
						 dopr_orden,
						 docpr_codlsd)
						VALUES      (rs2_docpr_seq.NEXTVAL,
						             orden,
						             lstdoc);

						INSERT INTO rs2_tradopr
						(trdp_codigo,
						 trdp_coddopr,
						 trdp_idioma,
						 trdp_titulo,
						 trdp_descri,
						 trdp_ficrol1)
						SELECT rs2_tradopr_seq.NEXTVAL,
						       rs2_docpr_seq.CURRVAL,
						       tdo_codidi,
						       SUBSTR(NVL(tdo_titulo, ''), 1, 256),
						       tdo_descri,
						       tdo_codarc
						FROM   r1_procedimientos_doc_trad
						WHERE  tdo_coddoc = documento.doc_codi
						/*AND TDO_CODARC IN (SELECT ARC_CODI FROM R1_ARCHIV WHERE ARC_DATOS IS NOT NULL)*/
						;

						orden := orden + 1;
					END LOOP;
			END IF;

			COMMIT;

			dbms_lob.Writeappend(l_clob, Length('El proc '
				|| codigo
				|| ' 1"'
				|| nombre
				|| '" se ha migrado.'), 'El proc '
				                     || codigo
				                     || ' 1"'
				                     || nombre
				                     ||
			                            '" se ha migrado.');
		EXCEPTION
			WHEN OTHERS THEN
				ROLLBACK;

				-- Si el error viene de obtenerUAconDIR3 (-20001)
				IF SQLCODE = -20001 THEN
					RAISE ex_ua_no_migrada;
				END IF;

				dbms_output.Put_line('SQLCODE:'
					|| SQLCODE);

				dbms_output.Put_line('SQLERRM:'
					|| SQLERRM);

				dbms_lob.Writeappend(l_clob, Length('El proc '
					|| codigo
					|| ' 2"'
					||nombre
					||'" ha dado el error. CODE:'
					|| SQLCODE
					|| '  MSG:'
					|| SQLERRM
					|| '. \n'), 'El proc '
					                     || codigo
					                     || ' 2"'
					                     ||nombre
					                     ||
				                '" ha dado el error. CODE:'
					                     || SQLCODE
					                     || '  MSG:'
					                     || SQLERRM
					                     || '. \n');
		END;
	ELSE
		dbms_lob.Writeappend(l_clob, Length('El proc '
			|| codigo
			|| ' "'
			||nombre
			||'" ya existe. \n'), 'El proc '
			                     || codigo
			                     || ' "'
			                     ||nombre
			                     ||
		                          '" ya existe. \n')
		;
	END IF;

	dbms_lob.CLOSE(l_clob);

	resultado := l_clob;
EXCEPTION
	WHEN ex_ua_no_migrada THEN
		ROLLBACK;
		resultado := 'El procediment ' || codigo || ' pertany a una UA que no se ha migrat, ' ||
		             'segurament perque no es vigent.';
	WHEN OTHERS THEN
		ROLLBACK;
		-- Aseguramos que el CLOB existe antes de escribir
		IF dbms_lob.istemporary(l_clob) = 0 THEN
			dbms_lob.createtemporary(l_clob, TRUE);
			dbms_lob.open(l_clob, dbms_lob.lob_readwrite);
		END IF;

		dbms_lob.writeappend(
				l_clob,
				length(
						'ERROR en MIGRAR_PROC. PRO=' || codigo ||
						' CODE=' || SQLCODE ||
						' MSG='  || SQLERRM || chr(10)
				),
				'ERROR en MIGRAR_PROC. PRO=' || codigo ||
				' CODE=' || SQLCODE ||
				' MSG='  || SQLERRM || chr(10)
		);

		resultado := l_clob;
END MIGRAR_PROC;
