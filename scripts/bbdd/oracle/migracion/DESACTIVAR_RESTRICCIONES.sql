create or replace PROCEDURE "DESACTIVAR_RESTRICCIONES" 
AS 
BEGIN
     /** DESACTIVAMOS LA FK DE UA PADRE, SINO HABRÍA QUE REALIZAR UN ORDENADO COMPLEJO**/
    EXECUTE IMMEDIATE 'ALTER TABLE RS2_UNIADM DISABLE CONSTRAINT RS2_UNIADM_UNIADM_FK';

END;