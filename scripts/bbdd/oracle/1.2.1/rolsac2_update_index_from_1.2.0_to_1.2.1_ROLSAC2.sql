DELETE FROM RS2_PROCES
WHERE PROCES_CODENTI = 1
  AND PROCES_IDENTI IN ('SIA_PUNT', 'SIA');

COMMIT;

insert into RS2_PROCES (PROCES_CODIGO, PROCES_CODENTI, PROCES_IDENTI, PROCES_DESCRI, proces_cron, proces_activo, proces_params) VALUES (RS2_PROCES_SEQ.NEXTVAL, 1, 'SIA_PUNT', 'Procés de llançament puntual SIA', null, 1, '[{"codigo":"valida","valor":"true"}]');
insert into RS2_PROCES (PROCES_CODIGO, PROCES_CODENTI, PROCES_IDENTI, PROCES_DESCRI, proces_cron, proces_activo, proces_params) VALUES (RS2_PROCES_SEQ.NEXTVAL, 1, 'SIA', 'Procés de llançament periòdic SIA', '0 30 2 * * ? ', 1, '[{"codigo":"valida","valor":"true"}]');

commit;