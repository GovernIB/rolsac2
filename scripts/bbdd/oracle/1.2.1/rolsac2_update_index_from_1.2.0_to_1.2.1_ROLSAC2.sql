UPDATE RS2_PROCES
SET PROCES_DESCRI = 'Procés de llançament puntual SIA',
    PROCES_CRON = NULL,
    PROCES_ACTIVO = 1,
    PROCES_PARAMS = '[{"codigo":"valida","valor":"true"}]'
WHERE PROCES_CODENTI = 1
  AND PROCES_IDENTI = 'SIA_PUNT';


UPDATE RS2_PROCES
SET PROCES_DESCRI = 'Procés de llançament periòdic SIA',
    PROCES_CRON = '0 30 2 * * ? ',
    PROCES_ACTIVO = 1,
    PROCES_PARAMS = '[{"codigo":"valida","valor":"true"}]'
WHERE PROCES_CODENTI = 1
  AND PROCES_IDENTI = 'SIA';

COMMIT;
