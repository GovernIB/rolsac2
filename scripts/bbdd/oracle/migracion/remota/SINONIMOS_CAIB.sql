

/** UAS, USUARIOS, TRADUCCIONES Y RELACIONES ENTRE ELLOS **/
CREATE SYNONYM R1_UNIADM FOR RSC_UNIADM@ROLSAC;
CREATE SYNONYM R1_USUARIO FOR RSC_USUARI@ROLSAC;
CREATE SYNONYM R1_UNIADM_TRAD FOR RSC_TRAUNA@ROLSAC;
CREATE SYNONYM R1_UNIADM_USU FOR RSC_UNAUSU@ROLSAC;

/** NORMATIVAS Y TRADUCCIONES **/
CREATE SYNONYM R1_NORMAT FOR RSC_NORMAT@ROLSAC;
CREATE SYNONYM R1_NORMAT_TRAD FOR RSC_TRANOR@ROLSAC;
			   
/** MATERIAS **/
CREATE SYNONYM R1_MATERIAS FOR RSC_MATERI@ROLSAC;

/** PROCEDIMIENTOS, TRADUCCIONES, NORMATIVAS, PUB. OBJETIVO Y MATERIAS **/
CREATE SYNONYM R1_PROCEDIMIENTOS FOR RSC_PROCED@ROLSAC;
CREATE SYNONYM R1_PROCEDIMIENTOS_TRAD FOR RSC_TRAPRO@ROLSAC;
CREATE SYNONYM R1_PROCEDIMIENTOS_NORM FOR RSC_PRONOR@ROLSAC;
CREATE SYNONYM R1_PROCEDIMIENTOS_POBJ FOR RSC_POBPRO@ROLSAC;
CREATE SYNONYM R1_PROCEDIMIENTOS_MATE FOR RSC_PROMAT@ROLSAC;


/** SERVICIOS, TRADUCCIONES, NORMATIVAS, PUB. OBJETIVO Y MATERIAS **/
CREATE SYNONYM R1_SERVICIOS FOR RSC_SERVIC@ROLSAC;
CREATE SYNONYM R1_SERVICIOS_TRAD FOR RSC_TRASER@ROLSAC;
CREATE SYNONYM R1_SERVICIOS_NORM FOR RSC_SERNOR@ROLSAC;
CREATE SYNONYM R1_SERVICIOS_POBJ FOR RSC_POBSER@ROLSAC;
CREATE SYNONYM R1_SERVICIOS_MATE FOR RSC_SERMAT@ROLSAC;