CREATE OR REPLACE FUNCTION obtenerUAconDIR3 (
	p_unad_codigo RS2_UNIADM.UNAD_CODIGO%TYPE
) RETURN RS2_UNIADM.UNAD_CODIGO%TYPE IS

	v_dir3   RS2_UNIADM.UNAD_DIR3%TYPE;
	v_padre  RS2_UNIADM.UNAD_UNADPADRE%TYPE;
	v_valida  r1_uniadm.UNA_VALIDA%TYPE;
	v_correcto  BOOLEAN;
BEGIN
	IF p_unad_codigo IS null
	THEN
		/** SI EL PARAMETRO QUE SE PASA ES NULO, DEVOLVER NULO **/
		RETURN NULL;
	END IF;

	SELECT UNA_CODDR3, UNA_CODUNA, UNA_VALIDA
	INTO v_dir3, v_padre, v_valida
	FROM r1_uniadm
	WHERE UNA_CODI = p_unad_codigo;


	-- Si la unidad tiene DIR3, devolver su código
	IF v_dir3 IS NOT NULL AND v_valida = 1 THEN

		--Comprobamos que todos los ascendentes son correctos (tiene DIR3 y valida = 1)
		v_correcto := todosAscendentesUACorrectos(v_padre);
		IF v_correcto = TRUE
		THEN
			RETURN p_unad_codigo;
		END IF;
	END IF;

	-- Si no tiene padre, no hay más que buscar
	IF v_padre IS NULL
	THEN
		RETURN NULL;
	END IF;

	-- buscar en el padre
	RETURN obtenerUAconDIR3(v_padre);

EXCEPTION
	WHEN NO_DATA_FOUND THEN
		RAISE_APPLICATION_ERROR(-20001,
		                        'No se encontró la unidad administrativa con código: ' || p_unad_codigo || ' seguramente porque no existe (al migrarse, solo se migran publicas)');
END;