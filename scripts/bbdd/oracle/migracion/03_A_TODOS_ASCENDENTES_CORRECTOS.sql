CREATE OR REPLACE FUNCTION todosAscendentesUACorrectos (
	p_unad_codigo RS2_UNIADM.UNAD_CODIGO%TYPE
) RETURN BOOLEAN IS

	v_dir3   RS2_UNIADM.UNAD_DIR3%TYPE;
	v_padre  RS2_UNIADM.UNAD_UNADPADRE%TYPE;
	v_valida  r1_uniadm.UNA_VALIDA%TYPE;
	v_correcto  BOOLEAN;
BEGIN
	 -- ESTE METODO ES PARA COMPROBAR SI TODOS LOS ASCENDENTES DE UNA UA SON CORRECTOS (TIENEN DIR3 Y VALIDA = 1)
	 -- SI TODOS SON CORRECTOS DEVUELVE TRUE, SINO FALSE

	IF p_unad_codigo IS null
	THEN
		/** SI EL PARAMETRO QUE SE PASA ES NULO, DEVOLVER TRUE, SEGURAMENTE SEA LA RAIZ **/
		RETURN true;
	END IF;

	SELECT UNA_CODDR3, UNA_CODUNA, UNA_VALIDA
	INTO v_dir3, v_padre, v_valida
	FROM r1_uniadm
	WHERE UNA_CODI = p_unad_codigo;

   IF v_dir3 IS NULL OR  v_valida != 1
   THEN
         RETURN FALSE;
   END IF;

	IF v_padre IS NULL
	THEN
		RETURN TRUE;
	END IF;
	 	-- buscar en el padre
	RETURN todosAscendentesUACorrectos(v_padre);

EXCEPTION
	WHEN NO_DATA_FOUND THEN
		RAISE_APPLICATION_ERROR(-20001,
		                        'No se encontró la unidad administrativa con código: ' || p_unad_codigo || ' seguramente porque no existe (al migrarse, solo se migran publicas)');
END;